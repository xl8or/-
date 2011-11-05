package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.ExtendedDigest;
import myorg.bouncycastle.util.Arrays;

public final class WhirlpoolDigest implements ExtendedDigest {

   private static final int BITCOUNT_ARRAY_SIZE = 32;
   private static final int BYTE_LENGTH = 64;
   private static final long[] C0 = null;
   private static final long[] C1 = null;
   private static final long[] C2 = null;
   private static final long[] C3 = null;
   private static final long[] C4 = null;
   private static final long[] C5 = null;
   private static final long[] C6 = null;
   private static final long[] C7 = null;
   private static final int DIGEST_LENGTH_BYTES = 64;
   private static final short[] EIGHT = null;
   private static final int REDUCTION_POLYNOMIAL = 285;
   private static final int ROUNDS = 10;
   private static final int[] SBOX = new int[]{24, 35, 198, 232, 135, 184, 1, 79, 54, 166, 210, 245, 121, 111, 145, 82, 96, 188, 155, 142, 163, 12, 123, 53, 29, 224, 215, 194, 46, 75, 254, 87, 21, 119, 55, 229, 159, 240, 74, 218, 88, 201, 41, 10, 177, 160, 107, 133, 189, 93, 16, 244, 203, 62, 5, 103, 228, 39, 65, 139, 167, 125, 149, 216, 251, 238, 124, 102, 221, 23, 71, 158, 202, 45, 191, 7, 173, 90, 131, 51, 99, 2, 170, 113, 200, 25, 73, 217, 242, 227, 91, 136, 154, 38, 50, 176, 233, 15, 213, 128, 190, 205, 52, 72, 255, 122, 144, 95, 32, 104, 26, 174, 180, 84, 147, 34, 100, 241, 115, 18, 64, 8, 195, 236, 219, 161, 141, 61, 151, 0, 207, 43, 118, 130, 214, 27, 181, 175, 106, 80, 69, 243, 48, 239, 63, 85, 162, 234, 101, 186, 47, 192, 222, 28, 253, 77, 146, 117, 6, 138, 178, 230, 14, 31, 98, 212, 168, 150, 249, 197, 37, 89, 132, 114, 57, 76, 94, 120, 56, 140, 209, 165, 226, 97, 179, 33, 156, 30, 67, 199, 252, 4, 81, 153, 109, 13, 250, 223, 126, 36, 59, 171, 206, 17, 143, 78, 183, 235, 60, 129, 148, 247, 185, 19, 44, 211, 231, 110, 196, 3, 86, 68, 127, 169, 42, 187, 193, 83, 220, 11, 157, 108, 49, 116, 246, 70, 172, 137, 20, 225, 22, 58, 105, 9, 112, 182, 208, 237, 204, 66, 152, 164, 40, 92, 248, 134};
   private long[] _K;
   private long[] _L;
   private short[] _bitCount;
   private long[] _block;
   private byte[] _buffer;
   private int _bufferPos;
   private long[] _hash;
   private final long[] _rc;
   private long[] _state;


   static {
      EIGHT[31] = 8;
   }

