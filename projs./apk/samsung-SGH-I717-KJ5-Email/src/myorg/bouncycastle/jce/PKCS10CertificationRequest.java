package myorg.bouncycastle.jce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.CertificationRequest;
import myorg.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.util.Strings;

public class PKCS10CertificationRequest extends CertificationRequest {

   private static Hashtable algorithms = new Hashtable();
   private static Hashtable keyAlgorithms = new Hashtable();
   private static Set noParams = new HashSet();
   private static Hashtable oids = new Hashtable();
   private static Hashtable params = new Hashtable();


   static {
      Hashtable var0 = algorithms;
      DERObjectIdentifier var1 = new DERObjectIdentifier("1.2.840.113549.1.1.2");
      var0.put("MD2WITHRSAENCRYPTION", var1);
      Hashtable var3 = algorithms;
      DERObjectIdentifier var4 = new DERObjectIdentifier("1.2.840.113549.1.1.2");
      var3.put("MD2WITHRSA", var4);
      Hashtable var6 = algorithms;
      DERObjectIdentifier var7 = new DERObjectIdentifier("1.2.840.113549.1.1.4");
      var6.put("MD5WITHRSAENCRYPTION", var7);
      Hashtable var9 = algorithms;
      DERObjectIdentifier var10 = new DERObjectIdentifier("1.2.840.113549.1.1.4");
      var9.put("MD5WITHRSA", var10);
      Hashtable var12 = algorithms;
      DERObjectIdentifier var13 = new DERObjectIdentifier("1.2.840.113549.1.1.4");
      var12.put("RSAWITHMD5", var13);
      Hashtable var15 = algorithms;
      DERObjectIdentifier var16 = new DERObjectIdentifier("1.2.840.113549.1.1.5");
      var15.put("SHA1WITHRSAENCRYPTION", var16);
      Hashtable var18 = algorithms;
      DERObjectIdentifier var19 = new DERObjectIdentifier("1.2.840.113549.1.1.5");
      var18.put("SHA1WITHRSA", var19);
      Hashtable var21 = algorithms;
      DERObjectIdentifier var22 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
      var21.put("SHA224WITHRSAENCRYPTION", var22);
      Hashtable var24 = algorithms;
      DERObjectIdentifier var25 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
      var24.put("SHA224WITHRSA", var25);
      Hashtable var27 = algorithms;
      DERObjectIdentifier var28 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
      var27.put("SHA256WITHRSAENCRYPTION", var28);
      Hashtable var30 = algorithms;
      DERObjectIdentifier var31 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
      var30.put("SHA256WITHRSA", var31);
      Hashtable var33 = algorithms;
      DERObjectIdentifier var34 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
      var33.put("SHA384WITHRSAENCRYPTION", var34);
      Hashtable var36 = algorithms;
      DERObjectIdentifier var37 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
      var36.put("SHA384WITHRSA", var37);
      Hashtable var39 = algorithms;
      DERObjectIdentifier var40 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
      var39.put("SHA512WITHRSAENCRYPTION", var40);
      Hashtable var42 = algorithms;
      DERObjectIdentifier var43 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
      var42.put("SHA512WITHRSA", var43);
      Hashtable var45 = algorithms;
      DERObjectIdentifier var46 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var45.put("SHA1WITHRSAANDMGF1", var46);
      Hashtable var48 = algorithms;
      DERObjectIdentifier var49 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var48.put("SHA224WITHRSAANDMGF1", var49);
      Hashtable var51 = algorithms;
      DERObjectIdentifier var52 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var51.put("SHA256WITHRSAANDMGF1", var52);
      Hashtable var54 = algorithms;
      DERObjectIdentifier var55 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var54.put("SHA384WITHRSAANDMGF1", var55);
      Hashtable var57 = algorithms;
      DERObjectIdentifier var58 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var57.put("SHA512WITHRSAANDMGF1", var58);
      Hashtable var60 = algorithms;
      DERObjectIdentifier var61 = new DERObjectIdentifier("1.2.840.113549.1.1.5");
      var60.put("RSAWITHSHA1", var61);
      Hashtable var63 = algorithms;
      DERObjectIdentifier var64 = new DERObjectIdentifier("1.3.36.3.3.1.2");
      var63.put("RIPEMD160WITHRSAENCRYPTION", var64);
      Hashtable var66 = algorithms;
      DERObjectIdentifier var67 = new DERObjectIdentifier("1.3.36.3.3.1.2");
      var66.put("RIPEMD160WITHRSA", var67);
      Hashtable var69 = algorithms;
      DERObjectIdentifier var70 = new DERObjectIdentifier("1.2.840.10040.4.3");
      var69.put("SHA1WITHDSA", var70);
      Hashtable var72 = algorithms;
      DERObjectIdentifier var73 = new DERObjectIdentifier("1.2.840.10040.4.3");
      var72.put("DSAWITHSHA1", var73);
      Hashtable var75 = algorithms;
      DERObjectIdentifier var76 = NISTObjectIdentifiers.dsa_with_sha224;
      var75.put("SHA224WITHDSA", var76);
      Hashtable var78 = algorithms;
      DERObjectIdentifier var79 = NISTObjectIdentifiers.dsa_with_sha256;
      var78.put("SHA256WITHDSA", var79);
      Hashtable var81 = algorithms;
      DERObjectIdentifier var82 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var81.put("SHA1WITHECDSA", var82);
      Hashtable var84 = algorithms;
      DERObjectIdentifier var85 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var84.put("SHA224WITHECDSA", var85);
      Hashtable var87 = algorithms;
      DERObjectIdentifier var88 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var87.put("SHA256WITHECDSA", var88);
      Hashtable var90 = algorithms;
      DERObjectIdentifier var91 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var90.put("SHA384WITHECDSA", var91);
      Hashtable var93 = algorithms;
      DERObjectIdentifier var94 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var93.put("SHA512WITHECDSA", var94);
      Hashtable var96 = algorithms;
      DERObjectIdentifier var97 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var96.put("ECDSAWITHSHA1", var97);
      Hashtable var99 = algorithms;
      DERObjectIdentifier var100 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var99.put("GOST3411WITHGOST3410", var100);
      Hashtable var102 = algorithms;
      DERObjectIdentifier var103 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var102.put("GOST3410WITHGOST3411", var103);
      Hashtable var105 = algorithms;
      DERObjectIdentifier var106 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var105.put("GOST3411WITHECGOST3410", var106);
      Hashtable var108 = algorithms;
      DERObjectIdentifier var109 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var108.put("GOST3411WITHECGOST3410-2001", var109);
      Hashtable var111 = algorithms;
      DERObjectIdentifier var112 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var111.put("GOST3411WITHGOST3410-2001", var112);
      Hashtable var114 = oids;
      DERObjectIdentifier var115 = new DERObjectIdentifier("1.2.840.113549.1.1.5");
      var114.put(var115, "SHA1WITHRSA");
      Hashtable var117 = oids;
      DERObjectIdentifier var118 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
      var117.put(var118, "SHA224WITHRSA");
      Hashtable var120 = oids;
      DERObjectIdentifier var121 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
      var120.put(var121, "SHA256WITHRSA");
      Hashtable var123 = oids;
      DERObjectIdentifier var124 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
      var123.put(var124, "SHA384WITHRSA");
      Hashtable var126 = oids;
      DERObjectIdentifier var127 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
      var126.put(var127, "SHA512WITHRSA");
      Hashtable var129 = oids;
      DERObjectIdentifier var130 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var129.put(var130, "GOST3411WITHGOST3410");
      Hashtable var132 = oids;
      DERObjectIdentifier var133 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var132.put(var133, "GOST3411WITHECGOST3410");
      Hashtable var135 = oids;
      DERObjectIdentifier var136 = new DERObjectIdentifier("1.2.840.113549.1.1.4");
      var135.put(var136, "MD5WITHRSA");
      Hashtable var138 = oids;
      DERObjectIdentifier var139 = new DERObjectIdentifier("1.2.840.113549.1.1.2");
      var138.put(var139, "MD2WITHRSA");
      Hashtable var141 = oids;
      DERObjectIdentifier var142 = new DERObjectIdentifier("1.2.840.10040.4.3");
      var141.put(var142, "SHA1WITHDSA");
      Hashtable var144 = oids;
      DERObjectIdentifier var145 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var144.put(var145, "SHA1WITHECDSA");
      Hashtable var147 = oids;
      DERObjectIdentifier var148 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var147.put(var148, "SHA224WITHECDSA");
      Hashtable var150 = oids;
      DERObjectIdentifier var151 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var150.put(var151, "SHA256WITHECDSA");
      Hashtable var153 = oids;
      DERObjectIdentifier var154 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var153.put(var154, "SHA384WITHECDSA");
      Hashtable var156 = oids;
      DERObjectIdentifier var157 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var156.put(var157, "SHA512WITHECDSA");
      Hashtable var159 = oids;
      DERObjectIdentifier var160 = OIWObjectIdentifiers.sha1WithRSA;
      var159.put(var160, "SHA1WITHRSA");
      Hashtable var162 = oids;
      DERObjectIdentifier var163 = OIWObjectIdentifiers.dsaWithSHA1;
      var162.put(var163, "SHA1WITHDSA");
      Hashtable var165 = oids;
      DERObjectIdentifier var166 = NISTObjectIdentifiers.dsa_with_sha224;
      var165.put(var166, "SHA224WITHDSA");
      Hashtable var168 = oids;
      DERObjectIdentifier var169 = NISTObjectIdentifiers.dsa_with_sha256;
      var168.put(var169, "SHA256WITHDSA");
      Hashtable var171 = keyAlgorithms;
      DERObjectIdentifier var172 = PKCSObjectIdentifiers.rsaEncryption;
      var171.put(var172, "RSA");
      Hashtable var174 = keyAlgorithms;
      DERObjectIdentifier var175 = X9ObjectIdentifiers.id_dsa;
      var174.put(var175, "DSA");
      Set var177 = noParams;
      DERObjectIdentifier var178 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var177.add(var178);
      Set var180 = noParams;
      DERObjectIdentifier var181 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var180.add(var181);
      Set var183 = noParams;
      DERObjectIdentifier var184 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var183.add(var184);
      Set var186 = noParams;
      DERObjectIdentifier var187 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var186.add(var187);
      Set var189 = noParams;
      DERObjectIdentifier var190 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var189.add(var190);
      Set var192 = noParams;
      DERObjectIdentifier var193 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var192.add(var193);
      Set var195 = noParams;
      DERObjectIdentifier var196 = NISTObjectIdentifiers.dsa_with_sha224;
      var195.add(var196);
      Set var198 = noParams;
      DERObjectIdentifier var199 = NISTObjectIdentifiers.dsa_with_sha256;
      var198.add(var199);
      Set var201 = noParams;
      DERObjectIdentifier var202 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var201.add(var202);
      Set var204 = noParams;
      DERObjectIdentifier var205 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var204.add(var205);
      DERObjectIdentifier var207 = OIWObjectIdentifiers.idSHA1;
      DERNull var208 = new DERNull();
      AlgorithmIdentifier var209 = new AlgorithmIdentifier(var207, var208);
      Hashtable var210 = params;
      RSASSAPSSparams var211 = creatPSSParams(var209, 20);
      var210.put("SHA1WITHRSAANDMGF1", var211);
      DERObjectIdentifier var213 = NISTObjectIdentifiers.id_sha224;
      DERNull var214 = new DERNull();
      AlgorithmIdentifier var215 = new AlgorithmIdentifier(var213, var214);
      Hashtable var216 = params;
      RSASSAPSSparams var217 = creatPSSParams(var215, 28);
      var216.put("SHA224WITHRSAANDMGF1", var217);
      DERObjectIdentifier var219 = NISTObjectIdentifiers.id_sha256;
      DERNull var220 = new DERNull();
      AlgorithmIdentifier var221 = new AlgorithmIdentifier(var219, var220);
      Hashtable var222 = params;
      RSASSAPSSparams var223 = creatPSSParams(var221, 32);
      var222.put("SHA256WITHRSAANDMGF1", var223);
      DERObjectIdentifier var225 = NISTObjectIdentifiers.id_sha384;
      DERNull var226 = new DERNull();
      AlgorithmIdentifier var227 = new AlgorithmIdentifier(var225, var226);
      Hashtable var228 = params;
      RSASSAPSSparams var229 = creatPSSParams(var227, 48);
      var228.put("SHA384WITHRSAANDMGF1", var229);
      DERObjectIdentifier var231 = NISTObjectIdentifiers.id_sha512;
      DERNull var232 = new DERNull();
      AlgorithmIdentifier var233 = new AlgorithmIdentifier(var231, var232);
      Hashtable var234 = params;
      RSASSAPSSparams var235 = creatPSSParams(var233, 64);
      var234.put("SHA512WITHRSAANDMGF1", var235);
   }

