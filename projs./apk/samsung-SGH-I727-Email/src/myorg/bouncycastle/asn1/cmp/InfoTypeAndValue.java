package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class InfoTypeAndValue extends ASN1Encodable {

   private DERObjectIdentifier infoType;
   private ASN1Encodable infoValue;


   private InfoTypeAndValue(ASN1Sequence var1) {
      DERObjectIdentifier var2 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
      this.infoType = var2;
      if(var1.size() > 1) {
         ASN1Encodable var3 = (ASN1Encodable)var1.getObjectAt(1);
         this.infoValue = var3;
      }
   }

   public static InfoTypeAndValue getInstance(Object var0) {
      InfoTypeAndValue var1;
      if(var0 instanceof InfoTypeAndValue) {
         var1 = (InfoTypeAndValue)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new InfoTypeAndValue(var2);
      }

      return var1;
   }

   public DERObjectIdentifier getInfoType() {
      return this.infoType;
   }

   public ASN1Encodable getInfoValue() {
      return this.infoValue;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.infoType;
      var1.add(var2);
      if(this.infoValue != null) {
         ASN1Encodable var3 = this.infoValue;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
