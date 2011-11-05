package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingObject;
import java.util.Iterator;

@GwtCompatible
public abstract class ForwardingIterator<T extends Object> extends ForwardingObject implements Iterator<T> {

   public ForwardingIterator() {}

   protected abstract Iterator<T> delegate();

   public boolean hasNext() {
      return this.delegate().hasNext();
   }

   public T next() {
      return this.delegate().next();
   }

   public void remove() {
      this.delegate().remove();
   }
}
