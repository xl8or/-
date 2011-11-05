package com.android.volley.toolbox;

import android.os.Handler;
import android.os.Looper;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public class ClearCacheRequest extends Request<Object> {

   private final Cache mCache;
   private final Runnable mCallback;


   public ClearCacheRequest(Cache var1, Runnable var2) {
      super((String)null, (Response.ErrorListener)null);
      this.mCache = var1;
      this.mCallback = var2;
   }

   protected void deliverResponse(Object var1) {}

   public Request.Priority getPriority() {
      return Request.Priority.IMMEDIATE;
   }

   public boolean isCanceled() {
      this.mCache.clear();
      if(this.mCallback != null) {
         Looper var1 = Looper.getMainLooper();
         Handler var2 = new Handler(var1);
         Runnable var3 = this.mCallback;
         var2.postAtFrontOfQueue(var3);
      }

      return true;
   }

   protected Response<Object> parseNetworkResponse(NetworkResponse var1) {
      return null;
   }
}
