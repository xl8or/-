package myorg.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.Signer;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.crypto.params.RSABlindingParameters;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;

public class PSSSigner implements Signer {

   public static final byte TRAILER_IMPLICIT = -68;
   private byte[] block;
   private AsymmetricBlockCipher cipher;
   private Digest contentDigest;
   private int emBits;
   private int hLen;
   private byte[] mDash;
   private Digest mgfDigest;
   private SecureRandom random;
   private int sLen;
   private byte[] salt;
   private byte trailer;


   public PSSSigner(AsymmetricBlockCipher var1, Digest var2, int var3) {
      this(var1, var2, var3, (byte)-68);
   }

   public PSSSigner(AsymmetricBlockCipher var1, Digest var2, int var3, byte var4) {
      this(var1, var2, var2, var3, var4);
   }

   public PSSSigner(AsymmetricBlockCipher var1, Digest var2, Digest var3, int var4, byte var5) {
      this.cipher = var1;
      this.contentDigest = var2;
      this.mgfDigest = var3;
      int var6 = var3.getDigestSize();
      this.hLen = var6;
      this.sLen = var4;
      byte[] var7 = new byte[var4];
      this.salt = var7;
      int var8 = var4 + 8;
      int var9 = this.hLen;
      byte[] var10 = new byte[var8 + var9];
      this.mDash = var10;
      this.trailer = var5;
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

   private byte[] maskGeneratorFunction1(byte[] var1, int var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      byte[] var6 = new byte[this.hLen];
      byte[] var7 = new byte[4];
      int var8 = 0;
      this.mgfDigest.reset();

      while(true) {
         int var9 = this.hLen;
         int var10 = var4 / var9;
         if(var8 >= var10) {
            if(this.hLen * var8 < var4) {
               this.ItoOSP(var8, var7);
               this.mgfDigest.update(var1, var2, var3);
               Digest var16 = this.mgfDigest;
               int var17 = var7.length;
               var16.update(var7, 0, var17);
               this.mgfDigest.doFinal(var6, 0);
               int var19 = this.hLen * var8;
               int var20 = var5.length;
               int var21 = this.hLen * var8;
               int var22 = var20 - var21;
               System.arraycopy(var6, 0, var5, var19, var22);
            }

            return var5;
         }

         this.ItoOSP(var8, var7);
         this.mgfDigest.update(var1, var2, var3);
         Digest var11 = this.mgfDigest;
         int var12 = var7.length;
         var11.update(var7, 0, var12);
         this.mgfDigest.doFinal(var6, 0);
         int var14 = this.hLen * var8;
         int var15 = this.hLen;
         System.arraycopy(var6, 0, var5, var14, var15);
         ++var8;
      }
   }

   public byte[] generateSignature() throws CryptoException, DataLengthException {
      Digest var1 = this.contentDigest;
      byte[] var2 = this.mDash;
      int var3 = this.mDash.length;
      int var4 = this.hLen;
      int var5 = var3 - var4;
      int var6 = this.sLen;
      int var7 = var5 - var6;
      var1.doFinal(var2, var7);
      if(this.sLen != 0) {
         SecureRandom var9 = this.random;
         byte[] var10 = this.salt;
         var9.nextBytes(var10);
         byte[] var11 = this.salt;
         byte[] var12 = this.mDash;
         int var13 = this.mDash.length;
         int var14 = this.sLen;
         int var15 = var13 - var14;
         int var16 = this.sLen;
         System.arraycopy(var11, 0, var12, var15, var16);
      }

      byte[] var17 = new byte[this.hLen];
      Digest var18 = this.mgfDigest;
      byte[] var19 = this.mDash;
      int var20 = this.mDash.length;
      var18.update(var19, 0, var20);
      this.mgfDigest.doFinal(var17, 0);
      byte[] var22 = this.block;
      int var23 = this.block.length;
      int var24 = this.sLen;
      int var25 = var23 - var24 - 1;
      int var26 = this.hLen;
      int var27 = var25 - var26 - 1;
      var22[var27] = 1;
      byte[] var28 = this.salt;
      byte[] var29 = this.block;
      int var30 = this.block.length;
      int var31 = this.sLen;
      int var32 = var30 - var31;
      int var33 = this.hLen;
      int var34 = var32 - var33 - 1;
      int var35 = this.sLen;
      System.arraycopy(var28, 0, var29, var34, var35);
      int var36 = var17.length;
      int var37 = this.block.length;
      int var38 = this.hLen;
      int var39 = var37 - var38 - 1;
      byte[] var40 = this.maskGeneratorFunction1(var17, 0, var36, var39);
      int var41 = 0;

      while(true) {
         int var42 = var40.length;
         if(var41 == var42) {
            byte[] var47 = this.block;
            byte var48 = var47[0];
            int var49 = this.block.length * 8;
            int var50 = this.emBits;
            int var51 = var49 - var50;
            int var52 = 255 >> var51;
            byte var53 = (byte)(var48 & var52);
            var47[0] = var53;
            byte[] var54 = this.block;
            int var55 = this.block.length;
            int var56 = this.hLen;
            int var57 = var55 - var56 - 1;
            int var58 = this.hLen;
            System.arraycopy(var17, 0, var54, var57, var58);
            byte[] var59 = this.block;
            int var60 = this.block.length - 1;
            byte var61 = this.trailer;
            var59[var60] = var61;
            AsymmetricBlockCipher var62 = this.cipher;
            byte[] var63 = this.block;
            int var64 = this.block.length;
            byte[] var65 = var62.processBlock(var63, 0, var64);
            byte[] var66 = this.block;
            this.clearBlock(var66);
            return var65;
         }

         byte[] var43 = this.block;
         byte var44 = var43[var41];
         byte var45 = var40[var41];
         byte var46 = (byte)(var44 ^ var45);
         var43[var41] = var46;
         ++var41;
      }
   }

   public void init(boolean var1, CipherParameters var2) {
      CipherParameters var4;
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         var4 = var3.getParameters();
         SecureRandom var5 = var3.getRandom();
         this.random = var5;
      } else if(var1) {
         SecureRandom var13 = new SecureRandom();
         this.random = var13;
      }

      this.cipher.init(var1, var4);
      RSAKeyParameters var6;
      if(var4 instanceof RSABlindingParameters) {
         var6 = ((RSABlindingParameters)var4).getPublicKey();
      } else {
         var6 = (RSAKeyParameters)var4;
      }

      int var7 = var6.getModulus().bitLength() - 1;
      this.emBits = var7;
      int var8 = this.emBits;
      int var9 = this.hLen * 8;
      int var10 = this.sLen * 8;
      int var11 = var9 + var10 + 9;
      if(var8 < var11) {
         throw new IllegalArgumentException("key too small for specified hash and salt lengths");
      } else {
         byte[] var14 = new byte[(this.emBits + 7) / 8];
         this.block = var14;
         this.reset();
      }
   }

