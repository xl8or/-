package org.apache.commons.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EndianUtils {

   public EndianUtils() {}

   private static int read(InputStream var0) throws IOException {
      int var1 = var0.read();
      if(-1 == var1) {
         throw new EOFException("Unexpected EOF reached");
      } else {
         return var1;
      }
   }

   public static double readSwappedDouble(InputStream var0) throws IOException {
      return Double.longBitsToDouble(readSwappedLong(var0));
   }

   public static double readSwappedDouble(byte[] var0, int var1) {
      return Double.longBitsToDouble(readSwappedLong(var0, var1));
   }

   public static float readSwappedFloat(InputStream var0) throws IOException {
      return Float.intBitsToFloat(readSwappedInteger(var0));
   }

   public static float readSwappedFloat(byte[] var0, int var1) {
      return Float.intBitsToFloat(readSwappedInteger(var0, var1));
   }

   public static int readSwappedInteger(InputStream var0) throws IOException {
      int var1 = read(var0);
      int var2 = read(var0);
      int var3 = read(var0);
      int var4 = read(var0);
      int var5 = (var1 & 255) << 0;
      int var6 = (var2 & 255) << 8;
      int var7 = var5 + var6;
      int var8 = (var3 & 255) << 16;
      int var9 = var7 + var8;
      int var10 = (var4 & 255) << 24;
      return var9 + var10;
   }

   public static int readSwappedInteger(byte[] var0, int var1) {
      int var2 = var1 + 0;
      int var3 = (var0[var2] & 255) << 0;
      int var4 = var1 + 1;
      int var5 = (var0[var4] & 255) << 8;
      int var6 = var3 + var5;
      int var7 = var1 + 2;
      int var8 = (var0[var7] & 255) << 16;
      int var9 = var6 + var8;
      int var10 = var1 + 3;
      int var11 = (var0[var10] & 255) << 24;
      return var9 + var11;
   }

   public static long readSwappedLong(InputStream var0) throws IOException {
      byte[] var1 = new byte[8];

      for(int var2 = 0; var2 < 8; ++var2) {
         byte var3 = (byte)read(var0);
         var1[var2] = var3;
      }

      return readSwappedLong(var1, 0);
   }

   public static long readSwappedLong(byte[] var0, int var1) {
      int var2 = var1 + 0;
      int var3 = (var0[var2] & 255) << 0;
      int var4 = var1 + 1;
      int var5 = (var0[var4] & 255) << 8;
      int var6 = var3 + var5;
      int var7 = var1 + 2;
      int var8 = (var0[var7] & 255) << 16;
      int var9 = var6 + var8;
      int var10 = var1 + 3;
      int var11 = (var0[var10] & 255) << 24;
      long var12 = (long)(var9 + var11);
      int var14 = var1 + 4;
      int var15 = (var0[var14] & 255) << 0;
      int var16 = var1 + 5;
      int var17 = (var0[var16] & 255) << 8;
      int var18 = var15 + var17;
      int var19 = var1 + 6;
      int var20 = (var0[var19] & 255) << 16;
      int var21 = var18 + var20;
      int var22 = var1 + 7;
      int var23 = (var0[var22] & 255) << 24;
      long var24 = (long)(var21 + var23) << 32;
      long var26 = 4294967295L & var12;
      return var24 + var26;
   }

   public static short readSwappedShort(InputStream var0) throws IOException {
      int var1 = (read(var0) & 255) << 0;
      int var2 = (read(var0) & 255) << 8;
      return (short)(var1 + var2);
   }

   public static short readSwappedShort(byte[] var0, int var1) {
      int var2 = var1 + 0;
      int var3 = (var0[var2] & 255) << 0;
      int var4 = var1 + 1;
      int var5 = (var0[var4] & 255) << 8;
      return (short)(var3 + var5);
   }

   public static long readSwappedUnsignedInteger(InputStream var0) throws IOException {
      int var1 = read(var0);
      int var2 = read(var0);
      int var3 = read(var0);
      int var4 = read(var0);
      int var5 = (var1 & 255) << 0;
      int var6 = (var2 & 255) << 8;
      int var7 = var5 + var6;
      int var8 = (var3 & 255) << 16;
      long var9 = (long)(var7 + var8);
      long var11 = (long)(var4 & 255) << 24;
      long var13 = 4294967295L & var9;
      return var11 + var13;
   }

   public static long readSwappedUnsignedInteger(byte[] var0, int var1) {
      int var2 = var1 + 0;
      int var3 = (var0[var2] & 255) << 0;
      int var4 = var1 + 1;
      int var5 = (var0[var4] & 255) << 8;
      int var6 = var3 + var5;
      int var7 = var1 + 2;
      int var8 = (var0[var7] & 255) << 16;
      long var9 = (long)(var6 + var8);
      int var11 = var1 + 3;
      long var12 = (long)(var0[var11] & 255) << 24;
      long var14 = 4294967295L & var9;
      return var12 + var14;
   }

   public static int readSwappedUnsignedShort(InputStream var0) throws IOException {
      int var1 = read(var0);
      int var2 = read(var0);
      int var3 = (var1 & 255) << 0;
      int var4 = (var2 & 255) << 8;
      return var3 + var4;
   }

   public static int readSwappedUnsignedShort(byte[] var0, int var1) {
      int var2 = var1 + 0;
      int var3 = (var0[var2] & 255) << 0;
      int var4 = var1 + 1;
      int var5 = (var0[var4] & 255) << 8;
      return var3 + var5;
   }

   public static double swapDouble(double var0) {
      return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(var0)));
   }

   public static float swapFloat(float var0) {
      return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(var0)));
   }

   public static int swapInteger(int var0) {
      int var1 = (var0 >> 0 & 255) << 24;
      int var2 = (var0 >> 8 & 255) << 16;
      int var3 = var1 + var2;
      int var4 = (var0 >> 16 & 255) << 8;
      int var5 = var3 + var4;
      int var6 = (var0 >> 24 & 255) << 0;
      return var5 + var6;
   }

   public static long swapLong(long var0) {
      long var2 = (var0 >> 0 & 255L) << 56;
      long var4 = (var0 >> 8 & 255L) << 48;
      long var6 = var2 + var4;
      long var8 = (var0 >> 16 & 255L) << 40;
      long var10 = var6 + var8;
      long var12 = (var0 >> 24 & 255L) << 32;
      long var14 = var10 + var12;
      long var16 = (var0 >> 32 & 255L) << 24;
      long var18 = var14 + var16;
      long var20 = (var0 >> 40 & 255L) << 16;
      long var22 = var18 + var20;
      long var24 = (var0 >> 48 & 255L) << 8;
      long var26 = var22 + var24;
      long var28 = (var0 >> 56 & 255L) << 0;
      return var26 + var28;
   }

   public static short swapShort(short var0) {
      int var1 = (var0 >> 0 & 255) << 8;
      int var2 = (var0 >> 8 & 255) << 0;
      return (short)(var1 + var2);
   }

   public static void writeSwappedDouble(OutputStream var0, double var1) throws IOException {
      long var3 = Double.doubleToLongBits(var1);
      writeSwappedLong(var0, var3);
   }

   public static void writeSwappedDouble(byte[] var0, int var1, double var2) {
      long var4 = Double.doubleToLongBits(var2);
      writeSwappedLong(var0, var1, var4);
   }

   public static void writeSwappedFloat(OutputStream var0, float var1) throws IOException {
      int var2 = Float.floatToIntBits(var1);
      writeSwappedInteger(var0, var2);
   }

   public static void writeSwappedFloat(byte[] var0, int var1, float var2) {
      int var3 = Float.floatToIntBits(var2);
      writeSwappedInteger(var0, var1, var3);
   }

   public static void writeSwappedInteger(OutputStream var0, int var1) throws IOException {
      byte var2 = (byte)(var1 >> 0 & 255);
      var0.write(var2);
      byte var3 = (byte)(var1 >> 8 & 255);
      var0.write(var3);
      byte var4 = (byte)(var1 >> 16 & 255);
      var0.write(var4);
      byte var5 = (byte)(var1 >> 24 & 255);
      var0.write(var5);
   }

   public static void writeSwappedInteger(byte[] var0, int var1, int var2) {
      int var3 = var1 + 0;
      byte var4 = (byte)(var2 >> 0 & 255);
      var0[var3] = var4;
      int var5 = var1 + 1;
      byte var6 = (byte)(var2 >> 8 & 255);
      var0[var5] = var6;
      int var7 = var1 + 2;
      byte var8 = (byte)(var2 >> 16 & 255);
      var0[var7] = var8;
      int var9 = var1 + 3;
      byte var10 = (byte)(var2 >> 24 & 255);
      var0[var9] = var10;
   }

   public static void writeSwappedLong(OutputStream var0, long var1) throws IOException {
      byte var3 = (byte)((int)(var1 >> 0 & 255L));
      var0.write(var3);
      byte var4 = (byte)((int)(var1 >> 8 & 255L));
      var0.write(var4);
      byte var5 = (byte)((int)(var1 >> 16 & 255L));
      var0.write(var5);
      byte var6 = (byte)((int)(var1 >> 24 & 255L));
      var0.write(var6);
      byte var7 = (byte)((int)(var1 >> 32 & 255L));
      var0.write(var7);
      byte var8 = (byte)((int)(var1 >> 40 & 255L));
      var0.write(var8);
      byte var9 = (byte)((int)(var1 >> 48 & 255L));
      var0.write(var9);
      byte var10 = (byte)((int)(var1 >> 56 & 255L));
      var0.write(var10);
   }

   public static void writeSwappedLong(byte[] var0, int var1, long var2) {
      int var4 = var1 + 0;
      byte var5 = (byte)((int)(var2 >> 0 & 255L));
      var0[var4] = var5;
      int var6 = var1 + 1;
      byte var7 = (byte)((int)(var2 >> 8 & 255L));
      var0[var6] = var7;
      int var8 = var1 + 2;
      byte var9 = (byte)((int)(var2 >> 16 & 255L));
      var0[var8] = var9;
      int var10 = var1 + 3;
      byte var11 = (byte)((int)(var2 >> 24 & 255L));
      var0[var10] = var11;
      int var12 = var1 + 4;
      byte var13 = (byte)((int)(var2 >> 32 & 255L));
      var0[var12] = var13;
      int var14 = var1 + 5;
      byte var15 = (byte)((int)(var2 >> 40 & 255L));
      var0[var14] = var15;
      int var16 = var1 + 6;
      byte var17 = (byte)((int)(var2 >> 48 & 255L));
      var0[var16] = var17;
      int var18 = var1 + 7;
      byte var19 = (byte)((int)(var2 >> 56 & 255L));
      var0[var18] = var19;
   }

   public static void writeSwappedShort(OutputStream var0, short var1) throws IOException {
      byte var2 = (byte)(var1 >> 0 & 255);
      var0.write(var2);
      byte var3 = (byte)(var1 >> 8 & 255);
      var0.write(var3);
   }

   public static void writeSwappedShort(byte[] var0, int var1, short var2) {
      int var3 = var1 + 0;
      byte var4 = (byte)(var2 >> 0 & 255);
      var0[var3] = var4;
      int var5 = var1 + 1;
      byte var6 = (byte)(var2 >> 8 & 255);
      var0[var5] = var6;
   }
}
