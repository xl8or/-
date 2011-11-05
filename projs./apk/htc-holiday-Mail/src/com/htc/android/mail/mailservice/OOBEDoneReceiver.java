package com.htc.android.mail.mailservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.htc.android.mail.HtcMailCustomization;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;

public class OOBEDoneReceiver extends BroadcastReceiver {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   static final String TAG = "OOBEDoneReceiver";


   public OOBEDoneReceiver() {}

   private void loadpreInstallMessage(Context var1) {
      HtcMailCustomization var2 = new HtcMailCustomization();
      Bundle var3 = var2.getMailCustomizationData(var1);
      if(var3 != null) {
         var2.preInstallMessage(var1, var3);
      }
   }

   public void onReceive(Context var1, Intent var2) {
      if(DEBUG) {
         ll.d("OOBEDoneReceiver", "Mail Receiver get OOBE done");
      }

      if(var2 != null) {
         label21: {
            String var3 = var2.getAction();
            if("android.htc.intent.action.SETUP_WIZARD_FINISHED".equals(var3)) {
               String var4 = var2.getStringExtra("SetupWizardLaunchedBy");
               if("LaunchedBySystem".equals(var4)) {
                  this.loadpreInstallMessage(var1);
                  break label21;
               }
            }

            ll.d("OOBEDoneReceiver", "Not first time finish");
         }

         if(DEBUG) {
            ll.d("OOBEDoneReceiver", "OOBEDoneReceiver end");
         }
      }
   }
}
