package com.google.common.io;

import java.io.Flushable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Flushables {

   private static final Logger logger = Logger.getLogger(Flushables.class.getName());


   private Flushables() {}

   public static void flush(Flushable var0, boolean var1) throws IOException {
      try {
         var0.flush();
      } catch (IOException var5) {
         Logger var3 = logger;
         Level var4 = Level.WARNING;
         var3.log(var4, "IOException thrown while flushing Flushable.", var5);
         if(!var1) {
            throw var5;
         }
      }
   }

   public static void flushQuietly(Flushable var0) {
      try {
         flush(var0, (boolean)1);
      } catch (IOException var4) {
         Logger var2 = logger;
         Level var3 = Level.SEVERE;
         var2.log(var3, "IOException should not have been thrown.", var4);
      }
   }
}
