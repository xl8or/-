package com.android.email.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;

public class EmailProvider extends ContentProvider {

   private static final int ACCOUNT = 0;
   private static final int ACCOUNT_BASE = 0;
   private static final int ACCOUNT_CB = 53248;
   private static final int ACCOUNT_CB_BASE = 53248;
   private static final int ACCOUNT_CB_ID = 53249;
   private static final int ACCOUNT_CB_PROTOCOL = 53250;
   private static final int ACCOUNT_ID = 2;
   private static final int ACCOUNT_ID_ADD_TO_FIELD = 3;
   private static final int ACCOUNT_MAILBOXES = 1;
   private static final int ATTACHMENT = 12288;
   private static final int ATTACHMENTS_MESSAGE_ID = 12291;
   private static final int ATTACHMENT_BASE = 12288;
   private static final int ATTACHMENT_CONTENT = 12289;
   private static final int ATTACHMENT_ID = 12290;
   private static final int BASE_SHIFT = 12;
   private static final int BODY = 69632;
   private static final int BODY_BASE = 69632;
   protected static final String BODY_DATABASE_NAME = "EmailProviderBody.db";
   public static final int BODY_DATABASE_VERSION = 6;
   private static final int BODY_HTML = 69635;
   private static final int BODY_ID = 69633;
   private static final int BODY_MESSAGE_ID = 69634;
   private static final int BODY_TEXT = 69636;
   private static final int CERTIFICATE_CACHE = 57344;
   private static final int CERTIFICATE_CACHE_BASE = 57344;
   protected static final String DATABASE_NAME = "EmailProvider.db";
   public static final int DATABASE_VERSION = 1015;
   private static final int DELETED_FOLLOWUP_FLAG = 36864;
   private static final int DELETED_FOLLOWUP_FLAG_BASE = 36864;
   private static final int DELETED_FOLLOWUP_FLAG_ID = 36865;
   private static final String DELETED_FOLLOWUP_INSERT = "insert or replace into FollowupFlag_Deletes select * from FollowupFlag where _id=";
   private static final int DELETED_MESSAGE = 24576;
   private static final int DELETED_MESSAGE_BASE = 24576;
   private static final int DELETED_MESSAGE_ID = 24577;
   private static final String DELETED_MESSAGE_INSERT = "insert or replace into Message_Deletes select * from Message where _id=";
   private static final String DELETED_MESSAGE_INSERT_MULTIPLE = "insert or replace into Message_Deletes select * from Message where ";
   private static final int DELETED_MESSAGE_MAILBOX = 24578;
   private static final String DELETE_BODY = "delete from Body where messageKey=";
   private static final String DELETE_ORPHAN_BODIES = "delete from Body where messageKey in (select messageKey from Body except select _id from Message)";
   public static final String EMAIL_AUTHORITY = "com.android.email.provider";
   private static final int FOLLOWUP_FLAG = 28672;
   private static final int FOLLOWUP_FLAG_BASE = 28672;
   private static final int FOLLOWUP_FLAG_ID = 28673;
   private static final int HISTORY_ACCOUNT = 65536;
   private static final int HISTORY_ACCOUNT_BASE = 65536;
   private static final int HOSTAUTH = 16384;
   private static final int HOSTAUTH_BASE = 16384;
   private static final int HOSTAUTH_ID = 16385;
   private static final String ID_EQUALS = "_id=?";
   public static final Uri INTEGRITY_CHECK_URI = Uri.parse("content://com.android.email.provider/integrityCheck");
   private static final int LAST_EMAIL_PROVIDER_DB_BASE = 65536;
   private static final int MAILBOX = 4096;
   private static final int MAILBOX_BASE = 4096;
   private static final int MAILBOX_CB = 45056;
   private static final int MAILBOX_CB_BASE = 45056;
   private static final int MAILBOX_CB_ID = 45057;
   private static final int MAILBOX_CB_MESSAGES = 45058;
   private static final int MAILBOX_ID = 4098;
   private static final int MAILBOX_ID_ADD_TO_FIELD = 4099;
   private static final int MAILBOX_MESSAGES = 4097;
   private static final int MESSAGE = 8192;
   private static final int MESSAGE_BASE = 8192;
   private static final int MESSAGE_CB = 49152;
   private static final int MESSAGE_CB_BASE = 49152;
   private static final int MESSAGE_CB_ID = 49153;
   private static final int MESSAGE_ID = 8193;
   private static final int ORPHANS_ID = 0;
   private static final int ORPHANS_MAILBOX_KEY = 1;
   private static final String[] ORPHANS_PROJECTION;
   private static final int POLICIES = 40960;
   private static final int POLICIES_BASE = 40960;
   private static final int POLICIES_ID = 40961;
   private static final int RECIPIENTINFORMATIONCACHE = 61440;
   private static final int RECIPIENTINFORMATIONCACHE_BASE = 61440;
   private static final int RECIPIENTINFORMATIONCACHE_FILTER = 61442;
   private static final int RECIPIENTINFORMATIONCACHE_ID = 61441;
   private static final int SYNCED_FOLLOWUP_FLAG_ID = 28674;
   private static final int SYNCED_MESSAGE = 8195;
   private static final int SYNCED_MESSAGE_ID = 8194;
   private static final String[] TABLE_NAMES;
   private static final String TAG = "EmailProvider";
   private static final String TRIGGER_MAILBOX_DELETE = "create trigger mailbox_delete before delete on Mailbox begin delete from Message  where mailboxKey=old._id; delete from Message_Updates  where mailboxKey=old._id; delete from Message_Deletes  where mailboxKey=old._id; end";
   private static final String UPDATED_FOLLOWUP_DELETE = "delete from FollowupFlag_Updates where _id=";
   private static final int UPDATED_FOLLOWUP_FLAG = 32768;
   private static final int UPDATED_FOLLOWUP_FLAG_BASE = 32768;
   private static final int UPDATED_FOLLOWUP_FLAG_ID = 32769;
   private static final String UPDATED_FOLLOWUP_INSERT = "insert or ignore into FollowupFlag_Updates select * from FollowupFlag where _id=";
   private static final int UPDATED_MESSAGE = 20480;
   private static final int UPDATED_MESSAGE_BASE = 20480;
   private static final String UPDATED_MESSAGE_DELETE = "delete from Message_Updates where _id=";
   private static final String UPDATED_MESSAGE_DELETE_MULTIPLE = "delete from Message_Updates where ";
   private static final int UPDATED_MESSAGE_ID = 20481;
   private static final String UPDATED_MESSAGE_INSERT = "insert or ignore into Message_Updates select * from Message where _id=";
   private static final String WHERE_ID = "_id=?";
   private static final UriMatcher sURIMatcher;
   private SQLiteDatabase mBodyDatabase;
   private SQLiteDatabase mDatabase;


