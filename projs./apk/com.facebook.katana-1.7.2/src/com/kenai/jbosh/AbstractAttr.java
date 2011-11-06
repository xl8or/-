// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractAttr.java

package com.kenai.jbosh;


abstract class AbstractAttr
    implements Comparable
{

    protected AbstractAttr(Comparable comparable)
    {
        value = comparable;
    }

    public int compareTo(Object obj)
    {
        int i;
        if(obj == null)
            i = 1;
        else
            i = value.compareTo(obj);
        return i;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null)
            flag = false;
        else
        if(obj instanceof AbstractAttr)
        {
            AbstractAttr abstractattr = (AbstractAttr)obj;
            flag = value.equals(abstractattr.value);
        } else
        {
            flag = false;
        }
        return flag;
    }

    public final Comparable getValue()
    {
        return value;
    }

    public int hashCode()
    {
        return value.hashCode();
    }

    public String toString()
    {
        return value.toString();
    }

    private final Comparable value;
}
