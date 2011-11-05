package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import myorg.bouncycastle.jce.interfaces.ElGamalPublicKey;
import myorg.bouncycastle.jce.provider.JCEDHPrivateKey;
import myorg.bouncycastle.jce.provider.JCEDHPublicKey;
import myorg.bouncycastle.jce.provider.JCEECPrivateKey;
import myorg.bouncycastle.jce.provider.JCEECPublicKey;
import myorg.bouncycastle.jce.provider.JCEElGamalPrivateKey;
import myorg.bouncycastle.jce.provider.JCEElGamalPublicKey;
import myorg.bouncycastle.jce.provider.JCERSAPrivateCrtKey;
import myorg.bouncycastle.jce.provider.JCERSAPrivateKey;
import myorg.bouncycastle.jce.provider.JCERSAPublicKey;
import myorg.bouncycastle.jce.provider.JDKDSAPrivateKey;
import myorg.bouncycastle.jce.provider.JDKDSAPublicKey;
import myorg.bouncycastle.jce.provider.JDKGOST3410PrivateKey;
import myorg.bouncycastle.jce.provider.JDKGOST3410PublicKey;
import myorg.bouncycastle.jce.provider.RSAUtil;
import myorg.bouncycastle.jce.spec.ElGamalPrivateKeySpec;
import myorg.bouncycastle.jce.spec.ElGamalPublicKeySpec;
import myorg.bouncycastle.jce.spec.GOST3410PrivateKeySpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeySpec;

public abstract class JDKKeyFactory extends KeyFactorySpi {

   protected boolean elGamalFactory = 0;


   public JDKKeyFactory() {}

   protected static PrivateKey createPrivateKeyFromDERStream(byte[] var0) throws IOException {
      ASN1Sequence var1 = (ASN1Sequence)ASN1Object.fromByteArray(var0);
      return createPrivateKeyFromPrivateKeyInfo(new PrivateKeyInfo(var1));
   }

   static PrivateKey createPrivateKeyFromPrivateKeyInfo(PrivateKeyInfo var0) {
      DERObjectIdentifier var1 = var0.getAlgorithmId().getObjectId();
      Object var2;
      if(RSAUtil.isRsaOid(var1)) {
         var2 = new JCERSAPrivateCrtKey(var0);
      } else {
         DERObjectIdentifier var3 = PKCSObjectIdentifiers.dhKeyAgreement;
         if(var1.equals(var3)) {
            var2 = new JCEDHPrivateKey(var0);
         } else {
            DERObjectIdentifier var4 = OIWObjectIdentifiers.elGamalAlgorithm;
            if(var1.equals(var4)) {
               var2 = new JCEElGamalPrivateKey(var0);
            } else {
               DERObjectIdentifier var5 = X9ObjectIdentifiers.id_dsa;
               if(var1.equals(var5)) {
                  var2 = new JDKDSAPrivateKey(var0);
               } else {
                  DERObjectIdentifier var6 = X9ObjectIdentifiers.id_ecPublicKey;
                  if(var1.equals(var6)) {
                     var2 = new JCEECPrivateKey(var0);
                  } else {
                     DERObjectIdentifier var7 = CryptoProObjectIdentifiers.gostR3410_94;
                     if(var1.equals(var7)) {
                        var2 = new JDKGOST3410PrivateKey(var0);
                     } else {
                        DERObjectIdentifier var8 = CryptoProObjectIdentifiers.gostR3410_2001;
                        if(!var1.equals(var8)) {
                           String var9 = "algorithm identifier " + var1 + " in key not recognised";
                           throw new RuntimeException(var9);
                        }

                        var2 = new JCEECPrivateKey(var0);
                     }
                  }
               }
            }
         }
      }

      return (PrivateKey)var2;
   }

   public static PublicKey createPublicKeyFromDERStream(byte[] var0) throws IOException {
      ASN1Sequence var1 = (ASN1Sequence)ASN1Object.fromByteArray(var0);
      return createPublicKeyFromPublicKeyInfo(new SubjectPublicKeyInfo(var1));
   }

