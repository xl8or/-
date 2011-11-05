package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.Secure;
import android.util.Log;
import com.android.settings.TestingSettings;

public class TestingSettingsBroadcastReceiver extends BroadcastReceiver {

   private String TAG = "TestingSettingsBroadcastReceiver";


   public TestingSettingsBroadcastReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      StringBuilder var3 = (new StringBuilder()).append("DEVICE_PROVISIONED : ");
      int var4 = Secure.getInt(var1.getContentResolver(), "device_provisioned", 0);
      String var5 = var3.append(var4).toString();
      int var6 = Log.e("DEVICE_PROVISIONED : ", var5);
      if(var2.getAction().equals("android.provider.Telephony.SECRET_CODE")) {
         Intent var7 = new Intent("android.intent.action.MAIN");
         var7.setClass(var1, TestingSettings.class);
         Intent var9 = var7.setFlags(268435456);
         var1.startActivity(var7);
      }
   }
}
