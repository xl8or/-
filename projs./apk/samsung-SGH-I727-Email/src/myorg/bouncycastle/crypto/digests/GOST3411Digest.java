package myorg.bouncycastle.crypto.digests;

import java.lang.reflect.Array;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.ExtendedDigest;
import myorg.bouncycastle.crypto.engines.GOST28147Engine;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithSBox;

public class GOST3411Digest implements ExtendedDigest {

   private static final byte[] C2 = new byte[]{(byte)0, (byte)255, (byte)0, (byte)255, (byte)0, (byte)255, (byte)0, (byte)255, (byte)255, (byte)0, (byte)255, (byte)0, (byte)255, (byte)0, (byte)255, (byte)0, (byte)0, (byte)255, (byte)255, (byte)0, (byte)255, (byte)0, (byte)0, (byte)255, (byte)255, (byte)0, (byte)0, (byte)0, (byte)255, (byte)255, (byte)0, (byte)255};
   private static final int DIGEST_LENGTH = 32;
   private byte[][] C;
   private byte[] H;
   private byte[] K;
   private byte[] L;
   private byte[] M;
   byte[] S;
   private byte[] Sum;
   byte[] U;
   byte[] V;
   byte[] W;
   byte[] a;
   private long byteCount;
   private BlockCipher cipher;
   short[] wS;
   short[] w_S;
   private byte[] xBuf;
   private int xBufOff;


   public GOST3411Digest() {
      byte[] var1 = new byte[32];
      this.H = var1;
      byte[] var2 = new byte[32];
      this.L = var2;
      byte[] var3 = new byte[32];
      this.M = var3;
      byte[] var4 = new byte[32];
      this.Sum = var4;
      int[] var5 = new int[]{4, 32};
      byte[][] var6 = (byte[][])Array.newInstance(Byte.TYPE, var5);
      this.C = var6;
      byte[] var7 = new byte[32];
      this.xBuf = var7;
      GOST28147Engine var8 = new GOST28147Engine();
      this.cipher = var8;
      byte[] var9 = new byte[32];
      this.K = var9;
      byte[] var10 = new byte[8];
      this.a = var10;
      Object var11 = null;
      this.wS = (short[])var11;
      Object var12 = null;
      this.w_S = (short[])var12;
      byte[] var13 = new byte[32];
      this.S = var13;
      byte[] var14 = new byte[32];
      this.U = var14;
      byte[] var15 = new byte[32];
      this.V = var15;
      byte[] var16 = new byte[32];
      this.W = var16;
      BlockCipher var17 = this.cipher;
      byte[] var18 = GOST28147Engine.getSBox("D-A");
      ParametersWithSBox var19 = new ParametersWithSBox((CipherParameters)null, var18);
      var17.init((boolean)1, var19);
      this.reset();
   }

   public GOST3411Digest(GOST3411Digest var1) {
      byte[] var2 = new byte[32];
      this.H = var2;
      byte[] var3 = new byte[32];
      this.L = var3;
      byte[] var4 = new byte[32];
      this.M = var4;
      byte[] var5 = new byte[32];
      this.Sum = var5;
      int[] var6 = new int[]{4, 32};
      byte[][] var7 = (byte[][])Array.newInstance(Byte.TYPE, var6);
      this.C = var7;
      byte[] var8 = new byte[32];
      this.xBuf = var8;
      GOST28147Engine var9 = new GOST28147Engine();
      this.cipher = var9;
      byte[] var10 = new byte[32];
      this.K = var10;
      byte[] var11 = new byte[8];
      this.a = var11;
      Object var12 = null;
      this.wS = (short[])var12;
      Object var13 = null;
      this.w_S = (short[])var13;
      byte[] var14 = new byte[32];
      this.S = var14;
      byte[] var15 = new byte[32];
      this.U = var15;
      byte[] var16 = new byte[32];
      this.V = var16;
      byte[] var17 = new byte[32];
      this.W = var17;
      BlockCipher var18 = this.cipher;
      byte[] var19 = GOST28147Engine.getSBox("D-A");
      ParametersWithSBox var20 = new ParametersWithSBox((CipherParameters)null, var19);
      var18.init((boolean)1, var20);
      this.reset();
      byte[] var21 = var1.H;
      byte[] var22 = this.H;
      int var23 = var1.H.length;
      System.arraycopy(var21, 0, var22, 0, var23);
      byte[] var24 = var1.L;
      byte[] var25 = this.L;
      int var26 = var1.L.length;
      System.arraycopy(var24, 0, var25, 0, var26);
      byte[] var27 = var1.M;
      byte[] var28 = this.M;
      int var29 = var1.M.length;
      System.arraycopy(var27, 0, var28, 0, var29);
      byte[] var30 = var1.Sum;
      byte[] var31 = this.Sum;
      int var32 = var1.Sum.length;
      System.arraycopy(var30, 0, var31, 0, var32);
      byte[] var33 = var1.C[1];
      byte[] var34 = this.C[1];
      int var35 = var1.C[1].length;
      System.arraycopy(var33, 0, var34, 0, var35);
      byte[] var36 = var1.C[2];
      byte[] var37 = this.C[2];
      int var38 = var1.C[2].length;
      System.arraycopy(var36, 0, var37, 0, var38);
      byte[] var39 = var1.C[3];
      byte[] var40 = this.C[3];
      int var41 = var1.C[3].length;
      System.arraycopy(var39, 0, var40, 0, var41);
      byte[] var42 = var1.xBuf;
      byte[] var43 = this.xBuf;
      int var44 = var1.xBuf.length;
      System.arraycopy(var42, 0, var43, 0, var44);
      int var45 = var1.xBufOff;
      this.xBufOff = var45;
      long var46 = var1.byteCount;
      this.byteCount = var46;
   }

