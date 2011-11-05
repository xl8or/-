package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.esf.OtherHashAlgAndValue;
import myorg.bouncycastle.asn1.esf.SigPolicyQualifiers;

public class SignaturePolicyId extends ASN1Encodable {

   private OtherHashAlgAndValue sigPolicyHash;
   private DERObjectIdentifier sigPolicyId;
   private SigPolicyQualifiers sigPolicyQualifiers;


   public SignaturePolicyId(ASN1Sequence var1) {
      if(var1.size() != 2 && var1.size() != 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         DERObjectIdentifier var5 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.sigPolicyId = var5;
         OtherHashAlgAndValue var6 = OtherHashAlgAndValue.getInstance(var1.getObjectAt(1));
         this.sigPolicyHash = var6;
         if(var1.size() == 3) {
            SigPolicyQualifiers var7 = SigPolicyQualifiers.getInstance(var1.getObjectAt(2));
            this.sigPolicyQualifiers = var7;
         }
      }
   }

   public SignaturePolicyId(DERObjectIdentifier var1, OtherHashAlgAndValue var2) {
      this(var1, var2, (SigPolicyQualifiers)null);
   }

   public SignaturePolicyId(DERObjectIdentifier var1, OtherHashAlgAndValue var2, SigPolicyQualifiers var3) {
      this.sigPolicyId = var1;
      this.sigPolicyHash = var2;
      this.sigPolicyQualifiers = var3;
   }

   public static SignaturePolicyId getInstance(Object var0) {
      SignaturePolicyId var1;
      if(var0 != null && !(var0 instanceof SignaturePolicyId)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Unknown object in \'SignaturePolicyId\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SignaturePolicyId(var2);
      } else {
         var1 = (SignaturePolicyId)var0;
      }

      return var1;
   }

   public OtherHashAlgAndValue getSigPolicyHash() {
      return this.sigPolicyHash;
   }

   public DERObjectIdentifier getSigPolicyId() {
      return this.sigPolicyId;
   }

   public SigPolicyQualifiers getSigPolicyQualifiers() {
      return this.sigPolicyQualifiers;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.sigPolicyId;
      var1.add(var2);
      OtherHashAlgAndValue var3 = this.sigPolicyHash;
      var1.add(var3);
      if(this.sigPolicyQualifiers != null) {
         SigPolicyQualifiers var4 = this.sigPolicyQualifiers;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
