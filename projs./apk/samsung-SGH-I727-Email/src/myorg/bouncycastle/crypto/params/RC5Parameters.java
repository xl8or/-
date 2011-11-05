package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class RC5Parameters implements CipherParameters {

   private byte[] key;
   private int rounds;


   public RC5Parameters(byte[] var1, int var2) {
      if(var1.length > 255) {
         throw new IllegalArgumentException("RC5 key length can be no greater than 255");
      } else {
         byte[] var3 = new byte[var1.length];
         this.key = var3;
         this.rounds = var2;
         byte[] var4 = this.key;
         int var5 = var1.length;
         System.arraycopy(var1, 0, var4, 0, var5);
      }
   }

   public byte[] getKey() {
      return this.key;
   }

   public int getRounds() {
      return this.rounds;
   }
}
