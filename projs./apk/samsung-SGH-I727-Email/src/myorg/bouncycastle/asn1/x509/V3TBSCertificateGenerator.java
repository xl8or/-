package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;

public class V3TBSCertificateGenerator {

   private boolean altNamePresentAndCritical;
   Time endDate;
   X509Extensions extensions;
   X509Name issuer;
   private DERBitString issuerUniqueID;
   DERInteger serialNumber;
   AlgorithmIdentifier signature;
   Time startDate;
   X509Name subject;
   SubjectPublicKeyInfo subjectPublicKeyInfo;
   private DERBitString subjectUniqueID;
   DERTaggedObject version;


   public V3TBSCertificateGenerator() {
      DERInteger var1 = new DERInteger(2);
      DERTaggedObject var2 = new DERTaggedObject(0, var1);
      this.version = var2;
   }

   public TBSCertificateStructure generateTBSCertificate() {
      if(this.serialNumber != null && this.signature != null && this.issuer != null && this.startDate != null && this.endDate != null && (this.subject != null || this.altNamePresentAndCritical) && this.subjectPublicKeyInfo != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         DERTaggedObject var2 = this.version;
         var1.add(var2);
         DERInteger var3 = this.serialNumber;
         var1.add(var3);
         AlgorithmIdentifier var4 = this.signature;
         var1.add(var4);
         X509Name var5 = this.issuer;
         var1.add(var5);
         ASN1EncodableVector var6 = new ASN1EncodableVector();
         Time var7 = this.startDate;
         var6.add(var7);
         Time var8 = this.endDate;
         var6.add(var8);
         DERSequence var9 = new DERSequence(var6);
         var1.add(var9);
         if(this.subject != null) {
            X509Name var10 = this.subject;
            var1.add(var10);
         } else {
            DERSequence var19 = new DERSequence();
            var1.add(var19);
         }

         SubjectPublicKeyInfo var11 = this.subjectPublicKeyInfo;
         var1.add(var11);
         if(this.issuerUniqueID != null) {
            DERBitString var12 = this.issuerUniqueID;
            DERTaggedObject var13 = new DERTaggedObject((boolean)0, 1, var12);
            var1.add(var13);
         }

         if(this.subjectUniqueID != null) {
            DERBitString var14 = this.subjectUniqueID;
            DERTaggedObject var15 = new DERTaggedObject((boolean)0, 2, var14);
            var1.add(var15);
         }

         if(this.extensions != null) {
            X509Extensions var16 = this.extensions;
            DERTaggedObject var17 = new DERTaggedObject(3, var16);
            var1.add(var17);
         }

         DERSequence var18 = new DERSequence(var1);
         return new TBSCertificateStructure(var18);
      } else {
         throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
      }
   }

   public void setEndDate(DERUTCTime var1) {
      Time var2 = new Time(var1);
      this.endDate = var2;
   }

   public void setEndDate(Time var1) {
      this.endDate = var1;
   }

   public void setExtensions(X509Extensions var1) {
      this.extensions = var1;
      if(var1 != null) {
         DERObjectIdentifier var2 = X509Extensions.SubjectAlternativeName;
         X509Extension var3 = var1.getExtension(var2);
         if(var3 != null) {
            if(var3.isCritical()) {
               this.altNamePresentAndCritical = (boolean)1;
            }
         }
      }
   }

   public void setIssuer(X509Name var1) {
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

   public void setStartDate(DERUTCTime var1) {
      Time var2 = new Time(var1);
      this.startDate = var2;
   }

   public void setStartDate(Time var1) {
      this.startDate = var1;
   }

   public void setSubject(X509Name var1) {
      this.subject = var1;
   }

   public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo var1) {
      this.subjectPublicKeyInfo = var1;
   }

   public void setSubjectUniqueID(DERBitString var1) {
      this.subjectUniqueID = var1;
   }
}
