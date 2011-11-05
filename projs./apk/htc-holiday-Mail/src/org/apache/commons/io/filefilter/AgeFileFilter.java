package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class AgeFileFilter extends AbstractFileFilter implements Serializable {

   private final boolean acceptOlder;
   private final long cutoff;


   public AgeFileFilter(long var1) {
      this(var1, (boolean)1);
   }

   public AgeFileFilter(long var1, boolean var3) {
      this.acceptOlder = var3;
      this.cutoff = var1;
   }

   public AgeFileFilter(File var1) {
      this(var1, (boolean)1);
   }

   public AgeFileFilter(File var1, boolean var2) {
      long var3 = var1.lastModified();
      this(var3, var2);
   }

   public AgeFileFilter(Date var1) {
      this(var1, (boolean)1);
   }

   public AgeFileFilter(Date var1, boolean var2) {
      long var3 = var1.getTime();
      this(var3, var2);
   }

   public boolean accept(File var1) {
      long var2 = this.cutoff;
      byte var4 = FileUtils.isFileNewer(var1, var2);
      byte var5;
      if(this.acceptOlder) {
         if(var4 == 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }
      } else {
         var5 = var4;
      }

      return (boolean)var5;
   }

   public String toString() {
      String var1;
      if(this.acceptOlder) {
         var1 = "<=";
      } else {
         var1 = ">";
      }

      StringBuilder var2 = new StringBuilder();
      String var3 = super.toString();
      StringBuilder var4 = var2.append(var3).append("(").append(var1);
      long var5 = this.cutoff;
      return var4.append(var5).append(")").toString();
   }
}
