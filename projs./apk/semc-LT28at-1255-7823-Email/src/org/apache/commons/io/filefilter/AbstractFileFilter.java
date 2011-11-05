package org.apache.commons.io.filefilter;

import java.io.File;
import org.apache.commons.io.filefilter.IOFileFilter;

public abstract class AbstractFileFilter implements IOFileFilter {

   public AbstractFileFilter() {}

   public boolean accept(File var1) {
      File var2 = var1.getParentFile();
      String var3 = var1.getName();
      return this.accept(var2, var3);
   }

   public boolean accept(File var1, String var2) {
      File var3 = new File(var1, var2);
      return this.accept(var3);
   }

   public String toString() {
      String var1 = this.getClass().getName();
      int var2 = var1.lastIndexOf(46);
      String var4;
      if(var2 > 0) {
         int var3 = var2 + 1;
         var4 = var1.substring(var3);
      } else {
         var4 = var1;
      }

      return var4;
   }
}
