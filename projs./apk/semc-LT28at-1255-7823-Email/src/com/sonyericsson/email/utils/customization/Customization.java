package com.sonyericsson.email.utils.customization;

import android.content.Context;
import android.net.Uri;
import com.sonyericsson.email.utils.customization.AccountData;
import com.sonyericsson.email.utils.customization.CmzXmlParser;
import java.io.Closeable;
import java.io.IOException;

public class Customization {

   static String CMZ_CONTENT_PATH = "/content/com.sonyericsson.email";
   static String CMZ_PATH = "content://com.sonyericsson.provider.customization";
   static String CMZ_SETTINGS_PATH = "/settings/com.sonyericsson.email";
   static String DEFAULT_CONTENT_PATH;
   static String DEFAULT_SETTINGS_PATH;
   private final Context mContext;
   private final CmzXmlParser mParser;
   private final Uri mUri;
   private AccountData mUxSettings;


   static {
      StringBuilder var0 = new StringBuilder();
      String var1 = CMZ_SETTINGS_PATH;
      DEFAULT_SETTINGS_PATH = var0.append(var1).append("/default_settings.xml").toString();
      StringBuilder var2 = new StringBuilder();
      String var3 = CMZ_CONTENT_PATH;
      DEFAULT_CONTENT_PATH = var2.append(var3).append("/default_settings.xml").toString();
   }

   Customization(Context var1) {
      this(var1, (boolean)0);
   }

   Customization(Context param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private void closeStream(Closeable var1) {
      if(var1 != null) {
         try {
            var1.close();
         } catch (IOException var3) {
            ;
         }
      }
   }

   public AccountData[] getBrandedAccountsData(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public AccountData getDefaultSettings() {
      return this.mUxSettings.makeCopy();
   }

   public AccountData getMasterResetData(String param1) {
      // $FF: Couldn't be decompiled
   }

   public AccountData getOtherAccountData(Context param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public AccountData getPreconfAccountData(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean hasBrandedAccountData(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean hasPreconfAccountData(Context param1) {
      // $FF: Couldn't be decompiled
   }
}
