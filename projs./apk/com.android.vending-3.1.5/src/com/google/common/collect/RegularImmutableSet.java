package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableSet;

@GwtCompatible(
   serializable = true
)
final class RegularImmutableSet<E extends Object> extends ImmutableSet.ArrayImmutableSet<E> {

   private final transient int hashCode;
   private final transient int mask;
   @VisibleForTesting
   final transient Object[] table;


   RegularImmutableSet(Object[] var1, int var2, Object[] var3, int var4) {
      super(var1);
      this.table = var3;
      this.mask = var4;
      this.hashCode = var2;
   }

   public boolean contains(Object var1) {
      boolean var2 = false;
      if(var1 != null) {
         int var3 = Hashing.smear(var1.hashCode());

         while(true) {
            Object[] var4 = this.table;
            int var5 = this.mask & var3;
            Object var6 = var4[var5];
            if(var6 == null) {
               break;
            }

            if(var6.equals(var1)) {
               var2 = true;
               break;
            }

            ++var3;
         }
      }

      return var2;
   }

   public int hashCode() {
      return this.hashCode;
   }

   boolean isHashCodeFast() {
      return true;
   }
}
