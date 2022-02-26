//$Header: /as2/de/mendelson/comm/as2/configurationcheck/ConfigurationCheckController.java 33    27/01/22 11:34 Heller $
package de.mendelson.comm.as2.configurationcheck;

import de.mendelson.comm.as2.preferences.PreferencesAS2;
import de.mendelson.comm.as2.timing.CertificateExpireController;
import de.mendelson.comm.as2.message.MessageAccessDB;
import de.mendelson.comm.as2.partner.Partner;
import de.mendelson.comm.as2.partner.PartnerAccessDB;
import de.mendelson.comm.as2.send.DirPollManager;
import de.mendelson.util.AS2Tools;
import de.mendelson.util.NamedThreadFactory;
import de.mendelson.util.database.IDBDriverManager;
import de.mendelson.util.httpconfig.server.HTTPServerConfigInfo;
import de.mendelson.util.security.cert.CertificateManager;
import de.mendelson.util.security.cert.KeystoreCertificate;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Checks several issues of the configuration
 *
 * @author S.Heller
 * @version $Revision: 33 $
 */
public class ConfigurationCheckController {

    private CertificateManager managerEncSign;
    private CertificateManager managerSSL;
    private ConfigurationCheckThread checkThread;
    private Connection configConnection;
    private Connection runtimeConnection;
    private PreferencesAS2 preferences = new PreferencesAS2();
    private HTTPServerConfigInfo httpServerConfigInfo;
    private DirPollManager pollManager;
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(
        new NamedThreadFactory("configuration-check"));
    private IDBDriverManager dbDriverManager;

    public ConfigurationCheckController(CertificateManager managerEncSign, CertificateManager managerSSL, Connection configConnection,
            Connection runtimeConnection, HTTPServerConfigInfo httpServerConfigInfo, DirPollManager pollManager,
            IDBDriverManager dbDriverManager) {
        this.configConnection = configConnection;
        this.runtimeConnection = runtimeConnection;
        this.managerEncSign = managerEncSign;
        this.managerSSL = managerSSL;
        this.httpServerConfigInfo = httpServerConfigInfo;
        this.pollManager = pollManager;
        this.dbDriverManager = dbDriverManager;
    }

    /**
     * Returns all available issues that are detected by the server control
     */
    public List<ConfigurationIssue> getIssues() {
        return (this.checkThread.getIssues());
    }

    /**
     * Runs the configuration checks once - outside the thread context
     */
    public List<ConfigurationIssue> runOnce() {
        ConfigurationCheckThread testThread = new ConfigurationCheckThread(this.configConnection, this.runtimeConnection);
        testThread.runAllChecks();
        return (testThread.getIssues());
    }

    /**
     * Starts the embedded task that guards the log
     */
    public void start() {
        this.checkThread = new ConfigurationCheckThread(this.configConnection, this.runtimeConnection);
        int initialDelay = 1;
        int delay = 30;
        this.scheduledExecutor.scheduleWithFixedDelay(this.checkThread, initialDelay, delay, TimeUnit.SECONDS);
    }

    public class ConfigurationCheckThread implements Runnable {

        private final List<ConfigurationIssue> issueList = Collections.synchronizedList(new ArrayList<ConfigurationIssue>());
        private Connection configConnection;
        private Connection runtimeConnection;

        public ConfigurationCheckThread(Connection configConnection, Connection runtimeConnection) {
            this.configConnection = configConnection;
            this.runtimeConnection = runtimeConnection;
        }

        @Override
        public void run() {
                synchronized (this.issueList) {
                    this.issueList.clear();
                    this.runAllChecks();
                }
        }

        public List<ConfigurationIssue> getIssues() {
            List<ConfigurationIssue> issues = new ArrayList<ConfigurationIssue>();
            synchronized (this.issueList) {
                issues.addAll(this.issueList);
            }
            return (issues);
        }

        public void runAllChecks() {
            this.checkCertificatesExpired();
            this.checkTLSKeystore();
            this.checkAutoDelete();
            this.checkCPUCores();
            this.checkHeapMemory();
            this.checkOutboundConnectionsAllowed();
            this.checkAllPartnersCertificatesAvailable();
            this.checkDataModel32bit();
            this.checkTLSKeystoreSettings();
            this.checkWindowsServiceWithLocalSystemAccount();
            this.checkDirPollAmount();
        }

