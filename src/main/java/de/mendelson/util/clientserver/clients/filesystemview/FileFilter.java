//$Header: /as2/de/mendelson/util/clientserver/clients/filesystemview/FileFilter.java 4     15.11.18 10:52 Heller $
package de.mendelson.util.clientserver.clients.filesystemview;

import java.io.Serializable;
import java.nio.file.Path;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */

/**
 * Msg for the client server protocol
 *
 * @author S.Heller
 * @version $Revision: 4 $
 */
public class FileFilter implements Serializable {

    public static final long serialVersionUID = 1L;

    public FileFilter() {
    }

    public boolean displayFile(Path file) {
        return (true);
    }

}
