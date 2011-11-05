package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class HiddenFileFilter extends AbstractFileFilter implements Serializable {

   public static final IOFileFilter HIDDEN = new HiddenFileFilter();
   public static final IOFileFilter VISIBLE;


   static {
      IOFileFilter var0 = HIDDEN;
      VISIBLE = new NotFileFilter(var0);
   }

   protected HiddenFileFilter() {}

   public boolean accept(File var1) {
      return var1.isHidden();
   }
}
