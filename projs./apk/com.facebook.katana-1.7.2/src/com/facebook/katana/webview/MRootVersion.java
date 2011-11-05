package com.facebook.katana.webview;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.Utils;
import com.facebook.katana.webview.MRoot;
import com.facebook.katana.webview.MRootVersionClient;

public class MRootVersion {

   public static final String KEY = "MRootVersion";
   public static final String TAG = Utils.getClassName(MRoot.class);
   protected static ManagedDataStore<Object, String, Object> store;


   public MRootVersion() {}

   public static String get(Context var0) {
      return (String)getStore().get(var0, "MRootVersion");
   }

   protected static ManagedDataStore<Object, String, Object> getStore() {
      if(store == null) {
         MRootVersionClient var0 = new MRootVersionClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   public static void set(Context var0, String var1) {
      ManagedDataStore var2 = getStore();
      var2.callback(var0, (boolean)1, "MRootVersion", var1, var1, (Object)null);
   }
}
