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
   public static final int CHECK_INTERVAL_NEVER = 255;
   public static final int CHECK_INTERVAL_PUSH = 254;
   public static final int DELETE_POLICY_7DAYS = 1;
   public static final int DELETE_POLICY_NEVER = 0;
   public static final int DELETE_POLICY_ON_DELETE = 2;
   private static final String KEY_BACKUP_FLAGS = ".backupFlags";
   private static final String KEY_NOTIFICATION_LED = ".led";
   private static final String KEY_PROTOCOL_VERSION = ".protocolVersion";
   private static final String KEY_SECURITY_FLAGS = ".securityFlags";
   private static final String KEY_SIGNATURE = ".signature";
   private static final String KEY_SYNC_WINDOW = ".syncWindow";
   private static final String KEY_VIBRATE_WHEN_SILENT = ".vibrateWhenSilent";
   public static final int SYNC_WINDOW_1_DAY = 1;
   public static final int SYNC_WINDOW_1_MONTH = 5;
   public static final int SYNC_WINDOW_1_WEEK = 3;
   public static final int SYNC_WINDOW_2_WEEKS = 4;
   public static final int SYNC_WINDOW_3_DAYS = 2;
   public static final int SYNC_WINDOW_ALL = 6;
   public static final int SYNC_WINDOW_USER = 255;
   int mAccountNumber;
   int mAutomaticCheckIntervalMinutes;
   int mBackupFlags;
   int mDeletePolicy;
   String mDescription;
   String mDraftsFolderName;
   String mEmail;
   boolean mLED;
   long mLastAutomaticCheckTime;
   String mLocalStoreUri;
   String mName;
   boolean mNotifyNewMail;
   String mOutboxFolderName;
   private transient Preferences mPreferences;
   String mProtocolVersion;
   String mRingtoneUri;
   int mSecurityFlags;
   String mSenderUri;
   String mSentFolderName;
   String mSignature;
   String mStoreUri;
   int mSyncWindow;
   String mTrashFolderName;
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
      this.mRingtoneUri = "content://settings/system/notification_sound";
      this.mSyncWindow = -1;
      this.mBackupFlags = 0;
      this.mProtocolVersion = null;
      this.mSecurityFlags = 0;
      this.mSignature = null;
      this.mLED = (boolean)0;
   }

   Account(Preferences var1, String var2) {
      this.mUuid = var2;
      this.refresh(var1);
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
      String var104 = var102.append(var103).append(".signature").toString();
      var12.remove(var104);
      StringBuilder var106 = new StringBuilder();
      String var107 = this.mUuid;
      String var108 = var106.append(var107).append(".led").toString();
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

   public String getRingtone() {
      return this.mRingtoneUri;
   }

   public String getSenderUri() {
      return this.mSenderUri;
   }

   public String getSentFolderName() {
      return this.mSentFolderName;
   }

   public String getStoreUri() {
      return this.mStoreUri;
   }

   public int getSyncWindow() {
      return this.mSyncWindow;
   }

   public String getTrashFolderName() {
      return this.mTrashFolderName;
   }

   public String getUuid() {
      return this.mUuid;
   }

   public boolean isLEDOn() {
      return this.mLED;
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

   public void refresh(Preferences var1) {
      this.mPreferences = var1;
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
      String var36 = var34.append(var35).append(".email").toString();
      String var37 = this.mEmail;
      String var38 = var33.getString(var36, var37);
      this.mEmail = var38;
      SharedPreferences var39 = var1.mSharedPreferences;
      StringBuilder var40 = new StringBuilder();
      String var41 = this.mUuid;
      String var42 = var40.append(var41).append(".automaticCheckIntervalMinutes").toString();
      int var43 = var39.getInt(var42, -1);
      this.mAutomaticCheckIntervalMinutes = var43;
      SharedPreferences var44 = var1.mSharedPreferences;
      StringBuilder var45 = new StringBuilder();
      String var46 = this.mUuid;
      String var47 = var45.append(var46).append(".lastAutomaticCheckTime").toString();
      long var48 = var44.getLong(var47, 0L);
      this.mLastAutomaticCheckTime = var48;
      SharedPreferences var50 = var1.mSharedPreferences;
      StringBuilder var51 = new StringBuilder();
      String var52 = this.mUuid;
      String var53 = var51.append(var52).append(".notifyNewMail").toString();
      boolean var54 = var50.getBoolean(var53, (boolean)0);
      this.mNotifyNewMail = var54;
      SharedPreferences var55 = var1.mSharedPreferences;
      StringBuilder var56 = new StringBuilder();
      String var57 = this.mUuid;
      String var58 = var56.append(var57).append(".deletePolicy").toString();
      int var59 = var55.getInt(var58, 0);
      this.mDeletePolicy = var59;
      if(this.mDeletePolicy == 0 && this.mStoreUri != null && this.mStoreUri.toString().startsWith("imap")) {
         this.mDeletePolicy = 2;
      }

      SharedPreferences var60 = var1.mSharedPreferences;
      StringBuilder var61 = new StringBuilder();
      String var62 = this.mUuid;
      String var63 = var61.append(var62).append(".draftsFolderName").toString();
      String var64 = var60.getString(var63, "Drafts");
      this.mDraftsFolderName = var64;
      SharedPreferences var65 = var1.mSharedPreferences;
      StringBuilder var66 = new StringBuilder();
      String var67 = this.mUuid;
      String var68 = var66.append(var67).append(".sentFolderName").toString();
      String var69 = var65.getString(var68, "Sent");
      this.mSentFolderName = var69;
      SharedPreferences var70 = var1.mSharedPreferences;
      StringBuilder var71 = new StringBuilder();
      String var72 = this.mUuid;
      String var73 = var71.append(var72).append(".trashFolderName").toString();
      String var74 = var70.getString(var73, "Trash");
      this.mTrashFolderName = var74;
      SharedPreferences var75 = var1.mSharedPreferences;
      StringBuilder var76 = new StringBuilder();
      String var77 = this.mUuid;
      String var78 = var76.append(var77).append(".outboxFolderName").toString();
      String var79 = var75.getString(var78, "Outbox");
      this.mOutboxFolderName = var79;
      SharedPreferences var80 = var1.mSharedPreferences;
      StringBuilder var81 = new StringBuilder();
      String var82 = this.mUuid;
      String var83 = var81.append(var82).append(".accountNumber").toString();
      int var84 = var80.getInt(var83, 0);
      this.mAccountNumber = var84;
      SharedPreferences var85 = var1.mSharedPreferences;
      StringBuilder var86 = new StringBuilder();
      String var87 = this.mUuid;
      String var88 = var86.append(var87).append(".vibrate").toString();
      boolean var89 = var85.getBoolean(var88, (boolean)0);
      this.mVibrate = var89;
      SharedPreferences var90 = var1.mSharedPreferences;
      StringBuilder var91 = new StringBuilder();
      String var92 = this.mUuid;
      String var93 = var91.append(var92).append(".vibrateWhenSilent").toString();
      boolean var94 = var90.getBoolean(var93, (boolean)0);
      this.mVibrateWhenSilent = var94;
      SharedPreferences var95 = var1.mSharedPreferences;
      StringBuilder var96 = new StringBuilder();
      String var97 = this.mUuid;
      String var98 = var96.append(var97).append(".ringtone").toString();
      String var99 = var95.getString(var98, "content://settings/system/notification_sound");
      this.mRingtoneUri = var99;
      SharedPreferences var100 = var1.mSharedPreferences;
      StringBuilder var101 = new StringBuilder();
      String var102 = this.mUuid;
      String var103 = var101.append(var102).append(".syncWindow").toString();
      int var104 = var100.getInt(var103, -1);
      this.mSyncWindow = var104;
      SharedPreferences var105 = var1.mSharedPreferences;
      StringBuilder var106 = new StringBuilder();
      String var107 = this.mUuid;
      String var108 = var106.append(var107).append(".backupFlags").toString();
      int var109 = var105.getInt(var108, 0);
      this.mBackupFlags = var109;
      SharedPreferences var110 = var1.mSharedPreferences;
      StringBuilder var111 = new StringBuilder();
      String var112 = this.mUuid;
      String var113 = var111.append(var112).append(".protocolVersion").toString();
      String var114 = var110.getString(var113, (String)null);
      this.mProtocolVersion = var114;
      SharedPreferences var115 = var1.mSharedPreferences;
      StringBuilder var116 = new StringBuilder();
      String var117 = this.mUuid;
      String var118 = var116.append(var117).append(".securityFlags").toString();
      int var119 = var115.getInt(var118, 0);
      this.mSecurityFlags = var119;
      SharedPreferences var120 = var1.mSharedPreferences;
      StringBuilder var121 = new StringBuilder();
      String var122 = this.mUuid;
      String var123 = var121.append(var122).append(".signature").toString();
      String var124 = var120.getString(var123, (String)null);
      this.mSignature = var124;
      SharedPreferences var125 = var1.mSharedPreferences;
      StringBuilder var126 = new StringBuilder();
      String var127 = this.mUuid;
      String var128 = var126.append(var127).append(".led").toString();
      boolean var129 = var125.getBoolean(var128, (boolean)0);
      this.mLED = var129;
   }

   public void save(Preferences var1) {
      this.mPreferences = var1;
      String var2 = var1.mSharedPreferences.getString("accountUuids", "");
      String var3 = this.mUuid;
      if(!var2.contains(var3)) {
         Account[] var4 = var1.getAccounts();
         int[] var5 = new int[var4.length];
         int var6 = 0;

         while(true) {
            int var7 = var4.length;
            if(var6 >= var7) {
               Arrays.sort(var5);
               int[] var9 = var5;
               int var10 = var5.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  int var12 = var9[var11];
                  int var13 = this.mAccountNumber + 1;
                  if(var12 > var13) {
                     break;
                  }

                  this.mAccountNumber = var12;
               }

               int var14 = this.mAccountNumber + 1;
               this.mAccountNumber = var14;
               String var15 = var1.mSharedPreferences.getString("accountUuids", "");
               StringBuilder var16 = (new StringBuilder()).append(var15);
               String var17;
               if(var15.length() != 0) {
                  var17 = ",";
               } else {
                  var17 = "";
               }

               StringBuilder var18 = var16.append(var17);
               String var19 = this.mUuid;
               String var20 = var18.append(var19).toString();
               Editor var21 = var1.mSharedPreferences.edit();
               var21.putString("accountUuids", var20);
               boolean var23 = var21.commit();
               break;
            }

            int var8 = var4[var6].getAccountNumber();
            var5[var6] = var8;
            ++var6;
         }
      }

      Editor var24 = var1.mSharedPreferences.edit();
      StringBuilder var25 = new StringBuilder();
      String var26 = this.mUuid;
      String var27 = var25.append(var26).append(".storeUri").toString();
      String var28 = Utility.base64Encode(this.mStoreUri);
      var24.putString(var27, var28);
      StringBuilder var30 = new StringBuilder();
      String var31 = this.mUuid;
      String var32 = var30.append(var31).append(".localStoreUri").toString();
      String var33 = this.mLocalStoreUri;
      var24.putString(var32, var33);
      StringBuilder var35 = new StringBuilder();
      String var36 = this.mUuid;
      String var37 = var35.append(var36).append(".senderUri").toString();
      String var38 = Utility.base64Encode(this.mSenderUri);
      var24.putString(var37, var38);
      StringBuilder var40 = new StringBuilder();
      String var41 = this.mUuid;
      String var42 = var40.append(var41).append(".description").toString();
      String var43 = this.mDescription;
      var24.putString(var42, var43);
      StringBuilder var45 = new StringBuilder();
      String var46 = this.mUuid;
      String var47 = var45.append(var46).append(".name").toString();
      String var48 = this.mName;
      var24.putString(var47, var48);
      StringBuilder var50 = new StringBuilder();
      String var51 = this.mUuid;
      String var52 = var50.append(var51).append(".email").toString();
      String var53 = this.mEmail;
      var24.putString(var52, var53);
      StringBuilder var55 = new StringBuilder();
      String var56 = this.mUuid;
      String var57 = var55.append(var56).append(".automaticCheckIntervalMinutes").toString();
      int var58 = this.mAutomaticCheckIntervalMinutes;
      var24.putInt(var57, var58);
      StringBuilder var60 = new StringBuilder();
      String var61 = this.mUuid;
      String var62 = var60.append(var61).append(".lastAutomaticCheckTime").toString();
      long var63 = this.mLastAutomaticCheckTime;
      var24.putLong(var62, var63);
      StringBuilder var66 = new StringBuilder();
      String var67 = this.mUuid;
      String var68 = var66.append(var67).append(".notifyNewMail").toString();
      boolean var69 = this.mNotifyNewMail;
      var24.putBoolean(var68, var69);
      StringBuilder var71 = new StringBuilder();
      String var72 = this.mUuid;
      String var73 = var71.append(var72).append(".deletePolicy").toString();
      int var74 = this.mDeletePolicy;
      var24.putInt(var73, var74);
      StringBuilder var76 = new StringBuilder();
      String var77 = this.mUuid;
      String var78 = var76.append(var77).append(".draftsFolderName").toString();
      String var79 = this.mDraftsFolderName;
      var24.putString(var78, var79);
      StringBuilder var81 = new StringBuilder();
      String var82 = this.mUuid;
      String var83 = var81.append(var82).append(".sentFolderName").toString();
      String var84 = this.mSentFolderName;
      var24.putString(var83, var84);
      StringBuilder var86 = new StringBuilder();
      String var87 = this.mUuid;
      String var88 = var86.append(var87).append(".trashFolderName").toString();
      String var89 = this.mTrashFolderName;
      var24.putString(var88, var89);
      StringBuilder var91 = new StringBuilder();
      String var92 = this.mUuid;
      String var93 = var91.append(var92).append(".outboxFolderName").toString();
      String var94 = this.mOutboxFolderName;
      var24.putString(var93, var94);
      StringBuilder var96 = new StringBuilder();
      String var97 = this.mUuid;
      String var98 = var96.append(var97).append(".accountNumber").toString();
      int var99 = this.mAccountNumber;
      var24.putInt(var98, var99);
      StringBuilder var101 = new StringBuilder();
      String var102 = this.mUuid;
      String var103 = var101.append(var102).append(".vibrate").toString();
      boolean var104 = this.mVibrate;
      var24.putBoolean(var103, var104);
      StringBuilder var106 = new StringBuilder();
      String var107 = this.mUuid;
      String var108 = var106.append(var107).append(".vibrateWhenSilent").toString();
      boolean var109 = this.mVibrateWhenSilent;
      var24.putBoolean(var108, var109);
      StringBuilder var111 = new StringBuilder();
      String var112 = this.mUuid;
      String var113 = var111.append(var112).append(".ringtone").toString();
      String var114 = this.mRingtoneUri;
      var24.putString(var113, var114);
      StringBuilder var116 = new StringBuilder();
      String var117 = this.mUuid;
      String var118 = var116.append(var117).append(".syncWindow").toString();
      int var119 = this.mSyncWindow;
      var24.putInt(var118, var119);
      StringBuilder var121 = new StringBuilder();
      String var122 = this.mUuid;
      String var123 = var121.append(var122).append(".backupFlags").toString();
      int var124 = this.mBackupFlags;
      var24.putInt(var123, var124);
      StringBuilder var126 = new StringBuilder();
      String var127 = this.mUuid;
      String var128 = var126.append(var127).append(".protocolVersion").toString();
      String var129 = this.mProtocolVersion;
      var24.putString(var128, var129);
      StringBuilder var131 = new StringBuilder();
      String var132 = this.mUuid;
      String var133 = var131.append(var132).append(".securityFlags").toString();
      int var134 = this.mSecurityFlags;
      var24.putInt(var133, var134);
      StringBuilder var136 = new StringBuilder();
      String var137 = this.mUuid;
      String var138 = var136.append(var137).append(".signature").toString();
      String var139 = this.mSignature;
      var24.putString(var138, var139);
      StringBuilder var141 = new StringBuilder();
      String var142 = this.mUuid;
      String var143 = var141.append(var142).append(".led").toString();
      boolean var144 = this.mLED;
      var24.putBoolean(var143, var144);
      StringBuilder var146 = new StringBuilder();
      String var147 = this.mUuid;
      String var148 = var146.append(var147).append(".transportUri").toString();
      var24.remove(var148);
      boolean var150 = var24.commit();
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

   public void setLED(boolean var1) {
      this.mLED = var1;
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

   public void setRingtone(String var1) {
      this.mRingtoneUri = var1;
   }

   public void setSenderUri(String var1) {
      this.mSenderUri = var1;
   }

   public void setSentFolderName(String var1) {
      this.mSentFolderName = var1;
   }

   public void setStoreUri(String var1) {
      this.mStoreUri = var1;
   }

   public void setSyncWindow(int var1) {
      this.mSyncWindow = var1;
   }

   public void setTrashFolderName(String var1) {
      this.mTrashFolderName = var1;
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
