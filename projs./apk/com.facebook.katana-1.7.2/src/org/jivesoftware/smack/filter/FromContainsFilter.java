// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FromContainsFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class FromContainsFilter
    implements PacketFilter
{

    public FromContainsFilter(String s)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Parameter cannot be null.");
        } else
        {
            from = s.toLowerCase();
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if(packet.getFrom() == null)
            flag = false;
        else
        if(packet.getFrom().toLowerCase().indexOf(from) != -1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private String from;
}
