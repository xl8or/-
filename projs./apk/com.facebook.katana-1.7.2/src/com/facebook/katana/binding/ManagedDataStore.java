package com.facebook.katana.binding;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.provider.CacheProvider;
import java.util.HashMap;
import java.util.Map;

public class ManagedDataStore<A extends Object, T extends Object, E extends Object> implements NetworkRequestCallback<A, T, E> {

   private static final String[] PROJECTION;
   protected static int globalGenerationId;
   protected int mCacheGenerationId;
   protected final ManagedDataStore.Client<A, T, E> mClient;
   protected Map<A, ManagedDataStore.InternalStore<T>> mData;


   static {
      String[] var0 = new String[]{"value", "timestamp"};
      PROJECTION = var0;
      globalGenerationId = 0;
   }

   public ManagedDataStore(ManagedDataStore.Client<A, T, E> var1) {
      this.mClient = var1;
      HashMap var2 = new HashMap();
      this.mData = var2;
      synchronized(ManagedDataStore.class) {
         int var3 = globalGenerationId;
         this.mCacheGenerationId = var3;
      }
   }

   static void dbSetValue(Context var0, String var1, String var2, long var3) {
      Uri var5 = CacheProvider.NAME_CONTENT_URI;
      String var6 = Uri.encode(var1);
      Uri var7 = Uri.withAppendedPath(var5, var6);
      ContentResolver var8 = var0.getContentResolver();
      String[] var9 = new String[]{"_id"};
      Object var10 = null;
      Object var11 = null;
      Cursor var12 = var8.query(var7, var9, (String)null, (String[])var10, (String)var11);
      if(var12 != null) {
         boolean var13 = var12.moveToFirst();
         var12.close();
         ContentValues var14 = new ContentValues();
         var14.put("value", var2);
         Long var15 = Long.valueOf(var3);
         var14.put("timestamp", var15);
         if(var13) {
            var8.update(var7, var14, (String)null, (String[])null);
         } else {
            var14.put("name", var1);
            Uri var17 = CacheProvider.CONTENT_URI;
            var8.insert(var17, var14);
         }
      }
   }

   public static void invalidateAllManagedDataStores() {
      synchronized(ManagedDataStore.class) {
         ++globalGenerationId;
      }
   }

   public void callback(Context var1, boolean var2, A var3, String var4, T var5, E var6) {
      if(var2) {
         long var7 = System.currentTimeMillis();
         if(this.mClient.getPersistentStoreTtl(var3, var5) > 0) {
            String var9 = this.mClient.getKey(var3);
            dbSetValue(var1, var9, var4, var7);
         }

         if(this.mClient.getCacheTtl(var3, var5) > 0) {
            ManagedDataStore.InternalStore var10 = new ManagedDataStore.InternalStore(var5, var7);
            synchronized(this) {
               this.mData.put(var3, var10);
            }
         }
      }
   }

   public T get(Context param1, A param2) {
      // $FF: Couldn't be decompiled
   }

   public void resetMemoryStore(Context var1) {
      synchronized(this) {
         HashMap var2 = new HashMap();
         this.mData = var2;
      }
   }

   public interface Client<A extends Object, T extends Object, E extends Object> {

      T deserialize(String var1);

      int getCacheTtl(A var1, T var2);

      String getKey(A var1);

      int getPersistentStoreTtl(A var1, T var2);

      String initiateNetworkRequest(Context var1, A var2, NetworkRequestCallback<A, T, E> var3);

      boolean staleDataAcceptable(A var1, T var2);
   }

   protected static class InternalStore<T extends Object> {

      T data;
      public final long timestamp;


      InternalStore(T var1, long var2) {
         this.data = var1;
         this.timestamp = var2;
      }
   }
}
