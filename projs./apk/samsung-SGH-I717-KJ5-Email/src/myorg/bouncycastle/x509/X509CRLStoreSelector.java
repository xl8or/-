package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.extension.X509ExtensionUtil;

public class X509CRLStoreSelector extends X509CRLSelector implements Selector {

   private X509AttributeCertificate attrCertChecking;
   private boolean completeCRLEnabled = 0;
   private boolean deltaCRLIndicator = 0;
   private byte[] issuingDistributionPoint = null;
   private boolean issuingDistributionPointEnabled = 0;
   private BigInteger maxBaseCRLNumber = null;


   public X509CRLStoreSelector() {}

   public static X509CRLStoreSelector getInstance(X509CRLSelector var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("cannot create from null selector");
      } else {
         X509CRLStoreSelector var1 = new X509CRLStoreSelector();
         X509Certificate var2 = var0.getCertificateChecking();
         var1.setCertificateChecking(var2);
         Date var3 = var0.getDateAndTime();
         var1.setDateAndTime(var3);

         try {
            Collection var4 = var0.getIssuerNames();
            var1.setIssuerNames(var4);
         } catch (IOException var9) {
            String var8 = var9.getMessage();
            throw new IllegalArgumentException(var8);
         }

         Collection var5 = var0.getIssuers();
         var1.setIssuers(var5);
         BigInteger var6 = var0.getMaxCRL();
         var1.setMaxCRLNumber(var6);
         BigInteger var7 = var0.getMinCRL();
         var1.setMinCRLNumber(var7);
         return var1;
      }
   }

   public Object clone() {
      X509CRLStoreSelector var1 = getInstance(this);
      boolean var2 = this.deltaCRLIndicator;
      var1.deltaCRLIndicator = var2;
      boolean var3 = this.completeCRLEnabled;
      var1.completeCRLEnabled = var3;
      BigInteger var4 = this.maxBaseCRLNumber;
      var1.maxBaseCRLNumber = var4;
      X509AttributeCertificate var5 = this.attrCertChecking;
      var1.attrCertChecking = var5;
      boolean var6 = this.issuingDistributionPointEnabled;
      var1.issuingDistributionPointEnabled = var6;
      byte[] var7 = Arrays.clone(this.issuingDistributionPoint);
      var1.issuingDistributionPoint = var7;
      return var1;
   }

   public X509AttributeCertificate getAttrCertificateChecking() {
      return this.attrCertChecking;
   }

   public byte[] getIssuingDistributionPoint() {
      return Arrays.clone(this.issuingDistributionPoint);
   }

   public BigInteger getMaxBaseCRLNumber() {
      return this.maxBaseCRLNumber;
   }

   public boolean isCompleteCRLEnabled() {
      return this.completeCRLEnabled;
   }

   public boolean isDeltaCRLIndicatorEnabled() {
      return this.deltaCRLIndicator;
   }

   public boolean isIssuingDistributionPointEnabled() {
      return this.issuingDistributionPointEnabled;
   }

   public boolean match(Object var1) {
      boolean var2;
      if(!(var1 instanceof X509CRL)) {
         var2 = false;
      } else {
         X509CRL var3 = (X509CRL)var1;
         DERInteger var4 = null;

         label55: {
            DERInteger var7;
            try {
               String var5 = X509Extensions.DeltaCRLIndicator.getId();
               byte[] var6 = var3.getExtensionValue(var5);
               if(var6 == null) {
                  break label55;
               }

               var7 = DERInteger.getInstance(X509ExtensionUtil.fromExtensionValue(var6));
            } catch (Exception var15) {
               var2 = false;
               return var2;
            }

            var4 = var7;
         }

         if(this.isDeltaCRLIndicatorEnabled() && var4 == null) {
            var2 = false;
         } else if(this.isCompleteCRLEnabled() && var4 != null) {
            var2 = false;
         } else {
            if(var4 != null && this.maxBaseCRLNumber != null) {
               BigInteger var9 = var4.getPositiveValue();
               BigInteger var10 = this.maxBaseCRLNumber;
               if(var9.compareTo(var10) == 1) {
                  var2 = false;
                  return var2;
               }
            }

            if(this.issuingDistributionPointEnabled) {
               String var11 = X509Extensions.IssuingDistributionPoint.getId();
               byte[] var12 = var3.getExtensionValue(var11);
               if(this.issuingDistributionPoint == null) {
                  if(var12 != null) {
                     var2 = false;
                     return var2;
                  }
               } else {
                  byte[] var13 = this.issuingDistributionPoint;
                  if(!Arrays.areEqual(var12, var13)) {
                     var2 = false;
                     return var2;
                  }
               }
            }

            X509CRL var14 = (X509CRL)var1;
            var2 = super.match(var14);
         }
      }

      return var2;
   }

   public boolean match(CRL var1) {
      return this.match((Object)var1);
   }

   public void setAttrCertificateChecking(X509AttributeCertificate var1) {
      this.attrCertChecking = var1;
   }

   public void setCompleteCRLEnabled(boolean var1) {
      this.completeCRLEnabled = var1;
   }

   public void setDeltaCRLIndicatorEnabled(boolean var1) {
      this.deltaCRLIndicator = var1;
   }

   public void setIssuingDistributionPoint(byte[] var1) {
      byte[] var2 = Arrays.clone(var1);
      this.issuingDistributionPoint = var2;
   }

   public void setIssuingDistributionPointEnabled(boolean var1) {
      this.issuingDistributionPointEnabled = var1;
   }

   public void setMaxBaseCRLNumber(BigInteger var1) {
      this.maxBaseCRLNumber = var1;
   }
}
