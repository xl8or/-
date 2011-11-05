package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.GatekeeperSetting;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetGatekeeperSettings extends FqlGeneratedQuery implements ApiMethodCallback {

   private static final String TAG = "FqlGetGatekeeperSettings";
   protected static Map<String, Boolean> mGatekeeperSettings;
   protected NetworkRequestCallback<String, Boolean, Object> mCallback;
   protected Map<String, Boolean> mSettings;


   private FqlGetGatekeeperSettings(Context var1, Intent var2, String var3, ApiMethodListener var4, Set<String> var5, NetworkRequestCallback<String, Boolean, Object> var6) {
      String var7 = buildWhereClause(var5);
      super(var1, var2, var3, var4, "project_gating", var7, GatekeeperSetting.class);
      HashMap var13 = new HashMap();
      this.mSettings = var13;
      this.mCallback = var6;
   }

   public static String Get(Context var0, String var1, NetworkRequestCallback<String, Boolean, Object> var2) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      String var14;
      if(var3 != null && !var3.isRequestPending(402)) {
         FacebookSessionInfo var4 = var3.getSessionInfo();
         if(var4 == null) {
            var14 = null;
         } else {
            HashSet var5 = new HashSet();
            var5.add(var1);
            String var7 = var4.sessionKey;
            FqlGetGatekeeperSettings var10 = new FqlGetGatekeeperSettings(var0, (Intent)null, var7, (ApiMethodListener)null, var5, var2);
            var14 = var3.postToService(var0, var10, 1001, 401, (Bundle)null);
         }
      } else {
         var14 = null;
      }

      return var14;
   }

   public static String SyncAll(Context var0) {
      AppSession var1 = AppSession.getActiveSession(var0, (boolean)0);
      String var2;
      if(var1.isRequestPending(402)) {
         var2 = null;
      } else {
         Set var3 = Gatekeeper.GATEKEEPER_PROJECTS.keySet();
         String var4 = var1.getSessionInfo().sessionKey;
         Object var6 = null;
         Object var7 = null;
         FqlGetGatekeeperSettings var8 = new FqlGetGatekeeperSettings(var0, (Intent)null, var4, (ApiMethodListener)var6, var3, (NetworkRequestCallback)var7);
         Object var12 = null;
         var2 = var1.postToService(var0, var8, 1001, 402, (Bundle)var12);
      }

      return var2;
   }

   protected static String buildWhereClause(Set<String> var0) {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("project_name IN(");
      StringUtils.StringProcessor var3 = StringUtils.FQLEscaper;
      Object[] var4 = new Object[]{var0};
      StringUtils.join(var1, ",", var3, var4);
      StringBuilder var5 = var1.append(")");
      return var1.toString();
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      String var9 = "extended_type";
      byte var10 = -1;
      Iterator var16;
      switch(var3.getIntExtra(var9, var10)) {
      case 401:
         String var27 = null;
         String var28 = null;
         Boolean var29 = null;
         Iterator var30 = this.mSettings.entrySet().iterator();
         if(var30.hasNext()) {
            Entry var31 = (Entry)var30.next();
            var27 = (String)var31.getKey();
            var29 = (Boolean)var31.getValue();
         }

         short var33 = 200;
         byte var34;
         if(var5 == var33 && var27 != null && var29 != null) {
            var34 = 1;
         } else {
            var34 = 0;
         }

         byte var35 = 0;
         if(var29 != null) {
            var28 = var29.toString();
            var35 = var29.booleanValue();
         }

         NetworkRequestCallback var36 = this.mCallback;
         var36.callback(var2, (boolean)var34, var27, var28, var29, (Object)null);
         var16 = var1.getListeners().iterator();

         while(var16.hasNext()) {
            AppSessionListener var38 = (AppSessionListener)var16.next();
            var38.onGkSettingsGetComplete(var1, var4, var5, var6, var7, var27, (boolean)var35);
         }

         return;
      case 402:
         short var12 = 200;
         if(var5 == var12) {
            Map var13 = this.mSettings;
            Gatekeeper.cachePrefill(var2, var13);
         }

         var16 = var1.getListeners().iterator();

         while(var16.hasNext()) {
            AppSessionListener var17 = (AppSessionListener)var16.next();
            Iterator var18 = this.mSettings.entrySet().iterator();

            while(var18.hasNext()) {
               Entry var19 = (Entry)var18.next();
               String var20 = (String)var19.getKey();
               boolean var21 = ((Boolean)var19.getValue()).booleanValue();
               var17.onGkSettingsGetComplete(var1, var4, var5, var6, var7, var20, var21);
            }
         }

         return;
      default:
      }
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      Iterator var2 = JMParser.parseObjectListJson(var1, GatekeeperSetting.class).iterator();

      while(var2.hasNext()) {
         GatekeeperSetting var3 = (GatekeeperSetting)var2.next();
         Map var4 = this.mSettings;
         String var5 = var3.mProjectName;
         Boolean var6 = Boolean.valueOf(var3.mEnabled);
         var4.put(var5, var6);
      }

   }
}
