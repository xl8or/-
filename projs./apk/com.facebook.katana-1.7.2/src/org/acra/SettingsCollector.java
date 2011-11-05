package org.acra;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.util.Log;
import java.lang.reflect.Field;
import org.acra.ACRA;

public class SettingsCollector {

   public SettingsCollector() {}

   public static String collectSecureSettings(Context var0) {
      StringBuilder var1 = new StringBuilder();
      Field[] var2 = Secure.class.getFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field var5 = var2[var4];
         if(!var5.isAnnotationPresent(Deprecated.class) && var5.getType() == String.class && isAuthorized(var5)) {
            try {
               ContentResolver var6 = var0.getContentResolver();
               String var7 = (String)var5.get((Object)null);
               String var8 = Secure.getString(var6, var7);
               if(var8 != null) {
                  String var9 = var5.getName();
                  StringBuilder var10 = var1.append(var9).append("=").append(var8).append("\n");
               }
            } catch (IllegalArgumentException var15) {
               int var12 = Log.w(ACRA.LOG_TAG, "Error : ", var15);
            } catch (IllegalAccessException var16) {
               int var14 = Log.w(ACRA.LOG_TAG, "Error : ", var16);
            }
         }
      }

      return var1.toString();
   }

   public static String collectSystemSettings(Context var0) {
      StringBuilder var1 = new StringBuilder();
      Field[] var2 = System.class.getFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field var5 = var2[var4];
         if(!var5.isAnnotationPresent(Deprecated.class) && var5.getType() == String.class) {
            try {
               ContentResolver var6 = var0.getContentResolver();
               String var7 = (String)var5.get((Object)null);
               String var8 = System.getString(var6, var7);
               if(var8 != null) {
                  String var9 = var5.getName();
                  StringBuilder var10 = var1.append(var9).append("=").append(var8).append("\n");
               }
            } catch (IllegalArgumentException var15) {
               int var12 = Log.w(ACRA.LOG_TAG, "Error : ", var15);
            } catch (IllegalAccessException var16) {
               int var14 = Log.w(ACRA.LOG_TAG, "Error : ", var16);
            }
         }
      }

      return var1.toString();
   }

   private static boolean isAuthorized(Field var0) {
      boolean var1;
      if(var0 != null && !var0.getName().startsWith("WIFI_AP")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
