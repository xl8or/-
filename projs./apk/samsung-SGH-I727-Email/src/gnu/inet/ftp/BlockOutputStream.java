package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class BlockOutputStream extends DTPOutputStream {

   static final byte EOF = 64;
   static final byte RECORD = -128;


   BlockOutputStream(DTP var1, OutputStream var2) {
      super(var1, var2);
   }

   public void close() throws IOException {
      byte[] var1 = new byte[]{(byte)64, (byte)0, (byte)0};
      this.out.write(var1, 0, 3);
      super.close();
   }

   public void write(int var1) throws IOException {
      if(!this.transferComplete) {
         byte[] var2 = new byte[]{(byte)'\uff80', (byte)0, (byte)1, (byte)0};
         byte var3 = (byte)var1;
         var2[3] = var3;
         this.out.write(var2, 0, 4);
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if(!this.transferComplete) {
         byte[] var4 = new byte[var3 + 3];
         var4[0] = (byte)'\uff80';
         byte var5 = (byte)((var3 & 255) >> 8);
         var4[1] = var5;
         byte var6 = (byte)('\uff00' & var3);
         var4[2] = var6;
         System.arraycopy(var1, var2, var4, 3, var3);
         this.out.write(var4, 0, var3);
      }
   }
}
