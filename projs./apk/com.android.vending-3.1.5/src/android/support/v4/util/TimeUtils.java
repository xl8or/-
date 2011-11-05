package android.support.v4.util;

import java.io.PrintWriter;

public class TimeUtils {

   public static final int HUNDRED_DAY_FIELD_LEN = 19;
   private static final int SECONDS_PER_DAY = 86400;
   private static final int SECONDS_PER_HOUR = 3600;
   private static final int SECONDS_PER_MINUTE = 60;
   private static char[] sFormatStr = new char[24];
   private static final Object sFormatSync = new Object();


   public TimeUtils() {}

   private static int accumField(int var0, int var1, boolean var2, int var3) {
      int var4;
      if(var0 <= 99 && (!var2 || var3 < 3)) {
         if(var0 <= 9 && (!var2 || var3 < 2)) {
            if(!var2 && var0 <= 0) {
               var4 = 0;
            } else {
               var4 = var1 + 1;
            }
         } else {
            var4 = var1 + 2;
         }
      } else {
         var4 = var1 + 3;
      }

      return var4;
   }

   public static void formatDuration(long var0, long var2, PrintWriter var4) {
      if(var0 == 0L) {
         var4.print("--");
      } else {
         formatDuration(var0 - var2, var4, 0);
      }
   }

   public static void formatDuration(long var0, PrintWriter var2) {
      formatDuration(var0, var2, 0);
   }

   public static void formatDuration(long var0, PrintWriter var2, int var3) {
      Object var4 = sFormatSync;
      synchronized(var4) {
         int var5 = formatDurationLocked(var0, var3);
         char[] var6 = sFormatStr;
         String var7 = new String(var6, 0, var5);
         var2.print(var7);
      }
   }

   public static void formatDuration(long var0, StringBuilder var2) {
      Object var3 = sFormatSync;
      synchronized(var3) {
         int var4 = formatDurationLocked(var0, 0);
         char[] var5 = sFormatStr;
         var2.append(var5, 0, var4);
      }
   }

   private static int formatDurationLocked(long var0, int var2) {
      int var3 = sFormatStr.length;
      if(var3 < var2) {
         sFormatStr = new char[var2];
      }

      char[] var5 = sFormatStr;
      int var8;
      if(var0 == 0L) {
         byte var6 = 0;

         for(var2 += -1; var6 < var2; var5[var6] = 32) {
            ;
         }

         var5[var6] = 48;
         var8 = 1;
      } else {
         byte var9;
         if(var0 > 0L) {
            var9 = 43;
         } else {
            var9 = 45;
            var0 = -var0;
         }

         int var10 = (int)(var0 % 1000L);
         int var11 = (int)Math.floor((double)(var0 / 1000L));
         int var12 = 0;
         int var13 = 0;
         int var14 = 0;
         if(var11 > 86400) {
            var12 = var11 / 86400;
            int var15 = 86400 * var12;
            var11 -= var15;
         }

         if(var11 > 3600) {
            var13 = var11 / 3600;
            int var16 = var13 * 3600;
            var11 -= var16;
         }

         if(var11 > 60) {
            var14 = var11 / 60;
            int var17 = var14 * 60;
            var11 -= var17;
         }

         int var18 = 0;
         if(var2 != 0) {
            int var19 = accumField(var12, 1, (boolean)0, 0);
            byte var20 = 1;
            byte var21;
            if(var19 > 0) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            int var22 = accumField(var13, var20, (boolean)var21, 2);
            int var23 = var19 + var22;
            byte var24 = 1;
            byte var25;
            if(var23 > 0) {
               var25 = 1;
            } else {
               var25 = 0;
            }

            int var26 = accumField(var14, var24, (boolean)var25, 2);
            int var27 = var23 + var26;
            byte var28 = 1;
            byte var29;
            if(var27 > 0) {
               var29 = 1;
            } else {
               var29 = 0;
            }

            int var30 = accumField(var11, var28, (boolean)var29, 2);
            int var31 = var27 + var30;
            byte var32 = 2;
            byte var33 = 1;
            byte var34;
            if(var31 > 0) {
               var34 = 3;
            } else {
               var34 = 0;
            }

            int var35 = accumField(var10, var32, (boolean)var33, var34) + 1;

            for(int var36 = var31 + var35; var36 < var2; ++var36) {
               var5[var18] = 32;
               ++var18;
            }
         }

         var5[var18] = (char)var9;
         int var38 = var18 + 1;
         boolean var40;
         if(var2 != 0) {
            var40 = true;
         } else {
            var40 = false;
         }

         int var41 = printField(var5, var12, 'd', var38, (boolean)0, 0);
         char var42 = 104;
         byte var44;
         if(var41 != var38) {
            var44 = 1;
         } else {
            var44 = 0;
         }

         byte var45;
         if(var40) {
            var45 = 2;
         } else {
            var45 = 0;
         }

         int var49 = printField(var5, var13, var42, var41, (boolean)var44, var45);
         char var50 = 109;
         byte var52;
         if(var49 != var38) {
            var52 = 1;
         } else {
            var52 = 0;
         }

         byte var53;
         if(var40) {
            var53 = 2;
         } else {
            var53 = 0;
         }

         int var57 = printField(var5, var14, var50, var49, (boolean)var52, var53);
         char var58 = 115;
         byte var60;
         if(var57 != var38) {
            var60 = 1;
         } else {
            var60 = 0;
         }

         byte var61;
         if(var40) {
            var61 = 2;
         } else {
            var61 = 0;
         }

         int var65 = printField(var5, var11, var58, var57, (boolean)var60, var61);
         char var66 = 109;
         byte var67 = 1;
         byte var69;
         if(var40 && var65 != var38) {
            var69 = 3;
         } else {
            var69 = 0;
         }

         int var73 = printField(var5, var10, var66, var65, (boolean)var67, var69);
         var5[var73] = 115;
         var8 = var73 + 1;
      }

      return var8;
   }

   private static int printField(char[] var0, int var1, char var2, int var3, boolean var4, int var5) {
      if(var4 || var1 > 0) {
         if(var4 && var5 >= 3 || var1 > 99) {
            int var7 = var1 / 100;
            char var8 = (char)(var7 + 48);
            var0[var3] = var8;
            ++var3;
            int var9 = var7 * 100;
            var1 -= var9;
         }

         if(var4 && var5 >= 2 || var1 > 9 || var3 != var3) {
            int var10 = var1 / 10;
            char var11 = (char)(var10 + 48);
            var0[var3] = var11;
            ++var3;
            int var12 = var10 * 10;
            var1 -= var12;
         }

         char var13 = (char)(var1 + 48);
         var0[var3] = var13;
         int var14 = var3 + 1;
         var0[var14] = var2;
         var3 = var14 + 1;
      }

      return var3;
   }
}
