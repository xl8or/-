package com.android.email.activity.setup;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.ContentProviderOperation.Builder;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.RemoteException;
import com.android.email.AccountBackupRestore;
import com.android.email.VendorPolicyLoader;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.provider.EmailContent;
import com.android.exchange.SyncScheduleData;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

public class AccountSettingsUtils {

   public AccountSettingsUtils() {}

   public static void commitSettings(Context var0, EmailContent.Account var1) {
      if(!var1.isSaved()) {
         var1.save(var0);
      } else {
         ContentValues var3 = new ContentValues();
         Boolean var4 = Boolean.valueOf(var1.mIsDefault);
         var3.put("isDefault", var4);
         String var5 = var1.getDisplayName();
         var3.put("displayName", var5);
         String var6 = var1.getSenderName();
         var3.put("senderName", var6);
         String var7 = var1.getSignature();
         var3.put("signature", var7);
         Integer var8 = Integer.valueOf(var1.mSyncInterval);
         var3.put("syncInterval", var8);
         String var9 = var1.mRingtoneUri;
         var3.put("ringtoneUri", var9);
         Integer var10 = Integer.valueOf(var1.mFlags);
         var3.put("flags", var10);
         Integer var11 = Integer.valueOf(var1.mSyncLookback);
         var3.put("syncLookback", var11);
         Integer var12 = Integer.valueOf(var1.mEmailSize);
         var3.put("emailsize", var12);
         Integer var13 = Integer.valueOf(var1.mConflictFlags);
         var3.put("conflict", var13);
         SyncScheduleData var14 = var1.getSyncScheduleData();
         Integer var15 = Integer.valueOf(var14.getPeakDay());
         var3.put("peakDays", var15);
         Integer var16 = Integer.valueOf(var14.getStartMinute());
         var3.put("peakStartMinute", var16);
         Integer var17 = Integer.valueOf(var14.getEndMinute());
         var3.put("peakEndMinute", var17);
         Integer var18 = Integer.valueOf(var14.getPeakSchedule());
         var3.put("peakSchedule", var18);
         Integer var19 = Integer.valueOf(var14.getOffPeakSchedule());
         var3.put("offPeakSchedule", var19);
         Integer var20 = Integer.valueOf(var14.getRoamingSchedule());
         var3.put("roamingSchedule", var20);
         Integer var21 = Integer.valueOf(var1.mCalendarSyncLookback);
         var3.put("calendarSyncLookback", var21);
         String var22 = var1.mSmimeOwnCertificateAlias;
         var3.put("smimeOwnCertificateAlias", var22);
         Integer var23 = Integer.valueOf(var1.getSmimeFlags());
         var3.put("smimeOptionsFlags", var23);
         Integer var24 = Integer.valueOf(var1.getSmimeSignAlgorithm());
         var3.put("smimeSignAlgorithm", var24);
         Integer var25 = Integer.valueOf(var1.getSmimeEncryptionAlgorithm());
         var3.put("smimeEncryptionAlgorithm", var25);
         Integer var26 = Integer.valueOf(var1.mConversationMode);
         var3.put("conversationMode", var26);
         Integer var27 = Integer.valueOf(var1.mTextPreviewSize);
         var3.put("textPreview", var27);
         var1.update(var0, var3);
         ContentValues var29 = new ContentValues();
         Long var30 = Long.valueOf(var1.mTimeLimit);
         var29.put("timeLimit", var30);
         Integer var31 = Integer.valueOf(var1.mPeakTime);
         var29.put("peakTime", var31);
         Integer var32 = Integer.valueOf(var1.mOffPeakTime);
         var29.put("offPeakTime", var32);
         String var33 = var1.mDays;
         var29.put("days", var33);
         String var34 = var1.mPeakStartTime;
         var29.put("peakStartTime", var34);
         String var35 = var1.mPeakEndTime;
         var29.put("peakEndTime", var35);
         ArrayList var36 = new ArrayList();
         Builder var37 = ContentProviderOperation.newUpdate(EmailContent.Account.CONTENT_URI).withValues(var3);
         String[] var38 = new String[1];
         String var39 = Long.toString(var1.mId);
         var38[0] = var39;
         ContentProviderOperation var40 = var37.withSelection("_id=?", var38).build();
         var36.add(var40);
         Builder var42 = ContentProviderOperation.newUpdate(EmailContent.AccountCB.CONTENT_URI).withValues(var29);
         String[] var43 = new String[1];
         String var44 = Long.toString(var1.mId);
         var43[0] = var44;
         ContentProviderOperation var45 = var42.withSelection("accountKey=?", var43).build();
         var36.add(var45);
         if(var1.mTypeMsg == 1) {
            ContentValues var47 = new ContentValues();
            Integer var48 = Integer.valueOf(var1.mWhileroaming);
            var47.put("whileRoaming", var48);
            Integer var49 = Integer.valueOf(var1.mAttachmentEnabled);
            var47.put("attachmentEnabled", var49);
            Builder var50 = ContentProviderOperation.newUpdate(EmailContent.AccountCB.CONTENT_URI).withValues(var47);
            String[] var51 = new String[1];
            String var52 = Integer.toString(1);
            var51[0] = var52;
            ContentProviderOperation var53 = var50.withSelection("typeMsg=?", var51).build();
            var36.add(var53);
         }

         try {
            ContentProviderResult[] var55 = var0.getContentResolver().applyBatch("com.android.email.provider", var36);
         } catch (RemoteException var58) {
            ;
         } catch (OperationApplicationException var59) {
            ;
         }
      }

      AccountBackupRestore.backupAccounts(var0);
   }

