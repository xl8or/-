package org.apache.james.mime4j.decoder;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.util.CharsetUtil;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class DecoderUtil {

   private static Log log = LogFactory.getLog(DecoderUtil.class);


   public DecoderUtil() {}

   public static String chardet(String var0) {
      if(var0 != null) {
         nsDetector var1 = new nsDetector(0);
         DecoderUtil.1 var2 = new DecoderUtil.1();
         var1.Init(var2);
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         char[] var4 = var0.toCharArray();
         int var5 = var4.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            char var7 = var4[var6];
            var3.write(var7);
         }

         byte[] var8 = var3.toByteArray();
         int var9 = var8.length;
         boolean var10 = var1.isAscii(var8, var9);
         if(!var10) {
            int var11 = var8.length;
            var1.DoIt(var8, var11, (boolean)0);
         }

         var1.DataEnd();
         Object var13 = null;
         if(var10) {
            var0 = null;
         } else {
            String[] var14 = var1.getProbableCharsets();
            var6 = var14.length;
            int var15 = 0;

            while(true) {
               if(var15 >= var6) {
                  var0 = (String)var13;
                  break;
               }

               if(var14[var15].equals("EUC-KR")) {
                  var0 = String.valueOf("EUC-KR");
                  break;
               }

               ++var15;
            }
         }
      }

      return var0;
   }

   public static String decodeB(String var0, String var1) throws UnsupportedEncodingException {
      byte[] var2 = decodeBase64(var0);
      return new String(var2, var1);
   }

   public static byte[] decodeBase64(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static byte[] decodeBaseQuotedPrintable(String param0) {
      // $FF: Couldn't be decompiled
   }

   private static String decodeEncodedWord(String var0, int var1, int var2) {
      int var3 = var1 + 2;
      int var4 = var0.indexOf(63, var3);
      int var5 = var2 - 2;
      String var6;
      if(var4 == var5) {
         var6 = null;
      } else {
         int var7 = var4 + 1;
         int var8 = var0.indexOf(63, var7);
         int var9 = var2 - 2;
         if(var8 == var9) {
            var6 = null;
         } else {
            int var10 = var1 + 2;
            String var11 = var0.substring(var10, var4);
            int var12 = var4 + 1;
            String var13 = var0.substring(var12, var8);
            int var14 = var8 + 1;
            int var15 = var2 - 2;
            String var16 = var0.substring(var14, var15);
            String var17 = CharsetUtil.toJavaCharset(var11);
            if(var17 == null) {
               if(log.isWarnEnabled()) {
                  Log var18 = log;
                  StringBuilder var19 = (new StringBuilder()).append("MIME charset \'").append(var11).append("\' in encoded word \'");
                  String var20 = var0.substring(var1, var2);
                  String var21 = var19.append(var20).append("\' doesn\'t have a ").append("corresponding Java charset").toString();
                  var18.warn(var21);
               }

               var6 = null;
            } else if(!CharsetUtil.isDecodingSupported(var17)) {
               if(log.isWarnEnabled()) {
                  Log var22 = log;
                  StringBuilder var23 = (new StringBuilder()).append("Current JDK doesn\'t support decoding of charset \'").append(var17).append("\' (MIME charset \'").append(var11).append("\' in encoded word \'");
                  String var24 = var0.substring(var1, var2);
                  String var25 = var23.append(var24).append("\')").toString();
                  var22.warn(var25);
               }

               var6 = null;
            } else if(var16.length() == 0) {
               if(log.isWarnEnabled()) {
                  Log var26 = log;
                  StringBuilder var27 = (new StringBuilder()).append("Missing encoded text in encoded word: \'");
                  String var28 = var0.substring(var1, var2);
                  String var29 = var27.append(var28).append("\'").toString();
                  var26.warn(var29);
               }

               var6 = "";
            } else {
               try {
                  if(var13.equalsIgnoreCase("Q")) {
                     var6 = decodeQ(var16, var17);
                     return var6;
                  }

                  if(var13.equalsIgnoreCase("B")) {
                     var6 = decodeB(var16, var17);
                     return var6;
                  }

                  if(log.isWarnEnabled()) {
                     Log var30 = log;
                     StringBuilder var31 = (new StringBuilder()).append("Warning: Unknown encoding in encoded word \'");
                     String var32 = var0.substring(var1, var2);
                     String var33 = var31.append(var32).append("\'").toString();
                     var30.warn(var33);
                  }
               } catch (UnsupportedEncodingException var43) {
                  if(log.isWarnEnabled()) {
                     Log var35 = log;
                     StringBuilder var36 = (new StringBuilder()).append("Unsupported encoding in encoded word \'");
                     String var37 = var0.substring(var1, var2);
                     String var38 = var36.append(var37).append("\'").toString();
                     var35.warn(var38, var43);
                  }

                  var6 = null;
                  return var6;
               } catch (RuntimeException var44) {
                  if(log.isWarnEnabled()) {
                     Log var39 = log;
                     StringBuilder var40 = (new StringBuilder()).append("Could not decode encoded word \'");
                     String var41 = var0.substring(var1, var2);
                     String var42 = var40.append(var41).append("\'").toString();
                     var39.warn(var42, var44);
                  }

                  var6 = null;
                  return var6;
               }

               var6 = null;
            }
         }
      }

      return var6;
   }

   public static String decodeEncodedWords(String var0) {
      String var1;
      if(var0.indexOf("=?") == -1) {
         var1 = var0;
      } else {
         int var2 = 0;
         boolean var3 = false;
         StringBuilder var4 = new StringBuilder();

         while(true) {
            int var5 = var0.indexOf("=?", var2);
            int var6 = var5 + 2;
            if(var5 != -1) {
               int var7 = var6 + 2;
               int var8 = var0.indexOf(63, var7) + 1;
               int var9 = var0.indexOf(63, var8);
               if(var9 != -1) {
                  var6 = var9 + 1;
               }
            }

            int var10;
            if(var5 == -1) {
               var10 = -1;
            } else {
               var10 = var0.indexOf("?=", var6);
            }

            if(var10 == -1) {
               if(var2 == 0) {
                  var1 = var0;
               } else {
                  String var11 = var0.substring(var2);
                  var4.append(var11);
                  var1 = var4.toString();
               }
               break;
            }

            int var13 = var10 + 2;
            String var14 = var0.substring(var2, var5);
            String var15 = decodeEncodedWord(var0, var5, var13);
            if(var15 == null) {
               var4.append(var14);
               String var17 = var0.substring(var5, var13);
               var4.append(var17);
            } else {
               if(!var3 || !CharsetUtil.isWhitespace(var14)) {
                  var4.append(var14);
               }

               var4.append(var15);
            }

            var2 = var13;
            if(var15 != null) {
               var3 = true;
            } else {
               var3 = false;
            }
         }
      }

      return var1;
   }

   public static String decodeGeneric(String var0) {
      String var1;
      if(var0 == null) {
         var1 = var0;
      } else {
         String var3;
         if(var0.indexOf("=?") == -1) {
            String var2 = chardet(var0);
            if(var2 == null) {
               var3 = var0;
            } else {
               var3 = justDecode(var0, var2);
            }
         } else {
            var3 = decodeEncodedWords(var0);
         }

         var1 = var3;
      }

      return var1;
   }

   public static String decodeQ(String var0, String var1) throws UnsupportedEncodingException {
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      while(true) {
         int var4 = var0.length();
         if(var3 >= var4) {
            byte[] var8 = decodeBaseQuotedPrintable(var2.toString());
            return new String(var8, var1);
         }

         char var5 = var0.charAt(var3);
         if(var5 == 95) {
            StringBuffer var6 = var2.append("=20");
         } else {
            var2.append(var5);
         }

         ++var3;
      }
   }

   public static String justDecode(String var0, String var1) {
      String var2;
      if(var0 != null && var0.indexOf("=?") == -1) {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();

         String var10;
         try {
            char[] var4 = var0.toCharArray();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               char var7 = var4[var6];
               var3.write(var7);
            }

            byte[] var8 = var3.toByteArray();
            String var9 = CharsetUtil.toJavaCharset(var1.toLowerCase());
            var10 = new String(var8, var9);
         } catch (UnsupportedEncodingException var12) {
            var10 = var0;
         }

         var2 = var10;
      } else {
         var2 = var0;
      }

      return var2;
   }

   static class 1 implements nsICharsetDetectionObserver {

      1() {}

      public void Notify(String var1) {
         Log var2 = DecoderUtil.log;
         String var3 = "Detected CHARSET = " + var1;
         var2.info(var3);
      }
   }
}
