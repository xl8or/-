// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AndFilter.java

package org.jivesoftware.smack.filter;

import java.util.*;
import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class AndFilter
    implements PacketFilter
{

    public AndFilter()
    {
        filters = new ArrayList();
    }

    public transient AndFilter(PacketFilter apacketfilter[])
    {
        filters = new ArrayList();
        if(apacketfilter == null)
            throw new IllegalArgumentException("Parameter cannot be null.");
        int i = apacketfilter.length;
        for(int j = 0; j < i; j++)
        {
            PacketFilter packetfilter = apacketfilter[j];
            if(packetfilter == null)
                throw new IllegalArgumentException("Parameter cannot be null.");
            filters.add(packetfilter);
        }

    }

    public boolean accept(Packet packet)
    {
        Iterator iterator = filters.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(((PacketFilter)iterator.next()).accept(packet)) goto _L4; else goto _L3
_L3:
        boolean flag = false;
_L6:
        return flag;
_L2:
        flag = true;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void addFilter(PacketFilter packetfilter)
    {
        if(packetfilter == null)
        {
            throw new IllegalArgumentException("Parameter cannot be null.");
        } else
        {
            filters.add(packetfilter);
            return;
        }
    }

    public String toString()
    {
        return filters.toString();
    }

    private List filters;
}
