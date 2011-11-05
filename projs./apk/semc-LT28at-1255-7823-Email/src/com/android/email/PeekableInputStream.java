package com.android.email;

import java.io.IOException;
import java.io.InputStream;

public class PeekableInputStream extends InputStream {

   private final InputStream mIn;
   private boolean mPeeked;
   private int mPeekedByte;


   public PeekableInputStream(InputStream var1) {
      this.mIn = var1;
   }

   public int peek() throws IOException {
      if(!this.mPeeked) {
         int var1 = this.read();
         this.mPeekedByte = var1;
         this.mPeeked = (boolean)1;
      }

      return this.mPeekedByte;
   }

   public int read() throws IOException {
      int var1;
      if(!this.mPeeked) {
         var1 = this.mIn.read();
      } else {
         this.mPeeked = (boolean)0;
         var1 = this.mPeekedByte;
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(!this.mPeeked) {
         var4 = this.mIn.read(var1, var2, var3);
      } else {
         byte var5 = (byte)this.mPeekedByte;
         var1[0] = var5;
         this.mPeeked = (boolean)0;
         InputStream var6 = this.mIn;
         int var7 = var2 + 1;
         int var8 = var3 - 1;
         int var9 = var6.read(var1, var7, var8);
         if(var9 == -1) {
            var4 = 1;
         } else {
            var4 = var9 + 1;
         }
      }

      return var4;
   }

   public String toString() {
      Object[] var1 = new Object[3];
      String var2 = this.mIn.toString();
      var1[0] = var2;
      Boolean var3 = Boolean.valueOf(this.mPeeked);
      var1[1] = var3;
      Integer var4 = Integer.valueOf(this.mPeekedByte);
      var1[2] = var4;
      return String.format("PeekableInputStream(in=%s, peeked=%b, peekedByte=%d)", var1);
   }
}
