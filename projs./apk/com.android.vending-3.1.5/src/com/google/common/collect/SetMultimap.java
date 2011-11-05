package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public interface SetMultimap<K extends Object, V extends Object> extends Multimap<K, V> {

   Map<K, Collection<V>> asMap();

   Set<Entry<K, V>> entries();

   boolean equals(@Nullable Object var1);

   Set<V> get(@Nullable K var1);

   Set<V> removeAll(@Nullable Object var1);

   Set<V> replaceValues(K var1, Iterable<? extends V> var2);
}
