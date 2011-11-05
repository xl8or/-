package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public interface SortedSetMultimap<K extends Object, V extends Object> extends SetMultimap<K, V> {

   Map<K, Collection<V>> asMap();

   SortedSet<V> get(@Nullable K var1);

   SortedSet<V> removeAll(@Nullable Object var1);

   SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2);

   Comparator<? super V> valueComparator();
}
