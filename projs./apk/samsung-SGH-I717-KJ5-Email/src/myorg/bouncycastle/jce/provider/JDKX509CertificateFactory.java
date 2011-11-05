package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.SignedData;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.jce.provider.PEMUtil;
import myorg.bouncycastle.jce.provider.PKIXCertPath;
import myorg.bouncycastle.jce.provider.X509CRLObject;
import myorg.bouncycastle.jce.provider.X509CertificateObject;

public class JDKX509CertificateFactory extends CertificateFactorySpi {

   private static final PEMUtil PEM_CERT_PARSER = new PEMUtil("CERTIFICATE");
   private static final PEMUtil PEM_CRL_PARSER = new PEMUtil("CRL");
   private InputStream currentCrlStream = null;
   private InputStream currentStream = null;
   private ASN1Set sCrlData = null;
   private int sCrlDataObjectCount = 0;
   private ASN1Set sData = null;
   private int sDataObjectCount = 0;


   public JDKX509CertificateFactory() {}

   private CRL getCRL() throws CRLException {
      CRL var3;
      if(this.sCrlData != null) {
         int var1 = this.sCrlDataObjectCount;
         int var2 = this.sCrlData.size();
         if(var1 < var2) {
            ASN1Set var4 = this.sCrlData;
            int var5 = this.sCrlDataObjectCount;
            int var6 = var5 + 1;
            this.sCrlDataObjectCount = var6;
            CertificateList var7 = CertificateList.getInstance(var4.getObjectAt(var5));
            var3 = this.createCRL(var7);
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   private Certificate getCertificate() throws CertificateParsingException {
      X509CertificateObject var8;
      if(this.sData != null) {
         while(true) {
            int var1 = this.sDataObjectCount;
            int var2 = this.sData.size();
            if(var1 >= var2) {
               break;
            }

            ASN1Set var3 = this.sData;
            int var4 = this.sDataObjectCount;
            int var5 = var4 + 1;
            this.sDataObjectCount = var5;
            DEREncodable var6 = var3.getObjectAt(var4);
            if(var6 instanceof ASN1Sequence) {
               X509CertificateStructure var7 = X509CertificateStructure.getInstance(var6);
               var8 = new X509CertificateObject(var7);
               return var8;
            }
         }
      }

      var8 = null;
      return var8;
   }

   private CRL readDERCRL(ASN1InputStream var1) throws IOException, CRLException {
      ASN1Sequence var2 = (ASN1Sequence)var1.readObject();
      CRL var7;
      if(var2 != null && var2.size() > 1 && var2.getObjectAt(0) instanceof DERObjectIdentifier) {
         DEREncodable var3 = var2.getObjectAt(0);
         DERObjectIdentifier var4 = PKCSObjectIdentifiers.signedData;
         if(var3.equals(var4)) {
            ASN1Sequence var5 = ASN1Sequence.getInstance((ASN1TaggedObject)var2.getObjectAt(1), (boolean)1);
            ASN1Set var6 = (new SignedData(var5)).getCRLs();
            this.sCrlData = var6;
            var7 = this.getCRL();
            return var7;
         }
      }

      CertificateList var8 = CertificateList.getInstance(var2);
      var7 = this.createCRL(var8);
      return var7;
   }

   private Certificate readDERCertificate(ASN1InputStream var1) throws IOException, CertificateParsingException {
      ASN1Sequence var2 = (ASN1Sequence)var1.readObject();
      Object var7;
      if(var2 != null && var2.size() > 1 && var2.getObjectAt(0) instanceof DERObjectIdentifier) {
         DEREncodable var3 = var2.getObjectAt(0);
         DERObjectIdentifier var4 = PKCSObjectIdentifiers.signedData;
         if(var3.equals(var4)) {
            ASN1Sequence var5 = ASN1Sequence.getInstance((ASN1TaggedObject)var2.getObjectAt(1), (boolean)1);
            ASN1Set var6 = (new SignedData(var5)).getCertificates();
            this.sData = var6;
            var7 = this.getCertificate();
            return (Certificate)var7;
         }
      }

      X509CertificateStructure var8 = X509CertificateStructure.getInstance(var2);
      var7 = new X509CertificateObject(var8);
      return (Certificate)var7;
   }

   private CRL readPEMCRL(InputStream var1) throws IOException, CRLException {
      ASN1Sequence var2 = PEM_CRL_PARSER.readPEMObject(var1);
      CRL var4;
      if(var2 != null) {
         CertificateList var3 = CertificateList.getInstance(var2);
         var4 = this.createCRL(var3);
      } else {
         var4 = null;
      }

      return var4;
   }

   private Certificate readPEMCertificate(InputStream var1) throws IOException, CertificateParsingException {
      ASN1Sequence var2 = PEM_CERT_PARSER.readPEMObject(var1);
      X509CertificateObject var4;
      if(var2 != null) {
         X509CertificateStructure var3 = X509CertificateStructure.getInstance(var2);
         var4 = new X509CertificateObject(var3);
      } else {
         var4 = null;
      }

      return var4;
   }

   protected CRL createCRL(CertificateList var1) throws CRLException {
      return new X509CRLObject(var1);
   }

   public CRL engineGenerateCRL(InputStream param1) throws CRLException {
      // $FF: Couldn't be decompiled
   }

   public Collection engineGenerateCRLs(InputStream var1) throws CRLException {
      ArrayList var2 = new ArrayList();

      while(true) {
         CRL var3 = this.engineGenerateCRL(var1);
         if(var3 == null) {
            return var2;
         }

         var2.add(var3);
      }
   }

   public CertPath engineGenerateCertPath(InputStream var1) throws CertificateException {
      return this.engineGenerateCertPath(var1, "PkiPath");
   }

   public CertPath engineGenerateCertPath(InputStream var1, String var2) throws CertificateException {
      return new PKIXCertPath(var1, var2);
   }

   public CertPath engineGenerateCertPath(List var1) throws CertificateException {
      Iterator var2 = var1.iterator();

      Object var3;
      do {
         if(!var2.hasNext()) {
            return new PKIXCertPath(var1);
         }

         var3 = var2.next();
      } while(var3 == null || var3 instanceof X509Certificate);

      StringBuilder var4 = (new StringBuilder()).append("list contains non X509Certificate object while creating CertPath\n");
      String var5 = var3.toString();
      String var6 = var4.append(var5).toString();
      throw new CertificateException(var6);
   }

   public Certificate engineGenerateCertificate(InputStream param1) throws CertificateException {
      // $FF: Couldn't be decompiled
   }

   public Collection engineGenerateCertificates(InputStream var1) throws CertificateException {
      ArrayList var2 = new ArrayList();

      while(true) {
         Certificate var3 = this.engineGenerateCertificate(var1);
         if(var3 == null) {
            return var2;
         }

         var2.add(var3);
      }
   }

   public Iterator engineGetCertPathEncodings() {
      return PKIXCertPath.certPathEncodings.iterator();
   }
}
