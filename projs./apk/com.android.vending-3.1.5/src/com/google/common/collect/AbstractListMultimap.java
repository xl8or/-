package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.ListMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractListMultimap<K extends Object, V extends Object> extends AbstractMultimap<K, V> implements ListMultimap<K, V> {

   private static final long serialVersionUID = 6588350623831699109L;


   protected AbstractListMultimap(Map<K, Collection<V>> var1) {
      super(var1);
   }

   abstract List<V> createCollection();

   public boolean equals(@Nullable Object var1) {
      return super.equals(var1);
   }

   public List<V> get(@Nullable K var1) {
      return (List)super.get(var1);
   }

   public boolean put(@Nullable K var1, @Nullable V var2) {
      return super.put(var1, var2);
   }

   public List<V> removeAll(@Nullable Object var1) {
      return (List)super.removeAll(var1);
   }

   public List<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2) {
      return (List)super.replaceValues(var1, var2);
   }
}
