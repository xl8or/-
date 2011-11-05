package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.packet.IQ;

public class Bind extends IQ {

   private String jid = null;
   private String resource = null;


   public Bind() {
      IQ.Type var1 = IQ.Type.SET;
      this.setType(var1);
   }

   public String getChildElementXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<bind xmlns=\"urn:ietf:params:xml:ns:xmpp-bind\">");
      if(this.resource != null) {
         StringBuilder var3 = var1.append("<resource>");
         String var4 = this.resource;
         StringBuilder var5 = var3.append(var4).append("</resource>");
      }

      if(this.jid != null) {
         StringBuilder var6 = var1.append("<jid>");
         String var7 = this.jid;
         StringBuilder var8 = var6.append(var7).append("</jid>");
      }

      StringBuilder var9 = var1.append("</bind>");
      return var1.toString();
   }

   public String getJid() {
      return this.jid;
   }

   public String getResource() {
      return this.resource;
   }

   public void setJid(String var1) {
      this.jid = var1;
   }

   public void setResource(String var1) {
      this.resource = var1;
   }
}
