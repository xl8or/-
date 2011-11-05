package myorg.bouncycastle.jce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.provider.X509CertificateObject;
import myorg.bouncycastle.util.Strings;

public class X509V3CertificateGenerator {

   private static Hashtable algorithms = new Hashtable();
   private Vector extOrdering = null;
   private Hashtable extensions = null;
   private AlgorithmIdentifier sigAlgId;
   private DERObjectIdentifier sigOID;
   private String signatureAlgorithm;
   private V3TBSCertificateGenerator tbsGen;


   static {
      Hashtable var0 = algorithms;
      DERObjectIdentifier var1 = new DERObjectIdentifier("1.2.840.113549.1.1.2");
      var0.put("MD2WITHRSAENCRYPTION", var1);
      Hashtable var3 = algorithms;
      DERObjectIdentifier var4 = new DERObjectIdentifier("1.2.840.113549.1.1.2");
      var3.put("MD2WITHRSA", var4);
      Hashtable var6 = algorithms;
      DERObjectIdentifier var7 = new DERObjectIdentifier("1.2.840.113549.1.1.4");
      var6.put("MD5WITHRSAENCRYPTION", var7);
      Hashtable var9 = algorithms;
      DERObjectIdentifier var10 = new DERObjectIdentifier("1.2.840.113549.1.1.4");
      var9.put("MD5WITHRSA", var10);
      Hashtable var12 = algorithms;
      DERObjectIdentifier var13 = new DERObjectIdentifier("1.2.840.113549.1.1.5");
      var12.put("SHA1WITHRSAENCRYPTION", var13);
      Hashtable var15 = algorithms;
      DERObjectIdentifier var16 = new DERObjectIdentifier("1.2.840.113549.1.1.5");
      var15.put("SHA1WITHRSA", var16);
      Hashtable var18 = algorithms;
      DERObjectIdentifier var19 = new DERObjectIdentifier("1.3.36.3.3.1.2");
      var18.put("RIPEMD160WITHRSAENCRYPTION", var19);
      Hashtable var21 = algorithms;
      DERObjectIdentifier var22 = new DERObjectIdentifier("1.3.36.3.3.1.2");
      var21.put("RIPEMD160WITHRSA", var22);
      Hashtable var24 = algorithms;
      DERObjectIdentifier var25 = new DERObjectIdentifier("1.2.840.10040.4.3");
      var24.put("SHA1WITHDSA", var25);
      Hashtable var27 = algorithms;
      DERObjectIdentifier var28 = new DERObjectIdentifier("1.2.840.10040.4.3");
      var27.put("DSAWITHSHA1", var28);
      Hashtable var30 = algorithms;
      DERObjectIdentifier var31 = new DERObjectIdentifier("1.2.840.10045.4.1");
      var30.put("SHA1WITHECDSA", var31);
      Hashtable var33 = algorithms;
      DERObjectIdentifier var34 = new DERObjectIdentifier("1.2.840.10045.4.1");
      var33.put("ECDSAWITHSHA1", var34);
   }

   public X509V3CertificateGenerator() {
      V3TBSCertificateGenerator var1 = new V3TBSCertificateGenerator();
      this.tbsGen = var1;
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
      if(this.extensions == null) {
         Hashtable var4 = new Hashtable();
         this.extensions = var4;
         Vector var5 = new Vector();
         this.extOrdering = var5;
      }

      ByteArrayOutputStream var6 = new ByteArrayOutputStream();
      DEROutputStream var7 = new DEROutputStream(var6);

      try {
         var7.writeObject(var3);
      } catch (IOException var11) {
         String var10 = "error encoding value: " + var11;
         throw new IllegalArgumentException(var10);
      }

      byte[] var8 = var6.toByteArray();
      this.addExtension(var1, var2, var8);
   }

