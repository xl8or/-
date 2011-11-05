package myorg.bouncycastle.jce;

import java.security.cert.CertStoreParameters;
import java.util.Collection;

public class MultiCertStoreParameters implements CertStoreParameters {

   private Collection certStores;
   private boolean searchAllStores;


   public MultiCertStoreParameters(Collection var1) {
      this(var1, (boolean)1);
   }

   public MultiCertStoreParameters(Collection var1, boolean var2) {
      this.certStores = var1;
      this.searchAllStores = var2;
   }

   public Object clone() {
      return this;
   }

   public Collection getCertStores() {
      return this.certStores;
   }

   public boolean getSearchAllStores() {
      return this.searchAllStores;
   }
}
