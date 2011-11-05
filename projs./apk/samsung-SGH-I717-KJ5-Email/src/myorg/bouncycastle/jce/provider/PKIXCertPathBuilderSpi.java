package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import myorg.bouncycastle.jce.exception.ExtCertPathBuilderException;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertPathValidatorUtilities;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import myorg.bouncycastle.x509.X509CertStoreSelector;

public class PKIXCertPathBuilderSpi extends CertPathBuilderSpi {

   private Exception certPathException;


   public PKIXCertPathBuilderSpi() {}

   protected CertPathBuilderResult build(X509Certificate param1, ExtendedPKIXBuilderParameters param2, List param3) {
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
         if(!(var9 instanceof X509CertStoreSelector)) {
            StringBuilder var10 = (new StringBuilder()).append("TargetConstraints must be an instance of ");
            String var11 = X509CertStoreSelector.class.getName();
            StringBuilder var12 = var10.append(var11).append(" for ");
            String var13 = this.getClass().getName();
            String var14 = var12.append(var13).append(" class.").toString();
            throw new CertPathBuilderException(var14);
         } else {
            Collection var17;
            try {
               X509CertStoreSelector var15 = (X509CertStoreSelector)var9;
               List var16 = var7.getStores();
               var17 = CertPathValidatorUtilities.findCertificates(var15, var16);
               if(var17 != null) {
                  X509CertStoreSelector var18 = (X509CertStoreSelector)var9;
                  List var19 = var7.getCertStores();
                  Collection var20 = CertPathValidatorUtilities.findCertificates(var18, var19);
                  var17.addAll(var20);
               }
            } catch (AnnotatedException var29) {
               throw new ExtCertPathBuilderException("Error finding target certificate.", var29);
            }

            if(var17.isEmpty()) {
               throw new CertPathBuilderException("No certificate found matching targetContraints.");
            } else {
               CertPathBuilderResult var23 = null;

               X509Certificate var25;
               for(Iterator var24 = var17.iterator(); var24.hasNext() && var23 == null; var23 = this.build(var25, var7, var8)) {
                  var25 = (X509Certificate)var24.next();
               }

               if(var23 == null && this.certPathException != null) {
                  if(this.certPathException instanceof AnnotatedException) {
                     String var26 = this.certPathException.getMessage();
                     Throwable var27 = this.certPathException.getCause();
                     throw new CertPathBuilderException(var26, var27);
                  } else {
                     Exception var28 = this.certPathException;
                     throw new CertPathBuilderException("Possible certificate chain could not be validated.", var28);
                  }
               } else if(var23 == null && this.certPathException == null) {
                  throw new CertPathBuilderException("Unable to find certificate chain.");
               } else {
                  return var23;
               }
            }
         }
      }
   }
}
