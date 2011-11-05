package com.android.i18n.addressinput;

import android.util.Log;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressVerificationNodeData;
import com.android.i18n.addressinput.CacheData;
import com.android.i18n.addressinput.DataLoadListener;
import com.android.i18n.addressinput.DataSource;
import com.android.i18n.addressinput.JsoMap;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.NotifyingListener;
import com.android.i18n.addressinput.RegionDataConstants;
import com.android.i18n.addressinput.Util;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientData implements DataSource {

   private static final String TAG = "ClientData";
   private final Map<String, JsoMap> mBootstrapMap;
   private CacheData mCacheData;


   public ClientData(CacheData var1) {
      HashMap var2 = new HashMap();
      this.mBootstrapMap = var2;
      this.mCacheData = var1;
      this.buildRegionalData();
   }

   private void buildRegionalData() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = RegionDataConstants.getCountryFormatMap().keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = var3 + "~";
         var1.append(var4);
         String var6 = (String)RegionDataConstants.getCountryFormatMap().get(var3);
         JsoMap var7 = null;

         label29: {
            JsoMap var8;
            try {
               var8 = JsoMap.buildJsoMap(var6);
            } catch (JSONException var27) {
               break label29;
            }

            var7 = var8;
         }

         AddressData var9 = (new AddressData.Builder()).setCountry(var3).build();
         LookupKey.KeyType var10 = LookupKey.KeyType.DATA;
         LookupKey var11 = (new LookupKey.Builder(var10)).setAddressData(var9).build();
         Map var12 = this.mBootstrapMap;
         String var13 = var11.toString();
         var12.put(var13, var7);
      }

      int var15 = var1.length() + -1;
      var1.setLength(var15);
      StringBuilder var16 = (new StringBuilder()).append("{\"id\":\"data\",\"");
      String var17 = AddressDataKey.COUNTRIES.toString().toLowerCase();
      StringBuilder var18 = var16.append(var17).append("\": \"");
      String var19 = var1.toString();
      String var20 = var18.append(var19).append("\"}").toString();
      JsoMap var21 = null;

      label21: {
         JsoMap var22;
         try {
            var22 = JsoMap.buildJsoMap(var20);
         } catch (JSONException var26) {
            break label21;
         }

         var21 = var22;
      }

      this.mBootstrapMap.put("data", var21);
   }

   private void fetchDataIfNotAvailable(String var1) {
      if(this.mCacheData.getObj(var1) == null) {
         JsoMap var2 = (JsoMap)this.mBootstrapMap.get(var1);
         NotifyingListener var3 = new NotifyingListener(this);
         if(LookupKey.hasValidKeyPrefix(var1)) {
            LookupKey var4 = (new LookupKey.Builder(var1)).build();
            this.mCacheData.fetchDynamicData(var4, var2, var3);

            try {
               var3.waitLoadingEnd();
               if(this.mCacheData.getObj(var1) == null) {
                  if(this.isCountryKey(var1)) {
                     int var5 = Log.i("ClientData", "Server failure: looking up key in region data constants.");
                     this.mCacheData.getFromRegionDataConstants(var4);
                  }
               }
            } catch (InterruptedException var7) {
               throw new RuntimeException(var7);
            }
         }
      }
   }

   private String getCountryKey(String var1) {
      if(var1.split("/").length <= 1) {
         String var2 = "Cannot get country key with key \'" + var1 + "\'";
         throw new RuntimeException(var2);
      } else {
         if(!this.isCountryKey(var1)) {
            String[] var3 = var1.split("/");
            StringBuilder var4 = new StringBuilder();
            String var5 = var3[0];
            StringBuilder var6 = var4.append(var5).append("/");
            String var7 = var3[1];
            var1 = var6.append(var7).toString();
         }

         return var1;
      }
   }

   private boolean isCountryKey(String var1) {
      Util.checkNotNull(var1, "Cannot use null as a key");
      boolean var2;
      if(var1.split("/").length == 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isValidDataKey(String var1) {
      return var1.startsWith("data");
   }

   protected AddressVerificationNodeData createNodeData(JsoMap param1) {
      // $FF: Couldn't be decompiled
   }

   public AddressVerificationNodeData get(String var1) {
      JsoMap var2 = this.mCacheData.getObj(var1);
      if(var2 == null) {
         this.fetchDataIfNotAvailable(var1);
         var2 = this.mCacheData.getObj(var1);
      }

      AddressVerificationNodeData var3;
      if(var2 != null && this.isValidDataKey(var1)) {
         var3 = this.createNodeData(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public AddressVerificationNodeData getDefaultData(String var1) {
      JsoMap var2;
      AddressVerificationNodeData var4;
      if(var1.split("/").length == 1) {
         var2 = (JsoMap)this.mBootstrapMap.get(var1);
         if(var2 == null || !this.isValidDataKey(var1)) {
            String var3 = "key " + var1 + " does not have bootstrap data";
            throw new RuntimeException(var3);
         }

         var4 = this.createNodeData(var2);
      } else {
         var1 = this.getCountryKey(var1);
         var2 = (JsoMap)this.mBootstrapMap.get(var1);
         if(var2 == null || !this.isValidDataKey(var1)) {
            String var5 = "key " + var1 + " does not have bootstrap data";
            throw new RuntimeException(var5);
         }

         var4 = this.createNodeData(var2);
      }

      return var4;
   }

   public void prefetchCountry(String var1, DataLoadListener var2) {
      String var3 = "data/" + var1;
      HashSet var4 = new HashSet();
      var2.dataLoadingBegin();
      CacheData var5 = this.mCacheData;
      LookupKey var6 = (new LookupKey.Builder(var3)).build();
      ClientData.RecursiveLoader var7 = new ClientData.RecursiveLoader(var3, var4, var2);
      var5.fetchDynamicData(var6, (JSONObject)null, var7);
   }

   public void requestData(LookupKey var1, DataLoadListener var2) {
      Util.checkNotNull(var1, "Null lookup key not allowed");
      Map var3 = this.mBootstrapMap;
      String var4 = var1.toString();
      JsoMap var5 = (JsoMap)var3.get(var4);
      this.mCacheData.fetchDynamicData(var1, var5, var2);
   }

   private class RecursiveLoader implements DataLoadListener {

      private final String key;
      private final DataLoadListener listener;
      private final Set<ClientData.RecursiveLoader> loaders;


      public RecursiveLoader(String var2, Set var3, DataLoadListener var4) {
         this.key = var2;
         this.loaders = var3;
         this.listener = var4;
         synchronized(var3) {
            var3.add(this);
         }
      }

      public void dataLoadingBegin() {}

      public void dataLoadingEnd() {
         String var1 = AddressDataKey.SUB_KEYS.name().toLowerCase();
         String var2 = AddressDataKey.SUB_MORES.name().toLowerCase();
         CacheData var3 = ClientData.this.mCacheData;
         String var4 = this.key;
         JsoMap var5 = var3.getObj(var4);
         if(var5.containsKey(var2)) {
            String[] var6 = new String[0];
            String[] var7 = new String[0];
            String[] var8 = var5.get(var2).split("~");
            if(var5.containsKey(var1)) {
               var7 = var5.get(var1).split("~");
            }

            int var9 = var8.length;
            int var10 = var7.length;
            if(var9 != var10) {
               throw new IndexOutOfBoundsException("mores.length != keys.length");
            }

            int var11 = 0;

            while(true) {
               int var12 = var8.length;
               if(var11 >= var12) {
                  break;
               }

               if(var8[var11].equalsIgnoreCase("true")) {
                  StringBuilder var13 = new StringBuilder();
                  String var14 = this.key;
                  StringBuilder var15 = var13.append(var14).append("/");
                  String var16 = var7[var11];
                  String var17 = var15.append(var16).toString();
                  CacheData var18 = ClientData.this.mCacheData;
                  LookupKey var19 = (new LookupKey.Builder(var17)).build();
                  ClientData var20 = ClientData.this;
                  Set var21 = this.loaders;
                  DataLoadListener var22 = this.listener;
                  ClientData.RecursiveLoader var23 = var20.new RecursiveLoader(var17, var21, var22);
                  var18.fetchDynamicData(var19, (JSONObject)null, var23);
               }

               ++var11;
            }
         }

         Set var24 = this.loaders;
         synchronized(var24) {
            boolean var25 = this.loaders.remove(this);
            if(this.loaders.isEmpty()) {
               this.listener.dataLoadingEnd();
            }

         }
      }
   }
}
