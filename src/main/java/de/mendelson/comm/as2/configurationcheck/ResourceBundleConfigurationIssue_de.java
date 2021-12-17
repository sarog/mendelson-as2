//$Header: /as2/de/mendelson/comm/as2/configurationcheck/ResourceBundleConfigurationIssue_de.java 23    8.12.20 11:42 Heller $
package de.mendelson.comm.as2.configurationcheck;

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
 * @version $Revision: 23 $
 */
public class ResourceBundleConfigurationIssue_de extends MecResourceBundle {

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
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN), "Zertifikat ist abgelaufen (enc/sign)"},
        {"hint." + String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_ENC_SIGN),
            "<HTML>Zertifikate haben nur eine begrenzte Laufzeit. In der Regel sind das ein, drei oder f�nf Jahre.<br>"
            + "Ein Zertifikat, was Sie in Ihrem System f�r einen Partner zum Verschl�sseln/Entschl�sseln von Daten, zum digitalen Signieren oder zum Pr�fen einer digitalen Signatur verwenden, ist nicht mehr g�ltig.<br>"
            + "Es ist nicht m�glich, mit einem abgelaufenen Zertifikat kryptographischer Operationen auszuf�hren - "
            + "daher sollten Sie sich bitte darum k�mmern, das Zertifikat zu erneuern oder ein neues Zertifikat erstellen "
            + "bzw beglaubigen lassen.<br><br>"
            + "<strong>Zusatzinformationen zum Zertifikat:</strong><br><br>"
            + "Alias: {0}<br>"
            + "Issuer: {1}<br>"
            + "Fingerprint (SHA-1): {2}<br>"
            + "G�ltig von: {3}<br>"
            + "G�ltig bis: {4}<br>"
            + "<br></HTML>"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL), "Zertifikat ist abgelaufen (SSL)"},
        {"hint." + String.valueOf(ConfigurationIssue.CERTIFICATE_EXPIRED_SSL),
            "<HTML>Zertifikate haben nur eine begrenzte Laufzeit. In der Regel sind das ein, drei oder f�nf Jahre.<br>"
            + "Ein Zertifikat, was Sie in Ihrem System f�r die SSL/TLS Leitungssicherung verwenden, ist nicht mehr g�ltig.<br>"
            + "Es ist nicht m�glich, mit einem abgelaufenen Zertifikat kryptographischer Operationen auszuf�hren - "
            + "daher sollten Sie sich bitte darum k�mmern, das Zertifikat zu erneuern oder ein neues Zertifikat erstellen "
            + "bzw beglaubigen lassen.<br><br>"
            + "<strong>Zusatzinformationen zum Zertifikat:</strong><br><br>"
            + "Alias: {0}<br>"
            + "Issuer: {1}<br>"
            + "Fingerprint (SHA-1): {2}<br>"
            + "G�ltig von: {3}<br>"
            + "G�ltig bis: {4}<br>"
            + "<br></HTML>"},
        {String.valueOf(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE), "Mehrere Schl�ssel im SSL Keystore gefunden - darf nur einer sein"},
        {"hint." + String.valueOf(ConfigurationIssue.MULTIPLE_KEYS_IN_SSL_KEYSTORE),
            "<HTML>In dem SSL/TLS Keystore Ihres Systems befinden sich mehrere Schl�ssel. Es darf sich allerdings nur einer darin befinden - dieser wird beim Start des Servers als SSL/TLS Schl�ssel verwendet.<br>"
            + "Bitte l�schen Sie so lange Sch�ssel aus dem SSL/TLS Keystore, bis nur noch ein Schl�ssel darin vorhanden ist.<br>"
            + "Sie erkennen die Schl�ssel in der Zertifikatverwaltung am Schl�sselsymbol in der ersten Spalte.<br>"
            + "Nach dieser �nderung ist es notwendig, den Server neu zu starten.</HTML>"},
        {String.valueOf(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE), "Kein Schl�ssel im SSL Keystore gefunden"},
        {"hint." + String.valueOf(ConfigurationIssue.NO_KEY_IN_SSL_KEYSTORE),
            "<HTML>Es wurde kein Schl�ssel im SSL/TLS Keystore Ihres Systems gefunden.<br>"
            + "Sie erkennen Schl�ssel am vorangestellten Schl�sselsymbol, wenn Sie die Zertifikatverwaltung �ffnen.<br>"
            + "Es wird genau ein Schl�ssel im SSL/TLS Keystore ben�tigt, um den Handshakeprozess der Leitungssicherung durchzuf�hren.<br>"
            + "Ohne diesen Schl�ssel kommen Sie also weder ein- noch ausgehend zu gesicherten Verbindungen.</HTML>"},
        {String.valueOf(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE), "Aktivieren Sie automatisches L�schen - Im System ist eine grosse Menge von Transaktionen"},
        {"hint." + String.valueOf(ConfigurationIssue.HUGE_AMOUNT_OF_TRANSACTIONS_NO_AUTO_DELETE),
            "<HTML>In den Einstellungen k�nnen Sie definieren, wie lange Transaktionen im System verbleiben sollen.<br>"
            + "Je mehr Transaktionen im System verbleiben, desto mehr Resourcen werden f�r die Verwaltung ben�tigt.<br>"
            + "Daher sollten Sie mit Hilfe der Einstellungen daf�r sorgen, dass Sie nie mehr als maximal 30000 Transaktionen im System haben.<br>"
            + "Bitte beachten Sie, dass dies kein Archivsystem ist, sondern ein Kommunikationsadapter.<br>"
            + "Sie haben �ber die integrierte Suchfunktion des Serverlogs Zugriff auf alle Transaktionslogs der Vergangenheit.</HTML>"},
        {String.valueOf(ConfigurationIssue.FEW_CPU_CORES), "Weisen Sie dem System mindestens 4 Prozessorkerne zu"},
        {"hint." + String.valueOf(ConfigurationIssue.FEW_CPU_CORES),
            "<HTML>F�r besseren Durchsatz ist es notwendig, dass unterschiedliche Aufgaben im System parallel durchgef�hrt werden.<br>"
            + "Daher ist es notwendig, eine entsprechende Anzahl von CPU Kernen f�r den Prozess zu reservieren.</HTML>"},
        {String.valueOf(ConfigurationIssue.LOW_MAX_HEAP_MEMORY), "Reservieren Sie mindestens 4GB Hauptspeicher f�r den Serverprozess"},
        {"hint." + String.valueOf(ConfigurationIssue.LOW_MAX_HEAP_MEMORY),
            "<HTML>Dieses Programm ist in Java geschrieben.<br>"
            + "Unabh�ngig von der physikalischen Ausstattung Ihres Rechners m�ssen Sie dem Serverprozess eine entsprechende Menge an Speicher reservieren. In Ihrem Fall haben Sie zu wenig Speicher reserviert.<br>"
            + "Bitte schauen Sie in die Hilfe (Abschnitt Installation) - dort steht, wie Sie f�r welche Startmethode den entsprechenden Speicher reservieren.</HTML>"},
        {String.valueOf(ConfigurationIssue.NO_OUTBOUND_CONNECTIONS_ALLOWED), "Menge ausgehender Verbindungen ist auf 0 gesetzt - das System wird NICHT senden"},
        {"hint." + String.valueOf(ConfigurationIssue.NO_OUTBOUND_CONNECTIONS_ALLOWED),
            "<HTML>Sie haben Konfigurations�nderungen vorgenommen, sodass aktuell keine ausgehenden Verbindungen m�glich sind.<br>"
            + "Wenn Sie ausgehende Verbindungen zu Partnern aufnehmen m�chten, m�sste die Anzahl der m�glichen Verbindungen mindestens auf den Wert 1 gesetzt werden.</HTML>"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_MISSING_ENC_REMOTE_PARTNER), "Fehlendes Verschl�sselungszertifikat eines entfernten Partners"},
        {"hint." + String.valueOf(ConfigurationIssue.CERTIFICATE_MISSING_ENC_REMOTE_PARTNER),
            "<HTML>Ein Verbindungpartner hat in Ihrer Konfiguration kein Verschl�sselungszertifikat zugewiesen.<br>"
            + "Sie k�nnen in diesem Fall keine Nachrichten an ihn verschl�sseln. Bitte �ffnen Sie die Partnerverwaltung und weisen Sie dem Partner ein Verschl�sselungszertifikat zu.</HTML>"},
        {String.valueOf(ConfigurationIssue.CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER), "Fehlendes Signaturzertifikat eines entfernten Partners"},
        {"hint." + String.valueOf(ConfigurationIssue.CERTIFICATE_MISSING_SIGN_REMOTE_PARTNER),
            "<HTML>Ein Verbindungpartner hat in Ihrer Konfiguration kein Signaturzertifikat zugewiesen.<br>"
            + "Sie k�nnen in diesem Fall keine digitalen Signaturen Ihres Partners verifizieren. Bitte �ffnen Sie die Partnerverwaltung und weisen Sie dem Partner ein Signaturzertifikat zu.</HTML>"},
        {String.valueOf(ConfigurationIssue.KEY_MISSING_ENC_LOCAL_STATION), "Fehlender Verschl�sselungsschl�ssel einer lokalen Station"},
        {"hint." + String.valueOf(ConfigurationIssue.KEY_MISSING_ENC_LOCAL_STATION),
            "<HTML>Ihre lokale Station hat keinen Verschl�sselungsschl�ssel zugewiesen.<br>"
            + "Sie k�nnen in dieser Konfiguration keine eingehenden Nachrichten entschl�sseln - egal von welchem Partner.<br>"
            + "Bitte �ffnen Sie die Partnerverwaltung und weisen Sie der lokalen Station einen privaten Schl�ssel zu.</HTML>"},
        {String.valueOf(ConfigurationIssue.KEY_MISSING_SIGN_LOCAL_STATION), "Fehlender Signaturschl�ssel einer lokalen Station"},
        {"hint." + String.valueOf(ConfigurationIssue.KEY_MISSING_SIGN_LOCAL_STATION),
            "<HTML>Ihre lokale Station hat keinen Signaturschl�ssel zugewiesen.<br>"
            + "Sie k�nnen in dieser Konfiguration keine ausgehenden Nachrichten digital signieren - egal f�r welchem Partner.<br>"
            + "Bitte �ffnen Sie die Partnerverwaltung und weisen Sie der lokalen Station einen privaten Schl�ssel zu.</HTML>"},
        {String.valueOf(ConfigurationIssue.USE_OF_TEST_KEYS_IN_SSL), "Verwendung eines �ffentlich verf�gbaren Testschl�ssels als SSL Schl�ssel"},
        {"hint." + String.valueOf(ConfigurationIssue.USE_OF_TEST_KEYS_IN_SSL),
            "<HTML>In der Auslieferung stellt mendelson einige Testschl�ssel zur Verf�gung.<br>"
            + "Diese sind auf der mendelson Webseite �ffentlich verf�gbar.<br>"
            + "Wenn Sie diese Schl�ssel f�r kryptographische Aufgaben innerhalb Ihres Datentransfers produktiv verwenden, bieten sie daher <strong>KEINE</strong> Sicherheit.<br>"
            + "Hier k�nnen Sie dann auch gleich ungesichert und unverschl�sselt senden.<br>"
            + "Wenn Sie einen beglaubigten Schl�ssel ben�tigen, wenden Sie sich bitte an den mendelson Support.</HTML>"},
        {String.valueOf(ConfigurationIssue.JVM_32_BIT), "Die Verwendung einer 32 Bit Java VM wird nicht f�r den produktiven Einsatz empfohlen, da dann der maximale Heap-Speicher auf 1,3GB begrenzt ist."},
        {"hint." + String.valueOf(ConfigurationIssue.JVM_32_BIT),
            "<HTML>Java 32bit Prozesse k�nnen nicht genug Speicher reservieren, um das System im Produktivbetrieb stabil zu halten. Bitte verwenden Sie eine 64bit JVM.</HTML>"},
        {String.valueOf(ConfigurationIssue.DIFFERENT_KEYSTORES_TLS), "TLS Sende- und Empfangskeystore sind unterschiedlich"},
        {"hint." + String.valueOf(ConfigurationIssue.DIFFERENT_KEYSTORES_TLS),
            "<HTML>Im unterliegenden HTTP Server haben Sie den Keystore \"<strong>{0}</strong>\" f�r TLS definiert (in der Konfigurationsdatei \"<strong>{1}</strong>\").<br><br>"
            + "F�r HTTPS Sendezwecke haben Sie den Keystore \"<strong>{2}</strong>\" in den Systemeinstellungen definiert.<br><br>"
            + "Das funktioniert gut. Sie k�nnen jedoch nicht beide Keystores �ber die Benutzeroberfl�che verwalten, da die TLS Zertifikatverwaltung nur einen Keystore bearbeiten kann.</HTML>"},
        {String.valueOf(ConfigurationIssue.WINDOWS_SERVICE_LOCAL_SYSTEM_ACCOUNT), "Windows Service mit lokalem Systemkonto gestartet"},
        {"hint." + String.valueOf(ConfigurationIssue.WINDOWS_SERVICE_LOCAL_SYSTEM_ACCOUNT),
            "<HTML>Sie haben den mendelson AS2 Server als Windows Service eingerichtet und starten ihn �ber ein lokales Systemkonto (\"{0}\").<br>"
            + "Leider ist es m�glich, dass dieser Benutzer nach einem Windows Update die Rechte an seinen vormals geschriebenen Dateien verliert, "
            + "das kann zu vielf�ltigen Systemproblemen f�hren.<br><br>"
            + "Bitte richten Sie einen eigenen Benutzer f�r den Service ein und starten den Service mit diesem Benutzer.</HTML>"},
        {String.valueOf(ConfigurationIssue.TOO_MANY_DIR_POLLS), "Gro�e Menge von Verzeichnis�berwachungen pro Zeiteinheit"},
        {"hint." + String.valueOf(ConfigurationIssue.TOO_MANY_DIR_POLLS),
            "<HTML>Sie haben in Ihrem System eine gro�e Menge von Partnerbeziehnungen definiert und �berwachen die entsprechenden "
            + "Ausgangsverzeichnisse in zu kurzen Zeitintervallen.<br>Aktuell werden pro Minute {0} Verzeichnis�berwachungen aktiviert.<br>"
            + "Bitte reduzieren Sie diesen Wert, indem Sie die �berwachungsintervalle der jeweiligen Partnerverzeichnisse vergr��ern und"
            + " auch �berwachungen f�r Partner deaktivieren, wo dies nicht ben�tigt wird."
            + "Bei einer gro�en Anzahl von Partnern wird empfohlen, alle Verzeichnis�berwachungen deaktivieren und die Sendeauftr�ge von "
            + "Ihrem Backend aus mit den Befehlen <i>AS2Send.exe</i> oder <i>as2send.sh</i> nach Bedarf zu erstellen.</HTML>"}
    };
}
