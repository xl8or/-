package myorg.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.engines.NoekeonEngine;
import myorg.bouncycastle.jce.provider.JCEBlockCipher;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameters;

public final class Noekeon {

   private Noekeon() {}

   public static class AlgParams extends JDKAlgorithmParameters.IVAlgorithmParameters {

      public AlgParams() {}

      protected String engineToString() {
         return "Noekeon IV";
      }
   }

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Noekeon", 128, var1);
      }
   }

   public static class ECB extends JCEBlockCipher {

      public ECB() {
         NoekeonEngine var1 = new NoekeonEngine();
         super(var1);
      }
   }

   public static class AlgParamGen extends JDKAlgorithmParameterGenerator {

      public AlgParamGen() {}

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[16];
         if(this.random == null) {
            SecureRandom var2 = new SecureRandom();
            this.random = var2;
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var3 = AlgorithmParameters.getInstance("Noekeon", "myBC");
            IvParameterSpec var4 = new IvParameterSpec(var1);
            var3.init(var4);
            return var3;
         } catch (Exception var6) {
            String var5 = var6.getMessage();
            throw new RuntimeException(var5);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Noekeon parameter generation.");
      }
   }
}
