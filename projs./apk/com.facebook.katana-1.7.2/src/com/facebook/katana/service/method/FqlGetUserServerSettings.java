package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.UserServerSetting;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Iterator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetUserServerSettings extends FqlGeneratedQuery implements ApiMethodCallback {

   private static final String TAG = "FqlGetUserServerSettings";
   protected NetworkRequestCallback<String, String, Object> mCallback;
   protected String mValue;
   public final String projectName;
   public final String settingName;


   public FqlGetUserServerSettings(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, String var6, NetworkRequestCallback<String, String, Object> var7) {
      String var8 = buildWhereClause(var5, var6);
      super(var1, var2, var3, var4, "user_settings", var8, UserServerSetting.class);
      this.mCallback = var7;
      this.projectName = var5;
      this.settingName = var6;
   }

   public static String RequestSettingsByProjectSetting(AppSession var0, Context var1, String var2, String var3, NetworkRequestCallback<String, String, Object> var4) {
      String var5 = var0.getSessionInfo().sessionKey;
      Object var7 = null;
      FqlGetUserServerSettings var11 = new FqlGetUserServerSettings(var1, (Intent)null, var5, (ApiMethodListener)var7, var2, var3, var4);
      Object var15 = null;
      return var0.postToService(var1, var11, 1001, 1020, (Bundle)var15);
   }

   protected static String buildWhereClause(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("project=");
      String var4 = StringUtils.FQLEscaper.formatString(var0);
      StringBuilder var5 = var3.append(var4).append(" AND ").append("setting=");
      String var6 = StringUtils.FQLEscaper.formatString(var1);
      var5.append(var6);
      return var2.toString();
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      byte var8;
      if(var5 == 200 && this.mValue != null) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      NetworkRequestCallback var9 = this.mCallback;
      String var10 = this.settingName;
      String var11 = this.mValue;
      String var12 = this.mValue;
      var9.callback(var2, (boolean)var8, var10, var11, var12, (Object)null);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      Iterator var2 = JMParser.parseObjectListJson(var1, UserServerSetting.class).iterator();

      while(var2.hasNext()) {
         UserServerSetting var3 = (UserServerSetting)var2.next();
         String var4 = var3.mProjectName;
         String var5 = this.projectName;
         if(var4.equals(var5)) {
            String var6 = var3.mSettingName;
            String var7 = this.settingName;
            if(var6.equals(var7)) {
               String var8 = var3.mValue;
               this.mValue = var8;
            }
         }
      }

   }
}
