package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingObject;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMap<K extends Object, V extends Object> extends ForwardingObject implements Map<K, V> {

   public ForwardingMap() {}

   public void clear() {
      this.delegate().clear();
   }

   public boolean containsKey(Object var1) {
      return this.delegate().containsKey(var1);
   }

   public boolean containsValue(Object var1) {
      return this.delegate().containsValue(var1);
   }

   protected abstract Map<K, V> delegate();

   public Set<Entry<K, V>> entrySet() {
      return this.delegate().entrySet();
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public V get(Object var1) {
      return this.delegate().get(var1);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public Set<K> keySet() {
      return this.delegate().keySet();
   }

   public V put(K var1, V var2) {
      return this.delegate().put(var1, var2);
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      this.delegate().putAll(var1);
   }

   public V remove(Object var1) {
      return this.delegate().remove(var1);
   }

   public int size() {
      return this.delegate().size();
   }

   public Collection<V> values() {
      return this.delegate().values();
   }
}
