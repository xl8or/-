package myorg.bouncycastle.jce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import myorg.bouncycastle.asn1.nist.NISTNamedCurves;
import myorg.bouncycastle.asn1.sec.SECNamedCurves;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import myorg.bouncycastle.asn1.x9.X962NamedCurves;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.jce.interfaces.ECPrivateKey;
import myorg.bouncycastle.jce.interfaces.ECPublicKey;
import myorg.bouncycastle.jce.provider.JCEECPublicKey;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import myorg.bouncycastle.jce.spec.ECParameterSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECUtil {

   public ECUtil() {}

   static int[] convertMidTerms(int[] var0) {
      int[] var1 = new int[3];
      if(var0.length == 1) {
         int var2 = var0[0];
         var1[0] = var2;
      } else {
         if(var0.length != 3) {
            throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
         }

         int var3 = var0[0];
         int var4 = var0[1];
         if(var3 < var4) {
            int var5 = var0[0];
            int var6 = var0[2];
            if(var5 < var6) {
               int var7 = var0[0];
               var1[0] = var7;
               int var8 = var0[1];
               int var9 = var0[2];
               if(var8 < var9) {
                  int var10 = var0[1];
                  var1[1] = var10;
                  int var11 = var0[2];
                  var1[2] = var11;
               } else {
                  int var12 = var0[2];
                  var1[1] = var12;
                  int var13 = var0[1];
                  var1[2] = var13;
               }

               return var1;
            }
         }

         int var14 = var0[1];
         int var15 = var0[2];
         if(var14 < var15) {
            int var16 = var0[1];
            var1[0] = var16;
            int var17 = var0[0];
            int var18 = var0[2];
            if(var17 < var18) {
               int var19 = var0[0];
               var1[1] = var19;
               int var20 = var0[2];
               var1[2] = var20;
            } else {
               int var21 = var0[2];
               var1[1] = var21;
               int var22 = var0[0];
               var1[2] = var22;
            }
         } else {
            int var23 = var0[2];
            var1[0] = var23;
            int var24 = var0[0];
            int var25 = var0[1];
            if(var24 < var25) {
               int var26 = var0[0];
               var1[1] = var26;
               int var27 = var0[1];
               var1[2] = var27;
            } else {
               int var28 = var0[1];
               var1[1] = var28;
               int var29 = var0[0];
               var1[2] = var29;
            }
         }
      }

      return var1;
   }

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if(var0 instanceof ECPrivateKey) {
         ECPrivateKey var1 = (ECPrivateKey)var0;
         ECParameterSpec var2 = var1.getParameters();
         if(var2 == null) {
            var2 = ProviderUtil.getEcImplicitlyCa();
         }

         BigInteger var3 = var1.getD();
         ECCurve var4 = var2.getCurve();
         ECPoint var5 = var2.getG();
         BigInteger var6 = var2.getN();
         BigInteger var7 = var2.getH();
         byte[] var8 = var2.getSeed();
         ECDomainParameters var9 = new ECDomainParameters(var4, var5, var6, var7, var8);
         return new ECPrivateKeyParameters(var3, var9);
      } else {
         throw new InvalidKeyException("can\'t identify EC private key.");
      }
   }

   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      ECPublicKeyParameters var11;
      if(var0 instanceof ECPublicKey) {
         ECPublicKey var1 = (ECPublicKey)var0;
         ECParameterSpec var2 = var1.getParameters();
         if(var2 == null) {
            ECParameterSpec var3 = ProviderUtil.getEcImplicitlyCa();
            ECPoint var4 = ((JCEECPublicKey)var1).engineGetQ();
            ECCurve var5 = var3.getCurve();
            ECPoint var6 = var3.getG();
            BigInteger var7 = var3.getN();
            BigInteger var8 = var3.getH();
            byte[] var9 = var3.getSeed();
            ECDomainParameters var10 = new ECDomainParameters(var5, var6, var7, var8, var9);
            var11 = new ECPublicKeyParameters(var4, var10);
         } else {
            ECPoint var12 = var1.getQ();
            ECCurve var13 = var2.getCurve();
            ECPoint var14 = var2.getG();
            BigInteger var15 = var2.getN();
            BigInteger var16 = var2.getH();
            byte[] var17 = var2.getSeed();
            ECDomainParameters var18 = new ECDomainParameters(var13, var14, var15, var16, var17);
            var11 = new ECPublicKeyParameters(var12, var18);
         }
      } else {
         if(!(var0 instanceof java.security.interfaces.ECPublicKey)) {
            throw new InvalidKeyException("cannot identify EC public key.");
         }

         java.security.interfaces.ECPublicKey var19 = (java.security.interfaces.ECPublicKey)var0;
         ECParameterSpec var20 = EC5Util.convertSpec(var19.getParams(), (boolean)0);
         java.security.spec.ECParameterSpec var21 = var19.getParams();
         java.security.spec.ECPoint var22 = var19.getW();
         ECPoint var23 = EC5Util.convertPoint(var21, var22, (boolean)0);
         ECCurve var24 = var20.getCurve();
         ECPoint var25 = var20.getG();
         BigInteger var26 = var20.getN();
         BigInteger var27 = var20.getH();
         byte[] var28 = var20.getSeed();
         ECDomainParameters var29 = new ECDomainParameters(var24, var25, var26, var27, var28);
         var11 = new ECPublicKeyParameters(var23, var29);
      }

      return var11;
   }

   public static String getCurveName(DERObjectIdentifier var0) {
      String var1 = X962NamedCurves.getName(var0);
      if(var1 == null) {
         var1 = SECNamedCurves.getName(var0);
         if(var1 == null) {
            String var2 = NISTNamedCurves.getName(var0);
         }

         if(var1 == null) {
            String var3 = TeleTrusTNamedCurves.getName(var0);
         }

         if(var1 == null) {
            var1 = ECGOST3410NamedCurves.getName(var0);
         }
      }

      return var1;
   }

   public static X9ECParameters getNamedCurveByOid(DERObjectIdentifier var0) {
      X9ECParameters var1 = X962NamedCurves.getByOID(var0);
      if(var1 == null) {
         var1 = SECNamedCurves.getByOID(var0);
         if(var1 == null) {
            X9ECParameters var2 = NISTNamedCurves.getByOID(var0);
         }

         if(var1 == null) {
            var1 = TeleTrusTNamedCurves.getByOID(var0);
         }
      }

      return var1;
   }

   public static DERObjectIdentifier getNamedCurveOid(String var0) {
      DERObjectIdentifier var1 = X962NamedCurves.getOID(var0);
      if(var1 == null) {
         var1 = SECNamedCurves.getOID(var0);
         if(var1 == null) {
            DERObjectIdentifier var2 = NISTNamedCurves.getOID(var0);
         }

         if(var1 == null) {
            DERObjectIdentifier var3 = TeleTrusTNamedCurves.getOID(var0);
         }

         if(var1 == null) {
            var1 = ECGOST3410NamedCurves.getOID(var0);
         }
      }

      return var1;
   }
}