   static PublicKey createPublicKeyFromPublicKeyInfo(SubjectPublicKeyInfo var0) {
      DERObjectIdentifier var1 = var0.getAlgorithmId().getObjectId();
      Object var2;
      if(RSAUtil.isRsaOid(var1)) {
         var2 = new JCERSAPublicKey(var0);
      } else {
         DERObjectIdentifier var3 = PKCSObjectIdentifiers.dhKeyAgreement;
         if(var1.equals(var3)) {
            var2 = new JCEDHPublicKey(var0);
         } else {
            DERObjectIdentifier var4 = X9ObjectIdentifiers.dhpublicnumber;
            if(var1.equals(var4)) {
               var2 = new JCEDHPublicKey(var0);
            } else {
               DERObjectIdentifier var5 = OIWObjectIdentifiers.elGamalAlgorithm;
               if(var1.equals(var5)) {
                  var2 = new JCEElGamalPublicKey(var0);
               } else {
                  DERObjectIdentifier var6 = X9ObjectIdentifiers.id_dsa;
                  if(var1.equals(var6)) {
                     var2 = new JDKDSAPublicKey(var0);
                  } else {
                     DERObjectIdentifier var7 = OIWObjectIdentifiers.dsaWithSHA1;
                     if(var1.equals(var7)) {
                        var2 = new JDKDSAPublicKey(var0);
                     } else {
                        DERObjectIdentifier var8 = X9ObjectIdentifiers.id_ecPublicKey;
                        if(var1.equals(var8)) {
                           var2 = new JCEECPublicKey(var0);
                        } else {
                           DERObjectIdentifier var9 = CryptoProObjectIdentifiers.gostR3410_94;
                           if(var1.equals(var9)) {
                              var2 = new JDKGOST3410PublicKey(var0);
                           } else {
                              DERObjectIdentifier var10 = CryptoProObjectIdentifiers.gostR3410_2001;
                              if(!var1.equals(var10)) {
                                 String var11 = "algorithm identifier " + var1 + " in key not recognised";
                                 throw new RuntimeException(var11);
                              }

                              var2 = new JCEECPublicKey(var0);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return (PublicKey)var2;
   }

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      if(var1 instanceof PKCS8EncodedKeySpec) {
         try {
            PrivateKey var2 = createPrivateKeyFromDERStream(((PKCS8EncodedKeySpec)var1).getEncoded());
            return var2;
         } catch (Exception var7) {
            String var3 = var7.toString();
            throw new InvalidKeySpecException(var3);
         }
      } else {
         StringBuilder var4 = (new StringBuilder()).append("Unknown KeySpec type: ");
         String var5 = var1.getClass().getName();
         String var6 = var4.append(var5).toString();
         throw new InvalidKeySpecException(var6);
      }
   }

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      if(var1 instanceof X509EncodedKeySpec) {
         try {
            PublicKey var2 = createPublicKeyFromDERStream(((X509EncodedKeySpec)var1).getEncoded());
            return var2;
         } catch (Exception var7) {
            String var3 = var7.toString();
            throw new InvalidKeySpecException(var3);
         }
      } else {
         StringBuilder var4 = (new StringBuilder()).append("Unknown KeySpec type: ");
         String var5 = var1.getClass().getName();
         String var6 = var4.append(var5).toString();
         throw new InvalidKeySpecException(var6);
      }
   }

   protected KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      Object var4;
      if(var2.isAssignableFrom(PKCS8EncodedKeySpec.class) && var1.getFormat().equals("PKCS#8")) {
         byte[] var3 = var1.getEncoded();
         var4 = new PKCS8EncodedKeySpec(var3);
      } else if(var2.isAssignableFrom(X509EncodedKeySpec.class) && var1.getFormat().equals("X.509")) {
         byte[] var5 = var1.getEncoded();
         var4 = new X509EncodedKeySpec(var5);
      } else if(var2.isAssignableFrom(RSAPublicKeySpec.class) && var1 instanceof RSAPublicKey) {
         RSAPublicKey var6 = (RSAPublicKey)var1;
         BigInteger var7 = var6.getModulus();
         BigInteger var8 = var6.getPublicExponent();
         var4 = new RSAPublicKeySpec(var7, var8);
      } else if(var2.isAssignableFrom(RSAPrivateKeySpec.class) && var1 instanceof RSAPrivateKey) {
         RSAPrivateKey var9 = (RSAPrivateKey)var1;
         BigInteger var10 = var9.getModulus();
         BigInteger var11 = var9.getPrivateExponent();
         var4 = new RSAPrivateKeySpec(var10, var11);
      } else if(var2.isAssignableFrom(RSAPrivateCrtKeySpec.class) && var1 instanceof RSAPrivateCrtKey) {
         RSAPrivateCrtKey var12 = (RSAPrivateCrtKey)var1;
         BigInteger var13 = var12.getModulus();
         BigInteger var14 = var12.getPublicExponent();
         BigInteger var15 = var12.getPrivateExponent();
         BigInteger var16 = var12.getPrimeP();
         BigInteger var17 = var12.getPrimeQ();
         BigInteger var18 = var12.getPrimeExponentP();
         BigInteger var19 = var12.getPrimeExponentQ();
         BigInteger var20 = var12.getCrtCoefficient();
         var4 = new RSAPrivateCrtKeySpec(var13, var14, var15, var16, var17, var18, var19, var20);
      } else if(var2.isAssignableFrom(DHPrivateKeySpec.class) && var1 instanceof DHPrivateKey) {
         DHPrivateKey var21 = (DHPrivateKey)var1;
         BigInteger var22 = var21.getX();
         BigInteger var23 = var21.getParams().getP();
         BigInteger var24 = var21.getParams().getG();
         var4 = new DHPrivateKeySpec(var22, var23, var24);
      } else {
         if(!var2.isAssignableFrom(DHPublicKeySpec.class) || !(var1 instanceof DHPublicKey)) {
            String var29 = "not implemented yet " + var1 + " " + var2;
            throw new RuntimeException(var29);
         }

         DHPublicKey var25 = (DHPublicKey)var1;
         BigInteger var26 = var25.getY();
         BigInteger var27 = var25.getParams().getP();
         BigInteger var28 = var25.getParams().getG();
         var4 = new DHPublicKeySpec(var26, var27, var28);
      }

      return (KeySpec)var4;
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      Object var3;
      if(var1 instanceof RSAPublicKey) {
         RSAPublicKey var2 = (RSAPublicKey)var1;
         var3 = new JCERSAPublicKey(var2);
      } else if(var1 instanceof RSAPrivateCrtKey) {
         RSAPrivateCrtKey var4 = (RSAPrivateCrtKey)var1;
         var3 = new JCERSAPrivateCrtKey(var4);
      } else if(var1 instanceof RSAPrivateKey) {
         RSAPrivateKey var5 = (RSAPrivateKey)var1;
         var3 = new JCERSAPrivateKey(var5);
      } else if(var1 instanceof DHPublicKey) {
         if(this.elGamalFactory) {
            DHPublicKey var6 = (DHPublicKey)var1;
            var3 = new JCEElGamalPublicKey(var6);
         } else {
            DHPublicKey var7 = (DHPublicKey)var1;
            var3 = new JCEDHPublicKey(var7);
         }
      } else if(var1 instanceof DHPrivateKey) {
         if(this.elGamalFactory) {
            DHPrivateKey var8 = (DHPrivateKey)var1;
            var3 = new JCEElGamalPrivateKey(var8);
         } else {
            DHPrivateKey var9 = (DHPrivateKey)var1;
            var3 = new JCEDHPrivateKey(var9);
         }
      } else if(var1 instanceof DSAPublicKey) {
         DSAPublicKey var10 = (DSAPublicKey)var1;
         var3 = new JDKDSAPublicKey(var10);
      } else if(var1 instanceof DSAPrivateKey) {
         DSAPrivateKey var11 = (DSAPrivateKey)var1;
         var3 = new JDKDSAPrivateKey(var11);
      } else if(var1 instanceof ElGamalPublicKey) {
         ElGamalPublicKey var12 = (ElGamalPublicKey)var1;
         var3 = new JCEElGamalPublicKey(var12);
      } else {
         if(!(var1 instanceof ElGamalPrivateKey)) {
            throw new InvalidKeyException("key type unknown");
         }

         ElGamalPrivateKey var13 = (ElGamalPrivateKey)var1;
         var3 = new JCEElGamalPrivateKey(var13);
      }

      return (Key)var3;
   }

   public static class RSA extends JDKKeyFactory {

      public RSA() {}

      protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof PKCS8EncodedKeySpec) {
            PrivateKey var2;
            try {
               var2 = JDKKeyFactory.createPrivateKeyFromDERStream(((PKCS8EncodedKeySpec)var1).getEncoded());
            } catch (Exception var14) {
               try {
                  ASN1Sequence var5 = (ASN1Sequence)ASN1Object.fromByteArray(((PKCS8EncodedKeySpec)var1).getEncoded());
                  RSAPrivateKeyStructure var6 = new RSAPrivateKeyStructure(var5);
                  var3 = new JCERSAPrivateCrtKey(var6);
                  return (PrivateKey)var3;
               } catch (Exception var13) {
                  String var7 = var13.toString();
                  throw new InvalidKeySpecException(var7);
               }
            }

            var3 = var2;
         } else if(var1 instanceof RSAPrivateCrtKeySpec) {
            RSAPrivateCrtKeySpec var8 = (RSAPrivateCrtKeySpec)var1;
            var3 = new JCERSAPrivateCrtKey(var8);
         } else {
            if(!(var1 instanceof RSAPrivateKeySpec)) {
               StringBuilder var10 = (new StringBuilder()).append("Unknown KeySpec type: ");
               String var11 = var1.getClass().getName();
               String var12 = var10.append(var11).toString();
               throw new InvalidKeySpecException(var12);
            }

            RSAPrivateKeySpec var9 = (RSAPrivateKeySpec)var1;
            var3 = new JCERSAPrivateKey(var9);
         }

         return (PrivateKey)var3;
      }

      protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof RSAPublicKeySpec) {
            RSAPublicKeySpec var2 = (RSAPublicKeySpec)var1;
            var3 = new JCERSAPublicKey(var2);
         } else {
            var3 = super.engineGeneratePublic(var1);
         }

         return (PublicKey)var3;
      }
   }

   public static class X509 extends JDKKeyFactory {

      public X509() {}
   }

   public static class DH extends JDKKeyFactory {

      public DH() {}

      protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof DHPrivateKeySpec) {
            DHPrivateKeySpec var2 = (DHPrivateKeySpec)var1;
            var3 = new JCEDHPrivateKey(var2);
         } else {
            var3 = super.engineGeneratePrivate(var1);
         }

         return (PrivateKey)var3;
      }

      protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof DHPublicKeySpec) {
            DHPublicKeySpec var2 = (DHPublicKeySpec)var1;
            var3 = new JCEDHPublicKey(var2);
         } else {
            var3 = super.engineGeneratePublic(var1);
         }

         return (PublicKey)var3;
      }
   }

   public static class DSA extends JDKKeyFactory {

      public DSA() {}

      protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof DSAPrivateKeySpec) {
            DSAPrivateKeySpec var2 = (DSAPrivateKeySpec)var1;
            var3 = new JDKDSAPrivateKey(var2);
         } else {
            var3 = super.engineGeneratePrivate(var1);
         }

         return (PrivateKey)var3;
      }

      protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof DSAPublicKeySpec) {
            DSAPublicKeySpec var2 = (DSAPublicKeySpec)var1;
            var3 = new JDKDSAPublicKey(var2);
         } else {
            var3 = super.engineGeneratePublic(var1);
         }

         return (PublicKey)var3;
      }
   }

   public static class GOST3410 extends JDKKeyFactory {

      public GOST3410() {}

      protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof GOST3410PrivateKeySpec) {
            GOST3410PrivateKeySpec var2 = (GOST3410PrivateKeySpec)var1;
            var3 = new JDKGOST3410PrivateKey(var2);
         } else {
            var3 = super.engineGeneratePrivate(var1);
         }

         return (PrivateKey)var3;
      }

      protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof GOST3410PublicKeySpec) {
            GOST3410PublicKeySpec var2 = (GOST3410PublicKeySpec)var1;
            var3 = new JDKGOST3410PublicKey(var2);
         } else {
            var3 = super.engineGeneratePublic(var1);
         }

         return (PublicKey)var3;
      }
   }

   public static class ElGamal extends JDKKeyFactory {

      public ElGamal() {
         this.elGamalFactory = (boolean)1;
      }

      protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof ElGamalPrivateKeySpec) {
            ElGamalPrivateKeySpec var2 = (ElGamalPrivateKeySpec)var1;
            var3 = new JCEElGamalPrivateKey(var2);
         } else if(var1 instanceof DHPrivateKeySpec) {
            DHPrivateKeySpec var4 = (DHPrivateKeySpec)var1;
            var3 = new JCEElGamalPrivateKey(var4);
         } else {
            var3 = super.engineGeneratePrivate(var1);
         }

         return (PrivateKey)var3;
      }

      protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
         Object var3;
         if(var1 instanceof ElGamalPublicKeySpec) {
            ElGamalPublicKeySpec var2 = (ElGamalPublicKeySpec)var1;
            var3 = new JCEElGamalPublicKey(var2);
         } else if(var1 instanceof DHPublicKeySpec) {
            DHPublicKeySpec var4 = (DHPublicKeySpec)var1;
            var3 = new JCEElGamalPublicKey(var4);
         } else {
            var3 = super.engineGeneratePublic(var1);
         }

         return (PublicKey)var3;
      }
   }
}