   public WhirlpoolDigest() {
      Object var1 = null;
      this._rc = (long[])var1;
      byte[] var2 = new byte[64];
      this._buffer = var2;
      byte var3 = 0;
      this._bufferPos = var3;
      Object var4 = null;
      this._bitCount = (short[])var4;
      Object var5 = null;
      this._hash = (long[])var5;
      Object var6 = null;
      this._K = (long[])var6;
      Object var7 = null;
      this._L = (long[])var7;
      Object var8 = null;
      this._block = (long[])var8;
      Object var9 = null;
      this._state = (long[])var9;
      int var10 = 0;

      while(true) {
         short var12 = 256;
         if(var10 >= var12) {
            this._rc[0] = 0L;
            int var109 = 1;

            while(true) {
               byte var111 = 10;
               if(var109 > var111) {
                  return;
               }

               int var112 = (var109 - 1) * 8;
               long[] var113 = this._rc;
               long var114 = C0[var112] & -72057594037927936L;
               long[] var116 = C1;
               int var117 = var112 + 1;
               long var118 = var116[var117] & 71776119061217280L;
               long var120 = var114 ^ var118;
               long[] var122 = C2;
               int var123 = var112 + 2;
               long var124 = var122[var123] & 280375465082880L;
               long var126 = var120 ^ var124;
               long[] var128 = C3;
               int var129 = var112 + 3;
               long var130 = var128[var129] & 1095216660480L;
               long var132 = var126 ^ var130;
               long[] var134 = C4;
               int var135 = var112 + 4;
               long var136 = var134[var135] & 4278190080L;
               long var138 = var132 ^ var136;
               long[] var140 = C5;
               int var141 = var112 + 5;
               long var142 = var140[var141] & 16711680L;
               long var144 = var138 ^ var142;
               long[] var146 = C6;
               int var147 = var112 + 6;
               long var148 = var146[var147] & 65280L;
               long var150 = var144 ^ var148;
               long[] var152 = C7;
               int var153 = var112 + 7;
               long var154 = var152[var153] & 255L;
               long var156 = var150 ^ var154;
               var113[var109] = var156;
               ++var109;
            }
         }

         int var13 = SBOX[var10];
         int var14 = var13 << 1;
         int var17 = this.maskWithReductionPolynomial(var14);
         int var18 = var17 << 1;
         int var21 = this.maskWithReductionPolynomial(var18);
         int var22 = var21 ^ var13;
         int var23 = var21 << 1;
         int var26 = this.maskWithReductionPolynomial(var23);
         int var27 = var26 ^ var13;
         long[] var28 = C0;
         long var32 = this.packIntoLong(var13, var13, var21, var13, var26, var22, var17, var27);
         var28[var10] = var32;
         long[] var34 = C1;
         long var44 = this.packIntoLong(var27, var13, var13, var21, var13, var26, var22, var17);
         var34[var10] = var44;
         long[] var46 = C2;
         long var56 = this.packIntoLong(var17, var27, var13, var13, var21, var13, var26, var22);
         var46[var10] = var56;
         long[] var58 = C3;
         long var68 = this.packIntoLong(var22, var17, var27, var13, var13, var21, var13, var26);
         var58[var10] = var68;
         long[] var70 = C4;
         long var76 = this.packIntoLong(var26, var22, var17, var27, var13, var13, var21, var13);
         var70[var10] = var76;
         long[] var78 = C5;
         long var88 = this.packIntoLong(var13, var26, var22, var17, var27, var13, var13, var21);
         var78[var10] = var88;
         long[] var90 = C6;
         long var95 = this.packIntoLong(var21, var13, var26, var22, var17, var27, var13, var13);
         var90[var10] = var95;
         long[] var97 = C7;
         long var107 = this.packIntoLong(var13, var21, var13, var26, var22, var17, var27, var13);
         var97[var10] = var107;
         ++var10;
      }
   }

   public WhirlpoolDigest(WhirlpoolDigest var1) {
      Object var2 = null;
      this._rc = (long[])var2;
      byte[] var3 = new byte[64];
      this._buffer = var3;
      this._bufferPos = 0;
      Object var4 = null;
      this._bitCount = (short[])var4;
      Object var5 = null;
      this._hash = (long[])var5;
      Object var6 = null;
      this._K = (long[])var6;
      Object var7 = null;
      this._L = (long[])var7;
      Object var8 = null;
      this._block = (long[])var8;
      Object var9 = null;
      this._state = (long[])var9;
      long[] var10 = var1._rc;
      long[] var11 = this._rc;
      int var12 = this._rc.length;
      System.arraycopy(var10, 0, var11, 0, var12);
      byte[] var13 = var1._buffer;
      byte[] var14 = this._buffer;
      int var15 = this._buffer.length;
      System.arraycopy(var13, 0, var14, 0, var15);
      int var16 = var1._bufferPos;
      this._bufferPos = var16;
      short[] var17 = var1._bitCount;
      short[] var18 = this._bitCount;
      int var19 = this._bitCount.length;
      System.arraycopy(var17, 0, var18, 0, var19);
      long[] var20 = var1._hash;
      long[] var21 = this._hash;
      int var22 = this._hash.length;
      System.arraycopy(var20, 0, var21, 0, var22);
      long[] var23 = var1._K;
      long[] var24 = this._K;
      int var25 = this._K.length;
      System.arraycopy(var23, 0, var24, 0, var25);
      long[] var26 = var1._L;
      long[] var27 = this._L;
      int var28 = this._L.length;
      System.arraycopy(var26, 0, var27, 0, var28);
      long[] var29 = var1._block;
      long[] var30 = this._block;
      int var31 = this._block.length;
      System.arraycopy(var29, 0, var30, 0, var31);
      long[] var32 = var1._state;
      long[] var33 = this._state;
      int var34 = this._state.length;
      System.arraycopy(var32, 0, var33, 0, var34);
   }

   private long bytesToLongFromBuffer(byte[] var1, int var2) {
      int var3 = var2 + 0;
      long var4 = ((long)var1[var3] & 255L) << 56;
      int var6 = var2 + 1;
      long var7 = ((long)var1[var6] & 255L) << 48;
      long var9 = var4 | var7;
      int var11 = var2 + 2;
      long var12 = ((long)var1[var11] & 255L) << 40;
      long var14 = var9 | var12;
      int var16 = var2 + 3;
      long var17 = ((long)var1[var16] & 255L) << 32;
      long var19 = var14 | var17;
      int var21 = var2 + 4;
      long var22 = ((long)var1[var21] & 255L) << 24;
      long var24 = var19 | var22;
      int var26 = var2 + 5;
      long var27 = ((long)var1[var26] & 255L) << 16;
      long var29 = var24 | var27;
      int var31 = var2 + 6;
      long var32 = ((long)var1[var31] & 255L) << 8;
      long var34 = var29 | var32;
      int var36 = var2 + 7;
      long var37 = (long)var1[var36] & 255L;
      return var34 | var37;
   }

