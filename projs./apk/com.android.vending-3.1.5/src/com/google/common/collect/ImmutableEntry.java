package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapEntry;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible
class ImmutableEntry<K extends Object, V extends Object> extends AbstractMapEntry<K, V> implements Serializable {

   private static final long serialVersionUID;
   private final K key;
   private final V value;


   ImmutableEntry(@Nullable K var1, @Nullable V var2) {
      this.key = var1;
      this.value = var2;
   }

   public K getKey() {
      return this.key;
   }

   public V getValue() {
      return this.value;
   }
}
