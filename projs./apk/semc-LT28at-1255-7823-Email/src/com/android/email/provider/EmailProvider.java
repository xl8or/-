package com.android.email.provider;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;

public class EmailProvider extends ContentProvider {

   private static final int ACCOUNT = 0;
   private static final int ACCOUNT_BASE = 0;
   private static final int ACCOUNT_ID = 2;
   private static final int ACCOUNT_ID_ADD_TO_FIELD = 3;
   private static final int ACCOUNT_MAILBOXES = 1;
   private static final int ATTACHMENT = 12288;
   private static final int ATTACHMENTS_MESSAGE_ID = 12291;
   private static final int ATTACHMENT_BASE = 12288;
   private static final int ATTACHMENT_CONTENT = 12289;
   private static final int ATTACHMENT_ID = 12290;
   private static final int BASE_SHIFT = 12;
   private static final int BODY = 28672;
   private static final int BODY_BASE = 28672;
   protected static final String BODY_DATABASE_NAME = "EmailProviderBody.db";
   public static final int BODY_DATABASE_VERSION = 6;
   private static final int BODY_HTML = 28675;
   private static final int BODY_ID = 28673;
   private static final int BODY_MESSAGE_ID = 28674;
   private static final int BODY_TEXT = 28676;
   protected static final String DATABASE_NAME = "EmailProvider.db";
   public static final int DATABASE_VERSION = 12;
   private static final int DELETED_MESSAGE = 24576;
   private static final int DELETED_MESSAGE_BASE = 24576;
   private static final int DELETED_MESSAGE_ID = 24577;
   private static final String DELETED_MESSAGE_INSERT = "insert or replace into Message_Deletes select * from Message where _id=";
   private static final int DELETED_MESSAGE_MAILBOX = 24578;
   private static final String DELETE_BODY = "delete from Body where messageKey=";
   private static final String DELETE_ORPHAN_BODIES = "delete from Body where messageKey in (select messageKey from Body except select _id from Message)";
   public static final String EMAIL_AUTHORITY = "com.android.email.provider";
   private static final int HOSTAUTH = 16384;
   private static final int HOSTAUTH_BASE = 16384;
   private static final int HOSTAUTH_ID = 16385;
   private static final String ID_EQUALS = "_id=?";
   public static final Uri INTEGRITY_CHECK_URI = Uri.parse("content://com.android.email.provider/integrityCheck");
   private static final int LAST_EMAIL_PROVIDER_DB_BASE = 24576;
   private static final int MAILBOX = 4096;
   private static final int MAILBOX_BASE = 4096;
   private static final int MAILBOX_ID = 4098;
   private static final int MAILBOX_ID_ADD_TO_FIELD = 4099;
   private static final int MAILBOX_MESSAGES = 4097;
   private static final int MAX_TRIES = 3;
   private static final int MESSAGE = 8192;
   private static final int MESSAGE_BASE = 8192;
   private static final int MESSAGE_ID = 8193;
   private static final int ORPHANS_ID = 0;
   private static final int ORPHANS_MAILBOX_KEY = 1;
   private static final String[] ORPHANS_PROJECTION;
   private static final long RETRY_INTERVAL = 500L;
   private static final int SYNCED_MESSAGE_ID = 8194;
   private static final String[] TABLE_NAMES;
   private static final String TAG = "EmailProvider";
   private static final String TRIGGER_MAILBOX_DELETE = "create trigger mailbox_delete before delete on Mailbox begin delete from Message  where mailboxKey=old._id; delete from Message_Updates  where mailboxKey=old._id; delete from Message_Deletes  where mailboxKey=old._id; end";
   private static final int UPDATED_MESSAGE = 20480;
   private static final int UPDATED_MESSAGE_BASE = 20480;
   private static final String UPDATED_MESSAGE_DELETE = "delete from Message_Updates where _id=";
   private static final int UPDATED_MESSAGE_ID = 20481;
   private static final String UPDATED_MESSAGE_INSERT = "insert or ignore into Message_Updates select * from Message where _id=";
   private static final String WHERE_ID = "_id=?";
   private static final UriMatcher sURIMatcher;
   private SQLiteDatabase mBodyDatabase;
   private SQLiteDatabase mDatabase;


