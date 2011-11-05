package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class OtherKeyAttribute extends ASN1Encodable {

   private DEREncodable keyAttr;
   private DERObjectIdentifier keyAttrId;


   public OtherKeyAttribute(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.keyAttrId = var2;
      DEREncodable var3 = var1.getObjectAt(1);
      this.keyAttr = var3;
   }

   public OtherKeyAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.keyAttrId = var1;
      this.keyAttr = var2;
   }

   public static OtherKeyAttribute getInstance(Object var0) {
      OtherKeyAttribute var1;
      if(var0 != null && !(var0 instanceof OtherKeyAttribute)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OtherKeyAttribute(var2);
      } else {
         var1 = (OtherKeyAttribute)var0;
      }

      return var1;
   }

   public DEREncodable getKeyAttr() {
      return this.keyAttr;
   }

   public DERObjectIdentifier getKeyAttrId() {
      return this.keyAttrId;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.keyAttrId;
      var1.add(var2);
      DEREncodable var3 = this.keyAttr;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
