package myorg.bouncycastle.jce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.pkcs.ContentInfo;
import myorg.bouncycastle.asn1.pkcs.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.SignedData;
import myorg.bouncycastle.asn1.pkcs.SignerInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.provider.X509CRLObject;
import myorg.bouncycastle.jce.provider.X509CertificateObject;

public class PKCS7SignedData implements PKCSObjectIdentifiers {

   private final String ID_DSA;
   private final String ID_MD2;
   private final String ID_MD5;
   private final String ID_PKCS7_DATA;
   private final String ID_PKCS7_SIGNED_DATA;
   private final String ID_RSA;
   private final String ID_SHA1;
   private Collection certs;
   private Collection crls;
   private byte[] digest;
   private String digestAlgorithm;
   private String digestEncryptionAlgorithm;
   private Set digestalgos;
   private transient PrivateKey privKey;
   private Signature sig;
   private X509Certificate signCert;
   private int signerversion;
   private int version;


   public PKCS7SignedData(PrivateKey var1, Certificate[] var2, String var3) throws SecurityException, InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
      this(var1, var2, var3, "myBC");
   }

   public PKCS7SignedData(PrivateKey var1, Certificate[] var2, String var3, String var4) throws SecurityException, InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
      this(var1, var2, (CRL[])null, var3, var4);
   }

   public PKCS7SignedData(PrivateKey var1, Certificate[] var2, CRL[] var3, String var4, String var5) throws SecurityException, InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
      this.ID_PKCS7_DATA = "1.2.840.113549.1.7.1";
      this.ID_PKCS7_SIGNED_DATA = "1.2.840.113549.1.7.2";
      this.ID_MD5 = "1.2.840.113549.2.5";
      this.ID_MD2 = "1.2.840.113549.2.2";
      this.ID_SHA1 = "1.3.14.3.2.26";
      this.ID_RSA = "1.2.840.113549.1.1.1";
      this.ID_DSA = "1.2.840.10040.4.1";
      this.privKey = var1;
      if("MD5".equals(var4)) {
         this.digestAlgorithm = "1.2.840.113549.2.5";
      } else if("MD2".equals(var4)) {
         this.digestAlgorithm = "1.2.840.113549.2.2";
      } else if("SHA".equals(var4)) {
         this.digestAlgorithm = "1.3.14.3.2.26";
      } else {
         if(!"SHA1".equals(var4)) {
            String var18 = "Unknown Hash Algorithm " + var4;
            throw new NoSuchAlgorithmException(var18);
         }

         this.digestAlgorithm = "1.3.14.3.2.26";
      }

      this.signerversion = 1;
      this.version = 1;
      ArrayList var6 = new ArrayList();
      this.certs = var6;
      ArrayList var7 = new ArrayList();
      this.crls = var7;
      HashSet var8 = new HashSet();
      this.digestalgos = var8;
      Set var9 = this.digestalgos;
      String var10 = this.digestAlgorithm;
      var9.add(var10);
      X509Certificate var12 = (X509Certificate)var2[0];
      this.signCert = var12;
      int var13 = 0;

      while(true) {
         int var14 = var2.length;
         if(var13 >= var14) {
            if(var3 != null) {
               var13 = 0;

               while(true) {
                  int var19 = var3.length;
                  if(var13 >= var19) {
                     break;
                  }

                  Collection var20 = this.crls;
                  CRL var21 = var3[var13];
                  var20.add(var21);
                  ++var13;
               }
            }

            String var23 = var1.getAlgorithm();
            this.digestEncryptionAlgorithm = var23;
            String var24 = this.digestEncryptionAlgorithm;
            if("RSA".equals(var24)) {
               this.digestEncryptionAlgorithm = "1.2.840.113549.1.1.1";
            } else {
               String var26 = this.digestEncryptionAlgorithm;
               if(!"DSA".equals(var26)) {
                  StringBuilder var27 = (new StringBuilder()).append("Unknown Key Algorithm ");
                  String var28 = this.digestEncryptionAlgorithm;
                  String var29 = var27.append(var28).toString();
                  throw new NoSuchAlgorithmException(var29);
               }

               this.digestEncryptionAlgorithm = "1.2.840.10040.4.1";
            }

            Signature var25 = Signature.getInstance(this.getDigestAlgorithm(), var5);
            this.sig = var25;
            this.sig.initSign(var1);
            return;
         }

         Collection var15 = this.certs;
         Certificate var16 = var2[var13];
         var15.add(var16);
         ++var13;
      }
   }

   public PKCS7SignedData(byte[] var1) throws SecurityException, CRLException, InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
      this(var1, "myBC");
   }

   public PKCS7SignedData(byte[] var1, String var2) throws SecurityException, CRLException, InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
      String var3 = "1.2.840.113549.1.7.1";
      this.ID_PKCS7_DATA = var3;
      String var4 = "1.2.840.113549.1.7.2";
      this.ID_PKCS7_SIGNED_DATA = var4;
      String var5 = "1.2.840.113549.2.5";
      this.ID_MD5 = var5;
      String var6 = "1.2.840.113549.2.2";
      this.ID_MD2 = var6;
      String var7 = "1.3.14.3.2.26";
      this.ID_SHA1 = var7;
      String var8 = "1.2.840.113549.1.1.1";
      this.ID_RSA = var8;
      String var9 = "1.2.840.10040.4.1";
      this.ID_DSA = var9;
      ASN1InputStream var10 = new ASN1InputStream;
      ByteArrayInputStream var11 = new ByteArrayInputStream(var1);
      var10.<init>((InputStream)var11);

      DERObject var16;
      try {
         var16 = var10.readObject();
      } catch (IOException var81) {
         throw new SecurityException("can\'t decode PKCS7SignedData object");
      }

      if(!(var16 instanceof ASN1Sequence)) {
         throw new SecurityException("Not a valid PKCS#7 object - not a sequence");
      } else {
         ContentInfo var19 = ContentInfo.getInstance(var16);
         DERObjectIdentifier var20 = var19.getContentType();
         DERObjectIdentifier var21 = signedData;
         if(!var20.equals(var21)) {
            StringBuilder var22 = (new StringBuilder()).append("Not a valid PKCS#7 signed-data object - wrong header ");
            String var23 = var19.getContentType().getId();
            String var24 = var22.append(var23).toString();
            throw new SecurityException(var24);
         } else {
            SignedData var25 = SignedData.getInstance(var19.getContent());
            ArrayList var26 = new ArrayList();
            this.certs = var26;
            Enumeration var27;
            if(var25.getCertificates() != null) {
               var27 = ASN1Set.getInstance(var25.getCertificates()).getObjects();

               while(var27.hasMoreElements()) {
                  try {
                     Collection var28 = this.certs;
                     X509CertificateStructure var29 = X509CertificateStructure.getInstance(var27.nextElement());
                     X509CertificateObject var30 = new X509CertificateObject(var29);
                     var28.add(var30);
                  } catch (CertificateParsingException var80) {
                     String var32 = var80.toString();
                     throw new SecurityException(var32);
                  }
               }
            }

            ArrayList var33 = new ArrayList();
            this.crls = var33;
            if(var25.getCRLs() != null) {
               var27 = ASN1Set.getInstance(var25.getCRLs()).getObjects();

               while(var27.hasMoreElements()) {
                  Collection var34 = this.crls;
                  CertificateList var35 = CertificateList.getInstance(var27.nextElement());
                  X509CRLObject var36 = new X509CRLObject(var35);
                  var34.add(var36);
               }
            }

            int var38 = var25.getVersion().getValue().intValue();
            this.version = var38;
            HashSet var39 = new HashSet();
            this.digestalgos = var39;
            Enumeration var40 = var25.getDigestAlgorithms().getObjects();

            while(var40.hasMoreElements()) {
               ASN1Sequence var41 = (ASN1Sequence)var40.nextElement();
               byte var42 = 0;
               DERObjectIdentifier var43 = (DERObjectIdentifier)var41.getObjectAt(var42);
               Set var44 = this.digestalgos;
               String var45 = var43.getId();
               var44.add(var45);
            }

            ASN1Set var47 = var25.getSignerInfos();
            int var48 = var47.size();
            byte var49 = 1;
            if(var48 != var49) {
               throw new SecurityException("This PKCS#7 object has multiple SignerInfos - only one is supported at this time");
            } else {
               SignerInfo var50 = SignerInfo.getInstance(var47.getObjectAt(0));
               int var51 = var50.getVersion().getValue().intValue();
               this.signerversion = var51;
               IssuerAndSerialNumber var52 = var50.getIssuerAndSerialNumber();
               BigInteger var53 = var52.getCertificateSerialNumber().getValue();
               X509Principal var54 = new X509Principal;
               X509Name var55 = var52.getName();
               var54.<init>(var55);
               Iterator var58 = this.certs.iterator();

               while(var58.hasNext()) {
                  X509Certificate var59 = (X509Certificate)var58.next();
                  BigInteger var60 = var59.getSerialNumber();
                  if(var53.equals(var60)) {
                     Principal var63 = var59.getIssuerDN();
                     if(var54.equals(var63)) {
                        this.signCert = var59;
                        break;
                     }
                  }
               }

               if(this.signCert == null) {
                  StringBuilder var67 = (new StringBuilder()).append("Can\'t find signing certificate with serial ");
                  byte var69 = 16;
                  String var70 = var53.toString(var69);
                  String var71 = var67.append(var70).toString();
                  throw new SecurityException(var71);
               } else {
                  String var72 = var50.getDigestAlgorithm().getObjectId().getId();
                  this.digestAlgorithm = var72;
                  byte[] var73 = var50.getEncryptedDigest().getOctets();
                  this.digest = var73;
                  String var74 = var50.getDigestEncryptionAlgorithm().getObjectId().getId();
                  this.digestEncryptionAlgorithm = var74;
                  String var75 = this.getDigestAlgorithm();
                  Signature var77 = Signature.getInstance(var75, var2);
                  this.sig = var77;
                  Signature var78 = this.sig;
                  PublicKey var79 = this.signCert.getPublicKey();
                  var78.initVerify(var79);
               }
            }
         }
      }
   }

   private DERObject getIssuer(byte[] param1) {
      // $FF: Couldn't be decompiled
   }

   public Collection getCRLs() {
      return this.crls;
   }

   public Certificate[] getCertificates() {
      Collection var1 = this.certs;
      X509Certificate[] var2 = new X509Certificate[this.certs.size()];
      return (X509Certificate[])((X509Certificate[])var1.toArray(var2));
   }

   public String getDigestAlgorithm() {
      String var1 = this.digestAlgorithm;
      String var2 = this.digestEncryptionAlgorithm;
      if(this.digestAlgorithm != null) {
         if(this.digestAlgorithm.equals("1.2.840.113549.2.5")) {
            var1 = "MD5";
         } else if(this.digestAlgorithm.equals("1.2.840.113549.2.2")) {
            var1 = "MD2";
         } else if(this.digestAlgorithm.equals("1.3.14.3.2.26")) {
            var1 = "SHA1";
         }
      }

      if(this.digestEncryptionAlgorithm != null) {
         if(this.digestEncryptionAlgorithm.equals("1.2.840.113549.1.1.1")) {
            var2 = "RSA";
         } else if(this.digestEncryptionAlgorithm.equals("1.2.840.10040.4.1")) {
            var2 = "DSA";
         }
      }

      return var1 + "with" + var2;
   }

   public byte[] getEncoded() {
      try {
         byte[] var1 = this.sig.sign();
         this.digest = var1;
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         Iterator var3 = this.digestalgos.iterator();

         while(var3.hasNext()) {
            AlgorithmIdentifier var4 = new AlgorithmIdentifier;
            DERObjectIdentifier var5 = new DERObjectIdentifier;
            String var6 = (String)var3.next();
            var5.<init>(var6);
            Object var11 = null;
            var4.<init>(var5, (DEREncodable)var11);
            var2.add(var4);
         }

         DERSet var15 = new DERSet(var2);
         DERSequence var18 = new DERSequence;
         DERObjectIdentifier var19 = new DERObjectIdentifier("1.2.840.113549.1.7.1");
         var18.<init>((DEREncodable)var19);
         var2 = new ASN1EncodableVector();
         var3 = this.certs.iterator();

         while(var3.hasNext()) {
            ASN1InputStream var22 = new ASN1InputStream;
            byte[] var23 = ((X509Certificate)var3.next()).getEncoded();
            ByteArrayInputStream var24 = new ByteArrayInputStream(var23);
            var22.<init>((InputStream)var24);
            DERObject var27 = var22.readObject();
            var2.add(var27);
         }

         DERSet var30 = new DERSet(var2);
         ASN1EncodableVector var33 = new ASN1EncodableVector();
         int var34 = this.signerversion;
         DERInteger var35 = new DERInteger(var34);
         var33.add(var35);
         IssuerAndSerialNumber var38 = new IssuerAndSerialNumber;
         X509Name var39 = new X509Name;
         byte[] var40 = this.signCert.getTBSCertificate();
         ASN1Sequence var43 = (ASN1Sequence)this.getIssuer(var40);
         var39.<init>(var43);
         BigInteger var46 = this.signCert.getSerialNumber();
         DERInteger var47 = new DERInteger(var46);
         var38.<init>(var39, var47);
         var33.add(var38);
         String var51 = this.digestAlgorithm;
         DERObjectIdentifier var52 = new DERObjectIdentifier(var51);
         DERNull var53 = new DERNull();
         AlgorithmIdentifier var54 = new AlgorithmIdentifier(var52, var53);
         var33.add(var54);
         String var57 = this.digestEncryptionAlgorithm;
         DERObjectIdentifier var58 = new DERObjectIdentifier(var57);
         DERNull var59 = new DERNull();
         AlgorithmIdentifier var60 = new AlgorithmIdentifier(var58, var59);
         var33.add(var60);
         byte[] var63 = this.digest;
         DEROctetString var64 = new DEROctetString(var63);
         var33.add(var64);
         ASN1EncodableVector var67 = new ASN1EncodableVector();
         int var68 = this.version;
         DERInteger var69 = new DERInteger(var68);
         var67.add(var69);
         var67.add(var15);
         var67.add(var18);
         DERTaggedObject var72 = new DERTaggedObject;
         byte var74 = 0;
         byte var75 = 0;
         var72.<init>((boolean)var74, var75, var30);
         var67.add(var72);
         if(this.crls.size() > 0) {
            var2 = new ASN1EncodableVector();
            var3 = this.crls.iterator();

            while(var3.hasNext()) {
               ASN1InputStream var79 = new ASN1InputStream;
               byte[] var80 = ((X509CRL)var3.next()).getEncoded();
               ByteArrayInputStream var81 = new ByteArrayInputStream(var80);
               var79.<init>((InputStream)var81);
               DERObject var84 = var79.readObject();
               var2.add(var84);
            }

            DERSet var87 = new DERSet(var2);
            DERTaggedObject var90 = new DERTaggedObject;
            byte var92 = 0;
            byte var93 = 1;
            var90.<init>((boolean)var92, var93, var87);
            var67.add(var90);
         }

         DERSequence var97 = new DERSequence(var33);
         DERSet var100 = new DERSet(var97);
         var67.add(var100);
         ASN1EncodableVector var103 = new ASN1EncodableVector();
         DERObjectIdentifier var104 = new DERObjectIdentifier("1.2.840.113549.1.7.2");
         var103.add(var104);
         DERSequence var105 = new DERSequence(var67);
         DERTaggedObject var108 = new DERTaggedObject(0, var105);
         var103.add(var108);
         ByteArrayOutputStream var109 = new ByteArrayOutputStream();
         DEROutputStream var110 = new DEROutputStream(var109);
         DERSequence var111 = new DERSequence(var103);
         var110.writeObject(var111);
         var110.close();
         byte[] var116 = var109.toByteArray();
         return var116;
      } catch (Exception var117) {
         String var14 = var117.toString();
         throw new RuntimeException(var14);
      }
   }

   public X509Certificate getSigningCertificate() {
      return this.signCert;
   }

   public int getSigningInfoVersion() {
      return this.signerversion;
   }

   public int getVersion() {
      return this.version;
   }

   public void reset() {
      try {
         if(this.privKey == null) {
            Signature var1 = this.sig;
            PublicKey var2 = this.signCert.getPublicKey();
            var1.initVerify(var2);
         } else {
            Signature var3 = this.sig;
            PrivateKey var4 = this.privKey;
            var3.initSign(var4);
         }
      } catch (Exception var6) {
         String var5 = var6.toString();
         throw new RuntimeException(var5);
      }
   }

   public void update(byte var1) throws SignatureException {
      this.sig.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) throws SignatureException {
      this.sig.update(var1, var2, var3);
   }

   public boolean verify() throws SignatureException {
      Signature var1 = this.sig;
      byte[] var2 = this.digest;
      return var1.verify(var2);
   }
}
