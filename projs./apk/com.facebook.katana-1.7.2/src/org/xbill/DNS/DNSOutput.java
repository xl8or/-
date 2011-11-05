package org.xbill.DNS;


public class DNSOutput {

   private byte[] array;
   private int pos;
   private int saved_pos;


   public DNSOutput() {
      this(32);
   }

   public DNSOutput(int var1) {
      byte[] var2 = new byte[var1];
      this.array = var2;
      this.pos = 0;
      this.saved_pos = -1;
   }

   private void check(long var1, int var3) {
      long var4 = 1L << var3;
      if(var1 < 0L || var1 > var4) {
         String var6 = var1 + " out of range for " + var3 + " bit value";
         throw new IllegalArgumentException(var6);
      }
   }

   private void need(int var1) {
      int var2 = this.array.length;
      int var3 = this.pos;
      if(var2 - var3 < var1) {
         int var4 = this.array.length * 2;
         int var5 = this.pos + var1;
         if(var4 < var5) {
            var4 = this.pos + var1;
         }

         byte[] var6 = new byte[var4];
         byte[] var7 = this.array;
         int var8 = this.pos;
         System.arraycopy(var7, 0, var6, 0, var8);
         this.array = var6;
      }
   }

   public int current() {
      return this.pos;
   }

   public void jump(int var1) {
      int var2 = this.pos;
      if(var1 > var2) {
         throw new IllegalArgumentException("cannot jump past end of data");
      } else {
         this.pos = var1;
      }
   }

   public void restore() {
      if(this.saved_pos < 0) {
         throw new IllegalStateException("no previous state");
      } else {
         int var1 = this.saved_pos;
         this.pos = var1;
         this.saved_pos = -1;
      }
   }

   public void save() {
      int var1 = this.pos;
      this.saved_pos = var1;
   }

   public byte[] toByteArray() {
      byte[] var1 = new byte[this.pos];
      byte[] var2 = this.array;
      int var3 = this.pos;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }

   public void writeByteArray(byte[] var1) {
      int var2 = var1.length;
      this.writeByteArray(var1, 0, var2);
   }

   public void writeByteArray(byte[] var1, int var2, int var3) {
      this.need(var3);
      byte[] var4 = this.array;
      int var5 = this.pos;
      System.arraycopy(var1, var2, var4, var5, var3);
      int var6 = this.pos + var3;
      this.pos = var6;
   }

   public void writeCountedString(byte[] var1) {
      if(var1.length > 255) {
         throw new IllegalArgumentException("Invalid counted string");
      } else {
         int var2 = var1.length + 1;
         this.need(var2);
         byte[] var3 = this.array;
         int var4 = this.pos;
         int var5 = var4 + 1;
         this.pos = var5;
         byte var6 = (byte)(var1.length & 255);
         var3[var4] = var6;
         int var7 = var1.length;
         this.writeByteArray(var1, 0, var7);
      }
   }

   public void writeU16(int var1) {
      long var2 = (long)var1;
      this.check(var2, 16);
      this.need(2);
      byte[] var4 = this.array;
      int var5 = this.pos;
      int var6 = var5 + 1;
      this.pos = var6;
      byte var7 = (byte)(var1 >>> 8 & 255);
      var4[var5] = var7;
      byte[] var8 = this.array;
      int var9 = this.pos;
      int var10 = var9 + 1;
      this.pos = var10;
      byte var11 = (byte)(var1 & 255);
      var8[var9] = var11;
   }

   public void writeU32(long var1) {
      this.check(var1, 32);
      this.need(4);
      byte[] var3 = this.array;
      int var4 = this.pos;
      int var5 = var4 + 1;
      this.pos = var5;
      byte var6 = (byte)((int)(var1 >>> 24 & 255L));
      var3[var4] = var6;
      byte[] var7 = this.array;
      int var8 = this.pos;
      int var9 = var8 + 1;
      this.pos = var9;
      byte var10 = (byte)((int)(var1 >>> 16 & 255L));
      var7[var8] = var10;
      byte[] var11 = this.array;
      int var12 = this.pos;
      int var13 = var12 + 1;
      this.pos = var13;
      byte var14 = (byte)((int)(var1 >>> 8 & 255L));
      var11[var12] = var14;
      byte[] var15 = this.array;
      int var16 = this.pos;
      int var17 = var16 + 1;
      this.pos = var17;
      byte var18 = (byte)((int)(var1 & 255L));
      var15[var16] = var18;
   }

   public void writeU8(int var1) {
      long var2 = (long)var1;
      this.check(var2, 8);
      this.need(1);
      byte[] var4 = this.array;
      int var5 = this.pos;
      int var6 = var5 + 1;
      this.pos = var6;
      byte var7 = (byte)(var1 & 255);
      var4[var5] = var7;
   }
}