   static {
      String[] var0 = new String[]{"_id", "mailboxKey"};
      ORPHANS_PROJECTION = var0;
      String[] var1 = new String[]{"Account", "Mailbox", "Message", "Attachment", "HostAuth", "Message_Updates", "Message_Deletes", "FollowupFlag", "FollowupFlag_Updates", "FollowupFlag_Deletes", "Policies", "Mailbox_CB", "Message_CB", "Account_CB", "CertificateCache", "RecipientInformation", "historyAccount", "Body"};
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
      var2.addURI("com.android.email.provider", "body", 69632);
      var2.addURI("com.android.email.provider", "body/#", 69633);
      var2.addURI("com.android.email.provider", "body/message/#", 69634);
      var2.addURI("com.android.email.provider", "body/#/html", 69635);
      var2.addURI("com.android.email.provider", "body/#/text", 69636);
      var2.addURI("com.android.email.provider", "hostauth", 16384);
      var2.addURI("com.android.email.provider", "hostauth/#", 16385);
      var2.addURI("com.android.email.provider", "mailboxIdAddToField/#", 4099);
      var2.addURI("com.android.email.provider", "accountIdAddToField/#", 3);
      var2.addURI("com.android.email.provider", "syncedMessage/#", 8194);
      var2.addURI("com.android.email.provider", "syncedMessage", 8195);
      var2.addURI("com.android.email.provider", "deletedMessage", 24576);
      var2.addURI("com.android.email.provider", "deletedMessage/#", 24577);
      var2.addURI("com.android.email.provider", "deletedMessage/mailbox/#", 24578);
      var2.addURI("com.android.email.provider", "updatedMessage", 20480);
      var2.addURI("com.android.email.provider", "updatedMessage/#", 20481);
      var2.addURI("com.android.email.provider", "followupFlag", 28672);
      var2.addURI("com.android.email.provider", "updatedFollowupFlag", '\u8000');
      var2.addURI("com.android.email.provider", "deletedFollowupFlag", '\u9000');
      var2.addURI("com.android.email.provider", "followupFlag/#", 28673);
      var2.addURI("com.android.email.provider", "updatedFollowupFlag/#", '\u8001');
      var2.addURI("com.android.email.provider", "deletedFollowupFlag/#", '\u9001');
      var2.addURI("com.android.email.provider", "syncedFollowupFlag/#", 28674);
      var2.addURI("com.android.email.provider", "policies", '\ua000');
      var2.addURI("com.android.email.provider", "policies/#", '\ua001');
      var2.addURI("com.android.email.provider", "mailboxcb", '\ub000');
      var2.addURI("com.android.email.provider", "mailboxcb/#", '\ub001');
      var2.addURI("com.android.email.provider", "mailboxcb/#/mailbox", '\ub002');
      var2.addURI("com.android.email.provider", "messagecb", '\uc000');
      var2.addURI("com.android.email.provider", "messagecb/#", '\uc001');
      var2.addURI("com.android.email.provider", "accountcb", '\ud000');
      var2.addURI("com.android.email.provider", "accountcb/#", '\ud001');
      var2.addURI("com.android.email.provider", "accountcb/protocol", '\ud002');
      var2.addURI("com.android.email.provider", "certificateCache", '\ue000');
      var2.addURI("com.android.email.provider", "recipientInformation", '\uf000');
      var2.addURI("com.android.email.provider", "recipientInformation/#", '\uf001');
      var2.addURI("com.android.email.provider", "recipientInformation/filter/*", '\uf002');
      var2.addURI("com.android.email.provider", "historyAccount", 65536);
   }

