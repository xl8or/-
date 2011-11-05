package com.google.common.collect;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.RegularImmutableList;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

final class ImmutableAsList<E extends Object> extends RegularImmutableList<E> {

   private final transient ImmutableCollection<E> collection;


   ImmutableAsList(Object[] var1, ImmutableCollection<E> var2) {
      int var3 = var1.length;
      super(var1, 0, var3);
      this.collection = var2;
   }

   private void readObject(ObjectInputStream var1) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   public boolean contains(Object var1) {
      return this.collection.contains(var1);
   }

   Object writeReplace() {
      ImmutableCollection var1 = this.collection;
      return new ImmutableAsList.SerializedForm(var1);
   }

   static class SerializedForm implements Serializable {

      private static final long serialVersionUID;
      final ImmutableCollection<?> collection;


      SerializedForm(ImmutableCollection<?> var1) {
         this.collection = var1;
      }

      Object readResolve() {
         return this.collection.asList();
      }
   }
}
