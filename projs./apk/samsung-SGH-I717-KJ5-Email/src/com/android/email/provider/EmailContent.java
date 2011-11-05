package com.android.email.provider;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.android.email.Email;
import com.android.email.mail.Snippet;
import com.android.exchange.SyncScheduleData;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.UUID;

public abstract class EmailContent {

   public static final String ADD_COLUMN_NAME = "add";
   public static final String AUTHORITY = "com.android.email.provider";
   public static final Uri CONTENT_URI = Uri.parse("content://com.android.email.provider");
   public static final int CONTROLED_SYNC_ID_COLUMN = 0;
   public static final int CONTROLED_SYNC_INTERVAL_COLUMN = 1;
   public static final String[] CONTROLED_SYNC_PROJECTION;
   private static final String[] COUNT_COLUMNS;
   public static final String FIELD_COLUMN_NAME = "field";
   public static final String[] ID_PROJECTION;
   public static final int ID_PROJECTION_COLUMN = 0;
   private static final String ID_SELECTION = "_id =?";
   private static final int NOT_SAVED = 255;
   public static final String RECORD_ID = "_id";
   public static final String SYNC_INTERVAL_COLUMN = "syncInterval";
   public static final String TAG = "EmailContent >>";
   public Uri mBaseUri;
   public long mId;
   private Uri mUri;


   static {
      String[] var0 = new String[]{"count(*)"};
      COUNT_COLUMNS = var0;
      String[] var1 = new String[]{"_id"};
      ID_PROJECTION = var1;
      String[] var2 = new String[]{"_id", "syncInterval"};
      CONTROLED_SYNC_PROJECTION = var2;
   }

   private EmailContent() {
      this.mUri = null;
      this.mId = 65535L;
   }

   // $FF: synthetic method
   EmailContent(EmailContent.1 var1) {
      this();
   }

   public static int count(Context param0, Uri param1, String param2, String[] param3) {
      // $FF: Couldn't be decompiled
   }

   public static int delete(Context var0, Uri var1, long var2) {
      ContentResolver var4 = var0.getContentResolver();
      Uri var5 = ContentUris.withAppendedId(var1, var2);
      return var4.delete(var5, (String)null, (String[])null);
   }

   public static <T extends EmailContent> T getContent(Cursor var0, Class<T> var1) {
      EmailContent var5;
      EmailContent var6;
      label23: {
         try {
            EmailContent var2 = (EmailContent)var1.newInstance();
            long var3 = var0.getLong(0);
            var2.mId = var3;
            var5 = var2.restore(var0);
            break label23;
         } catch (IllegalAccessException var7) {
            var7.printStackTrace();
         } catch (InstantiationException var8) {
            var8.printStackTrace();
         }

         var6 = null;
         return var6;
      }

      var6 = var5;
      return var6;
   }

   public static int update(Context var0, Uri var1, long var2, ContentValues var4) {
      ContentResolver var5 = var0.getContentResolver();
      Uri var6 = ContentUris.withAppendedId(var1, var2);
      return var5.update(var6, var4, (String)null, (String[])null);
   }

   public Uri getUri() {
      if(this.mUri == null) {
         Uri var1 = this.mBaseUri;
         long var2 = this.mId;
         Uri var4 = ContentUris.withAppendedId(var1, var2);
         this.mUri = var4;
      }

      return this.mUri;
   }

   public boolean isSaved() {
      boolean var1;
      if(this.mId != 65535L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract <T extends EmailContent> T restore(Cursor var1);

   public Uri save(Context var1) {
      if(this.isSaved()) {
         throw new UnsupportedOperationException();
      } else {
         ContentResolver var2 = var1.getContentResolver();
         Uri var3 = this.mBaseUri;
         ContentValues var4 = this.toContentValues();
         Uri var5 = var2.insert(var3, var4);
         long var6 = Long.parseLong((String)var5.getPathSegments().get(1));
         this.mId = var6;
         return var5;
      }
   }

   public abstract ContentValues toContentValues();

   public int update(Context var1, ContentValues var2) {
      if(!this.isSaved()) {
         throw new UnsupportedOperationException();
      } else {
         ContentResolver var3 = var1.getContentResolver();
         Uri var4 = this.getUri();
         return var3.update(var4, var2, (String)null, (String[])null);
      }
   }

   public interface MailboxCBColumns {

      String ID = "_id";
      String MAILBOX_KEY = "mailboxKey";
      String SEVEN_MAILBOX_KEY = "sevenMailboxKey";
      String SYNC_FLAG = "syncFlag";
      String TYPE_MSG = "typeMsg";

   }

   public interface MessageCBColumns {

      String ID = "_id";
      String MESSAGE_KEY = "messageKey";
      String MISSING_BODY = "missingBody";
      String MISSING_HTML_BODY = "missingHtmlBody";
      String SEVEN_ACCOUNT_KEY = "sevenAccountKey";
      String SEVEN_MESSAGE_KEY = "sevenMessageKey";
      String TYPE_MSG = "typeMsg";
      String UNK_ENCODING = "unkEncoding";

   }

   public interface CertificateCacheColumns {

      String CERTIFICATE = "certificate";
      Uri CONTENT_URI;
      String EMAIL = "email";
      String ID = "_id";
      String TABLE_NAME = "CertificateCache";


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/certificateCache").toString());
      }
   }

   public static final class Account extends EmailContent implements EmailContent.AccountColumns, Parcelable {

      public static final Uri ADD_TO_FIELD_URI;
      public static final int CALENDAR_SYNC_WINDOW_1_MONTH = 5;
      public static final int CALENDAR_SYNC_WINDOW_2_WEEKS = 4;
      public static final int CALENDAR_SYNC_WINDOW_3_MONTH = 6;
      public static final int CALENDAR_SYNC_WINDOW_6_MONTH = 7;
      public static final int CALENDAR_SYNC_WINDOW_ALL = 0;
      public static final int CHECK_INTERVAL_15_MINS = 15;
      public static final int CHECK_INTERVAL_1HOUR = 60;
      public static final int CHECK_INTERVAL_NEVER = 255;
      public static final int CHECK_INTERVAL_PUSH = 254;
      public static final int CHECK_ROAMING_MANUAL = 0;
      public static final int CHECK_ROAMING_SYNC_SCHEDULE = 1;
      public static final int CONTENT_ATTACHMENT_ENABLED = 37;
      public static final int CONTENT_BLOCKDEVICE_COLUMN = 45;
      public static final int CONTENT_CALENDAR_SYNC_LOOKBACK_COLUMN = 26;
      public static final int CONTENT_CBA_CERTIFICATE_ALIAS_COLUMN = 47;
      public static final int CONTENT_COMPATIBILITY_UUID_COLUMN = 10;
      public static final int CONTENT_CONFLICT_RESOLUTION_COLUMN = 46;
      public static final int CONTENT_CONVERSATION_MODE_COLUMN = 42;
      public static final int CONTENT_DAYS = 33;
      public static final int CONTENT_DISPLAY_NAME_COLUMN = 1;
      public static final int CONTENT_EMAIL_ADDRESS_COLUMN = 2;
      public static final int CONTENT_EMAIL_SIZE_COLUMN = 18;
      public static final int CONTENT_FLAGS_COLUMN = 8;
      public static final int CONTENT_HOST_AUTH_KEY_RECV_COLUMN = 6;
      public static final int CONTENT_HOST_AUTH_KEY_SEND_COLUMN = 7;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_IS_DEFAULT_COLUMN = 9;
      public static final int CONTENT_MAILBOX_TYPE_COLUMN = 1;
      public static final int CONTENT_NEW_MESSAGE_COUNT_COLUMN = 14;
      public static final int CONTENT_OFF_PEAK_SCHEDULE_COLUMN = 24;
      public static final int CONTENT_OFF_PEAK_TIME = 32;
      public static final int CONTENT_PEAK_DAYS_COLUMN = 20;
      public static final int CONTENT_PEAK_END_MINUTE_COLUMN = 22;
      public static final int CONTENT_PEAK_END_TIME = 35;
      public static final int CONTENT_PEAK_SCHEDULE_COLUMN = 23;
      public static final int CONTENT_PEAK_START_MINUTE_COLUMN = 21;
      public static final int CONTENT_PEAK_START_TIME = 34;
      public static final int CONTENT_PEAK_TIME = 31;
      public static final int CONTENT_POLICY_KEY_COLUMN = 19;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_PROTOCOL_VERSION_COLUMN = 13;
      public static final int CONTENT_RINGTONE_URI_COLUMN = 12;
      public static final int CONTENT_ROAMING_SCHEDULE_COLUMN = 25;
      public static final int CONTENT_SECURITY_FLAGS_COLUMN = 15;
      public static final int CONTENT_SECURITY_SYNC_KEY_COLUMN = 16;
      public static final int CONTENT_SENDER_NAME_COLUMN = 11;
      public static final int CONTENT_SEVEN_ACCOUNT_KEY_COLUMN = 28;
      public static final int CONTENT_SIGNATURE_COLUMN = 17;
      public static final int CONTENT_SIZE_LIMIT_COLUMN = 29;
      public static final int CONTENT_SMIME_OPTIONS_ENCRYPTION_ALGORITHM_COLUMN = 41;
      public static final int CONTENT_SMIME_OPTIONS_FLAGS_COLUMN = 39;
      public static final int CONTENT_SMIME_OPTIONS_SIGN_ALGORITHM_COLUMN = 40;
      public static final int CONTENT_SMIME_OWN_CERTIFICATE_ALIAS_COLUMN = 38;
      public static final int CONTENT_SYNC_INTERVAL_COLUMN = 5;
      public static final int CONTENT_SYNC_KEY_COLUMN = 3;
      public static final int CONTENT_SYNC_LOOKBACK_COLUMN = 4;
      public static final int CONTENT_TEXTPREVIEW_SIZE_COLUMN = 43;
      public static final int CONTENT_TIME_LIMIT_COLUMN = 30;
      public static final int CONTENT_TYPE_MESSAGE_COLUMN = 27;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_WHILE_ROAMING = 36;
      public static final Creator<EmailContent.Account> CREATOR;
      private static final String[] DEFAULT_ID_PROJECTION;
      public static final int DEFAULT_PEAK_DAYS = 62;
      public static final int DEFAULT_PEAK_END_MINUTE = 1020;
      public static final int DEFAULT_PEAK_START_MINUTE = 480;
      public static final int DELETE_POLICY_7DAYS = 1;
      public static final int DELETE_POLICY_NEVER = 0;
      public static final int DELETE_POLICY_ON_DELETE = 2;
      public static final int DEVICE_INFO_SENT_COLUMN = 44;
      public static int DEVICE_IS_ALLOWED;
      public static int DEVICE_IS_BLOCKED;
      public static int DEVICE_IS_QUARANTINED;
      public static final double EX_PROTOCOL_VERSION_12_0 = 12.0D;
      public static final double EX_PROTOCOL_VERSION_12_1 = 12.1D;
      public static final double EX_PROTOCOL_VERSION_2_0 = 2.0D;
      public static final double EX_PROTOCOL_VERSION_2_5 = 2.5D;
      public static final int FLAGS_ADD_SIGNATURE = 2048;
      public static final int FLAGS_BCC_MYSELF = 256;
      public static final int FLAGS_CC_MYSELF = 128;
      public static final int FLAGS_DELETE_POLICY_MASK = 12;
      public static final int FLAGS_DELETE_POLICY_SHIFT = 2;
      public static final int FLAGS_INCOMPLETE = 16;
      public static final int FLAGS_NOTIFY_NEW_MAIL = 1;
      public static final int FLAGS_ODE_HOLD = 1024;
      public static final int FLAGS_SECURITY_HOLD = 32;
      public static final int FLAGS_SMS_SYNC = 512;
      public static final int FLAGS_VIBRATE_ALWAYS = 2;
      public static final int FLAGS_VIBRATE_WHEN_SILENT = 64;
      public static final int FLAG_DEVICE_INFO_SENT = 1;
      public static final String[] ID_TYPE_PROJECTION;
      public static final String MAILBOX_SELECTION = "mailboxKey =?";
      public static final int SMIME_ENCRYPT_ALL_SHIFT = 0;
      public static final int SMIME_SIGN_ALL_SHIFT = 1;
      public static final int SYNC_WINDOW_USER = 255;
      public static final String TABLE_NAME = "Account";
      public static final String UNREAD_COUNT_SELECTION = "mailboxKey =? and flagRead= 0";
      public static final String UUID_SELECTION = "compatibilityUuid =?";
      public EmailContent.AccountCB accountCB;
      public long mAccountKey;
      public int mAttachmentEnabled;
      public int mCalendarSyncLookback = 4;
      public String mCbaCertificateAlias;
      public String mCompatibilityUuid;
      public int mConflictFlags;
      public int mConversationMode;
      public String mDays;
      public int mDeviceInfoSent;
      public String mDisplayName;
      public String mEmailAddress;
      public int mEmailSize;
      public int mFlags;
      public long mHostAuthKeyRecv;
      public long mHostAuthKeySend;
      public transient EmailContent.HostAuth mHostAuthRecv;
      public transient EmailContent.HostAuth mHostAuthSend;
      public boolean mIsDefault;
      public boolean mIsInPeakPeriod;
      public int mNewMessageCount;
      public int mOffPeakTime;
      public String mPeakEndTime;
      public String mPeakStartTime;
      public int mPeakTime;
      public String mPolicyKey;
      public String mProtocolVersion;
      public String mRingtoneUri;
      public int mSecurityFlags;
      public String mSecuritySyncKey;
      public String mSenderName;
      public long mSevenAccountKey;
      public String mSignature;
      public long mSizeLimit;
      public boolean mSmimeEncryptAll;
      public int mSmimeEncryptionAlgorithm;
      public String mSmimeOwnCertificateAlias;
      public int mSmimeSignAlgorithm;
      public boolean mSmimeSignAll;
      public int mSyncInterval;
      public String mSyncKey;
      public int mSyncLookback;
      private SyncScheduleData mSyncScheduleData;
      public int mTextPreviewSize;
      public long mTimeLimit;
      public int mTypeMsg;
      public int mWhileroaming;
      public int mdeviceBlockedType;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/account").toString());
         StringBuilder var2 = new StringBuilder();
         Uri var3 = EmailContent.CONTENT_URI;
         ADD_TO_FIELD_URI = Uri.parse(var2.append(var3).append("/accountIdAddToField").toString());
         DEVICE_IS_ALLOWED = 0;
         DEVICE_IS_BLOCKED = 1;
         DEVICE_IS_QUARANTINED = 2;
         String[] var4 = new String[]{"_id", "displayName", "emailAddress", "syncKey", "syncLookback", "syncInterval", "hostAuthKeyRecv", "hostAuthKeySend", "flags", "isDefault", "compatibilityUuid", "senderName", "ringtoneUri", "protocolVersion", "newMessageCount", "securityFlags", "securitySyncKey", "signature", "emailsize", "policyKey", "peakDays", "peakStartMinute", "peakEndMinute", "peakSchedule", "offPeakSchedule", "roamingSchedule", "calendarSyncLookback", "typeMsg", "sevenAccountKey", "sizeLimit", "timeLimit", "peakTime", "offPeakTime", "days", "peakStartTime", "peakEndTime", "whileRoaming", "attachmentEnabled", "smimeOwnCertificateAlias", "smimeOptionsFlags", "smimeSignAlgorithm", "smimeEncryptionAlgorithm", "conversationMode", "textPreview", "deviceInfoSent", "deviceBlockedType", "conflict", "cbaCertificateAlias"};
         CONTENT_PROJECTION = var4;
         String[] var5 = new String[]{"_id", "type"};
         ID_TYPE_PROJECTION = var5;
         String[] var6 = new String[]{"_id", "isDefault"};
         DEFAULT_ID_PROJECTION = var6;
         CREATOR = new EmailContent.Account.1();
      }

      public Account() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
         this.mRingtoneUri = "content://media/internal/audio/media/28";
         this.mSyncInterval = -1;
         this.mSyncLookback = -1;
         this.mFlags = 1;
         String var2 = UUID.randomUUID().toString();
         this.mCompatibilityUuid = var2;
         this.mEmailSize = 3;
         this.mConflictFlags = 1;
         this.mPolicyKey = "0";
         byte var3 = -1;
         SyncScheduleData var4 = new SyncScheduleData(480, 1020, 62, -1, var3, 0);
         this.mSyncScheduleData = var4;
         this.mIsInPeakPeriod = (boolean)0;
         this.mCalendarSyncLookback = -1;
         this.mTypeMsg = 0;
         this.mConversationMode = 0;
         this.mTextPreviewSize = 128;
         this.mDeviceInfoSent = 0;
         int var5 = this.mFlags | 2048;
         this.mFlags = var5;
      }

      public Account(Parcel var1) {
         super((EmailContent.1)null);
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.readLong();
         this.mId = var3;
         String var5 = var1.readString();
         this.mDisplayName = var5;
         String var6 = var1.readString();
         this.mEmailAddress = var6;
         String var7 = var1.readString();
         this.mSyncKey = var7;
         int var8 = var1.readInt();
         this.mSyncLookback = var8;
         int var9 = var1.readInt();
         this.mSyncInterval = var9;
         long var10 = var1.readLong();
         this.mHostAuthKeyRecv = var10;
         long var12 = var1.readLong();
         this.mHostAuthKeySend = var12;
         int var14 = var1.readInt();
         this.mFlags = var14;
         byte var15;
         if(var1.readByte() == 1) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         this.mIsDefault = (boolean)var15;
         String var16 = var1.readString();
         this.mCompatibilityUuid = var16;
         String var17 = var1.readString();
         this.mSenderName = var17;
         String var18 = var1.readString();
         this.mRingtoneUri = var18;
         String var19 = var1.readString();
         this.mProtocolVersion = var19;
         int var20 = var1.readInt();
         this.mNewMessageCount = var20;
         int var21 = var1.readInt();
         this.mSecurityFlags = var21;
         String var22 = var1.readString();
         this.mSecuritySyncKey = var22;
         String var23 = var1.readString();
         this.mSignature = var23;
         int var24 = var1.readInt();
         this.mEmailSize = var24;
         int var25 = var1.readInt();
         this.mConflictFlags = var25;
         String var26 = var1.readString();
         this.mPolicyKey = var26;
         ClassLoader var27 = SyncScheduleData.class.getClassLoader();
         SyncScheduleData var28 = (SyncScheduleData)var1.readParcelable(var27);
         this.mSyncScheduleData = var28;
         int var29 = var1.readInt();
         this.mCalendarSyncLookback = var29;
         int var30 = var1.readInt();
         this.mConversationMode = var30;
         int var31 = var1.readInt();
         this.mTextPreviewSize = var31;
         int var32 = var1.readInt();
         this.mDeviceInfoSent = var32;
         this.mHostAuthRecv = null;
         if(var1.readByte() == 1) {
            EmailContent.HostAuth var33 = new EmailContent.HostAuth(var1);
            this.mHostAuthRecv = var33;
         }

         this.mHostAuthSend = null;
         if(var1.readByte() == 1) {
            EmailContent.HostAuth var34 = new EmailContent.HostAuth(var1);
            this.mHostAuthSend = var34;
         }

         String var35 = var1.readString();
         this.mCbaCertificateAlias = var35;
      }

      public static long getAccountIdFromShortcutSafeUri(Context param0, Uri param1) {
         // $FF: Couldn't be decompiled
      }

      public static long getDefaultAccountId(Context var0) {
         long var1 = getDefaultAccountWhere(var0, "isDefault=1");
         if(var1 == 65535L) {
            var1 = getDefaultAccountWhere(var0, (String)null);
         }

         return var1;
      }

      private static long getDefaultAccountWhere(Context param0, String param1) {
         // $FF: Couldn't be decompiled
      }

      private long getId(Uri var1) {
         return Long.parseLong((String)var1.getPathSegments().get(1));
      }

      public static Uri getShortcutSafeUriFromUuid(String var0) {
         return CONTENT_URI.buildUpon().appendEncodedPath(var0).build();
      }

