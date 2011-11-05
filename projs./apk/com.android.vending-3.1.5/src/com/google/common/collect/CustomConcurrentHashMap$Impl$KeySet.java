package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap$Impl$KeyIterator;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.AbstractSet;
import java.util.Iterator;

final class CustomConcurrentHashMap$Impl$KeySet extends AbstractSet<K> {

   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$KeySet(Impl var1) {
      this.this$0 = var1;
   }

   public void clear() {
      this.this$0.clear();
   }

   public boolean contains(Object var1) {
      return this.this$0.containsKey(var1);
   }

   public boolean isEmpty() {
      return this.this$0.isEmpty();
   }

   public Iterator<K> iterator() {
      Impl var1 = this.this$0;
      return new CustomConcurrentHashMap$Impl$KeyIterator(var1);
   }

   public boolean remove(Object var1) {
      boolean var2;
      if(this.this$0.remove(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int size() {
      return this.this$0.size();
   }
}
