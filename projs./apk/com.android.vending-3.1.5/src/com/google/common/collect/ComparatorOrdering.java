package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class ComparatorOrdering<T extends Object> extends Ordering<T> implements Serializable {

   private static final long serialVersionUID;
   final Comparator<T> comparator;


   ComparatorOrdering(Comparator<T> var1) {
      Comparator var2 = (Comparator)Preconditions.checkNotNull(var1);
      this.comparator = var2;
   }

   public int binarySearch(List<? extends T> var1, T var2) {
      Comparator var3 = this.comparator;
      return Collections.binarySearch(var1, var2, var3);
   }

   public int compare(T var1, T var2) {
      return this.comparator.compare(var1, var2);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof ComparatorOrdering) {
         ComparatorOrdering var3 = (ComparatorOrdering)var1;
         Comparator var4 = this.comparator;
         Comparator var5 = var3.comparator;
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.comparator.hashCode();
   }

   public <E extends T> List<E> sortedCopy(Iterable<E> var1) {
      ArrayList var2 = Lists.newArrayList(var1);
      Comparator var3 = this.comparator;
      Collections.sort(var2, var3);
      return var2;
   }

   public String toString() {
      return this.comparator.toString();
   }
}