      public static boolean isValidId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Account restoreAccountWithEmailAddress(Context param0, String param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Account restoreAccountWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Account restoreAccountWithSevenAccountId(Context param0, int param1) {
         // $FF: Couldn't be decompiled
      }

      public static boolean setConflictResolutionSettings(ContentResolver var0, long var1, int var3) {
         boolean var4 = false;
         if(var0 != null) {
            Uri var5 = CONTENT_URI;
            String var6 = "" + var1;
            Uri var7 = Uri.withAppendedPath(var5, var6);
            ContentValues var8 = new ContentValues();
            Integer var9 = Integer.valueOf(var3);
            var8.put("conflict", var9);
            if(var0.update(var7, var8, (String)null, (String[])null) > 0) {
               var4 = true;
            }
         }

         return var4;
      }

      public int describeContents() {
         return 0;
      }

      public long getAccountKey() {
         return this.mAccountKey;
      }

      public int getCalendarSyncLookback() {
         return this.mCalendarSyncLookback;
      }

      public String getCbaCertificate() {
         return this.mCbaCertificateAlias;
      }

      public int getConflictResolutionFlags() {
         return this.mConflictFlags;
      }

      public int getConflictresolution() {
         return this.mConflictFlags;
      }

      public int getConversationMode() {
         return this.mConversationMode;
      }

      public int getDeletePolicy() {
         return (this.mFlags & 12) >> 2;
      }

      public int getDeviceInfoSent() {
         return this.mDeviceInfoSent;
      }

      public String getDisplayName() {
         return this.mDisplayName;
      }

      public String getEmailAddress() {
         return this.mEmailAddress;
      }

      public byte getEmailSize() {
         return (byte)this.mEmailSize;
      }

      public int getFlags() {
         return this.mFlags;
      }

      public String getLocalStoreUri(Context var1) {
         StringBuilder var2 = (new StringBuilder()).append("local://localhost/");
         StringBuilder var3 = new StringBuilder();
         String var4 = this.getUuid();
         String var5 = var3.append(var4).append(".db").toString();
         File var6 = var1.getDatabasePath(var5);
         return var2.append(var6).toString();
      }

      public String getRingtone() {
         return this.mRingtoneUri;
      }

      public String getSenderName() {
         return this.mSenderName;
      }

      public String getSenderUri(Context var1) {
         if(this.mHostAuthSend == null) {
            long var2 = this.mHostAuthKeySend;
            EmailContent.HostAuth var4 = EmailContent.HostAuth.restoreHostAuthWithId(var1, var2);
            this.mHostAuthSend = var4;
         }

         String var6;
         if(this.mHostAuthSend != null) {
            String var5 = this.mHostAuthSend.getStoreUri();
            if(var5 != null) {
               var6 = var5;
               return var6;
            }
         }

         var6 = "";
         return var6;
      }

      public long getSevenAccountKey() {
         return this.mSevenAccountKey;
      }

      public Uri getShortcutSafeUri() {
         return getShortcutSafeUriFromUuid(this.mCompatibilityUuid);
      }

      public String getSignature() {
         return this.mSignature;
      }

      public long getSizeLimit() {
         return this.mSizeLimit;
      }

      public boolean getSmimeEncryptAll() {
         return this.mSmimeEncryptAll;
      }

      public int getSmimeEncryptionAlgorithm() {
         return this.mSmimeEncryptionAlgorithm;
      }

      public int getSmimeFlags() {
         byte var1;
         if(this.mSmimeEncryptAll) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         int var2 = var1 << 0;
         byte var3;
         if(this.mSmimeSignAll) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         int var4 = var3 << 1;
         return var2 | var4;
      }

      public String getSmimeOwnCertificate() {
         return this.mSmimeOwnCertificateAlias;
      }

      public int getSmimeSignAlgorithm() {
         return this.mSmimeSignAlgorithm;
      }

      public boolean getSmimeSignAll() {
         return this.mSmimeSignAll;
      }

      public String getStoreUri(Context var1) {
         if(this.mHostAuthRecv == null) {
            long var2 = this.mHostAuthKeyRecv;
            EmailContent.HostAuth var4 = EmailContent.HostAuth.restoreHostAuthWithId(var1, var2);
            this.mHostAuthRecv = var4;
         }

         String var6;
         if(this.mHostAuthRecv != null) {
            String var5 = this.mHostAuthRecv.getStoreUri();
            if(var5 != null) {
               var6 = var5;
               return var6;
            }
         }

         var6 = "";
         return var6;
      }

      public int getSyncInterval() {
         return this.mSyncInterval;
      }

      public int getSyncLookback() {
         return this.mSyncLookback;
      }

      public SyncScheduleData getSyncScheduleData() {
         return this.mSyncScheduleData;
      }

      public int getTextPreviewSize() {
         return this.mTextPreviewSize;
      }

      public long getTimeLimit() {
         return this.mTimeLimit;
      }

      public int getTypeMsg() {
         return this.mTypeMsg;
      }

      public String getUuid() {
         return this.mCompatibilityUuid;
      }

      public void refresh(Context param1) {
         // $FF: Couldn't be decompiled
      }

      public EmailContent.Account restore(Cursor var1) {
         long var2 = var1.getLong(0);
         this.mId = var2;
         Uri var4 = CONTENT_URI;
         this.mBaseUri = var4;
         String var5 = var1.getString(1);
         this.mDisplayName = var5;
         String var6 = var1.getString(2);
         this.mEmailAddress = var6;
         String var7 = var1.getString(3);
         this.mSyncKey = var7;
         int var8 = var1.getInt(4);
         this.mSyncLookback = var8;
         int var9 = var1.getInt(5);
         this.mSyncInterval = var9;
         long var10 = var1.getLong(6);
         this.mHostAuthKeyRecv = var10;
         long var12 = var1.getLong(7);
         this.mHostAuthKeySend = var12;
         int var14 = var1.getInt(8);
         this.mFlags = var14;
         byte var15;
         if(var1.getInt(9) == 1) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         this.mIsDefault = (boolean)var15;
         String var16 = var1.getString(10);
         this.mCompatibilityUuid = var16;
         String var17 = var1.getString(11);
         this.mSenderName = var17;
         String var18 = var1.getString(12);
         this.mRingtoneUri = var18;
         String var19 = var1.getString(13);
         this.mProtocolVersion = var19;
         int var20 = var1.getInt(14);
         this.mNewMessageCount = var20;
         int var21 = var1.getInt(18);
         this.mEmailSize = var21;
         int var22 = var1.getInt(46);
         this.mConflictFlags = var22;
         String var23 = var1.getString(19);
         this.mPolicyKey = var23;
         int var24 = var1.getInt(42);
         this.mConversationMode = var24;
         int var25 = var1.getInt(43);
         this.mTextPreviewSize = var25;
         SyncScheduleData var26 = this.mSyncScheduleData;
         int var27 = var1.getInt(20);
         var26.setPeakDay(var27);
         SyncScheduleData var28 = this.mSyncScheduleData;
         int var29 = var1.getInt(21);
         var28.setStartMinute(var29);
         SyncScheduleData var30 = this.mSyncScheduleData;
         int var31 = var1.getInt(22);
         var30.setEndMinute(var31);
         SyncScheduleData var32 = this.mSyncScheduleData;
         int var33 = var1.getInt(23);
         var32.setPeakSchedule(var33);
         int var34 = var1.getInt(24);
         switch(var34) {
         default:
            var34 = -1;
         case -2:
         case -1:
         case 5:
         case 15:
         case 60:
         case 240:
         case 720:
            this.mSyncScheduleData.setOffPeakSchedule(var34);
            SyncScheduleData var35 = this.mSyncScheduleData;
            int var36 = var1.getInt(25);
            var35.setRoamingSchedule(var36);
            int var37 = var1.getInt(26);
            this.mCalendarSyncLookback = var37;
            int var38 = var1.getInt(15);
            this.mSecurityFlags = var38;
            String var39 = var1.getString(16);
            this.mSecuritySyncKey = var39;
            String var40 = var1.getString(17);
            this.mSignature = var40;
            int var41 = var1.getInt(27);
            this.mTypeMsg = var41;
            long var42 = (long)var1.getInt(28);
            this.mSevenAccountKey = var42;
            long var44 = (long)var1.getInt(30);
            this.mTimeLimit = var44;
            long var46 = (long)var1.getInt(29);
            this.mSizeLimit = var46;
            int var48 = var1.getInt(31);
            this.mPeakTime = var48;
            int var49 = var1.getInt(32);
            this.mOffPeakTime = var49;
            String var50 = var1.getString(33);
            this.mDays = var50;
            String var51 = var1.getString(34);
            this.mPeakStartTime = var51;
            String var52 = var1.getString(35);
            this.mPeakEndTime = var52;
            int var53 = var1.getInt(36);
            this.mWhileroaming = var53;
            int var54 = var1.getInt(37);
            this.mAttachmentEnabled = var54;
            String var55 = var1.getString(38);
            this.mSmimeOwnCertificateAlias = var55;
            int var56 = var1.getInt(39);
            this.setSmimeFlags(var56);
            int var57 = var1.getInt(40);
            this.mSmimeSignAlgorithm = var57;
            int var58 = var1.getInt(41);
            this.mSmimeEncryptionAlgorithm = var58;
            int var59 = var1.getInt(44);
            this.mDeviceInfoSent = var59;
            int var60 = var1.getInt(45);
            this.mdeviceBlockedType = var60;
            int var61 = var1.getInt(46);
            this.mConflictFlags = var61;
            String var62 = var1.getString(47);
            this.mCbaCertificateAlias = var62;
            return this;
         }
      }

      public Uri save(Context param1) {
         // $FF: Couldn't be decompiled
      }

      public void setAccountKey(long var1) {
         this.mAccountKey = var1;
      }

      public boolean setCalendarSyncLookback(int var1) {
         boolean var2;
         if(var1 != 4 && var1 != 5 && var1 != 6 && var1 != 7 && var1 != 0) {
            this.mCalendarSyncLookback = 4;
            var2 = false;
         } else {
            var2 = true;
            this.mCalendarSyncLookback = var1;
         }

         return var2;
      }

      public void setCbaCertificate(String var1) {
         this.mCbaCertificateAlias = var1;
      }

      public void setConflictresolution(int var1) {
         this.mConflictFlags = var1;
      }

      public void setConversationMode(int var1) {
         this.mConversationMode = var1;
      }

      public void setDefaultAccount(boolean var1) {
         this.mIsDefault = var1;
      }

      public void setDeletePolicy(int var1) {
         int var2 = this.mFlags & -13;
         this.mFlags = var2;
         int var3 = this.mFlags;
         int var4 = var1 << 2 & 12;
         int var5 = var3 | var4;
         this.mFlags = var5;
      }

      public void setDeviceInfoSent(int var1) {
         this.mDeviceInfoSent = var1;
      }

      public void setDisplayName(String var1) {
         this.mDisplayName = var1;
      }

      public void setEmailAddress(String var1) {
         this.mEmailAddress = var1;
      }

      public void setEmailSize(byte var1) {
         this.mEmailSize = var1;
      }

      public void setFlags(int var1) {
         this.mFlags = var1;
      }

      public void setRingtone(String var1) {
         this.mRingtoneUri = var1;
      }

      public void setSenderName(String var1) {
         this.mSenderName = var1;
      }

      @Deprecated
      public void setSenderUri(Context var1, String var2) {
         if(this.mHostAuthSend == null) {
            if(this.mHostAuthKeySend != 0L) {
               long var3 = this.mHostAuthKeySend;
               EmailContent.HostAuth var5 = EmailContent.HostAuth.restoreHostAuthWithId(var1, var3);
               this.mHostAuthSend = var5;
            } else {
               EmailContent.HostAuth var6 = new EmailContent.HostAuth();
               this.mHostAuthSend = var6;
            }
         }

         if(this.mHostAuthSend != null) {
            this.mHostAuthSend.setStoreUri(var2);
         }
      }

      public void setSevenAccountKey(long var1) {
         this.mSevenAccountKey = var1;
      }

      public void setSignature(String var1) {
         this.mSignature = var1;
      }

      public void setSizeLimit(long var1) {
         this.mSizeLimit = var1;
      }

      public void setSmimeEncryptAll(boolean var1) {
         this.mSmimeEncryptAll = var1;
      }

      public void setSmimeEncryptionAlgorithm(int var1) {
         this.mSmimeEncryptionAlgorithm = var1;
      }

      public void setSmimeFlags(int var1) {
         byte var2;
         if((var1 & 1) != 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         this.mSmimeEncryptAll = (boolean)var2;
         byte var3;
         if((var1 & 2) != 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         this.mSmimeSignAll = (boolean)var3;
      }

      public void setSmimeOwnCertificate(String var1) {
         this.mSmimeOwnCertificateAlias = var1;
      }

      public void setSmimeSignAlgorithm(int var1) {
         this.mSmimeSignAlgorithm = var1;
      }

      public void setSmimeSignAll(boolean var1) {
         this.mSmimeSignAll = var1;
      }

      @Deprecated
      public void setStoreUri(Context var1, String var2) {
         if(this.mHostAuthRecv == null) {
            if(this.mHostAuthKeyRecv != 0L) {
               long var3 = this.mHostAuthKeyRecv;
               EmailContent.HostAuth var5 = EmailContent.HostAuth.restoreHostAuthWithId(var1, var3);
               this.mHostAuthRecv = var5;
            } else {
               EmailContent.HostAuth var6 = new EmailContent.HostAuth();
               this.mHostAuthRecv = var6;
            }
         }

         if(this.mHostAuthRecv != null) {
            this.mHostAuthRecv.setStoreUri(var2);
         }
      }

      public void setSyncInterval(int var1) {
         this.mSyncInterval = var1;
      }

      public void setSyncLookback(int var1) {
         this.mSyncLookback = var1;
      }

      public void setSyncScheduleData(SyncScheduleData var1) {
         SyncScheduleData var2 = this.mSyncScheduleData;
         int var3 = var1.getPeakDay();
         var2.setPeakDay(var3);
         SyncScheduleData var4 = this.mSyncScheduleData;
         int var5 = var1.getStartMinute();
         var4.setStartMinute(var5);
         SyncScheduleData var6 = this.mSyncScheduleData;
         int var7 = var1.getEndMinute();
         var6.setEndMinute(var7);
         SyncScheduleData var8 = this.mSyncScheduleData;
         int var9 = var1.getPeakSchedule();
         var8.setPeakSchedule(var9);
         SyncScheduleData var10 = this.mSyncScheduleData;
         int var11 = var1.getOffPeakSchedule();
         var10.setOffPeakSchedule(var11);
         SyncScheduleData var12 = this.mSyncScheduleData;
         int var13 = var1.getRoamingSchedule();
         var12.setRoamingSchedule(var13);
         StringBuilder var14 = (new StringBuilder()).append("getPeakDay ");
         int var15 = var1.getPeakDay();
         String var16 = var14.append(var15).toString();
         int var17 = Log.i("EmailContent >>", var16);
         StringBuilder var18 = (new StringBuilder()).append("getStartMinute ");
         int var19 = var1.getStartMinute();
         String var20 = var18.append(var19).toString();
         int var21 = Log.i("EmailContent >>", var20);
         StringBuilder var22 = (new StringBuilder()).append("getEndMinute ");
         int var23 = var1.getEndMinute();
         String var24 = var22.append(var23).toString();
         int var25 = Log.i("EmailContent >>", var24);
         StringBuilder var26 = (new StringBuilder()).append("getPeakSchedule ");
         int var27 = var1.getPeakSchedule();
         String var28 = var26.append(var27).toString();
         int var29 = Log.i("EmailContent >>", var28);
         StringBuilder var30 = (new StringBuilder()).append("getOffPeakSchedule ");
         int var31 = var1.getOffPeakSchedule();
         String var32 = var30.append(var31).toString();
         int var33 = Log.i("EmailContent >>", var32);
         StringBuilder var34 = (new StringBuilder()).append("getRoamingSchedule ");
         int var35 = var1.getRoamingSchedule();
         String var36 = var34.append(var35).toString();
         int var37 = Log.i("EmailContent >>", var36);
      }

      public void setTextPreviewSize(int var1) {
         this.mTextPreviewSize = var1;
      }

      public void setTimeLimit(long var1) {
         this.mTimeLimit = var1;
      }

      public void setTypeMsg(int var1) {
         this.mTypeMsg = var1;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.mDisplayName;
         var1.put("displayName", var2);
         String var3 = this.mEmailAddress;
         var1.put("emailAddress", var3);
         String var4 = this.mSyncKey;
         var1.put("syncKey", var4);
         Integer var5 = Integer.valueOf(this.mSyncLookback);
         var1.put("syncLookback", var5);
         Integer var6 = Integer.valueOf(this.mSyncInterval);
         var1.put("syncInterval", var6);
         Long var7 = Long.valueOf(this.mHostAuthKeyRecv);
         var1.put("hostAuthKeyRecv", var7);
         Long var8 = Long.valueOf(this.mHostAuthKeySend);
         var1.put("hostAuthKeySend", var8);
         Integer var9 = Integer.valueOf(this.mFlags);
         var1.put("flags", var9);
         Boolean var10 = Boolean.valueOf(this.mIsDefault);
         var1.put("isDefault", var10);
         String var11 = this.mCompatibilityUuid;
         var1.put("compatibilityUuid", var11);
         String var12 = this.mSenderName;
         var1.put("senderName", var12);
         String var13 = this.mRingtoneUri;
         var1.put("ringtoneUri", var13);
         String var14 = this.mProtocolVersion;
         var1.put("protocolVersion", var14);
         Integer var15 = Integer.valueOf(this.mNewMessageCount);
         var1.put("newMessageCount", var15);
         Integer var16 = Integer.valueOf(this.mSecurityFlags);
         var1.put("securityFlags", var16);
         String var17 = this.mSecuritySyncKey;
         var1.put("securitySyncKey", var17);
         String var18 = this.mSignature;
         var1.put("signature", var18);
         Integer var19 = Integer.valueOf(this.mEmailSize);
         var1.put("emailsize", var19);
         Integer var20 = Integer.valueOf(this.mConflictFlags);
         var1.put("conflict", var20);
         String var21 = this.mPolicyKey;
         var1.put("policyKey", var21);
         Integer var22 = Integer.valueOf(this.mSyncScheduleData.getPeakDay());
         var1.put("peakDays", var22);
         Integer var23 = Integer.valueOf(this.mSyncScheduleData.getStartMinute());
         var1.put("peakStartMinute", var23);
         Integer var24 = Integer.valueOf(this.mSyncScheduleData.getEndMinute());
         var1.put("peakEndMinute", var24);
         Integer var25 = Integer.valueOf(this.mSyncScheduleData.getPeakSchedule());
         var1.put("peakSchedule", var25);
         Integer var26 = Integer.valueOf(this.mSyncScheduleData.getOffPeakSchedule());
         var1.put("offPeakSchedule", var26);
         Integer var27 = Integer.valueOf(this.mSyncScheduleData.getRoamingSchedule());
         var1.put("roamingSchedule", var27);
         Integer var28 = Integer.valueOf(this.mCalendarSyncLookback);
         var1.put("calendarSyncLookback", var28);
         String var29 = this.mSmimeOwnCertificateAlias;
         var1.put("smimeOwnCertificateAlias", var29);
         Integer var30 = Integer.valueOf(this.getSmimeFlags());
         var1.put("smimeOptionsFlags", var30);
         Integer var31 = Integer.valueOf(this.mConversationMode);
         var1.put("conversationMode", var31);
         Integer var32 = Integer.valueOf(this.mTextPreviewSize);
         var1.put("textPreview", var32);
         Integer var33 = Integer.valueOf(this.mDeviceInfoSent);
         var1.put("deviceInfoSent", var33);
         String var34 = this.mCbaCertificateAlias;
         var1.put("cbaCertificateAlias", var34);
         return var1;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(91);
         if(this.mHostAuthRecv != null && this.mHostAuthRecv.mProtocol != null) {
            String var2 = this.mHostAuthRecv.mProtocol;
            var1.append(var2);
            StringBuilder var4 = var1.append(':');
         }

         if(this.mDisplayName != null) {
            String var5 = this.mDisplayName;
            var1.append(var5);
         }

         StringBuilder var7 = var1.append(':');
         if(this.mEmailAddress != null) {
            String var8 = this.mEmailAddress;
            var1.append(var8);
         }

         StringBuilder var10 = var1.append(':');
         if(this.mSenderName != null) {
            String var11 = this.mSenderName;
            var1.append(var11);
         }

         StringBuilder var13 = var1.append(']');
         return var1.toString();
      }

      public int update(Context var1, ContentValues var2) {
         int var20;
         if(var2.containsKey("isDefault") && var2.getAsBoolean("isDefault").booleanValue()) {
            ArrayList var3 = new ArrayList();
            long var4 = getDefaultAccountId(var1);
            if(var4 != 65535L) {
               long var6 = this.mId;
               if(var4 != var6) {
                  EmailContent.Account var8 = restoreAccountWithId(var1, var4);
                  ContentValues var9 = new ContentValues();

                  try {
                     Boolean var10 = Boolean.valueOf((boolean)0);
                     var9.put("isDefault", var10);
                     Integer var11 = Integer.valueOf(var8.mEmailSize);
                     var9.put("emailsize", var11);
                     ContentProviderOperation var12 = ContentProviderOperation.newUpdate(ContentUris.withAppendedId(CONTENT_URI, var4)).withValues(var9).build();
                     var3.add(var12);
                  } catch (Exception var24) {
                     ;
                  }
               }
            }

            Uri var14 = CONTENT_URI;
            long var15 = this.mId;
            ContentProviderOperation var17 = ContentProviderOperation.newUpdate(ContentUris.withAppendedId(var14, var15)).withValues(var2).build();
            var3.add(var17);

            label44: {
               try {
                  ContentProviderResult[] var19 = var1.getContentResolver().applyBatch("com.android.email.provider", var3);
                  break label44;
               } catch (RemoteException var25) {
                  ;
               } catch (OperationApplicationException var26) {
                  ;
               }

               var20 = 0;
               return var20;
            }

            var20 = 1;
         } else {
            var20 = super.update(var1, var2);
         }

         return var20;
      }

      public void writeToParcel(Parcel var1, int var2) {
         long var3 = this.mId;
         var1.writeLong(var3);
         String var5 = this.mDisplayName;
         var1.writeString(var5);
         String var6 = this.mEmailAddress;
         var1.writeString(var6);
         String var7 = this.mSyncKey;
         var1.writeString(var7);
         int var8 = this.mSyncLookback;
         var1.writeInt(var8);
         int var9 = this.mSyncInterval;
         var1.writeInt(var9);
         long var10 = this.mHostAuthKeyRecv;
         var1.writeLong(var10);
         long var12 = this.mHostAuthKeySend;
         var1.writeLong(var12);
         int var14 = this.mFlags;
         var1.writeInt(var14);
         byte var15;
         if(this.mIsDefault) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         var1.writeByte(var15);
         String var16 = this.mCompatibilityUuid;
         var1.writeString(var16);
         String var17 = this.mSenderName;
         var1.writeString(var17);
         String var18 = this.mRingtoneUri;
         var1.writeString(var18);
         String var19 = this.mProtocolVersion;
         var1.writeString(var19);
         int var20 = this.mNewMessageCount;
         var1.writeInt(var20);
         int var21 = this.mSecurityFlags;
         var1.writeInt(var21);
         String var22 = this.mSecuritySyncKey;
         var1.writeString(var22);
         String var23 = this.mSignature;
         var1.writeString(var23);
         int var24 = this.mEmailSize;
         var1.writeInt(var24);
         int var25 = this.mConflictFlags;
         var1.writeInt(var25);
         String var26 = this.mPolicyKey;
         var1.writeString(var26);
         SyncScheduleData var27 = this.mSyncScheduleData;
         var1.writeParcelable(var27, 0);
         int var28 = this.mCalendarSyncLookback;
         var1.writeInt(var28);
         int var29 = this.mConversationMode;
         var1.writeInt(var29);
         int var30 = this.mTextPreviewSize;
         var1.writeInt(var30);
         int var31 = this.mDeviceInfoSent;
         var1.writeInt(var31);
         if(this.mHostAuthRecv != null) {
            var1.writeByte((byte)1);
            this.mHostAuthRecv.writeToParcel(var1, var2);
         } else {
            var1.writeByte((byte)0);
         }

         if(this.mHostAuthSend != null) {
            var1.writeByte((byte)1);
            this.mHostAuthSend.writeToParcel(var1, var2);
         } else {
            var1.writeByte((byte)0);
         }

         String var32 = this.mCbaCertificateAlias;
         var1.writeString(var32);
      }

      static class 1 implements Creator<EmailContent.Account> {

         1() {}

         public EmailContent.Account createFromParcel(Parcel var1) {
            return new EmailContent.Account(var1);
         }

         public EmailContent.Account[] newArray(int var1) {
            return new EmailContent.Account[var1];
         }
      }
   }

   public interface FollowupFlagColumns {

      String COMPLETE_TIME = "completeTime";
      String DATE_COMPLETED = "dateCompleted";
      String DUE_DATE = "dueDate";
      String FLAG_TYPE = "flagType";
      String ID = "_id";
      String MESSAGE_KEY = "messageKey";
      String MESSAGE_SYNC_SERVER_ID = "messageSyncServerId";
      String ORDINAL_DATE = "ordinalDate";
      String REMINDER_SET = "reminderSet";
      String REMINDER_TIME = "reminderTime";
      String RINGTONE_URI = "ringtoneUri";
      String START_DATE = "startDate";
      String STATUS = "status";
      String SUBORDINAL_DATE = "sub_ordinaldate";
      String TASK_KEY = "taskKey";
      String TASK_SYNC_SERVER_ID = "taskSyncServerId";
      String UTC_DUE_DATE = "UTCDueDate";
      String UTC_START_DATE = "UTCStartDate";

   }

   public interface AttachmentColumns {

      String CONTENT = "content";
      String CONTENT_BYTES = "content_bytes";
      String CONTENT_ID = "contentId";
      String CONTENT_URI = "contentUri";
      String ENCODING = "encoding";
      String FILENAME = "fileName";
      String FLAGS = "flags";
      String ID = "_id";
      String ISINLINE = "isInline";
      String LOCATION = "location";
      String MESSAGE_KEY = "messageKey";
      String MIME_TYPE = "mimeType";
      String SIZE = "size";
      String VOICEMAIL_ATT_DURATION = "vmAttDuration";
      String VOICEMAIL_ATT_ORDER = "vmAttOrder";

   }

   public static final class FollowupFlag extends EmailContent implements EmailContent.FollowupFlagColumns {

      public static final int CONTENT_COMPLETE_TIME_COLUMN = 8;
      public static final int CONTENT_DATE_COMPLETED_COLUMN = 9;
      public static final int CONTENT_DUE_DATE_COLUMN = 11;
      public static final int CONTENT_FLAG_TYPE_COLUMN = 7;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_MESSAGE_KEY_COLUMN = 1;
      public static final int CONTENT_MESSAGE_SYNC_SERVER_ID = 2;
      public static final int CONTENT_ORDINAL_DATE_COLUMN = 16;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_REMINDER_SET_COLUMN = 14;
      public static final int CONTENT_REMINDER_TIME_COLUMN = 15;
      public static final int CONTENT_RINGTONE_URI_COLUMN = 5;
      public static final int CONTENT_START_DATE_COLUMN = 10;
      public static final int CONTENT_STATUS_COLUMN = 6;
      public static final int CONTENT_SUB_ORDINAL_DATE_COLUMN = 17;
      public static final int CONTENT_TASK_KEY_COLUMN = 3;
      public static final int CONTENT_TASK_SYNC_SERVER_ID = 4;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_UTC_DUE_DATE_COLUMN = 13;
      public static final int CONTENT_UTC_START_DATE_COLUMN = 12;
      public static final Uri DELETED_CONTENT_URI;
      public static final String DELETED_TABLE_NAME = "FollowupFlag_Deletes";
      public static final Uri MESSAGE_CONTENT_URI;
      public static final Uri SYNCED_CONTENT_URI;
      public static final String TABLE_NAME = "FollowupFlag";
      public static final Uri UPDATED_CONTENT_URI;
      public static final String UPDATED_TABLE_NAME = "FollowupFlag_Updates";
      private static EmailContent.FollowupFlag.FollowupFlagDefaultState defaultFollowup;
      public long CompleteTime;
      public long DateCompleted;
      public long DueDate;
      public String FlagType;
      public long MsgId;
      public String MsgSyncServerId;
      public long OrdinalDate;
      public Boolean ReminderSet;
      public long ReminderTime;
      public Uri RingtoneUri;
      public long StartDate;
      public EmailContent.FollowupFlag.FollowupFlagStatus Status;
      public String SubOrdinalDate;
      public long TaskId;
      public String TaskSyncServerId;
      public long UTCDueDate;
      public long UTCStartDate;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/followupFlag").toString());
         StringBuilder var2 = new StringBuilder();
         Uri var3 = EmailContent.CONTENT_URI;
         MESSAGE_CONTENT_URI = Uri.parse(var2.append(var3).append("/message").toString());
         StringBuilder var4 = new StringBuilder();
         Uri var5 = EmailContent.CONTENT_URI;
         SYNCED_CONTENT_URI = Uri.parse(var4.append(var5).append("/syncedFollowupFlag").toString());
         StringBuilder var6 = new StringBuilder();
         Uri var7 = EmailContent.CONTENT_URI;
         UPDATED_CONTENT_URI = Uri.parse(var6.append(var7).append("/updatedFollowupFlag").toString());
         StringBuilder var8 = new StringBuilder();
         Uri var9 = EmailContent.CONTENT_URI;
         DELETED_CONTENT_URI = Uri.parse(var8.append(var9).append("/deletedFollowupFlag").toString());
         String[] var10 = new String[]{"_id", "messageKey", "messageSyncServerId", "taskKey", "taskSyncServerId", "ringtoneUri", "status", "flagType", "completeTime", "dateCompleted", "startDate", "dueDate", "UTCStartDate", "UTCDueDate", "reminderSet", "reminderTime", "ordinalDate", "sub_ordinaldate"};
         CONTENT_PROJECTION = var10;
         defaultFollowup = EmailContent.FollowupFlag.FollowupFlagDefaultState.THIS_WEEK;
      }

      public FollowupFlag() {
         super((EmailContent.1)null);
         EmailContent.FollowupFlag.FollowupFlagStatus var1 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_CLEARED;
         this.Status = var1;
         this.FlagType = null;
         this.DateCompleted = 0L;
         this.CompleteTime = 0L;
         this.StartDate = 0L;
         this.DueDate = 0L;
         this.UTCStartDate = 0L;
         this.UTCDueDate = 0L;
         Boolean var2 = Boolean.valueOf((boolean)0);
         this.ReminderSet = var2;
         this.ReminderTime = 0L;
         this.OrdinalDate = 0L;
         this.SubOrdinalDate = null;
         this.MsgId = 0L;
         this.MsgSyncServerId = null;
         this.TaskId = 0L;
         this.TaskSyncServerId = null;
         this.RingtoneUri = null;
         Uri var3 = CONTENT_URI;
         this.mBaseUri = var3;
         this.mId = 65535L;
      }

      public static EmailContent.FollowupFlag restoreFollowupFlagWithEmailId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.FollowupFlag restoreFollowupFlagWithEmailSyncId(Context param0, String param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.FollowupFlag restoreFollowupFlagWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public EmailContent.FollowupFlag.FollowupFlagDefaultState changeDefaultState(EmailContent.FollowupFlag.FollowupFlagDefaultState var1) {
         EmailContent.FollowupFlag.FollowupFlagDefaultState var2 = defaultFollowup;
         defaultFollowup = var1;
         return var2;
      }

      public EmailContent.FollowupFlag restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         switch(var1.getInt(6)) {
         case 1:
            EmailContent.FollowupFlag.FollowupFlagStatus var34 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_COMPLETE;
            this.Status = var34;
            break;
         case 2:
            EmailContent.FollowupFlag.FollowupFlagStatus var35 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
            this.Status = var35;
            break;
         default:
            EmailContent.FollowupFlag.FollowupFlagStatus var5 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_CLEARED;
            this.Status = var5;
         }

         String var6 = var1.getString(7);
         this.FlagType = var6;
         long var7 = var1.getLong(9);
         this.DateCompleted = var7;
         long var9 = var1.getLong(8);
         this.CompleteTime = var9;
         long var11 = var1.getLong(10);
         this.StartDate = var11;
         long var13 = var1.getLong(11);
         this.DueDate = var13;
         long var15 = var1.getLong(12);
         this.UTCStartDate = var15;
         long var17 = var1.getLong(13);
         this.UTCDueDate = var17;
         byte var19;
         if(var1.getInt(14) == 1) {
            var19 = 1;
         } else {
            var19 = 0;
         }

         Boolean var20 = Boolean.valueOf((boolean)var19);
         this.ReminderSet = var20;
         long var21 = var1.getLong(15);
         this.ReminderTime = var21;
         long var23 = var1.getLong(16);
         this.OrdinalDate = var23;
         String var25 = var1.getString(17);
         this.SubOrdinalDate = var25;
         long var26 = var1.getLong(1);
         this.MsgId = var26;
         String var28 = var1.getString(2);
         this.MsgSyncServerId = var28;
         long var29 = var1.getLong(3);
         this.TaskId = var29;
         String var31 = var1.getString(4);
         this.TaskSyncServerId = var31;
         String var32 = var1.getString(5);
         if(var32 != null) {
            Uri var33 = Uri.parse(var32);
            this.RingtoneUri = var33;
         }

         return this;
      }

      public Boolean setDefaults(EmailContent.FollowupFlag.FollowupFlagDefaultState var1, Context var2) {
         Calendar var3 = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
         String var4 = new String("Follow Up");
         this.FlagType = var4;
         int[] var5 = EmailContent.1.$SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
         int var6 = var1.ordinal();
         switch(var5[var6]) {
         case 2:
            EmailContent.FollowupFlag.FollowupFlagStatus var25 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
            this.Status = var25;
            var3.add(7, 1);
            long var26 = var3.getTimeInMillis();
            this.UTCStartDate = var26;
            this.StartDate = var26;
            long var28 = var3.getTimeInMillis();
            this.UTCDueDate = var28;
            this.DueDate = var28;
            Boolean var30 = Boolean.valueOf((boolean)1);
            this.ReminderSet = var30;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
            break;
         case 3:
            EmailContent.FollowupFlag.FollowupFlagStatus var31 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
            this.Status = var31;
            long var32 = var3.getTimeInMillis();
            this.UTCStartDate = var32;
            this.StartDate = var32;
            var3.set(7, 6);
            long var34 = var3.getTimeInMillis();
            this.UTCDueDate = var34;
            this.DueDate = var34;
            Boolean var36 = Boolean.valueOf((boolean)1);
            this.ReminderSet = var36;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
            break;
         case 4:
            EmailContent.FollowupFlag.FollowupFlagStatus var37 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
            this.Status = var37;
            var3.add(3, 1);
            var3.set(7, 2);
            long var38 = var3.getTimeInMillis();
            this.UTCStartDate = var38;
            this.StartDate = var38;
            var3.set(7, 6);
            long var40 = var3.getTimeInMillis();
            this.UTCDueDate = var40;
            this.DueDate = var40;
            Boolean var42 = Boolean.valueOf((boolean)0);
            this.ReminderSet = var42;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
            break;
         case 5:
            EmailContent.FollowupFlag.FollowupFlagStatus var43 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
            this.Status = var43;
            this.DueDate = 0L;
            this.StartDate = 0L;
            this.UTCDueDate = 0L;
            this.UTCStartDate = 0L;
            Boolean var44 = Boolean.valueOf((boolean)0);
            this.ReminderSet = var44;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
            break;
         case 6:
            EmailContent.FollowupFlag.FollowupFlagStatus var45 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_COMPLETE;
            this.Status = var45;
            long var46 = var3.getTimeInMillis();
            this.UTCStartDate = var46;
            this.StartDate = var46;
            long var48 = var3.getTimeInMillis();
            this.UTCDueDate = var48;
            this.DueDate = var48;
            Boolean var50 = Boolean.valueOf((boolean)0);
            this.ReminderSet = var50;
            long var51 = (long)var3.get(11);
            this.CompleteTime = var51;
            long var53 = var3.getTimeInMillis();
            this.DateCompleted = var53;
            this.ReminderTime = 0L;
            break;
         case 7:
            EmailContent.FollowupFlag.FollowupFlagStatus var55 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_CLEARED;
            this.Status = var55;
            this.DueDate = 0L;
            this.StartDate = 0L;
            this.UTCDueDate = 0L;
            this.UTCStartDate = 0L;
            Boolean var56 = Boolean.valueOf((boolean)0);
            this.ReminderSet = var56;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
            break;
         default:
            EmailContent.FollowupFlag.FollowupFlagStatus var7 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
            this.Status = var7;
            long var8 = var3.getTimeInMillis();
            this.UTCStartDate = var8;
            this.StartDate = var8;
            long var10 = var3.getTimeInMillis();
            this.UTCDueDate = var10;
            this.DueDate = var10;
            Boolean var12 = Boolean.valueOf((boolean)1);
            this.ReminderSet = var12;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
         }

         if(this.ReminderSet.booleanValue()) {
            Calendar var13 = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
            Calendar var14 = GregorianCalendar.getInstance();
            var13.set(14, 0);
            var13.set(13, 0);
            var13.set(12, 0);
            int var15 = var14.get(15) / 1000 / 3600;
            int var16 = 17 - var15;
            int var17 = var14.get(16) / 1000 / 3600;
            int var18 = var16 - var17;
            var13.set(11, var18);
            long var19 = var13.getTimeInMillis();
            this.ReminderTime = var19;
         }

         long var21 = this.OrdinalDate;
         if(0L == var21) {
            long var23 = var3.getTimeInMillis();
            this.OrdinalDate = var23;
         }

         return this.storeFollowupFlag(var2);
      }

      public Boolean storeFollowupFlag(Context var1) {
         Boolean var2 = Boolean.valueOf((boolean)0);
         if(this.isSaved()) {
            Uri var3 = SYNCED_CONTENT_URI;
            long var4 = this.mId;
            ContentValues var6 = this.toContentValues();
            if(update(var1, var3, var4, var6) != 0) {
               var2 = Boolean.valueOf((boolean)1);
            }
         } else if(this.save(var1) != null) {
            var2 = Boolean.valueOf((boolean)1);
         }

         return var2;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         int[] var2 = EmailContent.1.$SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus;
         int var3 = this.Status.ordinal();
         switch(var2[var3]) {
         case 1:
            Integer var26 = Integer.valueOf(1);
            var1.put("status", var26);
            break;
         case 2:
            Integer var27 = Integer.valueOf(2);
            var1.put("status", var27);
            break;
         case 3:
            Integer var24 = Integer.valueOf(0);
            var1.put("status", var24);
            this.FlagType = null;
            this.UTCStartDate = 0L;
            this.StartDate = 0L;
            this.UTCDueDate = 0L;
            this.DueDate = 0L;
            Boolean var25 = Boolean.valueOf((boolean)0);
            this.ReminderSet = var25;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
            break;
         default:
            Integer var4 = Integer.valueOf(0);
            var1.put("status", var4);
            this.FlagType = null;
            this.UTCStartDate = 0L;
            this.StartDate = 0L;
            this.UTCDueDate = 0L;
            this.DueDate = 0L;
            Boolean var5 = Boolean.valueOf((boolean)0);
            this.ReminderSet = var5;
            this.DateCompleted = 0L;
            this.CompleteTime = 0L;
            this.ReminderTime = 0L;
         }

         if(this.FlagType != null) {
            String var6 = this.FlagType;
            var1.put("flagType", var6);
         } else {
            var1.putNull("flagType");
         }

         Long var7 = Long.valueOf(this.DateCompleted);
         var1.put("dateCompleted", var7);
         Long var8 = Long.valueOf(this.CompleteTime);
         var1.put("completeTime", var8);
         Long var9 = Long.valueOf(this.StartDate);
         var1.put("startDate", var9);
         Long var10 = Long.valueOf(this.DueDate);
         var1.put("dueDate", var10);
         Long var11 = Long.valueOf(this.UTCStartDate);
         var1.put("UTCStartDate", var11);
         Long var12 = Long.valueOf(this.UTCDueDate);
         var1.put("UTCDueDate", var12);
         String var13 = "reminderSet";
         byte var14;
         if(!this.ReminderSet.booleanValue()) {
            var14 = 0;
         } else {
            var14 = 1;
         }

         Integer var15 = Integer.valueOf(var14);
         var1.put(var13, var15);
         Long var16 = Long.valueOf(this.ReminderTime);
         var1.put("reminderTime", var16);
         Long var17 = Long.valueOf(this.OrdinalDate);
         var1.put("ordinalDate", var17);
         String var18 = this.SubOrdinalDate;
         var1.put("sub_ordinaldate", var18);
         Long var19 = Long.valueOf(this.MsgId);
         var1.put("messageKey", var19);
         if(this.MsgSyncServerId != null) {
            String var20 = this.MsgSyncServerId;
            var1.put("messageSyncServerId", var20);
         }

         Long var21 = Long.valueOf(this.TaskId);
         var1.put("taskKey", var21);
         if(this.TaskSyncServerId != null) {
            String var22 = this.TaskSyncServerId;
            var1.put("taskSyncServerId", var22);
         }

         if(this.RingtoneUri != null) {
            String var23 = this.RingtoneUri.toString();
            var1.put("ringtoneUri", var23);
         } else {
            var1.putNull("ringtoneUri");
         }

         return var1;
      }

      public static enum FollowupFlagStatus {

         // $FF: synthetic field
         private static final EmailContent.FollowupFlag.FollowupFlagStatus[] $VALUES;
         FOLLOWUP_STATUS_ACTIVE("FOLLOWUP_STATUS_ACTIVE", 2),
         FOLLOWUP_STATUS_CLEARED("FOLLOWUP_STATUS_CLEARED", 0),
         FOLLOWUP_STATUS_COMPLETE("FOLLOWUP_STATUS_COMPLETE", 1);


         static {
            EmailContent.FollowupFlag.FollowupFlagStatus[] var0 = new EmailContent.FollowupFlag.FollowupFlagStatus[3];
            EmailContent.FollowupFlag.FollowupFlagStatus var1 = FOLLOWUP_STATUS_CLEARED;
            var0[0] = var1;
            EmailContent.FollowupFlag.FollowupFlagStatus var2 = FOLLOWUP_STATUS_COMPLETE;
            var0[1] = var2;
            EmailContent.FollowupFlag.FollowupFlagStatus var3 = FOLLOWUP_STATUS_ACTIVE;
            var0[2] = var3;
            $VALUES = var0;
         }

         private FollowupFlagStatus(String var1, int var2) {}
      }

      public static enum FollowupFlagDefaultState {

         // $FF: synthetic field
         private static final EmailContent.FollowupFlag.FollowupFlagDefaultState[] $VALUES;
         CLEAR("CLEAR", 6),
         MARK_COMPLETE("MARK_COMPLETE", 5),
         NEXT_WEEK("NEXT_WEEK", 3),
         NO_DATE("NO_DATE", 4),
         THIS_WEEK("THIS_WEEK", 2),
         TODAY("TODAY", 0),
         TOMORROW("TOMORROW", 1);


         static {
            EmailContent.FollowupFlag.FollowupFlagDefaultState[] var0 = new EmailContent.FollowupFlag.FollowupFlagDefaultState[7];
            EmailContent.FollowupFlag.FollowupFlagDefaultState var1 = TODAY;
            var0[0] = var1;
            EmailContent.FollowupFlag.FollowupFlagDefaultState var2 = TOMORROW;
            var0[1] = var2;
            EmailContent.FollowupFlag.FollowupFlagDefaultState var3 = THIS_WEEK;
            var0[2] = var3;
            EmailContent.FollowupFlag.FollowupFlagDefaultState var4 = NEXT_WEEK;
            var0[3] = var4;
            EmailContent.FollowupFlag.FollowupFlagDefaultState var5 = NO_DATE;
            var0[4] = var5;
            EmailContent.FollowupFlag.FollowupFlagDefaultState var6 = MARK_COMPLETE;
            var0[5] = var6;
            EmailContent.FollowupFlag.FollowupFlagDefaultState var7 = CLEAR;
            var0[6] = var7;
            $VALUES = var0;
         }

         private FollowupFlagDefaultState(String var1, int var2) {}
      }
   }

   public static final class Body extends EmailContent implements EmailContent.BodyColumns {

      public static final int COMMON_PROJECTION_COLUMN_TEXT = 1;
      public static final String[] COMMON_PROJECTION_HTML;
      public static final String[] COMMON_PROJECTION_INTRO;
      public static final String[] COMMON_PROJECTION_REPLY_HTML;
      public static final String[] COMMON_PROJECTION_REPLY_TEXT;
      public static final String[] COMMON_PROJECTION_TEXT;
      public static final int CONTENT_HTML_CONTENT_COLUMN = 2;
      public static final int CONTENT_HTML_REPLY_COLUMN = 4;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_INTRO_TEXT_COLUMN = 7;
      public static final int CONTENT_MESSAGE_KEY_COLUMN = 1;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_SOURCE_KEY_COLUMN = 6;
      public static final int CONTENT_TEXT_CONTENT_COLUMN = 3;
      public static final int CONTENT_TEXT_REPLY_COLUMN = 5;
      public static final Uri CONTENT_URI;
      private static final String[] PROJECTION_SOURCE_KEY;
      public static final String TABLE_NAME = "Body";
      public String mHtmlContent;
      public String mHtmlReply;
      public String mIntroText;
      public long mMessageKey;
      public long mSourceKey;
      public String mTextContent;
      public String mTextReply;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/body").toString());
         String[] var2 = new String[]{"_id", "messageKey", "htmlContent", "textContent", "htmlReply", "textReply", "sourceMessageKey", "introText"};
         CONTENT_PROJECTION = var2;
         String[] var3 = new String[]{"_id", "textContent"};
         COMMON_PROJECTION_TEXT = var3;
         String[] var4 = new String[]{"_id", "htmlContent"};
         COMMON_PROJECTION_HTML = var4;
         String[] var5 = new String[]{"_id", "textReply"};
         COMMON_PROJECTION_REPLY_TEXT = var5;
         String[] var6 = new String[]{"_id", "htmlReply"};
         COMMON_PROJECTION_REPLY_HTML = var6;
         String[] var7 = new String[]{"_id", "introText"};
         COMMON_PROJECTION_INTRO = var7;
         String[] var8 = new String[]{"sourceMessageKey"};
         PROJECTION_SOURCE_KEY = var8;
      }

      public Body() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public static long lookupBodyIdWithMessageId(ContentResolver param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static String restoreBodyHtmlWithMessageId(Context var0, long var1) {
         String[] var3 = COMMON_PROJECTION_HTML;
         return restoreTextWithMessageId(var0, var1, var3);
      }

      public static long restoreBodySourceKey(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static String restoreBodyTextWithMessageId(Context var0, long var1) {
         String[] var3 = COMMON_PROJECTION_TEXT;
         return restoreTextWithMessageId(var0, var1, var3);
      }

      private static EmailContent.Body restoreBodyWithCursor(Cursor var0) {
         EmailContent.Body var1;
         label56: {
            try {
               if(var0.moveToFirst()) {
                  var1 = (EmailContent.Body)getContent(var0, EmailContent.Body.class);
                  return var1;
               }
               break label56;
            } catch (Exception var6) {
               ;
            } finally {
               var0.close();
            }

            var1 = null;
            return var1;
         }

         var1 = null;
         return var1;
      }

      public static EmailContent.Body restoreBodyWithId(Context var0, long var1) {
         Uri var3 = ContentUris.withAppendedId(CONTENT_URI, var1);
         Cursor var4 = null;

         Cursor var7;
         try {
            ContentResolver var5 = var0.getContentResolver();
            String[] var6 = CONTENT_PROJECTION;
            var7 = var5.query(var3, var6, (String)null, (String[])null, (String)null);
         } catch (Exception var9) {
            return restoreBodyWithCursor(var4);
         }

         var4 = var7;
         return restoreBodyWithCursor(var4);
      }

      public static EmailContent.Body restoreBodyWithMessageId(Context var0, long var1) {
         Cursor var3 = null;

         Cursor var9;
         try {
            ContentResolver var4 = var0.getContentResolver();
            Uri var5 = CONTENT_URI;
            String[] var6 = CONTENT_PROJECTION;
            String[] var7 = new String[1];
            String var8 = Long.toString(var1);
            var7[0] = var8;
            var9 = var4.query(var5, var6, "messageKey=?", var7, (String)null);
         } catch (Exception var11) {
            return restoreBodyWithCursor(var3);
         }

         var3 = var9;
         return restoreBodyWithCursor(var3);
      }

      public static String restoreIntroTextWithMessageId(Context var0, long var1) {
         String[] var3 = COMMON_PROJECTION_INTRO;
         return restoreTextWithMessageId(var0, var1, var3);
      }

      public static String restoreReplyHtmlWithMessageId(Context var0, long var1) {
         String[] var3 = COMMON_PROJECTION_REPLY_HTML;
         return restoreTextWithMessageId(var0, var1, var3);
      }

      public static String restoreReplyTextWithMessageId(Context var0, long var1) {
         String[] var3 = COMMON_PROJECTION_REPLY_TEXT;
         return restoreTextWithMessageId(var0, var1, var3);
      }

      private static String restoreTextWithMessageId(Context param0, long param1, String[] param3) {
         // $FF: Couldn't be decompiled
      }

      public static void updateBodyWithMessageId(Context var0, long var1, ContentValues var3) {
         ContentResolver var4 = var0.getContentResolver();
         long var5 = lookupBodyIdWithMessageId(var4, var1);
         Long var7 = Long.valueOf(var1);
         var3.put("messageKey", var7);
         if(var5 == 65535L) {
            Uri var8 = CONTENT_URI;
            var4.insert(var8, var3);
         } else {
            Uri var10 = ContentUris.withAppendedId(CONTENT_URI, var5);
            var4.update(var10, var3, (String)null, (String[])null);
         }
      }

      public EmailContent.Body restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(1);
         this.mMessageKey = var3;
         String var5 = var1.getString(2);
         this.mHtmlContent = var5;
         String var6 = var1.getString(3);
         this.mTextContent = var6;
         String var7 = var1.getString(4);
         this.mHtmlReply = var7;
         String var8 = var1.getString(5);
         this.mTextReply = var8;
         long var9 = var1.getLong(6);
         this.mSourceKey = var9;
         String var11 = var1.getString(7);
         this.mIntroText = var11;
         return this;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         Long var2 = Long.valueOf(this.mMessageKey);
         var1.put("messageKey", var2);
         String var3 = this.mHtmlContent;
         var1.put("htmlContent", var3);
         String var4 = this.mTextContent;
         var1.put("textContent", var4);
         String var5 = this.mHtmlReply;
         var1.put("htmlReply", var5);
         String var6 = this.mTextReply;
         var1.put("textReply", var6);
         Long var7 = Long.valueOf(this.mSourceKey);
         var1.put("sourceMessageKey", var7);
         String var8 = this.mIntroText;
         var1.put("introText", var8);
         return var1;
      }

      public boolean update() {
         return false;
      }
   }

   public static final class AccountCB extends EmailContent implements EmailContent.AccountCBColumns, Parcelable {

      public static final int CONTENT_ACCOUNT_KEY = 1;
      public static final int CONTENT_ATTACHMENT_ENABLED = 12;
      public static final int CONTENT_DAYS = 8;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_MAILBOX_TYPE_COLUMN = 1;
      public static final int CONTENT_OFF_PEAK_TIME = 7;
      public static final int CONTENT_PEAK_END_TIME = 10;
      public static final int CONTENT_PEAK_START_TIME = 9;
      public static final int CONTENT_PEAK_TIME = 6;
      public static final String[] CONTENT_PROJECTION;
      public static String[] CONTENT_PROJECTION_PROTOCOL;
      public static final int CONTENT_RECV_PROTOCOL = 6;
      public static final int CONTENT_SEND_PROTOCOL = 7;
      public static final int CONTENT_SEVEN_ACCOUNT_KEY = 2;
      public static final int CONTENT_SIZE_LIMIT = 5;
      public static final int CONTENT_TIME_LIMIT = 4;
      public static final int CONTENT_TYPE_MSG = 3;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_WHILE_ROAMING = 11;
      public static final String TABLE_NAME = "Account_CB";
      public static final int TYPE_MSG_BASIC = 0;
      public static final int TYPE_MSG_SEVEN = 1;
      public static final String WHERE_ACCOUNT_KEY = "accountKey=?";
      public long mAccountKey;
      public int mAttachmentEnabled;
      public String mDays;
      public int mKeepConnectionLowBattery;
      public int mOffPeakTime;
      public String mPeakEndTime;
      public String mPeakStartTime;
      public int mPeakTime;
      public int mPushSync;
      public String mRecvProtocol;
      public String mSendProtocol;
      public long mSevenAccountKey;
      public long mSizeLimit;
      public String mSyncSchedule;
      public long mTimeLimit;
      public int mTypeMsg;
      public int mWhileroaming;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/accountcb").toString());
         String[] var2 = new String[]{"_id", "accountKey", "sevenAccountKey", "typeMsg", "timeLimit", "sizeLimit", "peakTime", "offPeakTime", "days", "peakStartTime", "peakEndTime", "whileRoaming", "attachmentEnabled"};
         CONTENT_PROJECTION = var2;
         String[] var3 = new String[]{"_id", "accountKey", "sevenAccountKey", "typeMsg", "timeLimit", "sizeLimit", "recvProtocol", "sendProtocol"};
         CONTENT_PROJECTION_PROTOCOL = var3;
      }

      public AccountCB() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public AccountCB(Parcel var1) {
         super((EmailContent.1)null);
         Uri var2 = EmailContent.Account.CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.readLong();
         this.mId = var3;
         long var5 = var1.readLong();
         this.mAccountKey = var5;
         long var7 = var1.readLong();
         this.mSevenAccountKey = var7;
         int var9 = var1.readInt();
         this.mTypeMsg = var9;
         long var10 = var1.readLong();
         this.mTimeLimit = var10;
         long var12 = var1.readLong();
         this.mSizeLimit = var12;
         int var14 = var1.readInt();
         this.mPeakTime = var14;
         int var15 = var1.readInt();
         this.mOffPeakTime = var15;
         String var16 = var1.readString();
         this.mDays = var16;
         String var17 = var1.readString();
         this.mPeakStartTime = var17;
         String var18 = var1.readString();
         this.mPeakEndTime = var18;
         int var19 = var1.readInt();
         this.mWhileroaming = var19;
         int var20 = var1.readInt();
         this.mAttachmentEnabled = var20;
      }

      public static long getAccountKey(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.AccountCB restoreMessageWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.AccountCB selectAccountProtocol(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public int describeContents() {
         return 0;
      }

      public long getAccountKey() {
         return this.mAccountKey;
      }

      public long getSevenAccountKey() {
         return this.mSevenAccountKey;
      }

      public long getSizeLimit() {
         return this.mSizeLimit;
      }

      public long getTimeLimit() {
         return this.mTimeLimit;
      }

      public int getTypeMsg() {
         return this.mTypeMsg;
      }

      public EmailContent.AccountCB restore(Cursor var1) {
         long var2 = var1.getLong(0);
         this.mId = var2;
         Uri var4 = CONTENT_URI;
         this.mBaseUri = var4;
         long var5 = var1.getLong(1);
         this.mAccountKey = var5;
         long var7 = var1.getLong(2);
         this.mSevenAccountKey = var7;
         int var9 = var1.getInt(3);
         this.mTypeMsg = var9;
         long var10 = var1.getLong(4);
         this.mTimeLimit = var10;
         long var12 = var1.getLong(5);
         this.mSizeLimit = var12;
         int var14 = var1.getInt(6);
         this.mPeakTime = var14;
         int var15 = var1.getInt(7);
         this.mOffPeakTime = var15;
         String var16 = var1.getString(8);
         this.mDays = var16;
         String var17 = var1.getString(9);
         this.mPeakStartTime = var17;
         String var18 = var1.getString(10);
         this.mPeakEndTime = var18;
         int var19 = var1.getInt(11);
         this.mWhileroaming = var19;
         int var20 = var1.getInt(12);
         this.mAttachmentEnabled = var20;
         return this;
      }

      public Uri save(Context var1) {
         if(this.isSaved()) {
            throw new UnsupportedOperationException();
         } else {
            return null;
         }
      }

      public void setAccountKey(long var1) {
         this.mAccountKey = var1;
      }

      public void setSevenAccountKey(int var1) {
         long var2 = (long)var1;
         this.mSevenAccountKey = var2;
      }

      public void setSizeLimit(long var1) {
         this.mSizeLimit = var1;
      }

      public void setTimeLimit(long var1) {
         this.mTimeLimit = var1;
      }

      public void setTypeMsg(int var1) {
         this.mTypeMsg = var1;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         Long var2 = Long.valueOf(this.mAccountKey);
         var1.put("accountKey", var2);
         Long var3 = Long.valueOf(this.mSevenAccountKey);
         var1.put("sevenAccountKey", var3);
         Long var4 = Long.valueOf(this.mTimeLimit);
         var1.put("timeLimit", var4);
         Long var5 = Long.valueOf(this.mSizeLimit);
         var1.put("sizeLimit", var5);
         Integer var6 = Integer.valueOf(this.mTypeMsg);
         var1.put("typeMsg", var6);
         Integer var7 = Integer.valueOf(this.mPeakTime);
         var1.put("peakTime", var7);
         Integer var8 = Integer.valueOf(this.mOffPeakTime);
         var1.put("offPeakTime", var8);
         String var9 = this.mDays;
         var1.put("days", var9);
         String var10 = this.mPeakStartTime;
         var1.put("peakStartTime", var10);
         String var11 = this.mPeakEndTime;
         var1.put("peakEndTime", var11);
         Integer var12 = Integer.valueOf(this.mWhileroaming);
         var1.put("whileRoaming", var12);
         Integer var13 = Integer.valueOf(this.mAttachmentEnabled);
         var1.put("attachmentEnabled", var13);
         return var1;
      }

      public String toString() {
         return "";
      }

      public int update(Context var1, ContentValues var2) {
         ContentResolver var3 = var1.getContentResolver();
         Uri var4 = CONTENT_URI;
         String[] var5 = new String[1];
         String var6 = Long.toString(this.mId);
         var5[0] = var6;
         return var3.update(var4, var2, "accountKey=?", var5);
      }

      public void writeToParcel(Parcel var1, int var2) {}
   }

   public static final class RecipientInformationCache extends EmailContent implements EmailContent.RecipientInformationColumns {

      public static final int CONTENT_ALIAS = 5;
      public static final int CONTENT_AccountKey_COLUMN = 1;
      public static final int CONTENT_EMAIL_ADDRESS_COLUMN = 3;
      public static final int CONTENT_FILEAS = 4;
      public static final Uri CONTENT_FILTER_URI;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_SERVER_ID_COLUMN = 2;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_WEIGHTEDRANK = 6;
      public static final String TABLE_NAME = "RecipientInformation";
      public long riAccountKey;
      public String riAlias;
      public String riEmailAddress;
      public String riFileAs;
      public long riMailboxKey;
      public String riServerId;
      public String riWeightedRank;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/recipientInformation").toString());
         CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
      }

      public RecipientInformationCache() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public void addSaveRiOps(ArrayList<ContentProviderOperation> var1) {
         Builder var2 = ContentProviderOperation.newInsert(CONTENT_URI);
         ContentValues var3 = this.toContentValues();
         ContentProviderOperation var4 = var2.withValues(var3).build();
         var1.add(var4);
      }

      public <T extends EmailContent> T restore(Cursor var1) {
         return null;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.riServerId;
         var1.put("server_id", var2);
         Long var3 = Long.valueOf(this.riAccountKey);
         var1.put("accountkey", var3);
         String var4 = this.riEmailAddress;
         var1.put("email_address", var4);
         String var5 = this.riFileAs;
         var1.put("fileas", var5);
         String var6 = this.riAlias;
         var1.put("alias", var6);
         String var7 = this.riWeightedRank;
         var1.put("weightedrank", var7);
         return var1;
      }
   }

   public interface MessageColumns {

      String ACCOUNT_KEY = "accountKey";
      String BCC_LIST = "bccList";
      String CC_LIST = "ccList";
      String CLIENT_ID = "clientId";
      String CONVERSATION_ID = "conversationId";
      String CONVERSATION_INDEX = "conversationIndex";
      String DISPLAY_NAME = "displayName";
      String DST_MAILBOX_KEY = "dstMailboxKey";
      String ENCRYPTION_ALGORITHM = "encryptionAlgorithm";
      String FLAGS = "flags";
      String FLAGSTATUS = "flagStatus";
      String FLAG_ATTACHMENT = "flagAttachment";
      String FLAG_FAVORITE = "flagFavorite";
      String FLAG_LOADED = "flagLoaded";
      String FLAG_MOVED = "flagMoved";
      String FLAG_READ = "flagRead";
      String FLAG_REPLY = "flagReply";
      String FOLLOWUP_FLAG = "followupflag";
      String FROM_LIST = "fromList";
      String ID = "_id";
      String IMPORTANCE = "importance";
      String IRM_CONTENT_EXPIRY_DATE = "IRMContentExpiryDate";
      String IRM_CONTENT_OWNER = "IRMContentOwner";
      String IRM_LICENSE_FLAG = "IRMLicenseFlag";
      String IRM_OWNER = "IRMOwner";
      String IRM_TEMPLATE_ID = "IRMTemplateId";
      String ISMIMELOADED = "isMimeLoaded";
      String ISTRUNCATED = "istruncated";
      String LAST_VERB = "lastVerb";
      String LAST_VERB_TIME = "lastVerbTime";
      String MAILBOX_KEY = "mailboxKey";
      String MEETING_INFO = "meetingInfo";
      String MESSAGE_DIRTY = "messageDirty";
      String MESSAGE_ID = "messageId";
      String MESSAGE_TYPE = "messageType";
      String ORIGINAL_ID = "originalId";
      String REPLY_TO_LIST = "replyToList";
      String SMIME_FLAGS = "smimeFlags";
      String SNIPPET = "snippet";
      String SUBJECT = "subject";
      String THREAD_ID = "threadId";
      String THREAD_NAME = "threadName";
      String TIMESTAMP = "timeStamp";
      String TO_LIST = "toList";
      String UM_CALLER_ID = "umCallerId";
      String UM_USER_NOTES = "umUserNotes";

   }

   public interface MailboxColumns {

      String ACCOUNT_KEY = "accountKey";
      String DELIMITER = "delimiter";
      String DISPLAY_NAME = "displayName";
      String DISPLAY_NAME_AJ = "displayname";
      String DST_MAILBOX_ID = "dstMailboxId";
      String FLAGS = "flags";
      String FLAG_CHANGED = "flagChanged";
      String FLAG_NOSELECT = "flagNoSelect";
      String FLAG_VISIBLE = "flagVisible";
      String ID = "_id";
      String NEW_DISPLAY_NAME = "newDisplayName";
      String PARENT_SERVER_ID = "parentServerId";
      String SERVER_ID = "serverId";
      String SYNC_INTERVAL = "syncInterval";
      String SYNC_KEY = "syncKey";
      String SYNC_LOOKBACK = "syncLookback";
      String SYNC_STATUS = "syncStatus";
      String SYNC_TIME = "syncTime";
      String TYPE = "type";
      String UNREAD_COUNT = "unreadCount";
      String VISIBLE_LIMIT = "visibleLimit";

   }

   public interface HistoryAccountColumns {

      Uri CONTENT_URI;
      String EMAIL = "EmailAddress";
      String ID = "_id";
      String TABLE_NAME = "historyAccount";
      String TIMEDATE = "TimeDate";


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/historyAccount").toString());
      }
   }

