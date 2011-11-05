package org.xbill.DNS;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public final class Options {

   private static Map table;


   static {
      try {
         refresh();
      } catch (SecurityException var1) {
         ;
      }
   }

   private Options() {}

   public static boolean check(String var0) {
      boolean var1;
      if(table == null) {
         var1 = false;
      } else {
         Map var2 = table;
         String var3 = var0.toLowerCase();
         if(var2.get(var3) != null) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public static void clear() {
      table = null;
   }

   public static int intValue(String var0) {
      String var1 = value(var0);
      int var5;
      if(var1 != null) {
         label16: {
            int var2;
            try {
               var2 = Integer.parseInt(var1);
            } catch (NumberFormatException var4) {
               break label16;
            }

            var5 = var2;
            if(var2 > 0) {
               return var5;
            }
         }
      }

      var5 = -1;
      return var5;
   }

   public static void refresh() {
      String var0 = System.getProperty("dnsjava.options");
      if(var0 != null) {
         StringTokenizer var1 = new StringTokenizer(var0, ",");

         while(var1.hasMoreTokens()) {
            var0 = var1.nextToken();
            int var2 = var0.indexOf(61);
            if(var2 == -1) {
               set(var0);
            } else {
               String var3 = var0.substring(0, var2);
               int var4 = var2 + 1;
               String var5 = var0.substring(var4);
               set(var3, var5);
            }
         }

      }
   }

   public static void set(String var0) {
      if(table == null) {
         table = new HashMap();
      }

      Map var1 = table;
      String var2 = var0.toLowerCase();
      var1.put(var2, "true");
   }

   public static void set(String var0, String var1) {
      if(table == null) {
         table = new HashMap();
      }

      Map var2 = table;
      String var3 = var0.toLowerCase();
      String var4 = var1.toLowerCase();
      var2.put(var3, var4);
   }

   public static void unset(String var0) {
      if(table != null) {
         Map var1 = table;
         String var2 = var0.toLowerCase();
         var1.remove(var2);
      }
   }

   public static String value(String var0) {
      String var1;
      if(table == null) {
         var1 = null;
      } else {
         Map var2 = table;
         String var3 = var0.toLowerCase();
         var1 = (String)var2.get(var3);
      }

      return var1;
   }
}
