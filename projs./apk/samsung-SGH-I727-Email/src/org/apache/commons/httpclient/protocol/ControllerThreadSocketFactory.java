package org.apache.commons.httpclient.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.util.TimeoutController;

public final class ControllerThreadSocketFactory {

   private ControllerThreadSocketFactory() {}

   public static Socket createSocket(ControllerThreadSocketFactory.SocketTask var0, int var1) throws IOException, UnknownHostException, ConnectTimeoutException {
      long var2 = (long)var1;

      try {
         TimeoutController.execute((Runnable)var0, var2);
      } catch (TimeoutController.TimeoutException var7) {
         String var6 = "The host did not accept the connection within timeout of " + var1 + " ms";
         throw new ConnectTimeoutException(var6);
      }

      Socket var4 = var0.getSocket();
      if(var0.exception != null) {
         throw var0.exception;
      } else {
         return var4;
      }
   }

   public static Socket createSocket(ProtocolSocketFactory var0, String var1, int var2, InetAddress var3, int var4, int var5) throws IOException, UnknownHostException, ConnectTimeoutException {
      ControllerThreadSocketFactory.1 var11 = new ControllerThreadSocketFactory.1(var0, var1, var2, var3, var4);
      long var12 = (long)var5;

      try {
         TimeoutController.execute((Runnable)var11, var12);
      } catch (TimeoutController.TimeoutException var17) {
         String var16 = "The host did not accept the connection within timeout of " + var5 + " ms";
         throw new ConnectTimeoutException(var16);
      }

      Socket var14 = var11.getSocket();
      if(var11.exception != null) {
         throw var11.exception;
      } else {
         return var14;
      }
   }

   static class 1 extends ControllerThreadSocketFactory.SocketTask {

      // $FF: synthetic field
      final String val$host;
      // $FF: synthetic field
      final InetAddress val$localAddress;
      // $FF: synthetic field
      final int val$localPort;
      // $FF: synthetic field
      final int val$port;
      // $FF: synthetic field
      final ProtocolSocketFactory val$socketfactory;


      1(ProtocolSocketFactory var1, String var2, int var3, InetAddress var4, int var5) {
         this.val$socketfactory = var1;
         this.val$host = var2;
         this.val$port = var3;
         this.val$localAddress = var4;
         this.val$localPort = var5;
      }

      public void doit() throws IOException {
         ProtocolSocketFactory var1 = this.val$socketfactory;
         String var2 = this.val$host;
         int var3 = this.val$port;
         InetAddress var4 = this.val$localAddress;
         int var5 = this.val$localPort;
         Socket var6 = var1.createSocket(var2, var3, var4, var5);
         this.setSocket(var6);
      }
   }

   public abstract static class SocketTask implements Runnable {

      private IOException exception;
      private Socket socket;


      public SocketTask() {}

      public abstract void doit() throws IOException;

      protected Socket getSocket() {
         return this.socket;
      }

      public void run() {
         try {
            this.doit();
         } catch (IOException var2) {
            this.exception = var2;
         }
      }

      protected void setSocket(Socket var1) {
         this.socket = var1;
      }
   }
}
