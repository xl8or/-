package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface OCSPObjectIdentifiers {

   DERObjectIdentifier id_pkix_ocsp = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1");
   DERObjectIdentifier id_pkix_ocsp_archive_cutoff = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.6");
   DERObjectIdentifier id_pkix_ocsp_basic = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.1");
   DERObjectIdentifier id_pkix_ocsp_crl = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.3");
   DERObjectIdentifier id_pkix_ocsp_nocheck = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.5");
   DERObjectIdentifier id_pkix_ocsp_nonce = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.2");
   DERObjectIdentifier id_pkix_ocsp_response = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.4");
   DERObjectIdentifier id_pkix_ocsp_service_locator = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1.7");
   String pkix_ocsp = "1.3.6.1.5.5.7.48.1";


}
