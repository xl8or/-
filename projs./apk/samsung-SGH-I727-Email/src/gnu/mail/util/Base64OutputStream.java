package gnu.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64OutputStream extends FilterOutputStream {

   private static final int CR = 13;
   private static final int EQ = 61;
   private static final int LF = 10;
   private static final char[] src = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private byte[] buffer;
   private int buflen;
   private int count;
   private int lineLength;


   public Base64OutputStream(OutputStream var1) {
      this(var1, 76);
   }

   public Base64OutputStream(OutputStream var1, int var2) {
      super(var1);
      byte[] var3 = new byte[3];
      this.buffer = var3;
      this.lineLength = var2;
   }

   private void encode() throws IOException {
      int var1 = this.count + 4;
      int var2 = this.lineLength;
      if(var1 > var2) {
         this.out.write(13);
         this.out.write(10);
         this.count = 0;
      }

      if(this.buflen == 1) {
         byte var3 = this.buffer[0];
         OutputStream var4 = this.out;
         char[] var5 = src;
         int var6 = var3 >>> 2 & 63;
         char var7 = var5[var6];
         var4.write(var7);
         OutputStream var8 = this.out;
         char[] var9 = src;
         int var10 = var3 << 4 & 48;
         int var11 = 0 >>> 4 & 15;
         int var12 = var10 + 0;
         char var13 = var9[var12];
         var8.write(var13);
         this.out.write(61);
         this.out.write(61);
      } else if(this.buflen == 2) {
         byte var15 = this.buffer[0];
         byte var16 = this.buffer[1];
         OutputStream var17 = this.out;
         char[] var18 = src;
         int var19 = var15 >>> 2 & 63;
         char var20 = var18[var19];
         var17.write(var20);
         OutputStream var21 = this.out;
         char[] var22 = src;
         int var23 = var15 << 4 & 48;
         int var24 = var16 >>> 4 & 15;
         int var25 = var23 + var24;
         char var26 = var22[var25];
         var21.write(var26);
         OutputStream var27 = this.out;
         char[] var28 = src;
         int var29 = var16 << 2 & 60;
         int var30 = 0 >>> 6 & 3;
         int var31 = var29 + 0;
         char var32 = var28[var31];
         var27.write(var32);
         this.out.write(61);
      } else {
         byte var33 = this.buffer[0];
         byte var34 = this.buffer[1];
         byte var35 = this.buffer[2];
         OutputStream var36 = this.out;
         char[] var37 = src;
         int var38 = var33 >>> 2 & 63;
         char var39 = var37[var38];
         var36.write(var39);
         OutputStream var40 = this.out;
         char[] var41 = src;
         int var42 = var33 << 4 & 48;
         int var43 = var34 >>> 4 & 15;
         int var44 = var42 + var43;
         char var45 = var41[var44];
         var40.write(var45);
         OutputStream var46 = this.out;
         char[] var47 = src;
         int var48 = var34 << 2 & 60;
         int var49 = var35 >>> 6 & 3;
         int var50 = var48 + var49;
         char var51 = var47[var50];
         var46.write(var51);
         OutputStream var52 = this.out;
         char[] var53 = src;
         int var54 = var35 & 63;
         char var55 = var53[var54];
         var52.write(var55);
      }

      int var14 = this.count + 4;
      this.count = var14;
   }

   public void close() throws IOException {
      this.flush();
      this.out.close();
   }

   public void flush() throws IOException {
      if(this.buflen > 0) {
         this.encode();
         this.buflen = 0;
      }

      this.out.flush();
   }

   public void write(int var1) throws IOException {
      byte[] var2 = this.buffer;
      int var3 = this.buflen;
      int var4 = var3 + 1;
      this.buflen = var4;
      byte var5 = (byte)var1;
      var2[var3] = var5;
      if(this.buflen == 3) {
         this.encode();
         this.buflen = 0;
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2 + var4;
         byte var6 = var1[var5];
         this.write(var6);
      }

   }
}
