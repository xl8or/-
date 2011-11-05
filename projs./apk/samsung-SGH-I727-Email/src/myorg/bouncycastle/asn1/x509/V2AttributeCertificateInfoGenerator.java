package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.AttCertIssuer;
import myorg.bouncycastle.asn1.x509.AttCertValidityPeriod;
import myorg.bouncycastle.asn1.x509.Attribute;
import myorg.bouncycastle.asn1.x509.AttributeCertificateInfo;
import myorg.bouncycastle.asn1.x509.Holder;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class V2AttributeCertificateInfoGenerator {

   private ASN1EncodableVector attributes;
   private DERGeneralizedTime endDate;
   private X509Extensions extensions;
   private Holder holder;
   private AttCertIssuer issuer;
   private DERBitString issuerUniqueID;
   private DERInteger serialNumber;
   private AlgorithmIdentifier signature;
   private DERGeneralizedTime startDate;
   private DERInteger version;


   public V2AttributeCertificateInfoGenerator() {
      DERInteger var1 = new DERInteger(1);
      this.version = var1;
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      this.attributes = var2;
   }

   public void addAttribute(String var1, ASN1Encodable var2) {
      ASN1EncodableVector var3 = this.attributes;
      DERObjectIdentifier var4 = new DERObjectIdentifier(var1);
      DERSet var5 = new DERSet(var2);
      Attribute var6 = new Attribute(var4, var5);
      var3.add(var6);
   }

   public void addAttribute(Attribute var1) {
      this.attributes.add(var1);
   }

   public AttributeCertificateInfo generateAttributeCertificateInfo() {
      if(this.serialNumber != null && this.signature != null && this.issuer != null && this.startDate != null && this.endDate != null && this.holder != null && this.attributes != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         DERInteger var2 = this.version;
         var1.add(var2);
         Holder var3 = this.holder;
         var1.add(var3);
         AttCertIssuer var4 = this.issuer;
         var1.add(var4);
         AlgorithmIdentifier var5 = this.signature;
         var1.add(var5);
         DERInteger var6 = this.serialNumber;
         var1.add(var6);
         DERGeneralizedTime var7 = this.startDate;
         DERGeneralizedTime var8 = this.endDate;
         AttCertValidityPeriod var9 = new AttCertValidityPeriod(var7, var8);
         var1.add(var9);
         ASN1EncodableVector var10 = this.attributes;
         DERSequence var11 = new DERSequence(var10);
         var1.add(var11);
         if(this.issuerUniqueID != null) {
            DERBitString var12 = this.issuerUniqueID;
            var1.add(var12);
         }

         if(this.extensions != null) {
            X509Extensions var13 = this.extensions;
            var1.add(var13);
         }

         DERSequence var14 = new DERSequence(var1);
         return new AttributeCertificateInfo(var14);
      } else {
         throw new IllegalStateException("not all mandatory fields set in V2 AttributeCertificateInfo generator");
      }
   }

   public void setEndDate(DERGeneralizedTime var1) {
      this.endDate = var1;
   }

   public void setExtensions(X509Extensions var1) {
      this.extensions = var1;
   }

   public void setHolder(Holder var1) {
      this.holder = var1;
   }

   public void setIssuer(AttCertIssuer var1) {
      this.issuer = var1;
   }

   public void setIssuerUniqueID(DERBitString var1) {
      this.issuerUniqueID = var1;
   }

   public void setSerialNumber(DERInteger var1) {
      this.serialNumber = var1;
   }

   public void setSignature(AlgorithmIdentifier var1) {
      this.signature = var1;
   }

   public void setStartDate(DERGeneralizedTime var1) {
      this.startDate = var1;
   }
}
