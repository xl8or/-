package org.apache.james.mime4j.decoder;

import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream extends InputStream {

   private static byte[] TRANSLATION = new byte[]{(byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)62, (byte)255, (byte)255, (byte)255, (byte)63, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255};
   private boolean done;
   private final byte[] inputBuffer;
   private int outCount = 0;
   private int outIndex = 0;
   private final int[] outputBuffer;
   private final InputStream s;


   public Base64InputStream(InputStream var1) {
      int[] var2 = new int[3];
      this.outputBuffer = var2;
      byte[] var3 = new byte[4];
      this.inputBuffer = var3;
      this.done = (boolean)0;
      this.s = var1;
   }

   private void decodeAndEnqueue(int var1) {
      int var2 = this.inputBuffer[0] << 18;
      int var3 = 0 | var2;
      int var4 = this.inputBuffer[1] << 12;
      int var5 = var3 | var4;
      int var6 = this.inputBuffer[2] << 6;
      int var7 = var5 | var6;
      byte var8 = this.inputBuffer[3];
      int var9 = var7 | var8;
      if(var1 == 4) {
         int[] var10 = this.outputBuffer;
         int var11 = var9 >> 16 & 255;
         var10[0] = var11;
         int[] var12 = this.outputBuffer;
         int var13 = var9 >> 8 & 255;
         var12[1] = var13;
         int[] var14 = this.outputBuffer;
         int var15 = var9 & 255;
         var14[2] = var15;
         this.outCount = 3;
      } else if(var1 == 3) {
         int[] var16 = this.outputBuffer;
         int var17 = var9 >> 16 & 255;
         var16[0] = var17;
         int[] var18 = this.outputBuffer;
         int var19 = var9 >> 8 & 255;
         var18[1] = var19;
         this.outCount = 2;
      } else {
         int[] var20 = this.outputBuffer;
         int var21 = var9 >> 16 & 255;
         var20[0] = var21;
         this.outCount = 1;
      }
   }

   private void fillBuffer() throws IOException {
      this.outCount = 0;
      this.outIndex = 0;
      int var1 = 0;

      while(!this.done) {
         int var2 = this.s.read();
         switch(var2) {
         case 61:
            this.done = (boolean)1;
            this.decodeAndEnqueue(var1);
            return;
         default:
            byte var3 = TRANSLATION[var2];
            if(var3 < 0) {
               break;
            }

            byte[] var4 = this.inputBuffer;
            int var5 = var1 + 1;
            var4[var1] = var3;
            if(var5 != 4) {
               var1 = var5;
               break;
            } else {
               this.decodeAndEnqueue(var5);
            }
         case -1:
            return;
         }
      }

   }

   public void close() throws IOException {
      this.s.close();
   }

   public int read() throws IOException {
      int var1 = this.outIndex;
      int var2 = this.outCount;
      int var5;
      if(var1 == var2) {
         this.fillBuffer();
         int var3 = this.outIndex;
         int var4 = this.outCount;
         if(var3 == var4) {
            var5 = -1;
            return var5;
         }
      }

      int[] var6 = this.outputBuffer;
      int var7 = this.outIndex;
      int var8 = var7 + 1;
      this.outIndex = var8;
      var5 = var6[var7];
      return var5;
   }
}
