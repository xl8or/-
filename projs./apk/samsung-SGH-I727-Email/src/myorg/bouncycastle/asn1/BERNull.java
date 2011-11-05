package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.BEROutputStream;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DEROutputStream;

public class BERNull extends DERNull {

   public static final BERNull INSTANCE = new BERNull();


   public BERNull() {}

   void encode(DEROutputStream var1) throws IOException {
      if(!(var1 instanceof ASN1OutputStream) && !(var1 instanceof BEROutputStream)) {
         super.encode(var1);
      } else {
         var1.write(5);
      }
   }
}
