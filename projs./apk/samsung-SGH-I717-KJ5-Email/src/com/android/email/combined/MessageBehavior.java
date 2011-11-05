package com.android.email.combined;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.BadgeManager;
import com.android.email.combined.EmailException;
import com.android.email.combined.MessageFacade;
import com.android.email.provider.EmailContent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBehavior {

   private static final String TAG = "MessageBehavior";
   private static MessageBehavior mInstance = null;
   private Context mContext;
   private Set<MessageFacade.MessageListener> mListeners;
   private ConcurrentHashMap<MessageFacade.MessageListener, Object> mListenersMap;
   MessageBehavior.NotificationTimestamp mNotificationTimestamp;


   public MessageBehavior(Context var1) {
      MessageBehavior.NotificationTimestamp var2 = new MessageBehavior.NotificationTimestamp();
      this.mNotificationTimestamp = var2;
      ConcurrentHashMap var3 = new ConcurrentHashMap();
      this.mListenersMap = var3;
      Set var4 = this.mListenersMap.keySet();
      this.mListeners = var4;
      this.mContext = var1;
   }

   public static MessageBehavior getInstance(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public void addFolder(int var1, int var2, String var3) {
      EmailContent.Account var4 = EmailContent.Account.restoreAccountWithSevenAccountId(this.mContext, var1);
      if(var4 != null && var3 != null && var2 >= 1) {
         Hashtable var7 = new Hashtable();
         Integer var8 = Integer.valueOf(var2);
         var7.put(var3, var8);
         long var10 = var4.mId;
         this.listFolders(var10, var7, (boolean)1);
      } else {
         String var5 = "addFolder >> account: " + var4 + ", remoteFolderName: " + var3 + ", remoteFolderId: " + var2;
         int var6 = Log.e("MessageBehavior", var5);
      }
   }

   public void addListener(MessageFacade.MessageListener var1) {
      synchronized(this){}

      try {
         this.mListenersMap.put(var1, this);
      } finally {
         ;
      }

   }

   public void addMessage(EmailContent.Message var1, EmailContent.Body var2, EmailContent.Attachment[] var3) {
      MessageBehavior.1 var4 = new MessageBehavior.1(var1, var2, var3);
      (new Thread(var4)).start();
   }

   public boolean isActiveListener(MessageFacade.MessageListener var1) {
      synchronized(this){}
      boolean var6 = false;

      boolean var2;
      try {
         var6 = true;
         var2 = this.mListenersMap.containsKey(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public void listFolders(long var1, Hashtable<String, Integer> var3, boolean var4) {
      EmailContent.Account var5 = EmailContent.Account.restoreAccountWithId(this.mContext, var1);
      if(var5 != null && var3 != null) {
         MessageBehavior.8 var13 = new MessageBehavior.8(var3, var5, var4, var1);
         (new Thread(var13)).start();
      } else {
         String var6 = "listFolders >> remoteFolders: " + var3;
         int var7 = Log.w("MessageBehavior", var6);
      }
   }

   public void moveMessage(int var1, int var2, int var3) {
      MessageBehavior.5 var4 = new MessageBehavior.5(var1, var2, var3);
      (new Thread(var4)).start();
   }

   public void notifyFolderFailed(EmailException var1) {
      synchronized(this){}

      try {
         Iterator var2 = this.mListeners.iterator();

         while(var2.hasNext()) {
            ((MessageFacade.MessageListener)var2.next()).onFolderFailed(var1);
         }
      } finally {
         ;
      }

   }

   public void notifyFolderFinished(long var1) {
      synchronized(this){}

      try {
         Iterator var3 = this.mListeners.iterator();

         while(var3.hasNext()) {
            ((MessageFacade.MessageListener)var3.next()).onFolderFinished(var1);
         }
      } finally {
         ;
      }

   }

   public void notifyFolderStarted() {
      synchronized(this){}

      try {
         Iterator var1 = this.mListeners.iterator();

         while(var1.hasNext()) {
            ((MessageFacade.MessageListener)var1.next()).onFolderStarted();
         }
      } finally {
         ;
      }

   }

   public void notifyMessageFailed(EmailException var1) {
      synchronized(this){}

      try {
         Iterator var2 = this.mListeners.iterator();

         while(var2.hasNext()) {
            ((MessageFacade.MessageListener)var2.next()).onMessageFailed(var1);
         }
      } finally {
         ;
      }

   }

   public void notifyMessageFinished(long var1) {
      synchronized(this){}

      try {
         Iterator var3 = this.mListeners.iterator();

         while(var3.hasNext()) {
            ((MessageFacade.MessageListener)var3.next()).onMessageFinished(var1);
         }
      } finally {
         ;
      }

   }

   public void notifyMessageStarted() {
      synchronized(this){}

      try {
         Iterator var1 = this.mListeners.iterator();

         while(var1.hasNext()) {
            ((MessageFacade.MessageListener)var1.next()).onMessageStarted();
         }
      } finally {
         ;
      }

   }

   public void removeFolder(int var1, int var2) {
      MessageBehavior.3 var3 = new MessageBehavior.3(var1, var2);
      (new Thread(var3)).start();
   }

   public void removeListener(MessageFacade.MessageListener var1) {
      synchronized(this){}

      try {
         this.mListenersMap.remove(var1);
      } finally {
         ;
      }

   }

   public void removeMessage(int var1, int var2) {
      MessageBehavior.6 var3 = new MessageBehavior.6(var1, var2);
      (new Thread(var3)).start();
   }

   public void removeMessageForDate(int var1, long var2) {
      MessageBehavior.7 var4 = new MessageBehavior.7(var2, var1);
      (new Thread(var4)).start();
   }

   public String replacePosToBasic(String var1, int var2) {
      String var3;
      if(var1 == null) {
         var3 = null;
      } else if(var1.trim().equals("")) {
         var3 = null;
      } else if(var2 <= 1) {
         var3 = null;
      } else {
         var3 = String.valueOf(Integer.parseInt(var1) + 2);
      }

      return var3;
   }

   public void setMessageRead(int var1, boolean var2) {
      MessageBehavior.4 var3 = new MessageBehavior.4(var1, var2);
      (new Thread(var3)).start();
   }

   public void syncMessage(EmailContent.Message var1, EmailContent.Body var2, EmailContent.Attachment[] var3) {
      this.addMessage(var1, var2, var3);
   }

   public ContentValues toContentAttachment(EmailContent.Attachment var1, int var2, int var3) {
      ContentValues var4 = new ContentValues();
      String var5 = var1.mFileName;
      var4.put("fileName", var5);
      String var6 = var1.mMimeType;
      var4.put("mimeType", var6);
      Long var7 = Long.valueOf(var1.mSize);
      var4.put("size", var7);
      String var8 = var1.mContentId;
      var4.put("contentId", var8);
      String var9 = var1.mContentUri;
      var4.put("contentUri", var9);
      Long var10 = Long.valueOf(var1.mMessageKey);
      var4.put("messageKey", var10);
      String var11 = var1.mLocation;
      String var12 = this.replacePosToBasic(var11, var3);
      var4.put("location", var12);
      String var13 = var1.mEncoding;
      var4.put("encoding", var13);
      Integer var14 = Integer.valueOf(var2);
      var4.put("messageKey", var14);
      return var4;
   }

   public ContentValues toContentValues(EmailContent.Message param1) {
      // $FF: Couldn't be decompiled
   }

   public ContentValues toContentValuesBody(EmailContent.Body var1, int var2) {
      ContentValues var3 = new ContentValues();
      if(var1.mTextContent != null) {
         String var4 = var1.mTextContent;
         var3.put("textContent", var4);
      }

      if(var1.mHtmlContent != null) {
         String var5 = var1.mHtmlContent;
         var3.put("htmlContent", var5);
      }

      if(var1.mTextReply != null) {
         String var6 = var1.mTextReply;
         var3.put("textReply", var6);
      }

      if(var1.mHtmlReply != null) {
         String var7 = var1.mHtmlReply;
         var3.put("htmlReply", var7);
      }

      if(var1.mSourceKey < 0L) {
         Integer var8 = Integer.valueOf(0);
         var3.put("sourceMessageKey", var8);
      } else if(var1.mSourceKey != 0L) {
         Long var11 = Long.valueOf(var1.mSourceKey);
         var3.put("sourceMessageKey", var11);
      }

      if(var1.mIntroText != null) {
         String var9 = var1.mIntroText;
         var3.put("introText", var9);
      }

      Integer var10 = Integer.valueOf(var2);
      var3.put("messageKey", var10);
      return var3;
   }

   public ContentValues toContentValuesSeven(EmailContent.Message var1) {
      ContentValues var2 = new ContentValues();
      Long var3 = Long.valueOf(var1.mSevenMessageKey);
      var2.put("sevenMessageKey", var3);
      Integer var4 = Integer.valueOf(var1.mTypeMsg);
      var2.put("typeMsg", var4);
      Integer var5 = Integer.valueOf(var1.mMissingBody);
      var2.put("missingBody", var5);
      Integer var6 = Integer.valueOf(var1.mMissingHtmlBody);
      var2.put("missingHtmlBody", var6);
      Integer var7 = Integer.valueOf(var1.mUnkEncoding);
      var2.put("unkEncoding", var7);
      Long var8 = Long.valueOf(var1.mSevenAccountKey);
      var2.put("sevenAccountKey", var8);
      return var2;
   }

   public void updateFolder(int var1, int var2, String var3) {
      MessageBehavior.2 var4 = new MessageBehavior.2(var1, var2, var3);
      (new Thread(var4)).start();
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Attachment[] val$attachments;
      // $FF: synthetic field
      final EmailContent.Body val$body;
      // $FF: synthetic field
      final EmailContent.Message val$message;


      1(EmailContent.Message var2, EmailContent.Body var3, EmailContent.Attachment[] var4) {
         this.val$message = var2;
         this.val$body = var3;
         this.val$attachments = var4;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final int val$remoteAccountId;
      // $FF: synthetic field
      final int val$remoteFolderId;


      3(int var2, int var3) {
         this.val$remoteAccountId = var2;
         this.val$remoteFolderId = var3;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   private static class NotificationTimestamp {

      public static final long MAX_WAIT_TIME = 3000L;
      private Hashtable<String, String> mNotiHistory;


      NotificationTimestamp() {
         Hashtable var1 = new Hashtable();
         this.mNotiHistory = var1;
      }

      public long elapsedTime(long var1) {
         long var3 = System.currentTimeMillis();
         long var5 = this.get(var1);
         return var3 - var5;
      }

      public long get(long var1) {
         Hashtable var3 = this.mNotiHistory;
         String var4 = Long.toString(var1);
         String var5 = (String)var3.get(var4);
         long var8;
         if(var5 != null) {
            label23: {
               long var6;
               try {
                  var6 = Long.parseLong(var5);
               } catch (Exception var11) {
                  break label23;
               }

               var8 = var6;
               return var8;
            }
         }

         var8 = 0L;
         return var8;
      }

      public void set(long var1) {
         Hashtable var3 = this.mNotiHistory;
         String var4 = Long.toString(var1);
         String var5 = Long.toString(System.currentTimeMillis());
         var3.put(var4, var5);
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final int val$remoteAccountId;
      // $FF: synthetic field
      final int val$remoteFolderId;
      // $FF: synthetic field
      final String val$remoteFolderName;


      2(int var2, int var3, String var4) {
         this.val$remoteAccountId = var2;
         this.val$remoteFolderId = var3;
         this.val$remoteFolderName = var4;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 8 implements Runnable {

      // $FF: synthetic field
      final EmailContent.Account val$account;
      // $FF: synthetic field
      final long val$accountId;
      // $FF: synthetic field
      final boolean val$isPush;
      // $FF: synthetic field
      final Hashtable val$remoteFolders;


      8(Hashtable var2, EmailContent.Account var3, boolean var4, long var5) {
         this.val$remoteFolders = var2;
         this.val$account = var3;
         this.val$isPush = var4;
         this.val$accountId = var5;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 6 implements Runnable {

      // $FF: synthetic field
      final int val$remoteFolderId;
      // $FF: synthetic field
      final int val$remoteMessageId;


      6(int var2, int var3) {
         this.val$remoteMessageId = var2;
         this.val$remoteFolderId = var3;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   private static class LocalMailboxInfo {

      private static final int COLUMN_ACCOUNT_KEY = 2;
      private static final int COLUMN_DISPLAY_NAME = 1;
      private static final int COLUMN_ID = 0;
      private static final int COLUMN_TYPE = 3;
      private static final String[] PROJECTION;
      long mAccountKey;
      String mDisplayName;
      long mId;
      int mType;


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

      // $FF: synthetic method
      static String[] access$100() {
         return PROJECTION;
      }
   }

   class 7 implements Runnable {

      // $FF: synthetic field
      final long val$remainDay;
      // $FF: synthetic field
      final int val$remoteAccountId;


      7(long var2, int var4) {
         this.val$remainDay = var2;
         this.val$remoteAccountId = var4;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final boolean val$isRead;
      // $FF: synthetic field
      final int val$remoteMessageId;


      4(int var2, boolean var3) {
         this.val$remoteMessageId = var2;
         this.val$isRead = var3;
      }

      public void run() {
         MessageBehavior.this.notifyMessageStarted();
         Context var1 = MessageBehavior.this.mContext;
         int var2 = this.val$remoteMessageId;
         EmailContent.Message var3 = EmailContent.Message.restoreMessageWithSevenMessageId(var1, var2);
         if(var3 != null && this.val$remoteMessageId >= 1) {
            try {
               ContentValues var8 = new ContentValues();
               Boolean var9 = Boolean.valueOf(this.val$isRead);
               var8.put("flagRead", var9);
               Uri var10 = EmailContent.Message.CONTENT_URI;
               long var11 = var3.mId;
               Uri var13 = ContentUris.withAppendedId(var10, var11);
               MessageBehavior.this.mContext.getContentResolver().update(var13, var8, (String)null, (String[])null);
               StringBuilder var15 = (new StringBuilder()).append("setMessageRead >> messageId: ");
               long var16 = var3.mId;
               String var18 = var15.append(var16).toString();
               int var19 = Log.d("MessageBehavior", var18);
               MessageBehavior var20 = MessageBehavior.this;
               long var21 = var3.mAccountKey;
               var20.notifyMessageFinished(var21);
               BadgeManager.updateBadgeProvider(MessageBehavior.this.mContext);
            } catch (Exception var27) {
               int var24 = Log.e("MessageBehavior", "setMessageRead: ", var27);
               MessageBehavior var25 = MessageBehavior.this;
               EmailException var26 = new EmailException(var27);
               var25.notifyMessageFailed(var26);
            }
         } else {
            StringBuilder var4 = (new StringBuilder()).append("setMessageRead >> remoteMessageId: ");
            int var5 = this.val$remoteMessageId;
            String var6 = var4.append(var5).toString();
            int var7 = Log.w("MessageBehavior", var6);
         }
      }
   }

   class 5 implements Runnable {

      // $FF: synthetic field
      final int val$remoteDestFolderId;
      // $FF: synthetic field
      final int val$remoteMessageId;
      // $FF: synthetic field
      final int val$remoteSrcFolderId;


      5(int var2, int var3, int var4) {
         this.val$remoteMessageId = var2;
         this.val$remoteSrcFolderId = var3;
         this.val$remoteDestFolderId = var4;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
