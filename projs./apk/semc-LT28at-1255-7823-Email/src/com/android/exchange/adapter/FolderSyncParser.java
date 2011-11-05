package com.android.exchange.adapter;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.net.Uri;
import com.android.email.provider.AttachmentProvider;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.MockParserStream;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FolderSyncParser extends AbstractSyncParser {

   public static final String ALL_BUT_ACCOUNT_MAILBOX = "accountKey=? and type!=68";
   public static final int CALENDAR_TYPE = 8;
   public static final int CONTACTS_TYPE = 9;
   public static final int DELETED_TYPE = 4;
   public static final int DRAFTS_TYPE = 3;
   public static final int INBOX_TYPE = 2;
   public static final int JOURNAL_TYPE = 11;
   private static final String[] MAILBOX_ID_COLUMNS_PROJECTION;
   private static final String[] MAILBOX_SERVER_ID_COLUMNS_PROJECTION;
   public static final int NOTES_TYPE = 10;
   public static final int OUTBOX_TYPE = 6;
   public static final int SENT_TYPE = 5;
   public static final String TAG = "FolderSyncParser";
   public static final int TASKS_TYPE = 7;
   public static final int USER_FOLDER_TYPE = 1;
   public static final int USER_MAILBOX_TYPE = 12;
   private static final String WHERE_DISPLAY_NAME_AND_ACCOUNT = "displayName=? and accountKey=?";
   private static final String WHERE_PARENT_SERVER_ID = "parentServerId=? and accountKey=?";
   private static final String WHERE_PARENT_SERVER_ID_AND_ACCOUNT = "parentServerId=? and accountKey=?";
   private static final String WHERE_SERVER_ID_AND_ACCOUNT = "serverId=? and accountKey=?";
   public static final List<Integer> mValidFolderTypes;
   private long mAccountId;
   private String mAccountIdAsString;
   private String[] mBindArguments;
   private MockParserStream mMock = null;


   static {
      Integer[] var0 = new Integer[8];
      Integer var1 = Integer.valueOf(2);
      var0[0] = var1;
      Integer var2 = Integer.valueOf(3);
      var0[1] = var2;
      Integer var3 = Integer.valueOf(4);
      var0[2] = var3;
      Integer var4 = Integer.valueOf(5);
      var0[3] = var4;
      Integer var5 = Integer.valueOf(6);
      var0[4] = var5;
      Integer var6 = Integer.valueOf(12);
      var0[5] = var6;
      Integer var7 = Integer.valueOf(8);
      var0[6] = var7;
      Integer var8 = Integer.valueOf(9);
      var0[7] = var8;
      mValidFolderTypes = Arrays.asList(var0);
      String[] var9 = new String[]{"_id", "serverId"};
      MAILBOX_ID_COLUMNS_PROJECTION = var9;
      String[] var10 = new String[]{"serverId"};
      MAILBOX_SERVER_ID_COLUMNS_PROJECTION = var10;
   }

   public FolderSyncParser(InputStream var1, AbstractSyncAdapter var2) throws IOException {
      super(var1, var2);
      String[] var3 = new String[2];
      this.mBindArguments = var3;
      long var4 = this.mAccount.mId;
      this.mAccountId = var4;
      String var6 = Long.toString(this.mAccountId);
      this.mAccountIdAsString = var6;
      if(var1 instanceof MockParserStream) {
         MockParserStream var7 = (MockParserStream)var1;
         this.mMock = var7;
      }
   }

   private void deleteChildFolder(String param1, ArrayList<ContentProviderOperation> param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void deleteFolder(String var1, ArrayList<ContentProviderOperation> var2) throws IOException {
      Cursor var3 = this.getServerIdCursor(var1);

      try {
         if(var3.moveToFirst()) {
            String[] var4 = new String[]{"Deleting ", var1};
            this.userLog(var4);
            Uri var5 = EmailContent.Mailbox.CONTENT_URI;
            long var6 = var3.getLong(0);
            ContentProviderOperation var8 = ContentProviderOperation.newDelete(ContentUris.withAppendedId(var5, var6)).build();
            var2.add(var8);
            Context var10 = this.mContext;
            long var11 = this.mAccountId;
            long var13 = this.mMailbox.mId;
            AttachmentProvider.deleteAllMailboxAttachmentFiles(var10, var11, var13);
         }
      } finally {
         var3.close();
      }

   }

   private Cursor getServerIdCursor(String var1) {
      this.mBindArguments[0] = var1;
      String[] var2 = this.mBindArguments;
      String var3 = this.mAccountIdAsString;
      var2[1] = var3;
      ContentResolver var4 = this.mContentResolver;
      Uri var5 = EmailContent.Mailbox.CONTENT_URI;
      String[] var6 = EmailContent.ID_PROJECTION;
      String[] var7 = this.mBindArguments;
      return var4.query(var5, var6, "serverId=? and accountKey=?", var7, (String)null);
   }

   public void addParser(ArrayList<ContentProviderOperation> var1) throws IOException {
      String var2 = null;
      String var3 = null;
      String var4 = null;
      int var5 = 0;

      while(this.nextTag(463) != 3) {
         switch(this.tag) {
         case 455:
            var2 = this.getValue();
            break;
         case 456:
            var3 = this.getValue();
            break;
         case 457:
            var4 = this.getValue();
            break;
         case 458:
            var5 = this.getValueInt();
            break;
         default:
            this.skipTag();
         }
      }

      List var6 = mValidFolderTypes;
      Integer var7 = Integer.valueOf(var5);
      if(var6.contains(var7)) {
         EmailContent.Mailbox var8 = new EmailContent.Mailbox();
         var8.mDisplayName = var2;
         var8.mServerId = var3;
         long var9 = this.mAccountId;
         var8.mAccountKey = var9;
         var8.mType = 1;
         var8.mSyncInterval = -1;
         switch(var5) {
         case 2:
            var8.mType = 0;
            int var18 = this.mAccount.mSyncInterval;
            var8.mSyncInterval = var18;
            break;
         case 3:
            var8.mType = 3;
            break;
         case 4:
            var8.mType = 6;
            break;
         case 5:
            var8.mType = 5;
            break;
         case 6:
            var8.mType = 4;
         case 7:
         default:
            break;
         case 8:
            var8.mType = 65;
            int var20 = this.mAccount.mSyncInterval;
            var8.mSyncInterval = var20;
            break;
         case 9:
            var8.mType = 66;
            int var19 = this.mAccount.mSyncInterval;
            var8.mSyncInterval = var19;
         }

         byte var11;
         if(var8.mType < 64) {
            var11 = 1;
         } else {
            var11 = 0;
         }

         var8.mFlagVisible = (boolean)var11;
         if(var4 != null && !var4.equals("0")) {
            var8.mParentServerId = var4;
         }

         String[] var12 = new String[]{"Adding mailbox: ", null};
         String var13 = var8.mDisplayName;
         var12[1] = var13;
         this.userLog(var12);
         Builder var14 = ContentProviderOperation.newInsert(EmailContent.Mailbox.CONTENT_URI);
         ContentValues var15 = var8.toContentValues();
         ContentProviderOperation var16 = var14.withValues(var15).build();
         var1.add(var16);
      }
   }

   public void changesParser() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void commandsParser() throws IOException {}

   public void commit() throws IOException {}

   public void deleteParser(ArrayList<ContentProviderOperation> var1) throws IOException {
      while(this.nextTag(464) != 3) {
         switch(this.tag) {
         case 456:
            String var2 = this.getValue();
            this.deleteChildFolder(var2, var1);
            this.deleteFolder(var2, var1);
            break;
         default:
            this.skipTag();
         }
      }

   }

   public boolean parse() throws IOException {
      boolean var1 = false;
      boolean var2 = false;
      if(this.nextTag(0) != 470) {
         throw new Parser.EasParserException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 460) {
               int var3 = this.getValueInt();
               if(var3 != 1) {
                  EasSyncService var4 = this.mService;
                  String var5 = "FolderSync failed: " + var3;
                  var4.errorLog(var5);
                  if(var3 != 9) {
                     this.mService.errorLog("Throwing IOException; will retry later");
                     throw new Parser.EasParserException("Folder status error");
                  }

                  this.mAccount.mSyncKey = "0";
                  this.mService.errorLog("Bad sync key; RESET and delete all folders");
                  ContentResolver var6 = this.mContentResolver;
                  Uri var7 = EmailContent.Mailbox.CONTENT_URI;
                  String[] var8 = new String[1];
                  String var9 = Long.toString(this.mAccountId);
                  var8[0] = var9;
                  var6.delete(var7, "accountKey=? and type!=68", var8);
                  SyncManager.folderListReloaded(this.mAccountId);
                  var1 = true;
                  var2 = true;
               }
            } else if(this.tag == 466) {
               EmailContent.Account var11 = this.mAccount;
               String var12 = this.getValue();
               var11.mSyncKey = var12;
               String[] var13 = new String[]{"New Account SyncKey: ", null};
               String var14 = this.mAccount.mSyncKey;
               var13[1] = var14;
               this.userLog(var13);
            } else if(this.tag == 462) {
               this.changesParser();
            } else {
               this.skipTag();
            }
         }

         Object var15 = this.mService.getSynchronizer();
         synchronized(var15) {
            if(!this.mService.isStopped() || var2) {
               ContentValues var16 = new ContentValues();
               String var17 = this.mAccount.mSyncKey;
               var16.put("syncKey", var17);
               EmailContent.Account var18 = this.mAccount;
               Context var19 = this.mContext;
               var18.update(var19, var16);
               String[] var21 = new String[]{"Leaving FolderSyncParser with Account syncKey=", null};
               String var22 = this.mAccount.mSyncKey;
               var21[1] = var22;
               this.userLog(var21);
            }

            return var1;
         }
      }
   }

   public void responsesParser() throws IOException {}

   public void updateParser(ArrayList<ContentProviderOperation> var1) throws IOException {
      String var2 = null;
      String var3 = null;
      String var4 = null;

      while(this.nextTag(465) != 3) {
         switch(this.tag) {
         case 455:
            var3 = this.getValue();
            break;
         case 456:
            var2 = this.getValue();
            break;
         case 457:
            var4 = this.getValue();
            break;
         default:
            this.skipTag();
         }
      }

      if(var2 != null) {
         if(var3 != null || var4 != null) {
            Cursor var5 = this.getServerIdCursor(var2);

            try {
               if(var5.moveToFirst()) {
                  String[] var6 = new String[]{"Updating ", var2};
                  this.userLog(var6);
                  ContentValues var7 = new ContentValues();
                  if(var3 != null) {
                     var7.put("displayName", var3);
                  }

                  if(var4 != null) {
                     var7.put("parentServerId", var4);
                  }

                  Uri var8 = EmailContent.Mailbox.CONTENT_URI;
                  long var9 = var5.getLong(0);
                  ContentProviderOperation var11 = ContentProviderOperation.newUpdate(ContentUris.withAppendedId(var8, var9)).withValues(var7).build();
                  var1.add(var11);
               }
            } finally {
               var5.close();
            }

         }
      }
   }

   public void wipe() {}
}
