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
import java.net.ServerSocket;
import java.net.Socket;

final class ActiveModeDTP implements DTP, Runnable {

   Thread acceptThread;
   boolean completed = 0;
   int connectionTimeout;
   IOException exception;
   DTPInputStream in;
   boolean inProgress = 0;
   DTPOutputStream out;
   ServerSocket server;
   Socket socket;
   int transferMode;


   ActiveModeDTP(InetAddress var1, int var2, int var3, int var4) throws IOException {
      ServerSocket var5 = new ServerSocket(var2, 1, var1);
      this.server = var5;
      if(var4 > 0) {
         this.server.setSoTimeout(var4);
      }

      int var6;
      if(var3 <= 0) {
         var6 = 20000;
      } else {
         var6 = var3;
      }

      this.connectionTimeout = var6;
      Thread var7 = new Thread(this, "ActiveModeDTP");
      this.acceptThread = var7;
      this.acceptThread.start();
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
         if(this.acceptThread != null) {
            this.waitFor();
         }

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
            throw new IllegalStateException("invalid transfer mode");
         }

         this.in.setTransferComplete((boolean)0);
         return this.in;
      }
   }

   public OutputStream getOutputStream() throws IOException {
      if(this.inProgress) {
         throw new IOException("Transfer in progress");
      } else {
         if(this.acceptThread != null) {
            this.waitFor();
         }

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
            throw new IllegalStateException("invalid transfer mode");
         }

         this.out.setTransferComplete((boolean)0);
         return this.out;
      }
   }

   public void run() {
      try {
         Socket var1 = this.server.accept();
         this.socket = var1;
      } catch (IOException var3) {
         this.exception = var3;
      }
   }

   public void setTransferMode(int var1) {
      this.transferMode = var1;
   }

   public void transferComplete() {
      if(this.socket != null) {
         if(this.in != null) {
            this.in.setTransferComplete((boolean)1);
         }

         if(this.out != null) {
            this.out.setTransferComplete((boolean)1);
         }

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
               } catch (IOException var5) {
                  ;
               }

               try {
                  this.server.close();
               } catch (IOException var4) {
                  ;
               }
            }
         }
      }
   }

   public void waitFor() throws IOException {
      try {
         Thread var1 = this.acceptThread;
         long var2 = (long)this.connectionTimeout;
         var1.join(var2);
      } catch (InterruptedException var5) {
         ;
      }

      if(this.exception != null) {
         throw this.exception;
      } else if(this.socket == null) {
         this.server.close();
         throw new IOException("client did not connect before timeout");
      } else {
         this.acceptThread = null;
      }
   }
}
