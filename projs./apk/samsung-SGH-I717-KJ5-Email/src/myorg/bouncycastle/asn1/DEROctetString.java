package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DEROctetString extends ASN1OctetString {

   public DEROctetString(DEREncodable var1) {
      super(var1);
   }

   public DEROctetString(byte[] var1) {
      super(var1);
   }

   static void encode(DEROutputStream var0, byte[] var1) throws IOException {
      var0.writeEncoded(4, var1);
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.string;
      var1.writeEncoded(4, var2);
   }
}