   public void addExtension(DERObjectIdentifier var1, boolean var2, byte[] var3) {
      if(this.extensions == null) {
         Hashtable var4 = new Hashtable();
         this.extensions = var4;
         Vector var5 = new Vector();
         this.extOrdering = var5;
      }

      Hashtable var6 = this.extensions;
      DEROctetString var7 = new DEROctetString(var3);
      X509Extension var8 = new X509Extension(var2, var7);
      var6.put(var1, var8);
      this.extOrdering.addElement(var1);
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
      if(this.sigOID == null) {
         throw new IllegalStateException("no signature algorithm specified");
      } else {
         Signature var5;
         label36: {
            Signature var4;
            try {
               var4 = Signature.getInstance(this.sigOID.getId(), var2);
            } catch (NoSuchAlgorithmException var29) {
               try {
                  var4 = Signature.getInstance(this.signatureAlgorithm, var2);
               } catch (NoSuchAlgorithmException var28) {
                  StringBuilder var22 = (new StringBuilder()).append("exception creating signature: ");
                  String var23 = var28.toString();
                  String var24 = var22.append(var23).toString();
                  throw new SecurityException(var24);
               }

               var5 = var4;
               break label36;
            }

            var5 = var4;
         }

         if(var3 != null) {
            var5.initSign(var1, var3);
         } else {
            var5.initSign(var1);
         }

         if(this.extensions != null) {
            V3TBSCertificateGenerator var6 = this.tbsGen;
            Vector var7 = this.extOrdering;
            Hashtable var8 = this.extensions;
            X509Extensions var9 = new X509Extensions(var7, var8);
            var6.setExtensions(var9);
         }

         TBSCertificateStructure var10 = this.tbsGen.generateTBSCertificate();

         try {
            ByteArrayOutputStream var11 = new ByteArrayOutputStream();
            (new DEROutputStream(var11)).writeObject(var10);
            byte[] var12 = var11.toByteArray();
            var5.update(var12);
            ASN1EncodableVector var13 = new ASN1EncodableVector();
            var13.add(var10);
            AlgorithmIdentifier var14 = this.sigAlgId;
            var13.add(var14);
            byte[] var15 = var5.sign();
            DERBitString var16 = new DERBitString(var15);
            var13.add(var16);
            DERSequence var17 = new DERSequence(var13);
            X509CertificateStructure var18 = new X509CertificateStructure(var17);
            X509CertificateObject var19 = new X509CertificateObject(var18);
            return var19;
         } catch (Exception var27) {
            String var26 = "exception encoding TBS cert - " + var27;
            throw new SecurityException(var26);
         }
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

   public void reset() {
      V3TBSCertificateGenerator var1 = new V3TBSCertificateGenerator();
      this.tbsGen = var1;
      this.extensions = null;
      this.extOrdering = null;
   }

   public void setIssuerDN(X509Name var1) {
      this.tbsGen.setIssuer(var1);
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

   public void setPublicKey(PublicKey var1) {
      try {
         V3TBSCertificateGenerator var2 = this.tbsGen;
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
      V3TBSCertificateGenerator var2 = this.tbsGen;
      DERInteger var3 = new DERInteger(var1);
      var2.setSerialNumber(var3);
   }

   public void setSignatureAlgorithm(String var1) {
      this.signatureAlgorithm = var1;
      Hashtable var2 = algorithms;
      String var3 = Strings.toUpperCase(var1);
      DERObjectIdentifier var4 = (DERObjectIdentifier)var2.get(var3);
      this.sigOID = var4;
      if(this.sigOID == null) {
         throw new IllegalArgumentException("Unknown signature type requested");
      } else {
         DERObjectIdentifier var5 = this.sigOID;
         DERNull var6 = new DERNull();
         AlgorithmIdentifier var7 = new AlgorithmIdentifier(var5, var6);
         this.sigAlgId = var7;
         V3TBSCertificateGenerator var8 = this.tbsGen;
         AlgorithmIdentifier var9 = this.sigAlgId;
         var8.setSignature(var9);
      }
   }

   public void setSubjectDN(X509Name var1) {
      this.tbsGen.setSubject(var1);
   }
}
