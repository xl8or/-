package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.ExtendedDigest;
import myorg.bouncycastle.crypto.util.Pack;

public abstract class LongDigest implements ExtendedDigest {

   private static final int BYTE_LENGTH = 128;
   static final long[] K;
   protected long H1;
   protected long H2;
   protected long H3;
   protected long H4;
   protected long H5;
   protected long H6;
   protected long H7;
   protected long H8;
   private long[] W;
   private long byteCount1;
   private long byteCount2;
   private int wOff;
   private byte[] xBuf;
   private int xBufOff;


   static {
      ((Object[])null)[0] = -685199838L;
      ((Object[])null)[1] = 8158064640168781261L;
      ((Object[])null)[2] = -330482897L;
      ((Object[])null)[3] = -2121671748L;
      ((Object[])null)[4] = -213338824L;
      ((Object[])null)[5] = -1241133031L;
      ((Object[])null)[6] = -1357295717L;
      ((Object[])null)[7] = -630357736L;
      ((Object[])null)[8] = -1560083902L;
      ((Object[])null)[9] = 1334009975649890238L;
      ((Object[])null)[10] = 2608012711638119052L;
      ((Object[])null)[11] = -704662302L;
      ((Object[])null)[12] = -226784913L;
      ((Object[])null)[13] = -9160688886553864527L;
      ((Object[])null)[14] = -7215885187991268811L;
      ((Object[])null)[15] = -815192428L;
      ((Object[])null)[16] = -1628353838L;
      ((Object[])null)[17] = -1171420211273849373L;
      ((Object[])null)[18] = -1953704523L;
      ((Object[])null)[19] = 2597628984639134821L;
      ((Object[])null)[20] = 3308224258029322869L;
      ((Object[])null)[21] = 5365058923640841347L;
      ((Object[])null)[22] = -1119749164L;
      ((Object[])null)[23] = -2096016459L;
      ((Object[])null)[24] = -295247957L;
      ((Object[])null)[25] = -6327057829258317296L;
      ((Object[])null)[26] = -1728372417L;
      ((Object[])null)[27] = -1091629340L;
      ((Object[])null)[28] = -4116276920077217854L;
      ((Object[])null)[29] = -1828018395L;
      ((Object[])null)[30] = -536640913L;
      ((Object[])null)[31] = 1452737877330783856L;
      ((Object[])null)[32] = 2861767655752347644L;
      ((Object[])null)[33] = 3322285676063803686L;
      ((Object[])null)[34] = 5560940570517711597L;
      ((Object[])null)[35] = -1651133473L;
      ((Object[])null)[36] = -1951439906L;
      ((Object[])null)[37] = 8532644243296465576L;
      ((Object[])null)[38] = -9096487096722542874L;
      ((Object[])null)[39] = -7894198246740708037L;
      ((Object[])null)[40] = -6719396339535248540L;
      ((Object[])null)[41] = -1136513023L;
      ((Object[])null)[42] = -789014639L;
      ((Object[])null)[43] = -4076793802049405392L;
      ((Object[])null)[44] = -688958952L;
      ((Object[])null)[45] = -2983346525034927856L;
      ((Object[])null)[46] = -860691631967231958L;
      ((Object[])null)[47] = 1182934255886127544L;
      ((Object[])null)[48] = -1194143544L;
      ((Object[])null)[49] = 2177327727835720531L;
      ((Object[])null)[50] = -544281703L;
      ((Object[])null)[51] = -509917016L;
      ((Object[])null)[52] = -976659869L;
      ((Object[])null)[53] = -482243893L;
      ((Object[])null)[54] = 6601373596472566643L;
      ((Object[])null)[55] = -692930397L;
      ((Object[])null)[56] = 8399075790359081724L;
      ((Object[])null)[57] = 8693463985226723168L;
      ((Object[])null)[58] = -1578062990L;
      ((Object[])null)[59] = -8302665154208450068L;
      ((Object[])null)[60] = -8016688836872298968L;
      ((Object[])null)[61] = -561857047L;
      ((Object[])null)[62] = -1295615723L;
      ((Object[])null)[63] = -479046869L;
      ((Object[])null)[64] = -366583396L;
      ((Object[])null)[65] = -3348786107499101689L;
      ((Object[])null)[66] = -840897762L;
      ((Object[])null)[67] = -294727304L;
      ((Object[])null)[68] = 500013540394364858L;
      ((Object[])null)[69] = -1563912026L;
      ((Object[])null)[70] = -1090974290L;
      ((Object[])null)[71] = 1977374033974150939L;
      ((Object[])null)[72] = 2944078676154940804L;
      ((Object[])null)[73] = 3659926193048069267L;
      ((Object[])null)[74] = 4368137639120453308L;
      ((Object[])null)[75] = -1676669620L;
      ((Object[])null)[76] = -885112138L;
      ((Object[])null)[77] = -60457430L;
      ((Object[])null)[78] = 6902733635092675308L;
      ((Object[])null)[79] = 7801388544844847127L;
      K = null;
   }

   protected LongDigest() {
      Object var1 = null;
      this.W = (long[])var1;
      byte[] var2 = new byte[8];
      this.xBuf = var2;
      this.xBufOff = 0;
      this.reset();
   }