   private byte[] A(byte[] var1) {
      for(int var2 = 0; var2 < 8; ++var2) {
         byte[] var3 = this.a;
         byte var4 = var1[var2];
         int var5 = var2 + 8;
         byte var6 = var1[var5];
         byte var7 = (byte)(var4 ^ var6);
         var3[var2] = var7;
      }

      System.arraycopy(var1, 8, var1, 0, 24);
      System.arraycopy(this.a, 0, var1, 24, 8);
      return var1;
   }

   private void E(byte[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      BlockCipher var6 = this.cipher;
      KeyParameter var7 = new KeyParameter(var1);
      var6.init((boolean)1, var7);
      this.cipher.processBlock(var4, var5, var2, var3);
   }

   private void LongToBytes(long var1, byte[] var3, int var4) {
      int var5 = var4 + 7;
      byte var6 = (byte)((int)(var1 >> 56));
      var3[var5] = var6;
      int var7 = var4 + 6;
      byte var8 = (byte)((int)(var1 >> 48));
      var3[var7] = var8;
      int var9 = var4 + 5;
      byte var10 = (byte)((int)(var1 >> 40));
      var3[var9] = var10;
      int var11 = var4 + 4;
      byte var12 = (byte)((int)(var1 >> 32));
      var3[var11] = var12;
      int var13 = var4 + 3;
      byte var14 = (byte)((int)(var1 >> 24));
      var3[var13] = var14;
      int var15 = var4 + 2;
      byte var16 = (byte)((int)(var1 >> 16));
      var3[var15] = var16;
      int var17 = var4 + 1;
      byte var18 = (byte)((int)(var1 >> 8));
      var3[var17] = var18;
      byte var19 = (byte)((int)var1);
      var3[var4] = var19;
   }

   private byte[] P(byte[] var1) {
      for(int var2 = 0; var2 < 8; ++var2) {
         byte[] var3 = this.K;
         int var4 = var2 * 4;
         byte var5 = var1[var2];
         var3[var4] = var5;
         byte[] var6 = this.K;
         int var7 = var2 * 4 + 1;
         int var8 = var2 + 8;
         byte var9 = var1[var8];
         var6[var7] = var9;
         byte[] var10 = this.K;
         int var11 = var2 * 4 + 2;
         int var12 = var2 + 16;
         byte var13 = var1[var12];
         var10[var11] = var13;
         byte[] var14 = this.K;
         int var15 = var2 * 4 + 3;
         int var16 = var2 + 24;
         byte var17 = var1[var16];
         var14[var15] = var17;
      }

      return this.K;
   }

   private void cpyBytesToShort(byte[] var1, short[] var2) {
      int var3 = 0;

      while(true) {
         int var4 = var1.length / 2;
         if(var3 >= var4) {
            return;
         }

         int var5 = var3 * 2 + 1;
         int var6 = var1[var5] << 8 & '\uff00';
         int var7 = var3 * 2;
         int var8 = var1[var7] & 255;
         short var9 = (short)(var6 | var8);
         var2[var3] = var9;
         ++var3;
      }
   }

   private void cpyShortToBytes(short[] var1, byte[] var2) {
      int var3 = 0;

      while(true) {
         int var4 = var2.length / 2;
         if(var3 >= var4) {
            return;
         }

         int var5 = var3 * 2 + 1;
         byte var6 = (byte)(var1[var3] >> 8);
         var2[var5] = var6;
         int var7 = var3 * 2;
         byte var8 = (byte)var1[var3];
         var2[var7] = var8;
         ++var3;
      }
   }

   private void finish() {
      long var1 = this.byteCount * 8L;
      byte[] var3 = this.L;
      this.LongToBytes(var1, var3, 0);

      while(this.xBufOff != 0) {
         this.update((byte)0);
      }

      byte[] var4 = this.L;
      this.processBlock(var4, 0);
      byte[] var5 = this.Sum;
      this.processBlock(var5, 0);
   }

   private void fw(byte[] var1) {
      short[] var2 = this.wS;
      this.cpyBytesToShort(var1, var2);
      short[] var3 = this.w_S;
      short var4 = this.wS[0];
      short var5 = this.wS[1];
      int var6 = var4 ^ var5;
      short var7 = this.wS[2];
      int var8 = var6 ^ var7;
      short var9 = this.wS[3];
      int var10 = var8 ^ var9;
      short var11 = this.wS[12];
      int var12 = var10 ^ var11;
      short var13 = this.wS[15];
      short var14 = (short)(var12 ^ var13);
      var3[15] = var14;
      short[] var15 = this.wS;
      short[] var16 = this.w_S;
      System.arraycopy(var15, 1, var16, 0, 15);
      short[] var17 = this.w_S;
      this.cpyShortToBytes(var17, var1);
   }

   private void sumByteArray(byte[] var1) {
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = this.Sum.length;
         if(var3 == var4) {
            return;
         }

         int var5 = this.Sum[var3] & 255;
         int var6 = var1[var3] & 255;
         int var7 = var5 + var6 + var2;
         byte[] var8 = this.Sum;
         byte var9 = (byte)var7;
         var8[var3] = var9;
         var2 = var7 >>> 8;
         ++var3;
      }
   }

