package com.kenai.jbosh;

import com.kenai.jbosh.AbstractAttr;

final class AttrCharsets extends AbstractAttr<String> {

   private final String[] charsets;


   private AttrCharsets(String var1) {
      super(var1);
      String[] var2 = var1.split("\\ +");
      this.charsets = var2;
   }

   static AttrCharsets createFromString(String var0) {
      AttrCharsets var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrCharsets(var0);
      }

      return var1;
   }

   boolean isAccepted(String var1) {
      String[] var2 = this.charsets;
      int var3 = var2.length;
      int var4 = 0;

      boolean var5;
      while(true) {
         if(var4 >= var3) {
            var5 = false;
            break;
         }

         if(var2[var4].equalsIgnoreCase(var1)) {
            var5 = true;
            break;
         }

         ++var4;
      }

      return var5;
   }
}