   private void convertLongToByteArray(long var1, byte[] var3, int var4) {
      for(int var5 = 0; var5 < 8; ++var5) {
         int var6 = var4 + var5;
         int var7 = var5 * 8;
         int var8 = 56 - var7;
         byte var9 = (byte)((int)(var1 >> var8 & 255L));
         var3[var6] = var9;
      }

   }

   private byte[] copyBitLength() {
      byte[] var1 = new byte[32];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return var1;
         }

         byte var4 = (byte)(this._bitCount[var2] & 255);
         var1[var2] = var4;
         ++var2;
      }
   }

   private void finish() {
      byte[] var1 = this.copyBitLength();
      byte[] var2 = this._buffer;
      int var3 = this._bufferPos;
      int var4 = var3 + 1;
      this._bufferPos = var4;
      byte var5 = (byte)(var2[var3] | 128);
      var2[var3] = var5;
      int var6 = this._bufferPos;
      int var7 = this._buffer.length;
      if(var6 == var7) {
         byte[] var8 = this._buffer;
         this.processFilledBuffer(var8, 0);
      }

      if(this._bufferPos > 32) {
         while(this._bufferPos != 0) {
            this.update((byte)0);
         }
      }

      while(this._bufferPos <= 32) {
         this.update((byte)0);
      }

      byte[] var9 = this._buffer;
      int var10 = var1.length;
      System.arraycopy(var1, 0, var9, 32, var10);
      byte[] var11 = this._buffer;
      this.processFilledBuffer(var11, 0);
   }

   private void increment() {
      int var1 = 0;

      for(int var2 = this._bitCount.length - 1; var2 >= 0; var2 += -1) {
         int var3 = this._bitCount[var2] & 255;
         short var4 = EIGHT[var2];
         int var5 = var3 + var4 + var1;
         var1 = var5 >>> 8;
         short[] var6 = this._bitCount;
         short var7 = (short)(var5 & 255);
         var6[var2] = var7;
      }

   }

   private int maskWithReductionPolynomial(int var1) {
      int var2 = var1;
      if((long)var1 >= 256L) {
         var2 = var1 ^ 285;
      }

      return var2;
   }

   private long packIntoLong(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      long var9 = (long)var1 << 56;
      long var11 = (long)var2 << 48;
      long var13 = var9 ^ var11;
      long var15 = (long)var3 << 40;
      long var17 = var13 ^ var15;
      long var19 = (long)var4 << 32;
      long var21 = var17 ^ var19;
      long var23 = (long)var5 << 24;
      long var25 = var21 ^ var23;
      long var27 = (long)var6 << 16;
      long var29 = var25 ^ var27;
      long var31 = (long)var7 << 8;
      long var33 = var29 ^ var31;
      long var35 = (long)var8;
      return var33 ^ var35;
   }

   private void processFilledBuffer(byte[] var1, int var2) {
      int var3 = 0;

      while(true) {
         int var4 = this._state.length;
         if(var3 >= var4) {
            this.processBlock();
            this._bufferPos = 0;
            Arrays.fill(this._buffer, (byte)0);
            return;
         }

         long[] var5 = this._block;
         byte[] var6 = this._buffer;
         int var7 = var3 * 8;
         long var8 = this.bytesToLongFromBuffer(var6, var7);
         var5[var3] = var8;
         ++var3;
      }
   }

   public int doFinal(byte[] param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public String getAlgorithmName() {
      return "Whirlpool";
   }

   public int getByteLength() {
      return 64;
   }

   public int getDigestSize() {
      return 64;
   }

   protected void processBlock() {
      // $FF: Couldn't be decompiled
   }

   public void reset() {
      this._bufferPos = 0;
      Arrays.fill(this._bitCount, (short)0);
      Arrays.fill(this._buffer, (byte)0);
      Arrays.fill(this._hash, 0L);
      Arrays.fill(this._K, 0L);
      Arrays.fill(this._L, 0L);
      Arrays.fill(this._block, 0L);
      Arrays.fill(this._state, 0L);
   }

   public void update(byte var1) {
      byte[] var2 = this._buffer;
      int var3 = this._bufferPos;
      var2[var3] = var1;
      int var4 = this._bufferPos + 1;
      this._bufferPos = var4;
      int var5 = this._bufferPos;
      int var6 = this._buffer.length;
      if(var5 == var6) {
         byte[] var7 = this._buffer;
         this.processFilledBuffer(var7, 0);
      }

      this.increment();
   }

   public void update(byte[] var1, int var2, int var3) {
      while(var3 > 0) {
         byte var4 = var1[var2];
         this.update(var4);
         ++var2;
         var3 += -1;
      }

   }
}
