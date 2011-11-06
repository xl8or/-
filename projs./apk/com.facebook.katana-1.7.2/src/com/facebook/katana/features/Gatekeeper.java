// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Gatekeeper.java

package com.facebook.katana.features;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.Utils;
import java.util.*;

// Referenced classes of package com.facebook.katana.features:
//            GkManagedStoreClient

public class Gatekeeper
{
    public static class Settings
    {

        public final CachePolicy memoryCachePolicy;
        public final CachePolicy persistentCachePolicy;

        public Settings(CachePolicy cachepolicy, CachePolicy cachepolicy1)
        {
            memoryCachePolicy = cachepolicy;
            persistentCachePolicy = cachepolicy1;
        }
    }

    public static final class CachePolicy extends Enum
    {

        public static CachePolicy valueOf(String s)
        {
            return (CachePolicy)Enum.valueOf(com/facebook/katana/features/Gatekeeper$CachePolicy, s);
        }

        public static CachePolicy[] values()
        {
            return (CachePolicy[])$VALUES.clone();
        }

        private static final CachePolicy $VALUES[];
        public static final CachePolicy CACHE_1HOUR;
        public static final CachePolicy CACHE_ALL;
        public static final CachePolicy CACHE_NEGATIVE;
        public static final CachePolicy CACHE_NONE;
        public static final CachePolicy CACHE_POSITIVE;
        public final boolean cacheIfFalse;
        public final boolean cacheIfTrue;
        public final int fallbackTtl;

        static 
        {
            CACHE_ALL = new CachePolicy("CACHE_ALL", 0, true, true);
            CACHE_POSITIVE = new CachePolicy("CACHE_POSITIVE", 1, true, false);
            CACHE_NEGATIVE = new CachePolicy("CACHE_NEGATIVE", 2, false, true);
            CACHE_NONE = new CachePolicy("CACHE_NONE", 3, false, false);
            CACHE_1HOUR = new CachePolicy("CACHE_1HOUR", 4, 3600);
            CachePolicy acachepolicy[] = new CachePolicy[5];
            acachepolicy[0] = CACHE_ALL;
            acachepolicy[1] = CACHE_POSITIVE;
            acachepolicy[2] = CACHE_NEGATIVE;
            acachepolicy[3] = CACHE_NONE;
            acachepolicy[4] = CACHE_1HOUR;
            $VALUES = acachepolicy;
        }

        private CachePolicy(String s, int i, int j)
        {
            super(s, i);
            cacheIfTrue = false;
            cacheIfFalse = false;
            fallbackTtl = j;
        }

        private CachePolicy(String s, int i, boolean flag, boolean flag1)
        {
            super(s, i);
            cacheIfTrue = flag;
            cacheIfFalse = flag1;
            fallbackTtl = 0;
        }
    }


    public Gatekeeper()
    {
    }

    public static void cachePrefill(Context context, Map map)
    {
        ManagedDataStore manageddatastore = getStore();
        java.util.Map.Entry entry;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); manageddatastore.callback(context, true, entry.getKey(), ((Boolean)entry.getValue()).toString(), entry.getValue(), null))
            entry = (java.util.Map.Entry)iterator.next();

    }

    public static Boolean get(Context context, String s)
    {
        return (Boolean)getStore().get(context, s);
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new GkManagedStoreClient());
        return store;
    }

    public static void reset()
    {
        store = null;
    }

    public static final Map GATEKEEPER_PROJECTS = Collections.unmodifiableMap(new HashMap() {

            
            {
                put("places", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_POSITIVE));
                put("android_beta", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_NONE));
                put("android_ci_legal_screen", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_POSITIVE));
                put("android_ci_legal_bar", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_POSITIVE));
                put("android_ci_kddi_intro_enabled", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_POSITIVE));
                put("android_ci_alert_enabled", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_POSITIVE));
                put("faceweb_android", new Settings(CachePolicy.CACHE_1HOUR, CachePolicy.CACHE_1HOUR));
                put("android_deep_links", new Settings(CachePolicy.CACHE_1HOUR, CachePolicy.CACHE_1HOUR));
                put("meta_composer", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_POSITIVE));
                put("android_fw_ssl", new Settings(CachePolicy.CACHE_ALL, CachePolicy.CACHE_1HOUR));
            }
    }
);
    public static final String TAG = Utils.getClassName(com/facebook/katana/features/Gatekeeper);
    protected static ManagedDataStore store;

}
