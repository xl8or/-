package org.xbill.DNS;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.security.SecureRandom;
import org.xbill.DNS.Client;

final class UDPClient extends Client {

   private static final int EPHEMERAL_RANGE = 64511;
   private static final int EPHEMERAL_START = 1024;
   private static final int EPHEMERAL_STOP = 65535;
   private static SecureRandom prng = new SecureRandom();
   private static volatile boolean prng_initializing = 1;
   private boolean bound;


   static {
      UDPClient.1 var0 = new UDPClient.1();
      (new Thread(var0)).start();
   }

   public UDPClient(long var1) throws IOException {
      DatagramChannel var3 = DatagramChannel.open();
      super(var3, var1);
      this.bound = (boolean)0;
   }

   private void bind_random(InetSocketAddress param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   static byte[] sendrecv(SocketAddress var0, SocketAddress var1, byte[] var2, int var3, long var4) throws IOException {
      UDPClient var6 = new UDPClient(var4);
      boolean var11 = false;

      byte[] var7;
      try {
         var11 = true;
         var6.bind(var0);
         var6.connect(var1);
         var6.send(var2);
         var7 = var6.recv(var3);
         var11 = false;
      } finally {
         if(var11) {
            var6.cleanup();
         }
      }

      var6.cleanup();
      return var7;
   }

   static byte[] sendrecv(SocketAddress var0, byte[] var1, int var2, long var3) throws IOException {
      return sendrecv((SocketAddress)null, var0, var1, var2, var3);
   }

   void bind(SocketAddress var1) throws IOException {
      if(var1 == null || var1 instanceof InetSocketAddress && ((InetSocketAddress)var1).getPort() == 0) {
         InetSocketAddress var2 = (InetSocketAddress)var1;
         this.bind_random(var2);
         if(this.bound) {
            return;
         }
      }

      if(var1 != null) {
         ((DatagramChannel)this.key.channel()).socket().bind(var1);
         this.bound = (boolean)1;
      }
   }

   void connect(SocketAddress var1) throws IOException {
      if(!this.bound) {
         this.bind((SocketAddress)null);
      }

      DatagramChannel var2 = ((DatagramChannel)this.key.channel()).connect(var1);
   }

   byte[] recv(int var1) throws IOException {
      DatagramChannel var2 = (DatagramChannel)this.key.channel();
      byte[] var3 = new byte[var1];
      SelectionKey var4 = this.key.interestOps(1);

      while(true) {
         boolean var17 = false;

         try {
            var17 = true;
            if(this.key.isReadable()) {
               var17 = false;
               break;
            }

            SelectionKey var5 = this.key;
            long var6 = this.endTime;
            blockUntil(var5, var6);
         } finally {
            if(var17) {
               if(this.key.isValid()) {
                  SelectionKey var9 = this.key.interestOps(0);
               }

            }
         }
      }

      if(this.key.isValid()) {
         SelectionKey var10 = this.key.interestOps(0);
      }

      ByteBuffer var11 = ByteBuffer.wrap(var3);
      long var12 = (long)var2.read(var11);
      if(var12 <= 0L) {
         throw new EOFException();
      } else {
         int var14 = (int)var12;
         byte[] var15 = new byte[var14];
         System.arraycopy(var3, 0, var15, 0, var14);
         verboseLog("UDP read", var15);
         return var15;
      }
   }

   void send(byte[] var1) throws IOException {
      DatagramChannel var2 = (DatagramChannel)this.key.channel();
      verboseLog("UDP write", var1);
      ByteBuffer var3 = ByteBuffer.wrap(var1);
      var2.write(var3);
   }

   static class 1 implements Runnable {

      1() {}

      public void run() {
         int var1 = UDPClient.prng.nextInt();
         boolean var2 = (boolean)(UDPClient.prng_initializing = (boolean)0);
      }
   }
}
