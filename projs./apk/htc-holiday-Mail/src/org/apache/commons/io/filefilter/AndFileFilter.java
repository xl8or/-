package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.ConditionalFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class AndFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {

   private List fileFilters;


   public AndFileFilter() {
      ArrayList var1 = new ArrayList();
      this.fileFilters = var1;
   }

   public AndFileFilter(List var1) {
      if(var1 == null) {
         ArrayList var2 = new ArrayList();
         this.fileFilters = var2;
      } else {
         ArrayList var3 = new ArrayList(var1);
         this.fileFilters = var3;
      }
   }

   public AndFileFilter(IOFileFilter var1, IOFileFilter var2) {
      if(var1 != null && var2 != null) {
         ArrayList var3 = new ArrayList();
         this.fileFilters = var3;
         this.addFileFilter(var1);
         this.addFileFilter(var2);
      } else {
         throw new IllegalArgumentException("The filters must not be null");
      }
   }

   public boolean accept(File var1) {
      boolean var2;
      if(this.fileFilters.size() == 0) {
         var2 = false;
      } else {
         Iterator var3 = this.fileFilters.iterator();

         while(true) {
            if(var3.hasNext()) {
               if(((IOFileFilter)var3.next()).accept(var1)) {
                  continue;
               }

               var2 = false;
               break;
            }

            var2 = true;
            break;
         }
      }

      return var2;
   }

   public boolean accept(File var1, String var2) {
      boolean var3;
      if(this.fileFilters.size() == 0) {
         var3 = false;
      } else {
         Iterator var4 = this.fileFilters.iterator();

         while(true) {
            if(var4.hasNext()) {
               if(((IOFileFilter)var4.next()).accept(var1, var2)) {
                  continue;
               }

               var3 = false;
               break;
            }

            var3 = true;
            break;
         }
      }

      return var3;
   }

   public void addFileFilter(IOFileFilter var1) {
      this.fileFilters.add(var1);
   }

   public List getFileFilters() {
      return Collections.unmodifiableList(this.fileFilters);
   }

   public boolean removeFileFilter(IOFileFilter var1) {
      return this.fileFilters.remove(var1);
   }

   public void setFileFilters(List var1) {
      ArrayList var2 = new ArrayList(var1);
      this.fileFilters = var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = super.toString();
      var1.append(var2);
      StringBuffer var4 = var1.append("(");
      if(this.fileFilters != null) {
         int var5 = 0;

         while(true) {
            int var6 = this.fileFilters.size();
            if(var5 >= var6) {
               break;
            }

            if(var5 > 0) {
               StringBuffer var7 = var1.append(",");
            }

            Object var8 = this.fileFilters.get(var5);
            String var9;
            if(var8 == null) {
               var9 = "null";
            } else {
               var9 = var8.toString();
            }

            var1.append(var9);
            ++var5;
         }
      }

      StringBuffer var11 = var1.append(")");
      return var1.toString();
   }
}
