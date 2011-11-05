package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Name;

public class V1TBSCertificateGenerator {

   Time endDate;
   X509Name issuer;
   DERInteger serialNumber;
   AlgorithmIdentifier signature;
   Time startDate;
   X509Name subject;
   SubjectPublicKeyInfo subjectPublicKeyInfo;
   DERTaggedObject version;


   public V1TBSCertificateGenerator() {
      DERInteger var1 = new DERInteger(0);
      DERTaggedObject var2 = new DERTaggedObject(0, var1);
      this.version = var2;
   }

   public TBSCertificateStructure generateTBSCertificate() {
      if(this.serialNumber != null && this.signature != null && this.issuer != null && this.startDate != null && this.endDate != null && this.subject != null && this.subjectPublicKeyInfo != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         DERInteger var2 = this.serialNumber;
         var1.add(var2);
         AlgorithmIdentifier var3 = this.signature;
         var1.add(var3);
         X509Name var4 = this.issuer;
         var1.add(var4);
         ASN1EncodableVector var5 = new ASN1EncodableVector();
         Time var6 = this.startDate;
         var5.add(var6);
         Time var7 = this.endDate;
         var5.add(var7);
         DERSequence var8 = new DERSequence(var5);
         var1.add(var8);
         X509Name var9 = this.subject;
         var1.add(var9);
         SubjectPublicKeyInfo var10 = this.subjectPublicKeyInfo;
         var1.add(var10);
         DERSequence var11 = new DERSequence(var1);
         return new TBSCertificateStructure(var11);
      } else {
         throw new IllegalStateException("not all mandatory fields set in V1 TBScertificate generator");
      }
   }

   public void setEndDate(DERUTCTime var1) {
      Time var2 = new Time(var1);
      this.endDate = var2;
   }

   public void setEndDate(Time var1) {
      this.endDate = var1;
   }

   public void setIssuer(X509Name var1) {
      this.issuer = var1;
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
}
