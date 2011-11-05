package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public interface ListMultimap<K extends Object, V extends Object> extends Multimap<K, V> {

   Map<K, Collection<V>> asMap();

   boolean equals(@Nullable Object var1);

   List<V> get(@Nullable K var1);

   List<V> removeAll(@Nullable Object var1);

   List<V> replaceValues(K var1, Iterable<? extends V> var2);
}
