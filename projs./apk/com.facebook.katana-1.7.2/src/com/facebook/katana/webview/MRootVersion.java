// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRootVersion.java

package com.facebook.katana.webview;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.Utils;

// Referenced classes of package com.facebook.katana.webview:
//            MRoot, MRootVersionClient

public class MRootVersion
{

    public MRootVersion()
    {
    }

    public static String get(Context context)
    {
        return (String)getStore().get(context, "MRootVersion");
    }

    protected static ManagedDataStore getStore()
    {
        if(store == null)
            store = new ManagedDataStore(new MRootVersionClient());
        return store;
    }

    public static void set(Context context, String s)
    {
        getStore().callback(context, true, "MRootVersion", s, s, null);
    }

    public static final String KEY = "MRootVersion";
    public static final String TAG = Utils.getClassName(com/facebook/katana/webview/MRoot);
    protected static ManagedDataStore store;

}
