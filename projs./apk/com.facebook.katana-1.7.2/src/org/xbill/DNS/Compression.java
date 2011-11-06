// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Compression.java

package org.xbill.DNS;

import java.io.PrintStream;

// Referenced classes of package org.xbill.DNS:
//            Options, Name

public class Compression
{
    private static class Entry
    {

        Name name;
        Entry next;
        int pos;

        private Entry()
        {
        }

    }


    public Compression()
    {
        verbose = Options.check("verbosecompression");
        table = new Entry[17];
    }

    public void add(int i, Name name)
    {
        if(i <= 16383) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j = (0x7fffffff & name.hashCode()) % 17;
        Entry entry = new Entry();
        entry.name = name;
        entry.pos = i;
        entry.next = table[j];
        table[j] = entry;
        if(verbose)
            System.err.println((new StringBuilder()).append("Adding ").append(name).append(" at ").append(i).toString());
        if(true) goto _L1; else goto _L3
_L3:
    }

    public int get(Name name)
    {
        int i = (0x7fffffff & name.hashCode()) % 17;
        int j = -1;
        for(Entry entry = table[i]; entry != null; entry = entry.next)
            if(entry.name.equals(name))
                j = entry.pos;

        if(verbose)
            System.err.println((new StringBuilder()).append("Looking for ").append(name).append(", found ").append(j).toString());
        return j;
    }

    private static final int MAX_POINTER = 16383;
    private static final int TABLE_SIZE = 17;
    private Entry table[];
    private boolean verbose;
}