   public PKCS10CertificationRequest(String var1, X500Principal var2, PublicKey var3, ASN1Set var4, PrivateKey var5) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      X509Name var6 = convertName(var2);
      this(var1, var6, var3, var4, var5, "myBC");
   }

   public PKCS10CertificationRequest(String var1, X500Principal var2, PublicKey var3, ASN1Set var4, PrivateKey var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      X509Name var7 = convertName(var2);
      this(var1, var7, var3, var4, var5, var6);
   }

   public PKCS10CertificationRequest(String var1, X509Name var2, PublicKey var3, ASN1Set var4, PrivateKey var5) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      this(var1, var2, var3, var4, var5, "myBC");
   }

   public PKCS10CertificationRequest(String var1, X509Name var2, PublicKey var3, ASN1Set var4, PrivateKey var5, String var6) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      String var7 = Strings.toUpperCase(var1);
      DERObjectIdentifier var8 = (DERObjectIdentifier)algorithms.get(var7);
      if(var8 == null) {
         throw new IllegalArgumentException("Unknown signature type requested");
      } else if(var2 == null) {
         throw new IllegalArgumentException("subject must not be null");
      } else if(var3 == null) {
         throw new IllegalArgumentException("public key must not be null");
      } else {
         if(noParams.contains(var8)) {
            AlgorithmIdentifier var9 = new AlgorithmIdentifier(var8);
            this.sigAlgId = var9;
         } else if(params.containsKey(var7)) {
            DEREncodable var17 = (DEREncodable)params.get(var7);
            AlgorithmIdentifier var18 = new AlgorithmIdentifier(var8, var17);
            this.sigAlgId = var18;
         } else {
            AlgorithmIdentifier var19 = new AlgorithmIdentifier(var8, (DEREncodable)null);
            this.sigAlgId = var19;
         }

         try {
            ASN1Sequence var10 = (ASN1Sequence)ASN1Object.fromByteArray(var3.getEncoded());
            SubjectPublicKeyInfo var11 = new SubjectPublicKeyInfo(var10);
            CertificationRequestInfo var12 = new CertificationRequestInfo(var2, var11, var4);
            this.reqInfo = var12;
         } catch (IOException var24) {
            throw new IllegalArgumentException("can\'t encode public key");
         }

         Signature var13;
         if(var6 == null) {
            var13 = Signature.getInstance(var1);
         } else {
            var13 = Signature.getInstance(var1, var6);
         }

         var13.initSign(var5);

         try {
            byte[] var14 = this.reqInfo.getEncoded("DER");
            var13.update(var14);
         } catch (Exception var23) {
            String var22 = "exception encoding TBS cert request - " + var23;
            throw new IllegalArgumentException(var22);
         }

         byte[] var15 = var13.sign();
         DERBitString var16 = new DERBitString(var15);
         this.sigBits = var16;
      }
   }

   public PKCS10CertificationRequest(ASN1Sequence var1) {
      super(var1);
   }

   public PKCS10CertificationRequest(byte[] var1) {
      ASN1Sequence var2 = toDERSequence(var1);
      super(var2);
   }

   private static X509Name convertName(X500Principal var0) {
      try {
         byte[] var1 = var0.getEncoded();
         X509Principal var2 = new X509Principal(var1);
         return var2;
      } catch (IOException var4) {
         throw new IllegalArgumentException("can\'t convert name");
      }
   }

   private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier var0, int var1) {
      DERObjectIdentifier var2 = PKCSObjectIdentifiers.id_mgf1;
      AlgorithmIdentifier var3 = new AlgorithmIdentifier(var2, var0);
      DERInteger var4 = new DERInteger(var1);
      DERInteger var5 = new DERInteger(1);
      return new RSASSAPSSparams(var0, var3, var4, var5);
   }

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
      if(var1 != null && !DERNull.INSTANCE.equals(var1)) {
         DERObjectIdentifier var2 = var0.getObjectId();
         DERObjectIdentifier var3 = PKCSObjectIdentifiers.id_RSASSA_PSS;
         if(var2.equals(var3)) {
            RSASSAPSSparams var4 = RSASSAPSSparams.getInstance(var1);
            StringBuilder var5 = new StringBuilder();
            String var6 = getDigestAlgName(var4.getHashAlgorithm().getObjectId());
            var7 = var5.append(var6).append("withRSAandMGF1").toString();
            return var7;
         }
      }

      var7 = var0.getObjectId().getId();
      return var7;
   }

   private void setSignatureParameters(Signature var1, DEREncodable var2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      if(var2 != null) {
         if(!DERNull.INSTANCE.equals(var2)) {
            String var3 = var1.getAlgorithm();
            Provider var4 = var1.getProvider();
            AlgorithmParameters var5 = AlgorithmParameters.getInstance(var3, var4);

            try {
               byte[] var6 = var2.getDERObject().getDEREncoded();
               var5.init(var6);
            } catch (IOException var17) {
               StringBuilder var9 = (new StringBuilder()).append("IOException decoding parameters: ");
               String var10 = var17.getMessage();
               String var11 = var9.append(var10).toString();
               throw new SignatureException(var11);
            }

            if(var1.getAlgorithm().endsWith("MGF1")) {
               try {
                  AlgorithmParameterSpec var7 = var5.getParameterSpec(PSSParameterSpec.class);
                  var1.setParameter(var7);
               } catch (GeneralSecurityException var16) {
                  StringBuilder var13 = (new StringBuilder()).append("Exception extracting parameters: ");
                  String var14 = var16.getMessage();
                  String var15 = var13.append(var14).toString();
                  throw new SignatureException(var15);
               }
            }
         }
      }
   }

   private static ASN1Sequence toDERSequence(byte[] var0) {
      try {
         ASN1Sequence var3 = (ASN1Sequence)(new ASN1InputStream(var0)).readObject();
         return var3;
      } catch (Exception var2) {
         throw new IllegalArgumentException("badly encoded request");
      }
   }

   public byte[] getEncoded() {
      try {
         byte[] var1 = this.getEncoded("DER");
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new RuntimeException(var2);
      }
   }

   public PublicKey getPublicKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      return this.getPublicKey("myBC");
   }

   public PublicKey getPublicKey(String param1) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      // $FF: Couldn't be decompiled
   }

   public boolean verify() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      return this.verify("myBC");
   }

   public boolean verify(String var1) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      PublicKey var2 = this.getPublicKey(var1);
      return this.verify(var2, var1);
   }

   public boolean verify(PublicKey param1, String param2) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
      // $FF: Couldn't be decompiled
   }
}
