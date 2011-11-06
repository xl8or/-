// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Gatekeeper.java

package com.facebook.katana.features;

import android.content.Context;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.FqlGetGatekeeperSettings;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Utils;
import java.util.Map;

// Referenced classes of package com.facebook.katana.features:
//            Gatekeeper

class GkManagedStoreClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    GkManagedStoreClient()
    {
    }

    public Boolean deserialize(String s)
    {
        return Boolean.valueOf(s);
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl((String)obj, (Boolean)obj1);
    }

    public int getCacheTtl(String s, Boolean boolean1)
    {
        Gatekeeper.Settings settings = (Gatekeeper.Settings)Gatekeeper.GATEKEEPER_PROJECTS.get(s);
        int i;
        if(settings == null)
        {
            Log.e(TAG, (new StringBuilder()).append("received a request for an unknown project: ").append(s).toString());
            i = 0;
        } else
        {
            i = mapPolicyToTtl(settings.memoryCachePolicy, boolean1);
        }
        return i;
    }

    public volatile String getKey(Object obj)
    {
        return getKey((String)obj);
    }

    public String getKey(String s)
    {
        return (new StringBuilder()).append("gk:").append(s).toString();
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl((String)obj, (Boolean)obj1);
    }

    public int getPersistentStoreTtl(String s, Boolean boolean1)
    {
        Gatekeeper.Settings settings = (Gatekeeper.Settings)Gatekeeper.GATEKEEPER_PROJECTS.get(s);
        int i;
        if(settings == null)
        {
            Log.e(TAG, (new StringBuilder()).append("received a request for an unknown project: ").append(s).toString());
            i = 0;
        } else
        {
            i = mapPolicyToTtl(settings.persistentCachePolicy, boolean1);
        }
        return i;
    }

    public volatile String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        return initiateNetworkRequest(context, (String)obj, networkrequestcallback);
    }

    public String initiateNetworkRequest(Context context, String s, NetworkRequestCallback networkrequestcallback)
    {
        return FqlGetGatekeeperSettings.Get(context, s, networkrequestcallback);
    }

    protected int mapPolicyToTtl(Gatekeeper.CachePolicy cachepolicy, Boolean boolean1)
    {
        int i;
        if(cachepolicy.cacheIfTrue && boolean1.booleanValue() || cachepolicy.cacheIfFalse && !boolean1.booleanValue())
            i = 0x1e13380;
        else
            i = cachepolicy.fallbackTtl;
        return i;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable((String)obj, (Boolean)obj1);
    }

    public boolean staleDataAcceptable(String s, Boolean boolean1)
    {
        return true;
    }

    public static final String TAG = Utils.getClassName(com/facebook/katana/features/Gatekeeper);

}
