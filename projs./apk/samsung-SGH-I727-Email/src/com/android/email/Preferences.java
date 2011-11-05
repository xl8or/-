package com.android.email;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import com.android.email.Account;
import com.android.email.Email;
import java.util.UUID;

public class Preferences {

   private static final String ACCOUNT_UUIDS = "accountUuids";
   private static final String ACTIVATION_LICENSE = "activationLicense";
   private static final String CP_UPDATE_MESSAGE = "cpUpdateMessage";
   private static final String DEFAULT_ACCOUNT_UUID = "defaultAccountUuid";
   private static final String DEVICE_UID = "deviceUID";
   private static final String ENABLE_DEBUG_LOGGING = "enableDebugLogging";
   private static final String ENABLE_EXCHANGE_FILE_LOGGING = "enableExchangeFileLogging";
   private static final String ENABLE_EXCHANGE_LOGGING = "enableExchangeLogging";
   private static final String ENABLE_SENSITIVE_LOGGING = "enableSensitiveLogging";
   private static final String ONE_TIME_INITIALIZATION_PROGRESS = "oneTimeInitializationProgress";
   private static final String PREFERENCES_FILE = "AndroidMail.Main";
   private static Preferences preferences;
   SharedPreferences mSharedPreferences;


   private Preferences(Context var1) {
      SharedPreferences var2 = var1.getSharedPreferences("AndroidMail.Main", 0);
      this.mSharedPreferences = var2;
   }

   public static Preferences getPreferences(Context var0) {
      synchronized(Preferences.class){}

      Preferences var1;
      try {
         if(preferences == null) {
            preferences = new Preferences(var0);
         }

         var1 = preferences;
      } finally {
         ;
      }

      return var1;
   }

   public void clear() {
      boolean var1 = this.mSharedPreferences.edit().clear().commit();
   }

   public void dump() {}

   public Account getAccountByContentUri(Uri var1) {
      String var2 = var1.getScheme();
      Account var4;
      if("content".equals(var2)) {
         String var3 = var1.getAuthority();
         if("accounts".equals(var3)) {
            String var5 = var1.getPath().substring(1);
            if(var5 == null) {
               var4 = null;
               return var4;
            } else {
               String var6 = this.mSharedPreferences.getString("accountUuids", (String)null);
               if(var6 != null && var6.length() != 0) {
                  String[] var7 = var6.split(",");
                  int var8 = 0;

                  for(int var9 = var7.length; var8 < var9; ++var8) {
                     String var10 = var7[var8];
                     if(var5.equals(var10)) {
                        var4 = new Account(this, var5);
                        return var4;
                     }
                  }

                  var4 = null;
               } else {
                  var4 = null;
               }

               return var4;
            }
         }
      }

      var4 = null;
      return var4;
   }

   public Account[] getAccounts() {
      String var1 = this.mSharedPreferences.getString("accountUuids", (String)null);
      Account[] var2;
      if(var1 != null && var1.length() != 0) {
         String[] var3 = var1.split(",");
         Account[] var4 = new Account[var3.length];
         int var5 = 0;

         for(int var6 = var3.length; var5 < var6; ++var5) {
            String var7 = var3[var5];
            Account var8 = new Account(this, var7);
            var4[var5] = var8;
            String var9 = "getAccounts() = " + var5;
            Email.loge(">>> Preference", var9);
         }

         var2 = var4;
      } else {
         Email.loge(">>> Preference", "new Account[] {}");
         var2 = new Account[0];
      }

      return var2;
   }

   public String getActivationLicense() {
      return this.mSharedPreferences.getString("activationLicense", (String)null);
   }

   public int getCPUpdateMessage() {
      return this.mSharedPreferences.getInt("cpUpdateMessage", 0);
   }

