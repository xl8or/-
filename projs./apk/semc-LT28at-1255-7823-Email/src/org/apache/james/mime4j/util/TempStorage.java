package org.apache.james.mime4j.util;

import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.util.SimpleTempStorage;
import org.apache.james.mime4j.util.TempPath;

public abstract class TempStorage {

   private static TempStorage inst = null;
   private static Log log = LogFactory.getLog(TempStorage.class);


   static {
      String var0 = System.getProperty("org.apache.james.mime4j.tempStorage");

      try {
         if(inst != null) {
            inst = (TempStorage)Class.forName(var0).newInstance();
         }
      } catch (Throwable var4) {
         Log var2 = log;
         String var3 = "Unable to create or instantiate TempStorage class \'" + var0 + "\' using SimpleTempStorage instead";
         var2.warn(var3, var4);
      }

      if(inst == null) {
         inst = new SimpleTempStorage();
      }
   }

   public TempStorage() {}

   public static TempStorage getInstance() {
      return inst;
   }

   public static void setInstance(TempStorage var0) {
      if(var0 == null) {
         throw new NullPointerException("inst");
      } else {
         inst = var0;
      }
   }

   public abstract TempPath getRootTempPath();
}
