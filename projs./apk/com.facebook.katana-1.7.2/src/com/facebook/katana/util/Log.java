// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Log.java

package com.facebook.katana.util;

import com.facebook.katana.Constants;

public class Log
{

    public Log()
    {
    }

    public static void d(String s, String s1)
    {
        if(Constants.isBetaBuild())
            android.util.Log.d(s, s1);
    }

    public static void d(String s, String s1, Throwable throwable)
    {
        if(Constants.isBetaBuild())
            android.util.Log.d(s, s1, throwable);
    }

    public static void e(String s, String s1)
    {
        if(Constants.isBetaBuild())
            android.util.Log.e(s, s1);
    }

    public static void e(String s, String s1, Throwable throwable)
    {
        if(Constants.isBetaBuild())
            android.util.Log.e(s, s1, throwable);
    }

    public static void i(String s, String s1)
    {
        if(Constants.isBetaBuild())
            android.util.Log.i(s, s1);
    }

    public static void i(String s, String s1, Throwable throwable)
    {
        if(Constants.isBetaBuild())
            android.util.Log.i(s, s1, throwable);
    }

    public static boolean isLoggable(String s, int j)
    {
        boolean flag;
        if(Constants.isBetaBuild() && android.util.Log.isLoggable(s, j))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void v(String s, String s1)
    {
        if(Constants.isBetaBuild())
            android.util.Log.v(s, s1);
    }

    public static void v(String s, String s1, Throwable throwable)
    {
        if(Constants.isBetaBuild())
            android.util.Log.v(s, s1, throwable);
    }

    public static void w(String s, String s1)
    {
        if(Constants.isBetaBuild())
            android.util.Log.w(s, s1);
    }

    public static void w(String s, String s1, Throwable throwable)
    {
        if(Constants.isBetaBuild())
            android.util.Log.w(s, s1, throwable);
    }

    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
}
