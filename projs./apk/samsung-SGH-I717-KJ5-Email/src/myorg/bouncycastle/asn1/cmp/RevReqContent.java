package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.cmp.RevDetails;

public class RevReqContent extends ASN1Encodable {

   private ASN1Sequence content;


   private RevReqContent(ASN1Sequence var1) {
      this.content = var1;
   }

   public static RevReqContent getInstance(Object var0) {
      RevReqContent var1;
      if(var0 instanceof RevReqContent) {
         var1 = (RevReqContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RevReqContent(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.content;
   }

   public RevDetails[] toRevDetailsArray() {
      RevDetails[] var1 = new RevDetails[this.content.size()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         RevDetails var4 = RevDetails.getInstance(this.content.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }
}
