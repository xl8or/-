// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageTypeFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class MessageTypeFilter
    implements PacketFilter
{

    public MessageTypeFilter(org.jivesoftware.smack.packet.Message.Type type1)
    {
        type = type1;
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if(!(packet instanceof Message))
            flag = false;
        else
            flag = ((Message)packet).getType().equals(type);
        return flag;
    }

    private final org.jivesoftware.smack.packet.Message.Type type;
}
