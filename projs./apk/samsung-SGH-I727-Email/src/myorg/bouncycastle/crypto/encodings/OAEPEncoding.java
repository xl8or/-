package myorg.bouncycastle.crypto.encodings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class OAEPEncoding implements AsymmetricBlockCipher {

   private byte[] defHash;
   private AsymmetricBlockCipher engine;
   private boolean forEncryption;
   private Digest hash;
   private Digest mgf1Hash;
   private SecureRandom random;


   public OAEPEncoding(AsymmetricBlockCipher var1) {
      SHA1Digest var2 = new SHA1Digest();
      this(var1, var2, (byte[])null);
   }

   public OAEPEncoding(AsymmetricBlockCipher var1, Digest var2) {
      this(var1, var2, (byte[])null);
   }

   public OAEPEncoding(AsymmetricBlockCipher var1, Digest var2, Digest var3, byte[] var4) {
      this.engine = var1;
      this.hash = var2;
      this.mgf1Hash = var3;
      byte[] var5 = new byte[var2.getDigestSize()];
      this.defHash = var5;
      if(var4 != null) {
         int var6 = var4.length;
         var2.update(var4, 0, var6);
      }

      byte[] var7 = this.defHash;
      var2.doFinal(var7, 0);
   }

   public OAEPEncoding(AsymmetricBlockCipher var1, Digest var2, byte[] var3) {
      this(var1, var2, var2, var3);
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

   private byte[] maskGeneratorFunction1(byte[] var1, int var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      byte[] var6 = new byte[this.mgf1Hash.getDigestSize()];
      byte[] var7 = new byte[4];
      int var8 = 0;
      this.hash.reset();

      int var15;
      do {
         this.ItoOSP(var8, var7);
         this.mgf1Hash.update(var1, var2, var3);
         Digest var9 = this.mgf1Hash;
         int var10 = var7.length;
         var9.update(var7, 0, var10);
         this.mgf1Hash.doFinal(var6, 0);
         int var12 = var6.length * var8;
         int var13 = var6.length;
         System.arraycopy(var6, 0, var5, var12, var13);
         ++var8;
         int var14 = var6.length;
         var15 = var4 / var14;
      } while(var8 < var15);

      if(var6.length * var8 < var4) {
         this.ItoOSP(var8, var7);
         this.mgf1Hash.update(var1, var2, var3);
         Digest var16 = this.mgf1Hash;
         int var17 = var7.length;
         var16.update(var7, 0, var17);
         this.mgf1Hash.doFinal(var6, 0);
         int var19 = var6.length * var8;
         int var20 = var5.length;
         int var21 = var6.length * var8;
         int var22 = var20 - var21;
         System.arraycopy(var6, 0, var5, var19, var22);
      }

      return var5;
   }

   public byte[] decodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4 = this.engine.processBlock(var1, var2, var3);
      int var5 = var4.length;
      int var6 = this.engine.getOutputBlockSize();
      byte[] var7;
      if(var5 < var6) {
         var7 = new byte[this.engine.getOutputBlockSize()];
         int var8 = var7.length;
         int var9 = var4.length;
         int var10 = var8 - var9;
         int var11 = var4.length;
         System.arraycopy(var4, 0, var7, var10, var11);
      } else {
         var7 = var4;
      }

      int var12 = var7.length;
      int var13 = this.defHash.length * 2 + 1;
      if(var12 < var13) {
         throw new InvalidCipherTextException("data too short");
      } else {
         int var14 = this.defHash.length;
         int var15 = var7.length;
         int var16 = this.defHash.length;
         int var17 = var15 - var16;
         int var18 = this.defHash.length;
         byte[] var19 = this.maskGeneratorFunction1(var7, var14, var17, var18);
         int var20 = 0;

         while(true) {
            int var21 = this.defHash.length;
            if(var20 == var21) {
               int var25 = this.defHash.length;
               int var26 = var7.length;
               int var27 = this.defHash.length;
               int var28 = var26 - var27;
               byte[] var29 = this.maskGeneratorFunction1(var7, 0, var25, var28);
               int var30 = this.defHash.length;

               while(true) {
                  int var31 = var7.length;
                  if(var30 == var31) {
                     int var37 = 0;

                     while(true) {
                        int var38 = this.defHash.length;
                        if(var37 == var38) {
                           int var42 = this.defHash.length * 2;

                           while(true) {
                              int var43 = var7.length;
                              if(var42 == var43 || var7[var42] != 0) {
                                 int var44 = var7.length - 1;
                                 if(var42 < var44 && var7[var42] == 1) {
                                    int var46 = var42 + 1;
                                    byte[] var47 = new byte[var7.length - var46];
                                    int var48 = var47.length;
                                    System.arraycopy(var7, var46, var47, 0, var48);
                                    return var47;
                                 } else {
                                    String var45 = "data start wrong " + var42;
                                    throw new InvalidCipherTextException(var45);
                                 }
                              }

                              ++var42;
                           }
                        }

                        byte var39 = this.defHash[var37];
                        int var40 = this.defHash.length + var37;
                        byte var41 = var7[var40];
                        if(var39 != var41) {
                           throw new InvalidCipherTextException("data hash wrong");
                        }

                        ++var37;
                     }
                  }

                  byte var32 = var7[var30];
                  int var33 = this.defHash.length;
                  int var34 = var30 - var33;
                  byte var35 = var29[var34];
                  byte var36 = (byte)(var32 ^ var35);
                  var7[var30] = var36;
                  ++var30;
               }
            }

            byte var22 = var7[var20];
            byte var23 = var19[var20];
            byte var24 = (byte)(var22 ^ var23);
            var7[var20] = var24;
            ++var20;
         }
      }
   }

   public byte[] encodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      int var4 = this.getInputBlockSize() + 1;
      int var5 = this.defHash.length * 2;
      byte[] var6 = new byte[var4 + var5];
      int var7 = var6.length - var3;
      System.arraycopy(var1, var2, var6, var7, var3);
      int var8 = var6.length - var3 - 1;
      var6[var8] = 1;
      byte[] var9 = this.defHash;
      int var10 = this.defHash.length;
      int var11 = this.defHash.length;
      System.arraycopy(var9, 0, var6, var10, var11);
      byte[] var12 = new byte[this.defHash.length];
      this.random.nextBytes(var12);
      int var13 = var12.length;
      int var14 = var6.length;
      int var15 = this.defHash.length;
      int var16 = var14 - var15;
      byte[] var17 = this.maskGeneratorFunction1(var12, 0, var13, var16);
      int var18 = this.defHash.length;

      while(true) {
         int var19 = var6.length;
         if(var18 == var19) {
            int var25 = this.defHash.length;
            System.arraycopy(var12, 0, var6, 0, var25);
            int var26 = this.defHash.length;
            int var27 = var6.length;
            int var28 = this.defHash.length;
            int var29 = var27 - var28;
            int var30 = this.defHash.length;
            byte[] var31 = this.maskGeneratorFunction1(var6, var26, var29, var30);
            int var32 = 0;

            while(true) {
               int var33 = this.defHash.length;
               if(var32 == var33) {
                  AsymmetricBlockCipher var37 = this.engine;
                  int var38 = var6.length;
                  return var37.processBlock(var6, 0, var38);
               }

               byte var34 = var6[var32];
               byte var35 = var31[var32];
               byte var36 = (byte)(var34 ^ var35);
               var6[var32] = var36;
               ++var32;
            }
         }

         byte var20 = var6[var18];
         int var21 = this.defHash.length;
         int var22 = var18 - var21;
         byte var23 = var17[var22];
         byte var24 = (byte)(var20 ^ var23);
         var6[var18] = var24;
         ++var18;
      }
   }

   public int getInputBlockSize() {
      int var1 = this.engine.getInputBlockSize();
      int var4;
      if(this.forEncryption) {
         int var2 = var1 - 1;
         int var3 = this.defHash.length * 2;
         var4 = var2 - var3;
      } else {
         var4 = var1;
      }

      return var4;
   }

   public int getOutputBlockSize() {
      int var1 = this.engine.getOutputBlockSize();
      int var2;
      if(this.forEncryption) {
         var2 = var1;
      } else {
         int var3 = var1 - 1;
         int var4 = this.defHash.length * 2;
         var2 = var3 - var4;
      }

      return var2;
   }

   public AsymmetricBlockCipher getUnderlyingCipher() {
      return this.engine;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof ParametersWithRandom) {
         SecureRandom var3 = ((ParametersWithRandom)var2).getRandom();
         this.random = var3;
      } else {
         SecureRandom var4 = new SecureRandom();
         this.random = var4;
      }

      this.engine.init(var1, var2);
      this.forEncryption = var1;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4;
      if(this.forEncryption) {
         var4 = this.encodeBlock(var1, var2, var3);
      } else {
         var4 = this.decodeBlock(var1, var2, var3);
      }

      return var4;
   }
}
