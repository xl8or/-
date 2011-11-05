package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PhotosUpload extends ApiMethod {

   public static final String AT_PLACE = "place";
   public static final String PHOTO_PUBLISH_PARAM = "published";
   public static final String PRIVACY = "privacy";
   public static final String PROFILE_ID_PARAM = "profile_id";
   public static final String TAGS = "tags";
   public static final String TARGET_ID = "target_id";
   private FacebookPhoto mPhoto;
   private final String mPhotoUri;


   public PhotosUpload(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6, String var7, long var8, long var10, boolean var12, long var13, String var15, long var16, String var18) {
      String var19 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "photos.upload", var19, var6, (HttpOperation.HttpOperationListener)null);
      PhotosUpload.PhotosUploadHttpListener var24 = new PhotosUpload.PhotosUploadHttpListener();
      this.mHttpListener = var24;
      this.mPhotoUri = var5;
      Map var26 = this.mParams;
      String var27 = this.mFacebookMethod;
      var26.put("method", var27);
      Object var29 = this.mParams.put("v", "1.0");
      Object var30 = this.mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
      Object var31 = this.mParams.put("format", "JSON");
      this.mParams.put("session_key", var3);
      Map var33 = this.mParams;
      StringBuilder var34 = (new StringBuilder()).append("");
      long var35 = System.currentTimeMillis();
      String var37 = var34.append(var35).toString();
      var33.put("call_id", var37);
      if(var4 != null) {
         this.mParams.put("caption", var4);
      }

      if(var7 != null) {
         Map var40 = this.mParams;
         String var41 = "aid";
         var40.put(var41, var7);
      }

      if(var8 != 65535L) {
         Map var44 = this.mParams;
         String var45 = Long.toString(var8);
         var44.put("checkin_id", var45);
      }

      if(var10 != 65535L) {
         Map var47 = this.mParams;
         String var48 = Long.toString(var10);
         var47.put("profile_id", var48);
      }

      Map var50 = this.mParams;
      String var51 = Boolean.toString(var12);
      var50.put("published", var51);
      if(var13 != 65535L) {
         Map var53 = this.mParams;
         String var54 = Long.toString(var13);
         var53.put("place", var54);
      }

      if(var15 != false && var15.length() > 0) {
         Map var56 = this.mParams;
         String var57 = "tags";
         var56.put(var57, var15);
      }

      if(var16 != 65535L) {
         Map var60 = this.mParams;
         String var61 = Long.toString(var16);
         var60.put("target_id", var61);
      }

      if(var18 != null) {
         if(var18.length() > 0) {
            Map var63 = this.mParams;
            String var64 = "privacy";
            var63.put(var64, var18);
         }
      }
   }

   private void adjustAlbum() {
      ContentResolver var1 = this.mContext.getContentResolver();
      Uri var2 = PhotosProvider.ALBUMS_AID_CONTENT_URI;
      String var3 = this.mPhoto.getAlbumId();
      Uri var4 = Uri.withAppendedPath(var2, var3);
      String[] var5 = PhotosUpload.AlbumQuery.ALBUM_PROJECTION;
      Object var6 = null;
      Object var7 = null;
      Cursor var8 = var1.query(var4, var5, (String)null, (String[])var6, (String)var7);
      if(var8.moveToFirst()) {
         ContentValues var9 = new ContentValues();
         Integer var10 = Integer.valueOf(var8.getInt(0) + 1);
         var9.put("size", var10);
         var1.update(var4, var9, (String)null, (String[])null);
      }

      var8.close();
   }

   private void insertPhoto() {
      ContentValues var1 = new ContentValues();
      String var2 = this.mPhoto.getPhotoId();
      var1.put("pid", var2);
      Long var3 = Long.valueOf(this.mPhoto.getOwnerId());
      var1.put("owner", var3);
      String var4 = this.mPhoto.getSrc();
      var1.put("src", var4);
      String var5 = this.mPhoto.getSrcBig();
      var1.put("src_big", var5);
      String var6 = this.mPhoto.getSrcSmall();
      var1.put("src_small", var6);
      String var7 = this.mPhoto.getCaption();
      var1.put("caption", var7);
      Long var8 = Long.valueOf(this.mPhoto.getCreated());
      var1.put("created", var8);
      Uri var9 = PhotosProvider.PHOTOS_AID_CONTENT_URI;
      String var10 = this.mPhoto.getAlbumId();
      Uri var11 = Uri.withAppendedPath(var9, var10);
      this.mContext.getContentResolver().insert(var11, var1);
   }

   public long getFileSizeFromURI(Uri var1) {
      String[] var2 = new String[]{"_size"};
      ContentResolver var3 = this.mContext.getContentResolver();
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var3.query(var1, var2, (String)null, (String[])var5, (String)var6);
      int var8 = var7.getColumnIndexOrThrow("_size");
      boolean var9 = var7.moveToFirst();
      return var7.getLong(var8);
   }

   public FacebookPhoto getPhoto() {
      return this.mPhoto;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      FacebookPhoto var2 = FacebookPhoto.parseJson(var1);
      this.mPhoto = var2;
      this.insertPhoto();
      this.adjustAlbum();
   }

   public void start() {
      // $FF: Couldn't be decompiled
   }

   private interface AlbumQuery {

      String[] ALBUM_PROJECTION;
      int INDEX_SIZE;


      static {
         String[] var0 = new String[]{"size"};
         ALBUM_PROJECTION = var0;
      }
   }

   protected class PhotosUploadHttpListener extends ApiMethod.ApiHttpListener {

      protected PhotosUploadHttpListener() {
         super();
      }

      public void onHttpOperationProgress(HttpOperation var1, long var2, long var4) {
         if(PhotosUpload.this.mListener != null) {
            Handler var6 = ApiMethod.mHandler;
            PhotosUpload.PhotosUploadHttpListener.1 var12 = new PhotosUpload.PhotosUploadHttpListener.1(var2, var4);
            var6.post(var12);
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final long val$length;
         // $FF: synthetic field
         final long val$position;


         1(long var2, long var4) {
            this.val$position = var2;
            this.val$length = var4;
         }

         public void run() {
            if(PhotosUpload.this.mHttpOp != null) {
               ApiMethodListener var1 = PhotosUpload.this.mListener;
               PhotosUpload var2 = PhotosUpload.this;
               long var3 = this.val$position;
               long var5 = this.val$length;
               var1.onOperationProgress(var2, var3, var5);
            }
         }
      }
   }
}
