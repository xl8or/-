// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacewebUriMap.java

package com.facebook.katana.features.faceweb;

import com.facebook.katana.features.UriMapClient;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.Map;

// Referenced classes of package com.facebook.katana.features.faceweb:
//            FacewebUriMap

class FacewebUriMapClient extends UriMapClient
{

    FacewebUriMapClient()
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
        return FacewebUriMap.LOCAL_DEV_MAPPINGS;
    }

    public String getKey(Object obj)
    {
        return "fw:urimap";
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
        return "urimap";
    }

    protected String getProjectName()
    {
        return "android_faceweb";
    }

    protected String getTag()
    {
        return TAG;
    }

    protected com.facebook.katana.IntentUriHandler.UriHandler getUriHandler(String s)
    {
        return new FacewebUriMap.FacewebUriHandler(s);
    }

    public static final int MEMORY_TTL = 3600;
    public static final int PERSISTENT_STORE_TTL = 3600;
    public static String TAG = Utils.getClassName(com/facebook/katana/features/faceweb/FacewebUriMapClient);

}
