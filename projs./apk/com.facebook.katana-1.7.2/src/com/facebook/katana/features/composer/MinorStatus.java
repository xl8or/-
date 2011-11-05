package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.composer.MinorStatusClient;

public class MinorStatus {

   protected static ManagedDataStore<Object, Boolean, Object> store;


   public MinorStatus() {}

   public static Boolean get(Context var0) {
      return (Boolean)getStore().get(var0, (Object)null);
   }

   protected static ManagedDataStore<Object, Boolean, Object> getStore() {
      synchronized(MinorStatus.class){}

      ManagedDataStore var1;
      try {
         if(store == null) {
            MinorStatusClient var0 = new MinorStatusClient();
            store = new ManagedDataStore(var0);
         }

         var1 = store;
      } finally {
         ;
      }

      return var1;
   }
}
