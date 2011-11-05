package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.features.places.PlacesUserSettings;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class PlacesSetTaggingOptInStatus extends ApiMethod implements ApiMethodCallback {

   public static final long INVALID_ID = 255L;


   public PlacesSetTaggingOptInStatus(Context var1, Intent var2, String var3, ApiMethodListener var4, int var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "places.setTaggingOptInStatus", var6, var4);
      Map var11 = this.mParams;
      String var12 = Long.toString(System.currentTimeMillis());
      var11.put("call_id", var12);
      this.mParams.put("session_key", var3);
      Map var15 = this.mParams;
      String var16 = Integer.toString(var5);
      var15.put("value", var16);
   }

   public static String SetStatus(Context var0, int var1) {
      AppSession var2 = AppSession.getActiveSession(var0, (boolean)0);
      String var3 = var2.getSessionInfo().sessionKey;
      Object var5 = null;
      PlacesSetTaggingOptInStatus var7 = new PlacesSetTaggingOptInStatus(var0, (Intent)null, var3, (ApiMethodListener)var5, var1);
      short var11 = 1001;
      Object var12 = null;
      return var2.postToService(var0, var7, 1001, var11, (Bundle)var12);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      if(var5 == 200) {
         PlacesUserSettings.setSetting(var2, "places_opt_in", "enabled");
      }

      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         var9.onSetTaggingOptInStatusComplete(var1, var4, var5, var6, var7);
      }

   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.VALUE_NULL;
      if(var2 != var3) {
         throw new FacebookApiException(var1);
      }
   }
}
