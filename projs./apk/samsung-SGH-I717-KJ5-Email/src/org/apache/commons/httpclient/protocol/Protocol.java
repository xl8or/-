package org.apache.commons.httpclient.protocol;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.httpclient.util.LangUtils;

public class Protocol {

   private static final Map PROTOCOLS = Collections.synchronizedMap(new HashMap());
   private int defaultPort;
   private String scheme;
   private boolean secure;
   private ProtocolSocketFactory socketFactory;


   public Protocol(String var1, ProtocolSocketFactory var2, int var3) {
      if(var1 == null) {
         throw new IllegalArgumentException("scheme is null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("socketFactory is null");
      } else if(var3 <= 0) {
         String var4 = "port is invalid: " + var3;
         throw new IllegalArgumentException(var4);
      } else {
         this.scheme = var1;
         this.socketFactory = var2;
         this.defaultPort = var3;
         boolean var5 = var2 instanceof SecureProtocolSocketFactory;
         this.secure = var5;
      }
   }

   public Protocol(String var1, SecureProtocolSocketFactory var2, int var3) {
      this(var1, (ProtocolSocketFactory)var2, var3);
   }

   public static Protocol getProtocol(String var0) throws IllegalStateException {
      if(var0 == null) {
         throw new IllegalArgumentException("id is null");
      } else {
         Protocol var1 = (Protocol)PROTOCOLS.get(var0);
         if(var1 == null) {
            var1 = lazyRegisterProtocol(var0);
         }

         return var1;
      }
   }

   private static Protocol lazyRegisterProtocol(String var0) throws IllegalStateException {
      Protocol var3;
      if("http".equals(var0)) {
         DefaultProtocolSocketFactory var1 = DefaultProtocolSocketFactory.getSocketFactory();
         Protocol var2 = new Protocol("http", var1, 80);
         registerProtocol("http", var2);
         var3 = var2;
      } else {
         if(!"https".equals(var0)) {
            String var6 = "unsupported protocol: \'" + var0 + "\'";
            throw new IllegalStateException(var6);
         }

         SSLProtocolSocketFactory var4 = SSLProtocolSocketFactory.getSocketFactory();
         Protocol var5 = new Protocol("https", var4, 443);
         registerProtocol("https", var5);
         var3 = var5;
      }

      return var3;
   }

   public static void registerProtocol(String var0, Protocol var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("id is null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("protocol is null");
      } else {
         PROTOCOLS.put(var0, var1);
      }
   }

   public static void unregisterProtocol(String var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("id is null");
      } else {
         Object var1 = PROTOCOLS.remove(var0);
      }
   }

   public boolean equals(Object var1) {
      boolean var11;
      if(var1 instanceof Protocol) {
         Protocol var2 = (Protocol)var1;
         int var3 = this.defaultPort;
         int var4 = var2.getDefaultPort();
         if(var3 == var4) {
            String var5 = this.scheme;
            String var6 = var2.getScheme();
            if(var5.equalsIgnoreCase(var6)) {
               boolean var7 = this.secure;
               boolean var8 = var2.isSecure();
               if(var7 == var8) {
                  ProtocolSocketFactory var9 = this.socketFactory;
                  ProtocolSocketFactory var10 = var2.getSocketFactory();
                  if(var9.equals(var10)) {
                     var11 = true;
                     return var11;
                  }
               }
            }
         }

         var11 = false;
      } else {
         var11 = false;
      }

      return var11;
   }

   public int getDefaultPort() {
      return this.defaultPort;
   }

   public String getScheme() {
      return this.scheme;
   }

   public ProtocolSocketFactory getSocketFactory() {
      return this.socketFactory;
   }

   public int hashCode() {
      int var1 = this.defaultPort;
      int var2 = LangUtils.hashCode(17, var1);
      String var3 = this.scheme.toLowerCase();
      int var4 = LangUtils.hashCode(var2, var3);
      boolean var5 = this.secure;
      int var6 = LangUtils.hashCode(var4, var5);
      ProtocolSocketFactory var7 = this.socketFactory;
      return LangUtils.hashCode(var6, var7);
   }

   public boolean isSecure() {
      return this.secure;
   }

   public int resolvePort(int var1) {
      int var2;
      if(var1 <= 0) {
         var2 = this.getDefaultPort();
      } else {
         var2 = var1;
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.scheme;
      StringBuilder var3 = var1.append(var2).append(":");
      int var4 = this.defaultPort;
      return var3.append(var4).toString();
   }
}
