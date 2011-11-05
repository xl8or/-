package myorg.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.engines.SEEDEngine;
import myorg.bouncycastle.crypto.engines.SEEDWrapEngine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.jce.provider.JCEBlockCipher;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameters;
import myorg.bouncycastle.jce.provider.WrapCipherSpi;

public final class SEED {

   private SEED() {}

   public static class CBC extends JCEBlockCipher {

      public CBC() {
         SEEDEngine var1 = new SEEDEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 128);
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
            AlgorithmParameters var3 = AlgorithmParameters.getInstance("SEED", "myBC");
            IvParameterSpec var4 = new IvParameterSpec(var1);
            var3.init(var4);
            return var3;
         } catch (Exception var6) {
            String var5 = var6.getMessage();
            throw new RuntimeException(var5);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for SEED parameter generation.");
      }
   }

   public static class AlgParams extends JDKAlgorithmParameters.IVAlgorithmParameters {

      public AlgParams() {}

      protected String engineToString() {
         return "SEED IV";
      }
   }

   public static class Wrap extends WrapCipherSpi {

      public Wrap() {
         SEEDWrapEngine var1 = new SEEDWrapEngine();
         super(var1);
      }
   }

   public static class ECB extends JCEBlockCipher {

      public ECB() {
         SEEDEngine var1 = new SEEDEngine();
         super(var1);
      }
   }

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("SEED", 128, var1);
      }
   }
}
