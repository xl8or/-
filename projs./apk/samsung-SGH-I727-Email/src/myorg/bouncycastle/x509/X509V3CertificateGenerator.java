package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.provider.X509CertificateObject;
import myorg.bouncycastle.x509.ExtCertificateEncodingException;
import myorg.bouncycastle.x509.X509Util;
import myorg.bouncycastle.x509.extension.X509ExtensionUtil;

public class X509V3CertificateGenerator {

   private X509ExtensionsGenerator extGenerator;
   private AlgorithmIdentifier sigAlgId;
   private DERObjectIdentifier sigOID;
   private String signatureAlgorithm;
   private V3TBSCertificateGenerator tbsGen;


   public X509V3CertificateGenerator() {
      V3TBSCertificateGenerator var1 = new V3TBSCertificateGenerator();
      this.tbsGen = var1;
      X509ExtensionsGenerator var2 = new X509ExtensionsGenerator();
      this.extGenerator = var2;
   }

   private DERBitString booleanToBitString(boolean[] var1) {
      byte[] var2 = new byte[(var1.length + 7) / 8];
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 == var4) {
            int var11 = var1.length % 8;
            DERBitString var12;
            if(var11 == 0) {
               var12 = new DERBitString(var2);
            } else {
               int var13 = 8 - var11;
               var12 = new DERBitString(var2, var13);
            }

            return var12;
         }

         int var5 = var3 / 8;
         byte var6 = var2[var5];
         int var9;
         if(var1[var3]) {
            int var7 = var3 % 8;
            int var8 = 7 - var7;
            var9 = 1 << var8;
         } else {
            var9 = 0;
         }

