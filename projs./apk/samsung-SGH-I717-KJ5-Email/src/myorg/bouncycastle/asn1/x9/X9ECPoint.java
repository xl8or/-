package myorg.bouncycastle.asn1.x9;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class X9ECPoint extends ASN1Encodable {

   ECPoint p;


   public X9ECPoint(ECCurve var1, ASN1OctetString var2) {
      byte[] var3 = var2.getOctets();
      ECPoint var4 = var1.decodePoint(var3);
      this.p = var4;
   }

   public X9ECPoint(ECPoint var1) {
      this.p = var1;
   }

   public ECPoint getPoint() {
      return this.p;
   }

   public DERObject toASN1Object() {
      byte[] var1 = this.p.getEncoded();
      return new DEROctetString(var1);
   }
}
