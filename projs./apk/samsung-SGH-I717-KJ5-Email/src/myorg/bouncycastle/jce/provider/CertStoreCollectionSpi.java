package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CertStoreCollectionSpi extends CertStoreSpi {

   private CollectionCertStoreParameters params;


   public CertStoreCollectionSpi(CertStoreParameters var1) throws InvalidAlgorithmParameterException {
      super(var1);
      if(!(var1 instanceof CollectionCertStoreParameters)) {
         StringBuilder var2 = (new StringBuilder()).append("myorg.bouncycastle.jce.provider.CertStoreCollectionSpi: parameter must be a CollectionCertStoreParameters object\n");
         String var3 = var1.toString();
         String var4 = var2.append(var3).toString();
         throw new InvalidAlgorithmParameterException(var4);
      } else {
         CollectionCertStoreParameters var5 = (CollectionCertStoreParameters)var1;
         this.params = var5;
      }
   }

   public Collection engineGetCRLs(CRLSelector var1) throws CertStoreException {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.params.getCollection().iterator();
      if(var1 == null) {
         while(var3.hasNext()) {
            Object var4 = var3.next();
            if(var4 instanceof CRL) {
               var2.add(var4);
            }
         }
      } else {
         while(var3.hasNext()) {
            Object var6 = var3.next();
            if(var6 instanceof CRL) {
               CRL var7 = (CRL)var6;
               if(var1.match(var7)) {
                  var2.add(var6);
               }
            }
         }
      }

      return var2;
   }

   public Collection engineGetCertificates(CertSelector var1) throws CertStoreException {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.params.getCollection().iterator();
      if(var1 == null) {
         while(var3.hasNext()) {
            Object var4 = var3.next();
            if(var4 instanceof Certificate) {
               var2.add(var4);
            }
         }
      } else {
         while(var3.hasNext()) {
            Object var6 = var3.next();
            if(var6 instanceof Certificate) {
               Certificate var7 = (Certificate)var6;
               if(var1.match(var7)) {
                  var2.add(var6);
               }
            }
         }
      }

      return var2;
   }
}
