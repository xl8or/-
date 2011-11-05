package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class NotFileFilter extends AbstractFileFilter implements Serializable {

   private final IOFileFilter filter;


   public NotFileFilter(IOFileFilter var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The filter must not be null");
      } else {
         this.filter = var1;
      }
   }

   public boolean accept(File var1) {
      boolean var2;
      if(!this.filter.accept(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean accept(File var1, String var2) {
      boolean var3;
      if(!this.filter.accept(var1, var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = super.toString();
      StringBuilder var3 = var1.append(var2).append("(");
      String var4 = this.filter.toString();
      return var3.append(var4).append(")").toString();
   }
}
