package myorg.bouncycastle.asn1.oiw;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface OIWObjectIdentifiers {

   DERObjectIdentifier desCBC = new DERObjectIdentifier("1.3.14.3.2.7");
   DERObjectIdentifier desCFB = new DERObjectIdentifier("1.3.14.3.2.9");
   DERObjectIdentifier desECB = new DERObjectIdentifier("1.3.14.3.2.6");
   DERObjectIdentifier desEDE = new DERObjectIdentifier("1.3.14.3.2.17");
   DERObjectIdentifier desOFB = new DERObjectIdentifier("1.3.14.3.2.8");
   DERObjectIdentifier dsaWithSHA1 = new DERObjectIdentifier("1.3.14.3.2.27");
   DERObjectIdentifier elGamalAlgorithm = new DERObjectIdentifier("1.3.14.7.2.1.1");
   DERObjectIdentifier idSHA1 = new DERObjectIdentifier("1.3.14.3.2.26");
   DERObjectIdentifier md4WithRSA = new DERObjectIdentifier("1.3.14.3.2.2");
   DERObjectIdentifier md4WithRSAEncryption = new DERObjectIdentifier("1.3.14.3.2.4");
   DERObjectIdentifier md5WithRSA = new DERObjectIdentifier("1.3.14.3.2.3");
   DERObjectIdentifier sha1WithRSA = new DERObjectIdentifier("1.3.14.3.2.29");


}
