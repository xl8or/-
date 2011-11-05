package myorg.bouncycastle.jce.provider.symmetric;

import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.engines.Grainv1Engine;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JCEStreamCipher;

public final class Grainv1 {

   private Grainv1() {}

   public static class Base extends JCEStreamCipher {

      public Base() {
         Grainv1Engine var1 = new Grainv1Engine();
         super((StreamCipher)var1, 8);
      }
   }

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Grainv1", 80, var1);
      }
   }
}
