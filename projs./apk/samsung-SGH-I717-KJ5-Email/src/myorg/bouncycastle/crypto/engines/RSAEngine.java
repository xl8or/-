package myorg.bouncycastle.crypto.engines;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.engines.RSACoreEngine;

public class RSAEngine implements AsymmetricBlockCipher {

   private RSACoreEngine core;


   public RSAEngine() {}

   public int getInputBlockSize() {
      return this.core.getInputBlockSize();
   }

   public int getOutputBlockSize() {
      return this.core.getOutputBlockSize();
   }

   public void init(boolean var1, CipherParameters var2) {
      if(this.core == null) {
         RSACoreEngine var3 = new RSACoreEngine();
         this.core = var3;
      }

      this.core.init(var1, var2);
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      if(this.core == null) {
         throw new IllegalStateException("RSA engine not initialised");
      } else {
         RSACoreEngine var4 = this.core;
         RSACoreEngine var5 = this.core;
         BigInteger var6 = this.core.convertInput(var1, var2, var3);
         BigInteger var7 = var5.processBlock(var6);
         return var4.convertOutput(var7);
      }
   }
}
