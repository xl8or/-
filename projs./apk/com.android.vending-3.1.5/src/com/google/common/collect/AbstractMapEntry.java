package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMapEntry<K extends Object, V extends Object> implements Entry<K, V> {

   AbstractMapEntry() {}

   public boolean equals(@Nullable Object var1) {
      boolean var2 = false;
      if(var1 instanceof Entry) {
         Entry var3 = (Entry)var1;
         Object var4 = this.getKey();
         Object var5 = var3.getKey();
         if(Objects.equal(var4, var5)) {
            Object var6 = this.getValue();
            Object var7 = var3.getValue();
            if(Objects.equal(var6, var7)) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public abstract K getKey();

   public abstract V getValue();

   public int hashCode() {
      int var1 = 0;
      Object var2 = this.getKey();
      Object var3 = this.getValue();
      int var4;
      if(var2 == null) {
         var4 = 0;
      } else {
         var4 = var2.hashCode();
      }

      if(var3 != null) {
         var1 = var3.hashCode();
      }

      return var1 ^ var4;
   }

   public V setValue(V var1) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Object var2 = this.getKey();
      StringBuilder var3 = var1.append(var2).append("=");
      Object var4 = this.getValue();
      return var3.append(var4).toString();
   }
}
