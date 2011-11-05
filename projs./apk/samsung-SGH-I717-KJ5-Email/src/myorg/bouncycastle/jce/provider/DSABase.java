package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.jce.provider.DSAEncoder;

public abstract class DSABase extends SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {

   protected Digest digest;
   protected DSAEncoder encoder;
   protected DSA signer;


   protected DSABase(Digest var1, DSA var2, DSAEncoder var3) {
      this.digest = var1;
      this.signer = var2;
      this.encoder = var3;
   }

   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      this.engineInitSign(var1, (SecureRandom)null);
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
         DSAEncoder var4 = this.encoder;
         BigInteger var5 = var3[0];
         BigInteger var6 = var3[1];
         byte[] var7 = var4.encode(var5, var6);
         return var7;
      } catch (Exception var9) {
         String var8 = var9.toString();
         throw new SignatureException(var8);
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
         var4 = this.encoder.decode(var1);
      } catch (Exception var10) {
         throw new SignatureException("error decoding signature bytes.");
      }

      DSA var6 = this.signer;
      BigInteger var7 = var4[0];
      BigInteger var8 = var4[1];
      return var6.verifySignature(var2, var7, var8);
   }
}
