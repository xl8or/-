package org.xbill.DNS.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HMAC {

   private static final byte IPAD = 54;
   private static final byte OPAD = 92;
   private static final byte PADLEN = 64;
   MessageDigest digest;
   private byte[] ipad;
   private byte[] opad;


   public HMAC(String var1, byte[] var2) {
      try {
         MessageDigest var3 = MessageDigest.getInstance(var1);
         this.digest = var3;
      } catch (NoSuchAlgorithmException var6) {
         String var5 = "unknown digest algorithm " + var1;
         throw new IllegalArgumentException(var5);
      }

      this.init(var2);
   }

   public HMAC(MessageDigest var1, byte[] var2) {
      var1.reset();
      this.digest = var1;
      this.init(var2);
   }

   private void init(byte[] param1) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      this.digest.reset();
      MessageDigest var1 = this.digest;
      byte[] var2 = this.ipad;
      var1.update(var2);
   }

   public byte[] sign() {
      byte[] var1 = this.digest.digest();
      this.digest.reset();
      MessageDigest var2 = this.digest;
      byte[] var3 = this.opad;
      var2.update(var3);
      return this.digest.digest(var1);
   }

   public void update(byte[] var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public boolean verify(byte[] var1) {
      byte[] var2 = this.sign();
      return Arrays.equals(var1, var2);
   }
}
