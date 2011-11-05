package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class ByFunctionOrdering<F extends Object, T extends Object> extends Ordering<F> implements Serializable {

   private static final long serialVersionUID;
   final Function<F, ? extends T> function;
   final Ordering<T> ordering;


   ByFunctionOrdering(Function<F, ? extends T> var1, Ordering<T> var2) {
      Function var3 = (Function)Preconditions.checkNotNull(var1);
      this.function = var3;
      Ordering var4 = (Ordering)Preconditions.checkNotNull(var2);
      this.ordering = var4;
   }

   public int compare(F var1, F var2) {
      Ordering var3 = this.ordering;
      Object var4 = this.function.apply(var1);
      Object var5 = this.function.apply(var2);
      return var3.compare(var4, var5);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(var1 instanceof ByFunctionOrdering) {
            ByFunctionOrdering var3 = (ByFunctionOrdering)var1;
            Function var4 = this.function;
            Function var5 = var3.function;
            if(var4.equals(var5)) {
               Ordering var6 = this.ordering;
               Ordering var7 = var3.ordering;
               if(var6.equals(var7)) {
                  return var2;
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public int hashCode() {
      Object[] var1 = new Object[2];
      Function var2 = this.function;
      var1[0] = var2;
      Ordering var3 = this.ordering;
      var1[1] = var3;
      return Objects.hashCode(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Ordering var2 = this.ordering;
      StringBuilder var3 = var1.append(var2).append(".onResultOf(");
      Function var4 = this.function;
      return var3.append(var4).append(")").toString();
   }
}
