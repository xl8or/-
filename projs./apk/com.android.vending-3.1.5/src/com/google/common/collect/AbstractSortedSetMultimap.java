package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.SortedSetMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractSortedSetMultimap<K extends Object, V extends Object> extends AbstractSetMultimap<K, V> implements SortedSetMultimap<K, V> {

   private static final long serialVersionUID = 430848587173315748L;


   protected AbstractSortedSetMultimap(Map<K, Collection<V>> var1) {
      super(var1);
   }

   abstract SortedSet<V> createCollection();

   public SortedSet<V> get(@Nullable K var1) {
      return (SortedSet)super.get(var1);
   }

   public SortedSet<V> removeAll(@Nullable Object var1) {
      return (SortedSet)super.removeAll(var1);
   }

   public SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2) {
      return (SortedSet)super.replaceValues(var1, var2);
   }

   public Collection<V> values() {
      return super.values();
   }
}
