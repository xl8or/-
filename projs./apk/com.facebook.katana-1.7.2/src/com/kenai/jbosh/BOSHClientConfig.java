package com.kenai.jbosh;

import java.net.URI;
import javax.net.ssl.SSLContext;

public final class BOSHClientConfig {

   private final boolean compressionEnabled;
   private final String from;
   private final String lang;
   private final String proxyHost;
   private final int proxyPort;
   private final String route;
   private final SSLContext sslContext;
   private final String to;
   private final URI uri;


   private BOSHClientConfig(URI var1, String var2, String var3, String var4, String var5, String var6, int var7, SSLContext var8, boolean var9) {
      this.uri = var1;
      this.to = var2;
      this.from = var3;
      this.lang = var4;
      this.route = var5;
      this.proxyHost = var6;
      this.proxyPort = var7;
      this.sslContext = var8;
      this.compressionEnabled = var9;
   }

   // $FF: synthetic method
   BOSHClientConfig(URI var1, String var2, String var3, String var4, String var5, String var6, int var7, SSLContext var8, boolean var9, BOSHClientConfig.1 var10) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public String getFrom() {
      return this.from;
   }

   public String getLang() {
      return this.lang;
   }

   public String getProxyHost() {
      return this.proxyHost;
   }

   public int getProxyPort() {
      return this.proxyPort;
   }

   public String getRoute() {
      return this.route;
   }

   public SSLContext getSSLContext() {
      return this.sslContext;
   }

   public String getTo() {
      return this.to;
   }

   public URI getURI() {
      return this.uri;
   }

   boolean isCompressionEnabled() {
      return this.compressionEnabled;
   }

   public static final class Builder {

      private Boolean bCompression;
      private final String bDomain;
      private String bFrom;
      private String bLang;
      private String bProxyHost;
      private int bProxyPort;
      private String bRoute;
      private SSLContext bSSLContext;
      private final URI bURI;


      private Builder(URI var1, String var2) {
         this.bURI = var1;
         this.bDomain = var2;
      }

      public static BOSHClientConfig.Builder create(BOSHClientConfig var0) {
         URI var1 = var0.getURI();
         String var2 = var0.getTo();
         BOSHClientConfig.Builder var3 = new BOSHClientConfig.Builder(var1, var2);
         String var4 = var0.getFrom();
         var3.bFrom = var4;
         String var5 = var0.getLang();
         var3.bLang = var5;
         String var6 = var0.getRoute();
         var3.bRoute = var6;
         String var7 = var0.getProxyHost();
         var3.bProxyHost = var7;
         int var8 = var0.getProxyPort();
         var3.bProxyPort = var8;
         SSLContext var9 = var0.getSSLContext();
         var3.bSSLContext = var9;
         Boolean var10 = Boolean.valueOf(var0.isCompressionEnabled());
         var3.bCompression = var10;
         return var3;
      }

      public static BOSHClientConfig.Builder create(URI var0, String var1) {
         if(var0 == null) {
            throw new IllegalArgumentException("Connection manager URI must not be null");
         } else if(var1 == null) {
            throw new IllegalArgumentException("Target domain must not be null");
         } else {
            String var2 = var0.getScheme();
            if(!"http".equals(var2) && !"https".equals(var2)) {
               throw new IllegalArgumentException("Only \'http\' and \'https\' URI are allowed");
            } else {
               return new BOSHClientConfig.Builder(var0, var1);
            }
         }
      }

      public BOSHClientConfig build() {
         String var1;
         if(this.bLang == null) {
            var1 = "en";
         } else {
            var1 = this.bLang;
         }

         int var2;
         if(this.bProxyHost == null) {
            var2 = 0;
         } else {
            var2 = this.bProxyPort;
         }

         byte var3;
         if(this.bCompression == null) {
            var3 = 0;
         } else {
            var3 = this.bCompression.booleanValue();
         }

         URI var4 = this.bURI;
         String var5 = this.bDomain;
         String var6 = this.bFrom;
         String var7 = this.bRoute;
         String var8 = this.bProxyHost;
         SSLContext var9 = this.bSSLContext;
         return new BOSHClientConfig(var4, var5, var6, var1, var7, var8, var2, var9, (boolean)var3, (BOSHClientConfig.1)null);
      }

      public BOSHClientConfig.Builder setCompressionEnabled(boolean var1) {
         Boolean var2 = Boolean.valueOf(var1);
         this.bCompression = var2;
         return this;
      }

      public BOSHClientConfig.Builder setFrom(String var1) {
         if(var1 == null) {
            throw new IllegalArgumentException("Client ID must not be null");
         } else {
            this.bFrom = var1;
            return this;
         }
      }

      public BOSHClientConfig.Builder setProxy(String var1, int var2) {
         if(var1 != null && var1.length() != 0) {
            if(var2 <= 0) {
               throw new IllegalArgumentException("Proxy port must be > 0");
            } else {
               this.bProxyHost = var1;
               this.bProxyPort = var2;
               return this;
            }
         } else {
            throw new IllegalArgumentException("Proxy host name cannot be null or empty");
         }
      }

      public BOSHClientConfig.Builder setRoute(String var1, String var2, int var3) {
         if(var1 == null) {
            throw new IllegalArgumentException("Protocol cannot be null");
         } else if(var1.contains(":")) {
            throw new IllegalArgumentException("Protocol cannot contain the \':\' character");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Host cannot be null");
         } else if(var2.contains(":")) {
            throw new IllegalArgumentException("Host cannot contain the \':\' character");
         } else if(var3 <= 0) {
            throw new IllegalArgumentException("Port number must be > 0");
         } else {
            String var4 = var1 + ":" + var2 + ":" + var3;
            this.bRoute = var4;
            return this;
         }
      }

      public BOSHClientConfig.Builder setSSLContext(SSLContext var1) {
         if(var1 == null) {
            throw new IllegalArgumentException("SSL context cannot be null");
         } else {
            this.bSSLContext = var1;
            return this;
         }
      }

      public BOSHClientConfig.Builder setXMLLang(String var1) {
         if(var1 == null) {
            throw new IllegalArgumentException("Default language ID must not be null");
         } else {
            this.bLang = var1;
            return this;
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