   public void reset() {
      this.contentDigest.reset();
   }

   public void update(byte var1) {
      this.contentDigest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.contentDigest.update(var1, var2, var3);
   }

   public boolean verifySignature(byte[] var1) {
      Digest var2 = this.contentDigest;
      byte[] var3 = this.mDash;
      int var4 = this.mDash.length;
      int var5 = this.hLen;
      int var6 = var4 - var5;
      int var7 = this.sLen;
      int var8 = var6 - var7;
      var2.doFinal(var3, var8);

      boolean var23;
      try {
         AsymmetricBlockCipher var10 = this.cipher;
         int var11 = var1.length;
         byte[] var12 = var10.processBlock(var1, 0, var11);
         byte[] var13 = this.block;
         int var14 = this.block.length;
         int var15 = var12.length;
         int var16 = var14 - var15;
         int var17 = var12.length;
         System.arraycopy(var12, 0, var13, var16, var17);
      } catch (Exception var93) {
         var23 = false;
         return var23;
      }

      byte[] var18 = this.block;
      int var19 = this.block.length - 1;
      byte var20 = var18[var19];
      byte var21 = this.trailer;
      if(var20 != var21) {
         byte[] var22 = this.block;
         this.clearBlock(var22);
         var23 = false;
         return var23;
      } else {
         byte[] var25 = this.block;
         int var26 = this.block.length;
         int var27 = this.hLen;
         int var28 = var26 - var27 - 1;
         int var29 = this.hLen;
         int var30 = this.block.length;
         int var31 = this.hLen;
         int var32 = var30 - var31 - 1;
         byte[] var33 = this.maskGeneratorFunction1(var25, var28, var29, var32);
         int var34 = 0;

         while(true) {
            int var35 = var33.length;
            if(var34 == var35) {
               byte[] var40 = this.block;
               byte var41 = var40[0];
               int var42 = this.block.length * 8;
               int var43 = this.emBits;
               int var44 = var42 - var43;
               int var45 = 255 >> var44;
               byte var46 = (byte)(var41 & var45);
               var40[0] = var46;
               int var47 = 0;

               while(true) {
                  int var48 = this.block.length;
                  int var49 = this.hLen;
                  int var50 = var48 - var49;
                  int var51 = this.sLen;
                  int var52 = var50 - var51 - 2;
                  if(var47 == var52) {
                     byte[] var54 = this.block;
                     int var55 = this.block.length;
                     int var56 = this.hLen;
                     int var57 = var55 - var56;
                     int var58 = this.sLen;
                     int var59 = var57 - var58 - 2;
                     if(var54[var59] != 1) {
                        byte[] var60 = this.block;
                        this.clearBlock(var60);
                        var23 = false;
                        return var23;
                     } else {
                        byte[] var61 = this.block;
                        int var62 = this.block.length;
                        int var63 = this.sLen;
                        int var64 = var62 - var63;
                        int var65 = this.hLen;
                        int var66 = var64 - var65 - 1;
                        byte[] var67 = this.mDash;
                        int var68 = this.mDash.length;
                        int var69 = this.sLen;
                        int var70 = var68 - var69;
                        int var71 = this.sLen;
                        System.arraycopy(var61, var66, var67, var70, var71);
                        Digest var72 = this.mgfDigest;
                        byte[] var73 = this.mDash;
                        int var74 = this.mDash.length;
                        var72.update(var73, 0, var74);
                        Digest var75 = this.mgfDigest;
                        byte[] var76 = this.mDash;
                        int var77 = this.mDash.length;
                        int var78 = this.hLen;
                        int var79 = var77 - var78;
                        var75.doFinal(var76, var79);
                        int var81 = this.block.length;
                        int var82 = this.hLen;
                        var47 = var81 - var82 - 1;
                        int var83 = this.mDash.length;
                        int var84 = this.hLen;
                        int var85 = var83 - var84;

                        while(true) {
                           int var86 = this.mDash.length;
                           if(var85 == var86) {
                              byte[] var91 = this.mDash;
                              this.clearBlock(var91);
                              byte[] var92 = this.block;
                              this.clearBlock(var92);
                              var23 = true;
                              return var23;
                           }

                           byte var87 = this.block[var47];
                           byte var88 = this.mDash[var85];
                           if((var87 ^ var88) != 0) {
                              byte[] var89 = this.mDash;
                              this.clearBlock(var89);
                              byte[] var90 = this.block;
                              this.clearBlock(var90);
                              var23 = false;
                              return var23;
                           }

                           ++var47;
                           ++var85;
                        }
                     }
                  }

                  if(this.block[var47] != 0) {
                     byte[] var53 = this.block;
                     this.clearBlock(var53);
                     var23 = false;
                     return var23;
                  }

                  ++var47;
               }
            }

            byte[] var36 = this.block;
            byte var37 = var36[var34];
            byte var38 = var33[var34];
            byte var39 = (byte)(var37 ^ var38);
            var36[var34] = var39;
            ++var34;
         }
      }
   }
}
