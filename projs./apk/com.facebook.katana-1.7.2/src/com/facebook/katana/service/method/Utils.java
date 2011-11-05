package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.PhotosProvider;

class Utils {

   private static final String[] ALBUM_PROJECTION;
   private static final int SNIPPET_LENGTH = 60;


   static {
      String[] var0 = new String[]{"size"};
      ALBUM_PROJECTION = var0;
   }

   Utils() {}

   public static String buildSnippet(String var0) {
      String var1;
      if(var0.length() > 60) {
         var1 = var0.substring(0, 60);
      } else {
         var1 = var0;
      }

      return var1.replaceAll("\n", " ");
   }

   public static void setAlbumsSize(Context var0, String var1, int var2) {
      ContentResolver var3 = var0.getContentResolver();
      Uri var4 = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, var1);
      String[] var5 = ALBUM_PROJECTION;
      Object var6 = null;
      Object var7 = null;
      Cursor var8 = var3.query(var4, var5, (String)null, (String[])var6, (String)var7);
      if(var8.moveToFirst() && var8.getInt(0) != var2) {
         ContentValues var9 = new ContentValues();
         Integer var10 = Integer.valueOf(var2);
         var9.put("size", var10);
         var3.update(var4, var9, (String)null, (String[])null);
      }

      var8.close();
   }
}
