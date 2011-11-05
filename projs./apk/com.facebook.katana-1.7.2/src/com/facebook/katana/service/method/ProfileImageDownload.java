package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.service.method.ApiLogging;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.HttpOperation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProfileImageDownload extends ApiMethod implements HttpOperation.HttpOperationListener {

   public final String filename;
   public final long id;
   private ProfileImage mProfileImage;


   public ProfileImageDownload(Context var1, Intent var2, long var3, String var5, String var6, ApiMethodListener var7) {
      super(var1, var2, "GET", (String)null, var5, var7);
      this.id = var3;
      this.filename = var6;
   }

   public ProfileImage getProfileImage() {
      return this.mProfileImage;
   }

   public void onHttpOperationComplete(HttpOperation var1, int var2, String var3, OutputStream var4, Exception var5) {
      if(var2 == 200) {
         try {
            Context var6 = this.mContext;
            long var7 = this.id;
            String var9 = this.mBaseUrl;
            String var10 = this.filename;
            ProfileImage var11 = ConnectionsProvider.updateImage(var6, var7, var9, var10);
            this.mProfileImage = var11;
         } catch (IOException var27) {
            var5 = var27;
            var2 = 0;
            var3 = null;
         }
      }

      String var12 = this.filename;
      File var13 = new File(var12);
      long var14 = var13.length();
      boolean var16 = var13.delete();
      if(ApiLogging.reportAndCheckTrx(var2)) {
         Context var20 = this.mContext;
         String var21 = var1.mHttpMethod.getURI().toString();
         long var22 = var1.calculateTimeElapsed();
         ApiLogging.logTransferResponse(var20, var21, var22, var14, var2);
      }

      Handler var24 = mHandler;
      ProfileImageDownload.1 var25 = new ProfileImageDownload.1(var2, var3, (Exception)var5);
      var24.post(var25);
   }

   public void onHttpOperationProgress(HttpOperation var1, long var2, long var4) {}

   public void start() {
      try {
         Context var1 = this.mContext;
         String var2 = this.mHttpMethod;
         String var3 = this.mBaseUrl;
         String var4 = this.filename;
         FileOutputStream var5 = new FileOutputStream(var4);
         HttpOperation var7 = new HttpOperation(var1, var2, var3, var5, this, (boolean)0);
         this.mHttpOp = var7;
         this.mHttpOp.start();
      } catch (Exception var9) {
         var9.printStackTrace();
         this.mListener.onOperationComplete(this, 0, (String)null, var9);
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final int val$fErrorCode;
      // $FF: synthetic field
      final String val$fReasonPhrase;
      // $FF: synthetic field
      final Exception val$fex;


      1(int var2, String var3, Exception var4) {
         this.val$fErrorCode = var2;
         this.val$fReasonPhrase = var3;
         this.val$fex = var4;
      }

      public void run() {
         if(ProfileImageDownload.this.mHttpOp != null) {
            ProfileImageDownload.this.mHttpOp = null;
            ApiMethodListener var1 = ProfileImageDownload.this.mListener;
            ProfileImageDownload var2 = ProfileImageDownload.this;
            int var3 = this.val$fErrorCode;
            String var4 = this.val$fReasonPhrase;
            Exception var5 = this.val$fex;
            var1.onOperationComplete(var2, var3, var4, var5);
         }
      }
   }
}
