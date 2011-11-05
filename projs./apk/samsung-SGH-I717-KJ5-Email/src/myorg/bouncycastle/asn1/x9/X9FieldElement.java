package myorg.bouncycastle.asn1.x9;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.x9.X9IntegerConverter;
import myorg.bouncycastle.math.ec.ECFieldElement;

public class X9FieldElement extends ASN1Encodable {

   private static X9IntegerConverter converter = new X9IntegerConverter();
   protected ECFieldElement f;


   public X9FieldElement(int var1, int var2, int var3, int var4, ASN1OctetString var5) {
      byte[] var6 = var5.getOctets();
      BigInteger var7 = new BigInteger(1, var6);
      ECFieldElement.F2m var12 = new ECFieldElement.F2m(var1, var2, var3, var4, var7);
      this(var12);
   }

   public X9FieldElement(BigInteger var1, ASN1OctetString var2) {
      byte[] var3 = var2.getOctets();
      BigInteger var4 = new BigInteger(1, var3);
      ECFieldElement.Fp var5 = new ECFieldElement.Fp(var1, var4);
      this(var5);
   }

   public X9FieldElement(ECFieldElement var1) {
      this.f = var1;
   }

   public ECFieldElement getValue() {
      return this.f;
   }

   public DERObject toASN1Object() {
      X9IntegerConverter var1 = converter;
      ECFieldElement var2 = this.f;
      int var3 = var1.getByteLength(var2);
      X9IntegerConverter var4 = converter;
      BigInteger var5 = this.f.toBigInteger();
      byte[] var6 = var4.integerToBytes(var5, var3);
      return new DEROctetString(var6);
   }
}
