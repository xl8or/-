package myorg.bouncycastle.jce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.misc.CAST5CBCParameters;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.engines.CAST5Engine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.jce.provider.JCEBlockCipher;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameters;

public final class CAST5 {

   private CAST5() {}

   public static class ECB extends JCEBlockCipher {

      public ECB() {
         CAST5Engine var1 = new CAST5Engine();
         super(var1);
      }
   }

   public static class AlgParams extends JDKAlgorithmParameters {

      private byte[] iv;
      private int keyLength = 128;


      public AlgParams() {}

      protected byte[] engineGetEncoded() {
         byte[] var1 = new byte[this.iv.length];
         byte[] var2 = this.iv;
         int var3 = this.iv.length;
         System.arraycopy(var2, 0, var1, 0, var3);
         return var1;
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         byte[] var4;
         if(this.isASN1FormatString(var1)) {
            byte[] var2 = this.engineGetEncoded();
            int var3 = this.keyLength;
            var4 = (new CAST5CBCParameters(var2, var3)).getEncoded();
         } else if(var1.equals("RAW")) {
            var4 = this.engineGetEncoded();
         } else {
            var4 = null;
         }

         return var4;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(var1 instanceof IvParameterSpec) {
            byte[] var2 = ((IvParameterSpec)var1).getIV();
            this.iv = var2;
         } else {
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a CAST5 parameters algorithm parameters object");
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         byte[] var2 = new byte[var1.length];
         this.iv = var2;
         byte[] var3 = this.iv;
         int var4 = this.iv.length;
         System.arraycopy(var1, 0, var3, 0, var4);
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(this.isASN1FormatString(var2)) {
            CAST5CBCParameters var3 = CAST5CBCParameters.getInstance((new ASN1InputStream(var1)).readObject());
            int var4 = var3.getKeyLength();
            this.keyLength = var4;
            byte[] var5 = var3.getIV();
            this.iv = var5;
         } else if(var2.equals("RAW")) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in IV parameters object");
         }
      }

      protected String engineToString() {
         return "CAST5 Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == IvParameterSpec.class) {
            byte[] var2 = this.iv;
            return new IvParameterSpec(var2);
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to CAST5 parameters object.");
         }
      }
   }

   public static class CBC extends JCEBlockCipher {

      public CBC() {
         CAST5Engine var1 = new CAST5Engine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class AlgParamGen extends JDKAlgorithmParameterGenerator {

      public AlgParamGen() {}

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[8];
         if(this.random == null) {
            SecureRandom var2 = new SecureRandom();
            this.random = var2;
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var3 = AlgorithmParameters.getInstance("CAST5", "myBC");
            IvParameterSpec var4 = new IvParameterSpec(var1);
            var3.init(var4);
            return var3;
         } catch (Exception var6) {
            String var5 = var6.getMessage();
            throw new RuntimeException(var5);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for CAST5 parameter generation.");
      }
   }

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("CAST5", 128, var1);
      }
   }
}
