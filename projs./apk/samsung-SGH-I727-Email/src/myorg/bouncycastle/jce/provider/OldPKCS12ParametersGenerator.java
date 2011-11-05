package myorg.bouncycastle.jce.provider;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

class OldPKCS12ParametersGenerator extends PBEParametersGenerator {

   public static final int IV_MATERIAL = 2;
   public static final int KEY_MATERIAL = 1;
   public static final int MAC_MATERIAL = 3;
   private Digest digest;
   private int u;
   private int v;


   public OldPKCS12ParametersGenerator(Digest var1) {
      this.digest = var1;
      if(var1 instanceof MD5Digest) {
         this.u = 16;
         this.v = 64;
      } else if(var1 instanceof SHA1Digest) {
         this.u = 20;
         this.v = 64;
      } else if(var1 instanceof RIPEMD160Digest) {
         this.u = 20;
         this.v = 64;
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Digest ");
         String var3 = var1.getAlgorithmName();
         String var4 = var2.append(var3).append(" unsupported").toString();
         throw new IllegalArgumentException(var4);
      }
   }

   private void adjust(byte[] var1, int var2, byte[] var3) {
      int var4 = var3.length - 1;
      int var5 = var3[var4] & 255;
      int var6 = var3.length + var2 - 1;
      int var7 = var1[var6] & 255;
      int var8 = var5 + var7 + 1;
      int var9 = var3.length + var2 - 1;
      byte var10 = (byte)var8;
      var1[var9] = var10;
      int var11 = var8 >>> 8;

      for(int var12 = var3.length - 2; var12 >= 0; var12 += -1) {
         int var13 = var3[var12] & 255;
         int var14 = var2 + var12;
         int var15 = var1[var14] & 255;
         int var16 = var13 + var15;
         int var17 = var11 + var16;
         int var18 = var2 + var12;
         byte var19 = (byte)var17;
         var1[var18] = var19;
         var11 = var17 >>> 8;
      }

   }

   private byte[] generateDerivedKey(int var1, int var2) {
      byte[] var3 = new byte[this.v];
      byte[] var4 = new byte[var2];

      while(true) {
         int var5 = var3.length;
         if(0 == var5) {
            byte[] var14;
            if(this.salt != null && this.salt.length != 0) {
               int var8 = this.v;
               int var9 = this.salt.length;
               int var10 = this.v;
               int var11 = var9 + var10 - 1;
               int var12 = this.v;
               int var13 = var11 / var12;
               var14 = new byte[var8 * var13];
               byte var15 = 0;

               while(true) {
                  int var16 = var14.length;
                  if(var15 == var16) {
                     break;
                  }

                  byte[] var17 = this.salt;
                  int var18 = this.salt.length;
                  int var19 = var15 % var18;
                  byte var20 = var17[var19];
                  var14[var15] = var20;
                  int var21 = var15 + 1;
               }
            } else {
               var14 = new byte[0];
            }

            byte[] var28;
            if(this.password != null && this.password.length != 0) {
               int var22 = this.v;
               int var23 = this.password.length;
               int var24 = this.v;
               int var25 = var23 + var24 - 1;
               int var26 = this.v;
               int var27 = var25 / var26;
               var28 = new byte[var22 * var27];
               int var120 = 0;

               while(true) {
                  int var29 = var28.length;
                  if(var120 == var29) {
                     break;
                  }

                  byte[] var30 = this.password;
                  int var31 = this.password.length;
                  int var32 = var120 % var31;
                  byte var33 = var30[var32];
                  var28[var120] = var33;
                  ++var120;
               }
            } else {
               var28 = new byte[0];
            }

            int var34 = var14.length;
            int var35 = var28.length;
            byte[] var36 = new byte[var34 + var35];
            int var37 = var14.length;
            byte var39 = 0;
            byte var41 = 0;
            System.arraycopy(var14, var39, var36, var41, var37);
            int var43 = var14.length;
            int var44 = var28.length;
            byte var46 = 0;
            System.arraycopy(var28, var46, var36, var43, var44);
            byte[] var50 = new byte[this.v];
            int var51 = this.u + var2 - 1;
            int var52 = this.u;
            int var53 = var51 / var52;
            int var54 = 1;

            label56:
            while(var54 <= var53) {
               byte[] var55 = new byte[this.u];
               Digest var56 = this.digest;
               int var57 = var3.length;
               byte var60 = 0;
               var56.update(var3, var60, var57);
               Digest var62 = this.digest;
               int var63 = var36.length;
               byte var66 = 0;
               var62.update(var36, var66, var63);
               Digest var68 = this.digest;
               byte var70 = 0;
               var68.doFinal(var55, var70);
               int var72 = 1;

               while(true) {
                  int var73 = this.iterationCount;
                  if(var72 == var73) {
                     int var84 = 0;

                     while(true) {
                        int var85 = var50.length;
                        if(var84 == var85) {
                           int var89 = 0;

                           while(true) {
                              int var90 = var36.length;
                              int var91 = this.v;
                              int var92 = var90 / var91;
                              if(var89 == var92) {
                                 if(var54 == var53) {
                                    int var98 = var54 - 1;
                                    int var99 = this.u;
                                    int var100 = var98 * var99;
                                    int var101 = var4.length;
                                    int var102 = var54 - 1;
                                    int var103 = this.u;
                                    int var104 = var102 * var103;
                                    int var105 = var101 - var104;
                                    byte var107 = 0;
                                    System.arraycopy(var55, var107, var4, var100, var105);
                                 } else {
                                    int var111 = var54 - 1;
                                    int var112 = this.u;
                                    int var113 = var111 * var112;
                                    int var114 = var55.length;
                                    byte var116 = 0;
                                    System.arraycopy(var55, var116, var4, var113, var114);
                                 }

                                 ++var54;
                                 continue label56;
                              }

                              int var93 = this.v * var89;
                              this.adjust(var36, var93, var50);
                              ++var89;
                           }
                        }

                        int var86 = var55.length;
                        int var87 = var84 % var86;
                        byte var88 = var55[var87];
                        var50[var54] = var88;
                        ++var84;
                     }
                  }

                  Digest var74 = this.digest;
                  int var75 = var55.length;
                  byte var78 = 0;
                  var74.update(var55, var78, var75);
                  Digest var80 = this.digest;
                  byte var82 = 0;
                  var80.doFinal(var55, var82);
                  ++var72;
               }
            }

            return var4;
         }

         byte var6 = (byte)var1;
         var3[0] = var6;
         int var7 = 0 + 1;
      }
   }

   public CipherParameters generateDerivedMacParameters(int var1) {
      int var2 = var1 / 8;
      byte[] var3 = this.generateDerivedKey(3, var2);
      return new KeyParameter(var3, 0, var2);
   }

   public CipherParameters generateDerivedParameters(int var1) {
      int var2 = var1 / 8;
      byte[] var3 = this.generateDerivedKey(1, var2);
      return new KeyParameter(var3, 0, var2);
   }

   public CipherParameters generateDerivedParameters(int var1, int var2) {
      int var3 = var1 / 8;
      int var4 = var2 / 8;
      byte[] var5 = this.generateDerivedKey(1, var3);
      byte[] var6 = this.generateDerivedKey(2, var4);
      KeyParameter var7 = new KeyParameter(var5, 0, var3);
      return new ParametersWithIV(var7, var6, 0, var4);
   }
}
