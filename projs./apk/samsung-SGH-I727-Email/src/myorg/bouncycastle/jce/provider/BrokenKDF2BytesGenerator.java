package myorg.bouncycastle.jce.provider;

import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.DerivationParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.params.KDFParameters;

public class BrokenKDF2BytesGenerator implements DerivationFunction {

   private Digest digest;
   private byte[] iv;
   private byte[] shared;


   public BrokenKDF2BytesGenerator(Digest var1) {
      this.digest = var1;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      if(var1.length - var3 < var2) {
         throw new DataLengthException("output buffer too small");
      } else {
         long var4 = (long)(var3 * 8);
         long var6 = (long)(this.digest.getDigestSize() * 8) * 29L;
         if(var4 > var6) {
            new IllegalArgumentException("Output length to large");
         }

         long var9 = (long)this.digest.getDigestSize();
         int var11 = (int)(var4 / var9);
         byte[] var12 = new byte[this.digest.getDigestSize()];

         for(int var13 = 1; var13 <= var11; ++var13) {
            Digest var14 = this.digest;
            byte[] var15 = this.shared;
            int var16 = this.shared.length;
            var14.update(var15, 0, var16);
            Digest var17 = this.digest;
            byte var18 = (byte)(var13 & 255);
            var17.update(var18);
            Digest var19 = this.digest;
            byte var20 = (byte)(var13 >> 8 & 255);
            var19.update(var20);
            Digest var21 = this.digest;
            byte var22 = (byte)(var13 >> 16 & 255);
            var21.update(var22);
            Digest var23 = this.digest;
            byte var24 = (byte)(var13 >> 24 & 255);
            var23.update(var24);
            Digest var25 = this.digest;
            byte[] var26 = this.iv;
            int var27 = this.iv.length;
            var25.update(var26, 0, var27);
            this.digest.doFinal(var12, 0);
            int var29 = var3 - var2;
            int var30 = var12.length;
            if(var29 > var30) {
               int var31 = var12.length;
               System.arraycopy(var12, 0, var1, var2, var31);
               int var32 = var12.length;
               var2 += var32;
            } else {
               int var33 = var3 - var2;
               System.arraycopy(var12, 0, var1, var2, var33);
            }
         }

         this.digest.reset();
         return var3;
      }
   }

   public Digest getDigest() {
      return this.digest;
   }

   public void init(DerivationParameters var1) {
      if(!(var1 instanceof KDFParameters)) {
         throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
      } else {
         KDFParameters var2 = (KDFParameters)var1;
         byte[] var3 = var2.getSharedSecret();
         this.shared = var3;
         byte[] var4 = var2.getIV();
         this.iv = var4;
      }
   }
}
