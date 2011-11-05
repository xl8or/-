package com.android.email;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;
import com.android.email.ExchangeUtils;
import com.android.email.MessagingController;
import com.android.email.MessagingListener;
import com.android.email.Utility;
import com.android.email.mail.AuthenticationFailedException;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.provider.AttachmentProvider;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailService;
import com.android.email.service.IEmailServiceCallback;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

public class Controller {

   private static int MESSAGEID_TO_ACCOUNTID_COLUMN_ACCOUNTID;
   private static String[] MESSAGEID_TO_ACCOUNTID_PROJECTION;
   private static int MESSAGEID_TO_MAILBOXID_COLUMN_MAILBOXID;
   private static String[] MESSAGEID_TO_MAILBOXID_PROJECTION;
   private static Controller sInstance;
   private final Context mContext;
   private final MessagingController mLegacyController;
   private final Controller.LegacyListener mLegacyListener;
   private final HashSet<Controller.Result> mListeners;
   private Context mProviderContext;
   private final Controller.ServiceCallback mServiceCallback;


   static {
      String[] var0 = new String[]{"_id", "accountKey"};
      MESSAGEID_TO_ACCOUNTID_PROJECTION = var0;
      MESSAGEID_TO_ACCOUNTID_COLUMN_ACCOUNTID = 1;
      String[] var1 = new String[]{"_id", "mailboxKey"};
      MESSAGEID_TO_MAILBOXID_PROJECTION = var1;
      MESSAGEID_TO_MAILBOXID_COLUMN_MAILBOXID = 1;
   }

   protected Controller(Context var1) {
      Controller.LegacyListener var2 = new Controller.LegacyListener((Controller.1)null);
      this.mLegacyListener = var2;
      Controller.ServiceCallback var3 = new Controller.ServiceCallback((Controller.1)null);
      this.mServiceCallback = var3;
      HashSet var4 = new HashSet();
      this.mListeners = var4;
      this.mContext = var1;
      this.mProviderContext = var1;
      MessagingController var5 = MessagingController.getInstance(this.mContext);
      this.mLegacyController = var5;
      MessagingController var6 = this.mLegacyController;
      Controller.LegacyListener var7 = this.mLegacyListener;
      var6.addListener(var7);
   }

   public static Controller getInstance(Context var0) {
      synchronized(Controller.class){}

      Controller var1;
      try {
         if(sInstance == null) {
            sInstance = new Controller(var0);
         }

         var1 = sInstance;
      } finally {
         ;
      }

      return var1;
   }

   private IEmailService getServiceForAccount(long var1) {
      EmailContent.Account var3 = EmailContent.Account.restoreAccountWithId(this.mProviderContext, var1);
      IEmailService var4;
      if(var3 != null && !this.isMessagingController(var3)) {
         Context var5 = this.mContext;
         Controller.ServiceCallback var6 = this.mServiceCallback;
         var4 = ExchangeUtils.getExchangeEmailService(var5, var6);
      } else {
         var4 = null;
      }

      return var4;
   }

   private IEmailService getServiceForMessage(long var1) {
      EmailContent.Message var3 = EmailContent.Message.restoreMessageWithId(this.mProviderContext, var1);
      IEmailService var4;
      if(var3 == null) {
         var4 = null;
      } else {
         long var5 = var3.mAccountKey;
         var4 = this.getServiceForAccount(var5);
      }

      return var4;
   }

   private boolean isActiveResultCallback(Controller.Result var1) {
      HashSet var2 = this.mListeners;
      synchronized(var2) {
         boolean var3 = this.mListeners.contains(var1);
         return var3;
      }
   }

   private long lookupAccountForMessage(long var1) {
      ContentResolver var3 = this.mProviderContext.getContentResolver();
      Uri var4 = EmailContent.Message.CONTENT_URI;
      String[] var5 = MESSAGEID_TO_ACCOUNTID_PROJECTION;
      String[] var6 = new String[1];
      String var7 = Long.toString(var1);
      var6[0] = var7;
      Cursor var8 = var3.query(var4, var5, "_id=?", var6, (String)null);
      boolean var16 = false;

      long var12;
      label42: {
         long var10;
         label41: {
            try {
               var16 = true;
               if(var8.moveToFirst()) {
                  int var9 = MESSAGEID_TO_ACCOUNTID_COLUMN_ACCOUNTID;
                  var10 = var8.getLong(var9);
                  var16 = false;
                  break label41;
               }

               var16 = false;
            } finally {
               if(var16) {
                  var8.close();
               }
            }

            var12 = 65535L;
            break label42;
         }

         var12 = var10;
      }

      var8.close();
      return var12;
   }

