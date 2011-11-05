package com.facebook.katana.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RingtoneUtils {

   private static final String FILENAME = "/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a";
   private static final String PATH = "/sdcard/media/audio/notifications";


   public RingtoneUtils() {}

   public static Uri createFacebookRingtone(Context var0) throws IOException {
      File var1 = new File("/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a");
      if(!var1.exists()) {
         InputStream var2 = var0.getAssets().open("pop.m4a");
         byte[] var3 = new byte[var2.available()];
         var2.read(var3);
         var2.close();
         boolean var5 = (new File("/sdcard/media/audio/notifications")).mkdirs();
         FileOutputStream var6 = new FileOutputStream("/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a");
         var6.write(var3);
         var6.flush();
         var6.close();
      }

      Object var7 = null;
      ContentResolver var8 = var0.getContentResolver();
      Uri var9 = Media.EXTERNAL_CONTENT_URI;
      String[] var10 = new String[]{"/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a"};
      Cursor var11 = var8.query(var9, (String[])null, "_data=?", var10, (String)null);
      Uri var22;
      if(var11 != null) {
         if(var11.moveToFirst()) {
            int var12 = var11.getColumnIndexOrThrow("_id");

            while(true) {
               Uri var14 = Media.EXTERNAL_CONTENT_URI;
               StringBuilder var15 = (new StringBuilder()).append("");
               int var16 = var11.getInt(var12);
               String var17 = var15.append(var16).toString();
               Uri var18 = Uri.withAppendedPath(var14, var17);
               if(var18 != null) {
                  var22 = var18;
                  break;
               }

               if(!var11.moveToNext()) {
                  var22 = var18;
                  break;
               }
            }
         } else {
            var22 = (Uri)var7;
         }

         var11.close();
      } else {
         var22 = (Uri)var7;
      }

      if(var22 == null) {
         Intent var19 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
         Uri var20 = Uri.fromFile(var1);
         var19.setData(var20);
         var0.sendBroadcast(var19);
      }

      return var22;
   }

   public static void deleteFacebookRingtone(Context var0) {
      ContentResolver var1 = var0.getContentResolver();
      Uri var2 = Media.EXTERNAL_CONTENT_URI;
      String[] var3 = new String[]{"/sdcard/media/audio/notifications/facebook_ringtone_pop.m4a"};
      var1.delete(var2, "_data=?", var3);
   }
}
