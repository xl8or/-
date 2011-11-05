package myorg.bouncycastle.jce.provider;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.engines.RSABlindedEngine;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.signers.ISO9796d2Signer;
import myorg.bouncycastle.jce.provider.RSAUtil;

public class JDKISOSignature extends SignatureSpi {

   private ISO9796d2Signer signer;


   protected JDKISOSignature(Digest var1, AsymmetricBlockCipher var2) {
      ISO9796d2Signer var3 = new ISO9796d2Signer(var2, var1, (boolean)1);
      this.signer = var3;
   }

   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      RSAKeyParameters var2 = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var1);
      this.signer.init((boolean)1, var2);
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      RSAKeyParameters var2 = RSAUtil.generatePublicKeyParameter((RSAPublicKey)var1);
      this.signer.init((boolean)0, var2);
   }

   protected void engineSetParameter(String var1, Object var2) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected void engineSetParameter(AlgorithmParameterSpec var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected byte[] engineSign() throws SignatureException {
      try {
         byte[] var1 = this.signer.generateSignature();
         return var1;
      } catch (Exception var3) {
         String var2 = var3.toString();
         throw new SignatureException(var2);
      }
   }

   protected void engineUpdate(byte var1) throws SignatureException {
      this.signer.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException {
      this.signer.update(var1, var2, var3);
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      return this.signer.verifySignature(var1);
   }

   public static class SHA1WithRSAEncryption extends JDKISOSignature {

      public SHA1WithRSAEncryption() {
         SHA1Digest var1 = new SHA1Digest();
         RSABlindedEngine var2 = new RSABlindedEngine();
         super(var1, var2);
      }
   }

   public static class RIPEMD160WithRSAEncryption extends JDKISOSignature {

      public RIPEMD160WithRSAEncryption() {
         RIPEMD160Digest var1 = new RIPEMD160Digest();
         RSABlindedEngine var2 = new RSABlindedEngine();
         super(var1, var2);
      }
   }

   public static class MD5WithRSAEncryption extends JDKISOSignature {

      public MD5WithRSAEncryption() {
         MD5Digest var1 = new MD5Digest();
         RSABlindedEngine var2 = new RSABlindedEngine();
         super(var1, var2);
      }
   }
}
