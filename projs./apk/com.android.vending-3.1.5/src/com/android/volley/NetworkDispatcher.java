package com.android.volley;

import com.android.volley.AuthFailureException;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionException;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.ServerException;
import com.android.volley.TimeoutException;
import com.android.volley.VolleyLog;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher extends Thread {

   private final Cache mCache;
   private final ResponseDelivery mDelivery;
   private final Network mNetwork;
   private final BlockingQueue<Request> mQueue;
   private volatile boolean mQuit = 0;


   public NetworkDispatcher(BlockingQueue<Request> var1, Network var2, Cache var3, ResponseDelivery var4) {
      this.mQueue = var1;
      this.mNetwork = var2;
      this.mCache = var3;
      this.mDelivery = var4;
   }

   private void parseAndDeliverNetworkError(Request<?> var1, NetworkError var2) {
      NetworkError var3 = var1.parseNetworkError(var2);
      Response.ErrorCode var4;
      if(var3 instanceof AuthFailureException) {
         var4 = Response.ErrorCode.AUTH;
      } else if(var3 instanceof ServerException) {
         var4 = Response.ErrorCode.SERVER;
      } else if(var3 instanceof TimeoutException) {
         var4 = Response.ErrorCode.TIMEOUT;
      } else if(var3 instanceof NoConnectionException) {
         var4 = Response.ErrorCode.NETWORK;
      } else {
         String var5 = "Unhandled NetworkError: " + var3;
         Object[] var6 = new Object[0];
         VolleyLog.e(var5, var6);
         var4 = Response.ErrorCode.SERVER;
      }

      this.mDelivery.postError(var1, var4, var3);
   }

   public void quit() {
      this.mQuit = (boolean)1;
      this.interrupt();
   }

   public void run() {
      while(true) {
         Request var1;
         try {
            var1 = (Request)this.mQueue.take();
         } catch (InterruptedException var17) {
            if(!this.mQuit) {
               continue;
            }

            return;
         }

         try {
            var1.addMarker("network-queue-take");
            if(var1.isCanceled()) {
               var1.finish("network-discard-cancelled");
            } else {
               NetworkResponse var4 = this.mNetwork.performRequest(var1);
               var1.addMarker("network-http-complete");
               if(var4.notModified && var1.hasHadResponseDelivered()) {
                  var1.finish("not-modified");
               } else {
                  Response var11 = var1.parseNetworkResponse(var4);
                  var1.addMarker("network-parse-complete");
                  if(var1.shouldCache() && var11.cacheEntry != null) {
                     Cache var12 = this.mCache;
                     String var13 = var1.getCacheKey();
                     Cache.Entry var14 = var11.cacheEntry;
                     var12.put(var13, var14);
                     var1.addMarker("network-cache-written");
                  }

                  var1.markDelivered();
                  this.mDelivery.postResponse(var1, var11);
               }
            }
         } catch (NetworkError var15) {
            this.parseAndDeliverNetworkError(var1, var15);
         } catch (Exception var16) {
            Object[] var6 = new Object[1];
            String var7 = var16.toString();
            var6[0] = var7;
            VolleyLog.e("Unhandled exception %s", var6);
            ResponseDelivery var8 = this.mDelivery;
            Response.ErrorCode var9 = Response.ErrorCode.SERVER;
            ServerException var10 = new ServerException();
            var8.postError(var1, var9, var10);
         }
      }
   }
}
