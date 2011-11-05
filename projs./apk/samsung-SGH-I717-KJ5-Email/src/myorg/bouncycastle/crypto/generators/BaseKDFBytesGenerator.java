package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.DerivationParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.params.ISO18033KDFParameters;
import myorg.bouncycastle.crypto.params.KDFParameters;

public class BaseKDFBytesGenerator implements DerivationFunction {

   private int counterStart;
   private Digest digest;
   private byte[] iv;
   private byte[] shared;


   protected BaseKDFBytesGenerator(int var1, Digest var2) {
      this.counterStart = var1;
      this.digest = var2;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      if(var1.length - var3 < var2) {
         throw new DataLengthException("output buffer too small");
      } else {
         long var4 = (long)var3;
         int var6 = this.digest.getDigestSize();
         if(var4 > 8589934591L) {
            throw new IllegalArgumentException("Output length too large");
         } else {
            long var7 = (long)var6 + var4 - 1L;
            long var9 = (long)var6;
            int var11 = (int)(var7 / var9);
            byte[] var12 = new byte[this.digest.getDigestSize()];
            int var13 = this.counterStart;

            for(int var14 = 0; var14 < var11; ++var14) {
               Digest var15 = this.digest;
               byte[] var16 = this.shared;
               int var17 = this.shared.length;
               var15.update(var16, 0, var17);
               Digest var18 = this.digest;
               byte var19 = (byte)(var13 >> 24);
               var18.update(var19);
               Digest var20 = this.digest;
               byte var21 = (byte)(var13 >> 16);
               var20.update(var21);
               Digest var22 = this.digest;
               byte var23 = (byte)(var13 >> 8);
               var22.update(var23);
               Digest var24 = this.digest;
               byte var25 = (byte)var13;
               var24.update(var25);
               if(this.iv != null) {
                  Digest var26 = this.digest;
                  byte[] var27 = this.iv;
                  int var28 = this.iv.length;
                  var26.update(var27, 0, var28);
               }

               this.digest.doFinal(var12, 0);
               if(var3 > var6) {
                  System.arraycopy(var12, 0, var1, var2, var6);
                  var2 += var6;
                  var3 -= var6;
               } else {
                  System.arraycopy(var12, 0, var1, var2, var3);
               }

               ++var13;
            }

            this.digest.reset();
            return var3;
         }
      }
   }

   public Digest getDigest() {
      return this.digest;
   }

   public void init(DerivationParameters var1) {
      if(var1 instanceof KDFParameters) {
         KDFParameters var2 = (KDFParameters)var1;
         byte[] var3 = var2.getSharedSecret();
         this.shared = var3;
         byte[] var4 = var2.getIV();
         this.iv = var4;
      } else if(var1 instanceof ISO18033KDFParameters) {
         byte[] var5 = ((ISO18033KDFParameters)var1).getSeed();
         this.shared = var5;
         this.iv = null;
      } else {
         throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
      }
   }
}
