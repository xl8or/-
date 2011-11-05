package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class DefaultFileComparator implements Comparator, Serializable {

   public static final Comparator DEFAULT_COMPARATOR = new DefaultFileComparator();
   public static final Comparator DEFAULT_REVERSE;


   static {
      Comparator var0 = DEFAULT_COMPARATOR;
      DEFAULT_REVERSE = new ReverseComparator(var0);
   }

   public DefaultFileComparator() {}

   public int compare(Object var1, Object var2) {
      File var3 = (File)var1;
      File var4 = (File)var2;
      return var3.compareTo(var4);
   }
}
