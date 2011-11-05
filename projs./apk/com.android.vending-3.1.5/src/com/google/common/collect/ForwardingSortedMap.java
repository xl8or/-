package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingMap;
import java.util.Comparator;
import java.util.SortedMap;

@GwtCompatible
public abstract class ForwardingSortedMap<K extends Object, V extends Object> extends ForwardingMap<K, V> implements SortedMap<K, V> {

   public ForwardingSortedMap() {}

   public Comparator<? super K> comparator() {
      return this.delegate().comparator();
   }

   protected abstract SortedMap<K, V> delegate();

   public K firstKey() {
      return this.delegate().firstKey();
   }

   public SortedMap<K, V> headMap(K var1) {
      return this.delegate().headMap(var1);
   }

   public K lastKey() {
      return this.delegate().lastKey();
   }

   public SortedMap<K, V> subMap(K var1, K var2) {
      return this.delegate().subMap(var1, var2);
   }

   public SortedMap<K, V> tailMap(K var1) {
      return this.delegate().tailMap(var1);
   }
}
