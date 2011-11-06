// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRoot.java

package com.facebook.katana.webview;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.Tuple;
import java.util.*;

// Referenced classes of package com.facebook.katana.webview:
//            MRootClient

class MRootDataStore extends ManagedDataStore
{

    public MRootDataStore()
    {
        super(new MRootClient());
        mListeners = new HashMap();
    }

    public void callback(Context context, boolean flag, Tuple tuple, String s, Tuple tuple1, Tuple tuple2)
    {
        super.callback(context, flag, tuple, s, tuple1, tuple2);
        this;
        JVM INSTR monitorenter ;
        List list = (List)mListeners.remove(tuple);
        if(list != null)
        {
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                MRoot.Listener listener = (MRoot.Listener)iterator.next();
                if(flag)
                    listener.onRootLoaded(tuple1);
                else
                    listener.onRootError(tuple2);
            }

        }
        return;
    }

    public volatile void callback(Context context, boolean flag, Object obj, String s, Object obj1, Object obj2)
    {
        callback(context, flag, (Tuple)obj, s, (Tuple)obj1, (Tuple)obj2);
    }

    public void clearOldEntries(Context context, int i)
    {
        long l = System.currentTimeMillis() / 1000L - (long)i;
        this;
        JVM INSTR monitorenter ;
        Iterator iterator = mData.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            if(((com.facebook.katana.binding.ManagedDataStore.InternalStore)((java.util.Map.Entry)iterator.next()).getValue()).timestamp < l)
                iterator.remove();
        } while(true);
        break MISSING_BLOCK_LABEL_82;
        Exception exception;
        exception;
        throw exception;
        this;
        JVM INSTR monitorexit ;
    }

    public Tuple get(Context context, Tuple tuple)
    {
        throw new UnsupportedOperationException("don't call me directly");
    }

    public Tuple get(Context context, Tuple tuple, MRoot.Listener listener)
    {
        this;
        JVM INSTR monitorenter ;
        List list = (List)mListeners.get(tuple);
        Tuple tuple2;
        if(list != null)
        {
            list.add(listener);
            tuple2 = null;
        } else
        {
            ArrayList arraylist = new ArrayList();
            arraylist.add(listener);
            mListeners.put(tuple, arraylist);
            Tuple tuple1 = (Tuple)super.get(context, tuple);
            if(tuple1 != null)
            {
                mListeners.remove(tuple);
                tuple2 = tuple1;
            } else
            {
                tuple2 = null;
            }
        }
        return tuple2;
    }

    public volatile Object get(Context context, Object obj)
    {
        return get(context, (Tuple)obj);
    }

    protected Map mListeners;
}
