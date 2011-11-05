package com.google.android.finsky.utils.persistence;

import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.utils.Sets;
import com.google.android.finsky.utils.persistence.KeyValueStore;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WriteThroughKeyValueStore implements KeyValueStore {

   private static final ExecutorService sWriteThread = Executors.newSingleThreadExecutor();
   private final KeyValueStore mBackingStore;
   private Map<String, Map<String, String>> mDataMap = null;
   private final Handler mHandler;
   private Set<Runnable> mOnCompleteListeners;


   public WriteThroughKeyValueStore(KeyValueStore var1) {
      HashSet var2 = Sets.newHashSet();
      this.mOnCompleteListeners = var2;
      this.mBackingStore = var1;
      Looper var3 = Looper.getMainLooper();
      Handler var4 = new Handler(var3);
      this.mHandler = var4;
   }

   WriteThroughKeyValueStore(KeyValueStore var1, Handler var2) {
      HashSet var3 = Sets.newHashSet();
      this.mOnCompleteListeners = var3;
      this.mBackingStore = var1;
      this.mHandler = var2;
   }

   private void ensureOnMainThread() {
      Looper var1 = Looper.myLooper();
      Looper var2 = Looper.getMainLooper();
      if(var1 != var2) {
         throw new IllegalStateException("Tried to access data off of the main thread.");
      }
   }

   private void ensureReadyAndOnMainThread() {
      if(this.mDataMap == null) {
         throw new IllegalStateException("Tried to access data before initializing.");
      } else {
         this.ensureOnMainThread();
      }
   }

   private void fetchAllFromBackingStoreAsync() {
      ExecutorService var1 = sWriteThread;
      WriteThroughKeyValueStore.3 var2 = new WriteThroughKeyValueStore.3();
      var1.submit(var2);
   }

   private void handleDataLoaded(Map<String, Map<String, String>> var1) {
      this.mDataMap = var1;
      Iterator var2 = this.mOnCompleteListeners.iterator();

      while(var2.hasNext()) {
         ((Runnable)var2.next()).run();
      }

      this.mOnCompleteListeners.clear();
   }

   public void delete(String var1) {
      this.ensureReadyAndOnMainThread();
      this.mDataMap.remove(var1);
      WriteThroughKeyValueStore.1 var3 = new WriteThroughKeyValueStore.1(var1);
      Future var4 = sWriteThread.submit(var3);
   }

   public Map<String, Map<String, String>> fetchAll() {
      this.ensureReadyAndOnMainThread();
      HashMap var1 = new HashMap();
      Iterator var2 = this.mDataMap.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         Map var4 = Collections.unmodifiableMap((Map)this.mDataMap.get(var3));
         var1.put(var3, var4);
      }

      return Collections.unmodifiableMap(var1);
   }

   public void forceSynchronousLoad() {
      this.ensureOnMainThread();
      Map var1 = this.mBackingStore.fetchAll();
      this.mDataMap = var1;
      this.mOnCompleteListeners.clear();
   }

   public Map<String, String> get(String var1) {
      this.ensureReadyAndOnMainThread();
      Map var2 = (Map)this.mDataMap.get(var1);
      Map var3;
      if(var2 != null) {
         var3 = Collections.unmodifiableMap(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public void load(Runnable var1) {
      this.ensureOnMainThread();
      if(this.mDataMap != null) {
         this.mHandler.post(var1);
      } else {
         this.mOnCompleteListeners.add(var1);
         if(this.mOnCompleteListeners.size() == 1) {
            this.fetchAllFromBackingStoreAsync();
         }
      }
   }

   public void put(String var1, Map<String, String> var2) {
      this.ensureReadyAndOnMainThread();
      this.mDataMap.put(var1, var2);
      HashMap var4 = new HashMap(var2);
      WriteThroughKeyValueStore.2 var5 = new WriteThroughKeyValueStore.2(var1, var4);
      Future var6 = sWriteThread.submit(var5);
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final String val$key;


      1(String var2) {
         this.val$key = var2;
      }

      public void run() {
         KeyValueStore var1 = WriteThroughKeyValueStore.this.mBackingStore;
         String var2 = this.val$key;
         var1.delete(var2);
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         Map var1 = WriteThroughKeyValueStore.this.mBackingStore.fetchAll();
         Handler var2 = WriteThroughKeyValueStore.this.mHandler;
         WriteThroughKeyValueStore.3.1 var3 = new WriteThroughKeyValueStore.3.1(var1);
         var2.post(var3);
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final Map val$backingData;


         1(Map var2) {
            this.val$backingData = var2;
         }

         public void run() {
            WriteThroughKeyValueStore var1 = WriteThroughKeyValueStore.this;
            Map var2 = this.val$backingData;
            var1.handleDataLoaded(var2);
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final String val$key;
      // $FF: synthetic field
      final HashMap val$mapCopy;


      2(String var2, HashMap var3) {
         this.val$key = var2;
         this.val$mapCopy = var3;
      }

      public void run() {
         KeyValueStore var1 = WriteThroughKeyValueStore.this.mBackingStore;
         String var2 = this.val$key;
         HashMap var3 = this.val$mapCopy;
         var1.put(var2, var3);
      }
   }
}
