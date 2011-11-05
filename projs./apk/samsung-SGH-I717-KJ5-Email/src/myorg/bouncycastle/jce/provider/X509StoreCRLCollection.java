package myorg.bouncycastle.jce.provider;

import java.util.Collection;
import myorg.bouncycastle.util.CollectionStore;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509CollectionStoreParameters;
import myorg.bouncycastle.x509.X509StoreParameters;
import myorg.bouncycastle.x509.X509StoreSpi;

public class X509StoreCRLCollection extends X509StoreSpi {

   private CollectionStore _store;


   public X509StoreCRLCollection() {}

   public Collection engineGetMatches(Selector var1) {
      return this._store.getMatches(var1);
   }

   public void engineInit(X509StoreParameters var1) {
      if(!(var1 instanceof X509CollectionStoreParameters)) {
         String var2 = var1.toString();
         throw new IllegalArgumentException(var2);
      } else {
         Collection var3 = ((X509CollectionStoreParameters)var1).getCollection();
         CollectionStore var4 = new CollectionStore(var3);
         this._store = var4;
      }
   }
}
