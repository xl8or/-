package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;

public class POPODecKeyRespContent extends ASN1Encodable {

   private ASN1Sequence content;


   private POPODecKeyRespContent(ASN1Sequence var1) {
      this.content = var1;
   }

   public static POPODecKeyRespContent getInstance(Object var0) {
      POPODecKeyRespContent var1;
      if(var0 instanceof POPODecKeyRespContent) {
         var1 = (POPODecKeyRespContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new POPODecKeyRespContent(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.content;
   }

   public DERInteger[] toDERIntegerArray() {
      DERInteger[] var1 = new DERInteger[this.content.size()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         DERInteger var4 = DERInteger.getInstance(this.content.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }
}
