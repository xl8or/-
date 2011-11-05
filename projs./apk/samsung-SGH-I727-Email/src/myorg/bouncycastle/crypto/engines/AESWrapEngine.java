package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.engines.AESEngine;
import myorg.bouncycastle.crypto.engines.RFC3394WrapEngine;

public class AESWrapEngine extends RFC3394WrapEngine {

   public AESWrapEngine() {
      AESEngine var1 = new AESEngine();
      super(var1);
   }
}
