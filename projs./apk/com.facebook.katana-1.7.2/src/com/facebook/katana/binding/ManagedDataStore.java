// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ManagedDataStore.java

package com.facebook.katana.binding;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.CacheProvider;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.facebook.katana.binding:
//            NetworkRequestCallback

public class ManagedDataStore
    implements NetworkRequestCallback
{
    protected static class InternalStore
    {

        Object data;
        public final long timestamp;

        InternalStore(Object obj, long l)
        {
            data = obj;
            timestamp = l;
        }
    }

    public static interface Client
    {

        public abstract Object deserialize(String s);

        public abstract int getCacheTtl(Object obj, Object obj1);

        public abstract String getKey(Object obj);

        public abstract int getPersistentStoreTtl(Object obj, Object obj1);

        public abstract String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback);

        public abstract boolean staleDataAcceptable(Object obj, Object obj1);
    }


    public ManagedDataStore(Client client)
    {
        mClient = client;
        mData = new HashMap();
        com/facebook/katana/binding/ManagedDataStore;
        JVM INSTR monitorenter ;
        mCacheGenerationId = globalGenerationId;
        return;
    }

    static void dbSetValue(Context context, String s, String s1, long l)
    {
        Uri uri = Uri.withAppendedPath(CacheProvider.NAME_CONTENT_URI, Uri.encode(s));
        ContentResolver contentresolver = context.getContentResolver();
        String as[] = new String[1];
        as[0] = "_id";
        Cursor cursor = contentresolver.query(uri, as, null, null, null);
        if(cursor != null)
        {
            boolean flag = cursor.moveToFirst();
            cursor.close();
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("value", s1);
            contentvalues.put("timestamp", Long.valueOf(l));
            if(flag)
            {
                contentresolver.update(uri, contentvalues, null, null);
            } else
            {
                contentvalues.put("name", s);
                contentresolver.insert(CacheProvider.CONTENT_URI, contentvalues);
            }
        }
    }

    public static void invalidateAllManagedDataStores()
    {
        com/facebook/katana/binding/ManagedDataStore;
        JVM INSTR monitorenter ;
        globalGenerationId = 1 + globalGenerationId;
        return;
    }

    public void callback(Context context, boolean flag, Object obj, String s, Object obj1, Object obj2)
    {
        if(flag) goto _L2; else goto _L1
_L1:
        return;
_L2:
        long l;
        l = System.currentTimeMillis();
        if(mClient.getPersistentStoreTtl(obj, obj1) > 0)
            dbSetValue(context, mClient.getKey(obj), s, l);
        if(mClient.getCacheTtl(obj, obj1) <= 0)
            continue; /* Loop/switch isn't completed */
        InternalStore internalstore = new InternalStore(obj1, l);
        this;
        JVM INSTR monitorenter ;
        mData.put(obj, internalstore);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public Object get(Context context, Object obj)
    {
        Object obj1 = null;
        com/facebook/katana/binding/ManagedDataStore;
        JVM INSTR monitorenter ;
        this;
        JVM INSTR monitorenter ;
        if(mCacheGenerationId != globalGenerationId)
        {
            mData = new HashMap();
            mCacheGenerationId = globalGenerationId;
        }
        this;
        JVM INSTR monitorexit ;
        com/facebook/katana/binding/ManagedDataStore;
        JVM INSTR monitorexit ;
        this;
        JVM INSTR monitorenter ;
        InternalStore internalstore = (InternalStore)mData.get(obj);
        if(internalstore == null) goto _L2; else goto _L1
_L1:
        obj1 = internalstore.data;
        if(System.currentTimeMillis() >= internalstore.timestamp + (long)(1000 * mClient.getCacheTtl(obj, internalstore.data))) goto _L4; else goto _L3
_L3:
        this;
        JVM INSTR monitorexit ;
        Object obj2 = obj1;
_L5:
        return obj2;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        throw exception;
_L4:
        if(!mClient.staleDataAcceptable(obj, obj1))
        {
            mData.remove(obj);
            obj1 = null;
        }
_L2:
        this;
        JVM INSTR monitorexit ;
        Uri uri;
        Cursor cursor;
        String s = mClient.getKey(obj);
        uri = Uri.withAppendedPath(CacheProvider.NAME_CONTENT_URI, Uri.encode(s));
        cursor = context.getContentResolver().query(uri, PROJECTION, null, null, null);
        if(cursor == null)
            break MISSING_BLOCK_LABEL_404;
        long l;
        Object obj3;
        if(!cursor.moveToFirst())
            break MISSING_BLOCK_LABEL_404;
        String s1 = cursor.getString(0);
        l = cursor.getLong(1);
        obj3 = mClient.deserialize(s1);
        if(System.currentTimeMillis() >= l + (long)(1000 * mClient.getPersistentStoreTtl(obj, obj3)))
            break MISSING_BLOCK_LABEL_344;
        this;
        JVM INSTR monitorenter ;
        Map map = mData;
        InternalStore internalstore1 = new InternalStore(obj3, l);
        map.put(obj, internalstore1);
        this;
        JVM INSTR monitorexit ;
        cursor.close();
        obj2 = obj3;
          goto _L5
        Exception exception2;
        exception2;
        this;
        JVM INSTR monitorexit ;
        throw exception2;
        Exception exception4;
        exception4;
        this;
        JVM INSTR monitorexit ;
        throw exception4;
        Exception exception3;
        exception3;
        cursor.close();
        throw exception3;
        if(mClient.staleDataAcceptable(obj, obj3)) goto _L7; else goto _L6
_L6:
        context.getContentResolver().delete(uri, null, null);
        obj2 = obj1;
_L8:
        mClient.initiateNetworkRequest(context, obj, this);
        cursor.close();
          goto _L5
_L7:
        obj2 = obj3;
          goto _L8
        obj2 = obj1;
          goto _L8
    }

    public void resetMemoryStore(Context context)
    {
        this;
        JVM INSTR monitorenter ;
        mData = new HashMap();
        return;
    }

    private static final String PROJECTION[];
    protected static int globalGenerationId = 0;
    protected int mCacheGenerationId;
    protected final Client mClient;
    protected Map mData;

    static 
    {
        String as[] = new String[2];
        as[0] = "value";
        as[1] = "timestamp";
        PROJECTION = as;
    }
}
