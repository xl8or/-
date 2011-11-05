package myorg.bouncycastle.sasn1;

import java.io.IOException;
import myorg.bouncycastle.sasn1.Asn1Object;

public interface Asn1Set {

   Asn1Object readObject() throws IOException;
}
