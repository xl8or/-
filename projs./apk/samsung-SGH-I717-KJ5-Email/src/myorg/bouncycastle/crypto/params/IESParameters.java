package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;

public class IESParameters implements CipherParameters {

   private byte[] derivation;
   private byte[] encoding;
   private int macKeySize;


   public IESParameters(byte[] var1, byte[] var2, int var3) {
      this.derivation = var1;
      this.encoding = var2;
      this.macKeySize = var3;
   }

   public byte[] getDerivationV() {
      return this.derivation;
   }

   public byte[] getEncodingV() {
      return this.encoding;
   }

   public int getMacKeySize() {
      return this.macKeySize;
   }
}
