package org.xbill.DNS;

import org.xbill.DNS.WireParseException;

public class DNSInput {

   private byte[] array;
   private int end;
   private int pos;
   private int saved_end;
   private int saved_pos;


   public DNSInput(byte[] var1) {
      this.array = var1;
      this.pos = 0;
      int var2 = this.array.length;
      this.end = var2;
      this.saved_pos = -1;
      this.saved_end = -1;
   }

   private void require(int var1) throws WireParseException {
      int var2 = this.remaining();
      if(var1 > var2) {
         throw new WireParseException("end of input");
      }
   }

   public void clearActive() {
      int var1 = this.array.length;
      this.end = var1;
   }

   public int current() {
      return this.pos;
   }

   public void jump(int var1) {
      int var2 = this.array.length;
      if(var1 >= var2) {
         throw new IllegalArgumentException("cannot jump past end of input");
      } else {
         this.pos = var1;
         int var3 = this.array.length;
         this.end = var3;
      }
   }

   public void readByteArray(byte[] var1, int var2, int var3) throws WireParseException {
      this.require(var3);
      byte[] var4 = this.array;
      int var5 = this.pos;
      System.arraycopy(var4, var5, var1, var2, var3);
      int var6 = this.pos + var3;
      this.pos = var6;
   }

   public byte[] readByteArray() {
      int var1 = this.remaining();
      byte[] var2 = new byte[var1];
      byte[] var3 = this.array;
      int var4 = this.pos;
      System.arraycopy(var3, var4, var2, 0, var1);
      int var5 = this.pos;
      int var6 = var1 + var5;
      this.pos = var6;
      return var2;
   }

   public byte[] readByteArray(int var1) throws WireParseException {
      this.require(var1);
      byte[] var2 = new byte[var1];
      byte[] var3 = this.array;
      int var4 = this.pos;
      System.arraycopy(var3, var4, var2, 0, var1);
      int var5 = this.pos + var1;
      this.pos = var5;
      return var2;
   }

   public byte[] readCountedString() throws WireParseException {
      this.require(1);
      byte[] var1 = this.array;
      int var2 = this.pos;
      int var3 = var2 + 1;
      this.pos = var3;
      int var4 = var1[var2] & 255;
      return this.readByteArray(var4);
   }

   public int readU16() throws WireParseException {
      this.require(2);
      byte[] var1 = this.array;
      int var2 = this.pos;
      int var3 = var2 + 1;
      this.pos = var3;
      int var4 = var1[var2] & 255;
      byte[] var5 = this.array;
      int var6 = this.pos;
      int var7 = var6 + 1;
      this.pos = var7;
      int var8 = var5[var6] & 255;
      return (var4 << 8) + var8;
   }

   public long readU32() throws WireParseException {
      this.require(4);
      byte[] var1 = this.array;
      int var2 = this.pos;
      int var3 = var2 + 1;
      this.pos = var3;
      int var4 = var1[var2] & 255;
      byte[] var5 = this.array;
      int var6 = this.pos;
      int var7 = var6 + 1;
      this.pos = var7;
      int var8 = var5[var6] & 255;
      byte[] var9 = this.array;
      int var10 = this.pos;
      int var11 = var10 + 1;
      this.pos = var11;
      int var12 = var9[var10] & 255;
      byte[] var13 = this.array;
      int var14 = this.pos;
      int var15 = var14 + 1;
      this.pos = var15;
      int var16 = var13[var14] & 255;
      long var17 = (long)var4 << 24;
      long var19 = (long)(var8 << 16) + var17;
      long var21 = (long)(var12 << 8);
      long var23 = var19 + var21;
      long var25 = (long)var16;
      return var23 + var25;
   }

   public int readU8() throws WireParseException {
      this.require(1);
      byte[] var1 = this.array;
      int var2 = this.pos;
      int var3 = var2 + 1;
      this.pos = var3;
      return var1[var2] & 255;
   }

   public int remaining() {
      int var1 = this.end;
      int var2 = this.pos;
      return var1 - var2;
   }

   public void restore() {
      if(this.saved_pos < 0) {
         throw new IllegalStateException("no previous state");
      } else {
         int var1 = this.saved_pos;
         this.pos = var1;
         int var2 = this.saved_end;
         this.end = var2;
         this.saved_pos = -1;
         this.saved_end = -1;
      }
   }

   public void save() {
      int var1 = this.pos;
      this.saved_pos = var1;
      int var2 = this.end;
      this.saved_end = var2;
   }

   public void setActive(int var1) {
      int var2 = this.array.length;
      int var3 = this.pos;
      int var4 = var2 - var3;
      if(var1 > var4) {
         throw new IllegalArgumentException("cannot set active region past end of input");
      } else {
         int var5 = this.pos + var1;
         this.end = var5;
      }
   }
}
