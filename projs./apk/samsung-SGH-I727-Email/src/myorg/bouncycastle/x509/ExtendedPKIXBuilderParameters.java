package myorg.bouncycastle.x509;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.X509CertSelector;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.ExtendedPKIXParameters;
import myorg.bouncycastle.x509.X509CertStoreSelector;

public class ExtendedPKIXBuilderParameters extends ExtendedPKIXParameters {

   private Set excludedCerts;
   private int maxPathLength = 5;


   public ExtendedPKIXBuilderParameters(Set var1, Selector var2) throws InvalidAlgorithmParameterException {
      super(var1);
      Set var3 = Collections.EMPTY_SET;
      this.excludedCerts = var3;
      this.setTargetConstraints(var2);
   }

   public static ExtendedPKIXParameters getInstance(PKIXParameters var0) {
      ExtendedPKIXBuilderParameters var3;
      try {
         Set var1 = var0.getTrustAnchors();
         X509CertStoreSelector var2 = X509CertStoreSelector.getInstance((X509CertSelector)var0.getTargetCertConstraints());
         var3 = new ExtendedPKIXBuilderParameters(var1, var2);
      } catch (Exception var5) {
         String var4 = var5.getMessage();
         throw new RuntimeException(var4);
      }

      var3.setParams(var0);
      return var3;
   }

   public Object clone() {
      ExtendedPKIXBuilderParameters var3;
      try {
         Set var1 = this.getTrustAnchors();
         Selector var2 = this.getTargetConstraints();
         var3 = new ExtendedPKIXBuilderParameters(var1, var2);
      } catch (Exception var5) {
         String var4 = var5.getMessage();
         throw new RuntimeException(var4);
      }

      var3.setParams(this);
      return var3;
   }

   public Set getExcludedCerts() {
      return Collections.unmodifiableSet(this.excludedCerts);
   }

   public int getMaxPathLength() {
      return this.maxPathLength;
   }

   public void setExcludedCerts(Set var1) {
      if(var1 == null) {
         Set var2 = Collections.EMPTY_SET;
      } else {
         HashSet var3 = new HashSet(var1);
         this.excludedCerts = var3;
      }
   }

   public void setMaxPathLength(int var1) {
      if(var1 < -1) {
         throw new InvalidParameterException("The maximum path length parameter can not be less than -1.");
      } else {
         this.maxPathLength = var1;
      }
   }

   protected void setParams(PKIXParameters var1) {
      super.setParams(var1);
      if(var1 instanceof ExtendedPKIXBuilderParameters) {
         ExtendedPKIXBuilderParameters var2 = (ExtendedPKIXBuilderParameters)var1;
         int var3 = var2.maxPathLength;
         this.maxPathLength = var3;
         Set var4 = var2.excludedCerts;
         HashSet var5 = new HashSet(var4);
         this.excludedCerts = var5;
      }

      if(var1 instanceof PKIXBuilderParameters) {
         int var6 = ((PKIXBuilderParameters)var1).getMaxPathLength();
         this.maxPathLength = var6;
      }
   }
}
