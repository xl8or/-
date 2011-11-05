package myorg.bouncycastle.asn1.x509.qualified;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface ETSIQCObjectIdentifiers {

   String id_etsi_qcs = "0.4.0.1862.1";
   DERObjectIdentifier id_etsi_qcs_LimiteValue = new DERObjectIdentifier("0.4.0.1862.1.2");
   DERObjectIdentifier id_etsi_qcs_QcCompliance = new DERObjectIdentifier("0.4.0.1862.1.1");
   DERObjectIdentifier id_etsi_qcs_QcSSCD = new DERObjectIdentifier("0.4.0.1862.1.4");
   DERObjectIdentifier id_etsi_qcs_RetentionPeriod = new DERObjectIdentifier("0.4.0.1862.1.3");


}
