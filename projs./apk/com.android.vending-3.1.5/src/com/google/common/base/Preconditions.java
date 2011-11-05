package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;

@GwtCompatible
public final class Preconditions {

   private Preconditions() {}

   private static String badElementIndex(int var0, int var1, String var2) {
      String var5;
      if(var0 < 0) {
         Object[] var3 = new Object[]{var2, null};
         Integer var4 = Integer.valueOf(var0);
         var3[1] = var4;
         var5 = format("%s (%s) must not be negative", var3);
      } else {
         if(var1 < 0) {
            String var6 = "negative size: " + var1;
            throw new IllegalArgumentException(var6);
         }

         Object[] var7 = new Object[]{var2, null, null};
         Integer var8 = Integer.valueOf(var0);
         var7[1] = var8;
         Integer var9 = Integer.valueOf(var1);
         var7[2] = var9;
         var5 = format("%s (%s) must be less than size (%s)", var7);
      }

      return var5;
   }

   private static String badPositionIndex(int var0, int var1, String var2) {
      String var5;
      if(var0 < 0) {
         Object[] var3 = new Object[]{var2, null};
         Integer var4 = Integer.valueOf(var0);
         var3[1] = var4;
         var5 = format("%s (%s) must not be negative", var3);
      } else {
         if(var1 < 0) {
            String var6 = "negative size: " + var1;
            throw new IllegalArgumentException(var6);
         }

         Object[] var7 = new Object[]{var2, null, null};
         Integer var8 = Integer.valueOf(var0);
         var7[1] = var8;
         Integer var9 = Integer.valueOf(var1);
         var7[2] = var9;
         var5 = format("%s (%s) must not be greater than size (%s)", var7);
      }

      return var5;
   }

   private static String badPositionIndexes(int var0, int var1, int var2) {
      String var3;
      if(var0 >= 0 && var0 <= var2) {
         if(var1 >= 0 && var1 <= var2) {
            Object[] var4 = new Object[2];
            Integer var5 = Integer.valueOf(var1);
            var4[0] = var5;
            Integer var6 = Integer.valueOf(var0);
            var4[1] = var6;
            var3 = format("end index (%s) must not be less than start index (%s)", var4);
         } else {
            var3 = badPositionIndex(var1, var2, "end index");
         }
      } else {
         var3 = badPositionIndex(var0, var2, "start index");
      }

      return var3;
   }

   public static void checkArgument(boolean var0) {
      if(!var0) {
         throw new IllegalArgumentException();
      }
   }

   public static void checkArgument(boolean var0, Object var1) {
      if(!var0) {
         String var2 = String.valueOf(var1);
         throw new IllegalArgumentException(var2);
      }
   }

   public static void checkArgument(boolean var0, String var1, Object ... var2) {
      if(!var0) {
         String var3 = format(var1, var2);
         throw new IllegalArgumentException(var3);
      }
   }

   public static int checkElementIndex(int var0, int var1) {
      return checkElementIndex(var0, var1, "index");
   }

   public static int checkElementIndex(int var0, int var1, String var2) {
      if(var0 >= 0 && var0 < var1) {
         return var0;
      } else {
         String var3 = badElementIndex(var0, var1, var2);
         throw new IndexOutOfBoundsException(var3);
      }
   }

   public static <T extends Object> T checkNotNull(T var0) {
      if(var0 == null) {
         throw new NullPointerException();
      } else {
         return var0;
      }
   }

   public static <T extends Object> T checkNotNull(T var0, Object var1) {
      if(var0 == null) {
         String var2 = String.valueOf(var1);
         throw new NullPointerException(var2);
      } else {
         return var0;
      }
   }

   public static <T extends Object> T checkNotNull(T var0, String var1, Object ... var2) {
      if(var0 == null) {
         String var3 = format(var1, var2);
         throw new NullPointerException(var3);
      } else {
         return var0;
      }
   }

   public static int checkPositionIndex(int var0, int var1) {
      return checkPositionIndex(var0, var1, "index");
   }

   public static int checkPositionIndex(int var0, int var1, String var2) {
      if(var0 >= 0 && var0 <= var1) {
         return var0;
      } else {
         String var3 = badPositionIndex(var0, var1, var2);
         throw new IndexOutOfBoundsException(var3);
      }
   }

   public static void checkPositionIndexes(int var0, int var1, int var2) {
      if(var0 < 0 || var1 < var0 || var1 > var2) {
         String var3 = badPositionIndexes(var0, var1, var2);
         throw new IndexOutOfBoundsException(var3);
      }
   }

   public static void checkState(boolean var0) {
      if(!var0) {
         throw new IllegalStateException();
      }
   }

   public static void checkState(boolean var0, Object var1) {
      if(!var0) {
         String var2 = String.valueOf(var1);
         throw new IllegalStateException(var2);
      }
   }

   public static void checkState(boolean var0, String var1, Object ... var2) {
      if(!var0) {
         String var3 = format(var1, var2);
         throw new IllegalStateException(var3);
      }
   }

   @VisibleForTesting
   static String format(String var0, Object ... var1) {
      int var2 = var0.length();
      int var3 = var1.length * 16;
      int var4 = var2 + var3;
      StringBuilder var5 = new StringBuilder(var4);
      int var6 = 0;
      int var7 = 0;

      while(true) {
         int var8 = var1.length;
         if(var7 >= var8) {
            break;
         }

         int var9 = var0.indexOf("%s", var6);
         if(var9 == -1) {
            break;
         }

         String var23 = var0.substring(var6, var9);
         var5.append(var23);
         int var25 = var7 + 1;
         Object var26 = var1[var7];
         var5.append(var26);
         var6 = var9 + 2;
         var7 = var25;
      }

      String var10 = var0.substring(var6);
      var5.append(var10);
      int var12 = var1.length;
      if(var7 < var12) {
         StringBuilder var13 = var5.append(" [");
         int var14 = var7 + 1;
         Object var15 = var1[var7];
         var5.append(var15);
         var7 = var14;

         while(true) {
            int var17 = var1.length;
            if(var7 >= var17) {
               StringBuilder var28 = var5.append("]");
               break;
            }

            StringBuilder var18 = var5.append(", ");
            int var19 = var7 + 1;
            Object var20 = var1[var7];
            var5.append(var20);
         }
      }

      return var5.toString();
   }
}
