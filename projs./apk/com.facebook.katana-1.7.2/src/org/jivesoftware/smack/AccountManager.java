// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccountManager.java

package org.jivesoftware.smack;

import java.util.*;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack:
//            XMPPException, Connection, SmackConfiguration, PacketCollector

public class AccountManager
{

    public AccountManager(Connection connection1)
    {
        info = null;
        accountCreationSupported = false;
        connection = connection1;
    }

    /**
     * @deprecated Method getRegistrationInfo is deprecated
     */

    private void getRegistrationInfo()
        throws XMPPException
    {
        this;
        JVM INSTR monitorenter ;
        IQ iq;
        Registration registration = new Registration();
        registration.setTo(connection.getServiceName());
        PacketFilter apacketfilter[] = new PacketFilter[2];
        apacketfilter[0] = new PacketIDFilter(registration.getPacketID());
        apacketfilter[1] = new PacketTypeFilter(org/jivesoftware/smack/packet/IQ);
        AndFilter andfilter = new AndFilter(apacketfilter);
        PacketCollector packetcollector = connection.createPacketCollector(andfilter);
        connection.sendPacket(registration);
        iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from server.");
        break MISSING_BLOCK_LABEL_120;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
        info = (Registration)iq;
        this;
        JVM INSTR monitorexit ;
    }

    public void changePassword(String s)
        throws XMPPException
    {
        Registration registration = new Registration();
        registration.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
        registration.setTo(connection.getServiceName());
        registration.setUsername(StringUtils.parseName(connection.getUser()));
        registration.setPassword(s);
        PacketFilter apacketfilter[] = new PacketFilter[2];
        apacketfilter[0] = new PacketIDFilter(registration.getPacketID());
        apacketfilter[1] = new PacketTypeFilter(org/jivesoftware/smack/packet/IQ);
        AndFilter andfilter = new AndFilter(apacketfilter);
        PacketCollector packetcollector = connection.createPacketCollector(andfilter);
        connection.sendPacket(registration);
        IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from server.");
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
        else
            return;
    }

    public void createAccount(String s, String s1)
        throws XMPPException
    {
        if(!supportsAccountCreation())
            throw new XMPPException("Server does not support account creation.");
        HashMap hashmap = new HashMap();
        for(Iterator iterator = getAccountAttributes().iterator(); iterator.hasNext(); hashmap.put((String)iterator.next(), ""));
        createAccount(s, s1, ((Map) (hashmap)));
    }

    public void createAccount(String s, String s1, Map map)
        throws XMPPException
    {
        if(!supportsAccountCreation())
            throw new XMPPException("Server does not support account creation.");
        Registration registration = new Registration();
        registration.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
        registration.setTo(connection.getServiceName());
        registration.setUsername(s);
        registration.setPassword(s1);
        String s2;
        for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); registration.addAttribute(s2, (String)map.get(s2)))
            s2 = (String)iterator.next();

        PacketFilter apacketfilter[] = new PacketFilter[2];
        apacketfilter[0] = new PacketIDFilter(registration.getPacketID());
        apacketfilter[1] = new PacketTypeFilter(org/jivesoftware/smack/packet/IQ);
        AndFilter andfilter = new AndFilter(apacketfilter);
        PacketCollector packetcollector = connection.createPacketCollector(andfilter);
        connection.sendPacket(registration);
        IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from server.");
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
        else
            return;
    }

    public void deleteAccount()
        throws XMPPException
    {
        if(!connection.isAuthenticated())
            throw new IllegalStateException("Must be logged in to delete a account.");
        Registration registration = new Registration();
        registration.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
        registration.setTo(connection.getServiceName());
        registration.setRemove(true);
        PacketFilter apacketfilter[] = new PacketFilter[2];
        apacketfilter[0] = new PacketIDFilter(registration.getPacketID());
        apacketfilter[1] = new PacketTypeFilter(org/jivesoftware/smack/packet/IQ);
        AndFilter andfilter = new AndFilter(apacketfilter);
        PacketCollector packetcollector = connection.createPacketCollector(andfilter);
        connection.sendPacket(registration);
        IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from server.");
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
        else
            return;
    }

    public String getAccountAttribute(String s)
    {
        String s2;
        if(info == null)
            getRegistrationInfo();
        s2 = (String)info.getAttributes().get(s);
        String s1 = s2;
_L2:
        return s1;
        XMPPException xmppexception;
        xmppexception;
        xmppexception.printStackTrace();
        s1 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Collection getAccountAttributes()
    {
        List list;
        if(info == null)
            getRegistrationInfo();
        list = info.getRequiredFields();
        if(list.size() <= 0) goto _L2; else goto _L1
_L1:
        Set set1 = Collections.unmodifiableSet(new HashSet(list));
        Set set = set1;
_L4:
        return set;
        XMPPException xmppexception;
        xmppexception;
        xmppexception.printStackTrace();
_L2:
        set = Collections.emptySet();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String getAccountInstructions()
    {
        String s1;
        if(info == null)
            getRegistrationInfo();
        s1 = info.getInstructions();
        String s = s1;
_L2:
        return s;
        XMPPException xmppexception;
        xmppexception;
        s = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void setSupportsAccountCreation(boolean flag)
    {
        accountCreationSupported = flag;
    }

    public boolean supportsAccountCreation()
    {
        if(!accountCreationSupported) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L7:
        return flag;
_L2:
        if(info != null) goto _L4; else goto _L3
_L3:
        boolean flag1;
        getRegistrationInfo();
        if(info.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            break MISSING_BLOCK_LABEL_50;
        flag1 = true;
_L5:
        accountCreationSupported = flag1;
_L4:
        flag = accountCreationSupported;
        continue; /* Loop/switch isn't completed */
        flag1 = false;
          goto _L5
        XMPPException xmppexception;
        xmppexception;
        flag = false;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private boolean accountCreationSupported;
    private Connection connection;
    private Registration info;
}
