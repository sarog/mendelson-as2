//$Header: /oftp2/de/mendelson/util/NamedThreadFactory.java 1     26/01/22 16:55 Heller $
package de.mendelson.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Thread factory that allows to name created threads - useful to verify the program running state using jconsole
 *
 * @author S.Heller
 * @version $Revision: 1 $
 */
public class NamedThreadFactory implements ThreadFactory{
    private final AtomicInteger threadsNum = new AtomicInteger();
    private final String namePattern;

    /**
     * Create a names thread factory that creates threads of the format suffix-n
     * @param suffix Suffix for the created threads
     */
    public NamedThreadFactory(String suffix){
        namePattern = suffix + "-%d";
    }

    @Override
    public Thread newThread(Runnable runnable){
        return new Thread(runnable, String.format(namePattern, threadsNum.addAndGet(1)));
    }    
}
