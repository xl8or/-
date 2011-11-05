package com.google.android.finsky.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.WindowManager;
import com.google.android.finsky.utils.VendingPreferences;

public class VendingUtils {

   private static volatile boolean sSystemWasUpgraded = 0;


   public VendingUtils() {}

   public static Pair<Integer, Integer> getScreenDimensions(Context var0) {
      WindowManager var1 = (WindowManager)var0.getSystemService("window");
      DisplayMetrics var2 = new DisplayMetrics();
      var1.getDefaultDisplay().getMetrics(var2);
      int var3 = var2.widthPixels;
      int var4 = var2.heightPixels;
      int var5 = Math.min(var3, var4);
      int var6 = var2.widthPixels;
      int var7 = var2.heightPixels;
      int var8 = Math.max(var6, var7);
      Integer var9 = Integer.valueOf(var5);
      Integer var10 = Integer.valueOf(var8);
      return new Pair(var9, var10);
   }

   public static boolean wasSystemUpgraded() {
      boolean var0 = true;
      if(!sSystemWasUpgraded) {
         String var1 = Build.FINGERPRINT;
         String var2 = (String)VendingPreferences.LAST_BUILD_FINGERPRINT.get();
         if(var1.equals(var2)) {
            var0 = false;
         } else {
            sSystemWasUpgraded = (boolean)1;
            VendingPreferences.LAST_BUILD_FINGERPRINT.put(var1);
         }
      }

      return var0;
   }
}
