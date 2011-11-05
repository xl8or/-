package myorg.bouncycastle.jce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import myorg.bouncycastle.jce.provider.JCEECPrivateKey;
import myorg.bouncycastle.jce.provider.JCEECPublicKey;
import myorg.bouncycastle.jce.provider.JDKKeyFactory;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import myorg.bouncycastle.jce.spec.ECPrivateKeySpec;
import myorg.bouncycastle.jce.spec.ECPublicKeySpec;
import myorg.bouncycastle.math.ec.ECCurve;

public class KeyFactory extends JDKKeyFactory {

   String algorithm;


   KeyFactory(String var1) {
      this.algorithm = var1;
   }

   protected PrivateKey engineGeneratePrivate(KeySpec var1) throws InvalidKeySpecException {
      JCEECPrivateKey var4;
      if(var1 instanceof PKCS8EncodedKeySpec) {
         try {
            JCEECPrivateKey var2 = (JCEECPrivateKey)JDKKeyFactory.createPrivateKeyFromDERStream(((PKCS8EncodedKeySpec)var1).getEncoded());
            String var3 = this.algorithm;
            var4 = new JCEECPrivateKey(var3, var2);
         } catch (Exception var13) {
            String var5 = var13.toString();
            throw new InvalidKeySpecException(var5);
         }
      } else if(var1 instanceof ECPrivateKeySpec) {
         String var6 = this.algorithm;
         ECPrivateKeySpec var7 = (ECPrivateKeySpec)var1;
         var4 = new JCEECPrivateKey(var6, var7);
      } else {
         if(!(var1 instanceof java.security.spec.ECPrivateKeySpec)) {
            StringBuilder var10 = (new StringBuilder()).append("Unknown KeySpec type: ");
            String var11 = var1.getClass().getName();
            String var12 = var10.append(var11).toString();
            throw new InvalidKeySpecException(var12);
         }

         String var8 = this.algorithm;
         java.security.spec.ECPrivateKeySpec var9 = (java.security.spec.ECPrivateKeySpec)var1;
         var4 = new JCEECPrivateKey(var8, var9);
      }

      return var4;
   }

   protected PublicKey engineGeneratePublic(KeySpec var1) throws InvalidKeySpecException {
      JCEECPublicKey var4;
      if(var1 instanceof X509EncodedKeySpec) {
         try {
            JCEECPublicKey var2 = (JCEECPublicKey)JDKKeyFactory.createPublicKeyFromDERStream(((X509EncodedKeySpec)var1).getEncoded());
            String var3 = this.algorithm;
            var4 = new JCEECPublicKey(var3, var2);
         } catch (Exception var13) {
            String var5 = var13.toString();
            throw new InvalidKeySpecException(var5);
         }
      } else if(var1 instanceof ECPublicKeySpec) {
         String var6 = this.algorithm;
         ECPublicKeySpec var7 = (ECPublicKeySpec)var1;
         var4 = new JCEECPublicKey(var6, var7);
      } else {
         if(!(var1 instanceof java.security.spec.ECPublicKeySpec)) {
            StringBuilder var10 = (new StringBuilder()).append("Unknown KeySpec type: ");
            String var11 = var1.getClass().getName();
            String var12 = var10.append(var11).toString();
            throw new InvalidKeySpecException(var12);
         }

         String var8 = this.algorithm;
         java.security.spec.ECPublicKeySpec var9 = (java.security.spec.ECPublicKeySpec)var1;
         var4 = new JCEECPublicKey(var8, var9);
      }

      return var4;
   }

   protected KeySpec engineGetKeySpec(Key var1, Class var2) throws InvalidKeySpecException {
      Object var4;
      if(var2.isAssignableFrom(PKCS8EncodedKeySpec.class) && var1.getFormat().equals("PKCS#8")) {
         byte[] var3 = var1.getEncoded();
         var4 = new PKCS8EncodedKeySpec(var3);
      } else if(var2.isAssignableFrom(X509EncodedKeySpec.class) && var1.getFormat().equals("X.509")) {
         byte[] var5 = var1.getEncoded();
         var4 = new X509EncodedKeySpec(var5);
      } else if(var2.isAssignableFrom(java.security.spec.ECPublicKeySpec.class) && var1 instanceof ECPublicKey) {
         ECPublicKey var22 = (ECPublicKey)var1;
         if(var22.getParams() != null) {
            ECPoint var7 = var22.getW();
            ECParameterSpec var8 = var22.getParams();
            var4 = new java.security.spec.ECPublicKeySpec(var7, var8);
         } else {
            myorg.bouncycastle.jce.spec.ECParameterSpec var9 = ProviderUtil.getEcImplicitlyCa();
            ECPoint var10 = var22.getW();
            ECCurve var11 = var9.getCurve();
            byte[] var12 = var9.getSeed();
            ECParameterSpec var13 = EC5Util.convertSpec(EC5Util.convertCurve(var11, var12), var9);
            var4 = new java.security.spec.ECPublicKeySpec(var10, var13);
         }
      } else {
         if(!var2.isAssignableFrom(java.security.spec.ECPrivateKeySpec.class) || !(var1 instanceof ECPrivateKey)) {
            String var21 = "not implemented yet " + var1 + " " + var2;
            throw new RuntimeException(var21);
         }

         ECPrivateKey var6 = (ECPrivateKey)var1;
         if(var6.getParams() != null) {
            BigInteger var14 = var6.getS();
            ECParameterSpec var15 = var6.getParams();
            var4 = new java.security.spec.ECPrivateKeySpec(var14, var15);
         } else {
            myorg.bouncycastle.jce.spec.ECParameterSpec var16 = ProviderUtil.getEcImplicitlyCa();
            BigInteger var17 = var6.getS();
            ECCurve var18 = var16.getCurve();
            byte[] var19 = var16.getSeed();
            ECParameterSpec var20 = EC5Util.convertSpec(EC5Util.convertCurve(var18, var19), var16);
            var4 = new java.security.spec.ECPrivateKeySpec(var17, var20);
         }
      }

      return (KeySpec)var4;
   }

   protected Key engineTranslateKey(Key var1) throws InvalidKeyException {
      Object var3;
      if(var1 instanceof ECPublicKey) {
         ECPublicKey var2 = (ECPublicKey)var1;
         var3 = new JCEECPublicKey(var2);
      } else {
         if(!(var1 instanceof ECPrivateKey)) {
            throw new InvalidKeyException("key type unknown");
         }

         ECPrivateKey var4 = (ECPrivateKey)var1;
         var3 = new JCEECPrivateKey(var4);
      }

      return (Key)var3;
   }

   public static class ECMQV extends KeyFactory {

      public ECMQV() {
         super("ECMQV");
      }
   }

   public static class ECDHC extends KeyFactory {

      public ECDHC() {
         super("ECDHC");
      }
   }

   public static class ECGOST3410 extends KeyFactory {

      public ECGOST3410() {
         super("ECGOST3410");
      }
   }

   public static class EC extends KeyFactory {

      public EC() {
         super("EC");
      }
   }

   public static class ECDSA extends KeyFactory {

      public ECDSA() {
         super("ECDSA");
      }
   }

   public static class ECDH extends KeyFactory {

      public ECDH() {
         super("ECDH");
      }
   }
}
