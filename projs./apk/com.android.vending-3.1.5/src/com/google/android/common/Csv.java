package com.google.android.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Csv {

   public static final String COMMA = ",";
   public static final String NEWLINE = "\n";


   private Csv() {}

   public static boolean parseLine(BufferedReader var0, List<String> var1) throws IOException {
      String var2 = var0.readLine();
      boolean var3;
      if(var2 == null) {
         var3 = false;
         return var3;
      } else {
         int var4 = 0;

         while(true) {
            StringBuilder var5 = new StringBuilder();

            while(true) {
               int var6 = var2.indexOf(44, var4);
               int var7 = var2.indexOf(34, var4);
               if(var7 == -1 || var6 != -1 && var6 < var7) {
                  int var8;
                  if(var6 == -1) {
                     var8 = var2.length();
                  } else {
                     var8 = var6;
                  }

                  var5.append(var2, var4, var8);
                  String var10 = var5.toString();
                  var1.add(var10);
                  var4 = var6 + 1;
                  if(var4 <= 0) {
                     var3 = true;
                     return var3;
                  }
                  break;
               }

               if(var4 > 0) {
                  int var12 = var4 + -1;
                  if(var2.charAt(var12) == 34) {
                     StringBuilder var13 = var5.append('\"');
                  }
               }

               var5.append(var2, var4, var7);

               while(true) {
                  var4 = var7 + 1;
                  var7 = var2.indexOf(34, var4);
                  if(var7 != -1) {
                     var5.append(var2, var4, var7);
                     var4 = var7 + 1;
                     break;
                  }

                  int var15 = var2.length();
                  StringBuilder var16 = var5.append(var2, var4, var15).append('\n');
                  var2 = var0.readLine();
                  if(var2 == null) {
                     String var17 = var5.toString();
                     var1.add(var17);
                     var3 = true;
                     return var3;
                  }

                  var7 = -1;
               }
            }
         }
      }
   }

   public static void writeValue(String var0, Appendable var1) throws IOException {
      int var2 = var0.length();
      if(var2 != 0) {
         char var3 = var0.charAt(0);
         int var4 = var2 + -1;
         char var5 = var0.charAt(var4);
         if(var3 != 32 && var3 != 9 && var5 != 32 && var5 != 9 && var0.indexOf(34) < 0 && var0.indexOf(44) < 0 && var0.indexOf(13) < 0 && var0.indexOf(10) < 0) {
            var1.append(var0);
         } else {
            Appendable var7 = var1.append('\"');
            String var8 = var0.replace("\"", "\"\"");
            Appendable var9 = var7.append(var8).append('\"');
         }
      }
   }
}
