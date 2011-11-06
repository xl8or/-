// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IQTypeFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class IQTypeFilter
    implements PacketFilter
{

    public IQTypeFilter(org.jivesoftware.smack.packet.IQ.Type type1)
    {
        type = type1;
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if((packet instanceof IQ) && ((IQ)packet).getType().equals(type))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private org.jivesoftware.smack.packet.IQ.Type type;
}
