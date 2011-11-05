package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.GOST3411Digest;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.signers.ECGOST3410Signer;
import myorg.bouncycastle.crypto.signers.GOST3410Signer;
import myorg.bouncycastle.jce.interfaces.ECKey;
import myorg.bouncycastle.jce.interfaces.ECPublicKey;
import myorg.bouncycastle.jce.interfaces.GOST3410Key;
import myorg.bouncycastle.jce.provider.GOST3410Util;
import myorg.bouncycastle.jce.provider.JDKKeyFactory;
import myorg.bouncycastle.jce.provider.asymmetric.ec.ECUtil;

public class JDKGOST3410Signer extends SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {

   private Digest digest;
   private SecureRandom random;
   private DSA signer;


   protected JDKGOST3410Signer(Digest var1, DSA var2) {
      this.digest = var1;
      this.signer = var2;
   }

   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2;
      if(var1 instanceof ECKey) {
         var2 = ECUtil.generatePrivateKeyParameter(var1);
      } else {
         var2 = GOST3410Util.generatePrivateKeyParameter(var1);
      }

      this.digest.reset();
      if(this.random != null) {
         DSA var3 = this.signer;
         SecureRandom var4 = this.random;
         ParametersWithRandom var5 = new ParametersWithRandom(var2, var4);
         var3.init((boolean)1, var5);
      } else {
         this.signer.init((boolean)1, var2);
      }
   }

   protected void engineInitSign(PrivateKey var1, SecureRandom var2) throws InvalidKeyException {
      this.random = var2;
      this.engineInitSign(var1);
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2;
      if(var1 instanceof ECPublicKey) {
         var2 = ECUtil.generatePublicKeyParameter(var1);
      } else if(var1 instanceof GOST3410Key) {
         var2 = GOST3410Util.generatePublicKeyParameter(var1);
      } else {
         try {
            var1 = JDKKeyFactory.createPublicKeyFromDERStream(var1.getEncoded());
            if(!(var1 instanceof ECPublicKey)) {
               throw new InvalidKeyException("can\'t recognise key type in DSA based signer");
            }

            var2 = ECUtil.generatePublicKeyParameter(var1);
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
      // $FF: Couldn't be decompiled
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

      BigInteger[] var6;
      try {
         byte[] var4 = new byte[32];
         byte[] var5 = new byte[32];
         System.arraycopy(var1, 0, var5, 0, 32);
         System.arraycopy(var1, 32, var4, 0, 32);
         var6 = new BigInteger[2];
         BigInteger var7 = new BigInteger(1, var4);
         var6[0] = var7;
         BigInteger var8 = new BigInteger(1, var5);
         var6[1] = var8;
      } catch (Exception var13) {
         throw new SignatureException("error decoding signature bytes.");
      }

      DSA var9 = this.signer;
      BigInteger var10 = var6[0];
      BigInteger var11 = var6[1];
      return var9.verifySignature(var2, var10, var11);
   }

   public static class ecgost3410 extends JDKGOST3410Signer {

      public ecgost3410() {
         GOST3411Digest var1 = new GOST3411Digest();
         ECGOST3410Signer var2 = new ECGOST3410Signer();
         super(var1, var2);
      }
   }

   public static class gost3410 extends JDKGOST3410Signer {

      public gost3410() {
         GOST3411Digest var1 = new GOST3411Digest();
         GOST3410Signer var2 = new GOST3410Signer();
         super(var1, var2);
      }
   }
}
