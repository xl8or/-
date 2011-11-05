package com.google.android.finsky.download;

import android.text.TextUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.persistence.KeyValueStore;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DownloadUriUrlMap {

   public static final String DATA_STORE_ID = "market_download_data";
   public static final String FILE_SUBDIR = "uri_url_map";
   private static final String URL_KEY = "url";
   private final KeyValueStore mStore;


   public DownloadUriUrlMap(KeyValueStore var1) {
      this.mStore = var1;
   }

   private String generateStoreKey(String var1) {
      try {
         String var2 = URLEncoder.encode(var1, "UTF-8");
         return var2;
      } catch (UnsupportedEncodingException var4) {
         throw new UnsupportedOperationException("Caught exception while encode ValueStore key.", var4);
      }
   }

   private Map<String, String> generateStoreValue(String var1) {
      HashMap var2 = Maps.newHashMap();
      var2.put("url", var1);
      return var2;
   }

   private String generateUriFromKey(String var1) {
      try {
         String var2 = URLDecoder.decode(var1, "UTF-8");
         return var2;
      } catch (UnsupportedEncodingException var4) {
         throw new UnsupportedOperationException("Caught exception while decode ValueStore key.", var4);
      }
   }

   private String getUrlFromStoreValue(Map<String, String> var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = (String)var1.get("url");
      }

      return var2;
   }

   public String get(String var1) {
      Utils.ensureOnMainThread();
      String var3;
      if(TextUtils.isEmpty(var1)) {
         Object[] var2 = new Object[0];
         FinskyLog.wtf("Empty uri pased to uri-url map", var2);
         var3 = null;
      } else {
         KeyValueStore var4 = this.mStore;
         String var5 = this.generateStoreKey(var1);
         Map var6 = var4.get(var5);
         var3 = this.getUrlFromStoreValue(var6);
      }

      return var3;
   }

   public Map<String, String> getGenericMap() {
      Map var1 = this.mStore.fetchAll();
      HashMap var2 = Maps.newHashMap();
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         String var5 = this.generateUriFromKey(var4);
         Map var6 = (Map)var1.get(var4);
         String var7 = this.getUrlFromStoreValue(var6);
         var2.put(var5, var7);
      }

      return var2;
   }

   public void put(String var1, String var2) {
      Utils.ensureOnMainThread();
      if(TextUtils.isEmpty(var1)) {
         Object[] var3 = new Object[0];
         FinskyLog.wtf("Empty uri pased to uri-url map", var3);
      } else {
         KeyValueStore var4 = this.mStore;
         String var5 = this.generateStoreKey(var1);
         Map var6 = this.generateStoreValue(var2);
         var4.put(var5, var6);
      }
   }

   public void remove(String var1) {
      Utils.ensureOnMainThread();
      if(TextUtils.isEmpty(var1)) {
         Object[] var2 = new Object[0];
         FinskyLog.wtf("Empty uri pased to uri-url map", var2);
      } else {
         KeyValueStore var3 = this.mStore;
         String var4 = this.generateStoreKey(var1);
         var3.delete(var4);
      }
   }
}
