// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToContainsFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class ToContainsFilter
    implements PacketFilter
{

    public ToContainsFilter(String s)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Parameter cannot be null.");
        } else
        {
            to = s.toLowerCase();
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if(packet.getTo() == null)
            flag = false;
        else
        if(packet.getTo().toLowerCase().indexOf(to) != -1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private String to;
}
