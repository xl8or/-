package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class EmptyImmutableMap extends ImmutableMap<Object, Object> {

   static final EmptyImmutableMap INSTANCE = new EmptyImmutableMap();
   private static final long serialVersionUID;


   private EmptyImmutableMap() {}

   public boolean containsKey(Object var1) {
      return false;
   }

   public boolean containsValue(Object var1) {
      return false;
   }

   public ImmutableSet<Entry<Object, Object>> entrySet() {
      return ImmutableSet.of();
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 instanceof Map) {
         var2 = ((Map)var1).isEmpty();
      } else {
         var2 = false;
      }

      return var2;
   }

   public Object get(Object var1) {
      return null;
   }

   public int hashCode() {
      return 0;
   }

   public boolean isEmpty() {
      return true;
   }

   public ImmutableSet<Object> keySet() {
      return ImmutableSet.of();
   }

   Object readResolve() {
      return INSTANCE;
   }

   public int size() {
      return 0;
   }

   public String toString() {
      return "{}";
   }

   public ImmutableCollection<Object> values() {
      return ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION;
   }
}
