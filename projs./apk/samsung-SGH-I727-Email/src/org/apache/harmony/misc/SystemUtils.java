package org.apache.harmony.misc;


public class SystemUtils {

   public static final int ARC_IA32 = 1;
   public static final int ARC_IA64 = 2;
   public static final int ARC_UNKNOWN = 255;
   public static final int OS_LINUX = 2;
   public static final int OS_UNKNOWN = 255;
   public static final int OS_WINDOWS = 1;
   private static int arc = 0;
   private static int os = 0;


   public SystemUtils() {}

   public static int getOS() {
      if(os == 0) {
         String var0 = System.getProperty("os.name").substring(0, 3);
         if(var0.compareToIgnoreCase("win") == 0) {
            os = 1;
         } else if(var0.compareToIgnoreCase("lin") == 0) {
            os = 2;
         } else {
            os = -1;
         }
      }

      return os;
   }
}
