package myorg.bouncycastle.x509;

import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509CertStoreSelector;
import myorg.bouncycastle.x509.X509CertificatePair;

public class X509CertPairStoreSelector implements Selector {

   private X509CertificatePair certPair;
   private X509CertStoreSelector forwardSelector;
   private X509CertStoreSelector reverseSelector;


   public X509CertPairStoreSelector() {}

   public Object clone() {
      X509CertPairStoreSelector var1 = new X509CertPairStoreSelector();
      X509CertificatePair var2 = this.certPair;
      var1.certPair = var2;
      if(this.forwardSelector != null) {
         X509CertStoreSelector var3 = (X509CertStoreSelector)this.forwardSelector.clone();
         var1.setForwardSelector(var3);
      }

      if(this.reverseSelector != null) {
         X509CertStoreSelector var4 = (X509CertStoreSelector)this.reverseSelector.clone();
         var1.setReverseSelector(var4);
      }

      return var1;
   }

   public X509CertificatePair getCertPair() {
      return this.certPair;
   }

   public X509CertStoreSelector getForwardSelector() {
      return this.forwardSelector;
   }

   public X509CertStoreSelector getReverseSelector() {
      return this.reverseSelector;
   }

   public boolean match(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public void setCertPair(X509CertificatePair var1) {
      this.certPair = var1;
   }

   public void setForwardSelector(X509CertStoreSelector var1) {
      this.forwardSelector = var1;
   }

   public void setReverseSelector(X509CertStoreSelector var1) {
      this.reverseSelector = var1;
   }
}
