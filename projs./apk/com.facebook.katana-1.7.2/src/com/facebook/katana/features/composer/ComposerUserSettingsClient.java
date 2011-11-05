package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.FqlGetUserServerSettings;

class ComposerUserSettingsClient implements ManagedDataStore.Client<String, String, Object> {

   ComposerUserSettingsClient() {}

   public String deserialize(String var1) {
      return var1;
   }

   public int getCacheTtl(String var1, String var2) {
      int var3;
      if(var1 != null && var1.equals("composer_share_location")) {
         var3 = 3600;
      } else {
         var3 = 31536000;
      }

      return var3;
   }

   public String getKey(String var1) {
      return "uss:" + var1;
   }

   public int getPersistentStoreTtl(String var1, String var2) {
      int var3;
      if(var1 != null && var1.equals("composer_share_location")) {
         var3 = 3600;
      } else {
         var3 = 31536000;
      }

      return var3;
   }

   public String initiateNetworkRequest(Context var1, String var2, NetworkRequestCallback<String, String, Object> var3) {
      return FqlGetUserServerSettings.RequestSettingsByProjectSetting(AppSession.getActiveSession(var1, (boolean)0), var1, "structured_composer", var2, var3);
   }

   public boolean staleDataAcceptable(String var1, String var2) {
      return true;
   }
}
