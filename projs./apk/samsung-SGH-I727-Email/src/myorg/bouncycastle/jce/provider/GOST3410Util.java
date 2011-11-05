package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;
import myorg.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import myorg.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import myorg.bouncycastle.jce.interfaces.GOST3410PrivateKey;
import myorg.bouncycastle.jce.interfaces.GOST3410PublicKey;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class GOST3410Util {

   public GOST3410Util() {}

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if(var0 instanceof GOST3410PrivateKey) {
         GOST3410PrivateKey var1 = (GOST3410PrivateKey)var0;
         GOST3410PublicKeyParameterSetSpec var2 = var1.getParameters().getPublicKeyParameters();
         BigInteger var3 = var1.getX();
         BigInteger var4 = var2.getP();
         BigInteger var5 = var2.getQ();
         BigInteger var6 = var2.getA();
         GOST3410Parameters var7 = new GOST3410Parameters(var4, var5, var6);
         return new GOST3410PrivateKeyParameters(var3, var7);
      } else {
         throw new InvalidKeyException("can\'t identify GOST3410 private key.");
      }
   }

   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if(var0 instanceof GOST3410PublicKey) {
         GOST3410PublicKey var1 = (GOST3410PublicKey)var0;
         GOST3410PublicKeyParameterSetSpec var2 = var1.getParameters().getPublicKeyParameters();
         BigInteger var3 = var1.getY();
         BigInteger var4 = var2.getP();
         BigInteger var5 = var2.getQ();
         BigInteger var6 = var2.getA();
         GOST3410Parameters var7 = new GOST3410Parameters(var4, var5, var6);
         return new GOST3410PublicKeyParameters(var3, var7);
      } else {
         StringBuilder var8 = (new StringBuilder()).append("can\'t identify GOST3410 public key: ");
         String var9 = var0.getClass().getName();
         String var10 = var8.append(var9).toString();
         throw new InvalidKeyException(var10);
      }
   }
}
