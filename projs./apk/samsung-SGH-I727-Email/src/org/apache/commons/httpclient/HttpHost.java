package org.apache.commons.httpclient;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.LangUtils;

public class HttpHost implements Cloneable {

   private String hostname;
   private int port;
   private Protocol protocol;


   public HttpHost(String var1) {
      Protocol var2 = Protocol.getProtocol("http");
      this(var1, -1, var2);
   }

   public HttpHost(String var1, int var2) {
      Protocol var3 = Protocol.getProtocol("http");
      this(var1, var2, var3);
   }

   public HttpHost(String var1, int var2, Protocol var3) {
      this.hostname = null;
      this.port = -1;
      this.protocol = null;
      if(var1 == null) {
         throw new IllegalArgumentException("Host name may not be null");
      } else if(var3 == null) {
         throw new IllegalArgumentException("Protocol may not be null");
      } else {
         this.hostname = var1;
         this.protocol = var3;
         if(var2 >= 0) {
            this.port = var2;
         } else {
            int var4 = this.protocol.getDefaultPort();
            this.port = var4;
         }
      }
   }

   public HttpHost(HttpHost var1) {
      this.hostname = null;
      this.port = -1;
      this.protocol = null;
      this.init(var1);
   }

   public HttpHost(URI var1) throws URIException {
      String var2 = var1.getHost();
      int var3 = var1.getPort();
      Protocol var4 = Protocol.getProtocol(var1.getScheme());
      this(var2, var3, var4);
   }

   private void init(HttpHost var1) {
      String var2 = var1.hostname;
      this.hostname = var2;
      int var3 = var1.port;
      this.port = var3;
      Protocol var4 = var1.protocol;
      this.protocol = var4;
   }

   public Object clone() throws CloneNotSupportedException {
      HttpHost var1 = (HttpHost)super.clone();
      var1.init(this);
      return var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 instanceof HttpHost) {
         if(var1 == this) {
            var2 = true;
         } else {
            HttpHost var3 = (HttpHost)var1;
            String var4 = this.hostname;
            String var5 = var3.hostname;
            if(!var4.equalsIgnoreCase(var5)) {
               var2 = false;
            } else {
               int var6 = this.port;
               int var7 = var3.port;
               if(var6 != var7) {
                  var2 = false;
               } else {
                  Protocol var8 = this.protocol;
                  Protocol var9 = var3.protocol;
                  if(!var8.equals(var9)) {
                     var2 = false;
                  } else {
                     var2 = true;
                  }
               }
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getHostName() {
      return this.hostname;
   }

   public int getPort() {
      return this.port;
   }

   public Protocol getProtocol() {
      return this.protocol;
   }

   public int hashCode() {
      String var1 = this.hostname;
      int var2 = LangUtils.hashCode(17, var1);
      int var3 = this.port;
      int var4 = LangUtils.hashCode(var2, var3);
      Protocol var5 = this.protocol;
      return LangUtils.hashCode(var4, var5);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(50);
      String var2 = this.toURI();
      var1.append(var2);
      return var1.toString();
   }

   public String toURI() {
      StringBuffer var1 = new StringBuffer(50);
      String var2 = this.protocol.getScheme();
      var1.append(var2);
      StringBuffer var4 = var1.append("://");
      String var5 = this.hostname;
      var1.append(var5);
      int var7 = this.port;
      int var8 = this.protocol.getDefaultPort();
      if(var7 != var8) {
         StringBuffer var9 = var1.append(':');
         int var10 = this.port;
         var1.append(var10);
      }

      return var1.toString();
   }
}
