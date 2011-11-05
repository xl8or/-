package org.jivesoftware.smack.util;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jivesoftware.smack.util.collections.AbstractMapEntry;

public class Cache<K extends Object, V extends Object> implements Map<K, V> {

   protected Cache.LinkedList ageList;
   protected long cacheHits;
   protected long cacheMisses = 0L;
   protected Cache.LinkedList lastAccessedList;
   protected Map<K, Cache.CacheObject<V>> map;
   protected int maxCacheSize;
   protected long maxLifetime;


   public Cache(int var1, long var2) {
      if(var1 == 0) {
         throw new IllegalArgumentException("Max cache size cannot be 0.");
      } else {
         this.maxCacheSize = var1;
         this.maxLifetime = var2;
         HashMap var4 = new HashMap(103);
         this.map = var4;
         Cache.LinkedList var5 = new Cache.LinkedList();
         this.lastAccessedList = var5;
         Cache.LinkedList var6 = new Cache.LinkedList();
         this.ageList = var6;
      }
   }

   public void clear() {
      synchronized(this){}

      try {
         Object[] var1 = this.map.keySet().toArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Object var4 = var1[var3];
            this.remove(var4);
         }

         this.map.clear();
         this.lastAccessedList.clear();
         this.ageList.clear();
         this.cacheHits = 0L;
         this.cacheMisses = 0L;
      } finally {
         ;
      }
   }

   public boolean containsKey(Object var1) {
      synchronized(this){}
      boolean var6 = false;

      boolean var2;
      try {
         var6 = true;
         this.deleteExpiredEntries();
         var2 = this.map.containsKey(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public boolean containsValue(Object var1) {
      synchronized(this){}
      boolean var7 = false;

      boolean var3;
      try {
         var7 = true;
         this.deleteExpiredEntries();
         Cache.CacheObject var2 = new Cache.CacheObject(var1);
         var3 = this.map.containsValue(var2);
         var7 = false;
      } finally {
         if(var7) {
            ;
         }
      }

      return var3;
   }

   protected void cullCache() {
      // $FF: Couldn't be decompiled
   }

   protected void deleteExpiredEntries() {
      // $FF: Couldn't be decompiled
   }

   public Set<Entry<K, V>> entrySet() {
      synchronized(this){}

      Cache.2 var1;
      try {
         this.deleteExpiredEntries();
         var1 = new Cache.2();
      } finally {
         ;
      }

      return var1;
   }

   public V get(Object var1) {
      synchronized(this){}
      boolean var13 = false;

      Object var15;
      try {
         var13 = true;
         this.deleteExpiredEntries();
         Cache.CacheObject var2 = (Cache.CacheObject)this.map.get(var1);
         if(var2 != null) {
            var2.lastAccessedListNode.remove();
            Cache.LinkedList var5 = this.lastAccessedList;
            Cache.LinkedListNode var6 = var2.lastAccessedListNode;
            var5.addFirst(var6);
            long var8 = this.cacheHits + 1L;
            this.cacheHits = var8;
            int var10 = var2.readCount + 1;
            var2.readCount = var10;
            var15 = var2.object;
            var13 = false;
            return var15;
         }

         long var3 = this.cacheMisses + 1L;
         this.cacheMisses = var3;
         var13 = false;
      } finally {
         if(var13) {
            ;
         }
      }

      var15 = null;
      return var15;
   }

   public long getCacheHits() {
      return this.cacheHits;
   }

   public long getCacheMisses() {
      return this.cacheMisses;
   }

   public int getMaxCacheSize() {
      return this.maxCacheSize;
   }

   public long getMaxLifetime() {
      return this.maxLifetime;
   }

   public boolean isEmpty() {
      synchronized(this){}
      boolean var5 = false;

      boolean var1;
      try {
         var5 = true;
         this.deleteExpiredEntries();
         var1 = this.map.isEmpty();
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public Set<K> keySet() {
      synchronized(this){}
      boolean var5 = false;

      Set var1;
      try {
         var5 = true;
         this.deleteExpiredEntries();
         var1 = Collections.unmodifiableSet(this.map.keySet());
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public V put(K var1, V var2) {
      synchronized(this){}
      Object var3 = null;

      try {
         if(this.map.containsKey(var1)) {
            var3 = this.remove(var1, (boolean)1);
         }

         Cache.CacheObject var4 = new Cache.CacheObject(var2);
         this.map.put(var1, var4);
         Cache.LinkedListNode var6 = this.lastAccessedList.addFirst(var1);
         var4.lastAccessedListNode = var6;
         Cache.LinkedListNode var7 = this.ageList.addFirst(var1);
         long var8 = System.currentTimeMillis();
         var7.timestamp = var8;
         var4.ageListNode = var7;
         this.cullCache();
      } finally {
         ;
      }

      return var3;
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         Object var4 = var3.getValue();
         if(var4 instanceof Cache.CacheObject) {
            var4 = ((Cache.CacheObject)var4).object;
         }

         Object var5 = var3.getKey();
         this.put(var5, var4);
      }

   }

   public V remove(Object var1) {
      synchronized(this){}
      boolean var6 = false;

      Object var2;
      try {
         var6 = true;
         var2 = this.remove(var1, (boolean)0);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public V remove(Object param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public void setMaxCacheSize(int var1) {
      synchronized(this){}

      try {
         this.maxCacheSize = var1;
         this.cullCache();
      } finally {
         ;
      }

   }

   public void setMaxLifetime(long var1) {
      this.maxLifetime = var1;
   }

   public int size() {
      synchronized(this){}
      boolean var5 = false;

      int var1;
      try {
         var5 = true;
         this.deleteExpiredEntries();
         var1 = this.map.size();
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public Collection<V> values() {
      synchronized(this){}
      boolean var5 = false;

      Collection var1;
      try {
         var5 = true;
         this.deleteExpiredEntries();
         var1 = Collections.unmodifiableCollection(new Cache.1());
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   private static class CacheObject<V extends Object> {

      public Cache.LinkedListNode ageListNode;
      public Cache.LinkedListNode lastAccessedListNode;
      public V object;
      public int readCount = 0;


      public CacheObject(V var1) {
         this.object = var1;
      }

      public boolean equals(Object var1) {
         byte var2;
         if(this == var1) {
            var2 = 1;
         } else if(!(var1 instanceof Cache.CacheObject)) {
            var2 = 0;
         } else {
            Cache.CacheObject var3 = (Cache.CacheObject)var1;
            Object var4 = this.object;
            Object var5 = var3.object;
            var2 = var4.equals(var5);
         }

         return (boolean)var2;
      }

      public int hashCode() {
         return this.object.hashCode();
      }
   }

   private static class LinkedList {

      private Cache.LinkedListNode head;


      public LinkedList() {
         Cache.LinkedListNode var1 = new Cache.LinkedListNode("head", (Cache.LinkedListNode)null, (Cache.LinkedListNode)null);
         this.head = var1;
         Cache.LinkedListNode var2 = this.head;
         Cache.LinkedListNode var3 = this.head;
         Cache.LinkedListNode var4 = this.head;
         var3.previous = var4;
         var2.next = var4;
      }

      public Cache.LinkedListNode addFirst(Object var1) {
         Cache.LinkedListNode var2 = this.head.next;
         Cache.LinkedListNode var3 = this.head;
         Cache.LinkedListNode var4 = new Cache.LinkedListNode(var1, var2, var3);
         var4.previous.next = var4;
         var4.next.previous = var4;
         return var4;
      }

      public Cache.LinkedListNode addFirst(Cache.LinkedListNode var1) {
         Cache.LinkedListNode var2 = this.head.next;
         var1.next = var2;
         Cache.LinkedListNode var3 = this.head;
         var1.previous = var3;
         var1.previous.next = var1;
         var1.next.previous = var1;
         return var1;
      }

      public Cache.LinkedListNode addLast(Object var1) {
         Cache.LinkedListNode var2 = this.head;
         Cache.LinkedListNode var3 = this.head.previous;
         Cache.LinkedListNode var4 = new Cache.LinkedListNode(var1, var2, var3);
         var4.previous.next = var4;
         var4.next.previous = var4;
         return var4;
      }

      public void clear() {
         for(Cache.LinkedListNode var1 = this.getLast(); var1 != null; var1 = this.getLast()) {
            var1.remove();
         }

         Cache.LinkedListNode var2 = this.head;
         Cache.LinkedListNode var3 = this.head;
         Cache.LinkedListNode var4 = this.head;
         var3.previous = var4;
         var2.next = var4;
      }

      public Cache.LinkedListNode getFirst() {
         Cache.LinkedListNode var1 = this.head.next;
         Cache.LinkedListNode var2 = this.head;
         if(var1 == var2) {
            var1 = null;
         }

         return var1;
      }

      public Cache.LinkedListNode getLast() {
         Cache.LinkedListNode var1 = this.head.previous;
         Cache.LinkedListNode var2 = this.head;
         if(var1 == var2) {
            var1 = null;
         }

         return var1;
      }

      public String toString() {
         Cache.LinkedListNode var1 = this.head.next;
         StringBuilder var2 = new StringBuilder();

         while(true) {
            Cache.LinkedListNode var3 = this.head;
            if(var1 == var3) {
               return var2.toString();
            }

            String var4 = var1.toString();
            StringBuilder var5 = var2.append(var4).append(", ");
            var1 = var1.next;
         }
      }
   }

   private static class LinkedListNode {

      public Cache.LinkedListNode next;
      public Object object;
      public Cache.LinkedListNode previous;
      public long timestamp;


      public LinkedListNode(Object var1, Cache.LinkedListNode var2, Cache.LinkedListNode var3) {
         this.object = var1;
         this.next = var2;
         this.previous = var3;
      }

      public void remove() {
         Cache.LinkedListNode var1 = this.previous;
         Cache.LinkedListNode var2 = this.next;
         var1.next = var2;
         Cache.LinkedListNode var3 = this.next;
         Cache.LinkedListNode var4 = this.previous;
         var3.previous = var4;
      }

      public String toString() {
         return this.object.toString();
      }
   }

   class 2 extends AbstractSet<Entry<K, V>> {

      private final Set<Entry<K, Cache.CacheObject<V>>> set;


      2() {
         Set var2 = Cache.this.map.entrySet();
         this.set = var2;
      }

      public Iterator<Entry<K, V>> iterator() {
         return new Cache.2.1();
      }

      public int size() {
         return this.set.size();
      }

      class 1 implements Iterator<Entry<K, V>> {

         private final Iterator<Entry<K, Cache.CacheObject<V>>> it;


         1() {
            Iterator var2 = 2.this.set.iterator();
            this.it = var2;
         }

         public boolean hasNext() {
            return this.it.hasNext();
         }

         public Entry<K, V> next() {
            Entry var1 = (Entry)this.it.next();
            Object var2 = var1.getKey();
            Object var3 = ((Cache.CacheObject)var1.getValue()).object;
            return new Cache.2.1.1(var2, var3);
         }

         public void remove() {
            this.it.remove();
         }

         class 1 extends AbstractMapEntry<K, V> {

            1(Object var2, Object var3) {
               super(var2, var3);
            }

            public V setValue(V var1) {
               throw new UnsupportedOperationException("Cannot set");
            }
         }
      }
   }

   class 1 extends AbstractCollection<V> {

      Collection<Cache.CacheObject<V>> values;


      1() {
         Collection var2 = Cache.this.map.values();
         this.values = var2;
      }

      public Iterator<V> iterator() {
         return new Cache.1.1();
      }

      public int size() {
         return this.values.size();
      }

      class 1 implements Iterator<V> {

         Iterator<Cache.CacheObject<V>> it;


         1() {
            Iterator var2 = 1.this.values.iterator();
            this.it = var2;
         }

         public boolean hasNext() {
            return this.it.hasNext();
         }

         public V next() {
            return ((Cache.CacheObject)this.it.next()).object;
         }

         public void remove() {
            this.it.remove();
         }
      }
   }
}
