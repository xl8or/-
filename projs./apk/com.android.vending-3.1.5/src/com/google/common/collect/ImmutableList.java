package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.SingletonImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public abstract class ImmutableList<E extends Object> extends ImmutableCollection<E> implements List<E>, RandomAccess {

   ImmutableList() {}

   public static <E extends Object> ImmutableList.Builder<E> builder() {
      return new ImmutableList.Builder();
   }

   private static <E extends Object> ImmutableList<E> copyFromCollection(Collection<? extends E> var0) {
      Object[] var1 = var0.toArray();
      Object var3;
      switch(var1.length) {
      case 0:
         var3 = of();
         break;
      case 1:
         Object var4 = var1[0];
         var3 = new SingletonImmutableList(var4);
         break;
      default:
         Object[] var2 = copyIntoArray(var1);
         var3 = new RegularImmutableList(var2);
      }

      return (ImmutableList)var3;
   }

   private static Object[] copyIntoArray(Object ... var0) {
      Object[] var1 = new Object[var0.length];
      Object[] var2 = var0;
      int var3 = var0.length;
      int var4 = 0;

      int var8;
      for(int var5 = 0; var4 < var3; var5 = var8) {
         Object var6 = var2[var4];
         if(var6 == false) {
            String var7 = "at index " + var5;
            throw new NullPointerException(var7);
         }

         var8 = var5 + 1;
         var1[var5] = var6;
         ++var4;
      }

      return var1;
   }

   public static <E extends Object> ImmutableList<E> copyOf(Iterable<? extends E> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ImmutableList var2;
      if(var0 instanceof Collection) {
         var2 = copyOf((Collection)var0);
      } else {
         var2 = copyOf(var0.iterator());
      }

      return var2;
   }

   public static <E extends Object> ImmutableList<E> copyOf(Collection<? extends E> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      ImmutableList var2;
      if(var0 instanceof ImmutableList) {
         var2 = (ImmutableList)var0;
      } else {
         var2 = copyFromCollection(var0);
      }

      return var2;
   }

   public static <E extends Object> ImmutableList<E> copyOf(Iterator<? extends E> var0) {
      return copyFromCollection(Lists.newArrayList(var0));
   }

   public static <E extends Object> ImmutableList<E> of() {
      return EmptyImmutableList.INSTANCE;
   }

   public static <E extends Object> ImmutableList<E> of(E var0) {
      return new SingletonImmutableList(var0);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1) {
      Object[] var2 = new Object[]{var0, var1};
      Object[] var3 = copyIntoArray(var2);
      return new RegularImmutableList(var3);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2) {
      Object[] var3 = new Object[]{var0, var1, var2};
      Object[] var4 = copyIntoArray(var3);
      return new RegularImmutableList(var4);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3) {
      Object[] var4 = new Object[]{var0, var1, var2, var3};
      Object[] var5 = copyIntoArray(var4);
      return new RegularImmutableList(var5);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4) {
      Object[] var5 = new Object[]{var0, var1, var2, var3, var4};
      Object[] var6 = copyIntoArray(var5);
      return new RegularImmutableList(var6);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5) {
      Object[] var6 = new Object[]{var0, var1, var2, var3, var4, var5};
      Object[] var7 = copyIntoArray(var6);
      return new RegularImmutableList(var7);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6) {
      Object[] var7 = new Object[]{var0, var1, var2, var3, var4, var5, var6};
      Object[] var8 = copyIntoArray(var7);
      return new RegularImmutableList(var8);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7) {
      Object[] var8 = new Object[]{var0, var1, var2, var3, var4, var5, var6, var7};
      Object[] var9 = copyIntoArray(var8);
      return new RegularImmutableList(var9);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8) {
      Object[] var9 = new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8};
      Object[] var10 = copyIntoArray(var9);
      return new RegularImmutableList(var10);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8, E var9) {
      Object[] var10 = new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8, var9};
      Object[] var11 = copyIntoArray(var10);
      return new RegularImmutableList(var11);
   }

   public static <E extends Object> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8, E var9, E var10) {
      Object[] var11 = new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10};
      Object[] var12 = copyIntoArray(var11);
      return new RegularImmutableList(var12);
   }

   public static <E extends Object> ImmutableList<E> of(E ... var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      Object var3;
      switch(var0.length) {
      case 0:
         var3 = of();
         break;
      case 1:
         Object var4 = var0[0];
         var3 = new SingletonImmutableList(var4);
         break;
      default:
         Object[] var2 = copyIntoArray(var0);
         var3 = new RegularImmutableList(var2);
      }

      return (ImmutableList)var3;
   }

   private void readObject(ObjectInputStream var1) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   public final void add(int var1, E var2) {
      throw new UnsupportedOperationException();
   }

   public final boolean addAll(int var1, Collection<? extends E> var2) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<E> asList() {
      return this;
   }

   public abstract int indexOf(@Nullable Object var1);

   public abstract UnmodifiableIterator<E> iterator();

   public abstract int lastIndexOf(@Nullable Object var1);

   public final E remove(int var1) {
      throw new UnsupportedOperationException();
   }

   public final E set(int var1, E var2) {
      throw new UnsupportedOperationException();
   }

   public abstract ImmutableList<E> subList(int var1, int var2);

   Object writeReplace() {
      Object[] var1 = this.toArray();
      return new ImmutableList.SerializedForm(var1);
   }

   public static final class Builder<E extends Object> extends ImmutableCollection.Builder<E> {

      private final ArrayList<E> contents;


      public Builder() {
         ArrayList var1 = Lists.newArrayList();
         this.contents = var1;
      }

      public ImmutableList.Builder<E> add(E var1) {
         ArrayList var2 = this.contents;
         Object var3 = Preconditions.checkNotNull(var1);
         var2.add(var3);
         return this;
      }

      public ImmutableList.Builder<E> add(E ... var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         ArrayList var3 = this.contents;
         int var4 = this.contents.size();
         int var5 = var1.length;
         int var6 = var4 + var5;
         var3.ensureCapacity(var6);
         super.add(var1);
         return this;
      }

      public ImmutableList.Builder<E> addAll(Iterable<? extends E> var1) {
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

      public ImmutableList.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableList<E> build() {
         return ImmutableList.copyOf((Collection)this.contents);
      }
   }

   private static class SerializedForm implements Serializable {

      private static final long serialVersionUID;
      final Object[] elements;


      SerializedForm(Object[] var1) {
         this.elements = var1;
      }

      Object readResolve() {
         return ImmutableList.of(this.elements);
      }
   }
}
