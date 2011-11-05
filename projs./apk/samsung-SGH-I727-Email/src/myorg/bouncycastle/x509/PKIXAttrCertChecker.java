package myorg.bouncycastle.x509;

import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.util.Collection;
import java.util.Set;
import myorg.bouncycastle.x509.X509AttributeCertificate;

public abstract class PKIXAttrCertChecker implements Cloneable {

   public PKIXAttrCertChecker() {}

   public abstract void check(X509AttributeCertificate var1, CertPath var2, CertPath var3, Collection var4) throws CertPathValidatorException;

   public abstract Object clone();

   public abstract Set getSupportedExtensions();
}
