package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.util.Comparator;

public class FlavorsComparator implements Comparator<DataFlavor> {

   public FlavorsComparator() {}

   public int compare(DataFlavor var1, DataFlavor var2) {
      byte var3;
      if(!var1.isFlavorTextType() && !var2.isFlavorTextType()) {
         var3 = 0;
      } else if(!var1.isFlavorTextType() && var2.isFlavorTextType()) {
         var3 = -1;
      } else if(var1.isFlavorTextType() && !var2.isFlavorTextType()) {
         var3 = 1;
      } else {
         DataFlavor[] var4 = new DataFlavor[]{var1, var2};
         if(DataFlavor.selectBestTextFlavor(var4) == var1) {
            var3 = -1;
         } else {
            var3 = 1;
         }
      }

      return var3;
   }
}
