package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
class RegularImmutableList<E extends Object> extends ImmutableList<E> {

   private final transient Object[] array;
   private final transient int offset;
   private final transient int size;


   RegularImmutableList(Object[] var1) {
      int var2 = var1.length;
      this(var1, 0, var2);
   }

   RegularImmutableList(Object[] var1, int var2, int var3) {
      this.offset = var2;
      this.size = var3;
      this.array = var1;
   }

   Object[] array() {
      return this.array;
   }

   public boolean contains(Object var1) {
      boolean var2;
      if(this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(!(var1 instanceof List)) {
            var2 = false;
         } else {
            List var3 = (List)var1;
            int var4 = this.size();
            int var5 = var3.size();
            if(var4 != var5) {
               var2 = false;
            } else {
               int var6 = this.offset;
               int var13;
               if(var1 instanceof RegularImmutableList) {
                  RegularImmutableList var7 = (RegularImmutableList)var1;
                  int var8 = var7.offset;

                  while(true) {
                     int var9 = var7.offset;
                     int var10 = var7.size;
                     int var11 = var9 + var10;
                     if(var8 >= var11) {
                        break;
                     }

                     Object[] var12 = this.array;
                     var13 = var6 + 1;
                     Object var14 = var12[var6];
                     Object var15 = var7.array[var8];
                     if(!var14.equals(var15)) {
                        var2 = false;
                        break;
                     }

                     ++var8;
                     var6 = var13;
                  }
               } else {
                  for(Iterator var16 = var3.iterator(); var16.hasNext(); var6 = var13) {
                     Object var17 = var16.next();
                     Object[] var18 = this.array;
                     var13 = var6 + 1;
                     if(!var18[var6].equals(var17)) {
                        var2 = false;
                        break;
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   public E get(int var1) {
      int var2 = this.size;
      Preconditions.checkElementIndex(var1, var2);
      Object[] var4 = this.array;
      int var5 = this.offset + var1;
      return var4[var5];
   }

   public int hashCode() {
      int var1 = 1;
      int var2 = this.offset;

      while(true) {
         int var3 = this.offset;
         int var4 = this.size;
         int var5 = var3 + var4;
         if(var2 >= var5) {
            return var1;
         }

         int var6 = var1 * 31;
         int var7 = this.array[var2].hashCode();
         var1 = var6 + var7;
         ++var2;
      }
   }

   public int indexOf(Object var1) {
      int var7;
      if(var1 != null) {
         int var2 = this.offset;

         while(true) {
            int var3 = this.offset;
            int var4 = this.size;
            int var5 = var3 + var4;
            if(var2 >= var5) {
               break;
            }

            if(this.array[var2].equals(var1)) {
               int var6 = this.offset;
               var7 = var2 - var6;
               return var7;
            }

            ++var2;
         }
      }

      var7 = -1;
      return var7;
   }

   public boolean isEmpty() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      Object[] var1 = this.array;
      int var2 = this.offset;
      int var3 = this.size;
      return Iterators.forArray(var1, var2, var3);
   }

   public int lastIndexOf(Object var1) {
      int var7;
      if(var1 != null) {
         int var2 = this.offset;
         int var3 = this.size;
         int var4 = var2 + var3 + -1;

         while(true) {
            int var5 = this.offset;
            if(var4 < var5) {
               break;
            }

            if(this.array[var4].equals(var1)) {
               int var6 = this.offset;
               var7 = var4 - var6;
               return var7;
            }

            var4 += -1;
         }
      }

      var7 = -1;
      return var7;
   }

   public ListIterator<E> listIterator() {
      return this.listIterator(0);
   }

   public ListIterator<E> listIterator(int var1) {
      int var2 = this.size;
      Preconditions.checkPositionIndex(var1, var2);
      return new RegularImmutableList.1(var1);
   }

   int offset() {
      return this.offset;
   }

   public int size() {
      return this.size;
   }

   public ImmutableList<E> subList(int var1, int var2) {
      int var3 = this.size;
      Preconditions.checkPositionIndexes(var1, var2, var3);
      Object var4;
      if(var1 == var2) {
         var4 = ImmutableList.of();
      } else {
         Object[] var5 = this.array;
         int var6 = this.offset + var1;
         int var7 = var2 - var1;
         var4 = new RegularImmutableList(var5, var6, var7);
      }

      return (ImmutableList)var4;
   }

   public Object[] toArray() {
      Object[] var1 = new Object[this.size()];
      Object[] var2 = this.array;
      int var3 = this.offset;
      int var4 = this.size;
      Platform.unsafeArrayCopy(var2, var3, var1, 0, var4);
      return var1;
   }

   public <T extends Object> T[] toArray(T[] var1) {
      int var2 = var1.length;
      int var3 = this.size;
      if(var2 < var3) {
         int var4 = this.size;
         var1 = ObjectArrays.newArray(var1, var4);
      } else {
         int var8 = var1.length;
         int var9 = this.size;
         if(var8 > var9) {
            int var10 = this.size;
            var1[var10] = false;
         }
      }

      Object[] var5 = this.array;
      int var6 = this.offset;
      int var7 = this.size;
      Platform.unsafeArrayCopy(var5, var6, var1, 0, var7);
      return var1;
   }

   public String toString() {
      int var1 = this.size() * 16;
      StringBuilder var2 = new StringBuilder(var1);
      StringBuilder var3 = var2.append('[');
      Object[] var4 = this.array;
      int var5 = this.offset;
      Object var6 = var4[var5];
      var3.append(var6);
      int var8 = this.offset + 1;

      while(true) {
         int var9 = this.offset;
         int var10 = this.size;
         int var11 = var9 + var10;
         if(var8 >= var11) {
            return var2.append(']').toString();
         }

         StringBuilder var12 = var2.append(", ");
         Object var13 = this.array[var8];
         var12.append(var13);
         ++var8;
      }
   }

   class 1 implements ListIterator<E> {

      int index;
      // $FF: synthetic field
      final int val$start;


      1(int var2) {
         this.val$start = var2;
         int var3 = this.val$start;
         this.index = var3;
      }

      public void add(E var1) {
         throw new UnsupportedOperationException();
      }

      public boolean hasNext() {
         int var1 = this.index;
         int var2 = RegularImmutableList.this.size;
         boolean var3;
         if(var1 < var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public boolean hasPrevious() {
         boolean var1;
         if(this.index > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public E next() {
         Object var3;
         try {
            RegularImmutableList var1 = RegularImmutableList.this;
            int var2 = this.index;
            var3 = var1.get(var2);
         } catch (IndexOutOfBoundsException var7) {
            throw new NoSuchElementException();
         }

         int var5 = this.index + 1;
         this.index = var5;
         return var3;
      }

      public int nextIndex() {
         return this.index;
      }

      public E previous() {
         Object var3;
         try {
            RegularImmutableList var1 = RegularImmutableList.this;
            int var2 = this.index + -1;
            var3 = var1.get(var2);
         } catch (IndexOutOfBoundsException var7) {
            throw new NoSuchElementException();
         }

         int var5 = this.index + -1;
         this.index = var5;
         return var3;
      }

      public int previousIndex() {
         return this.index + -1;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void set(E var1) {
         throw new UnsupportedOperationException();
      }
   }
}
