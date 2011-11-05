package myorg.bouncycastle.asn1.x9;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class KeySpecificInfo extends ASN1Encodable {

   private DERObjectIdentifier algorithm;
   private ASN1OctetString counter;


   public KeySpecificInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERObjectIdentifier var3 = (DERObjectIdentifier)var2.nextElement();
      this.algorithm = var3;
      ASN1OctetString var4 = (ASN1OctetString)var2.nextElement();
      this.counter = var4;
   }

   public KeySpecificInfo(DERObjectIdentifier var1, ASN1OctetString var2) {
      this.algorithm = var1;
      this.counter = var2;
   }

   public DERObjectIdentifier getAlgorithm() {
      return this.algorithm;
   }

   public ASN1OctetString getCounter() {
      return this.counter;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.algorithm;
      var1.add(var2);
      ASN1OctetString var3 = this.counter;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
