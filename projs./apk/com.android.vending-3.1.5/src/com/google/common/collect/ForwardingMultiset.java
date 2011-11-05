package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Multiset;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMultiset<E extends Object> extends ForwardingCollection<E> implements Multiset<E> {

   public ForwardingMultiset() {}

   public int add(E var1, int var2) {
      return this.delegate().add(var1, var2);
   }

   public int count(Object var1) {
      return this.delegate().count(var1);
   }

   protected abstract Multiset<E> delegate();

   public Set<E> elementSet() {
      return this.delegate().elementSet();
   }

   public Set<Multiset.Entry<E>> entrySet() {
      return this.delegate().entrySet();
   }

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

   public int remove(Object var1, int var2) {
      return this.delegate().remove(var1, var2);
   }

   public int setCount(E var1, int var2) {
      return this.delegate().setCount(var1, var2);
   }

   public boolean setCount(E var1, int var2, int var3) {
      return this.delegate().setCount(var1, var2, var3);
   }
}