   public void addResultCallback(Controller.Result var1) {
      HashSet var2 = this.mListeners;
      synchronized(var2) {
         this.mListeners.add(var1);
      }
   }

   long createMailbox(long var1, int var3) {
      if(var1 >= 0L && var3 >= 0) {
         EmailContent.Mailbox var6 = new EmailContent.Mailbox();
         var6.mAccountKey = var1;
         var6.mType = var3;
         var6.mSyncInterval = -1;
         var6.mFlagVisible = (boolean)1;
         String var7 = this.getMailboxServerName(var3);
         var6.mDisplayName = var7;
         Context var8 = this.mProviderContext;
         var6.save(var8);
         return var6.mId;
      } else {
         String var4 = "Invalid arguments " + var1 + ' ' + var3;
         int var5 = Log.e("Email", var4);
         throw new RuntimeException(var4);
      }
   }

   public void deleteAttachment(long var1) {
      ContentResolver var3 = this.mProviderContext.getContentResolver();
      Uri var4 = ContentUris.withAppendedId(EmailContent.Attachment.CONTENT_URI, var1);
      var3.delete(var4, (String)null, (String[])null);
   }

   public void deleteMessage(long var1, long var3) {
      AsyncTask var10 = Utility.runAsync(new Controller.9(var1, var3));
   }

   public void deleteMessageSync(long var1, long var3) {
      ContentResolver var5 = this.mProviderContext.getContentResolver();
      if(var3 == 65535L) {
         var3 = this.lookupAccountForMessage(var1);
      }

      if(var3 != 65535L) {
         byte var9 = 6;
         long var10 = this.findOrCreateMailboxOfType(var3, var9);
         Uri var12 = EmailContent.Message.CONTENT_URI;
         String[] var13 = MESSAGEID_TO_MAILBOXID_PROJECTION;
         String[] var14 = new String[1];
         String var15 = Long.toString(var1);
         var14[0] = var15;
         Cursor var16 = var5.query(var12, var13, "_id=?", var14, (String)null);
         boolean var57 = false;

         long var20;
         label81: {
            label80: {
               long var18;
               try {
                  var57 = true;
                  if(!var16.moveToFirst()) {
                     var57 = false;
                     break label80;
                  }

                  int var17 = MESSAGEID_TO_MAILBOXID_COLUMN_MAILBOXID;
                  var18 = var16.getLong(var17);
                  var57 = false;
               } finally {
                  if(var57) {
                     var16.close();
                  }
               }

               var20 = var18;
               break label81;
            }

            var20 = 65535L;
         }

         var16.close();
         Context var22 = this.mProviderContext;
         AttachmentProvider.deleteAllAttachmentFiles(var22, var3, var1);
         Uri var27 = EmailContent.Message.SYNCED_CONTENT_URI;
         Uri var30 = ContentUris.withAppendedId(var27, var1);
         if(var20 == var10) {
            Object var33 = null;
            Object var34 = null;
            var5.delete(var30, (String)var33, (String[])var34);
         } else {
            ContentValues var48 = new ContentValues();
            Long var49 = Long.valueOf(var10);
            var48.put("mailboxKey", var49);
            Object var53 = null;
            Object var54 = null;
            var5.update(var30, var48, (String)var53, (String[])var54);
         }

         Context var36 = this.mProviderContext;
         EmailContent.Account var39 = EmailContent.Account.restoreAccountWithId(var36, var3);
         if(var39 != null) {
            if(this.isMessagingController(var39)) {
               MessagingController var44 = this.mLegacyController;
               var44.processPendingActions(var3);
            }
         }
      }
   }

   public long findOrCreateMailboxOfType(long var1, int var3) {
      long var4;
      if(var1 >= 0L && var3 >= 0) {
         long var6 = EmailContent.Mailbox.findMailboxOfType(this.mProviderContext, var1, var3);
         if(var6 == 65535L) {
            var4 = this.createMailbox(var1, var3);
         } else {
            var4 = var6;
         }
      } else {
         var4 = 65535L;
      }

      return var4;
   }

   String getMailboxServerName(int var1) {
      int var2 = -1;
      switch(var1) {
      case 0:
         var2 = 2131165252;
      case 1:
      case 2:
      default:
         break;
      case 3:
         var2 = 2131165254;
         break;
      case 4:
         var2 = 2131165253;
         break;
      case 5:
         var2 = 2131165256;
         break;
      case 6:
         var2 = 2131165255;
         break;
      case 7:
         var2 = 2131165257;
      }

      String var3;
      if(var2 != -1) {
         var3 = this.mContext.getString(var2);
      } else {
         var3 = "";
      }

      return var3;
   }

