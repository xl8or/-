package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class SigPolicyQualifierInfo extends ASN1Encodable {

   private DERObjectIdentifier sigPolicyQualifierId;
   private DEREncodable sigQualifier;


   public SigPolicyQualifierInfo(ASN1Sequence var1) {
      DERObjectIdentifier var2 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
      this.sigPolicyQualifierId = var2;
      DEREncodable var3 = var1.getObjectAt(1);
      this.sigQualifier = var3;
   }

   public SigPolicyQualifierInfo(DERObjectIdentifier var1, DEREncodable var2) {
      this.sigPolicyQualifierId = var1;
      this.sigQualifier = var2;
   }

   public static SigPolicyQualifierInfo getInstance(Object var0) {
      SigPolicyQualifierInfo var1;
      if(var0 != null && !(var0 instanceof SigPolicyQualifierInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'SigPolicyQualifierInfo\' factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SigPolicyQualifierInfo(var2);
      } else {
         var1 = (SigPolicyQualifierInfo)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getSigPolicyQualifierId() {
      return this.sigPolicyQualifierId;
   }

   public DEREncodable getSigQualifier() {
      return this.sigQualifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.sigPolicyQualifierId;
      var1.add(var2);
      DEREncodable var3 = this.sigQualifier;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
