package org.apache.harmony.awt.internal.nls;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

   private static ResourceBundle bundle = null;


   static {
      try {
         bundle = setLocale(Locale.getDefault(), "org.apache.harmony.awt.internal.nls.messages");
      } catch (Throwable var0) {
         var0.printStackTrace();
      }
   }

   public Messages() {}

   public static String format(String var0, Object[] var1) {
      int var2 = var0.length();
      int var3 = var1.length * 20;
      int var4 = var2 + var3;
      StringBuilder var5 = new StringBuilder(var4);
      String[] var6 = new String[var1.length];
      int var7 = 0;

      while(true) {
         int var8 = var1.length;
         if(var7 >= var8) {
            int var9 = 0;

            for(var7 = var0.indexOf(123, 0); var7 >= 0; var7 = var0.indexOf(123, var9)) {
               if(var7 != 0) {
                  int var15 = var7 - 1;
                  if(var0.charAt(var15) == 92) {
                     if(var7 != 1) {
                        int var16 = var7 - 1;
                        String var17 = var0.substring(var9, var16);
                        var5.append(var17);
                     }

                     StringBuilder var19 = var5.append('{');
                     var9 = var7 + 1;
                     continue;
                  }
               }

               int var20 = var0.length() - 3;
               if(var7 > var20) {
                  int var21 = var0.length();
                  String var22 = var0.substring(var9, var21);
                  var5.append(var22);
                  var9 = var0.length();
               } else {
                  int var24 = var7 + 1;
                  byte var25 = (byte)Character.digit(var0.charAt(var24), 10);
                  if(var25 >= 0) {
                     int var26 = var7 + 2;
                     if(var0.charAt(var26) == 125) {
                        String var30 = var0.substring(var9, var7);
                        var5.append(var30);
                        int var32 = var6.length;
                        if(var25 >= var32) {
                           StringBuilder var33 = var5.append("<missing argument>");
                        } else {
                           String var34 = var6[var25];
                           var5.append(var34);
                        }

                        var9 = var7 + 3;
                        continue;
                     }
                  }

                  int var27 = var7 + 1;
                  String var28 = var0.substring(var9, var27);
                  var5.append(var28);
                  var9 = var7 + 1;
               }
            }

            int var10 = var0.length();
            if(var9 < var10) {
               int var11 = var0.length();
               String var12 = var0.substring(var9, var11);
               var5.append(var12);
            }

            return var5.toString();
         }

         if(var1[var7] == false) {
            var6[var7] = "<null>";
         } else {
            String var14 = var1[var7].toString();
            var6[var7] = var14;
         }

         ++var7;
      }
   }

   public static String getString(String var0) {
      String var1;
      if(bundle == null) {
         var1 = var0;
      } else {
         String var2;
         try {
            var2 = bundle.getString(var0);
         } catch (MissingResourceException var4) {
            var1 = "Missing message: " + var0;
            return var1;
         }

         var1 = var2;
      }

      return var1;
   }

   public static String getString(String var0, char var1) {
      Object[] var2 = new Object[1];
      String var3 = String.valueOf(var1);
      var2[0] = var3;
      return getString(var0, var2);
   }

   public static String getString(String var0, int var1) {
      Object[] var2 = new Object[1];
      String var3 = Integer.toString(var1);
      var2[0] = var3;
      return getString(var0, var2);
   }

   public static String getString(String var0, Object var1) {
      Object[] var2 = new Object[]{var1};
      return getString(var0, var2);
   }

   public static String getString(String var0, Object var1, Object var2) {
      Object[] var3 = new Object[]{var1, var2};
      return getString(var0, var3);
   }

   public static String getString(String var0, Object[] var1) {
      String var2 = var0;
      if(bundle != null) {
         String var3;
         try {
            var3 = bundle.getString(var0);
         } catch (MissingResourceException var5) {
            return format(var2, var1);
         }

         var2 = var3;
      }

      return format(var2, var1);
   }

   public static ResourceBundle setLocale(Locale var0, String var1) {
      ResourceBundle var2;
      ResourceBundle var5;
      try {
         var5 = (ResourceBundle)AccessController.doPrivileged(var1.new 1(var0, (ClassLoader)null));
      } catch (MissingResourceException var4) {
         var2 = null;
         return var2;
      }

      var2 = var5;
      return var2;
   }

   class 1 implements PrivilegedAction<Object> {

      // $FF: synthetic field
      private final ClassLoader val$loader;
      // $FF: synthetic field
      private final Locale val$locale;


      1(Locale var2, ClassLoader var3) {
         this.val$locale = var2;
         this.val$loader = var3;
      }

      public Object run() {
         String var1 = Messages.this;
         Locale var2 = this.val$locale;
         ClassLoader var3;
         if(this.val$loader != null) {
            var3 = this.val$loader;
         } else {
            var3 = ClassLoader.getSystemClassLoader();
         }

         return ResourceBundle.getBundle(var1, var2, var3);
      }
   }
}
