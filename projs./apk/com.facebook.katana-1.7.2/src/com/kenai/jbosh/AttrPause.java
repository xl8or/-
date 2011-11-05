package com.kenai.jbosh;

import com.kenai.jbosh.AbstractIntegerAttr;
import com.kenai.jbosh.BOSHException;
import java.util.concurrent.TimeUnit;

final class AttrPause extends AbstractIntegerAttr {

   private AttrPause(String var1) throws BOSHException {
      super(var1);
      this.checkMinValue(1);
   }

   static AttrPause createFromString(String var0) throws BOSHException {
      AttrPause var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrPause(var0);
      }

      return var1;
   }

   public int getInMilliseconds() {
      TimeUnit var1 = TimeUnit.MILLISECONDS;
      long var2 = (long)this.intValue();
      TimeUnit var4 = TimeUnit.SECONDS;
      return (int)var1.convert(var2, var4);
   }
}
