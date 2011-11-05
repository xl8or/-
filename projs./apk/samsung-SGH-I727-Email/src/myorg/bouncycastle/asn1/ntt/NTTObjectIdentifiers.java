package myorg.bouncycastle.asn1.ntt;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface NTTObjectIdentifiers {

   DERObjectIdentifier id_camellia128_cbc = new DERObjectIdentifier("1.2.392.200011.61.1.1.1.2");
   DERObjectIdentifier id_camellia128_wrap = new DERObjectIdentifier("1.2.392.200011.61.1.1.3.2");
   DERObjectIdentifier id_camellia192_cbc = new DERObjectIdentifier("1.2.392.200011.61.1.1.1.3");
   DERObjectIdentifier id_camellia192_wrap = new DERObjectIdentifier("1.2.392.200011.61.1.1.3.3");
   DERObjectIdentifier id_camellia256_cbc = new DERObjectIdentifier("1.2.392.200011.61.1.1.1.4");
   DERObjectIdentifier id_camellia256_wrap = new DERObjectIdentifier("1.2.392.200011.61.1.1.3.4");


}
