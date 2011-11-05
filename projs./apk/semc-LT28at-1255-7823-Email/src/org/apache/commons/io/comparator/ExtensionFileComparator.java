package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.ReverseComparator;

public class ExtensionFileComparator implements Comparator, Serializable {

   public static final Comparator EXTENSION_COMPARATOR = new ExtensionFileComparator();
   public static final Comparator EXTENSION_INSENSITIVE_COMPARATOR;
   public static final Comparator EXTENSION_INSENSITIVE_REVERSE;
   public static final Comparator EXTENSION_REVERSE;
   public static final Comparator EXTENSION_SYSTEM_COMPARATOR;
   public static final Comparator EXTENSION_SYSTEM_REVERSE;
   private final IOCase caseSensitivity;


   static {
      Comparator var0 = EXTENSION_COMPARATOR;
      EXTENSION_REVERSE = new ReverseComparator(var0);
      IOCase var1 = IOCase.INSENSITIVE;
      EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(var1);
      Comparator var2 = EXTENSION_INSENSITIVE_COMPARATOR;
      EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(var2);
      IOCase var3 = IOCase.SYSTEM;
      EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(var3);
      Comparator var4 = EXTENSION_SYSTEM_COMPARATOR;
      EXTENSION_SYSTEM_REVERSE = new ReverseComparator(var4);
   }

   public ExtensionFileComparator() {
      IOCase var1 = IOCase.SENSITIVE;
      this.caseSensitivity = var1;
   }

   public ExtensionFileComparator(IOCase var1) {
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
      String var5 = FilenameUtils.getExtension(var3.getName());
      String var6 = FilenameUtils.getExtension(var4.getName());
      return this.caseSensitivity.checkCompareTo(var5, var6);
   }
}