   static {
      String[] var0 = new String[]{"_id", "mailboxKey"};
      ORPHANS_PROJECTION = var0;
      String[] var1 = new String[]{"Account", "Mailbox", "Message", "Attachment", "HostAuth", "Message_Updates", "Message_Deletes", "Body"};
      TABLE_NAMES = var1;
      sURIMatcher = new UriMatcher(-1);
      UriMatcher var2 = sURIMatcher;
      var2.addURI("com.android.email.provider", "account", 0);
      var2.addURI("com.android.email.provider", "account/#", 2);
      var2.addURI("com.android.email.provider", "account/#/mailbox", 1);
      var2.addURI("com.android.email.provider", "mailbox", 4096);
      var2.addURI("com.android.email.provider", "mailbox/#", 4098);
      var2.addURI("com.android.email.provider", "mailbox/#/message", 4097);
      var2.addURI("com.android.email.provider", "message", 8192);
      var2.addURI("com.android.email.provider", "message/#", 8193);
      var2.addURI("com.android.email.provider", "attachment", 12288);
      var2.addURI("com.android.email.provider", "attachment/#", 12290);
      var2.addURI("com.android.email.provider", "attachment/content/*", 12289);
      var2.addURI("com.android.email.provider", "attachment/message/#", 12291);
      var2.addURI("com.android.email.provider", "body", 28672);
      var2.addURI("com.android.email.provider", "body/#", 28673);
      var2.addURI("com.android.email.provider", "body/message/#", 28674);
      var2.addURI("com.android.email.provider", "body/#/html", 28675);
      var2.addURI("com.android.email.provider", "body/#/text", 28676);
      var2.addURI("com.android.email.provider", "hostauth", 16384);
      var2.addURI("com.android.email.provider", "hostauth/#", 16385);
      var2.addURI("com.android.email.provider", "mailboxIdAddToField/#", 4099);
      var2.addURI("com.android.email.provider", "accountIdAddToField/#", 3);
      var2.addURI("com.android.email.provider", "syncedMessage/#", 8194);
      var2.addURI("com.android.email.provider", "deletedMessage", 24576);
      var2.addURI("com.android.email.provider", "deletedMessage/#", 24577);
      var2.addURI("com.android.email.provider", "deletedMessage/mailbox/#", 24578);
      var2.addURI("com.android.email.provider", "updatedMessage", 20480);
      var2.addURI("com.android.email.provider", "updatedMessage/#", 20481);
   }

   public EmailProvider() {}

   static void createAccountTable(SQLiteDatabase var0) {
      String var1 = "create table Account" + " (_id integer primary key autoincrement, displayName text, emailAddress text, syncKey text, syncLookback integer, syncInterval text, hostAuthKeyRecv integer, hostAuthKeySend integer, flags integer, isDefault integer, compatibilityUuid text, senderName text, ringtoneUri text, protocolVersion text, newMessageCount integer, securityFlags integer, securitySyncKey text, signature text );";
      var0.execSQL(var1);
      var0.execSQL("create trigger account_delete before delete on Account begin delete from Mailbox where accountKey=old._id; delete from HostAuth where _id=old.hostAuthKeyRecv; delete from HostAuth where _id=old.hostAuthKeySend; end");
   }

   static void createAttachmentTable(SQLiteDatabase var0) {
      String var1 = "create table Attachment" + " (_id integer primary key autoincrement, fileName text, mimeType text, size integer, contentId text, contentUri text, messageKey integer, location text, encoding text, content text, flags integer, content_bytes blob);";
      var0.execSQL(var1);
      String var2 = createIndex("Attachment", "messageKey");
      var0.execSQL(var2);
   }

   static void createBodyTable(SQLiteDatabase var0) {
      String var1 = "create table Body" + " (_id integer primary key autoincrement, messageKey integer, htmlContent text, textContent text, htmlReply text, textReply text, sourceMessageKey text, introText text);";
      var0.execSQL(var1);
      String var2 = createIndex("Body", "messageKey");
      var0.execSQL(var2);
   }

   static void createHostAuthTable(SQLiteDatabase var0) {
      String var1 = "create table HostAuth" + " (_id integer primary key autoincrement, protocol text, address text, port integer, flags integer, login text, password text, domain text, accountKey integer);";
      var0.execSQL(var1);
   }

