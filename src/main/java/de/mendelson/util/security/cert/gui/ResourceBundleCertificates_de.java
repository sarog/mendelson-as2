//$Header: /as2/de/mendelson/util/security/cert/gui/ResourceBundleCertificates_de.java 23    11.11.20 17:06 Heller $
package de.mendelson.util.security.cert.gui;

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
 * @author S.Heller
 * @version $Revision: 23 $
 */
public class ResourceBundleCertificates_de extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"display.ca.certs", "CA Zertifikate anzeigen"},
        {"button.delete", "Schl�ssel/Zertifikat l�schen"},
        {"button.delete.all.expired", "Alle abgelaufenen Schl�ssel/Zertifikate l�schen" },
        {"button.edit", "Alias umbenennen"},
        {"button.newkey", "Schl�ssel importieren"},
        {"button.newcertificate", "Zertifikat importieren"},
        {"button.export", "Zertifikat exportieren"},
        {"menu.file", "Datei" },        
        {"menu.file.close", "Beenden" },
        {"menu.import", "Import"},
        {"menu.export", "Export"},
        {"menu.tools", "Erweitert"},
        {"menu.tools.generatekey", "Neuen Schl�ssel generieren (Self signed)"},
        {"menu.tools.generatecsr", "Zertifikat beglaubigen: CSR generieren (an CA)"},
        {"menu.tools.generatecsr.renew", "Zertifikat erneuern: CSR generieren (an CA)"},
        {"menu.tools.importcsr", "Zertifikat beglaubigen: Antwort der CA auf CSR importieren"},
        {"menu.tools.importcsr.renew", "Zertifikat erneuern: Antwort der CA auf CSR importieren"},
        {"label.selectcsrfile", "Bitte w�hlen Sie die Datei zum Speichern des CSR"},
        {"label.cert.import", "Zertifikat importieren (vom Partner)"},
        {"label.cert.export", "Zertifikat exportieren (f�r den Partner)"},
        {"label.key.import.pem", "Eigenen privaten Schl�ssel importieren (von PEM)"},
        {"label.key.import.pkcs12", "Eigenen privaten Schl�ssel importieren (von PKCS#12)"},
        {"label.key.import.jks", "Eigenen privaten Schl�ssel importieren (von JKS, JAVA Keystore Format)"},
        {"label.key.export.pkcs12", "Schl�ssel exportieren (PKCS#12) (nur f�r Backup Zwecke!)"},
        {"label.keystore", "Keystore Datei:"},
        {"title.signencrypt", "Verf�gbare Schl�ssel und Zertifikate (Verschl�sselung, Signaturen)"},
        {"title.ssl", "Verf�gbare Schl�ssel und Zertifikate (SSL/TLS)"},
        {"button.ok", "Ok"},
        {"button.cancel", "Abbrechen"},
        {"filechooser.certificate.import", "Bitte w�hlen Sie die Zertifikatdatei f�r den Import"},
        {"certificate.import.success.message", "Das Zertifikat wurde erfolgreich mit dem Alias \"{0}\" importiert."},
        {"certificate.ca.import.success.message", "Das CA Zertifikat wurde erfolgreich mit dem Alias \"{0}\" importiert."},
        {"certificate.import.success.title", "Erfolg"},
        {"certificate.import.error.message", "Es gab einen Fehler w�hrend des Imports:\n{0}"},
        {"certificate.import.error.title", "Fehler"},
        {"certificate.import.alias", "Alias f�r dieses Zertifikat:"},
        {"keystore.readonly.message", "Die zugrundeliegende Keystore Datei ist schreibgesch�tzt.\nEine Schreiboperation (Speichern/Ver�ndern) kann nicht durchgef�hrt werden."},
        {"keystore.readonly.title", "Keystore schreibgesch�tzt - Bearbeiten nicht m�glich"},
        {"modifications.notalllowed.message", "Modifikationen sind nicht m�glich"},
        {"generatekey.error.message", "{0}"},
        {"generatekey.error.title", "Fehler bei der Schl�sselerstellung"},
        {"tab.info.basic", "Details"},
        {"tab.info.extension", "Erweiterungen"},
        {"tab.info.trustchain", "Zertifizierungspfad" },        
        {"dialog.cert.delete.message", "Wollen Sie wirklich das Zertifikat mit dem Alias \"{0}\" l�schen?"},
        {"dialog.cert.delete.title", "Zertifikat l�schen"},
        {"title.cert.in.use", "Zertifikat wird verwendet" },
        {"cert.delete.impossible", "Der Eintrag kann nicht gel�scht werden:" },
        {"module.locked", "Diese Zertifikatverwaltung wird aktuell exklusiv von einem anderen Client ge�ffnet, Sie k�nnen keine �nderungen vornehmen!" },
        {"label.trustanchor", "Trust anchor:" },
        {"warning.testkey", "�ffentlich verf�gbarer mendelson Testschl�ssel - nicht im produktiven Betrieb verwenden!" },
        {"label.key.valid", "Dieser Schl�ssel ist g�ltig" },
        {"label.key.invalid", "Dieser Schl�ssel ist ung�ltig" },
        {"label.cert.valid", "Dieses Zertifikat ist g�ltig" },
        {"label.cert.invalid", "Dieses Zertifikat ist ung�ltig" },
        {"warning.deleteallexpired.text", "Wollen Sie wirlich {0} abgelaufene und unbenutzte Eintr�ge l�schen?" },
        {"warning.deleteallexpired.title", "Abgelaufene und unbenutzte Schl�ssel/Zertifkate l�schen" },
        {"warning.deleteallexpired.noneavailable.title", "Keine verf�gbar" },
        {"warning.deleteallexpired.noneavailable.text", "Es gibt keine abgelaufenen, unbenutzen Eintr�ge" },
        {"success.deleteallexpired.title", "Abgelaufene und unbenutzte Zertifikate/Schl�ssel l�schen" },
        {"success.deleteallexpired.text", "{0} abgelaufene und unbenutzte Schl�ssel/Zertifikate wurden gel�scht" },
        {"warning.deleteallexpired.expired.but.used.title", "Benutzte Schl�ssel/Zertifikate" },
        {"warning.deleteallexpired.expired.but.used.text", "{0} abgelaufene Schl�ssel/Zertifikate werden in der Konfiguration verwendet und daher nicht gel�scht " },
    };
}
