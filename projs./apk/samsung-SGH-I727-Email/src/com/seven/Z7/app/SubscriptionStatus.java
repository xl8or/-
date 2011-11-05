package com.seven.Z7.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import com.seven.Z7.shared.ANSharedCommon;
import com.seven.Z7.shared.Z7DBPrefsEditor;
import com.seven.Z7.util.Z7ErrorCode2;

public class SubscriptionStatus {

   public static final String GLOBAL_NEXT_SUBSCRIPTION_RENEWAL_REMINDER_TIME_KEY = "next_activation_reminder_time_key";
   public static final String GLOBAL_SUBSCRIPTION_DISCLAIMER_ACCEPTED = "subscription_disclaimer_accepted";
   private static final String REMINDER_NOTIFICATION_FLAG = "reminder_notification_flag";
   private static final int REMINDER_NOTIFICATION_ID = 1;
   public static final boolean isLocalSubscriptionRenewalAvailable;


   static {
      byte var0;
      if(ANSharedCommon.getInt(2131230732) == 1) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      isLocalSubscriptionRenewalAvailable = (boolean)var0;
   }

   public SubscriptionStatus() {}

   public static void cancelSubscriptionRenewalReminder(Context var0) {
      Z7DBPrefsEditor var1 = ANSharedCommon.getUISharedPreferences().edit();
      Editor var2 = var1.putLong("next_activation_reminder_time_key", 65535L);
      boolean var3 = var1.commit();
      AlarmManager var4 = (AlarmManager)var0.getSystemService("alarm");
      PendingIntent var5 = getPendingIntent(var0);
      var4.cancel(var5);
      ((NotificationManager)var0.getSystemService("notification")).cancel("reminder_notification_flag", 1);
   }

   private static PendingIntent getPendingIntent(Context var0) {
      Intent var1 = new Intent(var0, SubscriptionStatus.class);
      return PendingIntent.getBroadcast(var0, 0, var1, 0);
   }

   public static Dialog getSubscriptionExpiredDialog(Activity var0, boolean var1) {
      Builder var2 = (new Builder(var0)).setIcon(17301543).setTitle(2131166091).setMessage(2131166093);
      SubscriptionStatus.3 var3 = new SubscriptionStatus.3();
      Builder var4 = var2.setPositiveButton(2131165471, var3);
      SubscriptionStatus.2 var5 = new SubscriptionStatus.2();
      Builder var6 = var4.setNegativeButton(2131165470, var5);
      SubscriptionStatus.1 var7 = new SubscriptionStatus.1(var0, var1);
      return var6.setOnCancelListener(var7).create();
   }

   public static boolean hasExpiredAccounts(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean hasPendingSubscriptionRenewalReminder() {
      long var0 = ANSharedCommon.getUISharedPreferences().getLong("next_activation_reminder_time_key", 65535L);
      long var2 = System.currentTimeMillis();
      boolean var4;
      if(var0 > var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static boolean isApplicationUsageDisclaimerAccepted() {
      return ANSharedCommon.getUISharedPreferences().getBoolean("subscription_disclaimer_accepted", (boolean)0);
   }

   public static void requestSubscriptionRenewalReminder(Context var0) {
      int var1 = ANSharedCommon.getInt(2131230731);
      long var2 = System.currentTimeMillis();
      long var4 = (long)(var1 * 60 * 1000);
      long var6 = var2 + var4;
      Z7DBPrefsEditor var8 = ANSharedCommon.getUISharedPreferences().edit();
      var8.putLong("next_activation_reminder_time_key", var6);
      boolean var10 = var8.commit();
      AlarmManager var11 = (AlarmManager)var0.getSystemService("alarm");
      PendingIntent var12 = getPendingIntent(var0);
      var11.set(1, var6, var12);
   }

   public static void setApplicationUsageDisclaimerAccepted(boolean var0) {
      Z7DBPrefsEditor var1 = ANSharedCommon.getUISharedPreferences().edit();
      var1.putBoolean("subscription_disclaimer_accepted", var0);
      boolean var3 = var1.commit();
   }

   public static boolean showConsultYourCarrierDialog(int var0, Activity var1, boolean var2) {
      boolean var3 = false;
      if(!isLocalSubscriptionRenewalAvailable) {
         int var4 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_TRIALS_NOT_ACCEPTED.getValue();
         if(var0 == var4) {
            showContactCarrierErrorDialog(2131166110, 2131166109, var1, var2);
            var3 = true;
         } else {
            int var5 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_LIMIT_EXCEEDED.getValue();
            if(var0 == var5) {
               showContactCarrierErrorDialog(2131166095, 2131166094, var1, var2);
               var3 = true;
            } else {
               int var6 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_EXPIRED.getValue();
               if(var0 == var6) {
                  showContactCarrierErrorDialog(2131166091, 2131166093, var1, var2);
                  var3 = true;
               } else {
                  int var7 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SERVICE_SUBSCRIPTION_REQUIRED.getValue();
                  if(var0 == var7) {
                     showContactCarrierErrorDialog(2131166102, 2131166101, var1, var2);
                     var3 = true;
                  }
               }
            }
         }
      }

      return var3;
   }

   private static void showContactCarrierErrorDialog(int var0, int var1, Activity var2, boolean var3) {
      Builder var4 = (new Builder(var2)).setIcon(17301543).setTitle(var0).setMessage(var1);
      SubscriptionStatus.5 var5 = new SubscriptionStatus.5();
      Builder var6 = var4.setNeutralButton(2131165467, var5);
      SubscriptionStatus.4 var7 = new SubscriptionStatus.4(var3, var2);
      var6.setOnCancelListener(var7).create().show();
   }

   static class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
      }
   }

   static class 4 implements OnCancelListener {

      // $FF: synthetic field
      final boolean val$finishActivity;
      // $FF: synthetic field
      final Activity val$parentActivity;


      4(boolean var1, Activity var2) {
         this.val$finishActivity = var1;
         this.val$parentActivity = var2;
      }

      public void onCancel(DialogInterface var1) {
         if(this.val$finishActivity) {
            this.val$parentActivity.finish();
         }
      }
   }

   static class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {}
   }

   static class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
      }
   }

   static class 1 implements OnCancelListener {

      // $FF: synthetic field
      final boolean val$finishActivity;
      // $FF: synthetic field
      final Activity val$parentActivity;


      1(Activity var1, boolean var2) {
         this.val$parentActivity = var1;
         this.val$finishActivity = var2;
      }

      public void onCancel(DialogInterface var1) {
         SubscriptionStatus.requestSubscriptionRenewalReminder(this.val$parentActivity);
         if(this.val$finishActivity) {
            this.val$parentActivity.finish();
         }
      }
   }
}
