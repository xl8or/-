// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RingtoneUtils.java

package com.facebook.katana.util;

import android.content.*;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import java.io.*;

public class RingtoneUtils
{

    public RingtoneUtils()
    {
    }

    public static Uri createFacebookRingtone(Context context)
        throws IOException
    {
        File file;
        Cursor cursor;
        file = new File("/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a");
        if(!file.exists())
        {
            InputStream inputstream = context.getAssets().open("pop.m4a");
            byte abyte0[] = new byte[inputstream.available()];
            inputstream.read(abyte0);
            inputstream.close();
            (new File("/sdcard/media/audio/notifications")).mkdirs();
            FileOutputStream fileoutputstream = new FileOutputStream("/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a");
            fileoutputstream.write(abyte0);
            fileoutputstream.flush();
            fileoutputstream.close();
        }
        ContentResolver contentresolver = context.getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String as[] = new String[1];
        as[0] = "/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a";
        cursor = contentresolver.query(uri, null, "_data=?", as, null);
        if(cursor == null)
            break MISSING_BLOCK_LABEL_260;
        if(!cursor.moveToFirst()) goto _L2; else goto _L1
_L1:
        int i = cursor.getColumnIndexOrThrow("_id");
_L4:
        Uri uri1;
        Uri uri2;
        uri2 = Uri.withAppendedPath(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, (new StringBuilder()).append("").append(cursor.getInt(i)).toString());
        if(uri2 == null)
            continue; /* Loop/switch isn't completed */
        uri1 = uri2;
_L5:
        cursor.close();
_L6:
        if(uri1 == null)
        {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
        }
        return uri1;
        if(cursor.moveToNext()) goto _L4; else goto _L3
_L3:
        uri1 = uri2;
          goto _L5
_L2:
        uri1 = null;
          goto _L5
        uri1 = null;
          goto _L6
    }

    public static void deleteFacebookRingtone(Context context)
    {
        ContentResolver contentresolver = context.getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String as[] = new String[1];
        as[0] = "/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a";
        contentresolver.delete(uri, "_data=?", as);
    }

    private static final String FILENAME = "/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a";
    private static final String PATH = "/sdcard/media/audio/notifications";
}
