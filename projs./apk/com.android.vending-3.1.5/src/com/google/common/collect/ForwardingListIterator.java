package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingIterator;
import java.util.ListIterator;

@GwtCompatible
public abstract class ForwardingListIterator<E extends Object> extends ForwardingIterator<E> implements ListIterator<E> {

   public ForwardingListIterator() {}

   public void add(E var1) {
      this.delegate().add(var1);
   }

   protected abstract ListIterator<E> delegate();

   public boolean hasPrevious() {
      return this.delegate().hasPrevious();
   }

   public int nextIndex() {
      return this.delegate().nextIndex();
   }

   public E previous() {
      return this.delegate().previous();
   }

   public int previousIndex() {
      return this.delegate().previousIndex();
   }

   public void set(E var1) {
      this.delegate().set(var1);
   }
}
