package com.google.common.collect;

import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.CustomConcurrentHashMap.Impl;

final class CustomConcurrentHashMap$Impl$WriteThroughEntry extends AbstractMapEntry<K, V> {

   final K key;
   // $FF: synthetic field
   final Impl this$0;
   V value;


   CustomConcurrentHashMap$Impl$WriteThroughEntry(Impl var1, Object var2, Object var3) {
      this.this$0 = var1;
      this.key = var2;
      this.value = var3;
   }

   public K getKey() {
      return this.key;
   }

   public V getValue() {
      return this.value;
   }

   public V setValue(V var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         Impl var2 = this.this$0;
         Object var3 = this.getKey();
         Object var4 = var2.put(var3, var1);
         this.value = var1;
         return var4;
      }
   }
}
