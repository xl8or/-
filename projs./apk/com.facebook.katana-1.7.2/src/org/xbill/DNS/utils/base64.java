package org.xbill.DNS.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class base64 {

   private static final String Base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";


   private base64() {}

   public static String formatString(byte[] var0, int var1, String var2, boolean var3) {
      String var4 = toString(var0);
      StringBuffer var5 = new StringBuffer();
      int var6 = 0;

      while(true) {
         int var7 = var4.length();
         if(var6 >= var7) {
            return var5.toString();
         }

         var5.append(var2);
         int var9 = var6 + var1;
         int var10 = var4.length();
         if(var9 >= var10) {
            String var11 = var4.substring(var6);
            var5.append(var11);
            if(var3) {
               StringBuffer var13 = var5.append(" )");
            }
         } else {
            int var14 = var6 + var1;
            String var15 = var4.substring(var6, var14);
            var5.append(var15);
            StringBuffer var17 = var5.append("\n");
         }

         var6 += var1;
      }
   }

   public static byte[] fromString(String var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = var0.getBytes();
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            byte[] var6 = var1.toByteArray();
            byte[] var31;
            if(var6.length % 4 != 0) {
               var31 = null;
            } else {
               var1.reset();
               DataOutputStream var7 = new DataOutputStream(var1);
               int var8 = 0;

               while(true) {
                  int var9 = (var6.length + 3) / 4;
                  if(var8 >= var9) {
                     var31 = var1.toByteArray();
                     break;
                  }

                  boolean var10 = true;
                  boolean var11 = true;

                  for(int var12 = 0; var12 < 4; ++var12) {
                     int var13 = var8 * 4 + var12;
                     byte var14 = var6[var13];
                     short var15 = (short)"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".indexOf(var14);
                     ((Object[])var10)[var12] = (boolean)var15;
                  }

                  int var16 = ((Object[])var10)[0] << 2;
                  int var17 = ((Object[])var10)[1] >> 4;
                  short var18 = (short)(var16 + var17);
                  ((Object[])var11)[0] = (boolean)var18;
                  if(((Object[])var10)[2] == 64) {
                     ((Object[])var11)[2] = true;
                     ((Object[])var11)[1] = true;
                     if((((Object[])var10)[1] & 15) != 0) {
                        var31 = null;
                        break;
                     }
                  } else if(((Object[])var10)[3] == 64) {
                     int var19 = ((Object[])var10)[1] << 4;
                     int var20 = ((Object[])var10)[2] >> 2;
                     short var21 = (short)(var19 + var20 & 255);
                     ((Object[])var11)[1] = (boolean)var21;
                     ((Object[])var11)[2] = true;
                     if((((Object[])var10)[2] & 3) != 0) {
                        var31 = null;
                        break;
                     }
                  } else {
                     int var22 = ((Object[])var10)[1] << 4;
                     int var23 = ((Object[])var10)[2] >> 2;
                     short var24 = (short)(var22 + var23 & 255);
                     ((Object[])var11)[1] = (boolean)var24;
                     int var25 = ((Object[])var10)[2] << 6;
                     short var26 = (short)(((Object[])var10)[3] + var25 & 255);
                     ((Object[])var11)[2] = (boolean)var26;
                  }

                  for(int var27 = 0; var27 < 3; ++var27) {
                     try {
                        if(((Object[])var11)[var27] >= 0) {
                           byte var28 = ((Object[])var11)[var27];
                           var7.writeByte(var28);
                        }
                     } catch (IOException var30) {
                        break;
                     }
                  }

                  ++var8;
               }
            }

            return var31;
         }

         if(!Character.isWhitespace((char)var2[var3])) {
            byte var5 = var2[var3];
            var1.write(var5);
         }

         ++var3;
      }
   }

   public static String toString(byte[] var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      int var2 = 0;

      while(true) {
         int var3 = (var0.length + 2) / 3;
         if(var2 >= var3) {
            byte[] var24 = var1.toByteArray();
            return new String(var24);
         }

         boolean var4 = true;
         boolean var5 = true;

         for(int var6 = 0; var6 < 3; ++var6) {
            int var7 = var2 * 3 + var6;
            int var8 = var0.length;
            if(var7 < var8) {
               int var9 = var2 * 3 + var6;
               short var10 = (short)(var0[var9] & 255);
               ((Object[])var4)[var6] = (boolean)var10;
            } else {
               ((Object[])var4)[var6] = true;
            }
         }

         short var11 = (short)(((Object[])var4)[0] >> 2);
         ((Object[])var5)[0] = (boolean)var11;
         if(((Object[])var4)[1] == -1) {
            short var12 = (short)((((Object[])var4)[0] & 3) << 4);
            ((Object[])var5)[1] = (boolean)var12;
         } else {
            int var16 = (((Object[])var4)[0] & 3) << 4;
            int var17 = ((Object[])var4)[1] >> 4;
            short var18 = (short)(var16 + var17);
            ((Object[])var5)[1] = (boolean)var18;
         }

         if(((Object[])var4)[1] == -1) {
            ((Object[])var5)[3] = true;
            ((Object[])var5)[2] = true;
         } else if(((Object[])var4)[2] == -1) {
            short var19 = (short)((((Object[])var4)[1] & 15) << 2);
            ((Object[])var5)[2] = (boolean)var19;
            ((Object[])var5)[3] = true;
         } else {
            int var20 = (((Object[])var4)[1] & 15) << 2;
            int var21 = ((Object[])var4)[2] >> 6;
            short var22 = (short)(var20 + var21);
            ((Object[])var5)[2] = (boolean)var22;
            short var23 = (short)(((Object[])var4)[2] & 63);
            ((Object[])var5)[3] = (boolean)var23;
         }

         for(int var13 = 0; var13 < 4; ++var13) {
            byte var14 = ((Object[])var5)[var13];
            char var15 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(var14);
            var1.write(var15);
         }

         ++var2;
      }
   }
}