         byte var10 = (byte)(var6 | var9);
         var2[var5] = var10;
         ++var3;
      }
   }

   private X509Certificate generateJcaObject(TBSCertificateStructure var1, byte[] var2) throws CertificateParsingException {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      AlgorithmIdentifier var4 = this.sigAlgId;
      var3.add(var4);
      DERBitString var5 = new DERBitString(var2);
      var3.add(var5);
      DERSequence var6 = new DERSequence(var3);
      X509CertificateStructure var7 = new X509CertificateStructure(var6);
      return new X509CertificateObject(var7);
   }

   private TBSCertificateStructure generateTbsCert() {
      if(!this.extGenerator.isEmpty()) {
         V3TBSCertificateGenerator var1 = this.tbsGen;
         X509Extensions var2 = this.extGenerator.generate();
         var1.setExtensions(var2);
      }

      return this.tbsGen.generateTBSCertificate();
   }

   public void addExtension(String var1, boolean var2, DEREncodable var3) {
      DERObjectIdentifier var4 = new DERObjectIdentifier(var1);
      this.addExtension(var4, var2, var3);
   }

   public void addExtension(String var1, boolean var2, byte[] var3) {
      DERObjectIdentifier var4 = new DERObjectIdentifier(var1);
      this.addExtension(var4, var2, var3);
   }

   public void addExtension(DERObjectIdentifier var1, boolean var2, DEREncodable var3) {
      this.extGenerator.addExtension(var1, var2, var3);
   }

   public void addExtension(DERObjectIdentifier var1, boolean var2, byte[] var3) {
      this.extGenerator.addExtension(var1, var2, var3);
   }

   public void copyAndAddExtension(String var1, boolean var2, X509Certificate var3) throws CertificateParsingException {
      byte[] var4 = var3.getExtensionValue(var1);
      if(var4 == null) {
         String var5 = "extension " + var1 + " not present";
         throw new CertificateParsingException(var5);
      } else {
         try {
            ASN1Object var6 = X509ExtensionUtil.fromExtensionValue(var4);
            this.addExtension(var1, var2, (DEREncodable)var6);
         } catch (IOException var8) {
            String var7 = var8.toString();
            throw new CertificateParsingException(var7);
         }
      }
   }

   public void copyAndAddExtension(DERObjectIdentifier var1, boolean var2, X509Certificate var3) throws CertificateParsingException {
      String var4 = var1.getId();
      this.copyAndAddExtension(var4, var2, var3);
   }

   public X509Certificate generate(PrivateKey var1) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      SecureRandom var2 = (SecureRandom)false;
      return this.generate(var1, var2);
   }

   public X509Certificate generate(PrivateKey var1, String var2) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      return this.generate(var1, var2, (SecureRandom)null);
   }

   public X509Certificate generate(PrivateKey var1, String var2, SecureRandom var3) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      TBSCertificateStructure var4 = this.generateTbsCert();

      byte[] var10;
      try {
         DERObjectIdentifier var5 = this.sigOID;
         String var6 = this.signatureAlgorithm;
         var10 = X509Util.calculateSignature(var5, var6, var2, var1, var3, var4);
      } catch (IOException var15) {
         throw new ExtCertificateEncodingException("exception encoding TBS cert", var15);
      }

      byte[] var11 = var10;

      try {
         X509Certificate var16 = this.generateJcaObject(var4, var11);
         return var16;
      } catch (CertificateParsingException var14) {
         throw new ExtCertificateEncodingException("exception producing certificate object", var14);
      }
   }

   public X509Certificate generate(PrivateKey var1, SecureRandom var2) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      TBSCertificateStructure var3 = this.generateTbsCert();

      byte[] var6;
      try {
         DERObjectIdentifier var4 = this.sigOID;
         String var5 = this.signatureAlgorithm;
         var6 = X509Util.calculateSignature(var4, var5, var1, var2, var3);
      } catch (IOException var11) {
         throw new ExtCertificateEncodingException("exception encoding TBS cert", var11);
      }

      byte[] var7 = var6;

      try {
         X509Certificate var12 = this.generateJcaObject(var3, var7);
         return var12;
      } catch (CertificateParsingException var10) {
         throw new ExtCertificateEncodingException("exception producing certificate object", var10);
      }
   }

   public X509Certificate generateX509Certificate(PrivateKey var1) throws SecurityException, SignatureException, InvalidKeyException {
      try {
         X509Certificate var2 = this.generateX509Certificate(var1, "myBC", (SecureRandom)null);
         return var2;
      } catch (NoSuchProviderException var4) {
         throw new SecurityException("BC provider not installed!");
      }
   }

   public X509Certificate generateX509Certificate(PrivateKey var1, String var2) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
      return this.generateX509Certificate(var1, var2, (SecureRandom)null);
   }

   public X509Certificate generateX509Certificate(PrivateKey var1, String var2, SecureRandom var3) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
      try {
         X509Certificate var4 = this.generate(var1, var2, var3);
         return var4;
      } catch (NoSuchProviderException var7) {
         throw var7;
      } catch (SignatureException var8) {
         throw var8;
      } catch (InvalidKeyException var9) {
         throw var9;
      } catch (GeneralSecurityException var10) {
         String var6 = "exception: " + var10;
         throw new SecurityException(var6);
      }
   }

   public X509Certificate generateX509Certificate(PrivateKey var1, SecureRandom var2) throws SecurityException, SignatureException, InvalidKeyException {
      try {
         X509Certificate var3 = this.generateX509Certificate(var1, "myBC", var2);
         return var3;
      } catch (NoSuchProviderException var5) {
         throw new SecurityException("BC provider not installed!");
      }
   }

   public Iterator getSignatureAlgNames() {
      return X509Util.getAlgNames();
   }

   public void reset() {
      V3TBSCertificateGenerator var1 = new V3TBSCertificateGenerator();
      this.tbsGen = var1;
      this.extGenerator.reset();
   }

   public void setIssuerDN(X500Principal var1) {
      try {
         V3TBSCertificateGenerator var2 = this.tbsGen;
         byte[] var3 = var1.getEncoded();
         X509Principal var4 = new X509Principal(var3);
         var2.setIssuer(var4);
      } catch (IOException var7) {
         String var6 = "can\'t process principal: " + var7;
         throw new IllegalArgumentException(var6);
      }
   }

   public void setIssuerDN(X509Name var1) {
      this.tbsGen.setIssuer(var1);
   }

   public void setIssuerUniqueID(boolean[] var1) {
      V3TBSCertificateGenerator var2 = this.tbsGen;
      DERBitString var3 = this.booleanToBitString(var1);
      var2.setIssuerUniqueID(var3);
   }

   public void setNotAfter(Date var1) {
      V3TBSCertificateGenerator var2 = this.tbsGen;
      Time var3 = new Time(var1);
      var2.setEndDate(var3);
   }

   public void setNotBefore(Date var1) {
      V3TBSCertificateGenerator var2 = this.tbsGen;
      Time var3 = new Time(var1);
      var2.setStartDate(var3);
   }

   public void setPublicKey(PublicKey var1) throws IllegalArgumentException {
      try {
         V3TBSCertificateGenerator var2 = this.tbsGen;
         byte[] var3 = var1.getEncoded();
         SubjectPublicKeyInfo var4 = SubjectPublicKeyInfo.getInstance((new ASN1InputStream(var3)).readObject());
         var2.setSubjectPublicKeyInfo(var4);
      } catch (Exception var9) {
         StringBuilder var6 = (new StringBuilder()).append("unable to process key - ");
         String var7 = var9.toString();
         String var8 = var6.append(var7).toString();
         throw new IllegalArgumentException(var8);
      }
   }

   public void setSerialNumber(BigInteger var1) {
      BigInteger var2 = BigInteger.ZERO;
      if(var1.compareTo(var2) <= 0) {
         throw new IllegalArgumentException("serial number must be a positive integer");
      } else {
         V3TBSCertificateGenerator var3 = this.tbsGen;
         DERInteger var4 = new DERInteger(var1);
         var3.setSerialNumber(var4);
      }
   }

   public void setSignatureAlgorithm(String var1) {
      this.signatureAlgorithm = var1;

      try {
         DERObjectIdentifier var2 = X509Util.getAlgorithmOID(var1);
         this.sigOID = var2;
      } catch (Exception var8) {
         String var7 = "Unknown signature type requested: " + var1;
         throw new IllegalArgumentException(var7);
      }

      AlgorithmIdentifier var3 = X509Util.getSigAlgID(this.sigOID, var1);
      this.sigAlgId = var3;
      V3TBSCertificateGenerator var4 = this.tbsGen;
      AlgorithmIdentifier var5 = this.sigAlgId;
      var4.setSignature(var5);
   }

   public void setSubjectDN(X500Principal var1) {
      try {
         V3TBSCertificateGenerator var2 = this.tbsGen;
         byte[] var3 = var1.getEncoded();
         X509Principal var4 = new X509Principal(var3);
         var2.setSubject(var4);
      } catch (IOException var7) {
         String var6 = "can\'t process principal: " + var7;
         throw new IllegalArgumentException(var6);
      }
   }

   public void setSubjectDN(X509Name var1) {
      this.tbsGen.setSubject(var1);
   }

   public void setSubjectUniqueID(boolean[] var1) {
      V3TBSCertificateGenerator var2 = this.tbsGen;
      DERBitString var3 = this.booleanToBitString(var1);
      var2.setSubjectUniqueID(var3);
   }
}
