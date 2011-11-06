// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WeakRef.java

package com.facebook.katana.util;

import java.lang.ref.WeakReference;

public class WeakRef extends WeakReference
{

    public WeakRef(Object obj)
    {
        super(obj);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof WeakRef))
        {
            flag = false;
        } else
        {
            WeakRef weakref = (WeakRef)obj;
            Object obj1 = get();
            Object obj2 = weakref.get();
            if(obj1 != null && obj2 != null)
                flag = obj1.equals(obj2);
            else
                flag = false;
        }
        return flag;
    }

    public int hashCode()
    {
        Object obj = get();
        int i;
        if(obj != null)
            i = obj.hashCode();
        else
            i = 0;
        return i;
    }
}
