//$Header: /as4/de/mendelson/util/log/JTextPaneLoggingHandler.java 28    16.10.19 12:34 Heller $
package de.mendelson.util.log;

import de.mendelson.util.ColorUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ErrorManager;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Handler to log logger data to a swing text component
 *
 * @author S.Heller
 * @version $Revision: 28 $
 */
public class JTextPaneLoggingHandler extends Handler {

    /**
     * The max number of bytes that are displayed. If the content exceeds this
     * there is data removed at the start
     */
    private final long MAX_BUFFER_SIZE = 30000;
    private boolean doneHeader;
    private JTextPane jTextPane = null;
    private Style currentStyle;
    private boolean bold = false;
    private boolean underline = false;
    private boolean italic = false;
    // Line separator string.  This is the value of the line.separator
    // property at the moment that the Formatter was created.
    private String lineSeparator = System.getProperty("line.separator");
    //allows to enable/disable the logging output
    private boolean enabled = true;
    private Color defaultForegroundColor = UIManager.getColor("TextPane.foreground");

    /**
     * Stores the logging colors for the logging levels
     */
    private final Map<Level, String> colorMap = Collections.synchronizedMap(new HashMap<Level, String>());

    public JTextPaneLoggingHandler(JTextPane jTextPane, Formatter formatter) {
        //set default colors, these could be overwritten using the setColor method
        synchronized (this.colorMap) {
            this.colorMap.put(Level.WARNING, IRCColors.BLUE);
            this.colorMap.put(Level.SEVERE, IRCColors.RED);
            this.colorMap.put(Level.FINE, IRCColors.DARK_GREEN);
        }
        this.setFormatter(formatter);
        this.jTextPane = jTextPane;
        StyleContext context = StyleContext.getDefaultStyleContext();
        this.currentStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
        if (this.defaultForegroundColor == null) {
            this.defaultForegroundColor = this.getBestContrastColorAsIndexedColor(
                    IRCColors.BLACK, jTextPane.getBackground());
        }
        this.resetStyle();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets a color for the log levels. The color is a constant of the class
     * IRCColor
     *
     */
    public void setColor(Level loglevel, String color) {
        synchronized (this.colorMap) {
            this.colorMap.put(loglevel, color);
        }
    }

    /**
     * Returns the current set color for the passed log level. May return null
     * if no color is defined for the passed level
     */
    public String getColor(Level loglevel) {
        synchronized (this.colorMap) {
            if (colorMap.containsKey(loglevel)) {
                return (this.colorMap.get(loglevel));
            }else{
                return( IRCColors.toColorStr(this.getBestContrastColorAsIndexedColor(IRCColors.BLACK, this.jTextPane.getBackground())));
            }
        }
    }

    /**
     * Controls the contrast of the used colors and tries to adjust them for
     * higher contrast
     */
    public void adjustColorsByContrast() {
        Color backgroundColor = this.jTextPane.getBackground();
        Level[] levelList = new Level[]{
            Level.SEVERE, Level.WARNING, Level.INFO, Level.CONFIG,
            Level.FINE, Level.FINER, Level.FINEST};
        for (Level level : levelList) {
            this.setColor(level,
                    IRCColors.toColorStr(
                            this.getBestContrastColorAsIndexedColor(this.getColor(level),
                                    backgroundColor)));
        }
    }

    /**
     * As there are just 16 indexed colors in the log this will test some
     * alternative colors if the contrast does not match
     * It will always return an indexed color as defined in the class IRCColors.
     *
     * @param ircColorForeground an index color
     * @param anyColorBackground
     * @return
     */
    private Color getBestContrastColorAsIndexedColor(String ircColorForeground, Color anyColorBackground) {
        Color colorForeground = IRCColors.toColor(ircColorForeground);
        if (ColorUtil.contrastIsOk(anyColorBackground, colorForeground)) {
            return (colorForeground);
        }
        if (ircColorForeground.equals(IRCColors.BLACK)
                || ircColorForeground.equals(IRCColors.DARK_GRAY)
                || ircColorForeground.equals(IRCColors.LIGHT_GRAY)
                || ircColorForeground.equals(IRCColors.WHITE)) {
            return (ColorUtil.getBestContrastColor(anyColorBackground,
                    Arrays.asList(new Color[]{
                IRCColors.toColor(IRCColors.DARK_GRAY),
                IRCColors.toColor(IRCColors.LIGHT_GRAY),
                IRCColors.toColor(IRCColors.WHITE)
            })));
        }
        if (ircColorForeground.equals(IRCColors.GREEN)
                || ircColorForeground.equals(IRCColors.DARK_GREEN)) {
            return (ColorUtil.getBestContrastColor(anyColorBackground,
                    Arrays.asList(new Color[]{
                IRCColors.toColor(IRCColors.GREEN),
                IRCColors.toColor(IRCColors.DARK_GREEN)
            })));
        }
        if (ircColorForeground.equals(IRCColors.RED)
                || ircColorForeground.equals(IRCColors.BROWN)
                || ircColorForeground.equals(IRCColors.OLIVE)) {
            return (ColorUtil.getBestContrastColor(anyColorBackground,
                    Arrays.asList(new Color[]{
                IRCColors.toColor(IRCColors.RED),
                IRCColors.toColor(IRCColors.BROWN),
                IRCColors.toColor(IRCColors.OLIVE),})));
        }
        if (ircColorForeground.equals(IRCColors.BLUE)
                || ircColorForeground.equals(IRCColors.DARK_BLUE)
                || ircColorForeground.equals(IRCColors.CYAN)
                || ircColorForeground.equals(IRCColors.TEAL)) {
            return (ColorUtil.getBestContrastColor(anyColorBackground,
                    Arrays.asList(new Color[]{
                IRCColors.toColor(IRCColors.BLUE),
                IRCColors.toColor(IRCColors.DARK_BLUE),
                IRCColors.toColor(IRCColors.CYAN),
                IRCColors.toColor(IRCColors.TEAL),})));
        }
        return (colorForeground);
    }

    /**
     * Appends a message to the output area. IRC color codes will be decoded
     * first.
     */
    public void messageDecode(String message) {
        // quick checks to speed things up
        if ((message.indexOf('\002') >= 0)
                || (message.indexOf('\003') >= 0)
                || (message.indexOf('\026') >= 0)
                || (message.indexOf(0x0f) >= 0)
                || (message.indexOf('\037') >= 0)) {
            StringBuilder buf = new StringBuilder();
            int len = message.length();
            int i;
            char c;

            for (i = 0; i < len; i++) {
                c = message.charAt(i);
                switch (c) {
                    case '\002':   // bold
                        this.messageDecodeWrite(buf);
                        this.toggleStyleBold();
                        break;
                    case '\003': // colors
                    {
                        char c1;
                        char c2;
                        if (i < (len - 1)) {
                            c1 = message.charAt(i + 1);
                            if ((c1 >= '0') && (c1 <= '9')) {
                                if (i < (len - 2)) {
                                    c2 = message.charAt(i + 2);

                                    if ((c2 >= '0') && (c2 <= '9')) {
                                        this.messageDecodeWrite(buf);
                                        this.setStyleForeground((c1 - '0') * 10 + c2 - '0');
                                        i += 2;
                                    }
                                } else {
                                    this.messageDecodeWrite(buf);
                                    this.setStyleForeground(c1 - '0');
                                    i++;
                                }
                            }
                        }
                    }
                    break;
                    case '\026':   // italic
                        this.messageDecodeWrite(buf);
                        toggleStyleItalic();
                        break;
                    case '\037':   // underline
                        this.messageDecodeWrite(buf);
                        toggleStyleUnderline();
                        break;
                    case 0x0f: //reset all styles
                        this.messageDecodeWrite(buf);
                        this.resetStyle();
                        break;
                    default:
                        buf.append(c);
                        break;
                }
            }
            this.messageDecodeWrite(buf);
        } else {
            this.resetStyle();
            messageDecodeWrite(new StringBuilder(message));
        }
    }

    /**
     * Appends the current buffer's contents to the output area while decoding a
     * message. The StringBuffer will be setLength(0) afterwards.
     */
    private void messageDecodeWrite(StringBuilder buffer) {
        final StyledDocument document = (StyledDocument) this.jTextPane.getDocument();
        synchronized (document) {
            try {
                long documentLength = document.getLength();
                long oversize = (documentLength + buffer.length()) - this.MAX_BUFFER_SIZE;
                if (oversize > 0) {
                    if (documentLength >= oversize) {
                        document.remove(0, (int) oversize);
                    } else {
                        document.remove(0, (int) documentLength);
                        if (buffer.length() > this.MAX_BUFFER_SIZE) {
                            buffer.delete(0, (int) (buffer.length() - this.MAX_BUFFER_SIZE));
                        }
                    }
                }
                document.insertString(document.getLength(), buffer.toString(), this.currentStyle);

            } catch (Throwable ex) {
                if (ex instanceof Exception) {
                    reportError(null, (Exception) ex, ErrorManager.WRITE_FAILURE);
                }
            }
        }
        buffer.setLength(0);
        //scroll to the last line, enqueue into the swing paint queue
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (document) {
                        int documentLength = document.getLength();
                        if (documentLength <= MAX_BUFFER_SIZE) {
                            JTextPaneLoggingHandler.this.jTextPane.setCaretPosition(documentLength);
                        }
                    }
                } catch (Error e) {
                    //ignore
                }
            }
        });
    }

    /**
     * Reset all style attributes to plain text.
     */
    private void resetStyle() {
        this.setStyleBold(false);
        this.setStyleItalic(false);
        this.setStyleUnderline(false);
        this.setStyleForeground(1);
        this.setStyleForeground(this.getDefaultForegroundColor());
    }

    /**
     * Enable or disable boldface mode for subsequent messages.
     */
    public void setStyleBold(boolean bold) {
        synchronized (this.currentStyle) {
            this.currentStyle.removeAttribute(StyleConstants.Bold);
            if (bold) {
                this.currentStyle.addAttribute(StyleConstants.Bold, Boolean.TRUE);
            }
            this.bold = bold;
        }
    }

    /**
     * Enable or disable italic mode for subsequent messages.
     */
    public void setStyleItalic(boolean italic) {
        synchronized (this.currentStyle) {
            this.currentStyle.removeAttribute(StyleConstants.Italic);
            if (italic) {
                this.currentStyle.addAttribute(StyleConstants.Italic, Boolean.TRUE);
            }
            this.italic = italic;
        }
    }

    /**
     * Toggle boldface mode for subsequent messages.
     */
    private void toggleStyleBold() {
        this.setStyleBold(!bold);
    }

    /**
     * Enable or disable underline mode for subsequent messages.
     */
    private void setStyleUnderline(boolean underline) {
        synchronized (this.currentStyle) {
            this.currentStyle.removeAttribute(StyleConstants.Underline);
            if (underline) {
                this.currentStyle.addAttribute(StyleConstants.Underline, Boolean.TRUE);
            }
            this.underline = underline;
        }
    }

    /**
     * Toggle underline mode for subsequent messages.
     */
    private void toggleStyleItalic() {
        this.setStyleItalic(!this.italic);
    }

    /**
     * Toggle underline mode for subsequent messages.
     */
    private void toggleStyleUnderline() {
        this.setStyleUnderline(!this.underline);
    }

    /**
     * Set foreground for subsequent messages (IRC standard indexed color).
     */
    private void setStyleForeground(int index) {
        setStyleForeground(IRCColors.indexedColors[index]);
    }

    /**
     * Set foreground for subsequent messages.
     */
    private void setStyleForeground(Color col) {
        synchronized (this.currentStyle) {
            this.currentStyle.removeAttribute(StyleConstants.Foreground);
            this.currentStyle.addAttribute(StyleConstants.Foreground, col);
        }
    }

    /**
     * Set (or change) the character encoding used by this <tt>Handler</tt>.
     * <p>
     * The encoding should be set before any <tt>LogRecords</tt> are written to
     * the <tt>Handler</tt>.
     *
     * @param encoding The name of a supported character encoding. May be null,
     * to indicate the default platform encoding.
     * @exception SecurityException if a security manager exists and if the
     * caller does not have <tt>LoggingPermission("control")</tt>.
     * @exception UnsupportedEncodingException if the named encoding is not
     * supported.
     */
    @Override
    public void setEncoding(String encoding)
            throws SecurityException, java.io.UnsupportedEncodingException {
        super.setEncoding(encoding);
    }

    /**
     * Format and publish a LogRecord.
     *
     * @param record description of the log event
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (!isLoggable(record) || !this.enabled) {
            return;
        }
        String msg;
        int rawMessageLength = 0;
        try {
            msg = this.getFormatter().format(record);
            String rawMessage = this.getFormatter().formatMessage(record);
            if (rawMessage != null) {
                rawMessageLength = rawMessage.length();
            }
        } catch (Throwable ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            if (ex instanceof Exception) {
                reportError(null, (Exception) ex, ErrorManager.FORMAT_FAILURE);
            }
            return;
        }
        try {
            if (!doneHeader) {
                this.logMessage(record.getLevel(), this.getFormatter().getHead(this), rawMessageLength);
                doneHeader = true;
            }
            this.logMessage(record.getLevel(), msg, rawMessageLength);
        } catch (Throwable ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            if (ex instanceof Exception) {
                reportError(null, (Exception) ex, ErrorManager.WRITE_FAILURE);
            }
        }
    }

    /**
     * Check if this Handler would actually log a given LogRecord, depending of
     * the log level
     *
     * @param record a LogRecord
     * @return true if the LogRecord would be logged.
     *
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        return super.isLoggable(record);
    }

    /**
     * Flush any buffered messages.
     */
    @Override
    public synchronized void flush() {
    }

    /**
     * Just flushes the current message
     */
    @Override
    public synchronized void close() throws SecurityException {
        this.flush();
    }

    /**
     * Finally logs the passed message to the text component and sets the canvas
     * pos
     */
    private void logMessage(Level level, String message, int rawMessageLength) {
        int timeStampPos = message.length() - rawMessageLength - this.lineSeparator.length();
        String color = "";
        synchronized (this.colorMap) {
            if (this.colorMap.containsKey(level)) {
                color = this.colorMap.get(level);
            }
        }
        if (timeStampPos >= 0) {
            message = message.substring(0, timeStampPos)
                    + color + message.substring(timeStampPos);
        }
        this.messageDecode(message);
        this.resetStyle();
    }

    /**
     * @return the maxBuffersize
     */
    public long getMaxBuffersize() {
        return MAX_BUFFER_SIZE;
    }

    /**
     * @return the defaultForegroundColor
     */
    public Color getDefaultForegroundColor() {
        return defaultForegroundColor;
    }

}
