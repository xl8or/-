package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.DERObject;

public class PKIConfirmContent extends ASN1Encodable {

   private ASN1Null val;


   private PKIConfirmContent(ASN1Null var1) {
      this.val = var1;
   }

   public static PKIConfirmContent getInstance(Object var0) {
      PKIConfirmContent var1;
      if(var0 instanceof PKIConfirmContent) {
         var1 = (PKIConfirmContent)var0;
      } else {
         if(!(var0 instanceof ASN1Null)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Null var2 = (ASN1Null)var0;
         var1 = new PKIConfirmContent(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.val;
   }
}
