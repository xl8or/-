package org.jivesoftware.smack.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

class DirectSocketFactory extends SocketFactory {

   public DirectSocketFactory() {}

   public Socket createSocket(String var1, int var2) throws IOException, UnknownHostException {
      Proxy var3 = Proxy.NO_PROXY;
      Socket var4 = new Socket(var3);
      InetSocketAddress var5 = new InetSocketAddress(var1, var2);
      var4.connect(var5);
      return var4;
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      return new Socket(var1, var2, var3, var4);
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      Proxy var3 = Proxy.NO_PROXY;
      Socket var4 = new Socket(var3);
      InetSocketAddress var5 = new InetSocketAddress(var1, var2);
      var4.connect(var5);
      return var4;
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      return new Socket(var1, var2, var3, var4);
   }
}
