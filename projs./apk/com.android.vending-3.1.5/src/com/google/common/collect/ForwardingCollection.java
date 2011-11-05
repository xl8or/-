package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingObject;
import java.util.Collection;
import java.util.Iterator;

@GwtCompatible
public abstract class ForwardingCollection<E extends Object> extends ForwardingObject implements Collection<E> {

   public ForwardingCollection() {}

   public boolean add(E var1) {
      return this.delegate().add(var1);
   }

   public boolean addAll(Collection<? extends E> var1) {
      return this.delegate().addAll(var1);
   }

   public void clear() {
      this.delegate().clear();
   }

   public boolean contains(Object var1) {
      return this.delegate().contains(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      return this.delegate().containsAll(var1);
   }

   protected abstract Collection<E> delegate();

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public Iterator<E> iterator() {
      return this.delegate().iterator();
   }

   public boolean remove(Object var1) {
      return this.delegate().remove(var1);
   }

   public boolean removeAll(Collection<?> var1) {
      return this.delegate().removeAll(var1);
   }

   public boolean retainAll(Collection<?> var1) {
      return this.delegate().retainAll(var1);
   }

   public int size() {
      return this.delegate().size();
   }

   public Object[] toArray() {
      return this.delegate().toArray();
   }

   public <T extends Object> T[] toArray(T[] var1) {
      return this.delegate().toArray(var1);
   }
}
