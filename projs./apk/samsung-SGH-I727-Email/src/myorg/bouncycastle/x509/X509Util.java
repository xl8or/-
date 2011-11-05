package myorg.bouncycastle.x509;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
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
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.util.Strings;

class X509Util {

   private static Hashtable algorithms = new Hashtable();
   private static Set noParams = new HashSet();
   private static Hashtable params = new Hashtable();


   static {
      Hashtable var0 = algorithms;
      DERObjectIdentifier var1 = PKCSObjectIdentifiers.md2WithRSAEncryption;
      var0.put("MD2WITHRSAENCRYPTION", var1);
      Hashtable var3 = algorithms;
      DERObjectIdentifier var4 = PKCSObjectIdentifiers.md2WithRSAEncryption;
      var3.put("MD2WITHRSA", var4);
      Hashtable var6 = algorithms;
      DERObjectIdentifier var7 = PKCSObjectIdentifiers.md5WithRSAEncryption;
      var6.put("MD5WITHRSAENCRYPTION", var7);
      Hashtable var9 = algorithms;
      DERObjectIdentifier var10 = PKCSObjectIdentifiers.md5WithRSAEncryption;
      var9.put("MD5WITHRSA", var10);
      Hashtable var12 = algorithms;
      DERObjectIdentifier var13 = PKCSObjectIdentifiers.sha1WithRSAEncryption;
      var12.put("SHA1WITHRSAENCRYPTION", var13);
      Hashtable var15 = algorithms;
      DERObjectIdentifier var16 = PKCSObjectIdentifiers.sha1WithRSAEncryption;
      var15.put("SHA1WITHRSA", var16);
      Hashtable var18 = algorithms;
      DERObjectIdentifier var19 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
      var18.put("SHA224WITHRSAENCRYPTION", var19);
      Hashtable var21 = algorithms;
      DERObjectIdentifier var22 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
      var21.put("SHA224WITHRSA", var22);
      Hashtable var24 = algorithms;
      DERObjectIdentifier var25 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
      var24.put("SHA256WITHRSAENCRYPTION", var25);
      Hashtable var27 = algorithms;
      DERObjectIdentifier var28 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
      var27.put("SHA256WITHRSA", var28);
      Hashtable var30 = algorithms;
      DERObjectIdentifier var31 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
      var30.put("SHA384WITHRSAENCRYPTION", var31);
      Hashtable var33 = algorithms;
      DERObjectIdentifier var34 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
      var33.put("SHA384WITHRSA", var34);
      Hashtable var36 = algorithms;
      DERObjectIdentifier var37 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
      var36.put("SHA512WITHRSAENCRYPTION", var37);
      Hashtable var39 = algorithms;
      DERObjectIdentifier var40 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
      var39.put("SHA512WITHRSA", var40);
      Hashtable var42 = algorithms;
      DERObjectIdentifier var43 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var42.put("SHA1WITHRSAANDMGF1", var43);
      Hashtable var45 = algorithms;
      DERObjectIdentifier var46 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var45.put("SHA224WITHRSAANDMGF1", var46);
      Hashtable var48 = algorithms;
      DERObjectIdentifier var49 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var48.put("SHA256WITHRSAANDMGF1", var49);
      Hashtable var51 = algorithms;
      DERObjectIdentifier var52 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var51.put("SHA384WITHRSAANDMGF1", var52);
      Hashtable var54 = algorithms;
      DERObjectIdentifier var55 = PKCSObjectIdentifiers.id_RSASSA_PSS;
      var54.put("SHA512WITHRSAANDMGF1", var55);
      Hashtable var57 = algorithms;
      DERObjectIdentifier var58 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
      var57.put("RIPEMD160WITHRSAENCRYPTION", var58);
      Hashtable var60 = algorithms;
      DERObjectIdentifier var61 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
      var60.put("RIPEMD160WITHRSA", var61);
      Hashtable var63 = algorithms;
      DERObjectIdentifier var64 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
      var63.put("RIPEMD128WITHRSAENCRYPTION", var64);
      Hashtable var66 = algorithms;
      DERObjectIdentifier var67 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
      var66.put("RIPEMD128WITHRSA", var67);
      Hashtable var69 = algorithms;
      DERObjectIdentifier var70 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
      var69.put("RIPEMD256WITHRSAENCRYPTION", var70);
      Hashtable var72 = algorithms;
      DERObjectIdentifier var73 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
      var72.put("RIPEMD256WITHRSA", var73);
      Hashtable var75 = algorithms;
      DERObjectIdentifier var76 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var75.put("SHA1WITHDSA", var76);
      Hashtable var78 = algorithms;
      DERObjectIdentifier var79 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var78.put("DSAWITHSHA1", var79);
      Hashtable var81 = algorithms;
      DERObjectIdentifier var82 = NISTObjectIdentifiers.dsa_with_sha224;
      var81.put("SHA224WITHDSA", var82);
      Hashtable var84 = algorithms;
      DERObjectIdentifier var85 = NISTObjectIdentifiers.dsa_with_sha256;
      var84.put("SHA256WITHDSA", var85);
      Hashtable var87 = algorithms;
      DERObjectIdentifier var88 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var87.put("SHA1WITHECDSA", var88);
      Hashtable var90 = algorithms;
      DERObjectIdentifier var91 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var90.put("ECDSAWITHSHA1", var91);
      Hashtable var93 = algorithms;
      DERObjectIdentifier var94 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var93.put("SHA224WITHECDSA", var94);
      Hashtable var96 = algorithms;
      DERObjectIdentifier var97 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var96.put("SHA256WITHECDSA", var97);
      Hashtable var99 = algorithms;
      DERObjectIdentifier var100 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var99.put("SHA384WITHECDSA", var100);
      Hashtable var102 = algorithms;
      DERObjectIdentifier var103 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var102.put("SHA512WITHECDSA", var103);
      Hashtable var105 = algorithms;
      DERObjectIdentifier var106 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var105.put("GOST3411WITHGOST3410", var106);
      Hashtable var108 = algorithms;
      DERObjectIdentifier var109 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var108.put("GOST3411WITHGOST3410-94", var109);
      Hashtable var111 = algorithms;
      DERObjectIdentifier var112 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var111.put("GOST3411WITHECGOST3410", var112);
      Hashtable var114 = algorithms;
      DERObjectIdentifier var115 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var114.put("GOST3411WITHECGOST3410-2001", var115);
      Hashtable var117 = algorithms;
      DERObjectIdentifier var118 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var117.put("GOST3411WITHGOST3410-2001", var118);
      Set var120 = noParams;
      DERObjectIdentifier var121 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var120.add(var121);
      Set var123 = noParams;
      DERObjectIdentifier var124 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var123.add(var124);
      Set var126 = noParams;
      DERObjectIdentifier var127 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var126.add(var127);
      Set var129 = noParams;
      DERObjectIdentifier var130 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var129.add(var130);
      Set var132 = noParams;
      DERObjectIdentifier var133 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var132.add(var133);
      Set var135 = noParams;
      DERObjectIdentifier var136 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var135.add(var136);
      Set var138 = noParams;
      DERObjectIdentifier var139 = NISTObjectIdentifiers.dsa_with_sha224;
      var138.add(var139);
      Set var141 = noParams;
      DERObjectIdentifier var142 = NISTObjectIdentifiers.dsa_with_sha256;
      var141.add(var142);
      Set var144 = noParams;
      DERObjectIdentifier var145 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var144.add(var145);
      Set var147 = noParams;
      DERObjectIdentifier var148 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
      var147.add(var148);
      DERObjectIdentifier var150 = OIWObjectIdentifiers.idSHA1;
      DERNull var151 = new DERNull();
      AlgorithmIdentifier var152 = new AlgorithmIdentifier(var150, var151);
      Hashtable var153 = params;
      RSASSAPSSparams var154 = creatPSSParams(var152, 20);
      var153.put("SHA1WITHRSAANDMGF1", var154);
      DERObjectIdentifier var156 = NISTObjectIdentifiers.id_sha224;
      DERNull var157 = new DERNull();
      AlgorithmIdentifier var158 = new AlgorithmIdentifier(var156, var157);
      Hashtable var159 = params;
      RSASSAPSSparams var160 = creatPSSParams(var158, 28);
      var159.put("SHA224WITHRSAANDMGF1", var160);
      DERObjectIdentifier var162 = NISTObjectIdentifiers.id_sha256;
      DERNull var163 = new DERNull();
      AlgorithmIdentifier var164 = new AlgorithmIdentifier(var162, var163);
      Hashtable var165 = params;
      RSASSAPSSparams var166 = creatPSSParams(var164, 32);
      var165.put("SHA256WITHRSAANDMGF1", var166);
      DERObjectIdentifier var168 = NISTObjectIdentifiers.id_sha384;
      DERNull var169 = new DERNull();
      AlgorithmIdentifier var170 = new AlgorithmIdentifier(var168, var169);
      Hashtable var171 = params;
      RSASSAPSSparams var172 = creatPSSParams(var170, 48);
      var171.put("SHA384WITHRSAANDMGF1", var172);
      DERObjectIdentifier var174 = NISTObjectIdentifiers.id_sha512;
      DERNull var175 = new DERNull();
      AlgorithmIdentifier var176 = new AlgorithmIdentifier(var174, var175);
      Hashtable var177 = params;
      RSASSAPSSparams var178 = creatPSSParams(var176, 64);
      var177.put("SHA512WITHRSAANDMGF1", var178);
   }

