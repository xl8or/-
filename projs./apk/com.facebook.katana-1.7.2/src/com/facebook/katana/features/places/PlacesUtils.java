package com.facebook.katana.features.places;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.features.places.PlacesUserSettings;
import com.facebook.katana.model.FacebookCheckin;
import com.facebook.katana.provider.UserValuesManager;

public class PlacesUtils {

   public static final String OPT_IN_DEFAULT_SETTING = "default";


   public PlacesUtils() {}

   public static boolean checkOptInStatus(AppSession var0, Context var1) {
      String var2 = PlacesUserSettings.get(var1, "places_opt_in");
      boolean var3;
      if(var2 != null && !"default".equals(var2)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public static void setLastCheckin(Context var0, FacebookCheckin var1, long var2) {
      Long var4 = Long.valueOf(var1.getDetails().getPlaceInfo().mPageId);
      UserValuesManager.setValue(var0, "places:last_checkin_pageid", var4);
      Long var5 = Long.valueOf(var2);
      UserValuesManager.setValue(var0, "places:last_checkin_time", var5);
      String var6 = var1.jsonEncode();
      if(var6 != null) {
         UserValuesManager.setValue(var0, "places:last_checkin", var6);
      }
   }
}
