// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Options.java

package org.xbill.DNS;

import java.util.*;

public final class Options
{

    private Options()
    {
    }

    public static boolean check(String s)
    {
        boolean flag;
        if(table == null)
            flag = false;
        else
        if(table.get(s.toLowerCase()) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void clear()
    {
        table = null;
    }

    public static int intValue(String s)
    {
        String s1 = value(s);
        if(s1 == null) goto _L2; else goto _L1
_L1:
        int j = Integer.parseInt(s1);
        int i = j;
        if(i <= 0) goto _L2; else goto _L3
_L3:
        return i;
        NumberFormatException numberformatexception;
        numberformatexception;
_L2:
        i = -1;
        if(true) goto _L3; else goto _L4
_L4:
    }

    public static void refresh()
    {
        String s = System.getProperty("dnsjava.options");
        if(s != null)
        {
            for(StringTokenizer stringtokenizer = new StringTokenizer(s, ","); stringtokenizer.hasMoreTokens();)
            {
                String s1 = stringtokenizer.nextToken();
                int i = s1.indexOf('=');
                if(i == -1)
                    set(s1);
                else
                    set(s1.substring(0, i), s1.substring(i + 1));
            }

        }
    }

    public static void set(String s)
    {
        if(table == null)
            table = new HashMap();
        table.put(s.toLowerCase(), "true");
    }

    public static void set(String s, String s1)
    {
        if(table == null)
            table = new HashMap();
        table.put(s.toLowerCase(), s1.toLowerCase());
    }

    public static void unset(String s)
    {
        if(table != null)
            table.remove(s.toLowerCase());
    }

    public static String value(String s)
    {
        String s1;
        if(table == null)
            s1 = null;
        else
            s1 = (String)table.get(s.toLowerCase());
        return s1;
    }

    private static Map table;

    static 
    {
        refresh();
_L2:
        return;
        SecurityException securityexception;
        securityexception;
        if(true) goto _L2; else goto _L1
_L1:
    }
}
