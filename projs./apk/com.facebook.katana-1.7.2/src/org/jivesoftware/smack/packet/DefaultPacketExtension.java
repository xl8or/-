package org.jivesoftware.smack.packet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.smack.packet.PacketExtension;

public class DefaultPacketExtension implements PacketExtension {

   private String elementName;
   private Map<String, String> map;
   private String namespace;


   public DefaultPacketExtension(String var1, String var2) {
      this.elementName = var1;
      this.namespace = var2;
   }

   public String getElementName() {
      return this.elementName;
   }

   public Collection<String> getNames() {
      synchronized(this){}
      boolean var6 = false;

      Set var1;
      Set var2;
      label55: {
         try {
            var6 = true;
            if(this.map == null) {
               var1 = Collections.emptySet();
               var6 = false;
               break label55;
            }

            Map var3 = this.map;
            var1 = Collections.unmodifiableSet((new HashMap(var3)).keySet());
            var6 = false;
         } finally {
            if(var6) {
               ;
            }
         }

         var2 = var1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String getValue(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void setValue(String var1, String var2) {
      synchronized(this){}

      try {
         if(this.map == null) {
            HashMap var3 = new HashMap();
            this.map = var3;
         }

         this.map.put(var1, var2);
      } finally {
         ;
      }

   }

   public String toXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<");
      String var3 = this.elementName;
      StringBuilder var4 = var2.append(var3).append(" xmlns=\"");
      String var5 = this.namespace;
      StringBuilder var6 = var4.append(var5).append("\">");

      String var8;
      StringBuilder var12;
      for(Iterator var7 = this.getNames().iterator(); var7.hasNext(); var12 = var1.append("</").append(var8).append(">")) {
         var8 = (String)var7.next();
         String var9 = this.getValue(var8);
         StringBuilder var10 = var1.append("<").append(var8).append(">");
         var1.append(var9);
      }

      StringBuilder var13 = var1.append("</");
      String var14 = this.elementName;
      StringBuilder var15 = var13.append(var14).append(">");
      return var1.toString();
   }
}
