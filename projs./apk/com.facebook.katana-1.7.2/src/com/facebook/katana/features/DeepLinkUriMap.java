package com.facebook.katana.features;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.features.DeepLinkUriMapClient;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeepLinkUriMap {

   public static final Map<String, String> LOCAL_DEV_MAPPINGS = new DeepLinkUriMap.1();
   protected static ManagedDataStore<Object, UriTemplateMap<IntentUriHandler.UriHandler>, Object> store;


   public DeepLinkUriMap() {}

   public static UriTemplateMap<IntentUriHandler.UriHandler> get(Context var0) {
      return (UriTemplateMap)getStore().get(var0, (Object)null);
   }

   protected static ManagedDataStore<Object, UriTemplateMap<IntentUriHandler.UriHandler>, Object> getStore() {
      if(store == null) {
         DeepLinkUriMapClient var0 = new DeepLinkUriMapClient();
         store = new ManagedDataStore(var0);
      }

      return store;
   }

   public static class DeepLinkUriHandler implements IntentUriHandler.UriHandler {

      public final String uriTemplate;


      public DeepLinkUriHandler(String var1) {
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

         return IntentUriHandler.getIntentForUri(var1, var3);
      }
   }

   static class 1 extends LinkedHashMap<String, String> {

      1() {}
   }
}
