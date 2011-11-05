package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class SafeBag extends ASN1Encodable {

   ASN1Set bagAttributes;
   DERObjectIdentifier bagId;
   DERObject bagValue;


   public SafeBag(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.bagId = var2;
      DERObject var3 = ((DERTaggedObject)var1.getObjectAt(1)).getObject();
      this.bagValue = var3;
      if(var1.size() == 3) {
         ASN1Set var4 = (ASN1Set)var1.getObjectAt(2);
         this.bagAttributes = var4;
      }
   }

   public SafeBag(DERObjectIdentifier var1, DERObject var2) {
      this.bagId = var1;
      this.bagValue = var2;
      this.bagAttributes = null;
   }

   public SafeBag(DERObjectIdentifier var1, DERObject var2, ASN1Set var3) {
      this.bagId = var1;
      this.bagValue = var2;
      this.bagAttributes = var3;
   }

   public ASN1Set getBagAttributes() {
      return this.bagAttributes;
   }

   public DERObjectIdentifier getBagId() {
      return this.bagId;
   }

   public DERObject getBagValue() {
      return this.bagValue;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.bagId;
      var1.add(var2);
      DERObject var3 = this.bagValue;
      DERTaggedObject var4 = new DERTaggedObject(0, var3);
      var1.add(var4);
      if(this.bagAttributes != null) {
         ASN1Set var5 = this.bagAttributes;
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
