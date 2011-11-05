package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public final class HashBiMap<K extends Object, V extends Object> extends AbstractBiMap<K, V> {

   private static final long serialVersionUID;


   private HashBiMap() {
      HashMap var1 = new HashMap();
      HashMap var2 = new HashMap();
      super(var1, (Map)var2);
   }

   private HashBiMap(int var1) {
      int var2 = Maps.capacity(var1);
      HashMap var3 = new HashMap(var2);
      int var4 = Maps.capacity(var1);
      HashMap var5 = new HashMap(var4);
      super(var3, (Map)var5);
   }

   public static <K extends Object, V extends Object> HashBiMap<K, V> create() {
      return new HashBiMap();
   }

   public static <K extends Object, V extends Object> HashBiMap<K, V> create(int var0) {
      return new HashBiMap(var0);
   }

   public static <K extends Object, V extends Object> HashBiMap<K, V> create(Map<? extends K, ? extends V> var0) {
      HashBiMap var1 = create(var0.size());
      var1.putAll(var0);
      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = Serialization.readCount(var1);
      HashMap var3 = Maps.newHashMapWithExpectedSize(var2);
      HashMap var4 = Maps.newHashMapWithExpectedSize(var2);
      this.setDelegates(var3, var4);
      Serialization.populateMap(this, var1, var2);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMap(this, var1);
   }

   public V forcePut(@Nullable K var1, @Nullable V var2) {
      return super.forcePut(var1, var2);
   }

   public V put(@Nullable K var1, @Nullable V var2) {
      return super.put(var1, var2);
   }
}
