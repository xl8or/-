package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.Map;

public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>> extends AbstractBiMap<K, V> {

   private static final long serialVersionUID;
   private transient Class<K> keyType;
   private transient Class<V> valueType;


   private EnumBiMap(Class<K> var1, Class<V> var2) {
      EnumMap var3 = new EnumMap(var1);
      EnumMap var4 = new EnumMap(var2);
      super(var3, (Map)var4);
      this.keyType = var1;
      this.valueType = var2;
   }

   public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> var0, Class<V> var1) {
      return new EnumBiMap(var0, var1);
   }

   public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> var0) {
      Class var1 = inferKeyType(var0);
      Class var2 = inferValueType(var0);
      EnumBiMap var3 = create(var1, var2);
      var3.putAll(var0);
      return var3;
   }

   static <K extends Enum<K>> Class<K> inferKeyType(Map<K, ?> var0) {
      Class var1;
      if(var0 instanceof EnumBiMap) {
         var1 = ((EnumBiMap)var0).keyType();
      } else if(var0 instanceof EnumHashBiMap) {
         var1 = ((EnumHashBiMap)var0).keyType();
      } else {
         byte var2;
         if(!var0.isEmpty()) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         Preconditions.checkArgument((boolean)var2);
         var1 = ((Enum)var0.keySet().iterator().next()).getDeclaringClass();
      }

      return var1;
   }

   private static <V extends Enum<V>> Class<V> inferValueType(Map<?, V> var0) {
      Class var1;
      if(var0 instanceof EnumBiMap) {
         var1 = ((EnumBiMap)var0).valueType;
      } else {
         byte var2;
         if(!var0.isEmpty()) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         Preconditions.checkArgument((boolean)var2);
         var1 = ((Enum)var0.values().iterator().next()).getDeclaringClass();
      }

      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Class var2 = (Class)var1.readObject();
      this.keyType = var2;
      Class var3 = (Class)var1.readObject();
      this.valueType = var3;
      Class var4 = this.keyType;
      EnumMap var5 = new EnumMap(var4);
      Class var6 = this.valueType;
      EnumMap var7 = new EnumMap(var6);
      this.setDelegates(var5, var7);
      Serialization.populateMap(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Class var2 = this.keyType;
      var1.writeObject(var2);
      Class var3 = this.valueType;
      var1.writeObject(var3);
      Serialization.writeMap(this, var1);
   }

   public Class<K> keyType() {
      return this.keyType;
   }

   public Class<V> valueType() {
      return this.valueType;
   }
}
