// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NonSASLAuthentication.java

package org.jivesoftware.smack;

import org.apache.harmony.javax.security.auth.callback.*;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.Authentication;
import org.jivesoftware.smack.packet.IQ;

// Referenced classes of package org.jivesoftware.smack:
//            UserAuthentication, XMPPException, Connection, SmackConfiguration, 
//            PacketCollector

class NonSASLAuthentication
    implements UserAuthentication
{

    public NonSASLAuthentication(Connection connection1)
    {
        connection = connection1;
    }

    public String authenticate(String s, String s1, String s2)
        throws XMPPException
    {
        Authentication authentication = new Authentication();
        authentication.setType(org.jivesoftware.smack.packet.IQ.Type.GET);
        authentication.setUsername(s);
        PacketCollector packetcollector = connection.createPacketCollector(new PacketIDFilter(authentication.getPacketID()));
        connection.sendPacket(authentication);
        IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        if(iq == null)
            throw new XMPPException("No response from the server.");
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
        Authentication authentication1 = (Authentication)iq;
        packetcollector.cancel();
        Authentication authentication2 = new Authentication();
        authentication2.setUsername(s);
        PacketCollector packetcollector1;
        IQ iq1;
        if(authentication1.getDigest() != null)
            authentication2.setDigest(connection.getConnectionID(), s1);
        else
        if(authentication1.getPassword() != null)
            authentication2.setPassword(s1);
        else
            throw new XMPPException("Server does not support compatible authentication mechanism.");
        authentication2.setResource(s2);
        packetcollector1 = connection.createPacketCollector(new PacketIDFilter(authentication2.getPacketID()));
        connection.sendPacket(authentication2);
        iq1 = (IQ)packetcollector1.nextResult(SmackConfiguration.getPacketReplyTimeout());
        if(iq1 == null)
            throw new XMPPException("Authentication failed.");
        if(iq1.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
        {
            throw new XMPPException(iq1.getError());
        } else
        {
            packetcollector1.cancel();
            return iq1.getTo();
        }
    }

    public String authenticate(String s, String s1, CallbackHandler callbackhandler)
        throws XMPPException
    {
        PasswordCallback passwordcallback = new PasswordCallback("Password: ", false);
        String s2;
        try
        {
            Callback acallback[] = new Callback[1];
            acallback[0] = passwordcallback;
            callbackhandler.handle(acallback);
            s2 = authenticate(s, String.valueOf(passwordcallback.getPassword()), s1);
        }
        catch(Exception exception)
        {
            throw new XMPPException("Unable to determine password.", exception);
        }
        return s2;
    }

    public String authenticateAnonymously()
        throws XMPPException
    {
        Authentication authentication = new Authentication();
        PacketCollector packetcollector = connection.createPacketCollector(new PacketIDFilter(authentication.getPacketID()));
        connection.sendPacket(authentication);
        IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        if(iq == null)
            throw new XMPPException("Anonymous login failed.");
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
        packetcollector.cancel();
        String s;
        if(iq.getTo() != null)
            s = iq.getTo();
        else
            s = (new StringBuilder()).append(connection.getServiceName()).append("/").append(((Authentication)iq).getResource()).toString();
        return s;
    }

    private Connection connection;
}