   public EmailProvider() {}

   // $FF: synthetic method
   static void access$000(EmailProvider var0, SQLiteDatabase var1) {
      var0.populateFollowupFlagsTable(var1);
   }

   static void createAccountCBTable(SQLiteDatabase var0) {
      String var1 = "CREATE TABLE IF NOT EXISTS  Account_CB" + " (_id integer primary key autoincrement, accountKey integer, sevenAccountKey integer, typeMsg integer, timeLimit integer default 7, sizeLimit integer default 50, peakTime integer default 0, offPeakTime integer default 1, days text default \'0111110\', peakStartTime text default \'09:00\', peakEndTime text default \'18:00\', whileRoaming integer default 1, attachmentEnabled integer default 5000 );";
      var0.execSQL(var1);
      String var2 = createIndex("Account_CB", "accountKey");
      var0.execSQL(var2);
      String var3 = createIndex("Account_CB", "sevenAccountKey");
      var0.execSQL(var3);
      String var4 = createIndex("Account_CB", "typeMsg");
      var0.execSQL(var4);
   }

   static void createAccountTable(SQLiteDatabase var0) {
      String var1 = "create table Account" + " (_id integer primary key autoincrement, displayName text, emailAddress text, syncKey text, syncLookback integer, syncInterval text, hostAuthKeyRecv integer, hostAuthKeySend integer, flags integer, isDefault integer, compatibilityUuid text, senderName text, ringtoneUri text, protocolVersion text, newMessageCount integer, securityFlags integer, securitySyncKey text, signature text, emailsize integer default 3, policyKey text, peakDays integer, peakStartMinute integer, peakEndMinute integer, peakSchedule integer, offPeakSchedule integer, roamingSchedule integer, calendarSyncLookback integer, conflict integer, smimeOwnCertificateAlias text, smimeOptionsFlags integer, smimeSignAlgorithm integer, smimeEncryptionAlgorithm integer, conversationMode integer, textPreview integer, deviceInfoSent integer, deviceBlockedType integer, cbaCertificateAlias text );";
      var0.execSQL(var1);
      var0.execSQL("create trigger account_delete before delete on Account begin delete from Mailbox where accountKey=old._id; delete from HostAuth where _id=old.hostAuthKeyRecv; delete from HostAuth where _id=old.hostAuthKeySend; end");
      var0.execSQL("create trigger account_delete_policy before delete on Account begin delete from Policies where account_id=old._id; end");
      var0.execSQL("create trigger account_insert after insert on Account begin  update HostAuth set accountKey = new._id where accountKey = 0; end");
      var0.execSQL("create trigger account_delete_recipientinformation before delete on Account begin delete from RecipientInformation where accountkey=old._id; end");
   }

