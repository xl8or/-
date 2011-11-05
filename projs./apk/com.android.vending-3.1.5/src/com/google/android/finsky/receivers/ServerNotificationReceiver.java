package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.utils.Notifier;

public class ServerNotificationReceiver extends BroadcastReceiver {

   private static final String ASSET_ID_KEY = "assetid";
   private static final String SERVER_DIALOG_MESSAGE_KEY = "server_dialog_message";
   private static final String SERVER_DIALOG_TITLE_KEY = "server_dialog_title";
   private static final String SERVER_NOTIFICATION_CATEGORY = "SERVER_NOTIFICATION";
   private static final String SERVER_NOTIFICATION_MESSAGE_KEY = "server_notification_message";
   private static final String SERVER_NOTIFICATION_STATUS_KEY = "server_notification_status";
   private static final String SERVER_NOTIFICATION_TITLE_KEY = "server_notification_title";
   private static Notifier sNotificationManager;


   public ServerNotificationReceiver() {}

   public static void initialize(Notifier var0) {
      sNotificationManager = var0;
   }

   public void onReceive(Context var1, Intent var2) {
      if(var2.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
         String var3 = var2.getStringExtra("from");
         if("google.com".equals(var3)) {
            if(var2.getCategories().contains("SERVER_NOTIFICATION")) {
               this.setResultCode(-1);
               String var4 = var2.getStringExtra("server_notification_message");
               String var5 = var2.getStringExtra("server_dialog_message");
               if(var4 != null) {
                  if(var5 != null) {
                     String var6;
                     if(var2.hasExtra("server_notification_status")) {
                        var6 = var2.getStringExtra("server_notification_status");
                     } else {
                        var6 = var1.getString(2131231098);
                     }

                     String var7;
                     if(var2.hasExtra("server_notification_title")) {
                        var7 = var2.getStringExtra("server_notification_title");
                     } else {
                        var7 = var1.getString(2131231099);
                     }

                     sNotificationManager.showMessage(var7, var6, var4);
                  }
               }
            }
         }
      }
   }
}
