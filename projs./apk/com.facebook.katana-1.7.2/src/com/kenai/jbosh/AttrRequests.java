package com.kenai.jbosh;

import com.kenai.jbosh.AbstractIntegerAttr;
import com.kenai.jbosh.BOSHException;

final class AttrRequests extends AbstractIntegerAttr {

   private AttrRequests(String var1) throws BOSHException {
      super(var1);
      this.checkMinValue(1);
   }

   static AttrRequests createFromString(String var0) throws BOSHException {
      AttrRequests var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrRequests(var0);
      }

      return var1;
   }
}
