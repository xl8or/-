package myorg.bouncycastle.asn1.oiw;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class ElGamalParameter extends ASN1Encodable {

   DERInteger g;
   DERInteger p;


   public ElGamalParameter(BigInteger var1, BigInteger var2) {
      DERInteger var3 = new DERInteger(var1);
      this.p = var3;
      DERInteger var4 = new DERInteger(var2);
      this.g = var4;
   }

   public ElGamalParameter(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = (DERInteger)var2.nextElement();
      this.p = var3;
      DERInteger var4 = (DERInteger)var2.nextElement();
      this.g = var4;
   }

   public BigInteger getG() {
      return this.g.getPositiveValue();
   }

   public BigInteger getP() {
      return this.p.getPositiveValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.p;
      var1.add(var2);
      DERInteger var3 = this.g;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
