package org.apache.commons.httpclient.util;

import org.apache.commons.httpclient.NameValuePair;

public class ParameterFormatter {

   private static final char[] SEPARATORS = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private static final char[] UNSAFE_CHARS = new char[]{(char)null, (char)null};
   private boolean alwaysUseQuotes = 1;


   public ParameterFormatter() {}

   public static void formatValue(StringBuffer var0, String var1, boolean var2) {
      if(var0 == null) {
         throw new IllegalArgumentException("String buffer may not be null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("Value buffer may not be null");
      } else if(var2) {
         StringBuffer var3 = var0.append('\"');
         int var19 = 0;

         while(true) {
            int var5 = var1.length();
            if(var19 >= var5) {
               StringBuffer var9 = var0.append('\"');
               return;
            }

            char var6 = var1.charAt(var19);
            if(isUnsafeChar(var6)) {
               StringBuffer var7 = var0.append('\\');
            }

            var0.append(var6);
            ++var19;
         }
      } else {
         int var10 = var0.length();
         boolean var11 = false;
         byte var4 = 0;

         while(true) {
            int var12 = var1.length();
            if(var4 >= var12) {
               if(!var11) {
                  return;
               }

               var0.insert(var10, '\"');
               StringBuffer var18 = var0.append('\"');
               return;
            }

            char var13 = var1.charAt(var4);
            if(isSeparator(var13)) {
               var11 = true;
            }

            if(isUnsafeChar(var13)) {
               StringBuffer var14 = var0.append('\\');
            }

            var0.append(var13);
            int var16 = var4 + 1;
         }
      }
   }

   private static boolean isOneOf(char[] var0, char var1) {
      int var2 = 0;

      boolean var5;
      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            var5 = false;
            break;
         }

         char var4 = var0[var2];
         if(var1 == var4) {
            var5 = true;
            break;
         }

         ++var2;
      }

      return var5;
   }

   private static boolean isSeparator(char var0) {
      return isOneOf(SEPARATORS, var0);
   }

   private static boolean isUnsafeChar(char var0) {
      return isOneOf(UNSAFE_CHARS, var0);
   }

   public String format(NameValuePair var1) {
      StringBuffer var2 = new StringBuffer();
      this.format(var2, var1);
      return var2.toString();
   }

   public void format(StringBuffer var1, NameValuePair var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("String buffer may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Parameter may not be null");
      } else {
         String var3 = var2.getName();
         var1.append(var3);
         String var5 = var2.getValue();
         if(var5 != null) {
            StringBuffer var6 = var1.append("=");
            boolean var7 = this.alwaysUseQuotes;
            formatValue(var1, var5, var7);
         }
      }
   }

   public boolean isAlwaysUseQuotes() {
      return this.alwaysUseQuotes;
   }

   public void setAlwaysUseQuotes(boolean var1) {
      this.alwaysUseQuotes = var1;
   }
}