   public boolean isMessagingController(EmailContent.Account var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         Context var3 = this.mProviderContext;
         String var4 = var1.getStoreUri(var3);
         Context var5 = this.mContext;
         Store.StoreInfo var6 = Store.StoreInfo.getStoreInfo(var4, var5);
         if(var6 == null) {
            var2 = false;
         } else {
            String var7 = var6.mScheme;
            if(!"pop3".equals(var7) && !"imap".equals(var7)) {
               var2 = false;
            } else {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public void loadAttachment(long var1, long var3, long var5, long var7, Controller.Result var9) {
      Context var10 = this.mProviderContext;
      File var15 = AttachmentProvider.getAttachmentFilename(var10, var7, var1);
      Context var16 = this.mProviderContext;
      EmailContent.Attachment var19 = EmailContent.Attachment.restoreAttachmentWithId(var16, var1);
      if(var19 != null) {
         if(!var15.exists() && var19.mContentUri == null) {
            IEmailService var36 = this.getServiceForMessage(var3);
            if(var36 != null) {
               try {
                  long var37 = var19.mId;
                  String var39 = var15.getAbsolutePath();
                  String var44 = AttachmentProvider.getAttachmentUri(var7, var1).toString();
                  var36.loadAttachment(var37, var39, var44);
               } catch (RemoteException var64) {
                  String var51 = "onDownloadAttachment";
                  String var52 = "RemoteException";
                  Log.e(var51, var52, var64);
               }
            } else {
               (new Controller.12(var7, var3, var5, var1)).start();
            }
         } else {
            HashSet var20 = this.mListeners;
            synchronized(var20) {
               Iterator var21 = this.mListeners.iterator();

               while(var21.hasNext()) {
                  Controller.Result var22 = (Controller.Result)var21.next();
                  var22.loadAttachmentCallback((MessagingException)null, var3, var1, 0);
               }

               var21 = this.mListeners.iterator();

               while(var21.hasNext()) {
                  Controller.Result var28 = (Controller.Result)var21.next();
                  var28.loadAttachmentCallback((MessagingException)null, var3, var1, 100);
               }

            }
         }
      }
   }

   public void loadMessageForView(long var1, Controller.Result var3) {
      AsyncTask var4 = Utility.runAsync(new Controller.4(var1));
   }

   public void loadMoreMessages(long var1, Controller.Result var3) {
      (new Controller.8(var1)).start();
   }

   public void removeResultCallback(Controller.Result var1) {
      HashSet var2 = this.mListeners;
      synchronized(var2) {
         this.mListeners.remove(var1);
      }
   }

   public void resetVisibleLimits() {
      (new Controller.7()).start();
   }

   public void saveToMailbox(EmailContent.Message var1, int var2) {
      long var3 = var1.mAccountKey;
      long var5 = this.findOrCreateMailboxOfType(var3, var2);
      var1.mMailboxKey = var5;
      Context var7 = this.mProviderContext;
      var1.save(var7);
   }

   public void sendMeetingResponse(long var1, int var3, Controller.Result var4) {
      IEmailService var5 = this.getServiceForMessage(var1);
      if(var5 != null) {
         try {
            var5.sendMeetingResponse(var1, var3);
         } catch (RemoteException var8) {
            int var7 = Log.e("onDownloadAttachment", "RemoteException", var8);
         }
      }
   }

   public void sendMessage(long var1, long var3) {
      ContentResolver var5 = this.mProviderContext.getContentResolver();
      if(var3 == 65535L) {
         var3 = this.lookupAccountForMessage(var1);
      }

      if(var3 != 65535L) {
         long var6 = this.findOrCreateMailboxOfType(var3, 4);
         ContentValues var8 = new ContentValues();
         Long var9 = Long.valueOf(var6);
         var8.put("mailboxKey", var9);
         Uri var10 = ContentUris.withAppendedId(EmailContent.Message.CONTENT_URI, var1);
         var5.update(var10, var8, (String)null, (String[])null);
         IEmailService var12 = this.getServiceForMessage(var1);
         if(var12 != null) {
            try {
               Controller.ServiceCallback var13 = this.mServiceCallback;
               var12.setCallback(var13);
            } catch (RemoteException var18) {
               ;
            }
         } else {
            EmailContent.Account var15 = EmailContent.Account.restoreAccountWithId(this.mProviderContext, var3);
            if(var15 != null) {
               long var16 = this.findOrCreateMailboxOfType(var3, 5);
               (new Controller.5(var15, var16)).start();
            }
         }
      }
   }

   public void sendPendingMessages(long var1, Controller.Result var3) {
      long var4 = EmailContent.Mailbox.findMailboxOfType(this.mProviderContext, var1, 4);
      if(var4 != 65535L) {
         IEmailService var6 = this.getServiceForAccount(var1);
         if(var6 != null) {
            try {
               var6.startSync(var4);
            } catch (RemoteException var13) {
               String var8 = "RemoteException" + var13;
               int var9 = Log.d("updateMailbox", var8);
            }
         } else {
            EmailContent.Account var10 = EmailContent.Account.restoreAccountWithId(this.mProviderContext, var1);
            if(var10 != null) {
               long var11 = this.findOrCreateMailboxOfType(var1, 5);
               (new Controller.6(var10, var11)).start();
            }
         }
      }
   }

   public void serviceCheckMail(long var1, long var3, long var5, Controller.Result var7) {
      if(this.getServiceForAccount(var1) != null) {
         var7.serviceCheckMailCallback((MessagingException)null, var1, var3, 100, var5);
      } else {
         (new Controller.2(var1, var5)).start();
      }
   }

   public void serviceLogging(int var1) {
      Context var2 = this.mContext;
      Controller.ServiceCallback var3 = this.mServiceCallback;
      IEmailService var4 = ExchangeUtils.getExchangeEmailService(var2, var3);

      try {
         var4.setLogging(var1);
      } catch (RemoteException var8) {
         String var6 = "RemoteException" + var8;
         int var7 = Log.d("updateMailboxList", var6);
      }
   }

   public void setMessageFavorite(long var1, boolean var3) {
      (new Controller.11(var1, var3)).start();
   }

   public void setMessageFavoriteSync(long var1, boolean var3) {
      this.setMessageFlagSync(var1, var3, "flagFavorite");
   }

   public void setMessageFlagSync(long var1, boolean var3, String var4) {
      ContentValues var5 = new ContentValues();
      Boolean var6 = Boolean.valueOf(var3);
      var5.put(var4, var6);
      Uri var7 = ContentUris.withAppendedId(EmailContent.Message.SYNCED_CONTENT_URI, var1);
      this.mProviderContext.getContentResolver().update(var7, var5, (String)null, (String[])null);
      EmailContent.Message var9 = EmailContent.Message.restoreMessageWithId(this.mProviderContext, var1);
      if(var9 != null) {
         Context var10 = this.mProviderContext;
         long var11 = var9.mAccountKey;
         EmailContent.Account var13 = EmailContent.Account.restoreAccountWithId(var10, var11);
         if(var13 != null) {
            if(this.isMessagingController(var13)) {
               MessagingController var14 = this.mLegacyController;
               long var15 = var9.mAccountKey;
               var14.processPendingActions(var15);
            }
         }
      }
   }

   public void setMessageRead(long var1, boolean var3) {
      (new Controller.10(var1, var3)).start();
   }

   public void setMessageReadSync(long var1, boolean var3) {
      this.setMessageFlagSync(var1, var3, "flagRead");
   }

   public void setProviderContext(Context var1) {
      this.mProviderContext = var1;
   }

   public void updateMailbox(long var1, long var3, Controller.Result var5) {
      if(var3 >= 0L) {
         IEmailService var6 = this.getServiceForAccount(var1);
         if(var6 != null) {
            try {
               var6.startSync(var3);
            } catch (RemoteException var15) {
               String var8 = "RemoteException" + var15;
               int var9 = Log.d("updateMailbox", var8);
            }
         } else {
            (new Controller.3(var1, var3)).start();
         }
      }
   }

   public void updateMailboxList(long var1, Controller.Result var3) {
      AsyncTask var4 = Utility.runAsync(new Controller.1(var1));
   }

   class 12 extends Thread {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$attachmentId;
      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final long val$messageId;


      12(long var2, long var4, long var6, long var8) {
         this.val$accountId = var2;
         this.val$messageId = var4;
         this.val$mailboxId = var6;
         this.val$attachmentId = var8;
      }

      public void run() {
         MessagingController var1 = Controller.this.mLegacyController;
         long var2 = this.val$accountId;
         long var4 = this.val$messageId;
         long var6 = this.val$mailboxId;
         long var8 = this.val$attachmentId;
         Controller.LegacyListener var10 = Controller.this.mLegacyListener;
         var1.loadAttachment(var2, var4, var6, var8, var10);
      }
   }

   class 10 extends Thread {

      // $FF: synthetic field
      final boolean val$cloneReadFlag;
      // $FF: synthetic field
      final long val$messageId;


      10(long var2, boolean var4) {
         this.val$messageId = var2;
         this.val$cloneReadFlag = var4;
      }

      public void run() {
         Controller var1 = Controller.this;
         long var2 = this.val$messageId;
         boolean var4 = this.val$cloneReadFlag;
         var1.setMessageReadSync(var2, var4);
      }
   }

   class 11 extends Thread {

      // $FF: synthetic field
      final boolean val$cloneFavoriteFlag;
      // $FF: synthetic field
      final long val$messageId;


      11(long var2, boolean var4) {
         this.val$messageId = var2;
         this.val$cloneFavoriteFlag = var4;
      }

      public void run() {
         Controller var1 = Controller.this;
         long var2 = this.val$messageId;
         boolean var4 = this.val$cloneFavoriteFlag;
         var1.setMessageFavoriteSync(var2, var4);
      }
   }

   class 2 extends Thread {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$tag;


      2(long var2, long var4) {
         this.val$accountId = var2;
         this.val$tag = var4;
      }

      public void run() {
         MessagingController var1 = Controller.this.mLegacyController;
         long var2 = this.val$accountId;
         long var4 = this.val$tag;
         Controller.LegacyListener var6 = Controller.this.mLegacyListener;
         var1.checkMail(var2, var4, var6);
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;


      1(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         Controller var1 = Controller.this;
         long var2 = this.val$accountId;
         IEmailService var4 = var1.getServiceForAccount(var2);
         if(var4 != null) {
            try {
               long var5 = this.val$accountId;
               var4.updateFolderList(var5);
            } catch (RemoteException var14) {
               String var8 = "RemoteException" + var14;
               int var9 = Log.d("updateMailboxList", var8);
            }
         } else {
            MessagingController var10 = Controller.this.mLegacyController;
            long var11 = this.val$accountId;
            Controller.LegacyListener var13 = Controller.this.mLegacyListener;
            var10.listFolders(var11, var13);
         }
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final long val$messageId;


      4(long var2) {
         this.val$messageId = var2;
      }

      public void run() {
         Controller var1 = Controller.this;
         long var2 = this.val$messageId;
         if(var1.getServiceForMessage(var2) != null) {
            Uri var4 = EmailContent.Message.CONTENT_URI;
            long var5 = this.val$messageId;
            Uri var7 = ContentUris.withAppendedId(var4, var5);
            ContentValues var8 = new ContentValues();
            Integer var9 = Integer.valueOf(1);
            var8.put("flagLoaded", var9);
            Controller.this.mProviderContext.getContentResolver().update(var7, var8, (String)null, (String[])null);
            int var11 = Log.d("Email", "Unexpected loadMessageForView() for service-based message.");
            HashSet var12 = Controller.this.mListeners;
            synchronized(var12) {
               Iterator var13 = Controller.this.mListeners.iterator();

               while(var13.hasNext()) {
                  Controller.Result var14 = (Controller.Result)var13.next();
                  long var15 = this.val$messageId;
                  var14.loadMessageForViewCallback((MessagingException)null, var15, 100);
               }

            }
         } else {
            MessagingController var18 = Controller.this.mLegacyController;
            long var19 = this.val$messageId;
            Controller.LegacyListener var21 = Controller.this.mLegacyListener;
            var18.loadMessageForView(var19, var21);
         }
      }
   }

   class 3 extends Thread {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$mailboxId;


      3(long var2, long var4) {
         this.val$accountId = var2;
         this.val$mailboxId = var4;
      }

      public void run() {
         Context var1 = Controller.this.mProviderContext;
         long var2 = this.val$accountId;
         EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(var1, var2);
         Context var5 = Controller.this.mProviderContext;
         long var6 = this.val$mailboxId;
         EmailContent.Mailbox var8 = EmailContent.Mailbox.restoreMailboxWithId(var5, var6);
         if(var4 != null) {
            if(var8 != null) {
               MessagingController var9 = Controller.this.mLegacyController;
               Controller.LegacyListener var10 = Controller.this.mLegacyListener;
               var9.synchronizeMailbox(var4, var8, var10);
            }
         }
      }
   }

   class 6 extends Thread {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$sentboxId;


      6(EmailContent.Account var2, long var3) {
         this.val$account = var2;
         this.val$sentboxId = var3;
      }

      public void run() {
         MessagingController var1 = Controller.this.mLegacyController;
         EmailContent.Account var2 = this.val$account;
         long var3 = this.val$sentboxId;
         Controller.LegacyListener var5 = Controller.this.mLegacyListener;
         var1.sendPendingMessages(var2, var3, var5);
      }
   }

   class 5 extends Thread {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$sentboxId;


      5(EmailContent.Account var2, long var3) {
         this.val$account = var2;
         this.val$sentboxId = var3;
      }

      public void run() {
         MessagingController var1 = Controller.this.mLegacyController;
         EmailContent.Account var2 = this.val$account;
         long var3 = this.val$sentboxId;
         Controller.LegacyListener var5 = Controller.this.mLegacyListener;
         var1.sendPendingMessages(var2, var3, var5);
      }
   }

   class 7 extends Thread {

      7() {}

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 8 extends Thread {

      // $FF: synthetic field
      final long val$mailboxId;


      8(long var2) {
         this.val$mailboxId = var2;
      }

      public void run() {
         Context var1 = Controller.this.mProviderContext;
         long var2 = this.val$mailboxId;
         EmailContent.Mailbox var4 = EmailContent.Mailbox.restoreMailboxWithId(var1, var2);
         if(var4 != null) {
            Context var5 = Controller.this.mProviderContext;
            long var6 = var4.mAccountKey;
            EmailContent.Account var8 = EmailContent.Account.restoreAccountWithId(var5, var6);
            if(var8 != null) {
               Context var9 = Controller.this.mProviderContext;
               String var10 = var8.getStoreUri(var9);
               Context var11 = Controller.this.mContext;
               Store.StoreInfo var12 = Store.StoreInfo.getStoreInfo(var10, var11);
               if(var12 != null) {
                  if(var12.mVisibleLimitIncrement > 0) {
                     ContentValues var13 = new ContentValues();
                     var13.put("field", "visibleLimit");
                     Integer var14 = Integer.valueOf(var12.mVisibleLimitIncrement);
                     var13.put("add", var14);
                     Uri var15 = EmailContent.Mailbox.ADD_TO_FIELD_URI;
                     long var16 = this.val$mailboxId;
                     Uri var18 = ContentUris.withAppendedId(var15, var16);
                     Controller.this.mProviderContext.getContentResolver().update(var18, var13, (String)null, (String[])null);
                     int var20 = var4.mVisibleLimit;
                     int var21 = var12.mVisibleLimitIncrement;
                     int var22 = var20 + var21;
                     var4.mVisibleLimit = var22;
                     MessagingController var23 = Controller.this.mLegacyController;
                     Controller.LegacyListener var24 = Controller.this.mLegacyListener;
                     var23.synchronizeMailbox(var8, var4, var24);
                  }
               }
            }
         }
      }
   }

   class 9 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$messageId;


      9(long var2, long var4) {
         this.val$messageId = var2;
         this.val$accountId = var4;
      }

      public void run() {
         Controller var1 = Controller.this;
         long var2 = this.val$messageId;
         long var4 = this.val$accountId;
         var1.deleteMessageSync(var2, var4);
      }
   }

   private class LegacyListener extends MessagingListener {

      private LegacyListener() {}

      // $FF: synthetic method
      LegacyListener(Controller.1 var2) {
         this();
      }

      public void checkMailFinished(Context var1, long var2, long var4, long var6) {
         HashSet var8 = Controller.this.mListeners;
         synchronized(var8) {
            Iterator var9 = Controller.this.mListeners.iterator();

            while(var9.hasNext()) {
               Controller.Result var10 = (Controller.Result)var9.next();
               var10.serviceCheckMailCallback((MessagingException)null, var2, var4, 100, var6);
            }

         }
      }

      public void checkMailStarted(Context var1, long var2, long var4) {
         HashSet var6 = Controller.this.mListeners;
         synchronized(var6) {
            Iterator var7 = Controller.this.mListeners.iterator();

            while(var7.hasNext()) {
               Controller.Result var8 = (Controller.Result)var7.next();
               var8.serviceCheckMailCallback((MessagingException)null, var2, 65535L, 0, var4);
            }

         }
      }

      public void listFoldersFailed(long var1, String var3) {
         HashSet var4 = Controller.this.mListeners;
         synchronized(var4) {
            Iterator var5 = Controller.this.mListeners.iterator();

            while(var5.hasNext()) {
               Controller.Result var6 = (Controller.Result)var5.next();
               MessagingException var7 = new MessagingException(var3);
               var6.updateMailboxListCallback(var7, var1, 0);
            }

         }
      }

      public void listFoldersFinished(long var1) {
         HashSet var3 = Controller.this.mListeners;
         synchronized(var3) {
            Iterator var4 = Controller.this.mListeners.iterator();

            while(var4.hasNext()) {
               ((Controller.Result)var4.next()).updateMailboxListCallback((MessagingException)null, var1, 100);
            }

         }
      }

      public void listFoldersStarted(long var1) {
         HashSet var3 = Controller.this.mListeners;
         synchronized(var3) {
            Iterator var4 = Controller.this.mListeners.iterator();

            while(var4.hasNext()) {
               ((Controller.Result)var4.next()).updateMailboxListCallback((MessagingException)null, var1, 0);
            }

         }
      }

      public void loadAttachmentFailed(long var1, long var3, long var5, String var7) {
         HashSet var8 = Controller.this.mListeners;
         synchronized(var8) {
            Iterator var9 = Controller.this.mListeners.iterator();

            while(var9.hasNext()) {
               Controller.Result var10 = (Controller.Result)var9.next();
               MessagingException var11 = new MessagingException(var7);
               var10.loadAttachmentCallback(var11, var3, var5, 0);
            }

         }
      }

      public void loadAttachmentFinished(long var1, long var3, long var5) {
         HashSet var7 = Controller.this.mListeners;
         synchronized(var7) {
            Iterator var8 = Controller.this.mListeners.iterator();

            while(var8.hasNext()) {
               Controller.Result var9 = (Controller.Result)var8.next();
               var9.loadAttachmentCallback((MessagingException)null, var3, var5, 100);
            }

         }
      }

      public void loadAttachmentStarted(long var1, long var3, long var5, boolean var7) {
         HashSet var8 = Controller.this.mListeners;
         synchronized(var8) {
            Iterator var9 = Controller.this.mListeners.iterator();

            while(var9.hasNext()) {
               Controller.Result var10 = (Controller.Result)var9.next();
               var10.loadAttachmentCallback((MessagingException)null, var3, var5, 0);
            }

         }
      }

      public void loadMessageForViewFailed(long var1, String var3) {
         HashSet var4 = Controller.this.mListeners;
         synchronized(var4) {
            Iterator var5 = Controller.this.mListeners.iterator();

            while(var5.hasNext()) {
               Controller.Result var6 = (Controller.Result)var5.next();
               MessagingException var7 = new MessagingException(var3);
               var6.loadMessageForViewCallback(var7, var1, 0);
            }

         }
      }

      public void loadMessageForViewFinished(long var1) {
         HashSet var3 = Controller.this.mListeners;
         synchronized(var3) {
            Iterator var4 = Controller.this.mListeners.iterator();

            while(var4.hasNext()) {
               ((Controller.Result)var4.next()).loadMessageForViewCallback((MessagingException)null, var1, 100);
            }

         }
      }

      public void loadMessageForViewStarted(long var1) {
         HashSet var3 = Controller.this.mListeners;
         synchronized(var3) {
            Iterator var4 = Controller.this.mListeners.iterator();

            while(var4.hasNext()) {
               ((Controller.Result)var4.next()).loadMessageForViewCallback((MessagingException)null, var1, 0);
            }

         }
      }

      public void sendPendingMessagesCompleted(long param1) {
         // $FF: Couldn't be decompiled
      }

      public void sendPendingMessagesFailed(long param1, long param3, Exception param5) {
         // $FF: Couldn't be decompiled
      }

      public void sendPendingMessagesStarted(long param1, long param3) {
         // $FF: Couldn't be decompiled
      }

      public void synchronizeMailboxFailed(long var1, long var3, Exception var5) {
         MessagingException var6;
         if(var5 instanceof MessagingException) {
            var6 = (MessagingException)var5;
         } else {
            String var15 = var5.toString();
            var6 = new MessagingException(var15);
         }

         HashSet var7 = Controller.this.mListeners;
         synchronized(var7) {
            Iterator var8 = Controller.this.mListeners.iterator();

            while(var8.hasNext()) {
               Controller.Result var9 = (Controller.Result)var8.next();
               var9.updateMailboxCallback(var6, var1, var3, 0, 0);
            }

         }
      }

      public void synchronizeMailboxFinished(long var1, long var3, int var5, int var6) {
         HashSet var7 = Controller.this.mListeners;
         synchronized(var7) {
            Iterator var8 = Controller.this.mListeners.iterator();

            while(var8.hasNext()) {
               Controller.Result var9 = (Controller.Result)var8.next();
               var9.updateMailboxCallback((MessagingException)null, var1, var3, 100, var6);
            }

         }
      }

      public void synchronizeMailboxStarted(long var1, long var3) {
         HashSet var5 = Controller.this.mListeners;
         synchronized(var5) {
            Iterator var6 = Controller.this.mListeners.iterator();

            while(var6.hasNext()) {
               Controller.Result var7 = (Controller.Result)var6.next();
               var7.updateMailboxCallback((MessagingException)null, var1, var3, 0, 0);
            }

         }
      }
   }

   public interface Result {

      void loadAttachmentCallback(MessagingException var1, long var2, long var4, int var6);

      void loadMessageForViewCallback(MessagingException var1, long var2, int var4);

      void sendMailCallback(MessagingException var1, long var2, long var4, int var6);

      void serviceCheckMailCallback(MessagingException var1, long var2, long var4, int var6, long var7);

      void updateMailboxCallback(MessagingException var1, long var2, long var4, int var6, int var7);

      void updateMailboxListCallback(MessagingException var1, long var2, int var4);
   }

   private class ServiceCallback extends IEmailServiceCallback.Stub {

      private static final boolean DEBUG_FAIL_DOWNLOADS;


      private ServiceCallback() {}

      // $FF: synthetic method
      ServiceCallback(Controller.1 var2) {
         this();
      }

      private MessagingException mapStatusToException(int var1) {
         Object var3;
         switch(var1) {
         case 0:
         case 1:
            var3 = null;
            break;
         case 22:
            var3 = new AuthenticationFailedException("");
            break;
         case 23:
            var3 = new MessagingException(7);
            break;
         case 32:
            var3 = new MessagingException(1);
            break;
         default:
            String var2 = String.valueOf(var1);
            var3 = new MessagingException(var2);
         }

         return (MessagingException)var3;
      }

      public void loadAttachmentStatus(long var1, long var3, int var5, int var6) {
         MessagingException var7 = this.mapStatusToException(var5);
         switch(var5) {
         case 0:
            var6 = 100;
            break;
         case 1:
            if(var6 < 0) {
               return;
            }

            if(var6 >= 100) {
               return;
            }
         }

         HashSet var8 = Controller.this.mListeners;
         synchronized(var8) {
            Iterator var9 = Controller.this.mListeners.iterator();

            while(var9.hasNext()) {
               Controller.Result var10 = (Controller.Result)var9.next();
               var10.loadAttachmentCallback(var7, var1, var3, var6);
            }

         }
      }

      public void sendMessageStatus(long var1, String var3, int var4, int var5) {
         MessagingException var6 = this.mapStatusToException(var4);
         switch(var4) {
         case 0:
            var5 = 100;
            break;
         case 1:
            if(var5 < 0) {
               return;
            }

            if(var5 >= 100) {
               return;
            }
         }

         HashSet var7 = Controller.this.mListeners;
         synchronized(var7) {
            Iterator var8 = Controller.this.mListeners.iterator();

            while(var8.hasNext()) {
               Controller.Result var9 = (Controller.Result)var8.next();
               var9.sendMailCallback(var6, 65535L, var1, var5);
            }

         }
      }

      public void syncMailboxListStatus(long var1, int var3, int var4) {
         MessagingException var5 = this.mapStatusToException(var3);
         switch(var3) {
         case 0:
            var4 = 100;
            break;
         case 1:
            if(var4 < 0) {
               return;
            }

            if(var4 >= 100) {
               return;
            }
         }

         HashSet var6 = Controller.this.mListeners;
         synchronized(var6) {
            Iterator var7 = Controller.this.mListeners.iterator();

            while(var7.hasNext()) {
               ((Controller.Result)var7.next()).updateMailboxListCallback(var5, var1, var4);
            }

         }
      }

      public void syncMailboxStatus(long var1, int var3, int var4) {
         MessagingException var5 = this.mapStatusToException(var3);
         switch(var3) {
         case 0:
            var4 = 100;
            break;
         case 1:
            if(var4 < 0) {
               return;
            }

            if(var4 >= 100) {
               return;
            }
         }

         EmailContent.Mailbox var6 = EmailContent.Mailbox.restoreMailboxWithId(Controller.this.mProviderContext, var1);
         if(var6 != null) {
            long var7 = var6.mAccountKey;
            HashSet var9 = Controller.this.mListeners;
            synchronized(var9) {
               Iterator var10 = Controller.this.mListeners.iterator();

               while(var10.hasNext()) {
                  Controller.Result var11 = (Controller.Result)var10.next();
                  var11.updateMailboxCallback(var5, var7, var1, var4, 0);
               }

            }
         }
      }
   }
}
