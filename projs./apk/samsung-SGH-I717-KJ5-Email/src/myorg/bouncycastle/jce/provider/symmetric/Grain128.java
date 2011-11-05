package myorg.bouncycastle.jce.provider.symmetric;

import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.engines.Grain128Engine;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JCEStreamCipher;

public final class Grain128 {

   private Grain128() {}

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Grain128", 128, var1);
      }
   }

   public static class Base extends JCEStreamCipher {

      public Base() {
         Grain128Engine var1 = new Grain128Engine();
         super((StreamCipher)var1, 12);
      }
   }
}
