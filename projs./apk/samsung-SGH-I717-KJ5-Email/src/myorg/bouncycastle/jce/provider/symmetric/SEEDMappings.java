package myorg.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.kisa.KISAObjectIdentifiers;

public class SEEDMappings extends HashMap {

   public SEEDMappings() {
      Object var1 = this.put("AlgorithmParameters.SEED", "myorg.bouncycastle.jce.provider.symmetric.SEED$AlgParams");
      StringBuilder var2 = (new StringBuilder()).append("Alg.Alias.AlgorithmParameters.");
      DERObjectIdentifier var3 = KISAObjectIdentifiers.id_seedCBC;
      String var4 = var2.append(var3).toString();
      this.put(var4, "SEED");
      Object var6 = this.put("AlgorithmParameterGenerator.SEED", "myorg.bouncycastle.jce.provider.symmetric.SEED$AlgParamGen");
      StringBuilder var7 = (new StringBuilder()).append("Alg.Alias.AlgorithmParameterGenerator.");
      DERObjectIdentifier var8 = KISAObjectIdentifiers.id_seedCBC;
      String var9 = var7.append(var8).toString();
      this.put(var9, "SEED");
      Object var11 = this.put("Cipher.SEED", "myorg.bouncycastle.jce.provider.symmetric.SEED$ECB");
      StringBuilder var12 = (new StringBuilder()).append("Cipher.");
      DERObjectIdentifier var13 = KISAObjectIdentifiers.id_seedCBC;
      String var14 = var12.append(var13).toString();
      this.put(var14, "myorg.bouncycastle.jce.provider.symmetric.SEED$CBC");
      Object var16 = this.put("Cipher.SEEDWRAP", "myorg.bouncycastle.jce.provider.symmetric.SEED$Wrap");
      StringBuilder var17 = (new StringBuilder()).append("Alg.Alias.Cipher.");
      DERObjectIdentifier var18 = KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap;
      String var19 = var17.append(var18).toString();
      this.put(var19, "SEEDWRAP");
      Object var21 = this.put("KeyGenerator.SEED", "myorg.bouncycastle.jce.provider.symmetric.SEED$KeyGen");
      StringBuilder var22 = (new StringBuilder()).append("KeyGenerator.");
      DERObjectIdentifier var23 = KISAObjectIdentifiers.id_seedCBC;
      String var24 = var22.append(var23).toString();
      this.put(var24, "myorg.bouncycastle.jce.provider.symmetric.SEED$KeyGen");
      StringBuilder var26 = (new StringBuilder()).append("KeyGenerator.");
      DERObjectIdentifier var27 = KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap;
      String var28 = var26.append(var27).toString();
      this.put(var28, "myorg.bouncycastle.jce.provider.symmetric.SEED$KeyGen");
   }
}
