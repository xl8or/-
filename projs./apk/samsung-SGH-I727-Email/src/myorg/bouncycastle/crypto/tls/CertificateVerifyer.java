package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.asn1.x509.X509CertificateStructure;

public interface CertificateVerifyer {

   boolean isValid(X509CertificateStructure[] var1);
}
