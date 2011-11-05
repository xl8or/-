package myorg.bouncycastle.jce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import myorg.bouncycastle.asn1.nist.NISTNamedCurves;
import myorg.bouncycastle.asn1.sec.SECNamedCurves;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import myorg.bouncycastle.asn1.x9.X962NamedCurves;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.generators.ECKeyPairGenerator;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.jce.provider.JCEECPrivateKey;
import myorg.bouncycastle.jce.provider.JCEECPublicKey;
import myorg.bouncycastle.jce.provider.JDKKeyPairGenerator;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import myorg.bouncycastle.jce.spec.ECNamedCurveSpec;
import myorg.bouncycastle.jce.spec.ECParameterSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public abstract class KeyPairGenerator extends JDKKeyPairGenerator {

   public KeyPairGenerator(String var1) {
      super(var1);
   }

   public static class ECDHC extends KeyPairGenerator.EC {

      public ECDHC() {
         super("ECDHC");
      }
   }

   public static class ECDSA extends KeyPairGenerator.EC {

      public ECDSA() {
         super("ECDSA");
      }
   }

   public static class ECGOST3410 extends KeyPairGenerator.EC {

      public ECGOST3410() {
         super("ECGOST3410");
      }
   }

   public static class ECMQV extends KeyPairGenerator.EC {

      public ECMQV() {
         super("ECMQV");
      }
   }

   public static class EC extends KeyPairGenerator {

      private static Hashtable ecParameters = new Hashtable();
      String algorithm;
      int certainty;
      Object ecParams;
      ECKeyPairGenerator engine;
      boolean initialised;
      ECKeyGenerationParameters param;
      SecureRandom random;
      int strength;


      static {
         Hashtable var0 = ecParameters;
         Integer var1 = new Integer(192);
         ECGenParameterSpec var2 = new ECGenParameterSpec("prime192v1");
         var0.put(var1, var2);
         Hashtable var4 = ecParameters;
         Integer var5 = new Integer(239);
         ECGenParameterSpec var6 = new ECGenParameterSpec("prime239v1");
         var4.put(var5, var6);
         Hashtable var8 = ecParameters;
         Integer var9 = new Integer(256);
         ECGenParameterSpec var10 = new ECGenParameterSpec("prime256v1");
         var8.put(var9, var10);
         Hashtable var12 = ecParameters;
         Integer var13 = new Integer(224);
         ECGenParameterSpec var14 = new ECGenParameterSpec("P-224");
         var12.put(var13, var14);
         Hashtable var16 = ecParameters;
         Integer var17 = new Integer(384);
         ECGenParameterSpec var18 = new ECGenParameterSpec("P-384");
         var16.put(var17, var18);
         Hashtable var20 = ecParameters;
         Integer var21 = new Integer(521);
         ECGenParameterSpec var22 = new ECGenParameterSpec("P-521");
         var20.put(var21, var22);
      }

      public EC() {
         super("EC");
         ECKeyPairGenerator var1 = new ECKeyPairGenerator();
         this.engine = var1;
         this.ecParams = null;
         this.strength = 239;
         this.certainty = 50;
         SecureRandom var2 = new SecureRandom();
         this.random = var2;
         this.initialised = (boolean)0;
         this.algorithm = "EC";
      }

      public EC(String var1) {
         super(var1);
         ECKeyPairGenerator var2 = new ECKeyPairGenerator();
         this.engine = var2;
         this.ecParams = null;
         this.strength = 239;
         this.certainty = 50;
         SecureRandom var3 = new SecureRandom();
         this.random = var3;
         this.initialised = (boolean)0;
         this.algorithm = var1;
      }

      public KeyPair generateKeyPair() {
         if(!this.initialised) {
            throw new IllegalStateException("EC Key Pair Generator not initialised");
         } else {
            AsymmetricCipherKeyPair var1 = this.engine.generateKeyPair();
            ECPublicKeyParameters var2 = (ECPublicKeyParameters)var1.getPublic();
            ECPrivateKeyParameters var3 = (ECPrivateKeyParameters)var1.getPrivate();
            KeyPair var9;
            if(this.ecParams instanceof ECParameterSpec) {
               ECParameterSpec var4 = (ECParameterSpec)this.ecParams;
               String var5 = this.algorithm;
               JCEECPublicKey var6 = new JCEECPublicKey(var5, var2, var4);
               String var7 = this.algorithm;
               JCEECPrivateKey var8 = new JCEECPrivateKey(var7, var3, var6, var4);
               var9 = new KeyPair(var6, var8);
            } else if(this.ecParams == null) {
               String var10 = this.algorithm;
               JCEECPublicKey var11 = new JCEECPublicKey(var10, var2);
               String var12 = this.algorithm;
               JCEECPrivateKey var13 = new JCEECPrivateKey(var12, var3);
               var9 = new KeyPair(var11, var13);
            } else {
               java.security.spec.ECParameterSpec var14 = (java.security.spec.ECParameterSpec)this.ecParams;
               String var15 = this.algorithm;
               JCEECPublicKey var16 = new JCEECPublicKey(var15, var2, var14);
               String var17 = this.algorithm;
               JCEECPrivateKey var18 = new JCEECPrivateKey(var17, var3, var16, var14);
               var9 = new KeyPair(var16, var18);
            }

            return var9;
         }
      }

      public void initialize(int var1, SecureRandom var2) {
         this.strength = var1;
         this.random = var2;
         Hashtable var3 = ecParameters;
         Integer var4 = new Integer(var1);
         Object var5 = var3.get(var4);
         this.ecParams = var5;
         if(this.ecParams != null) {
            try {
               ECGenParameterSpec var6 = (ECGenParameterSpec)this.ecParams;
               this.initialize(var6, var2);
            } catch (InvalidAlgorithmParameterException var8) {
               throw new InvalidParameterException("key size not configurable.");
            }
         } else {
            throw new InvalidParameterException("unknown key size.");
         }
      }

      public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         if(var1 instanceof ECParameterSpec) {
            ECParameterSpec var3 = (ECParameterSpec)var1;
            this.ecParams = var1;
            ECKeyGenerationParameters var5 = new ECKeyGenerationParameters;
            ECCurve var6 = var3.getCurve();
            ECPoint var7 = var3.getG();
            BigInteger var8 = var3.getN();
            ECDomainParameters var9 = new ECDomainParameters(var6, var7, var8);
            var5.<init>(var9, var2);
            this.param = var5;
            ECKeyPairGenerator var14 = this.engine;
            ECKeyGenerationParameters var15 = this.param;
            var14.init(var15);
            byte var16 = 1;
            this.initialised = (boolean)var16;
         } else if(var1 instanceof java.security.spec.ECParameterSpec) {
            java.security.spec.ECParameterSpec var17 = (java.security.spec.ECParameterSpec)var1;
            this.ecParams = var1;
            ECCurve var19 = EC5Util.convertCurve(var17.getCurve());
            java.security.spec.ECPoint var20 = var17.getGenerator();
            ECPoint var21 = EC5Util.convertPoint(var19, var20, (boolean)0);
            ECKeyGenerationParameters var22 = new ECKeyGenerationParameters;
            BigInteger var23 = var17.getOrder();
            BigInteger var24 = BigInteger.valueOf((long)var17.getCofactor());
            ECDomainParameters var25 = new ECDomainParameters(var19, var21, var23, var24);
            var22.<init>(var25, var2);
            this.param = var22;
            ECKeyPairGenerator var30 = this.engine;
            ECKeyGenerationParameters var31 = this.param;
            var30.init(var31);
            byte var32 = 1;
            this.initialised = (boolean)var32;
         } else if(var1 instanceof ECGenParameterSpec) {
            String var33 = ((ECGenParameterSpec)var1).getName();
            if(this.algorithm.equals("ECGOST3410")) {
               ECDomainParameters var34 = ECGOST3410NamedCurves.getByName(var33);
               if(var34 == null) {
                  String var35 = "unknown curve name: " + var33;
                  throw new InvalidAlgorithmParameterException(var35);
               }

               ECCurve var36 = var34.getCurve();
               ECPoint var37 = var34.getG();
               BigInteger var38 = var34.getN();
               BigInteger var39 = var34.getH();
               byte[] var40 = var34.getSeed();
               ECNamedCurveSpec var41 = new ECNamedCurveSpec(var33, var36, var37, var38, var39, var40);
               this.ecParams = var41;
            } else {
               X9ECParameters var84 = X962NamedCurves.getByName(var33);
               if(var84 == null) {
                  X9ECParameters var57 = SECNamedCurves.getByName(var33);
                  if(var57 == null) {
                     X9ECParameters var58 = NISTNamedCurves.getByName(var33);
                  }

                  if(var57 == null) {
                     X9ECParameters var59 = TeleTrusTNamedCurves.getByName(var33);
                  }

                  if(var57 == null) {
                     try {
                        DERObjectIdentifier var60 = new DERObjectIdentifier(var33);
                        var57 = X962NamedCurves.getByOID(var60);
                        if(var57 == null) {
                           var57 = SECNamedCurves.getByOID(var60);
                        }

                        if(var57 == null) {
                           var57 = NISTNamedCurves.getByOID(var60);
                        }

                        if(var57 == null) {
                           var57 = TeleTrusTNamedCurves.getByOID(var60);
                        }

                        if(var57 == null) {
                           String var61 = "unknown curve OID: " + var33;
                           throw new InvalidAlgorithmParameterException(var61);
                        }
                     } catch (IllegalArgumentException var83) {
                        String var63 = "unknown curve name: " + var33;
                        throw new InvalidAlgorithmParameterException(var63);
                     }
                  }
               }

               ECCurve var64 = var84.getCurve();
               ECPoint var65 = var84.getG();
               BigInteger var66 = var84.getN();
               BigInteger var67 = var84.getH();
               ECNamedCurveSpec var68 = new ECNamedCurveSpec(var33, var64, var65, var66, var67, (byte[])null);
               this.ecParams = var68;
            }

            java.security.spec.ECParameterSpec var42 = (java.security.spec.ECParameterSpec)this.ecParams;
            ECCurve var43 = EC5Util.convertCurve(var42.getCurve());
            java.security.spec.ECPoint var44 = var42.getGenerator();
            ECPoint var45 = EC5Util.convertPoint(var43, var44, (boolean)0);
            ECKeyGenerationParameters var46 = new ECKeyGenerationParameters;
            BigInteger var47 = var42.getOrder();
            BigInteger var48 = BigInteger.valueOf((long)var42.getCofactor());
            ECDomainParameters var49 = new ECDomainParameters(var43, var45, var47, var48);
            var46.<init>(var49, var2);
            this.param = var46;
            ECKeyPairGenerator var54 = this.engine;
            ECKeyGenerationParameters var55 = this.param;
            var54.init(var55);
            byte var56 = 1;
            this.initialised = (boolean)var56;
         } else if(var1 == null && ProviderUtil.getEcImplicitlyCa() != null) {
            ECParameterSpec var69 = ProviderUtil.getEcImplicitlyCa();
            this.ecParams = var1;
            ECKeyGenerationParameters var71 = new ECKeyGenerationParameters;
            ECCurve var72 = var69.getCurve();
            ECPoint var73 = var69.getG();
            BigInteger var74 = var69.getN();
            ECDomainParameters var75 = new ECDomainParameters(var72, var73, var74);
            var71.<init>(var75, var2);
            this.param = var71;
            ECKeyPairGenerator var80 = this.engine;
            ECKeyGenerationParameters var81 = this.param;
            var80.init(var81);
            byte var82 = 1;
            this.initialised = (boolean)var82;
         } else if(var1 == null && ProviderUtil.getEcImplicitlyCa() == null) {
            throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
         } else {
            throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
         }
      }
   }

   public static class ECDH extends KeyPairGenerator.EC {

      public ECDH() {
         super("ECDH");
      }
   }
}
