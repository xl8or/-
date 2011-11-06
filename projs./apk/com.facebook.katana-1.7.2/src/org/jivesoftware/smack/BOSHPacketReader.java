// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOSHPacketReader.java

package org.jivesoftware.smack;

import com.kenai.jbosh.*;
import java.io.StringReader;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

// Referenced classes of package org.jivesoftware.smack:
//            BOSHConnection, SASLAuthentication, AccountManager, XMPPException

public class BOSHPacketReader
    implements BOSHClientResponseListener
{

    public BOSHPacketReader(BOSHConnection boshconnection)
    {
        connection = boshconnection;
    }

    private void parseFeatures(XmlPullParser xmlpullparser)
        throws Exception
    {
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("mechanisms"))
                    connection.getSASLAuthentication().setAvailableSASLMethods(PacketParserUtils.parseMechanisms(xmlpullparser));
                else
                if(xmlpullparser.getName().equals("bind"))
                    connection.getSASLAuthentication().bindingRequired();
                else
                if(xmlpullparser.getName().equals("session"))
                    connection.getSASLAuthentication().sessionsSupported();
                else
                if(xmlpullparser.getName().equals("register"))
                    connection.getAccountManager().setSupportsAccountCreation(true);
            } else
            if(i == 3 && xmlpullparser.getName().equals("features"))
                flag = true;
        } while(true);
    }

    public void responseReceived(BOSHMessageEvent boshmessageevent)
    {
        AbstractBody abstractbody;
        abstractbody = boshmessageevent.getBody();
        if(abstractbody == null)
            break MISSING_BLOCK_LABEL_531;
        XmlPullParser xmlpullparser;
        if(connection.sessionID == null)
            connection.sessionID = abstractbody.getAttribute(BodyQName.create("http://jabber.org/protocol/httpbind", "sid"));
        if(connection.authID == null)
            connection.authID = abstractbody.getAttribute(BodyQName.create("http://jabber.org/protocol/httpbind", "authid"));
        xmlpullparser = XmlPullParserFactory.newInstance().newPullParser();
        xmlpullparser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        xmlpullparser.setInput(new StringReader(abstractbody.toXML()));
        xmlpullparser.getEventType();
        int i;
        do
        {
            i = xmlpullparser.next();
            if(i != 2 || xmlpullparser.getName().equals("body"))
                continue;
            Exception exception;
            if(xmlpullparser.getName().equals("message"))
            {
                connection.processPacket(PacketParserUtils.parseMessage(xmlpullparser));
                continue;
            }
            try
            {
                if(xmlpullparser.getName().equals("iq"))
                    connection.processPacket(PacketParserUtils.parseIQ(xmlpullparser, connection));
                else
                if(xmlpullparser.getName().equals("presence"))
                    connection.processPacket(PacketParserUtils.parsePresence(xmlpullparser));
                else
                if(xmlpullparser.getName().equals("challenge"))
                {
                    String s = xmlpullparser.nextText();
                    connection.getSASLAuthentication().challengeReceived(s);
                    connection.processPacket(new org.jivesoftware.smack.sasl.SASLMechanism.Challenge(s));
                } else
                if(xmlpullparser.getName().equals("success"))
                {
                    connection.send(ComposableBody.builder().setNamespaceDefinition("xmpp", "urn:xmpp:xbosh").setAttribute(BodyQName.createWithPrefix("urn:xmpp:xbosh", "restart", "xmpp"), "true").setAttribute(BodyQName.create("http://jabber.org/protocol/httpbind", "to"), connection.getServiceName()).build());
                    connection.getSASLAuthentication().authenticated();
                    connection.processPacket(new org.jivesoftware.smack.sasl.SASLMechanism.Success(xmlpullparser.nextText()));
                } else
                if(xmlpullparser.getName().equals("features"))
                    parseFeatures(xmlpullparser);
                else
                if(xmlpullparser.getName().equals("failure"))
                {
                    if("urn:ietf:params:xml:ns:xmpp-sasl".equals(xmlpullparser.getNamespace(null)))
                    {
                        org.jivesoftware.smack.sasl.SASLMechanism.Failure failure = PacketParserUtils.parseSASLFailure(xmlpullparser);
                        connection.getSASLAuthentication().authenticationFailed();
                        connection.processPacket(failure);
                    }
                } else
                if(xmlpullparser.getName().equals("error"))
                    throw new XMPPException(PacketParserUtils.parseStreamError(xmlpullparser));
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception)
            {
                if(connection.isConnected())
                    connection.notifyConnectionError(exception);
                break;
            }
        } while(i != 1);
    }

    private BOSHConnection connection;
}
