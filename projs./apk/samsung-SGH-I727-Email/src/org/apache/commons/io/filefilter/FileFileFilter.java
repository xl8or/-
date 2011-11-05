package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FileFileFilter extends AbstractFileFilter implements Serializable {

   public static final IOFileFilter FILE = new FileFileFilter();


   protected FileFileFilter() {}

   public boolean accept(File var1) {
      return var1.isFile();
   }
}
