package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class Attribute extends ASN1Encodable {

   private DERObjectIdentifier attrType;
   private ASN1Set attrValues;


   public Attribute(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         DERObjectIdentifier var5 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.attrType = var5;
         ASN1Set var6 = ASN1Set.getInstance(var1.getObjectAt(1));
         this.attrValues = var6;
      }
   }

   public Attribute(DERObjectIdentifier var1, ASN1Set var2) {
      this.attrType = var1;
      this.attrValues = var2;
   }

   public static Attribute getInstance(Object var0) {
      Attribute var1;
      if(var0 != null && !(var0 instanceof Attribute)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Attribute(var2);
      } else {
         var1 = (Attribute)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getAttrType() {
      return this.attrType;
   }

   public ASN1Set getAttrValues() {
      return this.attrValues;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.attrType;
      var1.add(var2);
      ASN1Set var3 = this.attrValues;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
