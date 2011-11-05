package org.jivesoftware.smack.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import org.jivesoftware.smack.proxy.ProxyException;
import org.jivesoftware.smack.proxy.ProxyInfo;

public class Socks5ProxySocketFactory extends SocketFactory {

   private ProxyInfo proxy;


   public Socks5ProxySocketFactory(ProxyInfo var1) {
      this.proxy = var1;
   }

   private void fill(InputStream var1, byte[] var2, int var3) throws IOException {
      int var6;
      for(int var4 = 0; var4 < var3; var4 += var6) {
         int var5 = var3 - var4;
         var6 = var1.read(var2, var4, var5);
         if(var6 <= 0) {
            ProxyInfo.ProxyType var7 = ProxyInfo.ProxyType.SOCKS5;
            throw new ProxyException(var7, "stream is closed");
         }
      }

   }

   private Socket socks5ProxifiedSocket(String param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Socket createSocket(String var1, int var2) throws IOException, UnknownHostException {
      return this.socks5ProxifiedSocket(var1, var2);
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      return this.socks5ProxifiedSocket(var1, var2);
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      String var3 = var1.getHostAddress();
      return this.socks5ProxifiedSocket(var3, var2);
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      String var5 = var1.getHostAddress();
      return this.socks5ProxifiedSocket(var5, var2);
   }
}
