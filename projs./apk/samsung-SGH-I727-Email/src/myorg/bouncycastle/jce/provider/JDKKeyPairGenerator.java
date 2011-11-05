package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Hashtable;
import javax.crypto.spec.DHParameterSpec;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import myorg.bouncycastle.crypto.generators.DHParametersGenerator;
import myorg.bouncycastle.crypto.generators.DSAKeyPairGenerator;
import myorg.bouncycastle.crypto.generators.DSAParametersGenerator;
import myorg.bouncycastle.crypto.generators.ElGamalKeyPairGenerator;
import myorg.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import myorg.bouncycastle.crypto.generators.GOST3410KeyPairGenerator;
import myorg.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import myorg.bouncycastle.crypto.params.DHKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;
import myorg.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;
import myorg.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410KeyGenerationParameters;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;
import myorg.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import myorg.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import myorg.bouncycastle.jce.provider.JCEDHPrivateKey;
import myorg.bouncycastle.jce.provider.JCEDHPublicKey;
import myorg.bouncycastle.jce.provider.JCEElGamalPrivateKey;
import myorg.bouncycastle.jce.provider.JCEElGamalPublicKey;
import myorg.bouncycastle.jce.provider.JCERSAPrivateCrtKey;
import myorg.bouncycastle.jce.provider.JCERSAPublicKey;
import myorg.bouncycastle.jce.provider.JDKDSAPrivateKey;
import myorg.bouncycastle.jce.provider.JDKDSAPublicKey;
import myorg.bouncycastle.jce.provider.JDKGOST3410PrivateKey;
import myorg.bouncycastle.jce.provider.JDKGOST3410PublicKey;
import myorg.bouncycastle.jce.spec.ElGamalParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410ParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract class JDKKeyPairGenerator extends KeyPairGenerator {

   public JDKKeyPairGenerator(String var1) {
      super(var1);
   }

   public abstract KeyPair generateKeyPair();

   public abstract void initialize(int var1, SecureRandom var2);

   public static class RSA extends JDKKeyPairGenerator {

      static final BigInteger defaultPublicExponent = BigInteger.valueOf(65537L);
      static final int defaultTests = 12;
      RSAKeyPairGenerator engine;
      RSAKeyGenerationParameters param;


      public RSA() {
         super("RSA");
         RSAKeyPairGenerator var1 = new RSAKeyPairGenerator();
         this.engine = var1;
         BigInteger var2 = defaultPublicExponent;
         SecureRandom var3 = new SecureRandom();
         RSAKeyGenerationParameters var4 = new RSAKeyGenerationParameters(var2, var3, 2048, 12);
         this.param = var4;
         RSAKeyPairGenerator var5 = this.engine;
         RSAKeyGenerationParameters var6 = this.param;
         var5.init(var6);
      }

      public KeyPair generateKeyPair() {
         AsymmetricCipherKeyPair var1 = this.engine.generateKeyPair();
         RSAKeyParameters var2 = (RSAKeyParameters)var1.getPublic();
         RSAPrivateCrtKeyParameters var3 = (RSAPrivateCrtKeyParameters)var1.getPrivate();
         JCERSAPublicKey var4 = new JCERSAPublicKey(var2);
         JCERSAPrivateCrtKey var5 = new JCERSAPrivateCrtKey(var3);
         return new KeyPair(var4, var5);
      }

      public void initialize(int var1, SecureRandom var2) {
         BigInteger var3 = defaultPublicExponent;
         RSAKeyGenerationParameters var4 = new RSAKeyGenerationParameters(var3, var2, var1, 12);
         this.param = var4;
         RSAKeyPairGenerator var5 = this.engine;
         RSAKeyGenerationParameters var6 = this.param;
         var5.init(var6);
      }

      public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof RSAKeyGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
         } else {
            RSAKeyGenParameterSpec var3 = (RSAKeyGenParameterSpec)var1;
            BigInteger var4 = var3.getPublicExponent();
            int var5 = var3.getKeysize();
            RSAKeyGenerationParameters var6 = new RSAKeyGenerationParameters(var4, var2, var5, 12);
            this.param = var6;
            RSAKeyPairGenerator var7 = this.engine;
            RSAKeyGenerationParameters var8 = this.param;
            var7.init(var8);
         }
      }
   }

   public static class ElGamal extends JDKKeyPairGenerator {

      int certainty;
      ElGamalKeyPairGenerator engine;
      boolean initialised;
      ElGamalKeyGenerationParameters param;
      SecureRandom random;
      int strength;


      public ElGamal() {
         super("ElGamal");
         ElGamalKeyPairGenerator var1 = new ElGamalKeyPairGenerator();
         this.engine = var1;
         this.strength = 1024;
         this.certainty = 20;
         SecureRandom var2 = new SecureRandom();
         this.random = var2;
         this.initialised = (boolean)0;
      }

      public KeyPair generateKeyPair() {
         if(!this.initialised) {
            ElGamalParametersGenerator var1 = new ElGamalParametersGenerator();
            int var2 = this.strength;
            int var3 = this.certainty;
            SecureRandom var4 = this.random;
            var1.init(var2, var3, var4);
            SecureRandom var5 = this.random;
            ElGamalParameters var6 = var1.generateParameters();
            ElGamalKeyGenerationParameters var7 = new ElGamalKeyGenerationParameters(var5, var6);
            this.param = var7;
            ElGamalKeyPairGenerator var8 = this.engine;
            ElGamalKeyGenerationParameters var9 = this.param;
            var8.init(var9);
            this.initialised = (boolean)1;
         }

         AsymmetricCipherKeyPair var10 = this.engine.generateKeyPair();
         ElGamalPublicKeyParameters var11 = (ElGamalPublicKeyParameters)var10.getPublic();
         ElGamalPrivateKeyParameters var12 = (ElGamalPrivateKeyParameters)var10.getPrivate();
         JCEElGamalPublicKey var13 = new JCEElGamalPublicKey(var11);
         JCEElGamalPrivateKey var14 = new JCEElGamalPrivateKey(var12);
         return new KeyPair(var13, var14);
      }

      public void initialize(int var1, SecureRandom var2) {
         this.strength = var1;
         this.random = var2;
      }

      public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof ElGamalParameterSpec) && !(var1 instanceof DHParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec or an ElGamalParameterSpec");
         } else {
            if(var1 instanceof ElGamalParameterSpec) {
               ElGamalParameterSpec var3 = (ElGamalParameterSpec)var1;
               BigInteger var4 = var3.getP();
               BigInteger var5 = var3.getG();
               ElGamalParameters var6 = new ElGamalParameters(var4, var5);
               ElGamalKeyGenerationParameters var7 = new ElGamalKeyGenerationParameters(var2, var6);
               this.param = var7;
            } else {
               DHParameterSpec var10 = (DHParameterSpec)var1;
               BigInteger var11 = var10.getP();
               BigInteger var12 = var10.getG();
               int var13 = var10.getL();
               ElGamalParameters var14 = new ElGamalParameters(var11, var12, var13);
               ElGamalKeyGenerationParameters var15 = new ElGamalKeyGenerationParameters(var2, var14);
               this.param = var15;
            }

            ElGamalKeyPairGenerator var8 = this.engine;
            ElGamalKeyGenerationParameters var9 = this.param;
            var8.init(var9);
            this.initialised = (boolean)1;
         }
      }
   }

   public static class DSA extends JDKKeyPairGenerator {

      int certainty;
      DSAKeyPairGenerator engine;
      boolean initialised;
      DSAKeyGenerationParameters param;
      SecureRandom random;
      int strength;


      public DSA() {
         super("DSA");
         DSAKeyPairGenerator var1 = new DSAKeyPairGenerator();
         this.engine = var1;
         this.strength = 1024;
         this.certainty = 20;
         SecureRandom var2 = new SecureRandom();
         this.random = var2;
         this.initialised = (boolean)0;
      }

      public KeyPair generateKeyPair() {
         if(!this.initialised) {
            DSAParametersGenerator var1 = new DSAParametersGenerator();
            int var2 = this.strength;
            int var3 = this.certainty;
            SecureRandom var4 = this.random;
            var1.init(var2, var3, var4);
            SecureRandom var5 = this.random;
            DSAParameters var6 = var1.generateParameters();
            DSAKeyGenerationParameters var7 = new DSAKeyGenerationParameters(var5, var6);
            this.param = var7;
            DSAKeyPairGenerator var8 = this.engine;
            DSAKeyGenerationParameters var9 = this.param;
            var8.init(var9);
            this.initialised = (boolean)1;
         }

         AsymmetricCipherKeyPair var10 = this.engine.generateKeyPair();
         DSAPublicKeyParameters var11 = (DSAPublicKeyParameters)var10.getPublic();
         DSAPrivateKeyParameters var12 = (DSAPrivateKeyParameters)var10.getPrivate();
         JDKDSAPublicKey var13 = new JDKDSAPublicKey(var11);
         JDKDSAPrivateKey var14 = new JDKDSAPrivateKey(var12);
         return new KeyPair(var13, var14);
      }

      public void initialize(int var1, SecureRandom var2) {
         if(var1 >= 512 && var1 <= 1024 && var1 % 64 == 0) {
            this.strength = var1;
            this.random = var2;
         } else {
            throw new InvalidParameterException("strength must be from 512 - 1024 and a multiple of 64");
         }
      }

      public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof DSAParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
         } else {
            DSAParameterSpec var3 = (DSAParameterSpec)var1;
            BigInteger var4 = var3.getP();
            BigInteger var5 = var3.getQ();
            BigInteger var6 = var3.getG();
            DSAParameters var7 = new DSAParameters(var4, var5, var6);
            DSAKeyGenerationParameters var8 = new DSAKeyGenerationParameters(var2, var7);
            this.param = var8;
            DSAKeyPairGenerator var9 = this.engine;
            DSAKeyGenerationParameters var10 = this.param;
            var9.init(var10);
            this.initialised = (boolean)1;
         }
      }
   }

   public static class DH extends JDKKeyPairGenerator {

      private static Hashtable params = new Hashtable();
      int certainty;
      DHBasicKeyPairGenerator engine;
      boolean initialised;
      DHKeyGenerationParameters param;
      SecureRandom random;
      int strength;


      public DH() {
         super("DH");
         DHBasicKeyPairGenerator var1 = new DHBasicKeyPairGenerator();
         this.engine = var1;
         this.strength = 1024;
         this.certainty = 20;
         SecureRandom var2 = new SecureRandom();
         this.random = var2;
         this.initialised = (boolean)0;
      }

      public KeyPair generateKeyPair() {
         if(!this.initialised) {
            int var1 = this.strength;
            Integer var2 = new Integer(var1);
            if(params.containsKey(var2)) {
               DHKeyGenerationParameters var3 = (DHKeyGenerationParameters)params.get(var2);
               this.param = var3;
            } else {
               DHParametersGenerator var11 = new DHParametersGenerator();
               int var12 = this.strength;
               int var13 = this.certainty;
               SecureRandom var14 = this.random;
               var11.init(var12, var13, var14);
               SecureRandom var15 = this.random;
               DHParameters var16 = var11.generateParameters();
               DHKeyGenerationParameters var17 = new DHKeyGenerationParameters(var15, var16);
               this.param = var17;
               Hashtable var18 = params;
               DHKeyGenerationParameters var19 = this.param;
               var18.put(var2, var19);
            }

            DHBasicKeyPairGenerator var4 = this.engine;
            DHKeyGenerationParameters var5 = this.param;
            var4.init(var5);
            this.initialised = (boolean)1;
         }

         AsymmetricCipherKeyPair var6 = this.engine.generateKeyPair();
         DHPublicKeyParameters var7 = (DHPublicKeyParameters)var6.getPublic();
         DHPrivateKeyParameters var8 = (DHPrivateKeyParameters)var6.getPrivate();
         JCEDHPublicKey var9 = new JCEDHPublicKey(var7);
         JCEDHPrivateKey var10 = new JCEDHPrivateKey(var8);
         return new KeyPair(var9, var10);
      }

      public void initialize(int var1, SecureRandom var2) {
         this.strength = var1;
         this.random = var2;
      }

      public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof DHParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
         } else {
            DHParameterSpec var3 = (DHParameterSpec)var1;
            BigInteger var4 = var3.getP();
            BigInteger var5 = var3.getG();
            int var6 = var3.getL();
            DHParameters var7 = new DHParameters(var4, var5, (BigInteger)null, var6);
            DHKeyGenerationParameters var8 = new DHKeyGenerationParameters(var2, var7);
            this.param = var8;
            DHBasicKeyPairGenerator var9 = this.engine;
            DHKeyGenerationParameters var10 = this.param;
            var9.init(var10);
            this.initialised = (boolean)1;
         }
      }
   }

   public static class GOST3410 extends JDKKeyPairGenerator {

      GOST3410KeyPairGenerator engine;
      GOST3410ParameterSpec gost3410Params;
      boolean initialised;
      GOST3410KeyGenerationParameters param;
      SecureRandom random;
      int strength;


      public GOST3410() {
         super("GOST3410");
         GOST3410KeyPairGenerator var1 = new GOST3410KeyPairGenerator();
         this.engine = var1;
         this.strength = 1024;
         this.random = null;
         this.initialised = (boolean)0;
      }

      private void init(GOST3410ParameterSpec var1, SecureRandom var2) {
         GOST3410PublicKeyParameterSetSpec var3 = var1.getPublicKeyParameters();
         BigInteger var4 = var3.getP();
         BigInteger var5 = var3.getQ();
         BigInteger var6 = var3.getA();
         GOST3410Parameters var7 = new GOST3410Parameters(var4, var5, var6);
         GOST3410KeyGenerationParameters var8 = new GOST3410KeyGenerationParameters(var2, var7);
         this.param = var8;
         GOST3410KeyPairGenerator var9 = this.engine;
         GOST3410KeyGenerationParameters var10 = this.param;
         var9.init(var10);
         this.initialised = (boolean)1;
         this.gost3410Params = var1;
      }

      public KeyPair generateKeyPair() {
         if(!this.initialised) {
            String var1 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A.getId();
            GOST3410ParameterSpec var2 = new GOST3410ParameterSpec(var1);
            SecureRandom var3 = new SecureRandom();
            this.init(var2, var3);
         }

         AsymmetricCipherKeyPair var4 = this.engine.generateKeyPair();
         GOST3410PublicKeyParameters var5 = (GOST3410PublicKeyParameters)var4.getPublic();
         GOST3410PrivateKeyParameters var6 = (GOST3410PrivateKeyParameters)var4.getPrivate();
         GOST3410ParameterSpec var7 = this.gost3410Params;
         JDKGOST3410PublicKey var8 = new JDKGOST3410PublicKey(var5, var7);
         GOST3410ParameterSpec var9 = this.gost3410Params;
         JDKGOST3410PrivateKey var10 = new JDKGOST3410PrivateKey(var6, var9);
         return new KeyPair(var8, var10);
      }

      public void initialize(int var1, SecureRandom var2) {
         this.strength = var1;
         this.random = var2;
      }

      public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof GOST3410ParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a GOST3410ParameterSpec");
         } else {
            GOST3410ParameterSpec var3 = (GOST3410ParameterSpec)var1;
            this.init(var3, var2);
         }
      }
   }
}
