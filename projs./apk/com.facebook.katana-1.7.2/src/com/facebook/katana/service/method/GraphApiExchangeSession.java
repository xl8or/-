package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.GraphApiMethod;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class GraphApiExchangeSession extends GraphApiMethod implements ApiMethodCallback {

   protected String mOAuthToken;
   protected FacebookSessionInfo mSessionInfo;


   private GraphApiExchangeSession(Context var1, String var2) {
      String var3 = Constants.URL.getGraphUrl(var1);
      super(var1, "POST", "oauth/exchange_sessions", var3);
      this.mParams.put("sessions", var2);
      Map var5 = this.mParams;
      String var6 = Long.toString(350685531728L);
      var5.put("client_id", var6);
      Object var8 = this.mParams.put("client_secret", "62f8ce9f74b12f84c123cc23437a4a32");
   }

   public static String RequestOAuthToken(Context var0) {
      AppSession var1 = AppSession.getActiveSession(var0, (boolean)0);
      String var2 = var1.getSessionInfo().sessionKey;
      GraphApiExchangeSession var3 = new GraphApiExchangeSession(var0, var2);
      return var1.postToService(var0, var3, 1001, 1020, (Bundle)null);
   }

   public void addAuthenticationData(FacebookSessionInfo var1) {}

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      if(var5 == 200) {
         FacebookSessionInfo var8 = var1.getSessionInfo();
         String var9 = this.mOAuthToken;
         FacebookSessionInfo var10 = new FacebookSessionInfo(var8, var9);
         var1.updateSessionInfo(var2, var10);
      }
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookSessionInfo.class);
      if(var2.size() == 1) {
         String var3 = ((FacebookSessionInfo)var2.get(0)).oAuthToken;
         this.mOAuthToken = var3;
      }
   }
}
