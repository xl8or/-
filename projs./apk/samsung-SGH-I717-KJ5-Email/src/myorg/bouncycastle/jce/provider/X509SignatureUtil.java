package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PSSParameterSpec;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;

class X509SignatureUtil {

   private static final ASN1Null derNull = new DERNull();


   X509SignatureUtil() {}

   private static String getDigestAlgName(DERObjectIdentifier var0) {
      String var1;
      if(PKCSObjectIdentifiers.md5.equals(var0)) {
         var1 = "MD5";
      } else if(OIWObjectIdentifiers.idSHA1.equals(var0)) {
         var1 = "SHA1";
      } else if(NISTObjectIdentifiers.id_sha224.equals(var0)) {
         var1 = "SHA224";
      } else if(NISTObjectIdentifiers.id_sha256.equals(var0)) {
         var1 = "SHA256";
      } else if(NISTObjectIdentifiers.id_sha384.equals(var0)) {
         var1 = "SHA384";
      } else if(NISTObjectIdentifiers.id_sha512.equals(var0)) {
         var1 = "SHA512";
      } else if(TeleTrusTObjectIdentifiers.ripemd128.equals(var0)) {
         var1 = "RIPEMD128";
      } else if(TeleTrusTObjectIdentifiers.ripemd160.equals(var0)) {
         var1 = "RIPEMD160";
      } else if(TeleTrusTObjectIdentifiers.ripemd256.equals(var0)) {
         var1 = "RIPEMD256";
      } else if(CryptoProObjectIdentifiers.gostR3411.equals(var0)) {
         var1 = "GOST3411";
      } else {
         var1 = var0.getId();
      }

      return var1;
   }

   static String getSignatureName(AlgorithmIdentifier var0) {
      DEREncodable var1 = var0.getParameters();
      String var7;
      if(var1 != null && !derNull.equals(var1)) {
         DERObjectIdentifier var2 = var0.getObjectId();
         DERObjectIdentifier var3 = PKCSObjectIdentifiers.id_RSASSA_PSS;
         if(var2.equals(var3)) {
            RSASSAPSSparams var4 = RSASSAPSSparams.getInstance(var1);
            StringBuilder var5 = new StringBuilder();
            String var6 = getDigestAlgName(var4.getHashAlgorithm().getObjectId());
            var7 = var5.append(var6).append("withRSAandMGF1").toString();
            return var7;
         }

         DERObjectIdentifier var8 = var0.getObjectId();
         DERObjectIdentifier var9 = X9ObjectIdentifiers.ecdsa_with_SHA2;
         if(var8.equals(var9)) {
            ASN1Sequence var10 = ASN1Sequence.getInstance(var1);
            StringBuilder var11 = new StringBuilder();
            String var12 = getDigestAlgName((DERObjectIdentifier)var10.getObjectAt(0));
            var7 = var11.append(var12).append("withECDSA").toString();
            return var7;
         }
      }

      var7 = var0.getObjectId().getId();
      return var7;
   }

   static void setSignatureParameters(Signature var0, DEREncodable var1) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      if(var1 != null) {
         if(!derNull.equals(var1)) {
            String var2 = var0.getAlgorithm();
            Provider var3 = var0.getProvider();
            AlgorithmParameters var4 = AlgorithmParameters.getInstance(var2, var3);

            try {
               byte[] var5 = var1.getDERObject().getDEREncoded();
               var4.init(var5);
            } catch (IOException var16) {
               StringBuilder var8 = (new StringBuilder()).append("IOException decoding parameters: ");
               String var9 = var16.getMessage();
               String var10 = var8.append(var9).toString();
               throw new SignatureException(var10);
            }

            if(var0.getAlgorithm().endsWith("MGF1")) {
               try {
                  AlgorithmParameterSpec var6 = var4.getParameterSpec(PSSParameterSpec.class);
                  var0.setParameter(var6);
               } catch (GeneralSecurityException var15) {
                  StringBuilder var12 = (new StringBuilder()).append("Exception extracting parameters: ");
                  String var13 = var15.getMessage();
                  String var14 = var12.append(var13).toString();
                  throw new SignatureException(var14);
               }
            }
         }
      }
   }
}
