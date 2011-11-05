package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookApp;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetAppsProfile extends FqlGeneratedQuery {

   private final Map<Long, FacebookApp> mAppsMap;


   public FqlGetAppsProfile(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, "application", var5, FacebookApp.class);
      HashMap var12 = new HashMap();
      this.mAppsMap = var12;
   }

   public FqlGetAppsProfile(Context var1, Intent var2, String var3, Map<Long, FacebookApp> var4, ApiMethodListener var5) {
      String var6 = buildWhereClause(var4);
      super(var1, var2, var3, var5, "application", var6, FacebookApp.class);
      this.mAppsMap = var4;
   }

   private static String buildWhereClause(Map<Long, FacebookApp> var0) {
      StringBuilder var1 = new StringBuilder("(app_id IN(");
      boolean var2 = true;

      Long var4;
      for(Iterator var3 = var0.keySet().iterator(); var3.hasNext(); var1.append(var4)) {
         var4 = (Long)var3.next();
         if(!var2) {
            StringBuilder var5 = var1.append(',');
         } else {
            var2 = false;
         }
      }

      StringBuilder var7 = var1.append("))");
      return var1.toString();
   }

   public Map<Long, FacebookApp> getApps() {
      return Collections.unmodifiableMap(this.mAppsMap);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookApp.class);
      if(var2 != null) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            FacebookApp var4 = (FacebookApp)var3.next();
            Map var5 = this.mAppsMap;
            Long var6 = Long.valueOf(var4.mAppId);
            var5.put(var6, var4);
         }
      }

      FacebookApp var8 = null;
      Iterator var9 = this.mAppsMap.keySet().iterator();

      while(var9.hasNext()) {
         Long var10 = (Long)var9.next();
         if(this.mAppsMap.get(var10) == null) {
            String var11 = "App not found: " + var10;
            Log.w("FqlGetAppsProfile", var11);
            if(var8 == null) {
               long var12 = var10.longValue();
               var8 = new FacebookApp(var12, "", (String)null);
            }

            this.mAppsMap.put(var10, var8);
         }
      }

   }
}
