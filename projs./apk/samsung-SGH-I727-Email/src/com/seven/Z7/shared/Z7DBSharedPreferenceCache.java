package com.seven.Z7.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Z7DBSharedPreferenceCache {

   private ReentrantLock cacheLock;
   private Map<String, Object> preferenceCache;


   Z7DBSharedPreferenceCache(Map<String, Object> var1) {
      ReentrantLock var2 = new ReentrantLock();
      this.cacheLock = var2;
      HashMap var3 = new HashMap(var1);
      this.preferenceCache = var3;
   }

   private static List<String> findChangedPreferences(Map<String, Object> var0, Map<String, Object> var1) {
      ArrayList var2 = new ArrayList();
      List var3 = findNewOrChangedKeys(var0, var1);
      var2.addAll(var3);
      List var5 = findKeysDeletedInNewCache(var0, var1);
      var2.addAll(var5);
      return var2;
   }

   private static List<String> findKeysDeletedInNewCache(Map<String, Object> var0, Map<String, Object> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(!var0.containsKey(var4)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   private static List<String> findNewOrChangedKeys(Map<String, Object> var0, Map<String, Object> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         Object var5 = var0.get(var4);
         Object var6 = var1.get(var4);
         if(var5 != null && var5.equals(var6)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   boolean contains(String var1) {
      return this.preferenceCache.containsKey(var1);
   }

   Object get(String var1) {
      return this.preferenceCache.get(var1);
   }

   Map<String, Object> getAll() {
      Map var1 = this.preferenceCache;
      return new HashMap(var1);
   }

   List<String> refreshCache(Map<String, Object> var1) {
      this.cacheLock.lock();
      boolean var8 = false;

      List var5;
      try {
         var8 = true;
         if(this.preferenceCache == null) {
            throw new IllegalStateException("Called refreshPreferenceCache without initialized cache");
         }

         HashMap var3 = new HashMap(var1);
         Map var4 = this.preferenceCache;
         this.preferenceCache = var3;
         var5 = findChangedPreferences(var3, var4);
         var8 = false;
      } finally {
         if(var8) {
            this.cacheLock.unlock();
         }
      }

      this.cacheLock.unlock();
      return var5;
   }
}
