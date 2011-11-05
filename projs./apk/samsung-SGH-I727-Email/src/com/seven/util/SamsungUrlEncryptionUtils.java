package com.seven.util;

import android.content.Context;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SamsungUrlEncryptionUtils {

   private static String ALGORITHM = "AES";
   private static int BUF_LEN = 15;
   private static String SAMSUNG_KEY = "fe134abce420daeccf3253abe99dd321";
   public static final String TAG = "ProvActivity";
   private static String TRANSFORM = "AES/ECB/PKCS5Padding";
   private static byte[] mBase64DecMap = new byte[128];
   private static byte[] mBase64EncMap = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};


   static {
      int var0 = 0;

      while(true) {
         int var1 = mBase64EncMap.length;
         if(var0 >= var1) {
            return;
         }

         byte[] var2 = mBase64DecMap;
         byte var3 = mBase64EncMap[var0];
         byte var4 = (byte)var0;
         var2[var3] = var4;
         ++var0;
      }
   }

   public SamsungUrlEncryptionUtils() {}

   private static String asHex(byte[] var0) {
      int var1 = var0.length * 2;
      StringBuffer var2 = new StringBuffer(var1);
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2.toString();
         }

         if((var0[var3] & 255) < 16) {
            StringBuffer var5 = var2.append("0");
         }

         String var6 = Long.toString((long)(var0[var3] & 255), 16);
         var2.append(var6);
         ++var3;
      }
   }

   public static byte[] base64Decode(String var0) {
      if(var0 != null && var0.length() != 0) {
         byte[] var1 = var0.getBytes();
         int var2 = var1.length;

         while(true) {
            int var3 = var2 - 1;
            if(var1[var3] != 61) {
               int var4 = var1.length / 4;
               byte[] var5 = new byte[var2 - var4];
               int var6 = 0;

               while(true) {
                  int var7 = var1.length;
                  if(var6 >= var7) {
                     int var11 = 0;
                     int var12 = 0;

                     while(true) {
                        int var13 = var5.length - 2;
                        if(var12 >= var13) {
                           int var30 = var5.length;
                           if(var12 < var30) {
                              int var31 = var1[var11] << 2 & 255;
                              int var32 = var11 + 1;
                              int var33 = var1[var32] >>> 4 & 3;
                              byte var34 = (byte)(var31 | var33);
                              var5[var12] = var34;
                           }

                           int var35 = var12 + 1;
                           int var36 = var5.length;
                           if(var35 < var36) {
                              int var37 = var11 + 1;
                              int var38 = var1[var37] << 4 & 255;
                              int var39 = var11 + 2;
                              int var40 = var1[var39] >>> 2 & 15;
                              byte var41 = (byte)(var38 | var40);
                              var5[var35] = var41;
                           }

                           return var5;
                        }

                        int var14 = var1[var11] << 2 & 255;
                        int var15 = var11 + 1;
                        int var16 = var1[var15] >>> 4 & 3;
                        byte var17 = (byte)(var14 | var16);
                        var5[var12] = var17;
                        int var18 = var12 + 1;
                        int var19 = var11 + 1;
                        int var20 = var1[var19] << 4 & 255;
                        int var21 = var11 + 2;
                        int var22 = var1[var21] >>> 2 & 15;
                        byte var23 = (byte)(var20 | var22);
                        var5[var18] = var23;
                        int var24 = var12 + 2;
                        int var25 = var11 + 2;
                        int var26 = var1[var25] << 6 & 255;
                        int var27 = var11 + 3;
                        int var28 = var1[var27] & 63;
                        byte var29 = (byte)(var26 | var28);
                        var5[var24] = var29;
                        var11 += 4;
                        var12 += 3;
                     }
                  }

                  byte[] var8 = mBase64DecMap;
                  byte var9 = var1[var6];
                  byte var10 = var8[var9];
                  var1[var6] = var10;
                  ++var6;
               }
            }

            var2 += -1;
         }
      } else {
         throw new IllegalArgumentException("Can not decode NULL or empty string.");
      }
   }

   public static String base64Encode(byte[] var0) {
      if(var0 != null && var0.length != 0) {
         byte[] var1 = new byte[(var0.length + 2) / 3 * 4];
         int var2 = 0;
         int var3 = 0;

         while(true) {
            int var4 = var0.length - 2;
            if(var2 >= var4) {
               int var28 = var0.length;
               if(var2 < var28) {
                  int var29 = var3 + 1;
                  byte[] var30 = mBase64EncMap;
                  int var31 = var0[var2] >>> 2 & 63;
                  byte var32 = var30[var31];
                  var1[var3] = var32;
                  int var33 = var0.length - 1;
                  if(var2 < var33) {
                     int var34 = var29 + 1;
                     byte[] var35 = mBase64EncMap;
                     int var36 = var2 + 1;
                     int var37 = var0[var36] >>> 4 & 15;
                     int var38 = var0[var2] << 4 & 63;
                     int var39 = var37 | var38;
                     byte var40 = var35[var39];
                     var1[var29] = var40;
                     int var41 = var34 + 1;
                     byte[] var42 = mBase64EncMap;
                     int var43 = var2 + 1;
                     int var44 = var0[var43] << 2 & 63;
                     byte var45 = var42[var44];
                     var1[var34] = var45;
                     var3 = var41;
                  } else {
                     var3 = var29 + 1;
                     byte[] var47 = mBase64EncMap;
                     int var48 = var0[var2] << 4 & 63;
                     byte var49 = var47[var48];
                     var1[var29] = var49;
                  }
               }

               while(true) {
                  int var46 = var1.length;
                  if(var3 >= var46) {
                     return new String(var1);
                  }

                  var1[var3] = 61;
                  ++var3;
               }
            }

            int var5 = var3 + 1;
            byte[] var6 = mBase64EncMap;
            int var7 = var0[var2] >>> 2 & 63;
            byte var8 = var6[var7];
            var1[var3] = var8;
            int var9 = var5 + 1;
            byte[] var10 = mBase64EncMap;
            int var11 = var2 + 1;
            int var12 = var0[var11] >>> 4 & 15;
            int var13 = var0[var2] << 4 & 63;
            int var14 = var12 | var13;
            byte var15 = var10[var14];
            var1[var5] = var15;
            int var16 = var9 + 1;
            byte[] var17 = mBase64EncMap;
            int var18 = var2 + 2;
            int var19 = var0[var18] >>> 6 & 3;
            int var20 = var2 + 1;
            int var21 = var0[var20] << 2 & 63;
            int var22 = var19 | var21;
            byte var23 = var17[var22];
            var1[var9] = var23;
            var3 = var16 + 1;
            byte[] var24 = mBase64EncMap;
            int var25 = var2 + 2;
            int var26 = var0[var25] & 63;
            byte var27 = var24[var26];
            var1[var16] = var27;
            var2 += 3;
         }
      } else {
         throw new IllegalArgumentException("Can not encode NULL or empty byte array.");
      }
   }

   private static String decrypt(ArrayList<String> var0, SecretKeySpec var1) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
      Cipher var2 = Cipher.getInstance(TRANSFORM);
      var2.init(2, var1);
      StringBuilder var3 = new StringBuilder();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         byte[] var5 = base64Decode((String)var4.next());
         byte[] var6 = var2.doFinal(var5);
         String var7 = new String(var6);
         var3.append(var7);
      }

      return var3.toString();
   }

   private static ArrayList<String> encrypt(String var0) throws Exception {
      String var1 = SAMSUNG_KEY;
      byte[] var2 = (new BigInteger(var1, 16)).toByteArray();
      byte[] var3 = new byte[16];
      if(var2.length == 17) {
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               break;
            }

            int var6 = var4 + 1;
            byte var7 = var2[var6];
            var3[var4] = var7;
            ++var4;
         }
      } else {
         var3 = var2;
      }

      String var8 = ALGORITHM;
      return encrypt(new SecretKeySpec(var3, var8), var0);
   }

   private static ArrayList<String> encrypt(SecretKeySpec var0, String var1) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
      Cipher var2 = Cipher.getInstance(TRANSFORM);
      var2.init(1, var0);
      ArrayList var3 = new ArrayList();
      int var4 = 0;

      while(true) {
         int var5 = var1.length();
         if(var4 >= var5) {
            return var3;
         }

         int var6 = var4;
         int var7 = BUF_LEN;
         var4 += var7;
         int var8 = var1.length();
         if(var4 > var8) {
            int var9 = var1.length();
         }

         byte[] var10 = var1.substring(var6, var4).getBytes();
         String var11 = base64Encode(var2.doFinal(var10));
         var3.add(var11);
      }
   }

   public static String getIMSIOfSubscriptionRenewalParams(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static String getSubscriptionRenewalParams(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static String getSubscriptionRenewalParamsWithoutImei(Context param0) {
      // $FF: Couldn't be decompiled
   }
}
