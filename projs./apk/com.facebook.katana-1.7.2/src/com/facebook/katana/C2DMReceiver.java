package com.facebook.katana;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookPushNotification;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMParser;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class C2DMReceiver extends BroadcastReceiver {

   private static final String MSG_EXTRA_PAYLOAD = "notification";
   private static final String MSG_RCV = "com.google.android.c2dm.intent.RECEIVE";
   private static final String NOTIFICATION_SENDER = "facebook.android@gmail.com";
   private static final String REG_EXTRA_ERROR = "error";
   private static final String REG_EXTRA_ID = "registration_id";
   private static final String REG_EXTRA_UNREG = "unregistered";
   private static final String REG_INTENT = "com.google.android.c2dm.intent.REGISTER";
   private static final String REG_PARAM_APP = "app";
   private static final String REG_PARAM_SENDER = "sender";
   private static final String REG_RCV = "com.google.android.c2dm.intent.REGISTRATION";
   private static final String TAG = "C2DMReceiver";
   private static final String UNREG_INTENT = "com.google.android.c2dm.intent.UNREGISTER";


   public C2DMReceiver() {}

   public static void getClientLogin(Context var0) {
      if(KeyValueManager.getValue(var0, "C2DMKey", (String)null) == null) {
         Intent var1 = new Intent("com.google.android.c2dm.intent.REGISTER");
         Intent var2 = new Intent();
         PendingIntent var3 = PendingIntent.getBroadcast(var0, 0, var2, 0);
         var1.putExtra("app", var3);
         Intent var5 = var1.putExtra("sender", "facebook.android@gmail.com");
         var0.startService(var1);
      }
   }

   private void handleMessage(Context var1, Intent var2) {
      if(AppSession.getActiveSession(var1, (boolean)0) != null) {
         String var3 = var2.getStringExtra("notification");
         JsonFactory var4 = new JsonFactory();
         if(var3 != null) {
            try {
               JsonParser var5 = var4.createJsonParser(var3);
               JsonToken var6 = var5.nextToken();
               ((FacebookPushNotification)JMParser.parseObjectJson(var5, FacebookPushNotification.class)).showNotification(var1);
            } catch (Exception var11) {
               StringBuilder var8 = (new StringBuilder()).append("Exception parsing push notification: ");
               String var9 = var11.getMessage();
               String var10 = var8.append(var9).toString();
               Log.e("C2DMReceiver", var10, var11);
            }
         }
      }
   }

   private void handleRegistration(Context var1, Intent var2) {
      String var3 = var2.getStringExtra("registration_id");
      if(var2.getStringExtra("error") == null) {
         AppSession var4;
         if(var2.getStringExtra("unregistered") != null) {
            var4 = AppSession.getActiveSession(var1, (boolean)0);
            if(var4 != null) {
               var4.unregisterForPush(var1);
            }
         } else if(var3 != null) {
            var4 = AppSession.getActiveSession(var1, (boolean)0);
            if(var4 != null) {
               var4.registerForPush(var1, var3);
            }
         }
      }
   }

   public static void logout(Context var0) {
      Intent var1 = new Intent("com.google.android.c2dm.intent.UNREGISTER");
      Intent var2 = new Intent();
      PendingIntent var3 = PendingIntent.getBroadcast(var0, 0, var2, 0);
      var1.putExtra("app", var3);
      var0.startService(var1);
   }

   public void onReceive(Context var1, Intent var2) {
      if(var2.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
         this.handleRegistration(var1, var2);
      } else if(var2.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
         this.handleMessage(var1, var2);
      }
   }
}
