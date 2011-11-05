package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

public class ClassLoaderObjectInputStream extends ObjectInputStream {

   private ClassLoader classLoader;


   public ClassLoaderObjectInputStream(ClassLoader var1, InputStream var2) throws IOException, StreamCorruptedException {
      super(var2);
      this.classLoader = var1;
   }

   protected Class resolveClass(ObjectStreamClass var1) throws IOException, ClassNotFoundException {
      String var2 = var1.getName();
      ClassLoader var3 = this.classLoader;
      Class var4 = Class.forName(var2, (boolean)0, var3);
      Class var5;
      if(var4 != null) {
         var5 = var4;
      } else {
         var5 = super.resolveClass(var1);
      }

      return var5;
   }
}
