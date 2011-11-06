// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCAProvider.java

package org.apache.qpid.management.common.sasl;

import java.security.Provider;
import java.util.*;

public class JCAProvider extends Provider
{

    public JCAProvider(Map map)
    {
        super("AMQSASLProvider", 1D, "A JCA provider that registers all AMQ SASL providers that want to be registered");
        register(map);
    }

    private void register(Map map)
    {
        java.util.Map.Entry entry;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); put((new StringBuilder()).append("SaslClientFactory.").append((String)entry.getKey()).toString(), ((Class)entry.getValue()).getName()))
            entry = (java.util.Map.Entry)iterator.next();

    }

    private static final long serialVersionUID = 1L;
}
