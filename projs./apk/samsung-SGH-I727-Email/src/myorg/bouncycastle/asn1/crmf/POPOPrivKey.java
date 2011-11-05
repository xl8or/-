package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;

public class POPOPrivKey extends ASN1Encodable implements ASN1Choice {

   private DERObject obj;


   private POPOPrivKey(DERObject var1) {
      this.obj = var1;
   }

   public static ASN1Encodable getInstance(ASN1TaggedObject var0, boolean var1) {
      DERObject var2 = var0.getObject();
      return new POPOPrivKey(var2);
   }

   public DERObject toASN1Object() {
      return this.obj;
   }
}
