package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class NullsFirstOrdering<T extends Object> extends Ordering<T> implements Serializable {

   private static final long serialVersionUID;
   final Ordering<? super T> ordering;


   NullsFirstOrdering(Ordering<? super T> var1) {
      this.ordering = var1;
   }

   public int compare(T var1, T var2) {
      int var3;
      if(var1 == var2) {
         var3 = 0;
      } else if(var1 == null) {
         var3 = -1;
      } else if(var2 == null) {
         var3 = 1;
      } else {
         var3 = this.ordering.compare(var1, var2);
      }

      return var3;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof NullsFirstOrdering) {
         NullsFirstOrdering var3 = (NullsFirstOrdering)var1;
         Ordering var4 = this.ordering;
         Ordering var5 = var3.ordering;
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.ordering.hashCode() ^ 957692532;
   }

   public <S extends T> Ordering<S> nullsFirst() {
      return this;
   }

   public <S extends T> Ordering<S> nullsLast() {
      return this.ordering.nullsLast();
   }

   public <S extends T> Ordering<S> reverse() {
      return this.ordering.reverse().nullsLast();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Ordering var2 = this.ordering;
      return var1.append(var2).append(".nullsFirst()").toString();
   }
}
