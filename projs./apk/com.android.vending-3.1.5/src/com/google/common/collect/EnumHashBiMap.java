package com.google.common.collect;

import com.google.common.collect.AbstractBiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public final class EnumHashBiMap<K extends Enum<K>, V extends Object> extends AbstractBiMap<K, V> {

   private static final long serialVersionUID;
   private transient Class<K> keyType;


   private EnumHashBiMap(Class<K> var1) {
      EnumMap var2 = new EnumMap(var1);
      HashMap var3 = Maps.newHashMapWithExpectedSize(((Enum[])var1.getEnumConstants()).length);
      super(var2, (Map)var3);
      this.keyType = var1;
   }

   public static <K extends Enum<K>, V extends Object> EnumHashBiMap<K, V> create(Class<K> var0) {
      return new EnumHashBiMap(var0);
   }

   public static <K extends Enum<K>, V extends Object> EnumHashBiMap<K, V> create(Map<K, ? extends V> var0) {
      EnumHashBiMap var1 = create(EnumBiMap.inferKeyType(var0));
      var1.putAll(var0);
      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Class var2 = (Class)var1.readObject();
      this.keyType = var2;
      Class var3 = this.keyType;
      EnumMap var4 = new EnumMap(var3);
      int var5 = ((Enum[])this.keyType.getEnumConstants()).length * 3 / 2;
      HashMap var6 = new HashMap(var5);
      this.setDelegates(var4, var6);
      Serialization.populateMap(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Class var2 = this.keyType;
      var1.writeObject(var2);
      Serialization.writeMap(this, var1);
   }

   public V forcePut(K var1, @Nullable V var2) {
      return super.forcePut(var1, var2);
   }

   public Class<K> keyType() {
      return this.keyType;
   }

   public V put(K var1, @Nullable V var2) {
      return super.put(var1, var2);
   }
}
