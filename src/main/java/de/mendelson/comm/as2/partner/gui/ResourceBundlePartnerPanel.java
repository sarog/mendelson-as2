//$Header: /as2/de/mendelson/comm/as2/partner/gui/ResourceBundlePartnerPanel.java 60    30.12.20 11:23 Heller $
package de.mendelson.comm.as2.partner.gui;

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
 * @version $Revision: 60 $
 */
public class ResourceBundlePartnerPanel extends MecResourceBundle {

    public static final long serialVersionUID = 1L;
    
    @Override
    public Object[][] getContents() {
        return CONTENTS;
    }
    /**List of messages in the specific language*/
    static final Object[][] CONTENTS = {
        {"title", "Partner configuration"},
        {"label.name", "Name:"},
        {"label.id", "AS2 id:"},
        {"label.partnercomment", "Comment:" },
        {"label.url", "Receipt URL:"},
        {"label.mdnurl", "MDN URL:"},
        {"label.signalias.key", "Private key (Outbound signature generation):"},
        {"label.cryptalias.key", "Private key (Inbound data decryption):"},
        {"label.signalias.cert", "Partner certificate (Inbound signature verification):"},
        {"label.cryptalias.cert", "Partner certificate (Outbound data encryption):"},
        {"label.signtype", "Digital signature algorithm:"},
        {"label.encryptiontype", "Message encryption algorithm:"},
        {"label.email", "EMail address:"},
        {"label.localstation", "Local station"},
        {"label.compression", "Compress outbound messages (requires AS2 1.1 partner solution)"},
        {"label.usecommandonreceipt", "On msg receipt:"},
        {"label.usecommandonsenderror", "On msg send (error):"},
        {"label.usecommandonsendsuccess", "On msg send (success):"},
        {"label.keepfilenameonreceipt", "Keep original file name on receipt (if sender added this information)"},
        {"label.address", "Address:" },
        {"label.contact", "Contact:" },        
        {"tab.misc", "Misc"},
        {"tab.security", "Security"},
        {"tab.send", "Send"},
        {"tab.mdn", "MDN"},
        {"tab.dirpoll", "Directory polling"},
        {"tab.receipt", "Receipt"},
        {"tab.httpauth", "HTTP authentication"},
        {"tab.httpheader", "HTTP header"},
        {"tab.notification", "Notification" },
        {"tab.events", "Postprocessing" },
        {"tab.partnersystem", "Info" },
        {"label.subject", "Payload subject:"},
        {"label.contenttype", "Payload content type:"},
        {"label.syncmdn", "Request sync MDN"},
        {"label.asyncmdn", "Request async MDN"},
        {"label.signedmdn", "Request signed MDN"},
        {"label.polldir", "Poll directory:"},
        {"label.pollinterval", "Poll interval:"},
        {"label.pollignore", "Poll ignore files:"},
        {"label.maxpollfiles", "Max files per poll:"},
        {"label.usehttpauth", "Use HTTP authentication to send AS2 messages"},
        {"label.usehttpauth.user", "Username:"},
        {"label.usehttpauth.pass", "Password:"},
        {"label.usehttpauth.asyncmdn", "Use HTTP authentication to send async MDN"},
        {"label.usehttpauth.asyncmdn.user", "Username:"},
        {"label.usehttpauth.asyncmdn.pass", "Password:"},        
        {"hint.subject.replacement", "<HTML>$'{'filename} will be replaced by the send filename.<br>This value will be transferred in the HTTP header, there are restrictions! Please use ISO-8859-1 as character encoding, only printable characters, no special characters. CR, LF and TAB are replaced by \"\\r\", \"\\\n\" and \"\\t\".</HTML>"},
        {"hint.keepfilenameonreceipt", "Please ensure your partner sends unique file names before enabling this option!"},        
        {"label.notify.send", "Notify if send message quota exceeds" },
        {"label.notify.receive", "Notify if receive message quota exceeds" },
        {"label.notify.sendreceive", "Notify if receive and send message quota exceeds" },
        {"header.httpheaderkey", "Name" },
        {"header.httpheadervalue", "Value" },
        {"httpheader.add", "Add" },
        {"httpheader.delete", "Remove" },
        {"label.as2version", "AS2 version:" },
        {"label.productname", "Product name:" },
        {"label.features", "Features:" },
        {"label.features.cem", "Certificate exchange via CEM" },
        {"label.features.ma", "Multiple attachments" },
        {"label.features.compression", "Compression" },
        {"partnerinfo", "Your trading partner transmits with every AS2 message some informations about his AS2 system capabilities. This is a list of features that has been transmitted by your partner." },
        {"partnersystem.noinfo", "No info available - has there been already a transaction?" },
        {"label.httpversion", "HTTP protocol version:" },
        {"label.test.connection", "Test connection" },
        {"label.url.hint", "<HTML>Please specify this URL in the format <strong>PROTOCOL://HOST:PORT/PATH</strong>, where the <strong>PROTOCOL</strong> must be one of \"http\" or \"https\". <strong>HOST</strong> denotes the AS2 server host of your partner. <strong>PORT</strong> is the receive port of your partner. If it is not specified, the value \"80\" will be set. <strong>PFAD</strong> denotes the receive path, for example \"/as2/HttpReceiver\".</HTML>"},
        {"label.url.hint.mdn", "<HTML>This is the URL that your partner will use for the incoming asynchronous MDN to this local station.<br>Please specify this URL in the format <strong>PROTOCOL://HOST:PORT/PATH</strong>. <br><strong>PROTOCOL</strong> must be one of \"http\" or \"https\".<br><strong>HOST</strong> denotes your own AS2 server host.<br><strong>PORT</strong> is the receive port of your AS2 system. If it is not specified, the value \"80\" is set.<br><strong>PATH</strong> denotes the receive path, for example \"/as2/HttpReceiver\".</HTML>"},
        {"label.mdn.description", "<HTML>The MDN (message delivery notification) is the acknowledgement message for the AS2 message. This section defines the behavior your partner has to follow for your outbound AS2 messages.</HTML>" },
        {"label.mdn.sync.description", "<HTML>The partner sends the acknowledgement (MDN) on the backchannel of your outbound connection</HTML>" },
        {"label.mdn.async.description", "<HTML>The partner establishs a new connection to your system to send the acknowledgement for your outbound message</HTML>" },
        {"label.mdn.sign.description", "<HTML>The AS2 protocol does not define how to deal with a MDN if the signature does not match - mendelson AS2 will just display a warning</HTML>" },
        {"label.algorithmidentifierprotection", "<HTML>Use \"Algorithm Identifier Protection Attribute\" in signature (recommended), please refer to RFC 6211</HTML>" },
        {"label.enabledirpoll", "Enable directory poll for this partner" },
        {"tooltip.button.editevent", "Edit event" },
        {"tooltip.button.addevent", "Create a new event" },
        {"label.httpauthentication.info", "<HTML>Please setup the HTTP basic access authentication here if this is enabled on your partners side (as defined in RFC 7617). To unauthenticated requests (wrong credentials etc), the remote partners system should return a <strong>HTTP 401 Unauthorized</strong> status.<br>If the connection to your partner requires TLS client authentication (via certificates), there is no setting required here. In this case just import the partners certificates via the TLS certificate manager and you are done.</HTML>" },
    };
}
