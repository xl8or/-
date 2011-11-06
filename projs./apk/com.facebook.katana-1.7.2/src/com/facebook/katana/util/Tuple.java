// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tuple.java

package com.facebook.katana.util;


public class Tuple
{

    public Tuple(Object obj, Object obj1)
    {
        d0 = obj;
        d1 = obj1;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof Tuple))
        {
            flag = false;
        } else
        {
            Tuple tuple = (Tuple)obj;
            if(d0 != tuple.d0 && d0 != null && !d0.equals(tuple.d0))
                flag = false;
            else
            if(d1 != tuple.d1 && d1 != null && !d1.equals(tuple.d1))
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    public int hashCode()
    {
        int i = 0;
        if(d0 != null)
            i ^= d0.hashCode();
        if(d1 != null)
            i ^= d1.hashCode();
        return i;
    }

    public String toString()
    {
        String s = "<";
        if(d0 != null)
            s = (new StringBuilder()).append(s).append(d0).toString();
        String s1 = (new StringBuilder()).append(s).append(":").toString();
        if(d1 != null)
            s1 = (new StringBuilder()).append(s1).append(d1).toString();
        return (new StringBuilder()).append(s1).append(">").toString();
    }

    public final Object d0;
    public final Object d1;
}
