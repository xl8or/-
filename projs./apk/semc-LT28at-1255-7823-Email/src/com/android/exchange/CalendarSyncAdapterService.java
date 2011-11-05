package com.android.exchange;

import android.accounts.Account;
import android.accounts.OperationCanceledException;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;

public class CalendarSyncAdapterService extends Service {

   private static final String ACCOUNT_AND_TYPE_CALENDAR = "accountKey=? AND type=65";
   private static final String DIRTY_IN_ACCOUNT = "_sync_dirty=1 AND _sync_account=? AND _sync_account_type=?";
   private static final String[] ID_HOST_PROJECTION;
   private static final int ID_SYNC_KEY_MAILBOX_ID = 0;
   private static final String[] ID_SYNC_KEY_PROJECTION;
   private static final int ID_SYNC_KEY_SYNC_KEY = 1;
   private static final String TAG = "EAS CalendarSyncAdapterService";
   private static CalendarSyncAdapterService.SyncAdapterImpl sSyncAdapter = null;
   private static final Object sSyncAdapterLock = new Object();


   static {
      String[] var0 = new String[]{"_id", "hostAuthKeyRecv"};
      ID_HOST_PROJECTION = var0;
      String[] var1 = new String[]{"_id", "syncKey"};
      ID_SYNC_KEY_PROJECTION = var1;
   }

   public CalendarSyncAdapterService() {}

   private static void performSync(Context param0, Account param1, Bundle param2, String param3, ContentProviderClient param4, SyncResult param5) throws OperationCanceledException {
      // $FF: Couldn't be decompiled
   }

   public IBinder onBind(Intent var1) {
      return sSyncAdapter.getSyncAdapterBinder();
   }

   public void onCreate() {
      super.onCreate();
      Object var1 = sSyncAdapterLock;
      synchronized(var1) {
         if(sSyncAdapter == null) {
            Context var2 = this.getApplicationContext();
            sSyncAdapter = new CalendarSyncAdapterService.SyncAdapterImpl(var2);
         }

      }
   }

   private static class SyncAdapterImpl extends AbstractThreadedSyncAdapter {

      private Context mContext;


      public SyncAdapterImpl(Context var1) {
         super(var1, (boolean)1);
         this.mContext = var1;
      }

      public void onPerformSync(Account var1, Bundle var2, String var3, ContentProviderClient var4, SyncResult var5) {
         try {
            Context var6 = this.mContext;
            CalendarSyncAdapterService.performSync(var6, var1, var2, var3, var4, var5);
         } catch (OperationCanceledException var13) {
            ;
         }
      }
   }
}
