// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookApp.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookApp extends JMCachingDictDestination
{

    private FacebookApp()
    {
        mAppId = -1L;
        mName = null;
        mImageUrl = null;
    }

    public FacebookApp(long l, String s, String s1)
    {
        mAppId = l;
        mName = s;
        mImageUrl = s1;
    }

    public static final int INVALID_ID = -1;
    public final long mAppId;
    public final String mImageUrl;
    public final String mName;
}
