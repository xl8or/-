package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class PolicyInformation extends ASN1Encodable {

   private DERObjectIdentifier policyIdentifier;
   private ASN1Sequence policyQualifiers;


   public PolicyInformation(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 2) {
         DERObjectIdentifier var5 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.policyIdentifier = var5;
         if(var1.size() > 1) {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var1.getObjectAt(1));
            this.policyQualifiers = var6;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public PolicyInformation(DERObjectIdentifier var1) {
      this.policyIdentifier = var1;
   }

   public PolicyInformation(DERObjectIdentifier var1, ASN1Sequence var2) {
      this.policyIdentifier = var1;
      this.policyQualifiers = var2;
   }

   public static PolicyInformation getInstance(Object var0) {
      PolicyInformation var1;
      if(var0 != null && !(var0 instanceof PolicyInformation)) {
         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new PolicyInformation(var2);
      } else {
         var1 = (PolicyInformation)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getPolicyIdentifier() {
      return this.policyIdentifier;
   }

   public ASN1Sequence getPolicyQualifiers() {
      return this.policyQualifiers;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.policyIdentifier;
      var1.add(var2);
      if(this.policyQualifiers != null) {
         ASN1Sequence var3 = this.policyQualifiers;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
