package com.android.email.combined.common;

import com.android.email.combined.common.CacheEntry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

   private ConcurrentHashMap<String, Object> mHashMap;
   private Set<String> mKey;


   public Cache() {
      ConcurrentHashMap var1 = new ConcurrentHashMap();
      this.mHashMap = var1;
      this.mKey = null;
   }

   public void add(CacheEntry var1) {
      ConcurrentHashMap var2 = this.mHashMap;
      String var3 = var1.getKey();
      Object var4 = var1.getValue();
      var2.put(var3, var4);
   }

   public void clear() {
      this.mHashMap.clear();
   }

   public void delete(String var1) {
      if(this.mHashMap.get(var1) != null) {
         this.mHashMap.remove(var1);
      }
   }

   public Object get(String var1) {
      return this.mHashMap.get(var1);
   }

   public String[] getKeys() {
      Set var1 = this.mHashMap.keySet();
      this.mKey = var1;
      Set var2 = this.mKey;
      String[] var3 = new String[this.mKey.size()];
      return (String[])((String[])var2.toArray(var3));
   }

   public void load(CacheEntry[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         ConcurrentHashMap var4 = this.mHashMap;
         String var5 = var1[var2].getKey();
         Object var6 = var1[var2].getValue();
         var4.put(var5, var6);
         ++var2;
      }
   }

   public int size() {
      return this.mHashMap.size();
   }

   public void update(CacheEntry var1) {
      ConcurrentHashMap var2 = this.mHashMap;
      String var3 = var1.getKey();
      if(var2.get(var3) != null) {
         ConcurrentHashMap var4 = this.mHashMap;
         String var5 = var1.getKey();
         Object var6 = var1.getValue();
         var4.put(var5, var6);
      }
   }
}
