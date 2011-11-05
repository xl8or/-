package myorg.bouncycastle.jce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.engines.CamelliaEngine;
import myorg.bouncycastle.crypto.engines.CamelliaWrapEngine;
import myorg.bouncycastle.crypto.engines.RFC3211WrapEngine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.jce.provider.JCEBlockCipher;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameters;
import myorg.bouncycastle.jce.provider.WrapCipherSpi;

public final class Camellia {

   private Camellia() {}

   public static class CBC extends JCEBlockCipher {

      public CBC() {
         CamelliaEngine var1 = new CamelliaEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 128);
      }
   }

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         this(256);
      }

      public KeyGen(int var1) {
         CipherKeyGenerator var2 = new CipherKeyGenerator();
         super("Camellia", var1, var2);
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
            AlgorithmParameters var3 = AlgorithmParameters.getInstance("Camellia", "myBC");
            IvParameterSpec var4 = new IvParameterSpec(var1);
            var3.init(var4);
            return var3;
         } catch (Exception var6) {
            String var5 = var6.getMessage();
            throw new RuntimeException(var5);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Camellia parameter generation.");
      }
   }

   public static class RFC3211Wrap extends WrapCipherSpi {

      public RFC3211Wrap() {
         CamelliaEngine var1 = new CamelliaEngine();
         RFC3211WrapEngine var2 = new RFC3211WrapEngine(var1);
         super(var2, 16);
      }
   }

   public static class KeyGen192 extends Camellia.KeyGen {

      public KeyGen192() {
         super(192);
      }
   }

   public static class Wrap extends WrapCipherSpi {

      public Wrap() {
         CamelliaWrapEngine var1 = new CamelliaWrapEngine();
         super(var1);
      }
   }

   public static class KeyGen256 extends Camellia.KeyGen {

      public KeyGen256() {
         super(256);
      }
   }

   public static class AlgParams extends JDKAlgorithmParameters.IVAlgorithmParameters {

      public AlgParams() {}

      protected String engineToString() {
         return "Camellia IV";
      }
   }

   public static class KeyGen128 extends Camellia.KeyGen {

      public KeyGen128() {
         super(128);
      }
   }

   public static class ECB extends JCEBlockCipher {

      public ECB() {
         CamelliaEngine var1 = new CamelliaEngine();
         super(var1);
      }
   }
}
