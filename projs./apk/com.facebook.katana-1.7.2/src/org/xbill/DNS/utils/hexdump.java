package org.xbill.DNS.utils;


public class hexdump {

   private static final char[] hex = "0123456789ABCDEF".toCharArray();


   public hexdump() {}

   public static String dump(String var0, byte[] var1) {
      int var2 = var1.length;
      return dump(var0, var1, 0, var2);
   }

   public static String dump(String var0, byte[] var1, int var2, int var3) {
      StringBuffer var4 = new StringBuffer();
      String var5 = var3 + "b";
      var4.append(var5);
      if(var0 != null) {
         String var7 = " (" + var0 + ")";
         var4.append(var7);
      }

      StringBuffer var9 = var4.append(':');
      int var10 = var4.toString().length() + 8 & -8;
      StringBuffer var11 = var4.append('\t');
      int var12 = (80 - var10) / 3;

      for(int var13 = 0; var13 < var3; ++var13) {
         if(var13 != 0 && var13 % var12 == 0) {
            StringBuffer var14 = var4.append('\n');
            int var15 = 0;

            while(true) {
               int var16 = var10 / 8;
               if(var15 >= var16) {
                  break;
               }

               StringBuffer var17 = var4.append('\t');
               ++var15;
            }
         }

         int var18 = var13 + var2;
         int var19 = var1[var18] & 255;
         char[] var20 = hex;
         int var21 = var19 >> 4;
         char var22 = var20[var21];
         var4.append(var22);
         char[] var24 = hex;
         int var25 = var19 & 15;
         char var26 = var24[var25];
         var4.append(var26);
         StringBuffer var28 = var4.append(' ');
      }

      StringBuffer var29 = var4.append('\n');
      return var4.toString();
   }
}
