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

public class ContactsSyncAdapterService extends Service {

   private static final String ACCOUNT_AND_TYPE_CONTACTS = "accountKey=? AND type=66";
   private static final String[] ID_PROJECTION;
   private static final String TAG = "EAS ContactsSyncAdapterService";
   private static ContactsSyncAdapterService.SyncAdapterImpl sSyncAdapter = null;
   private static final Object sSyncAdapterLock = new Object();


   static {
      String[] var0 = new String[]{"_id"};
      ID_PROJECTION = var0;
   }

   public ContactsSyncAdapterService() {}

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
            sSyncAdapter = new ContactsSyncAdapterService.SyncAdapterImpl(var2);
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
            ContactsSyncAdapterService.performSync(var6, var1, var2, var3, var4, var5);
         } catch (OperationCanceledException var13) {
            ;
         }
      }
   }
}
