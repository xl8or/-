package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.ReverseComparator;

public class PathFileComparator implements Comparator, Serializable {

   public static final Comparator PATH_COMPARATOR = new PathFileComparator();
   public static final Comparator PATH_INSENSITIVE_COMPARATOR;
   public static final Comparator PATH_INSENSITIVE_REVERSE;
   public static final Comparator PATH_REVERSE;
   public static final Comparator PATH_SYSTEM_COMPARATOR;
   public static final Comparator PATH_SYSTEM_REVERSE;
   private final IOCase caseSensitivity;


   static {
      Comparator var0 = PATH_COMPARATOR;
      PATH_REVERSE = new ReverseComparator(var0);
      IOCase var1 = IOCase.INSENSITIVE;
      PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(var1);
      Comparator var2 = PATH_INSENSITIVE_COMPARATOR;
      PATH_INSENSITIVE_REVERSE = new ReverseComparator(var2);
      IOCase var3 = IOCase.SYSTEM;
      PATH_SYSTEM_COMPARATOR = new PathFileComparator(var3);
      Comparator var4 = PATH_SYSTEM_COMPARATOR;
      PATH_SYSTEM_REVERSE = new ReverseComparator(var4);
   }

   public PathFileComparator() {
      IOCase var1 = IOCase.SENSITIVE;
      this.caseSensitivity = var1;
   }

   public PathFileComparator(IOCase var1) {
      IOCase var2;
      if(var1 == null) {
         var2 = IOCase.SENSITIVE;
      } else {
         var2 = var1;
      }

      this.caseSensitivity = var2;
   }

   public int compare(Object var1, Object var2) {
      File var3 = (File)var1;
      File var4 = (File)var2;
      IOCase var5 = this.caseSensitivity;
      String var6 = var3.getPath();
      String var7 = var4.getPath();
      return var5.checkCompareTo(var6, var7);
   }
}
