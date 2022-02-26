//$Header: /as2/de/mendelson/comm/as2/preferences/ResourceBundlePreferences.java 54    19.11.21 10:35 Heller $
package de.mendelson.comm.as2.preferences;

import de.mendelson.util.MecResourceBundle;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize gui entries
 *
 * @author S.Heller
 * @version $Revision: 54 $
 */
public class ResourceBundlePreferences extends MecResourceBundle {

    public static final long serialVersionUID = 1L;

    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**
     * List of messages in the specific language
     */
    static final Object[][] CONTENTS = {
        //preferences localized
        {PreferencesAS2.DIR_MSG, "Message storage"},
        {"button.ok", "Ok"},
        {"button.cancel", "Cancel"},
        {"button.modify", "Modify"},
        {"button.browse", "Browse"},
        {"filechooser.selectdir", "Select a directory to set"},
        {"title", "Preferences"},
        {"tab.language", "Language"},
        {"tab.dir", "Directories"},
        {"tab.security", "Security"},
        {"tab.proxy", "Proxy"},
        {"tab.misc", "Misc"},
        {"tab.maintenance", "Maintenance"},
        {"tab.notification", "Notification"},
        {"tab.interface", "Modules"},
        {"tab.log", "Log"},
        {"header.dirname", "Type"},
        {"header.dirvalue", "Dir"},
        {"label.language", "Language"},
        {"label.country", "Country/Region"},
        {"label.keystore.https.pass", "Keystore password (https send):"},
        {"label.keystore.pass", "Keystore password (encryption/signature):"},
        {"label.keystore.https", "Keystore (https send):"},
        {"label.keystore.encryptionsign", "Keystore (enc, sign):"},
        {"label.proxy.url", "Proxy URL:"},
        {"label.proxy.url.hint", "Proxy ip or domain"},
        {"label.proxy.port.hint", "Port"},
        {"label.proxy.user", "User:"},
        {"label.proxy.user.hint", "Proxy login user"},
        {"label.proxy.pass", "Password:"},
        {"label.proxy.pass.hint", "Proxy login password"},
        {"label.proxy.use", "Use a HTTP proxy for outgoing HTTP/HTTPs connections"},
        {"label.proxy.useauthentification", "Use proxy authentification"},
        {"filechooser.keystore", "Please select the keystore file (jks format)."},
        {"label.days", "days"},
        {"label.deletemsgolderthan", "Auto delete messages older than"},
        {"label.deletemsglog", "Inform in log and fire system event about auto deleted messages"},
        {"label.deletestatsolderthan", "Auto delete statistic data older than"},
        {"label.deletelogdirolderthan", "Auto delete log data older than"},
        {"label.asyncmdn.timeout", "Max waiting time for async MDN:"},
        {"label.httpsend.timeout", "HTTP(s) send timeout:"},
        {"label.min", "min"},
        {"receipt.subdir", "Create subdirectory for receipt messages per partner"},
        //notification
        {"checkbox.notifycertexpire", "Notify certificate expire"},
        {"checkbox.notifytransactionerror", "Notify transaction errors"},
        {"checkbox.notifycem", "Notify certificate exchange (CEM) events"},
        {"checkbox.notifyfailure", "Notify system problems"},
        {"checkbox.notifyresend", "Notify rejected resends"},
        {"checkbox.notifyconnectionproblem", "Notify connection problems"},
        {"checkbox.notifypostprocessing", "Notify postprocessing problems"},
        {"button.testmail", "Send test mail"},
        {"label.mailhost", "Mail server host (SMTP):"},
        {"label.mailhost.hint", "IP or domain of server"},
        {"label.mailport", "Port:"},
        {"label.mailport.hint", "SMTP port"},
        {"label.mailaccount", "Mail server account:"},
        {"label.mailpass", "Mail server password:"},
        {"label.notificationmail", "Notification receiver address:"},
        {"label.replyto", "Replyto address:"},
        {"label.smtpauthentication", "Use SMTP authentication"},
        {"label.smtpauthentication.user", "User:"},
        {"label.smtpauthentication.user.hint", "SMTP servers user name"},
        {"label.smtpauthentication.pass", "Password:"},
        {"label.smtpauthentication.pass.hint", "SMTP servers password"},
        {"label.security", "Connection security:"},
        {"testmail.message.success", "Test mail sent successfully."},
        {"testmail.message.error", "Error sending test mail:\n{0}"},
        {"testmail.title", "Test mail send result"},
        {"testmail", "Test mail"},
        //interface
        {"label.showhttpheader", "Allow to configure the HTTP headers in the partner configuration"},
        {"label.showquota", "Allow to configure quota notification in the partner configuration"},
        {"label.cem", "Allow certificate exchange (CEM)"},
        {"label.outboundstatusfiles", "Write outbound transaction status files"},
        {"info.restart.client", "A client restart is required to make these changes valid!"},
        {"remotedir.select", "Select a directory on the server"},
        //retry
        {"label.retry.max", "Max number of connection retries"},
        {"label.retry.waittime", "Wait time between connection retries"},
        {"label.sec", "seconds"},
        {"keystore.hint", "<HTML><strong>Warning:</strong><br>Do not modify these parameter unless you used a third party tool "
            + "to modify your keystore passwords (which is not recommended). Setting up passwords here will not modify the "
            + "underlaying keystore passwords - these options will just allow to access external keystores. Modified passwords could result in "
            + "problems during an update. </HTML>"},
        {"maintenancemultiplier.day", "day(s)"},
        {"maintenancemultiplier.hour", "hour(s)"},
        {"maintenancemultiplier.minute", "minute(s)"},
        {"label.logpollprocess", "Log poll process (Huge amount of entries - do not use in production)"},
        {"label.max.outboundconnections", "Max outbound parallel connections"},
        {"event.preferences.modified.subject", "The server settings entry {0} has been modified"},
        {"event.preferences.modified.body", "Old value: {0}\nNew value: {1}"},
        {"event.notificationdata.modified.subject", "The notification data has been modified"},
        {"event.notificationdata.modified.body", "The notification data has been modified from \n\n{0}\n\nto\n\n{1}"},
        {"label.maxmailspermin", "Max number of notifications/min:"},
        {"systemmaintenance.hint.deleteoldtransactions", "<HTML>This sets up the time range the transactions and related data (also temp files) will remain in the system and should be displayed in the transaction overview.<br>These settings will <strong>not</strong> touch your received data/files.<br>Even for deleted transactions the transaction log is still available via the \"log search\" functionality.</HTML>"},
        {"systemmaintenance.hint.deleteoldstatistic", "<HTML>The system collects compatibility data from the partner systems and can display this as statistics. This determines the time frame in which this data is kept.</HTML>" },
        {"systemmaintenance.hint.deleteoldlogdirs", "<HTML>Even if old transactions have been deleted, the transaction logs can still be traced via existing log files. This setting deletes these log files and also all files for system events that fall within the same time period.</HTML>" },
        {"label.colorblindness", "Enable support for color blindness"},
        {"warning.clientrestart.required", "Client settings have been changed - please restart the client to make them valid"},};
}
