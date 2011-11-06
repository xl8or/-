// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRootVersion.java

package com.facebook.katana.webview;

import android.content.Context;
import com.facebook.katana.binding.NetworkRequestCallback;

class MRootVersionClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    MRootVersionClient()
    {
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    public String deserialize(String s)
    {
        return s;
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl(obj, (String)obj1);
    }

    public int getCacheTtl(Object obj, String s)
    {
        return 0x1e13380;
    }

    public String getKey(Object obj)
    {
        return "MRootVersion";
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl(obj, (String)obj1);
    }

    public int getPersistentStoreTtl(Object obj, String s)
    {
        return 0x1e13380;
    }

    public String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        return null;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable(obj, (String)obj1);
    }

    public boolean staleDataAcceptable(Object obj, String s)
    {
        return true;
    }
}
