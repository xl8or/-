package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import com.facebook.katana.provider.CacheProvider;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.webview.MRootDataStore;

public class MRoot {

   public static final String KEY_PREFIX = "MRoot:";
   public static final int SWEEP_TTL = 1209600;
   public static final String TAG = MRoot.class.getName();
   public static final int TTL = 3600;
   protected static MRootDataStore store;


   public MRoot() {}

   public static void clearOldEntries(Context var0) {
      getStore().clearOldEntries(var0, 3600);
      Uri var1 = CacheProvider.SWEEP_PREFIX_CONTENT_URI;
      String var2 = Uri.encode("MRoot:");
      Uri var3 = Uri.withAppendedPath(var1, var2);
      String var4 = String.valueOf(1209600);
      Uri var5 = Uri.withAppendedPath(var3, var4);
      int var6 = var0.getContentResolver().delete(var5, (String)null, (String[])null);
   }

   public static Tuple<String, String> get(Context var0, Tuple<String, String> var1, MRoot.Listener var2) {
      return getStore().get(var0, var1, var2);
   }

   protected static MRootDataStore getStore() {
      if(store == null) {
         store = new MRootDataStore();
      }

      return store;
   }

   public static void reset(Context var0) {
      getStore().resetMemoryStore(var0);
      Uri var1 = CacheProvider.PREFIX_CONTENT_URI;
      String var2 = Uri.encode("MRoot:");
      Uri var3 = Uri.withAppendedPath(var1, var2);
      int var4 = var0.getContentResolver().delete(var3, (String)null, (String[])null);
   }

   public interface Listener {

      void onRootError(Tuple<MRoot.LoadError, String> var1);

      void onRootLoaded(Tuple<String, String> var1);
   }

   public static enum LoadError {

      // $FF: synthetic field
      private static final MRoot.LoadError[] $VALUES;
      NETWORK_ERROR("NETWORK_ERROR", 1),
      UNEXPECTED_REDIRECT("UNEXPECTED_REDIRECT", 0),
      UNKNOWN_ERROR("UNKNOWN_ERROR", 2);


      static {
         MRoot.LoadError[] var0 = new MRoot.LoadError[3];
         MRoot.LoadError var1 = UNEXPECTED_REDIRECT;
         var0[0] = var1;
         MRoot.LoadError var2 = NETWORK_ERROR;
         var0[1] = var2;
         MRoot.LoadError var3 = UNKNOWN_ERROR;
         var0[2] = var3;
         $VALUES = var0;
      }

      private LoadError(String var1, int var2) {}
   }
}
