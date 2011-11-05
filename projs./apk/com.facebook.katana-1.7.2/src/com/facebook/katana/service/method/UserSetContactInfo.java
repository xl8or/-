package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
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

public class UserSetContactInfo extends ApiMethod implements ApiMethodCallback {

   public static final String CELL_PARAM = "cell";


   protected UserSetContactInfo(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "userSetContactInfo", var6, var4);
      Map var11 = this.mParams;
      String var12 = String.valueOf(System.currentTimeMillis());
      var11.put("call_id", var12);
      this.mParams.put("session_key", var3);
      this.mParams.put("cell", var5);
   }

   public static String setCellNumber(AppSession var0, Context var1, String var2) {
      String var3 = var0.getSessionInfo().sessionKey;
      Object var5 = null;
      UserSetContactInfo var7 = new UserSetContactInfo(var1, (Intent)null, var3, (ApiMethodListener)var5, var2);
      Object var11 = null;
      return var0.postToService(var1, var7, 1001, 1020, (Bundle)var11);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         var9.onUserSetContactInfoComplete(var1, var4, var5, var6, var7);
      }

   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{") || !var1.trim().equals("true")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      }
   }
}
