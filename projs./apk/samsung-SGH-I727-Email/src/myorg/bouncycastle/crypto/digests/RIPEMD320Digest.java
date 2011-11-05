package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.digests.GeneralDigest;

public class RIPEMD320Digest extends GeneralDigest {

   private static final int DIGEST_LENGTH = 40;
   private int H0;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int H5;
   private int H6;
   private int H7;
   private int H8;
   private int H9;
   private int[] X;
   private int xOff;


   public RIPEMD320Digest() {
      int[] var1 = new int[16];
      this.X = var1;
      this.reset();
   }

   public RIPEMD320Digest(RIPEMD320Digest var1) {
      super(var1);
      int[] var2 = new int[16];
      this.X = var2;
      int var3 = var1.H0;
      this.H0 = var3;
      int var4 = var1.H1;
      this.H1 = var4;
      int var5 = var1.H2;
      this.H2 = var5;
      int var6 = var1.H3;
      this.H3 = var6;
      int var7 = var1.H4;
      this.H4 = var7;
      int var8 = var1.H5;
      this.H5 = var8;
      int var9 = var1.H6;
      this.H6 = var9;
      int var10 = var1.H7;
      this.H7 = var10;
      int var11 = var1.H8;
      this.H8 = var11;
      int var12 = var1.H9;
      this.H9 = var12;
      int[] var13 = var1.X;
      int[] var14 = this.X;
      int var15 = var1.X.length;
      System.arraycopy(var13, 0, var14, 0, var15);
      int var16 = var1.xOff;
      this.xOff = var16;
   }

   private int RL(int var1, int var2) {
      int var3 = var1 << var2;
      int var4 = 32 - var2;
      int var5 = var1 >>> var4;
      return var3 | var5;
   }

   private int f1(int var1, int var2, int var3) {
      return var1 ^ var2 ^ var3;
   }

   private int f2(int var1, int var2, int var3) {
      int var4 = var1 & var2;
      int var5 = ~var1 & var3;
      return var4 | var5;
   }

   private int f3(int var1, int var2, int var3) {
      return (~var2 | var1) ^ var3;
   }

   private int f4(int var1, int var2, int var3) {
      int var4 = var1 & var3;
      int var5 = ~var3 & var2;
      return var4 | var5;
   }

   private int f5(int var1, int var2, int var3) {
      return (~var3 | var2) ^ var1;
   }

   private void unpackWord(int var1, byte[] var2, int var3) {
      byte var4 = (byte)var1;
      var2[var3] = var4;
      int var5 = var3 + 1;
      byte var6 = (byte)(var1 >>> 8);
      var2[var5] = var6;
      int var7 = var3 + 2;
      byte var8 = (byte)(var1 >>> 16);
      var2[var7] = var8;
      int var9 = var3 + 3;
      byte var10 = (byte)(var1 >>> 24);
      var2[var9] = var10;
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      int var3 = this.H0;
      this.unpackWord(var3, var1, var2);
      int var4 = this.H1;
      int var5 = var2 + 4;
      this.unpackWord(var4, var1, var5);
      int var6 = this.H2;
      int var7 = var2 + 8;
      this.unpackWord(var6, var1, var7);
      int var8 = this.H3;
      int var9 = var2 + 12;
      this.unpackWord(var8, var1, var9);
      int var10 = this.H4;
      int var11 = var2 + 16;
      this.unpackWord(var10, var1, var11);
      int var12 = this.H5;
      int var13 = var2 + 20;
      this.unpackWord(var12, var1, var13);
      int var14 = this.H6;
      int var15 = var2 + 24;
      this.unpackWord(var14, var1, var15);
      int var16 = this.H7;
      int var17 = var2 + 28;
      this.unpackWord(var16, var1, var17);
      int var18 = this.H8;
      int var19 = var2 + 32;
      this.unpackWord(var18, var1, var19);
      int var20 = this.H9;
      int var21 = var2 + 36;
      this.unpackWord(var20, var1, var21);
      this.reset();
      return 40;
   }

   public String getAlgorithmName() {
      return "RIPEMD320";
   }

   public int getDigestSize() {
      return 40;
   }

   protected void processBlock() {
      // $FF: Couldn't be decompiled
   }

   protected void processLength(long var1) {
      if(this.xOff > 14) {
         this.processBlock();
      }

      int[] var3 = this.X;
      int var4 = (int)(65535L & var1);
      var3[14] = var4;
      int[] var5 = this.X;
      int var6 = (int)(var1 >>> 32);
      var5[15] = var6;
   }

   protected void processWord(byte[] var1, int var2) {
      int[] var3 = this.X;
      int var4 = this.xOff;
      int var5 = var4 + 1;
      this.xOff = var5;
      int var6 = var1[var2] & 255;
      int var7 = var2 + 1;
      int var8 = (var1[var7] & 255) << 8;
      int var9 = var6 | var8;
      int var10 = var2 + 2;
      int var11 = (var1[var10] & 255) << 16;
      int var12 = var9 | var11;
      int var13 = var2 + 3;
      int var14 = (var1[var13] & 255) << 24;
      int var15 = var12 | var14;
      var3[var4] = var15;
      if(this.xOff == 16) {
         this.processBlock();
      }
   }

   public void reset() {
      super.reset();
      this.H0 = 1732584193;
      this.H1 = -271733879;
      this.H2 = -1732584194;
      this.H3 = 271733878;
      this.H4 = -1009589776;
      this.H5 = 1985229328;
      this.H6 = -19088744;
      this.H7 = -1985229329;
      this.H8 = 19088743;
      this.H9 = 1009589775;
      this.xOff = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.X.length;
         if(var1 == var2) {
            return;
         }

         this.X[var1] = 0;
         ++var1;
      }
   }
}
