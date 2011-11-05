package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface BiMap<K extends Object, V extends Object> extends Map<K, V> {

   V forcePut(@Nullable K var1, @Nullable V var2);

   BiMap<V, K> inverse();

   V put(@Nullable K var1, @Nullable V var2);

   void putAll(Map<? extends K, ? extends V> var1);

   Set<V> values();
}
