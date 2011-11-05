package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.ReverseNaturalOrdering;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@GwtCompatible(
   serializable = true
)
final class NaturalOrdering extends Ordering<Comparable> implements Serializable {

   static final NaturalOrdering INSTANCE = new NaturalOrdering();
   private static final long serialVersionUID;


   private NaturalOrdering() {}

   private Object readResolve() {
      return INSTANCE;
   }

   public int binarySearch(List<? extends Comparable> var1, Comparable var2) {
      return Collections.binarySearch(var1, var2);
   }

   public int compare(Comparable var1, Comparable var2) {
      Object var3 = Preconditions.checkNotNull(var2);
      int var4;
      if(var1 == var2) {
         var4 = 0;
      } else {
         var4 = var1.compareTo(var2);
      }

      return var4;
   }

   public <S extends Object & Comparable> Ordering<S> reverse() {
      return ReverseNaturalOrdering.INSTANCE;
   }

   public <E extends Object & Comparable> List<E> sortedCopy(Iterable<E> var1) {
      ArrayList var2 = Lists.newArrayList(var1);
      Collections.sort(var2);
      return var2;
   }

   public String toString() {
      return "Ordering.natural()";
   }
}
