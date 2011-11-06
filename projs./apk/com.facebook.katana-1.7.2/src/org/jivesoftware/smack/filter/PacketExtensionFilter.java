// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketExtensionFilter.java

package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack.filter:
//            PacketFilter

public class PacketExtensionFilter
    implements PacketFilter
{

    public PacketExtensionFilter(String s)
    {
        this(null, s);
    }

    public PacketExtensionFilter(String s, String s1)
    {
        elementName = s;
        namespace = s1;
    }

    public boolean accept(Packet packet)
    {
        boolean flag;
        if(packet.getExtension(elementName, namespace) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private String elementName;
    private String namespace;
}
