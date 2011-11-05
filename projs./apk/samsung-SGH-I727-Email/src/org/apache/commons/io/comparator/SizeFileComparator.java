package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.ReverseComparator;

public class SizeFileComparator implements Comparator, Serializable {

   public static final Comparator SIZE_COMPARATOR = new SizeFileComparator();
   public static final Comparator SIZE_REVERSE;
   public static final Comparator SIZE_SUMDIR_COMPARATOR;
   public static final Comparator SIZE_SUMDIR_REVERSE;
   private final boolean sumDirectoryContents;


   static {
      Comparator var0 = SIZE_COMPARATOR;
      SIZE_REVERSE = new ReverseComparator(var0);
      SIZE_SUMDIR_COMPARATOR = new SizeFileComparator((boolean)1);
      Comparator var1 = SIZE_SUMDIR_COMPARATOR;
      SIZE_SUMDIR_REVERSE = new ReverseComparator(var1);
   }

   public SizeFileComparator() {
      this.sumDirectoryContents = (boolean)0;
   }

   public SizeFileComparator(boolean var1) {
      this.sumDirectoryContents = var1;
   }

   public int compare(Object var1, Object var2) {
      File var3 = (File)var1;
      File var4 = (File)var2;
      long var5;
      if(var3.isDirectory()) {
         if(this.sumDirectoryContents && var3.exists()) {
            var5 = FileUtils.sizeOfDirectory(var3);
         } else {
            var5 = 0L;
         }
      } else {
         var5 = var3.length();
      }

      long var7;
      if(var4.isDirectory()) {
         if(this.sumDirectoryContents && var4.exists()) {
            var7 = FileUtils.sizeOfDirectory(var4);
         } else {
            var7 = 0L;
         }
      } else {
         var7 = var4.length();
      }

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
