package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class KeyParameter implements CipherParameters {

   private byte[] key;


   public KeyParameter(byte[] var1) {
      int var2 = var1.length;
      this(var1, 0, var2);
   }

   public KeyParameter(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      this.key = var4;
      byte[] var5 = this.key;
      System.arraycopy(var1, var2, var5, 0, var3);
   }

   public byte[] getKey() {
      return this.key;
   }
}
