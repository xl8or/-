package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class SkipjackEngine implements BlockCipher {

   static final int BLOCK_SIZE = 8;
   static short[] ftable;
   private boolean encrypting;
   private int[] key0;
   private int[] key1;
   private int[] key2;
   private int[] key3;


   static {
      ((Object[])null)[0] = (short)null;
      ((Object[])null)[1] = (short)null;
      ((Object[])null)[2] = (short)null;
      ((Object[])null)[3] = (short)null;
      ((Object[])null)[4] = (short)null;
      ((Object[])null)[5] = (short)null;
      ((Object[])null)[6] = (short)null;
      ((Object[])null)[7] = (short)null;
      ((Object[])null)[8] = (short)null;
      ((Object[])null)[9] = (short)null;
      ((Object[])null)[10] = (short)null;
      ((Object[])null)[11] = (short)null;
      ((Object[])null)[12] = (short)null;
      ((Object[])null)[13] = (short)null;
      ((Object[])null)[14] = (short)null;
      ((Object[])null)[15] = (short)null;
      ((Object[])null)[16] = (short)null;
      ((Object[])null)[17] = (short)null;
      ((Object[])null)[18] = (short)null;
      ((Object[])null)[19] = (short)null;
      ((Object[])null)[20] = (short)null;
      ((Object[])null)[21] = (short)null;
      ((Object[])null)[22] = (short)null;
      ((Object[])null)[23] = (short)null;
      ((Object[])null)[24] = (short)null;
      ((Object[])null)[25] = (short)null;
      ((Object[])null)[26] = (short)null;
      ((Object[])null)[27] = (short)null;
      ((Object[])null)[28] = (short)null;
      ((Object[])null)[29] = (short)null;
      ((Object[])null)[30] = (short)null;
      ((Object[])null)[31] = (short)null;
      ((Object[])null)[32] = (short)null;
      ((Object[])null)[33] = (short)null;
      ((Object[])null)[34] = (short)null;
      ((Object[])null)[35] = (short)null;
      ((Object[])null)[36] = (short)null;
      ((Object[])null)[37] = (short)null;
      ((Object[])null)[38] = (short)null;
      ((Object[])null)[39] = (short)null;
      ((Object[])null)[40] = (short)null;
      ((Object[])null)[41] = (short)null;
      ((Object[])null)[42] = (short)null;
      ((Object[])null)[43] = (short)null;
      ((Object[])null)[44] = (short)null;
      ((Object[])null)[45] = (short)null;
      ((Object[])null)[46] = (short)null;
      ((Object[])null)[47] = (short)null;
      ((Object[])null)[48] = (short)null;
      ((Object[])null)[49] = (short)null;
      ((Object[])null)[50] = (short)null;
      ((Object[])null)[51] = (short)null;
      ((Object[])null)[52] = (short)null;
      ((Object[])null)[53] = (short)null;
      ((Object[])null)[54] = (short)null;
      ((Object[])null)[55] = (short)null;
      ((Object[])null)[56] = (short)null;
      ((Object[])null)[57] = (short)null;
      ((Object[])null)[58] = (short)null;
      ((Object[])null)[59] = (short)null;
      ((Object[])null)[60] = (short)null;
      ((Object[])null)[61] = (short)null;
      ((Object[])null)[62] = (short)null;
      ((Object[])null)[63] = (short)null;
      ((Object[])null)[64] = (short)null;
      ((Object[])null)[65] = (short)null;
      ((Object[])null)[66] = (short)null;
      ((Object[])null)[67] = (short)null;
      ((Object[])null)[68] = (short)null;
      ((Object[])null)[69] = (short)null;
      ((Object[])null)[70] = (short)null;
      ((Object[])null)[71] = (short)null;
      ((Object[])null)[72] = (short)null;
      ((Object[])null)[73] = (short)null;
      ((Object[])null)[74] = (short)null;
      ((Object[])null)[75] = (short)null;
      ((Object[])null)[76] = (short)null;
      ((Object[])null)[77] = (short)null;
      ((Object[])null)[78] = (short)null;
      ((Object[])null)[79] = (short)null;
      ((Object[])null)[80] = (short)null;
      ((Object[])null)[81] = (short)null;
      ((Object[])null)[82] = (short)null;
      ((Object[])null)[83] = (short)null;
      ((Object[])null)[84] = (short)null;
      ((Object[])null)[85] = (short)null;
      ((Object[])null)[86] = (short)null;
      ((Object[])null)[87] = (short)null;
      ((Object[])null)[88] = (short)null;
      ((Object[])null)[89] = (short)null;
      ((Object[])null)[90] = (short)null;
      ((Object[])null)[91] = (short)null;
      ((Object[])null)[92] = (short)null;
      ((Object[])null)[93] = (short)null;
      ((Object[])null)[94] = (short)null;
      ((Object[])null)[95] = (short)null;
      ((Object[])null)[96] = (short)null;
      ((Object[])null)[97] = (short)null;
      ((Object[])null)[98] = (short)null;
      ((Object[])null)[99] = (short)null;
      ((Object[])null)[100] = (short)null;
      ((Object[])null)[101] = (short)null;
      ((Object[])null)[102] = (short)null;
      ((Object[])null)[103] = (short)null;
      ((Object[])null)[104] = (short)null;
      ((Object[])null)[105] = (short)null;
      ((Object[])null)[106] = (short)null;
      ((Object[])null)[107] = (short)null;
      ((Object[])null)[108] = (short)null;
      ((Object[])null)[109] = (short)null;
      ((Object[])null)[110] = (short)null;
      ((Object[])null)[111] = (short)null;
      ((Object[])null)[112] = (short)null;
      ((Object[])null)[113] = (short)null;
      ((Object[])null)[114] = (short)null;
      ((Object[])null)[115] = (short)null;
      ((Object[])null)[116] = (short)null;
      ((Object[])null)[117] = (short)null;
      ((Object[])null)[118] = (short)null;
      ((Object[])null)[119] = (short)null;
      ((Object[])null)[120] = (short)null;
      ((Object[])null)[121] = (short)null;
      ((Object[])null)[122] = (short)null;
      ((Object[])null)[123] = (short)null;
      ((Object[])null)[124] = (short)null;
      ((Object[])null)[125] = (short)null;
      ((Object[])null)[126] = (short)null;
      ((Object[])null)[127] = (short)null;
      ((Object[])null)[128] = (short)null;
      ((Object[])null)[129] = (short)null;
      ((Object[])null)[130] = (short)null;
      ((Object[])null)[131] = (short)null;
      ((Object[])null)[132] = (short)null;
      ((Object[])null)[133] = (short)null;
      ((Object[])null)[134] = (short)null;
      ((Object[])null)[135] = (short)null;
      ((Object[])null)[136] = (short)null;
      ((Object[])null)[137] = (short)null;
      ((Object[])null)[138] = (short)null;
      ((Object[])null)[139] = (short)null;
      ((Object[])null)[140] = (short)null;
      ((Object[])null)[141] = (short)null;
      ((Object[])null)[142] = (short)null;
      ((Object[])null)[143] = (short)null;
      ((Object[])null)[144] = (short)null;
      ((Object[])null)[145] = (short)null;
      ((Object[])null)[146] = (short)null;
      ((Object[])null)[147] = (short)null;
      ((Object[])null)[148] = (short)null;
      ((Object[])null)[149] = (short)null;
      ((Object[])null)[150] = (short)null;
      ((Object[])null)[151] = (short)null;
      ((Object[])null)[152] = (short)null;
      ((Object[])null)[153] = (short)null;
      ((Object[])null)[154] = (short)null;
      ((Object[])null)[155] = (short)null;
      ((Object[])null)[156] = (short)null;
      ((Object[])null)[157] = (short)null;
      ((Object[])null)[158] = (short)null;
      ((Object[])null)[159] = (short)null;
      ((Object[])null)[160] = (short)null;
      ((Object[])null)[161] = (short)null;
      ((Object[])null)[162] = (short)null;
      ((Object[])null)[163] = (short)null;
      ((Object[])null)[164] = (short)null;
      ((Object[])null)[165] = (short)null;
      ((Object[])null)[166] = (short)null;
      ((Object[])null)[167] = (short)null;
      ((Object[])null)[168] = (short)null;
      ((Object[])null)[169] = (short)null;
      ((Object[])null)[170] = (short)null;
      ((Object[])null)[171] = (short)null;
      ((Object[])null)[172] = (short)null;
      ((Object[])null)[173] = (short)null;
      ((Object[])null)[174] = (short)null;
      ((Object[])null)[175] = (short)null;
      ((Object[])null)[176] = (short)null;
      ((Object[])null)[177] = (short)null;
      ((Object[])null)[178] = (short)null;
      ((Object[])null)[179] = (short)null;
      ((Object[])null)[180] = (short)null;
      ((Object[])null)[181] = (short)null;
      ((Object[])null)[182] = (short)null;
      ((Object[])null)[183] = (short)null;
      ((Object[])null)[184] = (short)null;
      ((Object[])null)[185] = (short)null;
      ((Object[])null)[186] = (short)null;
      ((Object[])null)[187] = (short)null;
      ((Object[])null)[188] = (short)null;
      ((Object[])null)[189] = (short)null;
      ((Object[])null)[190] = (short)null;
      ((Object[])null)[191] = (short)null;
      ((Object[])null)[192] = (short)null;
      ((Object[])null)[193] = (short)null;
      ((Object[])null)[194] = (short)null;
      ((Object[])null)[195] = (short)null;
      ((Object[])null)[196] = (short)null;
      ((Object[])null)[197] = (short)null;
      ((Object[])null)[198] = (short)null;
      ((Object[])null)[199] = (short)null;
      ((Object[])null)[200] = (short)null;
      ((Object[])null)[201] = (short)null;
      ((Object[])null)[202] = (short)null;
      ((Object[])null)[203] = (short)null;
      ((Object[])null)[204] = (short)null;
      ((Object[])null)[205] = (short)null;
      ((Object[])null)[206] = (short)null;
      ((Object[])null)[207] = (short)null;
      ((Object[])null)[208] = (short)null;
      ((Object[])null)[209] = (short)null;
      ((Object[])null)[210] = (short)null;
      ((Object[])null)[211] = (short)null;
      ((Object[])null)[212] = (short)null;
      ((Object[])null)[213] = (short)null;
      ((Object[])null)[214] = (short)null;
      ((Object[])null)[215] = (short)null;
      ((Object[])null)[216] = (short)null;
      ((Object[])null)[217] = (short)null;
      ((Object[])null)[218] = (short)null;
      ((Object[])null)[219] = (short)null;
      ((Object[])null)[220] = (short)null;
      ((Object[])null)[221] = (short)null;
      ((Object[])null)[222] = (short)null;
      ((Object[])null)[223] = (short)null;
      ((Object[])null)[224] = (short)null;
      ((Object[])null)[225] = (short)null;
      ((Object[])null)[226] = (short)null;
      ((Object[])null)[227] = (short)null;
      ((Object[])null)[228] = (short)null;
      ((Object[])null)[229] = (short)null;
      ((Object[])null)[230] = (short)null;
      ((Object[])null)[231] = (short)null;
      ((Object[])null)[232] = (short)null;
      ((Object[])null)[233] = (short)null;
      ((Object[])null)[234] = (short)null;
      ((Object[])null)[235] = (short)null;
      ((Object[])null)[236] = (short)null;
      ((Object[])null)[237] = (short)null;
      ((Object[])null)[238] = (short)null;
      ((Object[])null)[239] = (short)null;
      ((Object[])null)[240] = (short)null;
      ((Object[])null)[241] = (short)null;
      ((Object[])null)[242] = (short)null;
      ((Object[])null)[243] = (short)null;
      ((Object[])null)[244] = (short)null;
      ((Object[])null)[245] = (short)null;
      ((Object[])null)[246] = (short)null;
      ((Object[])null)[247] = (short)null;
      ((Object[])null)[248] = (short)null;
      ((Object[])null)[249] = (short)null;
      ((Object[])null)[250] = (short)null;
      ((Object[])null)[251] = (short)null;
      ((Object[])null)[252] = (short)null;
      ((Object[])null)[253] = (short)null;
      ((Object[])null)[254] = (short)null;
      ((Object[])null)[255] = (short)null;
      ftable = null;
   }

   public SkipjackEngine() {}

   private int g(int var1, int var2) {
      int var3 = var2 >> 8 & 255;
      int var4 = var2 & 255;
      short[] var5 = ftable;
      int var6 = this.key0[var1] ^ var4;
      int var7 = var5[var6] ^ var3;
      short[] var8 = ftable;
      int var9 = this.key1[var1] ^ var7;
      int var10 = var8[var9] ^ var4;
      short[] var11 = ftable;
      int var12 = this.key2[var1] ^ var10;
      int var13 = var11[var12] ^ var7;
      short[] var14 = ftable;
      int var15 = this.key3[var1] ^ var13;
      int var16 = var14[var15] ^ var10;
      return (var13 << 8) + var16;
   }

   private int h(int var1, int var2) {
      int var3 = var2 & 255;
      int var4 = var2 >> 8 & 255;
      short[] var5 = ftable;
      int var6 = this.key3[var1] ^ var4;
      int var7 = var5[var6] ^ var3;
      short[] var8 = ftable;
      int var9 = this.key2[var1] ^ var7;
      int var10 = var8[var9] ^ var4;
      short[] var11 = ftable;
      int var12 = this.key1[var1] ^ var10;
      int var13 = var11[var12] ^ var7;
      short[] var14 = ftable;
      int var15 = this.key0[var1] ^ var13;
      return ((var14[var15] ^ var10) << 8) + var13;
   }

   public int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = var2 + 0;
      int var6 = var1[var5] << 8;
      int var7 = var2 + 1;
      int var8 = var1[var7] & 255;
      int var9 = var6 + var8;
      int var10 = var2 + 2;
      int var11 = var1[var10] << 8;
      int var12 = var2 + 3;
      int var13 = var1[var12] & 255;
      int var14 = var11 + var13;
      int var15 = var2 + 4;
      int var16 = var1[var15] << 8;
      int var17 = var2 + 5;
      int var18 = var1[var17] & 255;
      int var19 = var16 + var18;
      int var20 = var2 + 6;
      int var21 = var1[var20] << 8;
      int var22 = var2 + 7;
      int var23 = var1[var22] & 255;
      int var24 = var21 + var23;
      int var25 = 31;

      for(int var26 = 0; var26 < 2; ++var26) {
         for(int var27 = 0; var27 < 8; ++var27) {
            int var28 = var19;
            var19 = var24;
            var24 = var9;
            var9 = this.h(var25, var14);
            int var29 = var9 ^ var28;
            int var30 = var25 + 1;
            var14 = var29 ^ var30;
            var25 += -1;
         }

         for(int var31 = 0; var31 < 8; ++var31) {
            int var32 = var19;
            var19 = var24;
            int var33 = var14 ^ var9;
            int var34 = var25 + 1;
            var24 = var33 ^ var34;
            var9 = this.h(var25, var14);
            var14 = var32;
            var25 += -1;
         }
      }

      int var35 = var4 + 0;
      byte var36 = (byte)(var9 >> 8);
      var3[var35] = var36;
      int var37 = var4 + 1;
      byte var38 = (byte)var9;
      var3[var37] = var38;
      int var39 = var4 + 2;
      byte var40 = (byte)(var14 >> 8);
      var3[var39] = var40;
      int var41 = var4 + 3;
      byte var42 = (byte)var14;
      var3[var41] = var42;
      int var43 = var4 + 4;
      byte var44 = (byte)(var19 >> 8);
      var3[var43] = var44;
      int var45 = var4 + 5;
      byte var46 = (byte)var19;
      var3[var45] = var46;
      int var47 = var4 + 6;
      byte var48 = (byte)(var24 >> 8);
      var3[var47] = var48;
      int var49 = var4 + 7;
      byte var50 = (byte)var24;
      var3[var49] = var50;
      return 8;
   }

   public int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = var2 + 0;
      int var6 = var1[var5] << 8;
      int var7 = var2 + 1;
      int var8 = var1[var7] & 255;
      int var9 = var6 + var8;
      int var10 = var2 + 2;
      int var11 = var1[var10] << 8;
      int var12 = var2 + 3;
      int var13 = var1[var12] & 255;
      int var14 = var11 + var13;
      int var15 = var2 + 4;
      int var16 = var1[var15] << 8;
      int var17 = var2 + 5;
      int var18 = var1[var17] & 255;
      int var19 = var16 + var18;
      int var20 = var2 + 6;
      int var21 = var1[var20] << 8;
      int var22 = var2 + 7;
      int var23 = var1[var22] & 255;
      int var24 = var21 + var23;
      int var25 = 0;

      for(int var26 = 0; var26 < 2; ++var26) {
         for(int var27 = 0; var27 < 8; ++var27) {
            int var28 = var24;
            var24 = var19;
            var19 = var14;
            var14 = this.g(var25, var9);
            int var29 = var14 ^ var28;
            int var30 = var25 + 1;
            var9 = var29 ^ var30;
            ++var25;
         }

         for(int var31 = 0; var31 < 8; ++var31) {
            int var32 = var24;
            var24 = var19;
            int var33 = var9 ^ var14;
            int var34 = var25 + 1;
            var19 = var33 ^ var34;
            var14 = this.g(var25, var9);
            var9 = var32;
            ++var25;
         }
      }

      int var35 = var4 + 0;
      byte var36 = (byte)(var9 >> 8);
      var3[var35] = var36;
      int var37 = var4 + 1;
      byte var38 = (byte)var9;
      var3[var37] = var38;
      int var39 = var4 + 2;
      byte var40 = (byte)(var14 >> 8);
      var3[var39] = var40;
      int var41 = var4 + 3;
      byte var42 = (byte)var14;
      var3[var41] = var42;
      int var43 = var4 + 4;
      byte var44 = (byte)(var19 >> 8);
      var3[var43] = var44;
      int var45 = var4 + 5;
      byte var46 = (byte)var19;
      var3[var45] = var46;
      int var47 = var4 + 6;
      byte var48 = (byte)(var24 >> 8);
      var3[var47] = var48;
      int var49 = var4 + 7;
      byte var50 = (byte)var24;
      var3[var49] = var50;
      return 8;
   }

   public String getAlgorithmName() {
      return "SKIPJACK";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to SKIPJACK init - ");
         String var4 = var2.getClass().getName();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      } else {
         byte[] var6 = ((KeyParameter)var2).getKey();
         this.encrypting = var1;
         int[] var7 = new int[32];
         this.key0 = var7;
         int[] var8 = new int[32];
         this.key1 = var8;
         int[] var9 = new int[32];
         this.key2 = var9;
         int[] var10 = new int[32];
         this.key3 = var10;

         for(int var11 = 0; var11 < 32; ++var11) {
            int[] var12 = this.key0;
            int var13 = var11 * 4 % 10;
            int var14 = var6[var13] & 255;
            var12[var11] = var14;
            int[] var15 = this.key1;
            int var16 = (var11 * 4 + 1) % 10;
            int var17 = var6[var16] & 255;
            var15[var11] = var17;
            int[] var18 = this.key2;
            int var19 = (var11 * 4 + 2) % 10;
            int var20 = var6[var19] & 255;
            var18[var11] = var20;
            int[] var21 = this.key3;
            int var22 = (var11 * 4 + 3) % 10;
            int var23 = var6[var22] & 255;
            var21[var11] = var23;
         }

      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.key1 == null) {
         throw new IllegalStateException("SKIPJACK engine not initialised");
      } else {
         int var5 = var2 + 8;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 8;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               if(this.encrypting) {
                  this.encryptBlock(var1, var2, var3, var4);
               } else {
                  this.decryptBlock(var1, var2, var3, var4);
               }

               return 8;
            }
         }
      }
   }

   public void reset() {}
}
