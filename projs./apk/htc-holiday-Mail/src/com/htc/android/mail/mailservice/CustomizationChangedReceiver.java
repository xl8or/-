package com.htc.android.mail.mailservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.htc.android.mail.HtcMailCustomization;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;

public class CustomizationChangedReceiver extends BroadcastReceiver {

   private static final String CASE_FOTA_UPGRADE = "com.htc.FOTA_UPGRADE";
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String INTENT_ACTION_CUSTOMIZATION = "android.htc.intent.action.CUSTOMIZATION_CHANGE";
   private static final String INTENT_ACTION_CUSTOMIZATION_FORCE = "android.htc.intent.action.CUSTOMIZATION_FORCE_CHANGE";
   private static final String KEY_CUSTOMIZED_REASON = "com.htc.CUSTOMIZED_REASON";
   static final String TAG = "MailCustomizationChangedReceiver";


   public CustomizationChangedReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      if(DEBUG) {
         String var3 = "Mail Receiver get CUSTOMIZATION_CHANGE: " + var2;
         ll.d("MailCustomizationChangedReceiver", var3);
      }

      String var4 = var2.getAction();
      if("android.htc.intent.action.CUSTOMIZATION_FORCE_CHANGE".equals(var4)) {
         Context var5 = var1.getApplicationContext();
         (new CustomizationChangedReceiver.ReLoadCustomizationThread(var5)).start();
      }
   }

   public static class ReLoadCustomizationThread extends Thread {

      private Context mContext;


      public ReLoadCustomizationThread(Context var1) {
         this.mContext = var1;
      }

      public void run() {
         HtcMailCustomization var1 = new HtcMailCustomization();
         Context var2 = this.mContext;
         Bundle var3 = var1.getMailCustomizationData(var2);
         Context var4 = this.mContext;
         var1.insertMailProviderCustomizationData(var4, var3);
         Context var5 = this.mContext;
         var1.insertMailProviderSettingCustomizationData(var5, var3);
         Context var6 = this.mContext;
         var1.loadCustSignature(var6, var3);
         Context var7 = this.mContext;
         var1.preInstallAccount(var7, var3);
      }
   }
}
