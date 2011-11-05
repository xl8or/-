package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public interface MapDifference<K extends Object, V extends Object> {

   boolean areEqual();

   Map<K, MapDifference.ValueDifference<V>> entriesDiffering();

   Map<K, V> entriesInCommon();

   Map<K, V> entriesOnlyOnLeft();

   Map<K, V> entriesOnlyOnRight();

   boolean equals(@Nullable Object var1);

   int hashCode();

   public interface ValueDifference<V extends Object> {

      boolean equals(@Nullable Object var1);

      int hashCode();

      V leftValue();

      V rightValue();
   }
}
