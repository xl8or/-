package com.facebook.katana.features;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.GkManagedStoreClient;
import com.facebook.katana.util.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Gatekeeper {

   public static final Map<String, Gatekeeper.Settings> GATEKEEPER_PROJECTS = Collections.unmodifiableMap(new Gatekeeper.1());
   public static final String TAG = Utils.getClassName(Gatekeeper.class);
   protected static ManagedDataStore<String, Boolean, Object> store;


   public Gatekeeper() {}

   public static void cachePrefill(Context var0, Map<String, Boolean> var1) {
      ManagedDataStore var2 = getStore();
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         String var6 = ((Boolean)var4.getValue()).toString();
         Object var7 = var4.getValue();
         var2.callback(var0, (boolean)1, var5, var6, var7, (Object)null);
      }

   }

   public static Boolean get(Context var0, String var1) {
      return (Boolean)getStore().get(var0, var1);
   }

   protected static ManagedDataStore<String, Boolean, Object> getStore() {
      if(store == null) {
         GkManagedStoreClient var0 = new GkManagedStoreClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   public static void reset() {
      store = null;
   }

   static class 1 extends HashMap<String, Gatekeeper.Settings> {

      1() {
         Gatekeeper.CachePolicy var1 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var2 = Gatekeeper.CachePolicy.CACHE_POSITIVE;
         Gatekeeper.Settings var3 = new Gatekeeper.Settings(var1, var2);
         this.put("places", var3);
         Gatekeeper.CachePolicy var5 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var6 = Gatekeeper.CachePolicy.CACHE_NONE;
         Gatekeeper.Settings var7 = new Gatekeeper.Settings(var5, var6);
         this.put("android_beta", var7);
         Gatekeeper.CachePolicy var9 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var10 = Gatekeeper.CachePolicy.CACHE_POSITIVE;
         Gatekeeper.Settings var11 = new Gatekeeper.Settings(var9, var10);
         this.put("android_ci_legal_screen", var11);
         Gatekeeper.CachePolicy var13 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var14 = Gatekeeper.CachePolicy.CACHE_POSITIVE;
         Gatekeeper.Settings var15 = new Gatekeeper.Settings(var13, var14);
         this.put("android_ci_legal_bar", var15);
         Gatekeeper.CachePolicy var17 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var18 = Gatekeeper.CachePolicy.CACHE_POSITIVE;
         Gatekeeper.Settings var19 = new Gatekeeper.Settings(var17, var18);
         this.put("android_ci_kddi_intro_enabled", var19);
         Gatekeeper.CachePolicy var21 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var22 = Gatekeeper.CachePolicy.CACHE_POSITIVE;
         Gatekeeper.Settings var23 = new Gatekeeper.Settings(var21, var22);
         this.put("android_ci_alert_enabled", var23);
         Gatekeeper.CachePolicy var25 = Gatekeeper.CachePolicy.CACHE_1HOUR;
         Gatekeeper.CachePolicy var26 = Gatekeeper.CachePolicy.CACHE_1HOUR;
         Gatekeeper.Settings var27 = new Gatekeeper.Settings(var25, var26);
         this.put("faceweb_android", var27);
         Gatekeeper.CachePolicy var29 = Gatekeeper.CachePolicy.CACHE_1HOUR;
         Gatekeeper.CachePolicy var30 = Gatekeeper.CachePolicy.CACHE_1HOUR;
         Gatekeeper.Settings var31 = new Gatekeeper.Settings(var29, var30);
         this.put("android_deep_links", var31);
         Gatekeeper.CachePolicy var33 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var34 = Gatekeeper.CachePolicy.CACHE_POSITIVE;
         Gatekeeper.Settings var35 = new Gatekeeper.Settings(var33, var34);
         this.put("meta_composer", var35);
         Gatekeeper.CachePolicy var37 = Gatekeeper.CachePolicy.CACHE_ALL;
         Gatekeeper.CachePolicy var38 = Gatekeeper.CachePolicy.CACHE_1HOUR;
         Gatekeeper.Settings var39 = new Gatekeeper.Settings(var37, var38);
         this.put("android_fw_ssl", var39);
      }
   }

   public static class Settings {

      public final Gatekeeper.CachePolicy memoryCachePolicy;
      public final Gatekeeper.CachePolicy persistentCachePolicy;


      public Settings(Gatekeeper.CachePolicy var1, Gatekeeper.CachePolicy var2) {
         this.memoryCachePolicy = var1;
         this.persistentCachePolicy = var2;
      }
   }

   public static enum CachePolicy {

      // $FF: synthetic field
      private static final Gatekeeper.CachePolicy[] $VALUES;
      CACHE_1HOUR("CACHE_1HOUR", 4, 3600),
      CACHE_ALL("CACHE_ALL", 0, (boolean)1, (boolean)1),
      CACHE_NEGATIVE("CACHE_NEGATIVE", 2, (boolean)0, (boolean)1),
      CACHE_NONE("CACHE_NONE", 3, (boolean)0, (boolean)0),
      CACHE_POSITIVE("CACHE_POSITIVE", 1, (boolean)1, (boolean)0);
      public final boolean cacheIfFalse;
      public final boolean cacheIfTrue;
      public final int fallbackTtl;


      static {
         Gatekeeper.CachePolicy[] var0 = new Gatekeeper.CachePolicy[5];
         Gatekeeper.CachePolicy var1 = CACHE_ALL;
         var0[0] = var1;
         Gatekeeper.CachePolicy var2 = CACHE_POSITIVE;
         var0[1] = var2;
         Gatekeeper.CachePolicy var3 = CACHE_NEGATIVE;
         var0[2] = var3;
         Gatekeeper.CachePolicy var4 = CACHE_NONE;
         var0[3] = var4;
         Gatekeeper.CachePolicy var5 = CACHE_1HOUR;
         var0[4] = var5;
         $VALUES = var0;
      }

      private CachePolicy(String var1, int var2, int var3) {
         this.cacheIfTrue = (boolean)0;
         this.cacheIfFalse = (boolean)0;
         this.fallbackTtl = var3;
      }

      private CachePolicy(String var1, int var2, boolean var3, boolean var4) {
         this.cacheIfTrue = var3;
         this.cacheIfFalse = var4;
         this.fallbackTtl = 0;
      }
   }
}
