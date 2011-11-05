package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingObject;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMapEntry<K extends Object, V extends Object> extends ForwardingObject implements Entry<K, V> {

   public ForwardingMapEntry() {}

   protected abstract Entry<K, V> delegate();

   public boolean equals(@Nullable Object var1) {
      return this.delegate().equals(var1);
   }

   public K getKey() {
      return this.delegate().getKey();
   }

   public V getValue() {
      return this.delegate().getValue();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public V setValue(V var1) {
      return this.delegate().setValue(var1);
   }
}
