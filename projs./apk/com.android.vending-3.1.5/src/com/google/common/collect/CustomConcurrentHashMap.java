package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.collect.CustomConcurrentHashMap$Impl$Segment;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javax.annotation.Nullable;

final class CustomConcurrentHashMap {

   private CustomConcurrentHashMap() {}

   // $FF: synthetic method
   static int access$000(int var0) {
      return rehash(var0);
   }

   private static int rehash(int var0) {
      int var1 = var0 << 15 ^ -12931;
      int var2 = var0 + var1;
      int var3 = var2 >>> 10;
      int var4 = var2 ^ var3;
      int var5 = var4 << 3;
      int var6 = var4 + var5;
      int var7 = var6 >>> 6;
      int var8 = var6 ^ var7;
      int var9 = var8 << 2;
      int var10 = var8 << 14;
      int var11 = var9 + var10;
      int var12 = var8 + var11;
      return var12 >>> 16 ^ var12;
   }

   static class SimpleStrategy<K extends Object, V extends Object> implements CustomConcurrentHashMap.Strategy<K, V, CustomConcurrentHashMap.SimpleInternalEntry<K, V>> {

      SimpleStrategy() {}

      public CustomConcurrentHashMap.SimpleInternalEntry<K, V> copyEntry(K var1, CustomConcurrentHashMap.SimpleInternalEntry<K, V> var2, CustomConcurrentHashMap.SimpleInternalEntry<K, V> var3) {
         int var4 = var2.hash;
         Object var5 = var2.value;
         return new CustomConcurrentHashMap.SimpleInternalEntry(var1, var4, var5, var3);
      }

      public boolean equalKeys(K var1, Object var2) {
         return var1.equals(var2);
      }

      public boolean equalValues(V var1, Object var2) {
         return var1.equals(var2);
      }

      public int getHash(CustomConcurrentHashMap.SimpleInternalEntry<K, V> var1) {
         return var1.hash;
      }

      public K getKey(CustomConcurrentHashMap.SimpleInternalEntry<K, V> var1) {
         return var1.key;
      }

      public CustomConcurrentHashMap.SimpleInternalEntry<K, V> getNext(CustomConcurrentHashMap.SimpleInternalEntry<K, V> var1) {
         return var1.next;
      }

      public V getValue(CustomConcurrentHashMap.SimpleInternalEntry<K, V> var1) {
         return var1.value;
      }

      public int hashKey(Object var1) {
         return var1.hashCode();
      }

      public CustomConcurrentHashMap.SimpleInternalEntry<K, V> newEntry(K var1, int var2, CustomConcurrentHashMap.SimpleInternalEntry<K, V> var3) {
         return new CustomConcurrentHashMap.SimpleInternalEntry(var1, var2, (Object)null, var3);
      }

      public void setInternals(CustomConcurrentHashMap.Internals<K, V, CustomConcurrentHashMap.SimpleInternalEntry<K, V>> var1) {}

      public void setValue(CustomConcurrentHashMap.SimpleInternalEntry<K, V> var1, V var2) {
         var1.value = var2;
      }
   }

   public interface Strategy<K extends Object, V extends Object, E extends Object> {

      E copyEntry(K var1, E var2, E var3);

      boolean equalKeys(K var1, Object var2);

      boolean equalValues(V var1, Object var2);

      int getHash(E var1);

      K getKey(E var1);

      E getNext(E var1);

      V getValue(E var1);

      int hashKey(Object var1);

      E newEntry(K var1, int var2, E var3);

      void setInternals(CustomConcurrentHashMap.Internals<K, V, E> var1);

      void setValue(E var1, V var2);
   }

   public interface ComputingStrategy<K extends Object, V extends Object, E extends Object> extends CustomConcurrentHashMap.Strategy<K, V, E> {

      V compute(K var1, E var2, Function<? super K, ? extends V> var3);

      V waitForValue(E var1) throws InterruptedException;
   }

   public interface Internals<K extends Object, V extends Object, E extends Object> {

      E getEntry(K var1);

      boolean removeEntry(E var1);

      boolean removeEntry(E var1, @Nullable V var2);
   }

   static class ComputingImpl<K extends Object, V extends Object, E extends Object> extends Impl<K, V, E> {

      static final long serialVersionUID;
      final Function<? super K, ? extends V> computer;
      final CustomConcurrentHashMap.ComputingStrategy<K, V, E> computingStrategy;


      ComputingImpl(CustomConcurrentHashMap.ComputingStrategy<K, V, E> var1, CustomConcurrentHashMap.Builder var2, Function<? super K, ? extends V> var3) {
         super(var1, var2);
         this.computingStrategy = var1;
         this.computer = var3;
      }

