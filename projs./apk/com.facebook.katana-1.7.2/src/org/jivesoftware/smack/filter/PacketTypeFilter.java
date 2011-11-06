// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketTypeFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class PacketTypeFilter
    implements PacketFilter
{

    public PacketTypeFilter(Class class1)
    {
        if(!org/jivesoftware/smack/packet/Packet.isAssignableFrom(class1))
        {
            throw new IllegalArgumentException("Packet type must be a sub-class of Packet.");
        } else
        {
            packetType = class1;
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        return packetType.isInstance(packet);
    }

    public String toString()
    {
        return (new StringBuilder()).append("PacketTypeFilter: ").append(packetType.getName()).toString();
    }

    Class packetType;
}
