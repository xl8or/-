package com.kenai.jbosh;

import com.kenai.jbosh.AbstractAttr;
import com.kenai.jbosh.BOSHException;

final class AttrAccept extends AbstractAttr<String> {

   private final String[] encodings;


   private AttrAccept(String var1) {
      super(var1);
      String[] var2 = var1.split("[\\s,]+");
      this.encodings = var2;
   }

   static AttrAccept createFromString(String var0) throws BOSHException {
      AttrAccept var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrAccept(var0);
      }

      return var1;
   }

   boolean isAccepted(String var1) {
      String[] var2 = this.encodings;
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
