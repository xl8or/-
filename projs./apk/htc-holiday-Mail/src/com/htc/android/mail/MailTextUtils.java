package com.htc.android.mail;

import java.lang.Character.UnicodeBlock;

public class MailTextUtils {

   public MailTextUtils() {}

   public static String addArabicAlign(String var0, boolean var1) {
      String var2;
      if(var1) {
         var2 = htmlEncode(var0);
      } else {
         var2 = var0;
      }

      return var2;
   }

   public static String htmlDecode(String var0) {
      return var0.replace("&lt;", "<").replace("&gt;", ">").replace("&#39;", "\'").replace("&quot;", "\"").replace("&nbsp;", " ");
   }

   public static String htmlDecodeWithoutSpace(String var0) {
      return var0.replace("&lt;", "<").replace("&gt;", ">").replace("&#39;", "\'").replace("&quot;", "\"");
   }

   public static String htmlEncode(String var0) {
      StringBuilder var1 = new StringBuilder();
      boolean var2 = false;
      int var3 = 0;

      while(true) {
         int var4 = var0.length();
         if(var3 >= var4) {
            return var1.toString();
         }

         char var5 = var0.charAt(var3);
         switch(var5) {
         case 32:
            if(var2) {
               var2 = false;
               StringBuilder var12 = var1.append("&nbsp;");
            } else {
               var2 = true;
               StringBuilder var13 = var1.append(" ");
            }
            break;
         case 34:
            StringBuilder var11 = var1.append("&quot;");
            var2 = false;
            break;
         case 38:
            StringBuilder var9 = var1.append("&amp;");
            var2 = false;
            break;
         case 39:
            StringBuilder var10 = var1.append("&#39;");
            var2 = false;
            break;
         case 60:
            StringBuilder var7 = var1.append("&lt;");
            var2 = false;
            break;
         case 62:
            StringBuilder var8 = var1.append("&gt;");
            var2 = false;
            break;
         default:
            var1.append(var5);
            var2 = false;
         }

         ++var3;
      }
   }

   public static String htmlEncode(String var0, boolean var1) {
      StringBuilder var2 = new StringBuilder();
      boolean var3 = false;
      int var4 = 0;

      while(true) {
         int var5 = var0.length();
         if(var4 >= var5) {
            return var2.toString();
         }

         char var6 = var0.charAt(var4);
         switch(var6) {
         case 10:
            if(var1) {
               StringBuilder var13 = var2.append("<br>");
            } else {
               StringBuilder var14 = var2.append('\n');
            }

            var3 = false;
            break;
         case 32:
            if(var3) {
               var3 = false;
               StringBuilder var15 = var2.append("&nbsp;");
            } else {
               var3 = true;
               StringBuilder var16 = var2.append(" ");
            }
            break;
         case 34:
            StringBuilder var12 = var2.append("&quot;");
            var3 = false;
            break;
         case 38:
            StringBuilder var10 = var2.append("&amp;");
            var3 = false;
            break;
         case 39:
            StringBuilder var11 = var2.append("&#39;");
            var3 = false;
            break;
         case 60:
            StringBuilder var8 = var2.append("&lt;");
            var3 = false;
            break;
         case 62:
            StringBuilder var9 = var2.append("&gt;");
            var3 = false;
            break;
         default:
            var2.append(var6);
            var3 = false;
         }

         ++var4;
      }
   }

   public static boolean isArabicCharacter(char var0) {
      UnicodeBlock var1 = UnicodeBlock.of(var0);
      UnicodeBlock var2 = UnicodeBlock.ARABIC;
      boolean var5;
      if(var1 != var2) {
         UnicodeBlock var3 = UnicodeBlock.ARABIC_PRESENTATION_FORMS_A;
         if(var1 != var3) {
            UnicodeBlock var4 = UnicodeBlock.ARABIC_PRESENTATION_FORMS_B;
            if(var1 != var4) {
               var5 = false;
               return var5;
            }
         }
      }

      var5 = true;
      return var5;
   }

   public static boolean isToken(char var0) {
      boolean var1;
      if((var0 < 32 || var0 > 47) && (var0 < 58 || var0 > 64) && (var0 < 91 || var0 > 96) && (var0 < 123 || var0 > 126) && (var0 < 128 || var0 > 173)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
