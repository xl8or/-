// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacewebUriMap.java

package com.facebook.katana.features.faceweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.activity.faceweb.FacewebChromeActivity;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.*;

// Referenced classes of package com.facebook.katana.features.faceweb:
//            FacewebUriMapClient

public class FacewebUriMap
{
    public static class FacewebUriHandler
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

            Intent intent = new Intent(context, com/facebook/katana/activity/faceweb/FacewebChromeActivity);
            intent.putExtra("mobile_page", s);
            return intent;
        }

        public final String uriTemplate;

        public FacewebUriHandler(String s)
        {
            uriTemplate = s;
        }
    }


    public FacewebUriMap()
    {
    }

    public static UriTemplateMap get(Context context)
    {
        return (UriTemplateMap)getStore().get(context, null);
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new FacewebUriMapClient());
        return store;
    }

    public static final Map LOCAL_DEV_MAPPINGS = new LinkedHashMap() {

    }
;
    protected static ManagedDataStore store;

}
