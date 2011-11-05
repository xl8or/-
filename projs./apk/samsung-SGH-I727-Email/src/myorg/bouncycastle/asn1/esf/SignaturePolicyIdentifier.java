package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.esf.SignaturePolicyId;

public class SignaturePolicyIdentifier extends ASN1Encodable {

   private boolean isSignaturePolicyImplied;
   private SignaturePolicyId signaturePolicyId;


   public SignaturePolicyIdentifier() {
      this.isSignaturePolicyImplied = (boolean)1;
   }

   public SignaturePolicyIdentifier(SignaturePolicyId var1) {
      this.signaturePolicyId = var1;
      this.isSignaturePolicyImplied = (boolean)0;
   }

   public static SignaturePolicyIdentifier getInstance(Object var0) {
      SignaturePolicyIdentifier var1;
      if(var0 != null && !(var0 instanceof SignaturePolicyIdentifier)) {
         if(var0 instanceof ASN1Sequence) {
            SignaturePolicyId var2 = SignaturePolicyId.getInstance(var0);
            var1 = new SignaturePolicyIdentifier(var2);
         } else {
            if(!(var0 instanceof ASN1Null)) {
               StringBuilder var3 = (new StringBuilder()).append("unknown object in \'SignaturePolicyIdentifier\' factory: ");
               String var4 = var0.getClass().getName();
               String var5 = var3.append(var4).append(".").toString();
               throw new IllegalArgumentException(var5);
            }

            var1 = new SignaturePolicyIdentifier();
         }
      } else {
         var1 = (SignaturePolicyIdentifier)var0;
      }

      return var1;
   }

   public SignaturePolicyId getSignaturePolicyId() {
      return this.signaturePolicyId;
   }

   public boolean isSignaturePolicyImplied() {
      return this.isSignaturePolicyImplied;
   }

   public DERObject toASN1Object() {
      Object var1;
      if(this.isSignaturePolicyImplied) {
         var1 = new DERNull();
      } else {
         var1 = this.signaturePolicyId.getDERObject();
      }

      return (DERObject)var1;
   }
}
