package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.CanWriteFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class CanReadFileFilter extends AbstractFileFilter implements Serializable {

   public static final IOFileFilter CANNOT_READ;
   public static final IOFileFilter CAN_READ = new CanReadFileFilter();
   public static final IOFileFilter READ_ONLY;


   static {
      IOFileFilter var0 = CAN_READ;
      CANNOT_READ = new NotFileFilter(var0);
      IOFileFilter var1 = CAN_READ;
      IOFileFilter var2 = CanWriteFileFilter.CANNOT_WRITE;
      READ_ONLY = new AndFileFilter(var1, var2);
   }

   protected CanReadFileFilter() {}

   public boolean accept(File var1) {
      return var1.canRead();
   }
}
