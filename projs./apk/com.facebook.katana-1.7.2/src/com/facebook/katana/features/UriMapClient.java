package com.facebook.katana.features;

import android.content.Context;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.FqlGetUserServerSettings;
import com.facebook.katana.util.UriTemplateMap;
import java.util.Map;

public abstract class UriMapClient implements ManagedDataStore.Client<Object, UriTemplateMap<IntentUriHandler.UriHandler>, Object> {

   public UriMapClient() {}

   public UriTemplateMap<IntentUriHandler.UriHandler> deserialize(String param1) {
      // $FF: Couldn't be decompiled
   }

   protected abstract Map<String, String> getDevMappings();

   protected abstract String getProjectMapSetting();

   protected abstract String getProjectName();

   protected abstract String getTag();

   protected abstract IntentUriHandler.UriHandler getUriHandler(String var1);

   public String initiateNetworkRequest(Context var1, Object var2, NetworkRequestCallback<Object, UriTemplateMap<IntentUriHandler.UriHandler>, Object> var3) {
      UriMapClient.1 var4 = new UriMapClient.1(var3);
      AppSession var5 = AppSession.getActiveSession(var1, (boolean)0);
      String var6 = this.getProjectName();
      String var7 = this.getProjectMapSetting();
      return FqlGetUserServerSettings.RequestSettingsByProjectSetting(var5, var1, var6, var7, var4);
   }

   public boolean staleDataAcceptable(Object var1, UriTemplateMap<IntentUriHandler.UriHandler> var2) {
      return true;
   }

   class 1 implements NetworkRequestCallback<String, String, Object> {

      // $FF: synthetic field
      final NetworkRequestCallback val$cb;


      1(NetworkRequestCallback var2) {
         this.val$cb = var2;
      }

      public void callback(Context var1, boolean var2, String var3, String var4, String var5, Object var6) {
         UriTemplateMap var7 = null;
         if(var2) {
            var7 = UriMapClient.this.deserialize(var5);
         }

         NetworkRequestCallback var8 = this.val$cb;
         var8.callback(var1, var2, var3, var5, var7, var6);
      }
   }
}
