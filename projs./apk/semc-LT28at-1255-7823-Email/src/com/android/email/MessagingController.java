package com.android.email;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Process;
import android.util.Log;
import com.android.email.Email;
import com.android.email.GroupMessagingListener;
import com.android.email.LegacyConversions;
import com.android.email.MessagingListener;
import com.android.email.mail.FetchProfile;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.mail.StoreSynchronizer;
import com.android.email.mail.internet.MimeBodyPart;
import com.android.email.mail.internet.MimeMultipart;
import com.android.email.mail.store.Pop3Store;
import com.android.email.provider.AttachmentProvider;
import com.android.email.provider.EmailContent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessagingController implements Runnable {

   private static final Flag[] FLAG_LIST_FLAGGED;
   private static final Flag[] FLAG_LIST_SEEN;
   private static final String LOCAL_SERVERID_PREFIX = "Local-";
   private static final int MAX_SMALL_MESSAGE_SIZE = 25600;
   private static final ContentValues PRUNE_ATTACHMENT_CV;
   private static final String[] PRUNE_ATTACHMENT_PROJECTION;
   private static MessagingController sInstance;
   private boolean mBusy;
   private final BlockingQueue<MessagingController.Command> mCommands;
   private final Context mContext;
   private final GroupMessagingListener mListeners;
   private final Thread mThread;


   static {
      Flag[] var0 = new Flag[1];
      Flag var1 = Flag.SEEN;
      var0[0] = var1;
      FLAG_LIST_SEEN = var0;
      Flag[] var2 = new Flag[1];
      Flag var3 = Flag.FLAGGED;
      var2[0] = var3;
      FLAG_LIST_FLAGGED = var2;
      String[] var4 = new String[]{"location"};
      PRUNE_ATTACHMENT_PROJECTION = var4;
      PRUNE_ATTACHMENT_CV = new ContentValues();
      PRUNE_ATTACHMENT_CV.putNull("contentUri");
      sInstance = null;
   }

   protected MessagingController(Context var1) {
      LinkedBlockingQueue var2 = new LinkedBlockingQueue();
      this.mCommands = var2;
      GroupMessagingListener var3 = new GroupMessagingListener();
      this.mListeners = var3;
      this.mContext = var1;
      Thread var4 = new Thread(this);
      this.mThread = var4;
      this.mThread.start();
   }

   // $FF: synthetic method
   static void access$600(MessagingController var0, EmailContent var1) {
      var0.saveOrUpdate(var1);
   }

   private void copyOneMessageToProvider(Message param1, EmailContent.Account param2, EmailContent.Mailbox param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public static MessagingController getInstance(Context var0) {
      synchronized(MessagingController.class){}

      MessagingController var1;
      try {
         if(sInstance == null) {
            sInstance = new MessagingController(var0);
         }

         var1 = sInstance;
      } finally {
         ;
      }

      return var1;
   }

   public static void injectMockController(MessagingController var0) {
      sInstance = var0;
   }

   private boolean isActiveListener(MessagingListener var1) {
      return this.mListeners.isActiveListener(var1);
   }

   private void processPendingActionsSynchronous(EmailContent.Account var1) throws MessagingException {
      ContentResolver var2 = this.mContext.getContentResolver();
      String[] var3 = new String[1];
      String var4 = Long.toString(var1.mId);
      var3[0] = var4;
      this.processPendingDeletesSynchronous(var1, var2, var3);
      this.processPendingUploadsSynchronous(var1, var2, var3);
      this.processPendingUpdatesSynchronous(var1, var2, var3);
   }

   private boolean processPendingAppend(Store var1, EmailContent.Account var2, EmailContent.Mailbox var3, EmailContent.Message var4) throws MessagingException {
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      String var8 = var3.mDisplayName;
      Folder var11 = var1.getFolder(var8);
      boolean var34;
      if(!var11.exists()) {
         Folder.FolderType var12 = Folder.FolderType.HOLDS_MESSAGES;
         if(!var11.canCreate(var12)) {
            if(var4.mServerId == null || var4.mServerId.length() == 0) {
               StringBuilder var15 = (new StringBuilder()).append("Local-");
               long var16 = var4.mId;
               String var18 = var15.append(var16).toString();
               var4.mServerId = var18;
               Uri var19 = EmailContent.Message.CONTENT_URI;
               long var20 = var4.mId;
               Uri var22 = ContentUris.withAppendedId(var19, var20);
               ContentValues var23 = new ContentValues();
               String var24 = var4.mServerId;
               String var26 = "syncServerId";
               var23.put(var26, var24);
               ContentResolver var28 = this.mContext.getContentResolver();
               Object var31 = null;
               Object var32 = null;
               var28.update(var22, var23, (String)var31, (String[])var32);
            }

            var34 = true;
            return var34;
         }

         Folder.FolderType var35 = Folder.FolderType.HOLDS_MESSAGES;
         if(!var11.create(var35)) {
            var34 = false;
            return var34;
         }
      }

      Folder.OpenMode var38 = Folder.OpenMode.READ_WRITE;
      Object var41 = null;
      var11.open(var38, (Folder.PersistentDataCallbacks)var41);
      Folder.OpenMode var42 = var11.getMode();
      Folder.OpenMode var43 = Folder.OpenMode.READ_WRITE;
      if(var42 != var43) {
         byte var47 = 0;
         var11.close((boolean)var47);
         var34 = false;
      } else {
         Message var48 = null;
         if(var4.mServerId != null && var4.mServerId.length() > 0) {
            String var49 = var4.mServerId;
            var48 = var11.getMessage(var49);
         }

         if(var48 == null) {
            Context var52 = this.mContext;
            Message var54 = LegacyConversions.makeMessage(var52, var4);
            FetchProfile var55 = new FetchProfile();
            FetchProfile.Item var56 = FetchProfile.Item.BODY;
            boolean var59 = var55.add(var56);
            Message[] var60 = new Message[]{var54};
            var11.appendMessages(var60);
            String var63 = var54.getUid();
            var4.mServerId = var63;
            var5 = true;
            var6 = true;
         } else {
            FetchProfile var92 = new FetchProfile();
            FetchProfile.Item var93 = FetchProfile.Item.ENVELOPE;
            boolean var96 = var92.add(var93);
            Message[] var97 = new Message[]{var48};
            Object var101 = null;
            var11.fetch(var97, var92, (Folder.MessageRetrievalListener)var101);
            Date var102 = new Date;
            long var103 = var4.mServerTimeStamp;
            var102.<init>(var103);
            Date var108 = var48.getInternalDate();
            if(var108 != null && var108.compareTo(var102) > 0) {
               var7 = true;
            } else {
               Context var109 = this.mContext;
               Message var111 = LegacyConversions.makeMessage(var109, var4);
               var92.clear();
               FetchProfile var112 = new FetchProfile();
               FetchProfile.Item var113 = FetchProfile.Item.BODY;
               boolean var116 = var112.add(var113);
               Message[] var117 = new Message[]{var111};
               var11.appendMessages(var117);
               String var120 = var111.getUid();
               var4.mServerId = var120;
               var5 = true;
               var6 = true;
               Flag var121 = Flag.DELETED;
               byte var124 = 1;
               var48.setFlag(var121, (boolean)var124);
            }
         }

         if(var5 && var4.mServerId != null) {
            try {
               String var64 = var4.mServerId;
               Message var67 = var11.getMessage(var64);
               if(var67 != null) {
                  FetchProfile var68 = new FetchProfile();
                  FetchProfile.Item var69 = FetchProfile.Item.ENVELOPE;
                  boolean var72 = var68.add(var69);
                  Message[] var73 = new Message[]{var67};
                  Object var77 = null;
                  var11.fetch(var73, var68, (Folder.MessageRetrievalListener)var77);
                  if(var67.getInternalDate() != null) {
                     long var78 = var67.getInternalDate().getTime();
                     var4.mServerTimeStamp = var78;
                  }
               }
            } catch (MessagingException var141) {
               ;
            }
         }

         if(var7 || var6) {
            Uri var80 = EmailContent.Message.CONTENT_URI;
            long var81 = var4.mId;
            Uri var83 = ContentUris.withAppendedId(var80, var81);
            ContentResolver var84 = this.mContext.getContentResolver();
            if(var7) {
               Object var87 = null;
               Object var88 = null;
               var84.delete(var83, (String)var87, (String[])var88);
            } else if(var6) {
               ContentValues var125 = new ContentValues();
               String var126 = var4.mServerId;
               String var128 = "syncServerId";
               var125.put(var128, var126);
               Long var130 = Long.valueOf(var4.mServerTimeStamp);
               String var132 = "syncServerTimeStamp";
               var125.put(var132, var130);
               Object var137 = null;
               Object var138 = null;
               var84.update(var83, var125, (String)var137, (String[])var138);
            }
         }

         byte var91 = 0;
         var11.close((boolean)var91);
         var34 = true;
      }

      return var34;
   }

   private void processPendingDeleteFromTrash(Store var1, EmailContent.Account var2, EmailContent.Mailbox var3, EmailContent.Message var4) throws MessagingException {
      if(var3.mType == 6) {
         String var5 = var3.mDisplayName;
         Folder var6 = var1.getFolder(var5);
         if(var6.exists()) {
            Folder.OpenMode var7 = Folder.OpenMode.READ_WRITE;
            var6.open(var7, (Folder.PersistentDataCallbacks)null);
            Folder.OpenMode var8 = var6.getMode();
            Folder.OpenMode var9 = Folder.OpenMode.READ_WRITE;
            if(var8 != var9) {
               var6.close((boolean)0);
            } else {
               String var10 = var4.mServerId;
               Message var11 = var6.getMessage(var10);
               if(var11 == null) {
                  var6.close((boolean)0);
               } else {
                  Flag var12 = Flag.DELETED;
                  var11.setFlag(var12, (boolean)1);
                  Message[] var13 = var6.expunge();
                  var6.close((boolean)0);
               }
            }
         }
      }
   }

   private void processPendingDeletesSynchronous(EmailContent.Account param1, ContentResolver param2, String[] param3) {
      // $FF: Couldn't be decompiled
   }

   private void processPendingFlagChange(Store var1, EmailContent.Mailbox var2, boolean var3, boolean var4, EmailContent.Message var5) throws MessagingException {
      if(!(var1 instanceof Pop3Store)) {
         if(var5.mServerId != null) {
            if(!var5.mServerId.equals("")) {
               if(!var5.mServerId.startsWith("Local-")) {
                  if(var2.mType != 3) {
                     if(var2.mType != 4) {
                        String var6 = var2.mDisplayName;
                        Folder var7 = var1.getFolder(var6);
                        if(var7.exists()) {
                           Folder.OpenMode var8 = Folder.OpenMode.READ_WRITE;
                           var7.open(var8, (Folder.PersistentDataCallbacks)null);
                           Folder.OpenMode var9 = var7.getMode();
                           Folder.OpenMode var10 = Folder.OpenMode.READ_WRITE;
                           if(var9 != var10) {
                              var7.close((boolean)0);
                           } else {
                              String var11 = var5.mServerId;
                              Message var12 = var7.getMessage(var11);
                              if(var12 == null) {
                                 var7.close((boolean)0);
                              } else {
                                 if(Email.DEBUG) {
                                    StringBuilder var13 = (new StringBuilder()).append("Update flags for msg id=");
                                    long var14 = var5.mId;
                                    StringBuilder var16 = var13.append(var14).append(" read=");
                                    boolean var17 = var5.mFlagRead;
                                    StringBuilder var18 = var16.append(var17).append(" flagged=");
                                    boolean var19 = var5.mFlagFavorite;
                                    String var20 = var18.append(var19).toString();
                                    int var21 = Log.d("Email", var20);
                                 }

                                 Message[] var22 = new Message[]{var12};
                                 if(var3) {
                                    Flag[] var23 = FLAG_LIST_SEEN;
                                    boolean var24 = var5.mFlagRead;
                                    var7.setFlags(var22, var23, var24);
                                 }

                                 if(var4) {
                                    Flag[] var25 = FLAG_LIST_FLAGGED;
                                    boolean var26 = var5.mFlagFavorite;
                                    var7.setFlags(var22, var25, var26);
                                 }

                                 var7.close((boolean)0);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void processPendingMoveToTrash(Store var1, EmailContent.Account var2, EmailContent.Mailbox var3, EmailContent.Message var4, EmailContent.Message var5) throws MessagingException {
      if(var5.mServerId != null) {
         if(!var5.mServerId.equals("")) {
            if(!var5.mServerId.startsWith("Local-")) {
               Context var6 = this.mContext;
               long var7 = var4.mMailboxKey;
               EmailContent.Mailbox var9 = EmailContent.Mailbox.restoreMailboxWithId(var6, var7);
               if(var9 != null) {
                  if(var9.mType != 6) {
                     if(var2.getDeletePolicy() == 0) {
                        EmailContent.Message var10 = new EmailContent.Message();
                        long var11 = var4.mAccountKey;
                        var10.mAccountKey = var11;
                        long var13 = var4.mMailboxKey;
                        var10.mMailboxKey = var13;
                        var10.mFlagLoaded = 3;
                        var10.mFlagRead = (boolean)1;
                        String var15 = var4.mServerId;
                        var10.mServerId = var15;
                        Context var16 = this.mContext;
                        var10.save(var16);
                     } else {
                        String var18 = var9.mDisplayName;
                        Folder var19 = var1.getFolder(var18);
                        if(var19.exists()) {
                           Folder.OpenMode var20 = Folder.OpenMode.READ_WRITE;
                           var19.open(var20, (Folder.PersistentDataCallbacks)null);
                           Folder.OpenMode var21 = var19.getMode();
                           Folder.OpenMode var22 = Folder.OpenMode.READ_WRITE;
                           if(var21 != var22) {
                              var19.close((boolean)0);
                           } else {
                              String var23 = var4.mServerId;
                              Message var24 = var19.getMessage(var23);
                              if(var24 == null) {
                                 var19.close((boolean)0);
                              } else {
                                 String var25 = var3.mDisplayName;
                                 Folder var26 = var1.getFolder(var25);
                                 if(!var26.exists()) {
                                    Folder.FolderType var27 = Folder.FolderType.HOLDS_MESSAGES;
                                    var26.create(var27);
                                 }

                                 if(var26.exists()) {
                                    Folder.OpenMode var29 = Folder.OpenMode.READ_WRITE;
                                    var26.open(var29, (Folder.PersistentDataCallbacks)null);
                                    Folder.OpenMode var30 = var26.getMode();
                                    Folder.OpenMode var31 = Folder.OpenMode.READ_WRITE;
                                    if(var30 != var31) {
                                       var19.close((boolean)0);
                                       var26.close((boolean)0);
                                       return;
                                    }

                                    Message[] var32 = new Message[]{var24};
                                    MessagingController.6 var33 = new MessagingController.6(var5);
                                    var19.copyMessages(var32, var26, var33);
                                    var26.close((boolean)0);
                                 }

                                 Flag var34 = Flag.DELETED;
                                 var24.setFlag(var34, (boolean)1);
                                 Message[] var35 = var19.expunge();
                                 var19.close((boolean)0);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void processPendingUpdatesSynchronous(EmailContent.Account param1, ContentResolver param2, String[] param3) {
      // $FF: Couldn't be decompiled
   }

   private void processPendingUploadsSynchronous(EmailContent.Account param1, ContentResolver param2, String[] param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void processUploadMessage(ContentResolver var1, Store var2, EmailContent.Account var3, EmailContent.Mailbox var4, long var5) throws MessagingException {
      EmailContent.Message var7 = EmailContent.Message.restoreMessageWithId(this.mContext, var5);
      byte var8;
      if(var7 == null) {
         var8 = 1;
         String var9 = "Upsync failed for null message, id=" + var5;
         int var10 = Log.d("Email", var9);
      } else if(var4.mType == 3) {
         var8 = 0;
         String var13 = "Upsync skipped for mailbox=drafts, id=" + var5;
         int var14 = Log.d("Email", var13);
      } else if(var4.mType == 4) {
         var8 = 0;
         String var15 = "Upsync skipped for mailbox=outbox, id=" + var5;
         int var16 = Log.d("Email", var15);
      } else if(var4.mType == 6) {
         var8 = 0;
         String var17 = "Upsync skipped for mailbox=trash, id=" + var5;
         int var18 = Log.d("Email", var17);
      } else {
         String var19 = "Upsyc triggered for message id=" + var5;
         int var20 = Log.d("Email", var19);
         var8 = this.processPendingAppend(var2, var3, var4, var7);
      }

      if(var8 != 0) {
         Uri var11 = ContentUris.withAppendedId(EmailContent.Message.UPDATED_CONTENT_URI, var5);
         var1.delete(var11, (String)null, (String[])null);
      }
   }

   private void put(String var1, MessagingListener var2, Runnable var3) {
      try {
         MessagingController.Command var4 = new MessagingController.Command((MessagingController.1)null);
         var4.listener = var2;
         var4.runnable = var3;
         var4.description = var1;
         this.mCommands.add(var4);
      } catch (IllegalStateException var7) {
         throw new Error(var7);
      }
   }

   private void saveOrUpdate(EmailContent var1) {
      if(var1.isSaved()) {
         Context var2 = this.mContext;
         ContentValues var3 = var1.toContentValues();
         var1.update(var2, var3);
      } else {
         Context var5 = this.mContext;
         var1.save(var5);
      }
   }

   private StoreSynchronizer.SyncResults synchronizeMailboxGeneric(EmailContent.Account param1, EmailContent.Mailbox param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void synchronizeMailboxSynchronous(EmailContent.Account param1, EmailContent.Mailbox param2) {
      // $FF: Couldn't be decompiled
   }

   public void addListener(MessagingListener var1) {
      this.mListeners.addListener(var1);
   }

   public void checkMail(long var1, long var3, MessagingListener var5) {
      GroupMessagingListener var6 = this.mListeners;
      Context var7 = this.mContext;
      var6.checkMailStarted(var7, var1, var3);
      this.listFolders(var1, (MessagingListener)null);
      MessagingController.10 var17 = new MessagingController.10(var1, var3);
      this.put("checkMail", var5, var17);
   }

   public boolean isBusy() {
      return this.mBusy;
   }

   public void listFolders(long var1, MessagingListener var3) {
      EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(this.mContext, var1);
      if(var4 != null) {
         this.mListeners.listFoldersStarted(var1);
         MessagingController.1 var5 = new MessagingController.1(var4, var1);
         this.put("listFolders", var3, var5);
      }
   }

   public void loadAttachment(long var1, long var3, long var5, long var7, MessagingListener var9) {
      GroupMessagingListener var10 = this.mListeners;
      var10.loadAttachmentStarted(var1, var3, var7, (boolean)1);
      MessagingController.8 var26 = new MessagingController.8(var1, var7, var3, var5);
      String var28 = "loadAttachment";
      this.put(var28, var9, var26);
   }

   public void loadMessageForView(long var1, MessagingListener var3) {
      this.mListeners.loadMessageForViewStarted(var1);
      MessagingController.7 var4 = new MessagingController.7(var1);
      this.put("loadMessageForViewRemote", var3, var4);
   }

   public void processPendingActions(long var1) {
      MessagingController.5 var3 = new MessagingController.5(var1);
      this.put("processPendingActions", (MessagingListener)null, var3);
   }

   void pruneCachedAttachments(long param1) {
      // $FF: Couldn't be decompiled
   }

   public void removeListener(MessagingListener var1) {
      this.mListeners.removeListener(var1);
   }

   public void run() {
      Process.setThreadPriority(10);

      while(true) {
         MessagingController.Command var1;
         while(true) {
            try {
               var1 = (MessagingController.Command)this.mCommands.take();
               break;
            } catch (InterruptedException var6) {
               ;
            }
         }

         label31: {
            if(var1.listener != null) {
               MessagingListener var2 = var1.listener;
               if(!this.isActiveListener(var2)) {
                  break label31;
               }
            }

            this.mBusy = (boolean)1;
            var1.runnable.run();
            GroupMessagingListener var3 = this.mListeners;
            byte var4;
            if(this.mCommands.size() > 0) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            var3.controllerCommandCompleted((boolean)var4);
         }

         this.mBusy = (boolean)0;
      }
   }

   public void sendPendingMessages(EmailContent.Account var1, long var2, MessagingListener var4) {
      MessagingController.9 var5 = new MessagingController.9(var1, var2);
      this.put("sendPendingMessages", var4, var5);
   }

   public void sendPendingMessagesSynchronous(EmailContent.Account param1, long param2) {
      // $FF: Couldn't be decompiled
   }

   public void synchronizeMailbox(EmailContent.Account var1, EmailContent.Mailbox var2, MessagingListener var3) {
      if(var2.mType != 4) {
         GroupMessagingListener var4 = this.mListeners;
         long var5 = var1.mId;
         long var7 = var2.mId;
         var4.synchronizeMailboxStarted(var5, var7);
         MessagingController.2 var9 = new MessagingController.2(var1, var2);
         this.put("synchronizeMailbox", var3, var9);
      }
   }

   class 9 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$sentFolderId;


      9(EmailContent.Account var2, long var3) {
         this.val$account = var2;
         this.val$sentFolderId = var3;
      }

      public void run() {
         MessagingController var1 = MessagingController.this;
         EmailContent.Account var2 = this.val$account;
         long var3 = this.val$sentFolderId;
         var1.sendPendingMessagesSynchronous(var2, var3);
      }
   }

   private static class Command {

      public String description;
      public MessagingListener listener;
      public Runnable runnable;


      private Command() {}

      // $FF: synthetic method
      Command(MessagingController.1 var1) {
         this();
      }

      public String toString() {
         return this.description;
      }
   }

   class 5 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;


      5(long var2) {
         this.val$accountId = var2;
      }

      public void run() {
         try {
            Context var1 = MessagingController.this.mContext;
            long var2 = this.val$accountId;
            EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(var1, var2);
            if(var4 != null) {
               MessagingController.this.processPendingActionsSynchronous(var4);
            }
         } catch (MessagingException var6) {
            ;
         }
      }
   }

   class 6 implements Folder.MessageUpdateCallbacks {

      // $FF: synthetic field
      final EmailContent.Message val$newMessage;


      6(EmailContent.Message var2) {
         this.val$newMessage = var2;
      }

      public void onMessageNotFound(Message var1) {
         ContentResolver var2 = MessagingController.this.mContext.getContentResolver();
         Uri var3 = this.val$newMessage.getUri();
         var2.delete(var3, (String)null, (String[])null);
      }

      public void onMessageUidChange(Message var1, String var2) {
         ContentValues var3 = new ContentValues();
         var3.put("syncServerId", var2);
         ContentResolver var4 = MessagingController.this.mContext.getContentResolver();
         Uri var5 = this.val$newMessage.getUri();
         var4.update(var5, var3, (String)null, (String[])null);
      }
   }

   class 7 implements Runnable {

      // $FF: synthetic field
      final long val$messageId;


      7(long var2) {
         this.val$messageId = var2;
      }

      public void run() {
         try {
            Context var1 = MessagingController.this.mContext;
            long var2 = this.val$messageId;
            EmailContent.Message var4 = EmailContent.Message.restoreMessageWithId(var1, var2);
            if(var4 == null) {
               GroupMessagingListener var5 = MessagingController.this.mListeners;
               long var6 = this.val$messageId;
               var5.loadMessageForViewFailed(var6, "Unknown message");
            } else if(var4.mFlagLoaded == 1) {
               GroupMessagingListener var8 = MessagingController.this.mListeners;
               long var9 = this.val$messageId;
               var8.loadMessageForViewFinished(var9);
            } else {
               Context var16 = MessagingController.this.mContext;
               long var17 = var4.mAccountKey;
               EmailContent.Account var19 = EmailContent.Account.restoreAccountWithId(var16, var17);
               Context var20 = MessagingController.this.mContext;
               long var21 = var4.mMailboxKey;
               EmailContent.Mailbox var23 = EmailContent.Mailbox.restoreMailboxWithId(var20, var21);
               if(var19 != null && var23 != null) {
                  Context var32 = MessagingController.this.mContext;
                  String var33 = var19.getStoreUri(var32);
                  Context var34 = MessagingController.this.mContext;
                  Store var35 = Store.getInstance(var33, var34, (Store.PersistentDataCallbacks)null);
                  String var36 = var23.mDisplayName;
                  Folder var37 = var35.getFolder(var36);
                  Folder.OpenMode var38 = Folder.OpenMode.READ_WRITE;
                  var37.open(var38, (Folder.PersistentDataCallbacks)null);
                  String var39 = var4.mServerId;
                  Message var40 = var37.getMessage(var39);
                  FetchProfile var41 = new FetchProfile();
                  FetchProfile.Item var42 = FetchProfile.Item.BODY;
                  var41.add(var42);
                  Message[] var44 = new Message[]{var40};
                  var37.fetch(var44, var41, (Folder.MessageRetrievalListener)null);
                  MessagingController.this.copyOneMessageToProvider(var40, var19, var23, 1);
                  GroupMessagingListener var45 = MessagingController.this.mListeners;
                  long var46 = this.val$messageId;
                  var45.loadMessageForViewFinished(var46);
               } else {
                  GroupMessagingListener var24 = MessagingController.this.mListeners;
                  long var25 = this.val$messageId;
                  var24.loadMessageForViewFailed(var25, "null account or mailbox");
               }
            }
         } catch (MessagingException var48) {
            GroupMessagingListener var12 = MessagingController.this.mListeners;
            long var13 = this.val$messageId;
            String var15 = var48.getMessage();
            var12.loadMessageForViewFailed(var13, var15);
         } catch (RuntimeException var49) {
            GroupMessagingListener var28 = MessagingController.this.mListeners;
            long var29 = this.val$messageId;
            String var31 = var49.getMessage();
            var28.loadMessageForViewFailed(var29, var31);
         }
      }
   }

   class 8 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$attachmentId;
      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final long val$messageId;


      8(long var2, long var4, long var6, long var8) {
         this.val$accountId = var2;
         this.val$attachmentId = var4;
         this.val$messageId = var6;
         this.val$mailboxId = var8;
      }

      public void run() {
         Folder var1 = null;
         boolean var160 = false;

         byte var19;
         label264: {
            label265: {
               label266: {
                  label267: {
                     label268: {
                        try {
                           var160 = true;
                           Context var2 = MessagingController.this.mContext;
                           long var3 = this.val$accountId;
                           long var5 = this.val$attachmentId;
                           File var7 = AttachmentProvider.getAttachmentFilename(var2, var3, var5);
                           Context var8 = MessagingController.this.mContext;
                           long var9 = this.val$attachmentId;
                           EmailContent.Attachment var11 = EmailContent.Attachment.restoreAttachmentWithId(var8, var9);
                           if(var11 == null) {
                              GroupMessagingListener var12 = MessagingController.this.mListeners;
                              long var13 = this.val$accountId;
                              long var15 = this.val$messageId;
                              long var17 = this.val$attachmentId;
                              var12.loadAttachmentFailed(var13, var15, var17, "Attachment is null");
                              var160 = false;
                              break label265;
                           }

                           if(var7.exists() && var11.mContentUri != null) {
                              GroupMessagingListener var22 = MessagingController.this.mListeners;
                              long var23 = this.val$accountId;
                              long var25 = this.val$messageId;
                              long var27 = this.val$attachmentId;
                              var22.loadAttachmentFinished(var23, var25, var27);
                              var160 = false;
                              break label264;
                           }

                           Context var32 = MessagingController.this.mContext;
                           long var33 = this.val$accountId;
                           EmailContent.Account var35 = EmailContent.Account.restoreAccountWithId(var32, var33);
                           Context var36 = MessagingController.this.mContext;
                           long var37 = this.val$mailboxId;
                           EmailContent.Mailbox var39 = EmailContent.Mailbox.restoreMailboxWithId(var36, var37);
                           Context var40 = MessagingController.this.mContext;
                           long var41 = this.val$messageId;
                           EmailContent.Message var43 = EmailContent.Message.restoreMessageWithId(var40, var41);
                           if(var35 == null || var39 == null || var43 == null) {
                              GroupMessagingListener var44 = MessagingController.this.mListeners;
                              long var45 = this.val$accountId;
                              long var47 = this.val$messageId;
                              long var49 = this.val$attachmentId;
                              var44.loadAttachmentFailed(var45, var47, var49, "Account, mailbox, message or attachment are null");
                              var160 = false;
                              break label267;
                           }

                           Context var54 = MessagingController.this.mContext;
                           String var55 = var35.getStoreUri(var54);
                           Context var56 = MessagingController.this.mContext;
                           Store var57 = Store.getInstance(var55, var56, (Store.PersistentDataCallbacks)null);
                           String var58 = var39.mDisplayName;
                           var1 = var57.getFolder(var58);
                           Folder.OpenMode var61 = Folder.OpenMode.READ_WRITE;
                           Object var64 = null;
                           var1.open(var61, (Folder.PersistentDataCallbacks)var64);
                           String var65 = var43.mServerId;
                           Message var68 = var1.createMessage(var65);
                           MimeBodyPart var69 = new MimeBodyPart();
                           int var70 = (int)var11.mSize;
                           var69.setSize(var70);
                           String var73 = var11.mLocation;
                           String var75 = "X-Android-Attachment-StoreData";
                           var69.setHeader(var75, var73);
                           Object[] var77 = new Object[2];
                           String var78 = var11.mMimeType;
                           var77[0] = var78;
                           String var79 = var11.mFileName;
                           var77[1] = var79;
                           String var80 = String.format("%s;\n name=\"%s\"", var77);
                           String var82 = "Content-Type";
                           var69.setHeader(var82, var80);
                           String var85 = "Content-Transfer-Encoding";
                           String var86 = "base64";
                           var69.setHeader(var85, var86);
                           MimeMultipart var87 = new MimeMultipart();
                           String var89 = "mixed";
                           var87.setSubType(var89);
                           var87.addBodyPart(var69);
                           String var93 = "Content-Type";
                           String var94 = "multipart/mixed";
                           var68.setHeader(var93, var94);
                           var68.setBody(var87);
                           FetchProfile var97 = new FetchProfile();
                           boolean var100 = var97.add(var69);
                           Message[] var101 = new Message[]{var68};
                           Object var105 = null;
                           var1.fetch(var101, var97, (Folder.MessageRetrievalListener)var105);
                           Context var106 = MessagingController.this.mContext;
                           long var107 = this.val$accountId;
                           LegacyConversions.saveAttachmentBody(var106, var69, var11, var107);
                           GroupMessagingListener var114 = MessagingController.this.mListeners;
                           long var115 = this.val$accountId;
                           long var117 = this.val$messageId;
                           long var119 = this.val$attachmentId;
                           var114.loadAttachmentFinished(var115, var117, var119);
                           var160 = false;
                        } catch (MessagingException var168) {
                           GroupMessagingListener var125 = MessagingController.this.mListeners;
                           long var126 = this.val$accountId;
                           long var128 = this.val$messageId;
                           long var130 = this.val$attachmentId;
                           String var132 = var168.getMessage();
                           var125.loadAttachmentFailed(var126, var128, var130, var132);
                           var160 = false;
                           break label266;
                        } catch (IOException var169) {
                           StringBuilder var137 = (new StringBuilder()).append("Error while storing attachment.");
                           String var138 = var169.toString();
                           String var139 = var137.append(var138).toString();
                           int var140 = Log.e("Email", var139);
                           var160 = false;
                           break label268;
                        } finally {
                           if(var160) {
                              if(var1 != null) {
                                 byte var145 = 0;

                                 try {
                                    var1.close((boolean)var145);
                                 } catch (Exception var161) {
                                    ;
                                 }
                              }

                           }
                        }

                        if(var1 == null) {
                           return;
                        }

                        var19 = 0;

                        try {
                           var1.close((boolean)var19);
                           return;
                        } catch (Exception var166) {
                           return;
                        }
                     }

                     if(var1 == null) {
                        return;
                     }

                     var19 = 0;

                     try {
                        var1.close((boolean)var19);
                        return;
                     } catch (Exception var162) {
                        return;
                     }
                  }

                  if(true) {
                     return;
                  }

                  var19 = 0;

                  try {
                     Object var51 = null;
                     ((Folder)var51).close((boolean)var19);
                     return;
                  } catch (Exception var167) {
                     return;
                  }
               }

               if(var1 == null) {
                  return;
               }

               var19 = 0;

               try {
                  var1.close((boolean)var19);
                  return;
               } catch (Exception var163) {
                  return;
               }
            }

            if(true) {
               return;
            }

            var19 = 0;

            try {
               Object var20 = null;
               ((Folder)var20).close((boolean)var19);
               return;
            } catch (Exception var164) {
               return;
            }
         }

         if(false) {
            var19 = 0;

            try {
               Object var29 = null;
               ((Folder)var29).close((boolean)var19);
            } catch (Exception var165) {
               ;
            }
         }
      }
   }

   private static class LocalMessageInfo {

      private static final int COLUMN_FLAG_FAVORITE = 2;
      private static final int COLUMN_FLAG_LOADED = 3;
      private static final int COLUMN_FLAG_READ = 1;
      private static final int COLUMN_ID = 0;
      private static final int COLUMN_SERVER_ID = 4;
      private static final String[] PROJECTION;
      final int mCursorIndex;
      final boolean mFlagFavorite;
      final int mFlagLoaded;
      final boolean mFlagRead;
      final long mId;
      final String mServerId;


      static {
         String[] var0 = new String[]{"_id", "flagRead", "flagFavorite", "flagLoaded", "syncServerId", "mailboxKey", "accountKey"};
         PROJECTION = var0;
      }

      public LocalMessageInfo(Cursor var1) {
         int var2 = var1.getPosition();
         this.mCursorIndex = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         byte var5;
         if(var1.getInt(1) != 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         this.mFlagRead = (boolean)var5;
         byte var6;
         if(var1.getInt(2) != 0) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         this.mFlagFavorite = (boolean)var6;
         int var7 = var1.getInt(3);
         this.mFlagLoaded = var7;
         String var8 = var1.getString(4);
         this.mServerId = var8;
      }

      // $FF: synthetic method
      static String[] access$500() {
         return PROJECTION;
      }
   }

   private static class LocalMailboxInfo {

      private static final int COLUMN_ACCOUNT_KEY = 2;
      private static final int COLUMN_DISPLAY_NAME = 1;
      private static final int COLUMN_ID = 0;
      private static final int COLUMN_TYPE = 3;
      private static final String[] PROJECTION;
      final long mAccountKey;
      final String mDisplayName;
      final long mId;
      final int mType;


      static {
         String[] var0 = new String[]{"_id", "displayName", "accountKey", "type"};
         PROJECTION = var0;
      }

      public LocalMailboxInfo(Cursor var1) {
         long var2 = var1.getLong(0);
         this.mId = var2;
         String var4 = var1.getString(1);
         this.mDisplayName = var4;
         long var5 = var1.getLong(2);
         this.mAccountKey = var5;
         int var7 = var1.getInt(3);
         this.mType = var7;
      }
   }

   class 10 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$tag;


      10(long var2, long var4) {
         this.val$accountId = var2;
         this.val$tag = var4;
      }

      public void run() {
         long var1 = 65535L;
         Context var3 = MessagingController.this.mContext;
         long var4 = this.val$accountId;
         EmailContent.Account var6 = EmailContent.Account.restoreAccountWithId(var3, var4);
         if(var6 != null) {
            Context var7 = MessagingController.this.mContext;
            long var8 = this.val$accountId;
            long var10 = EmailContent.Mailbox.findMailboxOfType(var7, var8, 5);
            if(var10 != 65535L) {
               MessagingController.this.sendPendingMessagesSynchronous(var6, var10);
            }

            Context var12 = MessagingController.this.mContext;
            long var13 = this.val$accountId;
            var1 = EmailContent.Mailbox.findMailboxOfType(var12, var13, 0);
            if(var1 != 65535L) {
               EmailContent.Mailbox var15 = EmailContent.Mailbox.restoreMailboxWithId(MessagingController.this.mContext, var1);
               if(var15 != null) {
                  MessagingController.this.synchronizeMailboxSynchronous(var6, var15);
               }
            }
         }

         GroupMessagingListener var16 = MessagingController.this.mListeners;
         Context var17 = MessagingController.this.mContext;
         long var18 = this.val$accountId;
         long var20 = this.val$tag;
         var16.checkMailFinished(var17, var18, var1, var20);
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;


      2(EmailContent.Account var2, EmailContent.Mailbox var3) {
         this.val$account = var2;
         this.val$folder = var3;
      }

      public void run() {
         MessagingController var1 = MessagingController.this;
         EmailContent.Account var2 = this.val$account;
         EmailContent.Mailbox var3 = this.val$folder;
         var1.synchronizeMailboxSynchronous(var2, var3);
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$accountId;


      1(EmailContent.Account var2, long var3) {
         this.val$account = var2;
         this.val$accountId = var3;
      }

      public void run() {
         Cursor var1 = null;
         boolean var79 = false;

         label165: {
            try {
               var79 = true;
               EmailContent.Account var2 = this.val$account;
               Context var3 = MessagingController.this.mContext;
               String var4 = var2.getStoreUri(var3);
               Context var5 = MessagingController.this.mContext;
               Folder[] var6 = Store.getInstance(var4, var5, (Store.PersistentDataCallbacks)null).getPersonalNamespaces();
               HashSet var7 = new HashSet();
               int var8 = 0;

               for(int var9 = var6.length; var8 < var9; ++var8) {
                  String var10 = var6[var8].getName();
                  boolean var13 = var7.add(var10);
               }

               HashMap var14 = new HashMap();
               HashSet var15 = new HashSet();
               ContentResolver var16 = MessagingController.this.mContext.getContentResolver();
               Uri var17 = EmailContent.Mailbox.CONTENT_URI;
               String[] var18 = MessagingController.LocalMailboxInfo.PROJECTION;
               String[] var19 = new String[1];
               String var20 = String.valueOf(this.val$account.mId);
               var19[0] = var20;

               String var27;
               boolean var30;
               for(var1 = var16.query(var17, var18, "accountKey=?", var19, (String)null); var1.moveToNext(); var30 = var15.add(var27)) {
                  MessagingController.LocalMailboxInfo var21 = new MessagingController.LocalMailboxInfo(var1);
                  String var22 = var21.mDisplayName;
                  Object var26 = var14.put(var22, var21);
                  var27 = var21.mDisplayName;
               }

               if(!var7.equals(var15)) {
                  HashSet var37 = new HashSet(var15);
                  var37.removeAll(var7);
                  Iterator var41 = var37.iterator();

                  while(var41.hasNext()) {
                     String var42 = (String)var41.next();
                     MessagingController.LocalMailboxInfo var45 = (MessagingController.LocalMailboxInfo)var14.get(var42);
                     switch(var45.mType) {
                     case 0:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                        break;
                     case 1:
                     case 2:
                     default:
                        Context var46 = MessagingController.this.mContext;
                        long var47 = this.val$accountId;
                        long var49 = var45.mId;
                        AttachmentProvider.deleteAllMailboxAttachmentFiles(var46, var47, var49);
                        Uri var51 = EmailContent.Mailbox.CONTENT_URI;
                        long var52 = var45.mId;
                        Uri var54 = ContentUris.withAppendedId(var51, var52);
                        ContentResolver var55 = MessagingController.this.mContext.getContentResolver();
                        Object var57 = null;
                        Object var58 = null;
                        var55.delete(var54, (String)var57, (String[])var58);
                     }
                  }

                  boolean var63 = var7.removeAll(var15);
                  var41 = var7.iterator();

                  while(var41.hasNext()) {
                     String var64 = (String)var41.next();
                     EmailContent.Mailbox var65 = new EmailContent.Mailbox();
                     var65.mDisplayName = var64;
                     long var67 = this.val$account.mId;
                     var65.mAccountKey = var67;
                     Context var69 = MessagingController.this.mContext;
                     int var71 = LegacyConversions.inferMailboxTypeFromName(var69, var64);
                     var65.mType = var71;
                     var65.mFlagVisible = (boolean)1;
                     var65.mVisibleLimit = 25;
                     Context var72 = MessagingController.this.mContext;
                     var65.save(var72);
                  }
               }

               GroupMessagingListener var74 = MessagingController.this.mListeners;
               long var75 = this.val$accountId;
               var74.listFoldersFinished(var75);
               var79 = false;
               break label165;
            } catch (Exception var80) {
               GroupMessagingListener var32 = MessagingController.this.mListeners;
               long var33 = this.val$accountId;
               var32.listFoldersFailed(var33, "");
               var79 = false;
            } finally {
               if(var79) {
                  if(var1 != null) {
                     var1.close();
                  }

               }
            }

            if(var1 == null) {
               return;
            }

            var1.close();
            return;
         }

         if(var1 != null) {
            var1.close();
         }
      }
   }

   class 4 implements Folder.MessageRetrievalListener {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;


      4(EmailContent.Account var2, EmailContent.Mailbox var3) {
         this.val$account = var2;
         this.val$folder = var3;
      }

      public void messageRetrieved(Message var1) {
         MessagingController var2 = MessagingController.this;
         EmailContent.Account var3 = this.val$account;
         EmailContent.Mailbox var4 = this.val$folder;
         var2.copyOneMessageToProvider(var1, var3, var4, 1);
      }
   }

   class 3 implements Folder.MessageRetrievalListener {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;
      // $FF: synthetic field
      final HashMap val$localMapCopy;
      // $FF: synthetic field
      final ArrayList val$newMessages;


      3(HashMap var2, EmailContent.Account var3, EmailContent.Mailbox var4, ArrayList var5) {
         this.val$localMapCopy = var2;
         this.val$account = var3;
         this.val$folder = var4;
         this.val$newMessages = var5;
      }

      public void messageRetrieved(Message param1) {
         // $FF: Couldn't be decompiled
      }
   }
}
