package org.codehaus.jackson;

import java.util.Arrays;

public final class Base64Variant {

   public static final int BASE64_VALUE_INVALID = 255;
   public static final int BASE64_VALUE_PADDING = 254;
   static final char PADDING_CHAR_NONE;
   private final int[] _asciiToBase64;
   private final byte[] _base64ToAsciiB;
   private final char[] _base64ToAsciiC;
   final int _maxLineLength;
   final String _name;
   final char _paddingChar;
   final boolean _usesPadding;


   public Base64Variant(String var1, String var2, boolean var3, char var4, int var5) {
      int[] var6 = new int[128];
      this._asciiToBase64 = var6;
      char[] var7 = new char[64];
      this._base64ToAsciiC = var7;
      byte[] var8 = new byte[64];
      this._base64ToAsciiB = var8;
      this._name = var1;
      this._usesPadding = var3;
      this._paddingChar = var4;
      this._maxLineLength = var5;
      int var9 = var2.length();
      if(var9 != 64) {
         String var10 = "Base64Alphabet length must be exactly 64 (was " + var9 + ")";
         throw new IllegalArgumentException(var10);
      } else {
         char[] var11 = this._base64ToAsciiC;
         var2.getChars(0, var9, var11, 0);
         Arrays.fill(this._asciiToBase64, -1);

         char var13;
         for(int var12 = 0; var12 < var9; this._asciiToBase64[var13] = var12++) {
            var13 = this._base64ToAsciiC[var12];
            byte[] var14 = this._base64ToAsciiB;
            byte var15 = (byte)var13;
            var14[var12] = var15;
         }

         if(var3) {
            this._asciiToBase64[var4] = -1;
         }
      }
   }

   public Base64Variant(Base64Variant var1, String var2, int var3) {
      boolean var4 = var1._usesPadding;
      char var5 = var1._paddingChar;
      this(var1, var2, var4, var5, var3);
   }

   public Base64Variant(Base64Variant var1, String var2, boolean var3, char var4, int var5) {
      int[] var6 = new int[128];
      this._asciiToBase64 = var6;
      char[] var7 = new char[64];
      this._base64ToAsciiC = var7;
      byte[] var8 = new byte[64];
      this._base64ToAsciiB = var8;
      this._name = var2;
      byte[] var9 = var1._base64ToAsciiB;
      byte[] var10 = this._base64ToAsciiB;
      int var11 = var9.length;
      System.arraycopy(var9, 0, var10, 0, var11);
      char[] var12 = var1._base64ToAsciiC;
      char[] var13 = this._base64ToAsciiC;
      int var14 = var12.length;
      System.arraycopy(var12, 0, var13, 0, var14);
      int[] var15 = var1._asciiToBase64;
      int[] var16 = this._asciiToBase64;
      int var17 = var15.length;
      System.arraycopy(var15, 0, var16, 0, var17);
      this._usesPadding = var3;
      this._paddingChar = var4;
      this._maxLineLength = var5;
   }

   public int decodeBase64Byte(byte var1) {
      int var2;
      if(var1 <= 127) {
         var2 = this._asciiToBase64[var1];
      } else {
         var2 = -1;
      }

      return var2;
   }

   public int decodeBase64Char(char var1) {
      int var2;
      if(var1 <= 127) {
         var2 = this._asciiToBase64[var1];
      } else {
         var2 = -1;
      }

      return var2;
   }

   public int decodeBase64Char(int var1) {
      int var2;
      if(var1 <= 127) {
         var2 = this._asciiToBase64[var1];
      } else {
         var2 = -1;
      }

      return var2;
   }

   public byte encodeBase64BitsAsByte(int var1) {
      return this._base64ToAsciiB[var1];
   }

   public char encodeBase64BitsAsChar(int var1) {
      return this._base64ToAsciiC[var1];
   }

   public int encodeBase64Chunk(int var1, byte[] var2, int var3) {
      int var4 = var3 + 1;
      byte[] var5 = this._base64ToAsciiB;
      int var6 = var1 >> 18 & 63;
      byte var7 = var5[var6];
      var2[var3] = var7;
      int var8 = var4 + 1;
      byte[] var9 = this._base64ToAsciiB;
      int var10 = var1 >> 12 & 63;
      byte var11 = var9[var10];
      var2[var4] = var11;
      int var12 = var8 + 1;
      byte[] var13 = this._base64ToAsciiB;
      int var14 = var1 >> 6 & 63;
      byte var15 = var13[var14];
      var2[var8] = var15;
      int var16 = var12 + 1;
      byte[] var17 = this._base64ToAsciiB;
      int var18 = var1 & 63;
      byte var19 = var17[var18];
      var2[var12] = var19;
      return var16;
   }

   public int encodeBase64Chunk(int var1, char[] var2, int var3) {
      int var4 = var3 + 1;
      char[] var5 = this._base64ToAsciiC;
      int var6 = var1 >> 18 & 63;
      char var7 = var5[var6];
      var2[var3] = var7;
      int var8 = var4 + 1;
      char[] var9 = this._base64ToAsciiC;
      int var10 = var1 >> 12 & 63;
      char var11 = var9[var10];
      var2[var4] = var11;
      int var12 = var8 + 1;
      char[] var13 = this._base64ToAsciiC;
      int var14 = var1 >> 6 & 63;
      char var15 = var13[var14];
      var2[var8] = var15;
      int var16 = var12 + 1;
      char[] var17 = this._base64ToAsciiC;
      int var18 = var1 & 63;
      char var19 = var17[var18];
      var2[var12] = var19;
      return var16;
   }

