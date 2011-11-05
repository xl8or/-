package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

final class CustomConcurrentHashMap$Impl$Segment extends ReentrantLock {

   volatile int count;
   int modCount;
   volatile AtomicReferenceArray<E> table;
   // $FF: synthetic field
   final Impl this$0;
   int threshold;


   CustomConcurrentHashMap$Impl$Segment(Impl var1, int var2) {
      this.this$0 = var1;
      AtomicReferenceArray var3 = this.newEntryArray(var2);
      this.setTable(var3);
   }

   void clear() {
      if(this.count != 0) {
         this.lock();

         try {
            AtomicReferenceArray var1 = this.table;
            int var2 = 0;

            while(true) {
               int var3 = var1.length();
               if(var2 >= var3) {
                  int var4 = this.modCount + 1;
                  this.modCount = var4;
                  this.count = 0;
                  return;
               }

               var1.set(var2, (Object)null);
               ++var2;
            }
         } finally {
            this.unlock();
         }
      }
   }

   boolean containsKey(Object var1, int var2) {
      boolean var3 = false;
      CustomConcurrentHashMap.Strategy var4 = this.this$0.strategy;
      if(this.count != 0) {
         for(Object var5 = this.getFirst(var2); var5 != null; var5 = var4.getNext(var5)) {
            if(var4.getHash(var5) == var2) {
               Object var6 = var4.getKey(var5);
               if(var6 != null && var4.equalKeys(var6, var1)) {
                  if(var4.getValue(var5) != null) {
                     var3 = true;
                  }
                  break;
               }
            }
         }
      }

      return var3;
   }

   boolean containsValue(Object var1) {
      CustomConcurrentHashMap.Strategy var2 = this.this$0.strategy;
      boolean var8;
      if(this.count != 0) {
         AtomicReferenceArray var3 = this.table;
         int var4 = var3.length();

         for(int var5 = 0; var5 < var4; ++var5) {
            for(Object var6 = var3.get(var5); var6 != null; var6 = var2.getNext(var6)) {
               Object var7 = var2.getValue(var6);
               if(var7 != null && var2.equalValues(var7, var1)) {
                  var8 = true;
                  return var8;
               }
            }
         }
      }

      var8 = false;
      return var8;
   }

   void expand() {
      AtomicReferenceArray var1 = this.table;
      int var2 = var1.length();
      int var3 = 1073741824;
      if(var2 < var3) {
         CustomConcurrentHashMap.Strategy var4 = this.this$0.strategy;
         int var5 = var2 << 1;
         AtomicReferenceArray var8 = this.newEntryArray(var5);
         int var9 = var8.length() * 3 / 4;
         this.threshold = var9;
         int var10 = var8.length() + -1;

         for(int var11 = 0; var11 < var2; ++var11) {
            Object var12 = var1.get(var11);
            if(var12 != null) {
               Object var13 = var4.getNext(var12);
               int var14 = var4.getHash(var12) & var10;
               if(var13 == null) {
                  var8.set(var14, var12);
               } else {
                  Object var15 = var12;
                  int var16 = var14;

                  for(Object var17 = var13; var17 != null; var17 = var4.getNext(var17)) {
                     int var18 = var4.getHash(var17) & var10;
                     if(var18 != var16) {
                        var16 = var18;
                        var15 = var17;
                     }
                  }

                  var8.set(var16, var15);

                  for(Object var22 = var12; var22 != var15; var22 = var4.getNext(var22)) {
                     Object var24 = var4.getKey(var22);
                     if(var24 != null) {
                        int var25 = var4.getHash(var22) & var10;
                        Object var26 = var8.get(var25);
                        Object var27 = var4.copyEntry(var24, var22, var26);
                        var8.set(var25, var27);
                     }
                  }
               }
            }
         }

         this.table = var8;
      }
   }

   V get(Object var1, int var2) {
      Object var3 = this.getEntry(var1, var2);
      Object var4;
      if(var3 == null) {
         var4 = null;
      } else {
         var4 = this.this$0.strategy.getValue(var3);
      }

      return var4;
   }

   public E getEntry(Object var1, int var2) {
      CustomConcurrentHashMap.Strategy var3 = this.this$0.strategy;
      Object var4;
      if(this.count != 0) {
         for(var4 = this.getFirst(var2); var4 != null; var4 = var3.getNext(var4)) {
            if(var3.getHash(var4) == var2) {
               Object var5 = var3.getKey(var4);
               if(var5 != null && var3.equalKeys(var5, var1)) {
                  return var4;
               }
            }
         }
      }

      var4 = null;
      return var4;
   }

   E getFirst(int var1) {
      AtomicReferenceArray var2 = this.table;
      int var3 = var2.length() + -1 & var1;
      return var2.get(var3);
   }

   AtomicReferenceArray<E> newEntryArray(int var1) {
      return new AtomicReferenceArray(var1);
   }

   V put(K param1, int param2, V param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   V remove(Object param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   boolean remove(Object param1, int param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   public boolean removeEntry(E param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean removeEntry(E param1, int param2, V param3) {
      // $FF: Couldn't be decompiled
   }

   V replace(K param1, int param2, V param3) {
      // $FF: Couldn't be decompiled
   }

   boolean replace(K param1, int param2, V param3, V param4) {
      // $FF: Couldn't be decompiled
   }

   void setTable(AtomicReferenceArray<E> var1) {
      int var2 = var1.length() * 3 / 4;
      this.threshold = var2;
      this.table = var1;
   }
}
