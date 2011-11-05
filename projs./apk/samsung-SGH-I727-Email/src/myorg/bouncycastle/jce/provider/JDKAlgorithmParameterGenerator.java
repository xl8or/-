package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import myorg.bouncycastle.crypto.generators.DHParametersGenerator;
import myorg.bouncycastle.crypto.generators.DSAParametersGenerator;
import myorg.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import myorg.bouncycastle.crypto.generators.GOST3410ParametersGenerator;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;
import myorg.bouncycastle.jce.spec.GOST3410ParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract class JDKAlgorithmParameterGenerator extends AlgorithmParameterGeneratorSpi {

   protected SecureRandom random;
   protected int strength = 1024;


   public JDKAlgorithmParameterGenerator() {}

   protected void engineInit(int var1, SecureRandom var2) {
      this.strength = var1;
      this.random = var2;
   }

   public static class DSA extends JDKAlgorithmParameterGenerator {

      public DSA() {}

      protected AlgorithmParameters engineGenerateParameters() {
         DSAParametersGenerator var1 = new DSAParametersGenerator();
         if(this.random != null) {
            int var2 = this.strength;
            SecureRandom var3 = this.random;
            var1.init(var2, 20, var3);
         } else {
            int var10 = this.strength;
            SecureRandom var11 = new SecureRandom();
            var1.init(var10, 20, var11);
         }

         DSAParameters var4 = var1.generateParameters();

         try {
            AlgorithmParameters var5 = AlgorithmParameters.getInstance("DSA", "myBC");
            BigInteger var6 = var4.getP();
            BigInteger var7 = var4.getQ();
            BigInteger var8 = var4.getG();
            DSAParameterSpec var9 = new DSAParameterSpec(var6, var7, var8);
            var5.init(var9);
            return var5;
         } catch (Exception var13) {
            String var12 = var13.getMessage();
            throw new RuntimeException(var12);
         }
      }

      protected void engineInit(int var1, SecureRandom var2) {
         if(var1 >= 512 && var1 <= 1024 && var1 % 64 == 0) {
            this.strength = var1;
            this.random = var2;
         } else {
            throw new InvalidParameterException("strength must be from 512 - 1024 and a multiple of 64");
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
      }
   }

   public static class DH extends JDKAlgorithmParameterGenerator {

      private int l = 0;


      public DH() {}

      protected AlgorithmParameters engineGenerateParameters() {
         DHParametersGenerator var1 = new DHParametersGenerator();
         if(this.random != null) {
            int var2 = this.strength;
            SecureRandom var3 = this.random;
            var1.init(var2, 20, var3);
         } else {
            int var10 = this.strength;
            SecureRandom var11 = new SecureRandom();
            var1.init(var10, 20, var11);
         }

         DHParameters var4 = var1.generateParameters();

         try {
            AlgorithmParameters var5 = AlgorithmParameters.getInstance("DH", "myBC");
            BigInteger var6 = var4.getP();
            BigInteger var7 = var4.getG();
            int var8 = this.l;
            DHParameterSpec var9 = new DHParameterSpec(var6, var7, var8);
            var5.init(var9);
            return var5;
         } catch (Exception var13) {
            String var12 = var13.getMessage();
            throw new RuntimeException(var12);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof DHGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
         } else {
            DHGenParameterSpec var3 = (DHGenParameterSpec)var1;
            int var4 = var3.getPrimeSize();
            this.strength = var4;
            int var5 = var3.getExponentSize();
            this.l = var5;
            this.random = var2;
         }
      }
   }

   public static class RC2 extends JDKAlgorithmParameterGenerator {

      RC2ParameterSpec spec = null;


      public RC2() {}

      protected AlgorithmParameters engineGenerateParameters() {
         AlgorithmParameters var3;
         if(this.spec == null) {
            byte[] var1 = new byte[8];
            if(this.random == null) {
               SecureRandom var2 = new SecureRandom();
               this.random = var2;
            }

            this.random.nextBytes(var1);

            try {
               var3 = AlgorithmParameters.getInstance("RC2", "myBC");
               IvParameterSpec var4 = new IvParameterSpec(var1);
               var3.init(var4);
            } catch (Exception var9) {
               String var5 = var9.getMessage();
               throw new RuntimeException(var5);
            }
         } else {
            try {
               var3 = AlgorithmParameters.getInstance("RC2", "myBC");
               RC2ParameterSpec var6 = this.spec;
               var3.init(var6);
            } catch (Exception var8) {
               String var7 = var8.getMessage();
               throw new RuntimeException(var7);
            }
         }

         return var3;
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(var1 instanceof RC2ParameterSpec) {
            RC2ParameterSpec var3 = (RC2ParameterSpec)var1;
            this.spec = var3;
         } else {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC2 parameter generation.");
         }
      }
   }

   public static class ElGamal extends JDKAlgorithmParameterGenerator {

      private int l = 0;


      public ElGamal() {}

      protected AlgorithmParameters engineGenerateParameters() {
         ElGamalParametersGenerator var1 = new ElGamalParametersGenerator();
         if(this.random != null) {
            int var2 = this.strength;
            SecureRandom var3 = this.random;
            var1.init(var2, 20, var3);
         } else {
            int var10 = this.strength;
            SecureRandom var11 = new SecureRandom();
            var1.init(var10, 20, var11);
         }

         ElGamalParameters var4 = var1.generateParameters();

         try {
            AlgorithmParameters var5 = AlgorithmParameters.getInstance("ElGamal", "myBC");
            BigInteger var6 = var4.getP();
            BigInteger var7 = var4.getG();
            int var8 = this.l;
            DHParameterSpec var9 = new DHParameterSpec(var6, var7, var8);
            var5.init(var9);
            return var5;
         } catch (Exception var13) {
            String var12 = var13.getMessage();
            throw new RuntimeException(var12);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(!(var1 instanceof DHGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
         } else {
            DHGenParameterSpec var3 = (DHGenParameterSpec)var1;
            int var4 = var3.getPrimeSize();
            this.strength = var4;
            int var5 = var3.getExponentSize();
            this.l = var5;
            this.random = var2;
         }
      }
   }

   public static class DES extends JDKAlgorithmParameterGenerator {

      public DES() {}

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[8];
         if(this.random == null) {
            SecureRandom var2 = new SecureRandom();
            this.random = var2;
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var3 = AlgorithmParameters.getInstance("DES", "myBC");
            IvParameterSpec var4 = new IvParameterSpec(var1);
            var3.init(var4);
            return var3;
         } catch (Exception var6) {
            String var5 = var6.getMessage();
            throw new RuntimeException(var5);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DES parameter generation.");
      }
   }

   public static class GOST3410 extends JDKAlgorithmParameterGenerator {

      public GOST3410() {}

      protected AlgorithmParameters engineGenerateParameters() {
         GOST3410ParametersGenerator var1 = new GOST3410ParametersGenerator();
         if(this.random != null) {
            int var2 = this.strength;
            SecureRandom var3 = this.random;
            var1.init(var2, 2, var3);
         } else {
            int var11 = this.strength;
            SecureRandom var12 = new SecureRandom();
            var1.init(var11, 2, var12);
         }

         GOST3410Parameters var4 = var1.generateParameters();

         try {
            AlgorithmParameters var5 = AlgorithmParameters.getInstance("GOST3410", "myBC");
            BigInteger var6 = var4.getP();
            BigInteger var7 = var4.getQ();
            BigInteger var8 = var4.getA();
            GOST3410PublicKeyParameterSetSpec var9 = new GOST3410PublicKeyParameterSetSpec(var6, var7, var8);
            GOST3410ParameterSpec var10 = new GOST3410ParameterSpec(var9);
            var5.init(var10);
            return var5;
         } catch (Exception var14) {
            String var13 = var14.getMessage();
            throw new RuntimeException(var13);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST3410 parameter generation.");
      }
   }
}
