package com.htc.android.mail.eassvc.util;

import android.os.ServiceManager;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.ITelephony.Stub;
import com.htc.android.mail.eassvc.util.EASLog;

public class PowerSavingUtil {

   private static final String TAG = "EAS_PowerSavingUtil";


   public PowerSavingUtil() {}

   public static void changeRadioDormantTimer(int var0, int var1) {
      try {
         ITelephony var2 = Stub.asInterface(ServiceManager.checkService("phone"));
         if(var2 != null) {
            String var3 = "setAT: " + var0 + "," + var1;
            EASLog.i("EAS_PowerSavingUtil", var3);
            var2.sendAT_PushMail(var0, var1);
         } else {
            EASLog.e("EAS_PowerSavingUtil", "fail to get ITelephony");
         }
      } catch (Exception var5) {
         EASLog.e("EAS_PowerSavingUtil", "fail to get ITelephony: ", var5);
      }
   }
}
