package com.facebook.katana.webview;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;

class MRootVersionClient implements ManagedDataStore.Client<Object, String, Object> {

   MRootVersionClient() {}

   public String deserialize(String var1) {
      return var1;
   }

   public int getCacheTtl(Object var1, String var2) {
      return 31536000;
   }

   public String getKey(Object var1) {
      return "MRootVersion";
   }

   public int getPersistentStoreTtl(Object var1, String var2) {
      return 31536000;
   }

   public String initiateNetworkRequest(Context var1, Object var2, NetworkRequestCallback<Object, String, Object> var3) {
      return null;
   }

   public boolean staleDataAcceptable(Object var1, String var2) {
      return true;
   }
}
