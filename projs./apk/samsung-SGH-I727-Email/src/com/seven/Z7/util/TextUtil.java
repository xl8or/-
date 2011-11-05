package com.seven.Z7.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class TextUtil {

   private static final String HEX = "0123456789ABCDEF";
   public static final String TAG = "Utils";


   public TextUtil() {}

   private static void appendHex(StringBuffer var0, byte var1) {
      int var2 = var1 >> 4 & 15;
      char var3 = "0123456789ABCDEF".charAt(var2);
      StringBuffer var4 = var0.append(var3);
      int var5 = var1 & 15;
      char var6 = "0123456789ABCDEF".charAt(var5);
      var4.append(var6);
   }

   public static String arrayToString(Object[] var0) {
      String var1 = Arrays.toString(var0);
      int var2 = var1.length() - 1;
      return var1.substring(1, var2);
   }

   public static String getUTF16LEString(byte[] var0) {
      char[] var1 = new char[var0.length / 2];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return new String(var1);
         }

         int var4 = var2 * 2;
         int var5 = var0[var4] & 255;
         int var6 = var2 * 2 + 1;
         int var7 = (var0[var6] & 255) << 8;
         char var8 = (char)(var5 + var7);
         var1[var2] = var8;
         ++var2;
      }
   }

   public static int parseInt(String var0) {
      int var1;
      int var2;
      try {
         var1 = Integer.parseInt(var0);
      } catch (Exception var4) {
         var2 = -1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public static String streamToString(InputStream var0, String var1) throws IOException {
      byte[] var2 = new byte[0];
      byte[] var3 = new byte[1024];

      while(true) {
         int var4 = var0.read(var3);
         if(var4 == -1) {
            String var8;
            if(var1 != null) {
               try {
                  if(var1.length() > 0) {
                     var8 = new String(var2, var1);
                     return var8;
                  }
               } catch (UnsupportedEncodingException var10) {
                  ;
               }
            }

            var8 = new String(var2);
            return var8;
         }

         byte[] var5 = new byte[var2.length + var4];
         int var6 = var2.length;
         System.arraycopy(var2, 0, var5, 0, var6);
         int var7 = var2.length;
         System.arraycopy(var3, 0, var5, var7, var4);
         var2 = var5;
      }
   }

   public static byte[] toByte(String var0) {
      int var1 = var0.length() / 2;
      byte[] var2 = new byte[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         int var4 = var3 * 2;
         int var5 = var3 * 2 + 2;
         byte var6 = Integer.valueOf(var0.substring(var4, var5), 16).byteValue();
         var2[var3] = var6;
      }

      return var2;
   }

   public static String toHex(byte[] var0) {
      String var1;
      if(var0 == null) {
         var1 = "";
      } else {
         int var2 = var0.length * 2;
         StringBuffer var3 = new StringBuffer(var2);
         int var4 = 0;

         while(true) {
            int var5 = var0.length;
            if(var4 >= var5) {
               var1 = var3.toString();
               break;
            }

            byte var6 = var0[var4];
            appendHex(var3, var6);
            ++var4;
         }
      }

      return var1;
   }

   private static String urlEncode(char var0) {
      String var2;
      if((var0 < 97 || var0 > 122) && (var0 < 65 || var0 > 90) && (var0 < 48 || var0 > 57) && var0 != 45 && var0 != 95 && var0 != 46 && var0 != 33 && var0 != 126 && var0 != 42 && var0 != 39 && var0 != 40 && var0 != 41) {
         String var1 = Integer.toHexString(var0);
         if(var1.length() == 1) {
            var1 = "0" + var1;
         }

         var2 = "%" + var1;
      } else {
         var2 = "" + var0;
      }

      return var2;
   }

   public static String urlEncode(String var0) {
      char[] var1 = var0.toCharArray();
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            return var2.toString();
         }

         String var5 = urlEncode(var1[var3]);
         var2.append(var5);
         ++var3;
      }
   }
}
