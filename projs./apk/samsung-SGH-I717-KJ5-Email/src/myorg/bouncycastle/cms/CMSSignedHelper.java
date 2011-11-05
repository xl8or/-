package myorg.bouncycastle.cms;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.CertStore;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.eac.EACObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSSignedDataGenerator;
import myorg.bouncycastle.x509.NoSuchStoreException;
import myorg.bouncycastle.x509.X509CollectionStoreParameters;
import myorg.bouncycastle.x509.X509Store;
import myorg.bouncycastle.x509.X509V2AttributeCertificate;

class CMSSignedHelper {

   static final CMSSignedHelper INSTANCE = new CMSSignedHelper();
   private static final Map digestAlgs = new HashMap();
   private static final Map digestAliases = new HashMap();
   private static final Map encryptionAlgs = new HashMap();


   static {
      addEntries(NISTObjectIdentifiers.dsa_with_sha224, "SHA224", "DSA");
      addEntries(NISTObjectIdentifiers.dsa_with_sha256, "SHA256", "DSA");
      addEntries(NISTObjectIdentifiers.dsa_with_sha384, "SHA384", "DSA");
      addEntries(NISTObjectIdentifiers.dsa_with_sha512, "SHA512", "DSA");
      addEntries(OIWObjectIdentifiers.dsaWithSHA1, "SHA1", "DSA");
      addEntries(OIWObjectIdentifiers.md4WithRSA, "MD4", "RSA");
      addEntries(OIWObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
      addEntries(OIWObjectIdentifiers.md5WithRSA, "MD5", "RSA");
      addEntries(OIWObjectIdentifiers.sha1WithRSA, "SHA1", "RSA");
      addEntries(PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2", "RSA");
      addEntries(PKCSObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
      addEntries(PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5", "RSA");
      addEntries(PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1", "RSA");
      addEntries(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224", "RSA");
      addEntries(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256", "RSA");
      addEntries(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384", "RSA");
      addEntries(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512", "RSA");
      addEntries(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1", "ECDSA");
      addEntries(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224", "ECDSA");
      addEntries(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256", "ECDSA");
      addEntries(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384", "ECDSA");
      addEntries(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512", "ECDSA");
      addEntries(X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1", "DSA");
      addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1", "ECDSA");
      addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224", "ECDSA");
      addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256", "ECDSA");
      addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384", "ECDSA");
      addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512", "ECDSA");
      addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, "SHA1", "RSA");
      addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, "SHA256", "RSA");
      addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "SHA1", "RSAandMGF1");
      addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "SHA256", "RSAandMGF1");
      Map var0 = encryptionAlgs;
      String var1 = X9ObjectIdentifiers.id_dsa.getId();
      var0.put(var1, "DSA");
      Map var3 = encryptionAlgs;
      String var4 = PKCSObjectIdentifiers.rsaEncryption.getId();
      var3.put(var4, "RSA");
      Object var6 = encryptionAlgs.put("1.3.36.3.3.1", "RSA");
      Map var7 = encryptionAlgs;
      String var8 = X509ObjectIdentifiers.id_ea_rsa.getId();
      var7.put(var8, "RSA");
      Map var10 = encryptionAlgs;
      String var11 = CMSSignedDataGenerator.ENCRYPTION_RSA_PSS;
      var10.put(var11, "RSAandMGF1");
      Map var13 = encryptionAlgs;
      String var14 = CryptoProObjectIdentifiers.gostR3410_94.getId();
      var13.put(var14, "GOST3410");
      Map var16 = encryptionAlgs;
      String var17 = CryptoProObjectIdentifiers.gostR3410_2001.getId();
      var16.put(var17, "ECGOST3410");
      Object var19 = encryptionAlgs.put("1.3.6.1.4.1.5849.1.6.2", "ECGOST3410");
      Object var20 = encryptionAlgs.put("1.3.6.1.4.1.5849.1.1.5", "GOST3410");
      Map var21 = digestAlgs;
      String var22 = PKCSObjectIdentifiers.md2.getId();
      var21.put(var22, "MD2");
      Map var24 = digestAlgs;
      String var25 = PKCSObjectIdentifiers.md4.getId();
      var24.put(var25, "MD4");
      Map var27 = digestAlgs;
      String var28 = PKCSObjectIdentifiers.md5.getId();
      var27.put(var28, "MD5");
      Map var30 = digestAlgs;
      String var31 = OIWObjectIdentifiers.idSHA1.getId();
      var30.put(var31, "SHA1");
      Map var33 = digestAlgs;
      String var34 = NISTObjectIdentifiers.id_sha224.getId();
      var33.put(var34, "SHA224");
      Map var36 = digestAlgs;
      String var37 = NISTObjectIdentifiers.id_sha256.getId();
      var36.put(var37, "SHA256");
      Map var39 = digestAlgs;
      String var40 = NISTObjectIdentifiers.id_sha384.getId();
      var39.put(var40, "SHA384");
      Map var42 = digestAlgs;
      String var43 = NISTObjectIdentifiers.id_sha512.getId();
      var42.put(var43, "SHA512");
      Map var45 = digestAlgs;
      String var46 = TeleTrusTObjectIdentifiers.ripemd128.getId();
      var45.put(var46, "RIPEMD128");
      Map var48 = digestAlgs;
      String var49 = TeleTrusTObjectIdentifiers.ripemd160.getId();
      var48.put(var49, "RIPEMD160");
      Map var51 = digestAlgs;
      String var52 = TeleTrusTObjectIdentifiers.ripemd256.getId();
      var51.put(var52, "RIPEMD256");
      Map var54 = digestAlgs;
      String var55 = CryptoProObjectIdentifiers.gostR3411.getId();
      var54.put(var55, "GOST3411");
      Object var57 = digestAlgs.put("1.3.6.1.4.1.5849.1.2.1", "GOST3411");
      Map var58 = digestAliases;
      String[] var59 = new String[]{"SHA-1"};
      var58.put("SHA1", var59);
      Map var61 = digestAliases;
      String[] var62 = new String[]{"SHA-224"};
      var61.put("SHA224", var62);
      Map var64 = digestAliases;
      String[] var65 = new String[]{"SHA-256"};
      var64.put("SHA256", var65);
      Map var67 = digestAliases;
      String[] var68 = new String[]{"SHA-384"};
      var67.put("SHA384", var68);
      Map var70 = digestAliases;
      String[] var71 = new String[]{"SHA-512"};
      var70.put("SHA512", var71);
   }

   CMSSignedHelper() {}

   private void addCRLsFromSet(List param1, ASN1Set param2, Provider param3) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   private void addCertsFromSet(List param1, ASN1Set param2, Provider param3) throws CMSException {
      // $FF: Couldn't be decompiled
   }

   private static void addEntries(DERObjectIdentifier var0, String var1, String var2) {
      Map var3 = digestAlgs;
      String var4 = var0.getId();
      var3.put(var4, var1);
      Map var6 = encryptionAlgs;
      String var7 = var0.getId();
      var6.put(var7, var2);
   }

   private MessageDigest createDigestInstance(String var1, Provider var2) throws NoSuchAlgorithmException {
      MessageDigest var3;
      if(var2 != null) {
         var3 = MessageDigest.getInstance(var1, var2);
      } else {
         var3 = MessageDigest.getInstance(var1);
      }

      return var3;
   }

   X509Store createAttributeStore(String var1, Provider var2, ASN1Set var3) throws NoSuchStoreException, CMSException {
      ArrayList var4 = new ArrayList();
      if(var3 != null) {
         Enumeration var5 = var3.getObjects();

         while(var5.hasMoreElements()) {
            try {
               DERObject var6 = ((DEREncodable)var5.nextElement()).getDERObject();
               if(var6 instanceof ASN1TaggedObject) {
                  ASN1TaggedObject var7 = (ASN1TaggedObject)var6;
                  if(var7.getTagNo() == 2) {
                     byte[] var8 = ASN1Sequence.getInstance(var7, (boolean)0).getEncoded();
                     X509V2AttributeCertificate var9 = new X509V2AttributeCertificate(var8);
                     var4.add(var9);
                  }
               }
            } catch (IOException var17) {
               throw new CMSException("can\'t re-encode attribute certificate!", var17);
            }
         }
      }

      try {
         String var12 = "AttributeCertificate/" + var1;
         X509CollectionStoreParameters var13 = new X509CollectionStoreParameters(var4);
         X509Store var14 = X509Store.getInstance(var12, var13, var2);
         return var14;
      } catch (IllegalArgumentException var16) {
         throw new CMSException("can\'t setup the X509Store", var16);
      }
   }

   X509Store createCRLsStore(String var1, Provider var2, ASN1Set var3) throws NoSuchStoreException, CMSException {
      ArrayList var4 = new ArrayList();
      if(var3 != null) {
         this.addCRLsFromSet(var4, var3, var2);
      }

      try {
         String var5 = "CRL/" + var1;
         X509CollectionStoreParameters var6 = new X509CollectionStoreParameters(var4);
         X509Store var7 = X509Store.getInstance(var5, var6, var2);
         return var7;
      } catch (IllegalArgumentException var9) {
         throw new CMSException("can\'t setup the X509Store", var9);
      }
   }

   CertStore createCertStore(String param1, Provider param2, ASN1Set param3, ASN1Set param4) throws CMSException, NoSuchAlgorithmException {
      // $FF: Couldn't be decompiled
   }

   X509Store createCertificateStore(String var1, Provider var2, ASN1Set var3) throws NoSuchStoreException, CMSException {
      ArrayList var4 = new ArrayList();
      if(var3 != null) {
         this.addCertsFromSet(var4, var3, var2);
      }

      try {
         String var5 = "Certificate/" + var1;
         X509CollectionStoreParameters var6 = new X509CollectionStoreParameters(var4);
         X509Store var7 = X509Store.getInstance(var5, var6, var2);
         return var7;
      } catch (IllegalArgumentException var9) {
         throw new CMSException("can\'t setup the X509Store", var9);
      }
   }

   AlgorithmIdentifier fixAlgID(AlgorithmIdentifier var1) {
      AlgorithmIdentifier var4;
      if(var1.getParameters() == null) {
         DERObjectIdentifier var2 = var1.getObjectId();
         DERNull var3 = DERNull.INSTANCE;
         var4 = new AlgorithmIdentifier(var2, var3);
      } else {
         var4 = var1;
      }

      return var4;
   }

   String getDigestAlgName(String var1) {
      String var2 = (String)digestAlgs.get(var1);
      String var3;
      if(var2 != null) {
         var3 = var2;
      } else {
         var3 = var1;
      }

      return var3;
   }

   String[] getDigestAliases(String var1) {
      String[] var2 = (String[])((String[])digestAliases.get(var1));
      String[] var3;
      if(var2 != null) {
         var3 = var2;
      } else {
         var3 = new String[0];
      }

      return var3;
   }

   MessageDigest getDigestInstance(String var1, Provider var2) throws NoSuchAlgorithmException {
      MessageDigest var3;
      MessageDigest var4;
      try {
         var3 = this.createDigestInstance(var1, var2);
      } catch (NoSuchAlgorithmException var12) {
         String[] var6 = this.getDigestAliases(var1);
         int var7 = 0;

         while(true) {
            int var8 = var6.length;
            if(var7 != var8) {
               try {
                  String var9 = var6[var7];
                  var3 = this.createDigestInstance(var9, var2);
               } catch (NoSuchAlgorithmException var11) {
                  ++var7;
                  continue;
               }

               var4 = var3;
               return var4;
            }

            if(var2 == null) {
               throw var12;
            }

            var4 = this.getDigestInstance(var1, (Provider)null);
            return var4;
         }
      }

      var4 = var3;
      return var4;
   }

   String getEncryptionAlgName(String var1) {
      String var2 = (String)encryptionAlgs.get(var1);
      String var3;
      if(var2 != null) {
         var3 = var2;
      } else {
         var3 = var1;
      }

      return var3;
   }

   Signature getSignatureInstance(String var1, Provider var2) throws NoSuchAlgorithmException {
      Signature var3;
      if(var2 != null) {
         var3 = Signature.getInstance(var1, var2);
      } else {
         var3 = Signature.getInstance(var1);
      }

      return var3;
   }

   void setSigningDigestAlgorithmMapping(DERObjectIdentifier var1, String var2) {
      Map var3 = digestAlgs;
      String var4 = var1.getId();
      var3.put(var4, var2);
   }

   void setSigningEncryptionAlgorithmMapping(DERObjectIdentifier var1, String var2) {
      Map var3 = encryptionAlgs;
      String var4 = var1.getId();
      var3.put(var4, var2);
   }
}
