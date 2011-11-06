// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultPacketExtension.java

package org.jivesoftware.smack.packet;

import java.util.*;

// Referenced classes of package org.jivesoftware.smack.packet:
//            PacketExtension

public class DefaultPacketExtension
    implements PacketExtension
{

    public DefaultPacketExtension(String s, String s1)
    {
        elementName = s;
        namespace = s1;
    }

    public String getElementName()
    {
        return elementName;
    }

    /**
     * @deprecated Method getNames is deprecated
     */

    public Collection getNames()
    {
        this;
        JVM INSTR monitorenter ;
        if(map != null) goto _L2; else goto _L1
_L1:
        java.util.Set set2 = Collections.emptySet();
        java.util.Set set1 = set2;
_L4:
        this;
        JVM INSTR monitorexit ;
        return set1;
_L2:
        java.util.Set set = Collections.unmodifiableSet((new HashMap(map)).keySet());
        set1 = set;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public String getNamespace()
    {
        return namespace;
    }

    /**
     * @deprecated Method getValue is deprecated
     */

    public String getValue(String s)
    {
        this;
        JVM INSTR monitorenter ;
        Map map1 = map;
        if(map1 != null) goto _L2; else goto _L1
_L1:
        String s1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return s1;
_L2:
        s1 = (String)map.get(s);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method setValue is deprecated
     */

    public void setValue(String s, String s1)
    {
        this;
        JVM INSTR monitorenter ;
        if(map == null)
            map = new HashMap();
        map.put(s, s1);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public String toXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<").append(elementName).append(" xmlns=\"").append(namespace).append("\">");
        String s;
        for(Iterator iterator = getNames().iterator(); iterator.hasNext(); stringbuilder.append("</").append(s).append(">"))
        {
            s = (String)iterator.next();
            String s1 = getValue(s);
            stringbuilder.append("<").append(s).append(">");
            stringbuilder.append(s1);
        }

        stringbuilder.append("</").append(elementName).append(">");
        return stringbuilder.toString();
    }

    private String elementName;
    private Map map;
    private String namespace;
}
