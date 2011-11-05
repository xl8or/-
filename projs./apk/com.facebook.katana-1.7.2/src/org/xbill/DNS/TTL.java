package org.xbill.DNS;

import org.xbill.DNS.InvalidTTLException;

public final class TTL {

   public static final long MAX_VALUE = 2147483647L;


   private TTL() {}

   static void check(long var0) {
      if(var0 < 0L || var0 > 2147483647L) {
         throw new InvalidTTLException(var0);
      }
   }

   public static String format(long var0) {
      check(var0);
      StringBuffer var2 = new StringBuffer();
      long var3 = var0 % 60L;
      long var5 = var0 / 60L;
      long var7 = var5 % 60L;
      long var9 = var5 / 60L;
      long var11 = var9 % 24L;
      long var13 = var9 / 24L;
      long var15 = var13 % 7L;
      long var17 = var13 / 7L;
      if(var17 > 0L) {
         String var19 = var17 + "W";
         var2.append(var19);
      }

      if(var15 > 0L) {
         String var21 = var15 + "D";
         var2.append(var21);
      }

      if(var11 > 0L) {
         String var23 = var11 + "H";
         var2.append(var23);
      }

      if(var7 > 0L) {
         String var25 = var7 + "M";
         var2.append(var25);
      }

      if(var3 > 0L || var17 == 0L && var15 == 0L && var11 == 0L && var7 == 0L) {
         String var27 = var3 + "S";
         var2.append(var27);
      }

      return var2.toString();
   }

   public static long parse(String var0, boolean var1) {
      if(var0 != null && var0.length() != 0 && Character.isDigit(var0.charAt(0))) {
         byte var2 = 0;
         long var3 = 0L;
         long var5 = 0L;
         int var7 = var2;
         long var8 = var3;

         while(true) {
            int var10 = var0.length();
            if(var7 >= var10) {
               long var20;
               if(var8 == 0L) {
                  var20 = var5;
               } else {
                  var20 = var8;
               }

               if(var20 > 4294967295L) {
                  throw new NumberFormatException();
               } else {
                  long var22;
                  if(var20 > 2147483647L && var1) {
                     var22 = 2147483647L;
                  } else {
                     var22 = var20;
                  }

                  return var22;
               }
            }

            char var11 = var0.charAt(var7);
            if(Character.isDigit(var11)) {
               long var12 = 10L * var5;
               long var14 = (long)Character.getNumericValue(var11);
               long var16 = var12 + var14;
               if(var16 < var5) {
                  throw new NumberFormatException();
               }
            } else {
               switch(Character.toUpperCase(var11)) {
               case 87:
                  var5 *= 7L;
               case 68:
                  var5 *= 24L;
               case 72:
                  var5 *= 60L;
               case 77:
                  var5 *= 60L;
               case 83:
                  var8 += var5;
                  var5 = 0L;
                  if(var8 > 4294967295L) {
                     throw new NumberFormatException();
                  }
                  break;
               default:
                  throw new NumberFormatException();
               }
            }

            ++var7;
         }
      } else {
         throw new NumberFormatException();
      }
   }

   public static long parseTTL(String var0) {
      return parse(var0, (boolean)1);
   }
}
