package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.util.Map;
import org.codehaus.jackson.JsonParser;

public class PhotosDeletePhoto extends ApiMethod {

   private boolean mAlbumCoverChanged;
   private final String mAlbumId;
   private final String mPhotoId;


   public PhotosDeletePhoto(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6) {
      String var7 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "photos.deletePhoto", var7, var6);
      this.mParams.put("session_key", var3);
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("pid", var5);
      this.mAlbumId = var4;
      this.mPhotoId = var5;
   }

   private void adjustAlbumSize() {
      ContentResolver var1 = this.mContext.getContentResolver();
      Uri var2 = PhotosProvider.ALBUMS_AID_CONTENT_URI;
      StringBuilder var3 = (new StringBuilder()).append("");
      String var4 = this.mAlbumId;
      String var5 = var3.append(var4).toString();
      Uri var6 = Uri.withAppendedPath(var2, var5);
      String[] var7 = PhotosDeletePhoto.AlbumQuery.ALBUM_PROJECTION;
      Object var8 = null;
      Object var9 = null;
      Cursor var10 = var1.query(var6, var7, (String)null, (String[])var8, (String)var9);
      if(var10.moveToFirst()) {
         String var11 = var10.getString(1);
         if(var11 != null) {
            String var12 = this.mPhotoId;
            if(var11.equals(var12)) {
               this.mAlbumCoverChanged = (boolean)1;
            }
         }

         int var13 = var10.getInt(0);
         if(var13 - 1 >= 0) {
            ContentValues var14 = new ContentValues();
            Integer var15 = Integer.valueOf(var13 - 1);
            var14.put("size", var15);
            var1.update(var6, var14, (String)null, (String[])null);
         }
      }

      var10.close();
   }

   public boolean hasAlbumCoverChanged() {
      return this.mAlbumCoverChanged;
   }

   protected void parseJSON(JsonParser var1) {
      Uri var2 = PhotosProvider.PHOTOS_AID_CONTENT_URI;
      String var3 = this.mAlbumId;
      Uri var4 = Uri.withAppendedPath(var2, var3);
      ContentResolver var5 = this.mContext.getContentResolver();
      String[] var6 = new String[1];
      String var7 = this.mPhotoId;
      var6[0] = var7;
      var5.delete(var4, "pid IN(?)", var6);
      this.adjustAlbumSize();
   }

   private interface AlbumQuery {

      String[] ALBUM_PROJECTION;
      int INDEX_COVER_PHOTO_ID = 1;
      int INDEX_SIZE;


      static {
         String[] var0 = new String[]{"size", "cover_pid"};
         ALBUM_PROJECTION = var0;
      }
   }
}
