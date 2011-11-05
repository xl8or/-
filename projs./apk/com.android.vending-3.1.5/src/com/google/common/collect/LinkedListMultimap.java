package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(
   serializable = true
)
public final class LinkedListMultimap<K extends Object, V extends Object> implements ListMultimap<K, V>, Serializable {

   private static final long serialVersionUID;
   private transient Collection<Entry<K, V>> entries;
   private transient LinkedListMultimap.Node<K, V> head;
   private transient Multiset<K> keyCount;
   private transient Set<K> keySet;
   private transient Map<K, LinkedListMultimap.Node<K, V>> keyToKeyHead;
   private transient Map<K, LinkedListMultimap.Node<K, V>> keyToKeyTail;
   private transient Multiset<K> keys;
   private transient Map<K, Collection<V>> map;
   private transient LinkedListMultimap.Node<K, V> tail;
   private transient Collection<V> valuesCollection;


   private LinkedListMultimap() {
      LinkedHashMultiset var1 = LinkedHashMultiset.create();
      this.keyCount = var1;
      HashMap var2 = Maps.newHashMap();
      this.keyToKeyHead = var2;
      HashMap var3 = Maps.newHashMap();
      this.keyToKeyTail = var3;
   }

   private LinkedListMultimap(int var1) {
      LinkedHashMultiset var2 = LinkedHashMultiset.create(var1);
      this.keyCount = var2;
      HashMap var3 = Maps.newHashMapWithExpectedSize(var1);
      this.keyToKeyHead = var3;
      HashMap var4 = Maps.newHashMapWithExpectedSize(var1);
      this.keyToKeyTail = var4;
   }

   private LinkedListMultimap(Multimap<? extends K, ? extends V> var1) {
      int var2 = var1.keySet().size();
      this(var2);
      this.putAll(var1);
   }

   private LinkedListMultimap.Node<K, V> addNode(@Nullable K var1, @Nullable V var2, @Nullable LinkedListMultimap.Node<K, V> var3) {
      LinkedListMultimap.Node var4 = new LinkedListMultimap.Node(var1, var2);
      if(this.head == null) {
         this.tail = var4;
         this.head = var4;
         this.keyToKeyHead.put(var1, var4);
         this.keyToKeyTail.put(var1, var4);
      } else if(var3 == null) {
         this.tail.next = var4;
         LinkedListMultimap.Node var8 = this.tail;
         var4.previous = var8;
         LinkedListMultimap.Node var9 = (LinkedListMultimap.Node)this.keyToKeyTail.get(var1);
         if(var9 == null) {
            this.keyToKeyHead.put(var1, var4);
         } else {
            var9.nextSibling = var4;
            var4.previousSibling = var9;
         }

         this.keyToKeyTail.put(var1, var4);
         this.tail = var4;
      } else {
         LinkedListMultimap.Node var12 = var3.previous;
         var4.previous = var12;
         LinkedListMultimap.Node var13 = var3.previousSibling;
         var4.previousSibling = var13;
         var4.next = var3;
         var4.nextSibling = var3;
         if(var3.previousSibling == null) {
            this.keyToKeyHead.put(var1, var4);
         } else {
            var3.previousSibling.nextSibling = var4;
         }

         if(var3.previous == null) {
            this.head = var4;
         } else {
            var3.previous.next = var4;
         }

         var3.previous = var4;
         var3.previousSibling = var4;
      }

      this.keyCount.add(var1);
      return var4;
   }

   private static void checkElement(@Nullable Object var0) {
      if(var0 == null) {
         throw new NoSuchElementException();
      }
   }

   public static <K extends Object, V extends Object> LinkedListMultimap<K, V> create() {
      return new LinkedListMultimap();
   }

   public static <K extends Object, V extends Object> LinkedListMultimap<K, V> create(int var0) {
      return new LinkedListMultimap(var0);
   }

