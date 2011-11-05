package myorg.bouncycastle.asn1.x509;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.DERInteger;

public class CRLNumber extends DERInteger {

   public CRLNumber(BigInteger var1) {
      super(var1);
   }

   public BigInteger getCRLNumber() {
      return this.getPositiveValue();
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("CRLNumber: ");
      BigInteger var2 = this.getCRLNumber();
      return var1.append(var2).toString();
   }
}
