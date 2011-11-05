package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.SignerIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.AttributeCertificate;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSStreamException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.SignerInformationStore;
import myorg.bouncycastle.jce.interfaces.GOST3410PrivateKey;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509Store;

public class CMSSignedGenerator {

   public static final String DATA = CMSObjectIdentifiers.data.getId();
   public static final String DIGEST_GOST3411 = CryptoProObjectIdentifiers.gostR3411.getId();
   public static final String DIGEST_MD5 = PKCSObjectIdentifiers.md5.getId();
   public static final String DIGEST_RIPEMD128 = TeleTrusTObjectIdentifiers.ripemd128.getId();
   public static final String DIGEST_RIPEMD160 = TeleTrusTObjectIdentifiers.ripemd160.getId();
   public static final String DIGEST_RIPEMD256 = TeleTrusTObjectIdentifiers.ripemd256.getId();
   public static final String DIGEST_SHA1 = OIWObjectIdentifiers.idSHA1.getId();
   public static final String DIGEST_SHA224 = NISTObjectIdentifiers.id_sha224.getId();
   public static final String DIGEST_SHA256 = NISTObjectIdentifiers.id_sha256.getId();
   public static final String DIGEST_SHA384 = NISTObjectIdentifiers.id_sha384.getId();
   public static final String DIGEST_SHA512 = NISTObjectIdentifiers.id_sha512.getId();
   private static final Map EC_ALGORITHMS = new HashMap();
   public static final String ENCRYPTION_DSA = X9ObjectIdentifiers.id_dsa_with_sha1.getId();
   public static final String ENCRYPTION_ECDSA = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
   private static final String ENCRYPTION_ECDSA_WITH_SHA1 = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
   private static final String ENCRYPTION_ECDSA_WITH_SHA224 = X9ObjectIdentifiers.ecdsa_with_SHA224.getId();
   private static final String ENCRYPTION_ECDSA_WITH_SHA256 = X9ObjectIdentifiers.ecdsa_with_SHA256.getId();
   private static final String ENCRYPTION_ECDSA_WITH_SHA384 = X9ObjectIdentifiers.ecdsa_with_SHA384.getId();
   private static final String ENCRYPTION_ECDSA_WITH_SHA512 = X9ObjectIdentifiers.ecdsa_with_SHA512.getId();
   public static final String ENCRYPTION_ECGOST3410 = CryptoProObjectIdentifiers.gostR3410_2001.getId();
   public static final String ENCRYPTION_GOST3410 = CryptoProObjectIdentifiers.gostR3410_94.getId();
   public static final String ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption.getId();
   public static final String ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS.getId();
   private static final Set NO_PARAMS = new HashSet();
   protected List _certs;
   protected List _crls;
   protected Map _digests;
   protected List _signers;
   protected final SecureRandom rand;


   static {
      Set var0 = NO_PARAMS;
      String var1 = ENCRYPTION_DSA;
      var0.add(var1);
      Set var3 = NO_PARAMS;
      String var4 = ENCRYPTION_ECDSA;
      var3.add(var4);
      Set var6 = NO_PARAMS;
      String var7 = ENCRYPTION_ECDSA_WITH_SHA1;
      var6.add(var7);
      Set var9 = NO_PARAMS;
      String var10 = ENCRYPTION_ECDSA_WITH_SHA224;
      var9.add(var10);
      Set var12 = NO_PARAMS;
      String var13 = ENCRYPTION_ECDSA_WITH_SHA256;
      var12.add(var13);
      Set var15 = NO_PARAMS;
      String var16 = ENCRYPTION_ECDSA_WITH_SHA384;
      var15.add(var16);
      Set var18 = NO_PARAMS;
      String var19 = ENCRYPTION_ECDSA_WITH_SHA512;
      var18.add(var19);
      Map var21 = EC_ALGORITHMS;
      String var22 = DIGEST_SHA1;
      String var23 = ENCRYPTION_ECDSA_WITH_SHA1;
      var21.put(var22, var23);
      Map var25 = EC_ALGORITHMS;
      String var26 = DIGEST_SHA224;
      String var27 = ENCRYPTION_ECDSA_WITH_SHA224;
      var25.put(var26, var27);
      Map var29 = EC_ALGORITHMS;
      String var30 = DIGEST_SHA256;
      String var31 = ENCRYPTION_ECDSA_WITH_SHA256;
      var29.put(var30, var31);
      Map var33 = EC_ALGORITHMS;
      String var34 = DIGEST_SHA384;
      String var35 = ENCRYPTION_ECDSA_WITH_SHA384;
      var33.put(var34, var35);
      Map var37 = EC_ALGORITHMS;
      String var38 = DIGEST_SHA512;
      String var39 = ENCRYPTION_ECDSA_WITH_SHA512;
      var37.put(var38, var39);
   }

