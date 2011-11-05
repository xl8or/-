package myorg.bouncycastle.crypto.engines;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.engines.RSACoreEngine;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSABlindingParameters;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class RSABlindingEngine implements AsymmetricBlockCipher {

   private BigInteger blindingFactor;
   private RSACoreEngine core;
   private boolean forEncryption;
   private RSAKeyParameters key;


   public RSABlindingEngine() {
      RSACoreEngine var1 = new RSACoreEngine();
      this.core = var1;
   }

   private BigInteger blindMessage(BigInteger var1) {
      BigInteger var2 = this.blindingFactor;
      BigInteger var3 = this.key.getExponent();
      BigInteger var4 = this.key.getModulus();
      BigInteger var5 = var2.modPow(var3, var4);
      BigInteger var6 = var1.multiply(var5);
      BigInteger var7 = this.key.getModulus();
      return var6.mod(var7);
   }

   private BigInteger unblindMessage(BigInteger var1) {
      BigInteger var2 = this.key.getModulus();
      BigInteger var4 = this.blindingFactor.modInverse(var2);
      return var1.multiply(var4).mod(var2);
   }

   public int getInputBlockSize() {
      return this.core.getInputBlockSize();
   }

   public int getOutputBlockSize() {
      return this.core.getOutputBlockSize();
   }

   public void init(boolean var1, CipherParameters var2) {
      RSABlindingParameters var3;
      if(var2 instanceof ParametersWithRandom) {
         var3 = (RSABlindingParameters)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (RSABlindingParameters)var2;
      }

      RSACoreEngine var4 = this.core;
      RSAKeyParameters var5 = var3.getPublicKey();
      var4.init(var1, var5);
      this.forEncryption = var1;
      RSAKeyParameters var6 = var3.getPublicKey();
      this.key = var6;
      BigInteger var7 = var3.getBlindingFactor();
      this.blindingFactor = var7;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      BigInteger var4 = this.core.convertInput(var1, var2, var3);
      if(this.forEncryption) {
         var4 = this.blindMessage(var4);
      } else {
         var4 = this.unblindMessage(var4);
      }

      return this.core.convertOutput(var4);
   }
}
