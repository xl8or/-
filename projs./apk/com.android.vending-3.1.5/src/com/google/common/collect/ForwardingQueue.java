package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingCollection;
import java.util.Queue;

@GwtCompatible
public abstract class ForwardingQueue<E extends Object> extends ForwardingCollection<E> implements Queue<E> {

   public ForwardingQueue() {}

   protected abstract Queue<E> delegate();

   public E element() {
      return this.delegate().element();
   }

   public boolean offer(E var1) {
      return this.delegate().offer(var1);
   }

   public E peek() {
      return this.delegate().peek();
   }

   public E poll() {
      return this.delegate().poll();
   }

   public E remove() {
      return this.delegate().remove();
   }
}
