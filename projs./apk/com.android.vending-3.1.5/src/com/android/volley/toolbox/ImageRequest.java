package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class ImageRequest extends Request<Bitmap> {

   private static final float IMAGE_BACKOFF_MULT = 2.0F;
   private static final int IMAGE_MAX_RETRIES = 2;
   private static final int IMAGE_TIMEOUT_MS = 1000;
   private static final Object sDecodeLock = new Object();
   private final Config mDecodeConfig;
   private final Response.Listener<Bitmap> mListener;
   private final int mMaxHeight;
   private final int mMaxWidth;


   public ImageRequest(String var1, Response.Listener<Bitmap> var2, int var3, int var4, Config var5, Response.ErrorListener var6) {
      super(var1, var6);
      DefaultRetryPolicy var7 = new DefaultRetryPolicy(1000, 2, 2.0F);
      this.setRetryPolicy(var7);
      this.mListener = var2;
      this.mDecodeConfig = var5;
      this.mMaxWidth = var3;
      this.mMaxHeight = var4;
   }

   static int findBestSampleSize(int var0, int var1, int var2, int var3) {
      double var4 = (double)var0;
      double var6 = (double)var2;
      double var8 = var4 / var6;
      double var10 = (double)var1;
      double var12 = (double)var3;
      double var14 = var10 / var12;
      double var16 = Math.min(var8, var14);

      float var18;
      for(var18 = 1.0F; (double)(var18 * 2.0F) <= var16; var18 *= 2.0F) {
         ;
      }

      return (int)var18;
   }

   private static int getResizedHeight(int var0, int var1, int var2, int var3) {
      if(var0 != 0 || var1 != 0) {
         if(var1 == 0) {
            double var4 = (double)var0;
            double var6 = (double)var2;
            double var8 = var4 / var6;
            var3 = (int)((double)var3 * var8);
         } else if(var0 == 0) {
            var3 = var1;
         } else {
            double var10 = (double)var2;
            double var12 = (double)var3;
            double var14 = var10 / var12;
            int var16 = var1;
            double var17 = (double)var1 * var14;
            double var19 = (double)var0;
            if(var17 > var19) {
               var16 = (int)((double)var0 / var14);
            }

            var3 = var16;
         }
      }

      return var3;
   }

   private static int getResizedWidth(int var0, int var1, int var2, int var3) {
      if(var0 != 0 || var1 != 0) {
         if(var0 == 0) {
            double var4 = (double)var1;
            double var6 = (double)var3;
            double var8 = var4 / var6;
            var2 = (int)((double)var2 * var8);
         } else if(var1 == 0) {
            var2 = var0;
         } else {
            double var10 = (double)var3;
            double var12 = (double)var2;
            double var14 = var10 / var12;
            int var16 = var0;
            double var17 = (double)var0 * var14;
            double var19 = (double)var1;
            if(var17 > var19) {
               var16 = (int)((double)var1 / var14);
            }

            var2 = var16;
         }
      }

      return var2;
   }

   protected void deliverResponse(Bitmap var1) {
      this.mListener.onResponse(var1);
   }

   public Request.Priority getPriority() {
      return Request.Priority.LOW;
   }

   protected Response<Bitmap> parseNetworkResponse(NetworkResponse param1) {
      // $FF: Couldn't be decompiled
   }
}
