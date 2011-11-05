package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractSetMultimap<K extends Object, V extends Object> extends AbstractMultimap<K, V> implements SetMultimap<K, V> {

   private static final long serialVersionUID = 7431625294878419160L;


   protected AbstractSetMultimap(Map<K, Collection<V>> var1) {
      super(var1);
   }

   abstract Set<V> createCollection();

   public Set<Entry<K, V>> entries() {
      return (Set)super.entries();
   }

   public boolean equals(@Nullable Object var1) {
      return super.equals(var1);
   }

   public Set<V> get(@Nullable K var1) {
      return (Set)super.get(var1);
   }

   public boolean put(K var1, V var2) {
      return super.put(var1, var2);
   }

   public Set<V> removeAll(@Nullable Object var1) {
      return (Set)super.removeAll(var1);
   }

   public Set<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2) {
      return (Set)super.replaceValues(var1, var2);
   }
}
