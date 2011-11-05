package com.facebook.katana.features.faceweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.activity.faceweb.FacewebChromeActivity;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.faceweb.FacewebUriMapClient;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FacewebUriMap {

   public static final Map<String, String> LOCAL_DEV_MAPPINGS = new FacewebUriMap.1();
   protected static ManagedDataStore<Object, UriTemplateMap<IntentUriHandler.UriHandler>, Object> store;


   public FacewebUriMap() {}

   public static UriTemplateMap<IntentUriHandler.UriHandler> get(Context var0) {
      return (UriTemplateMap)getStore().get(var0, (Object)null);
   }

   protected static ManagedDataStore<Object, UriTemplateMap<IntentUriHandler.UriHandler>, Object> getStore() {
      if(store == null) {
         FacewebUriMapClient var0 = new FacewebUriMapClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   static class 1 extends LinkedHashMap<String, String> {

      1() {}
   }

   public static class FacewebUriHandler implements IntentUriHandler.UriHandler {

      public final String uriTemplate;


      public FacewebUriHandler(String var1) {
         this.uriTemplate = var1;
      }

      public Intent handle(Context var1, Bundle var2) {
         String var3 = this.uriTemplate;

         String var6;
         String var7;
         for(Iterator var4 = var2.keySet().iterator(); var4.hasNext(); var3 = var3.replaceAll(var6, var7)) {
            String var5 = (String)var4.next();
            var6 = "<" + var5 + ">";
            var7 = Utils.getStringValue(var2, var5);
         }

         Intent var8 = new Intent(var1, FacewebChromeActivity.class);
         var8.putExtra("mobile_page", var3);
         return var8;
      }
   }
}
