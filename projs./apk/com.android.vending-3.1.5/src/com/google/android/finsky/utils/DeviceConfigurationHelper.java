package com.google.android.finsky.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.WindowManager;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.GlExtensionReader;
import com.google.android.finsky.utils.VendingUtils;
import com.google.android.vending.remoting.protos.DeviceConfigurationProto;
import java.util.Iterator;
import java.util.List;

public class DeviceConfigurationHelper {

   private static DeviceConfigurationProto sDeviceConfiguration;


   public DeviceConfigurationHelper() {}

   public static void customizeDeviceConfiguration(Context var0, DeviceConfigurationProto var1) {
      FeatureInfo[] var2 = var0.getPackageManager().getSystemAvailableFeatures();
      if(var2 != null) {
         FeatureInfo[] var3 = var2;
         int var4 = var2.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FeatureInfo var6 = var3[var5];
            if(var6.name != null) {
               String var7 = var6.name;
               var1.addSystemAvailableFeature(var7);
            }
         }
      }

      String var9 = Build.CPU_ABI;
      var1.addNativePlatform(var9);
      if(!Build.CPU_ABI2.equals("unknown")) {
         String var11 = Build.CPU_ABI2;
         var1.addNativePlatform(var11);
      }
   }

   public static DeviceConfigurationProto getDeviceConfiguration() {
      synchronized(DeviceConfigurationHelper.class){}

      DeviceConfigurationProto var41;
      try {
         if(sDeviceConfiguration == null) {
            sDeviceConfiguration = new DeviceConfigurationProto();
            FinskyApp var0 = FinskyApp.get();
            ConfigurationInfo var1 = ((ActivityManager)var0.getSystemService("activity")).getDeviceConfigurationInfo();
            Pair var2 = VendingUtils.getScreenDimensions(var0);
            WindowManager var3 = (WindowManager)var0.getSystemService("window");
            DisplayMetrics var4 = new DisplayMetrics();
            var3.getDefaultDisplay().getMetrics(var4);
            DeviceConfigurationProto var5 = sDeviceConfiguration;
            int var6 = getTouchScreenId(var1.reqTouchScreen);
            DeviceConfigurationProto var7 = var5.setTouchScreen(var6);
            int var8 = getKeyboardConfigId(var1.reqKeyboardType);
            DeviceConfigurationProto var9 = var7.setKeyboard(var8);
            int var10 = getNavigationId(var1.reqNavigation);
            DeviceConfigurationProto var11 = var9.setNavigation(var10);
            int var12 = var1.reqGlEsVersion;
            DeviceConfigurationProto var13 = var11.setGlEsVersion(var12);
            int var14 = ((Integer)var2.first).intValue();
            DeviceConfigurationProto var15 = var13.setScreenWidth(var14);
            int var16 = ((Integer)var2.second).intValue();
            DeviceConfigurationProto var17 = var15.setScreenHeight(var16);
            int var18 = var4.densityDpi;
            var17.setScreenDensity(var18);
            DeviceConfigurationProto var20 = sDeviceConfiguration;
            byte var21;
            if((var1.reqInputFeatures & 1) > 0) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            var20.setHasHardKeyboard((boolean)var21);
            var20 = sDeviceConfiguration;
            if((var1.reqInputFeatures & 2) > 0) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            var20.setHasFiveWayNavigation((boolean)var21);
            Configuration var24 = var0.getResources().getConfiguration();
            DeviceConfigurationProto var25 = sDeviceConfiguration;
            int var26 = getScreenLayoutSizeId(var24.screenLayout);
            var25.setScreenLayout(var26);
            String[] var28 = var0.getPackageManager().getSystemSharedLibraryNames();
            int var29 = var28.length;

            int var30;
            for(var30 = 0; var30 < var29; ++var30) {
               String var31 = var28[var30];
               DeviceConfigurationProto var32 = sDeviceConfiguration.addSystemSharedLibrary(var31);
            }

            var28 = FinskyApp.get().getAssets().getLocales();
            var29 = var28.length;

            for(var30 = 0; var30 < var29; ++var30) {
               String var33 = var28[var30];
               DeviceConfigurationProto var34 = sDeviceConfiguration.addSystemSupportedLocale(var33);
            }

            String var35;
            DeviceConfigurationProto var36;
            for(Iterator var42 = (new GlExtensionReader()).getGlExtensions().iterator(); var42.hasNext(); var36 = sDeviceConfiguration.addGlExtension(var35)) {
               var35 = (String)var42.next();
            }

            DeviceConfigurationProto var38 = sDeviceConfiguration;
            customizeDeviceConfiguration(var0, var38);
         }

         var41 = sDeviceConfiguration;
      } finally {
         ;
      }

      return var41;
   }

   public static int getHashCode(DeviceConfigurationProto var0) {
      int var1 = getHashCode((Object)Integer.valueOf(var0.getTouchScreen())) * 31;
      int var2 = var0.getKeyboard();
      int var3 = (var1 + var2) * 31;
      int var4 = var0.getNavigation();
      int var5 = (var3 + var4) * 31;
      int var6 = var0.getScreenLayout();
      int var7 = (var5 + var6) * 31;
      int var8 = var0.getScreenDensity();
      int var9 = (var7 + var8) * 31;
      int var10 = var0.getScreenWidth();
      int var11 = (var9 + var10) * 31;
      int var12 = var0.getScreenHeight();
      int var13 = (var11 + var12) * 31;
      int var14 = var0.getGlEsVersion();
      int var15 = var13 + var14;
      List var16 = var0.getSystemSharedLibraryList();
      int var17 = hashRepeatedString(var15, var16);
      List var18 = var0.getSystemAvailableFeatureList();
      int var19 = hashRepeatedString(var17, var18);
      List var20 = var0.getNativePlatformList();
      int var21 = hashRepeatedString(var19, var20);
      List var22 = var0.getSystemSupportedLocaleList();
      int var23 = hashRepeatedString(var21, var22);
      List var24 = var0.getGlExtensionList();
      return hashRepeatedString(var23, var24);
   }

   private static int getHashCode(Object var0) {
      int var1;
      if(var0 == null) {
         var1 = 7;
      } else {
         var1 = var0.hashCode();
      }

      return var1;
   }

   private static int getKeyboardConfigId(int var0) {
      byte var1 = 0;
      switch(var0) {
      case 0:
      default:
         break;
      case 1:
         var1 = 1;
         break;
      case 2:
         var1 = 2;
         break;
      case 3:
         var1 = 3;
      }

      return var1;
   }

   private static int getNavigationId(int var0) {
      byte var1;
      switch(var0) {
      case 1:
         var1 = 1;
         break;
      case 2:
         var1 = 2;
         break;
      case 3:
         var1 = 3;
         break;
      case 4:
         var1 = 4;
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   private static int getScreenLayoutSizeId(int var0) {
      byte var1;
      switch(var0 & 15) {
      case 1:
         var1 = 1;
         break;
      case 2:
         var1 = 2;
         break;
      case 3:
         var1 = 3;
         break;
      case 4:
         var1 = 4;
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   private static int getTouchScreenId(int var0) {
      byte var1;
      switch(var0) {
      case 1:
         var1 = 1;
         break;
      case 2:
         var1 = 2;
         break;
      case 3:
         var1 = 3;
         break;
      default:
         var1 = 0;
      }

      return var1;
   }

   private static int hashRepeatedString(int var0, List<String> var1) {
      int var4;
      if(var1 != null) {
         for(Iterator var2 = var1.iterator(); var2.hasNext(); var0 = var4 * 31) {
            String var3 = (String)var2.next();
            if(var3 == null) {
               var4 = 7;
            } else {
               var4 = var3.hashCode();
            }
         }
      }

      return var0;
   }
}
