package com.facebook.katana.service.xmpp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class FacebookChatSocketFactory extends SocketFactory {

   private final SocketFactory mySocketFactory;


   FacebookChatSocketFactory() {
      SocketFactory var1 = SocketFactory.getDefault();
      this.mySocketFactory = var1;
   }

   private Socket addChatSocketOptions(Socket var1) throws SocketException {
      var1.setSoTimeout('\u9c40');
      return var1;
   }

   public Socket createSocket() throws IOException {
      Socket var1 = this.mySocketFactory.createSocket();
      return this.addChatSocketOptions(var1);
   }

   public Socket createSocket(String var1, int var2) throws IOException {
      Socket var3 = this.mySocketFactory.createSocket(var1, var2);
      return this.addChatSocketOptions(var3);
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      Socket var5 = this.mySocketFactory.createSocket(var1, var2);
      return this.addChatSocketOptions(var5);
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      Socket var3 = this.mySocketFactory.createSocket(var1, var2);
      return this.addChatSocketOptions(var3);
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      Socket var5 = this.mySocketFactory.createSocket(var1, var2);
      return this.addChatSocketOptions(var5);
   }
}
