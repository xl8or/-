package com.android.i18n.addressinput;

import android.util.Log;
import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.ClientCacheManager;
import com.android.i18n.addressinput.DataLoadListener;
import com.android.i18n.addressinput.JsoMap;
import com.android.i18n.addressinput.JsonpRequestBuilder;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.RegionDataConstants;
import com.android.i18n.addressinput.SimpleClientCacheManager;
import com.android.i18n.addressinput.Util;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class CacheData {

   private static final String TAG = "CacheData";
   private static final int TIMEOUT = 5000;
   private final HashSet<String> mBadKeys;
   private final JsoMap mCache;
   private final ClientCacheManager mClientCacheManager;
   private final HashSet<String> mRequestedKeys;
   private String mServiceUrl;
   private final HashMap<LookupKey, HashSet<CacheData.CacheListener>> mTemporaryListenerStore;


   public CacheData() {
      SimpleClientCacheManager var1 = new SimpleClientCacheManager();
      this((ClientCacheManager)var1);
   }

   public CacheData(ClientCacheManager var1) {
      HashSet var2 = new HashSet();
      this.mRequestedKeys = var2;
      HashSet var3 = new HashSet();
      this.mBadKeys = var3;
      HashMap var4 = new HashMap();
      this.mTemporaryListenerStore = var4;
      this.mClientCacheManager = var1;
      String var5 = this.mClientCacheManager.getAddressServerUrl();
      this.setUrl(var5);
      JsoMap var6 = JsoMap.createEmptyJsoMap();
      this.mCache = var6;
   }

   public CacheData(String var1) {
      HashSet var2 = new HashSet();
      this.mRequestedKeys = var2;
      HashSet var3 = new HashSet();
      this.mBadKeys = var3;
      HashMap var4 = new HashMap();
      this.mTemporaryListenerStore = var4;
      SimpleClientCacheManager var5 = new SimpleClientCacheManager();
      this.mClientCacheManager = var5;
      String var6 = this.mClientCacheManager.getAddressServerUrl();
      this.setUrl(var6);
      boolean var15 = false;

      JsoMap var7;
      label39: {
         try {
            var15 = true;
            var7 = JsoMap.buildJsoMap(var1);
            var15 = false;
            break label39;
         } catch (JSONException var16) {
            int var10 = Log.w("CacheData", "Could not parse json string, creating empty cache instead.");
            var7 = JsoMap.createEmptyJsoMap();
            var15 = false;
         } finally {
            if(var15) {
               this.mCache = null;
            }
         }

         this.mCache = var7;
         return;
      }

      this.mCache = var7;
   }

   private void addListenerToTempStore(LookupKey var1, CacheData.CacheListener var2) {
      Util.checkNotNull(var1);
      Util.checkNotNull(var2);
      HashSet var3 = (HashSet)this.mTemporaryListenerStore.get(var1);
      if(var3 == null) {
         var3 = new HashSet();
         this.mTemporaryListenerStore.put(var1, var3);
      }

      var3.add(var2);
   }

   private void notifyListenersAfterJobDone(String var1) {
      LookupKey var2 = (new LookupKey.Builder(var1)).build();
      HashSet var3 = (HashSet)this.mTemporaryListenerStore.get(var2);
      if(var3 != null) {
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            CacheData.CacheListener var5 = (CacheData.CacheListener)var4.next();
            String var6 = var1.toString();
            var5.onAdd(var6);
         }

         var3.clear();
      }
   }

   private void triggerDataLoadingEndIfNotNull(DataLoadListener var1) {
      if(var1 != null) {
         var1.dataLoadingEnd();
      }
   }

   void addToJsoMap(String var1, JSONObject var2) {
      this.mCache.putObj(var1, var2);
   }

   public boolean containsKey(String var1) {
      return this.mCache.containsKey(var1);
   }

   void fetchDynamicData(LookupKey var1, JSONObject var2, DataLoadListener var3) {
      Util.checkNotNull(var1, "null key not allowed.");
      if(var3 != null) {
         var3.dataLoadingBegin();
      }

      JsoMap var4 = this.mCache;
      String var5 = var1.toString();
      if(var4.containsKey(var5)) {
         this.triggerDataLoadingEndIfNotNull(var3);
      } else {
         HashSet var6 = this.mBadKeys;
         String var7 = var1.toString();
         if(var6.contains(var7)) {
            this.triggerDataLoadingEndIfNotNull(var3);
         } else {
            HashSet var8 = this.mRequestedKeys;
            String var9 = var1.toString();
            if(!var8.add(var9)) {
               String var10 = "data for key " + var1 + " requested but not cached yet";
               int var11 = Log.d("CacheData", var10);
               CacheData.1 var12 = new CacheData.1(var3);
               this.addListenerToTempStore(var1, var12);
            } else {
               ClientCacheManager var13 = this.mClientCacheManager;
               String var14 = var1.toString();
               String var15 = var13.get(var14);
               if(var15 != null && var15.length() > 0) {
                  String var16 = var1.toString();
                  CacheData.JsonHandler var20 = new CacheData.JsonHandler(var16, var2, var3, (CacheData.1)null);

                  try {
                     JsoMap var21 = JsoMap.buildJsoMap(var15);
                     var20.handleJson(var21);
                     return;
                  } catch (JSONException var37) {
                     String var23 = "Data from client\'s cache is in the wrong format: " + var15;
                     int var24 = Log.w("CacheData", var23);
                  }
               }

               JsonpRequestBuilder var25 = new JsonpRequestBuilder();
               var25.setTimeout(5000);
               String var26 = var1.toString();
               CacheData.JsonHandler var30 = new CacheData.JsonHandler(var26, var2, var3, (CacheData.1)null);
               StringBuilder var31 = new StringBuilder();
               String var32 = this.mServiceUrl;
               StringBuilder var33 = var31.append(var32).append("/");
               String var34 = var1.toString();
               String var35 = var33.append(var34).toString();
               CacheData.2 var36 = new CacheData.2(var1, var3, var30);
               var25.requestObject(var35, var36);
            }
         }
      }
   }

   public String get(String var1) {
      Util.checkNotNull(var1, "null key not allowed");
      return this.mCache.get(var1);
   }

   void getFromRegionDataConstants(LookupKey var1) {
      Util.checkNotNull(var1, "null key not allowed.");
      Map var2 = RegionDataConstants.getCountryFormatMap();
      AddressField var3 = AddressField.COUNTRY;
      String var4 = var1.getValueForUpperLevelField(var3);
      String var5 = (String)var2.get(var4);
      if(var5 != null) {
         try {
            JsoMap var6 = this.mCache;
            String var7 = var1.toString();
            JsoMap var8 = JsoMap.buildJsoMap(var5);
            var6.putObj(var7, var8);
         } catch (JSONException var12) {
            String var10 = "Failed to parse data for key " + var1 + " from RegionDataConstants";
            int var11 = Log.w("CacheData", var10);
         }
      }
   }

   public String getJsonString() {
      return this.mCache.toString();
   }

   public JsoMap getObj(String var1) {
      Util.checkNotNull(var1, "null key not allowed");
      return this.mCache.getObj(var1);
   }

   public String getUrl() {
      return this.mServiceUrl;
   }

   boolean isEmpty() {
      boolean var1;
      if(this.mCache.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setUrl(String var1) {
      Util.checkNotNull(var1, "Cannot set URL of address data server to null.");
      this.mServiceUrl = var1;
   }

   private interface CacheListener extends EventListener {

      void onAdd(String var1);
   }

   private class JsonHandler {

      private final JSONObject mExistingJso;
      private final String mKey;
      private final DataLoadListener mListener;


      private JsonHandler(String var2, JSONObject var3, DataLoadListener var4) {
         Util.checkNotNull(var2);
         this.mKey = var2;
         this.mExistingJso = var3;
         this.mListener = var4;
      }

      // $FF: synthetic method
      JsonHandler(String var2, JSONObject var3, DataLoadListener var4, CacheData.1 var5) {
         this(var2, var3, var4);
      }

      private void handleJson(JsoMap var1) {
         if(var1 == null) {
            StringBuilder var2 = (new StringBuilder()).append("server returns null for key:");
            String var3 = this.mKey;
            String var4 = var2.append(var3).toString();
            int var5 = Log.w("CacheData", var4);
            HashSet var6 = CacheData.this.mBadKeys;
            String var7 = this.mKey;
            var6.add(var7);
            CacheData var9 = CacheData.this;
            String var10 = this.mKey;
            var9.notifyListenersAfterJobDone(var10);
            CacheData var11 = CacheData.this;
            DataLoadListener var12 = this.mListener;
            var11.triggerDataLoadingEndIfNotNull(var12);
         } else {
            String var14 = AddressDataKey.ID.name().toLowerCase();
            if(!var1.has(var14)) {
               StringBuilder var15 = (new StringBuilder()).append("invalid or empty data returned for key: ");
               String var16 = this.mKey;
               String var17 = var15.append(var16).toString();
               int var18 = Log.w("CacheData", var17);
               HashSet var19 = CacheData.this.mBadKeys;
               String var20 = this.mKey;
               var19.add(var20);
               CacheData var22 = CacheData.this;
               String var23 = this.mKey;
               var22.notifyListenersAfterJobDone(var23);
               CacheData var24 = CacheData.this;
               DataLoadListener var25 = this.mListener;
               var24.triggerDataLoadingEndIfNotNull(var25);
            } else {
               if(this.mExistingJso != null) {
                  JsoMap var26 = (JsoMap)this.mExistingJso;
                  var1.mergeData(var26);
               }

               JsoMap var27 = CacheData.this.mCache;
               String var28 = this.mKey;
               var27.putObj(var28, var1);
               CacheData var29 = CacheData.this;
               String var30 = this.mKey;
               var29.notifyListenersAfterJobDone(var30);
               CacheData var31 = CacheData.this;
               DataLoadListener var32 = this.mListener;
               var31.triggerDataLoadingEndIfNotNull(var32);
            }
         }
      }
   }

   class 2 implements JsonpRequestBuilder.AsyncCallback<JsoMap> {

      // $FF: synthetic field
      final CacheData.JsonHandler val$handler;
      // $FF: synthetic field
      final LookupKey val$key;
      // $FF: synthetic field
      final DataLoadListener val$listener;


      2(LookupKey var2, DataLoadListener var3, CacheData.JsonHandler var4) {
         this.val$key = var2;
         this.val$listener = var3;
         this.val$handler = var4;
      }

      public void onFailure(Throwable var1) {
         StringBuilder var2 = (new StringBuilder()).append("Request for key ");
         LookupKey var3 = this.val$key;
         String var4 = var2.append(var3).append(" failed").toString();
         int var5 = Log.w("CacheData", var4);
         HashSet var6 = CacheData.this.mRequestedKeys;
         String var7 = this.val$key.toString();
         var6.remove(var7);
         CacheData var9 = CacheData.this;
         String var10 = this.val$key.toString();
         var9.notifyListenersAfterJobDone(var10);
         CacheData var11 = CacheData.this;
         DataLoadListener var12 = this.val$listener;
         var11.triggerDataLoadingEndIfNotNull(var12);
      }

      public void onSuccess(JsoMap var1) {
         this.val$handler.handleJson(var1);
         String var2 = var1.toString();
         ClientCacheManager var3 = CacheData.this.mClientCacheManager;
         String var4 = this.val$key.toString();
         var3.put(var4, var2);
      }
   }

   class 1 implements CacheData.CacheListener {

      // $FF: synthetic field
      final DataLoadListener val$listener;


      1(DataLoadListener var2) {
         this.val$listener = var2;
      }

      public void onAdd(String var1) {
         CacheData var2 = CacheData.this;
         DataLoadListener var3 = this.val$listener;
         var2.triggerDataLoadingEndIfNotNull(var3);
      }
   }
}
