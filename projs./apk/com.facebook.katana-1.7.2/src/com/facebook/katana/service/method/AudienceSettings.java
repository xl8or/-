package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.model.FriendList;
import com.facebook.katana.model.PrivacySetting;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.AudienceSettingsManagedStoreClient;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.service.method.FqlGetFriendLists;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class AudienceSettings extends FqlMultiQuery implements ApiMethodCallback {

   protected static ManagedDataStore<PrivacySetting.Category, AudienceSettings, Object> store;
   private NetworkRequestCallback<PrivacySetting.Category, AudienceSettings, Object> mCallback;
   private List<FriendList> mFriendLists;
   private List<FacebookGroup> mGroups;
   private PrivacySetting.Category mPrivacyCategory;
   private PrivacySetting mPrivacySetting;


   public AudienceSettings(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, PrivacySetting.Category var7, NetworkRequestCallback<PrivacySetting.Category, AudienceSettings, Object> var8) {
      LinkedHashMap var15 = buildQueries(var1, var2, var3, var5, var7);
      super(var1, var2, var3, var15, var4);
      this.mCallback = var8;
      this.mPrivacyCategory = var7;
   }

   protected static String RequestAudienceSettings(Context var0, PrivacySetting.Category var1, NetworkRequestCallback<PrivacySetting.Category, AudienceSettings, Object> var2) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      String var4 = var3.getSessionInfo().sessionKey;
      long var5 = var3.getSessionInfo().userId;
      Object var8 = null;
      AudienceSettings var11 = new AudienceSettings(var0, (Intent)null, var4, (ApiMethodListener)var8, var5, var1, var2);
      Object var15 = null;
      return var3.postToService(var0, var11, 1001, 1020, (Bundle)var15);
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3, PrivacySetting.Category var5) {
      LinkedHashMap var6 = new LinkedHashMap();
      AudienceSettings.FqlGetPrivacySetting var11 = new AudienceSettings.FqlGetPrivacySetting(var0, var1, var2, (ApiMethodListener)null, var5);
      var6.put("privacy_setting", var11);
      FqlGetFriendLists var18 = new FqlGetFriendLists(var0, var1, var2, (ApiMethodListener)null, var3);
      var6.put("friendlists", var18);
      return var6;
   }

   public static AudienceSettings get(Context var0, PrivacySetting.Category var1) {
      return (AudienceSettings)getStore().get(var0, var1);
   }

   protected static ManagedDataStore<PrivacySetting.Category, AudienceSettings, Object> getStore() {
      synchronized(AudienceSettings.class){}

      ManagedDataStore var1;
      try {
         if(store == null) {
            AudienceSettingsManagedStoreClient var0 = new AudienceSettingsManagedStoreClient();
            store = new ManagedDataStore(var0);
         }

         var1 = store;
      } finally {
         ;
      }

      return var1;
   }

   public static void reset() {
      store = null;
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      short var9 = 200;
      byte var10;
      if(var5 == var9) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      NetworkRequestCallback var11 = this.mCallback;
      PrivacySetting.Category var12 = this.mPrivacyCategory;
      var11.callback(var2, (boolean)var10, var12, "", this, (Object)null);
      Iterator var15 = var1.getListeners().iterator();

      while(var15.hasNext()) {
         AppSessionListener var16 = (AppSessionListener)var15.next();
         var16.onGetAudienceSettingsComplete(var1, var4, var5, var6, var7, this);
      }

   }

   public NetworkRequestCallback<PrivacySetting.Category, AudienceSettings, Object> getCallback() {
      return this.mCallback;
   }

   public List<FriendList> getFriendLists() {
      return this.mFriendLists;
   }

   public List<FacebookGroup> getGroups() {
      return this.mGroups;
   }

   public PrivacySetting getPrivacySetting() {
      return this.mPrivacySetting;
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      PrivacySetting var3 = ((AudienceSettings.FqlGetPrivacySetting)this.getQueryByName("privacy_setting")).mPrivacySetting;
      this.mPrivacySetting = var3;
      List var4 = ((FqlGetFriendLists)this.getQueryByName("friendlists")).getFriendLists();
      this.mFriendLists = var4;
   }

   public void setPrivacySetting(PrivacySetting var1) {
      this.mPrivacySetting = var1;
   }

   static class FqlGetPrivacySetting extends FqlGeneratedQuery {

      PrivacySetting mPrivacySetting;


      protected FqlGetPrivacySetting(Context var1, Intent var2, String var3, ApiMethodListener var4, PrivacySetting.Category var5) {
         String var6 = buildWhereClause(var5);
         this(var1, var2, var3, var4, var6);
      }

      protected FqlGetPrivacySetting(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
         super(var1, var2, var3, var4, "privacy_setting", var5, PrivacySetting.class);
      }

      protected static String buildWhereClause(PrivacySetting.Category var0) {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("name=");
         String var3 = var0.toString();
         StringUtils.appendEscapedFQLString(var1, var3);
         return var1.toString();
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         List var2 = JMParser.parseObjectListJson(var1, PrivacySetting.class);
         if(!var2.isEmpty()) {
            PrivacySetting var3 = (PrivacySetting)var2.get(0);
            this.mPrivacySetting = var3;
         }
      }
   }
}
