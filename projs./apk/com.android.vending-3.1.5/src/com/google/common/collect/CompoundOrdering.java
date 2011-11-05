package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@GwtCompatible(
   serializable = true
)
final class CompoundOrdering<T extends Object> extends Ordering<T> implements Serializable {

   private static final long serialVersionUID;
   final ImmutableList<Comparator<? super T>> comparators;


   CompoundOrdering(Iterable<? extends Comparator<? super T>> var1) {
      ImmutableList var2 = ImmutableList.copyOf(var1);
      this.comparators = var2;
   }

   CompoundOrdering(Comparator<? super T> var1, Comparator<? super T> var2) {
      ImmutableList var3 = ImmutableList.of(var1, var2);
      this.comparators = var3;
   }

   CompoundOrdering(List<? extends Comparator<? super T>> var1, Comparator<? super T> var2) {
      ImmutableList var3 = (new ImmutableList.Builder()).addAll((Iterable)var1).add((Object)var2).build();
      this.comparators = var3;
   }

   public int compare(T var1, T var2) {
      Iterator var3 = this.comparators.iterator();

      int var4;
      do {
         if(!var3.hasNext()) {
            var4 = 0;
            break;
         }

         var4 = ((Comparator)var3.next()).compare(var1, var2);
      } while(var4 == 0);

      return var4;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof CompoundOrdering) {
         CompoundOrdering var3 = (CompoundOrdering)var1;
         ImmutableList var4 = this.comparators;
         ImmutableList var5 = var3.comparators;
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.comparators.hashCode();
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("Ordering.compound(");
      ImmutableList var2 = this.comparators;
      return var1.append(var2).append(")").toString();
   }
}
