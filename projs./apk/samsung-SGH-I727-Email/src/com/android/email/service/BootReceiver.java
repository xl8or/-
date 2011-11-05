package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.service.MailService;

public class BootReceiver extends BroadcastReceiver {

   public BootReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      AccountBackupRestore.restoreAccountsIfNeeded(var1);
      String var3 = var2.getAction();
      if(!"android.intent.action.BOOT_COMPLETED".equals(var3)) {
         String var4 = var2.getAction();
         if(!"com.android.email.RESTART_SYNCMANAGER".equals(var4)) {
            String var5 = var2.getAction();
            if("android.intent.action.DEVICE_STORAGE_LOW".equals(var5)) {
               MailService.actionCancel(var1);
               return;
            }

            String var6 = var2.getAction();
            if(!"android.intent.action.DEVICE_STORAGE_OK".equals(var6)) {
               return;
            }

            MailService.actionReschedule(var1);
            return;
         }
      }

      if(Email.setServicesEnabled(var1)) {
         MailService.actionReschedule(var1);
      }
   }
}
