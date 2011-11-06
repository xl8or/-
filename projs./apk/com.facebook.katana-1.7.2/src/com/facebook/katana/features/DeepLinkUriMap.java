// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeepLinkUriMap.java

package com.facebook.katana.features;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.*;

// Referenced classes of package com.facebook.katana.features:
//            DeepLinkUriMapClient

public class DeepLinkUriMap
{
    public static class DeepLinkUriHandler
        implements com.facebook.katana.IntentUriHandler.UriHandler
    {

        public Intent handle(Context context, Bundle bundle)
        {
            String s = uriTemplate;
            for(Iterator iterator = bundle.keySet().iterator(); iterator.hasNext();)
            {
                String s1 = (String)iterator.next();
                s = s.replaceAll((new StringBuilder()).append("<").append(s1).append(">").toString(), Utils.getStringValue(bundle, s1));
            }

            return IntentUriHandler.getIntentForUri(context, s);
        }

        public final String uriTemplate;

        public DeepLinkUriHandler(String s)
        {
            uriTemplate = s;
        }
    }


    public DeepLinkUriMap()
    {
    }

    public static UriTemplateMap get(Context context)
    {
        return (UriTemplateMap)getStore().get(context, null);
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new DeepLinkUriMapClient());
        return store;
    }

    public static final Map LOCAL_DEV_MAPPINGS = new LinkedHashMap() {

    }
;
    protected static ManagedDataStore store;

}