   public int doFinal(byte[] var1, int var2) {
      this.finish();
      byte[] var3 = this.H;
      int var4 = this.H.length;
      System.arraycopy(var3, 0, var1, var2, var4);
      this.reset();
      return 32;
   }

   public String getAlgorithmName() {
      return "GOST3411";
   }

   public int getByteLength() {
      return 32;
   }

   public int getDigestSize() {
      return 32;
   }

   protected void processBlock(byte[] var1, int var2) {
      byte[] var3 = this.M;
      System.arraycopy(var1, var2, var3, 0, 32);
      byte[] var4 = this.H;
      byte[] var5 = this.U;
      System.arraycopy(var4, 0, var5, 0, 32);
      byte[] var6 = this.M;
      byte[] var7 = this.V;
      System.arraycopy(var6, 0, var7, 0, 32);

      for(int var8 = 0; var8 < 32; ++var8) {
         byte[] var9 = this.W;
         byte var10 = this.U[var8];
         byte var11 = this.V[var8];
         byte var12 = (byte)(var10 ^ var11);
         var9[var8] = var12;
      }

      byte[] var13 = this.W;
      byte[] var14 = this.P(var13);
      byte[] var15 = this.S;
      byte[] var16 = this.H;
      this.E(var14, var15, 0, var16, 0);

      for(int var17 = 1; var17 < 4; ++var17) {
         byte[] var18 = this.U;
         byte[] var19 = this.A(var18);

         for(int var20 = 0; var20 < 32; ++var20) {
            byte[] var21 = this.U;
            byte var22 = var19[var20];
            byte var23 = this.C[var17][var20];
            byte var24 = (byte)(var22 ^ var23);
            var21[var20] = var24;
         }

         byte[] var25 = this.V;
         byte[] var26 = this.A(var25);
         byte[] var27 = this.A(var26);
         this.V = var27;

         int var32;
         for(byte var57 = 0; var57 < 32; var32 = var57 + 1) {
            byte[] var28 = this.W;
            byte var29 = this.U[var57];
            byte var30 = this.V[var57];
            byte var31 = (byte)(var29 ^ var30);
            var28[var57] = var31;
         }

         byte[] var33 = this.W;
         byte[] var34 = this.P(var33);
         byte[] var35 = this.S;
         int var36 = var17 * 8;
         byte[] var37 = this.H;
         int var38 = var17 * 8;
         this.E(var34, var35, var36, var37, var38);
      }

      for(int var39 = 0; var39 < 12; ++var39) {
         byte[] var40 = this.S;
         this.fw(var40);
      }

      for(int var41 = 0; var41 < 32; ++var41) {
         byte[] var42 = this.S;
         byte var43 = this.S[var41];
         byte var44 = this.M[var41];
         byte var45 = (byte)(var43 ^ var44);
         var42[var41] = var45;
      }

      byte[] var46 = this.S;
      this.fw(var46);

      for(int var47 = 0; var47 < 32; ++var47) {
         byte[] var48 = this.S;
         byte var49 = this.H[var47];
         byte var50 = this.S[var47];
         byte var51 = (byte)(var49 ^ var50);
         var48[var47] = var51;
      }

      for(int var52 = 0; var52 < 61; ++var52) {
         byte[] var53 = this.S;
         this.fw(var53);
      }

      byte[] var54 = this.S;
      byte[] var55 = this.H;
      int var56 = this.H.length;
      System.arraycopy(var54, 0, var55, 0, var56);
   }

