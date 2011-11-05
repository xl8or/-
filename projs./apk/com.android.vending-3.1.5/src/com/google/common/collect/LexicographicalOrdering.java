package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class LexicographicalOrdering<T extends Object> extends Ordering<Iterable<T>> implements Serializable {

   private static final long serialVersionUID;
   final Ordering<? super T> elementOrder;


   LexicographicalOrdering(Ordering<? super T> var1) {
      this.elementOrder = var1;
   }

   public int compare(Iterable<T> var1, Iterable<T> var2) {
      Iterator var3 = var1.iterator();
      Iterator var4 = var2.iterator();

      int var5;
      do {
         if(!var3.hasNext()) {
            if(var4.hasNext()) {
               var5 = -1;
            } else {
               var5 = 0;
            }
            break;
         }

         if(!var4.hasNext()) {
            var5 = 1;
            break;
         }

         Ordering var6 = this.elementOrder;
         Object var7 = var3.next();
         Object var8 = var4.next();
         var5 = var6.compare(var7, var8);
      } while(var5 == 0);

      return var5;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof LexicographicalOrdering) {
         LexicographicalOrdering var3 = (LexicographicalOrdering)var1;
         Ordering var4 = this.elementOrder;
         Ordering var5 = var3.elementOrder;
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.elementOrder.hashCode() ^ 2075626741;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Ordering var2 = this.elementOrder;
      return var1.append(var2).append(".lexicographical()").toString();
   }
}
