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
import java.security.cert.CertificateEncodingException;
import java.util.Date;
import java.util.Iterator;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.AttCertIssuer;
import myorg.bouncycastle.asn1.x509.Attribute;
import myorg.bouncycastle.asn1.x509.AttributeCertificate;
import myorg.bouncycastle.asn1.x509.AttributeCertificateInfo;
import myorg.bouncycastle.asn1.x509.Holder;
import myorg.bouncycastle.asn1.x509.V2AttributeCertificateInfoGenerator;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import myorg.bouncycastle.x509.AttributeCertificateHolder;
import myorg.bouncycastle.x509.AttributeCertificateIssuer;
import myorg.bouncycastle.x509.ExtCertificateEncodingException;
import myorg.bouncycastle.x509.X509Attribute;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509Util;
import myorg.bouncycastle.x509.X509V2AttributeCertificate;

public class X509V2AttributeCertificateGenerator {

   private V2AttributeCertificateInfoGenerator acInfoGen;
   private X509ExtensionsGenerator extGenerator;
   private AlgorithmIdentifier sigAlgId;
   private DERObjectIdentifier sigOID;
   private String signatureAlgorithm;


   public X509V2AttributeCertificateGenerator() {
      V2AttributeCertificateInfoGenerator var1 = new V2AttributeCertificateInfoGenerator();
      this.acInfoGen = var1;
      X509ExtensionsGenerator var2 = new X509ExtensionsGenerator();
      this.extGenerator = var2;
   }

   public void addAttribute(X509Attribute var1) {
      V2AttributeCertificateInfoGenerator var2 = this.acInfoGen;
      Attribute var3 = Attribute.getInstance(var1.toASN1Object());
      var2.addAttribute(var3);
   }

   public void addExtension(String var1, boolean var2, ASN1Encodable var3) throws IOException {
      X509ExtensionsGenerator var4 = this.extGenerator;
      DERObjectIdentifier var5 = new DERObjectIdentifier(var1);
      var4.addExtension(var5, var2, (DEREncodable)var3);
   }

   public void addExtension(String var1, boolean var2, byte[] var3) {
      X509ExtensionsGenerator var4 = this.extGenerator;
      DERObjectIdentifier var5 = new DERObjectIdentifier(var1);
      var4.addExtension(var5, var2, var3);
   }

   public X509AttributeCertificate generate(PrivateKey var1, String var2) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, SignatureException, InvalidKeyException, NoSuchAlgorithmException {
      return this.generate(var1, var2, (SecureRandom)null);
   }

   public X509AttributeCertificate generate(PrivateKey var1, String var2, SecureRandom var3) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      if(!this.extGenerator.isEmpty()) {
         V2AttributeCertificateInfoGenerator var4 = this.acInfoGen;
         X509Extensions var5 = this.extGenerator.generate();
         var4.setExtensions(var5);
      }

      AttributeCertificateInfo var6 = this.acInfoGen.generateAttributeCertificateInfo();
      ASN1EncodableVector var7 = new ASN1EncodableVector();
      var7.add(var6);
      AlgorithmIdentifier var8 = this.sigAlgId;
      var7.add(var8);

      try {
         DERObjectIdentifier var9 = this.sigOID;
         String var10 = this.signatureAlgorithm;
         byte[] var14 = X509Util.calculateSignature(var9, var10, var2, var1, var3, var6);
         DERBitString var15 = new DERBitString(var14);
         var7.add(var15);
         DERSequence var16 = new DERSequence(var7);
         AttributeCertificate var17 = new AttributeCertificate(var16);
         X509V2AttributeCertificate var18 = new X509V2AttributeCertificate(var17);
         return var18;
      } catch (IOException var20) {
         throw new ExtCertificateEncodingException("constructed invalid certificate", var20);
      }
   }

   public X509AttributeCertificate generateCertificate(PrivateKey var1, String var2) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
      return this.generateCertificate(var1, var2, (SecureRandom)null);
   }

   public X509AttributeCertificate generateCertificate(PrivateKey var1, String var2, SecureRandom var3) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
      try {
         X509AttributeCertificate var4 = this.generate(var1, var2, var3);
         return var4;
      } catch (NoSuchProviderException var7) {
         throw var7;
      } catch (SignatureException var8) {
         throw var8;
      } catch (InvalidKeyException var9) {
         throw var9;
      } catch (GeneralSecurityException var10) {
         String var6 = "exception creating certificate: " + var10;
         throw new SecurityException(var6);
      }
   }

   public Iterator getSignatureAlgNames() {
      return X509Util.getAlgNames();
   }

   public void reset() {
      V2AttributeCertificateInfoGenerator var1 = new V2AttributeCertificateInfoGenerator();
      this.acInfoGen = var1;
      this.extGenerator.reset();
   }

   public void setHolder(AttributeCertificateHolder var1) {
      V2AttributeCertificateInfoGenerator var2 = this.acInfoGen;
      Holder var3 = var1.holder;
      var2.setHolder(var3);
   }

   public void setIssuer(AttributeCertificateIssuer var1) {
      V2AttributeCertificateInfoGenerator var2 = this.acInfoGen;
      AttCertIssuer var3 = AttCertIssuer.getInstance(var1.form);
      var2.setIssuer(var3);
   }

   public void setIssuerUniqueId(boolean[] var1) {
      throw new RuntimeException("not implemented (yet)");
   }

   public void setNotAfter(Date var1) {
      V2AttributeCertificateInfoGenerator var2 = this.acInfoGen;
      DERGeneralizedTime var3 = new DERGeneralizedTime(var1);
      var2.setEndDate(var3);
   }

   public void setNotBefore(Date var1) {
      V2AttributeCertificateInfoGenerator var2 = this.acInfoGen;
      DERGeneralizedTime var3 = new DERGeneralizedTime(var1);
      var2.setStartDate(var3);
   }

   public void setSerialNumber(BigInteger var1) {
      V2AttributeCertificateInfoGenerator var2 = this.acInfoGen;
      DERInteger var3 = new DERInteger(var1);
      var2.setSerialNumber(var3);
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
      V2AttributeCertificateInfoGenerator var4 = this.acInfoGen;
      AlgorithmIdentifier var5 = this.sigAlgId;
      var4.setSignature(var5);
   }
}
