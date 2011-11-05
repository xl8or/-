package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class ReverseOrdering<T extends Object> extends Ordering<T> implements Serializable {

   private static final long serialVersionUID;
   final Ordering<? super T> forwardOrder;


   ReverseOrdering(Ordering<? super T> var1) {
      Ordering var2 = (Ordering)Preconditions.checkNotNull(var1);
      this.forwardOrder = var2;
   }

   public int compare(T var1, T var2) {
      return this.forwardOrder.compare(var2, var1);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof ReverseOrdering) {
         ReverseOrdering var3 = (ReverseOrdering)var1;
         Ordering var4 = this.forwardOrder;
         Ordering var5 = var3.forwardOrder;
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return -this.forwardOrder.hashCode();
   }

   public <E extends T> E max(Iterable<E> var1) {
      return this.forwardOrder.min(var1);
   }

   public <E extends T> E max(E var1, E var2) {
      return this.forwardOrder.min(var1, var2);
   }

   public <E extends T> E max(E var1, E var2, E var3, E ... var4) {
      return this.forwardOrder.min(var1, var2, var3, var4);
   }

   public <E extends T> E min(Iterable<E> var1) {
      return this.forwardOrder.max(var1);
   }

   public <E extends T> E min(E var1, E var2) {
      return this.forwardOrder.max(var1, var2);
   }

   public <E extends T> E min(E var1, E var2, E var3, E ... var4) {
      return this.forwardOrder.max(var1, var2, var3, var4);
   }

   public <S extends T> Ordering<S> reverse() {
      return this.forwardOrder;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Ordering var2 = this.forwardOrder;
      return var1.append(var2).append(".reverse()").toString();
   }
}
