package com.android.exchange;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.android.email.service.MailService;

public class CalendarSyncEnabler {

   private final Context mContext;


   public CalendarSyncEnabler(Context var1) {
      this.mContext = var1;
   }

   private Intent createLaunchCalendarIntent() {
      Uri var1 = Uri.parse("content://com.android.calendar/time");
      return new Intent("android.intent.action.VIEW", var1);
   }

   public final void enableEasCalendarSync() {
      String var1 = this.enableEasCalendarSyncInternal();
      if(var1.length() > 0) {
         this.showNotification(var1);
      }
   }

   final String enableEasCalendarSyncInternal() {
      StringBuilder var1 = new StringBuilder();
      Account[] var2 = AccountManager.get(this.mContext).getAccountsByType("com.android.exchange");
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Account var5 = var2[var4];
         String var6 = var5.name;
         String var7 = "Enabling Exchange calendar sync for " + var6;
         int var8 = Log.i("Email", var7);
         ContentResolver.setIsSyncable(var5, "com.android.calendar", 1);
         ContentResolver.setSyncAutomatically(var5, "com.android.calendar", (boolean)1);
         if(var1.length() > 0) {
            StringBuilder var9 = var1.append(' ');
         }

         var1.append(var6);
      }

      return var1.toString();
   }

   void showNotification(String var1) {
      Context var2 = this.mContext;
      Intent var3 = this.createLaunchCalendarIntent();
      PendingIntent var4 = PendingIntent.getActivity(var2, 0, var3, 0);
      String var5 = this.mContext.getString(2131166689);
      long var6 = System.currentTimeMillis();
      Notification var8 = new Notification(2130838097, var5, var6);
      Context var9 = this.mContext;
      var8.setLatestEventInfo(var9, var5, var1, var4);
      var8.flags = 16;
      NotificationManager var10 = (NotificationManager)this.mContext.getSystemService("notification");
      int var11 = MailService.NOTIFICATION_ID_EXCHANGE_CALENDAR_ADDED;
      var10.notify(var11, var8);
   }
}