   protected CMSSignedGenerator() {
      SecureRandom var1 = new SecureRandom();
      this(var1);
   }

   protected CMSSignedGenerator(SecureRandom var1) {
      ArrayList var2 = new ArrayList();
      this._certs = var2;
      ArrayList var3 = new ArrayList();
      this._crls = var3;
      ArrayList var4 = new ArrayList();
      this._signers = var4;
      HashMap var5 = new HashMap();
      this._digests = var5;
      this.rand = var1;
   }

   static SignerIdentifier getSignerIdentifier(X509Certificate var0) {
      TBSCertificateStructure var1;
      try {
         var1 = CMSUtils.getTBSCertificateStructure(var0);
      } catch (CertificateEncodingException var7) {
         throw new IllegalArgumentException("can\'t extract TBS structure from this cert");
      }

      X509Name var3 = var1.getIssuer();
      BigInteger var4 = var1.getSerialNumber().getValue();
      IssuerAndSerialNumber var5 = new IssuerAndSerialNumber(var3, var4);
      return new SignerIdentifier(var5);
   }

   static SignerIdentifier getSignerIdentifier(byte[] var0) {
      DEROctetString var1 = new DEROctetString(var0);
      return new SignerIdentifier(var1);
   }

   public void addAttributeCertificates(X509Store var1) throws CMSException {
      try {
         Iterator var2 = var1.getMatches((Selector)null).iterator();

         while(var2.hasNext()) {
            X509AttributeCertificate var3 = (X509AttributeCertificate)var2.next();
            List var4 = this._certs;
            AttributeCertificate var5 = AttributeCertificate.getInstance(ASN1Object.fromByteArray(var3.getEncoded()));
            DERTaggedObject var6 = new DERTaggedObject((boolean)0, 2, var5);
            var4.add(var6);
         }

      } catch (IllegalArgumentException var10) {
         throw new CMSException("error processing attribute certs", var10);
      } catch (IOException var11) {
         throw new CMSException("error processing attribute certs", var11);
      }
   }

   public void addCertificatesAndCRLs(CertStore var1) throws CertStoreException, CMSException {
      List var2 = this._certs;
      List var3 = CMSUtils.getCertificatesFromStore(var1);
      var2.addAll(var3);
      List var5 = this._crls;
      List var6 = CMSUtils.getCRLsFromStore(var1);
      var5.addAll(var6);
   }

   public void addSigners(SignerInformationStore var1) {
      Iterator var2 = var1.getSigners().iterator();

      while(var2.hasNext()) {
         List var3 = this._signers;
         Object var4 = var2.next();
         var3.add(var4);
      }

   }

