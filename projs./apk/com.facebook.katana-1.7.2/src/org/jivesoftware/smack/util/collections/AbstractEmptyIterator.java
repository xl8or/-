package org.jivesoftware.smack.util.collections;

import java.util.NoSuchElementException;

abstract class AbstractEmptyIterator<E extends Object> {

   protected AbstractEmptyIterator() {}

   public void add(E var1) {
      throw new UnsupportedOperationException("add() not supported for empty Iterator");
   }

   public E getKey() {
      throw new IllegalStateException("Iterator contains no elements");
   }

   public E getValue() {
      throw new IllegalStateException("Iterator contains no elements");
   }

   public boolean hasNext() {
      return false;
   }

   public boolean hasPrevious() {
      return false;
   }

   public E next() {
      throw new NoSuchElementException("Iterator contains no elements");
   }

   public int nextIndex() {
      return 0;
   }

   public E previous() {
      throw new NoSuchElementException("Iterator contains no elements");
   }

   public int previousIndex() {
      return -1;
   }

   public void remove() {
      throw new IllegalStateException("Iterator contains no elements");
   }

   public void reset() {}

   public void set(E var1) {
      throw new IllegalStateException("Iterator contains no elements");
   }

   public E setValue(E var1) {
      throw new IllegalStateException("Iterator contains no elements");
   }
}
