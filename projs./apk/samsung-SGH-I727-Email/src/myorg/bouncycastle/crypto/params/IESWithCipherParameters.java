package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.params.IESParameters;

public class IESWithCipherParameters extends IESParameters {

   private int cipherKeySize;


   public IESWithCipherParameters(byte[] var1, byte[] var2, int var3, int var4) {
      super(var1, var2, var3);
      this.cipherKeySize = var4;
   }

   public int getCipherKeySize() {
      return this.cipherKeySize;
   }
}
