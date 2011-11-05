package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap$Impl$ValueIterator;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.AbstractCollection;
import java.util.Iterator;

final class CustomConcurrentHashMap$Impl$Values extends AbstractCollection<V> {

   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$Values(Impl var1) {
      this.this$0 = var1;
   }

   public void clear() {
      this.this$0.clear();
   }

   public boolean contains(Object var1) {
      return this.this$0.containsValue(var1);
   }

   public boolean isEmpty() {
      return this.this$0.isEmpty();
   }

   public Iterator<V> iterator() {
      Impl var1 = this.this$0;
      return new CustomConcurrentHashMap$Impl$ValueIterator(var1);
   }

   public int size() {
      return this.this$0.size();
   }
}