   static String createIndex(String var0, String var1) {
      StringBuilder var2 = (new StringBuilder()).append("create index ");
      String var3 = var0.toLowerCase();
      return var2.append(var3).append('_').append(var1).append(" on ").append(var0).append(" (").append(var1).append(");").toString();
   }

   static void createMailboxTable(SQLiteDatabase var0) {
      String var1 = "create table Mailbox" + " (_id integer primary key autoincrement, displayName text, serverId text, parentServerId text, accountKey integer, type integer, delimiter integer, syncKey text, syncLookback integer, syncInterval integer, syncTime integer, unreadCount integer, flagVisible integer, flags integer, visibleLimit integer, syncStatus text);";
      var0.execSQL(var1);
      var0.execSQL("create index mailbox_serverId on Mailbox (serverId)");
      var0.execSQL("create index mailbox_accountKey on Mailbox (accountKey)");
      var0.execSQL("create trigger mailbox_delete before delete on Mailbox begin delete from Message  where mailboxKey=old._id; delete from Message_Updates  where mailboxKey=old._id; delete from Message_Deletes  where mailboxKey=old._id; end");
   }

   static void createMessageTable(SQLiteDatabase var0) {
      String var1 = " (_id integer primary key autoincrement, syncServerId text, syncServerTimeStamp integer, " + "displayName text, timeStamp integer, subject text, flagRead integer, flagLoaded integer, flagFavorite integer, flagAttachment integer, flags integer, clientId integer, messageId text, mailboxKey integer, accountKey integer, fromList text, toList text, ccList text, bccList text, replyToList text, meetingInfo text);";
      String var2 = " (_id integer unique, syncServerId text, syncServerTimeStamp integer, " + "displayName text, timeStamp integer, subject text, flagRead integer, flagLoaded integer, flagFavorite integer, flagAttachment integer, flags integer, clientId integer, messageId text, mailboxKey integer, accountKey integer, fromList text, toList text, ccList text, bccList text, replyToList text, meetingInfo text);";
      String var3 = "create table Message" + var1;
      var0.execSQL(var3);
      String var4 = "create table Message_Updates" + var2;
      var0.execSQL(var4);
      String var5 = "create table Message_Deletes" + var2;
      var0.execSQL(var5);
      String[] var6 = new String[]{"timeStamp", "flagRead", "flagLoaded", "mailboxKey", "syncServerId"};
      String[] var7 = var6;
      int var8 = var6.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String var10 = var7[var9];
         String var11 = createIndex("Message", var10);
         var0.execSQL(var11);
      }

