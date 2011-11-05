package org.jivesoftware.smack.util.collections;

import org.jivesoftware.smack.util.collections.KeyValue;

public abstract class AbstractKeyValue<K extends Object, V extends Object> implements KeyValue<K, V> {

   protected K key;
   protected V value;


   protected AbstractKeyValue(K var1, V var2) {
      this.key = var1;
      this.value = var2;
   }

   public K getKey() {
      return this.key;
   }

   public V getValue() {
      return this.value;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Object var2 = this.getKey();
      StringBuilder var3 = var1.append(var2).append('=');
      Object var4 = this.getValue();
      return var3.append(var4).toString();
   }
}
