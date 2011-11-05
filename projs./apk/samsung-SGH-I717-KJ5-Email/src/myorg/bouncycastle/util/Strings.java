package myorg.bouncycastle.util;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

public final class Strings {

   public Strings() {}

   public static String fromUTF8ByteArray(byte[] var0) {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var1 >= var3) {
            char[] var4 = new char[var2];
            int var5 = 0;
            int var6 = 0;

            while(true) {
               int var7 = var0.length;
               if(var5 >= var7) {
                  return new String(var4);
               }

               char var23;
               if((var0[var5] & 240) == 240) {
                  int var8 = (var0[var5] & 3) << 18;
                  int var9 = var5 + 1;
                  int var10 = (var0[var9] & 63) << 12;
                  int var11 = var8 | var10;
                  int var12 = var5 + 2;
                  int var13 = (var0[var12] & 63) << 6;
                  int var14 = var11 | var13;
                  int var15 = var5 + 3;
                  int var16 = var0[var15] & 63;
                  int var17 = (var14 | var16) - 65536;
                  int var18 = var17 >> 10;
                  char var19 = (char)('\ud800' | var18);
                  int var20 = var17 & 1023;
                  char var21 = (char)('\udc00' | var20);
                  int var22 = var6 + 1;
                  var4[var6] = var19;
                  var23 = var21;
                  var5 += 4;
                  var6 = var22;
               } else if((var0[var5] & 224) == 224) {
                  int var26 = (var0[var5] & 15) << 12;
                  int var27 = var5 + 1;
                  int var28 = (var0[var27] & 63) << 6;
                  int var29 = var26 | var28;
                  int var30 = var5 + 2;
                  int var31 = var0[var30] & 63;
                  var23 = (char)(var29 | var31);
                  var5 += 3;
               } else if((var0[var5] & 208) == 208) {
                  int var32 = (var0[var5] & 31) << 6;
                  int var33 = var5 + 1;
                  int var34 = var0[var33] & 63;
                  var23 = (char)(var32 | var34);
                  var5 += 2;
               } else if((var0[var5] & 192) == 192) {
                  int var35 = (var0[var5] & 31) << 6;
                  int var36 = var5 + 1;
                  int var37 = var0[var36] & 63;
                  var23 = (char)(var35 | var37);
                  var5 += 2;
               } else {
                  var23 = (char)(var0[var5] & 255);
                  ++var5;
               }

               int var24 = var6 + 1;
               var4[var6] = var23;
            }
         }

         ++var2;
         if((var0[var1] & 240) == 240) {
            ++var2;
            var1 += 4;
         } else if((var0[var1] & 224) == 224) {
            var1 += 3;
         } else if((var0[var1] & 192) == 192) {
            var1 += 2;
         } else {
            ++var1;
         }
      }
   }

   public static String[] split(String var0, char var1) {
      Vector var2 = new Vector();
      boolean var3 = true;

      while(var3) {
         int var4 = var0.indexOf(var1);
         if(var4 > 0) {
            String var5 = var0.substring(0, var4);
            var2.addElement(var5);
            int var6 = var4 + 1;
            var0 = var0.substring(var6);
         } else {
            var3 = false;
            var2.addElement(var0);
         }
      }

      String[] var7 = new String[var2.size()];
      int var8 = 0;

      while(true) {
         int var9 = var7.length;
         if(var8 == var9) {
            return var7;
         }

         String var10 = (String)var2.elementAt(var8);
         var7[var8] = var10;
         ++var8;
      }
   }

   public static byte[] toByteArray(String var0) {
      byte[] var1 = new byte[var0.length()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         byte var4 = (byte)var0.charAt(var2);
         var1[var2] = var4;
         ++var2;
      }
   }

   public static String toLowerCase(String var0) {
      boolean var1 = false;
      char[] var2 = var0.toCharArray();
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            String var7;
            if(var1) {
               var7 = new String(var2);
            } else {
               var7 = var0;
            }

            return var7;
         }

         char var5 = var2[var3];
         if(65 <= var5 && 90 >= var5) {
            var1 = true;
            char var6 = (char)(var5 - 65 + 97);
            var2[var3] = var6;
         }

         ++var3;
      }
   }

   public static byte[] toUTF8ByteArray(String var0) {
      return toUTF8ByteArray(var0.toCharArray());
   }

   public static byte[] toUTF8ByteArray(char[] var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      char[] var2 = var0;
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            return var1.toByteArray();
         }

         char var5 = var2[var3];
         if(var5 < 128) {
            var1.write(var5);
         } else if(var5 < 2048) {
            int var6 = var5 >> 6 | 192;
            var1.write(var6);
            int var7 = var5 & 63 | 128;
            var1.write(var7);
         } else if(var5 >= '\ud800' && var5 <= '\udfff') {
            int var8 = var3 + 1;
            int var9 = var2.length;
            if(var8 >= var9) {
               throw new IllegalStateException("invalid UTF-16 codepoint");
            }

            ++var3;
            char var11 = var2[var3];
            if(var5 > '\udbff') {
               throw new IllegalStateException("invalid UTF-16 codepoint");
            }

            int var12 = (var5 & 1023) << 10;
            int var13 = var11 & 1023;
            int var14 = (var12 | var13) + 65536;
            int var15 = var14 >> 18 | 240;
            var1.write(var15);
            int var16 = var14 >> 12 & 63 | 128;
            var1.write(var16);
            int var17 = var14 >> 6 & 63 | 128;
            var1.write(var17);
            int var18 = var14 & 63 | 128;
            var1.write(var18);
         } else {
            int var19 = var5 >> 12 | 224;
            var1.write(var19);
            int var20 = var5 >> 6 & 63 | 128;
            var1.write(var20);
            int var21 = var5 & 63 | 128;
            var1.write(var21);
         }

         ++var3;
      }
   }

   public static String toUpperCase(String var0) {
      boolean var1 = false;
      char[] var2 = var0.toCharArray();
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            String var7;
            if(var1) {
               var7 = new String(var2);
            } else {
               var7 = var0;
            }

            return var7;
         }

         char var5 = var2[var3];
         if(97 <= var5 && 122 >= var5) {
            var1 = true;
            char var6 = (char)(var5 - 97 + 65);
            var2[var3] = var6;
         }

         ++var3;
      }
   }
}
