package myorg.bouncycastle.crypto.agreement.kdf;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.DerivationParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.agreement.kdf.DHKDFParameters;
import myorg.bouncycastle.crypto.generators.KDF2BytesGenerator;
import myorg.bouncycastle.crypto.params.KDFParameters;

public class ECDHKEKGenerator implements DerivationFunction {

   private DERObjectIdentifier algorithm;
   private DerivationFunction kdf;
   private int keySize;
   private byte[] z;


   public ECDHKEKGenerator(Digest var1) {
      KDF2BytesGenerator var2 = new KDF2BytesGenerator(var1);
      this.kdf = var2;
   }

   private byte[] integerToBytes(int var1) {
      byte[] var2 = new byte[4];
      byte var3 = (byte)(var1 >> 24);
      var2[0] = var3;
      byte var4 = (byte)(var1 >> 16);
      var2[1] = var4;
      byte var5 = (byte)(var1 >> 8);
      var2[2] = var5;
      byte var6 = (byte)var1;
      var2[3] = var6;
      return var2;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      DERObjectIdentifier var5 = this.algorithm;
      DERNull var6 = new DERNull();
      AlgorithmIdentifier var7 = new AlgorithmIdentifier(var5, var6);
      var4.add(var7);
      int var8 = this.keySize;
      byte[] var9 = this.integerToBytes(var8);
      DEROctetString var10 = new DEROctetString(var9);
      DERTaggedObject var11 = new DERTaggedObject((boolean)1, 2, var10);
      var4.add(var11);
      DerivationFunction var12 = this.kdf;
      byte[] var13 = this.z;
      byte[] var14 = (new DERSequence(var4)).getDEREncoded();
      KDFParameters var15 = new KDFParameters(var13, var14);
      var12.init(var15);
      return this.kdf.generateBytes(var1, var2, var3);
   }

   public Digest getDigest() {
      return this.kdf.getDigest();
   }

   public void init(DerivationParameters var1) {
      DHKDFParameters var2 = (DHKDFParameters)var1;
      DERObjectIdentifier var3 = var2.getAlgorithm();
      this.algorithm = var3;
      int var4 = var2.getKeySize();
      this.keySize = var4;
      byte[] var5 = var2.getZ();
      this.z = var5;
   }
}
