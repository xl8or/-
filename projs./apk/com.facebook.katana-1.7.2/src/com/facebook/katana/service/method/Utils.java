// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utils.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;

class Utils
{

    Utils()
    {
    }

    public static String buildSnippet(String s)
    {
        String s1;
        if(s.length() > 60)
            s1 = s.substring(0, 60);
        else
            s1 = s;
        return s1.replaceAll("\n", " ");
    }

    public static void setAlbumsSize(Context context, String s, int i)
    {
        ContentResolver contentresolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, s);
        Cursor cursor = contentresolver.query(uri, ALBUM_PROJECTION, null, null, null);
        if(cursor.moveToFirst() && cursor.getInt(0) != i)
        {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("size", Integer.valueOf(i));
            contentresolver.update(uri, contentvalues, null, null);
        }
        cursor.close();
    }

    private static final String ALBUM_PROJECTION[];
    private static final int SNIPPET_LENGTH = 60;

    static 
    {
        String as[] = new String[1];
        as[0] = "size";
        ALBUM_PROJECTION = as;
    }
}
