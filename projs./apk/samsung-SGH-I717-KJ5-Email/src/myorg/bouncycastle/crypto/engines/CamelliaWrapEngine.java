package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.engines.CamelliaEngine;
import myorg.bouncycastle.crypto.engines.RFC3394WrapEngine;

public class CamelliaWrapEngine extends RFC3394WrapEngine {

   public CamelliaWrapEngine() {
      CamelliaEngine var1 = new CamelliaEngine();
      super(var1);
   }
}
