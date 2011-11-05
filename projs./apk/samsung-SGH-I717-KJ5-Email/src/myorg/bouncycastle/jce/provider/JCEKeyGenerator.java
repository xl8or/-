package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.KeyGenerationParameters;
import myorg.bouncycastle.crypto.generators.DESKeyGenerator;
import myorg.bouncycastle.crypto.generators.DESedeKeyGenerator;

public class JCEKeyGenerator extends KeyGeneratorSpi {

   protected String algName;
   protected int defaultKeySize;
   protected CipherKeyGenerator engine;
   protected int keySize;
   protected boolean uninitialised = 1;


   protected JCEKeyGenerator(String var1, int var2, CipherKeyGenerator var3) {
      this.algName = var1;
      this.defaultKeySize = var2;
      this.keySize = var2;
      this.engine = var3;
   }

   protected SecretKey engineGenerateKey() {
      if(this.uninitialised) {
         CipherKeyGenerator var1 = this.engine;
         SecureRandom var2 = new SecureRandom();
         int var3 = this.defaultKeySize;
         KeyGenerationParameters var4 = new KeyGenerationParameters(var2, var3);
         var1.init(var4);
         this.uninitialised = (boolean)0;
      }

      byte[] var5 = this.engine.generateKey();
      String var6 = this.algName;
      return new SecretKeySpec(var5, var6);
   }

   protected void engineInit(int var1, SecureRandom var2) {
      try {
         CipherKeyGenerator var3 = this.engine;
         KeyGenerationParameters var4 = new KeyGenerationParameters(var2, var1);
         var3.init(var4);
         this.uninitialised = (boolean)0;
      } catch (IllegalArgumentException var6) {
         String var5 = var6.getMessage();
         throw new InvalidParameterException(var5);
      }
   }

   protected void engineInit(SecureRandom var1) {
      if(var1 != null) {
         CipherKeyGenerator var2 = this.engine;
         int var3 = this.defaultKeySize;
         KeyGenerationParameters var4 = new KeyGenerationParameters(var1, var3);
         var2.init(var4);
         this.uninitialised = (boolean)0;
      }
   }

   protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      throw new InvalidAlgorithmParameterException("Not Implemented");
   }

   public static class HMACSHA384 extends JCEKeyGenerator {

      public HMACSHA384() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACSHA384", 384, var1);
      }
   }

   public static class Blowfish extends JCEKeyGenerator {

      public Blowfish() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Blowfish", 128, var1);
      }
   }

   public static class MD4HMAC extends JCEKeyGenerator {

      public MD4HMAC() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACMD4", 128, var1);
      }
   }

   public static class RC564 extends JCEKeyGenerator {

      public RC564() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("RC5-64", 256, var1);
      }
   }

   public static class CAST6 extends JCEKeyGenerator {

      public CAST6() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("CAST6", 256, var1);
      }
   }

   public static class RC2 extends JCEKeyGenerator {

      public RC2() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("RC2", 128, var1);
      }
   }

   public static class RC5 extends JCEKeyGenerator {

      public RC5() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("RC5", 128, var1);
      }
   }

   public static class RC4 extends JCEKeyGenerator {

      public RC4() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("RC4", 128, var1);
      }
   }

   public static class HC256 extends JCEKeyGenerator {

      public HC256() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HC256", 256, var1);
      }
   }

   public static class RC6 extends JCEKeyGenerator {

      public RC6() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("RC6", 256, var1);
      }
   }

   public static class MD2HMAC extends JCEKeyGenerator {

      public MD2HMAC() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACMD2", 128, var1);
      }
   }

   public static class GOST28147 extends JCEKeyGenerator {

      public GOST28147() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("GOST28147", 256, var1);
      }
   }

   public static class MD5HMAC extends JCEKeyGenerator {

      public MD5HMAC() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACMD5", 128, var1);
      }
   }

   public static class DESede3 extends JCEKeyGenerator {

      public DESede3() {
         DESedeKeyGenerator var1 = new DESedeKeyGenerator();
         super("DESede3", 192, var1);
      }
   }

   public static class DESede extends JCEKeyGenerator {

      private boolean keySizeSet;


      public DESede() {
         DESedeKeyGenerator var1 = new DESedeKeyGenerator();
         super("DESede", 192, var1);
         this.keySizeSet = (boolean)0;
      }

      protected SecretKey engineGenerateKey() {
         if(this.uninitialised) {
            CipherKeyGenerator var1 = this.engine;
            SecureRandom var2 = new SecureRandom();
            int var3 = this.defaultKeySize;
            KeyGenerationParameters var4 = new KeyGenerationParameters(var2, var3);
            var1.init(var4);
            this.uninitialised = (boolean)0;
         }

         SecretKeySpec var7;
         if(!this.keySizeSet) {
            byte[] var5 = this.engine.generateKey();
            System.arraycopy(var5, 0, var5, 16, 8);
            String var6 = this.algName;
            var7 = new SecretKeySpec(var5, var6);
         } else {
            byte[] var8 = this.engine.generateKey();
            String var9 = this.algName;
            var7 = new SecretKeySpec(var8, var9);
         }

         return var7;
      }

      protected void engineInit(int var1, SecureRandom var2) {
         super.engineInit(var1, var2);
         this.keySizeSet = (boolean)1;
      }
   }

   public static class HMACSHA256 extends JCEKeyGenerator {

      public HMACSHA256() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACSHA256", 256, var1);
      }
   }

   public static class HMACSHA512 extends JCEKeyGenerator {

      public HMACSHA512() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACSHA512", 512, var1);
      }
   }

   public static class HMACTIGER extends JCEKeyGenerator {

      public HMACTIGER() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACTIGER", 192, var1);
      }
   }

   public static class Salsa20 extends JCEKeyGenerator {

      public Salsa20() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Salsa20", 128, var1);
      }
   }

   public static class Serpent extends JCEKeyGenerator {

      public Serpent() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Serpent", 192, var1);
      }
   }

   public static class HC128 extends JCEKeyGenerator {

      public HC128() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HC128", 128, var1);
      }
   }

   public static class TEA extends JCEKeyGenerator {

      public TEA() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("TEA", 128, var1);
      }
   }

   public static class RIPEMD128HMAC extends JCEKeyGenerator {

      public RIPEMD128HMAC() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACRIPEMD128", 128, var1);
      }
   }

   public static class Twofish extends JCEKeyGenerator {

      public Twofish() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Twofish", 256, var1);
      }
   }

   public static class VMPCKSA3 extends JCEKeyGenerator {

      public VMPCKSA3() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("VMPC-KSA3", 128, var1);
      }
   }

   public static class XTEA extends JCEKeyGenerator {

      public XTEA() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("XTEA", 128, var1);
      }
   }

   public static class RIPEMD160HMAC extends JCEKeyGenerator {

      public RIPEMD160HMAC() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACRIPEMD160", 160, var1);
      }
   }

   public static class DES extends JCEKeyGenerator {

      public DES() {
         DESKeyGenerator var1 = new DESKeyGenerator();
         super("DES", 64, var1);
      }
   }

   public static class Skipjack extends JCEKeyGenerator {

      public Skipjack() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("SKIPJACK", 80, var1);
      }
   }

   public static class HMACSHA1 extends JCEKeyGenerator {

      public HMACSHA1() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACSHA1", 160, var1);
      }
   }

   public static class Rijndael extends JCEKeyGenerator {

      public Rijndael() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("Rijndael", 192, var1);
      }
   }

   public static class VMPC extends JCEKeyGenerator {

      public VMPC() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("VMPC", 128, var1);
      }
   }

   public static class HMACSHA224 extends JCEKeyGenerator {

      public HMACSHA224() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("HMACSHA224", 224, var1);
      }
   }
}
