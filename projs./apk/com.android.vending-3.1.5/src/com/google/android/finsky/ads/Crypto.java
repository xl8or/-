package com.google.android.finsky.ads;

import com.google.android.finsky.ads.Encryptor;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public final class Crypto {

   private Crypto() {}

   public static String byteArrayToHexString(byte[] var0) {
      char[] var1 = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
      int var2 = var0.length * 2;
      StringBuilder var3 = new StringBuilder(var2);
      int var4 = 0;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            return var3.toString();
         }

         int var6 = (var0[var4] & 240) >>> 4;
         char var7 = var1[var6];
         var3.append(var7);
         int var9 = var0[var4] & 15;
         char var10 = var1[var9];
         var3.append(var10);
         ++var4;
      }
   }

   public static void calculateCrc(byte[] var0, int var1, int var2) {
      int var3 = 0;
      int var4 = 0;

      int var5;
      for(var5 = var1; var5 + 16 <= var2; var5 += 16) {
         for(int var6 = 0; var6 < 16; ++var6) {
            int var7 = var5 + var6;
            int var8 = var0[var7] & 255;
            var3 += var8;
            var4 += var3;
         }
      }

      while(var5 < var2) {
         int var9 = var0[var5] & 255;
         var3 += var9;
         var4 += var3;
         ++var5;
      }

      int var10 = var3 % '\ufff1';
      loadInt(var4 % '\ufff1' << 16 | var10, var0, var2);
   }

   public static byte[] calculateMd5(String var0) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest var1 = MessageDigest.getInstance("MD5");
      byte[] var2 = var0.getBytes("UTF-8");
      return var1.digest(var2);
   }

   public static byte[] encryptMobileId(int var0, int var1, String var2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      byte[] var3 = new byte[256];
      loadInt(var0, var3, 0);
      loadInt(var1, var3, 4);
      byte[] var4 = byteArrayToHexString(calculateMd5(var2)).getBytes("UTF-8");
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            byte var9 = 40;
            loadInt(0, var3, var9);
            byte var10 = 44;
            calculateCrc(var3, 0, var10);
            Random var11 = new Random();

            for(int var12 = 48; var12 < 256; ++var12) {
               byte var13 = (byte)(var11.nextInt(256) & 255);
               var3[var12] = var13;
            }

            byte[] var14 = new byte[256];
            (new Encryptor()).encrypt(var3, var14);
            return var14;
         }

         int var7 = var5 + 8;
         byte var8 = var4[var5];
         var3[var7] = var8;
         ++var5;
      }
   }

   private static void loadInt(int var0, byte[] var1, int var2) {
      byte var3 = (byte)(var0 & 255);
      var1[var2] = var3;
      int var4 = var2 + 1;
      byte var5 = (byte)(('\uff00' & var0) >>> 8);
      var1[var4] = var5;
      int var6 = var2 + 2;
      byte var7 = (byte)((16711680 & var0) >>> 16);
      var1[var6] = var7;
      int var8 = var2 + 3;
      byte var9 = (byte)((-16777216 & var0) >>> 24);
      var1[var8] = var9;
   }
}