   public void reset() {
      this.byteCount = 0L;
      this.xBufOff = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.H.length;
         if(var1 >= var2) {
            int var3 = 0;

            while(true) {
               int var4 = this.L.length;
               if(var3 >= var4) {
                  int var5 = 0;

                  while(true) {
                     int var6 = this.M.length;
                     if(var5 >= var6) {
                        int var7 = 0;

                        while(true) {
                           int var8 = this.C[1].length;
                           if(var7 >= var8) {
                              int var9 = 0;

                              while(true) {
                                 int var10 = this.C[3].length;
                                 if(var9 >= var10) {
                                    int var11 = 0;

                                    while(true) {
                                       int var12 = this.Sum.length;
                                       if(var11 >= var12) {
                                          int var13 = 0;

                                          while(true) {
                                             int var14 = this.xBuf.length;
                                             if(var13 >= var14) {
                                                byte[] var15 = C2;
                                                byte[] var16 = this.C[2];
                                                int var17 = C2.length;
                                                System.arraycopy(var15, 0, var16, 0, var17);
                                                return;
                                             }

                                             this.xBuf[var13] = 0;
                                             ++var13;
                                          }
                                       }

                                       this.Sum[var11] = 0;
                                       ++var11;
                                    }
                                 }

                                 this.C[3][var9] = 0;
                                 ++var9;
                              }
                           }

                           this.C[1][var7] = 0;
                           ++var7;
                        }
                     }

                     this.M[var5] = 0;
                     ++var5;
                  }
               }

               this.L[var3] = 0;
               ++var3;
            }
         }

         this.H[var1] = 0;
         ++var1;
      }
   }

   public void update(byte var1) {
      byte[] var2 = this.xBuf;
      int var3 = this.xBufOff;
      int var4 = var3 + 1;
      this.xBufOff = var4;
      var2[var3] = var1;
      int var5 = this.xBufOff;
      int var6 = this.xBuf.length;
      if(var5 == var6) {
         byte[] var7 = this.xBuf;
         this.sumByteArray(var7);
         byte[] var8 = this.xBuf;
         this.processBlock(var8, 0);
         this.xBufOff = 0;
      }

      long var9 = this.byteCount + 1L;
      this.byteCount = var9;
   }

   public void update(byte[] var1, int var2, int var3) {
      while(this.xBufOff != 0 && var3 > 0) {
         byte var4 = var1[var2];
         this.update(var4);
         ++var2;
         var3 += -1;
      }

      while(true) {
         int var5 = this.xBuf.length;
         if(var3 <= var5) {
            while(var3 > 0) {
               byte var18 = var1[var2];
               this.update(var18);
               ++var2;
               var3 += -1;
            }

            return;
         }

         byte[] var6 = this.xBuf;
         int var7 = this.xBuf.length;
         System.arraycopy(var1, var2, var6, 0, var7);
         byte[] var8 = this.xBuf;
         this.sumByteArray(var8);
         byte[] var9 = this.xBuf;
         this.processBlock(var9, 0);
         int var10 = this.xBuf.length;
         var2 += var10;
         int var11 = this.xBuf.length;
         var3 -= var11;
         long var12 = this.byteCount;
         long var14 = (long)this.xBuf.length;
         long var16 = var12 + var14;
         this.byteCount = var16;
      }
   }
}
