package com.android.volley;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyLog;
import java.util.concurrent.BlockingQueue;

public class CacheDispatcher extends Thread {

   private static final boolean DEBUG = VolleyLog.DEBUG;
   private final Cache mCache;
   private final BlockingQueue<Request> mCacheQueue;
   private final ResponseDelivery mDelivery;
   private final BlockingQueue<Request> mNetworkQueue;
   private volatile boolean mQuit = 0;


   public CacheDispatcher(BlockingQueue<Request> var1, BlockingQueue<Request> var2, Cache var3, ResponseDelivery var4) {
      this.mCacheQueue = var1;
      this.mNetworkQueue = var2;
      this.mCache = var3;
      this.mDelivery = var4;
   }

   public void quit() {
      this.mQuit = (boolean)1;
      this.interrupt();
   }

   public void run() {
      if(DEBUG) {
         Object[] var1 = new Object[0];
         VolleyLog.v("start new dispatcher", var1);
      }

      this.mCache.initialize();

      while(true) {
         try {
            while(true) {
               while(true) {
                  Request var2 = (Request)this.mCacheQueue.take();
                  var2.addMarker("cache-queue-take");
                  if(var2.isCanceled()) {
                     var2.finish("cache-discard-canceled");
                  } else {
                     Cache var4 = this.mCache;
                     String var5 = var2.getCacheKey();
                     Cache.Entry var6 = var4.get(var5);
                     if(var6 == null) {
                        var2.addMarker("cache-miss");
                        this.mNetworkQueue.put(var2);
                     } else {
                        if(!var6.isExpired()) {
                           var2.addMarker("cache-hit");
                           byte[] var7 = var6.data;
                           NetworkResponse var8 = new NetworkResponse(var7);
                           Response var9 = var2.parseNetworkResponse(var8);
                           var2.addMarker("cache-hit-parsed");
                           boolean var10 = var6.refreshNeeded();
                           var9.intermediate = var10;
                           var2.markDelivered();
                           this.mDelivery.postResponse(var2, var9);
                        }

                        if(var6.refreshNeeded()) {
                           var2.addMarker("cache-hit-refresh-needed");
                           var2.setCacheEntry(var6);
                           this.mNetworkQueue.put(var2);
                        }
                     }
                  }
               }
            }
         } catch (InterruptedException var11) {
            if(this.mQuit) {
               return;
            }
         }
      }
   }
}
