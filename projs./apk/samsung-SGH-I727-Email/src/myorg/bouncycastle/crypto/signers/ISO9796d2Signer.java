package myorg.bouncycastle.crypto.signers;

import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.SignerWithRecovery;
import myorg.bouncycastle.crypto.digests.RIPEMD128Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class ISO9796d2Signer implements SignerWithRecovery {

   public static final int TRAILER_IMPLICIT = 188;
   public static final int TRAILER_RIPEMD128 = 13004;
   public static final int TRAILER_RIPEMD160 = 12748;
   public static final int TRAILER_SHA1 = 13260;
   private byte[] block;
   private AsymmetricBlockCipher cipher;
   private Digest digest;
   private boolean fullMessage;
   private int keyBits;
   private byte[] mBuf;
   private int messageLength;
   private byte[] recoveredMessage;
   private int trailer;


   public ISO9796d2Signer(AsymmetricBlockCipher var1, Digest var2) {
      this(var1, var2, (boolean)0);
   }

   public ISO9796d2Signer(AsymmetricBlockCipher var1, Digest var2, boolean var3) {
      this.cipher = var1;
      this.digest = var2;
      if(var3) {
         this.trailer = 188;
      } else if(var2 instanceof SHA1Digest) {
         this.trailer = 13260;
      } else if(var2 instanceof RIPEMD160Digest) {
         this.trailer = 12748;
      } else if(var2 instanceof RIPEMD128Digest) {
         this.trailer = 13004;
      } else {
         throw new IllegalArgumentException("no valid trailer for digest");
      }
   }

   private void clearBlock(byte[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return;
         }

         var1[var2] = 0;
         ++var2;
      }
   }

   private boolean isSameAs(byte[] var1, byte[] var2) {
      int var3 = this.messageLength;
      int var4 = this.mBuf.length;
      boolean var7;
      int var8;
      if(var3 > var4) {
         int var5 = this.mBuf.length;
         int var6 = var2.length;
         if(var5 > var6) {
            var7 = false;
            return var7;
         }

         var8 = 0;

         while(true) {
            int var9 = this.mBuf.length;
            if(var8 == var9) {
               break;
            }

            byte var10 = var1[var8];
            byte var11 = var2[var8];
            if(var10 != var11) {
               var7 = false;
               return var7;
            }

            ++var8;
         }
      } else {
         int var12 = this.messageLength;
         int var13 = var2.length;
         if(var12 != var13) {
            var7 = false;
            return var7;
         }

         var8 = 0;

         while(true) {
            int var14 = var2.length;
            if(var8 == var14) {
               break;
            }

            byte var15 = var1[var8];
            byte var16 = var2[var8];
            if(var15 != var16) {
               var7 = false;
               return var7;
            }

            ++var8;
         }
      }

      var7 = true;
      return var7;
   }

   public byte[] generateSignature() throws CryptoException {
      int var1 = this.digest.getDigestSize();
      byte var2;
      int var3;
      if(this.trailer == 188) {
         var2 = 8;
         var3 = this.block.length - var1 - 1;
         Digest var4 = this.digest;
         byte[] var5 = this.block;
         var4.doFinal(var5, var3);
         byte[] var7 = this.block;
         int var8 = this.block.length - 1;
         var7[var8] = (byte)'\uffbc';
      } else {
         var2 = 16;
         var3 = this.block.length - var1 - 2;
         Digest var20 = this.digest;
         byte[] var21 = this.block;
         var20.doFinal(var21, var3);
         byte[] var23 = this.block;
         int var24 = this.block.length - 2;
         byte var25 = (byte)(this.trailer >>> 8);
         var23[var24] = var25;
         byte[] var26 = this.block;
         int var27 = this.block.length - 1;
         byte var28 = (byte)this.trailer;
         var26[var27] = var28;
      }

      int var9 = (this.messageLength + var1) * 8 + var2 + 4;
      int var10 = this.keyBits;
      int var11 = var9 - var10;
      byte var15;
      int var16;
      if(var11 > 0) {
         int var12 = this.messageLength;
         int var13 = (var11 + 7) / 8;
         int var14 = var12 - var13;
         var15 = 96;
         var16 = var3 - var14;
         byte[] var17 = this.mBuf;
         byte[] var18 = this.block;
         System.arraycopy(var17, 0, var18, var16, var14);
      } else {
         var15 = 64;
         int var29 = this.messageLength;
         var16 = var3 - var29;
         byte[] var30 = this.mBuf;
         byte[] var31 = this.block;
         int var32 = this.messageLength;
         System.arraycopy(var30, 0, var31, var16, var32);
      }

      if(var16 - 1 > 0) {
         for(int var19 = var16 - 1; var19 != 0; var19 += -1) {
            this.block[var19] = (byte)'\uffbb';
         }

         byte[] var33 = this.block;
         int var34 = var16 - 1;
         byte var35 = (byte)(var33[var34] ^ 1);
         var33[var34] = var35;
         this.block[0] = 11;
         byte[] var36 = this.block;
         byte var37 = (byte)(var36[0] | var15);
         var36[0] = var37;
      } else {
         this.block[0] = 10;
         byte[] var44 = this.block;
         byte var45 = (byte)(var44[0] | var15);
         var44[0] = var45;
      }

      AsymmetricBlockCipher var38 = this.cipher;
      byte[] var39 = this.block;
      int var40 = this.block.length;
      byte[] var41 = var38.processBlock(var39, 0, var40);
      byte[] var42 = this.mBuf;
      this.clearBlock(var42);
      byte[] var43 = this.block;
      this.clearBlock(var43);
      return var41;
   }

   public byte[] getRecoveredMessage() {
      return this.recoveredMessage;
   }

   public boolean hasFullMessage() {
      return this.fullMessage;
   }

   public void init(boolean var1, CipherParameters var2) {
      RSAKeyParameters var3 = (RSAKeyParameters)var2;
      this.cipher.init(var1, var3);
      int var4 = var3.getModulus().bitLength();
      this.keyBits = var4;
      byte[] var5 = new byte[(this.keyBits + 7) / 8];
      this.block = var5;
      if(this.trailer == 188) {
         int var6 = this.block.length;
         int var7 = this.digest.getDigestSize();
         byte[] var8 = new byte[var6 - var7 - 2];
         this.mBuf = var8;
      } else {
         int var9 = this.block.length;
         int var10 = this.digest.getDigestSize();
         byte[] var11 = new byte[var9 - var10 - 3];
         this.mBuf = var11;
      }

      this.reset();
   }

   public void reset() {
      this.digest.reset();
      this.messageLength = 0;
      byte[] var1 = this.mBuf;
      this.clearBlock(var1);
      if(this.recoveredMessage != null) {
         byte[] var2 = this.recoveredMessage;
         this.clearBlock(var2);
      }

      this.recoveredMessage = null;
      this.fullMessage = (boolean)0;
   }

   public void update(byte var1) {
      this.digest.update(var1);
      int var2 = this.messageLength;
      int var3 = this.mBuf.length;
      if(var2 < var3) {
         byte[] var4 = this.mBuf;
         int var5 = this.messageLength;
         var4[var5] = var1;
      }

      int var6 = this.messageLength + 1;
      this.messageLength = var6;
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
      int var4 = this.messageLength;
      int var5 = this.mBuf.length;
      if(var4 < var5) {
         for(int var6 = 0; var6 < var3; ++var6) {
            int var7 = this.messageLength + var6;
            int var8 = this.mBuf.length;
            if(var7 >= var8) {
               break;
            }

            byte[] var9 = this.mBuf;
            int var10 = this.messageLength + var6;
            int var11 = var2 + var6;
            byte var12 = var1[var11];
            var9[var10] = var12;
         }
      }

      int var13 = this.messageLength + var3;
      this.messageLength = var13;
   }

   public boolean verifySignature(byte[] var1) {
      byte[] var4;
      boolean var7;
      try {
         AsymmetricBlockCipher var2 = this.cipher;
         int var3 = var1.length;
         var4 = var2.processBlock(var1, 0, var3);
      } catch (Exception var54) {
         var7 = false;
         return var7;
      }

      byte[] var5 = var4;
      if((var4[0] & 192 ^ 64) != 0) {
         byte[] var6 = this.mBuf;
         this.clearBlock(var6);
         this.clearBlock(var4);
         var7 = false;
      } else {
         int var9 = var4.length - 1;
         if((var4[var9] & 15 ^ 12) != 0) {
            byte[] var10 = this.mBuf;
            this.clearBlock(var10);
            this.clearBlock(var4);
            var7 = false;
         } else {
            int var11 = var4.length - 1;
            byte var12;
            if((var4[var11] & 255 ^ 188) == 0) {
               var12 = 1;
            } else {
               int var21 = var4.length - 2;
               int var22 = (var4[var21] & 255) << 8;
               int var23 = var4.length - 1;
               int var24 = var4[var23] & 255;
               switch(var22 | var24) {
               case 12748:
                  if(!(this.digest instanceof RIPEMD160Digest)) {
                     throw new IllegalStateException("signer should be initialised with RIPEMD160");
                  }
                  break;
               case 13004:
                  if(!(this.digest instanceof RIPEMD128Digest)) {
                     throw new IllegalStateException("signer should be initialised with RIPEMD128");
                  }
                  break;
               case 13260:
                  if(!(this.digest instanceof SHA1Digest)) {
                     throw new IllegalStateException("signer should be initialised with SHA1");
                  }
                  break;
               default:
                  throw new IllegalArgumentException("unrecognised hash in signature");
               }

               var12 = 2;
            }

            int var13 = 0;

            while(true) {
               int var14 = var5.length;
               if(var13 == var14 || (var5[var13] & 15 ^ 10) == 0) {
                  int var15 = var13 + 1;
                  byte[] var16 = new byte[this.digest.getDigestSize()];
                  int var17 = var5.length - var12;
                  int var18 = var16.length;
                  int var19 = var17 - var18;
                  if(var19 - var15 <= 0) {
                     byte[] var20 = this.mBuf;
                     this.clearBlock(var20);
                     this.clearBlock(var5);
                     var7 = false;
                  } else {
                     int var28;
                     if((var5[0] & 32) == 0) {
                        this.fullMessage = (boolean)1;
                        this.digest.reset();
                        Digest var25 = this.digest;
                        int var26 = var19 - var15;
                        var25.update(var5, var15, var26);
                        this.digest.doFinal(var16, 0);
                        var28 = 0;

                        while(true) {
                           int var29 = var16.length;
                           if(var28 == var29) {
                              byte[] var36 = new byte[var19 - var15];
                              this.recoveredMessage = var36;
                              byte[] var37 = this.recoveredMessage;
                              int var38 = this.recoveredMessage.length;
                              System.arraycopy(var5, var15, var37, 0, var38);
                              break;
                           }

                           int var30 = var19 + var28;
                           byte var31 = var5[var30];
                           byte var32 = var16[var28];
                           byte var33 = (byte)(var31 ^ var32);
                           var5[var30] = var33;
                           int var34 = var19 + var28;
                           if(var5[var34] != 0) {
                              byte[] var35 = this.mBuf;
                              this.clearBlock(var35);
                              this.clearBlock(var5);
                              var7 = false;
                              return var7;
                           }

                           ++var28;
                        }
                     } else {
                        this.fullMessage = (boolean)0;
                        this.digest.doFinal(var16, 0);
                        var28 = 0;

                        while(true) {
                           int var43 = var16.length;
                           if(var28 == var43) {
                              byte[] var50 = new byte[var19 - var15];
                              this.recoveredMessage = var50;
                              byte[] var51 = this.recoveredMessage;
                              int var52 = this.recoveredMessage.length;
                              System.arraycopy(var5, var15, var51, 0, var52);
                              break;
                           }

                           int var44 = var19 + var28;
                           byte var45 = var5[var44];
                           byte var46 = var16[var28];
                           byte var47 = (byte)(var45 ^ var46);
                           var5[var44] = var47;
                           int var48 = var19 + var28;
                           if(var5[var48] != 0) {
                              byte[] var49 = this.mBuf;
                              this.clearBlock(var49);
                              this.clearBlock(var5);
                              var7 = false;
                              return var7;
                           }

                           ++var28;
                        }
                     }

                     if(this.messageLength != 0) {
                        byte[] var39 = this.mBuf;
                        byte[] var40 = this.recoveredMessage;
                        if(!this.isSameAs(var39, var40)) {
                           byte[] var41 = this.mBuf;
                           this.clearBlock(var41);
                           this.clearBlock(var5);
                           var7 = false;
                           break;
                        }
                     }

                     byte[] var53 = this.mBuf;
                     this.clearBlock(var53);
                     this.clearBlock(var5);
                     var7 = true;
                  }
                  break;
               }

               ++var13;
            }
         }
      }

      return var7;
   }
}
