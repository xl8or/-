package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class ThreadFilter implements PacketFilter {

   private String thread;


   public ThreadFilter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Thread cannot be null.");
      } else {
         this.thread = var1;
      }
   }

   public boolean accept(Packet var1) {
      boolean var4;
      if(var1 instanceof Message) {
         String var2 = this.thread;
         String var3 = ((Message)var1).getThread();
         if(var2.equals(var3)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }
}
