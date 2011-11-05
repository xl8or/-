package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.FinskyLog;

public class ClearCacheReceiver extends BroadcastReceiver {

   public ClearCacheReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      Object[] var3 = new Object[1];
      String var4 = var2.getAction();
      var3[0] = var4;
      FinskyLog.d("Received %s. Clearing cache and exiting.", var3);
      FinskyApp var5 = FinskyApp.get();
      ClearCacheReceiver.1 var6 = new ClearCacheReceiver.1();
      var5.clearCacheAsync(var6);
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         System.exit(0);
      }
   }
}