   public static final class Mailbox extends EmailContent implements EmailContent.SyncColumns, EmailContent.MailboxColumns {

      public static final Uri ADD_TO_FIELD_URI;
      public static final int CHECK_INTERVAL_NEVER = 255;
      public static final int CHECK_INTERVAL_PING = 253;
      public static final int CHECK_INTERVAL_PUSH = 254;
      public static final int CHECK_INTERVAL_PUSH_HOLD = 252;
      public static final int CONTENT_ACCOUNT_KEY_COLUMN = 4;
      public static final int CONTENT_DELIMITER_COLUMN = 6;
      public static final int CONTENT_DISPLAY_NAME_COLUMN = 1;
      public static final int CONTENT_DST_SERVER_ID = 18;
      public static final int CONTENT_FLAGS_COLUMN = 14;
      public static final int CONTENT_FLAG_CHANGED = 17;
      public static final int CONTENT_FLAG_NOSELECT_COLUMN = 13;
      public static final int CONTENT_FLAG_VISIBLE_COLUMN = 12;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_MAILBOX_KEY_COLUMN = 20;
      public static final int CONTENT_NEW_DISPLAY_NAME = 19;
      public static final int CONTENT_PARENT_SERVER_ID_COLUMN = 3;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_SERVER_ID_COLUMN = 2;
      public static final int CONTENT_SEVEN_MAILBOX_KEY_COLUMN = 22;
      public static final int CONTENT_SYNC_FLAG_COLUMN = 23;
      public static final int CONTENT_SYNC_INTERVAL_COLUMN = 9;
      public static final int CONTENT_SYNC_KEY_COLUMN = 7;
      public static final int CONTENT_SYNC_LOOKBACK_COLUMN = 8;
      public static final int CONTENT_SYNC_STATUS_COLUMN = 16;
      public static final int CONTENT_SYNC_TIME_COLUMN = 10;
      public static final int CONTENT_TYPE_COLUMN = 5;
      public static final int CONTENT_TYPE_MSG_COLUMN = 21;
      public static final int CONTENT_UNREAD_COUNT_COLUMN = 11;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_VISIBLE_LIMIT_COLUMN = 15;
      public static final int FLAG_CANT_PUSH = 4;
      public static final int FLAG_CHILDREN_VISIBLE = 2;
      public static final int FLAG_FOLDER_CREATED = 4;
      public static final int FLAG_FOLDER_DELETED = 1;
      public static final int FLAG_FOLDER_UPDATED = 2;
      public static final int FLAG_HAS_CHILDREN = 1;
      public static final long NO_MAILBOX = 255L;
      public static final long QUERY_ALL_DRAFTS = 251L;
      public static final long QUERY_ALL_FAVORITES = 252L;
      public static final long QUERY_ALL_INBOXES = 254L;
      public static final long QUERY_ALL_OUTBOX = 250L;
      public static final long QUERY_ALL_UNREAD = 253L;
      public static final String TABLE_NAME = "Mailbox";
      public static final int TYPE_CALENDAR = 65;
      public static final int TYPE_CONTACTS = 66;
      public static final int TYPE_DRAFTS = 3;
      public static final int TYPE_EAS_ACCOUNT_MAILBOX = 68;
      public static final int TYPE_INBOX = 0;
      public static final int TYPE_JOURNAL = 70;
      public static final int TYPE_JUNK = 7;
      public static final int TYPE_MAIL = 1;
      public static final int TYPE_NOTES = 69;
      public static final int TYPE_NOT_EMAIL = 64;
      public static final int TYPE_OUTBOX = 4;
      public static final int TYPE_PARENT = 2;
      public static final int TYPE_RECIPIENT_INFORMATION_CACHE = 97;
      public static final int TYPE_SEARCH_RESULTS = 8;
      public static final int TYPE_SENT = 5;
      public static final int TYPE_TASKS = 67;
      public static final int TYPE_TRASH = 6;
      public static final int TYPE_UNKNOWN_FOLDER = 96;
      public static final int TYPE_USER_CALENDAR = 82;
      public static final int TYPE_USER_CONTACTS = 83;
      public static final int TYPE_USER_CREATED_MAIL = 12;
      public static final int TYPE_USER_JOURNAL = 85;
      public static final int TYPE_USER_NOTES = 84;
      public static final int TYPE_USER_TASKS = 81;
      private static final String WHERE_TYPE_AND_ACCOUNT_KEY = "type=? and accountKey=?";
      public long mAccountKey;
      public int mDelimiter;
      public String mDisplayName;
      public String mDstServerId;
      public int mFlagChanged;
      public boolean mFlagNoSelect = 0;
      public boolean mFlagVisible = 1;
      public int mFlags;
      public long mMailboxKey;
      public String mNewDisplayName;
      public String mParentServerId;
      public String mServerId;
      public long mSevenMailboxKey;
      public int mSyncFlag;
      public int mSyncInterval;
      public String mSyncKey;
      public int mSyncLookback;
      public String mSyncStatus;
      public long mSyncTime;
      public int mType;
      public int mTypeMsg;
      public int mUnreadCount;
      public int mVisibleLimit;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/mailbox").toString());
         StringBuilder var2 = new StringBuilder();
         Uri var3 = EmailContent.CONTENT_URI;
         ADD_TO_FIELD_URI = Uri.parse(var2.append(var3).append("/mailboxIdAddToField").toString());
         String[] var4 = new String[]{"_id", "displayName", "serverId", "parentServerId", "accountKey", "type", "delimiter", "syncKey", "syncLookback", "syncInterval", "syncTime", "unreadCount", "flagVisible", "flagNoSelect", "flags", "visibleLimit", "syncStatus", "flagChanged", "dstMailboxId", "newDisplayName", "mailboxKey", "typeMsg", "sevenMailboxKey", "syncFlag"};
         CONTENT_PROJECTION = var4;
      }

