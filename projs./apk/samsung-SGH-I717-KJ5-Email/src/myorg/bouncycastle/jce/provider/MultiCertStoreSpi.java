package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import myorg.bouncycastle.jce.MultiCertStoreParameters;

public class MultiCertStoreSpi extends CertStoreSpi {

   private MultiCertStoreParameters params;


   public MultiCertStoreSpi(CertStoreParameters var1) throws InvalidAlgorithmParameterException {
      super(var1);
      if(!(var1 instanceof MultiCertStoreParameters)) {
         StringBuilder var2 = (new StringBuilder()).append("myorg.bouncycastle.jce.provider.MultiCertStoreSpi: parameter must be a MultiCertStoreParameters object\n");
         String var3 = var1.toString();
         String var4 = var2.append(var3).toString();
         throw new InvalidAlgorithmParameterException(var4);
      } else {
         MultiCertStoreParameters var5 = (MultiCertStoreParameters)var1;
         this.params = var5;
      }
   }

   public Collection engineGetCRLs(CRLSelector var1) throws CertStoreException {
      boolean var2 = this.params.getSearchAllStores();
      Iterator var3 = this.params.getCertStores().iterator();
      Object var4;
      if(var2) {
         var4 = new ArrayList();
      } else {
         var4 = Collections.EMPTY_LIST;
      }

      Object var7;
      while(true) {
         if(var3.hasNext()) {
            Collection var5 = ((CertStore)var3.next()).getCRLs(var1);
            if(var2) {
               ((List)var4).addAll(var5);
               continue;
            }

            if(var5.isEmpty()) {
               continue;
            }

            var7 = var5;
            break;
         }

         var7 = var4;
         break;
      }

      return (Collection)var7;
   }

   public Collection engineGetCertificates(CertSelector var1) throws CertStoreException {
      boolean var2 = this.params.getSearchAllStores();
      Iterator var3 = this.params.getCertStores().iterator();
      Object var4;
      if(var2) {
         var4 = new ArrayList();
      } else {
         var4 = Collections.EMPTY_LIST;
      }

      Object var7;
      while(true) {
         if(var3.hasNext()) {
            Collection var5 = ((CertStore)var3.next()).getCertificates(var1);
            if(var2) {
               ((List)var4).addAll(var5);
               continue;
            }

            if(var5.isEmpty()) {
               continue;
            }

            var7 = var5;
            break;
         }

         var7 = var4;
         break;
      }

      return (Collection)var7;
   }
}
