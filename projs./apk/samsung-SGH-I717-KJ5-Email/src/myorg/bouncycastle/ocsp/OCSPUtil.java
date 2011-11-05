package myorg.bouncycastle.ocsp;

import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.util.Strings;

class OCSPUtil {

   private static Hashtable algorithms = new Hashtable();
   private static Set noParams = new HashSet();
   private static Hashtable oids = new Hashtable();


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
      DERObjectIdentifier var43 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
      var42.put("RIPEMD160WITHRSAENCRYPTION", var43);
      Hashtable var45 = algorithms;
      DERObjectIdentifier var46 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
      var45.put("RIPEMD160WITHRSA", var46);
      Hashtable var48 = algorithms;
      DERObjectIdentifier var49 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
      var48.put("RIPEMD128WITHRSAENCRYPTION", var49);
      Hashtable var51 = algorithms;
      DERObjectIdentifier var52 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
      var51.put("RIPEMD128WITHRSA", var52);
      Hashtable var54 = algorithms;
      DERObjectIdentifier var55 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
      var54.put("RIPEMD256WITHRSAENCRYPTION", var55);
      Hashtable var57 = algorithms;
      DERObjectIdentifier var58 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
      var57.put("RIPEMD256WITHRSA", var58);
      Hashtable var60 = algorithms;
      DERObjectIdentifier var61 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var60.put("SHA1WITHDSA", var61);
      Hashtable var63 = algorithms;
      DERObjectIdentifier var64 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var63.put("DSAWITHSHA1", var64);
      Hashtable var66 = algorithms;
      DERObjectIdentifier var67 = NISTObjectIdentifiers.dsa_with_sha224;
      var66.put("SHA224WITHDSA", var67);
      Hashtable var69 = algorithms;
      DERObjectIdentifier var70 = NISTObjectIdentifiers.dsa_with_sha256;
      var69.put("SHA256WITHDSA", var70);
      Hashtable var72 = algorithms;
      DERObjectIdentifier var73 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var72.put("SHA1WITHECDSA", var73);
      Hashtable var75 = algorithms;
      DERObjectIdentifier var76 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var75.put("ECDSAWITHSHA1", var76);
      Hashtable var78 = algorithms;
      DERObjectIdentifier var79 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var78.put("SHA224WITHECDSA", var79);
      Hashtable var81 = algorithms;
      DERObjectIdentifier var82 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var81.put("SHA256WITHECDSA", var82);
      Hashtable var84 = algorithms;
      DERObjectIdentifier var85 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var84.put("SHA384WITHECDSA", var85);
      Hashtable var87 = algorithms;
      DERObjectIdentifier var88 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var87.put("SHA512WITHECDSA", var88);
      Hashtable var90 = algorithms;
      DERObjectIdentifier var91 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var90.put("GOST3411WITHGOST3410", var91);
      Hashtable var93 = algorithms;
      DERObjectIdentifier var94 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var93.put("GOST3411WITHGOST3410-94", var94);
      Hashtable var96 = oids;
      DERObjectIdentifier var97 = PKCSObjectIdentifiers.md2WithRSAEncryption;
      var96.put(var97, "MD2WITHRSA");
      Hashtable var99 = oids;
      DERObjectIdentifier var100 = PKCSObjectIdentifiers.md5WithRSAEncryption;
      var99.put(var100, "MD5WITHRSA");
      Hashtable var102 = oids;
      DERObjectIdentifier var103 = PKCSObjectIdentifiers.sha1WithRSAEncryption;
      var102.put(var103, "SHA1WITHRSA");
      Hashtable var105 = oids;
      DERObjectIdentifier var106 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
      var105.put(var106, "SHA224WITHRSA");
      Hashtable var108 = oids;
      DERObjectIdentifier var109 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
      var108.put(var109, "SHA256WITHRSA");
      Hashtable var111 = oids;
      DERObjectIdentifier var112 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
      var111.put(var112, "SHA384WITHRSA");
      Hashtable var114 = oids;
      DERObjectIdentifier var115 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
      var114.put(var115, "SHA512WITHRSA");
      Hashtable var117 = oids;
      DERObjectIdentifier var118 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
      var117.put(var118, "RIPEMD160WITHRSA");
      Hashtable var120 = oids;
      DERObjectIdentifier var121 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
      var120.put(var121, "RIPEMD128WITHRSA");
      Hashtable var123 = oids;
      DERObjectIdentifier var124 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
      var123.put(var124, "RIPEMD256WITHRSA");
      Hashtable var126 = oids;
      DERObjectIdentifier var127 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var126.put(var127, "SHA1WITHDSA");
      Hashtable var129 = oids;
      DERObjectIdentifier var130 = NISTObjectIdentifiers.dsa_with_sha224;
      var129.put(var130, "SHA224WITHDSA");
      Hashtable var132 = oids;
      DERObjectIdentifier var133 = NISTObjectIdentifiers.dsa_with_sha256;
      var132.put(var133, "SHA256WITHDSA");
      Hashtable var135 = oids;
      DERObjectIdentifier var136 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var135.put(var136, "SHA1WITHECDSA");
      Hashtable var138 = oids;
      DERObjectIdentifier var139 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var138.put(var139, "SHA224WITHECDSA");
      Hashtable var141 = oids;
      DERObjectIdentifier var142 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var141.put(var142, "SHA256WITHECDSA");
      Hashtable var144 = oids;
      DERObjectIdentifier var145 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var144.put(var145, "SHA384WITHECDSA");
      Hashtable var147 = oids;
      DERObjectIdentifier var148 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var147.put(var148, "SHA512WITHECDSA");
      Hashtable var150 = oids;
      DERObjectIdentifier var151 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
      var150.put(var151, "GOST3411WITHGOST3410");
      Set var153 = noParams;
      DERObjectIdentifier var154 = X9ObjectIdentifiers.ecdsa_with_SHA1;
      var153.add(var154);
      Set var156 = noParams;
      DERObjectIdentifier var157 = X9ObjectIdentifiers.ecdsa_with_SHA224;
      var156.add(var157);
      Set var159 = noParams;
      DERObjectIdentifier var160 = X9ObjectIdentifiers.ecdsa_with_SHA256;
      var159.add(var160);
      Set var162 = noParams;
      DERObjectIdentifier var163 = X9ObjectIdentifiers.ecdsa_with_SHA384;
      var162.add(var163);
      Set var165 = noParams;
      DERObjectIdentifier var166 = X9ObjectIdentifiers.ecdsa_with_SHA512;
      var165.add(var166);
      Set var168 = noParams;
      DERObjectIdentifier var169 = X9ObjectIdentifiers.id_dsa_with_sha1;
      var168.add(var169);
      Set var171 = noParams;
      DERObjectIdentifier var172 = NISTObjectIdentifiers.dsa_with_sha224;
      var171.add(var172);
      Set var174 = noParams;
      DERObjectIdentifier var175 = NISTObjectIdentifiers.dsa_with_sha256;
      var174.add(var175);
   }

   OCSPUtil() {}

   static CertStore createCertStoreInstance(String var0, CertStoreParameters var1, String var2) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
      CertStore var3;
      if(var2 == null) {
         var3 = CertStore.getInstance(var0, var1);
      } else {
         var3 = CertStore.getInstance(var0, var1, var2);
      }

      return var3;
   }

   static MessageDigest createDigestInstance(String var0, String var1) throws NoSuchAlgorithmException, NoSuchProviderException {
      MessageDigest var2;
      if(var1 == null) {
         var2 = MessageDigest.getInstance(var0);
      } else {
         var2 = MessageDigest.getInstance(var0, var1);
      }

      return var2;
   }

   static Signature createSignatureInstance(String var0, String var1) throws NoSuchAlgorithmException, NoSuchProviderException {
      Signature var2;
      if(var1 == null) {
         var2 = Signature.getInstance(var0);
      } else {
         var2 = Signature.getInstance(var0, var1);
      }

      return var2;
   }

   static CertificateFactory createX509CertificateFactory(String var0) throws CertificateException, NoSuchProviderException {
      CertificateFactory var1;
      if(var0 == null) {
         var1 = CertificateFactory.getInstance("X.509");
      } else {
         var1 = CertificateFactory.getInstance("X.509", var0);
      }

      return var1;
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

   static String getAlgorithmName(DERObjectIdentifier var0) {
      String var1;
      if(oids.containsKey(var0)) {
         var1 = (String)oids.get(var0);
      } else {
         var1 = var0.getId();
      }

      return var1;
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

   static AlgorithmIdentifier getSigAlgID(DERObjectIdentifier var0) {
      AlgorithmIdentifier var1;
      if(noParams.contains(var0)) {
         var1 = new AlgorithmIdentifier(var0);
      } else {
         DERNull var2 = new DERNull();
         var1 = new AlgorithmIdentifier(var0, var2);
      }

      return var1;
   }
}
