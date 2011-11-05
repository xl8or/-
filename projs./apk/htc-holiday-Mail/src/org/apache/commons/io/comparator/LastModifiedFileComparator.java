package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class LastModifiedFileComparator implements Comparator, Serializable {

   public static final Comparator LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
   public static final Comparator LASTMODIFIED_REVERSE;


   static {
      Comparator var0 = LASTMODIFIED_COMPARATOR;
      LASTMODIFIED_REVERSE = new ReverseComparator(var0);
   }

   public LastModifiedFileComparator() {}

   public int compare(Object var1, Object var2) {
      File var3 = (File)var1;
      File var4 = (File)var2;
      long var5 = var3.lastModified();
      long var7 = var4.lastModified();
      long var9 = var5 - var7;
      byte var11;
      if(var9 < 0L) {
         var11 = -1;
      } else if(var9 > 0L) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      return var11;
   }
}
