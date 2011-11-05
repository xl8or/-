package myorg.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Wrapper;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.engines.RC2Engine;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;
import myorg.bouncycastle.util.Arrays;

public class RC2WrapEngine implements Wrapper {

   private static final byte[] IV2 = new byte[]{(byte)74, (byte)221, (byte)162, (byte)44, (byte)121, (byte)232, (byte)33, (byte)5};
   byte[] digest;
   private CBCBlockCipher engine;
   private boolean forWrapping;
   private byte[] iv;
   private CipherParameters param;
   private ParametersWithIV paramPlusIV;
   Digest sha1;
   private SecureRandom sr;


   public RC2WrapEngine() {
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

   public String getAlgorithmName() {
      return "RC2";
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      RC2Engine var3 = new RC2Engine();
      CBCBlockCipher var4 = new CBCBlockCipher(var3);
      this.engine = var4;
      if(var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var5 = (ParametersWithRandom)var2;
         SecureRandom var6 = var5.getRandom();
         this.sr = var6;
         var2 = var5.getParameters();
      } else {
         SecureRandom var10 = new SecureRandom();
         this.sr = var10;
      }

      if(var2 instanceof ParametersWithIV) {
         ParametersWithIV var7 = (ParametersWithIV)var2;
         this.paramPlusIV = var7;
         byte[] var8 = this.paramPlusIV.getIV();
         this.iv = var8;
         CipherParameters var9 = this.paramPlusIV.getParameters();
         this.param = var9;
         if(this.forWrapping) {
            if(this.iv == null || this.iv.length != 8) {
               throw new IllegalArgumentException("IV is not 8 octets");
            }
         } else {
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
         }
      } else {
         this.param = var2;
         if(this.forWrapping) {
            byte[] var11 = new byte[8];
            this.iv = var11;
            SecureRandom var12 = this.sr;
            byte[] var13 = this.iv;
            var12.nextBytes(var13);
            CipherParameters var14 = this.param;
            byte[] var15 = this.iv;
            ParametersWithIV var16 = new ParametersWithIV(var14, var15);
            this.paramPlusIV = var16;
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
            StringBuilder var5 = (new StringBuilder()).append("Ciphertext not multiple of ");
            int var6 = this.engine.getBlockSize();
            String var7 = var5.append(var6).toString();
            throw new InvalidCipherTextException(var7);
         } else {
            ParametersWithIV var8 = new ParametersWithIV;
            CipherParameters var9 = this.param;
            byte[] var10 = IV2;
            var8.<init>(var9, var10);
            CBCBlockCipher var14 = this.engine;
            byte var15 = 0;
            var14.init((boolean)var15, var8);
            byte[] var17 = new byte[var3];
            byte var21 = 0;
            System.arraycopy(var1, var2, var17, var21, var3);
            int var23 = 0;

            while(true) {
               int var24 = var17.length;
               int var25 = this.engine.getBlockSize();
               int var26 = var24 / var25;
               if(var23 >= var26) {
                  byte[] var30 = new byte[var17.length];
                  int var31 = 0;

                  while(true) {
                     int var32 = var17.length;
                     if(var31 >= var32) {
                        byte[] var37 = new byte[8];
                        this.iv = var37;
                        byte[] var38 = new byte[var30.length - 8];
                        byte[] var39 = this.iv;
                        byte var41 = 0;
                        byte var43 = 0;
                        byte var44 = 8;
                        System.arraycopy(var30, var41, var39, var43, var44);
                        int var45 = var30.length - 8;
                        byte var47 = 8;
                        byte var49 = 0;
                        System.arraycopy(var30, var47, var38, var49, var45);
                        CipherParameters var51 = this.param;
                        byte[] var52 = this.iv;
                        ParametersWithIV var53 = new ParametersWithIV(var51, var52);
                        this.paramPlusIV = var53;
                        CBCBlockCipher var54 = this.engine;
                        ParametersWithIV var55 = this.paramPlusIV;
                        var54.init((boolean)0, var55);
                        byte[] var56 = new byte[var38.length];
                        int var57 = var38.length;
                        byte var59 = 0;
                        byte var61 = 0;
                        System.arraycopy(var38, var59, var56, var61, var57);
                        int var63 = 0;

                        while(true) {
                           int var64 = var56.length;
                           int var65 = this.engine.getBlockSize();
                           int var66 = var64 / var65;
                           if(var63 >= var66) {
                              byte[] var70 = new byte[var56.length - 8];
                              byte[] var71 = new byte[8];
                              int var72 = var56.length - 8;
                              byte var74 = 0;
                              byte var76 = 0;
                              System.arraycopy(var56, var74, var70, var76, var72);
                              int var78 = var56.length - 8;
                              byte var82 = 0;
                              byte var83 = 8;
                              System.arraycopy(var56, var78, var71, var82, var83);
                              if(!this.checkCMSKeyChecksum(var70, var71)) {
                                 throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
                              } else {
                                 int var87 = var70.length;
                                 int var88 = (var70[0] & 255) + 1;
                                 int var89 = var87 - var88;
                                 byte var90 = 7;
                                 if(var89 > var90) {
                                    StringBuilder var91 = (new StringBuilder()).append("too many pad bytes (");
                                    int var92 = var70.length;
                                    int var93 = (var70[0] & 255) + 1;
                                    int var94 = var92 - var93;
                                    String var95 = var91.append(var94).append(")").toString();
                                    throw new InvalidCipherTextException(var95);
                                 } else {
                                    byte[] var96 = new byte[var70[0]];
                                    int var97 = var96.length;
                                    byte var99 = 1;
                                    byte var101 = 0;
                                    System.arraycopy(var70, var99, var96, var101, var97);
                                    return var96;
                                 }
                              }
                           }

                           int var67 = this.engine.getBlockSize();
                           int var68 = var63 * var67;
                           this.engine.processBlock(var56, var68, var56, var68);
                           ++var63;
                        }
                     }

                     int var33 = var17.length;
                     int var34 = var31 + 1;
                     int var35 = var33 - var34;
                     byte var36 = var17[var35];
                     var30[var31] = var36;
                     ++var31;
                  }
               }

               int var27 = this.engine.getBlockSize();
               int var28 = var23 * var27;
               this.engine.processBlock(var17, var28, var17, var28);
               ++var23;
            }
         }
      }
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if(!this.forWrapping) {
         throw new IllegalStateException("Not initialized for wrapping");
      } else {
         int var4 = var3 + 1;
         if(var4 % 8 != 0) {
            int var5 = var4 % 8;
            int var6 = 8 - var5;
            var4 += var6;
         }

         byte[] var7 = new byte[var4];
         byte var8 = (byte)var3;
         var7[0] = var8;
         byte var12 = 1;
         System.arraycopy(var1, var2, var7, var12, var3);
         byte[] var14 = new byte[var7.length - var3 - 1];
         if(var14.length > 0) {
            SecureRandom var15 = this.sr;
            var15.nextBytes(var14);
            int var17 = var3 + 1;
            int var18 = var14.length;
            byte var20 = 0;
            System.arraycopy(var14, var20, var7, var17, var18);
         }

         byte[] var26 = this.calculateCMSKeyChecksum(var7);
         int var27 = var7.length;
         int var28 = var26.length;
         byte[] var29 = new byte[var27 + var28];
         int var30 = var7.length;
         byte var32 = 0;
         byte var34 = 0;
         System.arraycopy(var7, var32, var29, var34, var30);
         int var36 = var7.length;
         int var37 = var26.length;
         byte var39 = 0;
         System.arraycopy(var26, var39, var29, var36, var37);
         byte[] var43 = new byte[var29.length];
         int var44 = var29.length;
         byte var46 = 0;
         byte var48 = 0;
         System.arraycopy(var29, var46, var43, var48, var44);
         int var50 = var29.length;
         int var51 = this.engine.getBlockSize();
         int var52 = var50 / var51;
         int var53 = var29.length;
         int var54 = this.engine.getBlockSize();
         if(var53 % var54 != 0) {
            throw new IllegalStateException("Not multiple of block length");
         } else {
            CBCBlockCipher var55 = this.engine;
            ParametersWithIV var56 = this.paramPlusIV;
            var55.init((boolean)1, var56);

            for(int var57 = 0; var57 < var52; ++var57) {
               int var58 = this.engine.getBlockSize();
               int var59 = var57 * var58;
               CBCBlockCipher var60 = this.engine;
               var60.processBlock(var43, var59, var43, var59);
            }

            int var66 = this.iv.length;
            int var67 = var43.length;
            byte[] var68 = new byte[var66 + var67];
            byte[] var69 = this.iv;
            int var70 = this.iv.length;
            byte var72 = 0;
            byte var74 = 0;
            System.arraycopy(var69, var72, var68, var74, var70);
            int var76 = this.iv.length;
            int var77 = var43.length;
            byte var79 = 0;
            System.arraycopy(var43, var79, var68, var76, var77);
            byte[] var83 = new byte[var68.length];
            int var84 = 0;

            while(true) {
               int var85 = var68.length;
               if(var84 >= var85) {
                  CipherParameters var92 = this.param;
                  byte[] var93 = IV2;
                  ParametersWithIV var94 = new ParametersWithIV(var92, var93);
                  CBCBlockCipher var95 = this.engine;
                  byte var96 = 1;
                  var95.init((boolean)var96, var94);
                  int var98 = 0;

                  while(true) {
                     int var99 = var52 + 1;
                     if(var98 >= var99) {
                        return var83;
                     }

                     int var102 = this.engine.getBlockSize();
                     int var103 = var98 * var102;
                     CBCBlockCipher var104 = this.engine;
                     var104.processBlock(var83, var103, var83, var103);
                     ++var98;
                  }
               }

               int var88 = var68.length;
               int var89 = var84 + 1;
               int var90 = var88 - var89;
               byte var91 = var68[var90];
               var83[var84] = var91;
               ++var84;
            }
         }
      }
   }
}
