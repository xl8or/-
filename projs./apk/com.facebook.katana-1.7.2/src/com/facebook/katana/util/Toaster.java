// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Toaster.java

package com.facebook.katana.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster
{

    public Toaster()
    {
    }

    public static void toast(Context context, int i)
    {
        toast(context, ((CharSequence) (context.getString(i))));
    }

    public static void toast(Context context, CharSequence charsequence)
    {
        boolean flag;
        int i;
        if(charsequence.length() > 60)
            flag = true;
        else
            flag = false;
        if(flag)
            i = 1;
        else
            i = 0;
        Toast.makeText(context, charsequence, i).show();
    }

    public static transient void toast(Context context, String s, Object aobj[])
    {
        toast(context, ((CharSequence) (String.format(s, aobj))));
    }
}
