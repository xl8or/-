package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.crmf.AttributeTypeAndValue;

public class Controls extends ASN1Encodable {

   private ASN1Sequence content;


   private Controls(ASN1Sequence var1) {
      this.content = var1;
   }

   public static Controls getInstance(Object var0) {
      Controls var1;
      if(var0 instanceof Controls) {
         var1 = (Controls)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Controls(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.content;
   }

   public AttributeTypeAndValue[] toAttributeTypeAndValueArray() {
      AttributeTypeAndValue[] var1 = new AttributeTypeAndValue[this.content.size()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         AttributeTypeAndValue var4 = AttributeTypeAndValue.getInstance(this.content.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }
}
