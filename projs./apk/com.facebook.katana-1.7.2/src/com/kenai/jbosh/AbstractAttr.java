package com.kenai.jbosh;


abstract class AbstractAttr<T extends Object & Comparable> implements Comparable {

   private final T value;


   protected AbstractAttr(T var1) {
      this.value = var1;
   }

   public int compareTo(Object var1) {
      int var2;
      if(var1 == null) {
         var2 = 1;
      } else {
         var2 = this.value.compareTo(var1);
      }

      return var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(var1 instanceof AbstractAttr) {
         AbstractAttr var3 = (AbstractAttr)var1;
         Comparable var4 = this.value;
         Comparable var5 = var3.value;
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public final T getValue() {
      return this.value;
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public String toString() {
      return this.value.toString();
   }
}