   static void createAttachmentTable(SQLiteDatabase var0) {
      String var1 = "create table Attachment" + " (_id integer primary key autoincrement, fileName text, mimeType text, size integer, contentId text, contentUri text, messageKey integer, location text, encoding text, content text, flags integer, content_bytes blob, vmAttOrder integer,vmAttDuration integer,isInline integer);";
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

   static void createCertificateCacheTable(SQLiteDatabase var0) {
      String var1 = "create table CertificateCache" + " (_id integer primary key autoincrement, email text UNIQUE, certificate text );";
      var0.execSQL(var1);
   }

   static void createFollowupFlagsTable(SQLiteDatabase var0) {
      String var1 = "create table FollowupFlag" + " (_id integer primary key autoincrement, messageKey integer, messageSyncServerId text, taskKey integer, taskSyncServerId text, ringtoneUri text, status integer, flagType text, completeTime text, dateCompleted text, startDate text, dueDate text, UTCStartDate text, UTCDueDate text, reminderSet integer, reminderTime text, ordinalDate text, sub_ordinaldate text );";
      var0.execSQL(var1);
      String var2 = "create table FollowupFlag_Updates" + " (_id integer primary key autoincrement, messageKey integer, messageSyncServerId text, taskKey integer, taskSyncServerId text, ringtoneUri text, status integer, flagType text, completeTime text, dateCompleted text, startDate text, dueDate text, UTCStartDate text, UTCDueDate text, reminderSet integer, reminderTime text, ordinalDate text, sub_ordinaldate text );";
      var0.execSQL(var2);
      String var3 = "create table FollowupFlag_Deletes" + " (_id integer primary key autoincrement, messageKey integer, messageSyncServerId text, taskKey integer, taskSyncServerId text, ringtoneUri text, status integer, flagType text, completeTime text, dateCompleted text, startDate text, dueDate text, UTCStartDate text, UTCDueDate text, reminderSet integer, reminderTime text, ordinalDate text, sub_ordinaldate text );";
      var0.execSQL(var3);
      var0.execSQL("create trigger message_delete_followup before delete on Message begin delete from FollowupFlag where messageKey=old._id; end");
   }

   static void createHistoryAccountTable(SQLiteDatabase var0) {
      String var1 = "create table historyAccount" + " (_id integer primary key autoincrement, EmailAddress text UNIQUE, TimeDate text );";
      var0.execSQL(var1);
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

   static void createMailboxCBTable(SQLiteDatabase var0) {
      String var1 = "CREATE TABLE IF NOT EXISTS  Mailbox_CB" + " (_id integer primary key autoincrement, mailboxKey integer, typeMsg integer, sevenMailboxKey integer, syncFlag integer);";
      var0.execSQL(var1);
      String var2 = createIndex("Mailbox_CB", "mailboxKey");
      var0.execSQL(var2);
      String var3 = createIndex("Mailbox_CB", "typeMsg");
      var0.execSQL(var3);
   }

   static void createMailboxTable(SQLiteDatabase var0) {
      String var1 = "create table Mailbox" + " (_id integer primary key autoincrement, displayName text, serverId text, parentServerId text, accountKey integer, type integer, delimiter integer, syncKey text, syncLookback integer, syncInterval integer, syncTime integer, unreadCount integer, flagVisible integer, flagNoSelect integer, flags integer, visibleLimit integer, syncStatus text,flagChanged integer, dstMailboxId text, newDisplayName text);";
      var0.execSQL(var1);
      var0.execSQL("create index mailbox_serverId on Mailbox (serverId)");
      var0.execSQL("create index mailbox_accountKey on Mailbox (accountKey)");
      var0.execSQL("create trigger mailbox_delete before delete on Mailbox begin delete from Message  where mailboxKey=old._id; delete from Message_Updates  where mailboxKey=old._id; delete from Message_Deletes  where mailboxKey=old._id; end");
   }

   static void createMessageCBTable(SQLiteDatabase var0) {
      String var1 = "CREATE TABLE IF NOT EXISTS  Message_CB" + " (_id integer primary key autoincrement, messageKey integer, typeMsg integer,sevenMessageKey integer, missingBody integer, missingHtmlBody integer, unkEncoding integer, sevenAccountKey integer );";
      var0.execSQL(var1);
      String var2 = createIndex("Message_CB", "messageKey");
      var0.execSQL(var2);
      String var3 = createIndex("Message_CB", "typeMsg");
      var0.execSQL(var3);
   }

   static void createMessageTable(SQLiteDatabase var0) {
      String var1 = " (_id integer primary key autoincrement, syncServerId text, syncServerTimeStamp integer, " + "displayName text, timeStamp integer, subject text, flagRead integer, flagLoaded integer, flagFavorite integer, flagAttachment integer, flagReply integer, originalId integer, flags integer, clientId integer, messageId text, mailboxKey integer, accountKey integer, fromList text, toList text, ccList text, bccList text, replyToList text, meetingInfo text, threadId integer, threadName text, importance integer default 1, istruncated integer default 0, flagMoved integer default 0, dstMailboxKey integer default -1,flagStatus integer default 0, isMimeLoaded integer default 0, smimeFlags integer default 0, encryptionAlgorithm integer, conversationId text, conversationIndex blob, followupflag integer, umCallerId text, umUserNotes text,lastVerb integer, lastVerbTime integer,messageType integer, messageDirty integer, snippet text,IRMTemplateId text, IRMContentExpiryDate text, IRMContentOwner text, IRMLicenseFlag integer default -1, IRMOwner integer );";
      String var2 = " (_id integer unique, syncServerId text, syncServerTimeStamp integer, " + "displayName text, timeStamp integer, subject text, flagRead integer, flagLoaded integer, flagFavorite integer, flagAttachment integer, flagReply integer, originalId integer, flags integer, clientId integer, messageId text, mailboxKey integer, accountKey integer, fromList text, toList text, ccList text, bccList text, replyToList text, meetingInfo text, threadId integer, threadName text, importance integer default 1, istruncated integer default 0, flagMoved integer default 0, dstMailboxKey integer default -1,flagStatus integer default 0, isMimeLoaded integer default 0, smimeFlags integer default 0, encryptionAlgorithm integer, conversationId text, conversationIndex blob, followupflag integer, umCallerId text, umUserNotes text,lastVerb integer, lastVerbTime integer,messageType integer, messageDirty integer, snippet text,IRMTemplateId text, IRMContentExpiryDate text, IRMContentOwner text, IRMLicenseFlag integer default -1, IRMOwner integer );";
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

   static void createPoliciesTable(SQLiteDatabase var0) {
      String var1 = "create table Policies" + " (_id integer primary key autoincrement, name text, type text, value text, account_id integer , CONSTRAINT policyconstraint UNIQUE(account_id,name));";
      var0.execSQL(var1);
   }

   static void createRecipientInformationTable(SQLiteDatabase var0) {
      String var1 = "create table RecipientInformation" + " (_id integer primary key autoincrement, server_id text, accountkey integer, email_address text, fileas text, alias text, weightedrank integer );";
      var0.execSQL(var1);
   }

   static void deleteOrphans(SQLiteDatabase param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static void dropTrigger(SQLiteDatabase var0, String var1) {
      try {
         String var2 = "drop trigger " + var1;
         var0.execSQL(var2);
      } catch (SQLException var4) {
         ;
      }
   }

   static SQLiteDatabase getReadableDatabase(Context var0) {
      EmailProvider var1 = new EmailProvider();
      Class var2 = var1.getClass();
      return (var1.new DatabaseHelper(var0, "EmailProvider.db")).getReadableDatabase();
   }

   private void populateFollowupFlagsTable(SQLiteDatabase var1) {}

   static void resetAccountCBTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Account_CB");
      } catch (SQLException var4) {
         ;
      }

      createAccountCBTable(var0);
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

   static void resetFollowupFlagsTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table FollowupFlag");
      } catch (SQLException var4) {
         ;
      }

      createFollowupFlagsTable(var0);
   }

   static void resetHostAuthTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table HostAuth");
      } catch (SQLException var4) {
         ;
      }

