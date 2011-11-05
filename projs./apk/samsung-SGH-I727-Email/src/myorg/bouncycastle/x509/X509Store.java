package myorg.bouncycastle.x509;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Collection;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.util.Store;
import myorg.bouncycastle.x509.NoSuchStoreException;
import myorg.bouncycastle.x509.X509StoreParameters;
import myorg.bouncycastle.x509.X509StoreSpi;
import myorg.bouncycastle.x509.X509Util;

public class X509Store implements Store {

   private Provider _provider;
   private X509StoreSpi _spi;


   private X509Store(Provider var1, X509StoreSpi var2) {
      this._provider = var1;
      this._spi = var2;
   }

   private static X509Store createStore(X509Util.Implementation var0, X509StoreParameters var1) {
      X509StoreSpi var2 = (X509StoreSpi)var0.getEngine();
      var2.engineInit(var1);
      Provider var3 = var0.getProvider();
      return new X509Store(var3, var2);
   }

   public static X509Store getInstance(String var0, X509StoreParameters var1) throws NoSuchStoreException {
      try {
         X509Store var2 = createStore(X509Util.getImplementation("X509Store", var0), var1);
         return var2;
      } catch (NoSuchAlgorithmException var4) {
         String var3 = var4.getMessage();
         throw new NoSuchStoreException(var3);
      }
   }

   public static X509Store getInstance(String var0, X509StoreParameters var1, String var2) throws NoSuchStoreException, NoSuchProviderException {
      Provider var3 = X509Util.getProvider(var2);
      return getInstance(var0, var1, var3);
   }

   public static X509Store getInstance(String var0, X509StoreParameters var1, Provider var2) throws NoSuchStoreException {
      try {
         X509Store var3 = createStore(X509Util.getImplementation("X509Store", var0, var2), var1);
         return var3;
      } catch (NoSuchAlgorithmException var5) {
         String var4 = var5.getMessage();
         throw new NoSuchStoreException(var4);
      }
   }

   public Collection getMatches(Selector var1) {
      return this._spi.engineGetMatches(var1);
   }

   public Provider getProvider() {
      return this._provider;
   }
}
