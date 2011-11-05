package org.xbill.DNS;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import org.xbill.DNS.Client;

final class TCPClient extends Client {

   public TCPClient(long var1) throws IOException {
      SocketChannel var3 = SocketChannel.open();
      super(var3, var1);
   }

   private byte[] _recv(int param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   static byte[] sendrecv(SocketAddress param0, SocketAddress param1, byte[] param2, long param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   static byte[] sendrecv(SocketAddress var0, byte[] var1, long var2) throws IOException {
      return sendrecv((SocketAddress)null, var0, var1, var2);
   }

   void bind(SocketAddress var1) throws IOException {
      ((SocketChannel)this.key.channel()).socket().bind(var1);
   }

   void connect(SocketAddress var1) throws IOException {
      SocketChannel var2 = (SocketChannel)this.key.channel();
      if(!var2.connect(var1)) {
         SelectionKey var3 = this.key.interestOps(8);

         while(true) {
            boolean var11 = false;

            try {
               var11 = true;
               if(var2.finishConnect()) {
                  var11 = false;
                  break;
               }

               if(!this.key.isConnectable()) {
                  SelectionKey var4 = this.key;
                  long var5 = this.endTime;
                  blockUntil(var4, var5);
               }
            } finally {
               if(var11) {
                  if(this.key.isValid()) {
                     SelectionKey var8 = this.key.interestOps(0);
                  }

               }
            }
         }

         if(this.key.isValid()) {
            SelectionKey var9 = this.key.interestOps(0);
         }
      }
   }

   byte[] recv() throws IOException {
      byte[] var1 = this._recv(2);
      int var2 = (var1[0] & 255) << 8;
      int var3 = (var1[1] & 255) + var2;
      byte[] var4 = this._recv(var3);
      verboseLog("TCP read", var4);
      return var4;
   }

   void send(byte[] param1) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
