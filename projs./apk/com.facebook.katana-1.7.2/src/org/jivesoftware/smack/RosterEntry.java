// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RosterEntry.java

package org.jivesoftware.smack;

import java.util.*;
import org.jivesoftware.smack.packet.RosterPacket;

// Referenced classes of package org.jivesoftware.smack:
//            RosterGroup, Roster, Connection

public class RosterEntry
{

    RosterEntry(String s, String s1, org.jivesoftware.smack.packet.RosterPacket.ItemType itemtype, org.jivesoftware.smack.packet.RosterPacket.ItemStatus itemstatus, Roster roster1, Connection connection1)
    {
        user = s;
        name = s1;
        type = itemtype;
        status = itemstatus;
        roster = roster1;
        connection = connection1;
    }

    static org.jivesoftware.smack.packet.RosterPacket.Item toRosterItem(RosterEntry rosterentry)
    {
        org.jivesoftware.smack.packet.RosterPacket.Item item = new org.jivesoftware.smack.packet.RosterPacket.Item(rosterentry.getUser(), rosterentry.getName());
        item.setItemType(rosterentry.getType());
        item.setItemStatus(rosterentry.getStatus());
        for(Iterator iterator = rosterentry.getGroups().iterator(); iterator.hasNext(); item.addGroupName(((RosterGroup)iterator.next()).getName()));
        return item;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(this == obj)
            flag = true;
        else
        if(obj != null && (obj instanceof RosterEntry))
            flag = user.equals(((RosterEntry)obj).getUser());
        else
            flag = false;
        return flag;
    }

    public Collection getGroups()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = roster.getGroups().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RosterGroup rostergroup = (RosterGroup)iterator.next();
            if(rostergroup.contains(this))
                arraylist.add(rostergroup);
        } while(true);
        return Collections.unmodifiableCollection(arraylist);
    }

    public String getName()
    {
        return name;
    }

    public org.jivesoftware.smack.packet.RosterPacket.ItemStatus getStatus()
    {
        return status;
    }

    public org.jivesoftware.smack.packet.RosterPacket.ItemType getType()
    {
        return type;
    }

    public String getUser()
    {
        return user;
    }

    public void setName(String s)
    {
        if(s == null || !s.equals(name))
        {
            name = s;
            RosterPacket rosterpacket = new RosterPacket();
            rosterpacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
            rosterpacket.addRosterItem(toRosterItem(this));
            connection.sendPacket(rosterpacket);
        }
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        if(name != null)
            stringbuilder.append(name).append(": ");
        stringbuilder.append(user);
        Collection collection = getGroups();
        if(!collection.isEmpty())
        {
            stringbuilder.append(" [");
            Iterator iterator = collection.iterator();
            stringbuilder.append(((RosterGroup)iterator.next()).getName());
            for(; iterator.hasNext(); stringbuilder.append(((RosterGroup)iterator.next()).getName()))
                stringbuilder.append(", ");

            stringbuilder.append("]");
        }
        return stringbuilder.toString();
    }

    void updateState(String s, org.jivesoftware.smack.packet.RosterPacket.ItemType itemtype, org.jivesoftware.smack.packet.RosterPacket.ItemStatus itemstatus)
    {
        name = s;
        type = itemtype;
        status = itemstatus;
    }

    private final Connection connection;
    private String name;
    private final Roster roster;
    private org.jivesoftware.smack.packet.RosterPacket.ItemStatus status;
    private org.jivesoftware.smack.packet.RosterPacket.ItemType type;
    private String user;
}
