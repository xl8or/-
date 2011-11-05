package com.htc.android.mail.pim.util;

import java.util.Vector;

public class StringUtil {

   private static final String CRLF = "\r\n";
   private static final String HT = "\t";


   private StringUtil() {}

   public static String[] SplitN(String var0, String var1, int var2) {
      Vector var3 = new Vector();
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;

      while(var5 >= 0) {
         var5 = var0.indexOf(var1, var4);
         if(var5 >= 0) {
            int var7 = var2 - 1;
            if(var6 != var7) {
               String var12 = var0.substring(var4, var5);
               var3.add(var12);
               int var14 = var1.length();
               var4 = var5 + var14;
               ++var6;
               continue;
            }
         }

         int var8 = var0.length();
         String var9 = var0.substring(var4, var8);
         var3.add(var9);
         break;
      }

      String[] var11 = new String[0];
      return (String[])var3.toArray(var11);
   }

   public static boolean equalsIgnoreCase(String var0, String var1) {
      boolean var2;
      if(var0 == null && var1 == null) {
         var2 = true;
      } else if(var0 != null && var1 != null) {
         String var3 = var0.toLowerCase();
         String var4 = var1.toLowerCase();
         if(var3.equals(var4)) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public static int findEmptyLine(String var0) {
      int var1 = 0;

      while(true) {
         var1 = var0.indexOf("\n", var1);
         if(var1 == -1) {
            break;
         }

         while(var0.charAt(var1) == 13) {
            int var2 = var1 + 1;
         }

         if(var0.charAt(var1) == 10) {
            ++var1;
            break;
         }
      }

      return var1;
   }

   public static String fold(String var0) {
      String[] var1 = split(var0, ",");
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            return var2.toString();
         }

         StringBuilder var5 = new StringBuilder();
         String var6 = var1[var3];
         StringBuilder var7 = var5.append(var6);
         int var8 = var1.length - 1;
         String var9;
         if(var3 != var8) {
            var9 = ",";
         } else {
            var9 = "";
         }

         String var10 = var7.append(var9).toString();
         String var11;
         if(var3 == 0) {
            var11 = var10 + "\r\n";
         } else {
            var11 = "\t" + var10 + "\r\n";
         }

         var2.append(var11);
         ++var3;
      }
   }

   public static boolean getBooleanValue(String var0) {
      boolean var1;
      if(var0 != null && !var0.equals("")) {
         if(equalsIgnoreCase(var0, "true")) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public static String[] getStringArray(Vector var0) {
      String[] var1;
      if(var0 == null) {
         var1 = null;
      } else {
         String[] var2 = new String[var0.size()];
         int var3 = 0;

         while(true) {
            int var4 = var0.size();
            if(var3 >= var4) {
               var1 = var2;
               break;
            }

            String var5 = (String)var0.elementAt(var3);
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public static boolean isNullOrEmpty(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = true;
      } else if(var0.trim().equals("")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static String join(String[] var0, String var1) {
      String var2 = var0[0];
      StringBuffer var3 = new StringBuffer(var2);
      int var4 = var0.length;

      for(int var5 = 1; var5 < var4; ++var5) {
         StringBuffer var6 = var3.append(var1);
         String var7 = var0[var5];
         var6.append(var7);
      }

      return var3.toString();
   }

   public static String removeBackslashes(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append(var0);

         for(int var4 = var2.length() - 1; var4 >= 0; var4 += -1) {
            char var5 = var2.charAt(var4);
            if(92 == var5) {
               var2.deleteCharAt(var4);
            }
         }

         var1 = var2.toString();
      }

      return var1;
   }

   public static String removeBlanks(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append(var0);

         for(int var4 = var2.length() - 1; var4 >= 0; var4 += -1) {
            char var5 = var2.charAt(var4);
            if(32 == var5) {
               var2.deleteCharAt(var4);
            }
         }

         var1 = var2.toString();
      }

      return var1;
   }

   public static String[] split(String var0, String var1) {
      Vector var2 = new Vector(10);
      int var3 = var0.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var0.charAt(var4);
         if(var1.indexOf(var5) != -1) {
            Integer var6 = new Integer(var4);
            var2.addElement(var6);
         }
      }

      int var7 = var2.size();
      String[] var8 = new String[var7 + 1];
      if(var7 == 0) {
         var8[0] = var0;
      } else {
         int var9 = ((Integer)var2.elementAt(0)).intValue();
         String var10 = var0.substring(0, var9);
         var8[0] = var10;

         int var15;
         byte var20;
         for(var20 = 1; var20 < var7; var15 = var20 + 1) {
            int var11 = var20 - 1;
            int var12 = ((Integer)var2.elementAt(var11)).intValue() + 1;
            int var13 = ((Integer)var2.elementAt(var20)).intValue();
            String var14 = var0.substring(var12, var13);
            var8[var20] = var14;
         }

         int var16 = var20 - 1;
         int var17 = ((Integer)var2.elementAt(var16)).intValue() + 1;
         int var18 = var0.length();
         String var19;
         if(var17 < var18) {
            var19 = var0.substring(var17);
         } else {
            var19 = "";
         }

         var8[var20] = var19;
      }

      return var8;
   }

   public static String trim(String var0, char var1) {
      int var2 = 0;
      int var3 = var0.length() - 1;

      while(true) {
         String var4;
         if(var0.charAt(var2) == var1) {
            ++var2;
            if(var2 < var3) {
               continue;
            }

            var4 = "";
            return var4;
         }

         while(true) {
            if(var0.charAt(var3) == var1) {
               var3 += -1;
               if(var3 > var2) {
                  continue;
               }

               var4 = "";
               return var4;
            }

            int var5 = var3 + 1;
            var4 = var0.substring(var2, var5);
            return var4;
         }
      }
   }
}
