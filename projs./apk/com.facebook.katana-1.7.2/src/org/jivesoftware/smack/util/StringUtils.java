package org.jivesoftware.smack.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;
import org.jivesoftware.smack.util.Base64;

public class StringUtils {

   private static final char[] AMP_ENCODE = "&amp;".toCharArray();
   private static final char[] APOS_ENCODE = "&apos;".toCharArray();
   private static final char[] GT_ENCODE = "&gt;".toCharArray();
   private static final char[] LT_ENCODE = "&lt;".toCharArray();
   private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
   private static MessageDigest digest = null;
   private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
   private static Random randGen = new Random();


   private StringUtils() {}

   public static byte[] decodeBase64(String var0) {
      return Base64.decode(var0);
   }

   public static String encodeBase64(String var0) {
      byte[] var1 = null;

      byte[] var2;
      try {
         var2 = var0.getBytes("ISO-8859-1");
      } catch (UnsupportedEncodingException var3) {
         var3.printStackTrace();
         return encodeBase64(var1);
      }

      var1 = var2;
      return encodeBase64(var1);
   }

   public static String encodeBase64(byte[] var0) {
      return encodeBase64(var0, (boolean)0);
   }

   public static String encodeBase64(byte[] var0, int var1, int var2, boolean var3) {
      byte var4;
      if(var3) {
         var4 = 0;
      } else {
         var4 = 8;
      }

      return Base64.encodeBytes(var0, var1, var2, var4);
   }

   public static String encodeBase64(byte[] var0, boolean var1) {
      int var2 = var0.length;
      return encodeBase64(var0, 0, var2, var1);
   }

   public static String encodeHex(byte[] var0) {
      int var1 = var0.length * 2;
      StringBuilder var2 = new StringBuilder(var1);
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte var5 = var0[var4];
         if((var5 & 255) < 16) {
            StringBuilder var6 = var2.append("0");
         }

         String var7 = Integer.toString(var5 & 255, 16);
         var2.append(var7);
      }

