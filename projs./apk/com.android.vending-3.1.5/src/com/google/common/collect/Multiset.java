package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multiset<E extends Object> extends Collection<E> {

   int add(@Nullable E var1, int var2);

   boolean add(E var1);

   boolean contains(@Nullable Object var1);

   boolean containsAll(Collection<?> var1);

   int count(@Nullable Object var1);

   Set<E> elementSet();

   Set<Multiset.Entry<E>> entrySet();

   boolean equals(@Nullable Object var1);

   int hashCode();

   Iterator<E> iterator();

   int remove(@Nullable Object var1, int var2);

   boolean remove(@Nullable Object var1);

   boolean removeAll(Collection<?> var1);

   boolean retainAll(Collection<?> var1);

   int setCount(E var1, int var2);

   boolean setCount(E var1, int var2, int var3);

   String toString();

   public interface Entry<E extends Object> {

      boolean equals(Object var1);

      int getCount();

      E getElement();

      int hashCode();

      String toString();
   }
}
