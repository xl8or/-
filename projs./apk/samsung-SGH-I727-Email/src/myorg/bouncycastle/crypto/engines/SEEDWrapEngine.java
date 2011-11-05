package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.engines.RFC3394WrapEngine;
import myorg.bouncycastle.crypto.engines.SEEDEngine;

public class SEEDWrapEngine extends RFC3394WrapEngine {

   public SEEDWrapEngine() {
      SEEDEngine var1 = new SEEDEngine();
      super(var1);
   }
}
