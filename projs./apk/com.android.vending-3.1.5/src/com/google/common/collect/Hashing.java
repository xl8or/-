package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@GwtCompatible
final class Hashing {

   private static final int CUTOFF = 536870912;
   private static final int MAX_TABLE_SIZE = 1073741824;


   private Hashing() {}

   static int chooseTableSize(int var0) {
      int var1;
      if(var0 < 536870912) {
         var1 = Integer.highestOneBit(var0) << 2;
      } else {
         byte var2;
         if(var0 < 1073741824) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         Preconditions.checkArgument((boolean)var2, "collection too large");
         var1 = 1073741824;
      }

      return var1;
   }

   static int smear(int var0) {
      int var1 = var0 >>> 20;
      int var2 = var0 >>> 12;
      int var3 = var1 ^ var2;
      int var4 = var0 ^ var3;
      int var5 = var4 >>> 7 ^ var4;
      int var6 = var4 >>> 4;
      return var5 ^ var6;
   }
}
