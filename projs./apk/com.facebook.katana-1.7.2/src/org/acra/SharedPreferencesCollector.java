package org.acra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.acra.ACRA;

public class SharedPreferencesCollector {

   public SharedPreferencesCollector() {}

   public static String collect(Context var0) {
      StringBuilder var1 = new StringBuilder();
      TreeMap var2 = new TreeMap();
      SharedPreferences var3 = PreferenceManager.getDefaultSharedPreferences(var0);
      var2.put("default", var3);
      String[] var5 = ACRA.getConfig().additionalSharedPreferences();
      if(var5 != null) {
         String[] var6 = var5;
         int var7 = var5.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = var6[var8];
            SharedPreferences var10 = var0.getSharedPreferences(var9, 0);
            var2.put(var9, var10);
         }
      }

      StringBuilder var23;
      for(Iterator var12 = var2.keySet().iterator(); var12.hasNext(); var23 = var1.append("\n")) {
         String var13 = (String)var12.next();
         StringBuilder var14 = var1.append(var13).append("\n");
         SharedPreferences var15 = (SharedPreferences)var2.get(var13);
         if(var15 != null) {
            Map var16 = var15.getAll();
            StringBuilder var19;
            StringBuilder var21;
            String var20;
            if(var16 != null && var16.size() > 0) {
               for(Iterator var17 = var16.keySet().iterator(); var17.hasNext(); var21 = var19.append(var20).append("\n")) {
                  String var18 = (String)var17.next();
                  var19 = var1.append(var18).append("=");
                  var20 = var16.get(var18).toString();
               }
            } else {
               StringBuilder var22 = var1.append("empty\n");
            }
         } else {
            StringBuilder var24 = var1.append("null\n");
         }
      }

      return var1.toString();
   }
}
