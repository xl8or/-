package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableMultiset;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import com.google.common.collect.UnmodifiableIterator;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public class ImmutableMultiset<E extends Object> extends ImmutableCollection<E> implements Multiset<E> {

   private static final long serialVersionUID;
   private transient ImmutableSet<Multiset.Entry<E>> entrySet;
   private final transient ImmutableMap<E, Integer> map;
   private final transient int size;


   ImmutableMultiset(ImmutableMap<E, Integer> var1, int var2) {
      this.map = var1;
      this.size = var2;
   }

   public static <E extends Object> ImmutableMultiset.Builder<E> builder() {
      return new ImmutableMultiset.Builder();
   }

   public static <E extends Object> ImmutableMultiset<E> copyOf(Iterable<? extends E> var0) {
      ImmutableMultiset var1;
      if(var0 instanceof ImmutableMultiset) {
         var1 = (ImmutableMultiset)var0;
      } else {
         Object var2;
         if(var0 instanceof Multiset) {
            var2 = (Multiset)var0;
         } else {
            var2 = LinkedHashMultiset.create(var0);
         }

         var1 = copyOfInternal((Multiset)var2);
      }

      return var1;
   }

   public static <E extends Object> ImmutableMultiset<E> copyOf(Iterator<? extends E> var0) {
      LinkedHashMultiset var1 = LinkedHashMultiset.create();
      Iterators.addAll(var1, var0);
      return copyOfInternal(var1);
   }

   private static <E extends Object> ImmutableMultiset<E> copyOfInternal(Multiset<? extends E> var0) {
      long var1 = 0L;
      ImmutableMap.Builder var3 = ImmutableMap.builder();
      Iterator var4 = var0.entrySet().iterator();

      while(var4.hasNext()) {
         Multiset.Entry var5 = (Multiset.Entry)var4.next();
         int var6 = var5.getCount();
         if(var6 > 0) {
            Object var7 = var5.getElement();
            Integer var8 = Integer.valueOf(var6);
            var3.put(var7, var8);
            long var10 = (long)var6;
            var1 += var10;
         }
      }

      ImmutableMultiset var12;
      if(var1 == 0L) {
         var12 = of();
      } else {
         ImmutableMap var13 = var3.build();
         int var14 = (int)Math.min(var1, 2147483647L);
         var12 = new ImmutableMultiset(var13, var14);
      }

      return var12;
   }

   public static <E extends Object> ImmutableMultiset<E> of() {
      return EmptyImmutableMultiset.INSTANCE;
   }

   public static <E extends Object> ImmutableMultiset<E> of(E ... var0) {
      return copyOf((Iterable)Arrays.asList(var0));
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = var1.readInt();
      ImmutableMap.Builder var3 = ImmutableMap.builder();
      long var4 = 0L;

      for(int var6 = 0; var6 < var2; ++var6) {
         Object var7 = var1.readObject();
         int var8 = var1.readInt();
         if(var8 <= 0) {
            String var9 = "Invalid count " + var8;
            throw new InvalidObjectException(var9);
         }

         Integer var10 = Integer.valueOf(var8);
         var3.put(var7, var10);
         long var12 = (long)var8;
         var4 += var12;
      }

      Serialization.FieldSetter var14 = ImmutableMultiset.FieldSettersHolder.MAP_FIELD_SETTER;
      ImmutableMap var15 = var3.build();
      var14.set(this, var15);
      Serialization.FieldSetter var16 = ImmutableMultiset.FieldSettersHolder.SIZE_FIELD_SETTER;
      int var17 = (int)Math.min(var4, 2147483647L);
      var16.set(this, var17);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultiset(this, var1);
   }

   public int add(E var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public boolean contains(@Nullable Object var1) {
      return this.map.containsKey(var1);
   }

   public int count(@Nullable Object var1) {
      Integer var2 = (Integer)this.map.get(var1);
      int var3;
      if(var2 == null) {
         var3 = 0;
      } else {
         var3 = var2.intValue();
      }

      return var3;
   }

   public Set<E> elementSet() {
      return this.map.keySet();
   }

   public Set<Multiset.Entry<E>> entrySet() {
      Object var1 = this.entrySet;
      if(var1 == null) {
         var1 = new ImmutableMultiset.EntrySet(this);
         this.entrySet = (ImmutableSet)var1;
      }

      return (Set)var1;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(var1 instanceof Multiset) {
            Multiset var3 = (Multiset)var1;
            int var4 = this.size();
            int var5 = var3.size();
            if(var4 != var5) {
               var2 = false;
            } else {
               Iterator var6 = var3.entrySet().iterator();

               while(var6.hasNext()) {
                  Multiset.Entry var7 = (Multiset.Entry)var6.next();
                  Object var8 = var7.getElement();
                  int var9 = this.count(var8);
                  int var10 = var7.getCount();
                  if(var9 != var10) {
                     var2 = false;
                     break;
                  }
               }
            }
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public int hashCode() {
      return this.map.hashCode();
   }

   public UnmodifiableIterator<E> iterator() {
      UnmodifiableIterator var1 = this.map.entrySet().iterator();
      return new ImmutableMultiset.1(var1);
   }

   public int remove(Object var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public int setCount(E var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public boolean setCount(E var1, int var2, int var3) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.size;
   }

   public String toString() {
      return this.entrySet().toString();
   }

   Object writeReplace() {
      return this;
   }

   private static class EntrySet<E extends Object> extends ImmutableSet<Multiset.Entry<E>> {

      private static final long serialVersionUID;
      final ImmutableMultiset<E> multiset;


      public EntrySet(ImmutableMultiset<E> var1) {
         this.multiset = var1;
      }

      public boolean contains(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Multiset.Entry) {
            Multiset.Entry var3 = (Multiset.Entry)var1;
            if(var3.getCount() > 0) {
               ImmutableMultiset var4 = this.multiset;
               Object var5 = var3.getElement();
               int var6 = var4.count(var5);
               int var7 = var3.getCount();
               if(var6 == var7) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         return this.multiset.map.hashCode();
      }

      public UnmodifiableIterator<Multiset.Entry<E>> iterator() {
         UnmodifiableIterator var1 = this.multiset.map.entrySet().iterator();
         return new ImmutableMultiset.EntrySet.1(var1);
      }

      public int size() {
         return this.multiset.map.size();
      }

      Object writeReplace() {
         return this;
      }

      class 1 extends UnmodifiableIterator<Multiset.Entry<E>> {

         // $FF: synthetic field
         final Iterator val$mapIterator;


         1(Iterator var2) {
            this.val$mapIterator = var2;
         }

         public boolean hasNext() {
            return this.val$mapIterator.hasNext();
         }

         public Multiset.Entry<E> next() {
            Entry var1 = (Entry)this.val$mapIterator.next();
            Object var2 = var1.getKey();
            int var3 = ((Integer)var1.getValue()).intValue();
            return Multisets.immutableEntry(var2, var3);
         }
      }
   }

   public static final class Builder<E extends Object> extends ImmutableCollection.Builder<E> {

      private final Multiset<E> contents;


      public Builder() {
         LinkedHashMultiset var1 = LinkedHashMultiset.create();
         this.contents = var1;
      }

      public ImmutableMultiset.Builder<E> add(E var1) {
         Multiset var2 = this.contents;
         Object var3 = Preconditions.checkNotNull(var1);
         var2.add(var3);
         return this;
      }

      public ImmutableMultiset.Builder<E> add(E ... var1) {
         super.add(var1);
         return this;
      }

      public ImmutableMultiset.Builder<E> addAll(Iterable<? extends E> var1) {
         if(var1 instanceof Multiset) {
            Iterator var2 = ((Multiset)var1).entrySet().iterator();

            while(var2.hasNext()) {
               Multiset.Entry var3 = (Multiset.Entry)var2.next();
               Object var4 = var3.getElement();
               int var5 = var3.getCount();
               this.addCopies(var4, var5);
            }
         } else {
            super.addAll(var1);
         }

         return this;
      }

      public ImmutableMultiset.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableMultiset.Builder<E> addCopies(E var1, int var2) {
         Multiset var3 = this.contents;
         Object var4 = Preconditions.checkNotNull(var1);
         var3.add(var4, var2);
         return this;
      }

      public ImmutableMultiset<E> build() {
         return ImmutableMultiset.copyOf((Iterable)this.contents);
      }

      public ImmutableMultiset.Builder<E> setCount(E var1, int var2) {
         Multiset var3 = this.contents;
         Object var4 = Preconditions.checkNotNull(var1);
         var3.setCount(var4, var2);
         return this;
      }
   }

   private static class FieldSettersHolder {

      static final Serialization.FieldSetter<ImmutableMultiset> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultiset.class, "map");
      static final Serialization.FieldSetter<ImmutableMultiset> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultiset.class, "size");


      private FieldSettersHolder() {}
   }

   class 1 extends UnmodifiableIterator<E> {

      E element;
      int remaining;
      // $FF: synthetic field
      final Iterator val$mapIterator;


      1(Iterator var2) {
         this.val$mapIterator = var2;
      }

      public boolean hasNext() {
         boolean var1;
         if(this.remaining <= 0 && !this.val$mapIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public E next() {
         if(this.remaining <= 0) {
            Entry var1 = (Entry)this.val$mapIterator.next();
            Object var2 = var1.getKey();
            this.element = var2;
            int var3 = ((Integer)var1.getValue()).intValue();
            this.remaining = var3;
         }

         int var4 = this.remaining + -1;
         this.remaining = var4;
         return this.element;
      }
   }
}
