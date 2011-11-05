package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV implements CipherParameters {

   private byte[] iv;
   private CipherParameters parameters;


   public ParametersWithIV(CipherParameters var1, byte[] var2) {
      int var3 = var2.length;
      this(var1, var2, 0, var3);
   }

   public ParametersWithIV(CipherParameters var1, byte[] var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      this.iv = var5;
      this.parameters = var1;
      byte[] var6 = this.iv;
      System.arraycopy(var2, var3, var6, 0, var4);
   }

   public byte[] getIV() {
      return this.iv;
   }

   public CipherParameters getParameters() {
      return this.parameters;
   }
}
