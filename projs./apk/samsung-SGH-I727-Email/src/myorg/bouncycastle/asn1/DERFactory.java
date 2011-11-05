package myorg.bouncycastle.asn1;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;

class DERFactory {

   static final DERSequence EMPTY_SEQUENCE = new DERSequence();
   static final DERSet EMPTY_SET = new DERSet();


   DERFactory() {}

   static DERSequence createSequence(ASN1EncodableVector var0) {
      DERSequence var1;
      if(var0.size() < 1) {
         var1 = EMPTY_SEQUENCE;
      } else {
         var1 = new DERSequence(var0);
      }

      return var1;
   }

   static DERSet createSet(ASN1EncodableVector var0) {
      DERSet var1;
      if(var0.size() < 1) {
         var1 = EMPTY_SET;
      } else {
         var1 = new DERSet(var0);
      }

      return var1;
   }

   static DERSet createSet(ASN1EncodableVector var0, boolean var1) {
      DERSet var2;
      if(var0.size() < 1) {
         var2 = EMPTY_SET;
      } else {
         var2 = new DERSet(var0, var1);
      }

      return var2;
   }
}
