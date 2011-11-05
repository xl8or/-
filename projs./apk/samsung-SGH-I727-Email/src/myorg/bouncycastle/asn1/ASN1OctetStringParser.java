package myorg.bouncycastle.asn1;

import java.io.InputStream;
import myorg.bouncycastle.asn1.DEREncodable;

public interface ASN1OctetStringParser extends DEREncodable {

   InputStream getOctetStream();
}