   X509Util() {}

   static byte[] calculateSignature(DERObjectIdentifier var0, String var1, String var2, PrivateKey var3, SecureRandom var4, ASN1Encodable var5) throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
      if(var0 == null) {
         throw new IllegalStateException("no signature algorithm specified");
      } else {
         Signature var6 = getSignatureInstance(var1, var2);
         if(var4 != null) {
            var6.initSign(var3, var4);
         } else {
            var6.initSign(var3);
         }

         byte[] var7 = var5.getEncoded("DER");
         var6.update(var7);
         return var6.sign();
      }
   }

   static byte[] calculateSignature(DERObjectIdentifier var0, String var1, PrivateKey var2, SecureRandom var3, ASN1Encodable var4) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
      if(var0 == null) {
         throw new IllegalStateException("no signature algorithm specified");
      } else {
         Signature var5 = getSignatureInstance(var1);
         if(var3 != null) {
            var5.initSign(var2, var3);
         } else {
            var5.initSign(var2);
         }

         byte[] var6 = var4.getEncoded("DER");
         var5.update(var6);
         return var5.sign();
      }
   }

   static X509Principal convertPrincipal(X500Principal var0) {
      try {
         byte[] var1 = var0.getEncoded();
         X509Principal var2 = new X509Principal(var1);
         return var2;
      } catch (IOException var4) {
         throw new IllegalArgumentException("cannot convert principal");
      }
   }

   private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier var0, int var1) {
      DERObjectIdentifier var2 = PKCSObjectIdentifiers.id_mgf1;
      AlgorithmIdentifier var3 = new AlgorithmIdentifier(var2, var0);
      DERInteger var4 = new DERInteger(var1);
      DERInteger var5 = new DERInteger(1);
      return new RSASSAPSSparams(var0, var3, var4, var5);
   }

   static Iterator getAlgNames() {
      Enumeration var0 = algorithms.keys();
      ArrayList var1 = new ArrayList();

      while(var0.hasMoreElements()) {
         Object var2 = var0.nextElement();
         var1.add(var2);
      }

      return var1.iterator();
   }

   static DERObjectIdentifier getAlgorithmOID(String var0) {
      String var1 = Strings.toUpperCase(var0);
      DERObjectIdentifier var2;
      if(algorithms.containsKey(var1)) {
         var2 = (DERObjectIdentifier)algorithms.get(var1);
      } else {
         var2 = new DERObjectIdentifier(var1);
      }

      return var2;
   }

   static X509Util.Implementation getImplementation(String var0, String var1) throws NoSuchAlgorithmException {
      Provider[] var2 = Security.getProviders();
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            String var11 = "cannot find implementation " + var1;
            throw new NoSuchAlgorithmException(var11);
         }

         String var5 = Strings.toUpperCase(var1);
         Provider var6 = var2[var3];
         X509Util.Implementation var7 = getImplementation(var0, var5, var6);
         if(var7 != null) {
            return var7;
         }

         try {
            Provider var8 = var2[var3];
            X509Util.Implementation var9 = getImplementation(var0, var1, var8);
         } catch (NoSuchAlgorithmException var13) {
            ;
         }

         ++var3;
      }
   }

   static X509Util.Implementation getImplementation(String param0, String param1, Provider param2) throws NoSuchAlgorithmException {
      // $FF: Couldn't be decompiled
   }

   static Provider getProvider(String var0) throws NoSuchProviderException {
      Provider var1 = Security.getProvider(var0);
      if(var1 == null) {
         String var2 = "Provider " + var0 + " not found";
         throw new NoSuchProviderException(var2);
      } else {
         return var1;
      }
   }

   static AlgorithmIdentifier getSigAlgID(DERObjectIdentifier var0, String var1) {
      AlgorithmIdentifier var2;
      if(noParams.contains(var0)) {
         var2 = new AlgorithmIdentifier(var0);
      } else {
         var1 = Strings.toUpperCase(var1);
         if(params.containsKey(var1)) {
            DEREncodable var3 = (DEREncodable)params.get(var1);
            var2 = new AlgorithmIdentifier(var0, var3);
         } else {
            DERNull var4 = new DERNull();
            var2 = new AlgorithmIdentifier(var0, var4);
         }
      }

      return var2;
   }

   static Signature getSignatureInstance(String var0) throws NoSuchAlgorithmException {
      return Signature.getInstance(var0);
   }

   static Signature getSignatureInstance(String var0, String var1) throws NoSuchProviderException, NoSuchAlgorithmException {
      Signature var2;
      if(var1 != null) {
         var2 = Signature.getInstance(var0, var1);
      } else {
         var2 = Signature.getInstance(var0);
      }

      return var2;
   }

   static class Implementation {

      Object engine;
      Provider provider;


      Implementation(Object var1, Provider var2) {
         this.engine = var1;
         this.provider = var2;
      }

      Object getEngine() {
         return this.engine;
      }

      Provider getProvider() {
         return this.provider;
      }
   }
}
