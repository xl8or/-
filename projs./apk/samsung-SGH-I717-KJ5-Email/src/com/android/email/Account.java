package com.android.email;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import com.android.email.Preferences;
import com.android.email.Utility;
import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class Account {

   public static final int BACKUP_FLAGS_IS_BACKUP = 1;
   public static final int BACKUP_FLAGS_IS_DEFAULT = 4;
   public static final int BACKUP_FLAGS_SYNC_CALENDAR = 8;
   public static final int BACKUP_FLAGS_SYNC_CONTACTS = 2;
   public static final int BACKUP_FLAGS_SYNC_NOTES = 10;
   public static final int BACKUP_FLAGS_SYNC_TASK = 9;
   public static final int CHECK_INTERVAL_1HOUR = 60;
   public static final int CHECK_INTERVAL_NEVER = 255;
   public static final int CHECK_INTERVAL_PUSH = 254;
   public static final int DELETE_POLICY_7DAYS = 1;
   public static final int DELETE_POLICY_NEVER = 0;
   public static final int DELETE_POLICY_ON_DELETE = 2;
   private static final String KEY_BACKUP_FLAGS = ".backupFlags";
   private static final String KEY_PROTOCOL_VERSION = ".protocolVersion";
   private static final String KEY_SECURITY_AUTH = ".securityAuth";
   private static final String KEY_SECURITY_FLAGS = ".securityFlags";
   private static final String KEY_SEND_SECURITY_FLAGS = ".sendSecurityFlags";
   private static final String KEY_SIGNATURE = ".signature";
   private static final String KEY_SYNC_WINDOW = ".syncWindow";
   private static final String KEY_VIBRATE_WHEN_SILENT = ".vibrateWhenSilent";
   private static final String STR_ACCOUNT_UUID = "accountUuids";
   public static final int SYNC_WINDOW_1_DAY = 1;
   public static final int SYNC_WINDOW_1_MONTH = 5;
   public static final int SYNC_WINDOW_1_WEEK = 3;
   public static final int SYNC_WINDOW_2_WEEKS = 4;
   public static final int SYNC_WINDOW_3_DAYS = 2;
   public static final int SYNC_WINDOW_ALL = 6;
   public static final int SYNC_WINDOW_USER = 255;
   long mAccountKey;
   int mAccountNumber;
   int mAutomaticCheckIntervalMinutes;
   int mBackupFlags;
   int mDeletePolicy;
   String mDescription;
   String mDraftsFolderName;
   String mEmail;
   int mFlagSyncCalendar;
   int mFlagSyncContact;
   long mLastAutomaticCheckTime;
   String mLocalStoreUri;
   String mName;
   boolean mNotifyNewMail;
   String mOutboxFolderName;
   String mPasswd;
   String mProtocolVersion;
   String mRingtoneUri;
   String mSecurityAuth;
   int mSecurityFlags;
   String mSendAddr;
   int mSendPort;
   int mSendSecurityFlags;
   String mSenderUri;
   String mSentFolderName;
   long mSevenAccountKey;
   String mSignature;
   long mSizeLimit;
   String mStoreUri;
   int mSyncWindow;
   long mTimeLimit;
   String mTrashFolderName;
   int mTypeMsg;
   String mUuid;
   boolean mVibrate;
   boolean mVibrateWhenSilent;


   public Account(Context var1) {
      String var2 = UUID.randomUUID().toString();
      this.mUuid = var2;
      StringBuilder var3 = (new StringBuilder()).append("local://localhost/");
      StringBuilder var4 = new StringBuilder();
      String var5 = this.mUuid;
      String var6 = var4.append(var5).append(".db").toString();
      File var7 = var1.getDatabasePath(var6);
      String var8 = var3.append(var7).toString();
      this.mLocalStoreUri = var8;
      this.mAutomaticCheckIntervalMinutes = -1;
      this.mAccountNumber = -1;
      this.mNotifyNewMail = (boolean)1;
      this.mVibrate = (boolean)0;
      this.mVibrateWhenSilent = (boolean)0;
      this.mRingtoneUri = "content://media/internal/audio/media/28";
      this.mSyncWindow = -1;
      this.mBackupFlags = 0;
      this.mProtocolVersion = null;
      this.mSecurityFlags = 0;
      this.mSendSecurityFlags = 0;
      this.mSecurityAuth = null;
      this.mSignature = null;
   }

   Account(Preferences var1, String var2) {
      this.mUuid = var2;
      this.refresh(var1);
   }

   private void refresh(Preferences var1) {
      SharedPreferences var2 = var1.mSharedPreferences;
      StringBuilder var3 = new StringBuilder();
      String var4 = this.mUuid;
      String var5 = var3.append(var4).append(".storeUri").toString();
      String var6 = Utility.base64Decode(var2.getString(var5, (String)null));
      this.mStoreUri = var6;
      SharedPreferences var7 = var1.mSharedPreferences;
      StringBuilder var8 = new StringBuilder();
      String var9 = this.mUuid;
      String var10 = var8.append(var9).append(".localStoreUri").toString();
      String var11 = var7.getString(var10, (String)null);
      this.mLocalStoreUri = var11;
      SharedPreferences var12 = var1.mSharedPreferences;
      StringBuilder var13 = new StringBuilder();
      String var14 = this.mUuid;
      String var15 = var13.append(var14).append(".senderUri").toString();
      String var16 = var12.getString(var15, (String)null);
      if(var16 == null) {
         SharedPreferences var17 = var1.mSharedPreferences;
         StringBuilder var18 = new StringBuilder();
         String var19 = this.mUuid;
         String var20 = var18.append(var19).append(".transportUri").toString();
         var16 = var17.getString(var20, (String)null);
      }

      String var21 = Utility.base64Decode(var16);
      this.mSenderUri = var21;
      SharedPreferences var22 = var1.mSharedPreferences;
      StringBuilder var23 = new StringBuilder();
      String var24 = this.mUuid;
      String var25 = var23.append(var24).append(".description").toString();
      String var26 = var22.getString(var25, (String)null);
      this.mDescription = var26;
      SharedPreferences var27 = var1.mSharedPreferences;
      StringBuilder var28 = new StringBuilder();
      String var29 = this.mUuid;
      String var30 = var28.append(var29).append(".name").toString();
      String var31 = this.mName;
      String var32 = var27.getString(var30, var31);
      this.mName = var32;
      SharedPreferences var33 = var1.mSharedPreferences;
      StringBuilder var34 = new StringBuilder();
      String var35 = this.mUuid;
      String var36 = var34.append(var35).append(".passwd").toString();
      String var37 = this.mPasswd;
      String var38 = var33.getString(var36, var37);
      this.mPasswd = var38;
      SharedPreferences var39 = var1.mSharedPreferences;
      StringBuilder var40 = new StringBuilder();
      String var41 = this.mUuid;
      String var42 = var40.append(var41).append(".sendaddr").toString();
      String var43 = this.mSendAddr;
      String var44 = var39.getString(var42, var43);
      this.mSendAddr = var44;
      SharedPreferences var45 = var1.mSharedPreferences;
      StringBuilder var46 = new StringBuilder();
      String var47 = this.mUuid;
      String var48 = var46.append(var47).append(".sendport").toString();
      int var49 = this.mSendPort;
      int var50 = var45.getInt(var48, var49);
      this.mSendPort = var50;
      SharedPreferences var51 = var1.mSharedPreferences;
      StringBuilder var52 = new StringBuilder();
      String var53 = this.mUuid;
      String var54 = var52.append(var53).append(".email").toString();
      String var55 = this.mEmail;
      String var56 = var51.getString(var54, var55);
      this.mEmail = var56;
      SharedPreferences var57 = var1.mSharedPreferences;
      StringBuilder var58 = new StringBuilder();
      String var59 = this.mUuid;
      String var60 = var58.append(var59).append(".automaticCheckIntervalMinutes").toString();
      int var61 = var57.getInt(var60, -1);
      this.mAutomaticCheckIntervalMinutes = var61;
      SharedPreferences var62 = var1.mSharedPreferences;
      StringBuilder var63 = new StringBuilder();
      String var64 = this.mUuid;
      String var65 = var63.append(var64).append(".lastAutomaticCheckTime").toString();
      long var66 = var62.getLong(var65, 0L);
      this.mLastAutomaticCheckTime = var66;
      SharedPreferences var68 = var1.mSharedPreferences;
      StringBuilder var69 = new StringBuilder();
      String var70 = this.mUuid;
      String var71 = var69.append(var70).append(".notifyNewMail").toString();
      boolean var72 = var68.getBoolean(var71, (boolean)0);
      this.mNotifyNewMail = var72;
      SharedPreferences var73 = var1.mSharedPreferences;
      StringBuilder var74 = new StringBuilder();
      String var75 = this.mUuid;
      String var76 = var74.append(var75).append(".deletePolicy").toString();
      int var77 = var73.getInt(var76, 0);
      this.mDeletePolicy = var77;
      if(this.mDeletePolicy == 0 && this.mStoreUri != null && this.mStoreUri.startsWith("imap")) {
         this.mDeletePolicy = 2;
      }

      SharedPreferences var78 = var1.mSharedPreferences;
      StringBuilder var79 = new StringBuilder();
      String var80 = this.mUuid;
      String var81 = var79.append(var80).append(".draftsFolderName").toString();
      String var82 = var78.getString(var81, "Drafts");
      this.mDraftsFolderName = var82;
      SharedPreferences var83 = var1.mSharedPreferences;
      StringBuilder var84 = new StringBuilder();
      String var85 = this.mUuid;
      String var86 = var84.append(var85).append(".sentFolderName").toString();
      String var87 = var83.getString(var86, "Sent");
      this.mSentFolderName = var87;
      SharedPreferences var88 = var1.mSharedPreferences;
      StringBuilder var89 = new StringBuilder();
      String var90 = this.mUuid;
      String var91 = var89.append(var90).append(".trashFolderName").toString();
      String var92 = var88.getString(var91, "Trash");
      this.mTrashFolderName = var92;
      SharedPreferences var93 = var1.mSharedPreferences;
      StringBuilder var94 = new StringBuilder();
      String var95 = this.mUuid;
      String var96 = var94.append(var95).append(".outboxFolderName").toString();
      String var97 = var93.getString(var96, "Outbox");
      this.mOutboxFolderName = var97;
      SharedPreferences var98 = var1.mSharedPreferences;
      StringBuilder var99 = new StringBuilder();
      String var100 = this.mUuid;
      String var101 = var99.append(var100).append(".accountNumber").toString();
      int var102 = var98.getInt(var101, 0);
      this.mAccountNumber = var102;
      SharedPreferences var103 = var1.mSharedPreferences;
      StringBuilder var104 = new StringBuilder();
      String var105 = this.mUuid;
      String var106 = var104.append(var105).append(".vibrate").toString();
      boolean var107 = var103.getBoolean(var106, (boolean)0);
      this.mVibrate = var107;
      SharedPreferences var108 = var1.mSharedPreferences;
      StringBuilder var109 = new StringBuilder();
      String var110 = this.mUuid;
      String var111 = var109.append(var110).append(".vibrateWhenSilent").toString();
      boolean var112 = var108.getBoolean(var111, (boolean)0);
      this.mVibrateWhenSilent = var112;
      SharedPreferences var113 = var1.mSharedPreferences;
      StringBuilder var114 = new StringBuilder();
      String var115 = this.mUuid;
      String var116 = var114.append(var115).append(".ringtone").toString();
      String var117 = var113.getString(var116, "content://media/internal/audio/media/28");
      this.mRingtoneUri = var117;
      SharedPreferences var118 = var1.mSharedPreferences;
      StringBuilder var119 = new StringBuilder();
      String var120 = this.mUuid;
      String var121 = var119.append(var120).append(".syncWindow").toString();
      int var122 = var118.getInt(var121, -1);
      this.mSyncWindow = var122;
      SharedPreferences var123 = var1.mSharedPreferences;
      StringBuilder var124 = new StringBuilder();
      String var125 = this.mUuid;
      String var126 = var124.append(var125).append(".backupFlags").toString();
      int var127 = var123.getInt(var126, 0);
      this.mBackupFlags = var127;
      SharedPreferences var128 = var1.mSharedPreferences;
      StringBuilder var129 = new StringBuilder();
      String var130 = this.mUuid;
      String var131 = var129.append(var130).append(".protocolVersion").toString();
      String var132 = var128.getString(var131, (String)null);
      this.mProtocolVersion = var132;
      SharedPreferences var133 = var1.mSharedPreferences;
      StringBuilder var134 = new StringBuilder();
      String var135 = this.mUuid;
      String var136 = var134.append(var135).append(".securityFlags").toString();
      int var137 = var133.getInt(var136, 0);
      this.mSecurityFlags = var137;
      SharedPreferences var138 = var1.mSharedPreferences;
      StringBuilder var139 = new StringBuilder();
      String var140 = this.mUuid;
      String var141 = var139.append(var140).append(".sendSecurityFlags").toString();
      int var142 = var138.getInt(var141, 0);
      this.mSendSecurityFlags = var142;
      SharedPreferences var143 = var1.mSharedPreferences;
      StringBuilder var144 = new StringBuilder();
      String var145 = this.mUuid;
      String var146 = var144.append(var145).append(".securityAuth").toString();
      String var147 = var143.getString(var146, (String)null);
      this.mSecurityAuth = var147;
      SharedPreferences var148 = var1.mSharedPreferences;
      StringBuilder var149 = new StringBuilder();
      String var150 = this.mUuid;
      String var151 = var149.append(var150).append(".signature").toString();
      String var152 = var148.getString(var151, (String)null);
      this.mSignature = var152;
   }

   public void delete(Preferences var1) {
      String[] var2 = var1.mSharedPreferences.getString("accountUuids", "").split(",");
      StringBuffer var3 = new StringBuffer();
      int var4 = 0;

      for(int var5 = var2.length; var4 < var5; ++var4) {
         String var6 = var2[var4];
         String var7 = this.mUuid;
         if(!var6.equals(var7)) {
            if(var3.length() > 0) {
               StringBuffer var8 = var3.append(',');
            }

            String var9 = var2[var4];
            var3.append(var9);
         }
      }

      String var11 = var3.toString();
      Editor var12 = var1.mSharedPreferences.edit();
      var12.putString("accountUuids", var11);
      StringBuilder var14 = new StringBuilder();
      String var15 = this.mUuid;
      String var16 = var14.append(var15).append(".storeUri").toString();
      var12.remove(var16);
      StringBuilder var18 = new StringBuilder();
      String var19 = this.mUuid;
      String var20 = var18.append(var19).append(".localStoreUri").toString();
      var12.remove(var20);
      StringBuilder var22 = new StringBuilder();
      String var23 = this.mUuid;
      String var24 = var22.append(var23).append(".senderUri").toString();
      var12.remove(var24);
      StringBuilder var26 = new StringBuilder();
      String var27 = this.mUuid;
      String var28 = var26.append(var27).append(".description").toString();
      var12.remove(var28);
      StringBuilder var30 = new StringBuilder();
      String var31 = this.mUuid;
      String var32 = var30.append(var31).append(".name").toString();
      var12.remove(var32);
      StringBuilder var34 = new StringBuilder();
      String var35 = this.mUuid;
      String var36 = var34.append(var35).append(".email").toString();
      var12.remove(var36);
      StringBuilder var38 = new StringBuilder();
      String var39 = this.mUuid;
      String var40 = var38.append(var39).append(".automaticCheckIntervalMinutes").toString();
      var12.remove(var40);
      StringBuilder var42 = new StringBuilder();
      String var43 = this.mUuid;
      String var44 = var42.append(var43).append(".lastAutomaticCheckTime").toString();
      var12.remove(var44);
      StringBuilder var46 = new StringBuilder();
      String var47 = this.mUuid;
      String var48 = var46.append(var47).append(".notifyNewMail").toString();
      var12.remove(var48);
      StringBuilder var50 = new StringBuilder();
      String var51 = this.mUuid;
      String var52 = var50.append(var51).append(".deletePolicy").toString();
      var12.remove(var52);
      StringBuilder var54 = new StringBuilder();
      String var55 = this.mUuid;
      String var56 = var54.append(var55).append(".draftsFolderName").toString();
      var12.remove(var56);
      StringBuilder var58 = new StringBuilder();
      String var59 = this.mUuid;
      String var60 = var58.append(var59).append(".sentFolderName").toString();
      var12.remove(var60);
      StringBuilder var62 = new StringBuilder();
      String var63 = this.mUuid;
      String var64 = var62.append(var63).append(".trashFolderName").toString();
      var12.remove(var64);
      StringBuilder var66 = new StringBuilder();
      String var67 = this.mUuid;
      String var68 = var66.append(var67).append(".outboxFolderName").toString();
      var12.remove(var68);
      StringBuilder var70 = new StringBuilder();
      String var71 = this.mUuid;
      String var72 = var70.append(var71).append(".accountNumber").toString();
      var12.remove(var72);
      StringBuilder var74 = new StringBuilder();
      String var75 = this.mUuid;
      String var76 = var74.append(var75).append(".vibrate").toString();
      var12.remove(var76);
      StringBuilder var78 = new StringBuilder();
      String var79 = this.mUuid;
      String var80 = var78.append(var79).append(".vibrateWhenSilent").toString();
      var12.remove(var80);
      StringBuilder var82 = new StringBuilder();
      String var83 = this.mUuid;
      String var84 = var82.append(var83).append(".ringtone").toString();
      var12.remove(var84);
      StringBuilder var86 = new StringBuilder();
      String var87 = this.mUuid;
      String var88 = var86.append(var87).append(".syncWindow").toString();
      var12.remove(var88);
      StringBuilder var90 = new StringBuilder();
      String var91 = this.mUuid;
      String var92 = var90.append(var91).append(".backupFlags").toString();
      var12.remove(var92);
      StringBuilder var94 = new StringBuilder();
      String var95 = this.mUuid;
      String var96 = var94.append(var95).append(".protocolVersion").toString();
      var12.remove(var96);
      StringBuilder var98 = new StringBuilder();
      String var99 = this.mUuid;
      String var100 = var98.append(var99).append(".securityFlags").toString();
      var12.remove(var100);
      StringBuilder var102 = new StringBuilder();
      String var103 = this.mUuid;
      String var104 = var102.append(var103).append(".sendSecurityFlags").toString();
      var12.remove(var104);
      StringBuilder var106 = new StringBuilder();
      String var107 = this.mUuid;
      String var108 = var106.append(var107).append(".signature").toString();
      var12.remove(var108);
      StringBuilder var110 = new StringBuilder();
      String var111 = this.mUuid;
      String var112 = var110.append(var111).append(".transportUri").toString();
      var12.remove(var112);
      boolean var114 = var12.commit();
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof Account) {
         String var2 = ((Account)var1).mUuid;
         String var3 = this.mUuid;
         var4 = var2.equals(var3);
      } else {
         var4 = super.equals(var1);
      }

      return var4;
   }

   public long getAccountKey() {
      return this.mAccountKey;
   }

   public int getAccountNumber() {
      return this.mAccountNumber;
   }

   public int getAutomaticCheckIntervalMinutes() {
      return this.mAutomaticCheckIntervalMinutes;
   }

   public int getBackupFlags() {
      return this.mBackupFlags;
   }

   public Uri getContentUri() {
      StringBuilder var1 = (new StringBuilder()).append("content://accounts/");
      String var2 = this.getUuid();
      return Uri.parse(var1.append(var2).toString());
   }

   public int getDeletePolicy() {
      return this.mDeletePolicy;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getDraftsFolderName() {
      return this.mDraftsFolderName;
   }

   public String getEmail() {
      return this.mEmail;
   }

   public int getFlagSyncCalendar() {
      return this.mFlagSyncCalendar;
   }

   public int getFlagSyncContact() {
      return this.mFlagSyncContact;
   }

   public long getLastAutomaticCheckTime() {
      return this.mLastAutomaticCheckTime;
   }

   public String getLocalStoreUri() {
      return this.mLocalStoreUri;
   }

   public String getName() {
      return this.mName;
   }

   public String getOutboxFolderName() {
      return this.mOutboxFolderName;
   }

   public String getPasswd() {
      return this.mPasswd;
   }

   public String getRingtone() {
      return this.mRingtoneUri;
   }

   public String getSecurityAuth() {
      return this.mSecurityAuth;
   }

   public int getSecurityFlags() {
      return this.mSecurityFlags;
   }

   public String getSendAddr() {
      return this.mSendAddr;
   }

   public int getSendPort() {
      return this.mSendPort;
   }

   public int getSendSecurityFlags() {
      return this.mSendSecurityFlags;
   }

   public String getSenderUri() {
      return this.mSenderUri;
   }

   public String getSentFolderName() {
      return this.mSentFolderName;
   }

   public long getSevenAccountKey() {
      return this.mSevenAccountKey;
   }

   public long getSizeLimit() {
      return this.mSizeLimit;
   }

   public String getStoreUri() {
      return this.mStoreUri;
   }

   public int getSyncWindow() {
      return this.mSyncWindow;
   }

   public long getTimeLimit() {
      return this.mTimeLimit;
   }

   public String getTrashFolderName() {
      return this.mTrashFolderName;
   }

   public int getTypeMsg() {
      return this.mTypeMsg;
   }

   public String getUuid() {
      return this.mUuid;
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean isNotifyNewMail() {
      return this.mNotifyNewMail;
   }

   public boolean isVibrate() {
      return this.mVibrate;
   }

   public boolean isVibrateWhenSilent() {
      return this.mVibrateWhenSilent;
   }

   public void save(Preferences var1) {
      SharedPreferences var2 = var1.mSharedPreferences;
      String var3 = var2.getString("accountUuids", "");
      StringBuffer var4 = new StringBuffer(var3);
      String var5 = var4.toString();
      String var6 = this.mUuid;
      if(!var5.contains(var6)) {
         Account[] var7 = var1.getAccounts();
         int[] var8 = new int[var7.length];
         int var9 = 0;

         while(true) {
            int var10 = var7.length;
            if(var9 >= var10) {
               Arrays.sort(var8);
               int[] var12 = var8;
               int var13 = var8.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  int var15 = var12[var14];
                  int var16 = this.mAccountNumber + 1;
                  if(var15 > var16) {
                     break;
                  }

                  this.mAccountNumber = var15;
               }

               int var17 = this.mAccountNumber + 1;
               this.mAccountNumber = var17;
               String var18;
               if(var4.length() != 0) {
                  var18 = ",";
               } else {
                  var18 = "";
               }

               StringBuffer var19 = var4.append(var18);
               String var20 = this.mUuid;
               var19.append(var20);
               Editor var22 = var2.edit();
               String var23 = var4.toString();
               var22.putString("accountUuids", var23);
               boolean var25 = var22.commit();
               break;
            }

            int var11 = var7[var9].getAccountNumber();
            var8[var9] = var11;
            ++var9;
         }
      }

      Editor var26 = var2.edit();
      StringBuilder var27 = new StringBuilder();
      String var28 = this.mUuid;
      String var29 = var27.append(var28).append(".storeUri").toString();
      String var30 = Utility.base64Encode(this.mStoreUri);
      var26.putString(var29, var30);
      StringBuilder var32 = new StringBuilder();
      String var33 = this.mUuid;
      String var34 = var32.append(var33).append(".localStoreUri").toString();
      String var35 = this.mLocalStoreUri;
      var26.putString(var34, var35);
      StringBuilder var37 = new StringBuilder();
      String var38 = this.mUuid;
      String var39 = var37.append(var38).append(".senderUri").toString();
      String var40 = Utility.base64Encode(this.mSenderUri);
      var26.putString(var39, var40);
      StringBuilder var42 = new StringBuilder();
      String var43 = this.mUuid;
      String var44 = var42.append(var43).append(".description").toString();
      String var45 = this.mDescription;
      var26.putString(var44, var45);
      StringBuilder var47 = new StringBuilder();
      String var48 = this.mUuid;
      String var49 = var47.append(var48).append(".name").toString();
      String var50 = this.mName;
      var26.putString(var49, var50);
      StringBuilder var52 = new StringBuilder();
      String var53 = this.mUuid;
      String var54 = var52.append(var53).append(".passwd").toString();
      String var55 = this.mPasswd;
      var26.putString(var54, var55);
      StringBuilder var57 = new StringBuilder();
      String var58 = this.mUuid;
      String var59 = var57.append(var58).append(".sendaddr").toString();
      String var60 = this.mSendAddr;
      var26.putString(var59, var60);
      StringBuilder var62 = new StringBuilder();
      String var63 = this.mUuid;
      String var64 = var62.append(var63).append(".sendport").toString();
      int var65 = this.mSendPort;
      var26.putInt(var64, var65);
      StringBuilder var67 = new StringBuilder();
      String var68 = this.mUuid;
      String var69 = var67.append(var68).append(".email").toString();
      String var70 = this.mEmail;
      var26.putString(var69, var70);
      StringBuilder var72 = new StringBuilder();
      String var73 = this.mUuid;
      String var74 = var72.append(var73).append(".automaticCheckIntervalMinutes").toString();
      int var75 = this.mAutomaticCheckIntervalMinutes;
      var26.putInt(var74, var75);
      StringBuilder var77 = new StringBuilder();
      String var78 = this.mUuid;
      String var79 = var77.append(var78).append(".lastAutomaticCheckTime").toString();
      long var80 = this.mLastAutomaticCheckTime;
      var26.putLong(var79, var80);
      StringBuilder var83 = new StringBuilder();
      String var84 = this.mUuid;
      String var85 = var83.append(var84).append(".notifyNewMail").toString();
      boolean var86 = this.mNotifyNewMail;
      var26.putBoolean(var85, var86);
      StringBuilder var88 = new StringBuilder();
      String var89 = this.mUuid;
      String var90 = var88.append(var89).append(".deletePolicy").toString();
      int var91 = this.mDeletePolicy;
      var26.putInt(var90, var91);
      StringBuilder var93 = new StringBuilder();
      String var94 = this.mUuid;
      String var95 = var93.append(var94).append(".draftsFolderName").toString();
      String var96 = this.mDraftsFolderName;
      var26.putString(var95, var96);
      StringBuilder var98 = new StringBuilder();
      String var99 = this.mUuid;
      String var100 = var98.append(var99).append(".sentFolderName").toString();
      String var101 = this.mSentFolderName;
      var26.putString(var100, var101);
      StringBuilder var103 = new StringBuilder();
      String var104 = this.mUuid;
      String var105 = var103.append(var104).append(".trashFolderName").toString();
      String var106 = this.mTrashFolderName;
      var26.putString(var105, var106);
      StringBuilder var108 = new StringBuilder();
      String var109 = this.mUuid;
      String var110 = var108.append(var109).append(".outboxFolderName").toString();
      String var111 = this.mOutboxFolderName;
      var26.putString(var110, var111);
      StringBuilder var113 = new StringBuilder();
      String var114 = this.mUuid;
      String var115 = var113.append(var114).append(".accountNumber").toString();
      int var116 = this.mAccountNumber;
      var26.putInt(var115, var116);
      StringBuilder var118 = new StringBuilder();
      String var119 = this.mUuid;
      String var120 = var118.append(var119).append(".vibrate").toString();
      boolean var121 = this.mVibrate;
      var26.putBoolean(var120, var121);
      StringBuilder var123 = new StringBuilder();
      String var124 = this.mUuid;
      String var125 = var123.append(var124).append(".vibrateWhenSilent").toString();
      boolean var126 = this.mVibrateWhenSilent;
      var26.putBoolean(var125, var126);
      StringBuilder var128 = new StringBuilder();
      String var129 = this.mUuid;
      String var130 = var128.append(var129).append(".ringtone").toString();
      String var131 = this.mRingtoneUri;
      var26.putString(var130, var131);
      StringBuilder var133 = new StringBuilder();
      String var134 = this.mUuid;
      String var135 = var133.append(var134).append(".syncWindow").toString();
      int var136 = this.mSyncWindow;
      var26.putInt(var135, var136);
      StringBuilder var138 = new StringBuilder();
      String var139 = this.mUuid;
      String var140 = var138.append(var139).append(".backupFlags").toString();
      int var141 = this.mBackupFlags;
      var26.putInt(var140, var141);
      StringBuilder var143 = new StringBuilder();
      String var144 = this.mUuid;
      String var145 = var143.append(var144).append(".protocolVersion").toString();
      String var146 = this.mProtocolVersion;
      var26.putString(var145, var146);
      StringBuilder var148 = new StringBuilder();
      String var149 = this.mUuid;
      String var150 = var148.append(var149).append(".securityFlags").toString();
      int var151 = this.mSecurityFlags;
      var26.putInt(var150, var151);
      StringBuilder var153 = new StringBuilder();
      String var154 = this.mUuid;
      String var155 = var153.append(var154).append(".sendSecurityFlags").toString();
      int var156 = this.mSendSecurityFlags;
      var26.putInt(var155, var156);
      StringBuilder var158 = new StringBuilder();
      String var159 = this.mUuid;
      String var160 = var158.append(var159).append(".securityAuth").toString();
      String var161 = this.mSecurityAuth;
      var26.putString(var160, var161);
      StringBuilder var163 = new StringBuilder();
      String var164 = this.mUuid;
      String var165 = var163.append(var164).append(".signature").toString();
      String var166 = this.mSignature;
      var26.putString(var165, var166);
      StringBuilder var168 = new StringBuilder();
      String var169 = this.mUuid;
      String var170 = var168.append(var169).append(".transportUri").toString();
      var26.remove(var170);
      boolean var172 = var26.commit();
   }

   public void setAccountKey(long var1) {
      this.mAccountKey = var1;
   }

   public void setAutomaticCheckIntervalMinutes(int var1) {
      this.mAutomaticCheckIntervalMinutes = var1;
   }

   public void setBackupFlags(int var1) {
      this.mBackupFlags = var1;
   }

   public void setDeletePolicy(int var1) {
      this.mDeletePolicy = var1;
   }

   public void setDescription(String var1) {
      this.mDescription = var1;
   }

   public void setDraftsFolderName(String var1) {
      this.mDraftsFolderName = var1;
   }

   public void setEmail(String var1) {
      this.mEmail = var1;
   }

   public void setFlagSyncCalendar(int var1) {
      this.mFlagSyncCalendar = var1;
   }

   public void setFlagSyncContact(int var1) {
      this.mFlagSyncContact = var1;
   }

   public void setLastAutomaticCheckTime(long var1) {
      this.mLastAutomaticCheckTime = var1;
   }

   public void setLocalStoreUri(String var1) {
      this.mLocalStoreUri = var1;
   }

   public void setName(String var1) {
      this.mName = var1;
   }

   public void setNotifyNewMail(boolean var1) {
      this.mNotifyNewMail = var1;
   }

   public void setOutboxFolderName(String var1) {
      this.mOutboxFolderName = var1;
   }

   public void setPasswd(String var1) {
      this.mPasswd = var1;
   }

   public void setRingtone(String var1) {
      this.mRingtoneUri = var1;
   }

   public void setSecurityAuth(String var1) {
      this.mSecurityAuth = var1;
   }

   public void setSecurityFlags(int var1) {
      this.mSecurityFlags = var1;
   }

   public void setSendAddr(String var1) {
      this.mSendAddr = var1;
   }

   public void setSendPort(int var1) {
      this.mSendPort = var1;
   }

   public void setSendSecurityFlags(int var1) {
      this.mSendSecurityFlags = var1;
   }

   public void setSenderUri(String var1) {
      this.mSenderUri = var1;
   }

   public void setSentFolderName(String var1) {
      this.mSentFolderName = var1;
   }

   public void setSevenAccountKey(long var1) {
      this.mSevenAccountKey = var1;
   }

   public void setSizeLimit(long var1) {
      this.mSizeLimit = var1;
   }

   public void setStoreUri(String var1) {
      this.mStoreUri = var1;
   }

   public void setSyncWindow(int var1) {
      this.mSyncWindow = var1;
   }

   public void setTimeLimit(long var1) {
      this.mTimeLimit = var1;
   }

   public void setTrashFolderName(String var1) {
      this.mTrashFolderName = var1;
   }

   public void setTypeMsg(int var1) {
      this.mTypeMsg = var1;
   }

   public void setVibrate(boolean var1) {
      this.mVibrate = var1;
   }

   public void setVibrateWhenSilent(boolean var1) {
      this.mVibrateWhenSilent = var1;
   }

   public String toString() {
      return this.mDescription;
   }
}
