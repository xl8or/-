package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERNull extends ASN1Null {

   public static final DERNull INSTANCE = new DERNull();
   byte[] zeroBytes;


   public DERNull() {
      byte[] var1 = new byte[0];
      this.zeroBytes = var1;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.zeroBytes;
      var1.writeEncoded(5, var2);
   }
}
