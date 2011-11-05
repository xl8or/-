package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class AttributeTypeAndValue extends ASN1Encodable {

   private DERObjectIdentifier type;
   private ASN1Encodable value;


   private AttributeTypeAndValue(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.type = var2;
      ASN1Encodable var3 = (ASN1Encodable)var1.getObjectAt(1);
      this.value = var3;
   }

   public static AttributeTypeAndValue getInstance(Object var0) {
      AttributeTypeAndValue var1;
      if(var0 instanceof AttributeTypeAndValue) {
         var1 = (AttributeTypeAndValue)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AttributeTypeAndValue(var2);
      }

      return var1;
   }

   public DERObjectIdentifier getType() {
      return this.type;
   }

   public ASN1Encodable getValue() {
      return this.value;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.type;
      var1.add(var2);
      ASN1Encodable var3 = this.value;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
