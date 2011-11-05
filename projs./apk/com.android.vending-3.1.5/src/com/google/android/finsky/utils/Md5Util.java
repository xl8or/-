package com.google.android.finsky.utils;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

   private Md5Util() {}

   public static String secureHash(byte[] var0) {
      return Base64.encodeToString(secureHashBytes(var0), 11);
   }

   public static byte[] secureHashBytes(byte[] var0) {
      MessageDigest var1;
      byte[] var3;
      try {
         var1 = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException var5) {
         var3 = null;
         return var3;
      }

      var1.update(var0);
      var3 = var1.digest();
      return var3;
   }
}
