package myorg.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.AttCertIssuer;
import myorg.bouncycastle.asn1.x509.AttributeCertificate;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.x509.AttributeCertificateHolder;
import myorg.bouncycastle.x509.AttributeCertificateIssuer;
import myorg.bouncycastle.x509.X509Attribute;
import myorg.bouncycastle.x509.X509AttributeCertificate;

public class X509V2AttributeCertificate implements X509AttributeCertificate {

   private AttributeCertificate cert;
   private Date notAfter;
   private Date notBefore;


   public X509V2AttributeCertificate(InputStream var1) throws IOException {
      AttributeCertificate var2 = AttributeCertificate.getInstance((new ASN1InputStream(var1)).readObject());
      this(var2);
   }

   X509V2AttributeCertificate(AttributeCertificate var1) throws IOException {
      this.cert = var1;

      try {
         Date var2 = var1.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime().getDate();
         this.notAfter = var2;
         Date var3 = var1.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime().getDate();
         this.notBefore = var3;
      } catch (ParseException var5) {
         throw new IOException("invalid data structure in certificate!");
      }
   }

   public X509V2AttributeCertificate(byte[] var1) throws IOException {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      this((InputStream)var2);
   }

   private Set getExtensionOIDs(boolean var1) {
      X509Extensions var2 = this.cert.getAcinfo().getExtensions();
      HashSet var8;
      if(var2 != null) {
         HashSet var3 = new HashSet();
         Enumeration var4 = var2.oids();

         while(var4.hasMoreElements()) {
            DERObjectIdentifier var5 = (DERObjectIdentifier)var4.nextElement();
            if(var2.getExtension(var5).isCritical() == var1) {
               String var6 = var5.getId();
               var3.add(var6);
            }
         }

         var8 = var3;
      } else {
         var8 = null;
      }

      return var8;
   }

   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
      Date var1 = new Date();
      this.checkValidity(var1);
   }

   public void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException {
      Date var2 = this.getNotAfter();
      if(var1.after(var2)) {
         StringBuilder var3 = (new StringBuilder()).append("certificate expired on ");
         Date var4 = this.getNotAfter();
         String var5 = var3.append(var4).toString();
         throw new CertificateExpiredException(var5);
      } else {
         Date var6 = this.getNotBefore();
         if(var1.before(var6)) {
            StringBuilder var7 = (new StringBuilder()).append("certificate not valid till ");
            Date var8 = this.getNotBefore();
            String var9 = var7.append(var8).toString();
            throw new CertificateNotYetValidException(var9);
         }
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof X509AttributeCertificate)) {
         var2 = false;
      } else {
         X509AttributeCertificate var3 = (X509AttributeCertificate)var1;

         boolean var6;
         try {
            byte[] var4 = this.getEncoded();
            byte[] var5 = var3.getEncoded();
            var6 = Arrays.areEqual(var4, var5);
         } catch (IOException var8) {
            var2 = false;
            return var2;
         }

         var2 = var6;
      }

      return var2;
   }

   public X509Attribute[] getAttributes() {
      ASN1Sequence var1 = this.cert.getAcinfo().getAttributes();
      X509Attribute[] var2 = new X509Attribute[var1.size()];
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 == var4) {
            return var2;
         }

         ASN1Encodable var5 = (ASN1Encodable)var1.getObjectAt(var3);
         X509Attribute var6 = new X509Attribute(var5);
         var2[var3] = var6;
         ++var3;
      }
   }

   public X509Attribute[] getAttributes(String var1) {
      ASN1Sequence var2 = this.cert.getAcinfo().getAttributes();
      ArrayList var3 = new ArrayList();
      int var4 = 0;

      while(true) {
         int var5 = var2.size();
         if(var4 == var5) {
            X509Attribute[] var9;
            if(var3.size() == 0) {
               var9 = null;
            } else {
               X509Attribute[] var10 = new X509Attribute[var3.size()];
               var9 = (X509Attribute[])((X509Attribute[])var3.toArray(var10));
            }

            return var9;
         }

         ASN1Encodable var6 = (ASN1Encodable)var2.getObjectAt(var4);
         X509Attribute var7 = new X509Attribute(var6);
         if(var7.getOID().equals(var1)) {
            var3.add(var7);
         }

         ++var4;
      }
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getEncoded() throws IOException {
      return this.cert.getEncoded();
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.cert.getAcinfo().getExtensions();
      byte[] var6;
      if(var2 != null) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         X509Extension var4 = var2.getExtension(var3);
         if(var4 != null) {
            byte[] var5;
            try {
               var5 = var4.getValue().getEncoded("DER");
            } catch (Exception var11) {
               StringBuilder var8 = (new StringBuilder()).append("error encoding ");
               String var9 = var11.toString();
               String var10 = var8.append(var9).toString();
               throw new RuntimeException(var10);
            }

            var6 = var5;
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   public AttributeCertificateHolder getHolder() {
      ASN1Sequence var1 = (ASN1Sequence)this.cert.getAcinfo().getHolder().toASN1Object();
      return new AttributeCertificateHolder(var1);
   }

   public AttributeCertificateIssuer getIssuer() {
      AttCertIssuer var1 = this.cert.getAcinfo().getIssuer();
      return new AttributeCertificateIssuer(var1);
   }

   public boolean[] getIssuerUniqueID() {
      DERBitString var1 = this.cert.getAcinfo().getIssuerUniqueID();
      boolean[] var13;
      if(var1 != null) {
         byte[] var2 = var1.getBytes();
         int var3 = var2.length * 8;
         int var4 = var1.getPadBits();
         boolean[] var5 = new boolean[var3 - var4];
         int var6 = 0;

         while(true) {
            int var7 = var5.length;
            if(var6 == var7) {
               var13 = var5;
               break;
            }

            int var8 = var6 / 8;
            byte var9 = var2[var8];
            int var10 = var6 % 8;
            int var11 = 128 >>> var10;
            boolean var12;
            if((var9 & var11) != 0) {
               var12 = true;
            } else {
               var12 = false;
            }

            var5[var6] = var12;
            ++var6;
         }
      } else {
         var13 = null;
      }

      return var13;
   }

   public Set getNonCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)0);
   }

   public Date getNotAfter() {
      return this.notAfter;
   }

   public Date getNotBefore() {
      return this.notBefore;
   }

   public BigInteger getSerialNumber() {
      return this.cert.getAcinfo().getSerialNumber().getValue();
   }

   public byte[] getSignature() {
      return this.cert.getSignatureValue().getBytes();
   }

   public int getVersion() {
      return this.cert.getAcinfo().getVersion().getValue().intValue() + 1;
   }

   public boolean hasUnsupportedCriticalExtension() {
      Set var1 = this.getCriticalExtensionOIDs();
      boolean var2;
      if(var1 != null && !var1.isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      int var1;
      int var2;
      try {
         var1 = Arrays.hashCode(this.getEncoded());
      } catch (IOException var4) {
         var2 = 0;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public final void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      AlgorithmIdentifier var3 = this.cert.getSignatureAlgorithm();
      AlgorithmIdentifier var4 = this.cert.getAcinfo().getSignature();
      if(!var3.equals(var4)) {
         throw new CertificateException("Signature algorithm in certificate info not same as outer certificate");
      } else {
         Signature var5 = Signature.getInstance(this.cert.getSignatureAlgorithm().getObjectId().getId(), var2);
         var5.initVerify(var1);

         try {
            byte[] var6 = this.cert.getAcinfo().getEncoded();
            var5.update(var6);
         } catch (IOException var9) {
            throw new SignatureException("Exception encoding certificate info object");
         }

         byte[] var7 = this.getSignature();
         if(!var5.verify(var7)) {
            throw new InvalidKeyException("Public key presented not for certificate signature");
         }
      }
   }
}
