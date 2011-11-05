package com.facebook.katana.webview;

import android.content.Context;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.webview.MRoot;
import com.facebook.katana.webview.MRootClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class MRootDataStore extends ManagedDataStore<Tuple<String, String>, Tuple<String, String>, Tuple<MRoot.LoadError, String>> {

   protected Map<Tuple<String, String>, List<MRoot.Listener>> mListeners;


   public MRootDataStore() {
      MRootClient var1 = new MRootClient();
      super(var1);
      HashMap var2 = new HashMap();
      this.mListeners = var2;
   }

   public void callback(Context var1, boolean var2, Tuple<String, String> var3, String var4, Tuple<String, String> var5, Tuple<MRoot.LoadError, String> var6) {
      super.callback(var1, var2, var3, var4, var5, var6);
      List var7;
      synchronized(this) {
         var7 = (List)this.mListeners.remove(var3);
         if(var7 == null) {
            return;
         }
      }

      Iterator var9 = var7.iterator();

      while(var9.hasNext()) {
         MRoot.Listener var10 = (MRoot.Listener)var9.next();
         if(var2) {
            var10.onRootLoaded(var5);
         } else {
            var10.onRootError(var6);
         }
      }

   }

   public void clearOldEntries(Context var1, int var2) {
      long var3 = System.currentTimeMillis() / 1000L;
      long var5 = (long)var2;
      long var7 = var3 - var5;
      synchronized(this) {
         Iterator var9 = this.mData.entrySet().iterator();

         while(var9.hasNext()) {
            if(((ManagedDataStore.InternalStore)((Entry)var9.next()).getValue()).timestamp < var7) {
               var9.remove();
            }
         }

      }
   }

   public Tuple<String, String> get(Context var1, Tuple<String, String> var2) {
      throw new UnsupportedOperationException("don\'t call me directly");
   }

   public Tuple<String, String> get(Context var1, Tuple<String, String> var2, MRoot.Listener var3) {
      synchronized(this) {
         List var4 = (List)this.mListeners.get(var2);
         Tuple var6;
         if(var4 != null) {
            var4.add(var3);
            var6 = null;
         } else {
            ArrayList var7 = new ArrayList();
            var7.add(var3);
            this.mListeners.put(var2, var7);
            Tuple var10 = (Tuple)super.get(var1, var2);
            if(var10 != null) {
               this.mListeners.remove(var2);
               var6 = var10;
            } else {
               var6 = null;
            }
         }

         return var6;
      }
   }
}
