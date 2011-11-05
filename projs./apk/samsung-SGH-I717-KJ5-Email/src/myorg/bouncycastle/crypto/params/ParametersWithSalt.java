package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class ParametersWithSalt implements CipherParameters {

   private CipherParameters parameters;
   private byte[] salt;


   public ParametersWithSalt(CipherParameters var1, byte[] var2) {
      int var3 = var2.length;
      this(var1, var2, 0, var3);
   }

   public ParametersWithSalt(CipherParameters var1, byte[] var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      this.salt = var5;
      this.parameters = var1;
      byte[] var6 = this.salt;
      System.arraycopy(var2, var3, var6, 0, var4);
   }

   public CipherParameters getParameters() {
      return this.parameters;
   }

   public byte[] getSalt() {
      return this.salt;
   }
}
