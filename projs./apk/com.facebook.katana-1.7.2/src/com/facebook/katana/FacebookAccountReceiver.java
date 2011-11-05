package com.facebook.katana;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.Log;

public class FacebookAccountReceiver extends BroadcastReceiver {

   private static final String TAG = "FacebookAccountReceiver";


   public FacebookAccountReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      if(Log.isLoggable("FacebookAccountReceiver", 3)) {
         Log.d("FacebookAccountReceiver", "----> onReceive");
      }

      String var3 = UserValuesManager.getCurrentAccount(var1);
      if(var3 == null) {
         if(!Log.isLoggable("FacebookAccountReceiver", 3)) {
            return;
         }

         Log.d("FacebookAccountReceiver", "Account does not exist.");
      } else {
         if(FacebookAuthenticationService.getAccount(var1, var3) != null) {
            if(!Log.isLoggable("FacebookAccountReceiver", 3)) {
               return;
            }

            String var4 = "Account still exists: " + var3;
            Log.d("FacebookAccountReceiver", var4);
            return;
         }

         AppSession var5 = AppSession.getActiveSession(var1, (boolean)0);
         if(var5 == null) {
            return;
         }

         if(Log.isLoggable("FacebookAccountReceiver", 3)) {
            StringBuilder var6 = (new StringBuilder()).append("Session status: ");
            AppSession.LoginStatus var7 = var5.getStatus();
            String var8 = var6.append(var7).toString();
            Log.d("FacebookAccountReceiver", var8);
         }

         int[] var9 = FacebookAccountReceiver.1.$SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
         int var10 = var5.getStatus().ordinal();
         switch(var9[var10]) {
         case 1:
            break;
         case 2:
            if(Log.isLoggable("FacebookAccountReceiver", 3)) {
               String var11 = "Logging out: " + var3;
               Log.d("FacebookAccountReceiver", var11);
            }

            var5.authLogout(var1);
            return;
         default:
            return;
         }
      }

   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus = new int[AppSession.LoginStatus.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var1 = AppSession.LoginStatus.STATUS_LOGGING_IN.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var3 = AppSession.LoginStatus.STATUS_LOGGED_IN.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var5 = AppSession.LoginStatus.STATUS_LOGGED_OUT.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus;
            int var7 = AppSession.LoginStatus.STATUS_LOGGING_OUT.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }
}
