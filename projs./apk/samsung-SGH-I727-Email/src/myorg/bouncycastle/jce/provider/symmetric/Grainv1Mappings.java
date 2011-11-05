package myorg.bouncycastle.jce.provider.symmetric;

import java.util.HashMap;

public class Grainv1Mappings extends HashMap {

   public Grainv1Mappings() {
      Object var1 = this.put("Cipher.Grainv1", "myorg.bouncycastle.jce.provider.symmetric.Grainv1$Base");
      Object var2 = this.put("KeyGenerator.Grainv1", "myorg.bouncycastle.jce.provider.symmetric.Grainv1$KeyGen");
   }
}
