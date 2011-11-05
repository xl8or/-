package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap$Impl$HashIterator;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.Iterator;
import java.util.Map.Entry;

final class CustomConcurrentHashMap$Impl$EntryIterator extends CustomConcurrentHashMap$Impl$HashIterator implements Iterator<Entry<K, V>> {

   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$EntryIterator(Impl var1) {
      super(var1);
      this.this$0 = var1;
   }

   public Entry<K, V> next() {
      return this.nextEntry();
   }
}
