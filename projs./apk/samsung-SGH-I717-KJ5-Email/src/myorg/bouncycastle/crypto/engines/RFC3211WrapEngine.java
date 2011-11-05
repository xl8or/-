package myorg.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Wrapper;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class RFC3211WrapEngine implements Wrapper {

   private CBCBlockCipher engine;
   private boolean forWrapping;
   private ParametersWithIV param;
   private SecureRandom rand;


   public RFC3211WrapEngine(BlockCipher var1) {
      CBCBlockCipher var2 = new CBCBlockCipher(var1);
      this.engine = var2;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.engine.getUnderlyingCipher().getAlgorithmName();
      return var1.append(var2).append("/RFC3211Wrap").toString();
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         SecureRandom var4 = var3.getRandom();
         this.rand = var4;
         ParametersWithIV var5 = (ParametersWithIV)var3.getParameters();
         this.param = var5;
      } else {
         if(var1) {
            SecureRandom var6 = new SecureRandom();
            this.rand = var6;
         }

         ParametersWithIV var7 = (ParametersWithIV)var2;
         this.param = var7;
      }
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if(this.forWrapping) {
         throw new IllegalStateException("not set for unwrapping");
      } else {
         int var4 = this.engine.getBlockSize();
         int var5 = var4 * 2;
         if(var3 < var5) {
            throw new InvalidCipherTextException("input too short");
         } else {
            byte[] var6 = new byte[var3];
            byte[] var7 = new byte[var4];
            System.arraycopy(var1, var2, var6, 0, var3);
            int var8 = var7.length;
            System.arraycopy(var1, var2, var7, 0, var8);
            CBCBlockCipher var9 = this.engine;
            CipherParameters var10 = this.param.getParameters();
            ParametersWithIV var11 = new ParametersWithIV(var10, var7);
            var9.init((boolean)0, var11);
            int var12 = var4;

            while(true) {
               int var13 = var6.length;
               if(var12 >= var13) {
                  int var15 = var6.length;
                  int var16 = var7.length;
                  int var17 = var15 - var16;
                  int var18 = var7.length;
                  System.arraycopy(var6, var17, var7, 0, var18);
                  CBCBlockCipher var19 = this.engine;
                  CipherParameters var20 = this.param.getParameters();
                  ParametersWithIV var21 = new ParametersWithIV(var20, var7);
                  var19.init((boolean)0, var21);
                  this.engine.processBlock(var6, 0, var6, 0);
                  CBCBlockCipher var23 = this.engine;
                  ParametersWithIV var24 = this.param;
                  var23.init((boolean)0, var24);
                  int var25 = 0;

                  while(true) {
                     int var26 = var6.length;
                     if(var25 >= var26) {
                        int var28 = var6[0] & 255;
                        int var29 = var6.length - 4;
                        if(var28 > var29) {
                           throw new InvalidCipherTextException("wrapped key corrupted");
                        } else {
                           byte[] var30 = new byte[var6[0] & 255];
                           byte var31 = var6[0];
                           System.arraycopy(var6, 4, var30, 0, var31);
                           int var32 = 0;

                           for(int var33 = 0; var33 != 3; ++var33) {
                              int var34 = var33 + 1;
                              byte var35 = (byte)(~var6[var34]);
                              int var36 = var30[var33] ^ var35;
                              var32 |= var36;
                           }

                           if(var32 != 0) {
                              throw new InvalidCipherTextException("wrapped key fails checksum");
                           } else {
                              return var30;
                           }
                        }
                     }

                     this.engine.processBlock(var6, var25, var6, var25);
                     var25 += var4;
                  }
               }

               this.engine.processBlock(var6, var12, var6, var12);
               var12 += var4;
            }
         }
      }
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if(!this.forWrapping) {
         throw new IllegalStateException("not set for wrapping");
      } else {
         CBCBlockCipher var4 = this.engine;
         ParametersWithIV var5 = this.param;
         var4.init((boolean)1, var5);
         int var6 = this.engine.getBlockSize();
         int var7 = var3 + 4;
         int var8 = var6 * 2;
         byte[] var9;
         if(var7 < var8) {
            var9 = new byte[var6 * 2];
         } else {
            int var19;
            if((var3 + 4) % var6 == 0) {
               var19 = var3 + 4;
            } else {
               var19 = ((var3 + 4) / var6 + 1) * var6;
            }

            var9 = new byte[var19];
         }

         byte var10 = (byte)var3;
         var9[0] = var10;
         byte var11 = (byte)(~var1[var2]);
         var9[1] = var11;
         int var12 = var2 + 1;
         byte var13 = (byte)(~var1[var12]);
         var9[2] = var13;
         int var14 = var2 + 2;
         byte var15 = (byte)(~var1[var14]);
         var9[3] = var15;
         System.arraycopy(var1, var2, var9, 4, var3);
         int var16 = var3 + 4;

         while(true) {
            int var17 = var9.length;
            if(var16 >= var17) {
               int var20 = 0;

               while(true) {
                  int var21 = var9.length;
                  if(var20 >= var21) {
                     int var23 = 0;

                     while(true) {
                        int var24 = var9.length;
                        if(var23 >= var24) {
                           return var9;
                        }

                        this.engine.processBlock(var9, var23, var9, var23);
                        var23 += var6;
                     }
                  }

                  this.engine.processBlock(var9, var20, var9, var20);
                  var20 += var6;
               }
            }

            byte var18 = (byte)this.rand.nextInt();
            var9[var16] = var18;
            ++var16;
         }
      }
   }
}
