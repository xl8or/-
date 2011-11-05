package myorg.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

public class CAST5Mappings extends HashMap {

   public CAST5Mappings() {
      Object var1 = this.put("AlgorithmParameters.CAST5", "myorg.bouncycastle.jce.provider.symmetric.CAST5$AlgParams");
      Object var2 = this.put("Alg.Alias.AlgorithmParameters.1.2.840.113533.7.66.10", "CAST5");
      Object var3 = this.put("AlgorithmParameterGenerator.CAST5", "myorg.bouncycastle.jce.provider.symmetric.CAST5$AlgParamGen");
      Object var4 = this.put("Alg.Alias.AlgorithmParameterGenerator.1.2.840.113533.7.66.10", "CAST5");
      Object var5 = this.put("Cipher.CAST5", "myorg.bouncycastle.jce.provider.symmetric.CAST5$ECB");
      Object var6 = this.put("Cipher.1.2.840.113533.7.66.10", "myorg.bouncycastle.jce.provider.symmetric.CAST5$CBC");
      Object var7 = this.put("KeyGenerator.CAST5", "myorg.bouncycastle.jce.provider.symmetric.CAST5$KeyGen");
      Object var8 = this.put("Alg.Alias.KeyGenerator.1.2.840.113533.7.66.10", "CAST5");
   }
}
