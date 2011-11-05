package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.Factory;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import java.util.Collection;
import java.util.Iterator;

public class PhotoSyncModel {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private static boolean DEBUG;


   static {
      byte var0;
      if(!PhotoSyncModel.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      DEBUG = (boolean)0;
   }

   public PhotoSyncModel() {}

   private static void D(String var0) {
      if(DEBUG) {
         Log.d("PhotoSyncModel", var0);
      }
   }

   private static String buildDeleteSelection(Collection<String> var0) {
      boolean var1 = true;
      StringBuffer var2 = new StringBuffer(256);
      StringBuffer var3 = var2.append("pid").append(" IN(");

      String var5;
      StringBuffer var6;
      for(Iterator var4 = var0.iterator(); var4.hasNext(); var6 = var2.append('\'').append(var5).append('\'')) {
         var5 = (String)var4.next();
         if(var1) {
            var1 = false;
         } else {
            StringBuffer var7 = var2.append(',');
         }
      }

      StringBuffer var8 = var2.append(")");
      return var2.toString();
   }

   public static Factory<Cursor> cursorFactoryForPhotos(Context var0, Collection<FacebookPhoto> var1) {
      return new PhotoSyncModel.2(var0, var1);
   }

   public static Factory<Cursor> cursorFactoryForPhotosForAlbum(Context var0, String var1) {
      return new PhotoSyncModel.1(var0, var1);
   }

   public static Cursor cursorForPhotos(Context var0, Collection<FacebookPhoto> var1) {
      ContentResolver var2 = var0.getContentResolver();
      StringBuilder var3 = new StringBuilder(128);
      StringBuilder var4 = var3.append("pid").append(" IN(");
      boolean var5 = true;

      StringBuilder var11;
      for(Iterator var6 = var1.iterator(); var6.hasNext(); var11 = var3.append('\'')) {
         FacebookPhoto var7 = (FacebookPhoto)var6.next();
         if(var5) {
            var5 = false;
         } else {
            StringBuilder var12 = var3.append(',');
         }

         StringBuilder var8 = var3.append('\'');
         String var9 = var7.getPhotoId();
         var3.append(var9);
      }

      StringBuilder var13 = var3.append(')');
      Uri var14 = PhotosProvider.PHOTOS_CONTENT_URI;
      String[] var15 = PhotoSyncModel.PhotoQuery.PROJECTION;
      String var16 = var3.toString();
      Object var17 = null;
      return var2.query(var14, var15, var16, (String[])null, (String)var17);
   }

   public static Cursor cursorForPhotosForAlbum(Context var0, String var1) {
      Uri var2 = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, var1);
      ContentResolver var3 = var0.getContentResolver();
      String[] var4 = PhotoSyncModel.PhotoQuery.PROJECTION;
      Object var5 = null;
      Object var6 = null;
      return var3.query(var2, var4, (String)null, (String[])var5, (String)var6);
   }

   public static void doSync(Context param0, Collection<FacebookPhoto> param1, Factory<Cursor> param2, boolean param3, boolean param4, boolean param5, String param6) {
      // $FF: Couldn't be decompiled
   }

   private static boolean photosIdentical(FacebookPhoto var0, Cursor var1) {
      String var2 = var1.getString(2);
      String var3 = var1.getString(0);
      long var4 = var1.getLong(3);
      boolean var6;
      if(StringUtils.saneStringEquals(var0.getCaption(), var2) && StringUtils.saneStringEquals(var0.getAlbumId(), var3) && var0.position == var4) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   private interface PhotoQuery {

      int INDEX_ALBUM_ID = 0;
      int INDEX_CAPTION = 2;
      int INDEX_PHOTO_ID = 1;
      int INDEX_POSITION = 3;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"aid", "pid", "caption", "position"};
         PROJECTION = var0;
      }
   }

   static class 1 implements Factory<Cursor> {

      // $FF: synthetic field
      final String val$finalAlbumId;
      // $FF: synthetic field
      final Context val$finalContext;


      1(Context var1, String var2) {
         this.val$finalContext = var1;
         this.val$finalAlbumId = var2;
      }

      public Cursor make() {
         Context var1 = this.val$finalContext;
         String var2 = this.val$finalAlbumId;
         return PhotoSyncModel.cursorForPhotosForAlbum(var1, var2);
      }
   }

   static class 2 implements Factory<Cursor> {

      // $FF: synthetic field
      final Context val$finalContext;
      // $FF: synthetic field
      final Collection val$finalPhotos;


      2(Context var1, Collection var2) {
         this.val$finalContext = var1;
         this.val$finalPhotos = var2;
      }

      public Cursor make() {
         Context var1 = this.val$finalContext;
         Collection var2 = this.val$finalPhotos;
         return PhotoSyncModel.cursorForPhotos(var1, var2);
      }
   }
}
