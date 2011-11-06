// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class NotFilter
    implements PacketFilter
{

    public NotFilter(PacketFilter packetfilter)
    {
        if(packetfilter == null)
        {
            throw new IllegalArgumentException("Parameter cannot be null.");
        } else
        {
            filter = packetfilter;
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if(!filter.accept(packet))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private PacketFilter filter;
}
