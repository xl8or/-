package com.android.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.ExchangeUtils;
import com.android.email.Utility;
import com.android.email.activity.UpgradeAccounts;

public class Welcome extends Activity {

   private static final String SETUP_WIZARD_CATEGORY = "com.sonyericsson.category.SETUP_WIZARD_SETTING";
   private Welcome.MainActivityLauncher mMainActivityLauncher;


   public Welcome() {}

   public static void actionStart(Activity var0) {
      Intent var1 = new Intent(var0, Welcome.class);
      Intent var2 = var1.addFlags(67108864);
      var0.startActivity(var1);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Email.setNotifyUiAccountsChanged((boolean)0);
      if(UpgradeAccounts.doBulkUpgradeIfNecessary(this)) {
         this.finish();
      } else {
         AccountBackupRestore.restoreAccountsIfNeeded(this);
         ExchangeUtils.startExchangeService(this);
         Welcome.MainActivityLauncher var2 = new Welcome.MainActivityLauncher(this);
         this.mMainActivityLauncher = var2;
         Welcome.MainActivityLauncher var3 = this.mMainActivityLauncher;
         Void[] var4 = new Void[0];
         var3.execute(var4);
      }
   }

   protected void onDestroy() {
      Utility.cancelTaskInterrupt(this.mMainActivityLauncher);
      this.mMainActivityLauncher = null;
      super.onDestroy();
   }

   private static class MainActivityLauncher extends AsyncTask<Void, Void, Void> {

      private final Activity mFromActivity;


      public MainActivityLauncher(Activity var1) {
         this.mFromActivity = var1;
      }

      protected Void doInBackground(Void ... param1) {
         // $FF: Couldn't be decompiled
      }

      protected void onPostExecute(Void var1) {
         this.mFromActivity.finish();
      }
   }
}
