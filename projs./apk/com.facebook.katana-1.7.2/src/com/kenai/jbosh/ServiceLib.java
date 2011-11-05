package com.kenai.jbosh;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

final class ServiceLib {

   private static final Logger LOG = Logger.getLogger(ServiceLib.class.getName());


   private ServiceLib() {}

   private static <T extends Object> T attemptLoad(Class<T> param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   private static void finalClose(Closeable var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var5) {
            Logger var2 = LOG;
            Level var3 = Level.FINEST;
            String var4 = "Could not close: " + var0;
            var2.log(var3, var4, var5);
         }
      }
   }

   static <T extends Object> T loadService(Class<T> var0) {
      Iterator var1 = loadServicesImplementations(var0).iterator();

      Object var3;
      do {
         if(!var1.hasNext()) {
            StringBuilder var12 = (new StringBuilder()).append("Could not load ");
            String var13 = var0.getName();
            String var14 = var12.append(var13).append(" implementation").toString();
            throw new IllegalStateException(var14);
         }

         String var2 = (String)var1.next();
         var3 = attemptLoad(var0, var2);
      } while(var3 == null);

      Logger var4 = LOG;
      Level var5 = Level.FINEST;
      if(var4.isLoggable(var5)) {
         Logger var6 = LOG;
         StringBuilder var7 = (new StringBuilder()).append("Selected ");
         String var8 = var0.getSimpleName();
         StringBuilder var9 = var7.append(var8).append(" implementation: ");
         String var10 = var3.getClass().getName();
         String var11 = var9.append(var10).toString();
         var6.finest(var11);
      }

      return var3;
   }

   private static List<String> loadServicesImplementations(Class param0) {
      // $FF: Couldn't be decompiled
   }
}