      return var2.toString();
   }

   public static String escapeForXML(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         char[] var2 = var0.toCharArray();
         int var3 = var2.length;
         int var4 = (int)((double)var3 * 1.3D);
         StringBuilder var5 = new StringBuilder(var4);
         int var6 = 0;

         int var7;
         for(var7 = 0; var7 < var3; ++var7) {
            char var8 = var2[var7];
            if(var8 <= 62) {
               if(var8 == 60) {
                  if(var7 > var6) {
                     int var9 = var7 - var6;
                     var5.append(var2, var6, var9);
                  }

                  var6 = var7 + 1;
                  char[] var11 = LT_ENCODE;
                  var5.append(var11);
               } else if(var8 == 62) {
                  if(var7 > var6) {
                     int var13 = var7 - var6;
                     var5.append(var2, var6, var13);
                  }

                  var6 = var7 + 1;
                  char[] var15 = GT_ENCODE;
                  var5.append(var15);
               } else if(var8 == 38) {
                  if(var7 > var6) {
                     int var17 = var7 - var6;
                     var5.append(var2, var6, var17);
                  }

                  int var19 = var7 + 5;
                  if(var3 > var19) {
                     int var20 = var7 + 1;
                     if(var2[var20] == 35) {
                        int var21 = var7 + 2;
                        if(Character.isDigit(var2[var21])) {
                           int var22 = var7 + 3;
                           if(Character.isDigit(var2[var22])) {
                              int var23 = var7 + 4;
                              if(Character.isDigit(var2[var23])) {
                                 int var24 = var7 + 5;
                                 if(var2[var24] == 59) {
                                    continue;
                                 }
                              }
                           }
                        }
                     }
                  }

                  var6 = var7 + 1;
                  char[] var25 = AMP_ENCODE;
                  var5.append(var25);
               } else if(var8 == 34) {
                  if(var7 > var6) {
                     int var27 = var7 - var6;
                     var5.append(var2, var6, var27);
                  }

                  var6 = var7 + 1;
                  char[] var29 = QUOTE_ENCODE;
                  var5.append(var29);
               } else if(var8 == 39) {
                  if(var7 > var6) {
                     int var31 = var7 - var6;
                     var5.append(var2, var6, var31);
                  }

                  var6 = var7 + 1;
                  char[] var33 = APOS_ENCODE;
                  var5.append(var33);
               }
            }
         }

         if(var6 == 0) {
            var1 = var0;
         } else {
            if(var7 > var6) {
               int var35 = var7 - var6;
               var5.append(var2, var6, var35);
            }

            var1 = var5.toString();
         }
      }

      return var1;
   }

   public static String escapeNode(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.length() + 8;
         StringBuilder var3 = new StringBuilder(var2);
         int var4 = 0;

         for(int var5 = var0.length(); var4 < var5; ++var4) {
            char var6 = var0.charAt(var4);
            switch(var6) {
            case 34:
               StringBuilder var8 = var3.append("\\22");
               break;
            case 38:
               StringBuilder var9 = var3.append("\\26");
               break;
            case 39:
               StringBuilder var10 = var3.append("\\27");
               break;
            case 47:
               StringBuilder var11 = var3.append("\\2f");
               break;
            case 58:
               StringBuilder var12 = var3.append("\\3a");
               break;
            case 60:
               StringBuilder var13 = var3.append("\\3c");
               break;
            case 62:
               StringBuilder var14 = var3.append("\\3e");
               break;
            case 64:
               StringBuilder var15 = var3.append("\\40");
               break;
            case 92:
               StringBuilder var16 = var3.append("\\5c");
               break;
            default:
               if(Character.isWhitespace(var6)) {
                  StringBuilder var7 = var3.append("\\20");
               } else {
                  var3.append(var6);
               }
            }
         }

         var1 = var3.toString();
      }

      return var1;
   }

   public static String hash(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static String parseBareAddress(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf("/");
         if(var2 < 0) {
            var1 = var0;
         } else if(var2 == 0) {
            var1 = "";
         } else {
            var1 = var0.substring(0, var2);
         }
      }

      return var1;
   }

   public static String parseName(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.lastIndexOf("@");
         if(var2 <= 0) {
            var1 = "";
         } else {
            var1 = var0.substring(0, var2);
         }
      }

      return var1;
   }

   public static String parseResource(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.indexOf("/");
         int var3 = var2 + 1;
         int var4 = var0.length();
         if(var3 <= var4 && var2 >= 0) {
            int var5 = var2 + 1;
            var1 = var0.substring(var5);
         } else {
            var1 = "";
         }
      }

      return var1;
   }

   public static String parseServer(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.lastIndexOf("@");
         int var3 = var2 + 1;
         int var4 = var0.length();
         if(var3 > var4) {
            var1 = "";
         } else {
            int var5 = var0.indexOf("/");
            if(var5 > 0 && var5 > var2) {
               int var6 = var2 + 1;
               var1 = var0.substring(var6, var5);
            } else {
               int var7 = var2 + 1;
               var1 = var0.substring(var7);
            }
         }
      }

      return var1;
   }

   public static String randomString(int var0) {
      String var1;
      if(var0 < 1) {
         var1 = null;
      } else {
         char[] var2 = new char[var0];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               var1 = new String(var2);
               break;
            }

            char[] var5 = numbersAndLetters;
            int var6 = randGen.nextInt(71);
            char var7 = var5[var6];
            var2[var3] = var7;
            ++var3;
         }
      }

      return var1;
   }

   public static String unescapeNode(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         char[] var2 = var0.toCharArray();
         int var3 = var2.length;
         StringBuilder var4 = new StringBuilder(var3);
         int var5 = 0;

         for(int var6 = var2.length; var5 < var6; ++var5) {
            char var7 = var0.charAt(var5);
            if(var7 == 92 && var5 + 2 < var6) {
               int var8 = var5 + 1;
               char var9 = var2[var8];
               int var10 = var5 + 2;
               char var11 = var2[var10];
               if(var9 == 50) {
                  switch(var11) {
                  case 48:
                     StringBuilder var13 = var4.append(' ');
                     var5 += 2;
                     continue;
                  case 50:
                     StringBuilder var14 = var4.append('\"');
                     var5 += 2;
                     continue;
                  case 54:
                     StringBuilder var15 = var4.append('&');
                     var5 += 2;
                     continue;
                  case 55:
                     StringBuilder var16 = var4.append('\'');
                     var5 += 2;
                     continue;
                  case 102:
                     StringBuilder var17 = var4.append('/');
                     var5 += 2;
                     continue;
                  }
               } else if(var9 == 51) {
                  switch(var11) {
                  case 97:
                     StringBuilder var18 = var4.append(':');
                     var5 += 2;
                     continue;
                  case 98:
                  case 100:
                  default:
                     break;
                  case 99:
                     StringBuilder var19 = var4.append('<');
                     var5 += 2;
                     continue;
                  case 101:
                     StringBuilder var20 = var4.append('>');
                     var5 += 2;
                     continue;
                  }
               } else if(var9 == 52) {
                  if(var11 == 48) {
                     StringBuilder var21 = var4.append("@");
                     var5 += 2;
                     continue;
                  }
               } else if(var9 == 53 && var11 == 99) {
                  StringBuilder var22 = var4.append("\\");
                  var5 += 2;
                  continue;
               }
            }

            var4.append(var7);
         }

         var1 = var4.toString();
      }

      return var1;
   }
}
