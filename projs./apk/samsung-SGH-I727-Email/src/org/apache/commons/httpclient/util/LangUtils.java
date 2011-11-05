package org.apache.commons.httpclient.util;


public class LangUtils {

   public static final int HASH_OFFSET = 37;
   public static final int HASH_SEED = 17;


   private LangUtils() {}

   public static boolean equals(Object var0, Object var1) {
      byte var2;
      if(var0 == null) {
         if(var1 == null) {
            var2 = 1;
         } else {
            var2 = 0;
         }
      } else {
         var2 = var0.equals(var1);
      }

      return (boolean)var2;
   }

   public static int hashCode(int var0, int var1) {
      return var0 * 37 + var1;
   }

   public static int hashCode(int var0, Object var1) {
      int var2;
      if(var1 != null) {
         var2 = var1.hashCode();
      } else {
         var2 = 0;
      }

      return hashCode(var0, var2);
   }

   public static int hashCode(int var0, boolean var1) {
      byte var2;
      if(var1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      return hashCode(var0, var2);
   }
}
