package com.google.android.finsky.utils;

import android.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Util {

   public Sha1Util() {}

   private static Sha1Util.DigestResult digest(InputStream var0) throws IOException {
      byte[] var1 = new byte[1024];
      long var2 = 0L;

      MessageDigest var4;
      Sha1Util.DigestResult var11;
      try {
         var4 = MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException var14) {
         var11 = null;
         return var11;
      }

      MessageDigest var5 = var4;

      while(true) {
         int var6 = var0.read(var1);
         if(var6 < 0) {
            var0.close();
            String var13 = Base64.encodeToString(var5.digest(), 11);
            var11 = new Sha1Util.DigestResult(var13, var2, (Sha1Util.1)null);
            return var11;
         }

         if(var6 != 0) {
            int var7 = var1.length;
            if(var6 == var7) {
               var5.update(var1);
            } else {
               byte[] var12 = new byte[var6];
               System.arraycopy(var1, 0, var12, 0, var6);
               var5.update(var12);
            }

            long var8 = (long)var6;
            var2 += var8;
         }
      }
   }

   public static String secureHash(String var0) {
      return secureHash(var0.getBytes());
   }

   public static String secureHash(byte[] var0) {
      MessageDigest var1;
      String var3;
      try {
         var1 = MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException var5) {
         var3 = null;
         return var3;
      }

      var1.update(var0);
      var3 = Base64.encodeToString(var1.digest(), 11);
      return var3;
   }

   public static boolean verify(InputStream var0, String var1, long var2) throws IOException {
      Sha1Util.DigestResult var4 = digest(var0);
      String var5 = var4.sha1HashBase64;
      boolean var6;
      if(var1.equals(var5) && var4.byteCount == var2) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   private static class DigestResult {

      public final long byteCount;
      public final String sha1HashBase64;


      private DigestResult(String var1, long var2) {
         this.byteCount = var2;
         this.sha1HashBase64 = var1;
      }

      // $FF: synthetic method
      DigestResult(String var1, long var2, Sha1Util.1 var4) {
         this(var1, var2);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
