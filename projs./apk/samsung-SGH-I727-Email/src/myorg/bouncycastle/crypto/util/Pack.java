package myorg.bouncycastle.crypto.util;


public abstract class Pack {

   public Pack() {}

   public static int bigEndianToInt(byte[] var0, int var1) {
      int var2 = var0[var1] << 24;
      int var3 = var1 + 1;
      int var4 = (var0[var3] & 255) << 16;
      int var5 = var2 | var4;
      int var6 = var3 + 1;
      int var7 = (var0[var6] & 255) << 8;
      int var8 = var5 | var7;
      int var9 = var6 + 1;
      int var10 = var0[var9] & 255;
      return var8 | var10;
   }

   public static long bigEndianToLong(byte[] var0, int var1) {
      int var2 = bigEndianToInt(var0, var1);
      int var3 = var1 + 4;
      int var4 = bigEndianToInt(var0, var3);
      long var5 = ((long)var2 & 4294967295L) << 32;
      long var7 = (long)var4 & 4294967295L;
      return var5 | var7;
   }

   public static void intToBigEndian(int var0, byte[] var1, int var2) {
      byte var3 = (byte)(var0 >>> 24);
      var1[var2] = var3;
      int var4 = var2 + 1;
      byte var5 = (byte)(var0 >>> 16);
      var1[var4] = var5;
      int var6 = var4 + 1;
      byte var7 = (byte)(var0 >>> 8);
      var1[var6] = var7;
      int var8 = var6 + 1;
      byte var9 = (byte)var0;
      var1[var8] = var9;
   }

   public static void longToBigEndian(long var0, byte[] var2, int var3) {
      intToBigEndian((int)(var0 >>> 32), var2, var3);
      int var4 = (int)(4294967295L & var0);
      int var5 = var3 + 4;
      intToBigEndian(var4, var2, var5);
   }
}
