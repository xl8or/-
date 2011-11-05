package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.Ordering;
import java.io.Serializable;

@GwtCompatible(
   serializable = true
)
final class ReverseNaturalOrdering extends Ordering<Comparable> implements Serializable {

   static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
   private static final long serialVersionUID;


   private ReverseNaturalOrdering() {}

   private Object readResolve() {
      return INSTANCE;
   }

   public int compare(Comparable var1, Comparable var2) {
      Object var3 = Preconditions.checkNotNull(var1);
      int var4;
      if(var1 == var2) {
         var4 = 0;
      } else {
         var4 = var2.compareTo(var1);
      }

      return var4;
   }

   public <E extends Object & Comparable> E max(E var1, E var2) {
      return (Comparable)NaturalOrdering.INSTANCE.min(var1, var2);
   }

   public <E extends Object & Comparable> E max(E var1, E var2, E var3, E ... var4) {
      return (Comparable)NaturalOrdering.INSTANCE.min(var1, var2, var3, var4);
   }

   public <E extends Object & Comparable> E max(Iterable<E> var1) {
      return (Comparable)NaturalOrdering.INSTANCE.min(var1);
   }

   public <E extends Object & Comparable> E min(E var1, E var2) {
      return (Comparable)NaturalOrdering.INSTANCE.max(var1, var2);
   }

   public <E extends Object & Comparable> E min(E var1, E var2, E var3, E ... var4) {
      return (Comparable)NaturalOrdering.INSTANCE.max(var1, var2, var3, var4);
   }

   public <E extends Object & Comparable> E min(Iterable<E> var1) {
      return (Comparable)NaturalOrdering.INSTANCE.max(var1);
   }

   public <S extends Object & Comparable> Ordering<S> reverse() {
      return Ordering.natural();
   }

   public String toString() {
      return "Ordering.natural().reverse()";
   }
}
