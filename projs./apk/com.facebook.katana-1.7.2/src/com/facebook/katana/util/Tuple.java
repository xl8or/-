package com.facebook.katana.util;


public class Tuple<T0 extends Object, T1 extends Object> {

   public final T0 d0;
   public final T1 d1;


   public Tuple(T0 var1, T1 var2) {
      this.d0 = var1;
      this.d1 = var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 != null && var1 instanceof Tuple) {
         Tuple var3 = (Tuple)var1;
         Object var4 = this.d0;
         Object var5 = var3.d0;
         if(var4 != var5 && this.d0 != null) {
            Object var6 = this.d0;
            Object var7 = var3.d0;
            if(!var6.equals(var7)) {
               var2 = false;
               return var2;
            }
         }

         Object var8 = this.d1;
         Object var9 = var3.d1;
         if(var8 != var9 && this.d1 != null) {
            Object var10 = this.d1;
            Object var11 = var3.d1;
            if(!var10.equals(var11)) {
               var2 = false;
               return var2;
            }
         }

         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      int var1 = 0;
      if(this.d0 != null) {
         int var2 = this.d0.hashCode();
         var1 = 0 ^ var2;
      }

      if(this.d1 != null) {
         int var3 = this.d1.hashCode();
         var1 ^= var3;
      }

      return var1;
   }

   public String toString() {
      String var1 = "<";
      if(this.d0 != null) {
         StringBuilder var2 = (new StringBuilder()).append("<");
         Object var3 = this.d0;
         var1 = var2.append(var3).toString();
      }

      String var4 = var1 + ":";
      if(this.d1 != null) {
         StringBuilder var5 = (new StringBuilder()).append(var4);
         Object var6 = this.d1;
         var4 = var5.append(var6).toString();
      }

      return var4 + ">";
   }
}
