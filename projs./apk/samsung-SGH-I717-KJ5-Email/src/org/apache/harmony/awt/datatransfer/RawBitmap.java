package org.apache.harmony.awt.datatransfer;


public final class RawBitmap {

   public final int bMask;
   public final int bits;
   public final Object buffer;
   public final int gMask;
   public final int height;
   public final int rMask;
   public final int stride;
   public final int width;


   public RawBitmap(int var1, int var2, int var3, int var4, int var5, int var6, int var7, Object var8) {
      this.width = var1;
      this.height = var2;
      this.stride = var3;
      this.bits = var4;
      this.rMask = var5;
      this.gMask = var6;
      this.bMask = var7;
      this.buffer = var8;
   }

   public RawBitmap(int[] var1, Object var2) {
      int var3 = var1[0];
      this.width = var3;
      int var4 = var1[1];
      this.height = var4;
      int var5 = var1[2];
      this.stride = var5;
      int var6 = var1[3];
      this.bits = var6;
      int var7 = var1[4];
      this.rMask = var7;
      int var8 = var1[5];
      this.gMask = var8;
      int var9 = var1[6];
      this.bMask = var9;
      this.buffer = var2;
   }

   public int[] getHeader() {
      int[] var1 = new int[7];
      int var2 = this.width;
      var1[0] = var2;
      int var3 = this.height;
      var1[1] = var3;
      int var4 = this.stride;
      var1[2] = var4;
      int var5 = this.bits;
      var1[3] = var5;
      int var6 = this.rMask;
      var1[4] = var6;
      int var7 = this.gMask;
      var1[5] = var7;
      int var8 = this.bMask;
      var1[6] = var8;
      return var1;
   }
}
