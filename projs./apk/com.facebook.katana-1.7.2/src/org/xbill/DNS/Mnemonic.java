package org.xbill.DNS;

import java.util.HashMap;

class Mnemonic {

   static final int CASE_LOWER = 3;
   static final int CASE_SENSITIVE = 1;
   static final int CASE_UPPER = 2;
   private static Integer[] cachedInts = new Integer[64];
   private String description;
   private int max;
   private boolean numericok;
   private String prefix;
   private HashMap strings;
   private HashMap values;
   private int wordcase;


   static {
      int var0 = 0;

      while(true) {
         int var1 = cachedInts.length;
         if(var0 >= var1) {
            return;
         }

         Integer[] var2 = cachedInts;
         Integer var3 = new Integer(var0);
         var2[var0] = var3;
         ++var0;
      }
   }

   public Mnemonic(String var1, int var2) {
      this.description = var1;
      this.wordcase = var2;
      HashMap var3 = new HashMap();
      this.strings = var3;
      HashMap var4 = new HashMap();
      this.values = var4;
      this.max = Integer.MAX_VALUE;
   }

   private int parseNumeric(String var1) {
      int var2;
      label18: {
         int var3;
         try {
            var2 = Integer.parseInt(var1);
            if(var2 < 0) {
               break label18;
            }

            var3 = this.max;
         } catch (NumberFormatException var5) {
            break label18;
         }

         if(var2 <= var3) {
            return var2;
         }
      }

      var2 = -1;
      return var2;
   }

   private String sanitize(String var1) {
      String var2;
      if(this.wordcase == 2) {
         var2 = var1.toUpperCase();
      } else if(this.wordcase == 3) {
         var2 = var1.toLowerCase();
      } else {
         var2 = var1;
      }

      return var2;
   }

   public static Integer toInteger(int var0) {
      Integer var2;
      if(var0 >= 0) {
         int var1 = cachedInts.length;
         if(var0 < var1) {
            var2 = cachedInts[var0];
            return var2;
         }
      }

      var2 = new Integer(var0);
      return var2;
   }

   public void add(int var1, String var2) {
      this.check(var1);
      Integer var3 = toInteger(var1);
      String var4 = this.sanitize(var2);
      this.strings.put(var4, var3);
      this.values.put(var3, var4);
   }

   public void addAlias(int var1, String var2) {
      this.check(var1);
      Integer var3 = toInteger(var1);
      String var4 = this.sanitize(var2);
      this.strings.put(var4, var3);
   }

   public void addAll(Mnemonic var1) {
      int var2 = this.wordcase;
      int var3 = var1.wordcase;
      if(var2 != var3) {
         StringBuilder var4 = new StringBuilder();
         String var5 = var1.description;
         String var6 = var4.append(var5).append(": wordcases do not match").toString();
         throw new IllegalArgumentException(var6);
      } else {
         HashMap var7 = this.strings;
         HashMap var8 = var1.strings;
         var7.putAll(var8);
         HashMap var9 = this.values;
         HashMap var10 = var1.values;
         var9.putAll(var10);
      }
   }

   public void check(int var1) {
      if(var1 >= 0) {
         int var2 = this.max;
         if(var1 <= var2) {
            return;
         }
      }

      StringBuilder var3 = new StringBuilder();
      String var4 = this.description;
      String var5 = var3.append(var4).append(" ").append(var1).append("is out of range").toString();
      throw new IllegalArgumentException(var5);
   }

   public String getText(int var1) {
      this.check(var1);
      HashMap var2 = this.values;
      Integer var3 = toInteger(var1);
      String var4 = (String)var2.get(var3);
      if(var4 == null) {
         var4 = Integer.toString(var1);
         if(this.prefix != null) {
            StringBuilder var5 = new StringBuilder();
            String var6 = this.prefix;
            var4 = var5.append(var6).append(var4).toString();
         }
      }

      return var4;
   }

   public int getValue(String var1) {
      String var2 = this.sanitize(var1);
      Integer var3 = (Integer)this.strings.get(var2);
      int var4;
      if(var3 != null) {
         var4 = var3.intValue();
      } else {
         if(this.prefix != null) {
            String var5 = this.prefix;
            if(var2.startsWith(var5)) {
               int var6 = this.prefix.length();
               String var7 = var2.substring(var6);
               var4 = this.parseNumeric(var7);
               if(var4 >= 0) {
                  return var4;
               }
            }
         }

         if(this.numericok) {
            var4 = this.parseNumeric(var2);
         } else {
            var4 = -1;
         }
      }

      return var4;
   }

   public void setMaximum(int var1) {
      this.max = var1;
   }

   public void setNumericAllowed(boolean var1) {
      this.numericok = var1;
   }

   public void setPrefix(String var1) {
      String var2 = this.sanitize(var1);
      this.prefix = var2;
   }
}
