// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesUserSettings.java

package com.facebook.katana.features.places;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.FqlGetUserServerSettings;

class PlacesUserSettingsClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    PlacesUserSettingsClient()
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
        return getCacheTtl((String)obj, (String)obj1);
    }

    public int getCacheTtl(String s, String s1)
    {
        return 3600;
    }

    public volatile String getKey(Object obj)
    {
        return getKey((String)obj);
    }

    public String getKey(String s)
    {
        return (new StringBuilder()).append("uss:").append(s).toString();
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl((String)obj, (String)obj1);
    }

    public int getPersistentStoreTtl(String s, String s1)
    {
        int i;
        if("places_opt_in".equals(s) && "default".equals(s1))
            i = 0;
        else
            i = 0x1e13380;
        return i;
    }

    public volatile String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        return initiateNetworkRequest(context, (String)obj, networkrequestcallback);
    }

    public String initiateNetworkRequest(Context context, String s, NetworkRequestCallback networkrequestcallback)
    {
        return FqlGetUserServerSettings.RequestSettingsByProjectSetting(AppSession.getActiveSession(context, false), context, "places", s, networkrequestcallback);
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable((String)obj, (String)obj1);
    }

    public boolean staleDataAcceptable(String s, String s1)
    {
        return true;
    }
}
