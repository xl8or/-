package com.htc.android.mail;

import android.os.Handler;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.nio.ByteOrder;

public class Base64 {

   private static final char[] BASE_64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
   private static final byte[] BASE_64_DECODE = new byte[123];
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final char PAD = '=';
   private static final String TAG = "Base64";


   static {
      for(int var0 = 0; var0 < 26; ++var0) {
         byte[] var1 = BASE_64_DECODE;
         int var2 = var0 + 65;
         byte var3 = (byte)var0;
         var1[var2] = var3;
         byte[] var4 = BASE_64_DECODE;
         int var5 = var0 + 97;
         byte var6 = (byte)(var0 + 26);
         var4[var5] = var6;
      }

      for(int var7 = 0; var7 < 10; ++var7) {
         byte[] var8 = BASE_64_DECODE;
         int var9 = var7 + 48;
         byte var10 = (byte)(var7 + 52);
         var8[var9] = var10;
      }

      BASE_64_DECODE[43] = 62;
      BASE_64_DECODE[47] = 63;
      BASE_64_DECODE[61] = -1;
   }

   public Base64() {}

   public static final String ModifiedUtf7decode(String var0) {
      String var1 = "";
      int var2 = var0.indexOf(38);

      for(int var3 = var0.indexOf(45); var2 != -1 && var3 != -1; var3 = var0.indexOf(45)) {
         if(var2 < var3) {
            if(var2 != 0) {
               StringBuilder var4 = (new StringBuilder()).append(var1);
               String var5 = var0.substring(0, var2);
               var1 = var4.append(var5).toString();
            }

            if(var2 + 1 == var3) {
               var1 = var1 + "&";
            } else {
               int var8 = var2 + 1;
               String var9 = var0.substring(var8, var3);
               StringBuilder var10 = (new StringBuilder()).append(var1);
               String var11 = decode(var9.replace(',', '/'));
               var1 = var10.append(var11).toString();
            }

            int var6 = var0.length() - 1;
            if(var3 >= var6) {
               var0 = "";
               break;
            }

            int var7 = var3 + 1;
            var0 = var0.substring(var7);
         } else {
            StringBuilder var12 = (new StringBuilder()).append(var1);
            String var13 = var0.substring(0, var2);
            var1 = var12.append(var13).toString();
            var0 = var0.substring(var2);
         }

         var2 = var0.indexOf(38);
      }

      if(var0.length() > 0) {
         var1 = var1 + var0;
      }

      return var1;
   }

   public static final String ModifiedUtf7encode(String var0, ByteOrder var1) {
      String var2 = "";
      String var3 = var0.replaceAll("&", "&-");
      int var4 = 0;

      while(true) {
         int var5 = var3.length();
         if(var4 >= var5) {
            break;
         }

         boolean var6;
         int var7;
         if(var3.charAt(var4) >= 32 && var3.charAt(var4) <= 126) {
            var6 = false;
            var7 = var4 + 1;

            while(true) {
               int var15 = var3.length();
               if(var7 >= var15) {
                  break;
               }

               if(var3.charAt(var7) < 32 || var3.charAt(var7) > 126) {
                  StringBuilder var16 = (new StringBuilder()).append(var2);
                  String var17 = var3.substring(var4, var7);
                  var2 = var16.append(var17).toString();
                  var4 = var7 - 1;
                  break;
               }

               ++var7;
            }

            if(!var6) {
               StringBuilder var18 = (new StringBuilder()).append(var2);
               String var19 = var3.substring(var4);
               var2 = var18.append(var19).toString();
               break;
            }
         } else {
            var6 = false;
            var7 = var4 + 1;

            while(true) {
               int var8 = var3.length();
               if(var7 >= var8) {
                  break;
               }

               if(var3.charAt(var7) >= 32 && var3.charAt(var7) <= 126) {
                  String var9 = encode(var3.substring(var4, var7), var1);
                  StringBuilder var10 = (new StringBuilder()).append(var2).append("&");
                  String var11 = var9.replace('/', ',');
                  var2 = var10.append(var11).append("-").toString();
                  var4 = var7 - 1;
                  var6 = true;
                  break;
               }

               ++var7;
            }

            if(!var6) {
               String var12 = encode(var3.substring(var4), var1);
               StringBuilder var13 = (new StringBuilder()).append(var2).append("&");
               String var14 = var12.replace('/', ',');
               var2 = var13.append(var14).append("-").toString();
               break;
            }
         }

         ++var4;
      }

      return var2;
   }

   public static final String ModifiedUtf7encodeBigEndian(String var0) {
      ByteOrder var1 = ByteOrder.BIG_ENDIAN;
      return ModifiedUtf7encode(var0, var1);
   }

   public static final String ModifiedUtf7encodeLittleEndian(String var0) {
      ByteOrder var1 = ByteOrder.LITTLE_ENDIAN;
      return ModifiedUtf7encode(var0, var1);
   }

   public static final String decode(String var0) {
      int var1 = var0.length() % 4;
      if(var1 > 0) {
         StringBuilder var2 = (new StringBuilder()).append(var0);
         int var3 = var1 - 1;
         String var4 = "===".substring(var3);
         var0 = var2.append(var4).toString();
      }

      String var5 = null;
      byte[] var6 = org.apache.commons.codec.binary.Base64.decodeBase64(var0.getBytes());

      int var7;
      for(var7 = var6.length; var7 > 0; var7 += -1) {
         int var8 = var7 - 1;
         if(var6[var8] != 0) {
            break;
         }
      }

      if(var7 > 0) {
         try {
            var5 = new String(var6, 0, var7, "unicode");
         } catch (UnsupportedEncodingException var10) {
            var5 = new String(var6);
         }
      }

      return var5;
   }

