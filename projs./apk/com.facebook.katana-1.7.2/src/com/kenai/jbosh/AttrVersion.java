package com.kenai.jbosh;

import com.kenai.jbosh.AbstractAttr;
import com.kenai.jbosh.BOSHException;

final class AttrVersion extends AbstractAttr<String> implements Comparable {

   private static final AttrVersion DEFAULT;
   private final int major;
   private final int minor;


   static {
      try {
         DEFAULT = createFromString("1.8");
      } catch (BOSHException var1) {
         throw new IllegalStateException(var1);
      }
   }

   private AttrVersion(String var1) throws BOSHException {
      super(var1);
      int var2 = var1.indexOf(46);
      if(var2 <= 0) {
         String var3 = "Illegal ver attribute value (not in major.minor form): " + var1;
         throw new BOSHException(var3);
      } else {
         String var4 = var1.substring(0, var2);

         try {
            int var5 = Integer.parseInt(var4);
            this.major = var5;
         } catch (NumberFormatException var14) {
            String var7 = "Could not parse ver attribute value (major ver): " + var4;
            throw new BOSHException(var7, var14);
         }

         if(this.major < 0) {
            throw new BOSHException("Major version may not be < 0");
         } else {
            int var8 = var2 + 1;
            String var9 = var1.substring(var8);

            try {
               int var10 = Integer.parseInt(var9);
               this.minor = var10;
            } catch (NumberFormatException var13) {
               String var12 = "Could not parse ver attribute value (minor ver): " + var9;
               throw new BOSHException(var12, var13);
            }

            if(this.minor < 0) {
               throw new BOSHException("Minor version may not be < 0");
            }
         }
      }
   }

   static AttrVersion createFromString(String var0) throws BOSHException {
      AttrVersion var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrVersion(var0);
      }

      return var1;
   }

   static AttrVersion getSupportedVersion() {
      return DEFAULT;
   }

   public int compareTo(Object var1) {
      byte var4;
      if(var1 instanceof AttrVersion) {
         AttrVersion var11 = (AttrVersion)var1;
         int var2 = this.major;
         int var3 = var11.major;
         if(var2 < var3) {
            var4 = -1;
         } else {
            int var5 = this.major;
            int var6 = var11.major;
            if(var5 > var6) {
               var4 = 1;
            } else {
               int var7 = this.minor;
               int var8 = var11.minor;
               if(var7 < var8) {
                  var4 = -1;
               } else {
                  int var9 = this.minor;
                  int var10 = var11.minor;
                  if(var9 > var10) {
                     var4 = 1;
                  } else {
                     var4 = 0;
                  }
               }
            }
         }
      } else {
         var4 = 0;
      }

      return var4;
   }

   int getMajor() {
      return this.major;
   }

   int getMinor() {
      return this.minor;
   }
}
