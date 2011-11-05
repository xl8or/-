package org.jivesoftware.smack.proxy;

import javax.net.SocketFactory;
import org.jivesoftware.smack.proxy.DirectSocketFactory;
import org.jivesoftware.smack.proxy.HTTPProxySocketFactory;
import org.jivesoftware.smack.proxy.Socks4ProxySocketFactory;
import org.jivesoftware.smack.proxy.Socks5ProxySocketFactory;

public class ProxyInfo {

   private String proxyAddress;
   private String proxyPassword;
   private int proxyPort;
   private ProxyInfo.ProxyType proxyType;
   private String proxyUsername;


   public ProxyInfo(ProxyInfo.ProxyType var1, String var2, int var3, String var4, String var5) {
      this.proxyType = var1;
      this.proxyAddress = var2;
      this.proxyPort = var3;
      this.proxyUsername = var4;
      this.proxyPassword = var5;
   }

   public static ProxyInfo forDefaultProxy() {
      ProxyInfo.ProxyType var0 = ProxyInfo.ProxyType.NONE;
      Object var1 = null;
      Object var2 = null;
      return new ProxyInfo(var0, (String)null, 0, (String)var1, (String)var2);
   }

   public static ProxyInfo forHttpProxy(String var0, int var1, String var2, String var3) {
      ProxyInfo.ProxyType var4 = ProxyInfo.ProxyType.HTTP;
      return new ProxyInfo(var4, var0, var1, var2, var3);
   }

   public static ProxyInfo forNoProxy() {
      ProxyInfo.ProxyType var0 = ProxyInfo.ProxyType.NONE;
      Object var1 = null;
      Object var2 = null;
      return new ProxyInfo(var0, (String)null, 0, (String)var1, (String)var2);
   }

   public static ProxyInfo forSocks4Proxy(String var0, int var1, String var2, String var3) {
      ProxyInfo.ProxyType var4 = ProxyInfo.ProxyType.SOCKS4;
      return new ProxyInfo(var4, var0, var1, var2, var3);
   }

   public static ProxyInfo forSocks5Proxy(String var0, int var1, String var2, String var3) {
      ProxyInfo.ProxyType var4 = ProxyInfo.ProxyType.SOCKS5;
      return new ProxyInfo(var4, var0, var1, var2, var3);
   }

   public String getProxyAddress() {
      return this.proxyAddress;
   }

   public String getProxyPassword() {
      return this.proxyPassword;
   }

   public int getProxyPort() {
      return this.proxyPort;
   }

   public ProxyInfo.ProxyType getProxyType() {
      return this.proxyType;
   }

   public String getProxyUsername() {
      return this.proxyUsername;
   }

   public SocketFactory getSocketFactory() {
      ProxyInfo.ProxyType var1 = this.proxyType;
      ProxyInfo.ProxyType var2 = ProxyInfo.ProxyType.NONE;
      Object var3;
      if(var1 == var2) {
         var3 = new DirectSocketFactory();
      } else {
         ProxyInfo.ProxyType var4 = this.proxyType;
         ProxyInfo.ProxyType var5 = ProxyInfo.ProxyType.HTTP;
         if(var4 == var5) {
            var3 = new HTTPProxySocketFactory(this);
         } else {
            ProxyInfo.ProxyType var6 = this.proxyType;
            ProxyInfo.ProxyType var7 = ProxyInfo.ProxyType.SOCKS4;
            if(var6 == var7) {
               var3 = new Socks4ProxySocketFactory(this);
            } else {
               ProxyInfo.ProxyType var8 = this.proxyType;
               ProxyInfo.ProxyType var9 = ProxyInfo.ProxyType.SOCKS5;
               if(var8 == var9) {
                  var3 = new Socks5ProxySocketFactory(this);
               } else {
                  var3 = null;
               }
            }
         }
      }

      return (SocketFactory)var3;
   }

   public static enum ProxyType {

      // $FF: synthetic field
      private static final ProxyInfo.ProxyType[] $VALUES;
      HTTP("HTTP", 1),
      NONE("NONE", 0),
      SOCKS4("SOCKS4", 2),
      SOCKS5("SOCKS5", 3);


      static {
         ProxyInfo.ProxyType[] var0 = new ProxyInfo.ProxyType[4];
         ProxyInfo.ProxyType var1 = NONE;
         var0[0] = var1;
         ProxyInfo.ProxyType var2 = HTTP;
         var0[1] = var2;
         ProxyInfo.ProxyType var3 = SOCKS4;
         var0[2] = var3;
         ProxyInfo.ProxyType var4 = SOCKS5;
         var0[3] = var4;
         $VALUES = var0;
      }

      private ProxyType(String var1, int var2) {}
   }
}
