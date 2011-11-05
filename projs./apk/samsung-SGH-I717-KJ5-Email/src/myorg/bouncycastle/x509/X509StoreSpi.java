package myorg.bouncycastle.x509;

import java.util.Collection;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509StoreParameters;

public abstract class X509StoreSpi {

   public X509StoreSpi() {}

   public abstract Collection engineGetMatches(Selector var1);

   public abstract void engineInit(X509StoreParameters var1);
}
