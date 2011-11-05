package com.google.android.finsky.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.utils.FinskyLog;

public final class NotificationUtils {

   public NotificationUtils() {}

   public static void showNotification(Context var0, Intent var1, String var2, String var3, String var4, String var5, int var6) {
      String var7;
      if(var2 == null) {
         var7 = "";
      } else {
         var7 = var2;
      }

      int var8 = var7.hashCode();
      PendingIntent var9 = PendingIntent.getActivity(var0, var8, var1, 1073741824);
      long var10 = System.currentTimeMillis();
      Notification var12 = new Notification(var6, var3, var10);
      int var13 = var12.flags | 16;
      var12.flags = var13;
      var12.setLatestEventInfo(var0, var4, var5, var9);
      android.app.NotificationManager var14 = (android.app.NotificationManager)var0.getSystemService("notification");
      var12.setLatestEventInfo(var0, var4, var5, var9);
      int var15 = var7.hashCode();
      var14.notify(var15, var12);
      Object[] var16 = new Object[4];
      var16[0] = var2;
      Integer var17 = Integer.valueOf(var15);
      var16[1] = var17;
      var16[2] = var4;
      var16[3] = var5;
      FinskyLog.d("Showing notification: [AssetID=%s, NotificationID=%d, Title=%s, Message=%s]", var16);
   }
}
