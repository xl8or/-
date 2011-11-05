package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.IOFileFilter;

public class TrueFileFilter implements IOFileFilter, Serializable {

   public static final IOFileFilter INSTANCE = TRUE;
   public static final IOFileFilter TRUE = new TrueFileFilter();


   protected TrueFileFilter() {}

   public boolean accept(File var1) {
      return true;
   }

   public boolean accept(File var1, String var2) {
      return true;
   }
}
