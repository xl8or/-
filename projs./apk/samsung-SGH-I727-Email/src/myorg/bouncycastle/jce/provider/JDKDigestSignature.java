package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DigestInfo;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.MD2Digest;
import myorg.bouncycastle.crypto.digests.MD4Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD128Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD256Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA224Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.SHA384Digest;
import myorg.bouncycastle.crypto.digests.SHA512Digest;
import myorg.bouncycastle.crypto.encodings.PKCS1Encoding;
import myorg.bouncycastle.crypto.engines.RSABlindedEngine;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.jce.provider.RSAUtil;
import myorg.bouncycastle.jce.provider.util.NullDigest;

public class JDKDigestSignature extends SignatureSpi {

   private AlgorithmIdentifier algId;
   private AsymmetricBlockCipher cipher;
   private Digest digest;


   protected JDKDigestSignature(DERObjectIdentifier var1, Digest var2, AsymmetricBlockCipher var3) {
      this.digest = var2;
      this.cipher = var3;
      DERNull var4 = DERNull.INSTANCE;
      AlgorithmIdentifier var5 = new AlgorithmIdentifier(var1, var4);
      this.algId = var5;
   }

   protected JDKDigestSignature(Digest var1, AsymmetricBlockCipher var2) {
      this.digest = var1;
      this.cipher = var2;
      this.algId = null;
   }

   private byte[] derEncode(byte[] var1) throws IOException {
      byte[] var2;
      if(this.algId == null) {
         var2 = var1;
      } else {
         AlgorithmIdentifier var3 = this.algId;
         var2 = (new DigestInfo(var3, var1)).getEncoded("DER");
      }

      return var2;
   }

   private String getType(Object var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getClass().getName();
      }

