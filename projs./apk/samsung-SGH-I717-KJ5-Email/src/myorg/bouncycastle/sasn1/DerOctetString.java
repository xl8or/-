package myorg.bouncycastle.sasn1;

import java.io.InputStream;
import myorg.bouncycastle.sasn1.Asn1OctetString;
import myorg.bouncycastle.sasn1.ConstructedOctetStream;
import myorg.bouncycastle.sasn1.DerObject;

public class DerOctetString extends DerObject implements Asn1OctetString {

   protected DerOctetString(int var1, byte[] var2) {
      super(var1, 4, var2);
   }

   public InputStream getOctetStream() {
      Object var2;
      if(this.isConstructed()) {
         InputStream var1 = this.getRawContentStream();
         var2 = new ConstructedOctetStream(var1);
      } else {
         var2 = this.getRawContentStream();
      }

      return (InputStream)var2;
   }
}
