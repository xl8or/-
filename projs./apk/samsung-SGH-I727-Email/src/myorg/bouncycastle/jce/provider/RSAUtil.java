package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

class RSAUtil {

   RSAUtil() {}

   static RSAKeyParameters generatePrivateKeyParameter(RSAPrivateKey var0) {
      Object var10;
      if(var0 instanceof RSAPrivateCrtKey) {
         RSAPrivateCrtKey var1 = (RSAPrivateCrtKey)var0;
         BigInteger var2 = var1.getModulus();
         BigInteger var3 = var1.getPublicExponent();
         BigInteger var4 = var1.getPrivateExponent();
         BigInteger var5 = var1.getPrimeP();
         BigInteger var6 = var1.getPrimeQ();
         BigInteger var7 = var1.getPrimeExponentP();
         BigInteger var8 = var1.getPrimeExponentQ();
         BigInteger var9 = var1.getCrtCoefficient();
         var10 = new RSAPrivateCrtKeyParameters(var2, var3, var4, var5, var6, var7, var8, var9);
      } else {
         BigInteger var12 = var0.getModulus();
         BigInteger var13 = var0.getPrivateExponent();
         var10 = new RSAKeyParameters((boolean)1, var12, var13);
      }

      return (RSAKeyParameters)var10;
   }

   static RSAKeyParameters generatePublicKeyParameter(RSAPublicKey var0) {
      BigInteger var1 = var0.getModulus();
      BigInteger var2 = var0.getPublicExponent();
      return new RSAKeyParameters((boolean)0, var1, var2);
   }

   static boolean isRsaOid(DERObjectIdentifier var0) {
      DERObjectIdentifier var1 = PKCSObjectIdentifiers.rsaEncryption;
      boolean var5;
      if(!var0.equals(var1)) {
         DERObjectIdentifier var2 = X509ObjectIdentifiers.id_ea_rsa;
         if(!var0.equals(var2)) {
            DERObjectIdentifier var3 = PKCSObjectIdentifiers.id_RSASSA_PSS;
            if(!var0.equals(var3)) {
               DERObjectIdentifier var4 = PKCSObjectIdentifiers.id_RSAES_OAEP;
               if(!var0.equals(var4)) {
                  var5 = false;
                  return var5;
               }
            }
         }
      }

      var5 = true;
      return var5;
   }
}
