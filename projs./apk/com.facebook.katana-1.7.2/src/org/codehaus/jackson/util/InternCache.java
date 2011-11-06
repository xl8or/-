// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.util;

import java.util.LinkedHashMap;

public final class InternCache extends LinkedHashMap
{

    private InternCache()
    {
        super(192, 0.8F, true);
    }

    /**
     * @deprecated Method intern is deprecated
     */

    public String intern(String s)
    {
        this;
        JVM INSTR monitorenter ;
        String s1;
        s1 = (String)get(s);
        if(s1 == null)
        {
            s1 = s.intern();
            put(s1, s1);
        }
        this;
        JVM INSTR monitorexit ;
        return s1;
        Exception exception;
        exception;
        throw exception;
    }

    protected boolean removeEldestEntry(java.util.Map.Entry entry)
    {
        boolean flag;
        if(size() > 192)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static final int MAX_ENTRIES = 192;
    public static final InternCache instance = new InternCache();

}
