package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.ReverseComparator;

public class NameFileComparator implements Comparator, Serializable {

   public static final Comparator NAME_COMPARATOR = new NameFileComparator();
   public static final Comparator NAME_INSENSITIVE_COMPARATOR;
   public static final Comparator NAME_INSENSITIVE_REVERSE;
   public static final Comparator NAME_REVERSE;
   public static final Comparator NAME_SYSTEM_COMPARATOR;
   public static final Comparator NAME_SYSTEM_REVERSE;
   private final IOCase caseSensitivity;


   static {
      Comparator var0 = NAME_COMPARATOR;
      NAME_REVERSE = new ReverseComparator(var0);
      IOCase var1 = IOCase.INSENSITIVE;
      NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(var1);
      Comparator var2 = NAME_INSENSITIVE_COMPARATOR;
      NAME_INSENSITIVE_REVERSE = new ReverseComparator(var2);
      IOCase var3 = IOCase.SYSTEM;
      NAME_SYSTEM_COMPARATOR = new NameFileComparator(var3);
      Comparator var4 = NAME_SYSTEM_COMPARATOR;
      NAME_SYSTEM_REVERSE = new ReverseComparator(var4);
   }

   public NameFileComparator() {
      IOCase var1 = IOCase.SENSITIVE;
      this.caseSensitivity = var1;
   }

   public NameFileComparator(IOCase var1) {
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
      String var6 = var3.getName();
      String var7 = var4.getName();
      return var5.checkCompareTo(var6, var7);
   }
}
