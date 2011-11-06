// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KeyValueManager.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;

// Referenced classes of package com.facebook.katana.provider:
//            KeyValueProvider

public class KeyValueManager
{

    private KeyValueManager()
    {
    }

    public static void delete(Context context, String s, String as[])
    {
        context.getContentResolver().delete(KeyValueProvider.CONTENT_URI, s, as);
    }

    public static boolean getBooleanValue(Context context, String s)
    {
        return Boolean.parseBoolean(getValue(context, s, "false"));
    }

    public static long getLongValue(Context context, String s, long l)
    {
        return Long.parseLong(getValue(context, s, String.valueOf(l)));
    }

    public static String getValue(Context context, String s, String s1)
    {
        Uri uri = Uri.withAppendedPath(KeyValueProvider.KEY_CONTENT_URI, s);
        Cursor cursor = context.getContentResolver().query(uri, VALUE_PROJECTION, null, null, null);
        String s2 = s1;
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                s2 = cursor.getString(0);
            cursor.close();
        }
        return s2;
    }

    public static void setValue(Context context, String s, Object obj)
    {
        Uri uri = Uri.withAppendedPath(KeyValueProvider.KEY_CONTENT_URI, s);
        ContentResolver contentresolver = context.getContentResolver();
        Cursor cursor = contentresolver.query(uri, ID_PROJECTION, null, null, null);
        if(cursor != null)
        {
            boolean flag = cursor.moveToFirst();
            cursor.close();
            ContentValues contentvalues = new ContentValues();
            String s1;
            if(obj == null)
                s1 = null;
            else
                s1 = obj.toString();
            contentvalues.put("value", s1);
            if(flag)
            {
                contentresolver.update(uri, contentvalues, null, null);
            } else
            {
                contentvalues.put("key", s);
                contentresolver.insert(KeyValueProvider.CONTENT_URI, contentvalues);
            }
        }
    }

    public static final String FACEBOOK_EMPLOYEE_EVER_KEY = "seen_employee";
    public static final String FACEBOOK_EMPLOYEE_KEY = "is_employee";
    private static final String FALSE_VALUE = "false";
    private static final String ID_PROJECTION[];
    public static final int INDEX_PROP_KEY = 0;
    public static final int INDEX_PROP_VALUE = 1;
    public static final String LAST_RUN_BUILD = "last_run_build";
    public static final String LAST_USERNAME = "last_username";
    public static final String PROJECTION[];
    private static final String VALUE_PROJECTION[];

    static 
    {
        String as[] = new String[2];
        as[0] = "key";
        as[1] = "value";
        PROJECTION = as;
        String as1[] = new String[1];
        as1[0] = "value";
        VALUE_PROJECTION = as1;
        String as2[] = new String[1];
        as2[0] = "_id";
        ID_PROJECTION = as2;
    }
}
