package com.kenai.jbosh;

import com.kenai.jbosh.AbstractIntegerAttr;
import com.kenai.jbosh.BOSHException;

final class AttrWait extends AbstractIntegerAttr {

   private AttrWait(String var1) throws BOSHException {
      super(var1);
      this.checkMinValue(1);
   }

   static AttrWait createFromString(String var0) throws BOSHException {
      AttrWait var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrWait(var0);
      }

      return var1;
   }
}
