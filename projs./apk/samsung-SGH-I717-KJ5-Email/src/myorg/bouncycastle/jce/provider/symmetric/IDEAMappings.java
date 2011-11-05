package myorg.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

public class IDEAMappings extends HashMap {

   public IDEAMappings() {
      Object var1 = this.put("AlgorithmParameterGenerator.IDEA", "myorg.bouncycastle.jce.provider.symmetric.IDEA$AlgParamGen");
      Object var2 = this.put("AlgorithmParameterGenerator.1.3.6.1.4.1.188.7.1.1.2", "myorg.bouncycastle.jce.provider.symmetric.IDEA$AlgParamGen");
      Object var3 = this.put("AlgorithmParameters.IDEA", "myorg.bouncycastle.jce.provider.symmetric.IDEA$AlgParams");
      Object var4 = this.put("AlgorithmParameters.1.3.6.1.4.1.188.7.1.1.2", "myorg.bouncycastle.jce.provider.symmetric.IDEA$AlgParams");
      Object var5 = this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA", "PKCS12PBE");
      Object var6 = this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA", "PKCS12PBE");
      Object var7 = this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA-CBC", "PKCS12PBE");
      Object var8 = this.put("Cipher.IDEA", "myorg.bouncycastle.jce.provider.symmetric.IDEA$ECB");
      Object var9 = this.put("Cipher.1.3.6.1.4.1.188.7.1.1.2", "myorg.bouncycastle.jce.provider.symmetric.IDEA$CBC");
      Object var10 = this.put("Cipher.PBEWITHSHAANDIDEA-CBC", "myorg.bouncycastle.jce.provider.symmetric.IDEA$PBEWithSHAAndIDEA");
      Object var11 = this.put("KeyGenerator.IDEA", "myorg.bouncycastle.jce.provider.symmetric.IDEA$KeyGen");
      Object var12 = this.put("KeyGenerator.1.3.6.1.4.1.188.7.1.1.2", "myorg.bouncycastle.jce.provider.symmetric.IDEA$KeyGen");
      Object var13 = this.put("SecretKeyFactory.PBEWITHSHAANDIDEA-CBC", "myorg.bouncycastle.jce.provider.symmetric.IDEA$PBEWithSHAAndIDEAKeyGen");
      Object var14 = this.put("Mac.IDEAMAC", "myorg.bouncycastle.jce.provider.symmetric.IDEA$Mac");
      Object var15 = this.put("Alg.Alias.Mac.IDEA", "IDEAMAC");
      Object var16 = this.put("Mac.IDEAMAC/CFB8", "myorg.bouncycastle.jce.provider.symmetric.IDEA$CFB8Mac");
      Object var17 = this.put("Alg.Alias.Mac.IDEA/CFB8", "IDEAMAC/CFB8");
   }
}
