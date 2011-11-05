package com.facebook.katana.util;

import java.lang.ref.WeakReference;

public class WeakRef<T extends Object> extends WeakReference<T> {

   public WeakRef(T var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 != null && var1 instanceof WeakRef) {
         WeakRef var3 = (WeakRef)var1;
         Object var4 = this.get();
         Object var5 = var3.get();
         if(var4 != null && var5 != null) {
            var2 = var4.equals(var5);
         } else {
            var2 = false;
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      Object var1 = this.get();
      int var2;
      if(var1 != null) {
         var2 = var1.hashCode();
      } else {
         var2 = 0;
      }

      return var2;
   }
}
