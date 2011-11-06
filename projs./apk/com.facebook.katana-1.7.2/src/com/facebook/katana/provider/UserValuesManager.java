// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserValuesManager.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;

// Referenced classes of package com.facebook.katana.provider:
//            UserValuesProvider

public class UserValuesManager
{

    private UserValuesManager()
    {
    }

    public static void clearUserValues(Context context)
    {
        delete(context, null, null);
        setRegisterRingtone(context, true);
    }

    public static void delete(Context context, String s, String as[])
    {
        context.getContentResolver().delete(UserValuesProvider.CONTENT_URI, s, as);
    }

    public static boolean getBooleanValue(Context context, String s)
    {
        return Boolean.parseBoolean(getValue(context, s, "false"));
    }

    public static boolean getContactsSyncSetupDisplayed(Context context)
    {
        return getBooleanValue(context, "sync");
    }

    public static String getCurrentAccount(Context context)
    {
        return getValue(context, "current_account", null);
    }

    public static long getLastContactsSync(Context context)
    {
        return Long.parseLong(getValue(context, "last_contacts_sync", "0"));
    }

    public static long getLastSeenId(Context context, String s)
    {
        return Long.parseLong(getValue(context, (new StringBuilder()).append("last_seen_id_").append(s).toString(), "0"));
    }

    public static long getLongValue(Context context, String s, long l)
    {
        String s1 = getValue(context, s, null);
        long l1;
        if(s1 != null)
            l1 = Long.parseLong(s1);
        else
            l1 = l;
        return l1;
    }

    public static boolean getRegisterRingtone(Context context)
    {
        return Boolean.parseBoolean(getValue(context, "ringtone", "false"));
    }

    public static Boolean getTristateValue(Context context, String s)
    {
        String s1 = getValue(context, s, "");
        Boolean boolean1;
        if(s1.length() == 0)
            boolean1 = null;
        else
            boolean1 = Boolean.valueOf(s1);
        return boolean1;
    }

    public static String getValue(Context context, String s, String s1)
    {
        Uri uri = Uri.withAppendedPath(UserValuesProvider.NAME_CONTENT_URI, s);
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

    public static String loadActiveSessionInfo(Context context)
    {
        return getValue(context, "active_session_info", null);
    }

    public static void saveActiveSessionInfo(Context context, String s)
    {
        setValue(context, "active_session_info", s);
    }

    public static void setContactsSyncSetupDisplayed(Context context, boolean flag)
    {
        setValue(context, "sync", Boolean.valueOf(flag));
    }

    public static void setCurrentAccount(Context context, String s)
    {
        setValue(context, "current_account", s);
    }

    public static void setLastContactsSync(Context context)
    {
        setValue(context, "last_contacts_sync", Long.valueOf(System.currentTimeMillis()));
    }

    public static void setLastSeenId(Context context, String s, Long long1)
    {
        setValue(context, (new StringBuilder()).append("last_seen_id_").append(s).toString(), long1);
    }

    public static void setRegisterRingtone(Context context, boolean flag)
    {
        setValue(context, "ringtone", Boolean.valueOf(flag));
    }

    public static void setValue(Context context, String s, Object obj)
    {
        Uri uri = Uri.withAppendedPath(UserValuesProvider.NAME_CONTENT_URI, s);
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
                contentvalues.put("name", s);
                contentresolver.insert(UserValuesProvider.CONTENT_URI, contentvalues);
            }
        }
    }

    private static final String ACTIVE_SESSION_INFO_KEY = "active_session_info";
    private static final String CONTACTS_SYNC_DISPLAYED_KEY = "sync";
    private static final String CURRENT_ACCOUNT_KEY = "current_account";
    private static final String FALSE_VALUE = "false";
    public static final String GATEKEEPER_PREFIX = "gk:";
    private static final String ID_PROJECTION[];
    private static final String LAST_CONTACTS_SYNC_ID = "last_contacts_sync";
    private static final String LAST_SEEN_ID = "last_seen_id";
    public static final String PLACES_HAS_CREATED_PLACE_BEFORE = "places:has_created_place_before";
    public static final String PLACES_LAST_CHECKIN_KEY = "places:last_checkin";
    public static final String PLACES_LAST_CHECKIN_PAGEID_KEY = "places:last_checkin_pageid";
    public static final String PLACES_LAST_CHECKIN_TIME_KEY = "places:last_checkin_time";
    private static final String REGISTER_RINGTONEKEY = "ringtone";
    public static final String USER_SERVER_SETTING_PREFIX = "uss:";
    private static final String VALUE_PROJECTION[];

    static 
    {
        String as[] = new String[1];
        as[0] = "value";
        VALUE_PROJECTION = as;
        String as1[] = new String[1];
        as1[0] = "_id";
        ID_PROJECTION = as1;
    }
}