      public Mailbox() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public static long findMailboxOfType(Context param0, long param1, int param3) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Mailbox restoreMailboxOfType(Context var0, long var1, int var3) {
         long var4 = findMailboxOfType(var0, var1, var3);
         EmailContent.Mailbox var6;
         if(var4 != 65535L) {
            var6 = restoreMailboxWithId(var0, var4);
         } else {
            var6 = null;
         }

         return var6;
      }

      public static EmailContent.Mailbox restoreMailboxWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Mailbox restoreMailboxWithParentId(Context param0, String param1, Long param2) {
         // $FF: Couldn't be decompiled
      }

      public EmailContent.Mailbox restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         String var5 = var1.getString(1);
         this.mDisplayName = var5;
         String var6 = var1.getString(2);
         this.mServerId = var6;
         String var7 = var1.getString(3);
         this.mParentServerId = var7;
         long var8 = var1.getLong(4);
         this.mAccountKey = var8;
         int var10 = var1.getInt(5);
         this.mType = var10;
         int var11 = var1.getInt(6);
         this.mDelimiter = var11;
         String var12 = var1.getString(7);
         this.mSyncKey = var12;
         int var13 = var1.getInt(8);
         this.mSyncLookback = var13;
         int var14 = var1.getInt(9);
         this.mSyncInterval = var14;
         long var15 = var1.getLong(10);
         this.mSyncTime = var15;
         int var17 = var1.getInt(11);
         this.mUnreadCount = var17;
         byte var18;
         if(var1.getInt(12) == 1) {
            var18 = 1;
         } else {
            var18 = 0;
         }

