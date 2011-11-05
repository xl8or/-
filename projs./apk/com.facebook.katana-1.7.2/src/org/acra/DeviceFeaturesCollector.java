package org.acra;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import java.lang.reflect.Method;
import org.acra.ACRA;
import org.acra.Compatibility;

public class DeviceFeaturesCollector {

   public DeviceFeaturesCollector() {}

   public static String getFeatures(Context var0) {
      String var25;
      if(Compatibility.getAPILevel() >= 5) {
         StringBuffer var1 = new StringBuffer();
         PackageManager var2 = var0.getPackageManager();

         try {
            Class[] var3 = (Class[])false;
            Method var4 = PackageManager.class.getMethod("getSystemAvailableFeatures", var3);
            Object[] var5 = new Object[0];
            Object[] var6 = (Object[])((Object[])var4.invoke(var2, var5));
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Object var9 = var6[var8];
               String var10 = (String)var9.getClass().getField("name").get(var9);
               if(var10 != null) {
                  var1.append(var10);
               } else {
                  Class var13 = var9.getClass();
                  Class[] var14 = (Class[])false;
                  Method var15 = var13.getMethod("getGlEsVersion", var14);
                  Object[] var16 = new Object[0];
                  String var17 = (String)var15.invoke(var9, var16);
                  StringBuffer var18 = var1.append("glEsVersion = ");
                  var1.append(var17);
               }

               StringBuffer var12 = var1.append("\n");
            }
         } catch (Throwable var26) {
            int var21 = Log.w(ACRA.LOG_TAG, "Error : ", var26);
            StringBuffer var22 = var1.append("Could not retrieve data: ");
            String var23 = var26.getMessage();
            var1.append(var23);
         }

         var25 = var1.toString();
      } else {
         var25 = "Data available only with API Level > 5";
      }

      return var25;
   }
}
