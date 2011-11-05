package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.MinorStatusModel;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlGetMinorStatus extends FqlGeneratedQuery implements ApiMethodCallback {

   protected final NetworkRequestCallback<Object, Boolean, Object> mCb;
   protected Boolean mIsMinor;
   protected String mResponse;


   protected FqlGetMinorStatus(Context var1, Intent var2, String var3, ApiMethodListener var4, NetworkRequestCallback<Object, Boolean, Object> var5, long var6) {
      Object[] var8 = new Object[1];
      Long var9 = Long.valueOf(var6);
      var8[0] = var9;
      String var10 = String.format("uid=%d", var8);
      super(var1, var2, var3, var4, "user", var10, MinorStatusModel.class);
      this.mCb = var5;
   }

   public static String get(Context var0, NetworkRequestCallback<Object, Boolean, Object> var1, long var2) {
      AppSession var4 = AppSession.getActiveSession(var0, (boolean)0);
      String var5 = var4.getSessionInfo().sessionKey;
      Object var7 = null;
      FqlGetMinorStatus var11 = new FqlGetMinorStatus(var0, (Intent)null, var5, (ApiMethodListener)var7, var1, var2);
      Object var15 = null;
      return var4.postToService(var0, var11, 1001, 1020, (Bundle)var15);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      NetworkRequestCallback var8 = this.mCb;
      byte var9;
      if(var5 == 200) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      String var10 = this.mResponse;
      Boolean var11 = this.mIsMinor;
      Object var13 = null;
      var8.callback(var2, (boolean)var9, (Object)null, var10, var11, var13);
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonParser var2 = mJsonFactory.createJsonParser(var1);
      JsonToken var3 = var2.nextToken();
      JsonToken var4 = var2.getCurrentToken();
      JsonToken var5 = JsonToken.START_OBJECT;
      if(var4 == var5) {
         JsonToken var6 = var2.nextToken();
         JsonToken var7 = var2.getCurrentToken();
         JsonToken var8 = JsonToken.END_OBJECT;
         if(var7 != var8) {
            JsonParser var9 = mJsonFactory.createJsonParser(var1);
            JsonToken var10 = var9.nextToken();
            throw new FacebookApiException(var9);
         }
      }

      JsonParser var11 = mJsonFactory.createJsonParser(var1);
      JsonToken var12 = var11.nextToken();
      List var13 = JMParser.parseObjectListJson(var11, MinorStatusModel.class);
      if(var13 != null && var13.size() == 1) {
         this.mResponse = var1;
         Boolean var14 = Boolean.valueOf(((MinorStatusModel)var13.get(0)).isMinor);
         this.mIsMinor = var14;
      } else {
         throw new IllegalArgumentException("unexpected number of results");
      }
   }
}
