// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RosterPacket.java

package org.jivesoftware.smack.packet;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.packet:
//            IQ

public class RosterPacket extends IQ
{
    public static final class ItemType extends Enum
    {

        public static ItemType valueOf(String s)
        {
            return (ItemType)Enum.valueOf(org/jivesoftware/smack/packet/RosterPacket$ItemType, s);
        }

        public static ItemType[] values()
        {
            return (ItemType[])$VALUES.clone();
        }

        private static final ItemType $VALUES[];
        public static final ItemType both;
        public static final ItemType from;
        public static final ItemType none;
        public static final ItemType remove;
        public static final ItemType to;

        static 
        {
            none = new ItemType("none", 0);
            to = new ItemType("to", 1);
            from = new ItemType("from", 2);
            both = new ItemType("both", 3);
            remove = new ItemType("remove", 4);
            ItemType aitemtype[] = new ItemType[5];
            aitemtype[0] = none;
            aitemtype[1] = to;
            aitemtype[2] = from;
            aitemtype[3] = both;
            aitemtype[4] = remove;
            $VALUES = aitemtype;
        }

        private ItemType(String s, int i)
        {
            super(s, i);
        }
    }

    public static class ItemStatus
    {

        public static ItemStatus fromString(String s)
        {
            ItemStatus itemstatus;
            if(s == null)
            {
                itemstatus = null;
            } else
            {
                String s1 = s.toLowerCase();
                if("unsubscribe".equals(s1))
                    itemstatus = UNSUBSCRIPTION_PENDING;
                else
                if("subscribe".equals(s1))
                    itemstatus = SUBSCRIPTION_PENDING;
                else
                    itemstatus = null;
            }
            return itemstatus;
        }

        public String toString()
        {
            return value;
        }

        public static final ItemStatus SUBSCRIPTION_PENDING = new ItemStatus("subscribe");
        public static final ItemStatus UNSUBSCRIPTION_PENDING = new ItemStatus("unsubscribe");
        private String value;


        private ItemStatus(String s)
        {
            value = s;
        }
    }

    public static class Item
    {

        public void addGroupName(String s)
        {
            groupNames.add(s);
        }

        public Set getGroupNames()
        {
            return Collections.unmodifiableSet(groupNames);
        }

        public ItemStatus getItemStatus()
        {
            return itemStatus;
        }

        public ItemType getItemType()
        {
            return itemType;
        }

        public String getName()
        {
            return name;
        }

        public String getUser()
        {
            return user;
        }

        public void removeGroupName(String s)
        {
            groupNames.remove(s);
        }

        public void setItemStatus(ItemStatus itemstatus)
        {
            itemStatus = itemstatus;
        }

        public void setItemType(ItemType itemtype)
        {
            itemType = itemtype;
        }

        public void setName(String s)
        {
            name = s;
        }

        public String toXML()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("<item jid=\"").append(user).append("\"");
            if(name != null)
                stringbuilder.append(" name=\"").append(StringUtils.escapeForXML(name)).append("\"");
            if(itemType != null)
                stringbuilder.append(" subscription=\"").append(itemType).append("\"");
            if(itemStatus != null)
                stringbuilder.append(" ask=\"").append(itemStatus).append("\"");
            stringbuilder.append(">");
            String s;
            for(Iterator iterator = groupNames.iterator(); iterator.hasNext(); stringbuilder.append("<group>").append(StringUtils.escapeForXML(s)).append("</group>"))
                s = (String)iterator.next();

            stringbuilder.append("</item>");
            return stringbuilder.toString();
        }

        private final Set groupNames = new CopyOnWriteArraySet();
        private ItemStatus itemStatus;
        private ItemType itemType;
        private String name;
        private String user;

        public Item(String s, String s1)
        {
            user = s.toLowerCase();
            name = s1;
            itemType = null;
            itemStatus = null;
        }
    }


    public RosterPacket()
    {
    }

    public void addRosterItem(Item item)
    {
        List list = rosterItems;
        list;
        JVM INSTR monitorenter ;
        rosterItems.add(item);
        return;
    }

    public String getChildElementXML()
    {
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder();
        stringbuilder.append("<query xmlns=\"jabber:iq:roster\" ");
        if(version != null)
            stringbuilder.append((new StringBuilder()).append(" ver=\"").append(version).append("\" ").toString());
        stringbuilder.append(">");
        List list = rosterItems;
        list;
        JVM INSTR monitorenter ;
        for(Iterator iterator = rosterItems.iterator(); iterator.hasNext(); stringbuilder.append(((Item)iterator.next()).toXML()));
        break MISSING_BLOCK_LABEL_120;
        Exception exception;
        exception;
        throw exception;
        list;
        JVM INSTR monitorexit ;
        stringbuilder.append("</query>");
        return stringbuilder.toString();
    }

    public int getRosterItemCount()
    {
        List list = rosterItems;
        list;
        JVM INSTR monitorenter ;
        int i = rosterItems.size();
        return i;
    }

    public Collection getRosterItems()
    {
        List list = rosterItems;
        list;
        JVM INSTR monitorenter ;
        List list1 = Collections.unmodifiableList(new ArrayList(rosterItems));
        return list1;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String s)
    {
        version = s;
    }

    private final List rosterItems = new ArrayList();
    private String version;
}
