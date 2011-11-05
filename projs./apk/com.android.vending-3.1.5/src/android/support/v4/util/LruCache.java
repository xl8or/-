package android.support.v4.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LruCache<K extends Object, V extends Object> {

   private int createCount;
   private int evictionCount;
   private int hitCount;
   private final LinkedHashMap<K, V> map;
   private int maxSize;
   private int missCount;
   private int putCount;
   private int size;


   public LruCache(int var1) {
      if(var1 <= 0) {
         throw new IllegalArgumentException("maxSize <= 0");
      } else {
         this.maxSize = var1;
         LinkedHashMap var2 = new LinkedHashMap(0, 0.75F, (boolean)1);
         this.map = var2;
      }
   }

   private int safeSizeOf(K var1, V var2) {
      int var3 = this.sizeOf(var1, var2);
      if(var3 < 0) {
         String var4 = "Negative size: " + var1 + "=" + var2;
         throw new IllegalStateException(var4);
      } else {
         return var3;
      }
   }

   private void trimToSize(int var1) {
      while(true) {
         Object var7;
         Object var8;
         synchronized(this) {
            if(this.size < 0 || this.map.isEmpty() && this.size != 0) {
               StringBuilder var2 = new StringBuilder();
               String var3 = this.getClass().getName();
               String var4 = var2.append(var3).append(".sizeOf() is reporting inconsistent results!").toString();
               throw new IllegalStateException(var4);
            }

            if(this.size <= var1 || this.map.isEmpty()) {
               return;
            }

            Entry var6 = (Entry)this.map.entrySet().iterator().next();
            var7 = var6.getKey();
            var8 = var6.getValue();
            this.map.remove(var7);
            int var10 = this.size;
            int var11 = this.safeSizeOf(var7, var8);
            int var12 = var10 - var11;
            this.size = var12;
            int var13 = this.evictionCount + 1;
            this.evictionCount = var13;
         }

         this.entryRemoved((boolean)1, var7, var8, (Object)null);
      }
   }

   protected V create(K var1) {
      return null;
   }

   public final int createCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.createCount;
      } finally {
         ;
      }

      return var1;
   }

   protected void entryRemoved(boolean var1, K var2, V var3, V var4) {}

   public final void evictAll() {
      this.trimToSize(-1);
   }

   public final int evictionCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.evictionCount;
      } finally {
         ;
      }

      return var1;
   }

   public final V get(K param1) {
      // $FF: Couldn't be decompiled
   }

   public final int hitCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.hitCount;
      } finally {
         ;
      }

      return var1;
   }

   public final int maxSize() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.maxSize;
      } finally {
         ;
      }

      return var1;
   }

   public final int missCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.missCount;
      } finally {
         ;
      }

      return var1;
   }

   public final V put(K param1, V param2) {
      // $FF: Couldn't be decompiled
   }

   public final int putCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.putCount;
      } finally {
         ;
      }

      return var1;
   }

   public final V remove(K param1) {
      // $FF: Couldn't be decompiled
   }

   public final int size() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.size;
      } finally {
         ;
      }

      return var1;
   }

   protected int sizeOf(K var1, V var2) {
      return 1;
   }

   public final Map<K, V> snapshot() {
      synchronized(this){}

      LinkedHashMap var2;
      try {
         LinkedHashMap var1 = this.map;
         var2 = new LinkedHashMap(var1);
      } finally {
         ;
      }

      return var2;
   }

   public final String toString() {
      int var1 = 0;
      synchronized(this){}
      boolean var14 = false;

      String var10;
      try {
         var14 = true;
         int var2 = this.hitCount;
         int var3 = this.missCount;
         int var4 = var2 + var3;
         if(var4 != 0) {
            var1 = this.hitCount * 100 / var4;
         }

         Object[] var5 = new Object[4];
         Integer var6 = Integer.valueOf(this.maxSize);
         var5[0] = var6;
         Integer var7 = Integer.valueOf(this.hitCount);
         var5[1] = var7;
         Integer var8 = Integer.valueOf(this.missCount);
         var5[2] = var8;
         Integer var9 = Integer.valueOf(var1);
         var5[3] = var9;
         var10 = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", var5);
         var14 = false;
      } finally {
         if(var14) {
            ;
         }
      }

      return var10;
   }
}
