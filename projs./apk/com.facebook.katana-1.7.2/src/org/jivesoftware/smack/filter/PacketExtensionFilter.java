package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class PacketExtensionFilter implements PacketFilter {

   private String elementName;
   private String namespace;


   public PacketExtensionFilter(String var1) {
      this((String)null, var1);
   }

   public PacketExtensionFilter(String var1, String var2) {
      this.elementName = var1;
      this.namespace = var2;
   }

   public boolean accept(Packet var1) {
      String var2 = this.elementName;
      String var3 = this.namespace;
      boolean var4;
      if(var1.getExtension(var2, var3) != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }
}
