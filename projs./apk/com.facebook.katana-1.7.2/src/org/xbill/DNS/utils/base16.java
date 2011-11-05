package org.xbill.DNS.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class base16 {

   private static final String Base16 = "0123456789ABCDEF";


   private base16() {}

   public static byte[] fromString(String var0) {
      byte var1 = 0;
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      byte[] var3 = var0.getBytes();
      int var4 = var1;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            byte[] var7 = var2.toByteArray();
            byte[] var20;
            if(var7.length % 2 != 0) {
               var20 = null;
            } else {
               var2.reset();
               DataOutputStream var8 = new DataOutputStream(var2);
               byte var9 = 0;

               while(true) {
                  int var10 = var7.length;
                  if(var9 >= var10) {
                     var20 = var2.toByteArray();
                     break;
                  }

                  char var11 = Character.toUpperCase((char)var7[var9]);
                  byte var12 = (byte)"0123456789ABCDEF".indexOf(var11);
                  int var13 = var9 + 1;
                  char var14 = Character.toUpperCase((char)var7[var13]);
                  byte var15 = (byte)"0123456789ABCDEF".indexOf(var14);
                  int var19 = (var12 << 4) + var15;

                  try {
                     var8.writeByte(var19);
                  } catch (IOException var18) {
                     ;
                  }

                  int var16 = var9 + 2;
               }
            }

            return var20;
         }

         if(!Character.isWhitespace((char)var3[var4])) {
            byte var6 = var3[var4];
            var2.write(var6);
         }

         ++var4;
      }
   }

   public static String toString(byte[] var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            byte[] var9 = var1.toByteArray();
            return new String(var9);
         }

         short var4 = (short)(var0[var2] & 255);
         byte var5 = (byte)(var4 >> 4);
         byte var6 = (byte)(var4 & 15);
         char var7 = "0123456789ABCDEF".charAt(var5);
         var1.write(var7);
         char var8 = "0123456789ABCDEF".charAt(var6);
         var1.write(var8);
         ++var2;
      }
   }
}
