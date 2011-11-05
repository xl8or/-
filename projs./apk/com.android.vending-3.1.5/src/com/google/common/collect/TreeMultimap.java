package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSortedSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public class TreeMultimap<K extends Object, V extends Object> extends AbstractSortedSetMultimap<K, V> {

   private static final long serialVersionUID;
   private transient Comparator<? super K> keyComparator;
   private transient Comparator<? super V> valueComparator;


   TreeMultimap() {
      this((Comparator)null, (Comparator)null);
   }

   TreeMultimap(@Nullable Comparator<? super K> var1, @Nullable Comparator<? super V> var2) {
      TreeMap var3;
      if(var1 == null) {
         var3 = new TreeMap();
      } else {
         var3 = new TreeMap(var1);
      }

      super(var3);
      this.keyComparator = var1;
      this.valueComparator = var2;
   }

   private TreeMultimap(Comparator<? super K> var1, Comparator<? super V> var2, Multimap<? extends K, ? extends V> var3) {
      this(var1, var2);
      this.putAll(var3);
   }

   public static <K extends Object & Comparable, V extends Object & Comparable> TreeMultimap<K, V> create() {
      Ordering var0 = Ordering.natural();
      Ordering var1 = Ordering.natural();
      return new TreeMultimap(var0, var1);
   }

   public static <K extends Object & Comparable, V extends Object & Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      Ordering var1 = Ordering.natural();
      Ordering var2 = Ordering.natural();
      return new TreeMultimap(var1, var2, var0);
   }

   public static <K extends Object, V extends Object> TreeMultimap<K, V> create(Comparator<? super K> var0, Comparator<? super V> var1) {
      Comparator var2 = (Comparator)Preconditions.checkNotNull(var0);
      Comparator var3 = (Comparator)Preconditions.checkNotNull(var1);
      return new TreeMultimap(var2, var3);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Comparator var2 = (Comparator)var1.readObject();
      this.keyComparator = var2;
      Comparator var3 = (Comparator)var1.readObject();
      this.valueComparator = var3;
      Comparator var4 = this.keyComparator;
      TreeMap var5 = new TreeMap(var4);
      this.setMap(var5);
      Serialization.populateMultimap(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Comparator var2 = this.keyComparator();
      var1.writeObject(var2);
      Comparator var3 = this.valueComparator();
      var1.writeObject(var3);
      Serialization.writeMultimap(this, var1);
   }

   public SortedMap<K, Collection<V>> asMap() {
      return (SortedMap)super.asMap();
   }

   SortedSet<V> createCollection() {
      TreeSet var1;
      if(this.valueComparator == null) {
         var1 = new TreeSet();
      } else {
         Comparator var2 = this.valueComparator;
         var1 = new TreeSet(var2);
      }

      return var1;
   }

   public Comparator<? super K> keyComparator() {
      return this.keyComparator;
   }

   public SortedSet<K> keySet() {
      return (SortedSet)super.keySet();
   }

   public Comparator<? super V> valueComparator() {
      return this.valueComparator;
   }
}
