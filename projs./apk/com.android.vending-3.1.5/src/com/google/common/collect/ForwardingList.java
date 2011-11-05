package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Platform;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingList<E extends Object> extends ForwardingCollection<E> implements List<E> {

   public ForwardingList() {}

   public void add(int var1, E var2) {
      this.delegate().add(var1, var2);
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      return this.delegate().addAll(var1, var2);
   }

   protected abstract List<E> delegate();

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public E get(int var1) {
      return this.delegate().get(var1);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public int indexOf(Object var1) {
      return this.delegate().indexOf(var1);
   }

   public int lastIndexOf(Object var1) {
      return this.delegate().lastIndexOf(var1);
   }

   public ListIterator<E> listIterator() {
      return this.delegate().listIterator();
   }

   public ListIterator<E> listIterator(int var1) {
      return this.delegate().listIterator(var1);
   }

   public E remove(int var1) {
      return this.delegate().remove(var1);
   }

   public E set(int var1, E var2) {
      return this.delegate().set(var1, var2);
   }

   @GwtIncompatible("List.subList")
   public List<E> subList(int var1, int var2) {
      return Platform.subList(this.delegate(), var1, var2);
   }
}
