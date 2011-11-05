package com.htc.android.mail;

import android.content.Context;
import android.content.Intent;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.util.ArrayList;

public class MailEventBroadcaster {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "MailEventBroadcaster";
   private boolean mAccountChange = 0;
   private long mAccountId;
   ArrayList<Long> mAccounts;
   private boolean mMailSyncFinish = 0;
   private boolean mMailSyncStart = 0;
   private boolean mMarkFlagChange = 0;
   private boolean mMessageChange = 0;
   ArrayList<Long> mMessages;
   private boolean mReadFlagChange = 0;


   public MailEventBroadcaster() {
      ArrayList var1 = new ArrayList();
      this.mAccounts = var1;
      ArrayList var2 = new ArrayList();
      this.mMessages = var2;
      this.mAccountId = 0L;
   }

   public static void sendUnreadMailCount(Context var0, int var1) {
      if(DEBUG) {
         String var2 = "sendUnreadMailCount, unreadNumber: " + var1;
         ll.i("MailEventBroadcaster", var2);
      }

      Intent var3 = new Intent("com.htc.android.mail.action.update_unread_mail_count");
      var3.putExtra("unread_mail_count", var1);
      var0.sendStickyBroadcast(var3);
   }

   public void flush(Context var1) {
      boolean var2 = false;
      Intent var3 = new Intent("com.htc.android.mail.intent.change");
      if(this.mAccountChange) {
         Intent var4 = var3.putExtra("accountChange", (boolean)1);
         long var5 = this.mAccountId;
         var3.putExtra("accountId", var5);
         var2 = true;
         if(DEBUG) {
            ll.d("MailEventBroadcaster", "set account change broadcast");
         }

         this.mAccountChange = (boolean)0;
         this.mAccounts.clear();
      }

      if(this.mMessageChange || this.mReadFlagChange || this.mMarkFlagChange) {
         Intent var8 = var3.putExtra("messageChange", (boolean)1);
         var2 = true;
         if(DEBUG) {
            ll.d("MailEventBroadcaster", "set message change broadcast");
         }

         this.mMessageChange = (boolean)0;
         this.mReadFlagChange = (boolean)0;
         this.mMarkFlagChange = (boolean)0;
         this.mMessages.clear();
      }

      if(this.mMailSyncStart) {
         Intent var9 = var3.putExtra("syncStart", (boolean)1);
         long var10 = this.mAccountId;
         var3.putExtra("accountId", var10);
         var2 = true;
         if(DEBUG) {
            ll.d("MailEventBroadcaster", "set sync start broadcast");
         }

         this.mMailSyncStart = (boolean)0;
      }

      if(this.mMailSyncFinish) {
         Intent var13 = var3.putExtra("syncFinish", (boolean)1);
         long var14 = this.mAccountId;
         var3.putExtra("accountId", var14);
         var2 = true;
         if(DEBUG) {
            ll.d("MailEventBroadcaster", "set sync finish broadcast");
         }

         this.mMailSyncFinish = (boolean)0;
      }

      if(var2) {
         var1.sendBroadcast(var3);
         if(DEBUG) {
            ll.d("MailEventBroadcaster", "sent mail event broadcast");
         }
      }
   }

   public void markStar(Context var1, long var2, long[] var4) {
      Intent var5 = new Intent("com.htc.android.mail.intent.change");
      var5.putExtra("accountId", var2);
      var5.putExtra("starMark", var4);
      var1.sendBroadcast(var5);
   }

   public void sendDeleteMailIntent(Context var1, long var2, long[] var4) {
      Intent var5 = new Intent("com.htc.android.mail.intent.change");
      var5.putExtra("accountId", var2);
      var5.putExtra("messageDelete", var4);
      var1.sendBroadcast(var5);
   }

   public void sendMarkStarIntent(Context var1, long var2, long[] var4, int var5) {
      Intent var6 = new Intent("com.htc.android.mail.intent.change");
      var6.putExtra("accountId", var2);
      if(var5 == null) {
         var6.putExtra("starMark", var4);
      } else {
         var6.putExtra("starUnmark", var4);
      }

      var1.sendBroadcast(var6);
   }

   public void sendReloadMailIntent(Context var1, long var2, long[] var4) {
      Intent var5 = new Intent("com.htc.android.mail.intent.change");
      var5.putExtra("accountId", var2);
      var5.putExtra("messageReload", var4);
      var1.sendBroadcast(var5);
   }

   public void sendSetReadStatusIntent(Context var1, long var2, long[] var4, int var5) {
      Intent var6 = new Intent("com.htc.android.mail.intent.change");
      var6.putExtra("accountId", var2);
      if(var5 == null) {
         var6.putExtra("messageRead", var4);
      } else {
         var6.putExtra("messageUnread", var4);
      }

      var1.sendBroadcast(var6);
   }

   public void sendSettingChangedIntent(Context var1, long var2) {
      Intent var4 = new Intent("com.htc.android.mail.intent.change");
      var4.putExtra("accountId", var2);
      Intent var6 = var4.putExtra("settingChange", (boolean)1);
      var1.sendBroadcast(var4);
   }

   public void setAccountChange() {
      this.mAccountChange = (boolean)1;
      this.mAccountId = 65535L;
   }

   public void setAccountChange(long var1) {
      this.mAccountChange = (boolean)1;
      ArrayList var3 = this.mAccounts;
      Long var4 = Long.valueOf(var1);
      var3.add(var4);
      this.mAccountId = 65535L;
   }

   public void setAccountID(long var1) {
      this.mAccountId = var1;
   }

   public void setMailSyncFinish(long var1) {
      this.mAccountId = var1;
      this.mMailSyncFinish = (boolean)1;
   }

   public void setMailSyncStart(long var1) {
      this.mAccountId = var1;
      this.mMailSyncStart = (boolean)1;
   }

   public void setMarkFlagChange() {
      this.mMarkFlagChange = (boolean)1;
   }

   public void setMarkFlagChange(long var1) {
      this.mMarkFlagChange = (boolean)1;
      ArrayList var3 = this.mMessages;
      Long var4 = Long.valueOf(var1);
      var3.add(var4);
   }

   public void setMessageChange() {
      this.mMessageChange = (boolean)1;
   }

   public void setMessageChange(long var1) {
      this.mMessageChange = (boolean)1;
      ArrayList var3 = this.mMessages;
      Long var4 = Long.valueOf(var1);
      var3.add(var4);
   }

   public void setReadFlagChange() {
      this.mReadFlagChange = (boolean)1;
   }

   public void setReadFlagChange(long var1) {
      this.mReadFlagChange = (boolean)1;
      ArrayList var3 = this.mMessages;
      Long var4 = Long.valueOf(var1);
      var3.add(var4);
   }
}
