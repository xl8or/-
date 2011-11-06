// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRoot.java

package com.facebook.katana.webview;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import com.facebook.katana.provider.CacheProvider;
import com.facebook.katana.util.Tuple;

// Referenced classes of package com.facebook.katana.webview:
//            MRootDataStore

public class MRoot
{
    public static interface Listener
    {

        public abstract void onRootError(Tuple tuple);

        public abstract void onRootLoaded(Tuple tuple);
    }

    public static final class LoadError extends Enum
    {

        public static LoadError valueOf(String s)
        {
            return (LoadError)Enum.valueOf(com/facebook/katana/webview/MRoot$LoadError, s);
        }

        public static LoadError[] values()
        {
            return (LoadError[])$VALUES.clone();
        }

        private static final LoadError $VALUES[];
        public static final LoadError NETWORK_ERROR;
        public static final LoadError UNEXPECTED_REDIRECT;
        public static final LoadError UNKNOWN_ERROR;

        static 
        {
            UNEXPECTED_REDIRECT = new LoadError("UNEXPECTED_REDIRECT", 0);
            NETWORK_ERROR = new LoadError("NETWORK_ERROR", 1);
            UNKNOWN_ERROR = new LoadError("UNKNOWN_ERROR", 2);
            LoadError aloaderror[] = new LoadError[3];
            aloaderror[0] = UNEXPECTED_REDIRECT;
            aloaderror[1] = NETWORK_ERROR;
            aloaderror[2] = UNKNOWN_ERROR;
            $VALUES = aloaderror;
        }

        private LoadError(String s, int i)
        {
            super(s, i);
        }
    }


    public MRoot()
    {
    }

    public static void clearOldEntries(Context context)
    {
        getStore().clearOldEntries(context, 3600);
        Uri uri = Uri.withAppendedPath(Uri.withAppendedPath(CacheProvider.SWEEP_PREFIX_CONTENT_URI, Uri.encode("MRoot:")), String.valueOf(0x127500));
        context.getContentResolver().delete(uri, null, null);
    }

    public static Tuple get(Context context, Tuple tuple, Listener listener)
    {
        return getStore().get(context, tuple, listener);
    }

    protected static MRootDataStore getStore()
    {
        if(store == null)
            store = new MRootDataStore();
        return store;
    }

    public static void reset(Context context)
    {
        getStore().resetMemoryStore(context);
        Uri uri = Uri.withAppendedPath(CacheProvider.PREFIX_CONTENT_URI, Uri.encode("MRoot:"));
        context.getContentResolver().delete(uri, null, null);
    }

    public static final String KEY_PREFIX = "MRoot:";
    public static final int SWEEP_TTL = 0x127500;
    public static final String TAG = com/facebook/katana/webview/MRoot.getName();
    public static final int TTL = 3600;
    protected static MRootDataStore store;

}
