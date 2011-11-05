package com.google.common.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Closeables {

   private static final Logger logger = Logger.getLogger(Closeables.class.getName());


   private Closeables() {}

   public static void close(@Nullable Closeable var0, boolean var1) throws IOException {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var5) {
            if(var1) {
               Logger var3 = logger;
               Level var4 = Level.WARNING;
               var3.log(var4, "IOException thrown while closing Closeable.", var5);
            } else {
               throw var5;
            }
         }
      }
   }

   public static void closeQuietly(@Nullable Closeable var0) {
      try {
         close(var0, (boolean)1);
      } catch (IOException var4) {
         Logger var2 = logger;
         Level var3 = Level.SEVERE;
         var2.log(var3, "IOException should not have been thrown.", var4);
      }
   }
}
