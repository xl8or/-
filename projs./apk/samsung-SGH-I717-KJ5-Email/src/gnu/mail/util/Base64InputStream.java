package gnu.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream extends FilterInputStream {

   private static final int CR = 13;
   private static final int EQ = 61;
   private static final int LF = 10;
   private static final byte[] dst = new byte[256];
   private static final char[] src = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private byte[] buffer;
   private int buflen;
   private byte[] decodeBuf;
   private int index;


   static {
      for(int var0 = 0; var0 < 255; ++var0) {
         dst[var0] = -1;
      }

      int var1 = 0;

      while(true) {
         int var2 = src.length;
         if(var1 >= var2) {
            return;
         }

         byte[] var3 = dst;
         char var4 = src[var1];
         byte var5 = (byte)var1;
         var3[var4] = var5;
         ++var1;
      }
   }

   public Base64InputStream(InputStream var1) {
      super(var1);
      byte[] var2 = new byte[4];
      this.decodeBuf = var2;
      byte[] var3 = new byte[3];
      this.buffer = var3;
   }

   private void decode() throws IOException {
      this.buflen = 0;

      int var1;
      do {
         var1 = this.in.read();
         if(var1 == -1) {
            return;
         }
      } while(var1 == 10 || var1 == 13);

      byte[] var2 = this.decodeBuf;
      byte var3 = (byte)var1;
      var2[0] = var3;
      int var4 = 1;
      int var5 = 3;

      while(true) {
         InputStream var6 = this.in;
         byte[] var7 = this.decodeBuf;
         int var8 = var6.read(var7, var4, var5);
         if(var8 == var5) {
            byte[] var9 = dst;
            int var10 = this.decodeBuf[0] & 255;
            byte var11 = var9[var10];
            byte[] var12 = dst;
            int var13 = this.decodeBuf[1] & 255;
            byte var14 = var12[var13];
            byte[] var15 = this.buffer;
            int var16 = this.buflen;
            int var17 = var16 + 1;
            this.buflen = var17;
            int var18 = var11 << 2 & 252;
            int var19 = var14 >>> 4 & 3;
            byte var20 = (byte)(var18 | var19);
            var15[var16] = var20;
            if(this.decodeBuf[2] == 61) {
               return;
            }

            byte[] var21 = dst;
            int var22 = this.decodeBuf[2] & 255;
            byte var38 = var21[var22];
            byte[] var23 = this.buffer;
            int var24 = this.buflen;
            int var25 = var24 + 1;
            this.buflen = var25;
            int var26 = var14 << 4 & 240;
            int var27 = var38 >>> 2 & 15;
            byte var28 = (byte)(var26 | var27);
            var23[var24] = var28;
            if(this.decodeBuf[3] == 61) {
               return;
            }

            byte[] var29 = dst;
            int var30 = this.decodeBuf[3] & 255;
            byte var31 = var29[var30];
            byte[] var32 = this.buffer;
            int var33 = this.buflen;
            int var34 = var33 + 1;
            this.buflen = var34;
            int var35 = var38 << 6 & 192;
            int var36 = var31 & 63;
            byte var37 = (byte)(var35 | var36);
            var32[var33] = var37;
            return;
         }

         if(var8 == -1) {
            throw new IOException("Base64 encoding error");
         }

         var5 -= var8;
         var4 += var8;
      }
   }

   public int available() throws IOException {
      int var1 = this.in.available() * 3 / 4;
      int var2 = this.buflen;
      int var3 = this.index;
      int var4 = var2 - var3;
      return var1 + var4;
   }

   public int read() throws IOException {
      int var1 = this.index;
      int var2 = this.buflen;
      int var3;
      if(var1 >= var2) {
         this.decode();
         if(this.buflen == 0) {
            var3 = -1;
            return var3;
         }

         this.index = 0;
      }

      byte[] var4 = this.buffer;
      int var5 = this.index;
      int var6 = var5 + 1;
      this.index = var6;
      var3 = var4[var5] & 255;
      return var3;
   }

   public int read(byte[] param1, int param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
