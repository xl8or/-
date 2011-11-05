package com.facebook.katana.features.places;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.places.PlacesUserSettingsClient;

public class PlacesUserSettings {

   public static final String PLACES_PROJECT_NAME = "places";
   public static final String PLACES_SETTINGS_KEY_OPT_IN = "places_opt_in";
   protected static ManagedDataStore<String, String, Object> store;


   public PlacesUserSettings() {}

   public static String get(Context var0, String var1) {
      return (String)getStore().get(var0, var1);
   }

   protected static ManagedDataStore<String, String, Object> getStore() {
      if(store == null) {
         PlacesUserSettingsClient var0 = new PlacesUserSettingsClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   public static void setSetting(Context var0, String var1, String var2) {
      ManagedDataStore var3 = getStore();
      var3.callback(var0, (boolean)1, var1, var2, var2, (Object)null);
   }
}
