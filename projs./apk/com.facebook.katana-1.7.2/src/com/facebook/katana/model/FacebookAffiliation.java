// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookAffiliation.java

package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.provider.KeyValueProvider;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Toaster;
import java.util.concurrent.atomic.AtomicBoolean;

public class FacebookAffiliation
{
    private static interface FacebookAffiliationQuery
    {

        public static final String PREDICATE = "key IN(\"is_employee\",\"seen_employee\")";
    }


    private FacebookAffiliation()
    {
    }

    public static boolean hasEmployeeEverLoggedInOnThisPhone()
    {
        boolean flag;
        if(mFacebookEmployeeEverAccurate && mFacebookEmployeeEver || mFacebookEmployeeAccurate && mFacebookEmployee)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static boolean hasEmployeeNeverLoggedInOnThisPhone()
    {
        boolean flag;
        if(mFacebookEmployeeEverAccurate && !mFacebookEmployeeEver && mFacebookEmployeeAccurate && !mFacebookEmployee)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void invalidateEmployeeBit(Context context)
    {
        Log.v("FacebookAffiliation", "employee accurate bit cleared");
        mFacebookEmployeeAccurate = false;
        mSuppressToast = false;
        KeyValueManager.delete(context, "key=\"is_employee\"", null);
    }

    public static boolean isCurrentUserEmployee()
    {
        boolean flag;
        if(mFacebookEmployeeAccurate && mFacebookEmployee)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void maybeToast(Context context)
    {
        if(!mSuppressToast)
        {
            Toaster.toast(context.getApplicationContext(), "This beta build is only enabled for employees and authorized users.");
            mSuppressToast = true;
        }
    }

    public static void requestCompleted()
    {
        mRequestLock.set(false);
    }

    public static void setIsEmployee(Context context, boolean flag)
    {
        Log.v("FacebookAffiliation", (new StringBuilder()).append("employee bit set to ").append(flag).toString());
        mFacebookEmployee = flag;
        boolean flag1;
        if(mFacebookEmployeeEverAccurate && mFacebookEmployeeEver || flag)
            flag1 = true;
        else
            flag1 = false;
        mFacebookEmployeeEver = flag1;
        mFacebookEmployeeAccurate = true;
        mFacebookEmployeeEverAccurate = true;
        KeyValueManager.setValue(context, "is_employee", Boolean.valueOf(mFacebookEmployee));
        KeyValueManager.setValue(context, "seen_employee", Boolean.valueOf(mFacebookEmployeeEver));
        mStatusSynced = true;
    }

    public static boolean shouldInitiateRequest()
    {
        boolean flag;
        if((!mFacebookEmployeeAccurate || !mFacebookEmployeeEverAccurate) && mRequestLock.compareAndSet(false, true))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static void syncStatus(Context context)
    {
        Cursor cursor = context.getContentResolver().query(KeyValueProvider.CONTENT_URI, KeyValueManager.PROJECTION, "key IN(\"is_employee\",\"seen_employee\")", null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) 
            {
                String s = cursor.getString(0);
                String s1 = cursor.getString(1);
                if(s.equals("is_employee"))
                {
                    mFacebookEmployee = Boolean.parseBoolean(s1);
                    mFacebookEmployeeAccurate = true;
                } else
                if(s.equals("seen_employee"))
                {
                    mFacebookEmployeeEver = Boolean.parseBoolean(s1);
                    mFacebookEmployeeEverAccurate = true;
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        mStatusSynced = true;
    }

    public static boolean synced()
    {
        return mStatusSynced;
    }

    private static final String TAG = "FacebookAffiliation";
    protected static boolean mFacebookEmployee;
    protected static boolean mFacebookEmployeeAccurate;
    protected static boolean mFacebookEmployeeEver;
    protected static boolean mFacebookEmployeeEverAccurate;
    protected static AtomicBoolean mRequestLock = new AtomicBoolean(false);
    protected static boolean mStatusSynced;
    protected static boolean mSuppressToast;

}
