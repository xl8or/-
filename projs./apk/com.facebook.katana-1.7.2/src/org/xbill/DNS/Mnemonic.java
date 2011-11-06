// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Mnemonic.java

package org.xbill.DNS;

import java.util.HashMap;

class Mnemonic
{

    public Mnemonic(String s, int i)
    {
        description = s;
        wordcase = i;
        strings = new HashMap();
        values = new HashMap();
        max = 0x7fffffff;
    }

    private int parseNumeric(String s)
    {
        int i = Integer.parseInt(s);
        if(i < 0) goto _L2; else goto _L1
_L1:
        int j = max;
        if(i > j) goto _L2; else goto _L3
_L3:
        return i;
        NumberFormatException numberformatexception;
        numberformatexception;
_L2:
        i = -1;
        if(true) goto _L3; else goto _L4
_L4:
    }

    private String sanitize(String s)
    {
        String s1;
        if(wordcase == 2)
            s1 = s.toUpperCase();
        else
        if(wordcase == 3)
            s1 = s.toLowerCase();
        else
            s1 = s;
        return s1;
    }

    public static Integer toInteger(int i)
    {
        Integer integer;
        if(i >= 0 && i < cachedInts.length)
            integer = cachedInts[i];
        else
            integer = new Integer(i);
        return integer;
    }

    public void add(int i, String s)
    {
        check(i);
        Integer integer = toInteger(i);
        String s1 = sanitize(s);
        strings.put(s1, integer);
        values.put(integer, s1);
    }

    public void addAlias(int i, String s)
    {
        check(i);
        Integer integer = toInteger(i);
        String s1 = sanitize(s);
        strings.put(s1, integer);
    }

    public void addAll(Mnemonic mnemonic)
    {
        if(wordcase != mnemonic.wordcase)
        {
            throw new IllegalArgumentException((new StringBuilder()).append(mnemonic.description).append(": wordcases do not match").toString());
        } else
        {
            strings.putAll(mnemonic.strings);
            values.putAll(mnemonic.values);
            return;
        }
    }

    public void check(int i)
    {
        if(i < 0 || i > max)
            throw new IllegalArgumentException((new StringBuilder()).append(description).append(" ").append(i).append("is out of range").toString());
        else
            return;
    }

    public String getText(int i)
    {
        String s;
        check(i);
        s = (String)values.get(toInteger(i));
        if(s == null) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        s = Integer.toString(i);
        if(prefix != null)
            s = (new StringBuilder()).append(prefix).append(s).toString();
        if(true) goto _L1; else goto _L3
_L3:
    }

    public int getValue(String s)
    {
        String s1;
        Integer integer;
        s1 = sanitize(s);
        integer = (Integer)strings.get(s1);
        if(integer == null) goto _L2; else goto _L1
_L1:
        int i = integer.intValue();
_L4:
        return i;
_L2:
        if(prefix != null && s1.startsWith(prefix))
        {
            i = parseNumeric(s1.substring(prefix.length()));
            if(i >= 0)
                continue; /* Loop/switch isn't completed */
        }
        if(numericok)
            i = parseNumeric(s1);
        else
            i = -1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setMaximum(int i)
    {
        max = i;
    }

    public void setNumericAllowed(boolean flag)
    {
        numericok = flag;
    }

    public void setPrefix(String s)
    {
        prefix = sanitize(s);
    }

    static final int CASE_LOWER = 3;
    static final int CASE_SENSITIVE = 1;
    static final int CASE_UPPER = 2;
    private static Integer cachedInts[];
    private String description;
    private int max;
    private boolean numericok;
    private String prefix;
    private HashMap strings;
    private HashMap values;
    private int wordcase;

    static 
    {
        cachedInts = new Integer[64];
        for(int i = 0; i < cachedInts.length; i++)
            cachedInts[i] = new Integer(i);

    }
}
