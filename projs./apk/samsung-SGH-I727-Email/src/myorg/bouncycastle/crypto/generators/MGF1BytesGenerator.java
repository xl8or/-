package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.DerivationParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.params.MGFParameters;

public class MGF1BytesGenerator implements DerivationFunction {

   private Digest digest;
   private int hLen;
   private byte[] seed;


   public MGF1BytesGenerator(Digest var1) {
      this.digest = var1;
      int var2 = var1.getDigestSize();
      this.hLen = var2;
   }

   private void ItoOSP(int var1, byte[] var2) {
      byte var3 = (byte)(var1 >>> 24);
      var2[0] = var3;
      byte var4 = (byte)(var1 >>> 16);
      var2[1] = var4;
      byte var5 = (byte)(var1 >>> 8);
      var2[2] = var5;
      byte var6 = (byte)(var1 >>> 0);
      var2[3] = var6;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      if(var1.length - var3 < var2) {
         throw new DataLengthException("output buffer too small");
      } else {
         byte[] var4 = new byte[this.hLen];
         byte[] var5 = new byte[4];
         int var6 = 0;
         this.digest.reset();
         int var7 = this.hLen;
         int var17;
         if(var3 > var7) {
            do {
               this.ItoOSP(var6, var5);
               Digest var8 = this.digest;
               byte[] var9 = this.seed;
               int var10 = this.seed.length;
               var8.update(var9, 0, var10);
               Digest var11 = this.digest;
               int var12 = var5.length;
               var11.update(var5, 0, var12);
               this.digest.doFinal(var4, 0);
               int var14 = this.hLen * var6 + var2;
               int var15 = this.hLen;
               System.arraycopy(var4, 0, var1, var14, var15);
               ++var6;
               int var16 = this.hLen;
               var17 = var3 / var16;
            } while(var6 < var17);
         }

         if(this.hLen * var6 < var3) {
            this.ItoOSP(var6, var5);
            Digest var18 = this.digest;
            byte[] var19 = this.seed;
            int var20 = this.seed.length;
            var18.update(var19, 0, var20);
            Digest var21 = this.digest;
            int var22 = var5.length;
            var21.update(var5, 0, var22);
            this.digest.doFinal(var4, 0);
            int var24 = this.hLen * var6 + var2;
            int var25 = this.hLen * var6;
            int var26 = var3 - var25;
            System.arraycopy(var4, 0, var1, var24, var26);
         }

         return var3;
      }
   }

   public Digest getDigest() {
      return this.digest;
   }

   public void init(DerivationParameters var1) {
      if(!(var1 instanceof MGFParameters)) {
         throw new IllegalArgumentException("MGF parameters required for MGF1Generator");
      } else {
         byte[] var2 = ((MGFParameters)var1).getSeed();
         this.seed = var2;
      }
   }
}
