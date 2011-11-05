package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class RC2Parameters implements CipherParameters {

   private int bits;
   private byte[] key;


   public RC2Parameters(byte[] var1) {
      int var2;
      if(var1.length > 128) {
         var2 = 1024;
      } else {
         var2 = var1.length * 8;
      }

      this(var1, var2);
   }

   public RC2Parameters(byte[] var1, int var2) {
      byte[] var3 = new byte[var1.length];
      this.key = var3;
      this.bits = var2;
      byte[] var4 = this.key;
      int var5 = var1.length;
      System.arraycopy(var1, 0, var4, 0, var5);
   }

   public int getEffectiveKeyBits() {
      return this.bits;
   }

   public byte[] getKey() {
      return this.key;
   }
}
