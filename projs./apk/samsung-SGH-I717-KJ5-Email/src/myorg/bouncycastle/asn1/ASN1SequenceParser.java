package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.DEREncodable;

public interface ASN1SequenceParser extends DEREncodable {

   DEREncodable readObject() throws IOException;
}
