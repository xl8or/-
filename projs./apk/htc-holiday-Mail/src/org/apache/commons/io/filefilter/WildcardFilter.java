package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class WildcardFilter extends AbstractFileFilter implements Serializable {

   private final String[] wildcards;


   public WildcardFilter(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The wildcard must not be null");
      } else {
         String[] var2 = new String[]{var1};
         this.wildcards = var2;
      }
   }

   public WildcardFilter(List var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The wildcard list must not be null");
      } else {
         String[] var2 = new String[var1.size()];
         String[] var3 = (String[])((String[])var1.toArray(var2));
         this.wildcards = var3;
      }
   }

   public WildcardFilter(String[] var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The wildcard array must not be null");
      } else {
         this.wildcards = var1;
      }
   }

   public boolean accept(File var1) {
      boolean var2;
      if(var1.isDirectory()) {
         var2 = false;
      } else {
         int var3 = 0;

         while(true) {
            int var4 = this.wildcards.length;
            if(var3 >= var4) {
               var2 = false;
               break;
            }

            String var5 = var1.getName();
            String var6 = this.wildcards[var3];
            if(FilenameUtils.wildcardMatch(var5, var6)) {
               var2 = true;
               break;
            }

            ++var3;
         }
      }

      return var2;
   }

   public boolean accept(File var1, String var2) {
      boolean var3;
      if(var1 != null && (new File(var1, var2)).isDirectory()) {
         var3 = false;
      } else {
         int var4 = 0;

         while(true) {
            int var5 = this.wildcards.length;
            if(var4 >= var5) {
               var3 = false;
               break;
            }

            String var6 = this.wildcards[var4];
            if(FilenameUtils.wildcardMatch(var2, var6)) {
               var3 = true;
               break;
            }

            ++var4;
         }
      }

      return var3;
   }
}
