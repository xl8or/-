package org.mozilla.intl.chardet;


public abstract class nsVerifier {

   static final int eBitSft4bits = 2;
   static final byte eError = 1;
   static final byte eItsMe = 2;
   static final int eSftMsk4bits = 7;
   static final byte eStart = 0;
   static final int eUnitMsk4bits = 15;
   static final int eidxSft4bits = 3;


   nsVerifier() {}

   public static byte getNextState(nsVerifier var0, byte var1, byte var2) {
      int[] var3 = var0.states();
      int var4 = var0.stFactor() * var2;
      int[] var5 = var0.cclass();
      int var6 = (var1 & 255) >> 3;
      int var7 = var5[var6];
      int var8 = (var1 & 7) << 2;
      int var9 = var7 >> var8 & 15;
      int var10 = (var4 + var9 & 255) >> 3;
      int var11 = var3[var10];
      int var12 = var0.stFactor() * var2;
      int[] var13 = var0.cclass();
      int var14 = (var1 & 255) >> 3;
      int var15 = var13[var14];
      int var16 = (var1 & 7) << 2;
      int var17 = var15 >> var16 & 15;
      int var18 = (var12 + var17 & 255 & 7) << 2;
      return (byte)(var11 >> var18 & 15 & 255);
   }

   public abstract int[] cclass();

   public abstract String charset();

   public abstract boolean isUCS2();

   public abstract int stFactor();

   public abstract int[] states();
}