      var0.execSQL("create trigger message_delete before delete on Message begin delete from Attachment  where messageKey=old._id; end");
      var0.execSQL("create trigger unread_message_insert before insert on Message when NEW.flagRead=0 begin update Mailbox set unreadCount=unreadCount+1  where _id=NEW.mailboxKey; end");
      var0.execSQL("create trigger unread_message_delete before delete on Message when OLD.flagRead=0 begin update Mailbox set unreadCount=unreadCount-1  where _id=OLD.mailboxKey; end");
      var0.execSQL("create trigger unread_message_move before update of mailboxKey on Message when OLD.flagRead=0 begin update Mailbox set unreadCount=unreadCount-1  where _id=OLD.mailboxKey; update Mailbox set unreadCount=unreadCount+1 where _id=NEW.mailboxKey; end");
      var0.execSQL("create trigger unread_message_read before update of flagRead on Message when OLD.flagRead!=NEW.flagRead begin update Mailbox set unreadCount=unreadCount+ case OLD.flagRead when 0 then -1 else 1 end  where _id=OLD.mailboxKey; end");
   }

   static void deleteOrphans(SQLiteDatabase param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static SQLiteDatabase getReadableDatabase(Context var0) {
      EmailProvider var1 = new EmailProvider();
      Class var2 = var1.getClass();
      return (var1.new DatabaseHelper(var0, "EmailProvider.db")).getReadableDatabase();
   }

   static void resetAccountTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Account");
      } catch (SQLException var4) {
         ;
      }

      createAccountTable(var0);
   }

   static void resetAttachmentTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Attachment");
      } catch (SQLException var4) {
         ;
      }

      createAttachmentTable(var0);
   }

   static void resetHostAuthTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table HostAuth");
      } catch (SQLException var4) {
         ;
      }

      createHostAuthTable(var0);
   }

   static void resetMailboxTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Mailbox");
      } catch (SQLException var4) {
         ;
      }

      createMailboxTable(var0);
   }

   static void resetMessageTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Message");
         var0.execSQL("drop table Message_Updates");
         var0.execSQL("drop table Message_Deletes");
      } catch (SQLException var4) {
         ;
      }

      createMessageTable(var0);
   }

   public static void setTotalUnread(Context var0, int var1) {
      if(var1 >= 0) {
         Intent var2 = new Intent("com.sonyericsson.home.action.UPDATE_BADGE");
         Intent var3 = var2.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", "com.android.email");
         Intent var4 = var2.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", "com.android.email.activity.Welcome");
         String var5 = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE";
         byte var6;
         if(var1 > 0) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         var2.putExtra(var5, (boolean)var6);
         String var8 = String.valueOf(var1);
         var2.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", var8);
         var0.sendBroadcast(var2, "com.sonyericsson.home.permission.RECEIVE_BADGE");
      }
   }

   private void totalUnreadUpdate(int param1) {
      // $FF: Couldn't be decompiled
   }

   private SQLiteDatabase tryGetWriteableDatabase(SQLiteOpenHelper var1) {
      int var2 = 0;
      SQLiteDatabase var3 = null;

      while(var2 < 3) {
         SQLiteDatabase var4;
         try {
            var4 = var1.getWritableDatabase();
         } catch (SQLiteException var13) {
            ++var2;
            String var6 = "Exception when get writeable database, try times = " + var2;
            int var7 = Log.w("EmailProvider", var6, var13);
            if(var2 == 3) {
               throw var13;
            }

            long var8 = 500L;

            try {
               Thread.sleep(var8);
            } catch (InterruptedException var12) {
               boolean var11 = Thread.interrupted();
            }
            continue;
         }

         var3 = var4;
         break;
      }

      return var3;
   }

   static void upgradeBodyTable(SQLiteDatabase var0, int var1, int var2) {
      if(var1 < 5) {
         try {
            var0.execSQL("drop table Body");
            createBodyTable(var0);
         } catch (SQLException var6) {
            ;
         }
      } else if(var1 == 5) {
         try {
            var0.execSQL("alter table Body add introText text");
         } catch (SQLException var7) {
            int var4 = Log.w("EmailProvider", "Exception upgrading EmailProviderBody.db from v5 to v6", var7);
         }

      }
   }

   private String whereWith(String var1, String var2) {
      String var3;
      if(var2 == null) {
         var3 = var1;
      } else {
         StringBuilder var4 = new StringBuilder(var1);
         StringBuilder var5 = var4.append(" AND (");
         var4.append(var2);
         StringBuilder var7 = var4.append(')');
         var3 = var4.toString();
      }

      return var3;
   }

   private String whereWithId(String var1, String var2) {
      StringBuilder var3 = new StringBuilder(256);
      StringBuilder var4 = var3.append("_id=");
      var3.append(var1);
      if(var2 != null) {
         StringBuilder var6 = var3.append(" AND (");
         var3.append(var2);
         StringBuilder var8 = var3.append(')');
      }

      return var3.toString();
   }

   public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> var1) throws OperationApplicationException {
      Context var2 = this.getContext();
      SQLiteDatabase var3 = this.getDatabase(var2);
      var3.beginTransaction();

      ContentProviderResult[] var4;
      try {
         var4 = super.applyBatch(var1);
         var3.setTransactionSuccessful();
      } finally {
         var3.endTransaction();
      }

      return var4;
   }

   public void checkDatabases() {
      if(this.mDatabase != null) {
         this.mDatabase = null;
      }

      if(this.mBodyDatabase != null) {
         this.mBodyDatabase = null;
      }

      File var1 = this.getContext().getDatabasePath("EmailProvider.db");
      File var2 = this.getContext().getDatabasePath("EmailProviderBody.db");
      if(var1.exists() && !var2.exists()) {
         int var3 = Log.w("EmailProvider", "Deleting orphaned EmailProvider database...");
         boolean var4 = var1.delete();
      } else if(var2.exists()) {
         if(!var1.exists()) {
            int var5 = Log.w("EmailProvider", "Deleting orphaned EmailProviderBody database...");
            boolean var6 = var2.delete();
         }
      }
   }

   public int delete(Uri param1, String param2, String[] param3) {
      // $FF: Couldn't be decompiled
   }

   public SQLiteDatabase getDatabase(Context var1) {
      synchronized(this){}

      SQLiteDatabase var2;
      try {
         if(this.mDatabase != null) {
            var2 = this.mDatabase;
         } else {
            this.checkDatabases();
            EmailProvider.DatabaseHelper var3 = new EmailProvider.DatabaseHelper(var1, "EmailProvider.db");
            SQLiteDatabase var4 = this.tryGetWriteableDatabase(var3);
            this.mDatabase = var4;
            if(this.mDatabase != null) {
               this.mDatabase.setLockingEnabled((boolean)1);
               EmailProvider.BodyDatabaseHelper var5 = new EmailProvider.BodyDatabaseHelper(var1, "EmailProviderBody.db");
               SQLiteDatabase var6 = this.tryGetWriteableDatabase(var5);
               this.mBodyDatabase = var6;
               if(this.mBodyDatabase != null) {
                  this.mBodyDatabase.setLockingEnabled((boolean)1);
                  String var7 = this.mBodyDatabase.getPath();
                  SQLiteDatabase var8 = this.mDatabase;
                  String var9 = "attach \"" + var7 + "\" as BodyDatabase";
                  var8.execSQL(var9);
               }
            }

            deleteOrphans(this.mDatabase, "Message_Updates");
            deleteOrphans(this.mDatabase, "Message_Deletes");
            var2 = this.mDatabase;
         }
      } finally {
         ;
      }

      return var2;
   }

   public String getType(Uri var1) {
      String var3;
      switch(sURIMatcher.match(var1)) {
      case 0:
         var3 = "vnd.android.cursor.dir/email-account";
         break;
      case 1:
      case 4096:
         var3 = "vnd.android.cursor.dir/email-mailbox";
         break;
      case 2:
         var3 = "vnd.android.cursor.item/email-account";
         break;
      case 4097:
      case 8192:
      case 20480:
         var3 = "vnd.android.cursor.dir/email-message";
         break;
      case 4098:
         var3 = "vnd.android.cursor.item/email-mailbox";
         break;
      case 8193:
      case 20481:
         var3 = "vnd.android.cursor.item/email-message";
         break;
      case 12288:
      case 12291:
         var3 = "vnd.android.cursor.dir/email-attachment";
         break;
      case 12290:
         var3 = "vnd.android.cursor.item/email-attachment";
         break;
      case 16384:
         var3 = "vnd.android.cursor.dir/email-hostauth";
         break;
      case 16385:
         var3 = "vnd.android.cursor.item/email-hostauth";
         break;
      case 28672:
         var3 = "vnd.android.cursor.dir/email-message";
         break;
      case 28673:
         var3 = "vnd.android.cursor.item/email-body";
         break;
      default:
         String var2 = "Unknown URI " + var1;
         throw new IllegalArgumentException(var2);
      }

      return var3;
   }

   public Uri insert(Uri param1, ContentValues param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean onCreate() {
      this.checkDatabases();
      return false;
   }

   public Cursor query(Uri param1, String[] param2, String param3, String[] param4, String param5) {
      // $FF: Couldn't be decompiled
   }

   public int update(Uri param1, ContentValues param2, String param3, String[] param4) {
      // $FF: Couldn't be decompiled
   }

   private class DatabaseHelper extends SQLiteOpenHelper {

      Context mContext;


      DatabaseHelper(Context var2, String var3) {
         super(var2, var3, (CursorFactory)null, 12);
         this.mContext = var2;
      }

      public void onCreate(SQLiteDatabase var1) {
         int var2 = Log.d("EmailProvider", "Creating EmailProvider database");
         EmailProvider.createMessageTable(var1);
         EmailProvider.createAttachmentTable(var1);
         EmailProvider.createMailboxTable(var1);
         EmailProvider.createHostAuthTable(var1);
         EmailProvider.createAccountTable(var1);
      }

      public void onOpen(SQLiteDatabase var1) {}

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         if(var2 >= 5) {
            if(var2 == 5) {
               try {
                  var1.execSQL("alter table Message add column syncServerTimeStamp integer;");
                  var1.execSQL("alter table Message_Updates add column syncServerTimeStamp integer;");
                  var1.execSQL("alter table Message_Deletes add column syncServerTimeStamp integer;");
               } catch (SQLException var31) {
                  int var10 = Log.w("EmailProvider", "Exception upgrading EmailProvider.db from v5 to v6", var31);
               }

               var2 = 6;
            }

            if(var2 == 6) {
               var1.execSQL("drop trigger mailbox_delete;");
               var1.execSQL("create trigger mailbox_delete before delete on Mailbox begin delete from Message  where mailboxKey=old._id; delete from Message_Updates  where mailboxKey=old._id; delete from Message_Deletes  where mailboxKey=old._id; end");
               var2 = 7;
            }

            if(var2 == 7) {
               try {
                  var1.execSQL("alter table Account add column securityFlags integer;");
               } catch (SQLException var30) {
                  String var12 = "Exception upgrading EmailProvider.db from 7 to 8 " + var30;
                  int var13 = Log.w("EmailProvider", var12);
               }

               var2 = 8;
            }

            if(var2 == 8) {
               try {
                  var1.execSQL("alter table Account add column securitySyncKey text;");
                  var1.execSQL("alter table Account add column signature text;");
               } catch (SQLException var29) {
                  String var15 = "Exception upgrading EmailProvider.db from 8 to 9 " + var29;
                  int var16 = Log.w("EmailProvider", var15);
               }

               var2 = 9;
            }

            if(var2 == 9) {
               try {
                  var1.execSQL("alter table Message add column meetingInfo text;");
                  var1.execSQL("alter table Message_Updates add column meetingInfo text;");
                  var1.execSQL("alter table Message_Deletes add column meetingInfo text;");
               } catch (SQLException var28) {
                  String var18 = "Exception upgrading EmailProvider.db from 9 to 10 " + var28;
                  int var19 = Log.w("EmailProvider", var18);
               }

               var2 = 10;
            }

            if(var2 == 10) {
               try {
                  var1.execSQL("alter table Attachment add column content text;");
                  var1.execSQL("alter table Attachment add column flags integer;");
               } catch (SQLException var27) {
                  String var21 = "Exception upgrading EmailProvider.db from 10 to 11 " + var27;
                  int var22 = Log.w("EmailProvider", var21);
               }

               var2 = 11;
            }

            if(var2 == 11) {
               try {
                  var1.execSQL("alter table Attachment add column content_bytes blob;");
               } catch (SQLException var26) {
                  String var24 = "Exception upgrading EmailProvider.db from 11 to 12 " + var26;
                  int var25 = Log.w("EmailProvider", var24);
               }

            }
         } else {
            Account[] var4 = AccountManager.get(this.mContext).getAccountsByType("com.android.exchange");
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Account var7 = var4[var6];
               AccountManagerFuture var8 = AccountManager.get(this.mContext).removeAccount(var7, (AccountManagerCallback)null, (Handler)null);
            }

            EmailProvider.resetMessageTable(var1, var2, var3);
            EmailProvider.resetAttachmentTable(var1, var2, var3);
            EmailProvider.resetMailboxTable(var1, var2, var3);
            EmailProvider.resetHostAuthTable(var1, var2, var3);
            EmailProvider.resetAccountTable(var1, var2, var3);
         }
      }
   }

   private class BodyDatabaseHelper extends SQLiteOpenHelper {

      BodyDatabaseHelper(Context var2, String var3) {
         super(var2, var3, (CursorFactory)null, 6);
      }

      public void onCreate(SQLiteDatabase var1) {
         int var2 = Log.d("EmailProvider", "Creating EmailProviderBody database");
         EmailProvider.createBodyTable(var1);
      }

      public void onOpen(SQLiteDatabase var1) {}

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         EmailProvider.upgradeBodyTable(var1, var2, var3);
      }
   }
}
