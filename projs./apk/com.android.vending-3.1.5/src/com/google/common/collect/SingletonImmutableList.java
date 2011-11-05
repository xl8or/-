package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class SingletonImmutableList<E extends Object> extends ImmutableList<E> {

   final transient E element;


   SingletonImmutableList(E var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      this.element = var2;
   }

   public boolean contains(@Nullable Object var1) {
      return this.element.equals(var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(var1 instanceof List) {
            List var3 = (List)var1;
            if(var3.size() == 1) {
               Object var4 = this.element;
               Object var5 = var3.get(0);
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

   public E get(int var1) {
      int var2 = Preconditions.checkElementIndex(var1, 1);
      return this.element;
   }

   public int hashCode() {
      return this.element.hashCode() + 31;
   }

   public int indexOf(@Nullable Object var1) {
      byte var2;
      if(this.element.equals(var1)) {
         var2 = 0;
      } else {
         var2 = -1;
      }

      return var2;
   }

   public boolean isEmpty() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      return Iterators.singletonIterator(this.element);
   }

   public int lastIndexOf(@Nullable Object var1) {
      byte var2;
      if(this.element.equals(var1)) {
         var2 = 0;
      } else {
         var2 = -1;
      }

      return var2;
   }

   public ListIterator<E> listIterator() {
      return this.listIterator(0);
   }

   public ListIterator<E> listIterator(int var1) {
      return Collections.singletonList(this.element).listIterator(var1);
   }

   public int size() {
      return 1;
   }

   public ImmutableList<E> subList(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, 1);
      if(var1 == var2) {
         this = ImmutableList.of();
      }

      return (ImmutableList)this;
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
}
