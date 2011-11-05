package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class EmptyFileFilter extends AbstractFileFilter implements Serializable {

   public static final IOFileFilter EMPTY = new EmptyFileFilter();
   public static final IOFileFilter NOT_EMPTY;


   static {
      IOFileFilter var0 = EMPTY;
      NOT_EMPTY = new NotFileFilter(var0);
   }

   protected EmptyFileFilter() {}

   public boolean accept(File var1) {
      boolean var3;
      if(var1.isDirectory()) {
         File[] var2 = var1.listFiles();
         if(var2 != null && var2.length != 0) {
            var3 = false;
         } else {
            var3 = true;
         }
      } else if(var1.length() == 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
