package myorg.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.BasicAgreement;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.crypto.params.MQVPrivateParameters;
import myorg.bouncycastle.crypto.params.MQVPublicParameters;
import myorg.bouncycastle.math.ec.ECAlgorithms;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECMQVBasicAgreement implements BasicAgreement {

   MQVPrivateParameters privParams;


   public ECMQVBasicAgreement() {}

   private ECPoint calculateMqvAgreement(ECDomainParameters var1, ECPrivateKeyParameters var2, ECPrivateKeyParameters var3, ECPublicKeyParameters var4, ECPublicKeyParameters var5, ECPublicKeyParameters var6) {
      BigInteger var7 = var1.getN();
      int var8 = (var7.bitLength() + 1) / 2;
      BigInteger var9 = ECConstants.ONE.shiftLeft(var8);
      ECPoint var12;
      if(var4 == null) {
         ECPoint var10 = var1.getG();
         BigInteger var11 = var3.getD();
         var12 = var10.multiply(var11);
      } else {
         var12 = var4.getQ();
      }

      BigInteger var13 = var12.getX().toBigInteger().mod(var9).setBit(var8);
      BigInteger var14 = var2.getD().multiply(var13).mod(var7);
      BigInteger var15 = var3.getD();
      BigInteger var16 = var14.add(var15).mod(var7);
      BigInteger var17 = var6.getQ().getX().toBigInteger().mod(var9).setBit(var8);
      BigInteger var18 = var1.getH().multiply(var16).mod(var7);
      ECPoint var19 = var5.getQ();
      BigInteger var20 = var17.multiply(var18).mod(var7);
      ECPoint var21 = var6.getQ();
      ECPoint var22 = ECAlgorithms.sumOfTwoMultiplies(var19, var20, var21, var18);
      if(var22.isInfinity()) {
         throw new IllegalStateException("Infinity is not a valid agreement value for MQV");
      } else {
         return var22;
      }
   }

   public BigInteger calculateAgreement(CipherParameters var1) {
      MQVPublicParameters var2 = (MQVPublicParameters)var1;
      ECPrivateKeyParameters var3 = this.privParams.getStaticPrivateKey();
      ECDomainParameters var4 = var3.getParameters();
      ECPrivateKeyParameters var5 = this.privParams.getEphemeralPrivateKey();
      ECPublicKeyParameters var6 = this.privParams.getEphemeralPublicKey();
      ECPublicKeyParameters var7 = var2.getStaticPublicKey();
      ECPublicKeyParameters var8 = var2.getEphemeralPublicKey();
      return this.calculateMqvAgreement(var4, var3, var5, var6, var7, var8).getX().toBigInteger();
   }

   public void init(CipherParameters var1) {
      MQVPrivateParameters var2 = (MQVPrivateParameters)var1;
      this.privParams = var2;
   }
}
