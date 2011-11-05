package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;

@GwtCompatible
public final class SignedBytes {

   private SignedBytes() {}

   public static byte checkedCast(long var0) {
      byte var2 = (byte)((int)var0);
      byte var3;
      if((long)var2 == var0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Object[] var4 = new Object[1];
      Long var5 = Long.valueOf(var0);
      var4[0] = var5;
      Preconditions.checkArgument((boolean)var3, "Out of range: %s", var4);
      return var2;
   }

   public static int compare(byte var0, byte var1) {
      return var0 - var1;
   }

   public static String join(String var0, byte ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      String var3;
      if(var1.length == 0) {
         var3 = "";
      } else {
         int var4 = var1.length * 5;
         StringBuilder var5 = new StringBuilder(var4);
         byte var6 = var1[0];
         var5.append(var6);
         int var8 = 1;

         while(true) {
            int var9 = var1.length;
            if(var8 >= var9) {
               var3 = var5.toString();
               break;
            }

            StringBuilder var10 = var5.append(var0);
            byte var11 = var1[var8];
            var10.append(var11);
            ++var8;
         }
      }

      return var3;
   }

   public static Comparator<byte[]> lexicographicalComparator() {
      return SignedBytes.LexicographicalComparator.INSTANCE;
   }

   public static byte max(byte ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      byte var2 = var0[0];
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         if(var0[var3] > var2) {
            var2 = var0[var3];
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
      byte var2 = var0[0];
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         if(var0[var3] < var2) {
            var2 = var0[var3];
         }

         ++var3;
      }
   }

   public static byte saturatedCast(long var0) {
      int var2;
      if(var0 > 127L) {
         var2 = 127;
      } else if(var0 < 65408L) {
         var2 = '\uff80';
      } else {
         var2 = (byte)((int)var0);
      }

      return (byte)var2;
   }

   private static enum LexicographicalComparator implements Comparator<byte[]> {

      // $FF: synthetic field
      private static final SignedBytes.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         SignedBytes.LexicographicalComparator[] var0 = new SignedBytes.LexicographicalComparator[1];
         SignedBytes.LexicographicalComparator var1 = INSTANCE;
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
            var9 = SignedBytes.compare(var7, var8);
            if(var9 != 0) {
               break;
            }

            ++var6;
         }

         return var9;
      }
   }
}
