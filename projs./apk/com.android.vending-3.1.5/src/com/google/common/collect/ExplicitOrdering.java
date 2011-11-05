package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
final class ExplicitOrdering<T extends Object> extends Ordering<T> implements Serializable {

   private static final long serialVersionUID;
   final ImmutableMap<T, Integer> rankMap;


   ExplicitOrdering(ImmutableMap<T, Integer> var1) {
      this.rankMap = var1;
   }

   ExplicitOrdering(List<T> var1) {
      ImmutableMap var2 = buildRankMap(var1);
      this(var2);
   }

   private static <T extends Object> ImmutableMap<T, Integer> buildRankMap(List<T> var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      int var2 = 0;

      int var5;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var2 = var5) {
         Object var4 = var3.next();
         var5 = var2 + 1;
         Integer var6 = Integer.valueOf(var2);
         var1.put(var4, var6);
      }

      return var1.build();
   }

   private int rank(T var1) {
      Integer var2 = (Integer)this.rankMap.get(var1);
      if(var2 == null) {
         throw new Ordering.IncomparableValueException(var1);
      } else {
         return var2.intValue();
      }
   }

   public int compare(T var1, T var2) {
      int var3 = this.rank(var1);
      int var4 = this.rank(var2);
      return var3 - var4;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var5;
      if(var1 instanceof ExplicitOrdering) {
         ExplicitOrdering var2 = (ExplicitOrdering)var1;
         ImmutableMap var3 = this.rankMap;
         ImmutableMap var4 = var2.rankMap;
         var5 = var3.equals(var4);
      } else {
         var5 = false;
      }

      return var5;
   }

   public int hashCode() {
      return this.rankMap.hashCode();
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("Ordering.explicit(");
      ImmutableSet var2 = this.rankMap.keySet();
      return var1.append(var2).append(")").toString();
   }
}
