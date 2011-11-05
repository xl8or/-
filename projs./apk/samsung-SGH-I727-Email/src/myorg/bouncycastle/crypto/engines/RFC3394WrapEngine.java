package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Wrapper;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.util.Arrays;

public class RFC3394WrapEngine implements Wrapper {

   private BlockCipher engine;
   private boolean forWrapping;
   private byte[] iv;
   private KeyParameter param;


   public RFC3394WrapEngine(BlockCipher var1) {
      byte[] var2 = new byte[]{(byte)166, (byte)166, (byte)166, (byte)166, (byte)166, (byte)166, (byte)166, (byte)166};
      this.iv = var2;
      this.engine = var1;
   }

   public String getAlgorithmName() {
      return this.engine.getAlgorithmName();
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      if(var2 instanceof ParametersWithRandom) {
         var2 = ((ParametersWithRandom)var2).getParameters();
      }

      if(var2 instanceof KeyParameter) {
         KeyParameter var3 = (KeyParameter)var2;
         this.param = var3;
      } else if(var2 instanceof ParametersWithIV) {
         byte[] var4 = ((ParametersWithIV)var2).getIV();
         this.iv = var4;
         KeyParameter var5 = (KeyParameter)((ParametersWithIV)var2).getParameters();
         this.param = var5;
         if(this.iv.length != 8) {
            throw new IllegalArgumentException("IV not equal to 8");
         }
      }
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if(this.forWrapping) {
         throw new IllegalStateException("not set for unwrapping");
      } else {
         int var4 = var3 / 8;
         if(var4 * 8 != var3) {
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
         } else {
            int var5 = this.iv.length;
            byte[] var6 = new byte[var3 - var5];
            byte[] var7 = new byte[this.iv.length];
            byte[] var8 = new byte[this.iv.length + 8];
            int var9 = this.iv.length;
            System.arraycopy(var1, 0, var7, 0, var9);
            int var10 = this.iv.length;
            int var11 = this.iv.length;
            int var12 = var3 - var11;
            System.arraycopy(var1, var10, var6, 0, var12);
            BlockCipher var13 = this.engine;
            KeyParameter var14 = this.param;
            var13.init((boolean)0, var14);
            int var15 = var4 - 1;

            for(int var16 = 5; var16 >= 0; var16 += -1) {
               for(int var17 = var15; var17 >= 1; var17 += -1) {
                  int var18 = this.iv.length;
                  System.arraycopy(var7, 0, var8, 0, var18);
                  int var19 = (var17 - 1) * 8;
                  int var20 = this.iv.length;
                  System.arraycopy(var6, var19, var8, var20, 8);
                  int var21 = var15 * var16 + var17;

                  for(int var22 = 1; var21 != 0; ++var22) {
                     byte var23 = (byte)var21;
                     int var24 = this.iv.length - var22;
                     byte var25 = (byte)(var8[var24] ^ var23);
                     var8[var24] = var25;
                     var21 >>>= 8;
                  }

                  this.engine.processBlock(var8, 0, var8, 0);
                  System.arraycopy(var8, 0, var7, 0, 8);
                  int var27 = (var17 - 1) * 8;
                  System.arraycopy(var8, 8, var6, var27, 8);
               }
            }

            byte[] var28 = this.iv;
            if(!Arrays.constantTimeAreEqual(var7, var28)) {
               throw new InvalidCipherTextException("checksum failed");
            } else {
               return var6;
            }
         }
      }
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if(!this.forWrapping) {
         throw new IllegalStateException("not set for wrapping");
      } else {
         int var4 = var3 / 8;
         if(var4 * 8 != var3) {
            throw new DataLengthException("wrap data must be a multiple of 8 bytes");
         } else {
            byte[] var5 = new byte[this.iv.length + var3];
            byte[] var6 = new byte[this.iv.length + 8];
            byte[] var7 = this.iv;
            int var8 = this.iv.length;
            System.arraycopy(var7, 0, var5, 0, var8);
            int var9 = this.iv.length;
            System.arraycopy(var1, 0, var5, var9, var3);
            BlockCipher var10 = this.engine;
            KeyParameter var11 = this.param;
            var10.init((boolean)1, var11);

            for(int var12 = 0; var12 != 6; ++var12) {
               for(int var13 = 1; var13 <= var4; ++var13) {
                  int var14 = this.iv.length;
                  System.arraycopy(var5, 0, var6, 0, var14);
                  int var15 = var13 * 8;
                  int var16 = this.iv.length;
                  System.arraycopy(var5, var15, var6, var16, 8);
                  this.engine.processBlock(var6, 0, var6, 0);
                  int var18 = var4 * var12 + var13;

                  for(int var19 = 1; var18 != 0; ++var19) {
                     byte var20 = (byte)var18;
                     int var21 = this.iv.length - var19;
                     byte var22 = (byte)(var6[var21] ^ var20);
                     var6[var21] = var22;
                     var18 >>>= 8;
                  }

                  System.arraycopy(var6, 0, var5, 0, 8);
                  int var23 = var13 * 8;
                  System.arraycopy(var6, 8, var5, var23, 8);
               }
            }

            return var5;
         }
      }
   }
}
