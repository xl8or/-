package com.facebook.katana.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IntentUtils {

   public IntentUtils() {}

   public static Set<Long> primitiveToSet(long[] var0) {
      HashSet var1;
      if(var0 == null) {
         var1 = null;
      } else {
         HashSet var2 = new HashSet();
         long[] var3 = var0;
         int var4 = var0.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Long var6 = Long.valueOf(var3[var5]);
            var2.add(var6);
         }

         var1 = var2;
      }

      return var1;
   }

   public static long[] setToPrimitive(Set<Long> var0) {
      Object var1;
      if(var0 == null) {
         var1 = false;
      } else {
         ArrayList var2 = new ArrayList(var0);
         Object var3 = var2.size();
         int var4 = 0;

         while(true) {
            int var5 = ((Object[])var3).length;
            if(var4 >= var5) {
               var1 = var3;
               break;
            }

            long var6 = ((Long)var2.get(var4)).longValue();
            ((Object[])var3)[var4] = var6;
            ++var4;
         }
      }

      return (long[])var1;
   }
}
