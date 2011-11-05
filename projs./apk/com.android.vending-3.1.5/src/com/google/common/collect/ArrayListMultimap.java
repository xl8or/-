package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@GwtCompatible(
   serializable = true
)
public final class ArrayListMultimap<K extends Object, V extends Object> extends AbstractListMultimap<K, V> {

   private static final int DEFAULT_VALUES_PER_KEY = 10;
   private static final long serialVersionUID;
   @VisibleForTesting
   transient int expectedValuesPerKey;


   private ArrayListMultimap() {
      HashMap var1 = new HashMap();
      super(var1);
      this.expectedValuesPerKey = 10;
   }

   private ArrayListMultimap(int var1, int var2) {
      HashMap var3 = Maps.newHashMapWithExpectedSize(var1);
      super(var3);
      byte var4;
      if(var2 >= 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      Preconditions.checkArgument((boolean)var4);
      this.expectedValuesPerKey = var2;
   }

   private ArrayListMultimap(Multimap<? extends K, ? extends V> var1) {
      int var2 = var1.keySet().size();
      int var3;
      if(var1 instanceof ArrayListMultimap) {
         var3 = ((ArrayListMultimap)var1).expectedValuesPerKey;
      } else {
         var3 = 10;
      }

      this(var2, var3);
      this.putAll(var1);
   }

   public static <K extends Object, V extends Object> ArrayListMultimap<K, V> create() {
      return new ArrayListMultimap();
   }

   public static <K extends Object, V extends Object> ArrayListMultimap<K, V> create(int var0, int var1) {
      return new ArrayListMultimap(var0, var1);
   }

   public static <K extends Object, V extends Object> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new ArrayListMultimap(var0);
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

   List<V> createCollection() {
      int var1 = this.expectedValuesPerKey;
      return new ArrayList(var1);
   }

   public void trimToSize() {
      Iterator var1 = this.backingMap().values().iterator();

      while(var1.hasNext()) {
         ((ArrayList)((Collection)var1.next())).trimToSize();
      }

   }
}
