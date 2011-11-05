package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.PrivacySetting;
import com.facebook.katana.service.method.AudienceSettings;

class AudienceSettingsManagedStoreClient implements ManagedDataStore.Client<PrivacySetting.Category, AudienceSettings, Object> {

   AudienceSettingsManagedStoreClient() {}

   public AudienceSettings deserialize(String var1) {
      throw new IllegalStateException("Attempting to deserialize AudienceSettings, currently unsupported");
   }

   public int getCacheTtl(PrivacySetting.Category var1, AudienceSettings var2) {
      return 300;
   }

   public String getKey(PrivacySetting.Category var1) {
      return "audience_setting:" + var1;
   }

   public int getPersistentStoreTtl(PrivacySetting.Category var1, AudienceSettings var2) {
      return 0;
   }

   public String initiateNetworkRequest(Context var1, PrivacySetting.Category var2, NetworkRequestCallback<PrivacySetting.Category, AudienceSettings, Object> var3) {
      return AudienceSettings.RequestAudienceSettings(var1, var2, var3);
   }

   public boolean staleDataAcceptable(PrivacySetting.Category var1, AudienceSettings var2) {
      return true;
   }
}
