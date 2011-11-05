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

public class StreamAddLike extends ApiMethod {

   public StreamAddLike(Context var1, Intent var2, String var3, long var4, String var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "stream.addLike", var8, var7);
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("session_key", var3);
      Map var20 = this.mParams;
      String var21 = "" + var4;
      var20.put("uid", var21);
      this.mParams.put("post_id", var6);
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      }
   }
}
