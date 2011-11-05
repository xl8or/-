package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public class ASN1OutputStream extends DEROutputStream {

   public ASN1OutputStream(OutputStream var1) {
      super(var1);
   }

   public void writeObject(Object var1) throws IOException {
      if(var1 == null) {
         this.writeNull();
      } else if(var1 instanceof DERObject) {
         ((DERObject)var1).encode(this);
      } else if(var1 instanceof DEREncodable) {
         ((DEREncodable)var1).getDERObject().encode(this);
      } else {
         throw new IOException("object not ASN1Encodable");
      }
   }
}
