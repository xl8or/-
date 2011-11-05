package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;

public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {

   public PKIXCertPathValidatorSpi() {}

   public CertPathValidatorResult engineValidate(CertPath param1, CertPathParameters param2) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      // $FF: Couldn't be decompiled
   }
}
