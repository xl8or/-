package org.jivesoftware.smack.filter;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

public class FromMatchesFilter implements PacketFilter {

   private String address;
   private boolean matchBareJID = 0;


   public FromMatchesFilter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameter cannot be null.");
      } else {
         String var2 = var1.toLowerCase();
         this.address = var2;
         String var3 = StringUtils.parseResource(var1);
         boolean var4 = "".equals(var3);
         this.matchBareJID = var4;
      }
   }

   public boolean accept(Packet var1) {
      byte var2;
      if(var1.getFrom() == null) {
         var2 = 0;
      } else if(this.matchBareJID) {
         String var3 = var1.getFrom().toLowerCase();
         String var4 = this.address;
         var2 = var3.startsWith(var4);
      } else {
         String var5 = this.address;
         String var6 = var1.getFrom().toLowerCase();
         var2 = var5.equals(var6);
      }

      return (boolean)var2;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("FromMatchesFilter: ");
      String var2 = this.address;
      return var1.append(var2).toString();
   }
}
