package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;

@GwtCompatible(
   serializable = true
)
public final class HashMultimap<K extends Object, V extends Object> extends AbstractSetMultimap<K, V> {

   private static final int DEFAULT_VALUES_PER_KEY = 8;
   private static final long serialVersionUID;
   @VisibleForTesting
   transient int expectedValuesPerKey;


   private HashMultimap() {
      HashMap var1 = new HashMap();
      super(var1);
      this.expectedValuesPerKey = 8;
   }

   private HashMultimap(int var1, int var2) {
      HashMap var3 = Maps.newHashMapWithExpectedSize(var1);
      super(var3);
      this.expectedValuesPerKey = 8;
      byte var4;
      if(var2 >= 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      Preconditions.checkArgument((boolean)var4);
      this.expectedValuesPerKey = var2;
   }

   private HashMultimap(Multimap<? extends K, ? extends V> var1) {
      HashMap var2 = Maps.newHashMapWithExpectedSize(var1.keySet().size());
      super(var2);
      this.expectedValuesPerKey = 8;
      this.putAll(var1);
   }

   public static <K extends Object, V extends Object> HashMultimap<K, V> create() {
      return new HashMultimap();
   }

   public static <K extends Object, V extends Object> HashMultimap<K, V> create(int var0, int var1) {
      return new HashMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> HashMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new HashMultimap(var0);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = var1.readInt();
      this.expectedValuesPerKey = var2;
      int var3 = Serialization.readCount(var1);
      HashMap var4 = Maps.newHashMapWithExpectedSize(var3);
      this.setMap(var4);
      Serialization.populateMultimap(this, var1, var3);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      int var2 = this.expectedValuesPerKey;
      var1.writeInt(var2);
      Serialization.writeMultimap(this, var1);
   }

   Set<V> createCollection() {
      return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
   }
}
