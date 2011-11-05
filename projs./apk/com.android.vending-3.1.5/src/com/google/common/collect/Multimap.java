package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multimap<K extends Object, V extends Object> {

   Map<K, Collection<V>> asMap();

   void clear();

   boolean containsEntry(@Nullable Object var1, @Nullable Object var2);

   boolean containsKey(@Nullable Object var1);

   boolean containsValue(@Nullable Object var1);

   Collection<Entry<K, V>> entries();

   boolean equals(@Nullable Object var1);

   Collection<V> get(@Nullable K var1);

   int hashCode();

   boolean isEmpty();

   Set<K> keySet();

   Multiset<K> keys();

   boolean put(@Nullable K var1, @Nullable V var2);

   boolean putAll(Multimap<? extends K, ? extends V> var1);

   boolean putAll(@Nullable K var1, Iterable<? extends V> var2);

   boolean remove(@Nullable Object var1, @Nullable Object var2);

   Collection<V> removeAll(@Nullable Object var1);

   Collection<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2);

   int size();

   Collection<V> values();
}
