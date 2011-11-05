package myorg.bouncycastle.util.encoders;

import myorg.bouncycastle.util.encoders.Translator;

public class BufferedEncoder {

   protected byte[] buf;
   protected int bufOff;
   protected Translator translator;


   public BufferedEncoder(Translator var1, int var2) {
      this.translator = var1;
      int var3 = var1.getEncodedBlockSize();
      if(var2 % var3 != 0) {
         throw new IllegalArgumentException("buffer size not multiple of input block size");
      } else {
         byte[] var4 = new byte[var2];
         this.buf = var4;
         this.bufOff = 0;
      }
   }

   public int processByte(byte var1, byte[] var2, int var3) {
      int var4 = 0;
      byte[] var5 = this.buf;
      int var6 = this.bufOff;
      int var7 = var6 + 1;
      this.bufOff = var7;
      var5[var6] = var1;
      int var8 = this.bufOff;
      int var9 = this.buf.length;
      if(var8 == var9) {
         Translator var10 = this.translator;
         byte[] var11 = this.buf;
         int var12 = this.buf.length;
         var4 = var10.encode(var11, 0, var12, var2, var3);
         this.bufOff = 0;
      }

      return var4;
   }

   public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      if(var3 < 0) {
         throw new IllegalArgumentException("Can\'t have a negative input length!");
      } else {
         int var6 = 0;
         int var7 = this.buf.length;
         int var8 = this.bufOff;
         int var9 = var7 - var8;
         if(var3 > var9) {
            byte[] var10 = this.buf;
            int var11 = this.bufOff;
            System.arraycopy(var1, var2, var10, var11, var9);
            Translator var12 = this.translator;
            byte[] var13 = this.buf;
            int var14 = this.buf.length;
            int var17 = var12.encode(var13, 0, var14, var4, var5);
            int var18 = var6 + var17;
            this.bufOff = 0;
            int var19 = var3 - var9;
            int var20 = var2 + var9;
            int var21 = var5 + var18;
            int var22 = this.buf.length;
            int var23 = var19 % var22;
            int var24 = var19 - var23;
            Translator var25 = this.translator;
            int var30 = var25.encode(var1, var20, var24, var4, var21);
            var6 = var18 + var30;
            var3 = var19 - var24;
            var2 = var20 + var24;
         }

         if(var3 != 0) {
            byte[] var31 = this.buf;
            int var32 = this.bufOff;
            System.arraycopy(var1, var2, var31, var32, var3);
            int var33 = this.bufOff + var3;
            this.bufOff = var33;
         }

         return var6;
      }
   }
}
