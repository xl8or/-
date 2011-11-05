package myorg.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DSA;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.generators.ECKeyPairGenerator;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECKeyGenerationParameters;
import myorg.bouncycastle.crypto.params.ECKeyParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.math.ec.ECAlgorithms;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECNRSigner implements DSA {

   private boolean forSigning;
   private ECKeyParameters key;
   private SecureRandom random;


   public ECNRSigner() {}

   public BigInteger[] generateSignature(byte[] var1) {
      if(!this.forSigning) {
         throw new IllegalStateException("not initialised for signing");
      } else {
         BigInteger var2 = ((ECPrivateKeyParameters)this.key).getParameters().getN();
         int var3 = var2.bitLength();
         BigInteger var4 = new BigInteger;
         byte var6 = 1;
         var4.<init>(var6, var1);
         int var8 = var4.bitLength();
         ECPrivateKeyParameters var9 = (ECPrivateKeyParameters)this.key;
         if(var8 > var3) {
            throw new DataLengthException("input too large for ECNR key.");
         } else {
            AsymmetricCipherKeyPair var16;
            BigInteger var19;
            BigInteger var20;
            do {
               ECKeyPairGenerator var10 = new ECKeyPairGenerator();
               ECDomainParameters var11 = var9.getParameters();
               SecureRandom var12 = this.random;
               ECKeyGenerationParameters var13 = new ECKeyGenerationParameters(var11, var12);
               var10.init(var13);
               var16 = var10.generateKeyPair();
               BigInteger var17 = ((ECPublicKeyParameters)var16.getPublic()).getQ().getX().toBigInteger().add(var4);
               var19 = var17.mod(var2);
               var20 = ECConstants.ZERO;
            } while(var19.equals(var20));

            BigInteger var23 = var9.getD();
            BigInteger var24 = ((ECPrivateKeyParameters)var16.getPrivate()).getD();
            BigInteger var27 = var19.multiply(var23);
            BigInteger var30 = var24.subtract(var27);
            BigInteger var32 = var30.mod(var2);
            BigInteger[] var33 = new BigInteger[]{var19, var32};
            return var33;
         }
      }
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forSigning = var1;
      if(var1) {
         if(var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            SecureRandom var4 = var3.getRandom();
            this.random = var4;
            ECPrivateKeyParameters var5 = (ECPrivateKeyParameters)var3.getParameters();
            this.key = var5;
         } else {
            SecureRandom var6 = new SecureRandom();
            this.random = var6;
            ECPrivateKeyParameters var7 = (ECPrivateKeyParameters)var2;
            this.key = var7;
         }
      } else {
         ECPublicKeyParameters var8 = (ECPublicKeyParameters)var2;
         this.key = var8;
      }
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      if(this.forSigning) {
         throw new IllegalStateException("not initialised for verifying");
      } else {
         ECPublicKeyParameters var4 = (ECPublicKeyParameters)this.key;
         BigInteger var5 = var4.getParameters().getN();
         int var6 = var5.bitLength();
         BigInteger var7 = new BigInteger(1, var1);
         if(var7.bitLength() > var6) {
            throw new DataLengthException("input too large for ECNR key.");
         } else {
            BigInteger var8 = ECConstants.ONE;
            boolean var9;
            if(var2.compareTo(var8) >= 0 && var2.compareTo(var5) < 0) {
               BigInteger var10 = ECConstants.ZERO;
               if(var3.compareTo(var10) >= 0 && var3.compareTo(var5) < 0) {
                  ECPoint var11 = var4.getParameters().getG();
                  ECPoint var12 = var4.getQ();
                  BigInteger var13 = ECAlgorithms.sumOfTwoMultiplies(var11, var3, var12, var2).getX().toBigInteger();
                  var9 = var2.subtract(var13).mod(var5).equals(var7);
               } else {
                  var9 = false;
               }
            } else {
               var9 = false;
            }

            return var9;
         }
      }
   }
}
