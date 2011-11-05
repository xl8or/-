package myorg.bouncycastle.crypto.agreement.kdf;

import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.crypto.DerivationParameters;

public class DHKDFParameters implements DerivationParameters {

   private final DERObjectIdentifier algorithm;
   private final byte[] extraInfo;
   private final int keySize;
   private final byte[] z;


   public DHKDFParameters(DERObjectIdentifier var1, int var2, byte[] var3) {
      this.algorithm = var1;
      this.keySize = var2;
      this.z = var3;
      this.extraInfo = null;
   }

   public DHKDFParameters(DERObjectIdentifier var1, int var2, byte[] var3, byte[] var4) {
      this.algorithm = var1;
      this.keySize = var2;
      this.z = var3;
      this.extraInfo = var4;
   }

   public DERObjectIdentifier getAlgorithm() {
      return this.algorithm;
   }

   public byte[] getExtraInfo() {
      return this.extraInfo;
   }

   public int getKeySize() {
      return this.keySize;
   }

   public byte[] getZ() {
      return this.z;
   }
}
