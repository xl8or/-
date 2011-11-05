package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.DerivationParameters;

public class MGFParameters implements DerivationParameters {

   byte[] seed;


   public MGFParameters(byte[] var1) {
      int var2 = var1.length;
      this(var1, 0, var2);
   }

   public MGFParameters(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      this.seed = var4;
      byte[] var5 = this.seed;
      System.arraycopy(var1, var2, var5, 0, var3);
   }

   public byte[] getSeed() {
      return this.seed;
   }
}
