package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.crypto.tls.TlsRuntimeException;

public class ByteQueue {

   private static final int INITBUFSIZE = 1024;
   private int available;
   private byte[] databuf;
   private int skipped;


   public ByteQueue() {
      byte[] var1 = new byte[1024];
      this.databuf = var1;
      this.skipped = 0;
      this.available = 0;
   }

   public static final int nextTwoPow(int var0) {
      int var1 = var0 >> 1;
      int var2 = var0 | var1;
      int var3 = var2 >> 2;
      int var4 = var2 | var3;
      int var5 = var4 >> 4;
      int var6 = var4 | var5;
      int var7 = var6 >> 8;
      int var8 = var6 | var7;
      int var9 = var8 >> 16;
      return (var8 | var9) + 1;
   }

   public void addData(byte[] var1, int var2, int var3) {
      int var4 = this.skipped;
      int var5 = this.available;
      int var6 = var4 + var5 + var3;
      int var7 = this.databuf.length;
      if(var6 > var7) {
         byte[] var8 = new byte[nextTwoPow(var1.length)];
         byte[] var9 = this.databuf;
         int var10 = this.skipped;
         int var11 = this.available;
         System.arraycopy(var9, var10, var8, 0, var11);
         this.skipped = 0;
         this.databuf = var8;
      }

      byte[] var12 = this.databuf;
      int var13 = this.skipped;
      int var14 = this.available;
      int var15 = var13 + var14;
      System.arraycopy(var1, var2, var12, var15, var3);
      int var16 = this.available + var3;
      this.available = var16;
   }

   public void read(byte[] var1, int var2, int var3, int var4) {
      if(this.available - var4 < var3) {
         throw new TlsRuntimeException("Not enough data to read");
      } else if(var1.length - var2 < var3) {
         StringBuilder var5 = (new StringBuilder()).append("Buffer size of ");
         int var6 = var1.length;
         String var7 = var5.append(var6).append(" is too small for a read of ").append(var3).append(" bytes").toString();
         throw new TlsRuntimeException(var7);
      } else {
         byte[] var8 = this.databuf;
         int var9 = this.skipped + var4;
         System.arraycopy(var8, var9, var1, var2, var3);
      }
   }

   public void removeData(int var1) {
      int var2 = this.available;
      if(var1 > var2) {
         StringBuilder var3 = (new StringBuilder()).append("Cannot remove ").append(var1).append(" bytes, only got ");
         int var4 = this.available;
         String var5 = var3.append(var4).toString();
         throw new TlsRuntimeException(var5);
      } else {
         int var6 = this.available - var1;
         this.available = var6;
         int var7 = this.skipped + var1;
         this.skipped = var7;
         int var8 = this.skipped;
         int var9 = this.databuf.length / 2;
         if(var8 > var9) {
            byte[] var10 = this.databuf;
            int var11 = this.skipped;
            byte[] var12 = this.databuf;
            int var13 = this.available;
            System.arraycopy(var10, var11, var12, 0, var13);
            this.skipped = 0;
         }
      }
   }

   public int size() {
      return this.available;
   }
}
