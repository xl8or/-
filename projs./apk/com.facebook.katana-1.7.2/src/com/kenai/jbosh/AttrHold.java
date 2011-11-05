package com.kenai.jbosh;

import com.kenai.jbosh.AbstractIntegerAttr;
import com.kenai.jbosh.BOSHException;

final class AttrHold extends AbstractIntegerAttr {

   private AttrHold(String var1) throws BOSHException {
      super(var1);
      this.checkMinValue(0);
   }

   static AttrHold createFromString(String var0) throws BOSHException {
      AttrHold var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrHold(var0);
      }

      return var1;
   }
}
