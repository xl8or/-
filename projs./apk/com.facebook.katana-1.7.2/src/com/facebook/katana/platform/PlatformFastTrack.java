// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlatformFastTrack.java

package com.facebook.katana.platform;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.QuickContactBadge;

public class PlatformFastTrack
{

    public PlatformFastTrack()
    {
    }

    protected static Uri getContactLookupUri(ContentResolver contentresolver, String s, long l)
    {
        Cursor cursor;
        Uri uri = android.provider.ContactsContract.Data.CONTENT_URI;
        String as[] = new String[2];
        as[0] = "contact_id";
        as[1] = "lookup";
        String as1[] = new String[2];
        as1[0] = s;
        as1[1] = String.valueOf(l);
        cursor = contentresolver.query(uri, as, "account_type='com.facebook.auth.login' AND account_name=? AND sourceid=?", as1, null);
        if(cursor == null) goto _L2; else goto _L1
_L1:
        if(!cursor.moveToFirst()) goto _L2; else goto _L3
_L3:
        Uri uri2 = android.provider.ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
        Uri uri1;
        uri1 = uri2;
        if(cursor != null)
            cursor.close();
_L5:
        return uri1;
_L2:
        if(cursor != null)
            cursor.close();
        uri1 = null;
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception;
        exception;
        if(cursor != null)
            cursor.close();
        throw exception;
    }

    public static void prepareBadge(View view, String s, long l, String as[])
    {
        QuickContactBadge quickcontactbadge = (QuickContactBadge)view;
        quickcontactbadge.assignContactUri(getContactLookupUri(view.getContext().getContentResolver(), s, l));
        if(as.length > 0)
            quickcontactbadge.setExcludeMimes(as);
    }

    private static final String SELECTION = "account_type='com.facebook.auth.login' AND account_name=? AND sourceid=?";
}
