package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;

class CompressedInputStream extends DTPInputStream {

   static final int COMPRESSED = 128;
   static final int EOF = 64;
   static final int FILLER = 192;
   static final int RAW;
   int count = -1;
   int descriptor;
   int max = -1;
   int n = 0;
   int rep;
   int state = 0;


   CompressedInputStream(DTP var1, InputStream var2) {
      super(var1, var2);
   }

   public int read() throws IOException {
      int var1;
      if(this.transferComplete) {
         var1 = -1;
      } else {
         if(this.count == -1) {
            this.readHeader();
         }

         if(this.max < 1) {
            this.close();
            var1 = -1;
         } else if(this.n > 0 && (this.state == 128 || this.state == 192)) {
            int var2 = this.n - 1;
            this.n = var2;
            var1 = this.rep;
         } else {
            var1 = this.in.read();
            if(var1 == -1) {
               this.close();
            }

            int var3 = this.count + 1;
            this.count = var3;
            int var4 = this.count;
            int var5 = this.max;
            if(var4 >= var5) {
               this.count = -1;
               if(this.descriptor == 64) {
                  this.close();
               }
            }

            if(var1 != -1) {
               while(true) {
                  if(this.n == 0) {
                     int var6 = var1 & 192;
                     this.state = var6;
                     int var7 = var1 & 63;
                     this.n = var7;
                     var1 = this.in.read();
                     if(var1 != -1) {
                        continue;
                     }

                     var1 = -1;
                     break;
                  }

                  switch(this.state) {
                  case 128:
                  case 192:
                     this.rep = var1;
                  case 0:
                     int var11 = this.n - 1;
                     this.n = var11;
                     return var1;
                  default:
                     StringBuilder var8 = (new StringBuilder()).append("Illegal state: ");
                     int var9 = this.state;
                     String var10 = var8.append(var9).toString();
                     throw new ProtocolException(var10);
                  }
               }
            }
         }
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.transferComplete) {
         var4 = -1;
      } else {
         if(this.count == -1) {
            this.readHeader();
         }

         if(this.max < 1) {
            this.close();
            var4 = -1;
         } else {
            var4 = var2;

            while(true) {
               if(var4 >= var3) {
                  var4 = var3;
                  break;
               }

               int var5 = this.read();
               if(var5 == -1) {
                  this.close();
                  break;
               }

               byte var6 = (byte)var5;
               var1[var4] = var6;
               ++var4;
            }
         }
      }

      return var4;
   }

   void readCodeHeader() throws IOException {
      int var1 = this.in.read();
      int var2 = var1 & 192;
      this.state = var2;
      int var3 = var1 & 63;
      this.n = var3;
   }

   void readHeader() throws IOException {
      int var1 = this.in.read();
      this.descriptor = var1;
      int var2 = this.in.read();
      int var3 = this.in.read();
      int var4 = var2 << 8 | var3;
      this.max = var4;
      this.count = 0;
   }
}
