package com.htc.android.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ByteString {

   public static final ByteString CRLF = new ByteString("\r\n");
   int mLength;
   int mOffset;
   byte[] mStorage;


   public ByteString(String var1) {
      byte[] var2 = var1.getBytes();
      this(var2);
   }

   public ByteString(List<ByteString> var1, int var2, int var3) {
      int var4 = 0;

      for(int var5 = var2; var5 < var3; ++var5) {
         int var6 = ((ByteString)var1.get(var5)).length();
         var4 += var6;
      }

      byte[] var7 = new byte[var4];
      this.mStorage = var7;
      this.mLength = var4;
      this.mOffset = 0;
      int var8 = 0;

      for(int var9 = var2; var9 < var3; ++var9) {
         ByteString var10 = (ByteString)var1.get(var9);
         byte[] var11 = var10.mStorage;
         int var12 = var10.mOffset;
         byte[] var13 = this.mStorage;
         int var14 = var10.mLength;
         System.arraycopy(var11, var12, var13, var8, var14);
         int var15 = var10.mLength;
         var8 += var15;
      }

   }

   public ByteString(byte[] var1) {
      this.mStorage = var1;
      this.mOffset = 0;
      int var2 = var1.length;
      this.mLength = var2;
   }

   public ByteString(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      this.mStorage = var4;
      byte[] var5 = this.mStorage;
      System.arraycopy(var1, var2, var5, 0, var3);
      this.mOffset = 0;
      this.mLength = var3;
   }

   public static native void arraycopy(Object var0, int var1, Object var2, int var3, int var4);

   private final void growIfNeeded(int var1) {
      int var2 = this.mOffset + var1;
      int var3 = this.mStorage.length;
      if(var2 >= var3) {
         byte[] var4 = new byte[var1 * 3 / 2];
         byte[] var5 = this.mStorage;
         int var6 = this.mOffset;
         int var7 = this.mLength;
         System.arraycopy(var5, var6, var4, 0, var7);
         this.mStorage = var4;
         this.mOffset = 0;
      }
   }

   public final byte byteAt(int var1) {
      byte[] var2 = this.mStorage;
      int var3 = this.mOffset + var1;
      return var2[var3];
   }

   public final ByteString collapseWhitespaceLeft() {
      int var1 = 0;

      while(true) {
         int var2 = this.mLength;
         if(var1 >= var2) {
            break;
         }

         byte[] var3 = this.mStorage;
         int var4 = this.mOffset + var1;
         if(var3[var4] > 32) {
            break;
         }

         int var5 = 0;
         int var6 = var1;

         while(true) {
            int var7 = var1 + 1;
            int var8 = this.mLength;
            if(var7 >= var8) {
               break;
            }

            byte[] var9 = this.mStorage;
            int var10 = this.mOffset + var1 + 1;
            if(var9[var10] > 32) {
               break;
            }

            ++var5;
            ++var1;
         }

         if(var5 > 0) {
            this.delete(var6, var5);
            break;
         }

         ++var1;
      }

      return this;
   }

   public final ByteString concat(ByteString var1) {
      if(var1.mLength != 0) {
         int var2 = this.mLength;
         int var3 = var1.mLength;
         int var4 = var2 + var3;
         this.growIfNeeded(var4);
         byte[] var5 = var1.mStorage;
         int var6 = var1.mOffset;
         byte[] var7 = this.mStorage;
         int var8 = this.mOffset;
         int var9 = this.mLength;
         int var10 = var8 + var9;
         int var11 = var1.mLength;
         System.arraycopy(var5, var6, var7, var10, var11);
         int var12 = this.mLength;
         int var13 = var1.mLength;
         int var14 = var12 + var13;
         this.mLength = var14;
      }

      return this;
   }

   public final void delete(int var1) {
      this.delete(var1, 1);
   }

   public final void delete(int var1, int var2) {
      if(var2 >= 1) {
         if(var1 >= 0) {
            int var3 = var1 + var2;
            int var4 = this.mLength;
            if(var3 <= var4) {
               if(var1 == 0) {
                  int var5 = this.mOffset + var2;
                  this.mOffset = var5;
               } else {
                  int var7 = var1 + var2;
                  int var8 = this.mLength;
                  if(var7 != var8) {
                     byte[] var9 = this.mStorage;
                     int var10 = this.mOffset + var1 + var2;
                     byte[] var11 = this.mStorage;
                     int var12 = this.mOffset + var1;
                     int var13 = this.mLength;
                     int var14 = var1 + var2;
                     int var15 = var13 - var14;
                     System.arraycopy(var9, var10, var11, var12, var15);
                  }
               }

               int var6 = this.mLength - var2;
               this.mLength = var6;
               return;
            }
         }

         throw new IndexOutOfBoundsException();
      }
   }

   public final boolean endsWith(ByteString var1) {
      int var2 = var1.mLength;
      int var3 = this.mLength;
      byte var4;
      if(var2 > var3) {
         var4 = 0;
      } else if(var1.mLength == 0) {
         var4 = 1;
      } else if(var1 == this) {
         var4 = 1;
      } else {
         int var5 = this.mLength;
         int var6 = var1.mLength;
         int var7 = var5 - var6;
         int var8 = var1.mLength;
         var4 = this.regionMatches(var7, var1, 0, var8);
      }

      return (boolean)var4;
   }

   public final boolean equals(Object var1) {
      boolean var2;
      if(var1 != null && var1 instanceof ByteString) {
         ByteString var3 = (ByteString)var1;
         int var4 = this.mLength;
         int var5 = var3.mLength;
         if(var4 != var5) {
            var2 = false;
         } else {
            byte[] var6 = var3.mStorage;
            int var7 = var3.mOffset;
            byte[] var8 = this.mStorage;
            int var9 = this.mOffset;
            int var10 = var9 + var4;
            int var11 = var9;
            int var12 = var7;

            while(true) {
               if(var11 >= var10) {
                  var2 = true;
                  break;
               }

               int var13 = var11 + 1;
               byte var14 = var8[var11];
               int var15 = var12 + 1;
               byte var16 = var6[var12];
               if(var14 != var16) {
                  var2 = false;
                  break;
               }

               var11 = var13;
               var12 = var15;
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public final int getBytes(int var1, byte[] var2, int var3, int var4) {
      int var5 = this.mLength;
      int var6 = var1 + var4;
      int var7 = var5 - var6;
      int var8;
      if(var7 < 0) {
         var4 += var7;
         var8 = -var4;
      } else if(var7 == 0) {
         var8 = 0;
      } else {
         var8 = this.mLength - var7;
      }

      byte[] var9 = this.mStorage;
      int var10 = this.mOffset + var1;
      System.arraycopy(var9, var10, var2, var3, var4);
      return var8;
   }

   public final int hashCode() {
      int var1 = 0;
      int var2 = this.mLength;
      byte[] var3 = this.mStorage;
      int var4 = this.mOffset;

      for(int var5 = 0; var5 < var2; ++var5) {
         int var6 = var1 * 31;
         int var7 = var4 + var5;
         byte var8 = var3[var7];
         var1 = var6 + var8;
      }

      return var1;
   }

   public final int indexOf(int var1) {
      return this.indexOf(var1, 0);
   }

   public final int indexOf(int var1, int var2) {
      if(var2 < 0) {
         var2 = 0;
      }

      int var3 = this.mLength;
      int var4;
      if(var2 >= var3) {
         var4 = -1;
      } else {
         int var5 = this.mOffset + var2;
         int var6 = this.mOffset;
         int var7 = this.mLength;
         int var8 = var6 + var7;
         byte[] var9 = this.mStorage;
         int var10 = var5;

         while(true) {
            if(var10 >= var8) {
               var4 = -1;
               break;
            }

            if(var9[var10] == var1) {
               int var11 = this.mOffset;
               var4 = var10 - var11;
               break;
            }

            ++var10;
         }
      }

      return var4;
   }

   public final int lastIndexOf(int var1, int var2) {
      int var3;
      if(var2 < 0) {
         var3 = -1;
      } else {
         int var4 = this.mLength;
         if(var2 >= var4) {
            var2 = this.mLength - 1;
         }

         int var5 = this.mOffset + var2;

         while(true) {
            int var6 = this.mOffset;
            if(var5 < var6) {
               var3 = -1;
               break;
            }

            if(this.mStorage[var5] == var1) {
               int var7 = this.mOffset;
               var3 = var5 - var7;
               break;
            }

            var5 += -1;
         }
      }

      return var3;
   }

   public final int length() {
      return this.mLength;
   }

   public final int parseInt(int var1) {
      int var2 = this.mLength;
      return this.parseInt(var1, var2);
   }

   public final int parseInt(int var1, int var2) {
      int var3 = 0;
      byte var4 = 1;
      int var5 = var1;
      byte[] var6 = this.mStorage;
      int var7 = this.mOffset;

      int var8;
      for(var8 = var2; var5 < var8; ++var5) {
         int var9 = var7 + var5;
         if(var6[var9] > 32) {
            break;
         }
      }

      int var10;
      if(var5 == var8) {
         var10 = 0;
      } else {
         int var11 = var7 + var5;
         if(var6[var11] == 45) {
            var4 = -1;
            ++var5;
         }

         int var12;
         if(var4 > 0) {
            var12 = Integer.MAX_VALUE / 10;
         } else {
            var12 = Integer.MIN_VALUE / 10;
         }

         while(var5 < var8) {
            int var13 = var7 + var5;
            int var14 = Character.digit((char)var6[var13], 10);
            if(-1 == var14) {
               while(var5 < var8) {
                  int var15 = var7 + var5;
                  if(var6[var15] > 32) {
                     break;
                  }

                  ++var5;
               }

               if(var5 < var8) {
                  StringBuilder var16 = (new StringBuilder()).append("Invalid character ");
                  byte var17 = this.byteAt(var5);
                  String var18 = var16.append(var17).toString();
                  throw new NumberFormatException(var18);
               }
               break;
            }

            if(var4 > 0 && var3 > var12 || var4 < 0 && var3 < var12) {
               throw new NumberFormatException("Integer rollover");
            }

            int var19 = var3 * 10;
            if(var4 > 0) {
               int var20 = Integer.MAX_VALUE - var14;
               if(var19 > var20) {
                  throw new NumberFormatException("Integer rollover");
               }
            }

            if(var4 < 0) {
               int var21 = Integer.MIN_VALUE + var14;
               if(var19 < var21) {
                  throw new NumberFormatException("Integer rollover");
               }
            }

            int var22 = var14 * var4;
            var3 = var19 + var22;
            ++var5;
         }

         var10 = var3;
      }

      return var10;
   }

   public final boolean regionMatches(int var1, ByteString var2, int var3, int var4) {
      boolean var5;
      if((var1 | var3) < 0) {
         var5 = false;
      } else {
         int var6 = var1 + var4;
         int var7 = this.mLength;
         if(var6 > var7) {
            var5 = false;
         } else {
            int var8 = var3 + var4;
            int var9 = var2.mLength;
            if(var8 > var9) {
               var5 = false;
            } else {
               byte[] var10 = this.mStorage;
               int var11 = this.mOffset + var1;
               byte[] var12 = var2.mStorage;
               int var13 = var2.mOffset + var3;
               int var14 = var4;
               int var15 = 0;
               int var16 = var13;
               int var17 = var11;

               while(true) {
                  if(var15 >= var14) {
                     var5 = true;
                     break;
                  }

                  int var18 = var17 + 1;
                  byte var19 = var10[var17];
                  int var20 = var16 + 1;
                  byte var21 = var12[var16];
                  if(var19 != var21) {
                     var5 = false;
                     break;
                  }

                  ++var15;
                  var16 = var20;
                  var17 = var18;
               }
            }
         }
      }

      return var5;
   }

   public final boolean regionMatches(boolean var1, int var2, ByteString var3, int var4, int var5) {
      boolean var11;
      if(!var1) {
         var11 = this.regionMatches(var2, var3, var4, var5);
      } else if((var2 | var4) < 0) {
         var11 = false;
      } else {
         int var12 = var2 + var5;
         int var13 = this.mLength;
         if(var12 > var13) {
            var11 = false;
         } else {
            int var14 = var4 + var5;
            int var15 = var3.mLength;
            if(var14 > var15) {
               var11 = false;
            } else {
               byte[] var16 = this.mStorage;
               int var17 = this.mOffset + var2;
               byte[] var18 = var3.mStorage;
               int var19 = var3.mOffset + var4;
               int var20 = 0;
               int var21 = var19;
               int var22 = var17;

               while(true) {
                  if(var20 >= var5) {
                     var11 = true;
                     break;
                  }

                  int var25 = var22 + 1;
                  char var26 = (char)var16[var22];
                  int var27 = var21 + 1;
                  char var28 = (char)var18[var21];
                  if(var26 < 127 && var28 < 127) {
                     if(var26 >= 65 && var26 <= 90) {
                        var26 = (char)(var26 + 32);
                     }

                     if(var28 >= 65 && var28 <= 90) {
                        var28 = (char)(var28 + 32);
                     }

                     if(var26 != var28) {
                        var11 = false;
                        break;
                     }
                  } else {
                     char var29 = Character.toLowerCase(Character.toUpperCase(var26));
                     char var30 = Character.toLowerCase(Character.toUpperCase(var28));
                     if(var29 != var30) {
                        var11 = false;
                        break;
                     }
                  }

                  ++var20;
                  var21 = var27;
                  var22 = var25;
               }
            }
         }
      }

      return var11;
   }

   public final void removeComments() {
      boolean var1 = false;
      int var2 = 0;

      while(true) {
         int var3 = this.mLength;
         if(var2 >= var3) {
            return;
         }

         byte[] var4 = this.mStorage;
         int var5 = this.mOffset + var2;
         switch(var4[var5]) {
         case 92:
            if(!var1) {
               var1 = true;
            } else {
               var1 = false;
            }
            break;
         default:
            var1 = false;
         }

         ++var2;
      }
   }

   public final void set(int var1, int var2) {
      byte[] var3 = this.mStorage;
      int var4 = this.mOffset + var1;
      byte var5 = (byte)(var2 & 255);
      var3[var4] = var5;
   }

   public final boolean startsWith(ByteString var1) {
      byte var2;
      if(var1.mLength == 0) {
         var2 = 1;
      } else if(var1 == this) {
         var2 = 1;
      } else {
         int var3 = var1.mLength;
         var2 = this.regionMatches(0, var1, 0, var3);
      }

      return (boolean)var2;
   }

   public final ByteString substring(int var1) {
      int var2 = this.mLength;
      return this.substring(var1, var2);
   }

   public final ByteString substring(int var1, int var2) {
      byte[] var3 = this.mStorage;
      int var4 = this.mOffset + var1;
      int var5 = var2 - var1;
      return new ByteString(var3, var4, var5);
   }

   public final ByteString toLowerCase() {
      ByteString var1 = null;
      int var2 = 0;

      while(true) {
         int var3 = this.mLength;
         if(var2 >= var3) {
            ByteString var14;
            if(var1 == null) {
               var14 = this;
            } else {
               var14 = var1;
            }

            return var14;
         }

         byte[] var4 = this.mStorage;
         int var5 = this.mOffset + var2;
         byte var6 = var4[var5];
         int var7 = var6 & 255;
         if(var7 <= 127) {
            if(var7 >= 65 && var7 <= 90) {
               var7 += 32;
            }
         } else {
            var7 = (byte)Character.toLowerCase(var7);
         }

         if(var7 != var6) {
            if(var1 == null) {
               byte[] var8 = this.mStorage;
               int var9 = this.mOffset;
               int var10 = this.mLength;
               var1 = new ByteString(var8, var9, var10);
            }

            byte[] var11 = var1.mStorage;
            int var12 = var1.mOffset + var2;
            byte var13 = (byte)var7;
            var11[var12] = var13;
         }

         ++var2;
      }
   }

   public final String toString() {
      byte[] var1 = this.mStorage;
      int var2 = this.mOffset;
      int var3 = this.mLength;
      return new String(var1, var2, var3);
   }

   public final String toString(int var1) {
      int var2 = this.mLength;
      return this.toString(var1, var2);
   }

   public final String toString(int var1, int var2) {
      int var3 = this.mOffset + var2;
      byte[] var4 = this.mStorage;
      int var5 = this.mOffset;

      for(int var6 = var5 + var1; var6 < var3 && var4[var6] <= 32; ++var6) {
         ++var1;
      }

      int var7 = var5 + var1;

      for(int var8 = var3 - 1; var8 > var7 && var4[var8] <= 32; var8 += -1) {
         var2 += -1;
      }

      String var9;
      if(var1 == var2) {
         var9 = "";
      } else {
         int var10 = var2 - var1;
         var9 = new String(var4, var1, var10);
      }

      return var9;
   }

   public final String toString(String var1) {
      String var5;
      try {
         byte[] var2 = this.mStorage;
         int var3 = this.mOffset;
         int var4 = this.mLength;
         var5 = new String(var2, var3, var4, var1);
      } catch (UnsupportedEncodingException var7) {
         var5 = this.toString();
      }

      return var5;
   }

   public final ByteString trim() {
      ByteString var1 = this.trimLeft();
      ByteString var2 = this.trimRight();
      return this;
   }

   public final ByteString trimCRLFRight() {
      int var1 = this.mOffset;
      int var2 = this.mLength;
      int var3 = var1 + var2 - 1;
      byte[] var4 = this.mStorage;

      while(true) {
         int var5 = this.mOffset;
         if(var3 <= var5 || var4[var3] != 19 && var4[var3] != 16) {
            return this;
         }

         var3 += -1;
         int var6 = this.mLength - 1;
         this.mLength = var6;
      }
   }

   public final ByteString trimHeader() {
      ByteString var1 = this.trimLeftWithoutSpace();
      ByteString var2 = this.trimRightWithoutSpace();
      return this;
   }

   public final ByteString trimLeft() {
      int var1 = this.mOffset;
      int var2 = this.mLength;
      int var3 = var1 + var2;
      int var4 = this.mOffset;

      int var7;
      for(byte[] var5 = this.mStorage; var4 < var3 && var5[var4] <= 32; this.mLength = var7) {
         ++var4;
         int var6 = this.mOffset + 1;
         this.mOffset = var6;
         var7 = this.mLength - 1;
      }

      return this;
   }

   public final ByteString trimLeftWithoutSpace() {
      int var1 = this.mOffset;
      int var2 = this.mLength;
      int var3 = var1 + var2;
      int var4 = this.mOffset;

      int var7;
      for(byte[] var5 = this.mStorage; var4 < var3 && var5[var4] < 32; this.mLength = var7) {
         ++var4;
         int var6 = this.mOffset + 1;
         this.mOffset = var6;
         var7 = this.mLength - 1;
      }

      return this;
   }

   public final ByteString trimRight() {
      int var1 = this.mOffset;
      int var2 = this.mLength;
      int var3 = var1 + var2 - 1;
      byte[] var4 = this.mStorage;

      while(true) {
         int var5 = this.mOffset;
         if(var3 <= var5 || var4[var3] > 32) {
            return this;
         }

         var3 += -1;
         int var6 = this.mLength - 1;
         this.mLength = var6;
      }
   }

   public final ByteString trimRightWithoutSpace() {
      int var1 = this.mOffset;
      int var2 = this.mLength;
      int var3 = var1 + var2 - 1;
      byte[] var4 = this.mStorage;

      while(true) {
         int var5 = this.mOffset;
         if(var3 <= var5 || var4[var3] >= 32) {
            return this;
         }

         var3 += -1;
         int var6 = this.mLength - 1;
         this.mLength = var6;
      }
   }

   public final void unescape() {
      boolean var1 = false;
      int var2 = 0;

      while(true) {
         int var3 = this.mLength;
         if(var2 >= var3) {
            return;
         }

         byte[] var4 = this.mStorage;
         int var5 = this.mOffset + var2;
         if(var4[var5] != 92) {
            var1 = false;
         } else if(var1) {
            var1 = false;
         } else {
            var1 = true;
            this.delete(var2);
         }

         ++var2;
      }
   }

   public final boolean unfoldLine(ByteString var1) {
      boolean var2;
      if(var1.mLength < 3) {
         var2 = false;
      } else {
         byte[] var3 = var1.mStorage;
         int var4 = var1.mOffset;
         byte var5 = var3[var4];
         if(var5 != 32 && var5 != 9) {
            var2 = false;
         } else {
            ByteString var6 = this.trimRight();
            ByteString var7 = var1.collapseWhitespaceLeft();
            this.concat(var1);
            var2 = true;
         }
      }

      return var2;
   }
}
