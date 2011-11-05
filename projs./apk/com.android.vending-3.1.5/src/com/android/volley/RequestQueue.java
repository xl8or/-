package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import com.android.volley.Cache;
import com.android.volley.CacheDispatcher;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.NetworkDispatcher;
import com.android.volley.Request;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyLog;
import com.google.android.finsky.utils.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {

   private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
   private static AtomicInteger sSequenceGenerator = new AtomicInteger();
   private final Cache mCache;
   private CacheDispatcher mCacheDispatcher;
   private final PriorityBlockingQueue<Request> mCacheQueue;
   private final ResponseDelivery mDelivery;
   private NetworkDispatcher[] mDispatchers;
   private final Network mNetwork;
   private final PriorityBlockingQueue<Request> mNetworkQueue;
   private final Map<String, Queue<Request>> mWaitingRequests;


   public RequestQueue(Cache var1, Network var2) {
      this(var1, var2, 4);
   }

   public RequestQueue(Cache var1, Network var2, int var3) {
      Looper var4 = Looper.getMainLooper();
      Handler var5 = new Handler(var4);
      ExecutorDelivery var6 = new ExecutorDelivery(var5);
      this(var1, var2, var3, var6);
   }

   public RequestQueue(Cache var1, Network var2, int var3, ResponseDelivery var4) {
      HashMap var5 = Maps.newHashMap();
      this.mWaitingRequests = var5;
      PriorityBlockingQueue var6 = new PriorityBlockingQueue();
      this.mCacheQueue = var6;
      PriorityBlockingQueue var7 = new PriorityBlockingQueue();
      this.mNetworkQueue = var7;
      this.mCache = var1;
      this.mNetwork = var2;
      NetworkDispatcher[] var8 = new NetworkDispatcher[var3];
      this.mDispatchers = var8;
      this.mDelivery = var4;
   }

   private void clearUndrainable(PriorityBlockingQueue<Request> var1) {
      ArrayList var2 = new ArrayList();
      var1.drainTo(var2);
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         Request var5 = (Request)var4.next();
         if(!var5.isDrainable()) {
            var1.add(var5);
         }
      }

   }

   public static int getSequenceNumber() {
      return sSequenceGenerator.incrementAndGet();
   }

   public Request add(Request var1) {
      var1.setRequestQueue(this);
      int var2 = sSequenceGenerator.incrementAndGet();
      var1.setSequence(var2);
      var1.addMarker("add-to-queue");
      if(!var1.shouldCache()) {
         this.mNetworkQueue.add(var1);
      } else {
         Map var4 = this.mWaitingRequests;
         synchronized(var4) {
            String var5 = var1.getCacheKey();
            if(this.mWaitingRequests.containsKey(var5)) {
               Object var6 = (Queue)this.mWaitingRequests.get(var5);
               if(var6 == null) {
                  var6 = new LinkedList();
               }

               ((Queue)var6).add(var1);
               this.mWaitingRequests.put(var5, var6);
               if(VolleyLog.DEBUG) {
                  Object[] var9 = new Object[]{var5};
                  VolleyLog.v("Request for cacheKey=%s is in flight, putting on hold.", var9);
               }
            } else {
               this.mWaitingRequests.put(var5, (Object)null);
               this.mCacheQueue.add(var1);
            }
         }
      }

      return var1;
   }

   public void drain() {
      int var1 = getSequenceNumber();
      this.drain(var1);
   }

   public void drain(int var1) {
      PriorityBlockingQueue var2 = this.mCacheQueue;
      this.clearUndrainable(var2);
      PriorityBlockingQueue var3 = this.mNetworkQueue;
      this.clearUndrainable(var3);
      this.mDelivery.discardBefore(var1);
      if(VolleyLog.DEBUG) {
         Object[] var4 = new Object[1];
         Integer var5 = Integer.valueOf(var1);
         var4[0] = var5;
         VolleyLog.v("Draining requests with sequence number below %s", var4);
      }
   }

   void finish(Request var1) {
      if(var1.shouldCache()) {
         Map var2 = this.mWaitingRequests;
         synchronized(var2) {
            String var3 = var1.getCacheKey();
            Queue var4 = (Queue)this.mWaitingRequests.remove(var3);
            if(var4 != null) {
               if(VolleyLog.DEBUG) {
                  Object[] var5 = new Object[2];
                  Integer var6 = Integer.valueOf(var4.size());
                  var5[0] = var6;
                  var5[1] = var3;
                  VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", var5);
               }

               this.mCacheQueue.addAll(var4);
            }

         }
      }
   }

   public void start() {
      this.stop();
      PriorityBlockingQueue var1 = this.mCacheQueue;
      PriorityBlockingQueue var2 = this.mNetworkQueue;
      Cache var3 = this.mCache;
      ResponseDelivery var4 = this.mDelivery;
      CacheDispatcher var5 = new CacheDispatcher(var1, var2, var3, var4);
      this.mCacheDispatcher = var5;
      this.mCacheDispatcher.start();
      int var6 = 0;

      while(true) {
         int var7 = this.mDispatchers.length;
         if(var6 >= var7) {
            return;
         }

         PriorityBlockingQueue var8 = this.mNetworkQueue;
         Network var9 = this.mNetwork;
         Cache var10 = this.mCache;
         ResponseDelivery var11 = this.mDelivery;
         NetworkDispatcher var12 = new NetworkDispatcher(var8, var9, var10, var11);
         this.mDispatchers[var6] = var12;
         var12.start();
         ++var6;
      }
   }

   public void stop() {
      if(this.mCacheDispatcher != null) {
         this.mCacheDispatcher.quit();
      }

      int var1 = 0;

      while(true) {
         int var2 = this.mDispatchers.length;
         if(var1 >= var2) {
            return;
         }

         if(this.mDispatchers[var1] != false) {
            this.mDispatchers[var1].quit();
         }

         ++var1;
      }
   }
}