      public V get(Object var1) {
         Object var2 = var1;
         if(var1 == null) {
            throw new NullPointerException("key");
         } else {
            int var3 = this.hash(var1);
            CustomConcurrentHashMap$Impl$Segment var4 = this.segmentFor(var3);

            Object var18;
            while(true) {
               Object var5 = var4.getEntry(var2, var3);
               if(var5 == null) {
                  boolean var6 = false;
                  var4.lock();

                  try {
                     var5 = var4.getEntry(var2, var3);
                     if(var5 == null) {
                        var6 = true;
                        int var7 = var4.count;
                        int var8 = var7 + 1;
                        int var9 = var4.threshold;
                        if(var7 > var9) {
                           var4.expand();
                        }

                        AtomicReferenceArray var10 = var4.table;
                        int var11 = var10.length() + -1;
                        int var12 = var3 & var11;
                        Object var13 = var10.get(var12);
                        int var14 = var4.modCount + 1;
                        var4.modCount = var14;
                        var5 = this.computingStrategy.newEntry(var2, var3, var13);
                        var10.set(var12, var5);
                        var4.count = var8;
                     }
                  } finally {
                     var4.unlock();
                  }

                  if(var6) {
                     boolean var15 = false;
                     boolean var36 = false;

                     try {
                        var36 = true;
                        CustomConcurrentHashMap.ComputingStrategy var16 = this.computingStrategy;
                        Function var17 = this.computer;
                        var18 = var16.compute(var2, var5, var17);
                        if(var18 == null) {
                           throw new NullPointerException("compute() returned null unexpectedly");
                        }

                        var36 = false;
                     } finally {
                        if(var36) {
                           if(!var15) {
                              var4.removeEntry(var5, var3);
                           }

                        }
                     }

                     if(false) {
                        var4.removeEntry(var5, var3);
                     }
                     break;
                  }
               }

               boolean var23 = false;

               while(true) {
                  boolean var31 = false;

                  label355: {
                     label380: {
                        try {
                           var31 = true;
                           var18 = this.computingStrategy.waitForValue(var5);
                           if(var18 == null) {
                              var4.removeEntry(var5, var3);
                              var31 = false;
                              break label355;
                           }

                           var31 = false;
                        } catch (InterruptedException var43) {
                           var31 = false;
                           break label380;
                        } finally {
                           if(var31) {
                              if(var23) {
                                 Thread.currentThread().interrupt();
                              }

                           }
                        }

                        if(var23) {
                           Thread.currentThread().interrupt();
                        }

                        return var18;
                     }

                     var23 = true;
                     continue;
                  }

                  if(var23) {
                     Thread.currentThread().interrupt();
                  }
                  break;
               }
            }

            return var18;
         }
      }
   }

   static final class Builder {

      private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
      private static final int DEFAULT_INITIAL_CAPACITY = 16;
      private static final int UNSET_CONCURRENCY_LEVEL = 255;
      private static final int UNSET_INITIAL_CAPACITY = 255;
      int concurrencyLevel = -1;
      int initialCapacity = -1;


      Builder() {}

      public <K extends Object, V extends Object, E extends Object> ConcurrentMap<K, V> buildComputingMap(CustomConcurrentHashMap.ComputingStrategy<K, V, E> var1, Function<? super K, ? extends V> var2) {
         if(var1 == null) {
            throw new NullPointerException("strategy");
         } else if(var2 == null) {
            throw new NullPointerException("computer");
         } else {
            return new CustomConcurrentHashMap.ComputingImpl(var1, this, var2);
         }
      }

      public <K extends Object, V extends Object, E extends Object> ConcurrentMap<K, V> buildMap(CustomConcurrentHashMap.Strategy<K, V, E> var1) {
         if(var1 == null) {
            throw new NullPointerException("strategy");
         } else {
            return new Impl(var1, this);
         }
      }

      public CustomConcurrentHashMap.Builder concurrencyLevel(int var1) {
         if(this.concurrencyLevel != -1) {
            StringBuilder var2 = (new StringBuilder()).append("concurrency level was already set to ");
            int var3 = this.concurrencyLevel;
            String var4 = var2.append(var3).toString();
            throw new IllegalStateException(var4);
         } else if(var1 <= 0) {
            throw new IllegalArgumentException();
         } else {
            this.concurrencyLevel = var1;
            return this;
         }
      }

      int getConcurrencyLevel() {
         int var1;
         if(this.concurrencyLevel == -1) {
            var1 = 16;
         } else {
            var1 = this.concurrencyLevel;
         }

         return var1;
      }

      int getInitialCapacity() {
         int var1;
         if(this.initialCapacity == -1) {
            var1 = 16;
         } else {
            var1 = this.initialCapacity;
         }

         return var1;
      }

      public CustomConcurrentHashMap.Builder initialCapacity(int var1) {
         if(this.initialCapacity != -1) {
            StringBuilder var2 = (new StringBuilder()).append("initial capacity was already set to ");
            int var3 = this.initialCapacity;
            String var4 = var2.append(var3).toString();
            throw new IllegalStateException(var4);
         } else if(var1 < 0) {
            throw new IllegalArgumentException();
         } else {
            this.initialCapacity = var1;
            return this;
         }
      }
   }

   static class SimpleInternalEntry<K extends Object, V extends Object> {

      final int hash;
      final K key;
      final CustomConcurrentHashMap.SimpleInternalEntry<K, V> next;
      volatile V value;


      SimpleInternalEntry(K var1, int var2, @Nullable V var3, CustomConcurrentHashMap.SimpleInternalEntry<K, V> var4) {
         this.key = var1;
         this.hash = var2;
         this.value = var3;
         this.next = var4;
      }
   }
}
