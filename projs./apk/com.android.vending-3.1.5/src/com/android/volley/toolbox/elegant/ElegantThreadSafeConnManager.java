package com.android.volley.toolbox.elegant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPooledConnAdapter;
import org.apache.http.impl.conn.tsccm.ConnPoolByRoute;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.conn.tsccm.WaitingThreadAborter;
import org.apache.http.params.HttpParams;

public class ElegantThreadSafeConnManager extends ThreadSafeClientConnManager {

   public ElegantThreadSafeConnManager(HttpParams var1, SchemeRegistry var2) {
      super(var1, var2);
   }

   protected AbstractConnPool createConnectionPool(HttpParams var1) {
      ClientConnectionOperator var2 = this.connOperator;
      ElegantThreadSafeConnManager.ElegantPool var3 = new ElegantThreadSafeConnManager.ElegantPool(var2, var1);
      if(true) {
         var3.enableConnectionGC();
      }

      return var3;
   }

   public void releaseConnection(ManagedClientConnection var1, long var2, TimeUnit var4) {
      if(var1 instanceof ElegantThreadSafeConnManager.ElegantBasicPooledConnAdapter && var1.getRoute() != null) {
         ElegantThreadSafeConnManager.ElegantBasicPooledConnAdapter var5 = (ElegantThreadSafeConnManager.ElegantBasicPooledConnAdapter)var1;
         long var6 = System.currentTimeMillis();
         long var8 = var5.startTime;
         long var10 = var6 - var8;
         long var12;
         if(var1.getRoute().isSecure()) {
            var12 = 5000L;
         } else {
            var12 = 2500L;
         }

         if(var10 > var12) {
            try {
               var1.close();
            } catch (IOException var15) {
               ;
            }
         }
      }

      super.releaseConnection(var1, var2, var4);
   }

   public ClientConnectionRequest requestConnection(HttpRoute var1, Object var2) {
      PoolEntryRequest var3 = this.connectionPool.requestPoolEntry(var1, var2);
      return new ElegantThreadSafeConnManager.1(var3, var1);
   }

   public static class ElegantPool extends ConnPoolByRoute {

      public ElegantPool(ClientConnectionOperator var1, HttpParams var2) {
         super(var1, var2);
      }

      protected BasicPoolEntry getEntryBlocking(HttpRoute param1, Object param2, long param3, TimeUnit param5, WaitingThreadAborter param6) throws ConnectionPoolTimeoutException, InterruptedException {
         // $FF: Couldn't be decompiled
      }

      public PoolEntryRequest requestPoolEntry(HttpRoute var1, Object var2) {
         WaitingThreadAborter var3 = new WaitingThreadAborter();
         return new ElegantThreadSafeConnManager.ElegantPool.1(var3, var1, var2);
      }

      class 1 implements PoolEntryRequest {

         // $FF: synthetic field
         final WaitingThreadAborter val$aborter;
         // $FF: synthetic field
         final HttpRoute val$route;
         // $FF: synthetic field
         final Object val$state;


         1(WaitingThreadAborter var2, HttpRoute var3, Object var4) {
            this.val$aborter = var2;
            this.val$route = var3;
            this.val$state = var4;
         }

         public void abortRequest() {
            ElegantPool.this.poolLock.lock();

            try {
               this.val$aborter.abort();
            } finally {
               ElegantPool.this.poolLock.unlock();
            }

         }

         public BasicPoolEntry getPoolEntry(long var1, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException {
            ElegantThreadSafeConnManager.ElegantPool var4 = ElegantPool.this;
            HttpRoute var5 = this.val$route;
            Object var6 = this.val$state;
            WaitingThreadAborter var7 = this.val$aborter;
            return var4.getEntryBlocking(var5, var6, var1, var3, var7);
         }
      }
   }

   class 1 implements ClientConnectionRequest {

      // $FF: synthetic field
      final PoolEntryRequest val$poolRequest;
      // $FF: synthetic field
      final HttpRoute val$route;


      1(PoolEntryRequest var2, HttpRoute var3) {
         this.val$poolRequest = var2;
         this.val$route = var3;
      }

      public void abortRequest() {
         this.val$poolRequest.abortRequest();
      }

      public ManagedClientConnection getConnection(long var1, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException {
         if(this.val$route == null) {
            throw new IllegalArgumentException("Route may not be null.");
         } else {
            BasicPoolEntry var4 = this.val$poolRequest.getPoolEntry(var1, var3);
            ElegantThreadSafeConnManager var5 = ElegantThreadSafeConnManager.this;
            return new ElegantThreadSafeConnManager.ElegantBasicPooledConnAdapter(var5, var4);
         }
      }
   }

   public static class ElegantBasicPooledConnAdapter extends BasicPooledConnAdapter {

      public final long startTime;


      protected ElegantBasicPooledConnAdapter(ThreadSafeClientConnManager var1, AbstractPoolEntry var2) {
         super(var1, var2);
         long var3 = System.currentTimeMillis();
         this.startTime = var3;
      }
   }
}
