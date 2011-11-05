package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingSet;
import java.util.Comparator;
import java.util.SortedSet;

@GwtCompatible
public abstract class ForwardingSortedSet<E extends Object> extends ForwardingSet<E> implements SortedSet<E> {

   public ForwardingSortedSet() {}

   public Comparator<? super E> comparator() {
      return this.delegate().comparator();
   }

   protected abstract SortedSet<E> delegate();

   public E first() {
      return this.delegate().first();
   }

   public SortedSet<E> headSet(E var1) {
      return this.delegate().headSet(var1);
   }

   public E last() {
      return this.delegate().last();
   }

   public SortedSet<E> subSet(E var1, E var2) {
      return this.delegate().subSet(var1, var2);
   }

   public SortedSet<E> tailSet(E var1) {
      return this.delegate().tailSet(var1);
   }
}
