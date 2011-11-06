// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRoot.java

package com.facebook.katana.webview;

import android.content.Context;
import android.os.Handler;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.util.Tuple;
import java.io.IOException;
import org.json.*;

// Referenced classes of package com.facebook.katana.webview:
//            MRootFetcher

class MRootClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    MRootClient()
    {
    }

    public Tuple deserialize(String s)
    {
        JSONTokener jsontokener = new JSONTokener(s);
        Object obj = jsontokener.nextValue();
        if(!(obj instanceof JSONArray)) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray = (JSONArray)obj;
        if(jsonarray.length() != 2) goto _L2; else goto _L3
_L3:
        Tuple tuple = new Tuple(jsonarray.getString(0), jsonarray.getString(1));
_L5:
        return tuple;
        JSONException jsonexception;
        jsonexception;
_L2:
        tuple = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    public int getCacheTtl(Tuple tuple, Tuple tuple1)
    {
        return 3600;
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl((Tuple)obj, (Tuple)obj1);
    }

    public String getKey(Tuple tuple)
    {
        Object aobj[] = new Object[3];
        aobj[0] = "MRoot:";
        aobj[1] = tuple.d0;
        aobj[2] = tuple.d1;
        return String.format("%s<%s,%s>", aobj);
    }

    public volatile String getKey(Object obj)
    {
        return getKey((Tuple)obj);
    }

    public int getPersistentStoreTtl(Tuple tuple, Tuple tuple1)
    {
        return 3600;
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl((Tuple)obj, (Tuple)obj1);
    }

    public String initiateNetworkRequest(Context context, Tuple tuple, NetworkRequestCallback networkrequestcallback)
    {
        try
        {
            (new MRootFetcher(context, tuple, new Handler(), networkrequestcallback)).start();
        }
        catch(IOException ioexception)
        {
            networkrequestcallback.callback(context, false, tuple, null, null, new Tuple(MRoot.LoadError.UNKNOWN_ERROR, null));
        }
        return null;
    }

    public volatile String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        return initiateNetworkRequest(context, (Tuple)obj, networkrequestcallback);
    }

    public boolean staleDataAcceptable(Tuple tuple, Tuple tuple1)
    {
        return true;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable((Tuple)obj, (Tuple)obj1);
    }
}
