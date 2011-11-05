package com.kenai.jbosh;

import com.kenai.jbosh.AbstractAttr;
import com.kenai.jbosh.BOSHException;

abstract class AbstractIntegerAttr extends AbstractAttr<Integer> {

   protected AbstractIntegerAttr(int var1) throws BOSHException {
      Integer var2 = Integer.valueOf(var1);
      super(var2);
   }

   protected AbstractIntegerAttr(String var1) throws BOSHException {
      Integer var2 = Integer.valueOf(parseInt(var1));
      super(var2);
   }

   private static int parseInt(String var0) throws BOSHException {
      try {
         int var1 = Integer.parseInt(var0);
         return var1;
      } catch (NumberFormatException var4) {
         String var3 = "Could not parse an integer from the value provided: " + var0;
         throw new BOSHException(var3, var4);
      }
   }

   protected final void checkMinValue(int var1) throws BOSHException {
      int var2 = ((Integer)this.getValue()).intValue();
      if(var2 < var1) {
         String var3 = "Illegal attribute value \'" + var2 + "\' provided.  " + "Must be >= " + var1;
         throw new BOSHException(var3);
      }
   }

   public int intValue() {
      return ((Integer)this.getValue()).intValue();
   }
}