      createHostAuthTable(var0);
   }

   static void resetMailboxCBTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Mailbox_CB");
      } catch (SQLException var4) {
         ;
      }

      createMailboxCBTable(var0);
   }

   static void resetMailboxTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Mailbox");
      } catch (SQLException var4) {
         ;
      }

      createMailboxTable(var0);
   }

   static void resetMessageCBTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Message_CB");
      } catch (SQLException var4) {
         ;
      }

      createMessageCBTable(var0);
   }

   static void resetMessageTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Message");
         var0.execSQL("drop table Message_Updates");
         var0.execSQL("drop table Message_Deletes");
         var0.execSQL("drop table Message_CB");
      } catch (SQLException var4) {
         ;
      }

      createMessageTable(var0);
   }

   static void resetPoliciesTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table Policies");
      } catch (SQLException var4) {
         ;
      }

      createPoliciesTable(var0);
   }

   static void resetRecipientInformationTable(SQLiteDatabase var0, int var1, int var2) {
      try {
         var0.execSQL("drop table RecipientInformation");
      } catch (SQLException var4) {
         ;
      }

      createRecipientInformationTable(var0);
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

   public void createSevenTrigger(SQLiteDatabase var1) {
      var1.execSQL(" create trigger mailbox_delete_cb before delete on Mailbox begin delete from Mailbox_CB where mailboxKey = old._id; end");
      var1.execSQL(" create trigger mailbox_insert_cb after insert on Mailbox begin insert into Mailbox_CB(mailboxKey  ,typeMsg) values (new._id  , (select typeMsg from Account_CB where accountKey = new.accountKey)); end");
      var1.execSQL("create trigger message_delete_cb before delete on Message begin  delete from Message_CB  where messageKey=old._id; end");
      var1.execSQL("create trigger message_insert_cb after insert on Message begin  insert into Message_CB(messageKey,typeMsg)  values (new._id, 0); end");
      var1.execSQL("create trigger account_delete_cb before delete on Account begin  delete from Account_CB where accountKey=old._id; delete from Mailbox_CB where mailboxKey    in ( select _id from Mailbox          where accountKey=old._id); end");
   }

   public int delete(Uri param1, String param2, String[] param3) {
      // $FF: Couldn't be decompiled
   }

   public SQLiteDatabase getDatabase(Context param1) {
      // $FF: Couldn't be decompiled
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
      case '\u8000':
         var3 = "vnd.android.cursor.dir/email-followupflags";
         break;
      case 28673:
      case '\u8001':
         var3 = "vnd.android.cursor.item/email-followupflags";
         break;
      case '\ua000':
         var3 = "vnd.android.cursor.dir/email-policies";
         break;
      case '\ua001':
         var3 = "vnd.android.cursor.item/email-policies";
         break;
      case '\uf000':
         var3 = "vnd.android.cursor.dir/email-recipientinformation";
         break;
      case '\uf001':
         var3 = "vnd.android.cursor.item/email-recipientinformation";
         break;
      case 69632:
         var3 = "vnd.android.cursor.dir/email-message";
         break;
      case 69633:
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

   public void resetSevenTrigger(SQLiteDatabase var1) {
      dropTrigger(var1, "mailbox_delete_cb");
      dropTrigger(var1, "mailbox_insert_cb");
      dropTrigger(var1, "message_updates_delete");
      dropTrigger(var1, "message_deletes_delete");
      dropTrigger(var1, "message_delete_cb");
      dropTrigger(var1, "message_insert_cb");
      dropTrigger(var1, "account_delete_cb");
      this.createSevenTrigger(var1);
   }

   public int update(Uri param1, ContentValues param2, String param3, String[] param4) {
      // $FF: Couldn't be decompiled
   }

   private class DatabaseHelper extends SQLiteOpenHelper {

      Context mContext;


      DatabaseHelper(Context var2, String var3) {
         super(var2, var3, (CursorFactory)null, 1015);
         this.mContext = var2;
      }

      private void addColumnToMsgTables(SQLiteDatabase var1, String var2, String var3) throws SQLException {
         String var4 = " add column " + var2 + " " + var3 + ";";
         String var5 = "alter table Message" + var4;
         var1.execSQL(var5);
         String var6 = "alter table Message_Updates" + var4;
         var1.execSQL(var6);
         String var7 = "alter table Message_Deletes" + var4;
         var1.execSQL(var7);
      }

      public void onCreate(SQLiteDatabase var1) {
         int var2 = Log.d("EmailProvider", "Creating EmailProvider database");
         EmailProvider.createMessageTable(var1);
         EmailProvider.createAttachmentTable(var1);
         EmailProvider.createMailboxTable(var1);
         EmailProvider.createHostAuthTable(var1);
         EmailProvider.createAccountTable(var1);
         EmailProvider.createFollowupFlagsTable(var1);
         EmailProvider.createPoliciesTable(var1);
         EmailProvider.createMessageCBTable(var1);
         EmailProvider.createMailboxCBTable(var1);
         EmailProvider.createAccountCBTable(var1);
         EmailProvider.this.createSevenTrigger(var1);
         EmailProvider.createCertificateCacheTable(var1);
         EmailProvider.createRecipientInformationTable(var1);
         EmailProvider.createHistoryAccountTable(var1);
      }

      public void onOpen(SQLiteDatabase var1) {}

      public void onUpgrade(SQLiteDatabase param1, int param2, int param3) {
         // $FF: Couldn't be decompiled
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
