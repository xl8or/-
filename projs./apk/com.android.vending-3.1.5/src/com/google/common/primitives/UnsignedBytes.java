package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.util.Comparator;

public final class UnsignedBytes {

   private UnsignedBytes() {}

   public static byte checkedCast(long var0) {
      byte var2;
      if(var0 >> 8 == 0L) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      Object[] var3 = new Object[1];
      Long var4 = Long.valueOf(var0);
      var3[0] = var4;
      Preconditions.checkArgument((boolean)var2, "out of range: %s", var3);
      return (byte)((int)var0);
   }

   public static int compare(byte var0, byte var1) {
      int var2 = var0 & 255;
      int var3 = var1 & 255;
      return var2 - var3;
   }

   public static String join(String var0, byte ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      String var3;
      if(var1.length == 0) {
         var3 = "";
      } else {
         int var4 = var1.length * 5;
         StringBuilder var5 = new StringBuilder(var4);
         int var6 = var1[0] & 255;
         var5.append(var6);
         int var8 = 1;

         while(true) {
            int var9 = var1.length;
            if(var8 >= var9) {
               var3 = var5.toString();
               break;
            }

            StringBuilder var10 = var5.append(var0);
            int var11 = var1[var8] & 255;
            var10.append(var11);
            ++var8;
         }
      }

      return var3;
   }

   public static Comparator<byte[]> lexicographicalComparator() {
      return UnsignedBytes.LexicographicalComparator.INSTANCE;
   }

   public static byte max(byte ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      int var2 = var0[0] & 255;
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return (byte)var2;
         }

         int var5 = var0[var3] & 255;
         if(var5 > var2) {
            var2 = var5;
         }

         ++var3;
      }
   }

   public static byte min(byte ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      int var2 = var0[0] & 255;
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return (byte)var2;
         }

         int var5 = var0[var3] & 255;
         if(var5 < var2) {
            var2 = var5;
         }

         ++var3;
      }
   }

   public static byte saturatedCast(long var0) {
      byte var2;
      if(var0 > 255L) {
         var2 = -1;
      } else if(var0 < 0L) {
         var2 = 0;
      } else {
         var2 = (byte)((int)var0);
      }

      return var2;
   }

   private static enum LexicographicalComparator implements Comparator<byte[]> {

      // $FF: synthetic field
      private static final UnsignedBytes.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         UnsignedBytes.LexicographicalComparator[] var0 = new UnsignedBytes.LexicographicalComparator[1];
         UnsignedBytes.LexicographicalComparator var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private LexicographicalComparator(String var1, int var2) {}

      public int compare(byte[] var1, byte[] var2) {
         int var3 = var1.length;
         int var4 = var2.length;
         int var5 = Math.min(var3, var4);
         int var6 = 0;

         int var9;
         while(true) {
            if(var6 >= var5) {
               int var10 = var1.length;
               int var11 = var2.length;
               var9 = var10 - var11;
               break;
            }

            byte var7 = var1[var6];
            byte var8 = var2[var6];
            var9 = UnsignedBytes.compare(var7, var8);
            if(var9 != 0) {
               break;
            }

            ++var6;
         }

         return var9;
      }
   }
}
