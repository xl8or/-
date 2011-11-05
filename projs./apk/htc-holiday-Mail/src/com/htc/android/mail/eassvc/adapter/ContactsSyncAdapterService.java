package com.htc.android.mail.eassvc.adapter;

import android.accounts.Account;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.adapter.AbstractThreadedSyncAdapter;
import com.htc.android.mail.eassvc.adapter.EASServiceBinder;
import com.htc.android.mail.eassvc.util.EASLog;

public class ContactsSyncAdapterService extends Service {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final boolean HTC_DEBUG = true;
   private static final String TAG = "htc_EAS_ContactsSyncAdapterService";
   private static ContactsSyncAdapterService.SyncAdapterImpl sSyncAdapter = null;
   private static final Object sSyncAdapterLock = new Object();
   private Context mContext;


   public ContactsSyncAdapterService() {}

   public IBinder onBind(Intent var1) {
      return sSyncAdapter.getSyncAdapterBinder();
   }

   public void onCreate() {
      // $FF: Couldn't be decompiled
   }

   public void onDestroy() {
      if(DEBUG) {
         EASLog.d("htc_EAS_ContactsSyncAdapterService", "onDestroy()");
      }

      EASServiceBinder.unbindService(this.mContext);
      super.onDestroy();
   }

   private class SyncAdapterImpl extends AbstractThreadedSyncAdapter {

      Account mAccount = null;


      public SyncAdapterImpl(Context var2) {
         super(var2, (boolean)1);
      }

      public void onPerformSync(Account param1, Bundle param2, String param3, SyncResult param4) {
         // $FF: Couldn't be decompiled
      }

      public void onSyncCanceled() {
         if(ContactsSyncAdapterService.DEBUG) {
            EASLog.i("htc_EAS_ContactsSyncAdapterService", "> cancelSync");
         }

         Intent var1 = new Intent("com.htc.android.mail.intent.cancelSync");
         Intent var2 = var1.setClassName("com.htc.android.mail", "com.htc.android.mail.eassvc.EASAppSvc");
         ComponentName var3 = ContactsSyncAdapterService.this.mContext.startService(var1);
         super.onSyncCanceled();
         if(ContactsSyncAdapterService.DEBUG) {
            EASLog.i("htc_EAS_ContactsSyncAdapterService", "< cancelSync");
         }
      }
   }
}
