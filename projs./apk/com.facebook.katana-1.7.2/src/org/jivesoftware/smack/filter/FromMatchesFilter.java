// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FromMatchesFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class FromMatchesFilter
    implements PacketFilter
{

    public FromMatchesFilter(String s)
    {
        matchBareJID = false;
        if(s == null)
        {
            throw new IllegalArgumentException("Parameter cannot be null.");
        } else
        {
            address = s.toLowerCase();
            matchBareJID = "".equals(StringUtils.parseResource(s));
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if(packet.getFrom() == null)
            flag = false;
        else
        if(matchBareJID)
            flag = packet.getFrom().toLowerCase().startsWith(address);
        else
            flag = address.equals(packet.getFrom().toLowerCase());
        return flag;
    }

    public String toString()
    {
        return (new StringBuilder()).append("FromMatchesFilter: ").append(address).toString();
    }

    private String address;
    private boolean matchBareJID;
}
