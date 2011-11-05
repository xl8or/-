package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KeyDerivationFunc extends AlgorithmIdentifier {

   KeyDerivationFunc(ASN1Sequence var1) {
      super(var1);
   }

   KeyDerivationFunc(DERObjectIdentifier var1, ASN1Encodable var2) {
      super(var1, var2);
   }
}
