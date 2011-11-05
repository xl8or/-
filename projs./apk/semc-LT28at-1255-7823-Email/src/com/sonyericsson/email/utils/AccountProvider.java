package com.sonyericsson.email.utils;

import android.content.Context;
import com.android.email.provider.EmailContent;
import com.sonyericsson.email.utils.customization.AccountData;
import com.sonyericsson.email.utils.customization.CustomizationFactory;
import com.sonyericsson.email.utils.r2r.R2R;

public class AccountProvider {

   public AccountProvider() {}

   public static EmailContent.Account getSettings(Context var0, String var1, String var2) {
      EmailContent.Account var3 = null;
      boolean var4 = false;
      String[] var5 = var1.split("@");
      String var6 = var5[0].trim();
      String var7;
      if(var5.length > 1) {
         var7 = var5[1].trim();
      } else {
         var7 = "";
      }

      AccountData var8 = getSettingsFromBranded(var0, var7);
      if(var8 == null) {
         AccountData var9 = getSettingsFromPreconf(var0);
         if(var9 != null) {
            String var10 = var9.getEmailAddress();
            int var11 = var10.indexOf("@") + 1;
            if(var10.substring(var11).trim().compareToIgnoreCase(var7) == 0) {
               var4 = true;
            } else {
               var8 = null;
            }
         }
      }

      if(var8 == null) {
         getSettingsFromOthers(var0, var7);
      }

      if(var8 == null) {
         getSettingsFromR2R(var0, var7);
      }

      if(var8 != null) {
         if(!var4) {
            String var14;
            if(var8.hasIncomingFullEmailLogin()) {
               var14 = var1;
            } else {
               var14 = var6;
            }

            String var15;
            if(var8.hasOutgoingFullEmailLogin()) {
               var15 = var1;
            } else {
               var15 = var6;
            }

            var8.setIncomingPassword(var2);
            var8.setIncomingUsername(var14);
            var8.setOutgoingPassword(var2);
            var8.setOutgoingUsername(var15);
            var8.setEmailAddress(var1);
         }

         var3 = var8.getAccount();
         if(var4) {
            String var16 = var8.getBrandedLabel();
            var3.setDisplayName(var16);
         }
      }

      return var3;
   }

   private static AccountData getSettingsFromBranded(Context var0, String var1) {
      AccountData var2 = null;
      AccountData[] var3 = CustomizationFactory.getInstance().getCustomization(var0).getBrandedAccountsData(var0);
      if(var3 != null) {
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5 || var2 != null) {
               break;
            }

            if(var3[var4].getDomain().equalsIgnoreCase(var1)) {
               var2 = var3[var4];
            }

            ++var4;
         }
      }

      return var2;
   }

   public static AccountData getSettingsFromDefaultUX(Context var0) {
      return CustomizationFactory.getInstance().getCustomization(var0).getDefaultSettings();
   }

   private static AccountData getSettingsFromOthers(Context var0, String var1) {
      return CustomizationFactory.getInstance().getCustomization(var0).getOtherAccountData(var0, var1);
   }

   private static AccountData getSettingsFromPreconf(Context var0) {
      return CustomizationFactory.getInstance().getCustomization(var0).getPreconfAccountData(var0);
   }

   private static AccountData getSettingsFromR2R(Context var0, String var1) {
      AccountData var2 = CustomizationFactory.getInstance().getCustomization(var0).getDefaultSettings();
      AccountData var3;
      if(var2 != null) {
         var2.setDomain(var1);
         if(R2R.addR2RSettings(var0, var2)) {
            var3 = var2;
            return var3;
         }
      }

      var3 = null;
      return var3;
   }
}
