package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookContactInfo;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class UsersGetContactInfo extends ApiMethod {

   private final List<FacebookContactInfo> mContactInfoList;


   public UsersGetContactInfo(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "users.getContactInfo", var6, var5);
      Map var11 = this.mParams;
      StringBuilder var12 = (new StringBuilder()).append("");
      long var13 = System.currentTimeMillis();
      String var15 = var12.append(var13).toString();
      var11.put("call_id", var15);
      this.mParams.put("session_key", var3);
      this.mParams.put("uids", var4);
      ArrayList var19 = new ArrayList();
      this.mContactInfoList = var19;
   }

   public List<FacebookContactInfo> getContactInfoList() {
      return this.mContactInfoList;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      JsonToken var6;
      if(var2 == var3) {
         int var4 = -1;
         String var5 = null;
         var6 = var1.nextToken();

         while(true) {
            JsonToken var7 = JsonToken.END_OBJECT;
            if(var6 == var7) {
               if(var4 == -1) {
                  return;
               }

               throw new FacebookApiException(var4, var5);
            }

            JsonToken var8 = JsonToken.VALUE_NUMBER_INT;
            if(var6 == var8) {
               if(var1.getCurrentName().equals("error_code")) {
                  var4 = var1.getIntValue();
               }
            } else {
               JsonToken var9 = JsonToken.VALUE_STRING;
               if(var6 == var9 && var1.getCurrentName().equals("error_msg")) {
                  var5 = var1.getText();
               }
            }

            var6 = var1.nextToken();
         }
      } else {
         JsonToken var10 = JsonToken.START_ARRAY;
         if(var2 == var10) {
            var6 = var1.nextToken();

            while(true) {
               JsonToken var11 = JsonToken.END_ARRAY;
               if(var6 == var11) {
                  return;
               }

               JsonToken var12 = JsonToken.START_OBJECT;
               if(var6 == var12) {
                  List var13 = this.mContactInfoList;
                  FacebookContactInfo var14 = new FacebookContactInfo(var1);
                  var13.add(var14);
               }

               var6 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}
