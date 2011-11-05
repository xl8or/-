package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap$Impl$HashIterator;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.Iterator;

final class CustomConcurrentHashMap$Impl$KeyIterator extends CustomConcurrentHashMap$Impl$HashIterator implements Iterator<K> {

   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$KeyIterator(Impl var1) {
      super(var1);
      this.this$0 = var1;
   }

   public K next() {
      return super.nextEntry().getKey();
   }
}