   public static <K extends Object, V extends Object> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new LinkedListMultimap(var0);
   }

   private List<V> getCopy(@Nullable Object var1) {
      return Collections.unmodifiableList(Lists.newArrayList((Iterator)(new LinkedListMultimap.ValueForKeyIterator(var1))));
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      LinkedHashMultiset var2 = LinkedHashMultiset.create();
      this.keyCount = var2;
      HashMap var3 = Maps.newHashMap();
      this.keyToKeyHead = var3;
      HashMap var4 = Maps.newHashMap();
      this.keyToKeyTail = var4;
      int var5 = var1.readInt();

      for(int var6 = 0; var6 < var5; ++var6) {
         Object var7 = var1.readObject();
         Object var8 = var1.readObject();
         this.put(var7, var8);
      }

   }

   private void removeAllNodes(@Nullable Object var1) {
      LinkedListMultimap.ValueForKeyIterator var2 = new LinkedListMultimap.ValueForKeyIterator(var1);

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var2.remove();
      }

   }

   private void removeNode(LinkedListMultimap.Node<K, V> var1) {
      if(var1.previous != null) {
         LinkedListMultimap.Node var2 = var1.previous;
         LinkedListMultimap.Node var3 = var1.next;
         var2.next = var3;
      } else {
         LinkedListMultimap.Node var13 = var1.next;
         this.head = var13;
      }

      if(var1.next != null) {
         LinkedListMultimap.Node var4 = var1.next;
         LinkedListMultimap.Node var5 = var1.previous;
         var4.previous = var5;
      } else {
         LinkedListMultimap.Node var14 = var1.previous;
         this.tail = var14;
      }

      if(var1.previousSibling != null) {
         LinkedListMultimap.Node var6 = var1.previousSibling;
         LinkedListMultimap.Node var7 = var1.nextSibling;
         var6.nextSibling = var7;
      } else if(var1.nextSibling != null) {
         Map var15 = this.keyToKeyHead;
         Object var16 = var1.key;
         LinkedListMultimap.Node var17 = var1.nextSibling;
         var15.put(var16, var17);
      } else {
         Map var19 = this.keyToKeyHead;
         Object var20 = var1.key;
         var19.remove(var20);
      }

      if(var1.nextSibling != null) {
         LinkedListMultimap.Node var8 = var1.nextSibling;
         LinkedListMultimap.Node var9 = var1.previousSibling;
         var8.previousSibling = var9;
      } else if(var1.previousSibling != null) {
         Map var22 = this.keyToKeyTail;
         Object var23 = var1.key;
         LinkedListMultimap.Node var24 = var1.previousSibling;
         var22.put(var23, var24);
      } else {
         Map var26 = this.keyToKeyTail;
         Object var27 = var1.key;
         var26.remove(var27);
      }

      Multiset var10 = this.keyCount;
      Object var11 = var1.key;
      var10.remove(var11);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      int var2 = this.size();
      var1.writeInt(var2);
      Iterator var3 = this.entries().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         var1.writeObject(var5);
         Object var6 = var4.getValue();
         var1.writeObject(var6);
      }

   }

   public Map<K, Collection<V>> asMap() {
      Object var1 = this.map;
      if(var1 == null) {
         var1 = new LinkedListMultimap.5();
         this.map = (Map)var1;
      }

      return (Map)var1;
   }

   public void clear() {
      this.head = null;
      this.tail = null;
      this.keyCount.clear();
      this.keyToKeyHead.clear();
      this.keyToKeyTail.clear();
   }

   public boolean containsEntry(@Nullable Object var1, @Nullable Object var2) {
      LinkedListMultimap.ValueForKeyIterator var3 = new LinkedListMultimap.ValueForKeyIterator(var1);

      boolean var4;
      while(true) {
         if(var3.hasNext()) {
            if(!Objects.equal(var3.next(), var2)) {
               continue;
            }

            var4 = true;
            break;
         }

         var4 = false;
         break;
      }

      return var4;
   }

   public boolean containsKey(@Nullable Object var1) {
      return this.keyToKeyHead.containsKey(var1);
   }

   public boolean containsValue(@Nullable Object var1) {
      LinkedListMultimap.NodeIterator var2 = new LinkedListMultimap.NodeIterator((LinkedListMultimap.1)null);

      boolean var3;
      while(true) {
         if(var2.hasNext()) {
            if(!Objects.equal(((LinkedListMultimap.Node)var2.next()).value, var1)) {
               continue;
            }

            var3 = true;
            break;
         }

         var3 = false;
         break;
      }

      return var3;
   }

   public Collection<Entry<K, V>> entries() {
      Object var1 = this.entries;
      if(var1 == null) {
         var1 = new LinkedListMultimap.4();
         this.entries = (Collection)var1;
      }

      return (Collection)var1;
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 instanceof Multimap) {
         Multimap var3 = (Multimap)var1;
         Map var4 = this.asMap();
         Map var5 = var3.asMap();
         var2 = var4.equals(var5);
      } else {
         var2 = false;
      }

      return var2;
   }

   public List<V> get(@Nullable K var1) {
      return new LinkedListMultimap.1(var1);
   }

   public int hashCode() {
      return this.asMap().hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.head == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Set<K> keySet() {
      Object var1 = this.keySet;
      if(var1 == null) {
         var1 = new LinkedListMultimap.2();
         this.keySet = (Set)var1;
      }

      return (Set)var1;
   }

   public Multiset<K> keys() {
      Object var1 = this.keys;
      if(var1 == null) {
         var1 = new LinkedListMultimap.MultisetView((LinkedListMultimap.1)null);
         this.keys = (Multiset)var1;
      }

      return (Multiset)var1;
   }

   public boolean put(@Nullable K var1, @Nullable V var2) {
      this.addNode(var1, var2, (LinkedListMultimap.Node)null);
      return true;
   }

   public boolean putAll(Multimap<? extends K, ? extends V> var1) {
      boolean var2 = false;

      boolean var7;
      for(Iterator var3 = var1.entries().iterator(); var3.hasNext(); var2 |= var7) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         Object var6 = var4.getValue();
         var7 = this.put(var5, var6);
      }

      return var2;
   }

   public boolean putAll(@Nullable K var1, Iterable<? extends V> var2) {
      boolean var3 = false;

      boolean var6;
      for(Iterator var4 = var2.iterator(); var4.hasNext(); var3 |= var6) {
         Object var5 = var4.next();
         var6 = this.put(var1, var5);
      }

      return var3;
   }

   public boolean remove(@Nullable Object var1, @Nullable Object var2) {
      LinkedListMultimap.ValueForKeyIterator var3 = new LinkedListMultimap.ValueForKeyIterator(var1);

      boolean var4;
      while(true) {
         if(var3.hasNext()) {
            if(!Objects.equal(var3.next(), var2)) {
               continue;
            }

            var3.remove();
            var4 = true;
            break;
         }

         var4 = false;
         break;
      }

      return var4;
   }

   public List<V> removeAll(@Nullable Object var1) {
      List var2 = this.getCopy(var1);
      this.removeAllNodes(var1);
      return var2;
   }

   public List<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2) {
      List var3 = this.getCopy(var1);
      LinkedListMultimap.ValueForKeyIterator var4 = new LinkedListMultimap.ValueForKeyIterator(var1);
      Iterator var5 = var2.iterator();

      while(var4.hasNext() && var5.hasNext()) {
         Object var6 = var4.next();
         Object var7 = var5.next();
         var4.set(var7);
      }

      while(var4.hasNext()) {
         Object var8 = var4.next();
         var4.remove();
      }

      while(var5.hasNext()) {
         Object var9 = var5.next();
         var4.add(var9);
      }

      return var3;
   }

   public int size() {
      return this.keyCount.size();
   }

   public String toString() {
      return this.asMap().toString();
   }

   public Collection<V> values() {
      Object var1 = this.valuesCollection;
      if(var1 == null) {
         var1 = new LinkedListMultimap.3();
         this.valuesCollection = (Collection)var1;
      }

      return (Collection)var1;
   }

   class 5 extends AbstractMap<K, Collection<V>> {

      Set<Entry<K, Collection<V>>> entrySet;


      5() {}

      public boolean containsKey(@Nullable Object var1) {
         return LinkedListMultimap.this.containsKey(var1);
      }

      public Set<Entry<K, Collection<V>>> entrySet() {
         Object var1 = this.entrySet;
         if(var1 == null) {
            LinkedListMultimap var2 = LinkedListMultimap.this;
            var1 = var2.new AsMapEntries((LinkedListMultimap.1)null);
            this.entrySet = (Set)var1;
         }

         return (Set)var1;
      }

      public Collection<V> get(@Nullable Object var1) {
         List var2 = LinkedListMultimap.this.get(var1);
         if(var2.isEmpty()) {
            var2 = null;
         }

         return var2;
      }

      public Collection<V> remove(@Nullable Object var1) {
         List var2 = LinkedListMultimap.this.removeAll(var1);
         if(var2.isEmpty()) {
            var2 = null;
         }

         return var2;
      }
   }

   private class DistinctKeyIterator implements Iterator<K> {

      LinkedListMultimap.Node<K, V> current;
      LinkedListMultimap.Node<K, V> next;
      final Set<K> seenKeys;


      private DistinctKeyIterator() {
         int var2 = Maps.capacity(LinkedListMultimap.this.keySet().size());
         HashSet var3 = new HashSet(var2);
         this.seenKeys = var3;
         LinkedListMultimap.Node var4 = LinkedListMultimap.this.head;
         this.next = var4;
      }

      // $FF: synthetic method
      DistinctKeyIterator(LinkedListMultimap.1 var2) {
         this();
      }

      public boolean hasNext() {
         boolean var1;
         if(this.next != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public K next() {
         LinkedListMultimap.checkElement(this.next);
         LinkedListMultimap.Node var1 = this.next;
         this.current = var1;
         Set var2 = this.seenKeys;
         Object var3 = this.current.key;
         var2.add(var3);

         Set var6;
         Object var7;
         do {
            LinkedListMultimap.Node var5 = this.next.next;
            this.next = var5;
            if(this.next == null) {
               break;
            }

            var6 = this.seenKeys;
            var7 = this.next.key;
         } while(!var6.add(var7));

         return this.current.key;
      }

      public void remove() {
         byte var1;
         if(this.current != null) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1);
         LinkedListMultimap var2 = LinkedListMultimap.this;
         Object var3 = this.current.key;
         var2.removeAllNodes(var3);
         this.current = null;
      }
   }

   class 4 extends AbstractCollection<Entry<K, V>> {

      4() {}

      public Iterator<Entry<K, V>> iterator() {
         LinkedListMultimap var1 = LinkedListMultimap.this;
         LinkedListMultimap.NodeIterator var2 = var1.new NodeIterator((LinkedListMultimap.1)null);
         return new LinkedListMultimap.4.1(var2);
      }

      public int size() {
         return LinkedListMultimap.this.keyCount.size();
      }

      class 1 implements Iterator<Entry<K, V>> {

         // $FF: synthetic field
         final Iterator val$nodes;


         1(Iterator var2) {
            this.val$nodes = var2;
         }

         public boolean hasNext() {
            return this.val$nodes.hasNext();
         }

         public Entry<K, V> next() {
            LinkedListMultimap.Node var1 = (LinkedListMultimap.Node)this.val$nodes.next();
            return new LinkedListMultimap.4.1.1(var1);
         }

         public void remove() {
            this.val$nodes.remove();
         }

         class 1 extends AbstractMapEntry<K, V> {

            // $FF: synthetic field
            final LinkedListMultimap.Node val$node;


            1(LinkedListMultimap.Node var2) {
               this.val$node = var2;
            }

            public K getKey() {
               return this.val$node.key;
            }

            public V getValue() {
               return this.val$node.value;
            }

            public V setValue(V var1) {
               Object var2 = this.val$node.value;
               this.val$node.value = var1;
               return var2;
            }
         }
      }
   }

   class 3 extends AbstractCollection<V> {

      3() {}

      public Iterator<V> iterator() {
         LinkedListMultimap var1 = LinkedListMultimap.this;
         LinkedListMultimap.NodeIterator var2 = var1.new NodeIterator((LinkedListMultimap.1)null);
         return new LinkedListMultimap.3.1(var2);
      }

      public int size() {
         return LinkedListMultimap.this.keyCount.size();
      }

      class 1 implements Iterator<V> {

         // $FF: synthetic field
         final Iterator val$nodes;


         1(Iterator var2) {
            this.val$nodes = var2;
         }

         public boolean hasNext() {
            return this.val$nodes.hasNext();
         }

         public V next() {
            return ((LinkedListMultimap.Node)this.val$nodes.next()).value;
         }

         public void remove() {
            this.val$nodes.remove();
         }
      }
   }

   class 2 extends AbstractSet<K> {

      2() {}

      public boolean contains(Object var1) {
         return LinkedListMultimap.this.keyCount.contains(var1);
      }

      public Iterator<K> iterator() {
         LinkedListMultimap var1 = LinkedListMultimap.this;
         return var1.new DistinctKeyIterator((LinkedListMultimap.1)null);
      }

      public int size() {
         return LinkedListMultimap.this.keyCount.elementSet().size();
      }
   }

   class 1 extends AbstractSequentialList<V> {

      // $FF: synthetic field
      final Object val$key;


      1(Object var2) {
         this.val$key = var2;
      }

      public ListIterator<V> listIterator(int var1) {
         LinkedListMultimap var2 = LinkedListMultimap.this;
         Object var3 = this.val$key;
         return var2.new ValueForKeyIterator(var3, var1);
      }

      public boolean removeAll(Collection<?> var1) {
         return Iterators.removeAll(this.iterator(), var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return Iterators.retainAll(this.iterator(), var1);
      }

      public int size() {
         Multiset var1 = LinkedListMultimap.this.keyCount;
         Object var2 = this.val$key;
         return var1.count(var2);
      }
   }

   private static final class Node<K extends Object, V extends Object> {

      final K key;
      LinkedListMultimap.Node<K, V> next;
      LinkedListMultimap.Node<K, V> nextSibling;
      LinkedListMultimap.Node<K, V> previous;
      LinkedListMultimap.Node<K, V> previousSibling;
      V value;


      Node(@Nullable K var1, @Nullable V var2) {
         this.key = var1;
         this.value = var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         Object var2 = this.key;
         StringBuilder var3 = var1.append(var2).append("=");
         Object var4 = this.value;
         return var3.append(var4).toString();
      }
   }

   private class AsMapEntries extends AbstractSet<Entry<K, Collection<V>>> {

      private AsMapEntries() {}

      // $FF: synthetic method
      AsMapEntries(LinkedListMultimap.1 var2) {
         this();
      }

      public Iterator<Entry<K, Collection<V>>> iterator() {
         LinkedListMultimap var1 = LinkedListMultimap.this;
         LinkedListMultimap.DistinctKeyIterator var2 = var1.new DistinctKeyIterator((LinkedListMultimap.1)null);
         return new LinkedListMultimap.AsMapEntries.1(var2);
      }

      public int size() {
         return LinkedListMultimap.this.keyCount.elementSet().size();
      }

      class 1 implements Iterator<Entry<K, Collection<V>>> {

         // $FF: synthetic field
         final Iterator val$keyIterator;


         1(Iterator var2) {
            this.val$keyIterator = var2;
         }

         public boolean hasNext() {
            return this.val$keyIterator.hasNext();
         }

         public Entry<K, Collection<V>> next() {
            Object var1 = this.val$keyIterator.next();
            return new LinkedListMultimap.AsMapEntries.1.1(var1);
         }

         public void remove() {
            this.val$keyIterator.remove();
         }

         class 1 extends AbstractMapEntry<K, Collection<V>> {

            // $FF: synthetic field
            final Object val$key;


            1(Object var2) {
               this.val$key = var2;
            }

            public K getKey() {
               return this.val$key;
            }

            public Collection<V> getValue() {
               LinkedListMultimap var1 = LinkedListMultimap.this;
               Object var2 = this.val$key;
               return var1.get(var2);
            }
         }
      }
   }

   private class NodeIterator implements Iterator<LinkedListMultimap.Node<K, V>> {

      LinkedListMultimap.Node<K, V> current;
      LinkedListMultimap.Node<K, V> next;


      private NodeIterator() {
         LinkedListMultimap.Node var2 = LinkedListMultimap.this.head;
         this.next = var2;
      }

      // $FF: synthetic method
      NodeIterator(LinkedListMultimap.1 var2) {
         this();
      }

      public boolean hasNext() {
         boolean var1;
         if(this.next != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public LinkedListMultimap.Node<K, V> next() {
         LinkedListMultimap.checkElement(this.next);
         LinkedListMultimap.Node var1 = this.next;
         this.current = var1;
         LinkedListMultimap.Node var2 = this.next.next;
         this.next = var2;
         return this.current;
      }

      public void remove() {
         byte var1;
         if(this.current != null) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1);
         LinkedListMultimap var2 = LinkedListMultimap.this;
         LinkedListMultimap.Node var3 = this.current;
         var2.removeNode(var3);
         this.current = null;
      }
   }

   private class ValueForKeyIterator implements ListIterator<V> {

      LinkedListMultimap.Node<K, V> current;
      final Object key;
      LinkedListMultimap.Node<K, V> next;
      int nextIndex;
      LinkedListMultimap.Node<K, V> previous;


      ValueForKeyIterator(Object var2) {
         this.key = var2;
         LinkedListMultimap.Node var3 = (LinkedListMultimap.Node)LinkedListMultimap.this.keyToKeyHead.get(var2);
         this.next = var3;
      }

      public ValueForKeyIterator(Object var2, int var3) {
         int var4 = LinkedListMultimap.this.keyCount.count(var2);
         Preconditions.checkPositionIndex(var3, var4);
         int var6 = var4 / 2;
         int var8;
         if(var3 >= var6) {
            LinkedListMultimap.Node var7 = (LinkedListMultimap.Node)LinkedListMultimap.this.keyToKeyTail.get(var2);
            this.previous = var7;
            this.nextIndex = var4;
            var8 = var3;

            while(true) {
               var3 = var8 + 1;
               if(var8 >= var4) {
                  break;
               }

               Object var9 = this.previous();
               var8 = var3;
            }
         } else {
            LinkedListMultimap.Node var10 = (LinkedListMultimap.Node)LinkedListMultimap.this.keyToKeyHead.get(var2);
            this.next = var10;
            var8 = var3;

            while(true) {
               var3 = var8 + -1;
               if(var8 <= 0) {
                  break;
               }

               Object var11 = this.next();
               var8 = var3;
            }
         }

         this.key = var2;
         this.current = null;
      }

      public void add(V var1) {
         LinkedListMultimap var2 = LinkedListMultimap.this;
         Object var3 = this.key;
         LinkedListMultimap.Node var4 = this.next;
         LinkedListMultimap.Node var5 = var2.addNode(var3, var1, var4);
         this.previous = var5;
         int var6 = this.nextIndex + 1;
         this.nextIndex = var6;
         this.current = null;
      }

      public boolean hasNext() {
         boolean var1;
         if(this.next != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean hasPrevious() {
         boolean var1;
         if(this.previous != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public V next() {
         LinkedListMultimap.checkElement(this.next);
         LinkedListMultimap.Node var1 = this.next;
         this.current = var1;
         this.previous = var1;
         LinkedListMultimap.Node var2 = this.next.nextSibling;
         this.next = var2;
         int var3 = this.nextIndex + 1;
         this.nextIndex = var3;
         return this.current.value;
      }

      public int nextIndex() {
         return this.nextIndex;
      }

      public V previous() {
         LinkedListMultimap.checkElement(this.previous);
         LinkedListMultimap.Node var1 = this.previous;
         this.current = var1;
         this.next = var1;
         LinkedListMultimap.Node var2 = this.previous.previousSibling;
         this.previous = var2;
         int var3 = this.nextIndex + -1;
         this.nextIndex = var3;
         return this.current.value;
      }

      public int previousIndex() {
         return this.nextIndex + -1;
      }

      public void remove() {
         byte var1;
         if(this.current != null) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         Preconditions.checkState((boolean)var1);
         LinkedListMultimap.Node var2 = this.current;
         LinkedListMultimap.Node var3 = this.next;
         if(var2 != var3) {
            LinkedListMultimap.Node var4 = this.current.previousSibling;
            this.previous = var4;
            int var5 = this.nextIndex + -1;
            this.nextIndex = var5;
         } else {
            LinkedListMultimap.Node var8 = this.current.nextSibling;
            this.next = var8;
         }

         LinkedListMultimap var6 = LinkedListMultimap.this;
         LinkedListMultimap.Node var7 = this.current;
         var6.removeNode(var7);
         this.current = null;
      }

      public void set(V var1) {
         byte var2;
         if(this.current != null) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         Preconditions.checkState((boolean)var2);
         this.current.value = var1;
      }
   }

   private class MultisetView extends AbstractCollection<K> implements Multiset<K> {

      private MultisetView() {}

      // $FF: synthetic method
      MultisetView(LinkedListMultimap.1 var2) {
         this();
      }

      public int add(@Nullable K var1, int var2) {
         throw new UnsupportedOperationException();
      }

      public int count(@Nullable Object var1) {
         return LinkedListMultimap.this.keyCount.count(var1);
      }

      public Set<K> elementSet() {
         return LinkedListMultimap.this.keySet();
      }

      public Set<Multiset.Entry<K>> entrySet() {
         return new LinkedListMultimap.MultisetView.2();
      }

      public boolean equals(@Nullable Object var1) {
         return LinkedListMultimap.this.keyCount.equals(var1);
      }

      public int hashCode() {
         return LinkedListMultimap.this.keyCount.hashCode();
      }

      public Iterator<K> iterator() {
         LinkedListMultimap var1 = LinkedListMultimap.this;
         LinkedListMultimap.NodeIterator var2 = var1.new NodeIterator((LinkedListMultimap.1)null);
         return new LinkedListMultimap.MultisetView.1(var2);
      }

      public int remove(@Nullable Object var1, int var2) {
         byte var3;
         if(var2 >= 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         Preconditions.checkArgument((boolean)var3);
         int var4 = this.count(var1);
         LinkedListMultimap var5 = LinkedListMultimap.this;
         LinkedListMultimap.ValueForKeyIterator var6 = var5.new ValueForKeyIterator(var1);
         int var7 = var2;

         while(true) {
            var2 = var7 + -1;
            if(var7 <= 0 || !var6.hasNext()) {
               return var4;
            }

            Object var8 = var6.next();
            var6.remove();
            var7 = var2;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         return Iterators.removeAll(this.iterator(), var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return Iterators.retainAll(this.iterator(), var1);
      }

      public int setCount(K var1, int var2) {
         return Multisets.setCountImpl(this, var1, var2);
      }

      public boolean setCount(K var1, int var2, int var3) {
         return Multisets.setCountImpl(this, var1, var2, var3);
      }

      public int size() {
         return LinkedListMultimap.this.keyCount.size();
      }

      public String toString() {
         return LinkedListMultimap.this.keyCount.toString();
      }

      class 2 extends AbstractSet<Multiset.Entry<K>> {

         2() {}

         public Iterator<Multiset.Entry<K>> iterator() {
            LinkedListMultimap var1 = LinkedListMultimap.this;
            LinkedListMultimap.DistinctKeyIterator var2 = var1.new DistinctKeyIterator((LinkedListMultimap.1)null);
            return new LinkedListMultimap.MultisetView.2.1(var2);
         }

         public int size() {
            return LinkedListMultimap.this.keyCount.elementSet().size();
         }

         class 1 implements Iterator<Multiset.Entry<K>> {

            // $FF: synthetic field
            final Iterator val$keyIterator;


            1(Iterator var2) {
               this.val$keyIterator = var2;
            }

            public boolean hasNext() {
               return this.val$keyIterator.hasNext();
            }

            public Multiset.Entry<K> next() {
               Object var1 = this.val$keyIterator.next();
               return new LinkedListMultimap.MultisetView.2.1.1(var1);
            }

            public void remove() {
               this.val$keyIterator.remove();
            }

            class 1 extends Multisets.AbstractEntry<K> {

               // $FF: synthetic field
               final Object val$key;


               1(Object var2) {
                  this.val$key = var2;
               }

               public int getCount() {
                  Multiset var1 = LinkedListMultimap.this.keyCount;
                  Object var2 = this.val$key;
                  return var1.count(var2);
               }

               public K getElement() {
                  return this.val$key;
               }
            }
         }
      }

      class 1 implements Iterator<K> {

         // $FF: synthetic field
         final Iterator val$nodes;


         1(Iterator var2) {
            this.val$nodes = var2;
         }

         public boolean hasNext() {
            return this.val$nodes.hasNext();
         }

         public K next() {
            return ((LinkedListMultimap.Node)this.val$nodes.next()).key;
         }

         public void remove() {
            this.val$nodes.remove();
         }
      }
   }
}
