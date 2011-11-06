// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntentUtils.java

package com.facebook.katana.util;

import java.util.*;

public class IntentUtils
{

    public IntentUtils()
    {
    }

    public static Set primitiveToSet(long al[])
    {
        Object obj;
        if(al == null)
        {
            obj = null;
        } else
        {
            HashSet hashset = new HashSet();
            int i = al.length;
            for(int j = 0; j < i; j++)
                hashset.add(Long.valueOf(al[j]));

            obj = hashset;
        }
        return ((Set) (obj));
    }

    public static long[] setToPrimitive(Set set)
    {
        long al1[];
        if(set == null)
        {
            al1 = null;
        } else
        {
            ArrayList arraylist = new ArrayList(set);
            long al[] = new long[arraylist.size()];
            for(int i = 0; i < al.length; i++)
                al[i] = ((Long)arraylist.get(i)).longValue();

            al1 = al;
        }
        return al1;
    }
}
