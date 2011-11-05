package com.android.email.activity.setup;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import com.android.email.AccountBackupRestore;
import com.android.email.VendorPolicyLoader;
import com.android.email.provider.EmailContent;
import com.sonyericsson.email.utils.customization.AccountData;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

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
         var1.update(var0, var3);
      }

      AccountBackupRestore.backupAccounts(var0);
   }

   public static AccountSettingsUtils.Provider findProviderForDomain(Context var0, String var1) {
      AccountSettingsUtils.Provider var2 = VendorPolicyLoader.getInstance(var0).findProviderForDomain(var1);
      if(var2 == null) {
         var2 = findProviderForDomain(var0, var1, 2131034119);
      }

      if(var2 == null) {
         var2 = findProviderForDomain(var0, var1, 2131034118);
      }

      return var2;
   }

   private static AccountSettingsUtils.Provider findProviderForDomain(Context param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   protected static String[] getCheckIntervalEntryList(Context var0, boolean var1, AccountData var2) {
      ArrayList var3 = new ArrayList();
      int var4;
      if(var1) {
         var4 = 2131099650;
      } else {
         var4 = 2131099648;
      }

      int var5;
      if(var1) {
         var5 = 2131099651;
      } else {
         var5 = 2131099649;
      }

      int[] var6;
      if(var1) {
         var6 = var2.getEasCheckIntervalList();
      } else {
         var6 = var2.getCheckIntervalList();
      }

      String[] var7 = var0.getResources().getStringArray(var4);
      String[] var8 = var0.getResources().getStringArray(var5);
      int var9 = 0;
      String[] var10 = var8;
      int var11 = var8.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         int var13 = Integer.parseInt(var10[var12]);
         if(var13 == -1) {
            var13 = 0;
         } else if(var13 > 0) {
            var13 *= 60;
         }

         if(Arrays.binarySearch(var6, var13) >= 0) {
            String var14 = var7[var9];
            var3.add(var14);
         }

         ++var9;
      }

      String[] var16 = new String[var3.size()];
      return (String[])var3.toArray(var16);
   }

   protected static int getCheckIntervalMinutes(int var0, int[] var1) {
      if(Arrays.binarySearch(var1, var0) < 0) {
         var0 = var1[0];
      }

      int var2;
      if(var0 == 0) {
         var2 = -1;
      } else if(var0 > 0) {
         var2 = var0 / 60;
      } else {
         var2 = var0;
      }

      return var2;
   }

   protected static String[] getCheckIntervalValueList(Context var0, boolean var1, AccountData var2) {
      ArrayList var3 = new ArrayList();
      int var4;
      if(var1) {
         var4 = 2131099651;
      } else {
         var4 = 2131099649;
      }

      int[] var5;
      if(var1) {
         var5 = var2.getEasCheckIntervalList();
      } else {
         var5 = var2.getCheckIntervalList();
      }

      String[] var6 = var0.getResources().getStringArray(var4);
      int var7 = 0;
      String[] var8 = var6;
      int var9 = var6.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String var11 = var8[var10];
         int var12 = Integer.parseInt(var11);
         int var13 = Integer.parseInt(var11);
         if(var13 == -1) {
            var13 = 0;
         } else if(var13 > 0) {
            var13 *= 60;
         }

         if(Arrays.binarySearch(var5, var13) >= 0) {
            var3.add(var11);
         }

         ++var7;
      }

      String[] var15 = new String[var3.size()];
      return (String[])var3.toArray(var15);
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

   public static void resetPreferences(String var0, Context var1) {
      boolean var2 = var1.getSharedPreferences(var0, 0).edit().clear().commit();
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
