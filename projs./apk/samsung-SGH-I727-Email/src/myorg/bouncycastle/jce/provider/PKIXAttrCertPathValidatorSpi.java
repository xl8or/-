package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import myorg.bouncycastle.jce.exception.ExtCertPathValidatorException;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertPathValidatorUtilities;
import myorg.bouncycastle.jce.provider.RFC3281CertPathUtilities;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.ExtendedPKIXParameters;
import myorg.bouncycastle.x509.X509AttributeCertStoreSelector;
import myorg.bouncycastle.x509.X509AttributeCertificate;

public class PKIXAttrCertPathValidatorSpi extends CertPathValidatorSpi {

   public PKIXAttrCertPathValidatorSpi() {}

   public CertPathValidatorResult engineValidate(CertPath var1, CertPathParameters var2) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      if(!(var2 instanceof ExtendedPKIXParameters)) {
         StringBuilder var3 = (new StringBuilder()).append("Parameters must be a ");
         String var4 = ExtendedPKIXParameters.class.getName();
         String var5 = var3.append(var4).append(" instance.").toString();
         throw new InvalidAlgorithmParameterException(var5);
      } else {
         ExtendedPKIXParameters var6 = (ExtendedPKIXParameters)var2;
         Selector var7 = var6.getTargetConstraints();
         if(!(var7 instanceof X509AttributeCertStoreSelector)) {
            StringBuilder var8 = (new StringBuilder()).append("TargetConstraints must be an instance of ");
            String var9 = X509AttributeCertStoreSelector.class.getName();
            StringBuilder var10 = var8.append(var9).append(" for ");
            String var11 = this.getClass().getName();
            String var12 = var10.append(var11).append(" class.").toString();
            throw new InvalidAlgorithmParameterException(var12);
         } else {
            X509AttributeCertificate var13 = ((X509AttributeCertStoreSelector)var7).getAttributeCert();
            CertPath var14 = RFC3281CertPathUtilities.processAttrCert1(var13, var6);
            CertPathValidatorResult var15 = RFC3281CertPathUtilities.processAttrCert2(var1, var6);
            X509Certificate var16 = (X509Certificate)var1.getCertificates().get(0);
            RFC3281CertPathUtilities.processAttrCert3(var16, var6);
            RFC3281CertPathUtilities.processAttrCert4(var16, var6);
            RFC3281CertPathUtilities.processAttrCert5(var13, var6);
            RFC3281CertPathUtilities.processAttrCert7(var13, var1, var14, var6);
            RFC3281CertPathUtilities.additionalChecks(var13, var6);
            Object var17 = null;
            byte var18 = -1;

            Date var19;
            try {
               var19 = CertPathValidatorUtilities.getValidCertDateFromValidityModel(var6, (CertPath)var17, var18);
            } catch (AnnotatedException var23) {
               throw new ExtCertPathValidatorException("Could not get validity date from attribute certificate.", var23);
            }

            List var21 = var1.getCertificates();
            RFC3281CertPathUtilities.checkCRLs(var13, var6, var16, var19, var21);
            return var15;
         }
      }
   }
}
