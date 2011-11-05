package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;

@GwtCompatible
public abstract class UnmodifiableIterator<E extends Object> implements Iterator<E> {

   public UnmodifiableIterator() {}

   public final void remove() {
      throw new UnsupportedOperationException();
   }
}
