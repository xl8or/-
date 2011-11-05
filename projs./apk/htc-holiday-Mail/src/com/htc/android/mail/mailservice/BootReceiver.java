package com.htc.android.mail.mailservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.NewMailNotification;
import com.htc.android.mail.SMTPAccountErrorNotification;
import com.htc.android.mail.SendErrorNotification;
import com.htc.android.mail.ll;
import com.htc.android.mail.mailservice.MailService;

public class BootReceiver extends BroadcastReceiver {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   static final String TAG = "MailBootReceiver";


   public BootReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      if(DEBUG) {
         ll.d("MailBootReceiver", "Mail Receiver get ACTION_BOOT_COMPLETED");
      }

      label24: {
         String var4 = var2.getAction();
         if(!"android.intent.action.BOOT_COMPLETED".equals(var4)) {
            String var5 = var2.getAction();
            if(!"android.intent.action.QUICKBOOT_POWERON".equals(var5)) {
               String var7 = var2.getAction();
               if("android.intent.action.DEVICE_STORAGE_LOW".equals(var7)) {
                  MailService.actionCancel(var1);
               } else {
                  String var8 = var2.getAction();
                  if("android.intent.action.DEVICE_STORAGE_OK".equals(var8)) {
                     Mail.setServicesEnabled(var1);
                  }
               }
               break label24;
            }
         }

         BootReceiver.1 var6 = new BootReceiver.1(var1);
         (new Thread(var6)).start();
      }

      if(DEBUG) {
         ll.d("MailBootReceiver", "bootReceiver");
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Context val$myContext;


      1(Context var2) {
         this.val$myContext = var2;
      }

      public void run() {
         Account.updateNextFetchTimes(this.val$myContext);
         Mail.setServicesEnabled(this.val$myContext);
         if(BootReceiver.DEBUG) {
            ll.d("MailBootReceiver", " try reschedule peak!");
         }

         MailService.actionReschedulePeak(this.val$myContext);
         if(BootReceiver.DEBUG) {
            ll.d("MailBootReceiver", "Mail Receiver get ACTION_BOOT_COMPLETED");
         }

         NewMailNotification.showAllNewMailNotificaition(this.val$myContext);
         SMTPAccountErrorNotification.showAllSMTPAccountErrorNotificaition(this.val$myContext);
         SendErrorNotification.showAllSendErrorNotificaition(this.val$myContext);
      }
   }
}
