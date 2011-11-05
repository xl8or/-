package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class PGPCFBBlockCipher implements BlockCipher {

   private byte[] FR;
   private byte[] FRE;
   private byte[] IV;
   private int blockSize;
   private BlockCipher cipher;
   private int count;
   private boolean forEncryption;
   private boolean inlineIv;
   private byte[] tmp;


   public PGPCFBBlockCipher(BlockCipher var1, boolean var2) {
      this.cipher = var1;
      this.inlineIv = var2;
      int var3 = var1.getBlockSize();
      this.blockSize = var3;
      byte[] var4 = new byte[this.blockSize];
      this.IV = var4;
      byte[] var5 = new byte[this.blockSize];
      this.FR = var5;
      byte[] var6 = new byte[this.blockSize];
      this.FRE = var6;
      byte[] var7 = new byte[this.blockSize];
      this.tmp = var7;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var7 = this.blockSize + var4;
         int var8 = var3.length;
         if(var7 > var8) {
            throw new DataLengthException("output buffer too short");
         } else {
            BlockCipher var9 = this.cipher;
            byte[] var10 = this.FR;
            byte[] var11 = this.FRE;
            var9.processBlock(var10, 0, var11, 0);
            int var13 = 0;

            while(true) {
               int var14 = this.blockSize;
               if(var13 >= var14) {
                  int var19 = 0;

                  while(true) {
                     int var20 = this.blockSize;
                     if(var19 >= var20) {
                        return this.blockSize;
                     }

                     byte[] var21 = this.FR;
                     int var22 = var2 + var19;
                     byte var23 = var1[var22];
                     var21[var19] = var23;
                     ++var19;
                  }
               }

               int var15 = var4 + var13;
               int var16 = var2 + var13;
               byte var17 = var1[var16];
               byte var18 = this.encryptByte(var17, var13);
               var3[var15] = var18;
               ++var13;
            }
         }
      }
   }

   private int decryptBlockWithIV(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var7 = this.blockSize + var4;
         int var8 = var3.length;
         if(var7 > var8) {
            throw new DataLengthException("output buffer too short");
         } else {
            int var21;
            if(this.count == 0) {
               int var9 = 0;

               while(true) {
                  int var10 = this.blockSize;
                  if(var9 >= var10) {
                     BlockCipher var14 = this.cipher;
                     byte[] var15 = this.FR;
                     byte[] var16 = this.FRE;
                     var14.processBlock(var15, 0, var16, 0);
                     int var18 = this.count;
                     int var19 = this.blockSize;
                     int var20 = var18 + var19;
                     this.count = var20;
                     var21 = 0;
                     break;
                  }

                  byte[] var11 = this.FR;
                  int var12 = var2 + var9;
                  byte var13 = var1[var12];
                  var11[var9] = var13;
                  ++var9;
               }
            } else {
               int var22 = this.count;
               int var23 = this.blockSize;
               byte var79;
               if(var22 == var23) {
                  byte[] var24 = this.tmp;
                  int var25 = this.blockSize;
                  System.arraycopy(var1, var2, var24, 0, var25);
                  byte[] var26 = this.FR;
                  byte[] var27 = this.FR;
                  int var28 = this.blockSize - 2;
                  System.arraycopy(var26, 2, var27, 0, var28);
                  byte[] var29 = this.FR;
                  int var30 = this.blockSize - 2;
                  byte var31 = this.tmp[0];
                  var29[var30] = var31;
                  byte[] var32 = this.FR;
                  int var33 = this.blockSize - 1;
                  byte var34 = this.tmp[1];
                  var32[var33] = var34;
                  BlockCipher var35 = this.cipher;
                  byte[] var36 = this.FR;
                  byte[] var37 = this.FRE;
                  var35.processBlock(var36, 0, var37, 0);
                  var79 = 0;

                  while(true) {
                     int var39 = this.blockSize - 2;
                     if(var79 >= var39) {
                        byte[] var46 = this.tmp;
                        byte[] var47 = this.FR;
                        int var48 = this.blockSize - 2;
                        System.arraycopy(var46, 2, var47, 0, var48);
                        int var49 = this.count + 2;
                        this.count = var49;
                        var21 = this.blockSize - 2;
                        break;
                     }

                     int var40 = var4 + var79;
                     byte[] var41 = this.tmp;
                     int var42 = var79 + 2;
                     byte var43 = var41[var42];
                     byte var44 = this.encryptByte(var43, var79);
                     var3[var40] = var44;
                     int var45 = var79 + 1;
                  }
               } else {
                  int var50 = this.count;
                  int var51 = this.blockSize + 2;
                  if(var50 >= var51) {
                     byte[] var52 = this.tmp;
                     int var53 = this.blockSize;
                     System.arraycopy(var1, var2, var52, 0, var53);
                     int var54 = var4 + 0;
                     byte var55 = this.tmp[0];
                     int var56 = this.blockSize - 2;
                     byte var57 = this.encryptByte(var55, var56);
                     var3[var54] = var57;
                     int var58 = var4 + 1;
                     byte var59 = this.tmp[1];
                     int var60 = this.blockSize - 1;
                     byte var61 = this.encryptByte(var59, var60);
                     var3[var58] = var61;
                     byte[] var62 = this.tmp;
                     byte[] var63 = this.FR;
                     int var64 = this.blockSize - 2;
                     System.arraycopy(var62, 0, var63, var64, 2);
                     BlockCipher var65 = this.cipher;
                     byte[] var66 = this.FR;
                     byte[] var67 = this.FRE;
                     var65.processBlock(var66, 0, var67, 0);
                     var79 = 0;

                     while(true) {
                        int var69 = this.blockSize - 2;
                        if(var79 >= var69) {
                           byte[] var76 = this.tmp;
                           byte[] var77 = this.FR;
                           int var78 = this.blockSize - 2;
                           System.arraycopy(var76, 2, var77, 0, var78);
                           break;
                        }

                        int var70 = var4 + var79 + 2;
                        byte[] var71 = this.tmp;
                        int var72 = var79 + 2;
                        byte var73 = var71[var72];
                        byte var74 = this.encryptByte(var73, var79);
                        var3[var70] = var74;
                        int var75 = var79 + 1;
                     }
                  }

                  var21 = this.blockSize;
               }
            }

            return var21;
         }
      }
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var7 = this.blockSize + var4;
         int var8 = var3.length;
         if(var7 > var8) {
            throw new DataLengthException("output buffer too short");
         } else {
            BlockCipher var9 = this.cipher;
            byte[] var10 = this.FR;
            byte[] var11 = this.FRE;
            var9.processBlock(var10, 0, var11, 0);
            int var13 = 0;

            while(true) {
               int var14 = this.blockSize;
               if(var13 >= var14) {
                  int var19 = 0;

                  while(true) {
                     int var20 = this.blockSize;
                     if(var19 >= var20) {
                        return this.blockSize;
                     }

                     byte[] var21 = this.FR;
                     int var22 = var4 + var19;
                     byte var23 = var3[var22];
                     var21[var19] = var23;
                     ++var19;
                  }
               }

               int var15 = var4 + var13;
               int var16 = var2 + var13;
               byte var17 = var1[var16];
               byte var18 = this.encryptByte(var17, var13);
               var3[var15] = var18;
               ++var13;
            }
         }
      }
   }

   private int encryptBlockWithIV(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5 = this.blockSize + var2;
      int var6 = var1.length;
      if(var5 > var6) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var7 = this.blockSize + var4;
         int var8 = var3.length;
         if(var7 > var8) {
            throw new DataLengthException("output buffer too short");
         } else {
            int var53;
            if(this.count == 0) {
               BlockCipher var9 = this.cipher;
               byte[] var10 = this.FR;
               byte[] var11 = this.FRE;
               var9.processBlock(var10, 0, var11, 0);
               int var13 = 0;

               while(true) {
                  int var14 = this.blockSize;
                  if(var13 >= var14) {
                     byte[] var18 = this.FR;
                     int var19 = this.blockSize;
                     System.arraycopy(var3, var4, var18, 0, var19);
                     BlockCipher var20 = this.cipher;
                     byte[] var21 = this.FR;
                     byte[] var22 = this.FRE;
                     var20.processBlock(var21, 0, var22, 0);
                     int var24 = this.blockSize + var4;
                     byte[] var25 = this.IV;
                     int var26 = this.blockSize - 2;
                     byte var27 = var25[var26];
                     byte var28 = this.encryptByte(var27, 0);
                     var3[var24] = var28;
                     int var29 = this.blockSize + var4 + 1;
                     byte[] var30 = this.IV;
                     int var31 = this.blockSize - 1;
                     byte var32 = var30[var31];
                     byte var33 = this.encryptByte(var32, 1);
                     var3[var29] = var33;
                     int var34 = var4 + 2;
                     byte[] var35 = this.FR;
                     int var36 = this.blockSize;
                     System.arraycopy(var3, var34, var35, 0, var36);
                     BlockCipher var37 = this.cipher;
                     byte[] var38 = this.FR;
                     byte[] var39 = this.FRE;
                     var37.processBlock(var38, 0, var39, 0);
                     int var41 = 0;

                     while(true) {
                        int var42 = this.blockSize;
                        if(var41 >= var42) {
                           int var47 = this.blockSize + var4 + 2;
                           byte[] var48 = this.FR;
                           int var49 = this.blockSize;
                           System.arraycopy(var3, var47, var48, 0, var49);
                           int var50 = this.count;
                           int var51 = this.blockSize * 2 + 2;
                           int var52 = var50 + var51;
                           this.count = var52;
                           var53 = this.blockSize * 2 + 2;
                           return var53;
                        }

                        int var43 = this.blockSize + var4 + 2 + var41;
                        int var44 = var2 + var41;
                        byte var45 = var1[var44];
                        byte var46 = this.encryptByte(var45, var41);
                        var3[var43] = var46;
                        ++var41;
                     }
                  }

                  int var15 = var4 + var13;
                  byte var16 = this.IV[var13];
                  byte var17 = this.encryptByte(var16, var13);
                  var3[var15] = var17;
                  ++var13;
               }
            } else {
               int var54 = this.count;
               int var55 = this.blockSize + 2;
               if(var54 >= var55) {
                  BlockCipher var56 = this.cipher;
                  byte[] var57 = this.FR;
                  byte[] var58 = this.FRE;
                  var56.processBlock(var57, 0, var58, 0);
                  byte var68 = 0;

                  while(true) {
                     int var60 = this.blockSize;
                     if(var68 >= var60) {
                        byte[] var66 = this.FR;
                        int var67 = this.blockSize;
                        System.arraycopy(var3, var4, var66, 0, var67);
                        break;
                     }

                     int var61 = var4 + var68;
                     int var62 = var2 + var68;
                     byte var63 = var1[var62];
                     byte var64 = this.encryptByte(var63, var68);
                     var3[var61] = var64;
                     int var65 = var68 + 1;
                  }
               }

               var53 = this.blockSize;
               return var53;
            }
         }
      }
   }

   private byte encryptByte(byte var1, int var2) {
      return (byte)(this.FRE[var2] ^ var1);
   }

   public String getAlgorithmName() {
      String var3;
      if(this.inlineIv) {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.cipher.getAlgorithmName();
         var3 = var1.append(var2).append("/PGPCFBwithIV").toString();
      } else {
         StringBuilder var4 = new StringBuilder();
         String var5 = this.cipher.getAlgorithmName();
         var3 = var4.append(var5).append("/PGPCFB").toString();
      }

      return var3;
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      if(!(var2 instanceof ParametersWithIV)) {
         this.reset();
         this.cipher.init((boolean)1, var2);
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         int var5 = var4.length;
         int var6 = this.IV.length;
         if(var5 < var6) {
            byte[] var7 = this.IV;
            int var8 = this.IV.length;
            int var9 = var4.length;
            int var10 = var8 - var9;
            int var11 = var4.length;
            System.arraycopy(var4, 0, var7, var10, var11);
            int var12 = 0;

            while(true) {
               int var13 = this.IV.length;
               int var14 = var4.length;
               int var15 = var13 - var14;
               if(var12 >= var15) {
                  break;
               }

               this.IV[var12] = 0;
               ++var12;
            }
         } else {
            byte[] var16 = this.IV;
            int var17 = this.IV.length;
            System.arraycopy(var4, 0, var16, 0, var17);
         }

         this.reset();
         BlockCipher var18 = this.cipher;
         CipherParameters var19 = var3.getParameters();
         var18.init((boolean)1, var19);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5;
      if(this.inlineIv) {
         if(this.forEncryption) {
            var5 = this.encryptBlockWithIV(var1, var2, var3, var4);
         } else {
            var5 = this.decryptBlockWithIV(var1, var2, var3, var4);
         }
      } else if(this.forEncryption) {
         var5 = this.encryptBlock(var1, var2, var3, var4);
      } else {
         var5 = this.decryptBlock(var1, var2, var3, var4);
      }

      return var5;
   }

   public void reset() {
      this.count = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.FR.length;
         if(var1 == var2) {
            this.cipher.reset();
            return;
         }

         if(this.inlineIv) {
            this.FR[var1] = 0;
         } else {
            byte[] var3 = this.FR;
            byte var4 = this.IV[var1];
            var3[var1] = var4;
         }

         ++var1;
      }
   }
}
