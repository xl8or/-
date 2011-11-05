package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.EmptyImmutableSet;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.RegularImmutableSet;
import com.google.common.collect.SingletonImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public abstract class ImmutableSet<E extends Object> extends ImmutableCollection<E> implements Set<E> {

   ImmutableSet() {}

   public static <E extends Object> ImmutableSet.Builder<E> builder() {
      return new ImmutableSet.Builder();
   }

   public static <E extends Object> ImmutableSet<E> copyOf(Iterable<? extends E> var0) {
      ImmutableSet var1;
      if(var0 instanceof ImmutableSet && !(var0 instanceof ImmutableSortedSet)) {
         var1 = (ImmutableSet)var0;
      } else {
         var1 = copyOfInternal(Collections2.toCollection(var0));
      }

      return var1;
   }

   public static <E extends Object> ImmutableSet<E> copyOf(Iterator<? extends E> var0) {
      return copyOfInternal(Lists.newArrayList(var0));
   }

   private static <E extends Object> ImmutableSet<E> copyOfInternal(Collection<? extends E> var0) {
      ImmutableSet var2;
      switch(var0.size()) {
      case 0:
         var2 = of();
         break;
      case 1:
         var2 = of(var0.iterator().next());
         break;
      default:
         int var1 = var0.size();
         var2 = create(var0, var1);
      }

      return var2;
   }

   private static <E extends Object> ImmutableSet<E> create(Iterable<? extends E> var0, int var1) {
      int var2 = Hashing.chooseTableSize(var1);
      Object[] var3 = new Object[var2];
      int var4 = var2 + -1;
      ArrayList var5 = new ArrayList(var1);
      int var6 = 0;
      Iterator var7 = var0.iterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         Object var9 = Preconditions.checkNotNull(var8);
         int var10 = var8.hashCode();
         int var11 = Hashing.smear(var10);

         while(true) {
            int var12 = var11 & var4;
            Object var13 = var3[var12];
            if(var13 == null) {
               var3[var12] = var8;
               var5.add(var8);
               var6 += var10;
               break;
            }

            if(var13.equals(var8)) {
               break;
            }

            ++var11;
         }
      }

      Object var16;
      if(var5.size() == 1) {
         Object var15 = var5.get(0);
         var16 = new SingletonImmutableSet(var15, var6);
      } else {
         int var17 = Hashing.chooseTableSize(var5.size());
         if(var2 > var17) {
            int var18 = var5.size();
            var16 = create(var5, var18);
         } else {
            Object[] var19 = var5.toArray();
            var16 = new RegularImmutableSet(var19, var6, var3, var4);
         }
      }

      return (ImmutableSet)var16;
   }

   private static <E extends Object> ImmutableSet<E> create(E ... var0) {
      List var1 = Arrays.asList(var0);
      int var2 = var0.length;
      return create(var1, var2);
   }

   public static <E extends Object> ImmutableSet<E> of() {
      return EmptyImmutableSet.INSTANCE;
   }

   public static <E extends Object> ImmutableSet<E> of(E var0) {
      return new SingletonImmutableSet(var0);
   }

   public static <E extends Object> ImmutableSet<E> of(E var0, E var1) {
      Object[] var2 = new Object[]{var0, var1};
      return create(var2);
   }

   public static <E extends Object> ImmutableSet<E> of(E var0, E var1, E var2) {
      Object[] var3 = new Object[]{var0, var1, var2};
      return create(var3);
   }

   public static <E extends Object> ImmutableSet<E> of(E var0, E var1, E var2, E var3) {
      Object[] var4 = new Object[]{var0, var1, var2, var3};
      return create(var4);
   }

   public static <E extends Object> ImmutableSet<E> of(E var0, E var1, E var2, E var3, E var4) {
      Object[] var5 = new Object[]{var0, var1, var2, var3, var4};
      return create(var5);
   }

   public static <E extends Object> ImmutableSet<E> of(E ... var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ImmutableSet var2;
      switch(var0.length) {
      case 0:
         var2 = of();
         break;
      case 1:
         var2 = of(var0[0]);
         break;
      default:
         var2 = create(var0);
      }

      return var2;
   }

   public boolean equals(@Nullable Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else {
         if(var1 instanceof ImmutableSet && this.isHashCodeFast() && ((ImmutableSet)var1).isHashCodeFast()) {
            int var3 = this.hashCode();
            int var4 = var1.hashCode();
            if(var3 != var4) {
               var2 = 0;
               return (boolean)var2;
            }
         }

         var2 = Collections2.setEquals(this, var1);
      }

      return (boolean)var2;
   }

   public int hashCode() {
      int var1 = 0;

      int var3;
      for(Iterator var2 = this.iterator(); var2.hasNext(); var1 += var3) {
         var3 = var2.next().hashCode();
      }

      return var1;
   }

   boolean isHashCodeFast() {
      return false;
   }

   public abstract UnmodifiableIterator<E> iterator();

   Object writeReplace() {
      Object[] var1 = this.toArray();
      return new ImmutableSet.SerializedForm(var1);
   }

   abstract static class TransformedImmutableSet<D extends Object, E extends Object> extends ImmutableSet<E> {

      final int hashCode;
      final D[] source;


      TransformedImmutableSet(D[] var1, int var2) {
         this.source = var1;
         this.hashCode = var2;
      }

      public final int hashCode() {
         return this.hashCode;
      }

      public boolean isEmpty() {
         return false;
      }

      boolean isHashCodeFast() {
         return true;
      }

      public UnmodifiableIterator<E> iterator() {
         return new ImmutableSet.TransformedImmutableSet.1();
      }

      public int size() {
         return this.source.length;
      }

      public Object[] toArray() {
         Object[] var1 = new Object[this.size()];
         return this.toArray(var1);
      }

      public <T extends Object> T[] toArray(T[] var1) {
         int var2 = this.size();
         if(var1.length < var2) {
            var1 = ObjectArrays.newArray(var1, var2);
         } else if(var1.length > var2) {
            var1[var2] = false;
         }

         Object[] var3 = var1;
         int var4 = 0;

         while(true) {
            int var5 = this.source.length;
            if(var4 >= var5) {
               return var1;
            }

            Object var6 = this.source[var4];
            Object var7 = this.transform(var6);
            var3[var4] = var7;
            ++var4;
         }
      }

      abstract E transform(D var1);

      class 1 extends AbstractIterator<E> {

         int index = 0;


         1() {}

         protected E computeNext() {
            int var1 = this.index;
            int var2 = TransformedImmutableSet.this.source.length;
            Object var8;
            if(var1 < var2) {
               ImmutableSet.TransformedImmutableSet var3 = TransformedImmutableSet.this;
               Object[] var4 = TransformedImmutableSet.this.source;
               int var5 = this.index;
               int var6 = var5 + 1;
               this.index = var6;
               Object var7 = var4[var5];
               var8 = var3.transform(var7);
            } else {
               var8 = this.endOfData();
            }

            return var8;
         }
      }
   }

   private static class SerializedForm implements Serializable {

      private static final long serialVersionUID;
      final Object[] elements;


      SerializedForm(Object[] var1) {
         this.elements = var1;
      }

      Object readResolve() {
         return ImmutableSet.of(this.elements);
      }
   }

   public static class Builder<E extends Object> extends ImmutableCollection.Builder<E> {

      final ArrayList<E> contents;


      public Builder() {
         ArrayList var1 = Lists.newArrayList();
         this.contents = var1;
      }

      public ImmutableSet.Builder<E> add(E var1) {
         ArrayList var2 = this.contents;
         Object var3 = Preconditions.checkNotNull(var1);
         var2.add(var3);
         return this;
      }

      public ImmutableSet.Builder<E> add(E ... var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         ArrayList var3 = this.contents;
         int var4 = this.contents.size();
         int var5 = var1.length;
         int var6 = var4 + var5;
         var3.ensureCapacity(var6);
         super.add(var1);
         return this;
      }

      public ImmutableSet.Builder<E> addAll(Iterable<? extends E> var1) {
         if(var1 instanceof Collection) {
            Collection var2 = (Collection)var1;
            ArrayList var3 = this.contents;
            int var4 = this.contents.size();
            int var5 = var2.size();
            int var6 = var4 + var5;
            var3.ensureCapacity(var6);
         }

         super.addAll(var1);
         return this;
      }

      public ImmutableSet.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableSet<E> build() {
         return ImmutableSet.copyOf((Iterable)this.contents);
      }
   }

   abstract static class ArrayImmutableSet<E extends Object> extends ImmutableSet<E> {

      final transient Object[] elements;


      ArrayImmutableSet(Object[] var1) {
         this.elements = var1;
      }

      public boolean containsAll(Collection<?> var1) {
         byte var2 = 1;
         if(var1 != this) {
            if(!(var1 instanceof ImmutableSet.ArrayImmutableSet)) {
               var2 = super.containsAll(var1);
            } else {
               int var3 = var1.size();
               int var4 = this.size();
               if(var3 > var4) {
                  var2 = 0;
               } else {
                  Object[] var5 = ((ImmutableSet.ArrayImmutableSet)var1).elements;
                  int var6 = var5.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     Object var8 = var5[var7];
                     if(!this.contains(var8)) {
                        var2 = 0;
                        break;
                     }
                  }
               }
            }
         }

         return (boolean)var2;
      }

      ImmutableList<E> createAsList() {
         Object[] var1 = this.elements;
         return new ImmutableAsList(var1, this);
      }

      public boolean isEmpty() {
         return false;
      }

      public UnmodifiableIterator<E> iterator() {
         return Iterators.forArray(this.elements);
      }

      public int size() {
         return this.elements.length;
      }

      public Object[] toArray() {
         Object[] var1 = new Object[this.size()];
         Object[] var2 = this.elements;
         int var3 = this.size();
         Platform.unsafeArrayCopy(var2, 0, var1, 0, var3);
         return var1;
      }

      public <T extends Object> T[] toArray(T[] var1) {
         int var2 = this.size();
         if(var1.length < var2) {
            var1 = ObjectArrays.newArray(var1, var2);
         } else if(var1.length > var2) {
            var1[var2] = false;
         }

         Platform.unsafeArrayCopy(this.elements, 0, var1, 0, var2);
         return var1;
      }
   }
}
