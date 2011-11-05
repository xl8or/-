package com.android.email;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Process;
import android.text.Html;
import android.util.Log;
import com.android.email.BadgeManager;
import com.android.email.Email;
import com.android.email.GroupMessagingListener;
import com.android.email.LegacyConversions;
import com.android.email.MessagingListener;
import com.android.email.SecurityPolicy;
import com.android.email.mail.Address;
import com.android.email.mail.FetchProfile;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.mail.StoreSynchronizer;
import com.android.email.provider.AttachmentProvider;
import com.android.email.provider.EmailContent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessagingController implements Runnable {

   private static Flag[] FLAG_LIST_ANSWERED;
   private static Flag[] FLAG_LIST_FLAGGED;
   private static Flag[] FLAG_LIST_SEEN;
   public static final String LOCAL_ACCOUNTMOVED_SERVERID_PREFIX = "LocalAccountMoved-";
   private static final String LOCAL_SERVERID_PREFIX = "Local-";
   private static final int MAX_SMALL_MESSAGE_SIZE = 25600;
   private static ContentValues PRUNE_ATTACHMENT_CV;
   private static String[] PRUNE_ATTACHMENT_PROJECTION;
   private static final String STR_M_CONTENT = "m_content";
   private static final String TAG = "MsgControl >>>";
   private static MessagingController inst;
   private boolean mBusy;
   private BlockingQueue<MessagingController.Command> mCommands;
   private Context mContext;
   private GroupMessagingListener mListeners;
   private boolean mRemoteSync;
   private Thread mThread;


   static {
      Flag[] var0 = new Flag[1];
      Flag var1 = Flag.SEEN;
      var0[0] = var1;
      FLAG_LIST_SEEN = var0;
      Flag[] var2 = new Flag[1];
      Flag var3 = Flag.FLAGGED;
      var2[0] = var3;
      FLAG_LIST_FLAGGED = var2;
      Flag[] var4 = new Flag[1];
      Flag var5 = Flag.ANSWERED;
      var4[0] = var5;
      FLAG_LIST_ANSWERED = var4;
      String[] var6 = new String[]{"location", "contentId"};
      PRUNE_ATTACHMENT_PROJECTION = var6;
      PRUNE_ATTACHMENT_CV = new ContentValues();
      PRUNE_ATTACHMENT_CV.putNull("contentUri");
      inst = null;
   }

   protected MessagingController(Context var1) {
      LinkedBlockingQueue var2 = new LinkedBlockingQueue();
      this.mCommands = var2;
      GroupMessagingListener var3 = new GroupMessagingListener();
      this.mListeners = var3;
      this.mRemoteSync = (boolean)1;
      this.mContext = var1;
      Thread var4 = new Thread(this);
      this.mThread = var4;
      this.mThread.start();
   }

   private boolean IsEAS(EmailContent.Account param1) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static boolean access$1100(MessagingController var0, EmailContent.Account var1, ContentResolver var2, String var3, long var4, long var6) {
      return var0.processPendingMoveSynchronous(var1, var2, var3, var4, var6);
   }

   // $FF: synthetic method
   static boolean access$1300(MessagingController var0, long var1, long var3, EmailContent.Account var5, long var6) throws MessagingException {
      return var0.processPendingAccountMoveActionsSynchronous(var1, var3, var5, var6);
   }

   // $FF: synthetic method
   static boolean access$700(MessagingController var0) {
      return var0.mRemoteSync;
   }

   // $FF: synthetic method
   static void access$800(MessagingController var0, EmailContent var1) {
      var0.saveOrUpdate(var1);
   }

   private boolean checkITPolicy_AllowPOPIMAP(EmailContent.Account var1) throws MessagingException {
      boolean var2;
      if(SecurityPolicy.getInstance(this.mContext).IsAllowPOPIMAPEmailSetted() && !this.IsEAS(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private void copyOneMessageToProvider(Message param1, EmailContent.Account param2, EmailContent.Mailbox param3, int param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public static MessagingController getInstance(Context var0) {
      synchronized(MessagingController.class){}

      MessagingController var1;
      try {
         if(inst == null) {
            inst = new MessagingController(var0);
         }

         var1 = inst;
      } finally {
         ;
      }

      return var1;
   }

   public static void injectMockController(MessagingController var0) {
      inst = var0;
   }

   private boolean isActiveListener(MessagingListener var1) {
      return this.mListeners.isActiveListener(var1);
   }

   private boolean loadMessageForViewSyncronous(long param1) {
      // $FF: Couldn't be decompiled
   }

   private boolean processAccountMoveUploadMessage(ContentResolver param1, Store param2, EmailContent.Account param3, long param4, EmailContent.Mailbox param6, long param7) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private boolean processPendingAccountMoveActionsSynchronous(long var1, long var3, EmailContent.Account var5, long var6) throws MessagingException {
      ContentResolver var8 = this.mContext.getContentResolver();
      String[] var9 = new String[1];
      String var10 = Long.toString(var5.mId);
      var9[0] = var10;
      return this.processPendingAccountMoveUploadsSynchronous(var1, var3, var5, var8, var9, var6);
   }

   private boolean processPendingAccountMoveAppend(Store var1, EmailContent.Account var2, long var3, EmailContent.Mailbox var5, EmailContent.Message var6) throws MessagingException {
      String var7 = var5.mDisplayName;
      Folder var8 = var1.getFolder(var7);
      StringBuilder var9 = (new StringBuilder()).append("DisplayName : ");
      String var10 = var5.mDisplayName;
      String var11 = var9.append(var10).toString();
      int var12 = Log.d("processPendingAccountMoveAppend", var11);
      boolean var15;
      if(!var8.exists()) {
         Folder.FolderType var13 = Folder.FolderType.HOLDS_MESSAGES;
         if(!var8.canCreate(var13)) {
            int var14 = Log.d("processPendingAccountMoveAppend", "This Pop account do not allowed upload sync. So we will just local move.");
            var15 = true;
            return var15;
         }

         Folder.FolderType var16 = Folder.FolderType.HOLDS_MESSAGES;
         if(!var8.create(var16)) {
            var15 = false;
            return var15;
         }
      }

      Folder.OpenMode var17 = Folder.OpenMode.READ_WRITE;
      var8.open(var17, (Folder.PersistentDataCallbacks)null);
      Folder.OpenMode var18 = var8.getMode();
      Folder.OpenMode var19 = Folder.OpenMode.READ_WRITE;
      if(var18 != var19) {
         int var20 = Log.d("processPendingAccountMoveAppend", "remoteFolder.getMode() != OpenMode.READ_WRITE.");
         var8.close((boolean)0);
         var15 = false;
      } else {
         Message var21 = null;
         if(var6.mServerId != null && var6.mServerId.length() > 0) {
            String var22 = var6.mServerId;
            var21 = var8.getMessage(var22);
         }

         if(var21 == null) {
            Message var23 = LegacyConversions.makeMessage(this.mContext, var6);
            FetchProfile var24 = new FetchProfile();
            FetchProfile.Item var25 = FetchProfile.Item.BODY;
            var24.add(var25);
            byte var27 = 1;

            try {
               Message[] var28 = new Message[var27];
               var28[0] = var23;
               var8.appendMessages(var28);
            } catch (MessagingException var33) {
               var8.close((boolean)0);
               var15 = false;
               return var15;
            }

            String var29 = var23.getUid();
            var6.mServerId = var29;
            if(var6.mServerId != null && (var6.mServerId == null || var6.mServerId.length() > 0)) {
               var8.close((boolean)0);
               var15 = true;
            } else {
               int var30 = Log.d("processPendingAccountMoveAppend", "message.mServerId is null. Sending is failed....");
               var8.close((boolean)0);
               var15 = false;
            }
         } else {
            int var32 = Log.d("processPendingAccountMoveAppend", "Target Account Server Already have same message. Something wrong. Do nothing.");
            var8.close((boolean)0);
            var15 = false;
         }
      }

      return var15;
   }

   private boolean processPendingAccountMoveUploadsSynchronous(long param1, long param3, EmailContent.Account param5, ContentResolver param6, String[] param7, long param8) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void processPendingActionsSynchronous(EmailContent.Account var1, boolean var2, boolean var3) throws MessagingException {
      ContentResolver var4 = this.mContext.getContentResolver();
      String[] var5 = new String[1];
      String var6 = Long.toString(var1.mId);
      var5[0] = var6;
      byte var7 = 0;
      if(var1 != null) {
         label32: {
            byte var13;
            try {
               Context var8 = this.mContext;
               String var9 = var1.getStoreUri(var8);
               Context var10 = this.mContext;
               Store.StoreInfo var11 = Store.StoreInfo.getStoreInfo(var9, var10);
               if(var11 == null) {
                  break label32;
               }

               String var12 = var11.mScheme;
               var13 = "pop3".equalsIgnoreCase(var12);
            } catch (Exception var16) {
               int var15 = Log.d("Email", "Error !!");
               break label32;
            }

            var7 = var13;
         }
      }

      if(var7 != 0 && !var2 && var3) {
         this.processPendingUploadsSynchronous(var1, var4, var5);
      }

      this.processPendingDeletesSynchronous(var1, var4, var5);
      if(var7 == 0 && !var2 && var3) {
         this.processPendingUploadsSynchronous(var1, var4, var5);
      }

      this.processPendingUpdatesSynchronous(var1, var4, var5, var2);
   }

   private boolean processPendingAppend(Store param1, EmailContent.Account param2, EmailContent.Mailbox param3, EmailContent.Message param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void processPendingDeleteForeverFromAnyfolder(Store var1, EmailContent.Account var2, EmailContent.Mailbox var3, EmailContent.Message var4) throws MessagingException {
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

   private void processPendingDeletesSynchronous(EmailContent.Account param1, ContentResolver param2, String[] param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void processPendingFlagChange(Store var1, EmailContent.Mailbox var2, boolean var3, boolean var4, boolean var5, EmailContent.Message var6) throws MessagingException {
      if(var6 != null) {
         if(var6.mServerId != null) {
            if(var6.mServerId.length() != 0) {
               if(!var6.mServerId.startsWith("Local-")) {
                  if(var2.mType != 3) {
                     if(var2.mType != 4) {
                        String var7 = var2.mDisplayName;
                        Folder var8 = var1.getFolder(var7);
                        if(var8.exists()) {
                           Folder.OpenMode var9 = Folder.OpenMode.READ_WRITE;
                           var8.open(var9, (Folder.PersistentDataCallbacks)null);
                           Folder.OpenMode var10 = var8.getMode();
                           Folder.OpenMode var11 = Folder.OpenMode.READ_WRITE;
                           if(var10 != var11) {
                              var8.close((boolean)0);
                           } else {
                              String var12 = var6.mServerId;
                              Message var13 = var8.getMessage(var12);
                              if(var13 == null) {
                                 var8.close((boolean)0);
                              } else {
                                 if(Email.DEBUG) {
                                    StringBuilder var14 = (new StringBuilder()).append("Update flags for msg id=");
                                    long var15 = var6.mId;
                                    StringBuilder var17 = var14.append(var15).append(" read=");
                                    boolean var18 = var6.mFlagRead;
                                    StringBuilder var19 = var17.append(var18).append(" flagged=");
                                    boolean var20 = var6.mFlagFavorite;
                                    String var21 = var19.append(var20).toString();
                                    int var22 = Log.d("Email", var21);
                                 }

                                 Message[] var23 = new Message[]{var13};
                                 if(var3) {
                                    Flag[] var24 = FLAG_LIST_SEEN;
                                    boolean var25 = var6.mFlagRead;
                                    var8.setFlags(var23, var24, var25);
                                 }

                                 if(var4) {
                                    Flag[] var26 = FLAG_LIST_FLAGGED;
                                    boolean var27 = var6.mFlagFavorite;
                                    var8.setFlags(var23, var26, var27);
                                 }

                                 if(var5) {
                                    Flag[] var28 = FLAG_LIST_ANSWERED;
                                    boolean var29 = var6.mFlagReply;
                                    var8.setFlags(var23, var28, var29);
                                 }

                                 var8.close((boolean)0);
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

   private boolean processPendingMoveSynchronous(EmailContent.Account var1, ContentResolver var2, String var3, long var4, long var6) {
      boolean var19;
      boolean var18;
      try {
         Context var8 = this.mContext;
         String var9 = var1.getStoreUri(var8);
         Context var10 = this.mContext;
         Store var11 = Store.getInstance(var9, var10, (Store.PersistentDataCallbacks)null);
         EmailContent.Mailbox var12 = EmailContent.Mailbox.restoreMailboxWithId(this.mContext, var4);
         var18 = this.processPendingMoveToTargetbox(var11, var1, var12, var3, var6);
      } catch (MessagingException var21) {
         var19 = false;
         return var19;
      }

      var19 = var18;
      return var19;
   }

   private boolean processPendingMoveToTargetbox(Store var1, EmailContent.Account var2, EmailContent.Mailbox var3, String var4, long var5) throws MessagingException {
      EmailContent.Mailbox var7 = EmailContent.Mailbox.restoreMailboxWithId(this.mContext, var5);
      boolean var8;
      if(var7 == null) {
         var8 = false;
      } else {
         String var9 = var7.mDisplayName;
         Folder var10 = var1.getFolder(var9);
         if(!var10.exists()) {
            Folder.FolderType var11 = Folder.FolderType.HOLDS_MESSAGES;
            var10.create(var11);
         }

         if(!var10.exists()) {
            var8 = false;
         } else {
            Folder.OpenMode var13 = Folder.OpenMode.READ_WRITE;
            var10.open(var13, (Folder.PersistentDataCallbacks)null);
            Folder.OpenMode var14 = var10.getMode();
            Folder.OpenMode var15 = Folder.OpenMode.READ_WRITE;
            if(var14 != var15) {
               var10.close((boolean)0);
               var8 = false;
            } else {
               Message var16 = var10.getMessage(var4);
               if(var16 == null) {
                  var10.close((boolean)0);
                  var8 = false;
               } else {
                  String var17 = var3.mDisplayName;
                  Folder var18 = var1.getFolder(var17);
                  StringBuilder var19 = (new StringBuilder()).append("newMailbox.mDisplayName:");
                  String var20 = var3.mDisplayName;
                  String var21 = var19.append(var20).toString();
                  int var22 = Log.d("processPendingMoveToTargetbox", var21);
                  if(!var18.exists()) {
                     Folder.FolderType var23 = Folder.FolderType.HOLDS_MESSAGES;
                     var18.create(var23);
                  }

                  if(var18.exists()) {
                     Folder.OpenMode var25 = Folder.OpenMode.READ_WRITE;
                     var18.open(var25, (Folder.PersistentDataCallbacks)null);
                     Folder.OpenMode var26 = var18.getMode();
                     Folder.OpenMode var27 = Folder.OpenMode.READ_WRITE;
                     if(var26 != var27) {
                        var10.close((boolean)0);
                        var18.close((boolean)0);
                        var8 = false;
                     } else {
                        Message[] var28 = new Message[]{var16};
                        MessagingController.14 var29 = new MessagingController.14();
                        var10.copyMessages(var28, var18, var29);
                        var18.close((boolean)0);
                        Flag var30 = Flag.FLAGGED;
                        var16.setFlag(var30, (boolean)0);
                        Flag var31 = Flag.DELETED;
                        var16.setFlag(var31, (boolean)1);
                        Message[] var32 = var10.expunge();
                        var10.close((boolean)0);
                        var8 = true;
                     }
                  } else {
                     var8 = false;
                  }
               }
            }
         }
      }

      return var8;
   }

   private void processPendingMoveToTrash(Store var1, EmailContent.Account var2, EmailContent.Mailbox var3, EmailContent.Message var4, EmailContent.Message var5, boolean var6) throws MessagingException {
      if(var5 != null) {
         if(var5.mServerId != null) {
            if(var5.mServerId.length() != 0) {
               if(!var5.mServerId.startsWith("Local-")) {
                  Context var7 = this.mContext;
                  long var8 = var4.mMailboxKey;
                  EmailContent.Mailbox var10 = EmailContent.Mailbox.restoreMailboxWithId(var7, var8);
                  if(var10 != null) {
                     if(var10.mType != 6 || var6) {
                        if(var2.getDeletePolicy() != 0) {
                           String var11 = var10.mDisplayName;
                           Folder var12 = var1.getFolder(var11);
                           if(var12.exists()) {
                              Folder.OpenMode var13 = Folder.OpenMode.READ_WRITE;
                              var12.open(var13, (Folder.PersistentDataCallbacks)null);
                              Folder.OpenMode var14 = var12.getMode();
                              Folder.OpenMode var15 = Folder.OpenMode.READ_WRITE;
                              if(var14 != var15) {
                                 var12.close((boolean)0);
                              } else {
                                 String var16 = var4.mServerId;
                                 Message var17 = var12.getMessage(var16);
                                 if(var17 == null) {
                                    var12.close((boolean)0);
                                 } else {
                                    String var18 = var3.mDisplayName;
                                    Folder var19 = var1.getFolder(var18);
                                    if(!var19.exists()) {
                                       Folder.FolderType var20 = Folder.FolderType.HOLDS_MESSAGES;
                                       var19.create(var20);
                                    }

                                    if(var19.exists()) {
                                       Folder.OpenMode var22 = Folder.OpenMode.READ_WRITE;
                                       var19.open(var22, (Folder.PersistentDataCallbacks)null);
                                       Folder.OpenMode var23 = var19.getMode();
                                       Folder.OpenMode var24 = Folder.OpenMode.READ_WRITE;
                                       if(var23 != var24) {
                                          var12.close((boolean)0);
                                          var19.close((boolean)0);
                                          return;
                                       }

                                       byte var25 = 1;

                                       try {
                                          Message[] var26 = new Message[var25];
                                          var26[0] = var17;
                                          MessagingController.15 var27 = new MessagingController.15(var5);
                                          var12.copyMessages(var26, var19, var27);
                                       } catch (UnsupportedOperationException var32) {
                                          ;
                                       }

                                       var19.close((boolean)0);
                                    }

                                    Flag var28 = Flag.FLAGGED;
                                    var17.setFlag(var28, (boolean)0);
                                    Flag var29 = Flag.DELETED;
                                    var17.setFlag(var29, (boolean)1);
                                    Message[] var30 = var12.expunge();
                                    var12.close((boolean)0);
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
   }

   private void processPendingUpdatesSynchronous(EmailContent.Account param1, ContentResolver param2, String[] param3, boolean param4) throws MessagingException {
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
      MessagingController.Command var4;
      try {
         var4 = new MessagingController.Command((MessagingController.1)null);
         var4.listener = var2;
         var4.runnable = var3;
         var4.description = var1;
         if(Email.DEBUG && Email.DEBUG_SENSITIVE) {
            StringBuilder var5 = (new StringBuilder()).append("<<< put cmd : ").append(var4).append(" | desc : ").append(var1).append(" | size : ");
            int var6 = this.mCommands.size();
            String var7 = var5.append(var6).toString();
            Email.loge("MsgControl >>>", var7);
         }

         if(var1.equalsIgnoreCase("synchronizeMailbox")) {
            Iterator var8 = this.mCommands.iterator();

            while(var8.hasNext()) {
               MessagingController.Command var9 = (MessagingController.Command)var8.next();
               if(var9.description.equalsIgnoreCase(var1)) {
                  if(Email.DEBUG && Email.DEBUG_SENSITIVE) {
                     String var10 = "command : " + var9 + " | " + var1;
                     Email.logd("MsgControl >>>", var10);
                  }

                  this.mCommands.remove(var9);
               }
            }
         }
      } catch (IllegalStateException var14) {
         return;
      }

      this.mCommands.add(var4);
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

   private static void sendBroadcastMessageAdd(long var0, Context var2) {
      String[] var3 = new String[1];
      String var4 = Long.toString(var0);
      var3[0] = var4;
      Intent var5 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
      Intent var6 = var5.putExtra("intentType", 400);
      var5.putExtra("id_array", var3);
      Intent var8 = var5.putExtra("action", 1);
      Intent var9 = var5.putExtra("status", 0);
      var2.sendBroadcast(var5);
   }

   private StoreSynchronizer.SyncResults syncSearchOnServer(EmailContent.Account param1, EmailContent.Mailbox param2, String param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private StoreSynchronizer.SyncResults synchronizeMailboxGeneric(EmailContent.Account param1, EmailContent.Mailbox param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void synchronizeMailboxSearchOnServer(EmailContent.Account param1, EmailContent.Mailbox param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   private void synchronizeMailboxSynchronous(EmailContent.Account var1, EmailContent.Mailbox var2) {
      GroupMessagingListener var3 = this.mListeners;
      long var4 = var1.mId;
      long var6 = var2.mId;
      var3.synchronizeMailboxStarted(var4, var6);

      try {
         Email.logd("EMAIL_PERFORMANCE", "synchronizeMailboxGeneric() START");
         this.processPendingActionsSynchronous(var1, (boolean)0, (boolean)1);
         Context var8 = this.mContext;
         String var9 = var1.getStoreUri(var8);
         Context var10 = this.mContext;
         StoreSynchronizer var11 = Store.getInstance(var9, var10, (Store.PersistentDataCallbacks)null).getMessageSynchronizer();
         StoreSynchronizer.SyncResults var12;
         if(var11 == null) {
            var12 = this.synchronizeMailboxGeneric(var1, var2);
         } else {
            GroupMessagingListener var28 = this.mListeners;
            Context var29 = this.mContext;
            var12 = var11.SynchronizeMessagesSynchronous(var1, var2, var28, var29);
         }

         if(var12 != null) {
            GroupMessagingListener var13 = this.mListeners;
            long var14 = var1.mId;
            long var16 = var2.mId;
            int var18 = var12.mTotalMessages;
            int var19 = var12.mNewMessages;
            var13.synchronizeMailboxFinished(var14, var16, var18, var19);
            GroupMessagingListener var20 = this.mListeners;
            long var21 = var1.mId;
            long var23 = var2.mId;
            int var25 = var12.mTotalMessages;
            int var26 = var12.mNewMessages;
            ArrayList var27 = var12.mNewMessageIds;
            var20.synchronizeMailboxFinishedEx(var21, var23, var25, var26, var27);
            Email.logd("EMAIL_PERFORMANCE", "synchronizeMailboxGeneric() END_SUCCESS");
         } else {
            GroupMessagingListener var30 = this.mListeners;
            long var31 = var1.mId;
            long var33 = var2.mId;
            var30.synchronizeMailboxFailed(var31, var33, (Exception)null);
            Email.logd("EMAIL_PERFORMANCE", "synchronizeMailboxGeneric() END_FAIL");
         }
      } catch (MessagingException var41) {
         GroupMessagingListener var36 = this.mListeners;
         long var37 = var1.mId;
         long var39 = var2.mId;
         var36.synchronizeMailboxFailed(var37, var39, var41);
      }

      BadgeManager.updateBadgeProvider(this.mContext);
   }

   public static boolean updateHistoryForEmail(EmailContent.Mailbox param0, String param1, EmailContent.Message param2, EmailContent.Body param3, String param4, Context param5) {
      // $FF: Couldn't be decompiled
   }

   public static void updateHistoryForEmailSend(EmailContent.Message var0, EmailContent.Body var1, String var2, Context var3) {
      ContentValues var4 = new ContentValues();

      try {
         String var5 = Address.toFriendlyUseInBubbleButton(Address.unpack(var0.mFrom));
         StringBuilder var6 = (new StringBuilder()).append("LogProvider Send>>>time : ");
         long var7 = System.currentTimeMillis();
         StringBuilder var9 = var6.append(var7).append(" | number : ");
         String var10 = var0.mTo;
         StringBuilder var11 = var9.append(var10).append(" | account_name : ").append(var2).append(" | name : ").append(var5).append(" | messageid : ");
         long var12 = var0.mId;
         StringBuilder var14 = var11.append(var12).append(" | m_subject : ");
         String var15 = var0.mSubject;
         String var16 = var14.append(var15).toString();
         Email.loge("MsgControl >>>", var16);
         Long var17 = Long.valueOf(System.currentTimeMillis());
         var4.put("date", var17);
         Integer var18 = Integer.valueOf(2);
         var4.put("type", var18);
         var4.put("account_name", var2);
         Long var19 = Long.valueOf(var0.mAccountKey);
         var4.put("contactid", var19);
         Long var20 = Long.valueOf(var0.mAccountKey);
         var4.put("account_id", var20);
         var4.put("name", var5);
         Long var21 = Long.valueOf(var0.mId);
         var4.put("messageid", var21);
         String var22 = var0.mSubject;
         var4.put("m_subject", var22);
         String var23 = var1.mTextContent;
         var2 = var1.mHtmlContent;
         String var36 = "";
         if(var23 != null) {
            var36 = var23;
         } else if(var2 != null) {
            var36 = Html.fromHtml(var2.toLowerCase().split("<body>")[1].toLowerCase().split("</body>")[0]).toString();
         }

         if(var36.length() > 50) {
            String var24 = var36.substring(0, 49);
            var4.put("m_content", var24);
         } else {
            var4.put("m_content", var36);
         }

         String var25 = var0.mTo;
         String var26 = String.valueOf('\u0001');
         String[] var37 = var25.split(var26);
         int var38 = var37.length;

         for(var2 = null; var2 < var38; ++var2) {
            String var27 = var37[var2];
            String var28 = String.valueOf('\u0002');
            String[] var35 = var27.split(var28);
            if(var35.length > 0 && var35[0].contains("@")) {
               String var29 = var35[0];
               var4.put("number", var29);
               ContentResolver var30 = var3.getContentResolver();
               Uri var31 = Uri.parse("content://logs/email");
               var30.insert(var31, var4);
            }
         }
      } catch (Exception var34) {
         ;
      }

      var4.clear();
   }

   public void addListener(MessagingListener var1) {
      this.mListeners.addListener(var1);
   }

   public void checkMail(long var1, long var3, MessagingListener var5) {
      GroupMessagingListener var6 = this.mListeners;
      Context var7 = this.mContext;
      var6.checkMailStarted(var7, var1, var3);
      this.listFolders(var1, (MessagingListener)null);
      MessagingController.19 var17 = new MessagingController.19(var1, var3);
      this.put("checkMail", var5, var17);
   }

   public void createFolder(long var1, long var3, String var5, MessagingListener var6) {
      Context var7 = this.mContext;
      EmailContent.Account var10 = EmailContent.Account.restoreAccountWithId(var7, var1);
      if(var10 != null) {
         Context var11 = this.mContext;
         EmailContent.Mailbox var14 = EmailContent.Mailbox.restoreMailboxWithId(var11, var3);
         String var15 = null;
         String var16 = "";
         int var17 = 0;
         if(var14 != null) {
            var15 = var14.mServerId;
            if(var14.mDelimiter != 0) {
               var16 = Character.toString((char)var14.mDelimiter);
               var17 = var14.mDelimiter;
            } else {
               var16 = "/";
               var17 = 47;
            }
         }

         String var21;
         if(var15 != null) {
            StringBuilder var18 = (new StringBuilder()).append(var15).append(var16);
            var21 = var18.append(var5).toString();
         } else {
            var21 = var5;
         }

         MessagingController.2 var27 = new MessagingController.2(var10, var21, var1, var15, var17);
         String var29 = "createFolder";
         this.put(var29, var6, var27);
      }
   }

   public void deleteFolder(long var1, EmailContent.Mailbox var3, boolean var4, MessagingListener var5) {
      EmailContent.Account var6 = EmailContent.Account.restoreAccountWithId(this.mContext, var1);
      if(var6 != null) {
         MessagingController.3 var11 = new MessagingController.3(var3, var6, var1);
         this.put("deleteFolder", var5, var11);
      }
   }

   public boolean isBusy() {
      return this.mBusy;
   }

   public void listFolders(long var1, MessagingListener var3) {
      EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(this.mContext, var1);
      if(var4 != null) {
         this.mListeners.foldersCommandStarted(var1, 0, (String)null);
         MessagingController.1 var5 = new MessagingController.1(var4, var1);
         this.put("listFolders", var3, var5);
      }
   }

   public void loadAttachment(long param1, long param3, long param5, long param7, MessagingListener param9, boolean param10, boolean param11) {
      // $FF: Couldn't be decompiled
   }

   public boolean loadAttachmentSyncronous(long param1, long param3, long param5, long param7) {
      // $FF: Couldn't be decompiled
   }

   public void loadMessageForView(long var1, MessagingListener var3) {
      this.mListeners.loadMessageForViewStarted(var1);
      MessagingController.16 var4 = new MessagingController.16(var1);
      this.put("loadMessageForViewRemote", var3, var4);
   }

   public void processPendingAccountMoveActions(HashSet<Long> var1, long var2, long var4, long var6, long var8, long var10, int var12, int var13) {
      MessagingController.13 var28 = new MessagingController.13(var1, var2, var4, var6, var8, var10, var12, var13);
      String var30 = "processPendingAccountMoveActions";
      Object var31 = null;
      this.put(var30, (MessagingListener)var31, var28);
   }

   public void processPendingActions(long var1, boolean var3, boolean var4) {
      MessagingController.11 var10 = new MessagingController.11(var1, var3, var4);
      this.put("processPendingActions", (MessagingListener)null, var10);
   }

   public void processPendingDeletesForeverSynchronous(Context param1, EmailContent.Account param2, long param3, ContentResolver param5, long param6, int param8, int param9, long param10, long param12) {
      // $FF: Couldn't be decompiled
   }

   public void processPendingMoveActions(HashSet<String> var1, long var2, long var4, long var6) {
      MessagingController.12 var16 = new MessagingController.12(this, var2, var1, var4, var6);
      this.put("processPendingMoveActions", (MessagingListener)null, var16);
   }

   void pruneCachedAttachments(long param1) {
      // $FF: Couldn't be decompiled
   }

   public void removeListener(MessagingListener var1) {
      this.mListeners.removeListener(var1);
   }

   public void renameFolder(long param1, String param3, long param4, MessagingListener param6) {
      // $FF: Couldn't be decompiled
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

   public void searchOnServerSync(EmailContent.Account var1, EmailContent.Mailbox var2, String var3, MessagingListener var4) {
      if(var2.mType != 4) {
         GroupMessagingListener var5 = this.mListeners;
         long var6 = var1.mId;
         long var8 = var2.mId;
         var5.synchronizeMailboxStarted(var6, var8);
         MessagingController.5 var10 = new MessagingController.5(var1, var2, var3);
         this.put("searchOnServerSync", var4, var10);
      }
   }

   public void sendPendingMessages(EmailContent.Account var1, long var2, MessagingListener var4) {
      MessagingController.18 var5 = new MessagingController.18(var1, var2);
      this.put("sendPendingMessages", var4, var5);
   }

   public void sendPendingMessagesSynchronous(EmailContent.Account param1, long param2) {
      // $FF: Couldn't be decompiled
   }

   public void setMessageReply(long var1, boolean var3) {
      ContentValues var4 = new ContentValues();
      Boolean var5 = Boolean.valueOf(var3);
      var4.put("flagReply", var5);
      Uri var6 = ContentUris.withAppendedId(EmailContent.Message.SYNCED_CONTENT_URI, var1);
      this.mContext.getContentResolver().update(var6, var4, (String)null, (String[])null);
      EmailContent.Message var8 = EmailContent.Message.restoreMessageWithId(this.mContext, var1);
      if(var8 != null) {
         if(var8.mTypeMsg == 0) {
            long var9 = var8.mAccountKey;
            this.processPendingActions(var9, (boolean)0, (boolean)0);
         }
      }
   }

   public void setRemoteSync(boolean var1) {
      this.mRemoteSync = var1;
   }

   public void synchronizeMailbox(EmailContent.Account var1, EmailContent.Mailbox var2, MessagingListener var3) {
      if(var2.mType != 4) {
         GroupMessagingListener var4 = this.mListeners;
         long var5 = var1.mId;
         long var7 = var2.mId;
         var4.synchronizeMailboxStarted(var5, var7);
         MessagingController.6 var9 = new MessagingController.6(var1, var2);
         this.put("synchronizeMailbox", var3, var9);
      }
   }

   class 9 implements Folder.MessageRetrievalListener {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;
      // $FF: synthetic field
      final HashMap val$localMapCopy;
      // $FF: synthetic field
      final HashMap val$newMessageMap;
      // $FF: synthetic field
      final ArrayList val$newMessages;


      9(HashMap var2, EmailContent.Account var3, EmailContent.Mailbox var4, HashMap var5, ArrayList var6) {
         this.val$localMapCopy = var2;
         this.val$account = var3;
         this.val$folder = var4;
         this.val$newMessageMap = var5;
         this.val$newMessages = var6;
      }

      public void messageFinished(Message param1, int param2, int param3) {
         // $FF: Couldn't be decompiled
      }

      public void messageStarted(String var1, int var2, int var3) {}
   }

   class 5 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;
      // $FF: synthetic field
      final String val$searchText;


      5(EmailContent.Account var2, EmailContent.Mailbox var3, String var4) {
         this.val$account = var2;
         this.val$folder = var3;
         this.val$searchText = var4;
      }

      public void run() {
         MessagingController var1 = MessagingController.this;
         EmailContent.Account var2 = this.val$account;
         EmailContent.Mailbox var3 = this.val$folder;
         String var4 = this.val$searchText;
         var1.synchronizeMailboxSearchOnServer(var2, var3, var4);
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

   class 6 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;


      6(EmailContent.Account var2, EmailContent.Mailbox var3) {
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

   class 7 implements Folder.MessageRetrievalListener {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;
      // $FF: synthetic field
      final HashMap val$localMapCopy;
      // $FF: synthetic field
      final ArrayList val$newMessages;


      7(HashMap var2, EmailContent.Account var3, EmailContent.Mailbox var4, ArrayList var5) {
         this.val$localMapCopy = var2;
         this.val$account = var3;
         this.val$folder = var4;
         this.val$newMessages = var5;
      }

      public void messageFinished(Message param1, int param2, int param3) {
         // $FF: Couldn't be decompiled
      }

      public void messageStarted(String var1, int var2, int var3) {}
   }

   class 8 implements Folder.MessageRetrievalListener {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;


      8(EmailContent.Account var2, EmailContent.Mailbox var3) {
         this.val$account = var2;
         this.val$folder = var3;
      }

      public void messageFinished(Message var1, int var2, int var3) {
         MessagingController var4 = MessagingController.this;
         EmailContent.Account var5 = this.val$account;
         EmailContent.Mailbox var6 = this.val$folder;
         byte var8 = 1;
         var4.copyOneMessageToProvider(var1, var5, var6, 1, (boolean)var8);
      }

      public void messageStarted(String var1, int var2, int var3) {}
   }

   private static class LocalMessageInfo {

      private static final int COLUMN_FLAG_FAVORITE = 2;
      private static final int COLUMN_FLAG_LOADED = 3;
      private static final int COLUMN_FLAG_READ = 1;
      private static final int COLUMN_FLAG_REPLY = 7;
      private static final int COLUMN_ID = 0;
      private static final int COLUMN_SERVER_ID = 4;
      private static final String[] PROJECTION;
      int mCursorIndex;
      boolean mFlagFavorite;
      int mFlagLoaded;
      boolean mFlagRead;
      boolean mFlagReply;
      long mId;
      String mServerId;


      static {
         String[] var0 = new String[]{"_id", "flagRead", "flagFavorite", "flagLoaded", "syncServerId", "mailboxKey", "accountKey", "flagReply"};
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
      static String[] access$600() {
         return PROJECTION;
      }
   }

   private static class LocalMailboxInfo {

      private static final int COLUMN_ACCOUNT_KEY = 2;
      private static final int COLUMN_DISPLAY_NAME = 1;
      private static final int COLUMN_ID = 0;
      private static final int COLUMN_PARENT_SERVER_ID = 5;
      private static final int COLUMN_SERVER_ID = 4;
      private static final int COLUMN_TYPE = 3;
      private static final String[] PROJECTION;
      long mAccountKey;
      String mDisplayName;
      long mId;
      String mParentServerId;
      String mServerId;
      int mType;


      static {
         String[] var0 = new String[]{"_id", "displayName", "accountKey", "type", "serverId", "parentServerId"};
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
         String var8 = var1.getString(4);
         this.mServerId = var8;
         String var9 = var1.getString(5);
         this.mParentServerId = var9;
      }
   }

   class 11 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final boolean val$isNeedUploadSyncFlag;
      // $FF: synthetic field
      final boolean val$movemaii;


      11(long var2, boolean var4, boolean var5) {
         this.val$accountId = var2;
         this.val$movemaii = var4;
         this.val$isNeedUploadSyncFlag = var5;
      }

      public void run() {
         try {
            Context var1 = MessagingController.this.mContext;
            long var2 = this.val$accountId;
            EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(var1, var2);
            if(var4 == null) {
               return;
            }

            MessagingController var5 = MessagingController.this;
            boolean var6 = this.val$movemaii;
            boolean var7 = this.val$isNeedUploadSyncFlag;
            var5.processPendingActionsSynchronous(var4, var6, var7);
         } catch (MessagingException var9) {
            ;
         }

         BadgeManager.updateBadgeProvider(MessagingController.this.mContext);
      }
   }

   class 10 implements Folder.MessageRetrievalListener {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final EmailContent.Mailbox val$folder;


      10(EmailContent.Account var2, EmailContent.Mailbox var3) {
         this.val$account = var2;
         this.val$folder = var3;
      }

      public void messageFinished(Message var1, int var2, int var3) {
         MessagingController var4 = MessagingController.this;
         EmailContent.Account var5 = this.val$account;
         EmailContent.Mailbox var6 = this.val$folder;
         byte var8 = 1;
         var4.copyOneMessageToProvider(var1, var5, var6, 1, (boolean)var8);
      }

      public void messageStarted(String var1, int var2, int var3) {}
   }

   class 13 implements Runnable {

      // $FF: synthetic field
      final HashSet val$messageId;
      // $FF: synthetic field
      final long val$orig_boxkey;
      // $FF: synthetic field
      final long val$source_accountId;
      // $FF: synthetic field
      final int val$source_server_type;
      // $FF: synthetic field
      final long val$source_trashMailboxId;
      // $FF: synthetic field
      final long val$target_accountId;
      // $FF: synthetic field
      final long val$target_boxkey;
      // $FF: synthetic field
      final int val$target_server_type;


      13(HashSet var2, long var3, long var5, long var7, long var9, long var11, int var13, int var14) {
         this.val$messageId = var2;
         this.val$source_accountId = var3;
         this.val$orig_boxkey = var5;
         this.val$target_accountId = var7;
         this.val$target_boxkey = var9;
         this.val$source_trashMailboxId = var11;
         this.val$target_server_type = var13;
         this.val$source_server_type = var14;
      }

      public void run() {
         Iterator var1 = this.val$messageId.iterator();
         byte var2 = 1;

         while(var1.hasNext()) {
            Long var3 = (Long)var1.next();
            Context var4 = MessagingController.this.mContext;
            long var5 = var3.longValue();
            EmailContent.Message var7 = EmailContent.Message.restoreMessageWithId(var4, var5);
            if(var7 == null) {
               GroupMessagingListener var8 = MessagingController.this.mListeners;
               long var9 = var3.longValue();
               long var11 = this.val$source_accountId;
               long var13 = this.val$orig_boxkey;
               long var15 = this.val$target_accountId;
               long var17 = this.val$target_boxkey;
               long var19 = this.val$source_trashMailboxId;
               int var21 = this.val$target_server_type;
               int var22 = this.val$source_server_type;
               var8.movemessageToOtherAccountCallback((boolean)0, var9, var11, var13, var15, var17, var19, var21, var22);
            } else {
               if(var7.mFlagLoaded != 1) {
                  MessagingController var23 = MessagingController.this;
                  long var24 = var3.longValue();
                  var2 = var23.loadMessageForViewSyncronous(var24);
                  if(var2 == 0) {
                     GroupMessagingListener var26 = MessagingController.this.mListeners;
                     long var27 = var3.longValue();
                     long var29 = this.val$source_accountId;
                     long var31 = this.val$orig_boxkey;
                     long var33 = this.val$target_accountId;
                     long var35 = this.val$target_boxkey;
                     long var37 = this.val$source_trashMailboxId;
                     int var39 = this.val$target_server_type;
                     int var40 = this.val$source_server_type;
                     var26.movemessageToOtherAccountCallback((boolean)0, var27, var29, var31, var33, var35, var37, var39, var40);
                     continue;
                  }
               }

               Context var41 = MessagingController.this.mContext;
               long var42 = this.val$source_accountId;
               long var44 = var3.longValue();
               if(!AttachmentProvider.IsAllAttachmentFilesExist(var41, var42, var44)) {
                  new HashSet();
                  Context var47 = MessagingController.this.mContext;
                  long var48 = var3.longValue();
                  HashSet var50 = AttachmentProvider.getAllAttachmentId(var47, var48);
                  if(!var50.isEmpty()) {
                     Iterator var51 = var50.iterator();

                     while(var51.hasNext()) {
                        Long var52 = (Long)var51.next();
                        MessagingController var53 = MessagingController.this;
                        long var54 = this.val$source_accountId;
                        long var56 = var3.longValue();
                        long var58 = this.val$orig_boxkey;
                        long var60 = var52.longValue();
                        var53.loadAttachmentSyncronous(var54, var56, var58, var60);
                     }
                  }
               }

               Context var63 = MessagingController.this.mContext;
               long var64 = this.val$source_accountId;
               long var66 = var3.longValue();
               if(!AttachmentProvider.IsAllAttachmentFilesExist(var63, var64, var66)) {
                  GroupMessagingListener var68 = MessagingController.this.mListeners;
                  long var69 = var3.longValue();
                  long var71 = this.val$source_accountId;
                  long var73 = this.val$orig_boxkey;
                  long var75 = this.val$target_accountId;
                  long var77 = this.val$target_boxkey;
                  long var79 = this.val$source_trashMailboxId;
                  int var81 = this.val$target_server_type;
                  int var82 = this.val$source_server_type;
                  var68.movemessageToOtherAccountCallback((boolean)0, var69, var71, var73, var75, var77, var79, var81, var82);
               } else {
                  byte var103;
                  label68: {
                     byte var126;
                     try {
                        Context var83 = MessagingController.this.mContext;
                        long var84 = this.val$target_accountId;
                        EmailContent.Account var86 = EmailContent.Account.restoreAccountWithId(var83, var84);
                        if(var86 == null) {
                           GroupMessagingListener var87 = MessagingController.this.mListeners;
                           long var88 = var3.longValue();
                           long var90 = this.val$source_accountId;
                           long var92 = this.val$orig_boxkey;
                           long var94 = this.val$target_accountId;
                           long var96 = this.val$target_boxkey;
                           long var98 = this.val$source_trashMailboxId;
                           int var100 = this.val$target_server_type;
                           int var101 = this.val$source_server_type;
                           var87.movemessageToOtherAccountCallback((boolean)0, var88, var90, var92, var94, var96, var98, var100, var101);
                           continue;
                        }

                        MessagingController var119 = MessagingController.this;
                        long var120 = var3.longValue();
                        long var122 = this.val$target_accountId;
                        long var124 = this.val$target_boxkey;
                        var126 = MessagingController.access$1300(var119, var120, var122, var86, var124);
                     } catch (MessagingException var144) {
                        var103 = var2;
                        break label68;
                     }

                     var103 = var126;

                     try {
                        GroupMessagingListener var127 = MessagingController.this.mListeners;
                        long var128 = var3.longValue();
                        long var130 = this.val$source_accountId;
                        long var132 = this.val$orig_boxkey;
                        long var134 = this.val$target_accountId;
                        long var136 = this.val$target_boxkey;
                        long var138 = this.val$source_trashMailboxId;
                        int var140 = this.val$target_server_type;
                        int var141 = this.val$source_server_type;
                        var127.movemessageToOtherAccountCallback((boolean)var103, var128, var130, var132, var134, var136, var138, var140, var141);
                     } catch (MessagingException var143) {
                        break label68;
                     }

                     BadgeManager.updateBadgeProvider(MessagingController.this.mContext);
                     var2 = var126;
                     continue;
                  }

                  GroupMessagingListener var104 = MessagingController.this.mListeners;
                  long var105 = var3.longValue();
                  long var107 = this.val$source_accountId;
                  long var109 = this.val$orig_boxkey;
                  long var111 = this.val$target_accountId;
                  long var113 = this.val$target_boxkey;
                  long var115 = this.val$source_trashMailboxId;
                  int var117 = this.val$target_server_type;
                  int var118 = this.val$source_server_type;
                  var104.movemessageToOtherAccountCallback((boolean)0, var105, var107, var109, var111, var113, var115, var117, var118);
                  var2 = var103;
               }
            }
         }

      }
   }

   class 12 implements Runnable {

      // $FF: synthetic field
      final MessagingController this$0;
      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final HashSet val$final_serverIds;
      // $FF: synthetic field
      final long val$orig_boxkey;
      // $FF: synthetic field
      final long val$target_boxkey;


      12(MessagingController param1, long param2, HashSet param4, long param5, long param7) {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         Context var1 = this.this$0.mContext;
         long var2 = this.val$accountId;
         EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(var1, var2);
         if(var4 != null) {
            ContentResolver var5 = this.this$0.mContext.getContentResolver();
            Iterator var6 = this.val$final_serverIds.iterator();

            while(var6.hasNext()) {
               String var7 = (String)var6.next();
               MessagingController var8 = this.this$0;
               long var9 = this.val$target_boxkey;
               long var11 = this.val$orig_boxkey;
               MessagingController.access$1100(var8, var4, var5, var7, var9, var11);
            }

            BadgeManager.updateBadgeProvider(this.this$0.mContext);
         }
      }
   }

   class 15 implements Folder.MessageUpdateCallbacks {

      // $FF: synthetic field
      final EmailContent.Message val$newMessage;


      15(EmailContent.Message var2) {
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

   class 2 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final String val$newMailboxName;
      // $FF: synthetic field
      final int val$tempDelimiter;
      // $FF: synthetic field
      final String val$tempFolderPath;


      2(EmailContent.Account var2, String var3, long var4, String var6, int var7) {
         this.val$account = var2;
         this.val$newMailboxName = var3;
         this.val$accountId = var4;
         this.val$tempFolderPath = var6;
         this.val$tempDelimiter = var7;
      }

      public void run() {
         try {
            EmailContent.Account var1 = this.val$account;
            Context var2 = MessagingController.this.mContext;
            String var3 = var1.getStoreUri(var2);
            Context var4 = MessagingController.this.mContext;
            Store var5 = Store.getInstance(var3, var4, (Store.PersistentDataCallbacks)null);
            String var6 = this.val$newMailboxName;
            Folder var7 = var5.getFolder(var6);
            if(var7 == null) {
               GroupMessagingListener var8 = MessagingController.this.mListeners;
               long var9 = this.val$accountId;
               String var11 = this.val$newMailboxName;
               MessagingException var12 = new MessagingException(31);
               var8.foldersCommandFinished(var9, 1, var11, var12);
            } else if(!var7.exists()) {
               Folder.FolderType var13 = Folder.FolderType.HOLDS_MESSAGES;
               if(!var7.canCreate(var13)) {
                  GroupMessagingListener var14 = MessagingController.this.mListeners;
                  long var15 = this.val$accountId;
                  String var17 = this.val$newMailboxName;
                  MessagingException var18 = new MessagingException(31);
                  var14.foldersCommandFinished(var15, 1, var17, var18);
               } else {
                  Folder.FolderType var26 = Folder.FolderType.HOLDS_MESSAGES;
                  if(!var7.create(var26)) {
                     GroupMessagingListener var27 = MessagingController.this.mListeners;
                     long var28 = this.val$accountId;
                     String var30 = this.val$newMailboxName;
                     MessagingException var31 = new MessagingException(31);
                     var27.foldersCommandFinished(var28, 1, var30, var31);
                  } else {
                     EmailContent.Mailbox var32 = new EmailContent.Mailbox();
                     String var33 = this.val$newMailboxName;
                     var32.mDisplayName = var33;
                     String var34 = this.val$newMailboxName;
                     var32.mServerId = var34;
                     String var35 = this.val$tempFolderPath;
                     var32.mParentServerId = var35;
                     long var36 = this.val$accountId;
                     var32.mAccountKey = var36;
                     Context var38 = MessagingController.this.mContext;
                     String var39 = this.val$newMailboxName;
                     int var40 = LegacyConversions.inferMailboxTypeFromName(var38, var39);
                     var32.mType = var40;
                     var32.mFlagVisible = (boolean)1;
                     var32.mVisibleLimit = 25;
                     int var41 = this.val$tempDelimiter;
                     var32.mDelimiter = var41;
                     Context var42 = MessagingController.this.mContext;
                     var32.save(var42);
                     GroupMessagingListener var44 = MessagingController.this.mListeners;
                     long var45 = this.val$accountId;
                     String var47 = this.val$newMailboxName;
                     var44.foldersCommandFinished(var45, 1, var47, (MessagingException)null);
                  }
               }
            } else {
               GroupMessagingListener var48 = MessagingController.this.mListeners;
               long var49 = this.val$accountId;
               String var51 = this.val$newMailboxName;
               MessagingException var52 = new MessagingException(35);
               var48.foldersCommandFinished(var49, 1, var51, var52);
            }
         } catch (Exception var53) {
            GroupMessagingListener var20 = MessagingController.this.mListeners;
            long var21 = this.val$accountId;
            String var23 = this.val$newMailboxName;
            MessagingException var24 = new MessagingException(31);
            byte var25 = 1;
            var20.foldersCommandFinished(var21, var25, var23, var24);
         }
      }
   }

   class 14 implements Folder.MessageUpdateCallbacks {

      14() {}

      public void onMessageNotFound(Message var1) {}

      public void onMessageUidChange(Message var1, String var2) {}
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
         boolean var135 = false;

         label233: {
            try {
               var135 = true;
               EmailContent.Account var2 = this.val$account;
               Context var3 = MessagingController.this.mContext;
               String var4 = var2.getStoreUri(var3);
               Context var5 = MessagingController.this.mContext;
               Folder[] var6 = Store.getInstance(var4, var5, (Store.PersistentDataCallbacks)null).getPersonalNamespaces();
               HashMap var7 = new HashMap();
               if(var6 != null) {
                  int var8 = 0;

                  for(int var9 = var6.length; var8 < var9; ++var8) {
                     String var10 = var6[var8].getFolderPath();
                     Folder var11 = var6[var8];
                     Object var15 = var7.put(var10, var11);
                  }
               }

               HashMap var16 = new HashMap();
               HashSet var17 = new HashSet();
               ContentResolver var18 = MessagingController.this.mContext.getContentResolver();
               Uri var19 = EmailContent.Mailbox.CONTENT_URI;
               String[] var20 = MessagingController.LocalMailboxInfo.PROJECTION;
               String[] var21 = new String[1];
               String var22 = String.valueOf(this.val$account.mId);
               var21[0] = var22;

               String var29;
               boolean var32;
               for(var1 = var18.query(var19, var20, "accountKey=?", var21, (String)null); var1.moveToNext(); var32 = var17.add(var29)) {
                  MessagingController.LocalMailboxInfo var23 = new MessagingController.LocalMailboxInfo(var1);
                  String var24 = var23.mDisplayName;
                  Object var28 = var16.put(var24, var23);
                  var29 = var23.mDisplayName;
               }

               if(!var7.equals(var17)) {
                  HashSet var40 = new HashSet(var17);
                  Set var43 = var7.keySet();
                  boolean var46 = var40.removeAll(var43);
                  Iterator var47 = var40.iterator();

                  MessagingController.LocalMailboxInfo var51;
                  while(var47.hasNext()) {
                     String var48 = (String)var47.next();
                     var51 = (MessagingController.LocalMailboxInfo)var16.get(var48);
                     switch(var51.mType) {
                     case 0:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                        break;
                     case 1:
                     case 2:
                     default:
                        Context var52 = MessagingController.this.mContext;
                        long var53 = this.val$accountId;
                        long var55 = var51.mId;
                        AttachmentProvider.deleteAllMailboxAttachmentFiles(var52, var53, var55);
                        Uri var57 = EmailContent.Mailbox.CONTENT_URI;
                        long var58 = var51.mId;
                        Uri var60 = ContentUris.withAppendedId(var57, var58);
                        ContentResolver var61 = MessagingController.this.mContext.getContentResolver();
                        Object var63 = null;
                        Object var64 = null;
                        var61.delete(var60, (String)var63, (String[])var64);
                     }
                  }

                  var47 = var17.iterator();

                  while(var47.hasNext()) {
                     String var67 = (String)var47.next();
                     if(var7.containsKey(var67)) {
                        var51 = (MessagingController.LocalMailboxInfo)var16.get(var67);
                        switch(var51.mType) {
                        case 1:
                        case 2:
                        default:
                           Folder var74 = (Folder)var7.get(var67);
                           if(var74 != null) {
                              Uri var75 = EmailContent.Mailbox.CONTENT_URI;
                              long var76 = var51.mId;
                              Uri var78 = ContentUris.withAppendedId(var75, var76);
                              ContentValues var79 = new ContentValues();
                              Integer var80 = Integer.valueOf(var74.getDelimiter());
                              String var82 = "delimiter";
                              var79.put(var82, var80);
                              String var84 = "flagNoSelect";
                              byte var85;
                              if(!var74.getSelect()) {
                                 var85 = 1;
                              } else {
                                 var85 = 0;
                              }

                              Boolean var86 = Boolean.valueOf((boolean)var85);
                              var79.put(var84, var86);
                              String var90 = var74.getParentFolderName();
                              String var92 = "parentServerId";
                              var79.put(var92, var90);
                              String var94 = var74.getFolderPath();
                              String var96 = "serverId";
                              var79.put(var96, var94);
                              ContentResolver var98 = MessagingController.this.mContext.getContentResolver();
                              Object var101 = null;
                              Object var102 = null;
                              var98.update(var78, var79, (String)var101, (String[])var102);
                           }
                        case 0:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                           Object var106 = var7.remove(var67);
                        }
                     }
                  }

                  var47 = var7.values().iterator();

                  while(var47.hasNext()) {
                     Folder var107 = (Folder)var47.next();
                     EmailContent.Mailbox var108 = new EmailContent.Mailbox();
                     String var109 = var107.getName();
                     var108.mDisplayName = var109;
                     String var110 = var107.getFolderPath();
                     var108.mServerId = var110;
                     String var111 = var107.getParentFolderName();
                     var108.mParentServerId = var111;
                     long var112 = this.val$account.mId;
                     var108.mAccountKey = var112;
                     Context var114 = MessagingController.this.mContext;
                     String var115 = var107.getName();
                     int var116 = LegacyConversions.inferMailboxTypeFromName(var114, var115);
                     var108.mType = var116;
                     int var117 = var107.getDelimiter();
                     var108.mDelimiter = var117;
                     var108.mFlagVisible = (boolean)1;
                     byte var138;
                     if(!var107.getSelect()) {
                        var138 = 1;
                     } else {
                        var138 = 0;
                     }

                     var108.mFlagNoSelect = (boolean)var138;
                     var108.mVisibleLimit = 25;
                     Context var118 = MessagingController.this.mContext;
                     var108.save(var118);
                  }
               }

               Context var120 = MessagingController.this.mContext;
               long var121 = this.val$accountId;
               if(EmailContent.Mailbox.findMailboxOfType(var120, var121, 6) == 65535L) {
                  EmailContent.Mailbox var123 = new EmailContent.Mailbox();
                  long var124 = this.val$account.mId;
                  var123.mAccountKey = var124;
                  var123.mType = 6;
                  var123.mSyncInterval = -1;
                  var123.mFlagVisible = (boolean)1;
                  String var126 = MessagingController.this.mContext.getString(2131166184);
                  var123.mDisplayName = var126;
                  String var127 = var123.mDisplayName;
                  var123.mServerId = var127;
                  Context var128 = MessagingController.this.mContext;
                  var123.save(var128);
               }

               GroupMessagingListener var130 = MessagingController.this.mListeners;
               long var131 = this.val$accountId;
               var130.foldersCommandFinished(var131, 0, (String)null, (MessagingException)null);
               var135 = false;
               break label233;
            } catch (Exception var136) {
               GroupMessagingListener var34 = MessagingController.this.mListeners;
               long var35 = this.val$accountId;
               MessagingException var37 = new MessagingException(30);
               var34.foldersCommandFinished(var35, 0, (String)null, var37);
               var135 = false;
            } finally {
               if(var135) {
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

   class 17 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$attachmentId;
      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final long val$messageId;
      // $FF: synthetic field
      final boolean val$prune;
      // $FF: synthetic field
      final boolean val$reconnect;


      17(boolean var2, long var3, long var5, long var7, long var9, boolean var11) {
         this.val$prune = var2;
         this.val$accountId = var3;
         this.val$mailboxId = var5;
         this.val$messageId = var7;
         this.val$attachmentId = var9;
         this.val$reconnect = var11;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }

      class 1 implements Folder.MessageRetrievalListener {

         1() {}

         public void messageFinished(Message var1, int var2, int var3) {}

         public void messageStarted(String param1, int param2, int param3) {
            // $FF: Couldn't be decompiled
         }
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final MessagingController this$0;
      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$mailboxId;
      // $FF: synthetic field
      final String val$newMailboxName;


      4(MessagingController param1, long param2, String param4, EmailContent.Account param5, long param6) {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 16 implements Runnable {

      // $FF: synthetic field
      final long val$messageId;


      16(long var2) {
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
                  if(!MessagingController.this.checkITPolicy_AllowPOPIMAP(var19)) {
                     GroupMessagingListener var32 = MessagingController.this.mListeners;
                     long var33 = this.val$messageId;
                     var32.loadMessageForViewFailed(var33, "MessagingException.SECURITY_POLICIES_REQUIRED");
                  } else {
                     Context var35 = MessagingController.this.mContext;
                     String var36 = var19.getStoreUri(var35);
                     Context var37 = MessagingController.this.mContext;
                     Store var38 = Store.getInstance(var36, var37, (Store.PersistentDataCallbacks)null);
                     String var39 = var23.mDisplayName;
                     Folder var40 = var38.getFolder(var39);
                     Folder.OpenMode var41 = Folder.OpenMode.READ_WRITE;
                     var40.open(var41, (Folder.PersistentDataCallbacks)null);
                     String var42 = var4.mServerId;
                     Message var43 = var40.getMessage(var42);
                     if(var43 == null) {
                        var40.close((boolean)0);
                        Folder.OpenMode var44 = Folder.OpenMode.READ_WRITE;
                        var40.open(var44, (Folder.PersistentDataCallbacks)null);
                        String var45 = var4.mServerId;
                        var43 = var40.getMessage(var45);
                     }

                     FetchProfile var46 = new FetchProfile();
                     FetchProfile.Item var47 = FetchProfile.Item.BODY;
                     var46.add(var47);
                     if(var43 != null) {
                        Message[] var49 = new Message[]{var43};
                        var40.fetch(var49, var46, (Folder.MessageRetrievalListener)null);
                     }

                     if(!MessagingController.this.checkITPolicy_AllowPOPIMAP(var19)) {
                        GroupMessagingListener var50 = MessagingController.this.mListeners;
                        long var51 = this.val$messageId;
                        var50.loadMessageForViewFailed(var51, "MessagingException.SECURITY_POLICIES_REQUIRED");
                     } else {
                        if(var43 != null) {
                           MessagingController.this.copyOneMessageToProvider(var43, var19, var23, 1, (boolean)0);
                        }

                        if(!MessagingController.this.checkITPolicy_AllowPOPIMAP(var19)) {
                           GroupMessagingListener var53 = MessagingController.this.mListeners;
                           long var54 = this.val$messageId;
                           var53.loadMessageForViewFailed(var54, "MessagingException.SECURITY_POLICIES_REQUIRED");
                        } else {
                           GroupMessagingListener var56 = MessagingController.this.mListeners;
                           long var57 = this.val$messageId;
                           var56.loadMessageForViewFinished(var57);
                        }
                     }
                  }
               } else {
                  GroupMessagingListener var24 = MessagingController.this.mListeners;
                  long var25 = this.val$messageId;
                  var24.loadMessageForViewFailed(var25, "null account or mailbox");
               }
            }
         } catch (MessagingException var59) {
            GroupMessagingListener var12 = MessagingController.this.mListeners;
            long var13 = this.val$messageId;
            String var15 = var59.getMessage();
            var12.loadMessageForViewFailed(var13, var15);
         } catch (RuntimeException var60) {
            GroupMessagingListener var28 = MessagingController.this.mListeners;
            long var29 = this.val$messageId;
            String var31 = var60.getMessage();
            var28.loadMessageForViewFailed(var29, var31);
         }
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final EmailContent.Mailbox val$deleteMailbox;


      3(EmailContent.Mailbox var2, EmailContent.Account var3, long var4) {
         this.val$deleteMailbox = var2;
         this.val$account = var3;
         this.val$accountId = var4;
      }

      public void run() {
         Cursor var1 = null;
         boolean var2 = false;
         boolean var170 = false;

         label312: {
            label313: {
               label314: {
                  label315: {
                     try {
                        var170 = true;
                        String var3 = this.val$deleteMailbox.mServerId;
                        EmailContent.Account var4 = this.val$account;
                        Context var5 = MessagingController.this.mContext;
                        String var6 = var4.getStoreUri(var5);
                        Context var7 = MessagingController.this.mContext;
                        Store var8 = Store.getInstance(var6, var7, (Store.PersistentDataCallbacks)null);
                        Folder var11 = var8.getFolder(var3);
                        HashMap var12 = new HashMap();
                        HashSet var13 = new HashSet();
                        if(var11 != null) {
                           if(!var11.exists()) {
                              GroupMessagingListener var164 = MessagingController.this.mListeners;
                              long var165 = this.val$accountId;
                              MessagingException var167 = new MessagingException(36);
                              var164.foldersCommandFinished(var165, 2, var3, var167);
                              var170 = false;
                              break label312;
                           }

                           byte var19 = 1;
                           if(var11.delete((boolean)var19)) {
                              var8.removeFolder(var3);
                              new ContentValues();
                              Uri var23 = EmailContent.Mailbox.CONTENT_URI;
                              long var24 = this.val$deleteMailbox.mId;
                              Uri var26 = ContentUris.withAppendedId(var23, var24);
                              ContentResolver var27 = MessagingController.this.mContext.getContentResolver();
                              Object var29 = null;
                              Object var30 = null;
                              var27.delete(var26, (String)var29, (String[])var30);
                              ContentResolver var32 = MessagingController.this.mContext.getContentResolver();
                              Uri var33 = EmailContent.Mailbox.CONTENT_URI;
                              String[] var34 = MessagingController.LocalMailboxInfo.PROJECTION;
                              String[] var35 = new String[1];
                              String var36 = String.valueOf(this.val$account.mId);
                              var35[0] = var36;
                              var1 = var32.query(var33, var34, "accountKey=?", var35, (String)null);
                              Folder[] var37 = var8.getPersonalNamespaces();
                              HashMap var38 = new HashMap();
                              if(var37 != null) {
                                 int var39 = 0;

                                 for(int var40 = var37.length; var39 < var40; ++var39) {
                                    String var43 = var37[var39].getFolderPath();
                                    Folder var44 = var37[var39];
                                    Object var48 = var38.put(var43, var44);
                                 }
                              }

                              if(var38.containsKey(var3)) {
                                 var2 = true;
                              }

                              while(var1.moveToNext()) {
                                 MessagingController.LocalMailboxInfo var51 = new MessagingController.LocalMailboxInfo(var1);
                                 String var52 = var51.mDisplayName;
                                 Object var56 = var12.put(var52, var51);
                                 String var57 = var51.mDisplayName;
                                 boolean var60 = var13.add(var57);
                              }

                              if(!var38.equals(var13)) {
                                 HashSet var69 = new HashSet(var13);
                                 Set var72 = var38.keySet();
                                 boolean var75 = var69.removeAll(var72);
                                 Iterator var76 = var69.iterator();

                                 MessagingController.LocalMailboxInfo var80;
                                 while(var76.hasNext()) {
                                    String var77 = (String)var76.next();
                                    var80 = (MessagingController.LocalMailboxInfo)var12.get(var77);
                                    switch(var80.mType) {
                                    case 0:
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                       break;
                                    case 1:
                                    case 2:
                                    default:
                                       Context var81 = MessagingController.this.mContext;
                                       long var82 = this.val$accountId;
                                       long var84 = var80.mId;
                                       AttachmentProvider.deleteAllMailboxAttachmentFiles(var81, var82, var84);
                                       Uri var86 = EmailContent.Mailbox.CONTENT_URI;
                                       long var87 = var80.mId;
                                       Uri var89 = ContentUris.withAppendedId(var86, var87);
                                       ContentResolver var90 = MessagingController.this.mContext.getContentResolver();
                                       Object var92 = null;
                                       Object var93 = null;
                                       var90.delete(var89, (String)var92, (String[])var93);
                                    }
                                 }

                                 var76 = var13.iterator();

                                 while(var76.hasNext()) {
                                    String var96 = (String)var76.next();
                                    if(var38.containsKey(var96)) {
                                       var80 = (MessagingController.LocalMailboxInfo)var12.get(var96);
                                       switch(var80.mType) {
                                       case 1:
                                       case 2:
                                       default:
                                          Folder var103 = (Folder)var38.get(var96);
                                          if(var103 != null) {
                                             Uri var104 = EmailContent.Mailbox.CONTENT_URI;
                                             long var105 = var80.mId;
                                             Uri var107 = ContentUris.withAppendedId(var104, var105);
                                             ContentValues var108 = new ContentValues();
                                             Integer var109 = Integer.valueOf(var103.getDelimiter());
                                             String var111 = "delimiter";
                                             var108.put(var111, var109);
                                             String var113 = "flagNoSelect";
                                             byte var114;
                                             if(!var103.getSelect()) {
                                                var114 = 1;
                                             } else {
                                                var114 = 0;
                                             }

                                             Boolean var115 = Boolean.valueOf((boolean)var114);
                                             var108.put(var113, var115);
                                             String var119 = var103.getParentFolderName();
                                             String var121 = "parentServerId";
                                             var108.put(var121, var119);
                                             String var123 = var103.getFolderPath();
                                             String var125 = "serverId";
                                             var108.put(var125, var123);
                                             ContentResolver var127 = MessagingController.this.mContext.getContentResolver();
                                             Object var130 = null;
                                             Object var131 = null;
                                             var127.update(var107, var108, (String)var130, (String[])var131);
                                          }
                                       case 0:
                                       case 3:
                                       case 4:
                                       case 5:
                                       case 6:
                                          Object var135 = var38.remove(var96);
                                       }
                                    }
                                 }

                                 EmailContent.Mailbox var137;
                                 Uri var153;
                                 Context var150;
                                 for(var76 = var38.values().iterator(); var76.hasNext(); var153 = var137.save(var150)) {
                                    Folder var136 = (Folder)var76.next();
                                    var137 = new EmailContent.Mailbox();
                                    String var138 = var136.getName();
                                    var137.mDisplayName = var138;
                                    String var139 = var136.getFolderPath();
                                    var137.mServerId = var139;
                                    String var140 = var136.getParentFolderName();
                                    var137.mParentServerId = var140;
                                    long var141 = this.val$account.mId;
                                    var137.mAccountKey = var141;
                                    Context var143 = MessagingController.this.mContext;
                                    String var144 = var136.getName();
                                    int var145 = LegacyConversions.inferMailboxTypeFromName(var143, var144);
                                    var137.mType = var145;
                                    int var146 = var136.getDelimiter();
                                    var137.mDelimiter = var146;
                                    byte var147 = 1;
                                    var137.mFlagVisible = (boolean)var147;
                                    byte var173;
                                    if(!var136.getSelect()) {
                                       var173 = 1;
                                    } else {
                                       var173 = 0;
                                    }

                                    var137.mFlagNoSelect = (boolean)var173;
                                    byte var149 = 25;
                                    var137.mVisibleLimit = var149;
                                    var150 = MessagingController.this.mContext;
                                 }
                              }

                              if(var2) {
                                 GroupMessagingListener var154 = MessagingController.this.mListeners;
                                 long var155 = this.val$accountId;
                                 var154.foldersCommandFinished(var155, 4, var3, (MessagingException)null);
                                 var170 = false;
                              } else {
                                 GroupMessagingListener var157 = MessagingController.this.mListeners;
                                 long var158 = this.val$accountId;
                                 var157.foldersCommandFinished(var158, 2, var3, (MessagingException)null);
                                 var170 = false;
                              }
                              break label313;
                           }

                           GroupMessagingListener var160 = MessagingController.this.mListeners;
                           long var161 = this.val$accountId;
                           MessagingException var163 = new MessagingException(32);
                           var160.foldersCommandFinished(var161, 2, var3, var163);
                           var170 = false;
                           break label314;
                        }

                        GroupMessagingListener var14 = MessagingController.this.mListeners;
                        long var15 = this.val$accountId;
                        MessagingException var17 = new MessagingException(34);
                        var14.foldersCommandFinished(var15, 2, var3, var17);
                        var170 = false;
                        break label315;
                     } catch (Exception var171) {
                        GroupMessagingListener var62 = MessagingController.this.mListeners;
                        long var63 = this.val$accountId;
                        String var65 = this.val$deleteMailbox.mDisplayName;
                        MessagingException var66 = new MessagingException(32);
                        var62.foldersCommandFinished(var63, 2, var65, var66);
                        var170 = false;
                     } finally {
                        if(var170) {
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

                  if(true) {
                     return;
                  }

                  null.close();
                  return;
               }

               if(true) {
                  return;
               }

               null.close();
               return;
            }

            if(var1 == null) {
               return;
            }

            var1.close();
            return;
         }

         if(false) {
            null.close();
         }
      }
   }

   class 19 implements Runnable {

      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final long val$tag;


      19(long var2, long var4) {
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
                  Intent var16 = new Intent("com.sec.android.socialhub.action.EXTERNAL_DB_UPDATE");
                  String[] var17 = new String[1];
                  String var18 = Long.toString(this.val$accountId);
                  var17[0] = var18;
                  var16.putExtra("id_array", var17);
                  Intent var20 = var16.putExtra("action", 7);
                  Intent var21 = var16.putExtra("intentType", 400);
                  Intent var22 = var16.putExtra("status", 0);
                  MessagingController.this.mContext.sendBroadcast(var16);
                  int var23 = Log.d("MsgControl >>>", "sendBroadcastNativeSyncCompleted");
               }
            }
         }

         GroupMessagingListener var24 = MessagingController.this.mListeners;
         Context var25 = MessagingController.this.mContext;
         long var26 = this.val$accountId;
         long var28 = this.val$tag;
         var24.checkMailFinished(var25, var26, var1, var28);
      }
   }

   class 18 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$sentFolderId;


      18(EmailContent.Account var2, long var3) {
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
}