         this.mFlagVisible = (boolean)var18;
         byte var19;
         if(var1.getInt(13) == 0) {
            var19 = 1;
         } else {
            var19 = 0;
         }

         this.mFlagNoSelect = (boolean)var19;
         int var20 = var1.getInt(14);
         this.mFlags = var20;
         int var21 = var1.getInt(15);
         this.mVisibleLimit = var21;
         String var22 = var1.getString(16);
         this.mSyncStatus = var22;
         int var23 = var1.getInt(17);
         this.mFlagChanged = var23;
         String var24 = var1.getString(18);
         this.mDstServerId = var24;
         String var25 = var1.getString(19);
         this.mNewDisplayName = var25;
         long var26 = var1.getLong(20);
         this.mMailboxKey = var26;
         int var28 = var1.getInt(21);
         this.mTypeMsg = var28;
         long var29 = var1.getLong(22);
         this.mSevenMailboxKey = var29;
         int var31 = var1.getInt(23);
         this.mSyncFlag = var31;
         return this;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.mDisplayName;
         var1.put("displayName", var2);
         String var3 = this.mServerId;
         var1.put("serverId", var3);
         String var4 = this.mParentServerId;
         var1.put("parentServerId", var4);
         Long var5 = Long.valueOf(this.mAccountKey);
         var1.put("accountKey", var5);
         Integer var6 = Integer.valueOf(this.mType);
         var1.put("type", var6);
         Integer var7 = Integer.valueOf(this.mDelimiter);
         var1.put("delimiter", var7);
         String var8 = this.mSyncKey;
         var1.put("syncKey", var8);
         Integer var9 = Integer.valueOf(this.mSyncLookback);
         var1.put("syncLookback", var9);
         Integer var10 = Integer.valueOf(this.mSyncInterval);
         var1.put("syncInterval", var10);
         Long var11 = Long.valueOf(this.mSyncTime);
         var1.put("syncTime", var11);
         Integer var12 = Integer.valueOf(this.mUnreadCount);
         var1.put("unreadCount", var12);
         Boolean var13 = Boolean.valueOf(this.mFlagVisible);
         var1.put("flagVisible", var13);
         Boolean var14 = Boolean.valueOf(this.mFlagNoSelect);
         var1.put("flagNoSelect", var14);
         Integer var15 = Integer.valueOf(this.mFlags);
         var1.put("flags", var15);
         Integer var16 = Integer.valueOf(this.mVisibleLimit);
         var1.put("visibleLimit", var16);
         String var17 = this.mSyncStatus;
         var1.put("syncStatus", var17);
         Integer var18 = Integer.valueOf(this.mFlagChanged);
         var1.put("flagChanged", var18);
         String var19 = this.mDstServerId;
         var1.put("dstMailboxId", var19);
         String var20 = this.mNewDisplayName;
         var1.put("newDisplayName", var20);
         return var1;
      }
   }

   public static class Policies extends EmailContent implements EmailContent.PoliciesColumns, Parcelable {

      public static final int CONTENT_ACCOUNT_ID_COLUMN = 4;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_NAME_COLUMN = 1;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_TYPE_COLUMN = 2;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_VALUE_COLUMN = 3;
      public static final Creator<EmailContent.Policies> CREATOR;
      public static final String TABLE_NAME = "Policies";
      public long mAccountId;
      public String mName;
      public String mType;
      public String mValue;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/policies").toString());
         String[] var2 = new String[]{"_id", "name", "type", "value", "account_id"};
         CONTENT_PROJECTION = var2;
         CREATOR = new EmailContent.Policies.1();
      }

      public Policies() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public Policies(Parcel var1) {
         super((EmailContent.1)null);
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.readLong();
         this.mId = var3;
         String var5 = var1.readString();
         this.mName = var5;
         String var6 = var1.readString();
         this.mType = var6;
         String var7 = var1.readString();
         this.mValue = var7;
         long var8 = var1.readLong();
         this.mAccountId = var8;
      }

      public static int getNumberOfPoliciesForAccount(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Policies restoreHostAuthWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public int describeContents() {
         return 0;
      }

      public EmailContent.Policies restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         String var5 = var1.getString(1);
         this.mName = var5;
         String var6 = var1.getString(2);
         this.mType = var6;
         String var7 = var1.getString(3);
         this.mValue = var7;
         long var8 = var1.getLong(4);
         this.mAccountId = var8;
         return this;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.mName;
         var1.put("name", var2);
         String var3 = this.mType;
         var1.put("type", var3);
         String var4 = this.mValue;
         var1.put("value", var4);
         Long var5 = Long.valueOf(this.mAccountId);
         var1.put("account_id", var5);
         return var1;
      }

      public void writeToParcel(Parcel var1, int var2) {
         long var3 = this.mId;
         var1.writeLong(var3);
         String var5 = this.mName;
         var1.writeString(var5);
         String var6 = this.mType;
         var1.writeString(var6);
         String var7 = this.mValue;
         var1.writeString(var7);
         long var8 = this.mAccountId;
         var1.writeLong(var8);
      }

      static class 1 implements Creator<EmailContent.Policies> {

         1() {}

         public EmailContent.Policies createFromParcel(Parcel var1) {
            return new EmailContent.Policies(var1);
         }

         public EmailContent.Policies[] newArray(int var1) {
            return new EmailContent.Policies[var1];
         }
      }
   }

   public interface HostAuthColumns {

      String ACCOUNT_KEY = "accountKey";
      String ADDRESS = "address";
      String DOMAIN = "domain";
      String FLAGS = "flags";
      String ID = "_id";
      String LOGIN = "login";
      String PASSWORD = "password";
      String PORT = "port";
      String PROTOCOL = "protocol";

   }

   public interface RecipientInformationColumns {

      String ACCOUNTKEY = "accountkey";
      String ALIAS = "alias";
      String EMAILADDRESS = "email_address";
      String FILEAS = "fileas";
      String ID = "_id";
      String SERVERID = "server_id";
      String WEIGHTEDRANK = "weightedrank";

   }

   public interface PoliciesColumns {

      String ACCOUNT_ID = "account_id";
      String ID = "_id";
      String NAME = "name";
      String TYPE = "type";
      String VALUE = "value";

   }

   public static final class MailboxCB extends EmailContent implements EmailContent.SyncColumns, EmailContent.MailboxCBColumns {

      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_MAILBOX_KEY_COLUMN = 1;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_SEVEN_MAILBOX_KEY_COLUMN = 3;
      public static final int CONTENT_SYNC_FLAG = 4;
      public static final int CONTENT_TYPE_MESSAGE_COLUMN = 2;
      public static final Uri CONTENT_URI;
      public static final long NO_MAILBOX = 255L;
      public static final String TABLE_NAME = "Mailbox_CB";
      public static final int TYPE_MSG_BASIC = 0;
      public static final int TYPE_MSG_SEVEN = 1;
      private static final String WHERE_TYPE_AND_ACCOUNT_KEY = "typeMsg=? and mailboxKey=?";
      public long mMailboxKey;
      public long mSevenMailboxKey;
      public int mSyncFlag;
      public int mTypeMsg;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/mailboxcb").toString());
         String[] var2 = new String[]{"_id", "mailboxKey", "typeMsg", "sevenMailboxKey", "syncFlag"};
         CONTENT_PROJECTION = var2;
      }

      public MailboxCB() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public static long findMailboxKey(Context param0, long param1, long param3) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.MailboxCB restoreMailboxWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public EmailContent.MailboxCB restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         long var5 = var1.getLong(1);
         this.mMailboxKey = var5;
         int var7 = var1.getInt(2);
         this.mTypeMsg = var7;
         long var8 = var1.getLong(3);
         this.mSevenMailboxKey = var8;
         int var10 = var1.getInt(4);
         this.mSyncFlag = var10;
         return this;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         Long var2 = Long.valueOf(this.mMailboxKey);
         var1.put("mailboxKey", var2);
         Integer var3 = Integer.valueOf(this.mTypeMsg);
         var1.put("typeMsg", var3);
         Long var4 = Long.valueOf(this.mSevenMailboxKey);
         var1.put("sevenMailboxKey", var4);
         Integer var5 = Integer.valueOf(this.mSyncFlag);
         var1.put("syncFlag", var5);
         return var1;
      }
   }

   public interface AccountColumns {

      String CALENDAR_SYNC_LOOKBACK = "calendarSyncLookback";
      String CBA_CERTIFICATE_ALIAS = "cbaCertificateAlias";
      String COMPATIBILITY_UUID = "compatibilityUuid";
      String CONFLICT_RESOLUTION = "conflict";
      String CONVERSATION_MODE = "conversationMode";
      String DEVICE_BLOCK_TYPE = "deviceBlockedType";
      String DEVICE_INFO_SENT = "deviceInfoSent";
      String DISPLAY_NAME = "displayName";
      String EMAIL_ADDRESS = "emailAddress";
      String EMAIL_SIZE = "emailsize";
      String FLAGS = "flags";
      String HOST_AUTH_KEY_RECV = "hostAuthKeyRecv";
      String HOST_AUTH_KEY_SEND = "hostAuthKeySend";
      String ID = "_id";
      String IS_DEFAULT = "isDefault";
      String NEW_MESSAGE_COUNT = "newMessageCount";
      String OFF_PEAK_SCHEDULE = "offPeakSchedule";
      String PEAK_DAYS = "peakDays";
      String PEAK_END_MINUTE = "peakEndMinute";
      String PEAK_SCHEDULE = "peakSchedule";
      String PEAK_START_MINUTE = "peakStartMinute";
      String POLICY_KEY = "policyKey";
      String PROTOCOL_VERSION = "protocolVersion";
      String RINGTONE_URI = "ringtoneUri";
      String ROAMING_SCHEDULE = "roamingSchedule";
      String SECURITY_FLAGS = "securityFlags";
      String SECURITY_SYNC_KEY = "securitySyncKey";
      String SENDER_NAME = "senderName";
      String SIGNATURE = "signature";
      String SMIME_OPTIONS_ENCRYPTION_ALGORITHM = "smimeEncryptionAlgorithm";
      String SMIME_OPTIONS_FLAGS = "smimeOptionsFlags";
      String SMIME_OPTIONS_SIGN_ALGORITHM = "smimeSignAlgorithm";
      String SMIME_OWN_CERTIFICATE_ALIAS = "smimeOwnCertificateAlias";
      String SYNC_INTERVAL = "syncInterval";
      String SYNC_KEY = "syncKey";
      String SYNC_LOOKBACK = "syncLookback";
      String TEXT_PREVIEW_SIZE = "textPreview";
      String TYPE_MESSAGE = "typeMsg";

   }

   public static final class HostAuth extends EmailContent implements EmailContent.HostAuthColumns, Parcelable {

      public static final int CONTENT_ACCOUNT_KEY_COLUMN = 8;
      public static final int CONTENT_ADDRESS_COLUMN = 2;
      public static final int CONTENT_DOMAIN_COLUMN = 7;
      public static final int CONTENT_FLAGS_COLUMN = 4;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_LOGIN_COLUMN = 5;
      public static final int CONTENT_PASSWORD_COLUMN = 6;
      public static final int CONTENT_PORT_COLUMN = 3;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_PROTOCOL_COLUMN = 1;
      public static final Uri CONTENT_URI;
      public static final Creator<EmailContent.HostAuth> CREATOR;
      public static final int FLAG_AUTHENTICATE = 4;
      public static final int FLAG_SSL = 1;
      public static final int FLAG_TLS = 2;
      public static final int FLAG_TRUST_ALL_CERTIFICATES = 8;
      public static final String TABLE_NAME = "HostAuth";
      public long mAccountKey;
      public String mAddress;
      public String mDomain;
      public int mFlags;
      public String mLogin;
      public String mPassword;
      public int mPort;
      public String mProtocol;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/hostauth").toString());
         String[] var2 = new String[]{"_id", "protocol", "address", "port", "flags", "login", "password", "domain", "accountKey"};
         CONTENT_PROJECTION = var2;
         CREATOR = new EmailContent.HostAuth.1();
      }

      public HostAuth() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
         this.mPort = -1;
      }

      public HostAuth(Parcel var1) {
         super((EmailContent.1)null);
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.readLong();
         this.mId = var3;
         String var5 = var1.readString();
         this.mProtocol = var5;
         String var6 = var1.readString();
         this.mAddress = var6;
         int var7 = var1.readInt();
         this.mPort = var7;
         int var8 = var1.readInt();
         this.mFlags = var8;
         String var9 = var1.readString();
         this.mLogin = var9;
         String var10 = var1.readString();
         this.mPassword = var10;
         String var11 = var1.readString();
         this.mDomain = var11;
         long var12 = var1.readLong();
         this.mAccountKey = var12;
      }

      public static EmailContent.HostAuth restoreHostAuthWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public int describeContents() {
         return 0;
      }

      public String getStoreUri() {
         String var1;
         switch(this.mFlags & 11) {
         case 1:
            var1 = "+ssl+";
            break;
         case 2:
            var1 = "+tls+";
            break;
         case 9:
            var1 = "+ssl+trustallcerts";
            break;
         case 10:
            var1 = "+tls+trustallcerts";
            break;
         default:
            var1 = "";
         }

         String var2 = null;
         if((this.mFlags & 4) != 0) {
            String var3;
            if(this.mLogin != null) {
               var3 = this.mLogin.trim();
            } else {
               var3 = "";
            }

            String var4;
            if(this.mPassword != null) {
               var4 = this.mPassword.trim();
            } else {
               var4 = "";
            }

            var2 = var3 + ":" + var4;
         }

         String var5;
         if(this.mAddress != null) {
            var5 = this.mAddress.trim();
         } else {
            var5 = null;
         }

         String var8;
         if(this.mDomain != null) {
            StringBuilder var6 = (new StringBuilder()).append("/");
            String var7 = this.mDomain;
            var8 = var6.append(var7).toString();
         } else {
            var8 = null;
         }

         String var13;
         String var14;
         try {
            StringBuilder var9 = new StringBuilder();
            String var10 = this.mProtocol;
            String var11 = var9.append(var10).append(var1).toString();
            int var12 = this.mPort;
            var13 = (new URI(var11, var2, var5, var12, var8, (String)null, (String)null)).toString();
         } catch (URISyntaxException var16) {
            var14 = null;
            return var14;
         }

         var14 = var13;
         return var14;
      }

      public EmailContent.HostAuth restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         String var5 = var1.getString(1);
         this.mProtocol = var5;
         String var6 = var1.getString(2);
         this.mAddress = var6;
         int var7 = var1.getInt(3);
         this.mPort = var7;
         int var8 = var1.getInt(4);
         this.mFlags = var8;
         String var9 = var1.getString(5);
         this.mLogin = var9;
         String var10 = var1.getString(6);
         this.mPassword = var10;
         String var11 = var1.getString(7);
         this.mDomain = var11;
         long var12 = var1.getLong(8);
         this.mAccountKey = var12;
         return this;
      }

      @Deprecated
      public void setStoreUri(String param1) {
         // $FF: Couldn't be decompiled
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.mProtocol;
         var1.put("protocol", var2);
         String var3 = this.mAddress;
         var1.put("address", var3);
         Integer var4 = Integer.valueOf(this.mPort);
         var1.put("port", var4);
         Integer var5 = Integer.valueOf(this.mFlags);
         var1.put("flags", var5);
         String var6 = this.mLogin;
         var1.put("login", var6);
         String var7 = this.mPassword;
         var1.put("password", var7);
         String var8 = this.mDomain;
         var1.put("domain", var8);
         Long var9 = Long.valueOf(this.mAccountKey);
         var1.put("accountKey", var9);
         return var1;
      }

      public String toString() {
         return this.getStoreUri();
      }

      public void writeToParcel(Parcel var1, int var2) {
         long var3 = this.mId;
         var1.writeLong(var3);
         String var5 = this.mProtocol;
         var1.writeString(var5);
         String var6 = this.mAddress;
         var1.writeString(var6);
         int var7 = this.mPort;
         var1.writeInt(var7);
         int var8 = this.mFlags;
         var1.writeInt(var8);
         String var9 = this.mLogin;
         var1.writeString(var9);
         String var10 = this.mPassword;
         var1.writeString(var10);
         String var11 = this.mDomain;
         var1.writeString(var11);
         long var12 = this.mAccountKey;
         var1.writeLong(var12);
      }

      static class 1 implements Creator<EmailContent.HostAuth> {

         1() {}

         public EmailContent.HostAuth createFromParcel(Parcel var1) {
            return new EmailContent.HostAuth(var1);
         }

         public EmailContent.HostAuth[] newArray(int var1) {
            return new EmailContent.HostAuth[var1];
         }
      }
   }

   public interface AccountCBColumns {

      String ACCOUNT_KEY = "accountKey";
      String ATTACHMENT_ENABLED = "attachmentEnabled";
      String DAYS = "days";
      String ID = "_id";
      String OFF_PEAK_TIME = "offPeakTime";
      String PEAK_END_TIME = "peakEndTime";
      String PEAK_START_TIME = "peakStartTime";
      String PEAK_TIME = "peakTime";
      String RECV_PROTOCOL = "recvProtocol";
      String SEND_PROTOCOL = "sendProtocol";
      String SEVEN_ACCOUNT_KEY = "sevenAccountKey";
      String SIZE_LIMIT = "sizeLimit";
      String TIME_LIMIT = "timeLimit";
      String TYPE_MSG = "typeMsg";
      String WHILE_ROAMING = "whileRoaming";

   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState = new int[EmailContent.FollowupFlag.FollowupFlagDefaultState.values().length];
      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus;


      static {
         try {
            int[] var0 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var1 = EmailContent.FollowupFlag.FollowupFlagDefaultState.TODAY.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var39) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var3 = EmailContent.FollowupFlag.FollowupFlagDefaultState.TOMORROW.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var38) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var5 = EmailContent.FollowupFlag.FollowupFlagDefaultState.THIS_WEEK.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var37) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var7 = EmailContent.FollowupFlag.FollowupFlagDefaultState.NEXT_WEEK.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var36) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var9 = EmailContent.FollowupFlag.FollowupFlagDefaultState.NO_DATE.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var35) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var11 = EmailContent.FollowupFlag.FollowupFlagDefaultState.MARK_COMPLETE.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var34) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagDefaultState;
            int var13 = EmailContent.FollowupFlag.FollowupFlagDefaultState.CLEAR.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var33) {
            ;
         }

         $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus = new int[EmailContent.FollowupFlag.FollowupFlagStatus.values().length];

         try {
            int[] var14 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus;
            int var15 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_COMPLETE.ordinal();
            var14[var15] = 1;
         } catch (NoSuchFieldError var32) {
            ;
         }

         try {
            int[] var16 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus;
            int var17 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE.ordinal();
            var16[var17] = 2;
         } catch (NoSuchFieldError var31) {
            ;
         }

         try {
            int[] var18 = $SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus;
            int var19 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_CLEARED.ordinal();
            var18[var19] = 3;
         } catch (NoSuchFieldError var30) {
            ;
         }
      }
   }

   public static final class Message extends EmailContent implements EmailContent.SyncColumns, EmailContent.MessageColumns {

      public static final int CONTENT_ACCOUNT_KEY_COLUMN = 13;
      public static final int CONTENT_BCC_LIST_COLUMN = 17;
      public static final int CONTENT_CC_LIST_COLUMN = 16;
      public static final int CONTENT_CLIENT_ID_COLUMN = 10;
      public static final int CONTENT_CONVERSATION_ID = 31;
      public static final int CONTENT_CONVERSATION_INDEX = 32;
      public static final int CONTENT_DISPLAY_NAME_COLUMN = 1;
      public static final int CONTENT_DST_MAILBOX_KEY_COLUMN = 26;
      public static final int CONTENT_ENCRYPTION_ALGORITHM = 30;
      public static final int CONTENT_FLAGS_COLUMN = 8;
      public static final int CONTENT_FLAG_ATTACHMENT_COLUMN = 7;
      public static final int CONTENT_FLAG_FAVORITE_COLUMN = 6;
      public static final int CONTENT_FLAG_LOADED_COLUMN = 5;
      public static final int CONTENT_FLAG_MOVED_COLUMN = 25;
      public static final int CONTENT_FLAG_READ_COLUMN = 4;
      public static final int CONTENT_FLAG_REPLY = 40;
      public static final int CONTENT_FLAG_STATUS_COLUMN = 27;
      public static final int CONTENT_FOLLOWUP_FLAG_COLUMN = 33;
      public static final int CONTENT_FROM_LIST_COLUMN = 14;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_IMPORTANCE_COLUMN = 23;
      public static final int CONTENT_IRM_CONTENT_EXPIRY_DATE = 44;
      public static final int CONTENT_IRM_CONTENT_OWNER = 45;
      public static final int CONTENT_IRM_LICENSE_FLAG = 46;
      public static final int CONTENT_IRM_OWNER = 47;
      public static final int CONTENT_IRM_TEMPLATE_ID = 43;
      public static final int CONTENT_ISMIMELOADED_COLUMN = 28;
      public static final int CONTENT_ISTRUNCATED_COLUMN = 24;
      public static final int CONTENT_LAST_VERB_COLUMN = 36;
      public static final int CONTENT_LAST_VERB_TIME_COLUMN = 37;
      public static final int CONTENT_MAILBOX_KEY_COLUMN = 12;
      public static final int CONTENT_MEETING_INFO_COLUMN = 20;
      public static final int CONTENT_MESSAGE_DIRTY = 39;
      public static final int CONTENT_MESSAGE_ID_COLUMN = 11;
      public static final int CONTENT_MESSAGE_TYPE = 38;
      public static final int CONTENT_MISSING_BODY_COLUMN = 50;
      public static final int CONTENT_MISSING_HTML_BODY_COLUMN = 51;
      public static final int CONTENT_ORIGINAL_ID = 41;
      public static final String[] CONTENT_PROJECTION;
      public static final String[] CONTENT_PROJECTION_UP_DEL;
      public static final int CONTENT_REPLY_TO_COLUMN = 18;
      public static final int CONTENT_SERVER_ID_COLUMN = 9;
      public static final int CONTENT_SERVER_TIMESTAMP_COLUMN = 19;
      public static final int CONTENT_SEVEN_ACCOUNT_KEY_COLUMN = 53;
      public static final int CONTENT_SEVEN_MESSAGE_KEY_COLUMN = 49;
      public static final int CONTENT_SMIME_FLAGS = 29;
      public static final int CONTENT_SNIPPET_COLUMN = 42;
      public static final int CONTENT_SUBJECT_COLUMN = 3;
      public static final int CONTENT_THREAD_ID_COLUMN = 21;
      public static final int CONTENT_THREAD_NAME_COLUMN = 22;
      public static final int CONTENT_TIMESTAMP_COLUMN = 2;
      public static final int CONTENT_TO_LIST_COLUMN = 15;
      public static final int CONTENT_TYPE_MESSAGE_COLUMN = 48;
      public static final int CONTENT_UM_CALLER_ID = 34;
      public static final int CONTENT_UM_USER_NOTES = 35;
      public static final int CONTENT_UNK_ENCODING_COLUMN = 52;
      public static final Uri CONTENT_URI;
      public static final Uri DELETED_CONTENT_URI;
      public static final String DELETED_TABLE_NAME = "Message_Deletes";
      public static final int ENCRYPT_SHIFT = 1;
      public static final int FLAG_DELIVERY_RECEIPT_REQUESTED = 2048;
      public static final int FLAG_INCOMING_MEETING_CANCEL = 8;
      public static final int FLAG_INCOMING_MEETING_INVITE = 4;
      public static final int FLAG_INCOMING_MEETING_MASK = 12;
      public static final int FLAG_LOADED_COMPLETE = 1;
      public static final int FLAG_LOADED_DELETED = 3;
      public static final int FLAG_LOADED_PARTIAL = 2;
      public static final int FLAG_LOADED_UNLOADED = 0;
      public static final int FLAG_ORIGINAL_MESSAGE_EDITED = 512;
      public static final int FLAG_OUTGOING_MEETING_ACCEPT = 64;
      public static final int FLAG_OUTGOING_MEETING_CANCEL = 32;
      public static final int FLAG_OUTGOING_MEETING_DECLINE = 128;
      public static final int FLAG_OUTGOING_MEETING_INVITE = 16;
      public static final int FLAG_OUTGOING_MEETING_MASK = 496;
      public static final int FLAG_OUTGOING_MEETING_REQUEST_MASK = 48;
      public static final int FLAG_OUTGOING_MEETING_TENTATIVE = 256;
      public static final int FLAG_READ_RECEIPT_REQUESTED = 1024;
      public static final byte FLAG_TRUNCATED_NO = 0;
      public static final byte FLAG_TRUNCATED_YES = 1;
      public static final int FLAG_TYPE_FORWARD = 2;
      public static final int FLAG_TYPE_MASK = 3;
      public static final int FLAG_TYPE_ORIGINAL = 0;
      public static final int FLAG_TYPE_REPLY = 1;
      public static final int ID_COLUMNS_ID_COLUMN = 0;
      public static final String[] ID_COLUMNS_PROJECTION;
      public static final int ID_COLUMNS_SYNC_SERVER_ID = 1;
      public static final String[] ID_COLUMN_PROJECTION;
      public static final int ID_MAILBOX_COLUMN_ID = 0;
      public static final int ID_MAILBOX_COLUMN_MAILBOX_KEY = 1;
      public static final String[] ID_MAILBOX_PROJECTION;
      public static final int ISMIMELOADED_NO = 0;
      public static final int ISMIMELOADED_YES = 1;
      public static final String KEY_TIMESTAMP_DESC = "timeStamp desc";
      public static final int LAST_VERB_FORWARD = 3;
      public static final int LAST_VERB_REPLYTOALL = 2;
      public static final int LAST_VERB_REPLYTOSENDER = 1;
      public static final int LAST_VERB_UNKNOWN = 0;
      public static final int LIST_ACCOUNT_KEY_COLUMN = 10;
      public static final int LIST_ATTACHMENT_COLUMN = 7;
      public static final int LIST_DISPLAY_NAME_COLUMN = 1;
      public static final int LIST_DST_MAILBOX_KEY_COLUMN = 15;
      public static final int LIST_FAVORITE_COLUMN = 6;
      public static final int LIST_FLAGS_COLUMN = 8;
      public static final int LIST_FLAG_MOVED_COLUMN = 14;
      public static final int LIST_FLAG_STATUS_COLUMN = 16;
      public static final int LIST_FOLLOWUP_FLAG_COLUMN = 17;
      public static final int LIST_ID_COLUMN = 0;
      public static final int LIST_IMPORTANCE_COLUMN = 12;
      public static final int LIST_ISTRUNCATED_COLUMN = 13;
      public static final int LIST_LAST_VERB = 18;
      public static final int LIST_LAST_VERB_TIME = 19;
      public static final int LIST_LOADED_COLUMN = 5;
      public static final int LIST_MAILBOX_KEY_COLUMN = 9;
      public static final String[] LIST_PROJECTION;
      public static final int LIST_READ_COLUMN = 4;
      public static final int LIST_SERVER_ID_COLUMN = 11;
      public static final int LIST_SNIPPET_COLUMN = 20;
      public static final int LIST_SUBJECT_COLUMN = 3;
      public static final int LIST_TIMESTAMP_COLUMN = 2;
      public static final int MESSAGE_DIRTY_FLAG = 1;
      public static final int MESSAGE_EMAIL = 0;
      public static final int MESSAGE_SMS = 256;
      public static final int MSG_CLASS_IRM_EDIT_ALLOWED = 8;
      public static final int MSG_CLASS_IRM_EXPORT_ALLOWED = 64;
      public static final int MSG_CLASS_IRM_EXTRACT_ALLOWED = 32;
      public static final int MSG_CLASS_IRM_FORWARD_ALLOWED = 2;
      public static final int MSG_CLASS_IRM_MODIFY_RECEPIENTS_ALLOWED = 16;
      public static final int MSG_CLASS_IRM_PRINT_ALLOWED = 128;
      public static final int MSG_CLASS_IRM_PROGRAMATIC_ACCESS_ALLOWED = 256;
      public static final int MSG_CLASS_IRM_REPLY_ALLOWED = 1;
      public static final int MSG_CLASS_IRM_REPLY_ALL_ALLOWED = 4;
      public static final int MSG_CLASS_SMIME_ENCRYPTED = 2;
      public static final int MSG_CLASS_SMIME_SIGNED = 1;
      public static final int MSG_SYNC_ID_OF_SEND_FAILED = 255;
      public static final int PROCESS_SHIFT = 2;
      public static final int READ = 1;
      public static final int SIGN_SHIFT = 0;
      public static final String SMS_DUMMY_CLIENT_ID = "SMS";
      public static final Uri SYNCED_CONTENT_URI;
      public static final String TABLE_NAME = "Message";
      public static final int UNREAD = 0;
      public static final Uri UPDATED_CONTENT_URI;
      public static final String UPDATED_TABLE_NAME = "Message_Updates";
      public static final int VERIFY_SHIFT = 3;
      public long mAccountKey;
      public transient ArrayList<EmailContent.Attachment> mAttachments = null;
      public String mBcc;
      public String mCc;
      public String mClientId;
      public String mConversationId;
      public byte[] mConversationIndex;
      public String mDisplayName;
      public int mDstMailBoxKey = -1;
      public transient boolean mEncrypted;
      public Integer mEncryptionAlgorithm;
      public boolean mFFlag = 0;
      public boolean mFlagAttachment = 0;
      public boolean mFlagFavorite = 0;
      public int mFlagLoaded = 0;
      public int mFlagMoved = 0;
      public boolean mFlagRead = 0;
      public boolean mFlagReply = 0;
      public int mFlagStatus = 0;
      public int mFlagTruncated = 0;
      public int mFlags = 0;
      public transient EmailContent.FollowupFlag mFollowupFlag = null;
      public String mFrom;
      public transient String mHtml;
      public transient String mHtmlReply;
      public String mIRMContentExpiryDate;
      public String mIRMContentOwner;
      public int mIRMLicenseFlag = -1;
      public int mIRMOwner;
      public String mIRMTemplateId;
      public int mImportance;
      public transient String mIntroText;
      public int mIsMimeLoaded = 0;
      public transient int mLastVerb;
      public transient long mLastVerbTime;
      public long mMailboxKey;
      public String mMeetingInfo;
      public int mMessageDirty;
      public String mMessageId;
      public long mMessageKey;
      public int mMessageType;
      public int mMissingBody;
      public int mMissingHtmlBody;
      public long mOriginalId = 65535L;
      public transient boolean mProcessed;
      public String mReplyTo;
      public String mServerId;
      public long mServerTimeStamp;
      public long mSevenAccountKey;
      public int mSevenMailboxKey;
      public long mSevenMessageKey;
      public transient boolean mSigned;
      public String mSnippet;
      public transient long mSourceKey;
      public String mSubject;
      public transient String mText;
      public transient String mTextReply;
      public long mThreadId = 0L;
      public String mThreadName = null;
      public long mTimeStamp;
      public String mTo;
      public int mTypeMsg;
      public String mUmCallerId;
      public String mUmUserNotes;
      public int mUnkEncoding;
      public transient boolean mVerified;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/message").toString());
         StringBuilder var2 = new StringBuilder();
         Uri var3 = EmailContent.CONTENT_URI;
         SYNCED_CONTENT_URI = Uri.parse(var2.append(var3).append("/syncedMessage").toString());
         StringBuilder var4 = new StringBuilder();
         Uri var5 = EmailContent.CONTENT_URI;
         DELETED_CONTENT_URI = Uri.parse(var4.append(var5).append("/deletedMessage").toString());
         StringBuilder var6 = new StringBuilder();
         Uri var7 = EmailContent.CONTENT_URI;
         UPDATED_CONTENT_URI = Uri.parse(var6.append(var7).append("/updatedMessage").toString());
         String[] var8 = new String[]{"_id", "displayName", "timeStamp", "subject", "flagRead", "flagLoaded", "flagFavorite", "flagAttachment", "flags", "syncServerId", "clientId", "messageId", "mailboxKey", "accountKey", "fromList", "toList", "ccList", "bccList", "replyToList", "syncServerTimeStamp", "meetingInfo", "threadId", "threadName", "importance", "istruncated", "flagMoved", "dstMailboxKey", "flagStatus", "isMimeLoaded", "smimeFlags", "encryptionAlgorithm", "conversationId", "conversationIndex", "followupflag", "umCallerId", "umUserNotes", "lastVerb", "lastVerbTime", "messageType", "messageDirty", "flagReply", "originalId", "snippet", "IRMTemplateId", "IRMContentExpiryDate", "IRMContentOwner", "IRMLicenseFlag", "IRMOwner", "typeMsg", "sevenMessageKey", "missingBody", "missingHtmlBody", "unkEncoding", "sevenAccountKey"};
         CONTENT_PROJECTION = var8;
         String[] var9 = new String[]{"_id", "displayName", "timeStamp", "subject", "flagRead", "flagLoaded", "flagFavorite", "flagAttachment", "flags", "syncServerId", "clientId", "messageId", "mailboxKey", "accountKey", "fromList", "toList", "ccList", "bccList", "replyToList", "syncServerTimeStamp", "meetingInfo", "threadId", "threadName", "importance", "istruncated", "flagMoved", "dstMailboxKey", "flagStatus", "isMimeLoaded", "smimeFlags", "encryptionAlgorithm", "conversationId", "conversationIndex", "followupflag", "umCallerId", "umUserNotes", "lastVerb", "lastVerbTime", "messageType", "messageDirty", "flagReply", "originalId", "snippet", "IRMTemplateId", "IRMContentExpiryDate", "IRMContentOwner", "IRMLicenseFlag", "IRMOwner"};
         CONTENT_PROJECTION_UP_DEL = var9;
         String[] var10 = new String[]{"_id", "displayName", "timeStamp", "subject", "flagRead", "flagLoaded", "flagFavorite", "flagAttachment", "flags", "mailboxKey", "accountKey", "syncServerId", "importance", "istruncated", "flagMoved", "dstMailboxKey", "flagStatus", "isMimeLoaded", "followupflag", "syncServerId", "lastVerb", "lastVerbTime", "snippet"};
         LIST_PROJECTION = var10;
         String[] var11 = new String[]{"_id", "syncServerId"};
         ID_COLUMNS_PROJECTION = var11;
         String[] var12 = new String[]{"_id", "mailboxKey"};
         ID_MAILBOX_PROJECTION = var12;
         String[] var13 = new String[]{"_id", "originalId", "syncServerId"};
         ID_COLUMN_PROJECTION = var13;
      }

      public Message() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public static long getThreadIdFromSubject(String var0) {
         String var1 = getThreadNameFromSubject(var0);
         long var2;
         if(TextUtils.isEmpty(var1)) {
            var2 = 0L;
         } else {
            long var4 = (long)var1.hashCode() << 12;
            long var6 = (long)(var1.length() & 4095);
            var2 = var4 | var6;
         }

         return var2;
      }

      public static String getThreadNameFromSubject(String var0) {
         String var1;
         if(TextUtils.isEmpty(var0)) {
            var1 = "";
         } else {
            byte var2 = 58;

            String var5;
            try {
               int var3 = var0.lastIndexOf(var2);
               if(var3 < 0) {
                  var1 = var0.trim();
                  return var1;
               }

               int var4 = var3 + 1;
               var5 = var0.substring(var4).trim();
            } catch (Exception var7) {
               var1 = "";
               return var1;
            }

            var1 = var5;
         }

         return var1;
      }

      public static EmailContent.Message[] restoreMessageAll(Context param0) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Message[] restoreMessageAllWithThreadName(Context param0, String param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Message restoreMessageWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Message[] restoreMessageWithLimit(Context param0, String param1, String param2) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Message restoreMessageWithSevenMessageId(Context param0, int param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Message[] restoreMessageWithThreadName(Context param0, String param1, String param2, String param3) {
         // $FF: Couldn't be decompiled
      }

      public int addSaveOps(ArrayList<ContentProviderOperation> var1) {
         int var2 = var1.size();
         Builder var3 = ContentProviderOperation.newInsert(this.mBaseUri);
         if(this.mHtml != null) {
            String var4 = Snippet.fromHtmlText(this.mHtml);
            this.mSnippet = var4;
         } else if(this.mText != null) {
            String var28 = Snippet.fromPlainText(this.mText);
            this.mSnippet = var28;
         }

         ContentValues var5 = this.toContentValues();
         ContentProviderOperation var6 = var3.withValues(var5).build();
         var1.add(var6);
         ContentValues var8 = new ContentValues();
         if(this.mText != null) {
            String var9 = this.mText;
            var8.put("textContent", var9);
         }

         if(this.mHtml != null) {
            String var10 = this.mHtml;
            var8.put("htmlContent", var10);
         }

         if(this.mTextReply != null) {
            String var11 = this.mTextReply;
            var8.put("textReply", var11);
         }

         if(this.mHtmlReply != null) {
            String var12 = this.mHtmlReply;
            var8.put("htmlReply", var12);
         }

         if(this.mSourceKey != 0L) {
            Long var13 = Long.valueOf(this.mSourceKey);
            var8.put("sourceMessageKey", var13);
         }

         if(this.mIntroText != null) {
            String var14 = this.mIntroText;
            var8.put("introText", var14);
         }

         Builder var15 = ContentProviderOperation.newInsert(EmailContent.Body.CONTENT_URI);
         var15.withValues(var8);
         ContentValues var17 = new ContentValues();
         int var18 = var1.size() - 1;
         Integer var19 = Integer.valueOf(var18);
         var17.put("messageKey", var19);
         ContentProviderOperation var20 = var15.withValueBackReferences(var17).build();
         var1.add(var20);
         if(this.mAttachments != null) {
            Iterator var22 = this.mAttachments.iterator();

            while(var22.hasNext()) {
               EmailContent.Attachment var23 = (EmailContent.Attachment)var22.next();
               Builder var24 = ContentProviderOperation.newInsert(EmailContent.Attachment.CONTENT_URI);
               ContentValues var25 = var23.toContentValues();
               ContentProviderOperation var26 = var24.withValues(var25).withValueBackReference("messageKey", var18).build();
               var1.add(var26);
            }
         }

         if(this.mFollowupFlag == null) {
            EmailContent.FollowupFlag var29 = new EmailContent.FollowupFlag();
            this.mFollowupFlag = var29;
         }

         Builder var30 = ContentProviderOperation.newInsert(EmailContent.FollowupFlag.CONTENT_URI);
         ContentValues var31 = this.mFollowupFlag.toContentValues();
         ContentProviderOperation var32 = var30.withValues(var31).withValueBackReference("messageKey", var18).build();
         var1.add(var32);
         return var2;
      }

      public void addUpdateAndDeleteOps(ArrayList<ContentProviderOperation> var1, Uri var2, Context var3) {
         ContentResolver var4 = var3.getContentResolver();
         String[] var5 = new String[]{"_id"};
         StringBuilder var6 = (new StringBuilder()).append("messageKey = ");
         String var7 = Long.toString(this.mId);
         String var8 = var6.append(var7).toString();
         Uri var9 = EmailContent.Body.CONTENT_URI;
         Cursor var10 = var4.query(var9, var5, var8, (String[])null, (String)null);
         Uri var11 = null;
         long var12;
         if(var10 != null) {
            if(var10.moveToNext()) {
               var12 = (long)var10.getInt(0);
               if(var12 > 0L) {
                  Uri var14 = EmailContent.Body.CONTENT_URI;
                  var11 = ContentUris.withAppendedId(var14, var12);
               }
            }

            var10.close();
         }

         String[] var17 = new String[]{"_id"};
         StringBuilder var18 = (new StringBuilder()).append("messageKey = ");
         String var19 = Long.toString(this.mId);
         String var20 = var18.append(var19).toString();
         Uri var21 = EmailContent.Attachment.CONTENT_URI;
         Cursor var22 = var4.query(var21, var17, var20, (String[])null, (String)null);
         Uri var23 = null;
         if(var22 != null) {
            if(var22.moveToNext()) {
               var12 = (long)var22.getInt(0);
               if(var12 > 0L) {
                  Uri var24 = EmailContent.Attachment.CONTENT_URI;
                  var23 = ContentUris.withAppendedId(var24, var12);
               }
            }

            var22.close();
         }

         Uri var27 = this.mBaseUri;
         long var28 = this.mId;
         Builder var30 = ContentProviderOperation.newUpdate(ContentUris.withAppendedId(var27, var28));
         if(var23 != null) {
            ContentProviderOperation var31 = ContentProviderOperation.newDelete(var23).build();
            boolean var34 = var1.add(var31);
         }

         if(this.mText != null) {
            String var35 = Snippet.fromPlainText(this.mText);
            this.mSnippet = var35;
         } else if(this.mHtml != null) {
            String var64 = Snippet.fromHtmlText(this.mHtml);
            this.mSnippet = var64;
         }

         ContentValues var36 = this.toContentValues();
         ContentProviderOperation var39 = var30.withValues(var36).build();
         boolean var42 = var1.add(var39);
         ContentValues var43 = new ContentValues();
         if(this.mText != null) {
            String var44 = this.mText;
            var43.put("textContent", var44);
         }

         if(this.mHtml != null) {
            String var45 = this.mHtml;
            var43.put("htmlContent", var45);
         }

         if(this.mTextReply != null) {
            String var46 = this.mTextReply;
            var43.put("textReply", var46);
         }

         if(this.mHtmlReply != null) {
            String var47 = this.mHtmlReply;
            var43.put("htmlReply", var47);
         }

         if(this.mSourceKey != 0L) {
            Long var48 = Long.valueOf(this.mSourceKey);
            var43.put("sourceMessageKey", var48);
         }

         if(this.mIntroText != null) {
            String var49 = this.mIntroText;
            var43.put("introText", var49);
         }

         Builder var50;
         if(var11 != null) {
            var50 = ContentProviderOperation.newUpdate(var11);
         } else {
            var50 = ContentProviderOperation.newInsert(EmailContent.Body.CONTENT_URI);
         }

         ContentProviderOperation var53 = var50.withValues(var43).build();
         boolean var56 = var1.add(var53);
         if(this.mAttachments != null) {
            boolean var63;
            ContentProviderOperation var60;
            for(Iterator var57 = this.mAttachments.iterator(); var57.hasNext(); var63 = var1.add(var60)) {
               ContentValues var58 = ((EmailContent.Attachment)var57.next()).toContentValues();
               Long var59 = Long.valueOf(this.mId);
               var58.put("messageKey", var59);
               var60 = ContentProviderOperation.newInsert(EmailContent.Attachment.CONTENT_URI).withValues(var58).build();
            }

         }
      }

      public EmailContent.FollowupFlag getFollowupFlag(Context var1) {
         long var2 = this.mId;
         return EmailContent.FollowupFlag.restoreFollowupFlagWithEmailId(var1, var2);
      }

      public int getSmimeFlags() {
         byte var1;
         if(this.mSigned) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         int var2 = var1 << 0;
         byte var3;
         if(this.mEncrypted) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         int var4 = var3 << 1;
         int var5 = var2 | var4;
         byte var6;
         if(this.mProcessed) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         int var7 = var6 << 2;
         int var8 = var5 | var7;
         byte var9;
         if(this.mVerified) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         int var10 = var9 << 3;
         return var8 | var10;
      }

      public EmailContent.Message restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         String var5 = var1.getString(1);
         this.mDisplayName = var5;
         long var6 = var1.getLong(2);
         this.mTimeStamp = var6;
         String var8 = var1.getString(3);
         this.mSubject = var8;
         byte var9;
         if(var1.getInt(4) == 1) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         this.mFlagRead = (boolean)var9;
         int var10 = var1.getInt(5);
         this.mFlagLoaded = var10;
         byte var11;
         if(var1.getInt(6) == 1) {
            var11 = 1;
         } else {
            var11 = 0;
         }

         this.mFlagFavorite = (boolean)var11;
         byte var12;
         if(var1.getInt(7) == 1) {
            var12 = 1;
         } else {
            var12 = 0;
         }

         this.mFlagAttachment = (boolean)var12;
         int var13 = var1.getInt(8);
         this.mFlags = var13;
         String var14 = var1.getString(9);
         this.mServerId = var14;
         long var15 = var1.getLong(19);
         this.mServerTimeStamp = var15;
         String var17 = var1.getString(10);
         this.mClientId = var17;
         String var18 = var1.getString(11);
         this.mMessageId = var18;
         long var19 = var1.getLong(12);
         this.mMailboxKey = var19;
         long var21 = var1.getLong(13);
         this.mAccountKey = var21;
         String var23 = var1.getString(14);
         this.mFrom = var23;
         String var24 = var1.getString(15);
         this.mTo = var24;
         String var25 = var1.getString(16);
         this.mCc = var25;
         String var26 = var1.getString(17);
         this.mBcc = var26;
         String var27 = var1.getString(18);
         this.mReplyTo = var27;
         String var28 = var1.getString(20);
         this.mMeetingInfo = var28;
         long var29 = var1.getLong(21);
         this.mThreadId = var29;
         String var31 = var1.getString(22);
         this.mThreadName = var31;
         int var32 = var1.getInt(23);
         this.mImportance = var32;
         int var33 = var1.getInt(24);
         this.mFlagTruncated = var33;
         int var34 = var1.getInt(25);
         this.mFlagMoved = var34;
         int var35 = var1.getInt(26);
         this.mDstMailBoxKey = var35;
         int var36 = var1.getInt(27);
         this.mFlagStatus = var36;
         int var37 = var1.getInt(28);
         this.mIsMimeLoaded = var37;
         int var38 = var1.getInt(29);
         this.setSmimeFlags(var38);
         Integer var39 = Integer.valueOf(var1.getInt(30));
         this.mEncryptionAlgorithm = var39;
         String var40 = var1.getString(31);
         this.mConversationId = var40;
         byte[] var41 = var1.getBlob(32);
         this.mConversationIndex = var41;
         byte var42;
         if(var1.getInt(33) == 1) {
            var42 = 1;
         } else {
            var42 = 0;
         }

         this.mFFlag = (boolean)var42;
         int var43 = var1.getInt(36);
         this.mLastVerb = var43;
         long var44 = var1.getLong(37);
         this.mLastVerbTime = var44;
         int var46 = var1.getInt(38);
         this.mMessageType = var46;
         int var47 = var1.getInt(39);
         this.mMessageDirty = var47;
         byte var48;
         if(var1.getInt(40) == 1) {
            var48 = 1;
         } else {
            var48 = 0;
         }

         this.mFlagReply = (boolean)var48;
         String var49 = var1.getString(42);
         this.mSnippet = var49;
         if(var1.getColumnCount() > 48) {
            int var50 = var1.getInt(48);
            this.mTypeMsg = var50;
            long var51 = (long)var1.getInt(49);
            this.mSevenMessageKey = var51;
            int var53 = var1.getInt(50);
            this.mMissingBody = var53;
            int var54 = var1.getInt(51);
            this.mMissingHtmlBody = var54;
            int var55 = var1.getInt(52);
            this.mUnkEncoding = var55;
            long var56 = (long)var1.getInt(53);
            this.mSevenAccountKey = var56;
         }

         long var58 = this.mId;
         this.mMessageKey = var58;
         String var60 = var1.getString(43);
         this.mIRMTemplateId = var60;
         String var61 = var1.getString(44);
         this.mIRMContentExpiryDate = var61;
         String var62 = var1.getString(45);
         this.mIRMContentOwner = var62;
         int var63 = var1.getInt(46);
         this.mIRMLicenseFlag = var63;
         int var64 = var1.getInt(47);
         this.mIRMOwner = var64;
         return this;
      }

      public Uri save(Context param1) {
         // $FF: Couldn't be decompiled
      }

      public Boolean setFollowupFlag(Context var1, EmailContent.FollowupFlag var2) {
         Boolean var3 = Boolean.valueOf((boolean)0);
         Boolean var4 = var2.storeFollowupFlag(var1);
         if(var4.booleanValue()) {
            int[] var5 = EmailContent.1.$SwitchMap$com$android$email$provider$EmailContent$FollowupFlag$FollowupFlagStatus;
            int var6 = var2.Status.ordinal();
            switch(var5[var6]) {
            case 1:
            case 2:
               this.mFFlag = (boolean)1;
               break;
            default:
               this.mFFlag = (boolean)0;
            }

            if(this.isSaved()) {
               if(this.mServerId != null) {
                  Uri var7 = SYNCED_CONTENT_URI;
                  long var8 = this.mId;
                  ContentValues var10 = this.toContentValues();
                  if(update(var1, var7, var8, var10) == 0) {
                     var4 = Boolean.valueOf((boolean)0);
                  }
               } else {
                  Uri var11 = UPDATED_CONTENT_URI;
                  long var12 = this.mId;
                  ContentValues var14 = this.toContentValues();
                  if(update(var1, var11, var12, var14) == 0) {
                     var4 = Boolean.valueOf((boolean)0);
                  }
               }
            } else if(this.save(var1) == null) {
               var4 = Boolean.valueOf((boolean)0);
            }
         }

         return var4;
      }

      public void setSmimeFlags(int var1) {
         byte var2;
         if((var1 & 1) != 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         this.mSigned = (boolean)var2;
         byte var3;
         if((var1 & 2) != 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         this.mEncrypted = (boolean)var3;
         byte var4;
         if((var1 & 4) != 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         this.mProcessed = (boolean)var4;
         byte var5;
         if((var1 & 8) != 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         this.mVerified = (boolean)var5;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.mDisplayName;
         var1.put("displayName", var2);
         Long var3 = Long.valueOf(this.mTimeStamp);
         var1.put("timeStamp", var3);
         String var4 = this.mSubject;
         var1.put("subject", var4);
         Boolean var5 = Boolean.valueOf(this.mFlagRead);
         var1.put("flagRead", var5);
         Integer var6 = Integer.valueOf(this.mFlagLoaded);
         var1.put("flagLoaded", var6);
         Boolean var7 = Boolean.valueOf(this.mFlagFavorite);
         var1.put("flagFavorite", var7);
         Boolean var8 = Boolean.valueOf(this.mFlagAttachment);
         var1.put("flagAttachment", var8);
         Boolean var9 = Boolean.valueOf(this.mFlagReply);
         var1.put("flagReply", var9);
         Long var10 = Long.valueOf(this.mOriginalId);
         var1.put("originalId", var10);
         Integer var11 = Integer.valueOf(this.mFlags);
         var1.put("flags", var11);
         String var12 = this.mServerId;
         var1.put("syncServerId", var12);
         Long var13 = Long.valueOf(this.mServerTimeStamp);
         var1.put("syncServerTimeStamp", var13);
         String var14 = this.mClientId;
         var1.put("clientId", var14);
         String var15 = this.mMessageId;
         var1.put("messageId", var15);
         Long var16 = Long.valueOf(this.mMailboxKey);
         var1.put("mailboxKey", var16);
         Long var17 = Long.valueOf(this.mAccountKey);
         var1.put("accountKey", var17);
         String var18 = this.mFrom;
         var1.put("fromList", var18);
         String var19 = this.mTo;
         var1.put("toList", var19);
         String var20 = this.mCc;
         var1.put("ccList", var20);
         String var21 = this.mBcc;
         var1.put("bccList", var21);
         String var22 = this.mReplyTo;
         var1.put("replyToList", var22);
         String var23 = this.mMeetingInfo;
         var1.put("meetingInfo", var23);
         String var24 = this.mUmCallerId;
         var1.put("umCallerId", var24);
         String var25 = this.mUmUserNotes;
         var1.put("umUserNotes", var25);
         if(this.mThreadId == 0L) {
            long var26 = getThreadIdFromSubject(this.mSubject);
            this.mThreadId = var26;
         }

         Long var28 = Long.valueOf(this.mThreadId);
         var1.put("threadId", var28);
         Integer var29 = Integer.valueOf(this.mImportance);
         var1.put("importance", var29);
         Integer var30 = Integer.valueOf(this.mFlagTruncated);
         var1.put("istruncated", var30);
         Integer var31 = Integer.valueOf(this.mFlagMoved);
         var1.put("flagMoved", var31);
         Integer var32 = Integer.valueOf(this.mDstMailBoxKey);
         var1.put("dstMailboxKey", var32);
         Integer var33 = Integer.valueOf(this.mFlagStatus);
         var1.put("flagStatus", var33);
         Integer var34 = Integer.valueOf(this.mIsMimeLoaded);
         var1.put("isMimeLoaded", var34);
         String var35 = this.mConversationId;
         var1.put("conversationId", var35);
         byte[] var36 = this.mConversationIndex;
         var1.put("conversationIndex", var36);
         Boolean var37 = Boolean.valueOf(this.mFFlag);
         var1.put("followupflag", var37);
         Integer var38 = Integer.valueOf(this.getSmimeFlags());
         var1.put("smimeFlags", var38);
         Integer var39 = this.mEncryptionAlgorithm;
         var1.put("encryptionAlgorithm", var39);
         Integer var40 = Integer.valueOf(this.mLastVerb);
         var1.put("lastVerb", var40);
         Long var41 = Long.valueOf(this.mLastVerbTime);
         var1.put("lastVerbTime", var41);
         Integer var42 = Integer.valueOf(this.mMessageType);
         var1.put("messageType", var42);
         Integer var43 = Integer.valueOf(this.mMessageDirty);
         var1.put("messageDirty", var43);
         String var44 = this.mSnippet;
         var1.put("snippet", var44);
         String var45 = this.mIRMTemplateId;
         var1.put("IRMTemplateId", var45);
         String var46 = this.mIRMContentOwner;
         var1.put("IRMContentOwner", var46);
         Integer var47 = Integer.valueOf(this.mIRMLicenseFlag);
         var1.put("IRMLicenseFlag", var47);
         Integer var48 = Integer.valueOf(this.mIRMOwner);
         var1.put("IRMOwner", var48);
         String var49 = this.mIRMContentExpiryDate;
         var1.put("IRMContentExpiryDate", var49);
         return var1;
      }

      public boolean update() {
         return false;
      }
   }

   public static final class Attachment extends EmailContent implements EmailContent.AttachmentColumns {

      public static final int CONTENT_CONTENT_BYTES_COLUMN = 11;
      public static final int CONTENT_CONTENT_COLUMN = 9;
      public static final int CONTENT_CONTENT_ID_COLUMN = 4;
      public static final int CONTENT_CONTENT_URI_COLUMN = 5;
      public static final int CONTENT_ENCODING_COLUMN = 8;
      public static final int CONTENT_FILENAME_COLUMN = 1;
      public static final int CONTENT_FLAGS_COLUMN = 10;
      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_ISINLINE = 14;
      public static final int CONTENT_LOCATION_COLUMN = 7;
      public static final int CONTENT_MESSAGE_ID_COLUMN = 6;
      public static final int CONTENT_MIME_TYPE_COLUMN = 2;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_SIZE_COLUMN = 3;
      public static final Uri CONTENT_URI;
      public static final int CONTENT_VOICEMAIL_ATT_DURATION = 13;
      public static final int CONTENT_VOICEMAIL_ATT_ORDER = 12;
      public static final Creator<EmailContent.Attachment> CREATOR;
      public static final int FLAG_CACHED_ATTACHMENT = 8;
      public static final int FLAG_FORWARD_ORIGINAL_ATTACHMENT = 2;
      public static final int FLAG_ICS_ALTERNATIVE_PART = 1;
      public static final int FLAG_RESIZED_ATTACHMENT = 4;
      public static final int IS_INLINE_ATTACHMENT = 1;
      public static final int IS_NORMAL_ATTACHMENT = 0;
      public static final Uri MESSAGE_ID_URI;
      public static final String TABLE_NAME = "Attachment";
      public String mContent;
      public byte[] mContentBytes;
      public String mContentId;
      public String mContentUri;
      public String mEncoding;
      public String mFileName;
      public int mFlags;
      public int mIsInline;
      public String mLocation;
      public long mMessageKey;
      public String mMimeType;
      public long mSize;
      public int mVoiceMailAttDuration;
      public int mVoiceMailAttOrder;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/attachment").toString());
         StringBuilder var2 = new StringBuilder();
         Uri var3 = EmailContent.CONTENT_URI;
         MESSAGE_ID_URI = Uri.parse(var2.append(var3).append("/attachment/message").toString());
         String[] var4 = new String[]{"_id", "fileName", "mimeType", "size", "contentId", "contentUri", "messageKey", "location", "encoding", "content", "flags", "content_bytes", "vmAttOrder", "vmAttDuration", "isInline"};
         CONTENT_PROJECTION = var4;
         CREATOR = new EmailContent.Attachment.1();
      }

      public Attachment() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public Attachment(Parcel var1) {
         super((EmailContent.1)null);
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.readLong();
         this.mId = var3;
         String var5 = var1.readString();
         this.mFileName = var5;
         String var6 = var1.readString();
         this.mMimeType = var6;
         long var7 = var1.readLong();
         this.mSize = var7;
         String var9 = var1.readString();
         this.mContentId = var9;
         String var10 = var1.readString();
         this.mContentUri = var10;
         long var11 = var1.readLong();
         this.mMessageKey = var11;
         String var13 = var1.readString();
         this.mLocation = var13;
         String var14 = var1.readString();
         this.mEncoding = var14;
         String var15 = var1.readString();
         this.mContent = var15;
         int var16 = var1.readInt();
         this.mFlags = var16;
         int var17 = var1.readInt();
         if(var17 == -1) {
            this.mContentBytes = null;
         } else {
            byte[] var21 = new byte[var17];
            this.mContentBytes = var21;
            byte[] var22 = this.mContentBytes;
            var1.readByteArray(var22);
         }

         int var18 = var1.readInt();
         this.mVoiceMailAttOrder = var18;
         int var19 = var1.readInt();
         this.mVoiceMailAttDuration = var19;
         int var20 = var1.readInt();
         this.mIsInline = var20;
      }

      public static File createUniqueFile(String var0) {
         File var6;
         if(Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder var1 = new StringBuilder();
            String var2 = Environment.getExternalStorageDirectory().toString();
            String var3 = var1.append(var2).append("/download/").toString();
            File var4 = new File(var3);
            File var5 = new File(var4, var0);
            if(!var5.exists()) {
               var6 = var5;
            } else {
               int var7 = var0.lastIndexOf(46);
               String var8 = var0;
               String var9 = "";
               if(var7 != -1) {
                  var8 = var0.substring(0, var7);
                  var9 = var0.substring(var7);
               }

               StringBuffer var10 = new StringBuffer();
               int var11 = 2;

               while(true) {
                  if(var11 >= Integer.MAX_VALUE) {
                     var6 = null;
                     break;
                  }

                  String var12 = var10.append(var8).append('-').append(var11).append(var9).toString();
                  var5 = new File(var4, var12);
                  if(!var5.exists()) {
                     var6 = var5;
                     break;
                  }

                  ++var11;
               }
            }
         } else {
            var6 = null;
         }

         return var6;
      }

      public static EmailContent.Attachment restoreAttachmentWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Attachment[] restoreAttachmentsWithMessageId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public static EmailContent.Attachment[] restoreAttachmentsWithMsgIdAndContentId(Context param0, long param1, String param3) {
         // $FF: Couldn't be decompiled
      }

      public int describeContents() {
         return 0;
      }

      public EmailContent.Attachment restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         String var5 = var1.getString(1);
         this.mFileName = var5;
         StringBuilder var6 = (new StringBuilder()).append("restore || name : ");
         String var7 = this.mFileName;
         String var8 = var6.append(var7).toString();
         Email.loge("EmailContent >>", var8);
         if(this.mFileName == null) {
            Email.loge("EmailContent >>", "set default name +++++++++++++++++++++++++++++++++++++++++++++++++");
            this.mFileName = "Unknown";
         }

         String var9 = var1.getString(2);
         this.mMimeType = var9;
         long var10 = var1.getLong(3);
         this.mSize = var10;
         String var12 = var1.getString(4);
         this.mContentId = var12;
         String var13 = var1.getString(5);
         this.mContentUri = var13;
         long var14 = var1.getLong(6);
         this.mMessageKey = var14;
         String var16 = var1.getString(7);
         this.mLocation = var16;
         String var17 = var1.getString(8);
         this.mEncoding = var17;
         String var18 = var1.getString(9);
         this.mContent = var18;
         int var19 = var1.getInt(10);
         this.mFlags = var19;
         byte[] var20 = var1.getBlob(11);
         this.mContentBytes = var20;
         int var21 = var1.getInt(12);
         this.mVoiceMailAttOrder = var21;
         int var22 = var1.getInt(13);
         this.mVoiceMailAttDuration = var22;
         int var23 = var1.getInt(14);
         this.mIsInline = var23;
         return this;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         String var2 = this.mFileName;
         var1.put("fileName", var2);
         String var3 = this.mMimeType;
         var1.put("mimeType", var3);
         Long var4 = Long.valueOf(this.mSize);
         var1.put("size", var4);
         String var5 = this.mContentId;
         var1.put("contentId", var5);
         String var6 = this.mContentUri;
         var1.put("contentUri", var6);
         Long var7 = Long.valueOf(this.mMessageKey);
         var1.put("messageKey", var7);
         String var8 = this.mLocation;
         var1.put("location", var8);
         String var9 = this.mEncoding;
         var1.put("encoding", var9);
         String var10 = this.mContent;
         var1.put("content", var10);
         Integer var11 = Integer.valueOf(this.mFlags);
         var1.put("flags", var11);
         byte[] var12 = this.mContentBytes;
         var1.put("content_bytes", var12);
         Integer var13 = Integer.valueOf(this.mVoiceMailAttOrder);
         var1.put("vmAttOrder", var13);
         Integer var14 = Integer.valueOf(this.mVoiceMailAttDuration);
         var1.put("vmAttDuration", var14);
         Integer var15 = Integer.valueOf(this.mIsInline);
         var1.put("isInline", var15);
         return var1;
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("[");
         String var2 = this.mFileName;
         StringBuilder var3 = var1.append(var2).append(", ");
         String var4 = this.mMimeType;
         StringBuilder var5 = var3.append(var4).append(", ");
         long var6 = this.mSize;
         StringBuilder var8 = var5.append(var6).append(", ");
         String var9 = this.mContentId;
         StringBuilder var10 = var8.append(var9).append(", ");
         String var11 = this.mContentUri;
         StringBuilder var12 = var10.append(var11).append(", ");
         long var13 = this.mMessageKey;
         StringBuilder var15 = var12.append(var13).append(", ");
         String var16 = this.mLocation;
         StringBuilder var17 = var15.append(var16).append(", ");
         String var18 = this.mEncoding;
         StringBuilder var19 = var17.append(var18).append(", ");
         int var20 = this.mFlags;
         StringBuilder var21 = var19.append(var20).append(", ");
         byte[] var22 = this.mContentBytes;
         StringBuilder var23 = var21.append(var22).append(", ");
         int var24 = this.mIsInline;
         return var23.append(var24).append("]").toString();
      }

      public void writeToParcel(Parcel var1, int var2) {
         long var3 = this.mId;
         var1.writeLong(var3);
         String var5 = this.mFileName;
         var1.writeString(var5);
         String var6 = this.mMimeType;
         var1.writeString(var6);
         long var7 = this.mSize;
         var1.writeLong(var7);
         String var9 = this.mContentId;
         var1.writeString(var9);
         String var10 = this.mContentUri;
         var1.writeString(var10);
         long var11 = this.mMessageKey;
         var1.writeLong(var11);
         String var13 = this.mLocation;
         var1.writeString(var13);
         String var14 = this.mEncoding;
         var1.writeString(var14);
         String var15 = this.mContent;
         var1.writeString(var15);
         int var16 = this.mFlags;
         var1.writeInt(var16);
         if(this.mContentBytes == null) {
            var1.writeInt(-1);
         } else {
            int var20 = this.mContentBytes.length;
            var1.writeInt(var20);
            byte[] var21 = this.mContentBytes;
            var1.writeByteArray(var21);
         }

         int var17 = this.mVoiceMailAttOrder;
         var1.writeInt(var17);
         int var18 = this.mVoiceMailAttDuration;
         var1.writeInt(var18);
         int var19 = this.mIsInline;
         var1.writeInt(var19);
      }

      static class 1 implements Creator<EmailContent.Attachment> {

         1() {}

         public EmailContent.Attachment createFromParcel(Parcel var1) {
            return new EmailContent.Attachment(var1);
         }

         public EmailContent.Attachment[] newArray(int var1) {
            return new EmailContent.Attachment[var1];
         }
      }
   }

   public interface BodyColumns {

      String HTML_CONTENT = "htmlContent";
      String HTML_REPLY = "htmlReply";
      String ID = "_id";
      String INTRO_TEXT = "introText";
      String MESSAGE_KEY = "messageKey";
      String SOURCE_MESSAGE_KEY = "sourceMessageKey";
      String TEXT_CONTENT = "textContent";
      String TEXT_REPLY = "textReply";

   }

   public static final class IRMTemplate {

      public int mIRMAccountKey;
      public String mIRMTemplateDescription;
      public String mIRMTemplateId;
      public String mIRMTemplateName;


      public IRMTemplate() {}
   }

   public interface SyncColumns {

      String ID = "_id";
      String SERVER_ID = "syncServerId";
      String SERVER_TIMESTAMP = "syncServerTimeStamp";

   }

   public static final class MessageCB extends EmailContent implements EmailContent.SyncColumns, EmailContent.MessageCBColumns {

      public static final int CONTENT_ID_COLUMN = 0;
      public static final int CONTENT_MESSAGE_KEY_COLUMN = 1;
      public static final int CONTENT_MISSING_BODY_COLUMN = 4;
      public static final int CONTENT_MISSING_HTML_BODY_COLUMN = 5;
      public static final String[] CONTENT_PROJECTION;
      public static final int CONTENT_SEVEN_ACCOUNT_KEY_COLUMN = 7;
      public static final int CONTENT_SEVEN_MESSAGE_KEY_COLUMN = 3;
      public static final int CONTENT_TYPE_MESSAGE_COLUMN = 2;
      public static final int CONTENT_UNK_ENCODING_COLUMN = 6;
      public static final Uri CONTENT_URI;
      public static final String TABLE_NAME = "Message_CB";
      public long mMessageKey;
      public int mMissingBody;
      public int mMissingHtmlBody;
      public int mSevenAccountKey;
      public long mSevenMessageKey;
      public int mTypeMsg;
      public int mUnkEncoding;


      static {
         StringBuilder var0 = new StringBuilder();
         Uri var1 = EmailContent.CONTENT_URI;
         CONTENT_URI = Uri.parse(var0.append(var1).append("/messagecb").toString());
         String[] var2 = new String[]{"_id", "messageKey", "typeMsg", "sevenMessageKey", "missingBody", "missingHtmlBody", "unkEncoding", "sevenAccountKey"};
         CONTENT_PROJECTION = var2;
      }

      public MessageCB() {
         super((EmailContent.1)null);
         Uri var1 = CONTENT_URI;
         this.mBaseUri = var1;
      }

      public static EmailContent.MessageCB restoreMessageCBWithId(Context param0, long param1) {
         // $FF: Couldn't be decompiled
      }

      public EmailContent.MessageCB restore(Cursor var1) {
         Uri var2 = CONTENT_URI;
         this.mBaseUri = var2;
         long var3 = var1.getLong(0);
         this.mId = var3;
         long var5 = var1.getLong(1);
         this.mMessageKey = var5;
         int var7 = var1.getInt(2);
         this.mTypeMsg = var7;
         long var8 = var1.getLong(3);
         this.mSevenMessageKey = var8;
         int var10 = var1.getInt(4);
         this.mMissingBody = var10;
         int var11 = var1.getInt(5);
         this.mMissingHtmlBody = var11;
         int var12 = var1.getInt(6);
         this.mUnkEncoding = var12;
         int var13 = var1.getInt(7);
         this.mSevenAccountKey = var13;
         return this;
      }

      public ContentValues toContentValues() {
         ContentValues var1 = new ContentValues();
         Long var2 = Long.valueOf(this.mMessageKey);
         var1.put("messageKey", var2);
         Integer var3 = Integer.valueOf(this.mTypeMsg);
         var1.put("typeMsg", var3);
         Long var4 = Long.valueOf(this.mSevenMessageKey);
         var1.put("sevenMessageKey", var4);
         Integer var5 = Integer.valueOf(this.mMissingBody);
         var1.put("missingBody", var5);
         Integer var6 = Integer.valueOf(this.mMissingHtmlBody);
         var1.put("missingHtmlBody", var6);
         Integer var7 = Integer.valueOf(this.mUnkEncoding);
         var1.put("unkEncoding", var7);
         Integer var8 = Integer.valueOf(this.mSevenAccountKey);
         var1.put("sevenAccountKey", var8);
         return var1;
      }

      public void update(EmailContent.MessageCB var1, Context var2) {}
   }
}
