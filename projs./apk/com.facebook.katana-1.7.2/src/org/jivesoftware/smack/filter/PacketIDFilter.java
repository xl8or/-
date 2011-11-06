// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketIDFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class PacketIDFilter
    implements PacketFilter
{

    public PacketIDFilter(String s)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Packet ID cannot be null.");
        } else
        {
            packetID = s;
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        return packetID.equals(packet.getPacketID());
    }

    public String toString()
    {
        return (new StringBuilder()).append("PacketIDFilter by id: ").append(packetID).toString();
    }

    private String packetID;
}