        /**
         * It is possible that a keystore has been modified by an external
         * program - or deleted?
         *
         */
        private void checkAllPartnersCertificatesAvailable() {
            PartnerAccessDB partnerAccess = new PartnerAccessDB(dbDriverManager);
            List<Partner> partnerList = partnerAccess.getAllPartner();
            for (Partner partner : partnerList) {
                String cryptFingerprint = partner.getCryptFingerprintSHA1();
                String signFingerprint = partner.getSignFingerprintSHA1();
                KeystoreCertificate certEncrypt = managerEncSign.getKeystoreCertificateByFingerprintSHA1(cryptFingerprint);
                if (certEncrypt == null) {
                    ConfigurationIssue issue = null;
                    if (partner.isLocalStation()) {
                        issue = new ConfigurationIssue(ConfigurationIssue.KEY_MISSING_ENC_LOCAL_STATION);
                    } else {
                        issue = new ConfigurationIssue(ConfigurationIssue.CERTIFICATE_MISSING_ENC_REMOTE_PARTNER);
                    }
                    issue.setDetails(partner.getName());
                    synchronized (this.issueList) {
                        this.issueList.add(issue);
                    }
                }
                KeystoreCertificate certSign = managerEncSign.getKeystoreCertificateByFingerprintSHA1(signFingerprint);
                if (certSign == null) {
                    ConfigurationIssue issue = null;
                    if (partner.isLocalStation()) {
                        issue = new ConfigurationIssue(ConfigurationIssue.KEY_MISSING_SIGN_LOCAL_STATION);
                    } else {
                        issue = new ConfigurationIssue(ConfigurationIssue.CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER);
                    }
                    issue.setDetails(partner.getName());
                    synchronized (this.issueList) {
                        this.issueList.add(issue);
                    }
                }

            }
        }

