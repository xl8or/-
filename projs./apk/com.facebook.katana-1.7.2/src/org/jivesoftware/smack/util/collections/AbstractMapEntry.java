package org.jivesoftware.smack.util.collections;

import java.util.Map.Entry;
import org.jivesoftware.smack.util.collections.AbstractKeyValue;

public abstract class AbstractMapEntry<K extends Object, V extends Object> extends AbstractKeyValue<K, V> implements Entry<K, V> {

   protected AbstractMapEntry(K var1, V var2) {
      super(var1, var2);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof Entry)) {
         var2 = false;
      } else {
         label36: {
            Entry var7 = (Entry)var1;
            if(this.getKey() == null) {
               if(var7.getKey() != null) {
                  break label36;
               }
            } else {
               Object var3 = this.getKey();
               Object var4 = var7.getKey();
               if(!var3.equals(var4)) {
                  break label36;
               }
            }

            if(this.getValue() == null) {
               if(var7.getValue() != null) {
                  break label36;
               }
            } else {
               Object var5 = this.getValue();
               Object var6 = var7.getValue();
               if(!var5.equals(var6)) {
                  break label36;
               }
            }

            var2 = true;
            return var2;
         }

         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      int var1;
      if(this.getKey() == null) {
         var1 = 0;
      } else {
         var1 = this.getKey().hashCode();
      }

      int var2;
      if(this.getValue() == null) {
         var2 = 0;
      } else {
         var2 = this.getValue().hashCode();
      }

      return var1 ^ var2;
   }

   public V setValue(V var1) {
      Object var2 = this.value;
      this.value = var1;
      return var2;
   }
}
