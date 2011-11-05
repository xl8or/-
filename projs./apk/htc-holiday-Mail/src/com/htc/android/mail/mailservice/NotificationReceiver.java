package com.htc.android.mail.mailservice;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailListScreen;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import org.kxml2.wap.WbxmlParser;

public class NotificationReceiver extends BroadcastReceiver {

   private static final boolean DEBUG;
   public static final String[] EMNattrStartTable;
   public static final String[] EMNattrValueTable;
   public static final String[] EMNtagTable;


   static {
      String[] var0 = new String[]{"emn"};
      EMNtagTable = var0;
      String[] var1 = new String[]{"timestamp", "mailbox", "mailbox=mailat:", "mailbox=pop://", "mailbox=imap://", "mailbox=http://", "mailbox=http://www.", "mailbox=https://", "mailbox=https://www."};
      EMNattrStartTable = var1;
      String[] var2 = new String[]{".com", ".edu", ".net", ".org"};
      EMNattrValueTable = var2;
      DEBUG = Mail.MAIL_DEBUG;
   }

   public NotificationReceiver() {}

   private Bundle getAccountId(ContentValues var1) {
      Bundle var2;
      if(var1.get("mailbox") == null) {
         var2 = null;
      } else {
         Cursor var3 = MailProvider.getAccounts();

         label121: {
            label122: {
               Bundle var16;
               try {
                  if(var3.getCount() <= 0) {
                     break label122;
                  }

                  while(true) {
                     if(!var3.moveToNext()) {
                        break label121;
                     }

                     if(var1.get("mailbox").toString().toLowerCase().startsWith("mailat:")) {
                        String var4 = var1.get("mailbox").toString();
                        StringBuilder var5 = (new StringBuilder()).append("mailat:");
                        int var6 = var3.getColumnIndexOrThrow("_emailaddress");
                        String var7 = var3.getString(var6);
                        String var8 = var5.append(var7).toString();
                        if(var4.equalsIgnoreCase(var8)) {
                           int var9 = var3.getColumnIndexOrThrow("_id");
                           long var10 = var3.getLong(var9);
                           int var12 = var3.getColumnIndexOrThrow("_provider");
                           String var13 = var3.getString(var12);
                           int var14 = var3.getColumnIndexOrThrow("_protocol");
                           boolean var15 = Mail.isIMAP4(var3.getInt(var14));
                           var16 = new Bundle();
                           var16.putString("provider", var13);
                           var16.putLong("id", var10);
                           var16.putBoolean("isIMAP4", var15);
                           break;
                        }
                     }
                  }
               } finally {
                  if(var3 != null && !var3.isClosed()) {
                     var3.close();
                  }

               }

               var2 = var16;
               return var2;
            }

            var2 = null;
            return var2;
         }

         var2 = null;
      }

      return var2;
   }

   private ContentValues parseNotification(WbxmlParser var1, Intent var2) {
      return null;
   }

   public void onReceive(Context var1, Intent var2) {
      String var3 = var2.getAction();
      if("android.provider.Telephony.WAP_PUSH_RECEIVED".equals(var3)) {
         WbxmlParser var4 = new WbxmlParser();
         ContentValues var5 = this.parseNotification(var4, var2);
         if(var5 != null) {
            Bundle var6 = this.getAccountId(var5);
            if(var6 == null) {
               if(DEBUG) {
                  ll.d("NotificationReceiver", "No account match.");
               }
            } else {
               StringBuilder var7 = (new StringBuilder()).append("content://mail/accounts/");
               long var8 = var6.getLong("id");
               Uri var10 = Uri.parse(var7.append(var8).toString());
               Intent var11 = new Intent("android.intent.action.VIEW", var10);
               Intent var12 = var11.setFlags(268435456);
               var11.setClass(var1, MailListScreen.class);
               boolean var14 = var6.getBoolean("isIMAP4");
               var11.putExtra("_isIMAP4", var14);
               String var16 = var6.getString("provider");
               var11.putExtra("provider", var16);
               var1.startActivity(var11);
            }
         }
      } else {
         String var18 = var2.getAction();
         if("android.intent.action.BOOT_COMPLETED".equals(var18)) {
            if(DEBUG) {
               ll.d("NotificationReceiver", "test");
            }
         } else {
            String var19 = var2.getAction();
            if("android.net.wifi.SMART_WIFI_STATE_CHANGED".equals(var19)) {
               if(DEBUG) {
                  ll.d("NotificationReceiver", "smartWifi recieved");
               }

               Intent var20 = new Intent();
               Intent var21 = var20.setClassName("com.htc.android.mail", "com.htc.android.mail.mailservice.MailService");
               Intent var22 = var20.setAction("android.net.wifi.SMART_WIFI_STATE_CHANGED");
               var20.putExtras(var2);
               var1.startService(var20);
            }
         }
      }
   }
}
