package android.support.v4.util;


public class DebugUtils {

   public DebugUtils() {}

   public static void buildShortClassTag(Object var0, StringBuilder var1) {
      if(var0 == null) {
         StringBuilder var2 = var1.append("null");
      } else {
         String var3 = var0.getClass().getSimpleName();
         if(var3 == null || var3.length() <= 0) {
            var3 = var0.getClass().getName();
            int var4 = var3.lastIndexOf(46);
            if(var4 > 0) {
               int var5 = var4 + 1;
               var3 = var3.substring(var5);
            }
         }

         var1.append(var3);
         StringBuilder var7 = var1.append('{');
         String var8 = Integer.toHexString(System.identityHashCode(var0));
         var1.append(var8);
      }
   }
}
