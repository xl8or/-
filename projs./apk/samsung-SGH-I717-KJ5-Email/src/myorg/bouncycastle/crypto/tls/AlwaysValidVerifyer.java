package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.crypto.tls.CertificateVerifyer;

public class AlwaysValidVerifyer implements CertificateVerifyer {

   public AlwaysValidVerifyer() {}

   public boolean isValid(X509CertificateStructure[] var1) {
      return true;
   }
}
