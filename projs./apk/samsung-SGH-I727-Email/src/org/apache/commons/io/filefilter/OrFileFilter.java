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

public class OrFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {

   private List fileFilters;


   public OrFileFilter() {
      ArrayList var1 = new ArrayList();
      this.fileFilters = var1;
   }

   public OrFileFilter(List var1) {
      if(var1 == null) {
         ArrayList var2 = new ArrayList();
         this.fileFilters = var2;
      } else {
         ArrayList var3 = new ArrayList(var1);
         this.fileFilters = var3;
      }
   }

   public OrFileFilter(IOFileFilter var1, IOFileFilter var2) {
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
      Iterator var2 = this.fileFilters.iterator();

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(!((IOFileFilter)var2.next()).accept(var1)) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   public boolean accept(File var1, String var2) {
      Iterator var3 = this.fileFilters.iterator();

      boolean var4;
      while(true) {
         if(var3.hasNext()) {
            if(!((IOFileFilter)var3.next()).accept(var1, var2)) {
               continue;
            }

            var4 = true;
            break;
         }

         var4 = false;
         break;
      }

      return var4;
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
      this.fileFilters = var1;
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
