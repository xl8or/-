package myorg.bouncycastle.sasn1;

import java.io.IOException;
import java.math.BigInteger;
import myorg.bouncycastle.sasn1.DerObject;

public class Asn1Integer extends DerObject {

   private BigInteger _value;


   protected Asn1Integer(int var1, byte[] var2) throws IOException {
      super(var1, 2, var2);
      BigInteger var3 = new BigInteger(var2);
      this._value = var3;
   }

   public Asn1Integer(long var1) {
      BigInteger var3 = BigInteger.valueOf(var1);
      this(var3);
   }

   public Asn1Integer(BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      super(0, 2, var2);
      this._value = var1;
   }

   public BigInteger getValue() {
      return this._value;
   }
}
