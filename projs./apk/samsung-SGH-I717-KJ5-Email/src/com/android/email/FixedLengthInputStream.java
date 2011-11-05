package com.android.email;

import java.io.IOException;
import java.io.InputStream;

public class FixedLengthInputStream extends InputStream {

   private int mCount;
   private final InputStream mIn;
   private final int mLength;


   public FixedLengthInputStream(InputStream var1, int var2) {
      this.mIn = var1;
      this.mLength = var2;
   }

   public int available() throws IOException {
      int var1 = this.mLength;
      int var2 = this.mCount;
      return var1 - var2;
   }

   public int getLength() {
      return this.mLength;
   }

   public int read() throws IOException {
      int var1 = this.mCount;
      int var2 = this.mLength;
      int var4;
      if(var1 < var2) {
         int var3 = this.mCount + 1;
         this.mCount = var3;
         var4 = this.mIn.read();
      } else {
         var4 = -1;
      }

      return var4;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.mCount;
      int var5 = this.mLength;
      int var11;
      if(var4 < var5) {
         InputStream var6 = this.mIn;
         int var7 = this.mLength;
         int var8 = this.mCount;
         int var9 = Math.min(var7 - var8, var3);
         int var10 = var6.read(var1, var2, var9);
         if(var10 == -1) {
            var11 = -1;
         } else {
            int var12 = this.mCount + var10;
            this.mCount = var12;
            var11 = var10;
         }
      } else {
         var11 = -1;
      }

      return var11;
   }

   public String toString() {
      Object[] var1 = new Object[2];
      String var2 = this.mIn.toString();
      var1[0] = var2;
      Integer var3 = Integer.valueOf(this.mLength);
      var1[1] = var3;
      return String.format("FixedLengthInputStream(in=%s, length=%d)", var1);
   }
}
