package org.jivesoftware.smack.util.collections;

import java.util.Map.Entry;
import org.jivesoftware.smack.util.collections.AbstractMapEntry;
import org.jivesoftware.smack.util.collections.KeyValue;

public final class DefaultMapEntry<K extends Object, V extends Object> extends AbstractMapEntry<K, V> {

   public DefaultMapEntry(K var1, V var2) {
      super(var1, var2);
   }

   public DefaultMapEntry(Entry<K, V> var1) {
      Object var2 = var1.getKey();
      Object var3 = var1.getValue();
      super(var2, var3);
   }

   public DefaultMapEntry(KeyValue<K, V> var1) {
      Object var2 = var1.getKey();
      Object var3 = var1.getValue();
      super(var2, var3);
   }
}
