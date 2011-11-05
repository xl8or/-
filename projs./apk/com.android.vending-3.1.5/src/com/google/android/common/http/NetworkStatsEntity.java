package com.google.android.common.http;

import android.net.TrafficStats;
import android.os.SystemClock;
import android.util.EventLog;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

public class NetworkStatsEntity extends HttpEntityWrapper {

   private static final int HTTP_STATS_EVENT = 52001;
   private final long mProcessingStartTime;
   private final long mResponseLatency;
   private final long mStartRx;
   private final long mStartTx;
   private final String mUa;
   private final int mUid;


   public NetworkStatsEntity(HttpEntity var1, String var2, int var3, long var4, long var6, long var8, long var10) {
      super(var1);
      this.mUa = var2;
      this.mUid = var3;
      this.mStartTx = var4;
      this.mStartRx = var6;
      this.mResponseLatency = var8;
      this.mProcessingStartTime = var10;
   }

   public InputStream getContent() throws IOException {
      InputStream var1 = super.getContent();
      return new NetworkStatsEntity.NetworkStatsInputStream(var1);
   }

   private class NetworkStatsInputStream extends FilterInputStream {

      public NetworkStatsInputStream(InputStream var2) {
         super(var2);
      }

      public void close() throws IOException {
         boolean var45 = false;

         try {
            var45 = true;
            super.close();
            var45 = false;
         } finally {
            if(var45) {
               long var23 = SystemClock.elapsedRealtime();
               long var25 = NetworkStatsEntity.this.mProcessingStartTime;
               long var27 = var23 - var25;
               long var29 = TrafficStats.getUidTxBytes(NetworkStatsEntity.this.mUid);
               long var31 = TrafficStats.getUidRxBytes(NetworkStatsEntity.this.mUid);
               Object[] var33 = new Object[5];
               String var34 = NetworkStatsEntity.this.mUa;
               var33[0] = var34;
               Long var35 = Long.valueOf(NetworkStatsEntity.this.mResponseLatency);
               var33[1] = var35;
               Long var36 = Long.valueOf(var27);
               var33[2] = var36;
               long var37 = NetworkStatsEntity.this.mStartTx;
               Long var39 = Long.valueOf(var29 - var37);
               var33[3] = var39;
               long var40 = NetworkStatsEntity.this.mStartRx;
               Long var42 = Long.valueOf(var31 - var40);
               var33[4] = var42;
               int var43 = EventLog.writeEvent('\ucb21', var33);
            }
         }

         long var1 = SystemClock.elapsedRealtime();
         long var3 = NetworkStatsEntity.this.mProcessingStartTime;
         long var5 = var1 - var3;
         long var7 = TrafficStats.getUidTxBytes(NetworkStatsEntity.this.mUid);
         long var9 = TrafficStats.getUidRxBytes(NetworkStatsEntity.this.mUid);
         Object[] var11 = new Object[5];
         String var12 = NetworkStatsEntity.this.mUa;
         var11[0] = var12;
         Long var13 = Long.valueOf(NetworkStatsEntity.this.mResponseLatency);
         var11[1] = var13;
         Long var14 = Long.valueOf(var5);
         var11[2] = var14;
         long var15 = NetworkStatsEntity.this.mStartTx;
         Long var17 = Long.valueOf(var7 - var15);
         var11[3] = var17;
         long var18 = NetworkStatsEntity.this.mStartRx;
         Long var20 = Long.valueOf(var9 - var18);
         var11[4] = var20;
         int var21 = EventLog.writeEvent('\ucb21', var11);
      }
   }
}
