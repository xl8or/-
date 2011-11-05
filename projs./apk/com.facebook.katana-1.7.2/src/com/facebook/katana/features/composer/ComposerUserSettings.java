package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.composer.ComposerUserSettingsClient;

public class ComposerUserSettings {

   public static final String IS_TOUR_COMPLETED = "composer_tour_completed";
   public static final String PROJECT_NAME = "structured_composer";
   public static final String SETTINGS_SHARE_LOC = "composer_share_location";
   public static final String SHARE_LOC_ON = "1";
   public static final String TOUR_COMPLETED = "1";
   protected static ManagedDataStore<String, String, Object> store;


   public ComposerUserSettings() {}

   public static String get(Context var0, String var1) {
      return (String)getStore().get(var0, var1);
   }

   protected static ManagedDataStore<String, String, Object> getStore() {
      if(store == null) {
         ComposerUserSettingsClient var0 = new ComposerUserSettingsClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   public static boolean isImplicitLocOn(Context var0) {
      String var1 = get(var0, "composer_share_location");
      return "1".equals(var1);
   }

   public static boolean isTourComplete(Context var0) {
      String var1 = get(var0, "composer_tour_completed");
      return "1".equals(var1);
   }

   public static void setSetting(Context var0, String var1, String var2) {
      ManagedDataStore var3 = getStore();
      var3.callback(var0, (boolean)1, var1, var2, var2, (Object)null);
   }
}
