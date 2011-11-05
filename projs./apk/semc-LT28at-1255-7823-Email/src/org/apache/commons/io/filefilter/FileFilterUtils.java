package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Date;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DelegateFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SizeFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileFilterUtils {

   private static IOFileFilter cvsFilter;
   private static IOFileFilter svnFilter;


   public FileFilterUtils() {}

   public static IOFileFilter ageFileFilter(long var0) {
      return new AgeFileFilter(var0);
   }

   public static IOFileFilter ageFileFilter(long var0, boolean var2) {
      return new AgeFileFilter(var0, var2);
   }

   public static IOFileFilter ageFileFilter(File var0) {
      return new AgeFileFilter(var0);
   }

   public static IOFileFilter ageFileFilter(File var0, boolean var1) {
      return new AgeFileFilter(var0, var1);
   }

   public static IOFileFilter ageFileFilter(Date var0) {
      return new AgeFileFilter(var0);
   }

   public static IOFileFilter ageFileFilter(Date var0, boolean var1) {
      return new AgeFileFilter(var0, var1);
   }

   public static IOFileFilter andFileFilter(IOFileFilter var0, IOFileFilter var1) {
      return new AndFileFilter(var0, var1);
   }

   public static IOFileFilter asFileFilter(FileFilter var0) {
      return new DelegateFileFilter(var0);
   }

   public static IOFileFilter asFileFilter(FilenameFilter var0) {
      return new DelegateFileFilter(var0);
   }

   public static IOFileFilter directoryFileFilter() {
      return DirectoryFileFilter.DIRECTORY;
   }

   public static IOFileFilter falseFileFilter() {
      return FalseFileFilter.FALSE;
   }

   public static IOFileFilter fileFileFilter() {
      return FileFileFilter.FILE;
   }

   public static IOFileFilter makeCVSAware(IOFileFilter var0) {
      if(cvsFilter == null) {
         IOFileFilter var1 = directoryFileFilter();
         IOFileFilter var2 = nameFileFilter("CVS");
         cvsFilter = notFileFilter(andFileFilter(var1, var2));
      }

      IOFileFilter var3;
      if(var0 == null) {
         var3 = cvsFilter;
      } else {
         IOFileFilter var4 = cvsFilter;
         var3 = andFileFilter(var0, var4);
      }

      return var3;
   }

   public static IOFileFilter makeDirectoryOnly(IOFileFilter var0) {
      Object var1;
      if(var0 == null) {
         var1 = DirectoryFileFilter.DIRECTORY;
      } else {
         IOFileFilter var2 = DirectoryFileFilter.DIRECTORY;
         var1 = new AndFileFilter(var2, var0);
      }

      return (IOFileFilter)var1;
   }

   public static IOFileFilter makeFileOnly(IOFileFilter var0) {
      Object var1;
      if(var0 == null) {
         var1 = FileFileFilter.FILE;
      } else {
         IOFileFilter var2 = FileFileFilter.FILE;
         var1 = new AndFileFilter(var2, var0);
      }

      return (IOFileFilter)var1;
   }

   public static IOFileFilter makeSVNAware(IOFileFilter var0) {
      if(svnFilter == null) {
         IOFileFilter var1 = directoryFileFilter();
         IOFileFilter var2 = nameFileFilter(".svn");
         svnFilter = notFileFilter(andFileFilter(var1, var2));
      }

      IOFileFilter var3;
      if(var0 == null) {
         var3 = svnFilter;
      } else {
         IOFileFilter var4 = svnFilter;
         var3 = andFileFilter(var0, var4);
      }

      return var3;
   }

   public static IOFileFilter nameFileFilter(String var0) {
      return new NameFileFilter(var0);
   }

   public static IOFileFilter notFileFilter(IOFileFilter var0) {
      return new NotFileFilter(var0);
   }

   public static IOFileFilter orFileFilter(IOFileFilter var0, IOFileFilter var1) {
      return new OrFileFilter(var0, var1);
   }

   public static IOFileFilter prefixFileFilter(String var0) {
      return new PrefixFileFilter(var0);
   }

   public static IOFileFilter sizeFileFilter(long var0) {
      return new SizeFileFilter(var0);
   }

   public static IOFileFilter sizeFileFilter(long var0, boolean var2) {
      return new SizeFileFilter(var0, var2);
   }

   public static IOFileFilter sizeRangeFileFilter(long var0, long var2) {
      SizeFileFilter var4 = new SizeFileFilter(var0, (boolean)1);
      long var5 = 1L + var2;
      SizeFileFilter var7 = new SizeFileFilter(var5, (boolean)0);
      return new AndFileFilter(var4, var7);
   }

   public static IOFileFilter suffixFileFilter(String var0) {
      return new SuffixFileFilter(var0);
   }

   public static IOFileFilter trueFileFilter() {
      return TrueFileFilter.TRUE;
   }
}
