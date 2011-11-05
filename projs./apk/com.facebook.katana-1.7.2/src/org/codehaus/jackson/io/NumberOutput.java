package org.codehaus.jackson.io;


public final class NumberOutput {

   private static int BILLION = 1000000000;
   static final char[] FULL_TRIPLETS = new char[4000];
   static final char[] LEADING_TRIPLETS = new char[4000];
   private static long MAX_INT_AS_LONG = 2147483647L;
   private static int MILLION = 1000000;
   private static long MIN_INT_AS_LONG = -2147483648L;
   private static final char NULL_CHAR;
   static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
   private static long TEN_BILLION_L = 10000000000L;
   private static long THOUSAND_L = 1000L;
   static final String[] sSmallIntStrs;
   static final String[] sSmallIntStrs2;


   static {
      int var0 = 0;

      int var4;
      for(int var1 = 0; var0 < 10; var1 = var4) {
         char var2 = (char)(var0 + 48);
         char var3;
         if(var0 == 0) {
            var3 = 0;
         } else {
            var3 = var2;
         }

         var4 = var1;

         int var8;
         for(int var5 = 0; var5 < 10; var4 = var8) {
            char var6 = (char)(var5 + 48);
            char var7;
            if(var0 == 0 && var5 == 0) {
               var7 = 0;
            } else {
               var7 = var6;
            }

            var8 = var4;

            for(int var9 = 0; var9 < 10; ++var9) {
               char var10 = (char)(var9 + 48);
               LEADING_TRIPLETS[var8] = var3;
               char[] var11 = LEADING_TRIPLETS;
               int var12 = var8 + 1;
               var11[var12] = var7;
               char[] var13 = LEADING_TRIPLETS;
               int var14 = var8 + 2;
               var13[var14] = var10;
               FULL_TRIPLETS[var8] = var2;
               char[] var15 = FULL_TRIPLETS;
               int var16 = var8 + 1;
               var15[var16] = var6;
               char[] var17 = FULL_TRIPLETS;
               int var18 = var8 + 2;
               var17[var18] = var10;
               var8 += 4;
            }

            ++var5;
         }

         ++var0;
      }

      String[] var19 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
      sSmallIntStrs = var19;
      String[] var20 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
      sSmallIntStrs2 = var20;
   }

   public NumberOutput() {}

   private static int calcLongStrLength(long var0) {
      long var2 = TEN_BILLION_L;
      int var4 = 10;

      long var7;
      for(long var5 = var2; var0 >= var5 && var4 != 19; var5 = (var5 << 1) + var7) {
         ++var4;
         var7 = var5 << 3;
      }

      return var4;
   }

   private static int outputFullTriplet(int var0, char[] var1, int var2) {
      int var3 = var0 << 2;
      int var4 = var2 + 1;
      char[] var5 = FULL_TRIPLETS;
      int var6 = var3 + 1;
      char var7 = var5[var3];
      var1[var2] = var7;
      int var8 = var4 + 1;
      char[] var9 = FULL_TRIPLETS;
      int var10 = var6 + 1;
      char var11 = var9[var6];
      var1[var4] = var11;
      int var12 = var8 + 1;
      char var13 = FULL_TRIPLETS[var10];
      var1[var8] = var13;
      return var12;
   }

   public static int outputInt(int var0, char[] var1, int var2) {
      int var3;
      int var4;
      int var5;
      if(var0 < 0) {
         if(var0 == Integer.MIN_VALUE) {
            var3 = outputLong((long)var0, var1, var2);
            return var3;
         }

         var4 = var2 + 1;
         var1[var2] = 45;
         var5 = -var0;
      } else {
         var4 = var2;
         var5 = var0;
      }

      int var6 = MILLION;
      if(var5 < var6) {
         if(var5 < 1000) {
            if(var5 < 10) {
               int var7 = var4 + 1;
               char var8 = (char)(var5 + 48);
               var1[var4] = var8;
               var3 = var7;
            } else {
               var3 = outputLeadingTriplet(var5, var1, var4);
            }
         } else {
            int var9 = var5 / 1000;
            int var10 = var9 * 1000;
            int var11 = var5 - var10;
            int var12 = outputLeadingTriplet(var9, var1, var4);
            var3 = outputFullTriplet(var11, var1, var12);
         }
      } else {
         int var13 = BILLION;
         boolean var14;
         if(var5 >= var13) {
            var14 = true;
         } else {
            var14 = false;
         }

         if(var14) {
            int var15 = BILLION;
            var5 -= var15;
            int var16 = BILLION;
            if(var5 >= var16) {
               int var17 = BILLION;
               var5 -= var17;
               int var18 = var4 + 1;
               var1[var4] = 50;
               var4 = var18;
            } else {
               int var26 = var4 + 1;
               var1[var4] = 49;
               var4 = var26;
            }
         }

         int var19 = var5 / 1000;
         int var20 = var19 * 1000;
         var5 -= var20;
         int var21 = var19 / 1000;
         int var22 = var21 * 1000;
         int var23 = var19 - var22;
         int var24;
         if(var14) {
            var24 = outputFullTriplet(var21, var1, var4);
         } else {
            var24 = outputLeadingTriplet(var21, var1, var4);
         }

         int var25 = outputFullTriplet(var23, var1, var24);
         var3 = outputFullTriplet(var5, var1, var25);
      }

      return var3;
   }

