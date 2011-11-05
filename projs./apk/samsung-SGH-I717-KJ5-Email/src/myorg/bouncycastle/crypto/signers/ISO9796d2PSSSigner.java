package myorg.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.SignerWithRecovery;
import myorg.bouncycastle.crypto.digests.RIPEMD128Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.ParametersWithSalt;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class ISO9796d2PSSSigner implements SignerWithRecovery {

   public static final int TRAILER_IMPLICIT = 188;
   public static final int TRAILER_RIPEMD128 = 13004;
   public static final int TRAILER_RIPEMD160 = 12748;
   public static final int TRAILER_SHA1 = 13260;
   private byte[] block;
   private AsymmetricBlockCipher cipher;
   private Digest digest;
   private boolean fullMessage;
   private int hLen;
   private int keyBits;
   private byte[] mBuf;
   private int messageLength;
   private SecureRandom random;
   private byte[] recoveredMessage;
   private int saltLength;
   private byte[] standardSalt;
   private int trailer;


   public ISO9796d2PSSSigner(AsymmetricBlockCipher var1, Digest var2, int var3) {
      this(var1, var2, var3, (boolean)0);
   }

   public ISO9796d2PSSSigner(AsymmetricBlockCipher var1, Digest var2, int var3, boolean var4) {
      this.cipher = var1;
      this.digest = var2;
      int var5 = var2.getDigestSize();
      this.hLen = var5;
      this.saltLength = var3;
      if(var4) {
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

   private void LtoOSP(long var1, byte[] var3) {
      byte var4 = (byte)((int)(var1 >>> 56));
      var3[0] = var4;
      byte var5 = (byte)((int)(var1 >>> 48));
      var3[1] = var5;
      byte var6 = (byte)((int)(var1 >>> 40));
      var3[2] = var6;
      byte var7 = (byte)((int)(var1 >>> 32));
      var3[3] = var7;
      byte var8 = (byte)((int)(var1 >>> 24));
      var3[4] = var8;
      byte var9 = (byte)((int)(var1 >>> 16));
      var3[5] = var9;
      byte var10 = (byte)((int)(var1 >>> 8));
      var3[6] = var10;
      byte var11 = (byte)((int)(var1 >>> 0));
      var3[7] = var11;
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
      int var4 = var2.length;
      boolean var5;
      if(var3 != var4) {
         var5 = false;
      } else {
         int var6 = 0;

         while(true) {
            int var7 = var2.length;
            if(var6 == var7) {
               var5 = true;
               break;
            }

            byte var8 = var1[var6];
            byte var9 = var2[var6];
            if(var8 != var9) {
               var5 = false;
               break;
            }

            ++var6;
         }
      }

      return var5;
   }

   private byte[] maskGeneratorFunction1(byte[] var1, int var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      byte[] var6 = new byte[this.hLen];
      byte[] var7 = new byte[4];
      int var8 = 0;
      this.digest.reset();

      while(true) {
         int var9 = this.hLen;
         int var10 = var4 / var9;
         if(var8 >= var10) {
            if(this.hLen * var8 < var4) {
               this.ItoOSP(var8, var7);
               this.digest.update(var1, var2, var3);
               Digest var16 = this.digest;
               int var17 = var7.length;
               var16.update(var7, 0, var17);
               this.digest.doFinal(var6, 0);
               int var19 = this.hLen * var8;
               int var20 = var5.length;
               int var21 = this.hLen * var8;
               int var22 = var20 - var21;
               System.arraycopy(var6, 0, var5, var19, var22);
            }

            return var5;
         }

         this.ItoOSP(var8, var7);
         this.digest.update(var1, var2, var3);
         Digest var11 = this.digest;
         int var12 = var7.length;
         var11.update(var7, 0, var12);
         this.digest.doFinal(var6, 0);
         int var14 = this.hLen * var8;
         int var15 = this.hLen;
         System.arraycopy(var6, 0, var5, var14, var15);
         ++var8;
      }
   }

   public byte[] generateSignature() throws CryptoException {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var1, 0);
      byte[] var3 = new byte[8];
      long var4 = (long)(this.messageLength * 8);
      this.LtoOSP(var4, var3);
      Digest var6 = this.digest;
      int var7 = var3.length;
      var6.update(var3, 0, var7);
      Digest var8 = this.digest;
      byte[] var9 = this.mBuf;
      int var10 = this.messageLength;
      var8.update(var9, 0, var10);
      Digest var11 = this.digest;
      int var12 = var1.length;
      var11.update(var1, 0, var12);
      byte[] var13;
      if(this.standardSalt != null) {
         var13 = this.standardSalt;
      } else {
         var13 = new byte[this.saltLength];
         this.random.nextBytes(var13);
      }

      Digest var14 = this.digest;
      int var15 = var13.length;
      var14.update(var13, 0, var15);
      byte[] var16 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var16, 0);
      byte var18 = 2;
      if(this.trailer == 188) {
         var18 = 1;
      }

      int var19 = this.block.length;
      int var20 = this.messageLength;
      int var21 = var19 - var20;
      int var22 = var13.length;
      int var23 = var21 - var22;
      int var24 = this.hLen;
      int var25 = var23 - var24 - var18 - 1;
      this.block[var25] = 1;
      byte[] var26 = this.mBuf;
      byte[] var27 = this.block;
      int var28 = var25 + 1;
      int var29 = this.messageLength;
      System.arraycopy(var26, 0, var27, var28, var29);
      byte[] var30 = this.block;
      int var31 = var25 + 1;
      int var32 = this.messageLength;
      int var33 = var31 + var32;
      int var34 = var13.length;
      System.arraycopy(var13, 0, var30, var33, var34);
      int var35 = var16.length;
      int var36 = this.block.length;
      int var37 = this.hLen;
      int var38 = var36 - var37 - var18;
      byte[] var39 = this.maskGeneratorFunction1(var16, 0, var35, var38);
      int var40 = 0;

      while(true) {
         int var41 = var39.length;
         if(var40 == var41) {
            byte[] var46 = this.block;
            int var47 = this.block.length;
            int var48 = this.hLen;
            int var49 = var47 - var48 - var18;
            int var50 = this.hLen;
            System.arraycopy(var16, 0, var46, var49, var50);
            if(this.trailer == 188) {
               byte[] var51 = this.block;
               int var52 = this.block.length - 1;
               var51[var52] = (byte)'\uffbc';
            } else {
               byte[] var61 = this.block;
               int var62 = this.block.length - 2;
               byte var63 = (byte)(this.trailer >>> 8);
               var61[var62] = var63;
               byte[] var64 = this.block;
               int var65 = this.block.length - 1;
               byte var66 = (byte)this.trailer;
               var64[var65] = var66;
            }

            byte[] var53 = this.block;
            byte var54 = (byte)(var53[0] & 127);
            var53[0] = var54;
            AsymmetricBlockCipher var55 = this.cipher;
            byte[] var56 = this.block;
            int var57 = this.block.length;
            byte[] var58 = var55.processBlock(var56, 0, var57);
            byte[] var59 = this.mBuf;
            this.clearBlock(var59);
            byte[] var60 = this.block;
            this.clearBlock(var60);
            this.messageLength = 0;
            return var58;
         }

         byte[] var42 = this.block;
         byte var43 = var42[var40];
         byte var44 = var39[var40];
         byte var45 = (byte)(var43 ^ var44);
         var42[var40] = var45;
         ++var40;
      }
   }

   public byte[] getRecoveredMessage() {
      return this.recoveredMessage;
   }

   public boolean hasFullMessage() {
      return this.fullMessage;
   }

   public void init(boolean var1, CipherParameters var2) {
      int var3 = this.saltLength;
      RSAKeyParameters var5;
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var4 = (ParametersWithRandom)var2;
         var5 = (RSAKeyParameters)var4.getParameters();
         if(var1) {
            SecureRandom var6 = var4.getRandom();
            this.random = var6;
         }
      } else if(var2 instanceof ParametersWithSalt) {
         ParametersWithSalt var12 = (ParametersWithSalt)var2;
         var5 = (RSAKeyParameters)var12.getParameters();
         byte[] var13 = var12.getSalt();
         this.standardSalt = var13;
         var3 = this.standardSalt.length;
         int var14 = this.standardSalt.length;
         int var15 = this.saltLength;
         if(var14 != var15) {
            throw new IllegalArgumentException("Fixed salt is of wrong length");
         }
      } else {
         var5 = (RSAKeyParameters)var2;
         if(var1) {
            SecureRandom var16 = new SecureRandom();
            this.random = var16;
         }
      }

      this.cipher.init(var1, var5);
      int var7 = var5.getModulus().bitLength();
      this.keyBits = var7;
      byte[] var8 = new byte[(this.keyBits + 7) / 8];
      this.block = var8;
      if(this.trailer == 188) {
         int var9 = this.block.length;
         int var10 = this.digest.getDigestSize();
         byte[] var11 = new byte[var9 - var10 - var3 - 1 - 1];
         this.mBuf = var11;
      } else {
         int var17 = this.block.length;
         int var18 = this.digest.getDigestSize();
         byte[] var19 = new byte[var17 - var18 - var3 - 1 - 2];
         this.mBuf = var19;
      }

      this.reset();
   }

   public void reset() {
      this.digest.reset();
      this.messageLength = 0;
      if(this.mBuf != null) {
         byte[] var1 = this.mBuf;
         this.clearBlock(var1);
      }

      if(this.recoveredMessage != null) {
         byte[] var2 = this.recoveredMessage;
         this.clearBlock(var2);
         this.recoveredMessage = null;
      }

      this.fullMessage = (boolean)0;
   }

   public void update(byte var1) {
      int var2 = this.messageLength;
      int var3 = this.mBuf.length;
      if(var2 < var3) {
         byte[] var4 = this.mBuf;
         int var5 = this.messageLength;
         int var6 = var5 + 1;
         this.messageLength = var6;
         var4[var5] = var1;
      } else {
         this.digest.update(var1);
      }
   }

   public void update(byte[] var1, int var2, int var3) {
      while(true) {
         if(var3 > 0) {
            int var4 = this.messageLength;
            int var5 = this.mBuf.length;
            if(var4 < var5) {
               byte var6 = var1[var2];
               this.update(var6);
               ++var2;
               var3 += -1;
               continue;
            }
         }

         if(var3 <= 0) {
            return;
         }

         this.digest.update(var1, var2, var3);
         return;
      }
   }

   public boolean verifySignature(byte[] var1) {
      byte[] var8;
      boolean var54;
      try {
         AsymmetricBlockCipher var2 = this.cipher;
         int var3 = var1.length;
         byte var6 = 0;
         var8 = var2.processBlock(var1, var6, var3);
      } catch (Exception var155) {
         var54 = false;
         return var54;
      }

      byte[] var9 = var8;
      int var10 = var8.length;
      int var11 = (this.keyBits + 7) / 8;
      if(var10 < var11) {
         byte[] var14 = new byte[(this.keyBits + 7) / 8];
         int var15 = var14.length;
         int var16 = var8.length;
         int var17 = var15 - var16;
         int var18 = var8.length;
         byte var20 = 0;
         System.arraycopy(var8, var20, var14, var17, var18);
         this.clearBlock(var8);
         var9 = var14;
      }

      int var26 = var9.length - 1;
      byte var27;
      if((var9[var26] & 255 ^ 188) == 0) {
         var27 = 1;
      } else {
         int var55 = var9.length - 2;
         int var56 = (var9[var55] & 255) << 8;
         int var57 = var9.length - 1;
         int var58 = var9[var57] & 255;
         switch(var56 | var58) {
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

         var27 = 2;
      }

      byte[] var28 = new byte[this.hLen];
      Digest var29 = this.digest;
      byte var31 = 0;
      var29.doFinal(var28, var31);
      int var33 = var9.length;
      int var34 = this.hLen;
      int var35 = var33 - var34 - var27;
      int var36 = this.hLen;
      int var37 = var9.length;
      int var38 = this.hLen;
      int var39 = var37 - var38 - var27;
      byte[] var45 = this.maskGeneratorFunction1(var9, var35, var36, var39);
      int var46 = 0;

      while(true) {
         int var47 = var45.length;
         if(var46 == var47) {
            byte var59 = (byte)(var9[0] & 127);
            var9[0] = var59;
            int var60 = 0;

            while(true) {
               int var61 = var9.length;
               if(var60 == var61) {
                  break;
               }

               byte var64 = var9[var60];
               byte var65 = 1;
               if(var64 == var65) {
                  break;
               }

               ++var60;
            }

            int var66 = var60 + 1;
            int var67 = var9.length;
            if(var66 >= var67) {
               this.clearBlock(var9);
               var54 = false;
               return var54;
            } else {
               byte var73 = 1;
               byte var74;
               if(var66 > var73) {
                  var74 = 1;
               } else {
                  var74 = 0;
               }

               this.fullMessage = (boolean)var74;
               int var76 = var45.length - var66;
               int var77 = this.saltLength;
               byte[] var78 = new byte[var76 - var77];
               this.recoveredMessage = var78;
               byte[] var79 = this.recoveredMessage;
               int var80 = this.recoveredMessage.length;
               byte var84 = 0;
               System.arraycopy(var9, var66, var79, var84, var80);
               byte[] var86 = new byte[8];
               long var87 = (long)(this.recoveredMessage.length * 8);
               this.LtoOSP(var87, var86);
               Digest var93 = this.digest;
               int var94 = var86.length;
               byte var97 = 0;
               var93.update(var86, var97, var94);
               if(this.recoveredMessage.length != 0) {
                  Digest var99 = this.digest;
                  byte[] var100 = this.recoveredMessage;
                  int var101 = this.recoveredMessage.length;
                  var99.update(var100, 0, var101);
               }

               Digest var102 = this.digest;
               int var103 = var28.length;
               byte var106 = 0;
               var102.update(var28, var106, var103);
               Digest var108 = this.digest;
               int var109 = this.recoveredMessage.length + var66;
               int var110 = this.saltLength;
               var108.update(var9, var109, var110);
               byte[] var115 = new byte[this.digest.getDigestSize()];
               Digest var116 = this.digest;
               byte var118 = 0;
               var116.doFinal(var115, var118);
               int var120 = var9.length - var27;
               int var121 = var115.length;
               int var122 = var120 - var121;
               var46 = 0;

               while(true) {
                  int var123 = var115.length;
                  if(var46 == var123) {
                     this.clearBlock(var9);
                     this.clearBlock(var115);
                     if(this.messageLength != 0) {
                        byte[] var143 = this.mBuf;
                        byte[] var144 = this.recoveredMessage;
                        if(!this.isSameAs(var143, var144)) {
                           byte[] var148 = this.mBuf;
                           this.clearBlock(var148);
                           var54 = false;
                           return var54;
                        }

                        byte var151 = 0;
                        this.messageLength = var151;
                     }

                     byte[] var152 = this.mBuf;
                     this.clearBlock(var152);
                     var54 = true;
                     return var54;
                  }

                  byte var126 = var115[var46];
                  int var127 = var122 + var46;
                  byte var128 = var9[var127];
                  if(var126 != var128) {
                     this.clearBlock(var9);
                     this.clearBlock(var115);
                     byte[] var135 = this.recoveredMessage;
                     this.clearBlock(var135);
                     byte var138 = 0;
                     this.fullMessage = (boolean)var138;
                     var54 = false;
                     return var54;
                  }

                  ++var46;
               }
            }
         }

         byte var50 = var9[var46];
         byte var51 = var45[var46];
         byte var52 = (byte)(var50 ^ var51);
         var9[var46] = var52;
         ++var46;
      }
   }
}
