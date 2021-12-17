//$Header: /as2/de/mendelson/comm/as2/send/ResourceBundleDirPollManager_de.java 20    7.12.18 9:51 Heller $
package de.mendelson.comm.as2.send;
import de.mendelson.util.MecResourceBundle;
/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * ResourceBundle to localize a mendelson product
 * @author S.Heller
 * @version $Revision: 20 $
 */
public class ResourceBundleDirPollManager_de extends MecResourceBundle{
    
    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"none", "Keine" },
        {"manager.status.modified", "Die Verzeichnis�berwachung hat Verzeichnis�berwachungen ver�ndert, es werden {0} Verzeichnisse �berwacht" },
        {"poll.stopped", "Die Verzeichnis�berwachung f�r die Beziehung \"{0}/{1}\" wurde gestoppt." },
        {"poll.started", "Die Verzeichnis�berwachung f�r die Beziehung \"{0}/{1}\" wurde gestartet. Ignoriere: \"{2}\". Intervall: {3}s" },
        {"poll.modified", "[Verzeichnis�berwachung] Die Partnereinstellungen f�r die Beziehung \"{0}/{1}\" wurden ver�ndert." },
        {"warning.noread", "[Verzeichnis�berwachung] Kein Lesezugriff m�glich f�r die Ausgangsdatei {0}, Datei wird ignoriert."},
        {"warning.ro", "[Verzeichnis�berwachung] Die Ausgangsdatei {0} ist schreibgesch�tzt, diese Datei wird ignoriert." },
        {"warning.notcomplete", "[Verzeichnis�berwachung] Die Ausgangsdatei {0} ist noch nicht vollst�ndig vorhanden, Datei wird ignoriert." },
        {"messagefile.deleted", "Die Datei \"{0}\" wurde gel�scht und der Verarbeitungswarteschlange des Servers �bergeben." },
        {"processing.file", "Verarbeite die Datei \"{0}\" f�r die Beziehung \"{1}/{2}\"." }, 
        {"processing.file.error", "Verarbeitungsfehler der Datei \"{0}\" f�r die Beziehung \"{1}/{2}\": \"{3}\"." },
        {"poll.log.wait", "[Verzeichnis�berwachung] {0}->{1}: N�chster Pollprozess in {2}s ({3})" },
        {"poll.log.polling", "[Verzeichnis�berwachung] {0}->{1}: Pr�fe Verzeichnis \"{2}\" auf neue Dateien"},
        {"title.list.polls.running", "Zusammenfassung der �berwachten Verzeichnisse:" },
        {"title.list.polls.stopped", "Die folgenden �berwachungen wurden beendet" },
        {"title.list.polls.started", "Die folgenden �berwachungen wurden gestartet" },
    };
    
}