   private static int outputLeadingTriplet(int var0, char[] var1, int var2) {
      int var3 = var0 << 2;
      char[] var4 = LEADING_TRIPLETS;
      int var5 = var3 + 1;
      char var6 = var4[var3];
      int var8;
      if(var6 != 0) {
         int var7 = var2 + 1;
         var1[var2] = var6;
         var8 = var7;
      } else {
         var8 = var2;
      }

      char[] var9 = LEADING_TRIPLETS;
      int var10 = var5 + 1;
      char var11 = var9[var5];
      if(var11 != 0) {
         int var12 = var8 + 1;
         var1[var8] = var11;
         var8 = var12;
      }

      int var13 = var8 + 1;
      char var14 = LEADING_TRIPLETS[var10];
      var1[var8] = var14;
      return var13;
   }

   public static int outputLong(long var0, char[] var2, int var3) {
      int var6;
      int var8;
      long var9;
      if(var0 < 0L) {
         long var4 = MIN_INT_AS_LONG;
         if(var0 > var4) {
            var6 = outputInt((int)var0, var2, var3);
            return var6;
         }

         if(var0 == Long.MIN_VALUE) {
            int var7 = SMALLEST_LONG.length();
            SMALLEST_LONG.getChars(0, var7, var2, var3);
            var6 = var7 + var3;
            return var6;
         }

         var8 = var3 + 1;
         var2[var3] = 45;
         var9 = -var0;
      } else {
         long var24 = MAX_INT_AS_LONG;
         if(var0 <= var24) {
            var6 = outputInt((int)var0, var2, var3);
            return var6;
         }

         var8 = var3;
         var9 = var0;
      }

      int var11 = calcLongStrLength(var9) + var8;
      long var12 = var9;
      int var14 = var11;

      while(true) {
         long var15 = MAX_INT_AS_LONG;
         if(var12 <= var15) {
            int var26 = (int)var12;
            int var27 = var14;
            int var28 = var26;

            while(var28 >= 1000) {
               int var29 = var27 + -3;
               int var30 = var28 / 1000;
               int var31 = var30 * 1000;
               outputFullTriplet(var28 - var31, var2, var29);
            }

            outputLeadingTriplet(var28, var2, var8);
            var6 = var11;
            return var6;
         }

         var14 += -3;
         long var17 = THOUSAND_L;
         long var19 = var12 / var17;
         long var21 = THOUSAND_L * var19;
         outputFullTriplet((int)(var12 - var21), var2, var14);
         var12 = var19;
      }
   }

   public static String toString(double var0) {
      return Double.toString(var0);
   }

   public static String toString(int var0) {
      int var1 = sSmallIntStrs.length;
      String var4;
      if(var0 < var1) {
         if(var0 >= 0) {
            var4 = sSmallIntStrs[var0];
            return var4;
         }

         int var2 = -var0 - 1;
         int var3 = sSmallIntStrs2.length;
         if(var2 < var3) {
            var4 = sSmallIntStrs2[var2];
            return var4;
         }
      }

      var4 = Integer.toString(var0);
      return var4;
   }

   public static String toString(long var0) {
      String var2;
      if(var0 <= 2147483647L && var0 >= -2147483648L) {
         var2 = toString((int)var0);
      } else {
         var2 = Long.toString(var0);
      }

      return var2;
   }
}
