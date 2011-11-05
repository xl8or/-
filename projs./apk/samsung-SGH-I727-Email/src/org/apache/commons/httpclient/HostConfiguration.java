package org.apache.commons.httpclient;

import java.net.InetAddress;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.params.HostParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.LangUtils;

public class HostConfiguration implements Cloneable {

   public static final HostConfiguration ANY_HOST_CONFIGURATION = new HostConfiguration();
   private HttpHost host = null;
   private InetAddress localAddress = null;
   private HostParams params;
   private ProxyHost proxyHost = null;


   public HostConfiguration() {
      HostParams var1 = new HostParams();
      this.params = var1;
   }

   public HostConfiguration(HostConfiguration var1) {
      HostParams var2 = new HostParams();
      this.params = var2;
      this.init(var1);
   }

   private void init(HostConfiguration param1) {
      // $FF: Couldn't be decompiled
   }

   public Object clone() {
      HostConfiguration var1;
      try {
         var1 = (HostConfiguration)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new IllegalArgumentException("Host configuration could not be cloned");
      }

      var1.init(this);
      return var1;
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public String getHost() {
      synchronized(this){}
      boolean var5 = false;

      String var1;
      String var2;
      label50: {
         try {
            var5 = true;
            if(this.host != null) {
               var1 = this.host.getHostName();
               var5 = false;
               break label50;
            }

            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public String getHostURL() {
      synchronized(this){}
      boolean var5 = false;

      String var2;
      try {
         var5 = true;
         if(this.host == null) {
            throw new IllegalStateException("Host must be set to create a host URL");
         }

         var2 = this.host.toURI();
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var2;
   }

   public InetAddress getLocalAddress() {
      synchronized(this){}

      InetAddress var1;
      try {
         var1 = this.localAddress;
      } finally {
         ;
      }

      return var1;
   }

   public HostParams getParams() {
      return this.params;
   }

   public int getPort() {
      synchronized(this){}
      boolean var5 = false;

      int var1;
      int var2;
      label50: {
         try {
            var5 = true;
            if(this.host != null) {
               var1 = this.host.getPort();
               var5 = false;
               break label50;
            }

            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         var2 = -1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public Protocol getProtocol() {
      synchronized(this){}
      boolean var5 = false;

      Protocol var1;
      Protocol var2;
      label50: {
         try {
            var5 = true;
            if(this.host != null) {
               var1 = this.host.getProtocol();
               var5 = false;
               break label50;
            }

            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public String getProxyHost() {
      synchronized(this){}
      boolean var5 = false;

      String var1;
      String var2;
      label50: {
         try {
            var5 = true;
            if(this.proxyHost != null) {
               var1 = this.proxyHost.getHostName();
               var5 = false;
               break label50;
            }

            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public int getProxyPort() {
      synchronized(this){}
      boolean var5 = false;

      int var1;
      int var2;
      label50: {
         try {
            var5 = true;
            if(this.proxyHost != null) {
               var1 = this.proxyHost.getPort();
               var5 = false;
               break label50;
            }

            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         var2 = -1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public String getVirtualHost() {
      synchronized(this){}
      boolean var5 = false;

      String var1;
      try {
         var5 = true;
         var1 = this.params.getVirtualHost();
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public int hashCode() {
      synchronized(this){}
      boolean var10 = false;

      int var6;
      try {
         var10 = true;
         HttpHost var1 = this.host;
         int var2 = LangUtils.hashCode(17, var1);
         ProxyHost var3 = this.proxyHost;
         int var4 = LangUtils.hashCode(var2, var3);
         InetAddress var5 = this.localAddress;
         var6 = LangUtils.hashCode(var4, var5);
         var10 = false;
      } finally {
         if(var10) {
            ;
         }
      }

      return var6;
   }

   public boolean hostEquals(HttpConnection param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isHostSet() {
      synchronized(this){}
      boolean var5 = false;

      HttpHost var1;
      try {
         var5 = true;
         var1 = this.host;
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      boolean var2;
      if(var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isProxySet() {
      synchronized(this){}
      boolean var5 = false;

      ProxyHost var1;
      try {
         var5 = true;
         var1 = this.proxyHost;
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      boolean var2;
      if(var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean proxyEquals(HttpConnection param1) {
      // $FF: Couldn't be decompiled
   }

   public void setHost(String var1) {
      synchronized(this){}

      try {
         Protocol var2 = Protocol.getProtocol("http");
         int var3 = var2.getDefaultPort();
         this.setHost(var1, var3, var2);
      } finally {
         ;
      }

   }

   public void setHost(String var1, int var2) {
      synchronized(this){}

      try {
         Protocol var3 = Protocol.getProtocol("http");
         this.setHost(var1, var2, var3);
      } finally {
         ;
      }

   }

   public void setHost(String var1, int var2, String var3) {
      synchronized(this){}

      try {
         Protocol var4 = Protocol.getProtocol(var3);
         HttpHost var5 = new HttpHost(var1, var2, var4);
         this.host = var5;
      } finally {
         ;
      }

   }

   public void setHost(String param1, int param2, Protocol param3) {
      // $FF: Couldn't be decompiled
   }

   public void setHost(String var1, String var2, int var3, Protocol var4) {
      synchronized(this){}

      try {
         this.setHost(var1, var3, var4);
         this.params.setVirtualHost(var2);
      } finally {
         ;
      }

   }

   public void setHost(HttpHost var1) {
      synchronized(this){}

      try {
         this.host = var1;
      } finally {
         ;
      }

   }

   public void setHost(URI var1) {
      synchronized(this){}

      try {
         String var2 = var1.getHost();
         int var3 = var1.getPort();
         String var4 = var1.getScheme();
         this.setHost(var2, var3, var4);
      } catch (URIException var10) {
         String var6 = var10.toString();
         throw new IllegalArgumentException(var6);
      } finally {
         ;
      }

   }

   public void setLocalAddress(InetAddress var1) {
      synchronized(this){}

      try {
         this.localAddress = var1;
      } finally {
         ;
      }

   }

   public void setParams(HostParams var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else {
         this.params = var1;
      }
   }

   public void setProxy(String var1, int var2) {
      synchronized(this){}

      try {
         ProxyHost var3 = new ProxyHost(var1, var2);
         this.proxyHost = var3;
      } finally {
         ;
      }

   }

   public void setProxyHost(ProxyHost var1) {
      synchronized(this){}

      try {
         this.proxyHost = var1;
      } finally {
         ;
      }

   }

   public String toString() {
      // $FF: Couldn't be decompiled
   }
}
