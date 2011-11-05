package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingCollection;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingSet<E extends Object> extends ForwardingCollection<E> implements Set<E> {

   public ForwardingSet() {}

   protected abstract Set<E> delegate();

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
