package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class UserUnregisterPushCallback extends ApiMethod {

   private static final String METHOD = "user.unregisterPushCallback";
   private boolean mSuccess;


   public UserUnregisterPushCallback(Context var1, Intent var2, String var3, ApiMethodListener var4) {
      String var5 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "user.unregisterPushCallback", var5, var4);
      Map var10 = this.mParams;
      String var11 = Long.toString(System.currentTimeMillis());
      var10.put("call_id", var11);
      this.mParams.put("session_key", var3);
      this.mSuccess = (boolean)0;
   }

   public boolean getSuccess() {
      return this.mSuccess;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.VALUE_TRUE;
      if(var2 == var3) {
         this.mSuccess = (boolean)1;
         KeyValueManager.delete(this.mContext, "key=\"C2DMKey\"", (String[])null);
      }
   }
}
