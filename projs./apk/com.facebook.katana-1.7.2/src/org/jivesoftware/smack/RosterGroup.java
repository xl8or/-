// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RosterGroup.java

package org.jivesoftware.smack;

import java.util.*;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack:
//            XMPPException, RosterEntry, Connection, SmackConfiguration, 
//            PacketCollector

public class RosterGroup
{

    RosterGroup(String s, Connection connection1)
    {
        name = s;
        connection = connection1;
    }

    public void addEntry(RosterEntry rosterentry)
        throws XMPPException
    {
        IQ iq;
        PacketCollector packetcollector = null;
        synchronized(entries)
        {
            if(!entries.contains(rosterentry))
            {
                RosterPacket rosterpacket = new RosterPacket();
                rosterpacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
                org.jivesoftware.smack.packet.RosterPacket.Item item = RosterEntry.toRosterItem(rosterentry);
                item.addGroupName(getName());
                rosterpacket.addRosterItem(item);
                PacketCollector packetcollector1 = connection.createPacketCollector(new PacketIDFilter(rosterpacket.getPacketID()));
                connection.sendPacket(rosterpacket);
                packetcollector = packetcollector1;
            }
        }
        if(packetcollector == null)
            break MISSING_BLOCK_LABEL_163;
        iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from the server.");
        break MISSING_BLOCK_LABEL_139;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
    }

    public void addEntryLocal(RosterEntry rosterentry)
    {
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        entries.remove(rosterentry);
        entries.add(rosterentry);
        return;
    }

    public boolean contains(String s)
    {
        boolean flag;
        if(getEntry(s) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean contains(RosterEntry rosterentry)
    {
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        boolean flag = entries.contains(rosterentry);
        return flag;
    }

    public Collection getEntries()
    {
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        List list1 = Collections.unmodifiableList(new ArrayList(entries));
        return list1;
    }

    public RosterEntry getEntry(String s)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        RosterEntry rosterentry = null;
_L4:
        return rosterentry;
_L2:
        String s1 = StringUtils.parseBareAddress(s).toLowerCase();
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        for(Iterator iterator = entries.iterator(); iterator.hasNext();)
        {
            RosterEntry rosterentry1 = (RosterEntry)iterator.next();
            if(rosterentry1.getUser().equals(s1))
            {
                rosterentry = rosterentry1;
                continue; /* Loop/switch isn't completed */
            }
        }

        rosterentry = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getEntryCount()
    {
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        int i = entries.size();
        return i;
    }

    public String getName()
    {
        return name;
    }

    public void removeEntry(RosterEntry rosterentry)
        throws XMPPException
    {
        IQ iq;
        PacketCollector packetcollector = null;
        synchronized(entries)
        {
            if(entries.contains(rosterentry))
            {
                RosterPacket rosterpacket = new RosterPacket();
                rosterpacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
                org.jivesoftware.smack.packet.RosterPacket.Item item = RosterEntry.toRosterItem(rosterentry);
                item.removeGroupName(getName());
                rosterpacket.addRosterItem(item);
                PacketCollector packetcollector1 = connection.createPacketCollector(new PacketIDFilter(rosterpacket.getPacketID()));
                connection.sendPacket(rosterpacket);
                packetcollector = packetcollector1;
            }
        }
        if(packetcollector == null)
            break MISSING_BLOCK_LABEL_163;
        iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from the server.");
        break MISSING_BLOCK_LABEL_139;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(iq.getError());
    }

    void removeEntryLocal(RosterEntry rosterentry)
    {
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        if(entries.contains(rosterentry))
            entries.remove(rosterentry);
        return;
    }

    public void setName(String s)
    {
        List list = entries;
        list;
        JVM INSTR monitorenter ;
        RosterPacket rosterpacket;
        for(Iterator iterator = entries.iterator(); iterator.hasNext(); connection.sendPacket(rosterpacket))
        {
            RosterEntry rosterentry = (RosterEntry)iterator.next();
            rosterpacket = new RosterPacket();
            rosterpacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
            org.jivesoftware.smack.packet.RosterPacket.Item item = RosterEntry.toRosterItem(rosterentry);
            item.removeGroupName(name);
            item.addGroupName(s);
            rosterpacket.addRosterItem(item);
        }

        break MISSING_BLOCK_LABEL_103;
        Exception exception;
        exception;
        throw exception;
        list;
        JVM INSTR monitorexit ;
    }

    private Connection connection;
    private final List entries = new ArrayList();
    private String name;
}
