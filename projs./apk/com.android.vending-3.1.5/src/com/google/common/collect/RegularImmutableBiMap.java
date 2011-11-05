package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import java.util.Iterator;
import java.util.Map.Entry;

@GwtCompatible(
   serializable = true
)
class RegularImmutableBiMap<K extends Object, V extends Object> extends ImmutableBiMap<K, V> {

   final transient ImmutableMap<K, V> delegate;
   final transient ImmutableBiMap<V, K> inverse;


   RegularImmutableBiMap(ImmutableMap<K, V> var1) {
      this.delegate = var1;
      ImmutableMap.Builder var2 = ImmutableMap.builder();
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getValue();
         Object var6 = var4.getKey();
         var2.put(var5, var6);
      }

      ImmutableMap var8 = var2.build();
      RegularImmutableBiMap var9 = new RegularImmutableBiMap(var8, this);
      this.inverse = var9;
   }

   RegularImmutableBiMap(ImmutableMap<K, V> var1, ImmutableBiMap<V, K> var2) {
      this.delegate = var1;
      this.inverse = var2;
   }

   ImmutableMap<K, V> delegate() {
      return this.delegate;
   }

   public ImmutableBiMap<V, K> inverse() {
      return this.inverse;
   }
}
