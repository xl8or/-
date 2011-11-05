package myorg.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.Collection;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.SignedData;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.jce.provider.PEMUtil;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.X509CertificateObject;
import myorg.bouncycastle.x509.X509StreamParserSpi;
import myorg.bouncycastle.x509.util.StreamParsingException;

public class X509CertParser extends X509StreamParserSpi {

   private static final PEMUtil PEM_PARSER = new PEMUtil("CERTIFICATE");
   private InputStream currentStream = null;
   private ASN1Set sData = null;
   private int sDataObjectCount = 0;


   public X509CertParser() {}

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

   private Certificate readDERCertificate(InputStream var1) throws IOException, CertificateParsingException {
      int var2 = ProviderUtil.getReadLimit(var1);
      ASN1Sequence var3 = (ASN1Sequence)(new ASN1InputStream(var1, var2)).readObject();
      Object var8;
      if(var3.size() > 1 && var3.getObjectAt(0) instanceof DERObjectIdentifier) {
         DEREncodable var4 = var3.getObjectAt(0);
         DERObjectIdentifier var5 = PKCSObjectIdentifiers.signedData;
         if(var4.equals(var5)) {
            ASN1Sequence var6 = ASN1Sequence.getInstance((ASN1TaggedObject)var3.getObjectAt(1), (boolean)1);
            ASN1Set var7 = (new SignedData(var6)).getCertificates();
            this.sData = var7;
            var8 = this.getCertificate();
            return (Certificate)var8;
         }
      }

      X509CertificateStructure var9 = X509CertificateStructure.getInstance(var3);
      var8 = new X509CertificateObject(var9);
      return (Certificate)var8;
   }

   private Certificate readPEMCertificate(InputStream var1) throws IOException, CertificateParsingException {
      ASN1Sequence var2 = PEM_PARSER.readPEMObject(var1);
      X509CertificateObject var4;
      if(var2 != null) {
         X509CertificateStructure var3 = X509CertificateStructure.getInstance(var2);
         var4 = new X509CertificateObject(var3);
      } else {
         var4 = null;
      }

      return var4;
   }

   public void engineInit(InputStream var1) {
      this.currentStream = var1;
      this.sData = null;
      this.sDataObjectCount = 0;
      if(!this.currentStream.markSupported()) {
         InputStream var2 = this.currentStream;
         BufferedInputStream var3 = new BufferedInputStream(var2);
         this.currentStream = var3;
      }
   }

   public Object engineRead() throws StreamParsingException {
      // $FF: Couldn't be decompiled
   }

   public Collection engineReadAll() throws StreamParsingException {
      ArrayList var1 = new ArrayList();

      while(true) {
         Certificate var2 = (Certificate)this.engineRead();
         if(var2 == null) {
            return var1;
         }

         var1.add(var2);
      }
   }
}
