package org.jivesoftware.smack.util.collections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jivesoftware.smack.util.collections.AbstractReferenceMap;

public class ReferenceMap<K extends Object, V extends Object> extends AbstractReferenceMap<K, V> implements Serializable {

   private static final long serialVersionUID = 1555089888138299607L;


   public ReferenceMap() {
      byte var2 = 0;
      super(0, 1, 16, 0.75F, (boolean)var2);
   }

   public ReferenceMap(int var1, int var2) {
      super(var1, var2, 16, 0.75F, (boolean)0);
   }

   public ReferenceMap(int var1, int var2, int var3, float var4) {
      super(var1, var2, var3, var4, (boolean)0);
   }

   public ReferenceMap(int var1, int var2, int var3, float var4, boolean var5) {
      super(var1, var2, var3, var4, var5);
   }

   public ReferenceMap(int var1, int var2, boolean var3) {
      super(var1, var2, 16, 0.75F, var3);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.doReadObject(var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      this.doWriteObject(var1);
   }
}
