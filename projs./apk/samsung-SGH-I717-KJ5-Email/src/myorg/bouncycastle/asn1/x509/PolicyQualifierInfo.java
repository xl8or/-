package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.PolicyQualifierId;

public class PolicyQualifierInfo extends ASN1Encodable {

   private DERObjectIdentifier policyQualifierId;
   private DEREncodable qualifier;


   public PolicyQualifierInfo(String var1) {
      PolicyQualifierId var2 = PolicyQualifierId.id_qt_cps;
      this.policyQualifierId = var2;
      DERIA5String var3 = new DERIA5String(var1);
      this.qualifier = var3;
   }

   public PolicyQualifierInfo(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         DERObjectIdentifier var5 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.policyQualifierId = var5;
         DEREncodable var6 = var1.getObjectAt(1);
         this.qualifier = var6;
      }
   }

   public PolicyQualifierInfo(DERObjectIdentifier var1, DEREncodable var2) {
      this.policyQualifierId = var1;
      this.qualifier = var2;
   }

   public static PolicyQualifierInfo getInstance(Object var0) {
      PolicyQualifierInfo var1;
      if(var0 instanceof PolicyQualifierInfo) {
         var1 = (PolicyQualifierInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance.");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PolicyQualifierInfo(var2);
      }

      return var1;
   }

   public DERObjectIdentifier getPolicyQualifierId() {
      return this.policyQualifierId;
   }

   public DEREncodable getQualifier() {
      return this.qualifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.policyQualifierId;
      var1.add(var2);
      DEREncodable var3 = this.qualifier;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