   protected LongDigest(LongDigest var1) {
      Object var2 = null;
      this.W = (long[])var2;
      byte[] var3 = new byte[var1.xBuf.length];
      this.xBuf = var3;
      byte[] var4 = var1.xBuf;
      byte[] var5 = this.xBuf;
      int var6 = var1.xBuf.length;
      System.arraycopy(var4, 0, var5, 0, var6);
      int var7 = var1.xBufOff;
      this.xBufOff = var7;
      long var8 = var1.byteCount1;
      this.byteCount1 = var8;
      long var10 = var1.byteCount2;
      this.byteCount2 = var10;
      long var12 = var1.H1;
      this.H1 = var12;
      long var14 = var1.H2;
      this.H2 = var14;
      long var16 = var1.H3;
      this.H3 = var16;
      long var18 = var1.H4;
      this.H4 = var18;
      long var20 = var1.H5;
      this.H5 = var20;
      long var22 = var1.H6;
      this.H6 = var22;
      long var24 = var1.H7;
      this.H7 = var24;
      long var26 = var1.H8;
      this.H8 = var26;
      long[] var28 = var1.W;
      long[] var29 = this.W;
      int var30 = var1.W.length;
      System.arraycopy(var28, 0, var29, 0, var30);
      int var31 = var1.wOff;
      this.wOff = var31;
   }

   private long Ch(long var1, long var3, long var5) {
      long var7 = var1 & var3;
      long var9 = (65535L ^ var1) & var5;
      return var7 ^ var9;
   }

   private long Maj(long var1, long var3, long var5) {
      long var7 = var1 & var3;
      long var9 = var1 & var5;
      long var11 = var7 ^ var9;
      long var13 = var3 & var5;
      return var11 ^ var13;
   }

   private long Sigma0(long var1) {
      long var3 = var1 << 63;
      long var5 = var1 >>> 1;
      long var7 = var3 | var5;
      long var9 = var1 << 56;
      long var11 = var1 >>> 8;
      long var13 = var9 | var11;
      long var15 = var7 ^ var13;
      long var17 = var1 >>> 7;
      return var15 ^ var17;
   }

   private long Sigma1(long var1) {
      long var3 = var1 << 45;
      long var5 = var1 >>> 19;
      long var7 = var3 | var5;
      long var9 = var1 << 3;
      long var11 = var1 >>> 61;
      long var13 = var9 | var11;
      long var15 = var7 ^ var13;
      long var17 = var1 >>> 6;
      return var15 ^ var17;
   }

   private long Sum0(long var1) {
      long var3 = var1 << 36;
      long var5 = var1 >>> 28;
      long var7 = var3 | var5;
      long var9 = var1 << 30;
      long var11 = var1 >>> 34;
      long var13 = var9 | var11;
      long var15 = var7 ^ var13;
      long var17 = var1 << 25;
      long var19 = var1 >>> 39;
      long var21 = var17 | var19;
      return var15 ^ var21;
   }

   private long Sum1(long var1) {
      long var3 = var1 << 50;
      long var5 = var1 >>> 14;
      long var7 = var3 | var5;
      long var9 = var1 << 46;
      long var11 = var1 >>> 18;
      long var13 = var9 | var11;
      long var15 = var7 ^ var13;
      long var17 = var1 << 23;
      long var19 = var1 >>> 41;
      long var21 = var17 | var19;
      return var15 ^ var21;
   }

   private void adjustByteCounts() {
      if(this.byteCount1 > 2305843009213693951L) {
         long var1 = this.byteCount2;
         long var3 = this.byteCount1 >>> 61;
         long var5 = var1 + var3;
         this.byteCount2 = var5;
         long var7 = this.byteCount1 & 2305843009213693951L;
         this.byteCount1 = var7;
      }
   }

   public void finish() {
      this.adjustByteCounts();
      long var1 = this.byteCount1 << 3;
      long var3 = this.byteCount2;
      this.update((byte)-128);

      while(this.xBufOff != 0) {
         this.update((byte)0);
      }

      this.processLength(var1, var3);
      this.processBlock();
   }

   public int getByteLength() {
      return 128;
   }

   protected void processBlock() {
      // $FF: Couldn't be decompiled
   }

   protected void processLength(long var1, long var3) {
      if(this.wOff > 14) {
         this.processBlock();
      }

      this.W[14] = var3;
      this.W[15] = var1;
   }

   protected void processWord(byte[] var1, int var2) {
      long[] var3 = this.W;
      int var4 = this.wOff;
      long var5 = Pack.bigEndianToLong(var1, var2);
      var3[var4] = var5;
      int var7 = this.wOff + 1;
      this.wOff = var7;
      if(var7 == 16) {
         this.processBlock();
      }
   }

   public void reset() {
      this.byteCount1 = 0L;
      this.byteCount2 = 0L;
      this.xBufOff = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.xBuf.length;
         if(var1 >= var2) {
            this.wOff = 0;
            int var3 = 0;

            while(true) {
               int var4 = this.W.length;
               if(var3 == var4) {
                  return;
               }

               this.W[var3] = 0L;
               ++var3;
            }
         }

         this.xBuf[var1] = 0;
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
         this.processWord(var7, 0);
         this.xBufOff = 0;
      }

      long var8 = this.byteCount1 + 1L;
      this.byteCount1 = var8;
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
               byte var14 = var1[var2];
               this.update(var14);
               ++var2;
               var3 += -1;
            }

            return;
         }

         this.processWord(var1, var2);
         int var6 = this.xBuf.length;
         var2 += var6;
         int var7 = this.xBuf.length;
         var3 -= var7;
         long var8 = this.byteCount1;
         long var10 = (long)this.xBuf.length;
         long var12 = var8 + var10;
         this.byteCount1 = var12;
      }
   }
}
