// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginActivity.java

package com.facebook.katana;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.util.PlatformUtils;

class VersionTasks
{

    private VersionTasks(Context context)
    {
        PackageInfo packageinfo;
        mContext = context;
        packageinfo = null;
        PackageInfo packageinfo1 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        packageinfo = packageinfo1;
_L2:
        if(packageinfo != null)
            mBuildNumber = packageinfo.versionCode;
        else
            mBuildNumber = -1L;
        mPreviouslyRunBuild = KeyValueManager.getLongValue(context, "last_run_build", -1L);
        return;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    /**
     * @deprecated Method getInstance is deprecated
     */

    public static VersionTasks getInstance(Context context)
    {
        com/facebook/katana/VersionTasks;
        JVM INSTR monitorenter ;
        VersionTasks versiontasks;
        if(mSingletonInstance == null)
            mSingletonInstance = new VersionTasks(context);
        versiontasks = mSingletonInstance;
        com/facebook/katana/VersionTasks;
        JVM INSTR monitorexit ;
        return versiontasks;
        Exception exception;
        exception;
        throw exception;
    }

    public void executeUpgrades()
    {
        (new Thread() {

            public void run()
            {
                if(mPreviouslyRunBuild < mBuildNumber)
                {
                    if(mPreviouslyRunBuild == -1L)
                        PlatformUtils.fixContacts(mContext);
                    KeyValueManager.setValue(mContext, "last_run_build", Long.valueOf(mBuildNumber));
                }
            }

            final VersionTasks this$0;

            
            {
                this$0 = VersionTasks.this;
                super();
            }
        }
).start();
    }

    private static final long VERSION_BEFORE_CONTACTS_SYNC_FIX = -1L;
    private static VersionTasks mSingletonInstance;
    private final long mBuildNumber;
    private final Context mContext;
    private final long mPreviouslyRunBuild;



}
