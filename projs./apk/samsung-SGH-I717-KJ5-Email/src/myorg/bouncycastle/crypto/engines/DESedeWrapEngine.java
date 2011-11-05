package myorg.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Wrapper;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.engines.DESedeEngine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.util.Arrays;

public class DESedeWrapEngine implements Wrapper {

   private static final byte[] IV2 = new byte[]{(byte)74, (byte)221, (byte)162, (byte)44, (byte)121, (byte)232, (byte)33, (byte)5};
   byte[] digest;
   private CBCBlockCipher engine;
   private boolean forWrapping;
   private byte[] iv;
   private KeyParameter param;
   private ParametersWithIV paramPlusIV;
   Digest sha1;


   public DESedeWrapEngine() {
      SHA1Digest var1 = new SHA1Digest();
      this.sha1 = var1;
      byte[] var2 = new byte[20];
      this.digest = var2;
   }

   private byte[] calculateCMSKeyChecksum(byte[] var1) {
      byte[] var2 = new byte[8];
      Digest var3 = this.sha1;
      int var4 = var1.length;
      var3.update(var1, 0, var4);
      Digest var5 = this.sha1;
      byte[] var6 = this.digest;
      var5.doFinal(var6, 0);
      System.arraycopy(this.digest, 0, var2, 0, 8);
      return var2;
   }

   private boolean checkCMSKeyChecksum(byte[] var1, byte[] var2) {
      return Arrays.constantTimeAreEqual(this.calculateCMSKeyChecksum(var1), var2);
   }

