package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@GwtCompatible(
   serializable = true
)
final class ImmutableEnumSet<E extends Object> extends ImmutableSet<E> {

   private final transient Set<E> delegate;
   private transient int hashCode;


   ImmutableEnumSet(Set<E> var1) {
      this.delegate = var1;
   }

   public boolean contains(Object var1) {
      return this.delegate.contains(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      return this.delegate.containsAll(var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 != this && !this.delegate.equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this.hashCode;
      if(var1 == 0) {
         var1 = this.delegate.hashCode();
         this.hashCode = var1;
      }

      return var1;
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public UnmodifiableIterator<E> iterator() {
      return Iterators.unmodifiableIterator(this.delegate.iterator());
   }

   public int size() {
      return this.delegate.size();
   }

   public Object[] toArray() {
      return this.delegate.toArray();
   }

   public <T extends Object> T[] toArray(T[] var1) {
      return this.delegate.toArray(var1);
   }

   public String toString() {
      return this.delegate.toString();
   }

   Object writeReplace() {
      EnumSet var1 = (EnumSet)this.delegate;
      return new ImmutableEnumSet.EnumSerializedForm(var1);
   }

   private static class EnumSerializedForm<E extends Enum<E>> implements Serializable {

      private static final long serialVersionUID;
      final EnumSet<E> delegate;


      EnumSerializedForm(EnumSet<E> var1) {
         this.delegate = var1;
      }

      Object readResolve() {
         EnumSet var1 = this.delegate.clone();
         return new ImmutableEnumSet(var1);
      }
   }
}
