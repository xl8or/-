package myorg.bouncycastle.crypto.signers;

import java.io.IOException;
import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.Signer;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class DSADigestSigner implements Signer {

   private final Digest digest;
   private final DSA dsaSigner;
   private boolean forSigning;


   public DSADigestSigner(DSA var1, Digest var2) {
      this.digest = var2;
      this.dsaSigner = var1;
   }

   private BigInteger[] derDecode(byte[] var1) throws IOException {
      ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
      BigInteger[] var3 = new BigInteger[2];
      BigInteger var4 = ((DERInteger)var2.getObjectAt(0)).getValue();
      var3[0] = var4;
      BigInteger var5 = ((DERInteger)var2.getObjectAt(1)).getValue();
      var3[1] = var5;
      return var3;
   }

   private byte[] derEncode(BigInteger var1, BigInteger var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      DERInteger var4 = new DERInteger(var1);
      var3.add(var4);
      DERInteger var5 = new DERInteger(var2);
      var3.add(var5);
      return (new DERSequence(var3)).getDEREncoded();
   }

   public byte[] generateSignature() {
      if(!this.forSigning) {
         throw new IllegalStateException("DSADigestSigner not initialised for signature generation.");
      } else {
         byte[] var1 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var1, 0);
         BigInteger[] var3 = this.dsaSigner.generateSignature(var1);
         BigInteger var4 = var3[0];
         BigInteger var5 = var3[1];
         return this.derEncode(var4, var5);
      }
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forSigning = var1;
      AsymmetricKeyParameter var3;
      if(var2 instanceof ParametersWithRandom) {
         var3 = (AsymmetricKeyParameter)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (AsymmetricKeyParameter)var2;
      }

      if(var1 && !var3.isPrivate()) {
         throw new IllegalArgumentException("Signing Requires Private Key.");
      } else if(!var1 && var3.isPrivate()) {
         throw new IllegalArgumentException("Verification Requires Public Key.");
      } else {
         this.reset();
         this.dsaSigner.init(var1, var2);
      }
   }

   public void reset() {
      this.digest.reset();
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public boolean verifySignature(byte[] var1) {
      if(this.forSigning) {
         throw new IllegalStateException("DSADigestSigner not initialised for verification");
      } else {
         byte[] var2 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var2, 0);

         boolean var8;
         boolean var9;
         try {
            BigInteger[] var4 = this.derDecode(var1);
            DSA var5 = this.dsaSigner;
            BigInteger var6 = var4[0];
            BigInteger var7 = var4[1];
            var8 = var5.verifySignature(var2, var6, var7);
         } catch (IOException var11) {
            var9 = false;
            return var9;
         }

         var9 = var8;
         return var9;
      }
   }
}
