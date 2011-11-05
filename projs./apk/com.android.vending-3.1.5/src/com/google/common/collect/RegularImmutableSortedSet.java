package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedAsList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class RegularImmutableSortedSet<E extends Object> extends ImmutableSortedSet<E> {

   private final Object[] elements;
   private final int fromIndex;
   private final int toIndex;


   RegularImmutableSortedSet(Object[] var1, Comparator<? super E> var2) {
      super(var2);
      this.elements = var1;
      this.fromIndex = 0;
      int var3 = var1.length;
      this.toIndex = var3;
   }

   RegularImmutableSortedSet(Object[] var1, Comparator<? super E> var2, int var3, int var4) {
      super(var2);
      this.elements = var1;
      this.fromIndex = var3;
      this.toIndex = var4;
   }

   private int binarySearch(Object var1) {
      int var2 = this.fromIndex;
      int var3 = this.toIndex + -1;

      int var5;
      while(true) {
         if(var2 > var3) {
            var5 = -var2 + -1;
            break;
         }

         int var4 = (var3 - var2) / 2;
         var5 = var2 + var4;
         Object var6 = this.elements[var5];
         int var7 = this.unsafeCompare(var1, var6);
         if(var7 < 0) {
            var3 = var5 + -1;
         } else {
            if(var7 <= 0) {
               break;
            }

            var2 = var5 + 1;
         }
      }

      return var5;
   }

   private ImmutableSortedSet<E> createSubset(int var1, int var2) {
      Object var5;
      if(var1 < var2) {
         Object[] var3 = this.elements;
         Comparator var4 = this.comparator;
         var5 = new RegularImmutableSortedSet(var3, var4, var1, var2);
      } else {
         var5 = emptySet(this.comparator);
      }

      return (ImmutableSortedSet)var5;
   }

   private int findSubsetIndex(E var1) {
      int var2 = this.binarySearch(var1);
      if(var2 < 0) {
         var2 = -var2 + -1;
      }

      return var2;
   }

   public boolean contains(Object var1) {
      boolean var2 = false;
      if(var1 != null) {
         int var3;
         try {
            var3 = this.binarySearch(var1);
         } catch (ClassCastException var5) {
            return var2;
         }

         if(var3 >= 0) {
            var2 = true;
         }
      }

      return var2;
   }

   public boolean containsAll(Collection<?> var1) {
      byte var2 = 0;
      Comparator var3 = this.comparator();
      if(hasSameComparator(var1, var3) && var1.size() > 1) {
         int var4 = this.fromIndex;
         Iterator var5 = var1.iterator();
         Object var6 = var5.next();

         while(true) {
            int var7 = this.toIndex;
            if(var4 >= var7) {
               break;
            }

            Object var8 = this.elements[var4];
            int var9 = this.unsafeCompare(var8, var6);
            if(var9 < 0) {
               ++var4;
            } else if(var9 == 0) {
               if(!var5.hasNext()) {
                  var2 = 1;
                  break;
               }

               var6 = var5.next();
               ++var4;
            } else if(var9 > 0) {
               break;
            }
         }
      } else {
         var2 = super.containsAll(var1);
      }

      return (boolean)var2;
   }

   ImmutableList<E> createAsList() {
      Object[] var1 = this.elements;
      int var2 = this.fromIndex;
      int var3 = this.size();
      return new ImmutableSortedAsList(var1, var2, var3, this);
   }

   public boolean equals(@Nullable Object param1) {
      // $FF: Couldn't be decompiled
   }

   public E first() {
      Object[] var1 = this.elements;
      int var2 = this.fromIndex;
      return var1[var2];
   }

   boolean hasPartialArray() {
      boolean var3;
      if(this.fromIndex == 0) {
         int var1 = this.toIndex;
         int var2 = this.elements.length;
         if(var1 == var2) {
            var3 = false;
            return var3;
         }
      }

      var3 = true;
      return var3;
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = this.fromIndex;

      while(true) {
         int var3 = this.toIndex;
         if(var2 >= var3) {
            return var1;
         }

         int var4 = this.elements[var2].hashCode();
         var1 += var4;
         ++var2;
      }
   }

   ImmutableSortedSet<E> headSetImpl(E var1) {
      int var2 = this.fromIndex;
      int var3 = this.findSubsetIndex(var1);
      return this.createSubset(var2, var3);
   }

   int indexOf(Object var1) {
      int var2 = -1;
      if(var1 != null) {
         int var3;
         try {
            var3 = this.binarySearch(var1);
         } catch (ClassCastException var7) {
            return var2;
         }

         if(var3 >= 0 && this.elements[var3].equals(var1)) {
            int var5 = this.fromIndex;
            var2 = var3 - var5;
         }
      }

      return var2;
   }

   public boolean isEmpty() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      Object[] var1 = this.elements;
      int var2 = this.fromIndex;
      int var3 = this.size();
      return Iterators.forArray(var1, var2, var3);
   }

   public E last() {
      Object[] var1 = this.elements;
      int var2 = this.toIndex + -1;
      return var1[var2];
   }

   public int size() {
      int var1 = this.toIndex;
      int var2 = this.fromIndex;
      return var1 - var2;
   }

   ImmutableSortedSet<E> subSetImpl(E var1, E var2) {
      int var3 = this.findSubsetIndex(var1);
      int var4 = this.findSubsetIndex(var2);
      return this.createSubset(var3, var4);
   }

   ImmutableSortedSet<E> tailSetImpl(E var1) {
      int var2 = this.findSubsetIndex(var1);
      int var3 = this.toIndex;
      return this.createSubset(var2, var3);
   }

   public Object[] toArray() {
      Object[] var1 = new Object[this.size()];
      Object[] var2 = this.elements;
      int var3 = this.fromIndex;
      int var4 = this.size();
      Platform.unsafeArrayCopy(var2, var3, var1, 0, var4);
      return var1;
   }

   public <T extends Object> T[] toArray(T[] var1) {
      int var2 = this.size();
      if(var1.length < var2) {
         var1 = ObjectArrays.newArray(var1, var2);
      } else if(var1.length > var2) {
         var1[var2] = false;
      }

      Object[] var3 = this.elements;
      int var4 = this.fromIndex;
      Platform.unsafeArrayCopy(var3, var4, var1, 0, var2);
      return var1;
   }
}
