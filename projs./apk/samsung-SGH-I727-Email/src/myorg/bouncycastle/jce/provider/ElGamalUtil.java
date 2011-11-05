package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ElGamalParameters;
import myorg.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import myorg.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import myorg.bouncycastle.jce.interfaces.ElGamalPublicKey;

public class ElGamalUtil {

   public ElGamalUtil() {}

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      ElGamalPrivateKeyParameters var6;
      if(var0 instanceof ElGamalPrivateKey) {
         ElGamalPrivateKey var1 = (ElGamalPrivateKey)var0;
         BigInteger var2 = var1.getX();
         BigInteger var3 = var1.getParameters().getP();
         BigInteger var4 = var1.getParameters().getG();
         ElGamalParameters var5 = new ElGamalParameters(var3, var4);
         var6 = new ElGamalPrivateKeyParameters(var2, var5);
      } else {
         if(!(var0 instanceof DHPrivateKey)) {
            throw new InvalidKeyException("can\'t identify private key for El Gamal.");
         }

         DHPrivateKey var7 = (DHPrivateKey)var0;
         BigInteger var8 = var7.getX();
         BigInteger var9 = var7.getParams().getP();
         BigInteger var10 = var7.getParams().getG();
         ElGamalParameters var11 = new ElGamalParameters(var9, var10);
         var6 = new ElGamalPrivateKeyParameters(var8, var11);
      }

      return var6;
   }

   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      ElGamalPublicKeyParameters var6;
      if(var0 instanceof ElGamalPublicKey) {
         ElGamalPublicKey var1 = (ElGamalPublicKey)var0;
         BigInteger var2 = var1.getY();
         BigInteger var3 = var1.getParameters().getP();
         BigInteger var4 = var1.getParameters().getG();
         ElGamalParameters var5 = new ElGamalParameters(var3, var4);
         var6 = new ElGamalPublicKeyParameters(var2, var5);
      } else {
         if(!(var0 instanceof DHPublicKey)) {
            throw new InvalidKeyException("can\'t identify public key for El Gamal.");
         }

         DHPublicKey var7 = (DHPublicKey)var0;
         BigInteger var8 = var7.getY();
         BigInteger var9 = var7.getParams().getP();
         BigInteger var10 = var7.getParams().getG();
         ElGamalParameters var11 = new ElGamalParameters(var9, var10);
         var6 = new ElGamalPublicKeyParameters(var8, var11);
      }

      return var6;
   }
}