      return var2;
   }

   protected Object engineGetParameter(String var1) {
      return null;
   }

   protected AlgorithmParameters engineGetParameters() {
      return null;
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      if(!(var1 instanceof RSAPrivateKey)) {
         StringBuilder var2 = (new StringBuilder()).append("Supplied key (");
         String var3 = this.getType(var1);
         String var4 = var2.append(var3).append(") is not a RSAPrivateKey instance").toString();
         throw new InvalidKeyException(var4);
      } else {
         RSAKeyParameters var5 = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var1);
         this.digest.reset();
         this.cipher.init((boolean)1, var5);
      }
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      if(!(var1 instanceof RSAPublicKey)) {
         StringBuilder var2 = (new StringBuilder()).append("Supplied key (");
         String var3 = this.getType(var1);
         String var4 = var2.append(var3).append(") is not a RSAPublicKey instance").toString();
         throw new InvalidKeyException(var4);
      } else {
         RSAKeyParameters var5 = RSAUtil.generatePublicKeyParameter((RSAPublicKey)var1);
         this.digest.reset();
         this.cipher.init((boolean)0, var5);
      }
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
         byte[] var3 = this.derEncode(var1);
         AsymmetricBlockCipher var4 = this.cipher;
         int var5 = var3.length;
         byte[] var6 = var4.processBlock(var3, 0, var5);
         return var6;
      } catch (ArrayIndexOutOfBoundsException var9) {
         throw new SignatureException("key too small for signature type");
      } catch (Exception var10) {
         String var8 = var10.toString();
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

      byte[] var6;
      byte[] var7;
      boolean var15;
      try {
         AsymmetricBlockCipher var4 = this.cipher;
         int var5 = var1.length;
         var6 = var4.processBlock(var1, 0, var5);
         var7 = this.derEncode(var2);
      } catch (Exception var34) {
         var15 = false;
         return var15;
      }

      byte[] var8 = var7;
      int var9 = var6.length;
      int var10 = var7.length;
      int var11;
      if(var9 == var10) {
         var11 = 0;

         while(true) {
            int var12 = var6.length;
            if(var11 >= var12) {
               break;
            }

            byte var13 = var6[var11];
            byte var14 = var8[var11];
            if(var13 != var14) {
               var15 = false;
               return var15;
            }

            ++var11;
         }
      } else {
         int var17 = var6.length;
         int var18 = var7.length - 2;
         if(var17 != var18) {
            var15 = false;
            return var15;
         }

         int var19 = var6.length;
         int var20 = var2.length;
         int var21 = var19 - var20 - 2;
         int var22 = var7.length;
         int var23 = var2.length;
         int var24 = var22 - var23 - 2;
         byte var25 = (byte)(var7[1] - 2);
         var7[1] = var25;
         byte var26 = (byte)(var7[3] - 2);
         var7[3] = var26;
         var11 = 0;

         while(true) {
            int var27 = var2.length;
            if(var11 >= var27) {
               for(var11 = 0; var11 < var21; ++var11) {
                  byte var32 = var6[var11];
                  byte var33 = var8[var11];
                  if(var32 != var33) {
                     var15 = false;
                     return var15;
                  }
               }
               break;
            }

            int var28 = var21 + var11;
            byte var29 = var6[var28];
            int var30 = var24 + var11;
            byte var31 = var8[var30];
            if(var29 != var31) {
               var15 = false;
               return var15;
            }

            ++var11;
         }
      }

      var15 = true;
      return var15;
   }

   public static class MD4WithRSAEncryption extends JDKDigestSignature {

      public MD4WithRSAEncryption() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.md4;
         MD4Digest var2 = new MD4Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class SHA1WithRSAEncryption extends JDKDigestSignature {

      public SHA1WithRSAEncryption() {
         DERObjectIdentifier var1 = X509ObjectIdentifiers.id_SHA1;
         SHA1Digest var2 = new SHA1Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class RIPEMD160WithRSAEncryption extends JDKDigestSignature {

      public RIPEMD160WithRSAEncryption() {
         DERObjectIdentifier var1 = TeleTrusTObjectIdentifiers.ripemd160;
         RIPEMD160Digest var2 = new RIPEMD160Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class SHA256WithRSAEncryption extends JDKDigestSignature {

      public SHA256WithRSAEncryption() {
         DERObjectIdentifier var1 = NISTObjectIdentifiers.id_sha256;
         SHA256Digest var2 = new SHA256Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class MD2WithRSAEncryption extends JDKDigestSignature {

      public MD2WithRSAEncryption() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.md2;
         MD2Digest var2 = new MD2Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class SHA224WithRSAEncryption extends JDKDigestSignature {

      public SHA224WithRSAEncryption() {
         DERObjectIdentifier var1 = NISTObjectIdentifiers.id_sha224;
         SHA224Digest var2 = new SHA224Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class SHA512WithRSAEncryption extends JDKDigestSignature {

      public SHA512WithRSAEncryption() {
         DERObjectIdentifier var1 = NISTObjectIdentifiers.id_sha512;
         SHA512Digest var2 = new SHA512Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class noneRSA extends JDKDigestSignature {

      public noneRSA() {
         NullDigest var1 = new NullDigest();
         RSABlindedEngine var2 = new RSABlindedEngine();
         PKCS1Encoding var3 = new PKCS1Encoding(var2);
         super(var1, var3);
      }
   }

   public static class MD5WithRSAEncryption extends JDKDigestSignature {

      public MD5WithRSAEncryption() {
         DERObjectIdentifier var1 = PKCSObjectIdentifiers.md5;
         MD5Digest var2 = new MD5Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class RIPEMD256WithRSAEncryption extends JDKDigestSignature {

      public RIPEMD256WithRSAEncryption() {
         DERObjectIdentifier var1 = TeleTrusTObjectIdentifiers.ripemd256;
         RIPEMD256Digest var2 = new RIPEMD256Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class SHA384WithRSAEncryption extends JDKDigestSignature {

      public SHA384WithRSAEncryption() {
         DERObjectIdentifier var1 = NISTObjectIdentifiers.id_sha384;
         SHA384Digest var2 = new SHA384Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }

   public static class RIPEMD128WithRSAEncryption extends JDKDigestSignature {

      public RIPEMD128WithRSAEncryption() {
         DERObjectIdentifier var1 = TeleTrusTObjectIdentifiers.ripemd128;
         RIPEMD128Digest var2 = new RIPEMD128Digest();
         RSABlindedEngine var3 = new RSABlindedEngine();
         PKCS1Encoding var4 = new PKCS1Encoding(var3);
         super(var1, var2, var4);
      }
   }
}
