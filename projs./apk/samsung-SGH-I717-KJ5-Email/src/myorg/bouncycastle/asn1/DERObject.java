package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERTags;

public abstract class DERObject extends ASN1Encodable implements DERTags {

   public DERObject() {}

   abstract void encode(DEROutputStream var1) throws IOException;

   public abstract boolean equals(Object var1);

   public abstract int hashCode();

   public DERObject toASN1Object() {
      return this;
   }
}
