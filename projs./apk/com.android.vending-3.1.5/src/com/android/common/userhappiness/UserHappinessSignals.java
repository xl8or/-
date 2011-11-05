package com.android.common.userhappiness;

import android.content.Context;
import android.content.Intent;

public class UserHappinessSignals {

   private static boolean mHasVoiceLoggingInfo = 0;


   public UserHappinessSignals() {}

   public static void setHasVoiceLoggingInfo(boolean var0) {
      mHasVoiceLoggingInfo = var0;
   }

   public static void userAcceptedImeText(Context var0) {
      if(mHasVoiceLoggingInfo) {
         Intent var1 = new Intent("com.android.common.speech.LOG_EVENT");
         Intent var2 = var1.putExtra("app_name", "voiceime");
         Intent var3 = var1.putExtra("extra_event", 21);
         String var4 = var0.getPackageName();
         var1.putExtra("", var4);
         long var6 = System.currentTimeMillis();
         var1.putExtra("timestamp", var6);
         var0.sendBroadcast(var1);
         setHasVoiceLoggingInfo((boolean)0);
      }
   }
}
