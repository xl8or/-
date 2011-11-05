package com.google.android.common;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.content.SyncStats;
import android.database.SQLException;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Process;
import android.util.EventLog;

public abstract class LoggingThreadedSyncAdapter extends AbstractThreadedSyncAdapter {

   private static final String TAG = "Sync";


   public LoggingThreadedSyncAdapter(Context var1, boolean var2) {
      super(var1, var2);
   }

   public LoggingThreadedSyncAdapter(Context var1, boolean var2, boolean var3) {
      super(var1, var2, var3);
   }

   protected void onLogSyncDetails(long var1, long var3, SyncResult var5) {
      Object[] var6 = new Object[4];
      var6[0] = "Sync";
      Long var7 = Long.valueOf(var1);
      var6[1] = var7;
      Long var8 = Long.valueOf(var3);
      var6[2] = var8;
      var6[3] = "";
      int var9 = EventLog.writeEvent(203001, var6);
   }

   public abstract void onPerformLoggedSync(Account var1, Bundle var2, String var3, ContentProviderClient var4, SyncResult var5);

   public void onPerformSync(Account var1, Bundle var2, String var3, ContentProviderClient var4, SyncResult var5) {
      int var6 = Process.myUid();
      long var7 = TrafficStats.getUidTxBytes(var6);
      long var9 = TrafficStats.getUidRxBytes(var6);
      boolean var36 = false;

      label39: {
         try {
            var36 = true;
            this.onPerformLoggedSync(var1, var2, var3, var4, var5);
            var36 = false;
            break label39;
         } catch (SQLException var37) {
            SyncStats var18 = var5.stats;
            long var19 = var18.numParseExceptions + 1L;
            var18.numParseExceptions = var19;
            var36 = false;
         } finally {
            if(var36) {
               long var28 = TrafficStats.getUidTxBytes(var6) - var7;
               long var30 = TrafficStats.getUidRxBytes(var6) - var9;
               this.onLogSyncDetails(var28, var30, var5);
            }
         }

         long var21 = TrafficStats.getUidTxBytes(var6) - var7;
         long var23 = TrafficStats.getUidRxBytes(var6) - var9;
         this.onLogSyncDetails(var21, var23, var5);
         return;
      }

      long var11 = TrafficStats.getUidTxBytes(var6) - var7;
      long var13 = TrafficStats.getUidRxBytes(var6) - var9;
      this.onLogSyncDetails(var11, var13, var5);
   }
}
