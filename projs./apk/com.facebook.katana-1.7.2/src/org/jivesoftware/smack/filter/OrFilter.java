// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class OrFilter
    implements PacketFilter
{

    public OrFilter()
    {
        size = 0;
        filters = new PacketFilter[3];
    }

    public OrFilter(PacketFilter packetfilter, PacketFilter packetfilter1)
    {
        if(packetfilter == null || packetfilter1 == null)
        {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else
        {
            size = 2;
            filters = new PacketFilter[2];
            filters[0] = packetfilter;
            filters[1] = packetfilter1;
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        int i = 0;
_L3:
        if(i >= size)
            break MISSING_BLOCK_LABEL_36;
        if(!filters[i].accept(packet)) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        i++;
          goto _L3
        flag = false;
          goto _L4
    }

    public void addFilter(PacketFilter packetfilter)
    {
        if(packetfilter == null)
            throw new IllegalArgumentException("Parameter cannot be null.");
        if(size == filters.length)
        {
            PacketFilter apacketfilter[] = new PacketFilter[2 + filters.length];
            for(int i = 0; i < filters.length; i++)
                apacketfilter[i] = filters[i];

            filters = apacketfilter;
        }
        filters[size] = packetfilter;
        size = 1 + size;
    }

    public String toString()
    {
        return filters.toString();
    }

    private PacketFilter filters[];
    private int size;
}
