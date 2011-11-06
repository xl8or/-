// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeepLinkUriMap.java

package com.facebook.katana.features;

import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.Map;

// Referenced classes of package com.facebook.katana.features:
//            UriMapClient, DeepLinkUriMap

class DeepLinkUriMapClient extends UriMapClient
{

    DeepLinkUriMapClient()
    {
    }

    public int getCacheTtl(Object obj, UriTemplateMap uritemplatemap)
    {
        return 3600;
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl(obj, (UriTemplateMap)obj1);
    }

    protected Map getDevMappings()
    {
        return DeepLinkUriMap.LOCAL_DEV_MAPPINGS;
    }

    public String getKey(Object obj)
    {
        return "fw:deeplinkurimap";
    }

    public int getPersistentStoreTtl(Object obj, UriTemplateMap uritemplatemap)
    {
        return 3600;
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl(obj, (UriTemplateMap)obj1);
    }

    protected String getProjectMapSetting()
    {
        return "deeplinkurimap";
    }

    protected String getProjectName()
    {
        return "android_deep_links";
    }

    protected String getTag()
    {
        return TAG;
    }

    protected com.facebook.katana.IntentUriHandler.UriHandler getUriHandler(String s)
    {
        return new DeepLinkUriMap.DeepLinkUriHandler(s);
    }

    public static final int MEMORY_TTL = 3600;
    public static final int PERSISTENT_STORE_TTL = 3600;
    public static String TAG = Utils.getClassName(com/facebook/katana/features/DeepLinkUriMapClient);

}
