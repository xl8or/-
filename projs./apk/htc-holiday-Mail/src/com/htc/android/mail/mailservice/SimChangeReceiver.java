package com.htc.android.mail.mailservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;

public class SimChangeReceiver extends BroadcastReceiver {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   static final String TAG = "SimChangeReceiver";


   public SimChangeReceiver() {}

   private void handler139MailAccountChanged(Context var1, long var2) {
      Intent var4 = new Intent();
      Intent var5 = var4.setClassName("com.htc.android.mail", "com.htc.android.mail.chinamail.China139AccountVerify");
      Intent var6 = var4.setAction("com.htc.android.mail.intent.action.VERIFY_139_MAIL");
      Intent var7 = var4.setFlags(268435456);
      var4.putExtra("accountId", var2);
      var1.startActivity(var4);
   }

   private boolean isIMSIChanged(Context var1, String var2) {
      boolean var3;
      if(var1 != null && var2 != null && !var2.equals("")) {
         String var4 = Util.getIMSIFromPref(var1);
         if(var4 != null && !var4.equals("")) {
            if(var4.equals(var2)) {
               var3 = false;
            } else {
               if(DEBUG) {
                  String var5 = "SIM changed :" + var2;
                  ll.d("SimChangeReceiver", var5);
               }

               Util.writeIMSIToPref(var1, var2);
               var3 = true;
            }
         } else {
            if(DEBUG) {
               ll.d("SimChangeReceiver", "last IMSI is empty");
            }

            Util.writeIMSIToPref(var1, var2);
            var3 = false;
         }
      } else {
         if(DEBUG) {
            ll.d("SimChangeReceiver", "SIM IMSI is null");
         }

         var3 = false;
      }

      return var3;
   }

   public void onReceive(Context var1, Intent var2) {
      if(DEBUG) {
         ll.d("SimChangeReceiver", "Mail Sim change Receiver");
      }

      String var3 = var2.getAction();
      if("android.intent.action.SIM_STATE_CHANGED".equals(var3)) {
         String var4 = ((TelephonyManager)var1.getSystemService("phone")).getSubscriberId();
         if(DEBUG) {
            String var5 = " imsi:" + var4;
            ll.d("SimChangeReceiver", var5);
         }

         if(!this.isIMSIChanged(var1, var4)) {
            return;
         }

         int var6 = Account.notify139SimChanged(var1);
         if(var6 > 0) {
            long var7 = Account.findNeedVerify139Account();
            if(DEBUG) {
               String var9 = "Sim Changed and change 139 id:" + var7;
               ll.d("SimChangeReceiver", var9);
            }

            if(var7 > 0L) {
               this.handler139MailAccountChanged(var1, var7);
            }
         }

         if(DEBUG) {
            String var10 = "Sim Changed and change 139 status:" + var6;
            ll.d("SimChangeReceiver", var10);
         }
      }

      if(DEBUG) {
         ll.d("SimChangeReceiver", "Mail Sim change Receiver end");
      }
   }
}
