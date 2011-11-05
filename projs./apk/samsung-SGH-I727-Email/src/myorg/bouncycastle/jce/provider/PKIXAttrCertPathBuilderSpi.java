package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Principal;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.jce.exception.ExtCertPathBuilderException;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertPathValidatorUtilities;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import myorg.bouncycastle.x509.X509AttributeCertStoreSelector;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509CertStoreSelector;

public class PKIXAttrCertPathBuilderSpi extends CertPathBuilderSpi {

   private Exception certPathException;


   public PKIXAttrCertPathBuilderSpi() {}

   private CertPathBuilderResult build(X509AttributeCertificate param1, X509Certificate param2, ExtendedPKIXBuilderParameters param3, List param4) {
      // $FF: Couldn't be decompiled
   }

   public CertPathBuilderResult engineBuild(CertPathParameters var1) throws CertPathBuilderException, InvalidAlgorithmParameterException {
      if(!(var1 instanceof PKIXBuilderParameters) && !(var1 instanceof ExtendedPKIXBuilderParameters)) {
         StringBuilder var2 = (new StringBuilder()).append("Parameters must be an instance of ");
         String var3 = PKIXBuilderParameters.class.getName();
         StringBuilder var4 = var2.append(var3).append(" or ");
         String var5 = ExtendedPKIXBuilderParameters.class.getName();
         String var6 = var4.append(var5).append(".").toString();
         throw new InvalidAlgorithmParameterException(var6);
      } else {
         ExtendedPKIXBuilderParameters var7;
         if(var1 instanceof ExtendedPKIXBuilderParameters) {
            var7 = (ExtendedPKIXBuilderParameters)var1;
         } else {
            var7 = (ExtendedPKIXBuilderParameters)ExtendedPKIXBuilderParameters.getInstance((PKIXBuilderParameters)var1);
         }

         ArrayList var8 = new ArrayList();
         Selector var9 = var7.getTargetConstraints();
         if(!(var9 instanceof X509AttributeCertStoreSelector)) {
            StringBuilder var10 = (new StringBuilder()).append("TargetConstraints must be an instance of ");
            String var11 = X509AttributeCertStoreSelector.class.getName();
            StringBuilder var12 = var10.append(var11).append(" for ");
            String var13 = this.getClass().getName();
            String var14 = var12.append(var13).append(" class.").toString();
            throw new CertPathBuilderException(var14);
         } else {
            Collection var19;
            try {
               X509AttributeCertStoreSelector var15 = (X509AttributeCertStoreSelector)var9;
               List var16 = var7.getStores();
               var19 = CertPathValidatorUtilities.findCertificates(var15, var16);
            } catch (AnnotatedException var71) {
               ExtCertPathBuilderException var22 = new ExtCertPathBuilderException;
               String var24 = "Error finding target attribute certificate.";
               var22.<init>(var24, var71);
               throw var22;
            }

            if(var19.isEmpty()) {
               throw new CertPathBuilderException("No attribute certificate found matching targetContraints.");
            } else {
               CertPathBuilderResult var26 = null;
               Iterator var27 = var19.iterator();

               label85:
               while(var27.hasNext() && var26 == null) {
                  X509AttributeCertificate var28 = (X509AttributeCertificate)var27.next();
                  X509CertStoreSelector var29 = new X509CertStoreSelector();
                  Principal[] var30 = var28.getIssuer().getPrincipals();
                  HashSet var31 = new HashSet();
                  int var32 = 0;

                  while(true) {
                     int var33 = var30.length;
                     if(var32 >= var33) {
                        if(var31.isEmpty()) {
                           throw new CertPathBuilderException("Public key certificate for attribute certificate cannot be found.");
                        }

                        Iterator var63 = var31.iterator();

                        while(true) {
                           if(!var63.hasNext() || var26 != null) {
                              continue label85;
                           }

                           X509Certificate var64 = (X509Certificate)var63.next();
                           var26 = this.build(var28, var64, var7, var8);
                        }
                     }

                     try {
                        if(var30[var32] instanceof X500Principal) {
                           byte[] var36 = ((X500Principal)var30[var32]).getEncoded();
                           var29.setSubject(var36);
                        }

                        List var39 = var7.getStores();
                        Collection var42 = CertPathValidatorUtilities.findCertificates(var29, var39);
                        boolean var45 = var31.addAll(var42);
                        List var46 = var7.getCertStores();
                        Collection var49 = CertPathValidatorUtilities.findCertificates(var29, var46);
                        boolean var52 = var31.addAll(var49);
                     } catch (AnnotatedException var72) {
                        ExtCertPathBuilderException var54 = new ExtCertPathBuilderException;
                        String var56 = "Public key certificate for attribute certificate cannot be searched.";
                        var54.<init>(var56, var72);
                        throw var54;
                     } catch (IOException var73) {
                        ExtCertPathBuilderException var59 = new ExtCertPathBuilderException;
                        String var61 = "cannot encode X500Principal.";
                        var59.<init>(var61, var73);
                        throw var59;
                     }

                     ++var32;
                  }
               }

               if(var26 == null && this.certPathException != null) {
                  Exception var70 = this.certPathException;
                  throw new ExtCertPathBuilderException("Possible certificate chain could not be validated.", var70);
               } else if(var26 == null && this.certPathException == null) {
                  throw new CertPathBuilderException("Unable to find certificate chain.");
               } else {
                  return var26;
               }
            }
         }
      }
   }
}
