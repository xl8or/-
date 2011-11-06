// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MinorStatus.java

package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;

// Referenced classes of package com.facebook.katana.features.composer:
//            MinorStatusClient

public class MinorStatus
{

    public MinorStatus()
    {
    }

    public static Boolean get(Context context)
    {
        return (Boolean)getStore().get(context, null);
    }

    /**
     * @deprecated Method getStore is deprecated
     */

    protected static ManagedDataStore getStore()
    {
        com/facebook/katana/features/composer/MinorStatus;
        JVM INSTR monitorenter ;
        ManagedDataStore manageddatastore;
        if(store == null)
            store = new ManagedDataStore(new MinorStatusClient());
        manageddatastore = store;
        com/facebook/katana/features/composer/MinorStatus;
        JVM INSTR monitorexit ;
        return manageddatastore;
        Exception exception;
        exception;
        throw exception;
    }

    protected static ManagedDataStore store;
}
