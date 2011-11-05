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
import org.json.JSONException;
import org.json.JSONStringer;

public class UserRegisterPushCallback extends ApiMethod {

   public static final String KEY_VALUE_REG_ID = "C2DMKey";
   private static final String METHOD = "user.registerPushCallback";
   private static final String TOKEN_PARAM = "token";
   private boolean mSuccess;
   private final String mToken;


   public UserRegisterPushCallback(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "user.registerPushCallback", var6, var4);
      Map var11 = this.mParams;
      String var12 = Long.toString(System.currentTimeMillis());
      var11.put("call_id", var12);
      this.mParams.put("session_key", var3);
      Map var15 = this.mParams;
      String var16 = this.protocolParams(var5);
      var15.put("protocol_params", var16);
      this.mSuccess = (boolean)0;
      this.mToken = var5;
   }

   private String protocolParams(String var1) {
      String var2;
      String var3;
      try {
         var2 = (new JSONStringer()).object().key("token").value(var1).endObject().toString();
      } catch (JSONException var5) {
         var3 = "";
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public boolean getSuccess() {
      return this.mSuccess;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.VALUE_TRUE;
      if(var2 == var3) {
         this.mSuccess = (boolean)1;
         Context var4 = this.mContext;
         String var5 = this.mToken;
         KeyValueManager.setValue(var4, "C2DMKey", var5);
      }
   }
}
