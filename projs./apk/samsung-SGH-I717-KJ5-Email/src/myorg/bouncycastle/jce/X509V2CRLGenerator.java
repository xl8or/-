package myorg.bouncycastle.jce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.SimpleTimeZone;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.V2TBSCertListGenerator;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.provider.X509CRLObject;
import myorg.bouncycastle.util.Strings;

public class X509V2CRLGenerator {

   private static Hashtable algorithms = new Hashtable();
   private SimpleDateFormat dateF;
   private Vector extOrdering;
   private Hashtable extensions;
   private AlgorithmIdentifier sigAlgId;
   private DERObjectIdentifier sigOID;
   private String signatureAlgorithm;
   private V2TBSCertListGenerator tbsGen;
   private SimpleTimeZone tz;


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

   public X509V2CRLGenerator() {
      SimpleDateFormat var1 = new SimpleDateFormat("yyMMddHHmmss");
      this.dateF = var1;
      SimpleTimeZone var2 = new SimpleTimeZone(0, "Z");
      this.tz = var2;
      this.extensions = null;
      this.extOrdering = null;
      SimpleDateFormat var3 = this.dateF;
      SimpleTimeZone var4 = this.tz;
      var3.setTimeZone(var4);
      V2TBSCertListGenerator var5 = new V2TBSCertListGenerator();
      this.tbsGen = var5;
   }

   public void addCRLEntry(BigInteger var1, Date var2, int var3) {
      V2TBSCertListGenerator var4 = this.tbsGen;
      DERInteger var5 = new DERInteger(var1);
      StringBuilder var6 = new StringBuilder();
      String var7 = this.dateF.format(var2);
      String var8 = var6.append(var7).append("Z").toString();
      DERUTCTime var9 = new DERUTCTime(var8);
      var4.addCRLEntry(var5, var9, var3);
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
      Signature var5;
      label41: {
         Signature var4;
         try {
            var4 = Signature.getInstance(this.sigOID.getId(), var2);
         } catch (NoSuchAlgorithmException var34) {
            try {
               var4 = Signature.getInstance(this.signatureAlgorithm, var2);
            } catch (NoSuchAlgorithmException var33) {
               StringBuilder var22 = (new StringBuilder()).append("exception creating signature: ");
               String var23 = var33.toString();
               String var24 = var22.append(var23).toString();
               throw new SecurityException(var24);
            }

            var5 = var4;
            break label41;
         }

         var5 = var4;
      }

      if(var3 != null) {
         var5.initSign(var1, var3);
      } else {
         var5.initSign(var1);
      }

      if(this.extensions != null) {
         V2TBSCertListGenerator var6 = this.tbsGen;
         Vector var7 = this.extOrdering;
         Hashtable var8 = this.extensions;
         X509Extensions var9 = new X509Extensions(var7, var8);
         var6.setExtensions(var9);
      }

      TBSCertList var10 = this.tbsGen.generateTBSCertList();

      try {
         ByteArrayOutputStream var11 = new ByteArrayOutputStream();
         (new DEROutputStream(var11)).writeObject(var10);
         byte[] var12 = var11.toByteArray();
         var5.update(var12);
      } catch (Exception var32) {
         String var26 = "exception encoding TBS cert - " + var32;
         throw new SecurityException(var26);
      }

      ASN1EncodableVector var13 = new ASN1EncodableVector();
      var13.add(var10);
      AlgorithmIdentifier var14 = this.sigAlgId;
      var13.add(var14);
      byte[] var15 = var5.sign();
      DERBitString var16 = new DERBitString(var15);
      var13.add(var16);

      try {
         DERSequence var17 = new DERSequence(var13);
         CertificateList var18 = new CertificateList(var17);
         X509CRLObject var19 = new X509CRLObject(var18);
         return var19;
      } catch (CRLException var31) {
         StringBuilder var28 = (new StringBuilder()).append("attempt to create malformed CRL: ");
         String var29 = var31.getMessage();
         String var30 = var28.append(var29).toString();
         throw new IllegalStateException(var30);
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

   public void reset() {
      V2TBSCertListGenerator var1 = new V2TBSCertListGenerator();
      this.tbsGen = var1;
   }

   public void setIssuerDN(X509Name var1) {
      this.tbsGen.setIssuer(var1);
   }

   public void setNextUpdate(Date var1) {
      V2TBSCertListGenerator var2 = this.tbsGen;
      StringBuilder var3 = new StringBuilder();
      String var4 = this.dateF.format(var1);
      String var5 = var3.append(var4).append("Z").toString();
      DERUTCTime var6 = new DERUTCTime(var5);
      var2.setNextUpdate(var6);
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
         AlgorithmIdentifier var6 = new AlgorithmIdentifier(var5, (DEREncodable)null);
         this.sigAlgId = var6;
         V2TBSCertListGenerator var7 = this.tbsGen;
         AlgorithmIdentifier var8 = this.sigAlgId;
         var7.setSignature(var8);
      }
   }

   public void setThisUpdate(Date var1) {
      V2TBSCertListGenerator var2 = this.tbsGen;
      StringBuilder var3 = new StringBuilder();
      String var4 = this.dateF.format(var1);
      String var5 = var3.append(var4).append("Z").toString();
      DERUTCTime var6 = new DERUTCTime(var5);
      var2.setThisUpdate(var6);
   }
}
