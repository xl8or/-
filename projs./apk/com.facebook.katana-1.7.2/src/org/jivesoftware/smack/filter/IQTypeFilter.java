package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

public class IQTypeFilter implements PacketFilter {

   private IQ.Type type;


   public IQTypeFilter(IQ.Type var1) {
      this.type = var1;
   }

   public boolean accept(Packet var1) {
      boolean var4;
      if(var1 instanceof IQ) {
         IQ.Type var2 = ((IQ)var1).getType();
         IQ.Type var3 = this.type;
         if(var2.equals(var3)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }
}
