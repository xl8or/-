package org.xbill.DNS;

import org.xbill.DNS.Message;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ResolverListener;

class ResolveThread extends Thread {

   private Object id;
   private ResolverListener listener;
   private Message query;
   private Resolver res;


   public ResolveThread(Resolver var1, Message var2, Object var3, ResolverListener var4) {
      this.res = var1;
      this.query = var2;
      this.id = var3;
      this.listener = var4;
   }

   public void run() {
      try {
         Resolver var1 = this.res;
         Message var2 = this.query;
         Message var3 = var1.send(var2);
         ResolverListener var4 = this.listener;
         Object var5 = this.id;
         var4.receiveMessage(var5, var3);
      } catch (Exception var9) {
         ResolverListener var7 = this.listener;
         Object var8 = this.id;
         var7.handleException(var8, var9);
      }
   }
}
