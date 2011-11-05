package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class StreamRemove extends ApiMethod {

   public StreamRemove(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "stream.remove", var6, var5);
      Map var11 = this.mParams;
      StringBuilder var12 = (new StringBuilder()).append("");
      long var13 = System.currentTimeMillis();
      String var15 = var12.append(var13).toString();
      var11.put("call_id", var15);
      this.mParams.put("session_key", var3);
      this.mParams.put("post_id", var4);
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      }
   }
}
