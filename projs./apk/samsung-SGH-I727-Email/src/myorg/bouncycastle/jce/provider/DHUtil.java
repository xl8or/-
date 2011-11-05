package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;

public class DHUtil {

   public DHUtil() {}

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if(var0 instanceof DHPrivateKey) {
         DHPrivateKey var1 = (DHPrivateKey)var0;
         BigInteger var2 = var1.getX();
         BigInteger var3 = var1.getParams().getP();
         BigInteger var4 = var1.getParams().getG();
         int var5 = var1.getParams().getL();
         DHParameters var6 = new DHParameters(var3, var4, (BigInteger)null, var5);
         return new DHPrivateKeyParameters(var2, var6);
      } else {
         throw new InvalidKeyException("can\'t identify DH private key.");
      }
   }

   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if(var0 instanceof DHPublicKey) {
         DHPublicKey var1 = (DHPublicKey)var0;
         BigInteger var2 = var1.getY();
         BigInteger var3 = var1.getParams().getP();
         BigInteger var4 = var1.getParams().getG();
         int var5 = var1.getParams().getL();
         DHParameters var6 = new DHParameters(var3, var4, (BigInteger)null, var5);
         return new DHPublicKeyParameters(var2, var6);
      } else {
         throw new InvalidKeyException("can\'t identify DH public key.");
      }
   }
}
