package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

@GwtCompatible(
   serializable = true
)
public final class HashMultiset<E extends Object> extends AbstractMapBasedMultiset<E> {

   private static final long serialVersionUID;


   private HashMultiset() {
      HashMap var1 = new HashMap();
      super(var1);
   }

   private HashMultiset(int var1) {
      int var2 = Maps.capacity(var1);
      HashMap var3 = new HashMap(var2);
      super(var3);
   }

   public static <E extends Object> HashMultiset<E> create() {
      return new HashMultiset();
   }

   public static <E extends Object> HashMultiset<E> create(int var0) {
      return new HashMultiset(var0);
   }

   public static <E extends Object> HashMultiset<E> create(Iterable<? extends E> var0) {
      HashMultiset var1 = create(Multisets.inferDistinctElements(var0));
      Iterables.addAll(var1, var0);
      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = Serialization.readCount(var1);
      HashMap var3 = Maps.newHashMapWithExpectedSize(var2);
      this.setBackingMap(var3);
      Serialization.populateMultiset(this, var1, var2);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultiset(this, var1);
   }
}
