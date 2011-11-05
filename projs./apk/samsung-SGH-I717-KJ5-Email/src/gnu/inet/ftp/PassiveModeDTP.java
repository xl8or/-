package gnu.inet.ftp;

import gnu.inet.ftp.BlockInputStream;
import gnu.inet.ftp.BlockOutputStream;
import gnu.inet.ftp.CompressedInputStream;
import gnu.inet.ftp.CompressedOutputStream;
import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPInputStream;
import gnu.inet.ftp.DTPOutputStream;
import gnu.inet.ftp.StreamInputStream;
import gnu.inet.ftp.StreamOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

final class PassiveModeDTP implements DTP {

   final String address;
   boolean completed;
   DTPInputStream in;
   boolean inProgress;
   DTPOutputStream out;
   final int port;
   Socket socket;
   int transferMode;


   PassiveModeDTP(String var1, int var2, InetAddress var3, int var4, int var5) throws IOException {
      this.address = var1;
      this.port = var2;
      this.completed = (boolean)0;
      this.inProgress = (boolean)0;
      Socket var6 = new Socket();
      this.socket = var6;
      InetSocketAddress var7 = new InetSocketAddress(var1, var2);
      int var8 = var2 + 1;
      InetSocketAddress var9 = new InetSocketAddress(var3, var8);
      this.socket.bind(var9);
      if(var4 > 0) {
         this.socket.connect(var7, var4);
      } else {
         this.socket.connect(var7);
      }

      if(var5 > 0) {
         this.socket.setSoTimeout(var5);
      }
   }

   public boolean abort() {
      this.completed = (boolean)1;
      this.transferComplete();
      return this.inProgress;
   }

   public void complete() {
      this.completed = (boolean)1;
      if(!this.inProgress) {
         this.transferComplete();
      }
   }

   public InputStream getInputStream() throws IOException {
      if(this.inProgress) {
         throw new IOException("Transfer in progress");
      } else {
         switch(this.transferMode) {
         case 1:
            InputStream var1 = this.socket.getInputStream();
            StreamInputStream var2 = new StreamInputStream(this, var1);
            this.in = var2;
            break;
         case 2:
            InputStream var3 = this.socket.getInputStream();
            BlockInputStream var4 = new BlockInputStream(this, var3);
            this.in = var4;
            break;
         case 3:
            InputStream var5 = this.socket.getInputStream();
            CompressedInputStream var6 = new CompressedInputStream(this, var5);
            this.in = var6;
            break;
         default:
            throw new IllegalStateException("Invalid transfer mode");
         }

         this.in.setTransferComplete((boolean)0);
         return this.in;
      }
   }

   public OutputStream getOutputStream() throws IOException {
      if(this.inProgress) {
         throw new IOException("Transfer in progress");
      } else {
         switch(this.transferMode) {
         case 1:
            OutputStream var1 = this.socket.getOutputStream();
            StreamOutputStream var2 = new StreamOutputStream(this, var1);
            this.out = var2;
            break;
         case 2:
            OutputStream var3 = this.socket.getOutputStream();
            BlockOutputStream var4 = new BlockOutputStream(this, var3);
            this.out = var4;
            break;
         case 3:
            OutputStream var5 = this.socket.getOutputStream();
            CompressedOutputStream var6 = new CompressedOutputStream(this, var5);
            this.out = var6;
            break;
         default:
            throw new IllegalStateException("Invalid transfer mode");
         }

         this.out.setTransferComplete((boolean)0);
         return this.out;
      }
   }

   public void setTransferMode(int var1) {
      this.transferMode = var1;
   }

   public void transferComplete() {
      if(this.in != null) {
         this.in.setTransferComplete((boolean)1);
      }

      if(this.out != null) {
         this.out.setTransferComplete((boolean)1);
      }

      this.inProgress = (boolean)0;
      byte var1;
      if(!this.completed && this.transferMode != 1) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      this.completed = (boolean)var1;
      if(this.completed) {
         if(this.socket != null) {
            try {
               this.socket.close();
            } catch (IOException var3) {
               ;
            }
         }
      }
   }
}
