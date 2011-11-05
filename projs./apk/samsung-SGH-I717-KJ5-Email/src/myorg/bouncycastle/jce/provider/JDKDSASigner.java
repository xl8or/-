package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.DSAKey;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA224Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.SHA384Digest;
import myorg.bouncycastle.crypto.digests.SHA512Digest;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.signers.DSASigner;
import myorg.bouncycastle.jce.interfaces.GOST3410Key;
import myorg.bouncycastle.jce.provider.DSAUtil;
import myorg.bouncycastle.jce.provider.GOST3410Util;
import myorg.bouncycastle.jce.provider.JDKKeyFactory;
import myorg.bouncycastle.jce.provider.util.NullDigest;

public class JDKDSASigner extends SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {

   private Digest digest;
   private SecureRandom random;
   private DSA signer;


   protected JDKDSASigner(Digest var1, DSA var2) {
      this.digest = var1;
      this.signer = var2;
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

   private byte[] derEncode(BigInteger var1, BigInteger var2) throws IOException {
      DERInteger[] var3 = new DERInteger[2];
      DERInteger var4 = new DERInteger(var1);
      var3[0] = var4;
      DERInteger var5 = new DERInteger(var2);
      var3[1] = var5;
      return (new DERSequence(var3)).getEncoded("DER");
   }

   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      Object var2;
      if(var1 instanceof GOST3410Key) {
         var2 = GOST3410Util.generatePrivateKeyParameter(var1);
      } else {
         var2 = DSAUtil.generatePrivateKeyParameter(var1);
      }

      if(this.random != null) {
         SecureRandom var3 = this.random;
         var2 = new ParametersWithRandom((CipherParameters)var2, var3);
      }

      this.digest.reset();
      this.signer.init((boolean)1, (CipherParameters)var2);
   }

   protected void engineInitSign(PrivateKey var1, SecureRandom var2) throws InvalidKeyException {
      this.random = var2;
      this.engineInitSign(var1);
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2;
      if(var1 instanceof GOST3410Key) {
         var2 = GOST3410Util.generatePublicKeyParameter(var1);
      } else if(var1 instanceof DSAKey) {
         var2 = DSAUtil.generatePublicKeyParameter(var1);
      } else {
         try {
            var1 = JDKKeyFactory.createPublicKeyFromDERStream(var1.getEncoded());
            if(!(var1 instanceof DSAKey)) {
               throw new InvalidKeyException("can\'t recognise key type in DSA based signer");
            }

            var2 = DSAUtil.generatePublicKeyParameter(var1);
         } catch (Exception var4) {
            throw new InvalidKeyException("can\'t recognise key type in DSA based signer");
         }
      }

      this.digest.reset();
      this.signer.init((boolean)0, var2);
   }

   protected void engineSetParameter(String var1, Object var2) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected void engineSetParameter(AlgorithmParameterSpec var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected byte[] engineSign() throws SignatureException {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var1, 0);

      try {
         BigInteger[] var3 = this.signer.generateSignature(var1);
         BigInteger var4 = var3[0];
         BigInteger var5 = var3[1];
         byte[] var6 = this.derEncode(var4, var5);
         return var6;
      } catch (Exception var8) {
         String var7 = var8.toString();
         throw new SignatureException(var7);
      }
   }

   protected void engineUpdate(byte var1) throws SignatureException {
      this.digest.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException {
      this.digest.update(var1, var2, var3);
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      byte[] var2 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var2, 0);

      BigInteger[] var4;
      try {
         var4 = this.derDecode(var1);
      } catch (Exception var10) {
         throw new SignatureException("error decoding signature bytes.");
      }

      DSA var6 = this.signer;
      BigInteger var7 = var4[0];
      BigInteger var8 = var4[1];
      return var6.verifySignature(var2, var7, var8);
   }

   public static class dsa224 extends JDKDSASigner {

      public dsa224() {
         SHA224Digest var1 = new SHA224Digest();
         DSASigner var2 = new DSASigner();
         super(var1, var2);
      }
   }

   public static class noneDSA extends JDKDSASigner {

      public noneDSA() {
         NullDigest var1 = new NullDigest();
         DSASigner var2 = new DSASigner();
         super(var1, var2);
      }
   }

   public static class stdDSA extends JDKDSASigner {

      public stdDSA() {
         SHA1Digest var1 = new SHA1Digest();
         DSASigner var2 = new DSASigner();
         super(var1, var2);
      }
   }

   public static class dsa512 extends JDKDSASigner {

      public dsa512() {
         SHA512Digest var1 = new SHA512Digest();
         DSASigner var2 = new DSASigner();
         super(var1, var2);
      }
   }

   public static class dsa384 extends JDKDSASigner {

      public dsa384() {
         SHA384Digest var1 = new SHA384Digest();
         DSASigner var2 = new DSASigner();
         super(var1, var2);
      }
   }

   public static class dsa256 extends JDKDSASigner {

      public dsa256() {
         SHA256Digest var1 = new SHA256Digest();
         DSASigner var2 = new DSASigner();
         super(var1, var2);
      }
   }
}
