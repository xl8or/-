package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;

public class DSAUtil {

   public DSAUtil() {}

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if(var0 instanceof DSAPrivateKey) {
         DSAPrivateKey var1 = (DSAPrivateKey)var0;
         BigInteger var2 = var1.getX();
         BigInteger var3 = var1.getParams().getP();
         BigInteger var4 = var1.getParams().getQ();
         BigInteger var5 = var1.getParams().getG();
         DSAParameters var6 = new DSAParameters(var3, var4, var5);
         return new DSAPrivateKeyParameters(var2, var6);
      } else {
         throw new InvalidKeyException("can\'t identify DSA private key.");
      }
   }

   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if(var0 instanceof DSAPublicKey) {
         DSAPublicKey var1 = (DSAPublicKey)var0;
         BigInteger var2 = var1.getY();
         BigInteger var3 = var1.getParams().getP();
         BigInteger var4 = var1.getParams().getQ();
         BigInteger var5 = var1.getParams().getG();
         DSAParameters var6 = new DSAParameters(var3, var4, var5);
         return new DSAPublicKeyParameters(var2, var6);
      } else {
         StringBuilder var7 = (new StringBuilder()).append("can\'t identify DSA public key: ");
         String var8 = var0.getClass().getName();
         String var9 = var7.append(var8).toString();
         throw new InvalidKeyException(var9);
      }
   }
}