   protected ASN1Set getAttributeSet(AttributeTable var1) {
      DERSet var3;
      if(var1 != null) {
         ASN1EncodableVector var2 = var1.toASN1EncodableVector();
         var3 = new DERSet(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   protected Map getBaseParameters(DERObjectIdentifier var1, AlgorithmIdentifier var2, byte[] var3) {
      HashMap var4 = new HashMap();
      var4.put("contentType", var1);
      var4.put("digestAlgID", var2);
      Object var7 = var3.clone();
      var4.put("digest", var7);
      return var4;
   }

   protected AlgorithmIdentifier getEncAlgorithmIdentifier(String var1, Signature var2) throws IOException {
      AlgorithmIdentifier var4;
      if(NO_PARAMS.contains(var1)) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         var4 = new AlgorithmIdentifier(var3);
      } else {
         String var5 = ENCRYPTION_RSA_PSS;
         if(var1.equals(var5)) {
            AlgorithmParameters var6 = var2.getParameters();
            DERObjectIdentifier var7 = new DERObjectIdentifier(var1);
            ASN1Object var8 = ASN1Object.fromByteArray(var6.getEncoded());
            var4 = new AlgorithmIdentifier(var7, var8);
         } else {
            DERObjectIdentifier var9 = new DERObjectIdentifier(var1);
            DERNull var10 = new DERNull();
            var4 = new AlgorithmIdentifier(var9, var10);
         }
      }

      return var4;
   }

   protected String getEncOID(PrivateKey var1, String var2) {
      String var3 = null;
      if(!(var1 instanceof RSAPrivateKey)) {
         String var4 = var1.getAlgorithm();
         if(!"RSA".equalsIgnoreCase(var4)) {
            if(!(var1 instanceof DSAPrivateKey)) {
               String var5 = var1.getAlgorithm();
               if(!"DSA".equalsIgnoreCase(var5)) {
                  String var7 = var1.getAlgorithm();
                  if(!"ECDSA".equalsIgnoreCase(var7)) {
                     String var8 = var1.getAlgorithm();
                     if(!"EC".equalsIgnoreCase(var8)) {
                        if(!(var1 instanceof GOST3410PrivateKey)) {
                           String var9 = var1.getAlgorithm();
                           if(!"GOST3410".equalsIgnoreCase(var9)) {
                              String var10 = var1.getAlgorithm();
                              if("ECGOST3410".equalsIgnoreCase(var10)) {
                                 var3 = ENCRYPTION_ECGOST3410;
                              }

                              return var3;
                           }
                        }

                        var3 = ENCRYPTION_GOST3410;
                        return var3;
                     }
                  }

                  var3 = (String)EC_ALGORITHMS.get(var2);
                  if(var3 == null) {
                     throw new IllegalArgumentException("can\'t mix ECDSA with anything but SHA family digests");
                  }

                  return var3;
               }
            }

            var3 = ENCRYPTION_DSA;
            String var6 = DIGEST_SHA1;
            if(!var2.equals(var6)) {
               throw new IllegalArgumentException("can\'t mix DSA with anything but SHA1");
            }

            return var3;
         }
      }

      var3 = ENCRYPTION_RSA;
      return var3;
   }

   public Map getGeneratedDigests() {
      Map var1 = this._digests;
      return new HashMap(var1);
   }

   static class DigOutputStream extends OutputStream {

      MessageDigest dig;


      public DigOutputStream(MessageDigest var1) {
         this.dig = var1;
      }

      public void write(int var1) throws IOException {
         MessageDigest var2 = this.dig;
         byte var3 = (byte)var1;
         var2.update(var3);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.dig.update(var1, var2, var3);
      }
   }

   static class SigOutputStream extends OutputStream {

      private final Signature sig;


      public SigOutputStream(Signature var1) {
         this.sig = var1;
      }

      public void write(int var1) throws IOException {
         try {
            Signature var2 = this.sig;
            byte var3 = (byte)var1;
            var2.update(var3);
         } catch (SignatureException var6) {
            String var5 = "signature problem: " + var6;
            throw new CMSStreamException(var5, var6);
         }
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         try {
            this.sig.update(var1, var2, var3);
         } catch (SignatureException var6) {
            String var5 = "signature problem: " + var6;
            throw new CMSStreamException(var5, var6);
         }
      }
   }
}
