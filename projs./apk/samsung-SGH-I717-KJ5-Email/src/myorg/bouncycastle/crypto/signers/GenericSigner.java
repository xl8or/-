package myorg.bouncycastle.crypto.signers;

import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.Signer;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.util.Arrays;

public class GenericSigner implements Signer {

   private final Digest digest;
   private final AsymmetricBlockCipher engine;
   private boolean forSigning;


   public GenericSigner(AsymmetricBlockCipher var1, Digest var2) {
      this.engine = var1;
      this.digest = var2;
   }

   public byte[] generateSignature() throws CryptoException, DataLengthException {
      if(!this.forSigning) {
         throw new IllegalStateException("GenericSigner not initialised for signature generation.");
      } else {
         byte[] var1 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var1, 0);
         AsymmetricBlockCipher var3 = this.engine;
         int var4 = var1.length;
         return var3.processBlock(var1, 0, var4);
      }
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forSigning = var1;
      AsymmetricKeyParameter var3;
      if(var2 instanceof ParametersWithRandom) {
         var3 = (AsymmetricKeyParameter)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (AsymmetricKeyParameter)var2;
      }

      if(var1 && !var3.isPrivate()) {
         throw new IllegalArgumentException("signing requires private key");
      } else if(!var1 && var3.isPrivate()) {
         throw new IllegalArgumentException("verification requires public key");
      } else {
         this.reset();
         this.engine.init(var1, var2);
      }
   }

   public void reset() {
      this.digest.reset();
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public boolean verifySignature(byte[] var1) {
      if(this.forSigning) {
         throw new IllegalStateException("GenericSigner not initialised for verification");
      } else {
         byte[] var2 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var2, 0);

         boolean var6;
         boolean var7;
         try {
            AsymmetricBlockCipher var4 = this.engine;
            int var5 = var1.length;
            var6 = Arrays.constantTimeAreEqual(var4.processBlock(var1, 0, var5), var2);
         } catch (Exception var9) {
            var7 = false;
            return var7;
         }

         var7 = var6;
         return var7;
      }
   }
}
