package myorg.bouncycastle.jce.provider;

import java.util.Collection;
import myorg.bouncycastle.util.CollectionStore;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509CollectionStoreParameters;
import myorg.bouncycastle.x509.X509StoreParameters;
import myorg.bouncycastle.x509.X509StoreSpi;

public class X509StoreCertPairCollection extends X509StoreSpi {

   private CollectionStore _store;


   public X509StoreCertPairCollection() {}

   public Collection engineGetMatches(Selector var1) {
      return this._store.getMatches(var1);
   }

   public void engineInit(X509StoreParameters var1) {
      if(!(var1 instanceof X509CollectionStoreParameters)) {
         StringBuilder var2 = (new StringBuilder()).append("Initialization parameters must be an instance of ");
         String var3 = X509CollectionStoreParameters.class.getName();
         String var4 = var2.append(var3).append(".").toString();
         throw new IllegalArgumentException(var4);
      } else {
         Collection var5 = ((X509CollectionStoreParameters)var1).getCollection();
         CollectionStore var6 = new CollectionStore(var5);
         this._store = var6;
      }
   }
}
