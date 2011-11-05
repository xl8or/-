package myorg.bouncycastle.asn1;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERSet;

class BERFactory {

   static final BERSequence EMPTY_SEQUENCE = new BERSequence();
   static final BERSet EMPTY_SET = new BERSet();


   BERFactory() {}

   static BERSequence createSequence(ASN1EncodableVector var0) {
      BERSequence var1;
      if(var0.size() < 1) {
         var1 = EMPTY_SEQUENCE;
      } else {
         var1 = new BERSequence(var0);
      }

      return var1;
   }

   static BERSet createSet(ASN1EncodableVector var0) {
      BERSet var1;
      if(var0.size() < 1) {
         var1 = EMPTY_SET;
      } else {
         var1 = new BERSet(var0);
      }

      return var1;
   }

   static BERSet createSet(ASN1EncodableVector var0, boolean var1) {
      BERSet var2;
      if(var0.size() < 1) {
         var2 = EMPTY_SET;
      } else {
         var2 = new BERSet(var0, var1);
      }

      return var2;
   }
}
