package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.V2TBSCertListGenerator;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.provider.X509CRLObject;
import myorg.bouncycastle.x509.X509Util;

public class X509V2CRLGenerator {

   private X509ExtensionsGenerator extGenerator;
   private AlgorithmIdentifier sigAlgId;
   private DERObjectIdentifier sigOID;
   private String signatureAlgorithm;
   private V2TBSCertListGenerator tbsGen;


   public X509V2CRLGenerator() {
      V2TBSCertListGenerator var1 = new V2TBSCertListGenerator();
      this.tbsGen = var1;
      X509ExtensionsGenerator var2 = new X509ExtensionsGenerator();
      this.extGenerator = var2;
   }

   private TBSCertList generateCertList() {
      if(!this.extGenerator.isEmpty()) {
         V2TBSCertListGenerator var1 = this.tbsGen;
         X509Extensions var2 = this.extGenerator.generate();
         var1.setExtensions(var2);
      }

      return this.tbsGen.generateTBSCertList();
   }

   private X509CRL generateJcaObject(TBSCertList var1, byte[] var2) throws CRLException {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(var1);
      AlgorithmIdentifier var4 = this.sigAlgId;
      var3.add(var4);
      DERBitString var5 = new DERBitString(var2);
      var3.add(var5);
      DERSequence var6 = new DERSequence(var3);
      CertificateList var7 = new CertificateList(var6);
      return new X509CRLObject(var7);
   }

   public void addCRL(X509CRL var1) throws CRLException {
      Set var2 = var1.getRevokedCertificates();
      if(var2 != null) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            byte[] var4 = ((X509CRLEntry)var3.next()).getEncoded();
            ASN1InputStream var5 = new ASN1InputStream(var4);

            try {
               V2TBSCertListGenerator var6 = this.tbsGen;
               ASN1Sequence var7 = ASN1Sequence.getInstance(var5.readObject());
               var6.addCRLEntry(var7);
            } catch (IOException var12) {
               StringBuilder var9 = (new StringBuilder()).append("exception processing encoding of CRL: ");
               String var10 = var12.toString();
               String var11 = var9.append(var10).toString();
               throw new CRLException(var11);
            }
         }

      }
   }

   public void addCRLEntry(BigInteger var1, Date var2, int var3) {
      V2TBSCertListGenerator var4 = this.tbsGen;
      DERInteger var5 = new DERInteger(var1);
      Time var6 = new Time(var2);
      var4.addCRLEntry(var5, var6, var3);
   }

   public void addCRLEntry(BigInteger var1, Date var2, int var3, Date var4) {
      V2TBSCertListGenerator var5 = this.tbsGen;
      DERInteger var6 = new DERInteger(var1);
      Time var7 = new Time(var2);
      DERGeneralizedTime var8 = new DERGeneralizedTime(var4);
      var5.addCRLEntry(var6, var7, var3, var8);
   }

   public void addCRLEntry(BigInteger var1, Date var2, X509Extensions var3) {
      V2TBSCertListGenerator var4 = this.tbsGen;
      DERInteger var5 = new DERInteger(var1);
      Time var6 = new Time(var2);
      var4.addCRLEntry(var5, var6, var3);
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

   public X509CRL generate(PrivateKey var1) throws CRLException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      SecureRandom var2 = (SecureRandom)false;
      return this.generate(var1, var2);
   }

   public X509CRL generate(PrivateKey var1, String var2) throws CRLException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      return this.generate(var1, var2, (SecureRandom)null);
   }

   public X509CRL generate(PrivateKey var1, String var2, SecureRandom var3) throws CRLException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      TBSCertList var4 = this.generateCertList();

      byte[] var10;
      try {
         DERObjectIdentifier var5 = this.sigOID;
         String var6 = this.signatureAlgorithm;
         var10 = X509Util.calculateSignature(var5, var6, var2, var1, var3, var4);
      } catch (IOException var13) {
         throw new X509V2CRLGenerator.ExtCRLException("cannot generate CRL encoding", var13);
      }

      return this.generateJcaObject(var4, var10);
   }

   public X509CRL generate(PrivateKey var1, SecureRandom var2) throws CRLException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      TBSCertList var3 = this.generateCertList();

      byte[] var6;
      try {
         DERObjectIdentifier var4 = this.sigOID;
         String var5 = this.signatureAlgorithm;
         var6 = X509Util.calculateSignature(var4, var5, var1, var2, var3);
      } catch (IOException var9) {
         throw new X509V2CRLGenerator.ExtCRLException("cannot generate CRL encoding", var9);
      }

      return this.generateJcaObject(var3, var6);
   }

   public X509CRL generateX509CRL(PrivateKey var1) throws SecurityException, SignatureException, InvalidKeyException {
      try {
         X509CRL var2 = this.generateX509CRL(var1, "myBC", (SecureRandom)null);
         return var2;
      } catch (NoSuchProviderException var4) {
         throw new SecurityException("BC provider not installed!");
      }
   }

   public X509CRL generateX509CRL(PrivateKey var1, String var2) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
      return this.generateX509CRL(var1, var2, (SecureRandom)null);
   }

   public X509CRL generateX509CRL(PrivateKey var1, String var2, SecureRandom var3) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
      try {
         X509CRL var4 = this.generate(var1, var2, var3);
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

   public X509CRL generateX509CRL(PrivateKey var1, SecureRandom var2) throws SecurityException, SignatureException, InvalidKeyException {
      try {
         X509CRL var3 = this.generateX509CRL(var1, "myBC", var2);
         return var3;
      } catch (NoSuchProviderException var5) {
         throw new SecurityException("BC provider not installed!");
      }
   }

   public Iterator getSignatureAlgNames() {
      return X509Util.getAlgNames();
   }

   public void reset() {
      V2TBSCertListGenerator var1 = new V2TBSCertListGenerator();
      this.tbsGen = var1;
      this.extGenerator.reset();
   }

   public void setIssuerDN(X500Principal var1) {
      try {
         V2TBSCertListGenerator var2 = this.tbsGen;
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

   public void setNextUpdate(Date var1) {
      V2TBSCertListGenerator var2 = this.tbsGen;
      Time var3 = new Time(var1);
      var2.setNextUpdate(var3);
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
      V2TBSCertListGenerator var4 = this.tbsGen;
      AlgorithmIdentifier var5 = this.sigAlgId;
      var4.setSignature(var5);
   }

   public void setThisUpdate(Date var1) {
      V2TBSCertListGenerator var2 = this.tbsGen;
      Time var3 = new Time(var1);
      var2.setThisUpdate(var3);
   }

   private static class ExtCRLException extends CRLException {

      Throwable cause;


      ExtCRLException(String var1, Throwable var2) {
         super(var1);
         this.cause = var2;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }
}
