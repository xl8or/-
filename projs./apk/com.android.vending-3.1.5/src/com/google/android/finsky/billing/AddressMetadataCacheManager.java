package com.google.android.finsky.billing;

import com.android.i18n.addressinput.ClientCacheManager;
import com.android.volley.Cache;
import com.google.android.finsky.config.G;
import java.io.UnsupportedEncodingException;

public class AddressMetadataCacheManager implements ClientCacheManager {

   private static final int CACHE_EXPIRATION_S = 604800;
   private static final String ENCODING = "UTF-8";
   private static final String KEY_PREFIX = "AddressMetadataCacheManager-";
   private final Cache mCache;


   public AddressMetadataCacheManager(Cache var1) {
      this.mCache = var1;
   }

   public String get(String var1) {
      Cache var2 = this.mCache;
      String var3 = "AddressMetadataCacheManager-" + var1;
      Cache.Entry var4 = var2.get(var3);
      String var5;
      if(var4 != null && !var4.isExpired()) {
         try {
            byte[] var6 = var4.data;
            var5 = new String(var6, "UTF-8");
         } catch (UnsupportedEncodingException var8) {
            throw new RuntimeException("UTF-8 not supported.");
         }
      } else {
         var5 = "";
      }

      return var5;
   }

   public String getAddressServerUrl() {
      return (String)G.vendingAddressServerUrl.get();
   }

   public void put(String var1, String var2) {
      Cache.Entry var3 = new Cache.Entry();

      try {
         byte[] var4 = var2.getBytes("UTF-8");
         var3.data = var4;
      } catch (UnsupportedEncodingException var12) {
         throw new RuntimeException("UTF-8 not supported.");
      }

      long var5 = System.currentTimeMillis();
      var3.serverDate = var5;
      long var7 = var3.serverDate + 604800000L;
      var3.ttl = var7;
      Cache var9 = this.mCache;
      String var10 = "AddressMetadataCacheManager-" + var1;
      var9.put(var10, var3);
   }
}
