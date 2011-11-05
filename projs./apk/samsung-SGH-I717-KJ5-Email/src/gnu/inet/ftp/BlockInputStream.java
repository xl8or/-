package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPInputStream;
import java.io.IOException;
import java.io.InputStream;

class BlockInputStream extends DTPInputStream {

   static final int EOF = 64;
   int count = -1;
   int descriptor;
   int max = -1;


   BlockInputStream(DTP var1, InputStream var2) {
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
         } else {
            var1 = this.in.read();
            if(var1 == -1) {
               this.dtp.transferComplete();
            }

            int var2 = this.count + 1;
            this.count = var2;
            int var3 = this.count;
            int var4 = this.max;
            if(var3 >= var4) {
               this.count = -1;
               if(this.descriptor == 64) {
                  this.close();
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
            var4 = this.in.read(var1, var2, var3);
            if(var4 == -1) {
               this.dtp.transferComplete();
            }

            int var5 = this.count + var4;
            this.count = var5;
            int var6 = this.count;
            int var7 = this.max;
            if(var6 >= var7) {
               this.count = -1;
               if(this.descriptor == 64) {
                  this.close();
               }
            }
         }
      }

      return var4;
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
