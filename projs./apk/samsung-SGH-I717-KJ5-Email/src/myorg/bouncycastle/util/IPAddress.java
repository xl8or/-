package myorg.bouncycastle.util;


public class IPAddress {

   public IPAddress() {}

   private static boolean isMaskValue(String var0, int var1) {
      boolean var2 = false;

      int var3;
      try {
         var3 = Integer.parseInt(var0);
      } catch (NumberFormatException var6) {
         return var2;
      }

      if(var3 >= 0 && var3 <= var1) {
         var2 = true;
      }

      return var2;
   }

   public static boolean isValid(String var0) {
      boolean var1;
      if(!isValidIPv4(var0) && !isValidIPv6(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isValidIPv4(String var0) {
      boolean var1;
      if(var0.length() == 0) {
         var1 = false;
      } else {
         int var2 = 0;
         String var3 = var0 + ".";
         int var4 = 0;

         while(true) {
            int var5 = var3.length();
            if(var4 < var5) {
               int var6 = var3.indexOf(46, var4);
               if(var6 > var4) {
                  if(var2 == 4) {
                     var1 = false;
                     break;
                  }

                  int var7;
                  try {
                     var7 = Integer.parseInt(var3.substring(var4, var6));
                  } catch (NumberFormatException var10) {
                     var1 = false;
                     break;
                  }

                  if(var7 >= 0 && var7 <= 255) {
                     var4 = var6 + 1;
                     ++var2;
                     continue;
                  }

                  var1 = false;
                  break;
               }
            }

            if(var2 == 4) {
               var1 = true;
            } else {
               var1 = false;
            }
            break;
         }
      }

      return var1;
   }

   public static boolean isValidIPv4WithNetmask(String var0) {
      int var1 = var0.indexOf("/");
      int var2 = var1 + 1;
      String var3 = var0.substring(var2);
      boolean var4;
      if(var1 > 0 && isValidIPv4(var0.substring(0, var1)) && (isValidIPv4(var3) || isMaskValue(var3, 32))) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static boolean isValidIPv6(String var0) {
      boolean var1;
      if(var0.length() == 0) {
         var1 = false;
      } else {
         int var2 = 0;
         String var3 = var0 + ":";
         boolean var4 = false;
         int var5 = 0;

         while(true) {
            int var6 = var3.length();
            if(var5 < var6) {
               int var7 = var3.indexOf(58, var5);
               if(var7 >= var5) {
                  if(var2 == 8) {
                     var1 = false;
                     break;
                  }

                  if(var5 != var7) {
                     String var8 = var3.substring(var5, var7);
                     int var9 = var3.length() - 1;
                     if(var7 == var9 && var8.indexOf(46) > 0) {
                        if(!isValidIPv4(var8)) {
                           var1 = false;
                           break;
                        }

                        ++var2;
                     } else {
                        int var11;
                        try {
                           var11 = Integer.parseInt(var3.substring(var5, var7), 16);
                        } catch (NumberFormatException var15) {
                           var1 = false;
                           break;
                        }

                        if(var11 < 0 || var11 > '\uffff') {
                           var1 = false;
                           break;
                        }
                     }
                  } else {
                     if(var7 != 1) {
                        int var14 = var3.length() - 1;
                        if(var7 != var14 && var4) {
                           var1 = false;
                           break;
                        }
                     }

                     var4 = true;
                  }

                  var5 = var7 + 1;
                  int var10 = var2 + 1;
                  continue;
               }
            }

            if(var2 != 8 && !var4) {
               var1 = false;
               break;
            }

            var1 = true;
            break;
         }
      }

      return var1;
   }

   public static boolean isValidIPv6WithNetmask(String var0) {
      int var1 = var0.indexOf("/");
      int var2 = var1 + 1;
      String var3 = var0.substring(var2);
      boolean var4;
      if(var1 > 0 && isValidIPv6(var0.substring(0, var1)) && (isValidIPv6(var3) || isMaskValue(var3, 128))) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static boolean isValidWithNetMask(String var0) {
      boolean var1;
      if(!isValidIPv4WithNetmask(var0) && !isValidIPv6WithNetmask(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
