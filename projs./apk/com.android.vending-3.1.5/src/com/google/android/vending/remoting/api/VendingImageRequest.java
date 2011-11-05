package com.google.android.vending.remoting.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.vending.remoting.api.VendingApiContext;
import com.google.android.vending.remoting.api.VendingRequest;
import com.google.android.vending.remoting.protos.VendingProtos;

public class VendingImageRequest extends VendingRequest<VendingProtos.GetImageRequestProto, VendingProtos.GetImageResponseProto> {

   private static final float IMAGE_BACKOFF_MULT = 1.0F;
   private static final int IMAGE_MAX_RETRIES = 2;
   private static final int IMAGE_TIMEOUT_MS = 1000;


   public VendingImageRequest(String var1, int var2, Response.Listener<Bitmap> var3, Response.ErrorListener var4) {
      VendingProtos.GetImageRequestProto var5 = (new VendingProtos.GetImageRequestProto()).setAssetId(var1).setImageUsage(var2).setProductType(0);
      VendingImageRequest.1 var6 = var3.new 1();
      VendingApiContext var7 = FinskyApp.get().getVendingApi().getApiContext();
      super("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.GetImageRequestProto.class, var5, VendingProtos.GetImageResponseProto.class, var6, var7, var4);
      DefaultRetryPolicy var10 = new DefaultRetryPolicy(1000, 2, 1.0F);
      this.setRetryPolicy(var10);
   }

   public Request.Priority getPriority() {
      return Request.Priority.LOW;
   }

   class 1 implements Response.Listener<VendingProtos.GetImageResponseProto> {

      1() {}

      public void onResponse(VendingProtos.GetImageResponseProto var1) {
         byte[] var2 = var1.getImageData().toByteArray();
         int var3 = var2.length;
         Bitmap var4 = BitmapFactory.decodeByteArray(var2, 0, var3, (Options)null);
         VendingImageRequest.this.onResponse(var4);
      }
   }
}
