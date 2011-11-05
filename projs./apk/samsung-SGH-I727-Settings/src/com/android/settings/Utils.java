package com.android.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import java.util.List;

public class Utils {

   public static final int UPDATE_PREFERENCE_FLAG_SET_TITLE_TO_MATCHING_ACTIVITY = 1;


   public Utils() {}

   public static boolean isMonkeyRunning() {
      return SystemProperties.getBoolean("ro.monkey", (boolean)0);
   }

   public static boolean updatePreferenceToSpecificActivityOrRemove(Context var0, PreferenceGroup var1, String var2, int var3) {
      boolean var4 = false;
      Preference var5 = var1.findPreference(var2);
      if(var5 != null) {
         Intent var6 = var5.getIntent();
         if(var6 != null) {
            PackageManager var7 = var0.getPackageManager();
            List var8 = var7.queryIntentActivities(var6, 0);
            int var9 = var8.size();

            for(int var10 = 0; var10 < var9; ++var10) {
               ResolveInfo var11 = (ResolveInfo)var8.get(var10);
               if((var11.activityInfo.applicationInfo.flags & 1) != 0) {
                  Intent var12 = new Intent();
                  String var13 = var11.activityInfo.packageName;
                  String var14 = var11.activityInfo.name;
                  Intent var15 = var12.setClassName(var13, var14);
                  var5.setIntent(var15);
                  if((var3 & 1) != 0) {
                     CharSequence var16 = var11.loadLabel(var7);
                     var5.setTitle(var16);
                  }

                  var4 = true;
                  return var4;
               }
            }
         }

         var1.removePreference(var5);
         var4 = true;
      }

      return var4;
   }
}
