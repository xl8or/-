package com.facebook.katana.webview;

import android.content.Context;
import android.os.Handler;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.webview.MRoot;
import com.facebook.katana.webview.MRootFetcher;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

class MRootClient implements ManagedDataStore.Client<Tuple<String, String>, Tuple<String, String>, Tuple<MRoot.LoadError, String>> {

   MRootClient() {}

   public Tuple<String, String> deserialize(String var1) {
      JSONTokener var2 = new JSONTokener(var1);

      Tuple var7;
      try {
         Object var3 = var2.nextValue();
         if(var3 instanceof JSONArray) {
            JSONArray var4 = (JSONArray)var3;
            if(var4.length() == 2) {
               String var5 = var4.getString(0);
               String var6 = var4.getString(1);
               var7 = new Tuple(var5, var6);
               return var7;
            }
         }
      } catch (JSONException var9) {
         ;
      }

      var7 = null;
      return var7;
   }

   public int getCacheTtl(Tuple<String, String> var1, Tuple<String, String> var2) {
      return 3600;
   }

   public String getKey(Tuple<String, String> var1) {
      Object[] var2 = new Object[]{"MRoot:", null, null};
      Object var3 = var1.d0;
      var2[1] = var3;
      Object var4 = var1.d1;
      var2[2] = var4;
      return String.format("%s<%s,%s>", var2);
   }

   public int getPersistentStoreTtl(Tuple<String, String> var1, Tuple<String, String> var2) {
      return 3600;
   }

   public String initiateNetworkRequest(Context var1, Tuple<String, String> var2, NetworkRequestCallback<Tuple<String, String>, Tuple<String, String>, Tuple<MRoot.LoadError, String>> var3) {
      try {
         Handler var4 = new Handler();
         (new MRootFetcher(var1, var2, var4, var3)).start();
      } catch (IOException var12) {
         MRoot.LoadError var6 = MRoot.LoadError.UNKNOWN_ERROR;
         Tuple var7 = new Tuple(var6, (Object)null);
         Object var11 = null;
         var3.callback(var1, (boolean)0, var2, (String)null, var11, var7);
      }

      return null;
   }

   public boolean staleDataAcceptable(Tuple<String, String> var1, Tuple<String, String> var2) {
      return true;
   }
}
