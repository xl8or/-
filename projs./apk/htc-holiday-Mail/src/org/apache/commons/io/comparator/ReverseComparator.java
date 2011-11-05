package org.apache.commons.io.comparator;

import java.io.Serializable;
import java.util.Comparator;

class ReverseComparator implements Comparator, Serializable {

   private final Comparator delegate;


   public ReverseComparator(Comparator var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Delegate comparator is missing");
      } else {
         this.delegate = var1;
      }
   }

   public int compare(Object var1, Object var2) {
      return this.delegate.compare(var2, var1);
   }
}
