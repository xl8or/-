package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class CertBag extends ASN1Encodable {

   DERObjectIdentifier certId;
   DERObject certValue;
   ASN1Sequence seq;


   public CertBag(ASN1Sequence var1) {
      this.seq = var1;
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.certId = var2;
      DERObject var3 = ((DERTaggedObject)var1.getObjectAt(1)).getObject();
      this.certValue = var3;
   }

   public CertBag(DERObjectIdentifier var1, DERObject var2) {
      this.certId = var1;
      this.certValue = var2;
   }

   public DERObjectIdentifier getCertId() {
      return this.certId;
   }

   public DERObject getCertValue() {
      return this.certValue;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.certId;
      var1.add(var2);
      DERObject var3 = this.certValue;
      DERTaggedObject var4 = new DERTaggedObject(0, var3);
      var1.add(var4);
      return new DERSequence(var1);
   }
}
