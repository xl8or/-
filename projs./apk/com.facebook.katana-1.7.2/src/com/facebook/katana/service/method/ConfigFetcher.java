package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiLogging;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class ConfigFetcher extends ApiMethod {

   private static final String PROP_API_LOGGING_RATIO = "api_logging_ratio";
   private static final String PROP_NAMES_PARAM = "prop_names";
   private static final String PROP_TRX_LOGGING_RATIO = "trx_logging_ratio";
   private static final String mFacebookMethod = "admin.getPrivateConfig";


   public ConfigFetcher(Context var1, Intent var2, String var3, ApiMethodListener var4) {
      String var5 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "admin.getPrivateConfig", var5, var4);
      Map var10 = this.mParams;
      StringBuilder var11 = (new StringBuilder()).append("");
      long var12 = System.currentTimeMillis();
      String var14 = var11.append(var12).toString();
      var10.put("call_id", var14);
      this.mParams.put("session_key", var3);
      Object var17 = this.mParams.put("prop_names", "api_logging_ratio,trx_logging_ratio");
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      int var3 = ApiLogging.API_LOG_RATIO;
      int var4 = ApiLogging.TRX_LOG_RATIO;
      JsonToken var5 = JsonToken.START_OBJECT;
      if(var2 == var5) {
         JsonToken var6 = var1.nextToken();
         int var7 = -1;
         String var8 = null;

         while(true) {
            JsonToken var9 = JsonToken.END_OBJECT;
            if(var6 == var9) {
               if(var7 > 0) {
                  throw new FacebookApiException(var7, var8);
               }

               ApiLogging.updateLogRatios(var3, var4);
               return;
            }

            JsonToken var10 = JsonToken.VALUE_NUMBER_INT;
            if(var6 == var10) {
               String var11 = var1.getCurrentName();
               if(var11.equals("api_logging_ratio")) {
                  var3 = var1.getIntValue();
               } else if(var11.equals("trx_logging_ratio")) {
                  var4 = var1.getIntValue();
               } else if(var11.equals("error_code")) {
                  var7 = var1.getIntValue();
               }
            } else {
               JsonToken var12 = JsonToken.VALUE_STRING;
               if(var6 == var12 && var1.getCurrentName().equals("error_msg")) {
                  var8 = var1.getText();
               }
            }

            var6 = var1.nextToken();
         }
      } else {
         throw new IOException("Malformed JSON");
      }
   }
}