   public static final void decode(InputStream var0, OutputStream var1) throws IOException {
      byte[] var2 = new byte[4];
      byte[] var3 = BASE_64_DECODE;

      while(true) {
         byte var4 = 0;
         byte var5 = 4;

         int var6;
         try {
            var6 = var0.read(var2, var4, var5);
         } catch (IOException var33) {
            var33.printStackTrace();
            throw var33;
         }

         int var7 = var6;
         if(var6 == -1) {
            var1.close();
            return;
         }

         byte var9 = var2[0];
         byte var10 = var3[var9];
         byte var11 = var2[1];
         byte var12 = var3[var11];
         byte var13 = var2[2];
         byte var14 = var3[var13];
         byte var15 = var2[3];
         byte var16 = var3[var15];
         if(var14 == -1) {
            var7 = 2;
         } else if(var16 == -1) {
            var7 = 3;
         }

         switch(var7) {
         case 2:
            int var17 = var10 << 2;
            int var18 = var12 >>> 4;
            int var19 = var17 | var18;
            var1.write(var19);
            break;
         case 3:
            int var27 = var10 << 2;
            int var28 = var12 >>> 4;
            int var29 = var27 | var28;
            var1.write(var29);
            int var30 = var12 << 4;
            int var31 = var14 >>> 2;
            int var32 = var30 | var31;
            var1.write(var32);
            break;
         case 4:
            int var20 = var10 << 2;
            int var21 = var12 >>> 4;
            int var22 = var20 | var21;
            var1.write(var22);
            int var23 = var12 << 4;
            int var24 = var14 >>> 2;
            int var25 = var23 | var24;
            var1.write(var25);
            int var26 = var14 << 6 | var16;
            var1.write(var26);
            break;
         default:
            return;
         }
      }
   }

   public static final byte[] decode(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      decode(var0, var1);
      return var1.toByteArray();
   }

   public static final boolean decodeImapFile(BufferedInputStream param0, OutputStream param1, String param2, Account param3, String param4, AbsRequestController param5, WeakReference<Handler> param6) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static final String decodeToString(String var0, String var1) throws UnsupportedEncodingException {
      byte[] var2 = var0.getBytes();
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);

      byte[] var4;
      String var6;
      try {
         var4 = decode((InputStream)var3);
      } catch (IOException var8) {
         var6 = "";
         return var6;
      }

      var6 = new String(var4, var1);
      return var6;
   }

   public static final boolean decodepopFile(BufferedInputStream param0, OutputStream param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static final String encode(InputStream var0) {
      StringBuilder var1 = new StringBuilder();
      byte[] var2 = new byte[3];
      char[] var3 = BASE_64_ALPHABET;

      String var48;
      while(true) {
         byte var4 = 0;
         byte var5 = 3;

         int var6;
         try {
            var6 = var0.read(var2, var4, var5);
         } catch (IOException var47) {
            var47.printStackTrace();
            var48 = var1.toString();
            break;
         }

         byte var8 = var2[0];
         byte var9 = var2[1];
         byte var10 = var2[2];
         switch(var6) {
         case 1:
            int var39 = var8 >>> 2;
            char var40 = var3[var39];
            var1.append(var40);
            int var42 = (var8 & 3) << 4;
            char var43 = var3[var42];
            var1.append(var43);
            StringBuilder var45 = var1.append('=');
            StringBuilder var46 = var1.append('=');
            break;
         case 2:
            int var27 = var8 >>> 2;
            char var28 = var3[var27];
            var1.append(var28);
            int var30 = (var8 & 3) << 4;
            int var31 = var9 >>> 4;
            int var32 = var30 | var31;
            char var33 = var3[var32];
            var1.append(var33);
            int var35 = (var9 & 15) << 2;
            char var36 = var3[var35];
            var1.append(var36);
            StringBuilder var38 = var1.append('=');
            break;
         case 3:
            int var11 = var8 >>> 2;
            char var12 = var3[var11];
            var1.append(var12);
            int var14 = (var8 & 3) << 4;
            int var15 = var9 >>> 4;
            int var16 = var14 | var15;
            char var17 = var3[var16];
            var1.append(var17);
            int var19 = (var9 & 15) << 2;
            int var20 = var10 >>> 6;
            int var21 = var19 | var20;
            char var22 = var3[var21];
            var1.append(var22);
            int var24 = var10 & 63;
            char var25 = var3[var24];
            var1.append(var25);
         }

         if(var6 <= 0) {
            var48 = var1.toString();
            break;
         }
      }

      return var48;
   }

   public static final String encode(String param0, ByteOrder param1) {
      // $FF: Couldn't be decompiled
   }

   private static boolean needBroadcastBack(int var0) {
      int[] var1 = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
      boolean var2 = false;
      int[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         if(var3[var5] == var0) {
            var2 = true;
            break;
         }
      }

      return var2;
   }

   private static ByteString readLine(BufferedInputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      if(DEBUG) {
         ll.d("Base64", "enter readLine");
      }

      int var2;
      while(true) {
         var2 = var0.read();
         if(var2 == -1) {
            break;
         }

         if((char)var2 == 13) {
            var1.write(var2);
         } else {
            if((char)var2 == 10) {
               var1.write(var2);
               break;
            }

            var1.write(var2);
         }
      }

      ByteString var3;
      if(var2 == -1) {
         var3 = null;
      } else {
         byte[] var4 = var1.toByteArray();
         var3 = new ByteString(var4);
      }

      return var3;
   }
}
