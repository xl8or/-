package org.apache.commons.io;

import java.io.Serializable;
import org.apache.commons.io.FilenameUtils;

public final class IOCase implements Serializable {

   public static final IOCase INSENSITIVE = new IOCase("Insensitive", (boolean)0);
   public static final IOCase SENSITIVE = new IOCase("Sensitive", (boolean)1);
   public static final IOCase SYSTEM;
   private static final long serialVersionUID = -6343169151696340687L;
   private final String name;
   private final transient boolean sensitive;


   static {
      IOCase var0 = new IOCase;
      byte var1;
      if(!FilenameUtils.isSystemWindows()) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      var0.<init>("System", (boolean)var1);
      SYSTEM = var0;
   }

   private IOCase(String var1, boolean var2) {
      this.name = var1;
      this.sensitive = var2;
   }

   public static IOCase forName(String var0) {
      IOCase var1;
      if(SENSITIVE.name.equals(var0)) {
         var1 = SENSITIVE;
      } else if(INSENSITIVE.name.equals(var0)) {
         var1 = INSENSITIVE;
      } else {
         if(!SYSTEM.name.equals(var0)) {
            String var2 = "Invalid IOCase name: " + var0;
            throw new IllegalArgumentException(var2);
         }

         var1 = SYSTEM;
      }

      return var1;
   }

   private Object readResolve() {
      return forName(this.name);
   }

   public int checkCompareTo(String var1, String var2) {
      if(var1 != null && var2 != null) {
         int var3;
         if(this.sensitive) {
            var3 = var1.compareTo(var2);
         } else {
            var3 = var1.compareToIgnoreCase(var2);
         }

         return var3;
      } else {
         throw new NullPointerException("The strings must not be null");
      }
   }

   public boolean checkEndsWith(String var1, String var2) {
      int var3 = var2.length();
      byte var4;
      if(!this.sensitive) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var5 = var1.length() - var3;
      return var1.regionMatches((boolean)var4, var5, var2, 0, var3);
   }

   public boolean checkEquals(String var1, String var2) {
      if(var1 != null && var2 != null) {
         boolean var3;
         if(this.sensitive) {
            var3 = var1.equals(var2);
         } else {
            var3 = var1.equalsIgnoreCase(var2);
         }

         return var3;
      } else {
         throw new NullPointerException("The strings must not be null");
      }
   }

   public boolean checkRegionMatches(String var1, int var2, String var3) {
      byte var4;
      if(!this.sensitive) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var5 = var3.length();
      return var1.regionMatches((boolean)var4, var2, var3, 0, var5);
   }

   public boolean checkStartsWith(String var1, String var2) {
      byte var3;
      if(!this.sensitive) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var2.length();
      byte var7 = 0;
      return var1.regionMatches((boolean)var3, 0, var2, var7, var4);
   }

   String convertCase(String var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else if(this.sensitive) {
         var2 = var1;
      } else {
         var2 = var1.toLowerCase();
      }

      return var2;
   }

   public String getName() {
      return this.name;
   }

   public boolean isCaseSensitive() {
      return this.sensitive;
   }

   public String toString() {
      return this.name;
   }
}
