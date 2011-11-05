package com.google.android.finsky.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.services.ContentSyncService;
import com.google.android.finsky.utils.FinskyLog;

public class TestCheckinReceiver extends BroadcastReceiver {

   public TestCheckinReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      Object[] var3 = new Object[0];
      FinskyLog.e("in TestCheckinReceiver", var3);
      ContentSyncService.get().sync(var1, (boolean)1);
   }
}
