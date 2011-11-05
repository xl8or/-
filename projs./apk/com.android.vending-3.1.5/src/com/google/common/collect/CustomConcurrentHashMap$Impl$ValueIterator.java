package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap$Impl$HashIterator;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.Iterator;

final class CustomConcurrentHashMap$Impl$ValueIterator extends CustomConcurrentHashMap$Impl$HashIterator implements Iterator<V> {

   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$ValueIterator(Impl var1) {
      super(var1);
      this.this$0 = var1;
   }

   public V next() {
      return super.nextEntry().getValue();
   }
}
