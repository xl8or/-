package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.io.Serializable;

class CustomConcurrentHashMap$Impl$InternalsImpl implements CustomConcurrentHashMap.Internals<K, V, E>, Serializable {

   static final long serialVersionUID;
   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$InternalsImpl(Impl var1) {
      this.this$0 = var1;
   }

   public E getEntry(K var1) {
      if(var1 == null) {
         throw new NullPointerException("key");
      } else {
         int var2 = this.this$0.hash(var1);
         return this.this$0.segmentFor(var2).getEntry(var1, var2);
      }
   }

   public boolean removeEntry(E var1) {
      if(var1 == null) {
         throw new NullPointerException("entry");
      } else {
         int var2 = this.this$0.strategy.getHash(var1);
         return this.this$0.segmentFor(var2).removeEntry(var1, var2);
      }
   }

   public boolean removeEntry(E var1, V var2) {
      if(var1 == null) {
         throw new NullPointerException("entry");
      } else {
         int var3 = this.this$0.strategy.getHash(var1);
         return this.this$0.segmentFor(var3).removeEntry(var1, var3, var2);
      }
   }
}
