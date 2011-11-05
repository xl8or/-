package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.DEREncodable;

public interface ASN1TaggedObjectParser extends DEREncodable {

   DEREncodable getObjectParser(int var1, boolean var2) throws IOException;

   int getTagNo();
}
