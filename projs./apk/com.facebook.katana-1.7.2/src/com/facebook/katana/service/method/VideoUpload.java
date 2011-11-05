package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ServiceNotificationManager;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookVideoUploadResponse;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class VideoUpload extends ApiMethod implements ApiMethodCallback {

   private static final String ID_PARAM = "id";
   private static String mReqId;
   private FacebookVideoUploadResponse mVideoResponse;
   private final String mVideoUri;


   public VideoUpload(Context var1, Intent var2, String var3, String var4, String var5, String var6, long var7, ApiMethodListener var9) {
      String var10 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "video.upload", var10, var9, (HttpOperation.HttpOperationListener)null);
      VideoUpload.VideoUploadListener var15 = new VideoUpload.VideoUploadListener();
      this.mHttpListener = var15;
      this.mVideoUri = var6;
      Map var16 = this.mParams;
      String var17 = this.mFacebookMethod;
      var16.put("method", var17);
      Object var19 = this.mParams.put("v", "1.0");
      Object var20 = this.mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
      Object var21 = this.mParams.put("format", "JSON");
      this.mParams.put("session_key", var3);
      Map var23 = this.mParams;
      String var24 = String.valueOf(System.currentTimeMillis());
      var23.put("call_id", var24);
      if(var7 != 65535L) {
         Map var26 = this.mParams;
         String var27 = String.valueOf(var7);
         var26.put("id", var27);
      }

      if(var4 != null) {
         this.mParams.put("title", var4);
      }

      if(var5 != null) {
         this.mParams.put("description", var5);
      }
   }

   public static String RequestVideoUpload(Context var0, String var1, String var2, String var3, long var4) {
      AppSession var6 = AppSession.getActiveSession(var0, (boolean)0);
      String var7 = var6.getSessionInfo().sessionKey;
      VideoUpload var14 = new VideoUpload(var0, (Intent)null, var7, var1, var2, var3, var4, (ApiMethodListener)null);
      mReqId = var6.postToService(var0, var14, 1001, 1020, (Bundle)null);
      int var18 = Integer.parseInt(mReqId);
      ServiceNotificationManager.beginVideoUploadProgressNotification(var0, var18, var1, var2, var3);
      return mReqId;
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      int var8 = Integer.parseInt(mReqId);
      String var9 = this.mVideoUri;
      if(!ServiceNotificationManager.endVideoUploadProgressNotification(var2, var8, var5, var9)) {
         String var10 = this.mVideoUri;
         boolean var11 = (new File(var10)).delete();
      }

      Iterator var12 = var1.getListeners().iterator();

      while(var12.hasNext()) {
         AppSessionListener var13 = (AppSessionListener)var12.next();
         FacebookVideoUploadResponse var14 = this.mVideoResponse;
         String var15 = this.mVideoUri;
         var13.onVideoUploadComplete(var1, var4, var5, var6, var7, var14, var15);
      }

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

   public String getRealPathFromURI(Uri var1) {
      String[] var2 = new String[]{"_data"};
      ContentResolver var3 = this.mContext.getContentResolver();
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var3.query(var1, var2, (String)null, (String[])var5, (String)var6);
      int var8 = var7.getColumnIndexOrThrow("_data");
      boolean var9 = var7.moveToFirst();
      return var7.getString(var8);
   }

   public FacebookVideoUploadResponse getVideoUploadResponse() {
      return this.mVideoResponse;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      FacebookVideoUploadResponse var2 = FacebookVideoUploadResponse.parseJson(var1);
      this.mVideoResponse = var2;
   }

   public void start() {
      // $FF: Couldn't be decompiled
   }

   protected class VideoUploadListener extends ApiMethod.ApiHttpListener {

      protected VideoUploadListener() {
         super();
      }

      public void onHttpOperationProgress(HttpOperation var1, long var2, long var4) {
         if(VideoUpload.this.mListener != null) {
            Handler var6 = ApiMethod.mHandler;
            VideoUpload.VideoUploadListener.1 var12 = new VideoUpload.VideoUploadListener.1(var2, var4);
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
            if(VideoUpload.this.mHttpOp != null) {
               Context var1 = VideoUpload.this.mContext;
               int var2 = Integer.parseInt(VideoUpload.mReqId);
               long var3 = this.val$position * 100L;
               long var5 = this.val$length;
               int var7 = (int)(var3 / var5);
               ServiceNotificationManager.updateProgressNotification(var1, var2, var7);
            }
         }
      }
   }
}
