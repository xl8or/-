package myorg.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
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
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.jce.provider.PEMUtil;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.X509CRLObject;
import myorg.bouncycastle.x509.X509StreamParserSpi;
import myorg.bouncycastle.x509.util.StreamParsingException;

public class X509CRLParser extends X509StreamParserSpi {

   private static final PEMUtil PEM_PARSER = new PEMUtil("CRL");
   private InputStream currentStream = null;
   private ASN1Set sData = null;
   private int sDataObjectCount = 0;


   public X509CRLParser() {}

   private CRL getCRL() throws CRLException {
      X509CRLObject var3;
      if(this.sData != null) {
         int var1 = this.sDataObjectCount;
         int var2 = this.sData.size();
         if(var1 < var2) {
            ASN1Set var4 = this.sData;
            int var5 = this.sDataObjectCount;
            int var6 = var5 + 1;
            this.sDataObjectCount = var6;
            CertificateList var7 = CertificateList.getInstance(var4.getObjectAt(var5));
            var3 = new X509CRLObject(var7);
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   private CRL readDERCRL(InputStream var1) throws IOException, CRLException {
      int var2 = ProviderUtil.getReadLimit(var1);
      ASN1Sequence var3 = (ASN1Sequence)(new ASN1InputStream(var1, var2)).readObject();
      Object var8;
      if(var3.size() > 1 && var3.getObjectAt(0) instanceof DERObjectIdentifier) {
         DEREncodable var4 = var3.getObjectAt(0);
         DERObjectIdentifier var5 = PKCSObjectIdentifiers.signedData;
         if(var4.equals(var5)) {
            ASN1Sequence var6 = ASN1Sequence.getInstance((ASN1TaggedObject)var3.getObjectAt(1), (boolean)1);
            ASN1Set var7 = (new SignedData(var6)).getCRLs();
            this.sData = var7;
            var8 = this.getCRL();
            return (CRL)var8;
         }
      }

      CertificateList var9 = CertificateList.getInstance(var3);
      var8 = new X509CRLObject(var9);
      return (CRL)var8;
   }

   private CRL readPEMCRL(InputStream var1) throws IOException, CRLException {
      ASN1Sequence var2 = PEM_PARSER.readPEMObject(var1);
      X509CRLObject var4;
      if(var2 != null) {
         CertificateList var3 = CertificateList.getInstance(var2);
         var4 = new X509CRLObject(var3);
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
         CRL var2 = (CRL)this.engineRead();
         if(var2 == null) {
            return var1;
         }

         var1.add(var2);
      }
   }
}
