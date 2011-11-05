package myorg.bouncycastle.x509;

import java.io.ByteArrayInputStream;
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
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.V1TBSCertificateGenerator;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.provider.X509CertificateObject;
import myorg.bouncycastle.x509.ExtCertificateEncodingException;
import myorg.bouncycastle.x509.X509Util;

public class X509V1CertificateGenerator {

   private AlgorithmIdentifier sigAlgId;
   private DERObjectIdentifier sigOID;
   private String signatureAlgorithm;
   private V1TBSCertificateGenerator tbsGen;


   public X509V1CertificateGenerator() {
      V1TBSCertificateGenerator var1 = new V1TBSCertificateGenerator();
      this.tbsGen = var1;
   }

   private X509Certificate generateJcaObject(TBSCertificateStructure var1, byte[] var2) throws CertificateEncodingException {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      AlgorithmIdentifier var4 = this.sigAlgId;
      var3.add(var4);
      DERBitString var5 = new DERBitString(var2);
      var3.add(var5);

      try {
         DERSequence var6 = new DERSequence(var3);
         X509CertificateStructure var7 = new X509CertificateStructure(var6);
         X509CertificateObject var8 = new X509CertificateObject(var7);
         return var8;
      } catch (CertificateParsingException var10) {
         throw new ExtCertificateEncodingException("exception producing certificate object", var10);
      }
   }

   public X509Certificate generate(PrivateKey var1) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      SecureRandom var2 = (SecureRandom)false;
      return this.generate(var1, var2);
   }

   public X509Certificate generate(PrivateKey var1, String var2) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      return this.generate(var1, var2, (SecureRandom)null);
   }

   public X509Certificate generate(PrivateKey var1, String var2, SecureRandom var3) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      TBSCertificateStructure var4 = this.tbsGen.generateTBSCertificate();

      byte[] var10;
      try {
         DERObjectIdentifier var5 = this.sigOID;
         String var6 = this.signatureAlgorithm;
         var10 = X509Util.calculateSignature(var5, var6, var2, var1, var3, var4);
      } catch (IOException var13) {
         throw new ExtCertificateEncodingException("exception encoding TBS cert", var13);
      }

      return this.generateJcaObject(var4, var10);
   }

   public X509Certificate generate(PrivateKey var1, SecureRandom var2) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      TBSCertificateStructure var3 = this.tbsGen.generateTBSCertificate();

      byte[] var6;
      try {
         DERObjectIdentifier var4 = this.sigOID;
         String var5 = this.signatureAlgorithm;
         var6 = X509Util.calculateSignature(var4, var5, var1, var2, var3);
      } catch (IOException var9) {
         throw new ExtCertificateEncodingException("exception encoding TBS cert", var9);
      }

      return this.generateJcaObject(var3, var6);
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
      V1TBSCertificateGenerator var1 = new V1TBSCertificateGenerator();
      this.tbsGen = var1;
   }

   public void setIssuerDN(X500Principal var1) {
      try {
         V1TBSCertificateGenerator var2 = this.tbsGen;
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

   public void setNotAfter(Date var1) {
      V1TBSCertificateGenerator var2 = this.tbsGen;
      Time var3 = new Time(var1);
      var2.setEndDate(var3);
   }

   public void setNotBefore(Date var1) {
      V1TBSCertificateGenerator var2 = this.tbsGen;
      Time var3 = new Time(var1);
      var2.setStartDate(var3);
   }

   public void setPublicKey(PublicKey var1) {
      try {
         V1TBSCertificateGenerator var2 = this.tbsGen;
         byte[] var3 = var1.getEncoded();
         ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
         ASN1Sequence var5 = (ASN1Sequence)(new ASN1InputStream(var4)).readObject();
         SubjectPublicKeyInfo var6 = new SubjectPublicKeyInfo(var5);
         var2.setSubjectPublicKeyInfo(var6);
      } catch (Exception var11) {
         StringBuilder var8 = (new StringBuilder()).append("unable to process key - ");
         String var9 = var11.toString();
         String var10 = var8.append(var9).toString();
         throw new IllegalArgumentException(var10);
      }
   }

   public void setSerialNumber(BigInteger var1) {
      BigInteger var2 = BigInteger.ZERO;
      if(var1.compareTo(var2) <= 0) {
         throw new IllegalArgumentException("serial number must be a positive integer");
      } else {
         V1TBSCertificateGenerator var3 = this.tbsGen;
         DERInteger var4 = new DERInteger(var1);
         var3.setSerialNumber(var4);
      }
   }

   public void setSignatureAlgorithm(String var1) {
      this.signatureAlgorithm = var1;

      try {
         DERObjectIdentifier var2 = X509Util.getAlgorithmOID(var1);
         this.sigOID = var2;
      } catch (Exception var7) {
         throw new IllegalArgumentException("Unknown signature type requested");
      }

      AlgorithmIdentifier var3 = X509Util.getSigAlgID(this.sigOID, var1);
      this.sigAlgId = var3;
      V1TBSCertificateGenerator var4 = this.tbsGen;
      AlgorithmIdentifier var5 = this.sigAlgId;
      var4.setSignature(var5);
   }

   public void setSubjectDN(X500Principal var1) {
      try {
         V1TBSCertificateGenerator var2 = this.tbsGen;
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
}
