package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class SingletonImmutableSet<E extends Object> extends ImmutableSet<E> {

   private transient Integer cachedHashCode;
   final transient E element;


   SingletonImmutableSet(E var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      this.element = var2;
   }

   SingletonImmutableSet(E var1, int var2) {
      this.element = var1;
      Integer var3 = Integer.valueOf(var2);
      this.cachedHashCode = var3;
   }

   public boolean contains(Object var1) {
      return this.element.equals(var1);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(var1 instanceof Set) {
            Set var3 = (Set)var1;
            if(var3.size() == 1) {
               Object var4 = this.element;
               Object var5 = var3.iterator().next();
               if(var4.equals(var5)) {
                  return var2;
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public final int hashCode() {
      Integer var1 = this.cachedHashCode;
      int var3;
      if(var1 == null) {
         Integer var2 = Integer.valueOf(this.element.hashCode());
         this.cachedHashCode = var2;
         var3 = var2.intValue();
      } else {
         var3 = var1.intValue();
      }

      return var3;
   }

   public boolean isEmpty() {
      return false;
   }

   boolean isHashCodeFast() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      return Iterators.singletonIterator(this.element);
   }

   public int size() {
      return 1;
   }

   public Object[] toArray() {
      Object[] var1 = new Object[1];
      Object var2 = this.element;
      var1[0] = var2;
      return var1;
   }

   public <T extends Object> T[] toArray(T[] var1) {
      if(var1.length == 0) {
         var1 = ObjectArrays.newArray(var1, 1);
      } else if(var1.length > 1) {
         var1[1] = false;
      }

      Object var3 = this.element;
      var1[0] = var3;
      return var1;
   }

   public String toString() {
      String var1 = this.element.toString();
      int var2 = var1.length() + 2;
      return (new StringBuilder(var2)).append('[').append(var1).append(']').toString();
   }
}
