package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

public class CanWriteFileFilter extends AbstractFileFilter implements Serializable {

   public static final IOFileFilter CANNOT_WRITE;
   public static final IOFileFilter CAN_WRITE = new CanWriteFileFilter();


   static {
      IOFileFilter var0 = CAN_WRITE;
      CANNOT_WRITE = new NotFileFilter(var0);
   }

   protected CanWriteFileFilter() {}

   public boolean accept(File var1) {
      return var1.canWrite();
   }
}
