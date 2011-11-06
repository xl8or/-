// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EclairKeyHandler.java

package com.facebook.katana.util;

import android.view.KeyEvent;

public class EclairKeyHandler
{

    private EclairKeyHandler()
    {
    }

    public static boolean onKeyDown(KeyEvent keyevent)
    {
        boolean flag;
        if(keyevent.getRepeatCount() == 0)
        {
            keyevent.startTracking();
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public static boolean onKeyUp(KeyEvent keyevent)
    {
        boolean flag;
        if(keyevent.isTracking() && !keyevent.isCanceled())
            flag = true;
        else
            flag = false;
        return flag;
    }
}