        private void checkCertificatesExpired() {
            List<KeystoreCertificate> encSignList = managerEncSign.getKeyStoreCertificateList();
            for (KeystoreCertificate cert : encSignList) {
                if (CertificateExpireController.getCertificateExpireDuration(cert) <= 0) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN);
                    issue.setDetails(cert.getAlias());
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    issue.setHintParameter(new Object[]{
                        cert.getAlias(), cert.getIssuerDN(), cert.getFingerPrintSHA1(),
                        dateFormat.format(cert.getNotBefore()),
                        dateFormat.format(cert.getNotAfter())
                    });
                    synchronized (this.issueList) {
                        this.issueList.add(issue);
                    }
                }
            }
            List<KeystoreCertificate> sslList = managerSSL.getKeyStoreCertificateList();
            for (KeystoreCertificate cert : sslList) {
                if (CertificateExpireController.getCertificateExpireDuration(cert) <= 0) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL);
                    issue.setDetails(cert.getAlias());
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    issue.setHintParameter(new Object[]{
                        cert.getAlias(), cert.getIssuerDN(), cert.getFingerPrintSHA1(),
                        dateFormat.format(cert.getNotBefore()),
                        dateFormat.format(cert.getNotAfter())
                    });
                    synchronized (this.issueList) {
                        this.issueList.add(issue);
                    }
                }
            }
        }

        private void checkDataModel32bit() {
            String dataModel = "";
            try {
                dataModel = System.getProperty("sun.arch.data.model");
                int bits = Integer.parseInt(dataModel);
                if (bits == 32) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.JVM_32_BIT);
                    synchronized (issueList) {
                        this.issueList.add(issue);
                    }
                }
            } catch (Throwable e) {
                //ignore this - it does work only on oracle VMs. If the property is not supported it will return "unknown" which could
                //not be parsed as an integer and will result in a NumberFormatException
            }
        }

        private void checkOutboundConnectionsAllowed() {
            int numberOfConnections = preferences.getInt(PreferencesAS2.MAX_OUTBOUND_CONNECTIONS);
            if (numberOfConnections == 0) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.NO_OUTBOUND_CONNECTIONS_ALLOWED);
                issue.setDetails("");
                synchronized (issueList) {
                    this.issueList.add(issue);
                }
            }
        }

        /**
         * Finds out some issues that could occur in the SSL configuration: no
         * SSL key set, multiple SSL keys, use of public test keys as SSL key
         */
        private void checkTLSKeystore() {
            List<KeystoreCertificate> sslList = managerSSL.getKeyStoreCertificateList();
            StringBuilder aliasList = new StringBuilder();
            int keyCount = 0;
            List<KeystoreCertificate> keystoreKeysList = new ArrayList<KeystoreCertificate>();
            for (KeystoreCertificate cert : sslList) {
                if (cert.getIsKeyPair()) {
                    if (aliasList.length() > 0) {
                        aliasList.append(", ");
                    }
                    aliasList.append(cert.getAlias());
                    keystoreKeysList.add(cert);
                    keyCount++;
                }
            }
            if (keyCount == 0) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE);
                synchronized (this.issueList) {
                    this.issueList.add(issue);
                }
            } else if (keyCount > 1) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE);
                issue.setDetails(aliasList.toString());
                synchronized (this.issueList) {
                    this.issueList.add(issue);
                }
            } else {
                KeystoreCertificate usedSSLKey = keystoreKeysList.get(0);
                String foundFingerprint = usedSSLKey.getFingerPrintSHA1();
                for (String testFingerprint : KeystoreCertificate.TEST_KEYS_FINGERPRINTS_SHA1) {
                    if (foundFingerprint.equalsIgnoreCase(testFingerprint)) {
                        ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.USE_OF_TEST_KEYS_IN_SSL);
                        issue.setDetails(usedSSLKey.getAlias());
                        synchronized (this.issueList) {
                            this.issueList.add(issue);
                        }
                    }
                }
            }
        }

        private void checkAutoDelete() {
            if (!preferences.getBoolean(PreferencesAS2.AUTO_MSG_DELETE)) {
                MessageAccessDB messageAccess = new MessageAccessDB(dbDriverManager, configConnection, runtimeConnection);
                int transmissionCount = messageAccess.getMessageCount();
                if (transmissionCount > 30000) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE);
                    issue.setDetails(String.valueOf(transmissionCount));
                    synchronized (this.issueList) {
                        this.issueList.add(issue);
                    }
                }
            }
        }

        private void checkCPUCores() {
            int cores = Runtime.getRuntime().availableProcessors();
            if (cores < 4) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.FEW_CPU_CORES);
                issue.setDetails(String.valueOf(cores));
                synchronized (this.issueList) {
                    this.issueList.add(issue);
                }
            }
        }

        private void checkHeapMemory() {
            long maxMemory = Runtime.getRuntime().maxMemory();
            long oneGB = 1073741824L;
            if (maxMemory < 4 * oneGB) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.LOW_MAX_HEAP_MEMORY);
                issue.setDetails(AS2Tools.getDataSizeDisplay(maxMemory));
                synchronized (this.issueList) {
                    this.issueList.add(issue);
                }
            }
        }

        /**
         * Checks if the underlaying http server uses the same keystore as
         * defined in the settings. This check makes just sense if a TLS receipt
         * handler is defined in the system
         *
         */
        private void checkTLSKeystoreSettings() {
            //its possible to start the system without the http server. In this case there are some values missing
            if (httpServerConfigInfo == null
                    || httpServerConfigInfo.getKeystorePath() == null) {
                return;
            }
            if ((!httpServerConfigInfo.isEmbeddedHTTPServerStarted()) || (!httpServerConfigInfo.isSSLEnabled())) {
                return;
            }
            String httpsKeystorePath = Paths.get(httpServerConfigInfo.getKeystorePath()).normalize().toAbsolutePath().toString();
            String preferencesKeystorePath = Paths.get(preferences.get(PreferencesAS2.KEYSTORE_HTTPS_SEND)).normalize().toAbsolutePath().toString();
            if (!httpsKeystorePath.equals(preferencesKeystorePath)) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.DIFFERENT_KEYSTORES_TLS);
                issue.setHintParameter(
                        new Object[]{
                            httpsKeystorePath,
                            httpServerConfigInfo.getHTTPServerConfigFile().normalize().toAbsolutePath().toString(),
                            preferencesKeystorePath
                        });
                synchronized (this.issueList) {
                    this.issueList.add(issue);
                }
            }
        }

        /**
         * Checks if the system runs as service with a local system account.
         * This is a problem because Windows updates may change the rights of
         * this user and it is possible that formerly written files of this user
         * become read only or unaccessible for this user.
         */
        private void checkWindowsServiceWithLocalSystemAccount() {
            //check if this is a windows service with a local system account as user
            if (System.getenv("iswindowsservice") != null && System.getenv("iswindowsservice").equals("1")) {
                String serverUser = System.getProperty("user.name");
                if (serverUser != null && serverUser.contains("$")) {
                    ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.WINDOWS_SERVICE_LOCAL_SYSTEM_ACCOUNT);
                    issue.setDetails(serverUser);
                    issue.setHintParameter(new Object[]{serverUser});
                    synchronized (this.issueList) {
                        this.issueList.add(issue);
                    }
                }
            }
        }

        /**
         * Checks if the number of dir polls exceeds 5 polls/sec - these are
         * 300polls/min. This might have impact on the file IO
         */
        private void checkDirPollAmount() {
            float pollsPerMin = ConfigurationCheckController.this.pollManager.getPollsPerMinute();
            if (pollsPerMin > 300f) {
                ConfigurationIssue issue = new ConfigurationIssue(ConfigurationIssue.TOO_MANY_DIR_POLLS);
                issue.setDetails(String.format("%.0f", pollsPerMin));
                issue.setHintParameter(new Object[]{String.format("%.0f", pollsPerMin)});
                synchronized (this.issueList) {
                    this.issueList.add(issue);
                }
            }
        }

    }
}
