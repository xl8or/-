package com.htc.android.mail;

import com.htc.android.mail.ByteString;
import java.util.ArrayList;
import java.util.Locale;

public class QuotedPrintable {

   private static final ByteString SLB = new ByteString("=\r\n");


   public QuotedPrintable() {}

   public static final void decode(ArrayList<ByteString> var0, int var1, int var2, String var3) {
      int var4 = stripSoftLineBreaks(var0, var1, var2);
      int var5 = var2 - var4;
      String var6;
      if(var3 == null) {
         var6 = null;
      } else {
         String var13 = "--" + var3;
         Locale var14 = Locale.US;
         var6 = var13.toLowerCase(var14);
      }

      boolean var7 = false;

      boolean var12;
      for(int var8 = var1; var8 < var5; var7 = var12) {
         label90: {
            ByteString var9 = (ByteString)var0.get(var8);
            if(var9 != null && var6 != null) {
               int var10 = var9.length();
               int var11 = var6.length();
               if(var10 >= var11 && var9.toString().toLowerCase().startsWith(var6)) {
                  var12 = var7;
                  break label90;
               }
            }

            int var18;
            label76: {
               stripTrailingWhitespace(var9);
               int var15 = var9.length();
               if(var15 >= 2) {
                  int var16 = var15 - 2;
                  if(var9.byteAt(var16) == 13) {
                     int var17 = var15 - 1;
                     if(var9.byteAt(var17) == 10) {
                        var18 = var15 + -2;
                        break label76;
                     }
                  }
               }

               var18 = var15;
            }

            int var19 = var18;
            boolean var20 = var7;
            byte var38 = 0;

            while(true) {
               if(var38 >= var19) {
                  var12 = var20;
                  break;
               }

               byte var21 = var9.byteAt(var38);
               int var25;
               int var26;
               if(var21 <= 26 && var21 != 9 && var21 != 32) {
                  var9.delete(var38);
                  int var22 = var19 + -1;
                  var25 = var22;
                  var26 = var38;
               } else {
                  label94: {
                     if(var21 == 61) {
                        if(var38 + 2 >= var19) {
                           var12 = var20;
                           break;
                        }

                        int var30 = var38 + 1;
                        int var39 = hexDigit(var9.byteAt(var30));
                        int var31 = var38 + 2;
                        int var32 = hexDigit(var9.byteAt(var31));
                        if(var39 != -1) {
                           if(var32 == -1) {
                              var26 = var38;
                              var25 = var19;
                           } else {
                              int var33 = var39 << 4;
                              var32 |= var33;
                              if(var32 == 10 && var20) {
                                 int var34 = var38 - 1;
                                 var9.set(var34, 10);
                                 var9.delete(var38, 3);
                                 var25 = var19 + -3;
                                 var26 = var38 + -1;
                                 boolean var24 = false;
                              } else {
                                 boolean var35;
                                 if(var32 == 13) {
                                    var35 = true;
                                 } else {
                                    var35 = false;
                                 }

                                 var9.set(var38, var32);
                                 int var36 = var38 + 1;
                                 var9.delete(var36, 2);
                                 var25 = var19 + -2;
                                 var26 = var38;
                              }
                           }
                           break label94;
                        }
                     }

                     var26 = var38;
                     var25 = var19;
                  }
               }

               int var27 = var26 + 1;
               var19 = var25;
            }
         }

         ++var8;
      }

   }

   public static final String encode(String var0) {
      return "";
   }

   private static final int hexDigit(int var0) {
      int var1;
      if(48 <= var0 && var0 <= 57) {
         var1 = var0 - 48;
      } else if(65 <= var0 && var0 <= 70) {
         var1 = var0 - 65 + 10;
      } else if(97 <= var0 && var0 <= 102) {
         var1 = var0 - 97 + 10;
      } else {
         var1 = -1;
      }

      return var1;
   }

   public static final boolean needsEncoding(String var0) {
      return false;
   }

   public static final int stripSoftLineBreaks(ArrayList<ByteString> var0, int var1, int var2) {
      int var3 = 0;
      int var4 = var1;

      while(true) {
         int var5 = var2 - 1;
         if(var4 >= var5) {
            return var3;
         }

         ByteString var6 = (ByteString)var0.get(var4);

         while(true) {
            ByteString var7 = SLB;
            if(var6.endsWith(var7)) {
               int var8 = var2 - 1;
               if(var4 < var8) {
                  int var9 = var6.length() - 3;
                  var6.delete(var9, 3);
                  int var10 = var4 + 1;
                  ByteString var11 = (ByteString)var0.get(var10);
                  var6.concat(var11);
                  int var13 = var4 + 1;
                  var0.remove(var13);
                  ++var3;
                  var2 += -1;
                  continue;
               }
            }

            ++var4;
            break;
         }
      }
   }

   private static final void stripTrailingWhitespace(ByteString var0) {
      if(var0.length() > 2) {
         int var1 = var0.length() - 3;
         int var3 = 0;

         for(int var4 = var1; var4 >= 0; var4 += -1) {
            byte var5 = var0.byteAt(var4);
            if(var5 != 32 && var5 != 9) {
               break;
            }

            ++var3;
         }

         if(var3 != 0) {
            int var6 = var1 - var3 + 1;
            var0.delete(var6, var3);
         }
      }
   }
}
