package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.DTPOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class CompressedOutputStream extends DTPOutputStream {

   static final byte EOF = 64;
   static final byte RECORD = -128;


   CompressedOutputStream(DTP var1, OutputStream var2) {
      super(var1, var2);
   }

   public void close() throws IOException {
      byte[] var1 = new byte[]{(byte)64, (byte)0, (byte)0};
      this.out.write(var1, 0, 3);
      this.out.close();
   }

   byte[] compress(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      int var5 = 0;
      int var6 = var2;
      int var7 = 1;
      int var8 = 0;

      byte var9;
      int var40;
      for(var9 = 0; var6 < var3; var5 = var40) {
         byte var10 = var1[var6];
         byte[] var19;
         int var20;
         int var23;
         int var24;
         if(var6 > var2 && var10 == var9) {
            int var21;
            if(var8 > 0) {
               int var11 = var8 + 1 + var5;
               int var12 = var4.length;
               byte[] var13;
               if(var11 > var12) {
                  var4 = this.realloc(var4, var3);
                  var13 = var4;
               } else {
                  var13 = var4;
               }

               int var14 = var6 - var8 - 1;
               int var17 = this.flush_raw(var13, var5, var1, var14, var8);
               byte var18 = 0;
               var19 = var13;
               var20 = var17;
               var21 = var18;
            } else {
               var20 = var5;
               var19 = var4;
               var21 = var8;
            }

            int var22 = var7 + 1;
            var23 = var21;
            var24 = var22;
         } else {
            if(var7 > 1) {
               int var43 = var5 + 2;
               int var44 = var4.length;
               if(var43 > var44) {
                  this.realloc(var4, var3);
               }

               this.flush_compressed(var4, var5, var7, var9);
               byte var47 = 1;
               var19 = var4;
               var24 = var47;
            } else {
               var19 = var4;
               var24 = var7;
            }

            var23 = var8 + 1;
         }

         byte[] var29;
         int var30;
         if(var24 == 127) {
            int var25 = var20 + 2;
            int var26 = var19.length;
            if(var25 > var26) {
               var19 = this.realloc(var19, var3);
            }

            int var27 = this.flush_compressed(var19, var20, var24, var9);
            var20 = 1;
            var7 = var20;
            var29 = var19;
            var30 = var27;
         } else {
            var7 = var24;
            var29 = var19;
            var30 = var20;
         }

         int var41;
         if(var23 == 127) {
            int var31 = var23 + 1 + var30;
            int var32 = var29.length;
            byte[] var33;
            if(var31 > var32) {
               var33 = this.realloc(var29, var3);
            } else {
               var33 = var29;
            }

            int var34 = var6 - var23;
            int var37 = this.flush_raw(var33, var30, var1, var34, var23);
            byte var38 = 0;
            var40 = var37;
            var41 = var38;
         } else {
            var40 = var30;
            var41 = var23;
         }

         ++var6;
         var8 = var41;
         var9 = var10;
      }

      if(var7 > 1) {
         int var48 = var5 + 2;
         int var49 = var4.length;
         if(var48 > var49) {
            this.realloc(var4, var3);
         }

         var5 = this.flush_compressed(var4, var5, var7, var9);
      }

      byte[] var53;
      int var57;
      if(var8 > 0) {
         int var51 = var8 + 1 + var5;
         int var52 = var4.length;
         if(var51 > var52) {
            var53 = this.realloc(var4, var3);
         } else {
            var53 = var4;
         }

         int var54 = var3 - var8;
         var57 = this.flush_raw(var53, var5, var1, var54, var8);
      } else {
         var53 = var4;
         var57 = var5;
      }

      byte[] var58 = new byte[var57 + 3];
      System.arraycopy(var53, 0, var58, 3, var57);
      return var58;
   }

   int flush_compressed(byte[] var1, int var2, int var3, byte var4) {
      int var5 = var2 + 1;
      byte var6 = (byte)(var3 | 128);
      var1[var2] = var6;
      int var7 = var5 + 1;
      var1[var5] = var4;
      return var7;
   }

   int flush_raw(byte[] var1, int var2, byte[] var3, int var4, int var5) {
      int var6 = var2 + 1;
      byte var7 = (byte)var5;
      var1[var2] = var7;
      System.arraycopy(var3, var4, var1, var6, var5);
      return var6 + var5;
   }

   byte[] realloc(byte[] var1, int var2) {
      byte[] var3 = new byte[var1.length + var2];
      int var4 = var1.length;
      System.arraycopy(var1, 0, var3, 0, var4);
      return var3;
   }

   public void write(int var1) throws IOException {
      if(!this.transferComplete) {
         byte[] var2 = new byte[]{(byte)'\uff80', (byte)0, (byte)1, (byte)1, (byte)0};
         byte var3 = (byte)var1;
         var2[4] = var3;
         this.out.write(var2, 0, 5);
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if(!this.transferComplete) {
         byte[] var4 = this.compress(var1, var2, var3);
         int var5 = var4.length;
         var4[0] = (byte)'\uff80';
         byte var6 = (byte)((var5 & 255) >> 8);
         var4[1] = var6;
         byte var7 = (byte)('\uff00' & var5);
         var4[2] = var7;
         this.out.write(var4, 0, var5);
      }
   }
}