   public void encodeBase64Chunk(StringBuilder var1, int var2) {
      char[] var3 = this._base64ToAsciiC;
      int var4 = var2 >> 18 & 63;
      char var5 = var3[var4];
      var1.append(var5);
      char[] var7 = this._base64ToAsciiC;
      int var8 = var2 >> 12 & 63;
      char var9 = var7[var8];
      var1.append(var9);
      char[] var11 = this._base64ToAsciiC;
      int var12 = var2 >> 6 & 63;
      char var13 = var11[var12];
      var1.append(var13);
      char[] var15 = this._base64ToAsciiC;
      int var16 = var2 & 63;
      char var17 = var15[var16];
      var1.append(var17);
   }

   public int encodeBase64Partial(int var1, int var2, byte[] var3, int var4) {
      int var5 = var4 + 1;
      byte[] var6 = this._base64ToAsciiB;
      int var7 = var1 >> 18 & 63;
      byte var8 = var6[var7];
      var3[var4] = var8;
      int var9 = var5 + 1;
      byte[] var10 = this._base64ToAsciiB;
      int var11 = var1 >> 12 & 63;
      byte var12 = var10[var11];
      var3[var5] = var12;
      int var19;
      if(this._usesPadding) {
         byte var13 = (byte)this._paddingChar;
         int var14 = var9 + 1;
         byte var17;
         if(var2 == 2) {
            byte[] var15 = this._base64ToAsciiB;
            int var16 = var1 >> 6 & 63;
            var17 = var15[var16];
         } else {
            var17 = var13;
         }

         var3[var9] = var17;
         int var18 = var14 + 1;
         var3[var14] = var13;
         var19 = var18;
      } else if(var2 == 2) {
         var19 = var9 + 1;
         byte[] var20 = this._base64ToAsciiB;
         int var21 = var1 >> 6 & 63;
         byte var22 = var20[var21];
         var3[var9] = var22;
      } else {
         var19 = var9;
      }

      return var19;
   }

   public int encodeBase64Partial(int var1, int var2, char[] var3, int var4) {
      int var5 = var4 + 1;
      char[] var6 = this._base64ToAsciiC;
      int var7 = var1 >> 18 & 63;
      char var8 = var6[var7];
      var3[var4] = var8;
      int var9 = var5 + 1;
      char[] var10 = this._base64ToAsciiC;
      int var11 = var1 >> 12 & 63;
      char var12 = var10[var11];
      var3[var5] = var12;
      int var19;
      if(this._usesPadding) {
         int var13 = var9 + 1;
         char var16;
         if(var2 == 2) {
            char[] var14 = this._base64ToAsciiC;
            int var15 = var1 >> 6 & 63;
            var16 = var14[var15];
         } else {
            var16 = this._paddingChar;
         }

         var3[var9] = var16;
         int var17 = var13 + 1;
         char var18 = this._paddingChar;
         var3[var13] = var18;
         var19 = var17;
      } else if(var2 == 2) {
         var19 = var9 + 1;
         char[] var20 = this._base64ToAsciiC;
         int var21 = var1 >> 6 & 63;
         char var22 = var20[var21];
         var3[var9] = var22;
      } else {
         var19 = var9;
      }

      return var19;
   }

   public void encodeBase64Partial(StringBuilder var1, int var2, int var3) {
      char[] var4 = this._base64ToAsciiC;
      int var5 = var2 >> 18 & 63;
      char var6 = var4[var5];
      var1.append(var6);
      char[] var8 = this._base64ToAsciiC;
      int var9 = var2 >> 12 & 63;
      char var10 = var8[var9];
      var1.append(var10);
      if(this._usesPadding) {
         char var14;
         if(var3 == 2) {
            char[] var12 = this._base64ToAsciiC;
            int var13 = var2 >> 6 & 63;
            var14 = var12[var13];
         } else {
            var14 = this._paddingChar;
         }

         var1.append(var14);
         char var16 = this._paddingChar;
         var1.append(var16);
      } else if(var3 == 2) {
         char[] var18 = this._base64ToAsciiC;
         int var19 = var2 >> 6 & 63;
         char var20 = var18[var19];
         var1.append(var20);
      }
   }

   public int getMaxLineLength() {
      return this._maxLineLength;
   }

   public String getName() {
      return this._name;
   }

   public byte getPaddingByte() {
      return (byte)this._paddingChar;
   }

   public char getPaddingChar() {
      return this._paddingChar;
   }

   public String toString() {
      return this._name;
   }

   public boolean usesPadding() {
      return this._usesPadding;
   }

   public boolean usesPaddingChar(char var1) {
      char var2 = this._paddingChar;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean usesPaddingChar(int var1) {
      char var2 = this._paddingChar;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
