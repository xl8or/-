package myorg.bouncycastle.crypto.digests;

import myorg.bouncycastle.crypto.ExtendedDigest;

public abstract class GeneralDigest implements ExtendedDigest {

   private static final int BYTE_LENGTH = 64;
   private long byteCount;
   private byte[] xBuf;
   private int xBufOff;


   protected GeneralDigest() {
      byte[] var1 = new byte[4];
      this.xBuf = var1;
      this.xBufOff = 0;
   }

   protected GeneralDigest(GeneralDigest var1) {
      byte[] var2 = new byte[var1.xBuf.length];
      this.xBuf = var2;
      byte[] var3 = var1.xBuf;
      byte[] var4 = this.xBuf;
      int var5 = var1.xBuf.length;
      System.arraycopy(var3, 0, var4, 0, var5);
      int var6 = var1.xBufOff;
      this.xBufOff = var6;
      long var7 = var1.byteCount;
      this.byteCount = var7;
   }

   public void finish() {
      long var1 = this.byteCount << 3;
      this.update((byte)-128);

      while(this.xBufOff != 0) {
         this.update((byte)0);
      }

      this.processLength(var1);
      this.processBlock();
   }

   public int getByteLength() {
      return 64;
   }

   protected abstract void processBlock();

   protected abstract void processLength(long var1);

   protected abstract void processWord(byte[] var1, int var2);

   public void reset() {
      this.byteCount = 0L;
      this.xBufOff = 0;
      int var1 = 0;

      while(true) {
         int var2 = this.xBuf.length;
         if(var1 >= var2) {
            return;
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

      long var8 = this.byteCount + 1L;
      this.byteCount = var8;
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
         long var8 = this.byteCount;
         long var10 = (long)this.xBuf.length;
         long var12 = var8 + var10;
         this.byteCount = var12;
      }
   }
}
