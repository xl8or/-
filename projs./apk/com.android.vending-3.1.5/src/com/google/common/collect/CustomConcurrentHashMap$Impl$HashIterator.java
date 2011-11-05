package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap;
import com.google.common.collect.CustomConcurrentHashMap$Impl$Segment;
import com.google.common.collect.CustomConcurrentHashMap$Impl$WriteThroughEntry;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceArray;

abstract class CustomConcurrentHashMap$Impl$HashIterator {

   AtomicReferenceArray<E> currentTable;
   CustomConcurrentHashMap$Impl$WriteThroughEntry lastReturned;
   E nextEntry;
   CustomConcurrentHashMap$Impl$WriteThroughEntry nextExternal;
   int nextSegmentIndex;
   int nextTableIndex;
   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$HashIterator(Impl var1) {
      this.this$0 = var1;
      int var2 = var1.segments.length + -1;
      this.nextSegmentIndex = var2;
      this.nextTableIndex = -1;
      this.advance();
   }

   final void advance() {
      this.nextExternal = null;
      if(!this.nextInChain()) {
         if(!this.nextInTable()) {
            while(this.nextSegmentIndex >= 0) {
               CustomConcurrentHashMap$Impl$Segment[] var1 = this.this$0.segments;
               int var2 = this.nextSegmentIndex;
               int var3 = var2 + -1;
               this.nextSegmentIndex = var3;
               CustomConcurrentHashMap$Impl$Segment var4 = var1[var2];
               if(var4.count != 0) {
                  AtomicReferenceArray var5 = var4.table;
                  this.currentTable = var5;
                  int var6 = this.currentTable.length() + -1;
                  this.nextTableIndex = var6;
                  if(this.nextInTable()) {
                     return;
                  }
               }
            }

         }
      }
   }

   boolean advanceTo(E var1) {
      CustomConcurrentHashMap.Strategy var2 = this.this$0.strategy;
      Object var3 = var2.getKey(var1);
      Object var4 = var2.getValue(var1);
      boolean var7;
      if(var3 != null && var4 != null) {
         Impl var5 = this.this$0;
         CustomConcurrentHashMap$Impl$WriteThroughEntry var6 = new CustomConcurrentHashMap$Impl$WriteThroughEntry(var5, var3, var4);
         this.nextExternal = var6;
         var7 = true;
      } else {
         var7 = false;
      }

      return var7;
   }

   public boolean hasMoreElements() {
      return this.hasNext();
   }

   public boolean hasNext() {
      boolean var1;
      if(this.nextExternal != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   CustomConcurrentHashMap$Impl$WriteThroughEntry nextEntry() {
      if(this.nextExternal == null) {
         throw new NoSuchElementException();
      } else {
         CustomConcurrentHashMap$Impl$WriteThroughEntry var1 = this.nextExternal;
         this.lastReturned = var1;
         this.advance();
         return this.lastReturned;
      }
   }

   boolean nextInChain() {
      CustomConcurrentHashMap.Strategy var1 = this.this$0.strategy;
      boolean var5;
      if(this.nextEntry != null) {
         Object var2 = this.nextEntry;
         Object var3 = var1.getNext(var2);

         Object var7;
         for(this.nextEntry = var3; this.nextEntry != null; this.nextEntry = var7) {
            Object var4 = this.nextEntry;
            if(this.advanceTo(var4)) {
               var5 = true;
               return var5;
            }

            Object var6 = this.nextEntry;
            var7 = var1.getNext(var6);
         }
      }

      var5 = false;
      return var5;
   }

   boolean nextInTable() {
      while(true) {
         boolean var6;
         if(this.nextTableIndex >= 0) {
            AtomicReferenceArray var1 = this.currentTable;
            int var2 = this.nextTableIndex;
            int var3 = var2 + -1;
            this.nextTableIndex = var3;
            Object var4 = var1.get(var2);
            this.nextEntry = var4;
            if(var4 == null) {
               continue;
            }

            Object var5 = this.nextEntry;
            if(!this.advanceTo(var5) && !this.nextInChain()) {
               continue;
            }

            var6 = true;
         } else {
            var6 = false;
         }

         return var6;
      }
   }

   public void remove() {
      if(this.lastReturned == null) {
         throw new IllegalStateException();
      } else {
         Impl var1 = this.this$0;
         Object var2 = this.lastReturned.getKey();
         var1.remove(var2);
         this.lastReturned = null;
      }
   }
}
