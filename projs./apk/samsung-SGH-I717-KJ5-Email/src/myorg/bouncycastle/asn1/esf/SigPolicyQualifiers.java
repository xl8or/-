package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.esf.SigPolicyQualifierInfo;

public class SigPolicyQualifiers extends ASN1Encodable {

   ASN1Sequence qualifiers;


   public SigPolicyQualifiers(ASN1Sequence var1) {
      this.qualifiers = var1;
   }

   public SigPolicyQualifiers(SigPolicyQualifierInfo[] var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            DERSequence var6 = new DERSequence(var2);
            this.qualifiers = var6;
            return;
         }

         SigPolicyQualifierInfo var5 = var1[var3];
         var2.add(var5);
         ++var3;
      }
   }

   public static SigPolicyQualifiers getInstance(Object var0) {
      SigPolicyQualifiers var1;
      if(var0 instanceof SigPolicyQualifiers) {
         var1 = (SigPolicyQualifiers)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'SigPolicyQualifiers\' factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SigPolicyQualifiers(var2);
      }

      return var1;
   }

   public SigPolicyQualifierInfo getStringAt(int var1) {
      return SigPolicyQualifierInfo.getInstance(this.qualifiers.getObjectAt(var1));
   }

   public int size() {
      return this.qualifiers.size();
   }

   public DERObject toASN1Object() {
      return this.qualifiers;
   }
}
