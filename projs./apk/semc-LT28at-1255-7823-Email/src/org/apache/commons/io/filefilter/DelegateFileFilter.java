package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class DelegateFileFilter extends AbstractFileFilter implements Serializable {

   private final FileFilter fileFilter;
   private final FilenameFilter filenameFilter;


   public DelegateFileFilter(FileFilter var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The FileFilter must not be null");
      } else {
         this.fileFilter = var1;
         this.filenameFilter = null;
      }
   }

   public DelegateFileFilter(FilenameFilter var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The FilenameFilter must not be null");
      } else {
         this.filenameFilter = var1;
         this.fileFilter = null;
      }
   }

   public boolean accept(File var1) {
      boolean var2;
      if(this.fileFilter != null) {
         var2 = this.fileFilter.accept(var1);
      } else {
         var2 = super.accept(var1);
      }

      return var2;
   }

   public boolean accept(File var1, String var2) {
      boolean var3;
      if(this.filenameFilter != null) {
         var3 = this.filenameFilter.accept(var1, var2);
      } else {
         var3 = super.accept(var1, var2);
      }

      return var3;
   }

   public String toString() {
      String var1;
      if(this.fileFilter != null) {
         var1 = this.fileFilter.toString();
      } else {
         var1 = this.filenameFilter.toString();
      }

      StringBuilder var2 = new StringBuilder();
      String var3 = super.toString();
      return var2.append(var3).append("(").append(var1).append(")").toString();
   }
}
