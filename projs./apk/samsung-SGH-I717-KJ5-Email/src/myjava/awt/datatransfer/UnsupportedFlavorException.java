package myjava.awt.datatransfer;

import myjava.awt.datatransfer.DataFlavor;

public class UnsupportedFlavorException extends Exception {

   private static final long serialVersionUID = 5383814944251665601L;


   public UnsupportedFlavorException(DataFlavor var1) {
      StringBuilder var2 = new StringBuilder("flavor = ");
      String var3 = String.valueOf(var1);
      String var4 = var2.append(var3).toString();
      super(var4);
   }
}
