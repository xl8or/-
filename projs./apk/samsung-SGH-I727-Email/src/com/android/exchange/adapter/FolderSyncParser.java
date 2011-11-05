package com.android.exchange.adapter;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.mail.DeviceAccessException;
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
   public static final int NOTES_TYPE = 10;
   public static final int OUTBOX_TYPE = 6;
   public static final int RECIPIENT_INFORMATION_CACHE_TYPE = 19;
   public static final int SENT_TYPE = 5;
   public static final String TAG = "FolderSyncParser";
   public static final int TASKS_TYPE = 7;
   public static final int UNKNOWN_FOLDER_TYPE = 18;
   public static final int USER_CALENDAR_TYPE = 13;
   public static final int USER_CONTACTS_TYPE = 14;
   public static final int USER_FOLDER_TYPE = 1;
   public static final int USER_JOURNAL_TYPE = 16;
   public static final int USER_MAILBOX_TYPE = 12;
   public static final int USER_MAIL_TYPE = 12;
   public static final int USER_NOTES_TYPE = 17;
   public static final int USER_TASKS_TYPE = 15;
   private static final String WHERE_DISPLAY_NAME_AND_ACCOUNT = "displayName=? and accountKey=?";
   private static final String WHERE_PARENT_SERVER_ID_AND_ACCOUNT = "parentServerId=? and accountKey=?";
   private static final String WHERE_SERVER_ID_AND_ACCOUNT = "serverId=? and accountKey=?";
   public static final List<Integer> mValidFolderTypes;
   private long mAccountId;
   private String mAccountIdAsString;
   private String[] mBindArguments;
   private MockParserStream mMock = null;


   static {
      Integer[] var0 = new Integer[18];
      Integer var1 = Integer.valueOf(1);
      var0[0] = var1;
      Integer var2 = Integer.valueOf(2);
      var0[1] = var2;
      Integer var3 = Integer.valueOf(3);
      var0[2] = var3;
      Integer var4 = Integer.valueOf(4);
      var0[3] = var4;
      Integer var5 = Integer.valueOf(5);
      var0[4] = var5;
      Integer var6 = Integer.valueOf(6);
      var0[5] = var6;
      Integer var7 = Integer.valueOf(12);
      var0[6] = var7;
      Integer var8 = Integer.valueOf(12);
      var0[7] = var8;
      Integer var9 = Integer.valueOf(8);
      var0[8] = var9;
      Integer var10 = Integer.valueOf(9);
      var0[9] = var10;
      Integer var11 = Integer.valueOf(7);
      var0[10] = var11;
      Integer var12 = Integer.valueOf(13);
      var0[11] = var12;
      Integer var13 = Integer.valueOf(14);
      var0[12] = var13;
      Integer var14 = Integer.valueOf(15);
      var0[13] = var14;
      Integer var15 = Integer.valueOf(16);
      var0[14] = var15;
      Integer var16 = Integer.valueOf(17);
      var0[15] = var16;
      Integer var17 = Integer.valueOf(18);
      var0[16] = var17;
      Integer var18 = Integer.valueOf(19);
      var0[17] = var18;
      mValidFolderTypes = Arrays.asList(var0);
      String[] var19 = new String[]{"_id", "serverId"};
      MAILBOX_ID_COLUMNS_PROJECTION = var19;
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

      EmailContent.Mailbox var6 = new EmailContent.Mailbox();
      var6.mDisplayName = var2;
      var6.mServerId = var3;
      long var7 = this.mAccountId;
      var6.mAccountKey = var7;
      var6.mType = 1;
      var6.mSyncInterval = -1;
      switch(var5) {
      case 2:
         var6.mType = 0;
         int var15 = this.mAccount.mSyncInterval;
         var6.mSyncInterval = var15;
         break;
      case 3:
         var6.mType = 3;
         break;
      case 4:
         var6.mType = 6;
         break;
      case 5:
         var6.mType = 5;
         break;
      case 6:
         var6.mType = 4;

         try {
            if(this.mAccount != null && Double.parseDouble(this.mAccount.mProtocolVersion) > 12.1D && (this.mAccount.mFlags & 512) != 0) {
               int var17 = this.mAccount.mSyncInterval;
               var6.mSyncInterval = var17;
            }
         } catch (NumberFormatException var24) {
            var24.printStackTrace();
         } catch (NullPointerException var25) {
            var25.printStackTrace();
         }
         break;
      case 7:
         var6.mType = 67;
         int var22 = this.mAccount.mSyncInterval;
         var6.mSyncInterval = var22;
         break;
      case 8:
         var6.mType = 65;
         int var18 = this.mAccount.mSyncInterval;
         var6.mSyncInterval = var18;
         break;
      case 9:
         var6.mType = 66;
         int var16 = this.mAccount.mSyncInterval;
         var6.mSyncInterval = var16;
         break;
      case 10:
         Context var19 = this.mContext;
         long var20 = this.mAccountId;
         if(Double.parseDouble(EmailContent.Account.restoreAccountWithId(var19, var20).mProtocolVersion) < 14.0D) {
            return;
         }

         var6.mType = 69;
         break;
      case 11:
      default:
         var6.mType = 96;
         break;
      case 12:
         var6.mType = 12;
         break;
      case 13:
         var6.mType = 82;
         break;
      case 14:
         var6.mType = 83;
         break;
      case 15:
         var6.mType = 81;
         break;
      case 16:
         var6.mType = 85;
         break;
      case 17:
         var6.mType = 84;
         break;
      case 18:
         var6.mType = 96;
         break;
      case 19:
         var6.mType = 97;
      }

      if(var6.mType == 96) {
         var6.mFlagVisible = (boolean)0;
      } else {
         byte var23;
         if(var6.mType < 64) {
            var23 = 1;
         } else {
            var23 = 0;
         }

         var6.mFlagVisible = (boolean)var23;
      }

      if(!"0".equals(var4)) {
         var6.mParentServerId = var4;
      }

      String[] var9 = new String[]{"Adding mailbox: ", null};
      String var10 = var6.mDisplayName;
      var9[1] = var10;
      this.userLog(var9);
      Builder var11 = ContentProviderOperation.newInsert(EmailContent.Mailbox.CONTENT_URI);
      ContentValues var12 = var6.toContentValues();
      ContentProviderOperation var13 = var11.withValues(var12).build();
      var1.add(var13);
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
            Cursor var3 = this.getServerIdCursor(var2);

            try {
               if(var3.moveToFirst()) {
                  String[] var4 = new String[]{"Deleting ", var2};
                  this.userLog(var4);
                  Uri var5 = EmailContent.Mailbox.CONTENT_URI;
                  long var6 = var3.getLong(0);
                  ContentProviderOperation var8 = ContentProviderOperation.newDelete(ContentUris.withAppendedId(var5, var6)).build();
                  var1.add(var8);
                  Context var10 = this.mContext;
                  long var11 = this.mAccountId;
                  long var13 = this.mMailbox.mId;
                  AttachmentProvider.deleteAllMailboxAttachmentFiles(var10, var11, var13);
               }
               break;
            } finally {
               var3.close();
            }
         default:
            this.skipTag();
         }
      }

   }

   public void moveResponseParser() throws IOException {}

   public boolean parse() throws IOException, DeviceAccessException {
      boolean var1 = false;
      boolean var2 = false;
      if(this.nextTag(0) != 470) {
         throw new Parser.EasParserException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 460) {
               int var3 = this.getValueInt();
               if(this.isProvisioningStatus(var3)) {
                  this.mService.mEasNeedsProvisioning = (boolean)1;
                  var1 = false;
               } else if(var3 != 1) {
                  EasSyncService var4 = this.mService;
                  String var5 = "FolderSync failed: " + var3;
                  var4.errorLog(var5);
                  if(var3 != 9) {
                     if(var3 == 129) {
                        throw new DeviceAccessException(262145, 2131166810);
                     }

                     this.mService.errorLog("Throwing IOException; will retry later");
                     throw new Parser.EasParserException("Folder status error");
                  }

                  this.mAccount.mSyncKey = "0";
                  this.mService.errorLog("Bad sync key; RESET and delete all folders");
                  String var6 = "** Bad sync key; RESET and delete data **" + var3;
                  int var7 = Log.e("FolderSyncParser", var6);
                  ContentResolver var8 = this.mContentResolver;
                  Uri var9 = EmailContent.Mailbox.CONTENT_URI;
                  String[] var10 = new String[1];
                  String var11 = Long.toString(this.mAccountId);
                  var10[0] = var11;
                  var8.delete(var9, "accountKey=? and type!=68", var10);
                  SyncManager.folderListReloaded(this.mAccountId);
                  var1 = true;
                  var2 = true;
               }
            } else if(this.tag == 466) {
               EmailContent.Account var13 = this.mAccount;
               String var14 = this.getValue();
               var13.mSyncKey = var14;
               String[] var15 = new String[]{"New Account SyncKey: ", null};
               String var16 = this.mAccount.mSyncKey;
               var15[1] = var16;
               this.userLog(var15);
            } else if(this.tag == 462) {
               this.changesParser();
            } else {
               this.skipTag();
            }
         }

         Object var17 = this.mService.getSynchronizer();
         synchronized(var17) {
            if(!this.mService.isStopped() || var2) {
               ContentValues var18 = new ContentValues();
               String var19 = this.mAccount.mSyncKey;
               var18.put("syncKey", var19);
               EmailContent.Account var20 = this.mAccount;
               Context var21 = this.mContext;
               var20.update(var21, var18);
               String[] var23 = new String[]{"Leaving FolderSyncParser with Account syncKey=", null};
               String var24 = this.mAccount.mSyncKey;
               var23[1] = var24;
               this.userLog(var23);
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
            String var6 = null;
            if(!"0".equals(var4)) {
               var6 = var4;
            }

            try {
               if(var5.moveToFirst()) {
                  String[] var7 = new String[]{"Updating ", var2};
                  this.userLog(var7);
                  ContentValues var8 = new ContentValues();
                  if(var3 != null) {
                     var8.put("displayName", var3);
                  }

                  if(var4 != null) {
                     var8.put("parentServerId", var6);
                  }

                  Uri var9 = EmailContent.Mailbox.CONTENT_URI;
                  long var10 = var5.getLong(0);
                  ContentProviderOperation var12 = ContentProviderOperation.newUpdate(ContentUris.withAppendedId(var9, var10)).withValues(var8).build();
                  var1.add(var12);
               }
            } finally {
               var5.close();
            }

         }
      }
   }

   public void wipe() {}
}
