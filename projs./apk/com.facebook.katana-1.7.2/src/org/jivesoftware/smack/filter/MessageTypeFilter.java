package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class MessageTypeFilter implements PacketFilter {

   private final Message.Type type;


   public MessageTypeFilter(Message.Type var1) {
      this.type = var1;
   }

   public boolean accept(Packet var1) {
      byte var2;
      if(!(var1 instanceof Message)) {
         var2 = 0;
      } else {
         Message.Type var3 = ((Message)var1).getType();
         Message.Type var4 = this.type;
         var2 = var3.equals(var4);
      }

      return (boolean)var2;
   }
}
