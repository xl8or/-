package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptionScheme extends AlgorithmIdentifier {

   DERObject obj;
   DERObject objectId;


   EncryptionScheme(ASN1Sequence var1) {
      super(var1);
      DERObject var2 = (DERObject)var1.getObjectAt(0);
      this.objectId = var2;
      DERObject var3 = (DERObject)var1.getObjectAt(1);
      this.obj = var3;
   }

   public DERObject getDERObject() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObject var2 = this.objectId;
      var1.add(var2);
      DERObject var3 = this.obj;
      var1.add(var3);
      return new DERSequence(var1);
   }

   public DERObject getObject() {
      return this.obj;
   }
}
