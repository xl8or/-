package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509Name;

public class CertificationRequestInfo extends ASN1Encodable {

   ASN1Set attributes;
   X509Name subject;
   SubjectPublicKeyInfo subjectPKInfo;
   DERInteger version;


   public CertificationRequestInfo(ASN1Sequence var1) {
      DERInteger var2 = new DERInteger(0);
      this.version = var2;
      this.attributes = null;
      DERInteger var3 = (DERInteger)var1.getObjectAt(0);
      this.version = var3;
      X509Name var4 = X509Name.getInstance(var1.getObjectAt(1));
      this.subject = var4;
      SubjectPublicKeyInfo var5 = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(2));
      this.subjectPKInfo = var5;
      if(var1.size() > 3) {
         ASN1Set var6 = ASN1Set.getInstance((DERTaggedObject)var1.getObjectAt(3), (boolean)0);
         this.attributes = var6;
      }

      if(this.subject == null || this.version == null || this.subjectPKInfo == null) {
         throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
      }
   }

   public CertificationRequestInfo(X509Name var1, SubjectPublicKeyInfo var2, ASN1Set var3) {
      DERInteger var4 = new DERInteger(0);
      this.version = var4;
      this.attributes = null;
      this.subject = var1;
      this.subjectPKInfo = var2;
      this.attributes = var3;
      if(var1 == null || this.version == null || this.subjectPKInfo == null) {
         throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
      }
   }

   public static CertificationRequestInfo getInstance(Object var0) {
      CertificationRequestInfo var1;
      if(var0 instanceof CertificationRequestInfo) {
         var1 = (CertificationRequestInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertificationRequestInfo(var2);
      }

      return var1;
   }

   public ASN1Set getAttributes() {
      return this.attributes;
   }

   public X509Name getSubject() {
      return this.subject;
   }

   public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
      return this.subjectPKInfo;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      X509Name var3 = this.subject;
      var1.add(var3);
      SubjectPublicKeyInfo var4 = this.subjectPKInfo;
      var1.add(var4);
      if(this.attributes != null) {
         ASN1Set var5 = this.attributes;
         DERTaggedObject var6 = new DERTaggedObject((boolean)0, 0, var5);
         var1.add(var6);
      }

      return new DERSequence(var1);
   }
}
