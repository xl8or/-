package com.facebook.katana.features;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.service.method.FqlGetGatekeeperSettings;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Utils;

class GkManagedStoreClient implements ManagedDataStore.Client<String, Boolean, Object> {

   public static final String TAG = Utils.getClassName(Gatekeeper.class);


   GkManagedStoreClient() {}

   public Boolean deserialize(String var1) {
      return Boolean.valueOf(var1);
   }

   public int getCacheTtl(String var1, Boolean var2) {
      Gatekeeper.Settings var3 = (Gatekeeper.Settings)Gatekeeper.GATEKEEPER_PROJECTS.get(var1);
      int var6;
      if(var3 == null) {
         String var4 = TAG;
         String var5 = "received a request for an unknown project: " + var1;
         Log.e(var4, var5);
         var6 = 0;
      } else {
         Gatekeeper.CachePolicy var7 = var3.memoryCachePolicy;
         var6 = this.mapPolicyToTtl(var7, var2);
      }

      return var6;
   }

   public String getKey(String var1) {
      return "gk:" + var1;
   }

   public int getPersistentStoreTtl(String var1, Boolean var2) {
      Gatekeeper.Settings var3 = (Gatekeeper.Settings)Gatekeeper.GATEKEEPER_PROJECTS.get(var1);
      int var6;
      if(var3 == null) {
         String var4 = TAG;
         String var5 = "received a request for an unknown project: " + var1;
         Log.e(var4, var5);
         var6 = 0;
      } else {
         Gatekeeper.CachePolicy var7 = var3.persistentCachePolicy;
         var6 = this.mapPolicyToTtl(var7, var2);
      }

      return var6;
   }

   public String initiateNetworkRequest(Context var1, String var2, NetworkRequestCallback<String, Boolean, Object> var3) {
      return FqlGetGatekeeperSettings.Get(var1, var2, var3);
   }

   protected int mapPolicyToTtl(Gatekeeper.CachePolicy var1, Boolean var2) {
      int var3;
      if((!var1.cacheIfTrue || var2.booleanValue() != 1) && (!var1.cacheIfFalse || var2.booleanValue())) {
         var3 = var1.fallbackTtl;
      } else {
         var3 = 31536000;
      }

      return var3;
   }

   public boolean staleDataAcceptable(String var1, Boolean var2) {
      return true;
   }
}