   private static byte[] reverse(byte[] var0) {
      byte[] var1 = new byte[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return var1;
         }

         int var4 = var0.length;
         int var5 = var2 + 1;
         int var6 = var4 - var5;
         byte var7 = var0[var6];
         var1[var2] = var7;
         ++var2;
      }
   }

   public String getAlgorithmName() {
      return "DESede";
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      DESedeEngine var3 = new DESedeEngine();
      CBCBlockCipher var4 = new CBCBlockCipher(var3);
      this.engine = var4;
      SecureRandom var6;
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var5 = (ParametersWithRandom)var2;
         var2 = var5.getParameters();
         var6 = var5.getRandom();
      } else {
         var6 = new SecureRandom();
      }

      if(var2 instanceof KeyParameter) {
         KeyParameter var7 = (KeyParameter)var2;
         this.param = var7;
         if(this.forWrapping) {
            byte[] var8 = new byte[8];
            this.iv = var8;
            byte[] var9 = this.iv;
            var6.nextBytes(var9);
            KeyParameter var10 = this.param;
            byte[] var11 = this.iv;
            ParametersWithIV var12 = new ParametersWithIV(var10, var11);
            this.paramPlusIV = var12;
         }
      } else if(var2 instanceof ParametersWithIV) {
         ParametersWithIV var13 = (ParametersWithIV)var2;
         this.paramPlusIV = var13;
         byte[] var14 = this.paramPlusIV.getIV();
         this.iv = var14;
         KeyParameter var15 = (KeyParameter)this.paramPlusIV.getParameters();
         this.param = var15;
         if(this.forWrapping) {
            if(this.iv == null || this.iv.length != 8) {
               throw new IllegalArgumentException("IV is not 8 octets");
            }
         } else {
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
         }
      }
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if(this.forWrapping) {
         throw new IllegalStateException("Not set for unwrapping");
      } else if(var1 == null) {
         throw new InvalidCipherTextException("Null pointer as ciphertext");
      } else {
         int var4 = this.engine.getBlockSize();
         if(var3 % var4 != 0) {
            String var5 = "Ciphertext not multiple of " + var4;
            throw new InvalidCipherTextException(var5);
         } else {
            KeyParameter var6 = this.param;
            byte[] var7 = IV2;
            ParametersWithIV var8 = new ParametersWithIV(var6, var7);
            this.engine.init((boolean)0, var8);
            byte[] var9 = new byte[var3];

            for(int var10 = 0; var10 != var3; var10 += var4) {
               CBCBlockCipher var13 = this.engine;
               int var14 = var2 + var10;
               int var20 = var13.processBlock(var1, var14, var9, var10);
            }

            byte[] var21 = reverse(var9);
            byte[] var22 = new byte[8];
            this.iv = var22;
            byte[] var23 = new byte[var21.length - 8];
            byte[] var24 = this.iv;
            byte var26 = 0;
            byte var28 = 0;
            byte var29 = 8;
            System.arraycopy(var21, var26, var24, var28, var29);
            int var30 = var21.length - 8;
            byte var32 = 8;
            byte var34 = 0;
            System.arraycopy(var21, var32, var23, var34, var30);
            KeyParameter var36 = this.param;
            byte[] var37 = this.iv;
            ParametersWithIV var38 = new ParametersWithIV(var36, var37);
            this.paramPlusIV = var38;
            CBCBlockCipher var39 = this.engine;
            ParametersWithIV var40 = this.paramPlusIV;
            var39.init((boolean)0, var40);
            byte[] var41 = new byte[var23.length];
            int var42 = 0;

            while(true) {
               int var43 = var41.length;
               if(var42 == var43) {
                  byte[] var45 = new byte[var41.length - 8];
                  byte[] var46 = new byte[8];
                  int var47 = var41.length - 8;
                  byte var49 = 0;
                  byte var51 = 0;
                  System.arraycopy(var41, var49, var45, var51, var47);
                  int var53 = var41.length - 8;
                  byte var57 = 0;
                  byte var58 = 8;
                  System.arraycopy(var41, var53, var46, var57, var58);
                  if(!this.checkCMSKeyChecksum(var45, var46)) {
                     throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
                  } else {
                     return var45;
                  }
               }

               this.engine.processBlock(var23, var42, var41, var42);
               var42 += var4;
            }
         }
      }
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if(!this.forWrapping) {
         throw new IllegalStateException("Not initialized for wrapping");
      } else {
         byte[] var4 = new byte[var3];
         byte var8 = 0;
         System.arraycopy(var1, var2, var4, var8, var3);
         byte[] var12 = this.calculateCMSKeyChecksum(var4);
         int var13 = var4.length;
         int var14 = var12.length;
         byte[] var15 = new byte[var13 + var14];
         int var16 = var4.length;
         byte var18 = 0;
         byte var20 = 0;
         System.arraycopy(var4, var18, var15, var20, var16);
         int var22 = var4.length;
         int var23 = var12.length;
         byte var25 = 0;
         System.arraycopy(var12, var25, var15, var22, var23);
         int var29 = this.engine.getBlockSize();
         if(var15.length % var29 != 0) {
            throw new IllegalStateException("Not multiple of block length");
         } else {
            CBCBlockCipher var30 = this.engine;
            ParametersWithIV var31 = this.paramPlusIV;
            var30.init((boolean)1, var31);
            byte[] var32 = new byte[var15.length];
            int var33 = 0;

            while(true) {
               int var34 = var15.length;
               if(var33 == var34) {
                  int var36 = this.iv.length;
                  int var37 = var32.length;
                  byte[] var38 = new byte[var36 + var37];
                  byte[] var39 = this.iv;
                  int var40 = this.iv.length;
                  byte var42 = 0;
                  byte var44 = 0;
                  System.arraycopy(var39, var42, var38, var44, var40);
                  int var46 = this.iv.length;
                  int var47 = var32.length;
                  byte var49 = 0;
                  System.arraycopy(var32, var49, var38, var46, var47);
                  byte[] var53 = reverse(var38);
                  KeyParameter var54 = this.param;
                  byte[] var55 = IV2;
                  ParametersWithIV var56 = new ParametersWithIV(var54, var55);
                  this.engine.init((boolean)1, var56);
                  int var57 = 0;

                  while(true) {
                     int var58 = var53.length;
                     if(var57 == var58) {
                        return var53;
                     }

                     this.engine.processBlock(var53, var57, var53, var57);
                     var57 += var29;
                  }
               }

               this.engine.processBlock(var15, var33, var32, var33);
               var33 += var29;
            }
         }
      }
   }
}
