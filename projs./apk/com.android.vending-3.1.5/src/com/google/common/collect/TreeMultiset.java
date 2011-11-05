package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@GwtCompatible
public final class TreeMultiset<E extends Object> extends AbstractMapBasedMultiset<E> {

   private static final long serialVersionUID;


   private TreeMultiset() {
      TreeMap var1 = new TreeMap();
      super(var1);
   }

   private TreeMultiset(Comparator<? super E> var1) {
      TreeMap var2 = new TreeMap(var1);
      super(var2);
   }

   public static <E extends Object & Comparable> TreeMultiset<E> create() {
      return new TreeMultiset();
   }

   public static <E extends Object & Comparable> TreeMultiset<E> create(Iterable<? extends E> var0) {
      TreeMultiset var1 = create();
      Iterables.addAll(var1, var0);
      return var1;
   }

   public static <E extends Object> TreeMultiset<E> create(Comparator<? super E> var0) {
      return new TreeMultiset(var0);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Comparator var2 = (Comparator)var1.readObject();
      TreeMap var3 = new TreeMap(var2);
      this.setBackingMap(var3);
      Serialization.populateMultiset(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Comparator var2 = this.elementSet().comparator();
      var1.writeObject(var2);
      Serialization.writeMultiset(this, var1);
   }

   public int count(@Nullable Object var1) {
      int var2 = 0;

      int var3;
      try {
         var3 = super.count(var1);
      } catch (NullPointerException var6) {
         return var2;
      } catch (ClassCastException var7) {
         return var2;
      }

      var2 = var3;
      return var2;
   }

   Set<E> createElementSet() {
      SortedMap var1 = (SortedMap)this.backingMap();
      return new TreeMultiset.SortedMapBasedElementSet(var1);
   }

   public SortedSet<E> elementSet() {
      return (SortedSet)super.elementSet();
   }

   private class SortedMapBasedElementSet extends AbstractMapBasedMultiset.MapBasedElementSet implements SortedSet<E> {

      SortedMapBasedElementSet(SortedMap var2) {
         super(var2);
      }

      public Comparator<? super E> comparator() {
         return this.sortedMap().comparator();
      }

      public E first() {
         return this.sortedMap().firstKey();
      }

      public SortedSet<E> headSet(E var1) {
         TreeMultiset var2 = TreeMultiset.this;
         SortedMap var3 = this.sortedMap().headMap(var1);
         return var2.new SortedMapBasedElementSet(var3);
      }

      public E last() {
         return this.sortedMap().lastKey();
      }

      public boolean remove(Object var1) {
         boolean var2 = false;

         boolean var3;
         try {
            var3 = super.remove(var1);
         } catch (NullPointerException var6) {
            return var2;
         } catch (ClassCastException var7) {
            return var2;
         }

         var2 = var3;
         return var2;
      }

      SortedMap<E, AtomicInteger> sortedMap() {
         return (SortedMap)this.getMap();
      }

      public SortedSet<E> subSet(E var1, E var2) {
         TreeMultiset var3 = TreeMultiset.this;
         SortedMap var4 = this.sortedMap().subMap(var1, var2);
         return var3.new SortedMapBasedElementSet(var4);
      }

      public SortedSet<E> tailSet(E var1) {
         TreeMultiset var2 = TreeMultiset.this;
         SortedMap var3 = this.sortedMap().tailMap(var1);
         return var2.new SortedMapBasedElementSet(var3);
      }
   }
}
