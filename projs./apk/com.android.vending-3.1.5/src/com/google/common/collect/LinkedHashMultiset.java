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
import java.util.LinkedHashMap;

@GwtCompatible(
   serializable = true
)
public final class LinkedHashMultiset<E extends Object> extends AbstractMapBasedMultiset<E> {

   private static final long serialVersionUID;


   private LinkedHashMultiset() {
      LinkedHashMap var1 = new LinkedHashMap();
      super(var1);
   }

   private LinkedHashMultiset(int var1) {
      int var2 = Maps.capacity(var1);
      LinkedHashMap var3 = new LinkedHashMap(var2);
      super(var3);
   }

   public static <E extends Object> LinkedHashMultiset<E> create() {
      return new LinkedHashMultiset();
   }

   public static <E extends Object> LinkedHashMultiset<E> create(int var0) {
      return new LinkedHashMultiset(var0);
   }

   public static <E extends Object> LinkedHashMultiset<E> create(Iterable<? extends E> var0) {
      LinkedHashMultiset var1 = create(Multisets.inferDistinctElements(var0));
      Iterables.addAll(var1, var0);
      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = Serialization.readCount(var1);
      int var3 = Maps.capacity(var2);
      LinkedHashMap var4 = new LinkedHashMap(var3);
      this.setBackingMap(var4);
      Serialization.populateMultiset(this, var1, var2);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultiset(this, var1);
   }
}