   public Account getCarrierAccount() {
      synchronized(Preferences.class) {
         String var1 = this.mSharedPreferences.getString("carrierAccount", (String)null);
         String var2 = "fixed account >> " + var1;
         Email.loge(">>> Preference", var2);
         Account var3 = null;
         Account var4;
         if(var1 == null) {
            Email.loge(">>> Preference", "carrierAccountId is null ");
            var4 = var3;
         } else {
            Account[] var5 = this.getAccounts();
            if(var1 != null) {
               Account[] var6 = var5;
               int var7 = var5.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  Account var9 = var6[var8];
                  StringBuilder var10 = (new StringBuilder()).append("account.getUuid() = ");
                  String var11 = var9.getUuid();
                  String var12 = var10.append(var11).toString();
                  Email.loge(">>> Preference", var12);
                  if(var9.getUuid().equals(var1)) {
                     var3 = var9;
                     break;
                  }
               }
            }

            var4 = var3;
         }

         return var4;
      }
   }

   public Account getDefaultAccount() {
      String var1 = this.mSharedPreferences.getString("defaultAccountUuid", (String)null);
      Account var2 = null;
      Account[] var3 = this.getAccounts();
      if(var1 != null) {
         Account[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Account var7 = var4[var6];
            if(var7.getUuid().equals(var1)) {
               var2 = var7;
               break;
            }
         }
      }

      if(var2 == null && var3.length > 0) {
         var2 = var3[0];
         this.setDefaultAccount(var2);
      }

      return var2;
   }

   public String getDeviceUID() {
      synchronized(this){}

      String var1;
      try {
         var1 = this.mSharedPreferences.getString("deviceUID", (String)null);
         if(var1 == null) {
            var1 = UUID.randomUUID().toString();
            this.mSharedPreferences.edit().putString("deviceUID", var1).commit();
         }
      } finally {
         ;
      }

      return var1;
   }

   public boolean getEnableDebugLogging() {
      return this.mSharedPreferences.getBoolean("enableDebugLogging", (boolean)0);
   }

   public boolean getEnableExchangeFileLogging() {
      return this.mSharedPreferences.getBoolean("enableExchangeFileLogging", (boolean)0);
   }

   public boolean getEnableExchangeLogging() {
      return this.mSharedPreferences.getBoolean("enableExchangeLogging", (boolean)0);
   }

   public boolean getEnableSensitiveLogging() {
      return this.mSharedPreferences.getBoolean("enableSensitiveLogging", (boolean)0);
   }

   public int getOneTimeInitializationProgress() {
      return this.mSharedPreferences.getInt("oneTimeInitializationProgress", 0);
   }

   public void save() {}

   public void setActivationLicense(String var1) {
      this.mSharedPreferences.edit().putString("activationLicense", var1).commit();
   }

   public void setCPUpdateMessage(int var1) {
      this.mSharedPreferences.edit().putInt("cpUpdateMessage", var1).commit();
   }

   public void setCarrierAccountId(String var1) {
      synchronized(Preferences.class) {
         this.mSharedPreferences.edit().putString("carrierAccount", var1).commit();
      }
   }

   public void setDefaultAccount(Account var1) {
      Editor var2 = this.mSharedPreferences.edit();
      String var3 = var1.getUuid();
      boolean var4 = var2.putString("defaultAccountUuid", var3).commit();
   }

   public void setEnableDebugLogging(boolean var1) {
      this.mSharedPreferences.edit().putBoolean("enableDebugLogging", var1).commit();
   }

   public void setEnableExchangeFileLogging(boolean var1) {
      this.mSharedPreferences.edit().putBoolean("enableExchangeFileLogging", var1).commit();
   }

   public void setEnableExchangeLogging(boolean var1) {
      this.mSharedPreferences.edit().putBoolean("enableExchangeLogging", var1).commit();
   }

   public void setEnableSensitiveLogging(boolean var1) {
      this.mSharedPreferences.edit().putBoolean("enableSensitiveLogging", var1).commit();
   }

   public void setOneTimeInitializationProgress(int var1) {
      this.mSharedPreferences.edit().putInt("oneTimeInitializationProgress", var1).commit();
   }
}
