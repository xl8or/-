package com.htc.android.mail;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.SyncConfig;
import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.ServiceConnection;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.HtcAdjustableCursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.DeadObjectException;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Attachment;
import com.htc.android.mail.HtcMailCustomization;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailEventBroadcaster;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mailboxs;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import com.htc.android.mail.eassvc.pim.EASOptions;
import com.htc.android.mail.server.ExchangeServer;
import com.htc.android.mail.ulog.MULogMgr;
import com.htc.android.pim.eas.IEASService;
import com.htc.opensense.sync.SyncSettingUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class MailProvider extends ContentProvider {

   private static final int ACCOUNTS = 5;
   private static final int ACCOUNT_ID = 6;
   private static final int ACCOUNT_WITH_UNREAD = 501;
   private static final int ALLMESSAGES = 100;
   private static final int ALLMESSAGES_COUNT_READ = 102;
   private static final int ALLMESSAGES_COUNT_UNREAD = 103;
   private static final int ALL_UNREAD_MAIL = 112;
   public static final Uri AccountVerify = Uri.parse("content://mail/AccountVerify");
   private static final int COMMAND_DELETEMAIL = 306;
   private static final int COMMAND_DELETEMAIL_ID = 307;
   private static final int COMMAND_MARKSTAR = 302;
   private static final int COMMAND_MARKSTAR_ID = 303;
   private static final int COMMAND_MOVEMAIL = 304;
   private static final int COMMAND_MOVEMAIL_ID = 305;
   private static final int COMMAND_READ = 300;
   private static final int COMMAND_READ_ID = 301;
   public static final String DATABASE_PEOPLE = "people_db";
   private static final int DATABASE_VERSION = 116;
   private static final boolean DEBUG = false;
   private static final int DECRYPT_ACCOUNT = 502;
   private static final int DELETEMESSAGE_ID = 400;
   private static final int EASMAILBOXS = 24;
   private static final int EASMAILBOXS_ID = 34;
   private static final int EASMAILFLAG_ID = 35;
   private static final int EASMESSAGES = 1;
   private static final int EASMESSAGE_ID = 2;
   private static final int EASMESSAGE_IDS = 49;
   private static final int EASPARTS = 7;
   private static final int EASPARTS_ID = 8;
   private static final int EASPARTS_MESSAGE_ID = 150;
   private static final int EASSUMMARIES = 3;
   private static final int EASSUMMARY_ID = 4;
   private static final int EASTRACKING = 31;
   private static final int EASTRACKING_ID = 32;
   public static String EASsyncSchedulePeakOff;
   public static String EASsyncSchedulePeakOn;
   private static final int EMAIL_FOR_MERGE = 52;
   private static final int EMAIL_HISTORY = 51;
   public static final Uri EMAIL_HISTORY_FOR_MERGE_DOMAIN_URI = EMAIL_HISTORY_FOR_MERGE_URI.buildUpon().appendQueryParameter("style", "1").build();
   public static final Uri EMAIL_HISTORY_FOR_MERGE_NOT_DOMAIN_URI = EMAIL_HISTORY_FOR_MERGE_URI.buildUpon().appendQueryParameter("style", "0").build();
   public static final Uri EMAIL_HISTORY_FOR_MERGE_URI = Uri.parse("content://mail/emailformerge");
   public static final Uri EMAIL_HISTORY_URI = Uri.parse("content://mail/email_history");
   private static final int GLOBAL_SETTING_MAIL = 113;
   private static final int GROUPB_MESSAGE_IDS = 53;
   private static final int IMESSAGES = 9;
   private static final int IMESSAGE_ID = 10;
   private static final int INFOS = 20;
   private static final int INFO_ID = 21;
   private static final int IPARTS = 13;
   private static final int IPARTS_ID = 14;
   private static final int ISUMMARIES = 11;
   private static final int ISUMMARY_ID = 12;
   private static final int LAST_ACCOUNT_ENTER = 208;
   private static final int MAILBOXS = 24;
   private static final int MAILBOXS_ID = 44;
   private static final int MAILFLAG_ID = 40;
   private static final int MEETING_MSG = 202;
   private static final int MESSAGELISTFILTER_ID = 18;
   private static final int MESSAGELISTFILTER_OTHER_ID = 19;
   private static final int MESSAGELIST_ID = 17;
   private static final int MESSAGES = 1;
   private static final int MESSAGE_ID = 2;
   private static final int MESSAGE_IDS = 48;
   private static String MailApPath;
   public static final Uri MailFlag = Uri.parse("content://mail/MailFlag");
   private static final int NOTIFICATION = 42;
   private static final int NOTIFICATION_ID = 43;
   private static final int NO_NOTIFY_MESSAGES = 45;
   private static final int NO_NOTIFY_MESSAGES_ID = 46;
   private static final int PARTS = 7;
   private static final int PARTS_ID = 8;
   private static final int PARTS_MESSAGE_ID = 55;
   private static final int PARTS_MSGID = 50;
   private static final int PENDINGREQUESTS = 38;
   private static final int PEOPLE_DATA = 601;
   private static final int PEOPLE_GROUPS = 600;
   private static final int PEOPLE_MESSAGE_IDS = 54;
   private static final int PROVIDERS = 15;
   private static final int PROVIDERSETTINGS = 36;
   private static final int PROVIDERSETTING_ID = 37;
   private static final int PROVIDER_ID = 16;
   private static final String QUERY_FILTER_I_MESSAGE = "SELECT imessages._id AS _id,imessages._tag AS _tag,imessages._account AS _account,imessages._to AS _to,imessages._from AS _from,imessages._fromEmail AS _fromEmail,imessages._subject AS _subject,imessages._date AS _date,imessages._read AS _read,imessages._del AS _del FROM imessages WHERE ";
   private static final String QUERY_FILTER_MESSAGE = "SELECT messages._id AS _id,messages._tag AS _tag,messages._account AS _account,messages._to AS _to,messages._from AS _from,messages._fromEmail AS _fromEmail,messages._subject AS _subject,messages._date AS _date,messages._read AS _read,messages._del AS _del FROM messages WHERE ";
   private static final int SEARCH_SVR_MAIL = 203;
   private static final int SEARCH_SVR_MAIL_ID = 204;
   private static final int SEARCH_SVR_PARTS = 205;
   private static final int SEARCH_SVR_PARTS_MESSAGE_ID = 207;
   private static final int SEARCH_SVR_PART_ID = 206;
   private static final int SET_MESSAGE_READ_ID = 401;
   private static final int SET_MESSAGE_UNREAD_ID = 402;
   private static final int SLEEP_AFTER_YIELD_DELAY = 4000;
   private static final int SQLITECOMMAND = 105;
   private static final int SUGGESTIONS = 22;
   private static final int SUMMARIES = 3;
   private static final int SUMMARIES_CHILDREN_GROUPS_WITH_Mailbox = 354;
   private static final int SUMMARIES_CHILDREN_THREAD_WITH_Mailbox = 352;
   private static final int SUMMARIES_PARENT_GROUPS_WITH_Mailbox = 353;
   private static final int SUMMARIES_PARENT_THREAD_WITH_Mailbox = 351;
   private static final int SUMMARIES_WITH_Mailbox = 350;
   public static final int SUMMARIES_WITH_Mailbox_AllGroupMail = 368;
   public static final int SUMMARIES_WITH_Mailbox_GroupNumCount = 367;
   public static final int SUMMARIES_WITH_Mailbox_MarkStar = 366;
   public static final int SUMMARIES_WITH_Mailbox_Unread = 355;
   private static final int SUMMARY_ID = 4;
   private static final int SVRSUMMARIES = 209;
   private static final int SVRSUMMARIES_ID = 210;
   public static final Uri SqliteCommandURI = Uri.parse("content://mail/SqliteCommand");
   private static final String TAG = "MailProvider";
   private static final int VERIFY_ACCOUNT_ID = 200;
   public static final Uri accountWithUnread = Uri.parse("content://mail/accountWithUnread");
   public static final Uri allUnreadMail = Uri.parse("content://mail/allUnreadMail");
   public static String alwaysBccMyself;
   public static String askBeforeDelete;
   public static String deleteFromServer;
   public static String downloadMessageWhenScroll;
   public static String emailNotifications;
   public static String enableSDsave;
   public static String fetchMailDays;
   public static String fetchMailNum;
   public static String fetchMailType;
   public static String fontSize;
   public static final String glancePreview = "_glancePreview";
   public static final Uri globalSetting = Uri.parse("content://mail/globalSetting");
   private static ConditionVariable mChkEASSvcBound;
   private static IEASService mEASSvc;
   private static ServiceConnection mEASSvcConn;
   public static String peakDays;
   public static String peakTimeEnd;
   public static String peakTimeStart;
   public static String poll_frequency_number;
   public static String previewLinesNumber;
   public static String refreshMailWhenOpenFolder;
   public static String replyWithText;
   public static final String sAccountSegmentLowStorage = "lowStorage";
   public static final Uri sAccountsURI = Uri.parse("content://mail/accounts");
   public static final Uri sAllMessageWithAccountURI = Uri.parse("content://mail/allmessages_with_account");
   public static final String[] sBodyPreviewLimitColumns;
   private static final String sDatabaseName = "mail.db";
   public static final Uri sDecrypAccountsURI = Uri.parse("content://mail/decryptAccounts");
   public static final Uri sDeleteMessage = Uri.parse("content://mail/deleteMessage");
   public static final String[] sDeletePartColumns;
   public static final Uri sEASMailBoxURI = sMailBoxURI;
   public static final Uri sEASMessageIdsURI = Uri.parse("content://mail/easMessageIds");
   public static final Uri sEASMessagesURI = sMessagesURI;
   public static final Uri sEASPartsMessageURI = Uri.parse("content://mail/easpartsMessage");
   public static final Uri sEASPartsURI = sPartsURI;
   public static final Uri sEASTracking = Uri.parse("content://mail/easTracking");
   public static final Uri sEASaailFlag = Uri.parse("content://mail/easMailFlag");
   public static final Uri sFilterEmailAddressURI = Uri.parse("content://com.android.contacts/contacts/filter_emailaddress");
   public static final Uri sGroupMessageIdsURI = Uri.parse("content://mail/groupMessageIds");
   public static final Uri sInfosURI = Uri.parse("content://mail/infos");
   public static final String[] sLimitPartColumns;
   public static final String[] sLimitPartColumnsForCompose;
   public static final Uri sMailBoxURI = Uri.parse("content://mail/mailboxs");
   public static final long sMailbodySizeLimit = 819200L;
   public static final Uri sMeetingMsgURI = Uri.parse("content://mail/meetingMsg");
   public static final Uri sMessageIdsURI = Uri.parse("content://mail/messageIds");
   public static final Uri sMessageViewFilterOtherURI = Uri.parse("content://mail/messageviewfilterother");
   public static final Uri sMessageViewFilterURI = Uri.parse("content://mail/messageviewfilter");
   public static final Uri sMessageViewURI = Uri.parse("content://mail/messageview");
   public static final Uri sMessagesURI = Uri.parse("content://mail/messages");
   public static final Uri sNoNotifyMessagesURI = Uri.parse("content://mail/noNotifyMessages");
   public static final Uri sNotificationURI = Uri.parse("content://mail/notification");
   public static final Uri sPartsMessageURI = Uri.parse("content://mail/partsMsgId");
   public static final Uri sPartsURI = Uri.parse("content://mail/parts");
   public static final String sPatternMessagesWithMailboxUnread = "messages/account/#/mailbox/#/unread/";
   public static final String sPatternSummaryWithMailboxUnread = "summary/account/#/mailbox/#/unread/";
   public static final Uri sPendingRequestURI = Uri.parse("content://mail/pending_requests");
   public static final Uri sPeopleDataURI = Uri.parse("content://mail/people_data");
   public static final Uri sPeopleGroupURI = Uri.parse("content://mail/people_groups");
   public static final Uri sPeopleMessageIdsURI = Uri.parse("content://mail/peopleMessageIds");
   public static final Uri sProviderSettingsURI = Uri.parse("content://mail/providersettings");
   public static final Uri sProvidersURI = Uri.parse("content://mail/providers");
   public static final Uri sSearchSvrMessagesURI = Uri.parse("content://mail/searchSvrMessages");
   public static final Uri sSearchSvrPartsMessageURI = Uri.parse("content://mail/searchSvrPartsMessage");
   public static final Uri sSearchSvrPartsURI = Uri.parse("content://mail/searchSvrParts");
   public static final Uri sSetMessageStatus = Uri.parse("content://mail/setMessageStatus");
   public static final Uri sSummariesDeleteMailURI = Uri.parse("content://mail/cmd/deleteMail");
   public static final Uri sSummariesMarkStarURI = Uri.parse("content://mail/cmd/markStar");
   public static final Uri sSummariesMoveMailURI = Uri.parse("content://mail/cmd/moveMail");
   public static final Uri sSummariesReadURI = Uri.parse("content://mail/cmd/read");
   public static final Uri sSummariesURI = Uri.parse("content://mail/summary");
   public static int sSummaryAccountIdx;
   public static final String[] sSummaryColumns;
   public static final String[] sSummaryColumnsForGroups;
   public static final String[] sSummaryColumnsForOutbox;
   public static final String[] sSummaryColumnsForThread;
   public static final String[] sSummaryColumnsForThreadOutbox;
   public static int sSummaryDownloadtotalsizeIdx;
   public static final String sSummaryFilterAttach = "attach";
   public static final String sSummaryFilterDefault = "default";
   public static final String sSummaryFilterGroupChildren = "groupChildren";
   public static final String sSummaryFilterGroupNumCount = "groupNumCount";
   public static final String sSummaryFilterGroupParent = "groupParent";
   public static final String sSummaryFilterMarkStar = "markStar";
   public static final String sSummaryFilterMeeting = "meeting";
   public static final String sSummaryFilterThreadChildren = "threadChildren";
   public static final String sSummaryFilterThreadParent = "threadParent";
   public static final String sSummaryFilterUnread = "unread";
   public static int sSummaryFlagsIdx;
   public static int sSummaryFromIdx;
   public static int sSummaryGroupIdx;
   public static int sSummaryGroupsCountIdx;
   public static int sSummaryGroupsIdIdx;
   public static int sSummaryGroupsReadCountIdx;
   public static int sSummaryGroupsTitleIdx;
   public static int sSummaryIdIdx;
   public static int sSummaryImportanceIdx;
   public static int sSummaryIncAttachmentIdx;
   public static int sSummaryInternaldateIdx;
   public static int sSummaryMailActIdx;
   public static int sSummaryMailboxIdIdx;
   public static int sSummaryMessageClassIntIdx;
   public static int sSummaryMessagesizeIdx;
   public static int sSummaryOutAccountIdx;
   public static int sSummaryOutCcIdx;
   public static int sSummaryOutCcString;
   public static int sSummaryOutDownloadtotalsizeIdx;
   public static int sSummaryOutFlagsIdx;
   public static int sSummaryOutGroupIdx;
   public static int sSummaryOutIdIdx;
   public static int sSummaryOutImportanceIdx;
   public static int sSummaryOutIncAttachmentIdx;
   public static int sSummaryOutInternaldateIdx;
   public static int sSummaryOutMailActIdx;
   public static int sSummaryOutMailboxIdIdx;
   public static int sSummaryOutMessageClassIntIdx;
   public static int sSummaryOutMessagesizeIdx;
   public static int sSummaryOutPreviewIdx;
   public static int sSummaryOutReadIdx;
   public static int sSummaryOutSubjectIdx;
   public static int sSummaryOutSubjtypeIdx;
   public static int sSummaryOutToIdx;
   public static int sSummaryOutToStringIdx;
   public static int sSummaryPreviewIdx;
   public static int sSummaryReadIdx;
   public static int sSummarySubjectIdx;
   public static int sSummarySubjtypeIdx;
   public static int sSummaryThreadAccountIdIdx;
   public static int sSummaryThreadCountIdx;
   public static int sSummaryThreadGroupIdx;
   public static int sSummaryThreadIdIdx;
   public static int sSummaryThreadMailboxIdIdx;
   public static int sSummaryThreadMessageActIdx;
   public static int sSummaryThreadMessageAttachIdx;
   public static int sSummaryThreadMessageClassIdx;
   public static int sSummaryThreadMessageDateIdx;
   public static int sSummaryThreadMessageDownloadSizeIdx;
   public static int sSummaryThreadMessageFlagIdx;
   public static int sSummaryThreadMessageIdIdx;
   public static int sSummaryThreadMessageImpoIdx;
   public static int sSummaryThreadMessageMsgSizeIdx;
   public static int sSummaryThreadMessageReadIdx;
   public static int sSummaryThreadMessageSenderIdx;
   public static int sSummaryThreadMessageSubtypeIdx;
   public static int sSummaryThreadOutboxAccountIdIdx;
   public static int sSummaryThreadOutboxCountIdx;
   public static int sSummaryThreadOutboxGroupIdx;
   public static int sSummaryThreadOutboxIdIdx;
   public static int sSummaryThreadOutboxMailboxIdIdx;
   public static int sSummaryThreadOutboxMessageActIdx;
   public static int sSummaryThreadOutboxMessageAttachIdx;
   public static int sSummaryThreadOutboxMessageCcIdx;
   public static int sSummaryThreadOutboxMessageCcStringIdx;
   public static int sSummaryThreadOutboxMessageClassIdx;
   public static int sSummaryThreadOutboxMessageDateIdx;
   public static int sSummaryThreadOutboxMessageDownloadSizeIdx;
   public static int sSummaryThreadOutboxMessageFlagIdx;
   public static int sSummaryThreadOutboxMessageIdIdx;
   public static int sSummaryThreadOutboxMessageImpoIdx;
   public static int sSummaryThreadOutboxMessageMsgSizeIdx;
   public static int sSummaryThreadOutboxMessageReadIdx;
   public static int sSummaryThreadOutboxMessageSenderIdx;
   public static int sSummaryThreadOutboxMessageSubtypeIdx;
   public static int sSummaryThreadOutboxMessageToIdx;
   public static int sSummaryThreadOutboxMessageToStringIdx;
   public static int sSummaryThreadOutboxPreviewIdx;
   public static int sSummaryThreadOutboxReadCountIdx;
   public static int sSummaryThreadOutboxSubjectIdx;
   public static int sSummaryThreadPreviewIdx;
   public static int sSummaryThreadReadCountIdx;
   public static int sSummaryThreadSubjectIdx;
   public static final Uri sSvrSummariesURI = Uri.parse("content://mail/searchSvrMessages/svrsummay");
   static MailProvider sTheOne;
   public static final UriMatcher sURLMatcher;
   public static String signature;
   public static String sizelimit;
   public static String smtpauth;
   public static String sound;
   public static String syncSchedulePeakOff;
   public static String syncSchedulePeakOn;
   public static String syncWithServer;
   public static String useSignature;
   public static String vibrate;
   private Toast ToastMsg_StorageFull = null;
   private final ThreadLocal<ArrayList<Runnable>> mAfterCommitRunnable;
   private final ThreadLocal<Boolean> mApplyingBatch;
   private BackupManager mBackupManager;
   private MailProvider.DatabaseWrapper mDbWrapper;
   private final ThreadLocal<Set<Uri>> mNotifiedUriSets;


   static {
      String[] var0 = new String[]{"_id", "ifnull(_from, _fromEmail) as sender", "substr(_subject, 0, 85) as _subject", "_mailboxId", "_internaldate", "_flags", "_read", "_downloadtotalsize", "_messagesize", "_account", "_incAttachment", "_group", "_mailAct", "_subjtype", "_importance", "_messageClassInt", "_preview"};
      sSummaryColumns = var0;
      sSummaryIdIdx = 0;
      sSummaryFromIdx = 1;
      sSummarySubjectIdx = 2;
      sSummaryMailboxIdIdx = 3;
      sSummaryInternaldateIdx = 4;
      sSummaryFlagsIdx = 5;
      sSummaryReadIdx = 6;
      sSummaryDownloadtotalsizeIdx = 7;
      sSummaryMessagesizeIdx = 8;
      sSummaryAccountIdx = 9;
      sSummaryIncAttachmentIdx = 10;
      sSummaryGroupIdx = 11;
      sSummaryMailActIdx = 12;
      sSummarySubjtypeIdx = 13;
      sSummaryImportanceIdx = 14;
      sSummaryMessageClassIntIdx = 15;
      sSummaryPreviewIdx = 16;
      String[] var1 = new String[]{"_id", "substr(_subject, 0, 85) as _subject", "_mailboxId", "_internaldate", "_flags", "_read", "_downloadtotalsize", "_messagesize", "_account", "_incAttachment", "_group", "_mailAct", "_subjtype", "_importance", "_messageClassInt", "_to", "_toString", "_cc", "_ccString", "_preview"};
      sSummaryColumnsForOutbox = var1;
      sSummaryOutIdIdx = 0;
      sSummaryOutSubjectIdx = 1;
      sSummaryOutMailboxIdIdx = 2;
      sSummaryOutInternaldateIdx = 3;
      sSummaryOutFlagsIdx = 4;
      sSummaryOutReadIdx = 5;
      sSummaryOutDownloadtotalsizeIdx = 6;
      sSummaryOutMessagesizeIdx = 7;
      sSummaryOutAccountIdx = 8;
      sSummaryOutIncAttachmentIdx = 9;
      sSummaryOutGroupIdx = 10;
      sSummaryOutMailActIdx = 11;
      sSummaryOutSubjtypeIdx = 12;
      sSummaryOutImportanceIdx = 13;
      sSummaryOutMessageClassIntIdx = 14;
      sSummaryOutToIdx = 15;
      sSummaryOutToStringIdx = 16;
      sSummaryOutCcIdx = 17;
      sSummaryOutCcString = 18;
      sSummaryOutPreviewIdx = 19;
      String[] var2 = new String[]{"_groupPseudo as _id", "_subject", "count(_id) as count", "_group", "sum(_read) as readcount", "_mailboxId", "_account", "_id as _messageId", "ifnull(_from, _fromEmail) as sender", "_internaldate", "_flags", "_read", "_downloadtotalsize", "_messagesize", "_incAttachment", "_mailAct", "_subjtype", "_importance", "_messageClassInt", "_preview"};
      sSummaryColumnsForThread = var2;
      sSummaryThreadIdIdx = 0;
      sSummaryThreadSubjectIdx = 1;
      sSummaryThreadCountIdx = 2;
      sSummaryThreadGroupIdx = 3;
      sSummaryThreadReadCountIdx = 4;
      sSummaryThreadMailboxIdIdx = 5;
      sSummaryThreadAccountIdIdx = 6;
      sSummaryThreadMessageIdIdx = 7;
      sSummaryThreadMessageSenderIdx = 8;
      sSummaryThreadMessageDateIdx = 9;
      sSummaryThreadMessageFlagIdx = 10;
      sSummaryThreadMessageReadIdx = 11;
      sSummaryThreadMessageDownloadSizeIdx = 12;
      sSummaryThreadMessageMsgSizeIdx = 13;
      sSummaryThreadMessageAttachIdx = 14;
      sSummaryThreadMessageActIdx = 15;
      sSummaryThreadMessageSubtypeIdx = 16;
      sSummaryThreadMessageImpoIdx = 17;
      sSummaryThreadMessageClassIdx = 18;
      sSummaryThreadPreviewIdx = 19;
      String[] var3 = new String[]{"_groupPseudo as _id", "_subject", "count(_id) as count", "_group", "sum(_read) as readcount", "_mailboxId", "_account", "_id as _messageId", "ifnull(_from, _fromEmail) as sender", "_internaldate", "_flags", "_read", "_downloadtotalsize", "_messagesize", "_incAttachment", "_mailAct", "_subjtype", "_importance", "_messageClassInt", "_to", "_toString", "_cc", "_ccString", "_preview"};
      sSummaryColumnsForThreadOutbox = var3;
      sSummaryThreadOutboxIdIdx = 0;
      sSummaryThreadOutboxSubjectIdx = 1;
      sSummaryThreadOutboxCountIdx = 2;
      sSummaryThreadOutboxGroupIdx = 3;
      sSummaryThreadOutboxReadCountIdx = 4;
      sSummaryThreadOutboxMailboxIdIdx = 5;
      sSummaryThreadOutboxAccountIdIdx = 6;
      sSummaryThreadOutboxMessageIdIdx = 7;
      sSummaryThreadOutboxMessageSenderIdx = 8;
      sSummaryThreadOutboxMessageDateIdx = 9;
      sSummaryThreadOutboxMessageFlagIdx = 10;
      sSummaryThreadOutboxMessageReadIdx = 11;
      sSummaryThreadOutboxMessageDownloadSizeIdx = 12;
      sSummaryThreadOutboxMessageMsgSizeIdx = 13;
      sSummaryThreadOutboxMessageAttachIdx = 14;
      sSummaryThreadOutboxMessageActIdx = 15;
      sSummaryThreadOutboxMessageSubtypeIdx = 16;
      sSummaryThreadOutboxMessageImpoIdx = 17;
      sSummaryThreadOutboxMessageClassIdx = 18;
      sSummaryThreadOutboxMessageToIdx = 19;
      sSummaryThreadOutboxMessageToStringIdx = 20;
      sSummaryThreadOutboxMessageCcIdx = 21;
      sSummaryThreadOutboxMessageCcStringIdx = 22;
      sSummaryThreadOutboxPreviewIdx = 23;
      String[] var4 = new String[]{"people_db.groups._id as _id", "people_db.groups.title as title", "count(messages._id) as count", "sum(messages._read) as readcount"};
      sSummaryColumnsForGroups = var4;
      sSummaryGroupsIdIdx = 0;
      sSummaryGroupsTitleIdx = 1;
      sSummaryGroupsCountIdx = 2;
      sSummaryGroupsReadCountIdx = 3;
      String[] var5 = new String[]{"_id", "_mimetype", "_nativeType", "_filereference", "_contentid", "_filename", "_filepath", "SUBSTR(_text, 1, 819200) as _text", "_meetingMailBody", "_uuid", "_cid", "_filesize", "_contenttype", "_inline", "_encode", "_index", "_charset", "_message", "_account", "_flags"};
      sLimitPartColumns = var5;
      String[] var6 = new String[]{"_id", "_mimetype", "_filereference", "_filename", "_filepath", "SUBSTR(_text, 1, 819200) as _text", "_uuid", "_cid", "_filesize", "_contenttype"};
      sLimitPartColumnsForCompose = var6;
      String[] var7 = new String[]{"_id", "_filename", "_mimetype", "_filepath", "_message"};
      sDeletePartColumns = var7;
      String[] var8 = new String[]{"_message", "SUBSTR(_text, 1, 2000) as _text", "_mimetype"};
      sBodyPreviewLimitColumns = var8;
      sURLMatcher = new UriMatcher(-1);
      sURLMatcher.addURI("mail", "messages", 1);
      sURLMatcher.addURI("mail", "meetingMsg", 202);
      sURLMatcher.addURI("mail", "noNotifyMessages", 45);
      sURLMatcher.addURI("mail", "messages/#", 2);
      sURLMatcher.addURI("mail", "noNotifyMessages/#", 46);
      sURLMatcher.addURI("mail", "summary", 3);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#", 350);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#", 350);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/default/", 350);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/default/", 350);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/unread/", 355);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/unread/", 355);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/markStar/", 366);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/markStar/", 366);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/meeting/", 350);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/meeting/", 350);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/attach/", 350);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/attach/", 350);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/threadParent/", 351);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/threadParent/", 351);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/threadChildren/", 352);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/threadChildren/", 352);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/groupsParent/", 353);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/groupsParent/", 353);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/groupsChildren", 354);
      sURLMatcher.addURI("mail", "messages/account/#/mailbox/#/groupsChildren", 354);
      sURLMatcher.addURI("mail", "summary/account/#/mailbox/#/groupNumCount", 367);
      sURLMatcher.addURI("mail", "cmd/read/", 300);
      sURLMatcher.addURI("mail", "cmd/read/#", 301);
      sURLMatcher.addURI("mail", "cmd/markStar/", 302);
      sURLMatcher.addURI("mail", "cmd/markStar/#", 303);
      sURLMatcher.addURI("mail", "cmd/moveMail", 304);
      sURLMatcher.addURI("mail", "cmd/moveMail/#", 305);
      sURLMatcher.addURI("mail", "cmd/deleteMail", 306);
      sURLMatcher.addURI("mail", "cmd/deleteMail/#", 307);
      sURLMatcher.addURI("mail", "accounts", 5);
      sURLMatcher.addURI("mail", "accounts/lowStorage", 5);
      sURLMatcher.addURI("mail", "accounts/#", 6);
      sURLMatcher.addURI("mail", "decryptAccounts", 502);
      sURLMatcher.addURI("mail", "parts", 7);
      sURLMatcher.addURI("mail", "partsMsgId/#", 50);
      sURLMatcher.addURI("mail", "parts/#", 8);
      sURLMatcher.addURI("mail", "parts/#/message/#", 55);
      sURLMatcher.addURI("mail", "providers/", 15);
      sURLMatcher.addURI("mail", "providers/#", 16);
      sURLMatcher.addURI("mail", "messageview", 17);
      sURLMatcher.addURI("mail", "messageviewfilter", 18);
      sURLMatcher.addURI("mail", "messageviewfilterother", 19);
      sURLMatcher.addURI("mail", "suggestion/search_suggest_query", 22);
      sURLMatcher.addURI("mail", "mailboxs", 24);
      sURLMatcher.addURI("mail", "mailboxs/#", 44);
      sURLMatcher.addURI("mail", "easpartsMessage/#", 150);
      sURLMatcher.addURI("mail", "easTracking", 31);
      sURLMatcher.addURI("mail", "easTracking/#", 32);
      sURLMatcher.addURI("mail", "partsMessage/#", 150);
      sURLMatcher.addURI("mail", "easMailFlag/#", 35);
      sURLMatcher.addURI("mail", "deleteMessage/#", 400);
      sURLMatcher.addURI("mail", "setMessageStatus/read/#", 401);
      sURLMatcher.addURI("mail", "setMessageStatus/unread/#", 402);
      sURLMatcher.addURI("mail", "providersettings/", 36);
      sURLMatcher.addURI("mail", "providersettings/#", 37);
      sURLMatcher.addURI("mail", "pending_requests", 38);
      sURLMatcher.addURI("mail", "notification", 42);
      sURLMatcher.addURI("mail", "notification/#", 43);
      sURLMatcher.addURI("mail", "allmessages_with_account", 100);
      sURLMatcher.addURI("mail", "allmessagesCountRead", 102);
      sURLMatcher.addURI("mail", "allmessagesCountUnread", 103);
      sURLMatcher.addURI("mail", "MailFlag/#", 40);
      sURLMatcher.addURI("mail", "AccountVerify/#", 200);
      sURLMatcher.addURI("mail", "SqliteCommand", 105);
      sURLMatcher.addURI("mail", "allUnreadMail", 112);
      sURLMatcher.addURI("mail", "globalSetting", 113);
      sURLMatcher.addURI("mail", "accountWithUnread", 501);
      sURLMatcher.addURI("mail", "messageIds", 48);
      sURLMatcher.addURI("mail", "groupMessageIds", 53);
      sURLMatcher.addURI("mail", "peopleMessageIds", 54);
      sURLMatcher.addURI("mail", "easMessageIds", 49);
      sURLMatcher.addURI("mail", "email_history", 51);
      sURLMatcher.addURI("mail", "emailformerge", 52);
      sURLMatcher.addURI("mail", "searchSvrMessages", 203);
      sURLMatcher.addURI("mail", "searchSvrMessages/#", 204);
      sURLMatcher.addURI("mail", "searchSvrParts", 205);
      sURLMatcher.addURI("mail", "searchSvrParts/#", 206);
      sURLMatcher.addURI("mail", "searchSvrPartsMessage/#", 207);
      sURLMatcher.addURI("mail", "searchSvrMessages/svrsummay", 209);
      sURLMatcher.addURI("mail", "searchSvrMessages/svrsummay/#", 210);
      sURLMatcher.addURI("mail", "people_groups", 600);
      sURLMatcher.addURI("mail", "people_data", 601);
      DEBUG = Mail.MAIL_DEBUG;
      replyWithText = "1";
      useSignature = "1";
      signature = "";
      sizelimit = "4";
      poll_frequency_number = "3";
      fetchMailType = "1";
      fetchMailNum = "0";
      fetchMailDays = "1";
      fontSize = "2";
      deleteFromServer = "0";
      askBeforeDelete = "0";
      alwaysBccMyself = "0";
      enableSDsave = "1";
      emailNotifications = "1";
      sound = "0";
      vibrate = "0";
      smtpauth = "1";
      refreshMailWhenOpenFolder = "1";
      previewLinesNumber = "2";
      peakDays = "31";
      syncSchedulePeakOn = "3";
      syncSchedulePeakOff = "5";
      EASsyncSchedulePeakOn = "4";
      EASsyncSchedulePeakOff = "6";
      peakTimeStart = "480";
      peakTimeEnd = "1200";
      downloadMessageWhenScroll = "1";
      syncWithServer = "0";
      MailApPath = null;
      mChkEASSvcBound = null;
      mEASSvc = null;
      mEASSvcConn = null;
   }

   public MailProvider() {
      ThreadLocal var1 = new ThreadLocal();
      this.mApplyingBatch = var1;
      ThreadLocal var2 = new ThreadLocal();
      this.mNotifiedUriSets = var2;
      ThreadLocal var3 = new ThreadLocal();
      this.mAfterCommitRunnable = var3;
   }

   public static final boolean IsAccountExisted(String param0) {
      // $FF: Couldn't be decompiled
   }

   private ContentValues addSlash(ContentValues var1, String var2) {
      String var3 = (String)var1.get(var2);
      if(var3 != null) {
         var1.remove(var2);
         String var4 = var3.replaceAll("\\\\", "\\\\\\\\");
         var1.put(var2, var4);
      }

      return var1;
   }

   public static void addSyncAccount(Context var0, long var1, String var3, String var4, int var5, int var6, int var7) {
      if(var5 != null) {
         if(var5 != null) {
            Bundle var8 = new Bundle();
            var8.putString("no_new_task", "true");
            android.accounts.Account var9 = new android.accounts.Account(var3, "com.htc.android.mail");
            AccountManager.get(var0).addAccountExplicitly(var9, var4, var8);
            ContentResolver.setSyncAutomatically(var9, "mail", (boolean)1);
            ContentResolver.setIsSyncable(var9, "mail", 1);
            Intent var20 = new Intent("android.intent.action.MAIN");
            Uri var11 = Uri.parse("content://mail/accounts/" + var1);
            var20.setData(var11);
            ComponentName var13 = new ComponentName("com.htc.android.mail", "com.htc.android.mail.easclient.PeakTimeSetting");
            var20.setComponent(var13);
            int var15;
            if(var6 == 0) {
               var15 = -1;
            } else {
               var15 = Account.getPollValue(var6) * 60;
            }

            byte var16;
            if(var7 > 0) {
               var16 = 1;
            } else {
               var16 = 0;
            }

            String var17 = var20.toUri(0);
            SyncSettingUtil.addSyncSetting(var0, "com.htc.android.mail", var3, var16, var15, -1, var17, "com.htc.android.mail", (boolean)1);
         }
      }
   }

   public static final void createAccountTable(SQLiteDatabase var0) {
      StringBuilder var1 = (new StringBuilder()).append("CREATE TABLE IF NOT EXISTS accounts (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_name TEXT NOT NULL,_emailaddress TEXT NOT NULL,_username TEXT NOT NULL,_outusername TEXT NOT NULL,_password TEXT NOT NULL,_outpassword TEXT NOT NULL,_desc TEXT NOT NULL,_protocol INTEGER DEFAULT 0,_inserver TEXT,_inport INTEGER,_outserver TEXT,_outport INTEGER,_useSSLin INTEGER DEFAULT 1,_useSSLout INTEGER DEFAULT 1,_easDomain TEXT,_easSvrProtocol TEXT DEFAULT \'Unknown\',_easHeartBeatInternal INTEGER DEFAULT -1,_easDeviceID TEXT,_easDeviceType TEXT,_useSignature INTEGER DEFAULT ");
      String var2 = useSignature;
      StringBuilder var3 = var1.append(var2).append(",").append("_sizelimit INTEGER DEFAULT ");
      String var4 = sizelimit;
      StringBuilder var5 = var3.append(var4).append(",").append("_poll_frequency_number INTEGER DEFAULT ");
      String var6 = poll_frequency_number;
      StringBuilder var7 = var5.append(var6).append(",").append("_fetchMailType INTEGER DEFAULT 1,").append("_fetchMailNum INTEGER DEFAULT ");
      String var8 = fetchMailNum;
      StringBuilder var9 = var7.append(var8).append(",").append("_fetchMailDays INTEGER DEFAULT ");
      String var10 = fetchMailDays;
      StringBuilder var11 = var9.append(var10).append(",").append("_previewLinesNumber INTEGER DEFAULT 2,").append("_fontSize INTEGER DEFAULT ");
      String var12 = fontSize;
      StringBuilder var13 = var11.append(var12).append(",").append("_deleteFromServer INTEGER DEFAULT ");
      String var14 = deleteFromServer;
      StringBuilder var15 = var13.append(var14).append(",").append("_alwaysBccMyself INTEGER DEFAULT ");
      String var16 = alwaysBccMyself;
      StringBuilder var17 = var15.append(var16).append(",").append("_askBeforeDelete INTEGER DEFAULT ");
      String var18 = askBeforeDelete;
      StringBuilder var19 = var17.append(var18).append(",").append("_enableSDsave INTEGER DEFAULT ");
      String var20 = enableSDsave;
      StringBuilder var21 = var19.append(var20).append(",").append("_smtpauth INTEGER DEFAULT ");
      String var22 = smtpauth;
      StringBuilder var23 = var21.append(var22).append(",").append("_del INTEGER DEFAULT -1,").append("_signature TEXT ,").append("_nextfetchtime INTEGER,").append("_lastupdatetime INTEGER,").append("_emailnotifications INTEGER DEFAULT ");
      String var24 = emailNotifications;
      StringBuilder var25 = var23.append(var24).append(",").append("_vibrate INTEGER DEFAULT ");
      String var26 = vibrate;
      StringBuilder var27 = var25.append(var26).append(",").append("_sound INTEGER DEFAULT ");
      String var28 = sound;
      StringBuilder var29 = var27.append(var28).append(",").append("_provider TEXT DEFAULT \'\',").append("_providerid INTEGER DEFAULT 0,").append("_replyWithText INTEGER  DEFAULT ");
      String var30 = replyWithText;
      StringBuilder var31 = var29.append(var30).append(",").append("_refreshMailWhenOpenFolder INTEGER  DEFAULT ");
      String var32 = refreshMailWhenOpenFolder;
      StringBuilder var33 = var31.append(var32).append(",").append("_defaultaccount INTEGER  DEFAULT 0,").append("_defaultfolderId INTEGER,").append("_trashfolder TEXT DEFAULT \'Trash\',").append("_trashfoldertext TEXT DEFAULT \'Trash\',").append("_trashfolderId INTEGER,").append("_sentfolder TEXT DEFAULT \'Sent\',").append("_sentfoldertext TEXT DEFAULT \'Sent\',").append("_sentfolderId INTEGER,").append("_draftfolder TEXT DEFAULT \'Drafts\',").append("_draftfoldertext TEXT DEFAULT \'Drafts\',").append("_draftfolderId INTEGER,").append("_outfolderId INTEGER,").append("_flags INTEGER,").append("_sortby INTEGER DEFAULT 0,").append("_initalscale INTEGER DEFAULT 0,").append("_peakdays INTEGER DEFAULT ");
      String var34 = peakDays;
      StringBuilder var35 = var33.append(var34).append(",").append("_peaktimestart INTEGER DEFAULT ");
      String var36 = peakTimeStart;
      StringBuilder var37 = var35.append(var36).append(",").append("_peaktimeend INTEGER DEFAULT ");
      String var38 = peakTimeEnd;
      StringBuilder var39 = var37.append(var38).append(",").append("_peakonfrequency INTEGER DEFAULT ");
      String var40 = syncSchedulePeakOn;
      StringBuilder var41 = var39.append(var40).append(",").append("_peakofffrequency INTEGER DEFAULT ");
      String var42 = syncSchedulePeakOff;
      String var43 = var41.append(var42).append(",").append("AccountVerify INTEGER DEFAULT 0,").append("_contactGroup TEXT,").append("_colorIdx INTEGER DEFAULT 0,").append("_downloadMessageWhenScroll INTEGER DEFAULT 1,").append("_syncWithServer INTEGER DEFAULT 0,").append("_providerGroup TEXT DEFAULT NULL").append(");").toString();
      var0.execSQL(var43);
      var0.execSQL("CREATE TRIGGER IF NOT EXISTS delete_accounts BEFORE DELETE ON accounts BEGIN DELETE FROM mailboxs WHERE old._id = _account; DELETE FROM pending_requests WHERE old._id = _accountId; DELETE FROM notification WHERE old._id = _accountid; END;");
   }

   public static final void createAccountTable_v100(SQLiteDatabase var0) {
      StringBuilder var1 = (new StringBuilder()).append("CREATE TABLE IF NOT EXISTS accounts (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_name TEXT NOT NULL,_emailaddress TEXT NOT NULL,_username TEXT NOT NULL,_outusername TEXT NOT NULL,_password TEXT NOT NULL,_outpassword TEXT NOT NULL,_desc TEXT NOT NULL,_protocol INTEGER DEFAULT 0,_inserver TEXT,_inport INTEGER,_outserver TEXT,_outport INTEGER,_useSSLin INTEGER DEFAULT 1,_useSSLout INTEGER DEFAULT 1,_easDomain TEXT,_easSvrProtocol TEXT DEFAULT \'Unknown\',_easHeartBeatInternal INTEGER DEFAULT -1,_easDeviceID TEXT,_easDeviceType TEXT,_useSignature INTEGER DEFAULT ");
      String var2 = useSignature;
      StringBuilder var3 = var1.append(var2).append(",").append("_sizelimit INTEGER DEFAULT ");
      String var4 = sizelimit;
      StringBuilder var5 = var3.append(var4).append(",").append("_poll_frequency_number INTEGER DEFAULT ");
      String var6 = poll_frequency_number;
      StringBuilder var7 = var5.append(var6).append(",").append("_fetchMailType INTEGER DEFAULT 1,").append("_fetchMailNum INTEGER DEFAULT ");
      String var8 = fetchMailNum;
      StringBuilder var9 = var7.append(var8).append(",").append("_fetchMailDays INTEGER DEFAULT ");
      String var10 = fetchMailDays;
      StringBuilder var11 = var9.append(var10).append(",").append("_previewLinesNumber INTEGER DEFAULT 2,").append("_fontSize INTEGER DEFAULT ");
      String var12 = fontSize;
      StringBuilder var13 = var11.append(var12).append(",").append("_deleteFromServer INTEGER DEFAULT ");
      String var14 = deleteFromServer;
      StringBuilder var15 = var13.append(var14).append(",").append("_alwaysBccMyself INTEGER DEFAULT ");
      String var16 = alwaysBccMyself;
      StringBuilder var17 = var15.append(var16).append(",").append("_askBeforeDelete INTEGER DEFAULT ");
      String var18 = askBeforeDelete;
      StringBuilder var19 = var17.append(var18).append(",").append("_enableSDsave INTEGER DEFAULT ");
      String var20 = enableSDsave;
      StringBuilder var21 = var19.append(var20).append(",").append("_smtpauth INTEGER DEFAULT ");
      String var22 = smtpauth;
      StringBuilder var23 = var21.append(var22).append(",").append("_del INTEGER DEFAULT -1,").append("_signature TEXT ,").append("_nextfetchtime INTEGER,").append("_lastupdatetime INTEGER,").append("_emailnotifications INTEGER DEFAULT ");
      String var24 = emailNotifications;
      StringBuilder var25 = var23.append(var24).append(",").append("_vibrate INTEGER DEFAULT ");
      String var26 = vibrate;
      StringBuilder var27 = var25.append(var26).append(",").append("_sound INTEGER DEFAULT ");
      String var28 = sound;
      StringBuilder var29 = var27.append(var28).append(",").append("_provider TEXT DEFAULT \'\',").append("_providerid INTEGER DEFAULT 0,").append("_replyWithText INTEGER  DEFAULT ");
      String var30 = replyWithText;
      StringBuilder var31 = var29.append(var30).append(",").append("_refreshMailWhenOpenFolder INTEGER  DEFAULT ");
      String var32 = refreshMailWhenOpenFolder;
      String var33 = var31.append(var32).append(",").append("_defaultaccount INTEGER  DEFAULT 0,").append("_defaultfolderId INTEGER,").append("_trashfolder TEXT DEFAULT \'Trash\',").append("_trashfoldertext TEXT DEFAULT \'Trash\',").append("_trashfolderId INTEGER,").append("_sentfolder TEXT DEFAULT \'Sent\',").append("_sentfoldertext TEXT DEFAULT \'Sent\',").append("_sentfolderId INTEGER,").append("_draftfolder TEXT DEFAULT \'Drafts\',").append("_draftfoldertext TEXT DEFAULT \'Drafts\',").append("_draftfolderId INTEGER,").append("_outfolderId INTEGER,").append("_flags INTEGER,").append("_sortby INTEGER DEFAULT 0,").append("_initalscale INTEGER DEFAULT 0,").append("AccountVerify INTEGER DEFAULT 0);").toString();
      var0.execSQL(var33);
      var0.execSQL("CREATE TRIGGER IF NOT EXISTS delete_accounts BEFORE DELETE ON accounts BEGIN DELETE FROM mailboxs WHERE old._id = _account; DELETE FROM pending_requests WHERE old._id = _accountId; DELETE FROM notification WHERE old._id = _accountid; END;");
   }

   public static final void createAccountTable_v101(SQLiteDatabase var0) {
      StringBuilder var1 = (new StringBuilder()).append("CREATE TABLE IF NOT EXISTS accounts (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_name TEXT NOT NULL,_emailaddress TEXT NOT NULL,_username TEXT NOT NULL,_outusername TEXT NOT NULL,_password TEXT NOT NULL,_outpassword TEXT NOT NULL,_desc TEXT NOT NULL,_protocol INTEGER DEFAULT 0,_inserver TEXT,_inport INTEGER,_outserver TEXT,_outport INTEGER,_useSSLin INTEGER DEFAULT 1,_useSSLout INTEGER DEFAULT 1,_easDomain TEXT,_easSvrProtocol TEXT DEFAULT \'Unknown\',_easHeartBeatInternal INTEGER DEFAULT -1,_easDeviceID TEXT,_easDeviceType TEXT,_useSignature INTEGER DEFAULT ");
      String var2 = useSignature;
      StringBuilder var3 = var1.append(var2).append(",").append("_sizelimit INTEGER DEFAULT ");
      String var4 = sizelimit;
      StringBuilder var5 = var3.append(var4).append(",").append("_poll_frequency_number INTEGER DEFAULT ");
      String var6 = poll_frequency_number;
      StringBuilder var7 = var5.append(var6).append(",").append("_fetchMailType INTEGER DEFAULT 1,").append("_fetchMailNum INTEGER DEFAULT ");
      String var8 = fetchMailNum;
      StringBuilder var9 = var7.append(var8).append(",").append("_fetchMailDays INTEGER DEFAULT ");
      String var10 = fetchMailDays;
      StringBuilder var11 = var9.append(var10).append(",").append("_previewLinesNumber INTEGER DEFAULT 2,").append("_fontSize INTEGER DEFAULT ");
      String var12 = fontSize;
      StringBuilder var13 = var11.append(var12).append(",").append("_deleteFromServer INTEGER DEFAULT ");
      String var14 = deleteFromServer;
      StringBuilder var15 = var13.append(var14).append(",").append("_alwaysBccMyself INTEGER DEFAULT ");
      String var16 = alwaysBccMyself;
      StringBuilder var17 = var15.append(var16).append(",").append("_askBeforeDelete INTEGER DEFAULT ");
      String var18 = askBeforeDelete;
      StringBuilder var19 = var17.append(var18).append(",").append("_enableSDsave INTEGER DEFAULT ");
      String var20 = enableSDsave;
      StringBuilder var21 = var19.append(var20).append(",").append("_smtpauth INTEGER DEFAULT ");
      String var22 = smtpauth;
      StringBuilder var23 = var21.append(var22).append(",").append("_del INTEGER DEFAULT -1,").append("_signature TEXT ,").append("_nextfetchtime INTEGER,").append("_lastupdatetime INTEGER,").append("_emailnotifications INTEGER DEFAULT ");
      String var24 = emailNotifications;
      StringBuilder var25 = var23.append(var24).append(",").append("_vibrate INTEGER DEFAULT ");
      String var26 = vibrate;
      StringBuilder var27 = var25.append(var26).append(",").append("_sound INTEGER DEFAULT ");
      String var28 = sound;
      StringBuilder var29 = var27.append(var28).append(",").append("_provider TEXT DEFAULT \'\',").append("_providerid INTEGER DEFAULT 0,").append("_replyWithText INTEGER  DEFAULT ");
      String var30 = replyWithText;
      StringBuilder var31 = var29.append(var30).append(",").append("_refreshMailWhenOpenFolder INTEGER  DEFAULT ");
      String var32 = refreshMailWhenOpenFolder;
      StringBuilder var33 = var31.append(var32).append(",").append("_defaultaccount INTEGER  DEFAULT 0,").append("_defaultfolderId INTEGER,").append("_trashfolder TEXT DEFAULT \'Trash\',").append("_trashfoldertext TEXT DEFAULT \'Trash\',").append("_trashfolderId INTEGER,").append("_sentfolder TEXT DEFAULT \'Sent\',").append("_sentfoldertext TEXT DEFAULT \'Sent\',").append("_sentfolderId INTEGER,").append("_draftfolder TEXT DEFAULT \'Drafts\',").append("_draftfoldertext TEXT DEFAULT \'Drafts\',").append("_draftfolderId INTEGER,").append("_outfolderId INTEGER,").append("_flags INTEGER,").append("_sortby INTEGER DEFAULT 0,").append("_initalscale INTEGER DEFAULT 0,").append("_peakdays INTEGER DEFAULT ");
      String var34 = peakDays;
      StringBuilder var35 = var33.append(var34).append(",").append("_peaktimestart INTEGER DEFAULT ");
      String var36 = peakTimeStart;
      StringBuilder var37 = var35.append(var36).append(",").append("_peaktimeend INTEGER DEFAULT ");
      String var38 = peakTimeEnd;
      StringBuilder var39 = var37.append(var38).append(",").append("_peakonfrequency INTEGER DEFAULT ");
      String var40 = syncSchedulePeakOn;
      StringBuilder var41 = var39.append(var40).append(",").append("_peakofffrequency INTEGER DEFAULT ");
      String var42 = syncSchedulePeakOff;
      String var43 = var41.append(var42).append(",").append("AccountVerify INTEGER DEFAULT 0);").toString();
      var0.execSQL(var43);
      var0.execSQL("CREATE TRIGGER IF NOT EXISTS delete_accounts BEFORE DELETE ON accounts BEGIN DELETE FROM mailboxs WHERE old._id = _account; DELETE FROM pending_requests WHERE old._id = _accountId; DELETE FROM notification WHERE old._id = _accountid; END;");
   }

   public static final void createGlobalTable(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE globalSetting (_id INTEGER NOT NULL PRIMARY KEY,_glancePreview INTEGER DEFAULT 0);");
   }

   public static final void createMailBoxTable(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS mailboxs (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_undecodename TEXT DEFAULT \'\',_decodename TEXT DEFAULT \'\',_shortname TEXT DEFAULT \'\',_serverfolder INTEGER DEFAULT 1,_defaultfolder INTEGER DEFAULT 0,_account INTEGER NOT NULL,_movegroup INTEGER DEFAULT 1,_showsender INTEGER DEFAULT 1,_lastuid TEXT DEFAULT \'\',_existsize INTEGER DEFAULT 0,_noselect INTEGER DEFAULT 0,_haschild INTEGER DEFAULT 0,_serverid INTEGER DEFAULT 0,_parentid INTEGER DEFAULT 0,_displayname TEXT,_type INTEGER,_synckey TEXT DEFAULT 0,_enablesync INTEGER DEFAULT 1,_default_sync INTEGER DEFAULT 0);");
      var0.execSQL("CREATE INDEX IF NOT EXISTS IDX_mailboxs_accountId ON mailboxs (_account);");
      var0.execSQL("CREATE TRIGGER IF NOT EXISTS delete_mailboxs2 BEFORE DELETE ON mailboxs BEGIN DELETE FROM messages WHERE old._id = _mailboxId; END;");
   }

   public static final void createMailBoxTable_v100(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS mailboxs (_id INTEGER NOT NULL PRIMARY KEY,_undecodename TEXT DEFAULT \'\',_decodename TEXT DEFAULT \'\',_shortname TEXT DEFAULT \'\',_serverfolder INTEGER DEFAULT 1,_defaultfolder INTEGER DEFAULT 0,_account INTEGER NOT NULL,_movegroup INTEGER DEFAULT 1,_showsender INTEGER DEFAULT 1,_lastuid TEXT DEFAULT \'\',_existsize INTEGER DEFAULT 0,_noselect INTEGER DEFAULT 0,_haschild INTEGER DEFAULT 0,_serverid INTEGER DEFAULT 0,_parentid INTEGER DEFAULT 0,_displayname TEXT,_type INTEGER,_synckey TEXT DEFAULT 0,_enablesync INTEGER DEFAULT 1);");
      var0.execSQL("CREATE INDEX IF NOT EXISTS IDX_mailboxs_accountId ON mailboxs (_account);");
      var0.execSQL("CREATE TRIGGER IF NOT EXISTS delete_mailboxs2 BEFORE DELETE ON mailboxs BEGIN DELETE FROM messages WHERE old._id = _mailboxId; END;");
   }

   public static final void createMailBoxTable_v114(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS mailboxs (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_undecodename TEXT DEFAULT \'\',_decodename TEXT DEFAULT \'\',_shortname TEXT DEFAULT \'\',_serverfolder INTEGER DEFAULT 1,_defaultfolder INTEGER DEFAULT 0,_account INTEGER NOT NULL,_movegroup INTEGER DEFAULT 1,_showsender INTEGER DEFAULT 1,_lastuid TEXT DEFAULT \'\',_existsize INTEGER DEFAULT 0,_noselect INTEGER DEFAULT 0,_haschild INTEGER DEFAULT 0,_serverid INTEGER DEFAULT 0,_parentid INTEGER DEFAULT 0,_displayname TEXT,_type INTEGER,_synckey TEXT DEFAULT 0,_enablesync INTEGER DEFAULT 1,_default_sync INTEGER DEFAULT 0);");
      var0.execSQL("CREATE INDEX IF NOT EXISTS IDX_mailboxs_accountId ON mailboxs (_account);");
      var0.execSQL("CREATE TRIGGER IF NOT EXISTS delete_mailboxs2 BEFORE DELETE ON mailboxs BEGIN DELETE FROM messages WHERE old._id = _mailboxId; END;");
   }

   public static final void createMessageTable(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE messages (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_uid TEXT,_from TEXT,_fromEmail TEXT COLLATE NOCASE,_subject TEXT DEFAULT \'\',_to TEXT,_cc TEXT,_bcc TEXT,_threadindex TEXT,_threadtopic TEXT,_headers TEXT,_date INTEGER,_internaldate INTEGER DEFAULT 0,_preview TEXT,_flags INTEGER,_read INTEGER DEFAULT 0,_del INTEGER DEFAULT -1,_readsize INTEGER DEFAULT 0,_readtotalsize INTEGER DEFAULT 0,_downloadtotalsize INTEGER DEFAULT 0,_messagesize INTEGER DEFAULT 0,_incAttachment INTEGER DEFAULT 0,_account INTEGER NOT NULL,_mailbox TEXT,_mailboxId INTEGER,_mailAct INTEGER DEFAULT 0,_toString TEXT,_ccString TEXT,_bccString TEXT,_displayMode INTEGER DEFAULT 0,_text TEXT,_htmlText TEXT,_messageid TEXT,_references TEXT,_group TEXT,_groupPseudo INTEGER DEFAULT 0,_charset TEXT,_subjtype TEXT DEFAULT \'\',_sync INTEGER DEFAULT 1,_done INTEGER DEFAULT 1,_local INTEGER DEFAULT 0,_tag INTEGER DEFAULT 0,_importance INTEGER DEFAULT 1,_notaddTrack INTEGER,_messageClass TEXT,_messageClassInt INTEGER DEFAULT 0,_smartCommand INTEGER DEFAULT 0,_refMsgId INTEGER DEFAULT 0,_allDayEvent TEXT,_startTime TEXT,_dtstamp TEXT,_endTime TEXT,_instanceType TEXT,_location TEXT,_organizer TEXT,_recurrenceId TEXT,_reminder TEXT,_responseRequested TEXT,_sensitivity TEXT,_IntdBusyStatus TEXT,_timezone TEXT,_globalObjId TEXT,_category TEXT,_recurrence_type TEXT DEFAULT \'-1\',_recurrence_occurrences TEXT,_recurrence_interval TEXT,_recurrence_dayofweek TEXT,_recurrence_dayofmonth TEXT,_recurrence_weekofmonth TEXT,_recurrence_monthofyear TEXT,_recurrence_until TEXT,_synckey TEXT,_replyTo TEXT,_retryCount INTEGER DEFAULT 0);");
      var0.execSQL("CREATE INDEX IDX_messages_accountId ON messages (_account);");
      var0.execSQL("CREATE INDEX IDX_messages_uid ON messages (_uid);");
      var0.execSQL("CREATE INDEX IDX_messages_mailboxId ON messages (_mailboxId);");
      var0.execSQL("CREATE INDEX IDX_messages_del ON messages (_del);");
      var0.execSQL("CREATE INDEX IDX_messages_internaldate ON messages (_internaldate);");
      var0.execSQL("CREATE INDEX IDX_messages_messageId ON messages (_messageid);");
      var0.execSQL("CREATE INDEX IDX_messages_read on messages (_read);");
      var0.execSQL("CREATE INDEX IDX_messages_globalObjId on messages (_globalObjId);");
      var0.execSQL("CREATE INDEX IDX_messages_fromEmail on messages (_fromEmail);");
      var0.execSQL("CREATE INDEX IDX_messages_group on messages (_group);");
      var0.execSQL("CREATE TRIGGER delete_message BEFORE DELETE ON messages BEGIN DELETE FROM parts WHERE old._id = _message; DELETE FROM pending_requests WHERE old._id = _messageId; END;");
      var0.execSQL("CREATE TRIGGER insert_message AFTER INSERT ON messages BEGIN DELETE FROM pending_requests WHERE NEW._uid = _uid AND _request = \'6\'; END;");
   }

   public static final void createMessageTable_v100(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE messages (_id INTEGER NOT NULL PRIMARY KEY,_uid TEXT,_from TEXT,_fromEmail TEXT COLLATE NOCASE,_subject TEXT DEFAULT \'\',_to TEXT,_cc TEXT,_bcc TEXT,_threadindex TEXT,_threadtopic TEXT,_headers TEXT,_date INTEGER,_internaldate INTEGER DEFAULT 0,_preview TEXT,_flags INTEGER,_read INTEGER DEFAULT 0,_del INTEGER DEFAULT -1,_readsize INTEGER DEFAULT 0,_readtotalsize INTEGER DEFAULT 0,_downloadtotalsize INTEGER DEFAULT 0,_messagesize INTEGER DEFAULT 0,_incAttachment INTEGER DEFAULT 0,_account INTEGER NOT NULL,_mailbox TEXT,_mailboxId INTEGER,_mailAct INTEGER DEFAULT 0,_toString TEXT,_ccString TEXT,_bccString TEXT,_displayMode INTEGER DEFAULT 0,_text TEXT,_htmlText TEXT,_messageid TEXT,_references TEXT,_group TEXT,_groupPseudo INTEGER DEFAULT 0,_charset TEXT,_subjtype TEXT DEFAULT \'\',_sync INTEGER DEFAULT 1,_done INTEGER DEFAULT 1,_local INTEGER DEFAULT 0,_tag INTEGER DEFAULT 0,_importance INTEGER DEFAULT 1,_notaddTrack INTEGER,_messageClass TEXT,_messageClassInt INTEGER DEFAULT 0,_smartCommand INTEGER DEFAULT 0,_refMsgId INTEGER DEFAULT 0,_allDayEvent TEXT,_startTime TEXT,_dtstamp TEXT,_endTime TEXT,_instanceType TEXT,_location TEXT,_organizer TEXT,_recurrenceId TEXT,_reminder TEXT,_responseRequested TEXT,_sensitivity TEXT,_IntdBusyStatus TEXT,_timezone TEXT,_globalObjId TEXT,_category TEXT,_recurrence_type TEXT DEFAULT \'-1\',_recurrence_occurrences TEXT,_recurrence_interval TEXT,_recurrence_dayofweek TEXT,_recurrence_dayofmonth TEXT,_recurrence_weekofmonth TEXT,_recurrence_monthofyear TEXT,_recurrence_until TEXT,_synckey TEXT);");
      var0.execSQL("CREATE INDEX IDX_messages_accountId ON messages (_account);");
      var0.execSQL("CREATE INDEX IDX_messages_uid ON messages (_uid);");
      var0.execSQL("CREATE INDEX IDX_messages_mailboxId ON messages (_mailboxId);");
      var0.execSQL("CREATE INDEX IDX_messages_del ON messages (_del);");
      var0.execSQL("CREATE INDEX IDX_messages_internaldate ON messages (_internaldate);");
      var0.execSQL("CREATE INDEX IDX_messages_messageId ON messages (_messageid);");
      var0.execSQL("CREATE INDEX IDX_messages_read on messages (_read);");
      var0.execSQL("CREATE INDEX IDX_messages_globalObjId on messages (_globalObjId);");
      var0.execSQL("CREATE INDEX IDX_messages_fromEmail on messages (_fromEmail);");
      var0.execSQL("CREATE INDEX IDX_messages_group on messages (_group);");
      var0.execSQL("CREATE TRIGGER delete_message BEFORE DELETE ON messages BEGIN DELETE FROM parts WHERE old._id = _message; DELETE FROM pending_requests WHERE old._id = _messageId; END;");
      var0.execSQL("CREATE TRIGGER insert_message AFTER INSERT ON messages BEGIN DELETE FROM pending_requests WHERE NEW._uid = _uid AND _request = \'6\'; END;");
   }

   public static final void createMessageTable_v112(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE messages (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_uid TEXT,_from TEXT,_fromEmail TEXT COLLATE NOCASE,_subject TEXT DEFAULT \'\',_to TEXT,_cc TEXT,_bcc TEXT,_threadindex TEXT,_threadtopic TEXT,_headers TEXT,_date INTEGER,_internaldate INTEGER DEFAULT 0,_preview TEXT,_flags INTEGER,_read INTEGER DEFAULT 0,_del INTEGER DEFAULT -1,_readsize INTEGER DEFAULT 0,_readtotalsize INTEGER DEFAULT 0,_downloadtotalsize INTEGER DEFAULT 0,_messagesize INTEGER DEFAULT 0,_incAttachment INTEGER DEFAULT 0,_account INTEGER NOT NULL,_mailbox TEXT,_mailboxId INTEGER,_mailAct INTEGER DEFAULT 0,_toString TEXT,_ccString TEXT,_bccString TEXT,_displayMode INTEGER DEFAULT 0,_text TEXT,_htmlText TEXT,_messageid TEXT,_references TEXT,_group TEXT,_groupPseudo INTEGER DEFAULT 0,_charset TEXT,_subjtype TEXT DEFAULT \'\',_sync INTEGER DEFAULT 1,_done INTEGER DEFAULT 1,_local INTEGER DEFAULT 0,_tag INTEGER DEFAULT 0,_importance INTEGER DEFAULT 1,_notaddTrack INTEGER,_messageClass TEXT,_messageClassInt INTEGER DEFAULT 0,_smartCommand INTEGER DEFAULT 0,_refMsgId INTEGER DEFAULT 0,_allDayEvent TEXT,_startTime TEXT,_dtstamp TEXT,_endTime TEXT,_instanceType TEXT,_location TEXT,_organizer TEXT,_recurrenceId TEXT,_reminder TEXT,_responseRequested TEXT,_sensitivity TEXT,_IntdBusyStatus TEXT,_timezone TEXT,_globalObjId TEXT,_category TEXT,_recurrence_type TEXT DEFAULT \'-1\',_recurrence_occurrences TEXT,_recurrence_interval TEXT,_recurrence_dayofweek TEXT,_recurrence_dayofmonth TEXT,_recurrence_weekofmonth TEXT,_recurrence_monthofyear TEXT,_recurrence_until TEXT,_synckey TEXT,_replyTo TEXT);");
      var0.execSQL("CREATE INDEX IDX_messages_accountId ON messages (_account);");
      var0.execSQL("CREATE INDEX IDX_messages_uid ON messages (_uid);");
      var0.execSQL("CREATE INDEX IDX_messages_mailboxId ON messages (_mailboxId);");
      var0.execSQL("CREATE INDEX IDX_messages_del ON messages (_del);");
      var0.execSQL("CREATE INDEX IDX_messages_internaldate ON messages (_internaldate);");
      var0.execSQL("CREATE INDEX IDX_messages_messageId ON messages (_messageid);");
      var0.execSQL("CREATE INDEX IDX_messages_read on messages (_read);");
      var0.execSQL("CREATE INDEX IDX_messages_globalObjId on messages (_globalObjId);");
      var0.execSQL("CREATE INDEX IDX_messages_fromEmail on messages (_fromEmail);");
      var0.execSQL("CREATE INDEX IDX_messages_group on messages (_group);");
      var0.execSQL("CREATE TRIGGER delete_message BEFORE DELETE ON messages BEGIN DELETE FROM parts WHERE old._id = _message; DELETE FROM pending_requests WHERE old._id = _messageId; END;");
      var0.execSQL("CREATE TRIGGER insert_message AFTER INSERT ON messages BEGIN DELETE FROM pending_requests WHERE NEW._uid = _uid AND _request = \'6\'; END;");
   }

   public static final void createPartsTable(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE parts (_id INTEGER NOT NULL PRIMARY KEY,_mimetype TEXT NOT NULL,_nativeType TEXT,_filereference TEXT,_contentid TEXT,_filename TEXT DEFAULT \'\',_filepath TEXT,_text TEXT,_meetingMailBody TEXT,_uuid TEXT,_cid TEXT,_filesize INTEGER DEFAULT 0,_contenttype INTEGER DEFAULT 0,_inline INTEGER DEFAULT 0,_encode TEXT,_index TEXT,_charset TEXT,_message INTEGER NOT NULL,_account INTEGER NOT NULL,_flags INTEGER);");
      var0.execSQL("CREATE INDEX IDX_parts_messageId ON parts (_message);");
   }

   public static final void createPartsTable_v100(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE parts (_id INTEGER NOT NULL PRIMARY KEY,_mimetype TEXT NOT NULL,_nativeType TEXT,_filereference TEXT,_contentid TEXT,_filename TEXT DEFAULT \'\',_filepath TEXT,_text TEXT,_meetingMailBody TEXT,_uuid TEXT,_cid TEXT,_filesize INTEGER DEFAULT 0,_contenttype INTEGER DEFAULT 0,_inline INTEGER DEFAULT 0,_encode TEXT,_index TEXT,_charset TEXT,_message INTEGER NOT NULL,_account INTEGER NOT NULL,_flags INTEGER);");
      var0.execSQL("CREATE INDEX IDX_parts_messageId ON parts (_message);");
   }

   public static final void createSearchSvrMessageTable(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS searchSvrMessages (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_uid TEXT,_collectionId,_from TEXT,_fromEmail TEXT COLLATE NOCASE,_subject TEXT DEFAULT \'\',_to TEXT,_cc TEXT,_bcc TEXT,_threadindex TEXT,_threadtopic TEXT,_headers TEXT,_date INTEGER,_internaldate INTEGER DEFAULT 0,_preview TEXT,_flags INTEGER,_read INTEGER DEFAULT 0,_del INTEGER DEFAULT -1,_readsize INTEGER DEFAULT 0,_readtotalsize INTEGER DEFAULT 0,_downloadtotalsize INTEGER DEFAULT 0,_messagesize INTEGER DEFAULT 0,_incAttachment INTEGER DEFAULT 0,_account INTEGER NOT NULL,_mailbox TEXT,_mailboxId INTEGER,_mailAct INTEGER DEFAULT 0,_toString TEXT,_ccString TEXT,_bccString TEXT,_displayMode INTEGER DEFAULT 0,_text TEXT,_htmlText TEXT,_messageid TEXT,_references TEXT,_group TEXT,_groupPseudo INTEGER DEFAULT 0,_charset TEXT,_subjtype TEXT DEFAULT \'\',_sync INTEGER DEFAULT 1,_done INTEGER DEFAULT 1,_local INTEGER DEFAULT 0,_tag INTEGER DEFAULT 0,_importance INTEGER DEFAULT 1,_notaddTrack INTEGER,_messageClass TEXT,_messageClassInt INTEGER DEFAULT 0,_smartCommand INTEGER DEFAULT 0,_refMsgId INTEGER DEFAULT 0,_allDayEvent TEXT,_startTime TEXT,_dtstamp TEXT,_endTime TEXT,_instanceType TEXT,_location TEXT,_organizer TEXT,_recurrenceId TEXT,_reminder TEXT,_responseRequested TEXT,_sensitivity TEXT,_IntdBusyStatus TEXT,_timezone TEXT,_globalObjId TEXT,_category TEXT,_recurrence_type TEXT DEFAULT \'-1\',_recurrence_occurrences TEXT,_recurrence_interval TEXT,_recurrence_dayofweek TEXT,_recurrence_dayofmonth TEXT,_recurrence_weekofmonth TEXT,_recurrence_monthofyear TEXT,_recurrence_until TEXT,_synckey TEXT,_replyTo TEXT);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_accountId ON searchSvrMessages (_account);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_uid ON searchSvrMessages (_uid);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_mailboxId ON searchSvrMessages (_mailboxId);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_del ON searchSvrMessages (_del);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_internaldate ON searchSvrMessages (_internaldate);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_messageId ON searchSvrMessages (_messageid);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_read on searchSvrMessages (_read);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_globalObjId on searchSvrMessages (_globalObjId);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_fromEmail on searchSvrMessages (_fromEmail);");
   }

   public static final void createSearchSvrMessageTable_v100(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS searchSvrMessages (_id INTEGER NOT NULL PRIMARY KEY,_uid TEXT,_collectionId,_from TEXT,_fromEmail TEXT COLLATE NOCASE,_subject TEXT DEFAULT \'\',_to TEXT,_cc TEXT,_bcc TEXT,_threadindex TEXT,_threadtopic TEXT,_headers TEXT,_date INTEGER,_internaldate INTEGER DEFAULT 0,_preview TEXT,_flags INTEGER,_read INTEGER DEFAULT 0,_del INTEGER DEFAULT -1,_readsize INTEGER DEFAULT 0,_readtotalsize INTEGER DEFAULT 0,_downloadtotalsize INTEGER DEFAULT 0,_messagesize INTEGER DEFAULT 0,_incAttachment INTEGER DEFAULT 0,_account INTEGER NOT NULL,_mailbox TEXT,_mailboxId INTEGER,_mailAct INTEGER DEFAULT 0,_toString TEXT,_ccString TEXT,_bccString TEXT,_displayMode INTEGER DEFAULT 0,_text TEXT,_htmlText TEXT,_messageid TEXT,_references TEXT,_group TEXT,_groupPseudo INTEGER DEFAULT 0,_charset TEXT,_subjtype TEXT DEFAULT \'\',_sync INTEGER DEFAULT 1,_done INTEGER DEFAULT 1,_local INTEGER DEFAULT 0,_tag INTEGER DEFAULT 0,_importance INTEGER DEFAULT 1,_notaddTrack INTEGER,_messageClass TEXT,_messageClassInt INTEGER DEFAULT 0,_smartCommand INTEGER DEFAULT 0,_refMsgId INTEGER DEFAULT 0,_allDayEvent TEXT,_startTime TEXT,_dtstamp TEXT,_endTime TEXT,_instanceType TEXT,_location TEXT,_organizer TEXT,_recurrenceId TEXT,_reminder TEXT,_responseRequested TEXT,_sensitivity TEXT,_IntdBusyStatus TEXT,_timezone TEXT,_globalObjId TEXT,_category TEXT,_recurrence_type TEXT DEFAULT \'-1\',_recurrence_occurrences TEXT,_recurrence_interval TEXT,_recurrence_dayofweek TEXT,_recurrence_dayofmonth TEXT,_recurrence_weekofmonth TEXT,_recurrence_monthofyear TEXT,_recurrence_until TEXT,_synckey TEXT);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_accountId ON searchSvrMessages (_account);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_uid ON searchSvrMessages (_uid);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_mailboxId ON searchSvrMessages (_mailboxId);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_del ON searchSvrMessages (_del);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_internaldate ON searchSvrMessages (_internaldate);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_messageId ON searchSvrMessages (_messageid);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_read on searchSvrMessages (_read);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_globalObjId on searchSvrMessages (_globalObjId);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_fromEmail on searchSvrMessages (_fromEmail);");
   }

   public static final void createSearchSvrMessageTable_v112(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS searchSvrMessages (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,_uid TEXT,_collectionId,_from TEXT,_fromEmail TEXT COLLATE NOCASE,_subject TEXT DEFAULT \'\',_to TEXT,_cc TEXT,_bcc TEXT,_threadindex TEXT,_threadtopic TEXT,_headers TEXT,_date INTEGER,_internaldate INTEGER DEFAULT 0,_preview TEXT,_flags INTEGER,_read INTEGER DEFAULT 0,_del INTEGER DEFAULT -1,_readsize INTEGER DEFAULT 0,_readtotalsize INTEGER DEFAULT 0,_downloadtotalsize INTEGER DEFAULT 0,_messagesize INTEGER DEFAULT 0,_incAttachment INTEGER DEFAULT 0,_account INTEGER NOT NULL,_mailbox TEXT,_mailboxId INTEGER,_mailAct INTEGER DEFAULT 0,_toString TEXT,_ccString TEXT,_bccString TEXT,_displayMode INTEGER DEFAULT 0,_text TEXT,_htmlText TEXT,_messageid TEXT,_references TEXT,_group TEXT,_groupPseudo INTEGER DEFAULT 0,_charset TEXT,_subjtype TEXT DEFAULT \'\',_sync INTEGER DEFAULT 1,_done INTEGER DEFAULT 1,_local INTEGER DEFAULT 0,_tag INTEGER DEFAULT 0,_importance INTEGER DEFAULT 1,_notaddTrack INTEGER,_messageClass TEXT,_messageClassInt INTEGER DEFAULT 0,_smartCommand INTEGER DEFAULT 0,_refMsgId INTEGER DEFAULT 0,_allDayEvent TEXT,_startTime TEXT,_dtstamp TEXT,_endTime TEXT,_instanceType TEXT,_location TEXT,_organizer TEXT,_recurrenceId TEXT,_reminder TEXT,_responseRequested TEXT,_sensitivity TEXT,_IntdBusyStatus TEXT,_timezone TEXT,_globalObjId TEXT,_category TEXT,_recurrence_type TEXT DEFAULT \'-1\',_recurrence_occurrences TEXT,_recurrence_interval TEXT,_recurrence_dayofweek TEXT,_recurrence_dayofmonth TEXT,_recurrence_weekofmonth TEXT,_recurrence_monthofyear TEXT,_recurrence_until TEXT,_synckey TEXT,_replyTo TEXT);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_accountId ON searchSvrMessages (_account);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_uid ON searchSvrMessages (_uid);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_mailboxId ON searchSvrMessages (_mailboxId);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_del ON searchSvrMessages (_del);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_internaldate ON searchSvrMessages (_internaldate);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_messageId ON searchSvrMessages (_messageid);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_read on searchSvrMessages (_read);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_globalObjId on searchSvrMessages (_globalObjId);");
      var0.execSQL("CREATE INDEX IDX_searchSvrMessages_fromEmail on searchSvrMessages (_fromEmail);");
   }

   public static final void createSearchSvrPartsTable(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS searchSvrParts (_id INTEGER NOT NULL PRIMARY KEY,_mimetype TEXT NOT NULL,_nativeType TEXT,_filereference TEXT,_contentid TEXT,_filename TEXT DEFAULT \'\',_filepath TEXT,_text TEXT,_meetingMailBody TEXT,_uuid TEXT,_cid TEXT,_filesize INTEGER DEFAULT 0,_contenttype INTEGER DEFAULT 0,_inline INTEGER DEFAULT 0,_encode TEXT,_index TEXT,_charset TEXT,_message INTEGER NOT NULL,_account INTEGER NOT NULL,_flags INTEGER);");
      var0.execSQL("CREATE INDEX IF NOT EXISTS IDX_searchSvrParts_messageId ON searchSvrParts (_message);");
   }

   public static final void createSearchSvrPartsTable_v100(SQLiteDatabase var0) {
      var0.execSQL("CREATE TABLE IF NOT EXISTS searchSvrParts (_id INTEGER NOT NULL PRIMARY KEY,_mimetype TEXT NOT NULL,_nativeType TEXT,_filereference TEXT,_contentid TEXT,_filename TEXT DEFAULT \'\',_filepath TEXT,_text TEXT,_meetingMailBody TEXT,_uuid TEXT,_cid TEXT,_filesize INTEGER DEFAULT 0,_contenttype INTEGER DEFAULT 0,_inline INTEGER DEFAULT 0,_encode TEXT,_index TEXT,_charset TEXT,_message INTEGER NOT NULL,_account INTEGER NOT NULL,_flags INTEGER);");
      var0.execSQL("CREATE INDEX IF NOT EXISTS IDX_searchSvrParts_messageId ON searchSvrParts (_message);");
   }

   public static final void deleteAccountById(long var0) {
      IContentProvider var2 = instance();

      int var7;
      label27: {
         int var6;
         try {
            if(DEBUG) {
               ll.d("MailProvider", "deleteAccountById(): deleteBufferFile");
            }

            StringBuilder var3 = new StringBuilder();
            Uri var4 = sAccountsURI;
            Uri var5 = Uri.parse(var3.append(var4).append("/").append(var0).toString());
            var6 = var2.delete(var5, (String)null, (String[])null);
         } catch (Exception var11) {
            var11.printStackTrace();
            var7 = -1;
            break label27;
         }

         var7 = var6;
      }

      if(var7 > 0) {
         Util.writeAccountCountToPref(sTheOne.getContext(), -1);
      }

      ContentResolver var8 = sTheOne.getContext().getContentResolver();
      Uri var9 = sAccountsURI;
      var8.notifyChange(var9, (ContentObserver)null, (boolean)0);
      if(DEBUG) {
         String var10 = "deleteAccountById>" + var0 + "," + var7;
         ll.d("MailProvider", var10);
      }
   }

   private final void deleteAccountMessages(long var1) {
      this.deleteBinaryAttachments("_account", var1);
      MailProvider.DatabaseWrapper var3 = this.getDatabaseWrapper();
      String var4 = "_account=" + var1;
      var3.delete("parts", var4, (String[])null);
      String var6 = "_account=" + var1;
      var3.delete("messages", var6, (String[])null);
      String var8 = "_account=" + var1;
      var3.delete("mailboxs", var8, (String[])null);
      int var10 = -1;
      String[] var11 = new String[]{"_protocol"};
      String var12 = "_id=" + var1;
      Object var13 = null;
      Object var14 = null;
      Object var15 = null;
      Cursor var16 = var3.query("accounts", var11, var12, (String[])null, (String)var13, (String)var14, (String)var15);
      if(var16 != null && var16.moveToFirst()) {
         int var17 = var16.getColumnIndexOrThrow("_protocol");
         var10 = var16.getInt(var17);
      }

      if(var16 != null && !var16.isClosed()) {
         var16.close();
      }

      if(var10 == 4) {
         String var18 = "_accountId=" + var1;
         var3.delete("easTracking", var18, (String[])null);
         String var20 = "_account=" + var1;
         var3.delete("searchSvrMessages", var20, (String[])null);
         String var22 = "_account=" + var1;
         var3.delete("searchSvrParts", var22, (String[])null);
         int var24 = var3.delete("mailboxs", "_account=-1", (String[])null);
      }
   }

   private final void deleteBinaryAttachments(Cursor var1) {
      if(var1 != null) {
         while(var1.moveToNext()) {
            Attachment.deleteAttachThumb(sTheOne.getContext(), var1);
            int var2 = var1.getColumnIndexOrThrow("_filepath");
            String var3 = var1.getString(var2);
            if(isMailCustomData(var3) == 1) {
               File var4 = new File(var3);
               if(var4.isFile()) {
                  boolean var5 = var4.delete();
               }
            }
         }

      }
   }

   private final void deleteBinaryAttachments(String param1, long param2) {
      // $FF: Couldn't be decompiled
   }

   private final int deleteMessageParts(long var1, boolean var3) {
      if(var3) {
         this.deleteBinaryAttachments("_message", var1);
      }

      MailProvider.DatabaseWrapper var4 = this.getDatabaseWrapper();
      String var5 = "_message=" + var1;
      return var4.delete("parts", var5, (String[])null);
   }

   public static final void deleteMessagesByMailbox(long var0, long var2) {
      IContentProvider var4 = instance();

      Cursor var33;
      label53: {
         Cursor var7;
         try {
            Uri var5 = sAccountsURI;
            String var6 = "_id=" + var0;
            var7 = var4.query(var5, (String[])null, var6, (String[])null, (String)null);
         } catch (DeadObjectException var31) {
            var33 = null;
            break label53;
         } catch (RemoteException var32) {
            var33 = null;
            break label53;
         }

         Cursor var34 = var7;

         try {
            if(!var34.moveToNext()) {
               var34.close();
               return;
            }

            int var9 = var34.getColumnIndexOrThrow("_protocol");
            boolean var10 = Mail.isIMAP4(var34.getInt(var9));
            var34.close();
            MailProvider.DatabaseWrapper var11 = sTheOne.getDatabaseWrapper();
            String[] var12 = new String[2];
            String var13 = String.valueOf(var0);
            var12[0] = var13;
            String var14 = String.valueOf(var2);
            var12[1] = var14;
            var34 = var11.rawQuery("select t2._filepath from messages t1, parts t2 where t1._id = t2._message and t1._account = ? and t1._mailboxId = ? and t2._filepath is not null", var12);

            while(true) {
               if(!var34.moveToNext()) {
                  MailProvider.DatabaseWrapper var20 = sTheOne.getDatabaseWrapper();
                  Object[] var21 = new Object[2];
                  Long var22 = Long.valueOf(var0);
                  var21[0] = var22;
                  Long var23 = Long.valueOf(var2);
                  var21[1] = var23;
                  String var24 = String.format("_account = \'%d\' and _mailboxId = \'%d\'", var21);
                  var20.delete("messages", var24, (String[])null);
                  break;
               }

               int var15 = var34.getColumnIndexOrThrow("_filepath");
               String var16 = var34.getString(var15);
               if(isMailCustomData(var16) == 1) {
                  File var17 = new File(var16);
                  if(var17.isFile()) {
                     boolean var18 = var17.delete();
                  }
               }
            }
         } catch (DeadObjectException var29) {
            var33 = var7;
            break label53;
         } catch (RemoteException var30) {
            var33 = var7;
            break label53;
         }

         var33 = var34;
      }

      if(var33 != null) {
         var33.close();
      }
   }

   private void deleteSyncAcount(Context var1, MailProvider.DatabaseWrapper var2, String var3, String var4) {
      String[] var5 = new String[]{"_emailaddress", "_protocol"};
      Cursor var8 = var2.query("accounts", var5, var3, (String[])null, (String)null, (String)null, (String)null);
      if(var8 != null) {
         while(var8.moveToNext()) {
            String var9 = var8.getString(0);
            int var10 = var8.getInt(1);
            if(var10 != 4 && var10 != 10 && !var9.equals(var4)) {
               android.accounts.Account var13 = new android.accounts.Account(var9, "com.htc.android.mail");
               AccountManagerFuture var14 = AccountManager.get(var1).removeAccount(var13, (AccountManagerCallback)null, (Handler)null);
            }
         }

         var8.close();
      }
   }

   private void encInformationifNeed(Context var1, ContentValues var2) {
      if(var2 != null) {
         if(var2.getAsString("_providerGroup") != null) {
            String var3 = var2.getAsString("_providerGroup");
            if("Yahoo".equals(var3)) {
               byte[] var4 = MailCommon.getEncryptKey(var1);
               if(var2.getAsString("_emailaddress") != null) {
                  String var5 = var2.getAsString("_emailaddress");
                  var2.remove("_emailaddress");
                  String var6 = MailCommon.encryptContent(var4, var5);
                  var2.put("_emailaddress", var6);
               }

               if(var2.getAsString("_username") != null) {
                  String var7 = var2.getAsString("_username");
                  var2.remove("_username");
                  String var8 = MailCommon.encryptContent(var4, var7);
                  var2.put("_username", var8);
               }

               if(var2.getAsString("_outusername") != null) {
                  String var9 = var2.getAsString("_outusername");
                  var2.remove("_outusername");
                  String var10 = MailCommon.encryptContent(var4, var9);
                  var2.put("_outusername", var10);
               }

               if(var2.getAsString("_password") != null) {
                  String var11 = Account.decodePwd(var2.getAsString("_password"));
                  var2.remove("_password");
                  String var12 = MailCommon.encryptContent(var4, var11);
                  var2.put("_password", var12);
               }

               if(var2.getAsString("_outpassword") != null) {
                  String var13 = Account.decodePwd(var2.getAsString("_outpassword"));
                  var2.remove("_outpassword");
                  String var14 = MailCommon.encryptContent(var4, var13);
                  var2.put("_outpassword", var14);
               }
            }
         }
      }
   }

   public static final Account getAccount(long var0) {
      AccountPool var2 = AccountPool.getInstance(sTheOne.getContext());
      Context var3 = sTheOne.getContext();
      return var2.getAccount(var3, var0);
   }

   public static final int getAccountCount(boolean param0) {
      // $FF: Couldn't be decompiled
   }

   public static final Cursor getAccountCursor(long var0) {
      IContentProvider var2 = instance();
      Uri var3;
      if(var0 == 0L) {
         var3 = sAccountsURI;
      } else {
         var3 = Uri.parse("content://mail/accounts/" + var0);
      }

      Object var4 = null;
      Object var5 = null;
      Object var6 = null;
      Object var7 = null;

      Cursor var8;
      Cursor var9;
      try {
         var8 = var2.query(var3, (String[])var4, (String)var5, (String[])var6, (String)var7);
      } catch (DeadObjectException var12) {
         var9 = null;
         return var9;
      } catch (RemoteException var13) {
         var9 = null;
         return var9;
      }

      var9 = var8;
      return var9;
   }

   private int getAccountEncodedColorIdx(MailProvider.DatabaseWrapper var1, String var2) {
      int var9;
      if(var2 != null) {
         String[] var3 = new String[]{"count(*)"};
         String[] var4 = new String[]{var2};
         Cursor var5 = var1.query("accounts", var3, "_providerGroup like ? AND _del = -1", var4, (String)null, (String)null, (String)null);
         int var6 = 0;
         if(var5 != null) {
            if(var5.moveToNext()) {
               var6 = var5.getInt(0);
            }

            var5.close();
         }

         if(var6 == 0) {
            String var7 = "Exchange".toLowerCase();
            String var8 = var2.toLowerCase();
            if(var7.equals(var8)) {
               var9 = Account.getEncodedColorIdx(0, 0);
               return var9;
            }

            String var10 = "Gmail".toLowerCase();
            String var11 = var2.toLowerCase();
            if(var10.equals(var11)) {
               var9 = Account.getEncodedColorIdx(0, 1);
               return var9;
            }

            String var12 = "Yahoo".toLowerCase();
            String var13 = var2.toLowerCase();
            if(var12.equals(var13)) {
               var9 = Account.getEncodedColorIdx(0, 2);
               return var9;
            }
         }
      }

      int var14 = Account.getDefaultEncodedColorIdx();
      Object[] var15 = new Object[1];
      Integer var16 = Integer.valueOf(Account.getEncodedColorIdx(1, 0));
      var15[0] = var16;
      String var17 = String.format("_colorIdx >= %d AND _del = -1", var15);
      String[] var18 = new String[]{"_colorIdx"};
      Cursor var19 = var1.query("accounts", var18, var17, (String[])null, (String)null, (String)null, "_colorIdx asc");
      if(var19 != null) {
         int var20 = Account.getEncodedColorIdx(1, 0);

         int var21;
         for(var21 = 0; var19.moveToNext(); ++var21) {
            int[] var22 = Account.getDecodedColorIdx(var19.getInt(0));
            if(var21 >= 16) {
               break;
            }

            int var24 = var22[1];
            if(var21 != var24) {
               break;
            }
         }

         int var23 = var21 % 16;
         var14 = Account.getEncodedColorIdx(1, var23);
         var19.close();
      }

      var9 = var14;
      return var9;
   }

   public static final Cursor getAccountIDs() {
      IContentProvider var0 = instance();

      Cursor var3;
      Cursor var4;
      try {
         Uri var1 = sAccountsURI;
         String[] var2 = new String[]{"_id"};
         var3 = var0.query(var1, var2, (String)null, (String[])null, (String)null);
      } catch (DeadObjectException var7) {
         var4 = null;
         return var4;
      } catch (RemoteException var8) {
         var4 = null;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public static final Cursor getAccounts() {
      return getAccountCursor(0L);
   }

   private String getCombinedAccountMailboxIdSeq(Context var1, long var2) {
      Account[] var4 = AccountPool.getInstance(this.getContext()).getAccounts(var1);
      StringBuffer var5 = new StringBuffer();
      Account[] var6 = var4;
      int var7 = var4.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Account var9 = var6[var8];
         if(var9 != null && var9.getMailboxs() != null) {
            Mailbox var10;
            if(var2 == Long.MAX_VALUE) {
               var10 = var9.getMailboxs().getDefaultMailbox();
            } else if(var2 == 9223372036854775806L) {
               var10 = var9.getMailboxs().getTrashMailbox();
            } else if(var2 == 9223372036854775805L) {
               var10 = var9.getMailboxs().getSentMailbox();
            } else if(var2 == 9223372036854775804L) {
               var10 = var9.getMailboxs().getDraftMailbox();
            } else if(var2 == 9223372036854775803L) {
               var10 = var9.getMailboxs().getOutMailbox();
            } else {
               var10 = var9.getMailboxs().getMailboxById(var2);
               if(var10.kind != 2147483642) {
                  throw new Error("not mailboxId for combined account");
               }
            }

            if(var10 != null) {
               long[] var11 = var10.getMailboxIds();
               if(var11 != null) {
                  if(var5.length() > 0) {
                     StringBuffer var12 = var5.append(",");
                  }

                  String var13 = MailCommon.getLongSequence(var11);
                  var5.append(var13);
               }
            }
         }
      }

      return var5.toString();
   }

   private MailProvider.DatabaseWrapper getDatabaseWrapper() {
      synchronized(this){}

      MailProvider.DatabaseWrapper var3;
      try {
         if(this.mDbWrapper == null) {
            Context var1 = this.getContext();
            MailProvider.DatabaseWrapper var2 = new MailProvider.DatabaseWrapper(var1);
            this.mDbWrapper = var2;
         }

         var3 = this.mDbWrapper;
      } finally {
         ;
      }

      return var3;
   }

   public static final Cursor getDefaultAccount() {
      // $FF: Couldn't be decompiled
   }

   public static final long getDefaultAccountId() {
      // $FF: Couldn't be decompiled
   }

   public static final long getDefaultExchangeAccountId() {
      // $FF: Couldn't be decompiled
   }

   public static final long getLastAccountEnterId() {
      return sTheOne.getContext().getSharedPreferences("account", 0).getLong("LAST_ACCOUNT_ENTER", 65535L);
   }

   public static Cursor getMessageDoneObserverCursor(long var0, int var2) {
      StringBuilder var3 = (new StringBuilder()).append("getMessageDoneObserverCursor(): ");
      String var4 = Integer.toString(var2);
      String var5 = var3.append(var4).toString();
      ll.d("MailProvider", var5);
      Object[] var6 = new Object[1];
      Long var7 = Long.valueOf(var0);
      var6[0] = var7;
      String var8 = String.format("_id = \'%d\'", var6);
      if(DEBUG) {
         String var9 = "get: " + var8;
         ll.i("MailProvider", var9);
      }

      Cursor var15;
      if(var2 == 4) {
         MailProvider.DatabaseWrapper var10 = sTheOne.getDatabaseWrapper();
         String[] var11 = new String[]{"_done", "_del"};
         Object var12 = null;
         Object var13 = null;
         Object var14 = null;
         var15 = var10.query("easmessages", var11, var8, (String[])null, (String)var12, (String)var13, (String)var14);
      } else {
         MailProvider.DatabaseWrapper var18 = sTheOne.getDatabaseWrapper();
         String[] var19 = new String[]{"_done", "_del"};
         Object var20 = null;
         Object var21 = null;
         Object var22 = null;
         var15 = var18.query("messages", var19, var8, (String[])null, (String)var20, (String)var21, (String)var22);
      }

      ContentResolver var16 = sTheOne.getContext().getContentResolver();
      Uri var17 = Uri.parse("content://mail/msgDone/" + var0);
      var15.setNotificationUri(var16, var17);
      return var15;
   }

   static final Cursor getProvider(long var0) {
      IContentProvider var2 = instance();
      Uri var3;
      if(var0 == 0L) {
         var3 = sProvidersURI;
      } else {
         var3 = Uri.parse("content://mail/providers/" + var0);
      }

      Object var4 = null;
      Object var5 = null;
      Object var6 = null;
      Object var7 = null;

      Cursor var8;
      Cursor var9;
      try {
         var8 = var2.query(var3, (String[])var4, (String)var5, (String[])var6, (String)var7);
      } catch (DeadObjectException var12) {
         var9 = null;
         return var9;
      } catch (RemoteException var13) {
         var9 = null;
         return var9;
      }

      var9 = var8;
      return var9;
   }

   static final Cursor getProvider(String var0) {
      IContentProvider var1 = instance();
      Cursor var5;
      if(var0 != null) {
         Uri var2 = sProvidersURI;

         Cursor var4;
         try {
            String var3 = "_provider=\'" + var0 + "\'";
            var4 = var1.query(var2, (String[])null, var3, (String[])null, (String)null);
         } catch (DeadObjectException var8) {
            var5 = null;
            return var5;
         } catch (RemoteException var9) {
            var5 = null;
            return var5;
         }

         var5 = var4;
      } else {
         var5 = null;
      }

      return var5;
   }

   public static final int getTotalMailNum(long param0, int param2, long param3) {
      // $FF: Couldn't be decompiled
   }

   public static final int getUnread(long param0, int param2, long param3) {
      // $FF: Couldn't be decompiled
   }

   private String getWhereForSummariesWithMailbox(Uri var1) {
      String[] var2 = MailCommon.parseSummariesWithMailboxUri(var1);
      String var3;
      if(var2 == null) {
         var3 = "";
      } else if(var2.length < 2) {
         var3 = "";
      } else {
         long var4 = Long.parseLong(var2[0]);
         long var6 = Long.parseLong(var2[1]);
         String var8 = null;
         if(var2.length > 2) {
            var8 = var2[2];
         }

         StringBuilder var9 = new StringBuilder();
         AccountPool var10 = AccountPool.getInstance(this.getContext());
         if(var4 == Long.MAX_VALUE) {
            Context var11 = this.getContext();
            String var12 = this.getCombinedAccountMailboxIdSeq(var11, var6);
            if(var12 != null && !"".equals(var12)) {
               StringBuilder var13 = var9.append(" (messages._mailboxId IN (").append(var12).append(")) ");
            }
         } else {
            Context var16 = this.getContext();
            Account var17 = var10.getAccount(var16, var4);
            if(var17 == null) {
               var3 = "";
               return var3;
            }

            if(var17.getMailboxs() == null) {
               var3 = "";
               return var3;
            }

            Mailbox var18 = var17.getMailboxs().getMailboxById(var6);
            if(var18 == null) {
               var3 = "";
               return var3;
            }

            long[] var19 = var18.getMailboxIds();
            if(var19 != null && var19.length > 0) {
               if(var19.length == 1) {
                  StringBuilder var20 = var9.append(" (messages._mailboxId = ");
                  long var21 = var19[0];
                  StringBuilder var23 = var20.append(var21).append(") ");
               } else {
                  StringBuilder var24 = var9.append(" (messages._mailboxId IN (");
                  String var25 = MailCommon.getLongSequence(var19);
                  StringBuilder var26 = var24.append(var25).append(")) ");
               }
            }
         }

         if(var9.length() != 0) {
            StringBuilder var14 = var9.append(" AND (messages._del = -1) ");
         } else {
            StringBuilder var27 = var9.append(" (messages._del = -1) ");
         }

         if("unread".equals(var8)) {
            StringBuilder var15 = var9.append(" AND (messages._read = 0) ");
         } else if("markStar".equals(var8)) {
            StringBuilder var28 = var9.append(" AND (messages._flags = 2)");
         } else if("attach".equals(var8)) {
            StringBuilder var29 = var9.append(" AND (messages._incAttachment = 1)");
         } else if("meeting".equals(var8)) {
            StringBuilder var30 = var9.append(" AND (messages._messageClassInt = 6)");
         }

         var3 = var9.toString();
      }

      return var3;
   }

   private int handleLowStorage(MailProvider.DatabaseWrapper var1, Uri var2, ContentValues var3) {
      int var4 = var1.update("accounts", var3, (String)null, (String[])null);
      AccountPool var5 = AccountPool.getInstance(this.getContext());
      Context var6 = this.getContext();
      Account[] var7 = var5.getAccounts(var6);
      if(var7 != null && var4 > 0) {
         int var8 = 0;

         while(true) {
            int var9 = var7.length;
            if(var8 >= var9) {
               break;
            }

            if(var7[var8] != false) {
               Account var10 = var7[var8];
               int var11 = var3.getAsInteger("_fetchMailDays").intValue();
               var10.fetchMailDaysIndex = var11;
               if(var7[var8].protocol == 4) {
                  byte var12;
                  switch(var3.getAsInteger("_fetchMailDays").intValue()) {
                  case 0:
                     var12 = 1;
                     break;
                  case 1:
                     var12 = 2;
                     break;
                  case 2:
                     var12 = 3;
                     break;
                  case 3:
                     var12 = 4;
                     break;
                  case 4:
                     var12 = 5;
                     break;
                  case 5:
                     var12 = 0;
                     break;
                  default:
                     var12 = 2;
                  }

                  Context var13 = this.getContext();
                  Account var14 = var7[var8];
                  ExchangeServer var15 = new ExchangeServer(var13, var14);
                  EASOptions var16 = var15.getSyncOption();
                  if(var16 != null) {
                     var16.mailFilterType = var12;
                     var15.updateSyncOption(var16);
                  }
               }
            }

            ++var8;
         }
      }

      return var4;
   }

   public static boolean haveTheLastUid(String var0, long var1, long var3, int var5) {
      if(var0 == null) {
         var0 = "";
      }

      boolean var15;
      String var19;
      boolean var25;
      Cursor var26;
      if(var5 == 2) {
         Cursor var14;
         label49: {
            Cursor var13;
            try {
               IContentProvider var6 = instance();
               Uri var7 = sMessagesURI;
               String[] var8 = new String[]{"min(abs(_uid)) as _minuid"};
               Object[] var9 = new Object[2];
               Long var10 = Long.valueOf(var3);
               var9[0] = var10;
               Long var11 = Long.valueOf(var1);
               var9[1] = var11;
               String var12 = String.format("_mailboxId = \'%d\' AND _account = \'%d\' AND _del=-1", var9);
               var13 = var6.query(var7, var8, var12, (String[])null, (String)null);
            } catch (RemoteException var39) {
               var39.printStackTrace();
               var14 = null;
               break label49;
            }

            var14 = var13;
         }

         if(var14.getCount() <= 0) {
            var14.close();
            var15 = true;
            boolean var17 = false;
            return var15;
         }

         boolean var23;
         label80: {
            if(var14.moveToNext()) {
               int var18 = var14.getColumnIndexOrThrow("_minuid");
               var19 = String.valueOf(var14.getLong(var18));
               String var20 = "min uid is " + var19;
               ll.d("MailProvider", var20);
               if(var19 == null || var19 == "" || var0 == "") {
                  var14.close();
                  var15 = true;
                  boolean var22 = false;
                  return var15;
               }

               if(!var0.equals("") && var19.equals(var0)) {
                  var23 = true;
                  break label80;
               }
            }

            var23 = false;
         }

         var25 = var23;
         var26 = var14;
      } else if(var5 != 0 && var5 != 1) {
         var26 = null;
         var25 = false;
      } else {
         label91: {
            Object[] var27 = new Object[2];
            Long var28 = Long.valueOf(var3);
            var27[0] = var28;
            Long var29 = Long.valueOf(var3);
            var27[1] = var29;
            String var30 = String.format("SELECT _uid FROM messages WHERE _mailboxId = %d AND _del = -1 AND _internaldate = (SELECT min(_internaldate) FROM messages WHERE _mailboxId = %d AND _del = -1)", var27);
            Cursor var31 = sTheOne.getDatabaseWrapper().getReadableDatabase().rawQuery(var30, (String[])null);
            if(var31.getCount() <= 0) {
               var31.close();
               var15 = true;
               boolean var33 = false;
               return var15;
            }

            if(var31.moveToNext()) {
               int var34 = var31.getColumnIndexOrThrow("_uid");
               var19 = var31.getString(var34);
               String var35 = "oldest uid is " + var19;
               ll.d("MailProvider", var35);
               if(var19 == null || var19 == "" || var0 == "") {
                  var31.close();
                  var15 = true;
                  boolean var37 = false;
                  return var15;
               }

               if(!var0.equals("") && var19.equals(var0)) {
                  var25 = true;
                  var26 = var31;
                  break label91;
               }
            }

            var26 = var31;
            var25 = false;
         }
      }

      if(var26 != null && !var26.isClosed()) {
         var26.close();
      }

      var15 = var25;
      return var15;
   }

   public static final IContentProvider instance() {
      return sTheOne.getIContentProvider();
   }

   public static boolean isDBLocked() {
      return sTheOne.getDatabaseWrapper().isDBLocked();
   }

   private static boolean isMailCustomData(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         boolean var2 = false;
         if(MailApPath == null) {
            MailApPath = sTheOne.getContext().getDir("mail", 0).getPath();
         }

         String var3 = MailApPath;
         if(var0.startsWith(var3)) {
            var2 = true;
         }

         if(var0.indexOf(".data/mail/related/") != -1) {
            if(DEBUG) {
               String var4 = "This file is in .data: " + var0;
               ll.d("MailProvider", var4);
            }

            var2 = true;
         }

         if(DEBUG) {
            StringBuilder var5 = (new StringBuilder()).append("isMailCustomData<").append(var2).append(",").append(var0).append(",");
            String var6 = MailApPath;
            String var7 = var5.append(var6).toString();
            ll.d("MailProvider", var7);
         }

         var1 = var2;
      }

      return var1;
   }

   private void notifyChange(ContentResolver var1, Uri var2, ContentObserver var3) {
      if(!this.applyingBatch()) {
         var1.notifyChange(var2, var3, (boolean)0);
      } else {
         Object var4 = (Set)this.mNotifiedUriSets.get();
         if(var4 == null) {
            var4 = new LinkedHashSet();
         }

         ((Set)var4).add(var2);
         this.mNotifiedUriSets.set(var4);
      }
   }

   private void notifyCombinedAccount(ContentResolver var1, long var2, long var4) {
      AccountPool var6 = AccountPool.getInstance(this.getContext());
      Context var7 = this.getContext();
      Mailboxs var8 = var6.getAccount(var7, var2).getMailboxs();
      if(var8 != null) {
         Mailbox var9 = var8.getDefaultMailbox();
         if(var9 != null && var9.id == var4) {
            Uri var10 = MailCommon.getSummariesUri(Long.MAX_VALUE, Long.MAX_VALUE);
            this.notifyChange(var1, var10, (ContentObserver)null);
         } else {
            var9 = var8.getTrashMailbox();
            if(var9 != null && var9.id == var4) {
               Uri var11 = MailCommon.getSummariesUri(Long.MAX_VALUE, 9223372036854775806L);
               this.notifyChange(var1, var11, (ContentObserver)null);
            } else {
               var9 = var8.getSentMailbox();
               if(var9 != null && var9.id == var4) {
                  Uri var12 = MailCommon.getSummariesUri(Long.MAX_VALUE, 9223372036854775805L);
                  this.notifyChange(var1, var12, (ContentObserver)null);
               } else {
                  var9 = var8.getDraftMailbox();
                  if(var9 != null && var9.id == var4) {
                     Uri var13 = MailCommon.getSummariesUri(Long.MAX_VALUE, 9223372036854775804L);
                     this.notifyChange(var1, var13, (ContentObserver)null);
                  } else {
                     var9 = var8.getOutMailbox();
                     if(var9 != null) {
                        if(var9.id == var4) {
                           Uri var14 = MailCommon.getSummariesUri(Long.MAX_VALUE, 9223372036854775803L);
                           this.notifyChange(var1, var14, (ContentObserver)null);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void notifyCombinedAccountAndMailbox(ContentResolver var1, Uri var2) {
      String[] var3 = MailCommon.parseSummariesWithMailboxUri(var2);
      if(var3 != null) {
         if(var3.length >= 2) {
            long var4 = Long.parseLong(var3[0]);
            long var6 = Long.parseLong(var3[1]);
            this.notifyCombinedAccount(var1, var4, var6);
            this.notifyCombinedMailbox(var1, var4, var6);
         }
      }
   }

   private void notifyCombinedMailbox(ContentResolver var1, long var2, long var4) {
      AccountPool var6 = AccountPool.getInstance(this.getContext());
      Context var7 = this.getContext();
      Mailboxs var12 = var6.getAccount(var7, var2).getMailboxs();
      if(var12 != null) {
         Mailbox[] var13 = var12.getCombinedMailboxs();
         int var14 = var13.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            Mailbox var16 = var13[var15];
            if(var16 != null && var16.contains(var4)) {
               long var20 = var16.id;
               Uri var26 = MailCommon.getSummariesUri(var2, var20);
               Object var30 = null;
               this.notifyChange(var1, var26, (ContentObserver)var30);
               long var31 = var16.id;
               Uri var33 = MailCommon.getSummariesUri(Long.MAX_VALUE, var31);
               Object var37 = null;
               this.notifyChange(var1, var33, (ContentObserver)var37);
            }
         }

      }
   }

   public static final void resetDefaultAccount() {
      IContentProvider var0 = instance();
      ContentValues var1 = new ContentValues();
      Integer var2 = Integer.valueOf(0);
      var1.put("_defaultaccount", var2);
      int var3 = sTheOne.getDatabaseWrapper().update("accounts", var1, (String)null, (String[])null);
      if(DEBUG) {
         String var4 = "setDefaultAccountById>" + var3;
         ll.d("MailProvider", var4);
      }
   }

   public static final void setDefaultAccountByEmail(String var0) {
      IContentProvider var1 = instance();
      ContentValues var2 = new ContentValues();
      Integer var3 = Integer.valueOf(0);
      var2.put("_defaultaccount", var3);

      Cursor var5;
      try {
         Uri var4 = sAccountsURI;
         var5 = var1.query(var4, (String[])null, (String)null, (String[])null, (String)null);
      } catch (RemoteException var29) {
         return;
      }

      Cursor var6 = var5;
      Uri var10;
      Object var11;
      Object var12;
      if(var5.getCount() > 0) {
         while(var6.moveToNext()) {
            StringBuilder var7 = (new StringBuilder()).append("content://mail/accounts/");
            int var8 = var6.getColumnIndexOrThrow("_defaultaccount");
            int var9 = var6.getInt(var8);
            var10 = Uri.parse(var7.append(var9).toString());
            var11 = null;
            var12 = null;

            try {
               var1.update(var10, var2, (String)var11, (String[])var12);
            } catch (RemoteException var31) {
               if(var6 == null) {
                  return;
               }

               var6.close();
               return;
            }
         }
      }

      var6.close();
      var2 = new ContentValues();
      Integer var16 = Integer.valueOf(1);
      var2.put("_defaultaccount", var16);

      Cursor var19;
      try {
         Uri var17 = sAccountsURI;
         String var18 = "_emailaddress=" + var0;
         var19 = var1.query(var17, (String[])null, var18, (String[])null, (String)null);
      } catch (RemoteException var28) {
         return;
      }

      StringBuilder var21 = (new StringBuilder()).append("content://mail/accounts/");
      int var22 = var19.getColumnIndexOrThrow("_id");
      long var23 = var19.getLong(var22);
      var10 = Uri.parse(var21.append(var23).toString());
      var11 = null;
      var12 = null;

      try {
         var1.update(var10, var2, (String)var11, (String[])var12);
      } catch (RemoteException var30) {
         if(var19 == null) {
            return;
         }

         var19.close();
         return;
      }

      var19.close();
   }

   public static final void setDefaultAccountById(Context var0, long var1) {
      IContentProvider var3 = instance();
      ContentValues var4 = new ContentValues();
      Integer var5 = Integer.valueOf(0);
      var4.put("_defaultaccount", var5);
      int var6 = sTheOne.getDatabaseWrapper().update("accounts", var4, (String)null, (String[])null);
      if(DEBUG) {
         String var7 = "setDefaultAccountById>" + var1 + "," + var6;
         ll.d("MailProvider", var7);
      }

      ContentValues var8 = new ContentValues();
      Integer var9 = Integer.valueOf(1);
      var8.put("_defaultaccount", var9);
      Uri var10 = Uri.parse("content://mail/accounts/" + var1);
      Object var11 = null;
      Object var12 = null;

      try {
         var3.update(var10, var8, (String)var11, (String[])var12);
         AccountPool.getInstance(sTheOne.getContext()).setDefaultAccountById(var0, var1);
      } catch (RemoteException var15) {
         ;
      }
   }

   public static final int updateAccountCheckFreq(long var0, int var2) {
      MailCommon.setUpdateSyncSettingIntervalSecs(sTheOne.getContext(), var0, var2);
      ContentValues var3 = new ContentValues(1);
      Long var4 = Long.valueOf((long)var2);
      var3.put("_poll_frequency_number", var4);
      MailProvider.DatabaseWrapper var5 = sTheOne.getDatabaseWrapper();
      String var6 = "_id=" + var0;
      return var5.update("accounts", var3, var6, (String[])null);
   }

   public static final int updateAccountLastUpdateTime(long var0) {
      int var2;
      if(var0 <= 0L) {
         var2 = 0;
      } else {
         long var3 = System.currentTimeMillis();
         Account var5 = getAccount(var0);
         if(var5 != null) {
            var5.lastupdatetime = var3;
         }

         ContentValues var6 = new ContentValues(1);
         Long var7 = Long.valueOf(var3);
         var6.put("_lastupdatetime", var7);
         MailProvider.DatabaseWrapper var8 = sTheOne.getDatabaseWrapper();
         String var9 = "_id=" + var0;
         var2 = var8.update("accounts", var6, var9, (String[])null);
      }

      return var2;
   }

   public static final int updateAccountPollTime(long var0, long var2) {
      ContentValues var4 = new ContentValues(1);
      Long var5 = Long.valueOf(var2);
      var4.put("_nextfetchtime", var5);
      MailProvider.DatabaseWrapper var6 = sTheOne.getDatabaseWrapper();
      String var7 = "_id=" + var0;
      return var6.update("accounts", var4, var7, (String[])null);
   }

   public static final int updateAccountPollTimeAndMarkFetch(long var0, long var2) {
      ContentValues var4 = new ContentValues(1);
      Long var5 = Long.valueOf(var2);
      var4.put("_nextfetchtime", var5);
      Integer var6 = Integer.valueOf(1);
      var4.put("_fetchme", var6);
      MailProvider.DatabaseWrapper var7 = sTheOne.getDatabaseWrapper();
      String var8 = "_id=" + var0;
      return var7.update("accounts", var4, var8, (String[])null);
   }

   public static final int updateCharset(Uri var0, String var1) {
      if(DEBUG) {
         String var2 = "updateCharset>" + var0 + "," + var1;
         ll.d("MailProvider", var2);
      }

      ContentValues var3 = new ContentValues(1);
      var3.put("_charset", var1);
      MailProvider.DatabaseWrapper var4 = sTheOne.getDatabaseWrapper();
      StringBuilder var5 = (new StringBuilder()).append("_id=");
      long var6 = ContentUris.parseId(var0);
      String var8 = var5.append(var6).toString();
      int var9 = var4.update("messages", var3, var8, (String[])null);
      if(var9 > 0) {
         long var10 = ContentUris.parseId(var0);
         if(DEBUG) {
            String var12 = "put2>>" + var9 + "," + var10;
            ll.d("MailProvider", var12);
         }

         sTheOne.getContext().getContentResolver().notifyChange(var0, (ContentObserver)null, (boolean)0);
         if(DEBUG) {
            String var13 = "put2<<" + var9 + "," + var10;
            ll.d("MailProvider", var13);
         }
      }

      return var9;
   }

   public static final int updateFlags(Uri var0, int var1) {
      int var2;
      switch(sURLMatcher.match(var0)) {
      case 6:
         ContentValues var3 = new ContentValues(1);
         Integer var4 = Integer.valueOf(var1);
         var3.put("_flags", var4);
         MailProvider.DatabaseWrapper var5 = sTheOne.getDatabaseWrapper();
         StringBuilder var6 = (new StringBuilder()).append("_id=");
         long var7 = ContentUris.parseId(var0);
         String var9 = var6.append(var7).toString();
         int var10 = var5.update("accounts", var3, var9, (String[])null);
         if(var10 > 0) {
            long var11 = ContentUris.parseId(var0);
            sTheOne.getContext().getContentResolver().notifyChange(var0, (ContentObserver)null, (boolean)0);
         }

         var2 = var10;
         break;
      default:
         if(DEBUG) {
            ll.d("MailProvider", "updateFlags:unknown uri");
         }

         var2 = 0;
      }

      return var2;
   }

   public static int updateMessageDone(long var0, int var2, int var3) {
      Object[] var4 = new Object[1];
      Long var5 = Long.valueOf(var0);
      var4[0] = var5;
      String var6 = String.format("_id = \'%d\'", var4);
      if(DEBUG) {
         String var7 = "update: " + var6;
         ll.i("MailProvider", var7);
      }

      ContentValues var8 = new ContentValues();
      Integer var9 = Integer.valueOf(var2);
      var8.put("_done", var9);
      if(DEBUG) {
         int var10 = Log.i("MailProvider", "before update");
      }

      int var11;
      if(var3 == 4) {
         var11 = sTheOne.getDatabaseWrapper().update("easmessages", var8, var6, (String[])null);
      } else {
         var11 = sTheOne.getDatabaseWrapper().update("messages", var8, var6, (String[])null);
      }

      if(DEBUG) {
         int var12 = Log.i("MailProvider", "before notify");
      }

      ContentResolver var13 = sTheOne.getContext().getContentResolver();
      Uri var14 = Uri.parse("content://mail/msgDone/" + var0);
      var13.notifyChange(var14, (ContentObserver)null, (boolean)0);
      if(DEBUG) {
         int var15 = Log.i("MailProvider", "after notify");
      }

      return var11;
   }

   public static final int updateRead(Uri var0, int var1, boolean var2) {
      int var3 = sURLMatcher.match(var0);
      if(DEBUG) {
         String var4 = "updateRead>" + var0 + "," + var1;
         ll.d("MailProvider", var4);
      }

      int var5;
      switch(var3) {
      case 2:
         String var6 = "messages";
         if(DEBUG) {
            ll.d("MailProvider", "MESSAGE_ID>");
         }

         ContentValues var7 = new ContentValues(1);
         Integer var8 = Integer.valueOf(var1);
         var7.put("_read", var8);
         MailProvider.DatabaseWrapper var9 = sTheOne.getDatabaseWrapper();
         StringBuilder var10 = (new StringBuilder()).append("_id=");
         long var11 = ContentUris.parseId(var0);
         String var13 = var10.append(var11).toString();
         var1 = var9.update(var6, var7, var13, (String[])null);
         if(DEBUG) {
            String var14 = "put>>" + var1;
            ll.d("MailProvider", var14);
         }

         if(var1 > 0 && var2) {
            long var15 = ContentUris.parseId(var0);
            if(DEBUG) {
               String var17 = "put2>>" + var1 + "," + var15;
               ll.d("MailProvider", var17);
            }

            sTheOne.getContext().getContentResolver().notifyChange(var0, (ContentObserver)null, (boolean)0);
            ContentResolver var18 = sTheOne.getContext().getContentResolver();
            Uri var19 = sSummariesReadURI;
            var18.notifyChange(var19, (ContentObserver)null, (boolean)0);
            ContentResolver var20 = sTheOne.getContext().getContentResolver();
            Uri var21 = sAllMessageWithAccountURI;
            var20.notifyChange(var21, (ContentObserver)null, (boolean)0);
            if(DEBUG) {
               String var22 = "put3<<" + var1 + "," + var15;
               ll.d("MailProvider", var22);
            }
         }

         var5 = var1;
         break;
      default:
         if(DEBUG) {
            ll.d("MailProvider", "updateRead:unknown uri");
         }

         var5 = 0;
      }

      return var5;
   }

   public static int updateRetryCountToMessage(Context var0, long var1, int var3) {
      ContentValues var4 = new ContentValues();
      Integer var5 = Integer.valueOf(var3);
      var4.put("_retryCount", var5);
      ContentResolver var6 = var0.getContentResolver();
      Builder var7 = sNoNotifyMessagesURI.buildUpon();
      String var8 = String.valueOf(var1);
      Uri var9 = var7.appendEncodedPath(var8).build();
      return var6.update(var9, var4, (String)null, (String[])null);
   }

   private String whereWithId(long var1, String var3) {
      StringBuilder var4 = new StringBuilder(256);
      StringBuilder var5 = var4.append("_id=");
      var4.append(var1);
      if(var3 != null) {
         StringBuilder var7 = var4.append(" AND (");
         var4.append(var3);
         StringBuilder var9 = var4.append(')');
      }

      return var4.toString();
   }

   public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> param1) throws OperationApplicationException {
      // $FF: Couldn't be decompiled
   }

   protected boolean applyingBatch() {
      boolean var1;
      if(this.mApplyingBatch.get() != null && ((Boolean)this.mApplyingBatch.get()).booleanValue()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int bulkInsert(Uri param1, ContentValues[] param2) {
      // $FF: Couldn't be decompiled
   }

   public int delete(Uri param1, String param2, String[] param3) {
      // $FF: Couldn't be decompiled
   }

   public String getType(Uri var1) {
      String var2;
      switch(sURLMatcher.match(var1)) {
      case 1:
         var2 = "vnd.android.cursor.dir/mail";
         break;
      case 2:
      case 204:
         var2 = "vnd.android.cursor.item/mail";
         break;
      case 3:
         var2 = "vnd.android.cursor.dir/mail-summary";
         break;
      case 4:
         var2 = "vnd.android.cursor.item/mail-summary";
         break;
      case 5:
         var2 = "vnd.android.cursor.dir/mail-account";
         break;
      case 6:
         var2 = "vnd.android.cursor.item/mail-account";
         break;
      case 7:
         var2 = "vnd.android.cursor.dir/mail-part";
         break;
      case 15:
         var2 = "vnd.android.cursor.dir/mail-provider";
         break;
      case 16:
         var2 = "vnd.android.cursor.item/mail-provider";
         break;
      case 24:
         var2 = "vnd.android.cursor.dir/mail-box";
         break;
      case 31:
         var2 = "vnd.android.cursor.dir/mail-tracking";
         break;
      case 32:
         var2 = "vnd.android.cursor.item/mail-tracking";
         break;
      case 44:
         var2 = "vnd.android.cursor.item/mail-box";
         break;
      case 51:
      case 52:
         var2 = "vnd.android.cursor.item/email-history";
         break;
      case 200:
         var2 = "content://mail/AccountVerify";
         break;
      default:
         if(DEBUG) {
            ll.d("MailProvider", "gettype:Unknown URI");
         }

         var2 = "";
      }

      return var2;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      UriMatcher var3 = sURLMatcher;
      int var5 = var3.match(var1);
      ContentResolver var6 = this.getContext().getContentResolver();
      MailProvider.DatabaseWrapper var7 = this.getDatabaseWrapper();
      byte var9 = 1;
      ContentValues var10;
      long var12;
      Uri var34;
      if(var5 == var9) {
         if(var2 == null) {
            var10 = new ContentValues(1);
            Long var11 = Long.valueOf(System.currentTimeMillis());
            var10.put("_date", var11);
         } else {
            String var36 = "_date";
            if(!var2.containsKey(var36)) {
               var10 = new ContentValues(var2);
               Long var39 = Long.valueOf(System.currentTimeMillis());
               var10.put("_date", var39);
            } else {
               var10 = var2;
            }
         }

         var12 = var7.insert("messages", "_subject", var10);
         if(var12 > 0L) {
            Uri var14 = Uri.parse("content://mail/messages/" + var12);
            Object var18 = null;
            this.notifyChange(var6, var1, (ContentObserver)var18);
            Uri var19 = sSummariesURI;
            Object var23 = null;
            this.notifyChange(var6, var19, (ContentObserver)var23);
            Uri var24 = sMessageIdsURI;
            Object var28 = null;
            this.notifyChange(var6, var24, (ContentObserver)var28);
            Uri var29 = sAllMessageWithAccountURI;
            Object var33 = null;
            this.notifyChange(var6, var29, (ContentObserver)var33);
            var34 = var14;
            return var34;
         }
      } else {
         short var41 = 350;
         if(var5 == var41) {
            if(var2 == null) {
               var10 = new ContentValues(1);
               Long var42 = Long.valueOf(System.currentTimeMillis());
               var10.put("_date", var42);
            } else {
               String var67 = "_date";
               if(!var2.containsKey(var67)) {
                  var10 = new ContentValues(var2);
                  Long var70 = Long.valueOf(System.currentTimeMillis());
                  var10.put("_date", var70);
               } else {
                  var10 = var2;
               }
            }

            var12 = var7.insert("messages", "_subject", var10);
            if(var12 > 0L) {
               Uri var43 = Uri.parse("content://mail/messages/" + var12);
               Object var47 = null;
               this.notifyChange(var6, var1, (ContentObserver)var47);
               Uri var48 = sMessagesURI;
               Object var52 = null;
               this.notifyChange(var6, var48, (ContentObserver)var52);
               Uri var53 = sMessageIdsURI;
               Object var57 = null;
               this.notifyChange(var6, var53, (ContentObserver)var57);
               Uri var58 = sAllMessageWithAccountURI;
               Object var62 = null;
               this.notifyChange(var6, var58, (ContentObserver)var62);
               this.notifyCombinedAccountAndMailbox(var6, var1);
               var34 = var43;
               return var34;
            }
         } else {
            short var72 = 203;
            if(var5 == var72) {
               if(var2 == null) {
                  var10 = new ContentValues(1);
                  Long var73 = Long.valueOf(System.currentTimeMillis());
                  var10.put("_date", var73);
               } else {
                  String var85 = "_date";
                  if(!var2.containsKey(var85)) {
                     var10 = new ContentValues(var2);
                     Long var88 = Long.valueOf(System.currentTimeMillis());
                     var10.put("_date", var88);
                  } else {
                     var10 = var2;
                  }
               }

               var12 = var7.insert("searchSvrMessages", "_subject", var10);
               if(var12 > 0L) {
                  Uri var74 = Uri.parse("content://mail/searchSvrMessages/" + var12);
                  Object var78 = null;
                  this.notifyChange(var6, var1, (ContentObserver)var78);
                  Uri var79 = sMessageIdsURI;
                  Object var83 = null;
                  this.notifyChange(var6, var79, (ContentObserver)var83);
                  var34 = var74;
                  return var34;
               }
            } else {
               byte var90 = 45;
               if(var5 == var90) {
                  if(var2 == null) {
                     var10 = new ContentValues(1);
                     Long var91 = Long.valueOf(System.currentTimeMillis());
                     var10.put("_date", var91);
                  } else {
                     String var99 = "_date";
                     if(!var2.containsKey(var99)) {
                        var10 = new ContentValues(var2);
                        Long var102 = Long.valueOf(System.currentTimeMillis());
                        var10.put("_date", var102);
                     } else {
                        var10 = var2;
                     }
                  }

                  var12 = var7.insert("messages", "_subject", var10);
                  if(var12 > 0L) {
                     Uri var92 = Uri.parse("content://mail/messages/" + var12);
                     Uri var93 = sMessageIdsURI;
                     Object var97 = null;
                     this.notifyChange(var6, var93, (ContentObserver)var97);
                     var34 = var92;
                     return var34;
                  }
               } else {
                  byte var104 = 5;
                  if(var5 == var104) {
                     if(DEBUG) {
                        int var105 = Log.d("MailProvider", "accounts");
                     }

                     this.getContext().enforceCallingOrSelfPermission("com.htc.android.mail.permission.WRITE_ACCOUNT", "");
                     if(SyncConfig.isEASEnabled()) {
                        String var107 = "_provider";
                        String var108 = (String)var2.get(var107);
                        if(var108 != null) {
                           String var110 = "Exchange";
                           if(var108.equals(var110)) {
                              Integer var111 = Integer.valueOf(EASsyncSchedulePeakOn);
                              String var113 = "_peakonfrequency";
                              var2.put(var113, var111);
                              Integer var115 = Integer.valueOf(EASsyncSchedulePeakOff);
                              String var117 = "_peakofffrequency";
                              var2.put(var117, var115);
                           }
                        }
                     }

                     String var120 = "_providerGroup";
                     String var121 = (String)var2.get(var120);
                     Integer var125 = Integer.valueOf(this.getAccountEncodedColorIdx(var7, var121));
                     String var127 = "_colorIdx";
                     var2.put(var127, var125);
                     ContentValues var129 = new ContentValues(var2);
                     Context var132 = this.getContext();
                     this.encInformationifNeed(var132, var129);
                     var12 = var7.insert("accounts", "_username", var129);
                     if(var12 > 0L) {
                        Uri var136 = Uri.parse("content://mail/accounts/" + var12);
                        Object var140 = null;
                        this.notifyChange(var6, var1, (ContentObserver)var140);
                        Util.writeAccountCountToPref(this.getContext(), -1);
                        String var142 = "_protocol";
                        int var143 = var2.getAsInteger(var142).intValue();
                        if(var143 != 4 && var143 != 10) {
                           ArrayList var144 = (ArrayList)this.mAfterCommitRunnable.get();
                           if(var144 == null) {
                              var144 = new ArrayList();
                              this.mAfterCommitRunnable.set(var144);
                           }

                           MailProvider.1 var146 = new MailProvider.1(var12, var2, var143);
                           var144.add(var146);
                        }

                        int var148 = getAccountCount((boolean)0);
                        MULogMgr var149 = new MULogMgr();
                        byte var151 = 1;
                        if(var148 == var151) {
                           Context var152 = this.getContext();
                           byte var155 = 1;
                           var149.setAlarm(var152, (boolean)var155);
                        }

                        AccountPool var156 = AccountPool.getInstance(this.getContext());
                        Context var157 = this.getContext();
                        int var158 = var156.getAccountCount(var157, 4);
                        if(var148 - var158 == 1) {
                           Context var159 = this.getContext();
                           var149.manageShowMeLog(var159, var143);
                        }

                        var34 = var136;
                        return var34;
                     }
                  } else {
                     byte var164 = 113;
                     if(var5 == var164) {
                        String var166 = "globalSetting";
                        String var167 = "_glancePreview";
                        var12 = var7.insert(var166, var167, var2);
                        if(DEBUG) {
                           StringBuilder var169 = (new StringBuilder()).append("GLOBAL_SETTING_MAIL<").append(var12).append(",");
                           String var171 = var169.append(var2).toString();
                           ll.d("MailProvider", var171);
                        }

                        if(var12 > 0L) {
                           Uri var172 = Uri.parse("content://mail/globalSetting/" + var12);
                           Object var176 = null;
                           this.notifyChange(var6, var1, (ContentObserver)var176);
                           var34 = var172;
                           return var34;
                        }
                     } else {
                        byte var178 = 7;
                        if(var5 == var178) {
                           String var180 = "parts";
                           String var181 = "_mimetype";
                           if(var7.insert(var180, var181, var2) > 0L) {
                              StringBuilder var183 = (new StringBuilder()).append("content://mail/partsMsgId/");
                              String var185 = "_message";
                              String var186 = var2.getAsString(var185);
                              Uri var187 = Uri.parse(var183.append(var186).toString());
                              Object var191 = null;
                              this.notifyChange(var6, var1, (ContentObserver)var191);
                              var34 = var187;
                              return var34;
                           }
                        } else {
                           short var193 = 205;
                           if(var5 == var193) {
                              String var195 = "searchSvrParts";
                              String var196 = "_mimetype";
                              if(var7.insert(var195, var196, var2) > 0L) {
                                 StringBuilder var198 = (new StringBuilder()).append("content://mail/partsMsgId/");
                                 String var200 = "_message";
                                 String var201 = var2.getAsString(var200);
                                 Uri var202 = Uri.parse(var198.append(var201).toString());
                                 Object var206 = null;
                                 this.notifyChange(var6, var1, (ContentObserver)var206);
                                 Uri var207 = sSearchSvrPartsURI;
                                 Object var211 = null;
                                 this.notifyChange(var6, var207, (ContentObserver)var211);
                                 var34 = var202;
                                 return var34;
                              }
                           } else {
                              byte var213 = 13;
                              if(var5 == var213) {
                                 if(DEBUG) {
                                    int var214 = Log.d("MailProvider", "iparts");
                                 }

                                 String var216 = "iparts";
                                 String var217 = "_mimetype";
                                 if(var7.insert(var216, var217, var2) > 0L) {
                                    StringBuilder var219 = (new StringBuilder()).append("content://mail/iparts/");
                                    String var221 = "_message";
                                    String var222 = var2.getAsString(var221);
                                    Uri var223 = Uri.parse(var219.append(var222).toString());
                                    Object var227 = null;
                                    this.notifyChange(var6, var1, (ContentObserver)var227);
                                    var34 = var223;
                                    return var34;
                                 }
                              } else {
                                 byte var229 = 31;
                                 if(var5 == var229) {
                                    if(DEBUG) {
                                       int var230 = Log.d("MailProvider", "easTracking");
                                    }

                                    String var232 = "easTracking";
                                    String var233 = "_message";
                                    var12 = var7.insert(var232, var233, var2);
                                    if(var12 > 0L) {
                                       Uri var235 = Uri.parse("content://mail/easTracking/" + var12);
                                       Object var239 = null;
                                       this.notifyChange(var6, var1, (ContentObserver)var239);
                                       var34 = var235;
                                       return var34;
                                    }

                                    if(DEBUG) {
                                       int var240 = Log.d("MailProvider", "insert easTracking table fail");
                                    }
                                 } else {
                                    byte var242 = 24;
                                    if(var5 == var242) {
                                       if(DEBUG) {
                                          int var243 = Log.d("MailProvider", "mailboxs");
                                       }

                                       String var245 = "mailboxs";
                                       String var246 = "_accountid";
                                       var12 = var7.insert(var245, var246, var2);
                                       if(var12 > 0L) {
                                          Uri var248 = Uri.parse("content://mail/mailboxs/" + var12);
                                          Object var252 = null;
                                          this.notifyChange(var6, var1, (ContentObserver)var252);
                                          var34 = var248;
                                          return var34;
                                       }
                                    } else {
                                       byte var254 = 38;
                                       if(var5 == var254) {
                                          String var256 = "pending_requests";
                                          String var257 = "_accountId";
                                          var12 = var7.insert(var256, var257, var2);
                                          if(var12 > 0L) {
                                             Uri var259 = Uri.parse("content://mail/pending_requests/" + var12);
                                             Object var263 = null;
                                             this.notifyChange(var6, var1, (ContentObserver)var263);
                                             var34 = var259;
                                             return var34;
                                          }
                                       } else {
                                          byte var265 = 42;
                                          if(var5 == var265) {
                                             if(DEBUG) {
                                                ll.d("MailProvider", "notification insert");
                                             }

                                             String var267 = "notification";
                                             String var268 = "_title";
                                             var12 = var7.insert(var267, var268, var2);
                                             if(DEBUG) {
                                                String var270 = "notification insert rowi=" + var12;
                                                ll.d("MailProvider", var270);
                                             }

                                             if(var12 > 0L) {
                                                Uri var271 = Uri.parse("content://mail/notification/" + var12);
                                                Object var275 = null;
                                                this.notifyChange(var6, var1, (ContentObserver)var275);
                                                MailEventBroadcaster var276 = Mail.mMailEvent;
                                                String var278 = "_accountid";
                                                long var279 = var2.getAsLong(var278).longValue();
                                                var276.setMailSyncFinish(var279);
                                                MailEventBroadcaster var281 = Mail.mMailEvent;
                                                Context var282 = this.getContext();
                                                var281.flush(var282);
                                                var34 = var271;
                                                return var34;
                                             }
                                          } else {
                                             byte var284 = 51;
                                             if(var5 == var284) {
                                                String var286 = "email_history";
                                                String var287 = "data";
                                                var12 = var7.insert(var286, var287, var2);
                                                if(var12 > 0L) {
                                                   var34 = Uri.parse("content://mail/email_history/" + var12);
                                                   return var34;
                                                }
                                             } else {
                                                short var290 = 600;
                                                if(var5 == var290) {
                                                   String var292 = "people_db.groups";
                                                   String var293 = "_id";
                                                   var12 = var7.insert(var292, var293, var2);
                                                   if(var12 > 0L) {
                                                      String var295 = String.valueOf(var12);
                                                      var34 = Uri.withAppendedPath(var1, var295);
                                                      return var34;
                                                   }
                                                } else {
                                                   short var299 = 601;
                                                   if(var5 == var299) {
                                                      String var301 = "people_db.data";
                                                      String var302 = "_id";
                                                      byte var304 = 4;
                                                      var12 = var7.insertWithOnConflict(var301, var302, var2, var304);
                                                      if(var12 > 0L) {
                                                         String var305 = String.valueOf(var12);
                                                         var34 = Uri.withAppendedPath(var1, var305);
                                                         return var34;
                                                      }
                                                   } else {
                                                      byte var309 = 15;
                                                      if(var5 == var309) {
                                                         String var311 = "providers";
                                                         Object var312 = null;
                                                         var12 = var7.insert(var311, (String)var312, var2);
                                                         if(var12 > 0L) {
                                                            var34 = Uri.parse("content://mail/providers/" + var12);
                                                            return var34;
                                                         }
                                                      } else {
                                                         byte var315 = 36;
                                                         if(var5 == var315) {
                                                            String var317 = "providersettings";
                                                            Object var318 = null;
                                                            var12 = var7.insert(var317, (String)var318, var2);
                                                            if(var12 > 0L) {
                                                               var34 = Uri.parse("content://mail/providersettings/" + var12);
                                                               return var34;
                                                            }
                                                         } else if(DEBUG) {
                                                            int var320 = Log.d("MailProvider", "insert(): Can\'t find table");
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
                        }
                     }
                  }
               }
            }
         }
      }

      var34 = null;
      return var34;
   }

   public boolean onCreate() {
      Context var1 = this.getContext();
      sTheOne = this;
      BackupManager var2 = new BackupManager(var1);
      this.mBackupManager = var2;
      return true;
   }

   public ParcelFileDescriptor openFile(Uri param1, String param2) throws FileNotFoundException {
      // $FF: Couldn't be decompiled
   }

   public Cursor query(Uri param1, String[] param2, String param3, String[] param4, String param5) {
      // $FF: Couldn't be decompiled
   }

   public final int update(Uri param1, ContentValues param2, String param3, String[] param4) {
      // $FF: Couldn't be decompiled
   }

   private static class DatabaseHelper extends SQLiteOpenHelper {

      static HtcAdjustableCursorFactory mAdjustableFactory = new HtcAdjustableCursorFactory(4096);
      Context mContext;


      public DatabaseHelper(Context var1) {
         HtcAdjustableCursorFactory var2 = mAdjustableFactory;
         super(var1, "mail.db", var2, 116);
         this.mContext = var1;
      }

      private void checkMailInSystemAccount(SQLiteDatabase param1) {
         // $FF: Couldn't be decompiled
      }

      private void createEasDefaultMailbox(SQLiteDatabase param1, long param2) {
         // $FF: Couldn't be decompiled
      }

      private void loadAccounts(SQLiteDatabase var1) {
         HtcMailCustomization var2 = new HtcMailCustomization();
         Context var3 = MailProvider.sTheOne.getContext();
         Bundle var4 = var2.getMailCustomizationData(var3);
         if(var4 != null) {
            Context var5 = MailProvider.sTheOne.getContext();
            var2.preInstallAccount(var5, var1, var4);
         }
      }

      @Deprecated
      public static void loadCustSignature(Context var0) {
         Bundle var1 = (new HtcMailCustomization()).getMailCustomizationData(var0);
         if(var1 != null) {
            Bundle var2 = var1.getBundle("mail_signature_localization");
            if(var2 != null) {
               if(var2.size() > 0) {
                  String var3 = "plenty_set";
                  String var4 = "locale";
                  int var5 = 0;

                  while(true) {
                     int var6 = var2.size();
                     if(var5 >= var6) {
                        return;
                     }

                     StringBuilder var7 = (new StringBuilder()).append(var3);
                     int var8 = var5 + 1;
                     String var9 = var7.append(var8).toString();
                     Bundle var10 = var2.getBundle(var9);
                     String var11 = var10.getString(var4);
                     String var12 = var10.getString("signature");
                     if(MailProvider.DEBUG) {
                        String var13 = "locale:" + var11 + ", signature:" + var12;
                        ll.d("MailProvider", var13);
                     }

                     if(var11 != null && var12 != null) {
                        Util.writeSignatureToPref(var0, var11, var12);
                     }

                     ++var5;
                  }
               }
            }
         }
      }

      private void loadMultiSettings(SQLiteDatabase param1) {
         // $FF: Couldn't be decompiled
      }

      private void loadProviderSettings(SQLiteDatabase param1) {
         // $FF: Couldn't be decompiled
      }

      private void loadSettings(SQLiteDatabase param1) {
         // $FF: Couldn't be decompiled
      }

      private void upgrade_to_version_106(SQLiteDatabase var1) {
         var1.execSQL("ALTER TABLE easTracking ADD COLUMN _accountId INTEGER DEFAULT 0");
         var1.execSQL("CREATE INDEX IF NOT EXISTS IDX_easTracking_accountId ON easTracking (_accountId)");
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(4);
         var2[0] = var3;
         String var4 = String.format("_del=-1 AND _protocol=\'%d\'", var2);
         String[] var5 = new String[]{"_id"};
         Object var7 = null;
         Object var8 = null;
         Object var9 = null;
         Cursor var10 = var1.query("accounts", var5, var4, (String[])null, (String)var7, (String)var8, (String)var9);
         if(var10 != null && var10.moveToFirst()) {
            long var11 = var10.getLong(0);
            ContentValues var13 = new ContentValues();
            Long var14 = Long.valueOf(var11);
            var13.put("_accountId", var14);
            var1.update("easTracking", var13, (String)null, (String[])null);
         }

         if(var10 != null && !var10.isClosed()) {
            var10.close();
         }

         var1.execSQL("ALTER TABLE accounts ADD COLUMN _syncWithServer INTEGER DEFAULT 0");
      }

      public Uri addCallerIsSyncAdapterParameter(Uri var1) {
         return var1.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").build();
      }

      public void onCreate(SQLiteDatabase var1) {
         this.loadSettings(var1);
         MailProvider.createAccountTable(var1);
         MailProvider.createGlobalTable(var1);
         MailProvider.createSearchSvrMessageTable(var1);
         MailProvider.createSearchSvrPartsTable(var1);
         MailProvider.createMessageTable(var1);
         MailProvider.createPartsTable(var1);
         var1.execSQL("CREATE TABLE providers (_id INTEGER NOT NULL PRIMARY KEY,_provider TEXT NO NULL,_domain TEXT,_inprotocol INTEGER DEFAULT 0,_description TEXT,_resid INTEGER);");
         var1.execSQL("CREATE TABLE providersettings (_id INTEGER NOT NULL PRIMARY KEY,_provider TEXT NO NULL,_domain TEXT,_inserver TEXT,_inport INTEGER NO NULL,_outserver TEXT,_outport INTEGER NO NULL,_inprotocol INTEGER,_useSSLin INTEGER DEFAULT 1,_useSSLout INTEGER DEFAULT 1,_smtpauth INTEGER DEFAULT 1,_deleteNonExistMail INTEGER DEFAULT -1,_providerGroup TEXT DEFAULT NULL);");
         MailProvider.createMailBoxTable(var1);
         var1.execSQL("CREATE TABLE IF NOT EXISTS pending_requests ( _id INTEGER NOT NULL PRIMARY KEY, _accountId INTEGER NOT NULL, _messageId INTEGER, _msgId TEXT, _request INTEGER NOT NULL, _fromMailboxId INTEGER,  _uid TEXT,  _toMailboxId INTEGER);");
         var1.execSQL("CREATE TABLE easTracking (_id INTEGER NOT NULL PRIMARY KEY,_message INTEGER NOT NULL,_uid TEXT,_collectionId TEXT,_modify INTEGER DEFAULT 0,_delete INTEGER DEFAULT 0,_move INTEGER DEFAULT 0,_param TEXT,_meetingResp INTEGER DEFAULT 0,_calendarEventId INTEGER DEFAULT 0,_accountId INTEGER DEFAULT 0);");
         var1.execSQL("CREATE INDEX IDX_easTracking_uid ON easTracking (_uid);");
         var1.execSQL("CREATE INDEX IDX_easTracking_message ON easTracking (_message);");
         var1.execSQL("CREATE INDEX IDX_easTracking_accountId ON easTracking (_accountId);");
         var1.execSQL("CREATE TABLE notification (_id INTEGER NOT NULL PRIMARY KEY,_accountid INTEGER NOT NULL,_date INTEGER,_title TEXT,_desc TEXT,_type INTEGER,_messageid INTEGER,_number INTEGER);");
         var1.execSQL("CREATE TABLE email_history (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,data TEXT,label TEXT);");
         this.loadProviderSettings(var1);
         this.loadMultiSettings(var1);
      }

      public void onUpgrade(SQLiteDatabase param1, int param2, int param3) {
         // $FF: Couldn't be decompiled
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            File[] var1 = DatabaseHelper.this.mContext.getDir("mail", 0).listFiles();
            File[] var2;
            int var3;
            int var4;
            if(var1 != null) {
               var2 = var1;
               var3 = var1.length;

               for(var4 = 0; var4 < var3; ++var4) {
                  boolean var5 = var2[var4].delete();
               }
            }

            File[] var6 = DatabaseHelper.this.mContext.getDir("mail_eas", 0).listFiles();
            if(var6 != null) {
               var2 = var6;
               var3 = var6.length;

               for(var4 = 0; var4 < var3; ++var4) {
                  boolean var7 = var2[var4].delete();
               }

            }
         }
      }
   }

   class DatabaseWrapper {

      private Context mContext;
      private MailProvider.DatabaseHelper mDbHelper;


      public DatabaseWrapper(Context var2) {
         MailProvider.DatabaseHelper var3 = new MailProvider.DatabaseHelper(var2);
         this.mDbHelper = var3;
         this.mContext = var2;
      }

      // $FF: synthetic method
      static SQLiteDatabase access$200(MailProvider.DatabaseWrapper var0) {
         return var0.getWritableDatabase();
      }

      private SQLiteDatabase getReadableDatabase() {
         return this.mDbHelper.getReadableDatabase();
      }

      private SQLiteDatabase getWritableDatabase() {
         return this.mDbHelper.getWritableDatabase();
      }

      public int delete(String var1, String var2, String[] var3) {
         if(MailProvider.DEBUG) {
            StringBuilder var4 = (new StringBuilder()).append("delete() table: ");
            StringBuilder var6 = var4.append(var1).append(" whereClause: ");
            String var8 = var6.append(var2).toString();
            ll.d("MailProvider", var8);
         }

         SQLiteDatabase var9 = this.getWritableDatabase();
         String var10 = Environment.getDataDirectory().getPath();
         StatFs var11 = new StatFs(var10);
         long var12 = (long)var11.getBlockSize();
         long var14 = (long)var11.getAvailableBlocks() * var12;
         String var16 = var9.getPath();
         long var17 = (new File(var16)).length();
         int var26;
         if(var14 < var17) {
            if(MailProvider.DEBUG) {
               StringBuilder var19 = (new StringBuilder()).append("try delete2 ");
               StringBuilder var22 = var19.append(var17).append("/");
               String var25 = var22.append(var14).toString();
               ll.d("MailProvider", var25);
            }

            var26 = this.delete2(var1, var2, var3);
         } else {
            int var27 = 0;

            label32: {
               int var32;
               try {
                  if(!var9.isOpen()) {
                     var26 = var27;
                     return var26;
                  }

                  var32 = var9.delete(var1, var2, var3);
               } catch (IllegalStateException var42) {
                  StringBuilder var34 = (new StringBuilder()).append("IllegalStateException:");
                  String var36 = var34.append(var42).toString();
                  int var37 = Log.d("MailProvider", var36);
                  var26 = var27;
                  return var26;
               } catch (Exception var43) {
                  StringBuilder var39 = (new StringBuilder()).append("delete exception: ");
                  String var41 = var39.append(var43).toString();
                  ll.d("MailProvider", var41);
                  break label32;
               }

               var27 = var32;
            }

            var26 = var27;
         }

         return var26;
      }

      public int delete2(String param1, String param2, String[] param3) {
         // $FF: Couldn't be decompiled
      }

      public long insert(String var1, String var2, ContentValues var3) {
         return this.insertWithOnConflict(var1, var2, var3, 0);
      }

      public long insertWithOnConflict(String param1, String param2, ContentValues param3, int param4) {
         // $FF: Couldn't be decompiled
      }

      public boolean isDBLocked() {
         SQLiteDatabase var1 = this.getReadableDatabase();
         boolean var2;
         if(!var1.isDbLockedByOtherThreads() && !var1.isDbLockedByCurrentThread()) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public Cursor query(String var1, String[] var2, String var3, String[] var4, String var5, String var6, String var7) {
         SQLiteDatabase var8 = this.getReadableDatabase();
         return var8.query(var1, var2, var3, var4, var5, var6, var7);
      }

      public Cursor query(boolean var1, String var2, String[] var3, String var4, String[] var5, String var6, String var7, String var8, String var9) {
         SQLiteDatabase var10 = this.getReadableDatabase();
         return var10.query(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }

      public Cursor rawQuery(String var1, String[] var2) {
         SQLiteDatabase var3 = this.getReadableDatabase();
         Cursor var4;
         if(!var3.isOpen()) {
            var4 = null;
         } else {
            var4 = var3.rawQuery(var1, var2);
         }

         return var4;
      }

      public int update(String param1, ContentValues param2, String param3, String[] param4) {
         // $FF: Couldn't be decompiled
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final int val$protocol;
      // $FF: synthetic field
      final long val$rowId;
      // $FF: synthetic field
      final ContentValues val$values;


      1(long var2, ContentValues var4, int var5) {
         this.val$rowId = var2;
         this.val$values = var4;
         this.val$protocol = var5;
      }

      public void run() {
         Context var1 = MailProvider.this.getContext();
         long var2 = this.val$rowId;
         String var4 = (String)this.val$values.get("_emailaddress");
         String var5 = (String)this.val$values.get("_password");
         int var6 = this.val$protocol;
         int var7 = this.val$values.getAsInteger("_poll_frequency_number").intValue();
         int var8 = this.val$values.getAsInteger("_refreshMailWhenOpenFolder").intValue();
         MailProvider.addSyncAccount(var1, var2, var4, var5, var6, var7, var8);
      }
   }
}
