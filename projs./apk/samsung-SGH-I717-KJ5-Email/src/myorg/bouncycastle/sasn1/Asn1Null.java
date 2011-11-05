package myorg.bouncycastle.sasn1;

import java.io.InputStream;
import myorg.bouncycastle.sasn1.Asn1Object;

public class Asn1Null extends Asn1Object {

   protected Asn1Null(int var1) {
      super(var1, 5, (InputStream)null);
   }
}
