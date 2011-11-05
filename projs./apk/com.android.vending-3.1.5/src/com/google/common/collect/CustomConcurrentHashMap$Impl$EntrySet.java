package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap;
import com.google.common.collect.CustomConcurrentHashMap$Impl$EntryIterator;
import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

final class CustomConcurrentHashMap$Impl$EntrySet extends AbstractSet<Entry<K, V>> {

   // $FF: synthetic field
   final Impl this$0;


   CustomConcurrentHashMap$Impl$EntrySet(Impl var1) {
      this.this$0 = var1;
   }

   public void clear() {
      this.this$0.clear();
   }

   public boolean contains(Object var1) {
      boolean var2 = false;
      if(var1 instanceof Entry) {
         Entry var3 = (Entry)var1;
         Object var4 = var3.getKey();
         if(var4 != null) {
            Object var5 = this.this$0.get(var4);
            if(var5 != null) {
               CustomConcurrentHashMap.Strategy var6 = this.this$0.strategy;
               Object var7 = var3.getValue();
               if(var6.equalValues(var5, var7)) {
                  var2 = true;
               }
            }
         }
      }

      return var2;
   }

   public boolean isEmpty() {
      return this.this$0.isEmpty();
   }

   public Iterator<Entry<K, V>> iterator() {
      Impl var1 = this.this$0;
      return new CustomConcurrentHashMap$Impl$EntryIterator(var1);
   }

   public boolean remove(Object var1) {
      boolean var2 = false;
      if(var1 instanceof Entry) {
         Entry var3 = (Entry)var1;
         Object var4 = var3.getKey();
         if(var4 != null) {
            Impl var5 = this.this$0;
            Object var6 = var3.getValue();
            if(var5.remove(var4, var6)) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public int size() {
      return this.this$0.size();
   }
}
