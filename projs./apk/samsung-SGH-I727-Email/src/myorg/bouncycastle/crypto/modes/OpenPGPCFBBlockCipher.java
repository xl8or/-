package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;

public class OpenPGPCFBBlockCipher implements BlockCipher {

   private byte[] FR;
   private byte[] FRE;
   private byte[] IV;
   private int blockSize;
   private BlockCipher cipher;
   private int count;
   private boolean forEncryption;


   public OpenPGPCFBBlockCipher(BlockCipher var1) {
      this.cipher = var1;
      int var2 = var1.getBlockSize();
      this.blockSize = var2;
      byte[] var3 = new byte[this.blockSize];
      this.IV = var3;
      byte[] var4 = new byte[this.blockSize];
      this.FR = var4;
      byte[] var5 = new byte[this.blockSize];
      this.FRE = var5;
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
            int var9 = this.count;
            int var10 = this.blockSize;
            if(var9 > var10) {
               byte var11 = var1[var2];
               byte[] var12 = this.FR;
               int var13 = this.blockSize - 2;
               var12[var13] = var11;
               int var14 = this.blockSize - 2;
               byte var15 = this.encryptByte(var11, var14);
               var3[var4] = var15;
               int var16 = var2 + 1;
               byte var17 = var1[var16];
               byte[] var18 = this.FR;
               int var19 = this.blockSize - 1;
               var18[var19] = var17;
               int var20 = var4 + 1;
               int var21 = this.blockSize - 1;
               byte var22 = this.encryptByte(var17, var21);
               var3[var20] = var22;
               BlockCipher var23 = this.cipher;
               byte[] var24 = this.FR;
               byte[] var25 = this.FRE;
               var23.processBlock(var24, 0, var25, 0);
               int var27 = 2;

               while(true) {
                  int var28 = this.blockSize;
                  if(var27 >= var28) {
                     break;
                  }

                  int var29 = var2 + var27;
                  byte var30 = var1[var29];
                  byte[] var31 = this.FR;
                  int var32 = var27 - 2;
                  var31[var32] = var30;
                  int var33 = var4 + var27;
                  int var34 = var27 - 2;
                  byte var35 = this.encryptByte(var30, var34);
                  var3[var33] = var35;
                  ++var27;
               }
            } else {
               byte var86;
               if(this.count == 0) {
                  BlockCipher var36 = this.cipher;
                  byte[] var37 = this.FR;
                  byte[] var38 = this.FRE;
                  var36.processBlock(var37, 0, var38, 0);
                  var86 = 0;

                  while(true) {
                     int var40 = this.blockSize;
                     if(var86 >= var40) {
                        int var48 = this.count;
                        int var49 = this.blockSize;
                        int var50 = var48 + var49;
                        this.count = var50;
                        break;
                     }

                     byte[] var41 = this.FR;
                     int var42 = var2 + var86;
                     byte var43 = var1[var42];
                     var41[var86] = var43;
                     int var44 = var2 + var86;
                     byte var45 = var1[var44];
                     byte var46 = this.encryptByte(var45, var86);
                     var3[var86] = var46;
                     int var47 = var86 + 1;
                  }
               } else {
                  int var51 = this.count;
                  int var52 = this.blockSize;
                  if(var51 == var52) {
                     BlockCipher var53 = this.cipher;
                     byte[] var54 = this.FR;
                     byte[] var55 = this.FRE;
                     var53.processBlock(var54, 0, var55, 0);
                     byte var57 = var1[var2];
                     int var58 = var2 + 1;
                     byte var59 = var1[var58];
                     byte var60 = this.encryptByte(var57, 0);
                     var3[var4] = var60;
                     int var61 = var4 + 1;
                     byte var62 = this.encryptByte(var59, 1);
                     var3[var61] = var62;
                     byte[] var63 = this.FR;
                     byte[] var64 = this.FR;
                     int var65 = this.blockSize - 2;
                     System.arraycopy(var63, 2, var64, 0, var65);
                     byte[] var66 = this.FR;
                     int var67 = this.blockSize - 2;
                     var66[var67] = var57;
                     byte[] var68 = this.FR;
                     int var69 = this.blockSize - 1;
                     var68[var69] = var59;
                     BlockCipher var70 = this.cipher;
                     byte[] var71 = this.FR;
                     byte[] var72 = this.FRE;
                     var70.processBlock(var71, 0, var72, 0);
                     var86 = 2;

                     while(true) {
                        int var74 = this.blockSize;
                        if(var86 >= var74) {
                           int var83 = this.count;
                           int var84 = this.blockSize;
                           int var85 = var83 + var84;
                           this.count = var85;
                           break;
                        }

                        int var75 = var2 + var86;
                        byte var76 = var1[var75];
                        byte[] var77 = this.FR;
                        int var78 = var86 - 2;
                        var77[var78] = var76;
                        int var79 = var4 + var86;
                        int var80 = var86 - 2;
                        byte var81 = this.encryptByte(var76, var80);
                        var3[var79] = var81;
                        int var82 = var86 + 1;
                     }
                  }
               }
            }

            return this.blockSize;
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
            int var9 = this.count;
            int var10 = this.blockSize;
            if(var9 > var10) {
               byte[] var11 = this.FR;
               int var12 = this.blockSize - 2;
               byte var13 = var1[var2];
               int var14 = this.blockSize - 2;
               byte var15 = this.encryptByte(var13, var14);
               var3[var4] = var15;
               var11[var12] = var15;
               byte[] var16 = this.FR;
               int var17 = this.blockSize - 1;
               int var18 = var4 + 1;
               int var19 = var2 + 1;
               byte var20 = var1[var19];
               int var21 = this.blockSize - 1;
               byte var22 = this.encryptByte(var20, var21);
               var3[var18] = var22;
               var16[var17] = var22;
               BlockCipher var23 = this.cipher;
               byte[] var24 = this.FR;
               byte[] var25 = this.FRE;
               var23.processBlock(var24, 0, var25, 0);
               int var27 = 2;

               while(true) {
                  int var28 = this.blockSize;
                  if(var27 >= var28) {
                     break;
                  }

                  byte[] var29 = this.FR;
                  int var30 = var27 - 2;
                  int var31 = var4 + var27;
                  int var32 = var2 + var27;
                  byte var33 = var1[var32];
                  int var34 = var27 - 2;
                  byte var35 = this.encryptByte(var33, var34);
                  var3[var31] = var35;
                  var29[var30] = var35;
                  ++var27;
               }
            } else {
               byte var83;
               if(this.count == 0) {
                  BlockCipher var36 = this.cipher;
                  byte[] var37 = this.FR;
                  byte[] var38 = this.FRE;
                  var36.processBlock(var37, 0, var38, 0);
                  var83 = 0;

                  while(true) {
                     int var40 = this.blockSize;
                     if(var83 >= var40) {
                        int var47 = this.count;
                        int var48 = this.blockSize;
                        int var49 = var47 + var48;
                        this.count = var49;
                        break;
                     }

                     byte[] var41 = this.FR;
                     int var42 = var4 + var83;
                     int var43 = var2 + var83;
                     byte var44 = var1[var43];
                     byte var45 = this.encryptByte(var44, var83);
                     var3[var42] = var45;
                     var41[var83] = var45;
                     int var46 = var83 + 1;
                  }
               } else {
                  int var50 = this.count;
                  int var51 = this.blockSize;
                  if(var50 == var51) {
                     BlockCipher var52 = this.cipher;
                     byte[] var53 = this.FR;
                     byte[] var54 = this.FRE;
                     var52.processBlock(var53, 0, var54, 0);
                     byte var56 = var1[var2];
                     byte var57 = this.encryptByte(var56, 0);
                     var3[var4] = var57;
                     int var58 = var4 + 1;
                     int var59 = var2 + 1;
                     byte var60 = var1[var59];
                     byte var61 = this.encryptByte(var60, 1);
                     var3[var58] = var61;
                     byte[] var62 = this.FR;
                     byte[] var63 = this.FR;
                     int var64 = this.blockSize - 2;
                     System.arraycopy(var62, 2, var63, 0, var64);
                     byte[] var65 = this.FR;
                     int var66 = this.blockSize - 2;
                     System.arraycopy(var3, var4, var65, var66, 2);
                     BlockCipher var67 = this.cipher;
                     byte[] var68 = this.FR;
                     byte[] var69 = this.FRE;
                     var67.processBlock(var68, 0, var69, 0);
                     var83 = 2;

                     while(true) {
                        int var71 = this.blockSize;
                        if(var83 >= var71) {
                           int var80 = this.count;
                           int var81 = this.blockSize;
                           int var82 = var80 + var81;
                           this.count = var82;
                           break;
                        }

                        byte[] var72 = this.FR;
                        int var73 = var83 - 2;
                        int var74 = var4 + var83;
                        int var75 = var2 + var83;
                        byte var76 = var1[var75];
                        int var77 = var83 - 2;
                        byte var78 = this.encryptByte(var76, var77);
                        var3[var74] = var78;
                        var72[var73] = var78;
                        int var79 = var83 + 1;
                     }
                  }
               }
            }

            return this.blockSize;
         }
      }
   }

   private byte encryptByte(byte var1, int var2) {
      return (byte)(this.FRE[var2] ^ var1);
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.cipher.getAlgorithmName();
      return var1.append(var2).append("/OpenPGPCFB").toString();
   }

   public int getBlockSize() {
      return this.cipher.getBlockSize();
   }

   public BlockCipher getUnderlyingCipher() {
      return this.cipher;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.forEncryption = var1;
      this.reset();
      this.cipher.init((boolean)1, var2);
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      int var5;
      if(this.forEncryption) {
         var5 = this.encryptBlock(var1, var2, var3, var4);
      } else {
         var5 = this.decryptBlock(var1, var2, var3, var4);
      }

      return var5;
   }

   public void reset() {
      this.count = 0;
      byte[] var1 = this.IV;
      byte[] var2 = this.FR;
      int var3 = this.FR.length;
      System.arraycopy(var1, 0, var2, 0, var3);
      this.cipher.reset();
   }
}