   public static void commitSyncSettings(Context var0, EmailContent.Account var1) {
      ContentValues var2 = new ContentValues();
      Integer var3 = Integer.valueOf(var1.mPeakTime);
      var2.put("peakTime", var3);
      Integer var4 = Integer.valueOf(var1.mOffPeakTime);
      var2.put("offPeakTime", var4);
      String var5 = var1.mDays;
      var2.put("days", var5);
      String var6 = var1.mPeakStartTime;
      var2.put("peakStartTime", var6);
      String var7 = var1.mPeakEndTime;
      var2.put("peakEndTime", var7);
      ContentResolver var8 = var0.getContentResolver();
      Uri var9 = EmailContent.AccountCB.CONTENT_URI;
      var8.update(var9, var2, "typeMsg=1", (String[])null);
   }

   public static AccountSettingsUtils.Provider findProviderCustomer(Context var0, String var1) {
      AccountSettingsUtils.Provider var2 = AccountSetupCustomer.getInstance().getProviderCustomer(var1);
      if(var2 == null) {
         var2 = findProviderForDomain(var0, var1);
      }

      return var2;
   }

   public static AccountSettingsUtils.Provider findProviderForDomain(Context var0, String var1) {
      AccountSettingsUtils.Provider var2 = VendorPolicyLoader.getInstance(var0).findProviderForDomain(var1);
      if(var2 == null) {
         var2 = findProviderForDomain(var0, var1, 2131034132);
      }

      if(var2 == null) {
         var2 = findProviderForDomain(var0, var1, 2131034131);
      }

      return var2;
   }

   private static AccountSettingsUtils.Provider findProviderForDomain(Context param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private static String getXmlAttribute(Context var0, XmlResourceParser var1, String var2) {
      int var3 = var1.getAttributeResourceValue((String)null, var2, 0);
      String var4;
      if(var3 == 0) {
         var4 = var1.getAttributeValue((String)null, var2);
      } else {
         var4 = var0.getString(var3);
      }

      return var4;
   }

   public static class Provider implements Serializable {

      private static final long serialVersionUID = 8511656164616538989L;
      public String domain;
      public String id;
      public URI incomingUriTemplate;
      public String incomingUsernameTemplate;
      public String label;
      public String note;
      public URI outgoingUriTemplate;
      public String outgoingUsernameTemplate;


      public Provider() {}
   }
}
