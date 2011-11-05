package com.kenai.jbosh;

import com.kenai.jbosh.AbstractIntegerAttr;
import com.kenai.jbosh.BOSHException;
import java.util.concurrent.TimeUnit;

final class AttrMaxPause extends AbstractIntegerAttr {

   private AttrMaxPause(String var1) throws BOSHException {
      super(var1);
      this.checkMinValue(1);
   }

   static AttrMaxPause createFromString(String var0) throws BOSHException {
      AttrMaxPause var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrMaxPause(var0);
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
