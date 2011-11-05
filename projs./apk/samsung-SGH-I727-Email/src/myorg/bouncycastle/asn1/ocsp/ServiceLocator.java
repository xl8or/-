package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.X509Name;

public class ServiceLocator extends ASN1Encodable {

   X509Name issuer;
   DERObject locator;


   public ServiceLocator() {}

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      X509Name var2 = this.issuer;
      var1.add(var2);
      if(this.locator != null) {
         DERObject var3 = this.locator;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
