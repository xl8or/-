package myorg.bouncycastle.util;

import java.util.Collection;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.util.StoreException;

public interface Store {

   Collection getMatches(Selector var1) throws StoreException;
}
