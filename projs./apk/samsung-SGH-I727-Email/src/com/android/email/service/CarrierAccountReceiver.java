package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.android.email.Email;
import com.android.email.service.DefaultAccountService;

public class CarrierAccountReceiver extends BroadcastReceiver {

   public CarrierAccountReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      StringBuilder var3 = (new StringBuilder()).append(">>>>>> ");
      String var4 = var2.getAction();
      String var5 = var3.append(var4).toString();
      Email.loge(">>> CarrierAccountRecv", var5);
      PackageManager var6 = var1.getPackageManager();
      String var7 = "pm " + var6;
      Email.loge(">>> CarrierAccountRecv", var7);
      ComponentName var8 = new ComponentName(var1, DefaultAccountService.class);
      if(var6.getComponentEnabledSetting(var8) != 1) {
         Email.loge(">>> CarrierAccountRecv", "service is not avaiable");
         ComponentName var9 = new ComponentName(var1, DefaultAccountService.class);
         var6.setComponentEnabledSetting(var9, 1, 1);
      }

      if(var2.getAction().equals("android.intent.action.SET_RECV_HOST")) {
         DefaultAccountService.actionSetDefaultAccount(var1, var2);
      } else if(var2.getAction().equals("android.intent.action.GET_RECV_HOST")) {
         DefaultAccountService.actionGetDefaultAccount(var1, var2);
      }
   }
}
