package myorg.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

public class NoekeonMappings extends HashMap {

   public NoekeonMappings() {
      Object var1 = this.put("AlgorithmParameters.NOEKEON", "myorg.bouncycastle.jce.provider.symmetric.Noekeon$AlgParams");
      Object var2 = this.put("AlgorithmParameterGenerator.NOEKEON", "myorg.bouncycastle.jce.provider.symmetric.Noekeon$AlgParamGen");
      Object var3 = this.put("Cipher.NOEKEON", "myorg.bouncycastle.jce.provider.symmetric.Noekeon$ECB");
      Object var4 = this.put("KeyGenerator.NOEKEON", "myorg.bouncycastle.jce.provider.symmetric.Noekeon$KeyGen");
   }
}
