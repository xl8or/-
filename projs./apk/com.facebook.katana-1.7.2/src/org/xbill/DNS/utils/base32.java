package org.xbill.DNS.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class base32 {

   private String alphabet;
   private boolean lowercase;
   private boolean padding;


   public base32(String var1, boolean var2, boolean var3) {
      this.alphabet = var1;
      this.padding = var2;
      this.lowercase = var3;
   }

   private static int blockLenToPadding(int var0) {
      byte var1;
      switch(var0) {
      case 1:
         var1 = 6;
         break;
      case 2:
         var1 = 4;
         break;
      case 3:
         var1 = 3;
         break;
      case 4:
         var1 = 1;
         break;
      case 5:
         var1 = 0;
         break;
      default:
         var1 = -1;
      }

      return var1;
   }

   private static int paddingToBlockLen(int var0) {
      byte var1;
      switch(var0) {
      case 0:
         var1 = 5;
         break;
      case 1:
         var1 = 4;
         break;
      case 2:
      case 5:
      default:
         var1 = -1;
         break;
      case 3:
         var1 = 3;
         break;
      case 4:
         var1 = 2;
         break;
      case 6:
         var1 = 1;
      }

      return var1;
   }

   public byte[] fromString(String var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      byte[] var3 = var1.getBytes();
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            byte[] var43;
            if(this.padding) {
               if(var2.size() % 8 != 0) {
                  var43 = null;
                  return var43;
               }
            } else {
               while(var2.size() % 8 != 0) {
                  var2.write(61);
               }
            }

            byte[] var8 = var2.toByteArray();
            var2.reset();
            DataOutputStream var44 = new DataOutputStream(var2);
            int var9 = 0;

            while(true) {
               int var10 = var8.length / 8;
               if(var9 >= var10) {
                  var43 = var2.toByteArray();
                  break;
               }

               boolean var11 = true;
               int[] var12 = new int[5];
               byte var13 = 0;
               int var14 = 8;

               int var15;
               for(var15 = var13; var15 < 8; ++var15) {
                  int var16 = var9 * 8 + var15;
                  if((char)var8[var16] == 61) {
                     break;
                  }

                  String var17 = this.alphabet;
                  int var18 = var9 * 8 + var15;
                  byte var19 = var8[var18];
                  short var20 = (short)var17.indexOf(var19);
                  ((Object[])var11)[var15] = (boolean)var20;
                  if(((Object[])var11)[var15] < 0) {
                     var43 = null;
                     return var43;
                  }

                  var14 += -1;
               }

               var15 = paddingToBlockLen(var14);
               if(var15 < 0) {
                  var43 = null;
                  break;
               }

               int var21 = ((Object[])var11)[0] << 3;
               int var22 = ((Object[])var11)[1] >> 2;
               int var23 = var21 | var22;
               var12[0] = var23;
               int var24 = (((Object[])var11)[1] & 3) << 6;
               int var25 = ((Object[])var11)[2] << 1;
               int var26 = var24 | var25;
               int var27 = ((Object[])var11)[3] >> 4;
               int var28 = var26 | var27;
               var12[1] = var28;
               int var29 = (((Object[])var11)[3] & 15) << 4;
               int var30 = ((Object[])var11)[4] >> 1 & 15;
               int var31 = var29 | var30;
               var12[2] = var31;
               int var32 = ((Object[])var11)[4] << 7;
               int var33 = ((Object[])var11)[5] << 2;
               int var34 = var32 | var33;
               int var35 = ((Object[])var11)[6] >> 3;
               int var36 = var34 | var35;
               var12[3] = var36;
               int var37 = (((Object[])var11)[6] & 7) << 5;
               int var38 = ((Object[])var11)[7] | var37;
               var12[4] = var38;

               for(int var39 = 0; var39 < var15; ++var39) {
                  try {
                     byte var40 = (byte)(var12[var39] & 255);
                     var44.writeByte(var40);
                  } catch (IOException var42) {
                     break;
                  }
               }

               ++var9;
            }

            return var43;
         }

         char var6 = (char)var3[var4];
         if(!Character.isWhitespace(var6)) {
            byte var7 = (byte)Character.toUpperCase(var6);
            var2.write(var7);
         }

         ++var4;
      }
   }

   public String toString(byte[] var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      int var3 = 0;

      while(true) {
         int var4 = (var1.length + 4) / 5;
         if(var3 >= var4) {
            byte[] var38 = var2.toByteArray();
            return new String(var38);
         }

         boolean var5 = true;
         int[] var6 = new int[8];
         int var7 = 0;

         int var8;
         for(var8 = 5; var7 < 5; ++var7) {
            int var9 = var3 * 5 + var7;
            int var10 = var1.length;
            if(var9 < var10) {
               int var11 = var3 * 5 + var7;
               short var12 = (short)(var1[var11] & 255);
               ((Object[])var5)[var7] = (boolean)var12;
            } else {
               ((Object[])var5)[var7] = false;
               var8 += -1;
            }
         }

         int var13 = blockLenToPadding(var8);
         byte var14 = (byte)(((Object[])var5)[0] >> 3 & 31);
         var6[0] = var14;
         int var15 = (((Object[])var5)[0] & 7) << 2;
         int var16 = ((Object[])var5)[1] >> 6 & 3;
         byte var17 = (byte)(var15 | var16);
         var6[1] = var17;
         byte var18 = (byte)(((Object[])var5)[1] >> 1 & 31);
         var6[2] = var18;
         int var19 = (((Object[])var5)[1] & 1) << 4;
         int var20 = ((Object[])var5)[2] >> 4 & 15;
         byte var21 = (byte)(var19 | var20);
         var6[3] = var21;
         int var22 = (((Object[])var5)[2] & 15) << 1;
         int var23 = ((Object[])var5)[3] >> 7 & 1;
         byte var24 = (byte)(var22 | var23);
         var6[4] = var24;
         byte var25 = (byte)(((Object[])var5)[3] >> 2 & 31);
         var6[5] = var25;
         int var26 = (((Object[])var5)[3] & 3) << 3;
         int var27 = ((Object[])var5)[4] >> 5 & 7;
         byte var28 = (byte)(var26 | var27);
         var6[6] = var28;
         byte var29 = (byte)(((Object[])var5)[4] & 31);
         var6[7] = var29;
         int var30 = 0;

         while(true) {
            int var31 = var6.length - var13;
            if(var30 >= var31) {
               if(this.padding) {
                  int var36 = var6.length - var13;

                  while(true) {
                     int var37 = var6.length;
                     if(var36 >= var37) {
                        break;
                     }

                     var2.write(61);
                     ++var36;
                  }
               }

               ++var3;
               break;
            }

            String var32 = this.alphabet;
            int var33 = var6[var30];
            char var34 = var32.charAt(var33);
            if(this.lowercase) {
               char var35 = Character.toLowerCase(var34);
            }

            var2.write(var34);
            ++var30;
         }
      }
   }

   public static class Alphabet {

      public static final String BASE32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567=";
      public static final String BASE32HEX = "0123456789ABCDEFGHIJKLMNOPQRSTUV=";


      private Alphabet() {}
   }
}
