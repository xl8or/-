// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThreadFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class ThreadFilter
    implements PacketFilter
{

    public ThreadFilter(String s)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Thread cannot be null.");
        } else
        {
            thread = s;
            return;
        }
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if((packet instanceof Message) && thread.equals(((Message)packet).getThread()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private String thread;
}
