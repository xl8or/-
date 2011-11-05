package myorg.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

public class Grain128Mappings extends HashMap {

   public Grain128Mappings() {
      Object var1 = this.put("Cipher.Grain128", "myorg.bouncycastle.jce.provider.symmetric.Grain128$Base");
      Object var2 = this.put("KeyGenerator.Grain128", "myorg.bouncycastle.jce.provider.symmetric.Grain128$KeyGen");
   }
}
