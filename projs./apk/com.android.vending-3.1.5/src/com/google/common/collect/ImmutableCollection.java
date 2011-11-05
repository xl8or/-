package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ImmutableCollection<E extends Object> implements Collection<E>, Serializable {

   static final ImmutableCollection<Object> EMPTY_IMMUTABLE_COLLECTION = new ImmutableCollection.EmptyImmutableCollection((ImmutableCollection.1)null);
   private transient ImmutableList<E> asList;


   ImmutableCollection() {}

   public final boolean add(E var1) {
      throw new UnsupportedOperationException();
   }

   public final boolean addAll(Collection<? extends E> var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<E> asList() {
      ImmutableList var1 = this.asList;
      if(var1 == null) {
         var1 = this.createAsList();
         this.asList = var1;
      }

      return var1;
   }

   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean contains(@Nullable Object var1) {
      boolean var2 = false;
      if(var1 != null) {
         Iterator var3 = this.iterator();

         while(var3.hasNext()) {
            if(var3.next().equals(var1)) {
               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   public boolean containsAll(Collection<?> var1) {
      Iterator var2 = var1.iterator();

      boolean var4;
      while(true) {
         if(var2.hasNext()) {
            Object var3 = var2.next();
            if(this.contains(var3)) {
               continue;
            }

            var4 = false;
            break;
         }

         var4 = true;
         break;
      }

      return var4;
   }

   ImmutableList<E> createAsList() {
      Object var2;
      switch(this.size()) {
      case 0:
         var2 = ImmutableList.of();
         break;
      case 1:
         var2 = ImmutableList.of(this.iterator().next());
         break;
      default:
         Object[] var1 = this.toArray();
         var2 = new ImmutableAsList(var1, this);
      }

      return (ImmutableList)var2;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract UnmodifiableIterator<E> iterator();

   public final boolean remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   public final boolean removeAll(Collection<?> var1) {
      throw new UnsupportedOperationException();
   }

   public final boolean retainAll(Collection<?> var1) {
      throw new UnsupportedOperationException();
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

      int var7;
      for(Iterator var5 = this.iterator(); var5.hasNext(); var4 = var7) {
         Object var6 = var5.next();
         var7 = var4 + 1;
         var3[var4] = var6;
      }

      return var1;
   }

   public String toString() {
      int var1 = this.size() * 16;
      StringBuilder var2 = (new StringBuilder(var1)).append('[');
      Collections2.standardJoiner.appendTo(var2, (Iterable)this);
      return var2.append(']').toString();
   }

   Object writeReplace() {
      Object[] var1 = this.toArray();
      return new ImmutableCollection.SerializedForm(var1);
   }

   abstract static class Builder<E extends Object> {

      Builder() {}

      public abstract ImmutableCollection.Builder<E> add(E var1);

      public ImmutableCollection.Builder<E> add(E ... var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         Object[] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object var6 = var3[var5];
            this.add(var6);
         }

         return this;
      }

      public ImmutableCollection.Builder<E> addAll(Iterable<? extends E> var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            this.add(var4);
         }

         return this;
      }

      public ImmutableCollection.Builder<E> addAll(Iterator<? extends E> var1) {
         Object var2 = Preconditions.checkNotNull(var1);

         while(var1.hasNext()) {
            Object var3 = var1.next();
            this.add(var3);
         }

         return this;
      }

      public abstract ImmutableCollection<E> build();
   }

   private static class ArrayImmutableCollection<E extends Object> extends ImmutableCollection<E> {

      private final E[] elements;


      ArrayImmutableCollection(E[] var1) {
         this.elements = var1;
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
   }

   private static class SerializedForm implements Serializable {

      private static final long serialVersionUID;
      final Object[] elements;


      SerializedForm(Object[] var1) {
         this.elements = var1;
      }

      Object readResolve() {
         Object var1;
         if(this.elements.length == 0) {
            var1 = ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION;
         } else {
            Object[] var2 = Platform.clone(this.elements);
            var1 = new ImmutableCollection.ArrayImmutableCollection(var2);
         }

         return var1;
      }
   }

   private static class EmptyImmutableCollection extends ImmutableCollection<Object> {

      private static final Object[] EMPTY_ARRAY = new Object[0];


      private EmptyImmutableCollection() {}

      // $FF: synthetic method
      EmptyImmutableCollection(ImmutableCollection.1 var1) {
         this();
      }

      public boolean contains(@Nullable Object var1) {
         return false;
      }

      public boolean isEmpty() {
         return true;
      }

      public UnmodifiableIterator<Object> iterator() {
         return Iterators.EMPTY_ITERATOR;
      }

      public int size() {
         return 0;
      }

      public Object[] toArray() {
         return EMPTY_ARRAY;
      }

      public <T extends Object> T[] toArray(T[] var1) {
         if(var1.length > 0) {
            var1[0] = false;
         }

         return var1;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
