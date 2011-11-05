package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import org.xbill.DNS.Options;
import org.xbill.DNS.utils.hexdump;

class Client {

   protected long endTime;
   protected SelectionKey key;


   protected Client(SelectableChannel var1, long var2) throws IOException {
      this.endTime = var2;

      Selector var4;
      label134: {
         Selector var10;
         Throwable var11;
         label135: {
            try {
               var4 = Selector.open();
            } catch (Throwable var18) {
               var10 = null;
               var11 = var18;
               break label135;
            }

            Selector var5 = var4;
            byte var6 = 0;

            label125:
            try {
               var1.configureBlocking((boolean)var6);
               SelectionKey var8 = var1.register(var5, 1);
               this.key = var8;
               break label134;
            } catch (Throwable var17) {
               var10 = var4;
               var11 = var17;
               break label125;
            }
         }

         if(true && var10 != null) {
            var10.close();
         }

         if(true) {
            var1.close();
         }

         throw var11;
      }

      if(false && var4 != null) {
         var4.close();
      }

      if(false) {
         var1.close();
      }
   }

   protected static void blockUntil(SelectionKey var0, long var1) throws IOException {
      long var3 = System.currentTimeMillis();
      long var5 = var1 - var3;
      int var7;
      if(var5 > 0L) {
         var7 = var0.selector().select(var5);
      } else if(var5 == 0L) {
         var7 = var0.selector().selectNow();
      } else {
         var7 = 0;
      }

      if(var7 == 0) {
         throw new SocketTimeoutException();
      }
   }

   protected static void verboseLog(String var0, byte[] var1) {
      if(Options.check("verbosemsg")) {
         PrintStream var2 = System.err;
         String var3 = hexdump.dump(var0, var1);
         var2.println(var3);
      }
   }

   void cleanup() throws IOException {
      this.key.selector().close();
      this.key.channel().close();
   }
}
