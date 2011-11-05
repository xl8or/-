package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class SizeFileFilter extends AbstractFileFilter implements Serializable {

   private final boolean acceptLarger;
   private final long size;


   public SizeFileFilter(long var1) {
      this(var1, (boolean)1);
   }

   public SizeFileFilter(long var1, boolean var3) {
      if(var1 < 0L) {
         throw new IllegalArgumentException("The size must be non-negative");
      } else {
         this.size = var1;
         this.acceptLarger = var3;
      }
   }

   public boolean accept(File var1) {
      long var2 = var1.length();
      long var4 = this.size;
      boolean var6;
      if(var2 < var4) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7;
      if(this.acceptLarger) {
         if(!var6) {
            var7 = true;
         } else {
            var7 = false;
         }
      } else {
         var7 = var6;
      }

      return var7;
   }

   public String toString() {
      String var1;
      if(this.acceptLarger) {
         var1 = ">=";
      } else {
         var1 = "<";
      }

      StringBuilder var2 = new StringBuilder();
      String var3 = super.toString();
      StringBuilder var4 = var2.append(var3).append("(").append(var1);
      long var5 = this.size;
      return var4.append(var5).append(")").toString();
   }
}
