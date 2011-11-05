package org.jivesoftware.smack.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import org.jivesoftware.smack.proxy.ProxyInfo;

public class Socks4ProxySocketFactory extends SocketFactory {

   private ProxyInfo proxy;


   public Socks4ProxySocketFactory(ProxyInfo var1) {
      this.proxy = var1;
   }

   private Socket socks4ProxifiedSocket(String param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Socket createSocket(String var1, int var2) throws IOException, UnknownHostException {
      return this.socks4ProxifiedSocket(var1, var2);
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      return this.socks4ProxifiedSocket(var1, var2);
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      String var3 = var1.getHostAddress();
      return this.socks4ProxifiedSocket(var3, var2);
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      String var5 = var1.getHostAddress();
      return this.socks4ProxifiedSocket(var5, var2);
   }
}
