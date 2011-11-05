package myorg.bouncycastle.jce.provider.asymmetric.ec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA224Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.SHA384Digest;
import myorg.bouncycastle.crypto.digests.SHA512Digest;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.signers.ECDSASigner;
import myorg.bouncycastle.crypto.signers.ECNRSigner;
import myorg.bouncycastle.jce.interfaces.ECKey;
import myorg.bouncycastle.jce.provider.DSABase;
import myorg.bouncycastle.jce.provider.DSAEncoder;
import myorg.bouncycastle.jce.provider.JDKKeyFactory;
import myorg.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import myorg.bouncycastle.jce.provider.util.NullDigest;

public class Signature extends DSABase {

   Signature(Digest var1, DSA var2, DSAEncoder var3) {
      super(var1, var2, var3);
   }

   protected void engineInitSign(PrivateKey var1, SecureRandom var2) throws InvalidKeyException {
      if(var1 instanceof ECKey) {
         AsymmetricKeyParameter var3 = ECUtil.generatePrivateKeyParameter(var1);
         this.digest.reset();
         if(var2 != null) {
            DSA var4 = this.signer;
            ParametersWithRandom var5 = new ParametersWithRandom(var3, var2);
            var4.init((boolean)1, var5);
         } else {
            this.signer.init((boolean)1, var3);
         }
      } else {
         throw new InvalidKeyException("can\'t recognise key type in ECDSA based signer");
      }
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2;
      if(var1 instanceof ECPublicKey) {
         var2 = ECUtil.generatePublicKeyParameter(var1);
      } else {
         try {
            var1 = JDKKeyFactory.createPublicKeyFromDERStream(var1.getEncoded());
            if(!(var1 instanceof ECPublicKey)) {
               throw new InvalidKeyException("can\'t recognise key type in ECDSA based signer");
            }

            var2 = ECUtil.generatePublicKeyParameter(var1);
         } catch (Exception var4) {
            throw new InvalidKeyException("can\'t recognise key type in ECDSA based signer");
         }
      }

      this.digest.reset();
      this.signer.init((boolean)0, var2);
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class StdDSAEncoder implements DSAEncoder {

      private StdDSAEncoder() {}

      // $FF: synthetic method
      StdDSAEncoder(Signature.1 var1) {
         this();
      }

      public BigInteger[] decode(byte[] var1) throws IOException {
         ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
         BigInteger[] var3 = new BigInteger[2];
         BigInteger var4 = ((DERInteger)var2.getObjectAt(0)).getValue();
         var3[0] = var4;
         BigInteger var5 = ((DERInteger)var2.getObjectAt(1)).getValue();
         var3[1] = var5;
         return var3;
      }

      public byte[] encode(BigInteger var1, BigInteger var2) throws IOException {
         ASN1EncodableVector var3 = new ASN1EncodableVector();
         DERInteger var4 = new DERInteger(var1);
         var3.add(var4);
         DERInteger var5 = new DERInteger(var2);
         var3.add(var5);
         return (new DERSequence(var3)).getEncoded("DER");
      }
   }

   public static class ecCVCDSA256 extends Signature {

      public ecCVCDSA256() {
         SHA256Digest var1 = new SHA256Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.CVCDSAEncoder var3 = new Signature.CVCDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecCVCDSA extends Signature {

      public ecCVCDSA() {
         SHA1Digest var1 = new SHA1Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.CVCDSAEncoder var3 = new Signature.CVCDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecCVCDSA224 extends Signature {

      public ecCVCDSA224() {
         SHA224Digest var1 = new SHA224Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.CVCDSAEncoder var3 = new Signature.CVCDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecDSARipeMD160 extends Signature {

      public ecDSARipeMD160() {
         RIPEMD160Digest var1 = new RIPEMD160Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecDSA256 extends Signature {

      public ecDSA256() {
         SHA256Digest var1 = new SHA256Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   private static class CVCDSAEncoder implements DSAEncoder {

      private CVCDSAEncoder() {}

      // $FF: synthetic method
      CVCDSAEncoder(Signature.1 var1) {
         this();
      }

      private byte[] makeUnsigned(BigInteger var1) {
         byte[] var2 = var1.toByteArray();
         byte[] var5;
         if(var2[0] == 0) {
            byte[] var3 = new byte[var2.length - 1];
            int var4 = var3.length;
            System.arraycopy(var2, 1, var3, 0, var4);
            var5 = var3;
         } else {
            var5 = var2;
         }

         return var5;
      }

      public BigInteger[] decode(byte[] var1) throws IOException {
         BigInteger[] var2 = new BigInteger[2];
         byte[] var3 = new byte[var1.length / 2];
         byte[] var4 = new byte[var1.length / 2];
         int var5 = var3.length;
         System.arraycopy(var1, 0, var3, 0, var5);
         int var6 = var3.length;
         int var7 = var4.length;
         System.arraycopy(var1, var6, var4, 0, var7);
         BigInteger var8 = new BigInteger(1, var3);
         var2[0] = var8;
         BigInteger var9 = new BigInteger(1, var4);
         var2[1] = var9;
         return var2;
      }

      public byte[] encode(BigInteger var1, BigInteger var2) throws IOException {
         byte[] var3 = this.makeUnsigned(var1);
         byte[] var4 = this.makeUnsigned(var2);
         int var5 = var3.length;
         int var6 = var4.length;
         byte[] var7;
         if(var5 > var6) {
            var7 = new byte[var3.length * 2];
         } else {
            var7 = new byte[var4.length * 2];
         }

         int var8 = var7.length / 2;
         int var9 = var3.length;
         int var10 = var8 - var9;
         int var11 = var3.length;
         System.arraycopy(var3, 0, var7, var10, var11);
         int var12 = var7.length;
         int var13 = var4.length;
         int var14 = var12 - var13;
         int var15 = var4.length;
         System.arraycopy(var4, 0, var7, var14, var15);
         return var7;
      }
   }

   public static class ecDSA384 extends Signature {

      public ecDSA384() {
         SHA384Digest var1 = new SHA384Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecNR extends Signature {

      public ecNR() {
         SHA1Digest var1 = new SHA1Digest();
         ECNRSigner var2 = new ECNRSigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecDSAnone extends Signature {

      public ecDSAnone() {
         NullDigest var1 = new NullDigest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecDSA512 extends Signature {

      public ecDSA512() {
         SHA512Digest var1 = new SHA512Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecNR256 extends Signature {

      public ecNR256() {
         SHA256Digest var1 = new SHA256Digest();
         ECNRSigner var2 = new ECNRSigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecNR224 extends Signature {

      public ecNR224() {
         SHA224Digest var1 = new SHA224Digest();
         ECNRSigner var2 = new ECNRSigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecNR384 extends Signature {

      public ecNR384() {
         SHA384Digest var1 = new SHA384Digest();
         ECNRSigner var2 = new ECNRSigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecNR512 extends Signature {

      public ecNR512() {
         SHA512Digest var1 = new SHA512Digest();
         ECNRSigner var2 = new ECNRSigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecDSA224 extends Signature {

      public ecDSA224() {
         SHA224Digest var1 = new SHA224Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }

   public static class ecDSA extends Signature {

      public ecDSA() {
         SHA1Digest var1 = new SHA1Digest();
         ECDSASigner var2 = new ECDSASigner();
         Signature.StdDSAEncoder var3 = new Signature.StdDSAEncoder((Signature.1)null);
         super(var1, var2, var3);
      }
   }
}
