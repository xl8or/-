package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.RegularImmutableSortedSet;
import java.util.Comparator;

final class ImmutableSortedAsList<E extends Object> extends RegularImmutableList<E> {

   private final transient ImmutableSortedSet<E> set;


   ImmutableSortedAsList(Object[] var1, int var2, int var3, ImmutableSortedSet<E> var4) {
      super(var1, var2, var3);
      this.set = var4;
   }

   public boolean contains(Object var1) {
      boolean var2;
      if(this.set.indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int indexOf(Object var1) {
      return this.set.indexOf(var1);
   }

   public int lastIndexOf(Object var1) {
      return this.set.indexOf(var1);
   }

   public ImmutableList<E> subList(int var1, int var2) {
      int var3 = this.size();
      Preconditions.checkPositionIndexes(var1, var2, var3);
      ImmutableList var4;
      if(var1 == var2) {
         var4 = ImmutableList.of();
      } else {
         Object[] var5 = this.array();
         Comparator var6 = this.set.comparator();
         int var7 = this.offset() + var1;
         int var8 = this.offset() + var2;
         var4 = (new RegularImmutableSortedSet(var5, var6, var7, var8)).asList();
      }

      return var4;
   }

   Object writeReplace() {
      ImmutableSortedSet var1 = this.set;
      return new ImmutableAsList.SerializedForm(var1);
   }
}
