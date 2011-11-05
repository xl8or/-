package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;

public class PollReqContent extends ASN1Encodable {

   private ASN1Sequence content;


   private PollReqContent(ASN1Sequence var1) {
      this.content = var1;
   }

   public static PollReqContent getInstance(Object var0) {
      PollReqContent var1;
      if(var0 instanceof PollReqContent) {
         var1 = (PollReqContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PollReqContent(var2);
      }

      return var1;
   }

   private DERInteger[] seqenceToDERIntegerArray(ASN1Sequence var1) {
      DERInteger[] var2 = new DERInteger[var1.size()];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return var2;
         }

         DERInteger var5 = DERInteger.getInstance(var1.getObjectAt(var3));
         var2[var3] = var5;
         ++var3;
      }
   }

   public DERInteger[][] getCertReqIds() {
      DERInteger[] var1 = new DERInteger[this.content.size()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         ASN1Sequence var4 = (ASN1Sequence)this.content.getObjectAt(var2);
         DERInteger[] var5 = this.seqenceToDERIntegerArray(var4);
         var1[var2] = var5;
         ++var2;
      }
   }

   public DERObject toASN1Object() {
      return this.content;
   }
}
