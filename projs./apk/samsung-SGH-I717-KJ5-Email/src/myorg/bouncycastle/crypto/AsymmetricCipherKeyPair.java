package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;

public class AsymmetricCipherKeyPair {

   private CipherParameters privateParam;
   private CipherParameters publicParam;


   public AsymmetricCipherKeyPair(CipherParameters var1, CipherParameters var2) {
      this.publicParam = var1;
      this.privateParam = var2;
   }

   public CipherParameters getPrivate() {
      return this.privateParam;
   }

   public CipherParameters getPublic() {
      return this.publicParam;
   }
}
