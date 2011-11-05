package org.jivesoftware.smack;

import java.net.URI;
import java.net.URISyntaxException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.proxy.ProxyInfo;

public class BOSHConfiguration extends ConnectionConfiguration {

   private String file;
   private boolean ssl;


   public BOSHConfiguration(String var1) {
      super(var1, 7070);
      this.setSASLAuthenticationEnabled((boolean)1);
      this.ssl = (boolean)0;
      this.file = "/http-bind/";
   }

   public BOSHConfiguration(String var1, int var2) {
      super(var1, var2);
      this.setSASLAuthenticationEnabled((boolean)1);
      this.ssl = (boolean)0;
      this.file = "/http-bind/";
   }

   public BOSHConfiguration(boolean var1, String var2, int var3, String var4, String var5) {
      super(var2, var3, var5);
      this.setSASLAuthenticationEnabled((boolean)1);
      this.ssl = var1;
      String var6;
      if(var4 != null) {
         var6 = var4;
      } else {
         var6 = "/";
      }

      this.file = var6;
   }

   public BOSHConfiguration(boolean var1, String var2, int var3, String var4, ProxyInfo var5, String var6) {
      super(var2, var3, var6, var5);
      this.setSASLAuthenticationEnabled((boolean)1);
      this.ssl = var1;
      String var7;
      if(var4 != null) {
         var7 = var4;
      } else {
         var7 = "/";
      }

      this.file = var7;
   }

   public String getProxyAddress() {
      String var1;
      if(this.proxy != null) {
         var1 = this.proxy.getProxyAddress();
      } else {
         var1 = null;
      }

      return var1;
   }

   public ProxyInfo getProxyInfo() {
      return this.proxy;
   }

   public int getProxyPort() {
      int var1;
      if(this.proxy != null) {
         var1 = this.proxy.getProxyPort();
      } else {
         var1 = 8080;
      }

      return var1;
   }

   public URI getURI() throws URISyntaxException {
      if(this.file.charAt(0) != 47) {
         StringBuilder var1 = (new StringBuilder()).append('/');
         String var2 = this.file;
         String var3 = var1.append(var2).toString();
         this.file = var3;
      }

      URI var4 = new URI;
      StringBuilder var5 = new StringBuilder();
      String var6;
      if(this.ssl) {
         var6 = "https://";
      } else {
         var6 = "http://";
      }

      StringBuilder var7 = var5.append(var6);
      String var8 = this.getHost();
      StringBuilder var9 = var7.append(var8).append(":");
      int var10 = this.getPort();
      StringBuilder var11 = var9.append(var10);
      String var12 = this.file;
      String var13 = var11.append(var12).toString();
      var4.<init>(var13);
      return var4;
   }

   public boolean isProxyEnabled() {
      boolean var3;
      if(this.proxy != null) {
         ProxyInfo.ProxyType var1 = this.proxy.getProxyType();
         ProxyInfo.ProxyType var2 = ProxyInfo.ProxyType.NONE;
         if(var1 != var2) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public boolean isUsingSSL() {
      return this.ssl;
   }
}
