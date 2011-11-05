package com.kenai.jbosh;

import com.kenai.jbosh.QName;

public final class BodyQName {

   static final String BOSH_NS_URI = "http://jabber.org/protocol/httpbind";
   private final QName qname;


   private BodyQName(QName var1) {
      this.qname = var1;
   }

   public static BodyQName create(String var0, String var1) {
      return createWithPrefix(var0, var1, (String)null);
   }

   static BodyQName createBOSH(String var0) {
      return createWithPrefix("http://jabber.org/protocol/httpbind", var0, (String)null);
   }

   public static BodyQName createWithPrefix(String var0, String var1, String var2) {
      if(var0 != null && var0.length() != 0) {
         if(var1 != null && var1.length() != 0) {
            BodyQName var4;
            if(var2 != null && var2.length() != 0) {
               QName var5 = new QName(var0, var1, var2);
               var4 = new BodyQName(var5);
            } else {
               QName var3 = new QName(var0, var1);
               var4 = new BodyQName(var3);
            }

            return var4;
         } else {
            throw new IllegalArgumentException("Local arg is required and may not be null/empty");
         }
      } else {
         throw new IllegalArgumentException("URI is required and may not be null/empty");
      }
   }

   public boolean equals(Object var1) {
      boolean var5;
      if(var1 instanceof BodyQName) {
         BodyQName var2 = (BodyQName)var1;
         QName var3 = this.qname;
         QName var4 = var2.qname;
         var5 = var3.equals(var4);
      } else {
         var5 = false;
      }

      return var5;
   }

   boolean equalsQName(QName var1) {
      return this.qname.equals(var1);
   }

   public String getLocalPart() {
      return this.qname.getLocalPart();
   }

   public String getNamespaceURI() {
      return this.qname.getNamespaceURI();
   }

   public String getPrefix() {
      return this.qname.getPrefix();
   }

   public int hashCode() {
      return this.qname.hashCode();
   }
}
