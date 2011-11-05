package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class ParametersWithSBox implements CipherParameters {

   private CipherParameters parameters;
   private byte[] sBox;


   public ParametersWithSBox(CipherParameters var1, byte[] var2) {
      this.parameters = var1;
      this.sBox = var2;
   }

   public CipherParameters getParameters() {
      return this.parameters;
   }

   public byte[] getSBox() {
      return this.sBox;
   }
}
