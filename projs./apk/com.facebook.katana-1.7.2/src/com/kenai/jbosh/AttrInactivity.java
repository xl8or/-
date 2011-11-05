package com.kenai.jbosh;

import com.kenai.jbosh.AbstractIntegerAttr;
import com.kenai.jbosh.BOSHException;

final class AttrInactivity extends AbstractIntegerAttr {

   private AttrInactivity(String var1) throws BOSHException {
      super(var1);
      this.checkMinValue(0);
   }

   static AttrInactivity createFromString(String var0) throws BOSHException {
      AttrInactivity var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrInactivity(var0);
      }

      return var1;
   }
}
